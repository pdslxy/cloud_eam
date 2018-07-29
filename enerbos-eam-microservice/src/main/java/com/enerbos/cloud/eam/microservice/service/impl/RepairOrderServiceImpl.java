package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.RepairOrderCommon;
import com.enerbos.cloud.eam.microservice.domain.RepairOrder;
import com.enerbos.cloud.eam.microservice.domain.RepairOrderRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.RepairOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.RepairOrderRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.RepairOrderDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.RepairOrderRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.RepairOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
 * @date 2017-07-11 18:14
 * @Description EAM报修工单接口
 */
@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderDao repairOrderDao;

    @Autowired
    private RepairOrderRepository repairOrderRepository;

    @Autowired
    private RepairOrderRfCollectorDao repairOrderRfCollectorDao;

    @Autowired
    private RepairOrderRfCollectorRepository repairOrderRfCollectorRepository;

    @Override
    public RepairOrder findOne(String id) {
        return repairOrderRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RepairOrderFlowVo findRepairOrderFlowVoById(String id) {
        return repairOrderDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<RepairOrderFlowVo> findRepairOrderFlowVoByIdList(String[] ids) {
        if (ids == null || ids.length <= 0) {
            return Collections.EMPTY_LIST;
        }
        return repairOrderDao.findList(Arrays.asList(ids));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<RepairOrderListVo> findListByFilter(RepairOrderListFilterVo repairOrderListFilterVo) {
        PageHelper.startPage(repairOrderListFilterVo.getPageNum(), repairOrderListFilterVo.getPageSize());
        return repairOrderDao.findListByFilter(repairOrderListFilterVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RepairOrder saveOrUpdate(RepairOrderFlowVo repairOrderFlowVo) {
        RepairOrder repairOrder;
        //新增
        if (StringUtils.isEmpty(repairOrderFlowVo.getWorkOrderId())) {
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderNum()), "工单编号不能为空。");
            //Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getDescription()), "报修描述不能为空。");   因为上传图片需要直接保存，保存时不再校验
            //Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getProjectType()), "工程类型不能为空。");
            //Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderSource()), "工单来源不能为空。");
            //Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getReportPersonId()), "提报人不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getOrgId()), "所属组织不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getSiteId()), "所属站点不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getOperatorPersonId()), "获取当前用户失败。");

            Integer checkResult = repairOrderDao.checkWorkOrderNum(repairOrderFlowVo.getWorkOrderNum());
            Assert.isTrue(Objects.isNull(checkResult), "工单编号重复。");

            repairOrder = new RepairOrder();
            updateForDTBSector(repairOrder, repairOrderFlowVo);
            //工单编号、组织及站点信息仅在创建时保存
            repairOrder.setWorkOrderNum(repairOrderFlowVo.getWorkOrderNum());
            repairOrder.setOrgId(repairOrderFlowVo.getOrgId());
            repairOrder.setSiteId(repairOrderFlowVo.getSiteId());
            //记录创建人
            repairOrder.setCreateUser(repairOrderFlowVo.getOperatorPerson());

            updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DTB);

            repairOrderRepository.save(repairOrder);
            repairOrderFlowVo.setId(repairOrder.getId());
            repairOrderFlowVo.setWorkOrderId(repairOrder.getId());
        } else {
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderId()), "未知工单。");

            repairOrder = repairOrderRepository.findOne(repairOrderFlowVo.getWorkOrderId());
            Assert.isTrue(Objects.nonNull(repairOrder), "未知工单。");
            Assert.isTrue(repairOrder.getWorkOrderStatus().equals(repairOrderFlowVo.getWorkOrderStatus()), "工单状态不匹配，请刷新后重新尝试！");

            switch (repairOrder.getWorkOrderStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB: {
                    updateForDTBSector(repairOrder, repairOrderFlowVo);
                } break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP: {
                    updateForDFPSector(repairOrder, repairOrderFlowVo);
                } break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB: {
                    updateForDHBSector(repairOrder, repairOrderFlowVo);
                } break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS: {
                    updateForDYSSector(repairOrder, repairOrderFlowVo);
                } break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR: {
                    updateForYSDQRSector(repairOrder, repairOrderFlowVo);
                } break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ: break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT: break;
                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrder.getWorkOrderStatus()));
            }
            repairOrderRepository.save(repairOrder);
        }

        return repairOrder;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RepairOrder save(RepairOrder repairOrder) {
        if (StringUtils.isEmpty(repairOrder.getId())) {
            Assert.isTrue(StringUtils.isNotEmpty(repairOrder.getWorkOrderNum()), "工单编号不能为空。");
            Integer checkResult = repairOrderDao.checkWorkOrderNum(repairOrder.getWorkOrderNum());
            Assert.isTrue(Objects.isNull(checkResult), "工单编号重复。");
        }
        return repairOrderRepository.save(repairOrder);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RepairOrderFlowVo reportOrder(RepairOrderFlowVo repairOrderFlowVo) {
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderId()), "未知工单。");
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderStatus()), "工单状态不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getDescription()), "报修描述不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getProjectType()), "工程类型不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderSource()), "工单来源不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getReportPersonId()), "提报人不能为空。");

        RepairOrder repairOrder = repairOrderRepository.findOne(repairOrderFlowVo.getWorkOrderId());
        Assert.isTrue(Objects.nonNull(repairOrder), "未知工单。");
        Assert.isTrue(repairOrder.getWorkOrderStatus().equals(repairOrderFlowVo.getWorkOrderStatus()), "工单状态不匹配，请刷新后重新尝试！");

        switch (repairOrder.getWorkOrderStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB: {
                processPreSaveRepairOrderForDTB(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP: {
                processPreSaveRepairOrderForDFP(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DJD: {
                processPreSaveRepairOrderForDJD(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB: {
                processPreSaveRepairOrderForDHB(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS: {
                processPreSaveRepairOrderForDYS(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR: {
                processPreSaveRepairOrderForYSDQR(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ: {
                processPreSaveRepairOrderForWAIT_SQ(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT: {
                processPreSaveRepairOrderForWAIT(repairOrder, repairOrderFlowVo);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_CANCEL: throw new EnerbosException("500", "工单已取消！");
            case RepairOrderCommon.REPAIR_ORDER_STATUS_CLOSE: throw new EnerbosException("500", "工单已关闭！");
            default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrder.getWorkOrderStatus()));
        }

        repairOrderRepository.save(repairOrder);
        return repairOrderFlowVo;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return;
        }

        List<RepairOrder> deleteList = new ArrayList<>();
        RepairOrder repairOrder;
        for (int i = 0, len = ids.size(); i < len; i++) {
            repairOrder = repairOrderRepository.findOne(ids.get(i));

            if (repairOrder == null) {
                continue;
                //throw new EnerbosException("404", String.format("工单编号不存在！  [%s]", ids.get(i)));
            }

            if (!RepairOrderCommon.REPAIR_ORDER_STATUS_DTB.equals(repairOrder.getWorkOrderStatus())) {
                throw new EnerbosException("500", String.format("指定工单已经提报，不允许删除！  当前状态：[%s]", repairOrder.getWorkOrderStatus()));
            }
            deleteList.add(repairOrder);
        }

        repairOrderRepository.deleteInBatch(deleteList);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, String> changeOrderStatus(List<String> ids, String status) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        Map<String, String> oldStatusMap = new HashMap<>();
        List<RepairOrder> updateList = new ArrayList<>();
        RepairOrder repairOrder;
        for (int i = 0, len = ids.size(); i < len; i++) {
            repairOrder = repairOrderRepository.findOne(ids.get(i));

            if (repairOrder == null) {
                throw new EnerbosException("404", String.format("工单编号不存在！  [%s]", ids.get(i)));
            }

            //记录原始状态
            oldStatusMap.put(repairOrder.getId(), repairOrder.getWorkOrderStatus());
            repairOrder.setWorkOrderStatus(status);
            updateList.add(repairOrder);
        }

        repairOrderRepository.save(updateList);
        return oldStatusMap;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void collectWorkOrder(List<RepairOrderRfCollectorVo> repairOrderRfCollectorVoList) {
        if (repairOrderRfCollectorVoList == null || repairOrderRfCollectorVoList.isEmpty()) {
            return;
        }
        //过滤重复数据
        Map<String, List<RepairOrderRfCollectorVo>> map = repairOrderRfCollectorVoList.stream()
                .filter(vo -> Objects.nonNull(vo.getWorkOrderId()))
                .filter(vo -> Objects.nonNull(vo.getPersonId()))
                .collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getWorkOrderId(), o.getPersonId()), Collectors.toList()));

        List<RepairOrderRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new RepairOrderRfCollector(vo.getWorkOrderId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
        list.stream().filter(o -> 0 == repairOrderRfCollectorDao.checkIsCollected(o.getWorkOrderId(), o.getPersonId(), o.getProduct())).forEach(repairOrderRfCollectorRepository::save);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cancelCollectWorkOrder(List<RepairOrderRfCollectorVo> repairOrderRfCollectorVoList) {
        if (repairOrderRfCollectorVoList == null || repairOrderRfCollectorVoList.isEmpty()) {
            return;
        }
        Map<String, List<RepairOrderRfCollectorVo>> map = repairOrderRfCollectorVoList.stream().collect(Collectors.groupingBy(RepairOrderRfCollectorVo::getPersonId, Collectors.toList()));
        for (Map.Entry<String, List<RepairOrderRfCollectorVo>> entry : map.entrySet()) {
            List<RepairOrderRfCollector> list = repairOrderRfCollectorDao.findWorkOrderRfCollectorByPersonId(entry.getKey());

            if (!ObjectUtils.isEmpty(list)) {
                Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(RepairOrderRfCollectorVo::getWorkOrderId, RepairOrderRfCollectorVo::getPersonId));

                list.stream().filter(o -> collectedIdMap.containsKey(o.getWorkOrderId())).forEach(o -> repairOrderRfCollectorRepository.delete(o.getId()));
            }
        }
    }

    /**
     * 针对待提报工单保存处理
     */
    private void processPreSaveRepairOrderForDTB(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                //更新流程ID
                repairOrder.setProcessInstanceId(repairOrderFlowVo.getProcessInstanceId());
                //更新关联设备ID
                repairOrder.setRefAssetId(repairOrderFlowVo.getRefAssetId());
                updateForDTBSector(repairOrder, repairOrderFlowVo);
                //如果是
                if (Objects.nonNull(repairOrderFlowVo.getIgnoreDispatchFlow()) && repairOrderFlowVo.getIgnoreDispatchFlow()) {
                    //更新分派人信息
                    repairOrder.setDispatchPersonId(repairOrderFlowVo.getDispatchPersonId());
                    repairOrder.setDispatchTime(repairOrderFlowVo.getDispatchTime());
                    //进入待接单环节
                    updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DJD);
                } else {
                    //进入待分派环节
                    updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DFP);
                }
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待分派工单保存处理
     */
    private void processPreSaveRepairOrderForDFP(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                updateForDFPSector(repairOrder, repairOrderFlowVo);
                //只有在发送流程时，才更新分派人信息
                repairOrder.setDispatchPersonId(repairOrderFlowVo.getDispatchPersonId());
                repairOrder.setDispatchTime(repairOrderFlowVo.getDispatchTime());

                //进入待接单环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DJD);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                updateForDFPSector(repairOrder, repairOrderFlowVo);
                //移除分派人信息
                repairOrder.setDispatchPersonId(null);
                repairOrderFlowVo.setDispatchPersonId(null);
                repairOrder.setDispatchTime(null);
                repairOrderFlowVo.setDispatchTime(null);

                //待分派驳回，回退至待提报环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DTB);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL: {
                //待分派取消，直接关闭
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_CANCEL);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待接单工单保存处理
     */
    private void processPreSaveRepairOrderForDJD(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                //记录接单人数据
                repairOrder.setReceivePersonId(repairOrderFlowVo.getOperatorPersonId());
                repairOrderFlowVo.setReceivePersonId(repairOrderFlowVo.getOperatorPersonId());
                //进入待汇报环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DHB);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                repairOrder.setReceivePersonId(null);
                repairOrderFlowVo.setReceivePersonId(null);
                //待接单驳回，回退至待分派环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DFP);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待汇报工单保存处理
     */
    private void processPreSaveRepairOrderForDHB(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                updateForDHBSector(repairOrder, repairOrderFlowVo);
                //更新工单汇报人信息
                repairOrder.setOrderReportPersonId(repairOrderFlowVo.getOperatorPersonId());
                //进入待验收环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DYS);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                updateForDHBSector(repairOrder, repairOrderFlowVo);
                //待汇报挂起，进入申请挂起环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对申请挂起工单保存处理
     */
    private void processPreSaveRepairOrderForWAIT_SQ(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                //工单挂起，进入额外判断
                if (Common.GYSWX.equals(repairOrder.getSuspensionType())) {
                    //供应商维修，进入待验收环节
                    updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DYS);
                } else {
                    updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT);
                }
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                //申请挂起驳回，进入待汇报环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DHB);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对挂起工单保存处理
     */
    private void processPreSaveRepairOrderForWAIT(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                //#EAMI-35 挂起通过，进入待汇报环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DHB);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                //挂起驳回，进入待分派环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DFP);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对待验收工单保存处理
     */
    private void processPreSaveRepairOrderForDYS(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                updateForDYSSector(repairOrder, repairOrderFlowVo);

                boolean isClose = RepairOrderCommon.SYSTEM_AUTO.equals(repairOrder.getWorkOrderSource())
                        || (repairOrder.getSuspension() && Common.GYSWX.equals(repairOrder.getSuspensionType()))
                        || (repairOrderFlowVo.getCreateMaintenanceWorkOrder() && !repairOrderFlowVo.getConfirm());
                if (isClose) {
                    //1.如果工单来源为系统自动或供应商维修挂起，则不需要提报人最终确认，流程提前结束，工单状态变更为关闭
                    //2.如果工单未解决并且选择生成维保工单，流程提前结束，工单状态变更为关闭
                    updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_CLOSE);
                } else {
                    //进入验收待确认环节
                    updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR);
                }
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                //待验收驳回，回退至待汇报环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DHB);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 针对验收待确认工单保存处理
     */
    private void processPreSaveRepairOrderForYSDQR(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        switch (repairOrderFlowVo.getProcessStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                updateForYSDQRSector(repairOrder, repairOrderFlowVo);

                //流程结束，状态变更为关闭
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_CLOSE);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                //验收待确认驳回，回退至待验收环节
                updateOrderStatus(repairOrder, repairOrderFlowVo, RepairOrderCommon.REPAIR_ORDER_STATUS_DYS);
            } break;
            default: throw new EnerbosException("500", String.format("%1$s环节不支持此操作！当前状态: %1$s   目标状态：%2$s", repairOrder.getWorkOrderStatus(), repairOrderFlowVo.getProcessStatus()));
        }
    }

    /**
     * 更新待提报环节数据字段
     */
    private void updateForDTBSector(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        repairOrder.setDescription(repairOrderFlowVo.getDescription());
        //工单类型信息
        repairOrder.setWorkOrderSource(repairOrderFlowVo.getWorkOrderSource());
        repairOrder.setProjectType(repairOrderFlowVo.getProjectType());
        repairOrder.setIncidentNature(repairOrderFlowVo.getIncidentNature());
        repairOrder.setIncidentLevel(repairOrderFlowVo.getIncidentLevel());
        //提报人信息
        repairOrder.setReportAssignFlag(repairOrderFlowVo.getReportAssignFlag());
        repairOrder.setReportPersonId(repairOrderFlowVo.getReportPersonId());
        repairOrder.setReportPersonTel(repairOrderFlowVo.getReportPersonTel());
        repairOrder.setReportDate(repairOrderFlowVo.getReportDate());
        repairOrder.setReportDescription(repairOrderFlowVo.getReportDescription());
        //报修人信息
        repairOrder.setRepairDept(repairOrderFlowVo.getRepairDept());
        repairOrder.setRepairPerson(repairOrderFlowVo.getRepairPerson());
        repairOrder.setRepairPersonTel(repairOrderFlowVo.getRepairPersonTel());
        //小程序-工号
        repairOrder.setJobNumber(repairOrderFlowVo.getJobNumber());
    }

    /**
     * 更新待分派环节数据字段
     */
    private void updateForDFPSector(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        repairOrder.setEntrustExecute(repairOrderFlowVo.getEntrustExecute());
    }

    /**
     * 更新待汇报环节数据字段
     */
    private void updateForDHBSector(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        repairOrder.setCompletionTime(repairOrderFlowVo.getCompletionTime());
        repairOrder.setConsumeHours(repairOrderFlowVo.getConsumeHours());
        repairOrder.setSuspension(repairOrderFlowVo.getSuspension());
        repairOrder.setSuspensionType(repairOrderFlowVo.getSuspensionType());
        repairOrder.setSuspensionCause(repairOrderFlowVo.getSuspensionCause());
    }

    /**
     * 更新待验收环节数据字段
     */
    private void updateForDYSSector(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        repairOrder.setAcceptTime(repairOrderFlowVo.getAcceptTime());
        repairOrder.setConfirm(repairOrderFlowVo.getConfirm());
        repairOrder.setAcceptDescription(repairOrderFlowVo.getAcceptDescription());

        //关联报修工单相关数据
        repairOrder.setMaintenanceWorkOrderId(repairOrderFlowVo.getMaintenanceWorkOrderId());
        repairOrder.setMaintenanceWorkOrderNum(repairOrderFlowVo.getMaintenanceWorkOrderNum());
    }

    /**
     * 更新验收待确认环节数据字段
     */
    private void updateForYSDQRSector(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo) {
        //维修质量相关
        repairOrder.setMaintenanceQuality(repairOrderFlowVo.getMaintenanceQuality());
        repairOrder.setMaintenanceAttitude(repairOrderFlowVo.getMaintenanceAttitude());
    }

    /**
     * 更新工单状态字段
     */
    private void updateOrderStatus(RepairOrder repairOrder, RepairOrderFlowVo repairOrderFlowVo, String status) {
        repairOrder.setWorkOrderStatus(status);
        repairOrder.setWorkOrderStatusDate(new Date());
        repairOrderFlowVo.setWorkOrderStatus(status);
        repairOrderFlowVo.setWorkOrderStatusDate(repairOrder.getWorkOrderStatusDate());
    }

    //查询报修工单各情况的统计
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = DataAccessException.class,
            readOnly = true)
    @Override
    public List<Object> findCountRepair(String startDate, String enddate,String siteId) {
        List<Object> list = new ArrayList<>();
        list = repairOrderRepository.findCountRepair(startDate,enddate,siteId);
        return list;
    }

    //按月查询未修复报修工单各专业的统计分析
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = DataAccessException.class,
            readOnly = true)
    @Override
    public List<Object> findUndoneCountRepair(String startDate, String endDate, String siteId) {
        List<Object> list = new ArrayList<>();
        list = repairOrderRepository.findUndoneCountRepair(startDate,endDate,siteId);
        return list;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = DataAccessException.class,
            readOnly = true)
    @Override
    public OrderCountBySiteVo findCountByStatus(String orgId,String siteId,Date startDate, Date endDate) {
        OrderCountBySiteVo obj = repairOrderDao.findCountByStatus(orgId,siteId,startDate,endDate);
        return obj;
    }

    @Override
    public Map<String,Object> findCountAndRingratio(Map<String,String> map){
        Map<String,Object> map1=repairOrderDao.findCountAndRingratio(map);
        return map1;
    }

    @Override
    public Map<String,Object> findCountRepairAndEvaluate(Map<String,String> map){
        Map<String,Object> map1=repairOrderDao.findCountRepairAndEvaluate(map);
        return map1;
    }


    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            rollbackFor = DataAccessException.class,
            readOnly = true)
    public OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate) {
        Map<String, String> param = new HashMap<>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

        return repairOrderDao.findMaxCountOrder(param);
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            rollbackFor = DataAccessException.class,
            readOnly = true)
    public int getRepairOrderCount(String orgId, String siteId, String orderSource){
        return  repairOrderDao.getRepairOrderCount(orgId,siteId,orderSource);
    }
}
