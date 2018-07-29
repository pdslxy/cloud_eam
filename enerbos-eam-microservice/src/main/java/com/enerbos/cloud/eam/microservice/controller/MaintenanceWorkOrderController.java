package com.enerbos.cloud.eam.microservice.controller;


import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrder;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderService;
import com.enerbos.cloud.eam.microservice.service.RepairOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM维保工单接口
 */
@RestController
public class MaintenanceWorkOrderController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderController.class);

    @Resource
    protected MaintenanceWorkOrderService maintenanceWorkOrderService;

    @Resource
    protected RepairOrderService repairOrderService;

    /**
     * saveWorkOrderCommit:保存维保工单-工单提报
     *
     * @param maintenanceWorkOrderForCommitVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForCommitVo}
     * @return MaintenanceWorkOrderForCommitVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderCommit", method = RequestMethod.POST)
    public MaintenanceWorkOrderForCommitVo saveWorkOrderCommit(@RequestBody MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo) {
        MaintenanceWorkOrder maintenanceWorkOrder = new MaintenanceWorkOrder();
        try {
            List<String> ignoreList = new ArrayList<String>();
            ignoreList.add("locationsDesc");
            ignoreList.add("siteName");
            ignoreList.add("parentWoDesc");
            ignoreList.add("reportedName");
            ignoreList.add("assetList");
            ignoreList.add("addAssetList");
            ignoreList.add("deleteAssetList");
            ignoreList.add("eamImpleRecordVoVoList");
            ignoreList.add("actualExecutorResponsibleId");
            ReflectionUtils.copyProperties(maintenanceWorkOrderForCommitVo, maintenanceWorkOrder, ignoreList);
            maintenanceWorkOrder = maintenanceWorkOrderService.save(maintenanceWorkOrder);
            ReflectionUtils.copyProperties(maintenanceWorkOrder, maintenanceWorkOrderForCommitVo, null);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderCommit-----", e);
        }
        return maintenanceWorkOrderForCommitVo;
    }

    /**
     * saveWorkOrderAssign:保存维保工单-任务分派
     *
     * @param maintenanceWorkOrderForAssignVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForAssignVo}
     * @return MaintenanceWorkOrderForAssignVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderAssign", method = RequestMethod.POST)
    public MaintenanceWorkOrderForAssignVo saveWorkOrderAssign(@RequestBody MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo) {
        try {
            MaintenanceWorkOrder maintenanceWorkOrder = maintenanceWorkOrderService.findWorkOrderByID(maintenanceWorkOrderForAssignVo.getId());
            if (null == maintenanceWorkOrder || maintenanceWorkOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            List<String> ignoreList = new ArrayList<String>();
            ignoreList.add("id");
            ignoreList.add("siteId");
            ignoreList.add("orgId");
            ignoreList.add("status");
            ignoreList.add("statusDate");
            ignoreList.add("processInstanceId");
            ignoreList.add("maintenancePlanNum");
            ignoreList.add("reportId");

            ignoreList.add("assignPersonId");
            ignoreList.add("entrustExecutePersonName");
            ignoreList.add("executorPeopleName");
            ignoreList.add("entrustExecutePersonId");
            ignoreList.add("executorPeople");
            ignoreList.add("dispatchPeopleName");
            ignoreList.add("companiesName");

            ignoreList.add("jobStandardNum");
            ignoreList.add("jobStandardDesc");
//    		ignoreList.add("executionWorkGroup");
            ignoreList.add("executionWorkGroupName");
            ignoreList.add("actualExecutorResponsibleId");
            ignoreList.add("eamOrderstepVoList");
            ignoreList.add("deleteEamOrderstepVoList");
            ignoreList.add("eamNeedItemVoList");
            ignoreList.add("deleteEamNeedItemVoList");
            ignoreList.add("eamImpleRecordVoVoList");
            ReflectionUtils.copyProperties(maintenanceWorkOrderForAssignVo, maintenanceWorkOrder, ignoreList);
            maintenanceWorkOrder.setAcceptorId(maintenanceWorkOrder.getAssignPersonId());
            maintenanceWorkOrderService.save(maintenanceWorkOrder);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderAssign-----", e);
        }
        return maintenanceWorkOrderForAssignVo;
    }

    /**
     * saveWorkOrderReport:保存维保工单-执行汇报
     *
     * @param maintenanceWorkOrderForReportVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForReportVo}
     * @return MaintenanceWorkOrderForReportVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderReport", method = RequestMethod.POST)
    public MaintenanceWorkOrderForReportVo saveWorkOrderReport(@RequestBody MaintenanceWorkOrderForReportVo maintenanceWorkOrderForReportVo) {
        try {
            MaintenanceWorkOrder maintenanceWorkOrder = maintenanceWorkOrderService.findWorkOrderByID(maintenanceWorkOrderForReportVo.getId());
            if (null == maintenanceWorkOrder || maintenanceWorkOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            List<String> ignoreList = new ArrayList<String>();
            ignoreList.add("id");
            ignoreList.add("status");
            ignoreList.add("statusDate");
            ignoreList.add("siteId");
            ignoreList.add("orgId");
            ignoreList.add("processInstanceId");
            ignoreList.add("reportId");
            ignoreList.add("projectType");
            ignoreList.add("assignPersonId");
            ignoreList.add("actualExecutorResponsibleId");
            ignoreList.add("actualExecutorName");
            ignoreList.add("actualExecutorId");
            ignoreList.add("actualWorkGroup");

            ignoreList.add("eamOrderstepVoList");
            ignoreList.add("deleteEamOrderstepVoList");
            ignoreList.add("eamActualitemVoList");
            ignoreList.add("eamImpleRecordVoVoList");
            ReflectionUtils.copyProperties(maintenanceWorkOrderForReportVo, maintenanceWorkOrder, ignoreList);
            maintenanceWorkOrderService.save(maintenanceWorkOrder);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderReport-----", e);
        }
        return maintenanceWorkOrderForReportVo;
    }

    /**
     * saveWorkOrderCheckAccept:保存维保工单-验收确认
     *
     * @param maintenanceWorkOrderForCheckAcceptVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForCheckAcceptVo}
     * @return MaintenanceWorkOrderForCheckAcceptVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderCheckAccept", method = RequestMethod.POST)
    public MaintenanceWorkOrderForCheckAcceptVo saveWorkOrderCheckAccept(@RequestBody MaintenanceWorkOrderForCheckAcceptVo maintenanceWorkOrderForCheckAcceptVo) {
        try {
            MaintenanceWorkOrder maintenanceWorkOrder = maintenanceWorkOrderService.findWorkOrderByID(maintenanceWorkOrderForCheckAcceptVo.getId());
            if (null == maintenanceWorkOrder || maintenanceWorkOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            List<String> ignoreList = new ArrayList<String>();
            ignoreList.add("id");
            ignoreList.add("status");
            ignoreList.add("statusDate");
            ignoreList.add("siteId");
            ignoreList.add("orgId");
            ignoreList.add("processInstanceId");
            ignoreList.add("reportId");
            ignoreList.add("actualExecutorId");
            ignoreList.add("actualExecutorResponsibleId");

            ignoreList.add("acceptorName");
            ReflectionUtils.copyProperties(maintenanceWorkOrderForCheckAcceptVo, maintenanceWorkOrder, ignoreList);
            maintenanceWorkOrderService.save(maintenanceWorkOrder);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderCheckAccept-----", e);
        }
        return maintenanceWorkOrderForCheckAcceptVo;
    }

    /**
     * findWorkOrderById:根据ID查询维保工单-详情
     * @param id
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderById", method = RequestMethod.GET)
    MaintenanceWorkOrderForDetailVo findWorkOrderById(@RequestParam("id") String id) {
        MaintenanceWorkOrderForDetailVo maintenanceWorkOrderForDetailVo = new MaintenanceWorkOrderForDetailVo();
        try {
            MaintenanceWorkOrder maintenanceWorkOrder = maintenanceWorkOrderService.findWorkOrderByID(id);
            if (null != maintenanceWorkOrder && maintenanceWorkOrder.getId() != null) {
                ReflectionUtils.copyProperties(maintenanceWorkOrder, maintenanceWorkOrderForDetailVo, null);
            }
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/findWorkOrderById-----", e);
        }
        return maintenanceWorkOrderForDetailVo;
    }

    /**
     * findWorkOrderByIds:根据ID数组查询维保工单-详情
     * @param ids
     * @return List<MaintenanceWorkOrderForDetailVo> 维保工单详情VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderByIds", method = RequestMethod.POST)
    List<MaintenanceWorkOrderForDetailVo> findWorkOrderByIds(@RequestBody List<String> ids) {
        List<MaintenanceWorkOrderForDetailVo> list=new ArrayList<>();
        try {
            list = maintenanceWorkOrderService.findWorkOrderByIDs(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/findWorkOrderByIds-----", e);
        }
        return list;
    }

    /**
     * findWorkOrderCommitById:根据ID查询维保工单-工单提报
     *
     * @param id
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderCommitById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForCommitVo findWorkOrderCommitById(@RequestParam("id") String id) {
        MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo = maintenanceWorkOrderService.findEamWorkOrderCommitByID(id);
        if (maintenanceWorkOrderForCommitVo != null && StringUtils.isNotBlank(maintenanceWorkOrderForCommitVo.getParentWorkOrderId())) {
            MaintenanceWorkOrderForCommitVo maintenanceWorkOrder = maintenanceWorkOrderService.findEamWorkOrderCommitByID(maintenanceWorkOrderForCommitVo.getParentWorkOrderId());
            if (maintenanceWorkOrder != null) {
                maintenanceWorkOrderForCommitVo.setParentWorkOrderDesc(maintenanceWorkOrder.getDescription());
            }
        }
        return maintenanceWorkOrderForCommitVo;
    }

    /**
     * findWorkOrderAssignById:根据ID查询维保工单-任务分派
     *
     * @param id
     * @return MaintenanceWorkOrderForAssignVo 维保工单-任务分派VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderAssignById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForAssignVo findWorkOrderAssignById(@RequestParam("id") String id) {
        MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo = maintenanceWorkOrderService.findEamWorkOrderAssignByID(id);
        return maintenanceWorkOrderForAssignVo;
    }

    /**
     * findWorkOrderReportById:根据ID查询维保工单-执行汇报
     *
     * @param id
     * @return MaintenanceWorkOrderForReportVo 维保工单-执行汇报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderReportById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForReportVo findWorkOrderReportById(@RequestParam("id") String id) {
        MaintenanceWorkOrderForReportVo maintenanceWorkOrderForReportVo = maintenanceWorkOrderService.findEamWorkOrderReportByID(id);
        return maintenanceWorkOrderForReportVo;
    }

    /**
     * findWorkOrderCheckAcceptById:根据ID查询维保工单-验收确认
     *
     * @param id
     * @return MaintenanceWorkOrderForCheckAcceptVo 维保工单-验收确认VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderCheckAcceptById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForCheckAcceptVo findWorkOrderCheckAcceptById(@RequestParam("id") String id) {
        MaintenanceWorkOrderForCheckAcceptVo maintenanceWorkOrderForCheckAcceptVo = maintenanceWorkOrderService.findEamWorkOrderCheckAcceptByID(id);
        return maintenanceWorkOrderForCheckAcceptVo;
    }

    /**
     * findWorkOrderCommitByWorkOrderNum: 根据woNum查询维保工单-工单提报
     *
     * @param woNum 维保工单编码
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderCommitByWorkOrderNum", method = RequestMethod.GET)
    public MaintenanceWorkOrderForCommitVo findWorkOrderCommitByWorkOrderNum(@RequestParam("woNum") String woNum) {
        return maintenanceWorkOrderService.findWorkOrderCommitByWorkOrderNum(woNum);
    }

    /**
     * findPageWorkOrderList: 分页查询维保工单
     *
     * @param maintenanceWorkOrderSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
     * @return PageInfo<MaintenanceWorkOrderForListVo> 预防维保工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workorder/findPageWorkOrderList")
    public PageInfo<MaintenanceWorkOrderForListVo> findPageWorkOrderList(@RequestBody MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo) {
        List<MaintenanceWorkOrderForListVo> result = null;
        try {
            result = maintenanceWorkOrderService.findWorkOrderList(maintenanceWorkOrderSelectVo);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/findPageWorkOrderList-----", e);
        }
        return new PageInfo<MaintenanceWorkOrderForListVo>(result);
    }

    /**
     * deleteWorkOrderList:删除选中的维保工单
     *
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workorder/deleteWorkOrderList", method = RequestMethod.POST)
    public Boolean deleteWorkOrderList(@RequestBody List<String> ids) {
        try {
            maintenanceWorkOrderService.deleteEamWorkOrderByIds(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/deleteWorkOrderList-----", e);
            return false;
        }
        return true;
    }

    /**
     * deleteWorkOrder:删除维保工单
     *
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workorder/deleteWorkOrder", method = RequestMethod.DELETE)
    public Boolean deleteWorkOrder(@RequestParam("id") String id) {
        try {
            maintenanceWorkOrderService.deleteEamWorkOrderById(id);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/deleteWorkOrder-----", e);
            return false;
        }
        return true;
    }

    /**
     * findPageWorkOrderByAssetId:根据设备ID分页查询维保工单
     *
     * @param maintenanceForAssetFilterVo 根据设备查询维保工单列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
     * @return PageInfo<MaintenanceWorkOrderForListVo> 维保工单集合
     */
    @RequestMapping(value = "/eam/micro/workorder/findPageWorkOrderByAssetId", method = RequestMethod.POST)
    PageInfo<MaintenanceWorkOrderForListVo> findPageWorkOrderByAssetId(@RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
        List<MaintenanceWorkOrderForListVo> list = maintenanceWorkOrderService.findPageWorkOrderByAssetId(maintenanceForAssetFilterVo);
        return new PageInfo<MaintenanceWorkOrderForListVo>(list);
    }

    /**
     * findWorkOrderSingleAssetLastTimeById:根据维保工单ID查询该预防性维护计划上次生成工单的实际工时（分钟）
     *
     * @param id
     * @return Double 该预防性维护计划上次生成工单的实际工时（分钟）
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/workorder/findWorkOrderSingleAssetLastTimeById")
    public Double findWorkOrderSingleAssetLastTimeById(@RequestParam("id") String id) {
        return maintenanceWorkOrderService.findWorkOrderSingleAssetLastTimeById(id);
    }

    /**
     * findWorkOrderCommitById:根据ID查询维保工单-工单提报
     *
     * @param id
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderWorkFlowById", method = RequestMethod.GET)
    MaintenanceWorkOrderForWorkFlowVo findWorkOrderWorkFlowById(@RequestParam("id") String id) {
        MaintenanceWorkOrderForWorkFlowVo maintenanceWorkOrderForWorkFlowVo = new MaintenanceWorkOrderForWorkFlowVo();
        try {
            MaintenanceWorkOrder maintenanceWorkOrder = maintenanceWorkOrderService.findWorkOrderByID(id);
            if (null == maintenanceWorkOrder || maintenanceWorkOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            ReflectionUtils.copyProperties(maintenanceWorkOrder, maintenanceWorkOrderForWorkFlowVo, null);
            maintenanceWorkOrderService.save(maintenanceWorkOrder);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderCheckAccept-----", e);
        }
        return maintenanceWorkOrderForWorkFlowVo;
    }

    /**
     * findWorkOrderCommitById:根据ID查询维保工单-工单提报
     *
     * @param maintenanceWorkOrderForWorkFlowVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForWorkFlowVo}
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrder", method = RequestMethod.POST)
    MaintenanceWorkOrderForWorkFlowVo saveWorkOrder(@RequestBody MaintenanceWorkOrderForWorkFlowVo maintenanceWorkOrderForWorkFlowVo) {
        try {
            MaintenanceWorkOrder maintenanceWorkOrder = maintenanceWorkOrderService.findWorkOrderByID(maintenanceWorkOrderForWorkFlowVo.getId());
            if (null == maintenanceWorkOrder || maintenanceWorkOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            List<String> ignoreList = new ArrayList<String>();
            ignoreList.add("id");
            ignoreList.add("workOrderNum");
            ignoreList.add("description");
            ignoreList.add("siteId");
            ignoreList.add("orgId");
            ignoreList.add("reportId");
            ignoreList.add("projectType");
            ignoreList.add("entrustExecute");
            ignoreList.add("entrustExecutePerson");
            ignoreList.add("suspension");
            ignoreList.add("suspensionType");
            ignoreList.add("maintenancePlanNum");
            if (StringUtils.isBlank(maintenanceWorkOrderForWorkFlowVo.getAssignPersonId())) {
                ignoreList.add("assignPersonId");
                ignoreList.add("planStartDate");
                ignoreList.add("planCompletionDate");
            }
            ReflectionUtils.copyProperties(maintenanceWorkOrderForWorkFlowVo, maintenanceWorkOrder, ignoreList);
            maintenanceWorkOrderService.save(maintenanceWorkOrder);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderCheckAccept-----", e);
        }
        return maintenanceWorkOrderForWorkFlowVo;
    }

    /**
     * 收藏维保工单
     *
     * @param maintenanceWorkOrderRfCollectorVoList 收藏的维保工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workorder/collect")
    public void collectWorkOrder(@RequestBody List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList) {
        logger.info("/eam/micro/workorder/collect, params: [{}]", maintenanceWorkOrderRfCollectorVoList);

        maintenanceWorkOrderService.collectWorkOrder(maintenanceWorkOrderRfCollectorVoList);
    }

    /**
     * 取消收藏维保工单
     *
     * @param maintenanceWorkOrderRfCollectorVoList 需要取消收藏的维保工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workorder/collect/cancel")
    public void cancelCollectWorkOrder(@RequestBody List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList) {
        logger.info("/eam/micro/workorder/collect/cancel, params: [{}]", maintenanceWorkOrderRfCollectorVoList);

        maintenanceWorkOrderService.cancelCollectWorkOrder(maintenanceWorkOrderRfCollectorVoList);
    }

    /**
     * 查询维保工单各专业情况的统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/workorder/findCountWorkOrder", method = RequestMethod.GET)
    public List findCountWorkOrder(@RequestParam("siteId") String siteId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
      //  OrderCountVo orderCountVo = new OrderCountVo();
        List   list = new ArrayList<>();
        list = maintenanceWorkOrderService.findCountWorkOrder(startDate, endDate, siteId);
//        List<Object>  list1 = new ArrayList<>();
//        list1.add("0") ;

//        if (list.size() > 0) {
//            List listme = new ArrayList<>();
//            List listyxf = new ArrayList<>();
//            List listwxf = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                Object[] itemArray = (Object[]) list.get(i);
//                listme.add(itemArray[0].toString());
//                listyxf.add(itemArray[1].toString());
//                listwxf.add(itemArray[2].toString());
//            }
//            orderCountVo.setListme(listme);
//            orderCountVo.setListyxf(listyxf);
//            orderCountVo.setListwxf(listwxf);
//        }
        return list;
    }


    /**
     * 查询未完成维保工单各专业情况的统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/workorder/findCountUndoneWorkOrder", method = RequestMethod.GET)
    public List findCountUndoneWorkOrder(@RequestParam("siteId") String siteId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {

        List<Object> list = new ArrayList<>();
        list = maintenanceWorkOrderService.findCountUndoneWorkOrder(startDate, endDate, siteId);

        return list;
    }

    /**
     * 查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     *
     * @param orgId  组织ID
     * @param siteId 站点id
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/workorder/findCountByStatus", method = RequestMethod.GET)
    public OrderCountBySiteVo findCountByStatus(@RequestParam("orgId") String orgId, @RequestParam(value = "siteId", required = false) String siteId,
                                                @RequestParam(value = "startDate", required = false) Date startDate,
                                                @RequestParam(value = "endDate", required = false) Date endDate) {
        OrderCountBySiteVo workOrder = maintenanceWorkOrderService.findCountByStatus(orgId, siteId, startDate, endDate);
        return workOrder;
    }

    /**
     * 今日工单总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param siteId 站点id
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/workorder/findCountAndRingratio", method = RequestMethod.GET)
    public OrderCountAndRingratio findCountAndRingratio(@RequestParam("siteId") String siteId) {
        OrderCountAndRingratio orderCountAndRingratio = new OrderCountAndRingratio();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String today = formatter.format(currentTime);
        Map<String, String> map = new HashMap<>();
        map.put("siteId", siteId);
        map.put("createDate", today);
        Map<String, Object> workOrder = maintenanceWorkOrderService.findCountAndRingratio(map);


        List<String> numList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(System.currentTimeMillis());
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -i);
            date = calendar.getTime();
            String todayOfLast = formatter.format(date);
            Map<String, String> map1 = new HashMap<>();
            map1.put("siteId", siteId);
            map1.put("createDate", todayOfLast);
            Map<String, Object> workOrderFor = maintenanceWorkOrderService.findCountAndRingratio(map1);
            numList.add(workOrderFor.get("total").toString());
        }


//		List<String> ringList=new ArrayList<>();
//		for(int j=0;j<numList.size();j++){
//			if(!orderCountAndRingratio.getTotal().equals("0")) {
//				ringList.add(Integer.toString(Integer.valueOf(numList.get(j).toString()) / Integer.valueOf(orderCountAndRingratio.getTotal())));
//			}
//			else{
//				ringList.add("0");
//			}
//		}
        orderCountAndRingratio.setList(numList);
//		orderCountAndRingratio.setRingList(ringList);
        return orderCountAndRingratio;
    }

    /**
     * 查询工单最大数量
     *
     * @param orgId     组织id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/workorder/findMaxCountOrder", method = RequestMethod.GET)
    public List<OrderMaxCountVo> findMaxCountOrder(@RequestParam("orgId") String orgId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return maintenanceWorkOrderService.findMaxCountOrder(orgId, startDate, endDate);
    }

    /**
     * 统计总部维修工单、保养工单在时间段内的总量
     *
     * @param orgId     组织Id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderTotal", method = RequestMethod.GET)
    List<DashboardOrderCountVo> findWorkOrderTotal(@RequestParam("orgId") String orgId, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        return maintenanceWorkOrderService.findWorkOrderTotal(orgId, startDate, endDate);
    }


}