package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.PatrolOrderClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.PatrolOrderCommon;
import com.enerbos.cloud.eam.openservice.service.PushService;
import com.enerbos.cloud.eam.vo.PatrolOrderForWorkFlowVo;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.wfs.client.ProcessFormClient;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import com.enerbos.cloud.wfs.vo.TaskForFilterVo;
import com.enerbos.cloud.wfs.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/17
 * @Description
 */
@RestController
@Api(description = "巡检工单流程管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolOrderFlowController {

    private Logger logger = LoggerFactory.getLogger(PatrolOrderFlowController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Resource
    protected PatrolOrderClient patrolOrderClient;

    @Resource
    protected WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private ProcessFormClient processFormClient;

    @Autowired
    private UserGroupDomainColler userGroupDomainColler;

    @Autowired
    private PushService pushService;

    /**
     * 巡检工单-发送流程
     *
     * @param id
     * @param description
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "巡检工单-发送流程", response = String.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrderFlow/commit", method = RequestMethod.POST)
    public EnerbosMessage commitWorkOrderFlow(@ApiParam(value = "巡检工单id", required = true) @RequestParam("id") String id,
                                              @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name = "processStatus", required = true) String processStatus,
                                              @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description,
                                              @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,
                                              @ApiParam(value = "人员ID", required = true) @RequestParam("personId") String personId,
                                              Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/patrolOrderFlow/commit, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, processStatus, description, user);


            PatrolOrderForWorkFlowVo patrolOrder = patrolOrderClient.findPatrolOrderWorkFlowById(id);
            EnerbosMessage message;
            if (patrolOrder == null) {
                return EnerbosMessage.createErrorMsg("", "巡检工单不存在", "");
            }

            SiteVoForDetail site = siteClient.findById(siteId);
            if (site == null || null == site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo = new TaskForFilterVo();
            taskForFilterVo.setUserId(personId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(patrolOrder.getProcessInstanceId());
            List<TaskVo> page = processTaskClient.findTasks(taskForFilterVo).getList();
            if (StringUtils.isNotBlank(patrolOrder.getProcessInstanceId()) && (null == page || page.size() < 1)) {
                return EnerbosMessage.createSuccessMsg("401", "无权操作此工单", "");
            }
            if (!PatrolOrderCommon.STATUS_DTB.equals(patrolOrder.getStatus()) && !patrolOrder.getStatus().equals(page.get(0).getOrderStatus())) {
                return EnerbosMessage.createSuccessMsg("401", "工单状态和流程状态不一致", "");
            }
            switch (patrolOrder.getStatus()) {
                //待提报分支
                case PatrolOrderCommon.STATUS_DTB: {
                    message = commitWorkOrder(patrolOrder, description, personId, user.getName());
                }
                break;
                //待分派分支
                case PatrolOrderCommon.STATUS_DFP: {
                    message = assignWorkOrderWorkFlow(patrolOrder, processStatus, description, personId, user.getName());
                }
                break;
                //待接单分支
                case PatrolOrderCommon.STATUS_DJD: {
                    message = takingWorkOrderFlow(patrolOrder, processStatus, description, personId, user.getName());
                }
                break;
                //待汇报分支
                case PatrolOrderCommon.STATUS_DHB: {
                    message = reportWorkOrderFlow(patrolOrder, processStatus, description, personId, user.getName());
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", patrolOrder.getStatus()));
            }
            return message;
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolOrderFlow/commit ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/patrolOrderFlow/commit----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * commitWorkOrderWorkFlow:巡检工单，流程启动、提报
     *
     * @param patrolOrder
     * @param description 巡检工单提报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage commitWorkOrder(PatrolOrderForWorkFlowVo patrolOrder, String description, String userId, String loginName) {
        try {
            if (!userId.equals(patrolOrder.getCreatePersonId())) {
                return EnerbosMessage.createErrorMsg("", "不是提报人，不能提报！", "");
            }
            if (StringUtils.isEmpty(patrolOrder.getSiteId())) {
                return EnerbosMessage.createErrorMsg("", "未知站点", "");
            }
            if (patrolOrder.getProcessInstanceId() == null || "".equals(patrolOrder.getProcessInstanceId())) {
                //启动流程
                //设置流程变量
                Map<String, Object> variables = new HashMap<String, Object>();
                //业务主键
                String businessKey = patrolOrder.getId();
                //流程key,key为维保固定前缀+站点code
                String code = "";
                SiteVoForDetail site = siteClient.findById(patrolOrder.getSiteId());
                if (site != null) {
                    code = site.getCode();
                }
                String processKey = PatrolOrderCommon.WFS_PROCESS_KEY + code;
                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(businessKey);
                processVo.setProcessKey(processKey);
                processVo.setUserId(userId);
                variables.put(PatrolOrderCommon.SUBMIT_USER, userId);
                variables.put("userId", userId);
                variables.put(PatrolOrderCommon.ORDER_NUM, patrolOrder.getPatrolOrderNum());
                variables.put(PatrolOrderCommon.ORDER_DESCRIPTION, patrolOrder.getDescription());
                logger.debug("/eam/open/patrolOrder/commit, processKey: {}", processKey);
                processVo = workflowClient.startProcess(variables, processVo);

                if (null == processVo || "".equals(processVo.getProcessInstanceId())) {
                    return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
                }
                //提报，修改基本字段保存
                patrolOrder.setProcessInstanceId(processVo.getProcessInstanceId());
                patrolOrder.setReportDate(new Date());
                patrolOrder = patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            }
            //查询分派组签收人员
            Map<String, Object> variables = new HashMap<String, Object>();
            List<String> userList = new ArrayList<>();
            List<String> userListLoginName = new ArrayList<>();
            List<PersonAndUserVoForDetail> personList = userGroupDomainColler.findUserByDomainValueORDomainNums(patrolOrder.getType(),
                    PatrolOrderCommon.PATROL_TYPE, patrolOrder.getOrgId(),
                    patrolOrder.getSiteId(), Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            personList.forEach(person -> {
                userList.add(person.getPersonId());
                userListLoginName.add(person.getLoginName());
            });
            if (null == userList || userList.size() <= 0) {
                return EnerbosMessage.createSuccessMsg("", "巡检工单分派组下没有人员,请联系管理员添加!!", "");
            }
            variables.put(PatrolOrderCommon.ASSIGN_USER, StringUtils.join(userList, ","));
            variables.put("description", description);
            variables.put("status", PatrolOrderCommon.STATUS_DFP);
            variables.put(PatrolOrderCommon.ACTIVITY_REJECT_ASSIGN_PASS, true);
            variables.put("userId", userId);
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(patrolOrder.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            //提报，修改基本字段保存
            patrolOrder.setStatus(PatrolOrderCommon.STATUS_DFP);
            patrolOrder.setStatusdate(new Date());//状态日期
            patrolOrder = patrolOrderClient.savePatrolOrderFlow(patrolOrder);

            //推送消息给App端
            String pushTitle = "请分派巡检工单,编号:" + patrolOrder.getPatrolOrderNum();
            String pushContent = "编号:" + patrolOrder.getPatrolOrderNum() + ",描述:" + patrolOrder.getDescription();
            String buildUpContent = "请分派巡检工单,编号:" + patrolOrder.getPatrolOrderNum() + ",描述:" + patrolOrder.getDescription();
            pushService.sendPush(userListLoginName.toArray(new String[0]), pushTitle, pushContent, getExtraMap(patrolOrder), buildUpContent);
            return EnerbosMessage.createSuccessMsg(patrolOrder.getStatus(), "提报巡检工单成功", "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(patrolOrder.getId()) &&
                    !PatrolOrderCommon.STATUS_DTB.equals(patrolOrder.getStatus())) {
                patrolOrder.setStatus(PatrolOrderCommon.STATUS_DTB);
                patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            }
            logger.error("-----/eam/open/workorder/commit ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/commit----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * assignWorkOrderWorkFlow:巡检工单-分派/驳回
     *
     * @param patrolOrder
     * @param processStatus
     * @param description   分派工单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage assignWorkOrderWorkFlow(PatrolOrderForWorkFlowVo patrolOrder, String processStatus, String description, String userId, String loginName) {
        try {
            List<String> userList = new ArrayList<>();
            List<String> userListLoginName = new ArrayList<>();
            List<PersonAndUserVoForDetail> personList = userGroupDomainColler.findUserByDomainValueORDomainNums(patrolOrder.getType(),
                    PatrolOrderCommon.PATROL_TYPE, patrolOrder.getOrgId(),
                    patrolOrder.getSiteId(), Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            personList.forEach(person -> {
                userList.add(person.getPersonId());
                userListLoginName.add(person.getLoginName());
            });
            if (null == userList || userList.size() <= 0) {
                return EnerbosMessage.createSuccessMsg("", "巡检工单分派组下没有人员,请联系管理员添加!!", "");
            }
            if (userList.stream().noneMatch(userId::equals)) {
                return EnerbosMessage.createErrorMsg("401", "无权限分派！", "");
            }
            if (StringUtils.isNotBlank(patrolOrder.getPatrolPlanNum()) && PatrolOrderCommon.PROCESS_STATUS_REJECTS.equals(processStatus)) {
                return EnerbosMessage.createErrorMsg("401", "巡检计划生成工单，不允许驳回重新提报", "");
            }

            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case PatrolOrderCommon.PROCESS_STATUS_PASS: {
                    //同意分派，流程走下一步
                    //委托办理人
                    List<String> executors = new ArrayList<>();
                    if (patrolOrder.getExcutePersonId() != null && !"".equals(patrolOrder.getExcutePersonId())) {
                        Collections.addAll(executors, patrolOrder.getExcutePersonId().split(","));
                    } else {
                        return EnerbosMessage.createErrorMsg(null, "执行人不能为空", "");
                    }

                    for (String executorId : executors) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(executorId);
                        Assert.notNull(personVo, "未知委托执行人！");
                    }
                    //TODO 预留物料
                    variables.put(PatrolOrderCommon.RECEIVE_USER, StringUtils.join(executors, ","));
                    variables.put(PatrolOrderCommon.ACTIVITY_ASSIGN_PASS, true);
                    variables.put(PatrolOrderCommon.ACTIVITY_CANCEL_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_REJECT_ASSIGN_PASS, false);
                    variables.put("status", PatrolOrderCommon.STATUS_DJD);
                    patrolOrder.setStatus(PatrolOrderCommon.STATUS_DJD);//工单状态更新到待接单
                    message = "巡检工单-分派成功";
                }
                break;
                case PatrolOrderCommon.PROCESS_STATUS_REJECTS: {
                    variables.put(PatrolOrderCommon.ACTIVITY_REJECT_ASSIGN_PASS, true);
                    variables.put(PatrolOrderCommon.ACTIVITY_CANCEL_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_ASSIGN_PASS, false);
                    variables.put("status", PatrolOrderCommon.STATUS_DTB);
                    variables.put(PatrolOrderCommon.SUBMIT_USER, userId);
                    patrolOrder.setStatus(PatrolOrderCommon.STATUS_DTB);//工单状态更新
                    message = "驳回巡检工单，重新提报成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", patrolOrder.getId(), processStatus, description));
            }
            variables.put(PatrolOrderCommon.ASSIGN_USER, userId);
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(patrolOrder.getProcessInstanceId(), variables);
            patrolOrder.setStatusdate(new Date());//状态日期
            if (PatrolOrderCommon.PROCESS_STATUS_PASS.equals(processStatus)) {
                patrolOrder.setAssignPersonId(userId);//设定自己为分派人
            } else {
                patrolOrder.setAssignPersonId(null);
            }
            //保存工单
            patrolOrder = patrolOrderClient.savePatrolOrderFlow(patrolOrder);

            //推送消息给App端
            String pushTitle = "请执行巡检工单,编号:" + patrolOrder.getPatrolOrderNum();
            String pushContent = "编号:" + patrolOrder.getPatrolOrderNum() + ",描述:" + patrolOrder.getDescription();
            String buildUpContent = "请执行巡检工单,编号:" + patrolOrder.getPatrolOrderNum() + ",描述:" + patrolOrder.getDescription();
            pushService.sendPush(userListLoginName.toArray(new String[0]), pushTitle, pushContent, getExtraMap(patrolOrder), buildUpContent);

            return EnerbosMessage.createSuccessMsg(patrolOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待分派
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(patrolOrder.getId()) &&
                    !PatrolOrderCommon.STATUS_DFP.equals(patrolOrder.getStatus())) {
                patrolOrder.setStatus(PatrolOrderCommon.STATUS_DFP);
                patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            }
            logger.error("-----/eam/open/workorder/assignWorkOrderWorkFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/assignWorkOrderWorkFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * takingWorkOrderFlow:巡检工单-接单
     *
     * @param patrolOrder
     * @param processStatus
     * @param description   巡检工单-接单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage takingWorkOrderFlow(PatrolOrderForWorkFlowVo patrolOrder, String processStatus, String description, String userId, String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case PatrolOrderCommon.PROCESS_STATUS_PASS: {
                    //同意接单，流程走下一步
                    variables.put(PatrolOrderCommon.ACTIVITY_TAKING_PASS, true);
                    variables.put(PatrolOrderCommon.ACTIVITY_REJECT_TAKING_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_CANCEL_PASS, false);
                    variables.put(PatrolOrderCommon.RECEIVE_USER, userId);
                    variables.put("status", PatrolOrderCommon.STATUS_DHB);
                    patrolOrder.setStatus(PatrolOrderCommon.STATUS_DHB);//工单状态更新到待汇报
                    //指定汇报人为接单人
                    variables.put(PatrolOrderCommon.REPORT_USER, userId);
                    message = "巡检工单-接单成功";
                }
                break;
                case PatrolOrderCommon.PROCESS_STATUS_REJECTS: {
                    variables.put(PatrolOrderCommon.ACTIVITY_REJECT_TAKING_PASS, true);
                    variables.put(PatrolOrderCommon.ACTIVITY_TAKING_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", PatrolOrderCommon.STATUS_DFP);
                    patrolOrder.setStatus(PatrolOrderCommon.STATUS_DFP);//工单状态更新到待分派
                    message = "驳回巡检工单，重新分派成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", patrolOrder.getId(), processStatus, description));
            }
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(patrolOrder.getProcessInstanceId(), variables);
            patrolOrder.setStatusdate(new Date());//状态日期
            //设置实际执行人(实际执行人改为手动修改)
            /*if (PatrolOrderCommon.PROCESS_STATUS_PASS.equals(processStatus)) {
                patrolOrder.setActualExecutorId(userId);
                PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(userId);
                patrolOrder.setActualWorkGroup(person.getWorkgroup());
            }*/
            //保存工单
            patrolOrder = patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            return EnerbosMessage.createSuccessMsg(patrolOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待接单
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(patrolOrder.getId()) &&
                    !PatrolOrderCommon.STATUS_DJD.equals(patrolOrder.getStatus())) {
                patrolOrder.setStatus(PatrolOrderCommon.STATUS_DJD);
                patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            }
            logger.error("-----/eam/open/workorder/takingWorkOrderFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/takingWorkOrderFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * reportWorkOrderFlow:巡检工单-汇报
     *
     * @param patrolOrder
     * @param processStatus
     * @param description   工单汇报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage reportWorkOrderFlow(PatrolOrderForWorkFlowVo patrolOrder, String processStatus, String description, String userId, String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case PatrolOrderCommon.PROCESS_STATUS_PASS: {
                    //同意分派，流程走下一步
                    variables.put(PatrolOrderCommon.ACTIVITY_REPORT_PASS, true);
                    variables.put(PatrolOrderCommon.ACTIVITY_APPLY_SUSPEND_PASS, false);
                    variables.put("status", PatrolOrderCommon.STATUS_GB);
                    patrolOrder.setStatus(PatrolOrderCommon.STATUS_GB);//工单状态更新到关闭
                    variables.put(PatrolOrderCommon.PERFORMREPORTING_USER, patrolOrder.getAssignPersonId());//设置验收人为分派人
                    message = "巡检工单-执行汇报成功";

                    variables.put(PatrolOrderCommon.ACTIVITY_REJECT_REPORT_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_CANCEL_PASS, false);
                    variables.put(PatrolOrderCommon.PERFORMREPORTING_USER, patrolOrder.getCreatePersonId());

                }
                break;
                case PatrolOrderCommon.PROCESS_STATUS_REJECTS: {
                    variables.put(PatrolOrderCommon.ACTIVITY_REJECT_REPORT_PASS, true);
                    variables.put(PatrolOrderCommon.ACTIVITY_REPORT_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_APPLY_SUSPEND_PASS, false);
                    variables.put(PatrolOrderCommon.ACTIVITY_CANCEL_PASS, false);
                    variables.put("status", PatrolOrderCommon.STATUS_DFP);
                    patrolOrder.setStatus(PatrolOrderCommon.STATUS_DFP);//工单状态更新到待分派
                    message = "驳回巡检工单，重新分派成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", patrolOrder.getId(), processStatus, description));
            }
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(patrolOrder.getProcessInstanceId(), variables);
            patrolOrder.setStatusdate(new Date());//状态日期
            //保存工单
            patrolOrder = patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            return EnerbosMessage.createSuccessMsg(patrolOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待汇报
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(patrolOrder.getId()) &&
                    !PatrolOrderCommon.STATUS_DHB.equals(patrolOrder.getStatus())) {
                patrolOrder.setStatus(PatrolOrderCommon.STATUS_DHB);
                patrolOrderClient.savePatrolOrderFlow(patrolOrder);
            }
            logger.error("-----/eam/open/workorder/reportWorkOrderFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/reportWorkOrderFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    private Map getExtraMap(PatrolOrderForWorkFlowVo patrolOrder) {
        Map extras = new HashMap<>();
        extras.put("getBusinessDataByCurrentUser", "true");
        extras.put("workorderid", patrolOrder.getId());
        extras.put("notificationType", PatrolOrderCommon.WFS_PROCESS_KEY);
        return extras;
    }
}
