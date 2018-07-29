package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.DispatchWorkOrderCommon;
import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrder;
import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrderRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.DispatchWorkOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DispatchWorkOrderRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DispatchWorkOrderDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DispatchWorkOrderRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.DispatchWorkOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-23 17:12
 * @Description EAM派工工单接口
 */
@Service
public class DispatchWorkOrderServiceImpl implements DispatchWorkOrderService {

    @Autowired
    private DispatchWorkOrderDao dispatchWorkOrderDao;

    @Autowired
    private DispatchWorkOrderRepository dispatchWorkOrderRepository;

    @Autowired
    private DispatchWorkOrderRfCollectorDao dispatchWorkOrderRfCollectorDao;

    @Autowired
    private DispatchWorkOrderRfCollectorRepository dispatchWorkOrderRfCollectorRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DispatchWorkOrder findOne(String id) {
        return dispatchWorkOrderRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DispatchWorkOrderFlowVo findDispatchWorkOrderFlowVoById(String id) {
        return dispatchWorkOrderDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<DispatchWorkOrderFlowVo> findDispatchWorkOrderFlowVoByIdList(String[] ids) {
        if (ids == null || ids.length <= 0) {
            return Collections.EMPTY_LIST;
        }
        return dispatchWorkOrderDao.findList(Arrays.asList(ids));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<DispatchWorkOrderListVo> findListByFilter(DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo) {
        PageHelper.startPage(dispatchWorkOrderListFilterVo.getPageNum(), dispatchWorkOrderListFilterVo.getPageSize());
        if(dispatchWorkOrderListFilterVo.getSorts()!=null&& "reportPerson".equals(dispatchWorkOrderListFilterVo.getSorts())){
            dispatchWorkOrderListFilterVo.setSorts("reportPersonId");
        }
        //因sorts异常传参导致查询失败

         if(dispatchWorkOrderListFilterVo.getSorts()!=null&&dispatchWorkOrderListFilterVo.getSorts().contains("desc")){
             dispatchWorkOrderListFilterVo.setSorts(dispatchWorkOrderListFilterVo.getSorts().replaceAll("desc",""));
         }
        return dispatchWorkOrderDao.findListByFilter(dispatchWorkOrderListFilterVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DispatchWorkOrder saveOrUpdate(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        DispatchWorkOrder dispatchWorkOrder;
        //新增
        if (StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())) {
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderNum()), "工单编号不能为空。");
            //Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getDescription()), "派工描述不能为空。");  因为上传图片需要直接保存，保存时不再校验
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getReportPersonId()), "提报人不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getOrgId()), "所属组织不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getSiteId()), "所属站点不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getOperatorPersonId()), "获取当前用户失败。");

            Integer checkResult = dispatchWorkOrderDao.checkWorkOrderNum(dispatchWorkOrderFlowVo.getWorkOrderNum());
            Assert.isTrue(Objects.isNull(checkResult), "工单编号重复。");

            dispatchWorkOrder = new DispatchWorkOrder();
            updateForDTBSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
            //工单编号、组织及站点信息仅在创建时保存
            dispatchWorkOrder.setWorkOrderNum(dispatchWorkOrderFlowVo.getWorkOrderNum());
            dispatchWorkOrder.setOrgId(dispatchWorkOrderFlowVo.getOrgId());
            dispatchWorkOrder.setSiteId(dispatchWorkOrderFlowVo.getSiteId());
            //记录创建人
            dispatchWorkOrder.setCreateUser(dispatchWorkOrderFlowVo.getOperatorPersonId());

            updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB);

            dispatchWorkOrderRepository.save(dispatchWorkOrder);
            dispatchWorkOrderFlowVo.setId(dispatchWorkOrder.getId());
            dispatchWorkOrderFlowVo.setWorkOrderId(dispatchWorkOrder.getId());
        } else {
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()), "未知工单。");

            dispatchWorkOrder = dispatchWorkOrderRepository.findOne(dispatchWorkOrderFlowVo.getWorkOrderId());
            Assert.isTrue(Objects.nonNull(dispatchWorkOrder), "未知工单。");
            Assert.isTrue(dispatchWorkOrder.getWorkOrderStatus().equals(dispatchWorkOrderFlowVo.getWorkOrderStatus()), "工单状态不匹配，请刷新后重新尝试！");

            switch (dispatchWorkOrder.getWorkOrderStatus()) {
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB: {
                    updateForDTBSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                }
                break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP: {
                    updateForDFPSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                }
                break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB: {
                    updateForDHBSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                }
                break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS: {
                    updateForDYSSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                }
                break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_CLOSE:
                    break;
                default:
                    throw new EnerbosException(String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrder.getWorkOrderStatus()), "500");
            }
            dispatchWorkOrderRepository.save(dispatchWorkOrder);
        }

        return dispatchWorkOrder;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DispatchWorkOrder save(DispatchWorkOrder dispatchWorkOrder) {
        return dispatchWorkOrderRepository.save(dispatchWorkOrder);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DispatchWorkOrderFlowVo commitWorkOrder(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        DispatchWorkOrder dispatchWorkOrder;
        Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getDescription()), "派工描述不能为空。");
        boolean isCreate = StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())
                && (StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderStatus()) ||
                (DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB.equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())
                        || DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP.equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())));

        //允许工单在创建环节不保存时直接提交
        if (isCreate) {
            dispatchWorkOrder = this.saveOrUpdate(dispatchWorkOrderFlowVo);
        } else {
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()), "未知工单。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderStatus()), "工单状态不能为空。");

            dispatchWorkOrder = dispatchWorkOrderRepository.findOne(dispatchWorkOrderFlowVo.getWorkOrderId());
            Assert.isTrue(Objects.nonNull(dispatchWorkOrder), "未知工单。");
            Assert.isTrue(dispatchWorkOrder.getWorkOrderStatus().equals(dispatchWorkOrderFlowVo.getWorkOrderStatus()), "工单状态不匹配，请刷新后重新尝试！");
        }

        switch (dispatchWorkOrder.getWorkOrderStatus()) {
            case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB: {
                processPreSaveDispatchWorkOrderForDTB(dispatchWorkOrder, dispatchWorkOrderFlowVo);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP: {
                processPreSaveDispatchWorkOrderForDFP(dispatchWorkOrder, dispatchWorkOrderFlowVo);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB: {
                processPreSaveDispatchWorkOrderForDHB(dispatchWorkOrder, dispatchWorkOrderFlowVo);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS: {
                processPreSaveDispatchWorkOrderForDYS(dispatchWorkOrder, dispatchWorkOrderFlowVo);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_CLOSE:
                throw new EnerbosException("500", "工单已关闭！");
            default:
                throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrder.getWorkOrderStatus()));
        }

        dispatchWorkOrderRepository.save(dispatchWorkOrder);
        return dispatchWorkOrderFlowVo;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, String> changeOrderStatus(List<String> ids, String status) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        Map<String, String> oldStatusMap = new HashMap<>();
        List<DispatchWorkOrder> updateList = new ArrayList<>();
        DispatchWorkOrder dispatchWorkOrder;
        for (int i = 0, len = ids.size(); i < len; i++) {
            dispatchWorkOrder = dispatchWorkOrderRepository.findOne(ids.get(i));

            if (dispatchWorkOrder == null) {
                throw new EnerbosException("404", String.format("工单编号不存在！  [%s]", ids.get(i)));
            }

            //记录原始状态
            oldStatusMap.put(dispatchWorkOrder.getId(), dispatchWorkOrder.getWorkOrderStatus());
            dispatchWorkOrder.setWorkOrderStatus(status);
            updateList.add(dispatchWorkOrder);
        }

        dispatchWorkOrderRepository.save(updateList);
        return oldStatusMap;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return;
        }

        List<DispatchWorkOrder> deleteList = new ArrayList<>();
        DispatchWorkOrder dispatchWorkOrder;
        for (int i = 0, len = ids.size(); i < len; i++) {
            dispatchWorkOrder = dispatchWorkOrderRepository.findOne(ids.get(i));

            if (dispatchWorkOrder == null) {
                continue;
                //throw new EnerbosException("404", String.format("工单编号不存在！  [%s]", ids.get(i)));
            }

            if (!DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB.equals(dispatchWorkOrder.getWorkOrderStatus())) {
                throw new EnerbosException("500", String.format("指定工单已经提报，不允许删除！  [%s] 状态：[%s]", dispatchWorkOrder.getWorkOrderNum(), dispatchWorkOrder.getWorkOrderStatus()));
            }
            deleteList.add(dispatchWorkOrder);
        }

        dispatchWorkOrderRepository.deleteInBatch(deleteList);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void collectWorkOrder(List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList) {
        if (dispatchWorkOrderRfCollectorVoList == null || dispatchWorkOrderRfCollectorVoList.isEmpty()) {
            return;
        }
        //过滤重复数据
        Map<String, List<DispatchWorkOrderRfCollectorVo>> map = dispatchWorkOrderRfCollectorVoList.stream()
                .filter(vo -> Objects.nonNull(vo.getWorkOrderId()))
                .filter(vo -> Objects.nonNull(vo.getPersonId()))
                .collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getWorkOrderId(), o.getPersonId()), Collectors.toList()));

        List<DispatchWorkOrderRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new DispatchWorkOrderRfCollector(vo.getWorkOrderId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
        list.stream().filter(o -> 0 == dispatchWorkOrderRfCollectorDao.checkIsCollected(o.getWorkOrderId(), o.getPersonId(), o.getProduct())).forEach(dispatchWorkOrderRfCollectorRepository::save);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cancelCollectWorkOrder(List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList) {
        if (dispatchWorkOrderRfCollectorVoList == null || dispatchWorkOrderRfCollectorVoList.isEmpty()) {
            return;
        }
        Map<String, List<DispatchWorkOrderRfCollectorVo>> map = dispatchWorkOrderRfCollectorVoList.stream().collect(Collectors.groupingBy(DispatchWorkOrderRfCollectorVo::getPersonId, Collectors.toList()));
        for (Map.Entry<String, List<DispatchWorkOrderRfCollectorVo>> entry : map.entrySet()) {
            List<DispatchWorkOrderRfCollector> list = dispatchWorkOrderRfCollectorDao.findWorkOrderRfCollectorByPersonId(entry.getKey());

            if (!ObjectUtils.isEmpty(list)) {
                Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(DispatchWorkOrderRfCollectorVo::getWorkOrderId, DispatchWorkOrderRfCollectorVo::getPersonId));

                list.stream().filter(o -> collectedIdMap.containsKey(o.getWorkOrderId())).forEach(o -> dispatchWorkOrderRfCollectorRepository.delete(o.getId()));
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public OrderCountBySiteVo findDispatchWorkOrderTotal(Map<String, Object> map) {
        return dispatchWorkOrderDao.findDispatchWorkOrderTotal(map);
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

        OrderMaxCountVo orderMaxCountVo = dispatchWorkOrderDao.findMaxCountOrder(param);
        return orderMaxCountVo ;
    }

    /**
     * 针对待提报工单保存处理
     */
    private void processPreSaveDispatchWorkOrderForDTB(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                dispatchWorkOrder.setProcessInstanceId(dispatchWorkOrderFlowVo.getProcessInstanceId());
                updateForDTBSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                //进入待分派环节
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP);
            }
            break;
            default:
                throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", dispatchWorkOrder.getWorkOrderStatus(), dispatchWorkOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待分派工单保存处理
     */
    private void processPreSaveDispatchWorkOrderForDFP(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                updateForDFPSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                //只有在发送流程时，才更新分派人信息
                dispatchWorkOrder.setDispatchPersonId(dispatchWorkOrderFlowVo.getDispatchPersonId());

                //进入待接单环节
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT: {
                updateForDFPSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                //只有在发送流程时，才更新分派人信息
                dispatchWorkOrder.setDispatchPersonId(dispatchWorkOrderFlowVo.getDispatchPersonId());

                //进入待提报环节
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB);
            }
            break;
            default:
                throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", dispatchWorkOrder.getWorkOrderStatus(), dispatchWorkOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待汇报工单保存处理
     */
    private void processPreSaveDispatchWorkOrderForDHB(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                //更新工单汇报人信息
                dispatchWorkOrder.setOrderReportPersonId(dispatchWorkOrderFlowVo.getOperatorPersonId());
                updateForDHBSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                //进入待验收环节
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT: {
                //更新工单汇报人信息
                dispatchWorkOrder.setOrderReportPersonId(dispatchWorkOrderFlowVo.getOperatorPersonId());
                updateForDHBSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                //进入待分派环节
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP);
            }
            break;
            default:
                throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", dispatchWorkOrder.getWorkOrderStatus(), dispatchWorkOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待验收工单保存处理
     */
    private void processPreSaveDispatchWorkOrderForDYS(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                updateForDYSSector(dispatchWorkOrder, dispatchWorkOrderFlowVo);
                //流程结束，状态变更为关闭
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_CLOSE);
            }
            break;
            case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT: {
                //待验收驳回，回退至待汇报环节
                updateOrderStatus(dispatchWorkOrder, dispatchWorkOrderFlowVo, DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB);
            }
            break;
            default:
                throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", dispatchWorkOrder.getWorkOrderStatus(), dispatchWorkOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 更新待提报环节数据字段
     */
    private void updateForDTBSector(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        dispatchWorkOrder.setDescription(dispatchWorkOrderFlowVo.getDescription());
        //提报人信息
        dispatchWorkOrder.setReportPersonId(dispatchWorkOrderFlowVo.getReportPersonId());
        dispatchWorkOrder.setReportPersonTel(dispatchWorkOrderFlowVo.getReportPersonTel());
        dispatchWorkOrder.setReportDate(dispatchWorkOrderFlowVo.getReportDate());
        dispatchWorkOrder.setReportRemarks(dispatchWorkOrderFlowVo.getReportRemarks());
        //报修人信息
        dispatchWorkOrder.setDemandDept(dispatchWorkOrderFlowVo.getDemandDept());
        dispatchWorkOrder.setDemandPerson(dispatchWorkOrderFlowVo.getDemandPerson());
        dispatchWorkOrder.setDemandPersonTel(dispatchWorkOrderFlowVo.getDemandPersonTel());
    }

    /**
     * 更新待分派环节数据字段
     */
    private void updateForDFPSector(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        dispatchWorkOrder.setPlanCompleteTime(dispatchWorkOrderFlowVo.getPlanCompleteTime());
    }

    /**
     * 更新待汇报环节数据字段
     */
    private void updateForDHBSector(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        dispatchWorkOrder.setReceiveTime(dispatchWorkOrderFlowVo.getReceiveTime());
        dispatchWorkOrder.setCompletionTime(dispatchWorkOrderFlowVo.getCompletionTime());
        dispatchWorkOrder.setConsumeHours(dispatchWorkOrderFlowVo.getConsumeHours());
        dispatchWorkOrder.setReportDescription(dispatchWorkOrderFlowVo.getReportDescription());
    }

    /**
     * 更新待验收环节数据字段
     */
    private void updateForDYSSector(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        dispatchWorkOrder.setAcceptTime(dispatchWorkOrderFlowVo.getAcceptTime());
        dispatchWorkOrder.setAcceptDescription(dispatchWorkOrderFlowVo.getAcceptDescription());
    }

    /**
     * 更新工单状态字段
     */
    private void updateOrderStatus(DispatchWorkOrder dispatchWorkOrder, DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, String status) {
        dispatchWorkOrder.setWorkOrderStatus(status);
        dispatchWorkOrder.setWorkOrderStatusDate(new Date());
        dispatchWorkOrderFlowVo.setWorkOrderStatus(status);
        dispatchWorkOrderFlowVo.setWorkOrderStatusDate(dispatchWorkOrder.getWorkOrderStatusDate());
    }
}
