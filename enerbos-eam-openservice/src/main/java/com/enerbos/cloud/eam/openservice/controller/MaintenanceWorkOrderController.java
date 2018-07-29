package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.CompanyClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.client.MeasureClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForFilter;
import com.enerbos.cloud.ams.vo.asset.AssetVoForList;
import com.enerbos.cloud.ams.vo.company.CompanyVoForDetail;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.ams.vo.measure.MeasureVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.WorkOrderCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.constants.UASConstants;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM维保工单接口
 */
@RestController
@Api(description = "维保工单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaintenanceWorkOrderController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderController.class);

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
	private SiteClient siteClient;
    
    @Autowired
	private LocationClient locationClient;
    
    @Autowired
	private PersonAndUserClient personAndUserClient;
    
    @Autowired
	private OrgClient orgClient;
    
    @Resource
	private MaintenanceWorkOrderClient maintenanceWorkOrderClient;
    
    @Resource
	private MaintenanceWorkOrderNeedItemClient maintenanceWorkOrderNeedItemClient;
    
    @Resource
	private AssetClient assetClient;

    @Resource
	private MaintenanceWorkOrderStepClient maintenanceWorkOrderStepClient;
    
    @Resource
	private MaintenanceWorkOrderActualItemClient maintenanceWorkOrderActualItemClient;
    
    @Resource
	private MaintenanceWorkOrderAssetClient maintenanceWorkOrderAssetClient;

    @Resource
	private MaintenanceJobStandardClient maintenanceJobStandardClient;
    
    @Autowired
	private ProcessActivitiClient processActivitiClient;
    
	@Resource
	private MaintenanceJobStandardTaskClient maintenanceJobStandardTaskClient;

	@Autowired
	private ContractClient contractClient;

	@Autowired
	private RepairOrderClient repairOrderClient;

	@Autowired
	private UgroupClient ugroupClient;

	@Autowired
	private CompanyClient companyClient;

	@Autowired
	private OrderPersonClient orderPersonClient;

	@Autowired
	private UserGroupDomainColler userGroupDomainColler;

	@Autowired
	private MeasureClient measureClient;

    /**
     * saveWorkOrderCommit:保存维保工单-工单提报
     * @param maintenanceWorkOrderForCommitVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForCommitVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存维保工单-工单提报", response = MaintenanceWorkOrderForCommitVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/saveWorkOrderCommit", method = RequestMethod.POST)
    public EnerbosMessage saveWorkOrderCommit(@ApiParam(value = "维保工单-工单提报VO", required = true) @Valid @RequestBody MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo, Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/saveWorkOrderCommit, host: [{}:{}], service_id: {}, MaintenanceWorkOrderForCommitVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceWorkOrderForCommitVo);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			MaintenanceWorkOrderForCommitVo ewo = maintenanceWorkOrderClient.findWorkOrderCommitByWorkOrderNum(maintenanceWorkOrderForCommitVo.getWorkOrderNum());
			boolean isHavingAsset=false;
			//判断维保工单是否有设备
			if (StringUtils.isNotBlank(maintenanceWorkOrderForCommitVo.getId())) {
				List<String> assets=maintenanceWorkOrderAssetClient.findAssetIdByWorkOrderId(maintenanceWorkOrderForCommitVo.getId());
				if (assets != null&&assets.size()>0) {
					isHavingAsset=true;
				}
			}
			//判断是否添加设备
			if (maintenanceWorkOrderForCommitVo.getAddAssetList() != null&&maintenanceWorkOrderForCommitVo.getAddAssetList().size()>0) {
				isHavingAsset=true;
			}
			if (StringUtils.isBlank(maintenanceWorkOrderForCommitVo.getLocationId())&&!isHavingAsset) {
				return EnerbosMessage.createErrorMsg("405", "维保工单位置和设备必须二选一", "");
			}
			if (StringUtils.isBlank(maintenanceWorkOrderForCommitVo.getId())) {
                if (ewo != null && !"".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "维保工单编码重复", "");
                }
            }else {
            	if (ewo != null && !maintenanceWorkOrderForCommitVo.getId().equals(ewo.getId())) {
                    return EnerbosMessage.createErrorMsg("", "维保工单编码重复", "");
                }
            }
        	if (!WorkOrderCommon.WORK_ORDER_STATUS_DTB.equals(maintenanceWorkOrderForCommitVo.getStatus())) {
            	return EnerbosMessage.createErrorMsg("", "维保工单状态不是待提报，不允许保存！", "");
            }
			if (StringUtils.isBlank(maintenanceWorkOrderForCommitVo.getCreateUser())) {
				maintenanceWorkOrderForCommitVo.setCreateUser(userId);
			} else if (!userId.equals(maintenanceWorkOrderForCommitVo.getCreateUser())) {
				return EnerbosMessage.createErrorMsg("", "维保工单创建人只能是自己", "");
			}
			if (StringUtils.isBlank(maintenanceWorkOrderForCommitVo.getReportId()) ) {
				maintenanceWorkOrderForCommitVo.setReportId(userId);
			} else if (!userId.equals(maintenanceWorkOrderForCommitVo.getReportId())) {
				return EnerbosMessage.createErrorMsg("", "维保工单提报人只能是自己", "");
			}
        	maintenanceWorkOrderForCommitVo.setStatusDate(new Date());//状态日期
			maintenanceWorkOrderForCommitVo.setReportDate(new Date());//提报日期
			if (StringUtils.isBlank(maintenanceWorkOrderForCommitVo.getId())) {
				maintenanceWorkOrderForCommitVo.setCreateUser(userId);
			}
			if (maintenanceWorkOrderForCommitVo.getCreateDate() == null) {
				maintenanceWorkOrderForCommitVo.setCreateDate(new Date());
			}

			MaintenanceWorkOrderForCommitVo workOrder=maintenanceWorkOrderClient.saveWorkOrderCommit(maintenanceWorkOrderForCommitVo);

			//删除关联设备
			List<String> deleteAssetList=maintenanceWorkOrderForCommitVo.getDeleteAssetList();
			if (deleteAssetList != null&&deleteAssetList.size()>0) {
				maintenanceWorkOrderAssetClient.deleteWorkOrderAssetByAssetIds(workOrder.getId(),maintenanceWorkOrderForCommitVo.getDeleteAssetList());
			}
			//添加关联设备到预防维护计划
			List<MaintenanceWorkOrderAssetVo> addAssetList=maintenanceWorkOrderForCommitVo.getAddAssetList();
			if (null!=addAssetList&&addAssetList.size()>0) {
				for (MaintenanceWorkOrderAssetVo asset : addAssetList) {
					String id=asset.getId();
					if (null==id||"".equals(id)||id.length()<32) {
						asset.setId(null);
					}
					asset.setWorkOrderId(workOrder.getId());
				}
				maintenanceWorkOrderAssetClient.saveWorkOrderAsset(addAssetList);
			}
			return EnerbosMessage.createSuccessMsg(workOrder, "保存维保工单-工单提报保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/saveWorkOrderCommit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/saveWorkOrderCommit----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * saveWorkOrderAssign:保存维保工单-任务分派
     * @param maintenanceWorkOrderForAssignVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForAssignVo}
     * @param user
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存维保工单-任务分派", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/saveWorkOrderAssign", method = RequestMethod.POST)
    public EnerbosMessage saveWorkOrderAssign(@ApiParam(value = "维保工单-任务分派VO", required = true) @Valid  @RequestBody MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/saveWorkOrderAssign, host: [{}:{}], service_id: {}, MaintenanceWorkOrderForAssignVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceWorkOrderForAssignVo);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
        	if (!WorkOrderCommon.WORK_ORDER_STATUS_DFP.equals(maintenanceWorkOrderForAssignVo.getStatus())) {
        		return EnerbosMessage.createErrorMsg("", "维保工单状态不是待分派，不允许修改！", "");
            }
			if (maintenanceWorkOrderForAssignVo.getEntrustExecute()&&null == maintenanceWorkOrderForAssignVo.getEntrustExecutePersonId()) {
				return EnerbosMessage.createErrorMsg("405", "维保工单委托执行情况下委托执行人不能为空", "");
			}
            maintenanceWorkOrderForAssignVo.setUpdateDate(new Date());//更新最后修改时间
			String[] executorPerson=maintenanceWorkOrderForAssignVo.getExecutorPersonId().split(",");
			String workGroup=null;
			for (String person:executorPerson){
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(person);
				if (personVo == null) {
					return EnerbosMessage.createErrorMsg("405", "维保工单执行人中有不存在的人员", "");
				}
				if (StringUtils.isBlank(workGroup)){
					workGroup=personVo.getWorkgroup();
				}else if (!workGroup.equals(workGroup=personVo.getWorkgroup())){
					workGroup=null;
					break;
				}
			}
			maintenanceWorkOrderForAssignVo.setExecutionWorkGroup(workGroup);
			maintenanceWorkOrderForAssignVo.setActualWorkGroup(workGroup);

			//更新执行人和实际执行人数据
			if (StringUtils.isNotEmpty(maintenanceWorkOrderForAssignVo.getExecutorPersonId())) {
				String[] executionPersonIdArray = maintenanceWorkOrderForAssignVo.getExecutorPersonId().split(",");
				List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
				List<OrderPersonVo> actualExecutorVoList = new ArrayList<>();
				for (String id : executionPersonIdArray) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
					Assert.notNull(personVo, "未知执行人！");
					orderPersonVoList.add(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_EXECUTION, id));
					actualExecutorVoList.add(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION, id));
				}
				orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
				actualExecutorVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(actualExecutorVoList);
			} else {
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_EXECUTION);
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION);
			}

			//更新委托执行人数据
			if (StringUtils.isNotEmpty(maintenanceWorkOrderForAssignVo.getEntrustExecutePersonId())) {
				String[] entrustExecutePersonIdArray = maintenanceWorkOrderForAssignVo.getEntrustExecutePersonId().split(",");
				List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
				for (String id : entrustExecutePersonIdArray) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
					Assert.notNull(personVo, "未知执行人！");
					orderPersonVoList.add(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ENTRUST_EXECUTION, id));
				}
				orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
			} else {
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ENTRUST_EXECUTION);
			}
            maintenanceWorkOrderClient.saveWorkOrderAssign(maintenanceWorkOrderForAssignVo);
			//添加所需物料
			List<MaintenanceWorkOrderNeedItemVo> addNeedItemList=maintenanceWorkOrderForAssignVo.getEamNeedItemVoList();
			if (null!=addNeedItemList&&addNeedItemList.size()>0) {
				for (MaintenanceWorkOrderNeedItemVo addNeedItem : addNeedItemList) {
					String id=addNeedItem.getId();
					if (null==id||"".equals(id)||id.length()<32) {
						addNeedItem.setId(null);
						addNeedItem.setCreateDate(new Date());
					}
					addNeedItem.setWorkOrderId(maintenanceWorkOrderForAssignVo.getId());
					addNeedItem.setCreateUser(userId);
					addNeedItem.setUpdateDate(new Date());
					addNeedItem.setCreateUser(userId);
				}
				maintenanceWorkOrderNeedItemClient.saveNeedItemList(addNeedItemList);
			}
			List<String> delete=maintenanceWorkOrderForAssignVo.getDeleteEamNeedItemVoList();
			if (delete != null&&delete.size()>0) {
				//删除所需物料
				maintenanceWorkOrderNeedItemClient.deleteNeedItemList(maintenanceWorkOrderForAssignVo.getDeleteEamNeedItemVoList());
			}
			//添加任务步骤
			List<MaintenanceWorkOrderStepVo> addStepList=maintenanceWorkOrderForAssignVo.getEamOrderstepVoList();
			if (null!=addStepList&&addStepList.size()>0) {
				for (MaintenanceWorkOrderStepVo step : addStepList) {
					String id=step.getId();
					if ("".equals(id)||id.length()<32) {
						step.setId(null);
					}
					step.setWorkOrderId(maintenanceWorkOrderForAssignVo.getId());
					maintenanceWorkOrderStepClient.saveOrderStep(step);
				}
			}
			List<String> deleteEamOrderstepVoList=maintenanceWorkOrderForAssignVo.getDeleteEamOrderstepVoList();
			if (deleteEamOrderstepVoList != null&&deleteEamOrderstepVoList.size()>0) {
				//删除任务步骤
				maintenanceWorkOrderStepClient.deleteOrderStepList(maintenanceWorkOrderForAssignVo.getDeleteEamOrderstepVoList());
			}
			return EnerbosMessage.createSuccessMsg(true, "保存维保工单-任务分派保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/saveWorkOrderAssign ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/saveWorkOrderAssign----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * saveWorkOrderReport:保存维保工单-执行汇报
     * @param maintenanceWorkOrderForReportVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForReportVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存维保工单-执行汇报", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/saveWorkOrderReport", method = RequestMethod.POST)
    public EnerbosMessage saveWorkOrderReport(@ApiParam(value = "维保工单-执行汇报VO", required = true) @RequestBody MaintenanceWorkOrderForReportVo maintenanceWorkOrderForReportVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/saveWorkOrderReport, host: [{}:{}], service_id: {}, MaintenanceWorkOrderForListVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceWorkOrderForReportVo);
            
        	String id = maintenanceWorkOrderForReportVo.getId();
        	if (null == id) {
                return EnerbosMessage.createErrorMsg("", "维保工单不存在，请先新建！", "");
            }
        	if (null == maintenanceWorkOrderForReportVo.getStatus()||!WorkOrderCommon.WORK_ORDER_STATUS_DHB.equals(maintenanceWorkOrderForReportVo.getStatus())) {
        		return EnerbosMessage.createErrorMsg("", "维保工单状态不是待汇报，不允许修改！", "");
            }
			if (null==maintenanceWorkOrderForReportVo.getActualStartDate()) {
				return EnerbosMessage.createErrorMsg("405", "维保工单实际开始时间不能为空", "");
			}
			if (null==maintenanceWorkOrderForReportVo.getActualEndDate()) {
				return EnerbosMessage.createErrorMsg("405", "维保工单工程实际结束时间不能为空", "");
			}
        	if (maintenanceWorkOrderForReportVo.getSiteId() != null) {
            	SiteVoForDetail site=siteClient.findById(maintenanceWorkOrderForReportVo.getSiteId());
            	maintenanceWorkOrderForReportVo.setSiteId(site.getId());
            }
            if (maintenanceWorkOrderForReportVo.getOrgId() != null) {
            	OrgVoForDetail org=orgClient.findById(maintenanceWorkOrderForReportVo.getOrgId());
            	maintenanceWorkOrderForReportVo.setOrgId(org.getId());
            }
			String[] executorPerson=maintenanceWorkOrderForReportVo.getActualExecutorId().split(",");
			String workGroup=null;
			for (String person:executorPerson){
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(person);
				if (personVo == null) {
					return EnerbosMessage.createErrorMsg("405", "维保工单实际执行人中有不存在的人员", "");
				}
				if (StringUtils.isBlank(workGroup)){
					workGroup=personVo.getWorkgroup();
				}else if (!workGroup.equals(workGroup=personVo.getWorkgroup())){
					workGroup=null;
					break;
				}
			}
			maintenanceWorkOrderForReportVo.setActualWorkGroup(workGroup);
            maintenanceWorkOrderForReportVo.setUpdateDate(new Date());//更新最后修改时间
			//更新执行人和实际执行人数据
			if (StringUtils.isNotEmpty(maintenanceWorkOrderForReportVo.getActualExecutorId())) {
				String[] actualExecutorIdArray = maintenanceWorkOrderForReportVo.getActualExecutorId().split(",");
				List<OrderPersonVo> actualExecutorVoList = new ArrayList<>();
				for (String actualId : actualExecutorIdArray) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(actualId);
					Assert.notNull(personVo, "未知执行人！");
					actualExecutorVoList.add(new OrderPersonVo(maintenanceWorkOrderForReportVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION, actualId));
				}
				actualExecutorVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(actualExecutorVoList);
			} else {
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(maintenanceWorkOrderForReportVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION);
			}
            maintenanceWorkOrderClient.saveWorkOrderReport(maintenanceWorkOrderForReportVo);
			//修改实际使用物料数量
			List<MaintenanceWorkOrderActualItemVo> addActualItemList=maintenanceWorkOrderForReportVo.getEamActualitemVoList();
			if (null!=addActualItemList&&addActualItemList.size()>0) {
				for (MaintenanceWorkOrderActualItemVo addActualItem : addActualItemList) {
					String actualId=addActualItem.getId();
					if (null==actualId||"".equals(actualId)||actualId.length()<32) {
						continue;
					}
					addActualItem.setWorkOrderId(maintenanceWorkOrderForReportVo.getId());
					addActualItem.setUpdateDate(new Date());
				}
				maintenanceWorkOrderActualItemClient.saveWorkOrderActualItemList(addActualItemList);
			}

			//添加任务步骤
			List<MaintenanceWorkOrderStepVo> addStepList=maintenanceWorkOrderForReportVo.getEamOrderstepVoList();
			if (null!=addStepList&&addStepList.size()>0) {
				for (MaintenanceWorkOrderStepVo step : addStepList) {
					String stepId=step.getId();
					if (null==stepId||"".equals(stepId)||stepId.length()<32) {
						continue;
					}
					step.setWorkOrderId(maintenanceWorkOrderForReportVo.getId());
					maintenanceWorkOrderStepClient.saveOrderStep(step);
				}
			}
			//计算单设备本次工时（系统自动算出结果）--实际执行步骤工时之和（分钟）
			List<MaintenanceWorkOrderStepVo> orderStepList=maintenanceWorkOrderStepClient.findOrderStepByWorkOrderId(id);
			Double singleThisTime=0.0;
			if (orderStepList != null&&orderStepList.size()>0) {
				for (MaintenanceWorkOrderStepVo orderStep:orderStepList){
					if (orderStep != null&& StringUtils.isNotBlank(orderStep.getActualExecuteTime())) {
						singleThisTime+=Double.valueOf(orderStep.getActualExecuteTime());
					}
				}
			}
			maintenanceWorkOrderForReportVo.setSingleAssetThisTime(singleThisTime);
			//计算工单总工时（系统自动算出结果）--本次工时*设备数量（分钟）
			List<String> assets=maintenanceWorkOrderAssetClient.findAssetIdByWorkOrderId(id);
			int assetTotal=0;
			if (assets != null) {
				assetTotal=assets.size();
			}
			maintenanceWorkOrderForReportVo.setWorkOrderTotalTime(singleThisTime*assetTotal);
			//计算单设备标准工时（系统自动算出结果）--作业标准步骤工时之和（分钟）
			MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo=maintenanceWorkOrderClient.findWorkOrderAssignById(id);
			Double singleAssetNomalTime=0.0;
			if (StringUtils.isNotBlank(maintenanceWorkOrderForAssignVo.getJobStandardId())) {
				List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList=maintenanceJobStandardTaskClient.findJobStandardTaskByJobStandardId(maintenanceWorkOrderForAssignVo.getJobStandardId());
				if (maintenanceJobStandardTaskVoList != null&&maintenanceJobStandardTaskVoList.size()>0) {
					for (MaintenanceJobStandardTaskVo maintenanceJobStandardTaskVo:maintenanceJobStandardTaskVoList){
						singleAssetNomalTime+=Double.valueOf(maintenanceJobStandardTaskVo.getTaskDuration());
					}
				}
			}
			maintenanceWorkOrderForReportVo.setSingleAssetNomalTime(singleAssetNomalTime);
			Double singleAssetLastTime=0.0;
			if (null!=maintenanceWorkOrderForReportVo.getMaintenancePlanNum()&&!"".equals(maintenanceWorkOrderForReportVo.getMaintenancePlanNum())) {
				singleAssetLastTime=maintenanceWorkOrderClient.findWorkOrderSingleAssetLastTimeById(maintenanceWorkOrderForReportVo.getId());
			}
			maintenanceWorkOrderForReportVo.setSingleAssetLastTime(singleAssetLastTime);
			maintenanceWorkOrderClient.saveWorkOrderReport(maintenanceWorkOrderForReportVo);
            return EnerbosMessage.createSuccessMsg(true, "保存维保工单-执行汇报保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/saveWorkOrderReport ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/saveWorkOrderReport----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * saveWorkOrderCheckAccept:保存维保工单-验收确认
     * @param maintenanceWorkOrderForCheckAcceptVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForCheckAcceptVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存维保工单-验收确认", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/saveWorkOrderCheckAccept", method = RequestMethod.POST)
    public EnerbosMessage saveWorkOrderCheckAccept(@ApiParam(value = "维保工单-验收确认VO", required = true) @RequestBody MaintenanceWorkOrderForCheckAcceptVo maintenanceWorkOrderForCheckAcceptVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/saveWorkOrderCheckAccept, host: [{}:{}], service_id: {}, MaintenanceWorkOrderForCheckAcceptVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceWorkOrderForCheckAcceptVo);
            
        	if (null == maintenanceWorkOrderForCheckAcceptVo.getId()) {
                return EnerbosMessage.createErrorMsg("", "维保工单不存在，请先新建！", "");
            }
        	if (null == maintenanceWorkOrderForCheckAcceptVo.getStatus()||!WorkOrderCommon.WORK_ORDER_STATUS_DYS.equals(maintenanceWorkOrderForCheckAcceptVo.getStatus())) {
        		return EnerbosMessage.createErrorMsg("", "维保工单状态不是待验收，不允许修改！", "");
            }
        	if (maintenanceWorkOrderForCheckAcceptVo.getSiteId() != null) {
            	SiteVoForDetail site=siteClient.findById(maintenanceWorkOrderForCheckAcceptVo.getSiteId());
            	maintenanceWorkOrderForCheckAcceptVo.setSiteId(site.getId());
            }
            if (maintenanceWorkOrderForCheckAcceptVo.getOrgId() != null) {
            	OrgVoForDetail org=orgClient.findById(maintenanceWorkOrderForCheckAcceptVo.getOrgId());
            	maintenanceWorkOrderForCheckAcceptVo.setOrgId(org.getId());
            }
			if (null==maintenanceWorkOrderForCheckAcceptVo.getConfirm()) {
				return EnerbosMessage.createErrorMsg("405", "维保工单是否已解决不能为空", "");
			}
			if (null==maintenanceWorkOrderForCheckAcceptVo.getAcceptorId()) {
				return EnerbosMessage.createErrorMsg("405", "维保工单验收人不能为空", "");
			}
			if (null==maintenanceWorkOrderForCheckAcceptVo.getAcceptionTime()) {
				return EnerbosMessage.createErrorMsg("405", "维保工单确认验收时间不能为空", "");
			}
            maintenanceWorkOrderForCheckAcceptVo.setUpdateDate(new Date());//更新最后修改时间
            maintenanceWorkOrderForCheckAcceptVo.setAcceptionTime(new Date());//设置验收时间
            //计算工单总用时长
            MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo=maintenanceWorkOrderClient.findWorkOrderCommitById(maintenanceWorkOrderForCheckAcceptVo.getId());
			long workOrderTotalDuration = (long)((new Date().getTime()-maintenanceWorkOrderForCommitVo.getCreateDate().getTime())/(1000 * 60));
			maintenanceWorkOrderForCheckAcceptVo.setWorkOrderTotalDuration(workOrderTotalDuration);
            maintenanceWorkOrderClient.saveWorkOrderCheckAccept(maintenanceWorkOrderForCheckAcceptVo);
            return EnerbosMessage.createSuccessMsg(true, "保存维保工单-验收确认保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/saveWorkOrderCheckAccept ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/saveWorkOrderCheckAccept----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * updateWorkOrderStatus:修改维保工单状态
     * @param id 维保工单ID
     * @param status 状态
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "修改维保工单状态", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/updateWorkOrderStatus", method = RequestMethod.POST)
    public EnerbosMessage updateWorkOrderStatus(@ApiParam(value = "维保工单id", required = true) @RequestParam("id") String id,
    		@ApiParam(value = "status", required = true) @RequestParam("status") String status,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/updateWorkOrderStatus, host: [{}:{}], service_id: {}, id: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,status);
            
    		MaintenanceWorkOrderForCommitVo ewo = maintenanceWorkOrderClient.findWorkOrderCommitById(id);
            if (ewo == null || "".equals(ewo)) {
                return EnerbosMessage.createErrorMsg("", "维保工单不存在", "");
            }
			if (WorkOrderCommon.WORK_ORDER_STATUS_GB.equals(ewo.getStatus())) {
				return EnerbosMessage.createErrorMsg("", "维保工单编码为"+ewo.getWorkOrderNum()+"的工单已关闭，不允许修改状态", "");
			}
            if (WorkOrderCommon.WORK_ORDER_STATUS_QX.equals(ewo.getStatus())) {
                return EnerbosMessage.createErrorMsg("", "维保工单编码为"+ewo.getWorkOrderNum()+"的工单已取消，不允许修改状态", "");
            }
            ewo.setStatusDate(new Date());//状态日期
            ewo.setStatus(status);
            maintenanceWorkOrderClient.saveWorkOrderCommit(ewo);
            return EnerbosMessage.createSuccessMsg(true, "修改维保工单状态成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/updateWorkOrderStatus ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/updateWorkOrderStatus----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * updateWorkOrderStatusList:批量修改维保工单状态
     * @param ids    维保工单ID数组{@link java.util.List<String>}
	 * @param status 维保工单状态
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改维保工单状态", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/updateWorkOrderStatusList", method = RequestMethod.POST)
    public EnerbosMessage updateWorkOrderStatusList(@ApiParam(value = "维保工单id", required = true) @RequestParam("ids") List<String> ids,
    		@ApiParam(value = "status", required = true) @RequestParam("status") String status,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/updateWorkOrderStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids,status);
            
        	List<MaintenanceWorkOrderForCommitVo> list=new ArrayList<>();
        	for (String id : ids) {
        		MaintenanceWorkOrderForCommitVo ewo = maintenanceWorkOrderClient.findWorkOrderCommitById(id);
                if (ewo== null || "".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "维保工单不存在", "");
                }
				if (WorkOrderCommon.WORK_ORDER_STATUS_GB.equals(ewo.getStatus())) {
					return EnerbosMessage.createErrorMsg("", "维保工单编码为"+ewo.getWorkOrderNum()+"的工单已关闭，不允许修改状态", "");
				}
				if (WorkOrderCommon.WORK_ORDER_STATUS_QX.equals(ewo.getStatus())) {
					return EnerbosMessage.createErrorMsg("", "维保工单编码为"+ewo.getWorkOrderNum()+"的工单已取消，不允许修改状态", "");
				}
				ewo.setStatusDate(new Date());//状态日期
                ewo.setUpdateDate(new Date());
                ewo.setStatus(status);
                list.add(ewo);
			}
        	for (MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo : list) {
        		maintenanceWorkOrderClient.saveWorkOrderCommit(maintenanceWorkOrderForCommitVo);
			}
            return EnerbosMessage.createSuccessMsg(true, "修改维保工单状态成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/updateWorkOrderStatusList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/updateWorkOrderStatusList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findPageWorkOrderList: 分页模糊查询维保工单
	 * @param maintenanceWorkOrderSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询维保工单", response = MaintenanceWorkOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/findPageWorkOrderList", method = RequestMethod.POST)
    public EnerbosMessage findPageWorkOrderList(@ApiParam(value = "维保工单模糊搜索查询条件VO", required = true) @RequestBody MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo,Principal user) {
        try {
			if (Objects.isNull(maintenanceWorkOrderSelectVo)) {
				maintenanceWorkOrderSelectVo = new MaintenanceWorkOrderSelectVo();
			}
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
			Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
			//设置查询人，用于获取关注信息
			maintenanceWorkOrderSelectVo.setPersonId(personId);
        	if (maintenanceWorkOrderSelectVo.getWords()!=null) {
        		String words=maintenanceWorkOrderSelectVo.getWords();
            	String[] word=StringUtils.split(words, " ");
            	maintenanceWorkOrderSelectVo.setWordsList(Arrays.asList(word));
			}
        	EnerbosPage<MaintenanceWorkOrderForListVo> maintenanceWorkOrderForListVos=maintenanceWorkOrderClient.findPageWorkOrderList(maintenanceWorkOrderSelectVo);
			if (maintenanceWorkOrderForListVos != null&&null!=maintenanceWorkOrderForListVos.getList()&&maintenanceWorkOrderForListVos.getList().size()>0) {
				for(MaintenanceWorkOrderForListVo maintenanceWorkOrderForListVo:maintenanceWorkOrderForListVos.getList()){
					if (maintenanceWorkOrderForListVo != null&&maintenanceWorkOrderForListVo.getLocationId()!=null) {
						LocationVoForDetail location=locationClient.findById(user.getName(),maintenanceWorkOrderForListVo.getLocationId());
						if (location != null) {
							maintenanceWorkOrderForListVo.setLocationDesc(location.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForListVos, "分页模糊查询维保工单", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/findPageWorkOrderList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findPageWorkOrderList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findPageWorkOrderNotParentList: 分页模糊查询不是父级维保工单的维保工单-新建维保工单选择父工单的查询列表
	 * @param maintenanceWorkOrderSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询不是父级维保工单的维保工单-新建维保工单选择父工单的查询列表", response = MaintenanceWorkOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/findPageWorkOrderNotParentList", method = RequestMethod.POST)
    public EnerbosMessage findPageWorkOrderNotParentList(@ApiParam(value = "维保工单模糊搜索查询条件VO", required = true) @RequestBody MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo,Principal user) {
        try {
			if (Objects.isNull(maintenanceWorkOrderSelectVo)) {
				maintenanceWorkOrderSelectVo = new MaintenanceWorkOrderSelectVo();
			}
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
			Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
			//设置查询人，用于获取关注信息
			maintenanceWorkOrderSelectVo.setPersonId(personId);
        	if (maintenanceWorkOrderSelectVo.getWords()!=null) {
        		String words=maintenanceWorkOrderSelectVo.getWords();
            	String[] word=StringUtils.split(words, " ");
            	maintenanceWorkOrderSelectVo.setWordsList(Arrays.asList(word));
			}
        	maintenanceWorkOrderSelectVo.setWhetherParentWorkOrder(false);
        	EnerbosPage<MaintenanceWorkOrderForListVo> maintenanceWorkOrderForListVos=maintenanceWorkOrderClient.findPageWorkOrderList(maintenanceWorkOrderSelectVo);
			if (maintenanceWorkOrderForListVos != null&&null!=maintenanceWorkOrderForListVos.getList()&&maintenanceWorkOrderForListVos.getList().size()>0) {
				for(MaintenanceWorkOrderForListVo maintenanceWorkOrderForListVo:maintenanceWorkOrderForListVos.getList()){
					if (maintenanceWorkOrderForListVo != null&&maintenanceWorkOrderForListVo.getLocationId()!=null) {
						LocationVoForDetail location=locationClient.findById(user.getName(),maintenanceWorkOrderForListVo.getLocationId());
						if (location != null) {
							maintenanceWorkOrderForListVo.setLocationDesc(location.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForListVos, "分页模糊查询不是父级维保工单的维保工单-新建维保工单选择父工单的查询列表成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/findPageWorkOrderNotParentList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findPageWorkOrderNotParentList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * findPageWorkOrderByMaintenancePlanId: 根据预防性维护计划分页查询维保工单
	 * @param maintenancePlanNum 预防性维护计划编码
	 * @param sorts 排序参数
	 * @param pageNum 分页-页码
	 * @param pageSize 分页-每页显示条数
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据预防性维护计划分页查询维保工单", response = MaintenanceWorkOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/findPageWorkOrderByMaintenancePlanNum", method = RequestMethod.GET)
	public EnerbosMessage findPageWorkOrderByMaintenancePlanId(@ApiParam(value = "预防性维护计划编码", required = true) @RequestParam("maintenancePlanNum") String maintenancePlanNum,
															   @ApiParam(value = "排序参数", required = false) @RequestParam(name="sorts",required = false) String sorts,
															   @ApiParam(value = "分页-页码", required = false,defaultValue = "1") @RequestParam(name="pageNum",required = false,defaultValue = "1") int pageNum,
															   @ApiParam(value = "分页-每页显示条数", required = false,defaultValue = "10") @RequestParam(name="pageSize",required = false,defaultValue = "10") int pageSize,Principal user) {
		try {
			if (maintenancePlanNum == null) {
				return EnerbosMessage.createSuccessMsg(null, "无数据！", "");
			}
			MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo=new MaintenanceWorkOrderSelectVo();
			maintenanceWorkOrderSelectVo.setMaintenancePlanNum(maintenancePlanNum);
			maintenanceWorkOrderSelectVo.setSorts(sorts);
			maintenanceWorkOrderSelectVo.setPageNum(pageNum);
			maintenanceWorkOrderSelectVo.setPageSize(pageSize);
			EnerbosPage<MaintenanceWorkOrderForListVo> maintenanceWorkOrderForListVos=maintenanceWorkOrderClient.findPageWorkOrderList(maintenanceWorkOrderSelectVo);
			if (maintenanceWorkOrderForListVos != null&&null!=maintenanceWorkOrderForListVos.getList()&&maintenanceWorkOrderForListVos.getList().size()>0) {
				for(MaintenanceWorkOrderForListVo maintenanceWorkOrderForListVo:maintenanceWorkOrderForListVos.getList()){
					if (maintenanceWorkOrderForListVo != null&&maintenanceWorkOrderForListVo.getLocationId()!=null) {
						LocationVoForDetail location=locationClient.findById(user.getName(),maintenanceWorkOrderForListVo.getLocationId());
						if (location != null) {
							maintenanceWorkOrderForListVo.setLocationDesc(location.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForListVos, "根据预防性维护计划分页查询维保工单成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workorder/findPageWorkOrderByMaintenancePlanId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findPageWorkOrderByMaintenancePlanId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findPageWorkOrderByAssetId:根据设备ID分页查询维保工单
	 * @param maintenanceWorkOrderForAssetVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据设备ID分页查询维保工单", response = MaintenanceWorkOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/findPageWorkOrderByAssetId", method = RequestMethod.POST)
	public EnerbosMessage findPageWorkOrderByAssetId(@ApiParam(value = "根据设备ID分页查询维保工单查询条件VO", required = true) @Valid @RequestBody MaintenanceForAssetFilterVo maintenanceWorkOrderForAssetVo,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/workorder/findPageWorkOrderByAssetId, host: [{}:{}], service_id: {}, MaintenanceForAssetFilterVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceWorkOrderForAssetVo);
			EnerbosPage<MaintenanceWorkOrderForListVo> maintenanceWorkOrderForListVos=maintenanceWorkOrderClient.findPageWorkOrderByAssetId(maintenanceWorkOrderForAssetVo);
			if (maintenanceWorkOrderForListVos != null&&null!=maintenanceWorkOrderForListVos.getList()&&maintenanceWorkOrderForListVos.getList().size()>0) {
				for(MaintenanceWorkOrderForListVo maintenanceWorkOrderForListVo:maintenanceWorkOrderForListVos.getList()){
					if (maintenanceWorkOrderForListVo != null&&maintenanceWorkOrderForListVo.getLocationId()!=null) {
						LocationVoForDetail location=locationClient.findById(user.getName(),maintenanceWorkOrderForListVo.getLocationId());
						if (location != null) {
							maintenanceWorkOrderForListVo.setLocationDesc(location.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForListVos, "根据设备ID分页查询维保工单成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workorder/findPageWorkOrderByAssetId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findPageWorkOrderByAssetId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findPageWorkOrderByContractId: 根据合同id分页模糊查询维保工单
	 * @param maintenanceWorkOrderSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据合同id分页模糊查询维保工单", response = MaintenanceWorkOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/findPageWorkOrderByContractId", method = RequestMethod.POST)
	public EnerbosMessage findPageWorkOrderByContractId(@ApiParam(value = "维保工单模糊搜索查询条件VO", required = true) @RequestBody MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo,Principal user) {
		try {
			if (Objects.isNull(maintenanceWorkOrderSelectVo)) {
				return EnerbosMessage.createErrorMsg("", "合同id不能为空", null);
			}
			EnerbosPage<MaintenanceWorkOrderForListVo> maintenanceWorkOrderForListVos=maintenanceWorkOrderClient.findPageWorkOrderList(maintenanceWorkOrderSelectVo);
			if (maintenanceWorkOrderForListVos != null&&null!=maintenanceWorkOrderForListVos.getList()&&maintenanceWorkOrderForListVos.getList().size()>0) {
				for(MaintenanceWorkOrderForListVo maintenanceWorkOrderForListVo:maintenanceWorkOrderForListVos.getList()){
					if (maintenanceWorkOrderForListVo != null&&maintenanceWorkOrderForListVo.getLocationId()!=null) {
						LocationVoForDetail location=locationClient.findById(user.getName(),maintenanceWorkOrderForListVo.getLocationId());
						if (location != null) {
							maintenanceWorkOrderForListVo.setLocationDesc(location.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForListVos, "根据合同id分页模糊查询维保工单成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workorder/findPageWorkOrderByContractId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findPageWorkOrderByContractId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findPageWorkOrderByLocationId: 根据位置id分页模糊查询维保工单
	 * @param maintenanceWorkOrderSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据位置id分页模糊查询维保工单", response = MaintenanceWorkOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/findPageWorkOrderByLocationId", method = RequestMethod.POST)
	public EnerbosMessage findPageWorkOrderByLocationId(@ApiParam(value = "维保工单模糊搜索查询条件VO", required = true) @RequestBody MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo,Principal user) {
		try {
			if (Objects.isNull(maintenanceWorkOrderSelectVo)||StringUtils.isBlank(maintenanceWorkOrderSelectVo.getLocationId())) {
				return EnerbosMessage.createErrorMsg("", "位置id不能为空", null);
			}
			EnerbosPage<MaintenanceWorkOrderForListVo> maintenanceWorkOrderForListVos=maintenanceWorkOrderClient.findPageWorkOrderList(maintenanceWorkOrderSelectVo);
			if (maintenanceWorkOrderForListVos != null&&null!=maintenanceWorkOrderForListVos.getList()&&maintenanceWorkOrderForListVos.getList().size()>0) {
				for(MaintenanceWorkOrderForListVo maintenanceWorkOrderForListVo:maintenanceWorkOrderForListVos.getList()){
					if (maintenanceWorkOrderForListVo != null&&maintenanceWorkOrderForListVo.getLocationId()!=null) {
						LocationVoForDetail location=locationClient.findById(user.getName(),maintenanceWorkOrderForListVo.getLocationId());
						if (location != null) {
							maintenanceWorkOrderForListVo.setLocationDesc(location.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForListVos, "根据合同id分页模糊查询维保工单成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workorder/findPageWorkOrderByContractId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findPageWorkOrderByContractId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
    
    /**
	 * findWorkOrderCommitById:根据ID查询维保工单-工单提报
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询维保工单-工单提报", response = MaintenanceWorkOrderForCommitVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/findWorkOrderCommitById", method = RequestMethod.GET)
    public EnerbosMessage findWorkOrderCommitById(@ApiParam(value = "维保工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo=maintenanceWorkOrderClient.findWorkOrderCommitById(id);
			if (null!=maintenanceWorkOrderForCommitVo) {
        		if (null!=maintenanceWorkOrderForCommitVo.getSiteId()) {
        			SiteVoForDetail site=siteClient.findById(maintenanceWorkOrderForCommitVo.getSiteId());
        			if (null!=site) {
    					maintenanceWorkOrderForCommitVo.setSiteName(site.getName());
    				}
				}
        		if (null!=maintenanceWorkOrderForCommitVo.getLocationId()) {
        			LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), maintenanceWorkOrderForCommitVo.getLocationId());
        			if (null!=locationVoForDetail) {
						maintenanceWorkOrderForCommitVo.setLocationNum(locationVoForDetail.getCode());
    					maintenanceWorkOrderForCommitVo.setLocationDesc(locationVoForDetail.getName());
    				}
				}
        		if (null!=maintenanceWorkOrderForCommitVo.getReportId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForCommitVo.getReportId());
					if (null!=personVo) {
						maintenanceWorkOrderForCommitVo.setReportName(personVo.getName());
						maintenanceWorkOrderForCommitVo.setReportMobile(personVo.getMobile());
					}
				}
				if (null!=maintenanceWorkOrderForCommitVo.getParentWorkOrderId()) {
					MaintenanceWorkOrderForCommitVo parentWorkOrderForCommitVo=maintenanceWorkOrderClient.findWorkOrderCommitById(maintenanceWorkOrderForCommitVo.getParentWorkOrderId());
					if (null!=parentWorkOrderForCommitVo) {
						maintenanceWorkOrderForCommitVo.setParentWorkOrderNum(parentWorkOrderForCommitVo.getWorkOrderNum());
						maintenanceWorkOrderForCommitVo.setParentWorkOrderDesc(parentWorkOrderForCommitVo.getDescription());
					}
				}
				if (null!=maintenanceWorkOrderForCommitVo.getContractId()) {
					ContractVo contractVo=contractClient.findContractVoById(maintenanceWorkOrderForCommitVo.getContractId());
					if (null!=contractVo) {
						maintenanceWorkOrderForCommitVo.setContractNum(contractVo.getContractNum());
						maintenanceWorkOrderForCommitVo.setContractName(contractVo.getDescription());
						maintenanceWorkOrderForCommitVo.setContractCompany(contractVo.getContractCompany());
					}
				}
				if (null!=maintenanceWorkOrderForCommitVo.getRepairId()) {
					RepairOrderFlowVo repairOrderFlowVo=repairOrderClient.findRepairOrderFlowVoById(maintenanceWorkOrderForCommitVo.getRepairId());
					if (null!=repairOrderFlowVo) {
						maintenanceWorkOrderForCommitVo.setRepairNum(repairOrderFlowVo.getWorkOrderNum());
					}
				}
				if (StringUtils.isNotBlank(maintenanceWorkOrderForCommitVo.getProjectType())) {
					UserGroupDomainVo userGroupDomainVo=userGroupDomainColler.findUserGroupDomainByDomainValues(maintenanceWorkOrderForCommitVo.getProjectType(),
							WorkOrderCommon.WORK_ORDER_PROJECT_TYPE,maintenanceWorkOrderForCommitVo.getOrgId(),
							maintenanceWorkOrderForCommitVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
					if (userGroupDomainVo != null) {
						maintenanceWorkOrderForCommitVo.setProjectGroupTypeName(userGroupDomainVo.getUserGroupName());
					}
				}
        		List<String> assets=maintenanceWorkOrderAssetClient.findAssetIdByWorkOrderId(maintenanceWorkOrderForCommitVo.getId());
				if (assets != null&&assets.size()>0) {
					AssetVoForFilter assetVoForFilter=new AssetVoForFilter();
					assetVoForFilter.setAssetIds(assets);
					assetVoForFilter.setPageNum(1);
					assetVoForFilter.setPageSize(assets.size());
					assetVoForFilter.setProductArray(new String[]{UASConstants.PRODUCT_EAM_ID});
					EnerbosPage<AssetVoForList> sssetVoForList=assetClient.findPage(user.getName(), assetVoForFilter);
					if (sssetVoForList != null&&!"".equals(sssetVoForList)) {
						List<AssetVoForList> pageAsset=sssetVoForList.getList();
						maintenanceWorkOrderForCommitVo.setAssetList(pageAsset);
					}
				}
                //插入执行记录
                String processInstanceId=maintenanceWorkOrderForCommitVo.getProcessInstanceId();
            	if (null!=processInstanceId&&!"".equals(processInstanceId)) {
            		maintenanceWorkOrderForCommitVo.setEamImpleRecordVoVoList(getExecution(processInstanceId));
    			}
			}
        	return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForCommitVo, "根据ID查询维保工单-工单提报成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/findWorkOrderCommitById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findWorkOrderCommitById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findWorkOrderAssignById:根据ID查询维保工单-任务分派
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询维保工单-任务分派", response = MaintenanceWorkOrderForAssignVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/findWorkOrderAssignById", method = RequestMethod.GET)
    public EnerbosMessage findWorkOrderAssignById(@ApiParam(value = "维保工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo=maintenanceWorkOrderClient.findWorkOrderAssignById(id);
        	if (null!=maintenanceWorkOrderForAssignVo) {
        		//查询委托执行人
				List<OrderPersonVo> entrustExecutePersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ENTRUST_EXECUTION));
				if (!entrustExecutePersonList.isEmpty()) {
					List<PersonAndUserVoForDetail> tmp = entrustExecutePersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
					maintenanceWorkOrderForAssignVo.setEntrustExecutePersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
					maintenanceWorkOrderForAssignVo.setEntrustExecutePersonName(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
				}
				//查询执行人
				List<OrderPersonVo> executorPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_EXECUTION));
				if (!executorPersonList.isEmpty()) {
					List<PersonAndUserVoForDetail> tmp = executorPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
					maintenanceWorkOrderForAssignVo.setExecutorPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
					maintenanceWorkOrderForAssignVo.setExecutorPersonName(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
					maintenanceWorkOrderForAssignVo.setPersonWorkGroup(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getWorkgroup).toArray(), ","));
				}
        		if (null!=maintenanceWorkOrderForAssignVo.getAssignPersonId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForAssignVo.getAssignPersonId());
        			if (null!=personVo) {
    					maintenanceWorkOrderForAssignVo.setAssignPersonName(personVo.getName());
    				}
				}
        		if (null!=maintenanceWorkOrderForAssignVo.getJobStandardId()) {
					MaintenanceJobStandardVo maintenanceJobStandardVo=maintenanceJobStandardClient.findJobStandardById(maintenanceWorkOrderForAssignVo.getJobStandardId());
					maintenanceWorkOrderForAssignVo.setJobStandardNum(maintenanceJobStandardVo.getJobStandardNum());
					maintenanceWorkOrderForAssignVo.setJobStandardDesc(maintenanceJobStandardVo.getDescription());
				}
				if (null!=maintenanceWorkOrderForAssignVo.getReportId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForAssignVo.getReportId());
					if (null!=personVo) {
						maintenanceWorkOrderForAssignVo.setReportName(personVo.getName());
					}
				}
//				if (StringUtils.isNotBlank(maintenanceWorkOrderForAssignVo.getExecutionWorkGroup())) {
//					Map<String, Object> filters=new HashMap<>();
//					filters.put("name", maintenanceWorkOrderForAssignVo.getExecutionWorkGroup());
//					filters.put("siteId", maintenanceWorkOrderForAssignVo.getSiteId());
//					filters.put("orgId", maintenanceWorkOrderForAssignVo.getOrgId());
//
//					List<UgroupVo> uGroupVoList=ugroupClient.findUgroups(user.getName(), filters, 0, 10, Common.EAM_PROD_IDS).getList();
//					if (!CollectionUtils.isEmpty(uGroupVoList)) {
//						maintenanceWorkOrderForAssignVo.setExecutionWorkGroupName(uGroupVoList.get(0).getDescription());
//					}
//				}
				if (StringUtils.isNotBlank(maintenanceWorkOrderForAssignVo.getCompanyId())) {
					CompanyVoForDetail company=companyClient.findById(user.getName(),maintenanceWorkOrderForAssignVo.getCompanyId());
					if (company != null) {
						maintenanceWorkOrderForAssignVo.setCompanyName(company.getName());
					}
				}
        		
        		maintenanceWorkOrderForAssignVo.setEamNeedItemVoList(maintenanceWorkOrderNeedItemClient.findNeedItemListByWorkOrderId(maintenanceWorkOrderForAssignVo.getId()));
				maintenanceWorkOrderForAssignVo.getEamNeedItemVoList().forEach(needItemVo->{
					if (StringUtils.isNotBlank(needItemVo.getItemUnit())) {
						MeasureVo measure=measureClient.findById(needItemVo.getItemUnit());
						if (measure != null) {
							needItemVo.setItemUnitDesc(measure.getDescription());
						}
					}
				});
            	maintenanceWorkOrderForAssignVo.setEamOrderstepVoList(maintenanceWorkOrderStepClient.findOrderStepByWorkOrderId(maintenanceWorkOrderForAssignVo.getId()));
                
            	String processInstanceId=maintenanceWorkOrderForAssignVo.getProcessInstanceId();
            	if (null!=processInstanceId&&!"".equals(processInstanceId)) {
            		maintenanceWorkOrderForAssignVo.setEamImpleRecordVoVoList(getExecution(processInstanceId));
				}

				//设置待接单人
				List<OrderPersonVo> availableTakingPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_EXECUTION));
				if (maintenanceWorkOrderForAssignVo.getEntrustExecute()) {
					//委托执行人
					availableTakingPersonList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(maintenanceWorkOrderForAssignVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ENTRUST_EXECUTION)));
				}
				List<PersonAndUserVoForDetail> tmp = availableTakingPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
				String availableTakingPersonName=StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ",");
				maintenanceWorkOrderForAssignVo.setAvailableTakingPersonName(availableTakingPersonName);
			}
        	return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForAssignVo, "根据ID查询维保工单-任务分派成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/findWorkOrderAssignById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findWorkOrderAssignById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findWorkOrderReportById:根据ID查询维保工单-执行汇报
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询维保工单-执行汇报", response = MaintenanceWorkOrderForReportVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/findWorkOrderReportById", method = RequestMethod.GET)
    public EnerbosMessage findWorkOrderReportById(@ApiParam(value = "维保工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	MaintenanceWorkOrderForReportVo maintenanceWorkOrderForReportVo=maintenanceWorkOrderClient.findWorkOrderReportById(id);
			if (maintenanceWorkOrderForReportVo != null) {
				maintenanceWorkOrderForReportVo.setEamActualitemVoList(maintenanceWorkOrderActualItemClient.findItemIdByWorkOrderId(maintenanceWorkOrderForReportVo.getId()));
				maintenanceWorkOrderForReportVo.getEamActualitemVoList().forEach(needItemVo->{
					if (StringUtils.isNotBlank(needItemVo.getItemUnit())) {
						MeasureVo measure = measureClient.findById(needItemVo.getItemUnit());
						if (measure != null) {
							needItemVo.setItemUnitDesc(measure.getDescription());
						}
					}
				});
				maintenanceWorkOrderForReportVo.setEamOrderstepVoList(maintenanceWorkOrderStepClient.findOrderStepByWorkOrderId(maintenanceWorkOrderForReportVo.getId()));
				//查询实际执行人
				List<OrderPersonVo> executorPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(maintenanceWorkOrderForReportVo.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION));
				if (!executorPersonList.isEmpty()) {
					List<PersonAndUserVoForDetail> tmp = executorPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
					maintenanceWorkOrderForReportVo.setActualExecutorId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
					maintenanceWorkOrderForReportVo.setActualExecutorName(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
					maintenanceWorkOrderForReportVo.setPersonWorkGroup(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getWorkgroup).toArray(), ","));
				}
				//TODO 查询分组名称
//        	if (null!=maintenanceWorkOrderForReportVo.getActualWorkGroup()) {
//    			PersonVo personVo=personAndUserClient.findOne(maintenanceWorkOrderForReportVo.getActualWorkGroup());
//    			if (null!=personVo) {
//    				maintenanceWorkOrderForReportVo.setActualWorkGroupName(personVo.getName());
//				}
//			}
				String processInstanceId=maintenanceWorkOrderForReportVo.getProcessInstanceId();
				if (null!=processInstanceId&&!"".equals(processInstanceId)) {
					maintenanceWorkOrderForReportVo.setEamImpleRecordVoVoList(getExecution(processInstanceId));
				}
				if (null!=maintenanceWorkOrderForReportVo.getActualExecutorResponsibleId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForReportVo.getActualExecutorResponsibleId());
					if (null!=personVo) {
						maintenanceWorkOrderForReportVo.setActualExecutorResponsibleName(personVo.getName());
					}
				}
				if (null!=maintenanceWorkOrderForReportVo.getReportId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForReportVo.getReportId());
					if (null!=personVo) {
						maintenanceWorkOrderForReportVo.setReportName(personVo.getName());
					}
				}
				if (null!=maintenanceWorkOrderForReportVo.getAssignPersonId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForReportVo.getAssignPersonId());
					if (null!=personVo) {
						maintenanceWorkOrderForReportVo.setAssignPersonName(personVo.getName());
					}
				}
				if (StringUtils.isNotBlank(maintenanceWorkOrderForReportVo.getProjectType())) {
					UserGroupDomainVo userGroupDomainVo=userGroupDomainColler.findUserGroupDomainByDomainValues(maintenanceWorkOrderForReportVo.getProjectType(),
							WorkOrderCommon.WORK_ORDER_PROJECT_TYPE,maintenanceWorkOrderForReportVo.getOrgId(),
							maintenanceWorkOrderForReportVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
					if (userGroupDomainVo != null) {
						maintenanceWorkOrderForReportVo.setProjectGroupTypeName(userGroupDomainVo.getUserGroupName());
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForReportVo, "根据ID查询维保工单-执行汇报成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/findWorkOrderReportById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findWorkOrderReportById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findWorkOrderCheckAcceptById:根据ID查询维保工单-验收确认
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询维保工单-验收确认", response = MaintenanceWorkOrderForCheckAcceptVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/findWorkOrderCheckAcceptById", method = RequestMethod.GET)
    public EnerbosMessage findWorkOrderCheckAcceptById(@ApiParam(value = "维保工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	MaintenanceWorkOrderForCheckAcceptVo maintenanceWorkOrderForCheckAcceptVo=maintenanceWorkOrderClient.findWorkOrderCheckAcceptById(id);
			if (null!=maintenanceWorkOrderForCheckAcceptVo){
				if (StringUtils.isNotBlank(maintenanceWorkOrderForCheckAcceptVo.getAcceptorId())) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForCheckAcceptVo.getAcceptorId());
					if (null!=personVo) {
						maintenanceWorkOrderForCheckAcceptVo.setAcceptorName(personVo.getName());
					}
				}
				String processInstanceId=maintenanceWorkOrderForCheckAcceptVo.getProcessInstanceId();
				if (StringUtils.isNotBlank(processInstanceId)) {
					maintenanceWorkOrderForCheckAcceptVo.setEamImpleRecordVoVoList(getExecution(processInstanceId));
				}
				if (StringUtils.isNotBlank(maintenanceWorkOrderForCheckAcceptVo.getReportId())) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForCheckAcceptVo.getReportId());
					if (null!=personVo) {
						maintenanceWorkOrderForCheckAcceptVo.setReportName(personVo.getName());
					}
				}
				if (StringUtils.isNotBlank(maintenanceWorkOrderForCheckAcceptVo.getActualExecutorResponsibleId())) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(maintenanceWorkOrderForCheckAcceptVo.getActualExecutorResponsibleId());
					if (null!=personVo) {
						maintenanceWorkOrderForCheckAcceptVo.setActualExecutorResponsibleName(personVo.getName());
					}
				}
			}
        	return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderForCheckAcceptVo, "根据ID查询维保工单-验收确认成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/findWorkOrderCheckAcceptById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/findWorkOrderCheckAcceptById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * checkWonumUnique:验证维保工单编码的唯一性
	 * @param id
	 * @param workOrderNum 维保工单编码
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
    @ApiOperation(value = "验证维保工单编码的唯一性", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/checkWoNumUnique", method = RequestMethod.GET)
	public EnerbosMessage checkJpnumUnique(@ApiParam(value = "维保工单id", required = false) @RequestParam(name="id", required = false) String id,
			@ApiParam(value = "维保工单编码id", required = true) @RequestParam("workOrderNum") String workOrderNum,Principal user) {
	    Boolean flag = false;
	    try {
	    	MaintenanceWorkOrderForCommitVo jpn = maintenanceWorkOrderClient.findWorkOrderCommitByWorkOrderNum(workOrderNum);
	        if (id == null) {//新建时
	        	if (jpn == null) {
	              flag = true;
	            }
	        } else {//修改时
              if (jpn != null&&jpn.getId().equals(id)||jpn == null) {
                  flag = true;
              }
	        }
	        return EnerbosMessage.createSuccessMsg(flag, "验证维保工单编码的唯一性成功", "");
	    } catch (Exception e) {
	    	logger.error("-----/eam/open/workorder/checkWoNumUnique ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/checkWoNumUnique----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
	    }
	}

    /**
     * 删除选中的维保工单
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除维保工单", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/deleteWorkOrderList", method = RequestMethod.GET)
    public EnerbosMessage deleteWorkOrderList(@ApiParam(value = "删除选中的维保工单ids", required = true) @RequestParam("ids") List<String> ids,Principal user) {
    	try {
        	for (String id : ids) {
        		MaintenanceWorkOrderForCommitVo workOrder = maintenanceWorkOrderClient.findWorkOrderCommitById(id);
                if (null == workOrder) {
                	return EnerbosMessage.createErrorMsg("","要删除的维保工单不存在", "");
                }else if(!WorkOrderCommon.WORK_ORDER_STATUS_DTB.equals(workOrder.getStatus())) {
					return EnerbosMessage.createErrorMsg("","编码为"+workOrder.getWorkOrderNum()+"的维保工单状态不是待提报", "");
				}else if(StringUtils.isNotBlank(workOrder.getProcessInstanceId())) {
					return EnerbosMessage.createErrorMsg("","编码为"+workOrder.getWorkOrderNum()+"的维保工单已启动流程，不允许删除", "");
				}
			}
        	maintenanceWorkOrderClient.deleteWorkOrderList(ids);
        	maintenanceWorkOrderAssetClient.deleteWorkOrderAssetByWorkOrderIds(ids);
            return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderClient.deleteWorkOrderList(ids), "删除人员信息维保工单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/workorder/deleteWorkOrderList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/deleteWorkOrderList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * 删除维保工单
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除维保工单", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/deleteWorkOrder", method = RequestMethod.GET)
    public EnerbosMessage deleteWorkOrder(@ApiParam(value = "删除选中的维保工单id", required = true) @RequestParam("id") String id,Principal user) {
    	try {
    		MaintenanceWorkOrderForCommitVo workOrder = maintenanceWorkOrderClient.findWorkOrderCommitById(id);
            if (null == workOrder) {
            	return EnerbosMessage.createErrorMsg("","要删除的维保工单不存在", "");
            }else if(!WorkOrderCommon.WORK_ORDER_STATUS_DTB.equals(workOrder.getStatus())) {
            	return EnerbosMessage.createErrorMsg("","ID为"+workOrder.getId()+"的维保工单状态不是待提报", "");
			}
            return EnerbosMessage.createSuccessMsg(maintenanceWorkOrderClient.deleteWorkOrder(id), "删除维保工单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/workorder/deleteWorkOrder ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/deleteWorkOrder----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * 维保工单-收藏
	 * @param ids 维保工单ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "维保工单-收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/collect", method = RequestMethod.POST)
	public EnerbosMessage collectWorkOrder(@ApiParam(value = "维保工单ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/workorder/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要收藏的维保工单！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<MaintenanceWorkOrderRfCollectorVo> workOrderRfCollectorVos = Arrays.stream(ids).map(o -> new MaintenanceWorkOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			maintenanceWorkOrderClient.collectWorkOrder(workOrderRfCollectorVos);

			return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workorder/collect ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/workorder/deleteWorkOrder----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * 维保工单-取消收藏
	 * @param ids 维保工单ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "维保工单-取消收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workorder/collect/cancel", method = RequestMethod.POST)
	public EnerbosMessage cancelCollectWorkOrder(@ApiParam(value = "维保工单ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/workorder/collect/cancel, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要取消收藏的维保工单！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<MaintenanceWorkOrderRfCollectorVo> workOrderRfCollectorVos = Arrays.stream(ids).map(o -> new MaintenanceWorkOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			maintenanceWorkOrderClient.cancelCollectWorkOrder(workOrderRfCollectorVos);
			return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workorder/collect/cancel ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/workorder/deleteWorkOrder----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
    
    /**
     * getExecution:根据流程实例ID查询执行记录
     * @param processInstanceId 流程实例ID
     * @return List<MaintenanceWorkOrderImpleRecordVo> 执行记录VO集合
     */
    private List<WorkFlowImpleRecordVo> getExecution(String processInstanceId) {
    	List<WorkFlowImpleRecordVo> workOrderImpleRecordVoList=new ArrayList<>();
    	List<HistoricTaskVo> historicTaskVoList=processActivitiClient.findProcessTrajectory(processInstanceId);
		WorkFlowImpleRecordVo maintenanceWorkOrderImpleRecordVo=null;
		Set<String> processName=new HashSet<>();
    	if (null!=historicTaskVoList&&historicTaskVoList.size()>0) {
			for (int i = historicTaskVoList.size()-1; i >=0; i--) {
				HistoricTaskVo historicTaskVo=historicTaskVoList.get(i);
        		maintenanceWorkOrderImpleRecordVo=new WorkFlowImpleRecordVo();
        		maintenanceWorkOrderImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
        		maintenanceWorkOrderImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
        		maintenanceWorkOrderImpleRecordVo.setName(historicTaskVo.getName());
				if (processName.isEmpty()||!processName.contains(maintenanceWorkOrderImpleRecordVo.getName())) {
					processName.add(maintenanceWorkOrderImpleRecordVo.getName());
					maintenanceWorkOrderImpleRecordVo.setProcessType(Common.ORDER_PROCESS_TYPE_NORMAL);
				}else if (!processName.isEmpty()&&processName.contains(maintenanceWorkOrderImpleRecordVo.getName())){
					maintenanceWorkOrderImpleRecordVo.setProcessType(Common.ORDER_PROCESS_TYPE_REJECT);
				}

        		if (StringUtils.isNotBlank(historicTaskVo.getAssignee())) {
					String[] ids=historicTaskVo.getAssignee().split(",");
					String personName="";
					if (null!=ids&&!"".equals(ids)){
						for (String id:ids){
							PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(id);
							if (person != null) {
								personName+= personAndUserClient.findByPersonId(id).getName()+",";
							}
						}
						personName=personName.substring(0,personName.length()-1);
					}
        			maintenanceWorkOrderImpleRecordVo.setPersonName(personName);
        		}
        		maintenanceWorkOrderImpleRecordVo.setDurationInMillis(historicTaskVo.getDurationInMillis());
        		maintenanceWorkOrderImpleRecordVo.setDescription(historicTaskVo.getDescription());
        		workOrderImpleRecordVoList.add(0,maintenanceWorkOrderImpleRecordVo);
        	}
		}
    	return workOrderImpleRecordVoList;
    }
}