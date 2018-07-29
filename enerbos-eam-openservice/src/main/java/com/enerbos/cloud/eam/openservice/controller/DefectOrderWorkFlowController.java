package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.DefectDocumentClient;
import com.enerbos.cloud.eam.client.DefectOrderClient;
import com.enerbos.cloud.eam.client.OrderPersonClient;
import com.enerbos.cloud.eam.client.UserGroupDomainClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DefectCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessFormClient;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
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
 * @date 2017年09月07日
 * @Description 消缺工单接口
 */
@RestController
@Api(description = "消缺工单流程管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DefectOrderWorkFlowController {

    private Logger logger = LoggerFactory.getLogger(DefectOrderWorkFlowController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
	private SiteClient siteClient;

    @Autowired
	private UgroupClient ugroupClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Resource
    protected DefectOrderClient defectOrderClient;

    @Resource
    protected WorkflowClient workflowClient;

    @Autowired
	private ProcessTaskClient processTaskClient;

    @Autowired
    private ProcessFormClient processFormClient;

    @Autowired
    private OrderPersonClient orderPersonClient;

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;

    @Autowired
    private DefectDocumentClient defectDocumentClient;

//    /**
//     * assignDefectOrderList:批量分派消缺工单
//     * @param defectOrderForAssignListVo 批量分派消缺工单Vo {@link DefectOrderForAssignListVo}
//     * @return EnerbosMessage返回执行码及数据
//     */
//    @ApiOperation(value = "批量分派消缺工单", response = DefectOrderForAssignVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
//    @RequestMapping(value = "/eam/open/defectOrderFlow/assignDefectOrderList", method = RequestMethod.POST)
//    public EnerbosMessage assignDefectOrderList(@ApiParam(value = "批量分派消缺工单VO", required = true) @RequestBody DefectOrderForAssignListVo defectOrderForAssignListVo,Principal user) {
//        try {
//        	ServiceInstance instance = client.getLocalServiceInstance();
//        	logger.info("/eam/open/DefectOrder/assignDefectOrderList, host: [{}:{}], service_id: {}, DefectOrderForAssignListVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectOrderForAssignListVo);
//
//            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
//        	List<DefectOrderVo> list=new ArrayList<>();
//            String workGroup=null;
//            List<String> executorPersonId=defectOrderForAssignListVo.getExecutorPersonId();
//            if (executorPersonId == null||executorPersonId.size()<1) {
//                return EnerbosMessage.createErrorMsg("", "批量派工-执行人不能为空", "");
//            }
//            if (defectOrderForAssignListVo.getIds() == null||defectOrderForAssignListVo.getIds().size()<1) {
//                return EnerbosMessage.createErrorMsg("", "批量派工-工单ID不能为空", "");
//            }
//            if (defectOrderForAssignListVo.getPlanStartDate()==null) {
//                return EnerbosMessage.createErrorMsg("", "批量派工-计划开始时间不能为空", "");
//            }
//            if (defectOrderForAssignListVo.getPlanCompletionDate() == null) {
//                return EnerbosMessage.createErrorMsg("", "批量派工-计划结束时间不能为空", "");
//            }
//            for (String person:executorPersonId){
//                PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(person);
//                if (personVo == null) {
//                    return EnerbosMessage.createErrorMsg("405", "消缺工单执行人中有不存在的人员", "");
//                }
//                if (StringUtils.isBlank(workGroup)){
//                    workGroup=personVo.getDefectgroup();
//                }else if (!workGroup.equals(workGroup=personVo.getDefectgroup())){
//                    workGroup=null;
//                    break;
//                }
//            }
//        	for (String id : defectOrderForAssignListVo.getIds()) {
//                DefectOrderVo workOrder=defectOrderClient.findDefectOrderDefectFlowById(id);
//                if (workOrder == null ) {
//                    return EnerbosMessage.createErrorMsg("", id+"消缺工单不存在", "");
//                }
//                if (!DefectCommon.DEFECT_ORDER_STATUS_DFP.equals(workOrder.getStatus())) {
//                    return EnerbosMessage.createErrorMsg("", id+"消缺工单状态不是待分派，不允许分派", "");
//                }
//                List<String> userList = new ArrayList<>();
//                List<PersonAndUserVoForDetail> personList=userGroupDomainColler.findUserByDomainValueORDomainNums(workOrder.getProjectType(),
//                        DefectCommon.DEFECT_ORDER_PROJECT_TYPE,workOrder.getOrgId(),
//                        workOrder.getSiteId());
//                personList.forEach(person->userList.add(person.getPersonId()));
//                if(null==userList||userList.size() <= 0){
//                    return EnerbosMessage.createSuccessMsg("", id+"消缺工单分派组下没有人员,请联系管理员添加!!", "");
//                }
//                if (userList.stream().noneMatch(userId::equals)) {
//                    return EnerbosMessage.createErrorMsg("401", id+"无权限分派！", "");
//                }
//                if (StringUtils.isNotBlank(workOrder.getPlanNum())&&Common.WORK_ORDER_PROCESS_STATUS_CANCEL.equals(defectOrderForAssignListVo.getProcessStatus())) {
//                    return EnerbosMessage.createErrorMsg("401", id+"预防性维护计划生成工单，不允许驳回重新提报", "");
//                }
//                workOrder.setExecutionDefectGroup(workGroup);
//                workOrder.setActualDefectGroup(workGroup);
//
//                //更新执行人和实际执行人数据
//                List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
//                List<OrderPersonVo> actualExecutorVoList = new ArrayList<>();
//                for (String personId : executorPersonId) {
//                    orderPersonVoList.add(new OrderPersonVo(workOrder.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION, personId));
//                    actualExecutorVoList.add(new OrderPersonVo(workOrder.getId(), DefectCommon.DEFECT_ORDER_PERSON_ACTUAL_EXECUTION, personId));
//                }
//                orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
//                actualExecutorVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(actualExecutorVoList);
//                workOrder.setPlanStartDate(defectOrderForAssignListVo.getPlanStartDate());
//                workOrder.setPlanCompletionDate(defectOrderForAssignListVo.getPlanCompletionDate());
//                defectOrderClient.saveDefectOrder(workOrder);
//                list.add(workOrder);
//			}
//            EnerbosMessage message;
//        	for (DefectOrderVo defectOrderForDefectFlowVo : list) {
//        		//分派
//                message=assignDefectOrderDefectFlow(defectOrderForDefectFlowVo,defectOrderForAssignListVo.getProcessStatus(), defectOrderForAssignListVo.getDescription(), userId,user.getName());
//                if (!message.isSuccess()) {
//                    throw new EnerbosException(message.getErrorCode(),message.getDetailMsg());
//                }
//            }
//            return EnerbosMessage.createSuccessMsg(list, "批量分派消缺工单成功", "");
//        } catch (Exception e) {
//        	logger.error("-----/eam/open/defectOrderFlow/assignDefectOrderList ------", e);
//            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
//            String statusCode  = "" ;
//            try {
//            	JSONObject jsonMessage = JSONObject.parseObject(message);
//            	if(jsonMessage !=null){
//            		statusCode =   jsonMessage.get("status").toString(); 
//       		 	}
//			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/defectOrderFlow/assignDefectOrderList----",jsonException);
//			}
//            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
//        }
//    }

    /**
     * 整改工单-发送流程
     * @param id
     * @param description
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "整改工单-发送流程", response = String.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrderFlow/commit", method = RequestMethod.POST)
    public EnerbosMessage commitDefectOrderFlow(@ApiParam(value = "消缺工单id", required = true) @RequestParam("id") String id,
                                                @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name="processStatus",required = true) String processStatus,
                                                @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/defectOrderFlow/commit, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,processStatus,description, user);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            DefectOrderVo defectOrder=defectOrderClient.findDefectOrderById(id);
            EnerbosMessage message;
            if (defectOrder == null) {
                return EnerbosMessage.createErrorMsg("", "消缺工单不存在", "");
            }
            String siteId =defectOrder.getSiteId();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||null==site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(defectOrder.getProcessInstanceId());
            List<TaskVo> page=processTaskClient.findTasks(taskForFilterVo).getList();
            if (StringUtils.isNotBlank(defectOrder.getProcessInstanceId())&&(null==page||page.size()<1)){
                return EnerbosMessage.createErrorMsg("401", "无权操作此工单", "");
            }
            if (!DefectCommon.DEFECT_ORDER_STATUS_DTB.equals(defectOrder.getStatus())&&!defectOrder.getStatus().equals(page.get(0).getOrderStatus())) {
                return EnerbosMessage.createErrorMsg("401", "工单状态和流程状态不一致", "");
            }
//            if (!defectOrder.getStatus().equals(page.get(0).getOrderStatus())) {
//                return EnerbosMessage.createErrorMsg("401", "工单状态和流程状态不一致", "");
//            }
            switch (defectOrder.getStatus()) {
                //待提报分支
                case DefectCommon.DEFECT_ORDER_STATUS_DTB: {
                    message = commitDefectOrder(defectOrder,description,userId,user.getName());
                } break;
                //待分派分支
                case DefectCommon.DEFECT_ORDER_STATUS_DFP: {
                    message = assignDefectOrderDefectFlow(defectOrder,processStatus,description,userId,user.getName());
                } break;
                //待接单分支
                case DefectCommon.DEFECT_ORDER_STATUS_DJD: {
                    message = takingDefectOrderFlow(defectOrder,processStatus,description,userId,user.getName());
                } break;
                //待汇报分支
                case DefectCommon.DEFECT_ORDER_STATUS_DHB: {
                    message = reportDefectOrderFlow(defectOrder,processStatus,description,userId,user.getName());
                } break;
                //申请挂起分支
                case DefectCommon.DEFECT_ORDER_STATUS_SQGQ: {
                    message = applySuspendReportDefectOrderFlow(defectOrder,processStatus,description,userId,user.getName());
                } break;
                //挂起分支
                case DefectCommon.DEFECT_ORDER_STATUS_GQ: {
                    message = confirmSuspendDefectOrderFlow(defectOrder,processStatus,description,userId,user.getName());
                } break;
                //待验收分支
                case DefectCommon.DEFECT_ORDER_STATUS_DYS: {
                    message = acceptDefectOrderFlow(defectOrder,processStatus,description,userId,user.getName());
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", defectOrder.getStatus()));
            }

            //更新进度
            Map map=this.findDefectDocumentCountByDefectOrderId(defectOrder.getDefectDocumentId());
            String completeness=map.get("completeness").toString();
            DefectDocumentVo documentVo=defectDocumentClient.findDefectDocumentById(defectOrder.getDefectDocumentId());
            documentVo.setCompleteness(completeness);
            defectDocumentClient.saveDefectDocument(documentVo);

            return message;
        } catch (Exception e) {
            logger.error("-----/eam/open/defectOrderFlow/commit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/commit----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    public Map findDefectDocumentCountByDefectOrderId(String defectOrderId){
        Map map=new HashMap();
        DefectOrderForFilterVo defectOrderForFilterVo=new DefectOrderForFilterVo();
        defectOrderForFilterVo.setDefectDocumentId(defectOrderId);
        EnerbosPage<DefectOrderForListVo> page=defectOrderClient.findPageDefectOrderList(defectOrderForFilterVo);//总数
        map.put("total",page.getTotal());
        List status=new ArrayList();
        status.add( DefectCommon.DEFECT_ORDER_STATUS_GQ);
        status.add( DefectCommon.DEFECT_ORDER_STATUS_GB);
        defectOrderForFilterVo.setStatus(status);
        EnerbosPage<DefectOrderForListVo> page1=defectOrderClient.findPageDefectOrderList(defectOrderForFilterVo);//总数
        map.put("complete",page1.getTotal());

        map.put("completeness",page1.getTotal()+"/"+page.getTotal());
        return  map;
    }
    /**
     * commitDefectOrderDefectFlow:整改工单，流程启动、提报
     * @param defectOrder
     * @param description  消缺工单提报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage commitDefectOrder(DefectOrderVo defectOrder,String description,String userId,String loginName) {
        try {
            if (!userId.equals(defectOrder.getReportId())) {
                return EnerbosMessage.createErrorMsg("", "不是提报人，不能提报！", "");
            }
            if (defectOrder.getProcessInstanceId() == null||"".equals(defectOrder.getProcessInstanceId())) {
                //启动流程
                //设置流程变量
                Map<String, Object> variables = new HashMap<String, Object>();
                //业务主键
                String businessKey = defectOrder.getId();
                //流程key,key为维保固定前缀+站点code
                String code="";
                SiteVoForDetail site=siteClient.findById(defectOrder.getSiteId());
                if (site != null) {
                    code=site.getCode();
                }
                String processKey = DefectCommon.DEFECT_ORDER_WFS_PROCESS_KEY + code;
                ProcessVo processVo=new ProcessVo();
                processVo.setBusinessKey(businessKey);
                processVo.setProcessKey(processKey);
                processVo.setUserId(userId);
                variables.put(DefectCommon.DEFECT_ORDER_SUBMIT_USER, userId);
                variables.put(Common.ORDER_NUM,defectOrder.getDefectOrderNum());
                variables.put(Common.ORDER_DESCRIPTION,defectOrder.getDescription());
                variables.put("userId", userId);
                logger.debug("/eam/open/defectOrderFlow/commit, processKey: {}", processKey);
                processVo=workflowClient.startProcess(variables, processVo);

                if (null==processVo || "".equals(processVo.getProcessInstanceId())) {
                    return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
                }
                //提报，修改基本字段保存
                defectOrder.setProcessInstanceId(processVo.getProcessInstanceId());
                defectOrder.setReportDate(new Date());
                defectOrder = defectOrderClient.saveDefectOrder(defectOrder);
            }
        	//查询分派组签收人员
            Map<String, Object> variables = new HashMap<String, Object>();
            List<String> userList = new ArrayList<>();
            UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(defectOrder.getProjectType(),
                    DefectCommon.DEFECT_DOCUMENT_PROJECT_TYPE,defectOrder.getOrgId(),
                    defectOrder.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            if (vo != null) {
                UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
                if (voForDetail != null) {
                    voForDetail.getUsers().stream().map(person->personAndUserClient.findByPersonId(person.getPersonId())).filter(Objects::nonNull).forEach(person->userList.add(person.getPersonId()));
                }
            }
            if(null==userList||userList.size() <= 0){
            	return EnerbosMessage.createErrorMsg(null, "整改工单分派组下没有人员,请联系管理员添加!!", null);
            }
            variables.put(DefectCommon.DEFECT_ORDER_ASSIGN_USER, StringUtils.join(userList, ","));
            variables.put("description", description);
            variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DFP);
            variables.put("userId", userId);
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            //提报，修改基本字段保存
        	defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DFP);
        	defectOrder.setStatusDate(new Date());//状态日期
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), "提报整改工单成功", "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_DTB.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DTB);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/commit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/commit----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * assignDefectOrderDefectFlow:整改工单-分派/驳回
     * @param defectOrder
     * @param processStatus
     * @param description 分派工单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage assignDefectOrderDefectFlow(DefectOrderVo defectOrder,String processStatus,String description,String userId,String loginName) {
        try {
            List<String> userList = new ArrayList<>();
            UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(defectOrder.getProjectType(),
                    DefectCommon.DEFECT_DOCUMENT_PROJECT_TYPE,defectOrder.getOrgId(),
                    defectOrder.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            if (vo != null) {
                UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
                if (voForDetail != null) {
                    voForDetail.getUsers().stream().map(person->personAndUserClient.findByPersonId(person.getPersonId())).filter(Objects::nonNull).forEach(person->userList.add(person.getPersonId()));
                }
            }
            if(null==userList||userList.size() <= 0){
                return EnerbosMessage.createErrorMsg("", "消缺工单分派组下没有人员,请联系管理员添加!!", "");
            }
            if (userList.stream().noneMatch(userId::equals)) {
                return EnerbosMessage.createErrorMsg("401", "无权限分派！", "");
            }

            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    //执行人
                    List<String> executors = new ArrayList<>();
                    List<OrderPersonVo> executorPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrder.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION));
                    executorPersonList.forEach(executor->executors.add(executor.getPersonId()));
                    if (executors.size()<1) {
                        return EnerbosMessage.createErrorMsg(null, "执行人不能为空", "");
                    }
                    if (defectOrder.getEntrustExecute()) {
                        //委托执行人
                        List<OrderPersonVo> entrustExecutePersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrder.getId(), DefectCommon.DEFECT_ORDER_PERSON_ENTRUST_EXECUTION));
                        entrustExecutePersonList.forEach(entrustExecutor->executors.add(entrustExecutor.getPersonId()));
                    }
                    for (String executorId : executors) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(executorId);
                        Assert.notNull(personVo, "未知执行人！");
                    }

                    variables.put(DefectCommon.DEFECT_ORDER_RECEIVE_USER, StringUtils.join(executors,","));
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_ASSIGN_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_ASSIGN_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DJD);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DJD);//工单状态更新到待接单
                    message="整改工单-分派成功";
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_ASSIGN_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_ASSIGN_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DTB);
                    variables.put(DefectCommon.DEFECT_ORDER_SUBMIT_USER, userId);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DTB);//工单状态更新
                    message="驳回消缺工单，重新提报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put(DefectCommon.DEFECT_ORDER_ASSIGN_USER, userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            defectOrder.setStatusDate(new Date());//状态日期
            defectOrder.setAssignPersonId(userId);//设定自己为分派人
            defectOrder.setReponseTime(new Date());//设置响应时间
            //保存工单
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待分派
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_DFP.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DFP);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/assignDefectOrderDefectFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/assignDefectOrderDefectFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * takingDefectOrderFlow:整改工单-接单
     * @param defectOrder
     * @param processStatus
     * @param description 整改工单-接单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage takingDefectOrderFlow(DefectOrderVo defectOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意接单，流程走下一步
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_TAKING_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_TAKING_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DHB);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    //指定汇报人为接单人
                    variables.put(DefectCommon.DEFECT_ORDER_REPORT_USER,userId);
                    message="整改工单-接单成功";
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_TAKING_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_TAKING_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DFP);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DFP);//工单状态更新到待分派
                    message="驳回消缺工单，重新分派成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectOrder.getId(),processStatus,description));
            }
            variables.put(DefectCommon.DEFECT_ORDER_RECEIVE_USER, userId);
            variables.put("userId", userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            defectOrder.setStatusDate(new Date());//状态日期
            defectOrder.setActualExecutorResponsibleId(userId);//添加实际执行负责人
            //保存工单
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待接单
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_DJD.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DJD);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/takingDefectOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/takingDefectOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * reportDefectOrderFlow:整改工单-汇报
     * @param defectOrder
     * @param processStatus
     * @param description 工单汇报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage reportDefectOrderFlow(DefectOrderVo defectOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    //根据是否挂起，判断是申请挂起还是汇报
                    if (!defectOrder.getSuspension()) {
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REPORT_PASS, true);
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_PASS, false);
                        variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DYS);
                        defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DYS);//工单状态更新到验收待验收
                        variables.put(DefectCommon.DEFECT_ORDER_PERFORMREPORTING_USER, defectOrder.getAssignPersonId());//设置验收人为分派人
                        message="整改工单-执行汇报成功";
                    }else {
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_PASS, true);
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REPORT_PASS, false);
                        variables.put(DefectCommon.DEFECT_ORDER_SUSPENDAPPLY_USER, defectOrder.getAssignPersonId());
                        variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_SQGQ);
                        defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_SQGQ);//工单状态更新到申请挂起
                        message="整改工单-申请挂起成功";
                    }
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_REPORT_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_PERFORMREPORTING_USER, defectOrder.getReportId());
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_REPORT_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REPORT_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DFP);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DFP);//工单状态更新到待分派
                    message="驳回消缺工单，重新分派成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            defectOrder.setStatusDate(new Date());//状态日期
            //保存工单
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待汇报
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_DHB.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DHB);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/reportDefectOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/reportDefectOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * applySuspendReportDefectOrderFlow:整改工单-申请挂起变更到驳回重新执行汇报/维修商挂起到待验收/挂起
     * @param defectOrder
     * @param processStatus
     * @param description 整改工单-申请挂起驳回重新执行汇报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage applySuspendReportDefectOrderFlow(DefectOrderVo defectOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    //根据挂起类型，判断是挂起还是到确认验收
                    if (Common.GYSWX.equals(defectOrder.getSuspensionType())) {
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS, true);
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_PASS, false);
                        variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DYS);
                        variables.put(DefectCommon.DEFECT_ORDER_PERFORMREPORTING_USER, defectOrder.getReportId());
                        defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DYS);//工单状态更新到待验收
                        message="消缺工单申请挂起到待验收成功";
                    }else {
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_PASS, true);
                        variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS, false);
                        variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_GQ);
                        variables.put(DefectCommon.DEFECT_ORDER_SUSPEND_USER, defectOrder.getAssignPersonId());
                        defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_GQ);//工单状态更新到挂起
                        message="消缺工单申请挂起到挂起成功";
                    }
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DHB);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    message="驳回消缺工单，重新执行汇报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            defectOrder.setStatusDate(new Date());//状态日期
            //保存工单
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成申请挂起
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_SQGQ.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_SQGQ);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/applySuspendRejectReportDefectOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/applySuspendRejectReportDefectOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * confirmSuspendDefectOrderFlow:整改工单-确认挂起/驳回到待分派/驳回到待汇报/待验收
     * @param defectOrder
     * @param processStatus
     * @param description 整改工单-确认挂起说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage confirmSuspendDefectOrderFlow(DefectOrderVo defectOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_ACCEPT_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_REPORT_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DYS);
                    variables.put(DefectCommon.DEFECT_ORDER_PERFORMREPORTING_USER, defectOrder.getReportId());
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DYS);//工单状态更新到待验收
                    message="消缺工单挂起到待验收成功";
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    //驳回到执行汇报
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_REPORT_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_ACCEPT_PASS, false);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DHB);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    message="驳回消缺工单挂起，重新执行汇报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_ASSIGN_PASS, false);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            defectOrder.setStatusDate(new Date());//状态日期
            //保存工单
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成挂起
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_GQ.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_GQ);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/confirmSuspendDefectOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/confirmSuspendDefectOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * acceptDefectOrderFlow:整改工单-验收/驳回重新汇报
     * @param defectOrder
     * @param processStatus
     * @param description 整改工单-验收说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage acceptDefectOrderFlow(DefectOrderVo defectOrder,String processStatus,String description,String userId,String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message="";
            switch (processStatus){
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意分派，流程走下一步
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_ACCEPT_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_ACCEPT_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_GB);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_GB);//工单状态更新到关闭
                    message="消缺工单验收成功";
                } break;
                case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
                    //驳回到执行汇报
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_REJECT_ACCEPT_PASS, true);
                    variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_ACCEPT_PASS, false);
                    variables.put("status", DefectCommon.DEFECT_ORDER_STATUS_DHB);
                    defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DHB);//工单状态更新到待汇报
                    message="驳回消缺工单验收，重新执行汇报成功";
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectOrder.getId(),processStatus,description));
            }
            variables.put("userId", userId);
            variables.put(DefectCommon.DEFECT_ORDER_ACTIVITY_SUSPEND_ASSIGN_PASS, false);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(defectOrder.getProcessInstanceId(), variables);
            defectOrder.setStatusDate(new Date());//状态日期
            //保存工单
            defectOrder=defectOrderClient.saveDefectOrder(defectOrder);
            return EnerbosMessage.createSuccessMsg(defectOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待验收
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectOrder.getId()) &&
                    !DefectCommon.DEFECT_ORDER_STATUS_DYS.equals(defectOrder.getStatus())) {
                defectOrder.setStatus(DefectCommon.DEFECT_ORDER_STATUS_DYS);
                defectOrderClient.saveDefectOrder(defectOrder);
            }
            logger.error("-----/eam/open/defectOrderFlow/acceptDefectOrderFlow ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/defectOrderFlow/acceptDefectOrderFlow----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
}