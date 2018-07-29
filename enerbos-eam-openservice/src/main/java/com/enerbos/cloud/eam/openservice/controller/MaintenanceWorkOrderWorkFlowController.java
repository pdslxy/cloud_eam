package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.MaintenanceWorkOrderClient;
import com.enerbos.cloud.eam.client.OrderPersonClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.WorkOrderCommon;
import com.enerbos.cloud.eam.openservice.service.PushService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.client.ProcessFormClient;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import com.enerbos.cloud.wfs.vo.TaskForFilterVo;
import com.enerbos.cloud.wfs.vo.TaskVo;
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
import java.security.Principal;
import java.util.*;

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
@Api(description = "维保工单流程管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaintenanceWorkOrderWorkFlowController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderWorkFlowController.class);

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
	private SiteClient siteClient;
    
    @Autowired
	private UgroupClient ugroupClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Resource
    protected MaintenanceWorkOrderClient maintenanceWorkOrderClient;
    
    @Resource
    protected WorkflowClient workflowClient;
    
    @Autowired
	private ProcessTaskClient processTaskClient;

    @Autowired
    private ProcessFormClient processFormClient;

    @Autowired
    private OrderPersonClient orderPersonClient;

    @Autowired
    private UserGroupDomainColler userGroupDomainColler;

    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @Autowired
    private PushService pushService;

    /**
     * assignWorkOrderList:批量分派维保工单
     * @param maintenanceWorkOrderForAssignListVo 批量分派维保工单Vo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForAssignListVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量分派维保工单", response = MaintenanceWorkOrderForAssignVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workorder/assignWorkOrderList", method = RequestMethod.POST)
    public EnerbosMessage assignWorkOrderList(@ApiParam(value = "批量分派维保工单VO", required = true) @RequestBody MaintenanceWorkOrderForAssignListVo maintenanceWorkOrderForAssignListVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/WorkOrder/assignWorkOrderList, host: [{}:{}], service_id: {}, MaintenanceWorkOrderForAssignListVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceWorkOrderForAssignListVo);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
        	List<MaintenanceWorkOrderForWorkFlowVo> list=new ArrayList<>();
            String workGroup=null;
            List<String> executorPersonId=maintenanceWorkOrderForAssignListVo.getExecutorPersonId();
            if (executorPersonId == null||executorPersonId.size()<1) {
                return EnerbosMessage.createErrorMsg("", "批量派工-执行人不能为空", "");
            }
            if (maintenanceWorkOrderForAssignListVo.getIds() == null||maintenanceWorkOrderForAssignListVo.getIds().size()<1) {
                return EnerbosMessage.createErrorMsg("", "批量派工-工单ID不能为空", "");
            }
            if (maintenanceWorkOrderForAssignListVo.getPlanStartDate()==null) {
                return EnerbosMessage.createErrorMsg("", "批量派工-计划开始时间不能为空", "");
            }
            if (maintenanceWorkOrderForAssignListVo.getPlanCompletionDate() == null) {
                return EnerbosMessage.createErrorMsg("", "批量派工-计划结束时间不能为空", "");
            }
            for (String person:executorPersonId){
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
        	for (String id : maintenanceWorkOrderForAssignListVo.getIds()) {
                MaintenanceWorkOrderForWorkFlowVo workOrder=maintenanceWorkOrderClient.findWorkOrderWorkFlowById(id);
                if (workOrder == null ) {
                    return EnerbosMessage.createErrorMsg("", id+"维保工单不存在", "");
                }
                if (!WorkOrderCommon.WORK_ORDER_STATUS_DFP.equals(workOrder.getStatus())) {
                    return EnerbosMessage.createErrorMsg("", id+"维保工单状态不是待分派，不允许分派", "");
                }
                List<String> userList = new ArrayList<>();
                List<PersonAndUserVoForDetail> personList=userGroupDomainColler.findUserByDomainValueORDomainNums(workOrder.getProjectType(),
                        WorkOrderCommon.WORK_ORDER_PROJECT_TYPE,workOrder.getOrgId(),
                        workOrder.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
                personList.forEach(person->userList.add(person.getPersonId()));
                if(null==userList||userList.size() <= 0){
                    return EnerbosMessage.createSuccessMsg("", id+"维保工单分派组下没有人员,请联系管理员添加!!", "");
                }
                if (userList.stream().noneMatch(userId::equals)) {
                    return EnerbosMessage.createErrorMsg("401", id+"无权限分派！", "");
                }
                if (StringUtils.isNotBlank(workOrder.getMaintenancePlanNum())&&Common.WORK_ORDER_PROCESS_STATUS_CANCEL.equals(maintenanceWorkOrderForAssignListVo.getProcessStatus())) {
                    return EnerbosMessage.createErrorMsg("401", id+"预防性维护计划生成工单，不允许驳回重新提报", "");
                }
                workOrder.setExecutionWorkGroup(workGroup);
                workOrder.setActualWorkGroup(workGroup);

                //更新执行人和实际执行人数据
                List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                List<OrderPersonVo> actualExecutorVoList = new ArrayList<>();
                for (String personId : executorPersonId) {
                    orderPersonVoList.add(new OrderPersonVo(workOrder.getId(), WorkOrderCommon.WORK_ORDER_PERSON_EXECUTION, personId));
                    actualExecutorVoList.add(new OrderPersonVo(workOrder.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION, personId));
                }
                orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                actualExecutorVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(actualExecutorVoList);
                workOrder.setPlanStartDate(maintenanceWorkOrderForAssignListVo.getPlanStartDate());
                workOrder.setPlanCompletionDate(maintenanceWorkOrderForAssignListVo.getPlanCompletionDate());
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
                list.add(workOrder);
			}
            EnerbosMessage message;
        	for (MaintenanceWorkOrderForWorkFlowVo maintenanceWorkOrderForWorkFlowVo : list) {
        		//分派
                message=assignWorkOrderWorkFlow(maintenanceWorkOrderForWorkFlowVo,maintenanceWorkOrderForAssignListVo.getProcessStatus(), maintenanceWorkOrderForAssignListVo.getDescription(), userId,user.getName());
                if (!message.isSuccess()) {
                    throw new EnerbosException(message.getErrorCode(),message.getDetailMsg());
                }
            }
            return EnerbosMessage.createSuccessMsg(list, "批量分派维保工单成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/workorder/assignWorkOrderList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workorder/assignWorkOrderList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 维保工单-发送流程
     * @param id
     * @param description
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "维保工单-发送流程", response = String.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/workOrderFlow/commit", method = RequestMethod.POST)
    public EnerbosMessage commitWorkOrderFlow(@ApiParam(value = "维保工单id", required = true) @RequestParam("id") String id,
                                                @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name="processStatus",required = true) String processStatus,
                                                @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/workOrderFlow/commit, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,processStatus,description, user);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            MaintenanceWorkOrderForWorkFlowVo workOrder=maintenanceWorkOrderClient.findWorkOrderWorkFlowById(id);
            EnerbosMessage message;
            if (workOrder == null) {
                return EnerbosMessage.createErrorMsg("", "维保工单不存在", "");
            }
            String siteId =workOrder.getSiteId();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||null==site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(workOrder.getProcessInstanceId());
            List<TaskVo> page=processTaskClient.findTasks(taskForFilterVo).getList();
            if (StringUtils.isNotBlank(workOrder.getProcessInstanceId())&&(null==page||page.size()<1)){
                return EnerbosMessage.createErrorMsg("401", "无权操作此工单", "");
            }
            if (!WorkOrderCommon.WORK_ORDER_STATUS_DTB.equals(workOrder.getStatus())&&!workOrder.getStatus().equals(page.get(0).getOrderStatus())) {
                return EnerbosMessage.createErrorMsg("401", "工单状态和流程状态不一致", "");
            }
            switch (workOrder.getStatus()) {
                //待提报分支
                case WorkOrderCommon.WORK_ORDER_STATUS_DTB: {
                    message = commitWorkOrder(workOrder,description,userId,user.getName());
                } break;
                //待分派分支
                case WorkOrderCommon.WORK_ORDER_STATUS_DFP: {
                    message = assignWorkOrderWorkFlow(workOrder,processStatus,description,userId,user.getName());
                } break;
                //待接单分支
                case WorkOrderCommon.WORK_ORDER_STATUS_DJD: {
                    message = takingWorkOrderFlow(workOrder,processStatus,description,userId,user.getName());
                } break;
                //待汇报分支
                case WorkOrderCommon.WORK_ORDER_STATUS_DHB: {
                    message = reportWorkOrderFlow(workOrder,processStatus,description,userId,user.getName());
                } break;
                //申请挂起分支
                case WorkOrderCommon.WORK_ORDER_STATUS_SQGQ: {
                    message = applySuspendReportWorkOrderFlow(workOrder,processStatus,description,userId,user.getName());
                } break;
                //挂起分支
                case WorkOrderCommon.WORK_ORDER_STATUS_GQ: {
                    message = confirmSuspendWorkOrderFlow(workOrder,processStatus,description,userId,user.getName());
                } break;
                //待验收分支
                case WorkOrderCommon.WORK_ORDER_STATUS_DYS: {
                    message = acceptWorkOrderFlow(workOrder,processStatus,description,userId,user.getName());
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", workOrder.getStatus()));
            }
            return message;
        } catch (Exception e) {
            logger.error("-----/eam/open/workOrderFlow/commit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workOrderFlow/commit----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * commitWorkOrderWorkFlow:维保工单，流程启动、提报
     * @param workOrder
     * @param description  维保工单提报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage commitWorkOrder(MaintenanceWorkOrderForWorkFlowVo workOrder,String description,String userId,String loginName) {
        try {
            if (!userId.equals(workOrder.getReportId())) {
                return EnerbosMessage.createErrorMsg("", "不是提报人，不能提报！", "");
            }
            if (workOrder.getProcessInstanceId() == null||"".equals(workOrder.getProcessInstanceId())) {
                //启动流程
                //设置流程变量
                Map<String, Object> variables = new HashMap<String, Object>();
                //业务主键
                String businessKey = workOrder.getId();
                //流程key,key为维保固定前缀+站点code
                String code="";
                SiteVoForDetail site=siteClient.findById(workOrder.getSiteId());
                if (site != null) {
                    code=site.getCode();
                }
                String processKey = Common.WORK_ORDER_WFS_PROCESS_KEY + code;
                ProcessVo processVo=new ProcessVo();
                processVo.setBusinessKey(businessKey);
                processVo.setProcessKey(processKey);
                processVo.setUserId(userId);
                variables.put(WorkOrderCommon.WORK_ORDER_SUBMIT_USER, userId);
                variables.put(Common.ORDER_NUM,workOrder.getWorkOrderNum());
                variables.put(Common.ORDER_DESCRIPTION,workOrder.getDescription());
                variables.put("userId", userId);
                logger.debug("/eam/open/workorder/commit, processKey: {}", processKey);
                processVo=workflowClient.startProcess(variables, processVo);

                if (null==processVo || "".equals(processVo.getProcessInstanceId())) {
                    return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
                }
                //提报，修改基本字段保存
                workOrder.setProcessInstanceId(processVo.getProcessInstanceId());
                workOrder.setReportDate(new Date());
                workOrder = maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
        	//查询分派组签收人员
            Map<String, Object> variables = new HashMap<String, Object>();
            List<String> userList = new ArrayList<>();
            List<PersonAndUserVoForDetail> personList=userGroupDomainColler.findUserByDomainValueORDomainNums(workOrder.getProjectType(),
                    WorkOrderCommon.WORK_ORDER_PROJECT_TYPE,workOrder.getOrgId(),
                    workOrder.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            personList.forEach(person->userList.add(person.getPersonId()));
            if(null==userList||userList.size() <= 0){
            	return EnerbosMessage.createSuccessMsg("", "维保工单分派组下没有人员,请联系管理员添加!!", "");
            }
            variables.put(WorkOrderCommon.WORK_ORDER_ASSIGN_USER, StringUtils.join(userList, ","));
            variables.put("description", description);
            variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DFP);
            variables.put("userId", userId);
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            //提报，修改基本字段保存
        	workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DFP);
        	workOrder.setStatusDate(new Date());//状态日期
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,null,WorkOrderCommon.WORK_ORDER_STATUS_DTB);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), "提报维保工单成功", "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_DTB.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DTB);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/commit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/commit----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * assignWorkOrderWorkFlow:维保工单-分派/驳回
     * @param workOrder
     * @param processStatus
     * @param description 分派工单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage assignWorkOrderWorkFlow(MaintenanceWorkOrderForWorkFlowVo workOrder,String processStatus,String description,String userId,String loginName) {
        try {
            List<String> userList = new ArrayList<>();
            List<PersonAndUserVoForDetail> personList=userGroupDomainColler.findUserByDomainValueORDomainNums(workOrder.getProjectType(),
                    WorkOrderCommon.WORK_ORDER_PROJECT_TYPE,workOrder.getOrgId(),
                    workOrder.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            personList.forEach(person->userList.add(person.getPersonId()));
            if(null==userList||userList.size() <= 0){
                return EnerbosMessage.createSuccessMsg("", "维保工单分派组下没有人员,请联系管理员添加!!", "");
            }
            if (userList.stream().noneMatch(userId::equals)) {
                return EnerbosMessage.createErrorMsg("401", "无权限分派！", "");
            }
            if (StringUtils.isNotBlank(workOrder.getMaintenancePlanNum())&&Common.WORK_ORDER_PROCESS_STATUS_CANCEL.equals(processStatus)) {
                return EnerbosMessage.createErrorMsg("401", "预防性维护计划生成工单，不允许驳回重新提报", "");
            }

            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    //执行人
                    List<String> executors = new ArrayList<>();
                    List<OrderPersonVo> executorPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(workOrder.getId(), WorkOrderCommon.WORK_ORDER_PERSON_EXECUTION));
                    executorPersonList.forEach(executor->executors.add(executor.getPersonId()));
                    if (executors.size()<1) {
                        return EnerbosMessage.createErrorMsg(null, "执行人不能为空", "");
                    }
                    if (workOrder.getEntrustExecute()) {
                        //委托执行人
                        List<OrderPersonVo> entrustExecutePersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(workOrder.getId(), WorkOrderCommon.WORK_ORDER_PERSON_ENTRUST_EXECUTION));
                        entrustExecutePersonList.forEach(entrustExecutor->executors.add(entrustExecutor.getPersonId()));
                    }
                    for (String executorId : executors) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(executorId);
                        Assert.notNull(personVo, "未知执行人！");
                    }
                    //TODO 预留物料
                    variables.put(WorkOrderCommon.WORK_ORDER_RECEIVE_USER, StringUtils.join(executors,","));
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_ASSIGN_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_ASSIGN_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DJD);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DJD);//工单状态更新到待接单
                    message="维保工单-分派成功";
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_ASSIGN_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_ASSIGN_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DTB);
                    variables.put(WorkOrderCommon.WORK_ORDER_SUBMIT_USER, userId);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DTB);//工单状态更新
                    message="驳回维保工单，重新提报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", workOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put(WorkOrderCommon.WORK_ORDER_ASSIGN_USER, userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            workOrder.setStatusDate(new Date());//状态日期
            workOrder.setAssignPersonId(userId);//设定自己为分派人
            //保存工单
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,processStatus,WorkOrderCommon.WORK_ORDER_STATUS_DFP);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待分派
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_DFP.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DFP);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/assignWorkOrderWorkFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/assignWorkOrderWorkFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * takingWorkOrderFlow:维保工单-接单
     * @param workOrder
     * @param processStatus
     * @param description 维保工单-接单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage takingWorkOrderFlow(MaintenanceWorkOrderForWorkFlowVo workOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意接单，流程走下一步
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_TAKING_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_TAKING_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DHB);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    //指定汇报人为接单人
                    variables.put(WorkOrderCommon.WORK_ORDER_REPORT_USER,userId);
                    message="维保工单-接单成功";
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_TAKING_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_TAKING_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DFP);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DFP);//工单状态更新到待分派
                    message="驳回维保工单，重新分派成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", workOrder.getId(),processStatus,description));
            }
            variables.put(WorkOrderCommon.WORK_ORDER_RECEIVE_USER, userId);
            variables.put("userId", userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            workOrder.setStatusDate(new Date());//状态日期
            workOrder.setActualExecutorResponsibleId(userId);//添加实际执行负责人
            //保存工单
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,processStatus,WorkOrderCommon.WORK_ORDER_STATUS_DJD);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待接单
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_DJD.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DJD);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/takingWorkOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/takingWorkOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * reportWorkOrderFlow:维保工单-汇报
     * @param workOrder
     * @param processStatus
     * @param description 工单汇报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage reportWorkOrderFlow(MaintenanceWorkOrderForWorkFlowVo workOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    //根据是否挂起，判断是申请挂起还是汇报
                    if (!workOrder.getSuspension()) {
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REPORT_PASS, true);
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_APPLY_SUSPEND_PASS, false);
                        variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DYS);
                        workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DYS);//工单状态更新到验收待验收
                        variables.put(WorkOrderCommon.WORK_ORDER_PERFORMREPORTING_USER, workOrder.getAssignPersonId());//设置验收人为分派人
                        message="维保工单-执行汇报成功";
                    }else {
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_APPLY_SUSPEND_PASS, true);
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REPORT_PASS, false);
                        variables.put(WorkOrderCommon.WORK_ORDER_SUSPENDAPPLY_USER, workOrder.getAssignPersonId());
                        variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_SQGQ);
                        workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_SQGQ);//工单状态更新到申请挂起
                        message="维保工单-申请挂起成功";
                    }
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_REPORT_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_REPORT_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REPORT_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_APPLY_SUSPEND_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DFP);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DFP);//工单状态更新到待分派
                    message="驳回维保工单，重新分派成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", workOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            workOrder.setStatusDate(new Date());//状态日期
            //保存工单
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,processStatus,WorkOrderCommon.WORK_ORDER_STATUS_DHB);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待汇报
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_DHB.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DHB);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/reportWorkOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/reportWorkOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * applySuspendReportWorkOrderFlow:维保工单-申请挂起变更到驳回重新执行汇报/维修商挂起到待验收/挂起
     * @param workOrder
     * @param processStatus
     * @param description 维保工单-申请挂起驳回重新执行汇报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage applySuspendReportWorkOrderFlow(MaintenanceWorkOrderForWorkFlowVo workOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    //根据挂起类型，判断是挂起还是到确认验收
                    if (Common.GYSWX.equals(workOrder.getSuspensionType())) {
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS, true);
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_PASS, false);
                        variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DYS);
                        variables.put(WorkOrderCommon.WORK_ORDER_PERFORMREPORTING_USER, workOrder.getReportId());
                        workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DYS);//工单状态更新到待验收
                        message="维保工单申请挂起到待验收成功";
                    }else {
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_PASS, true);
                        variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS, false);
                        variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_GQ);
                        variables.put(WorkOrderCommon.WORK_ORDER_SUSPEND_USER, workOrder.getActualExecutorResponsibleId());
                        workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_GQ);//工单状态更新到挂起
                        message="维保工单申请挂起到挂起成功";
                    }
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DHB);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    message="驳回维保工单，重新执行汇报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", workOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            workOrder.setStatusDate(new Date());//状态日期
            //保存工单
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,processStatus,WorkOrderCommon.WORK_ORDER_STATUS_SQGQ);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成申请挂起
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_SQGQ.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_SQGQ);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/applySuspendRejectReportWorkOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/applySuspendRejectReportWorkOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * confirmSuspendWorkOrderFlow:维保工单-确认挂起/驳回到待分派/驳回到待汇报/待验收
     * @param workOrder
     * @param processStatus
     * @param description 维保工单-确认挂起说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage confirmSuspendWorkOrderFlow(MaintenanceWorkOrderForWorkFlowVo workOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_ACCEPT_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_REPORT_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DYS);
                    variables.put(WorkOrderCommon.WORK_ORDER_PERFORMREPORTING_USER, workOrder.getReportId());
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DYS);//工单状态更新到待验收
                    message="维保工单挂起到待验收成功";
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    //驳回到执行汇报
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_REPORT_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_ACCEPT_PASS, false);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DHB);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    message="驳回维保工单挂起，重新执行汇报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", workOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_ASSIGN_PASS, false);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            workOrder.setStatusDate(new Date());//状态日期
            //保存工单
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,processStatus,WorkOrderCommon.WORK_ORDER_STATUS_GQ);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成挂起
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_GQ.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_GQ);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/confirmSuspendWorkOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/confirmSuspendWorkOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * acceptWorkOrderFlow:维保工单-验收/驳回重新汇报
     * @param workOrder
     * @param processStatus
     * @param description 维保工单-验收说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage acceptWorkOrderFlow(MaintenanceWorkOrderForWorkFlowVo workOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    if (!workOrder.getConfirm()||workOrder.getAcceptionTime()==null) {
                        return EnerbosMessage.createErrorMsg("401", "问题未解决或者验收时间为空不允许验收", "");
                    }
                    //同意分派，流程走下一步
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_ACCEPT_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_ACCEPT_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_GB);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_GB);//工单状态更新到关闭
                    message="维保工单验收成功";
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    //驳回到执行汇报
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_REJECT_ACCEPT_PASS, true);
                    variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_ACCEPT_PASS, false);
                    variables.put("status", WorkOrderCommon.WORK_ORDER_STATUS_DHB);
                    workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    message="驳回维保工单验收，重新执行汇报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", workOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put(WorkOrderCommon.WORK_ORDER_ACTIVITY_SUSPEND_ASSIGN_PASS, false);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(workOrder.getProcessInstanceId(), variables);
            workOrder.setStatusDate(new Date());//状态日期
            //保存工单
            workOrder=maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            this.sendPush(workOrder,processStatus,WorkOrderCommon.WORK_ORDER_STATUS_DYS);
            return EnerbosMessage.createSuccessMsg(workOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待验收
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(workOrder.getId()) &&
                    !WorkOrderCommon.WORK_ORDER_STATUS_DYS.equals(workOrder.getStatus())) {
                workOrder.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DYS);
                maintenanceWorkOrderClient.saveWorkOrder(workOrder);
            }
            logger.error("-----/eam/open/workorder/acceptWorkOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/acceptWorkOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     *APP发送
     * @param workOrderFlowVo 工单类型
     * @param processStatus   流程请求状态（确认：agree，驳回：reject）
     * @param workOrderStatus 工单状态
     */
    private void  sendPush(MaintenanceWorkOrderForWorkFlowVo workOrderFlowVo,String processStatus,String workOrderStatus) {
        List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(workOrderFlowVo.getProcessInstanceId());
        String[] person = null;
        if (historicTaskVoList.size() > 0) {
            person = historicTaskVoList.get(0).getAssignee().split(",");
            if (person.length>0) {
                for (int i = 0; i < person.length; i++) {
                    PersonAndUserVoForDetail personDetail=personAndUserClient.findByPersonId(person[i]);
                    if (personDetail != null) {
                        person[i]=personDetail.getLoginName();
                    }
                }
            }
        }
        //标题
        String pushTitle = null;
        //内容
        String pushContent = null;
        //用于组合的短信内容
        String buildUpContent = null;
        String workType = "维保工单";
        String status = null;
        switch (workOrderStatus) {
            case WorkOrderCommon.WORK_ORDER_STATUS_DTB: {
                status = "分派";
                pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
            }
            break;
            case WorkOrderCommon.WORK_ORDER_STATUS_DFP: {
                if (Common.WORK_ORDER_PROCESS_STATUS_AGREE.equals(processStatus)) {
                    status = "接单";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                } else if (Common.WORK_ORDER_PROCESS_STATUS_REJECT.equals(processStatus)) {
                    status = "提报";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }
            break;
            case WorkOrderCommon.WORK_ORDER_STATUS_DJD: {
                if (Common.WORK_ORDER_PROCESS_STATUS_AGREE.equals(processStatus)) {
                    status = "执行";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                } else if (Common.WORK_ORDER_PROCESS_STATUS_REJECT.equals(processStatus)) {
                    status = "分派";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }
            break;
            case WorkOrderCommon.WORK_ORDER_STATUS_SQGQ: {
                if (Common.WORK_ORDER_PROCESS_STATUS_AGREE.equals(processStatus)) {
                    if (Common.GYSWX.equals(workOrderFlowVo.getSuspensionType())) {
                        status = "验收";
                        pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }else {
                        status = "已挂起";
                        pushTitle = ""+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }
                } else if (Common.WORK_ORDER_PROCESS_STATUS_REJECT.equals(processStatus)) {
                    status = "汇报";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }
            break;
            case WorkOrderCommon.WORK_ORDER_STATUS_GQ: {
                if (Common.WORK_ORDER_PROCESS_STATUS_AGREE.equals(processStatus)) {
                    status = "验收";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                } else if (Common.WORK_ORDER_PROCESS_STATUS_REJECT.equals(processStatus)) {
                    status = "提报";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }
            break;
            case WorkOrderCommon.WORK_ORDER_STATUS_DHB: {
                if (Common.WORK_ORDER_PROCESS_STATUS_AGREE.equals(processStatus)) {

                    if (!workOrderFlowVo.getSuspension()) {
                        status = "验收";
                        pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);

                    }else {
                        status = "确认挂起";
                        pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }
                } else if (Common.WORK_ORDER_PROCESS_STATUS_REJECT.equals(processStatus)) {
                    status = "提报";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }
            break;
            case WorkOrderCommon.WORK_ORDER_STATUS_DYS: {
                if (Common.WORK_ORDER_PROCESS_STATUS_AGREE.equals(processStatus)) {
                    status = "验收完成";
                    pushTitle = ""+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒："+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                } else if (Common.WORK_ORDER_PROCESS_STATUS_REJECT.equals(processStatus)) {
                    status = "提报";
                    pushTitle = "请"+status+"" + workType + ",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<" + workType + ">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }
            break;
        }
    }

    private Map getExtraMap(MaintenanceWorkOrderForWorkFlowVo workOrderFlowVo) {
        Map extras = new HashMap<>();
        extras.put("getBusinessDataByCurrentUser", "true");
        extras.put("workorderid", workOrderFlowVo.getId());
        extras.put("notificationType", Common.WORK_ORDER_WFS_PROCESS_KEY
        );
        return extras;
    }
}