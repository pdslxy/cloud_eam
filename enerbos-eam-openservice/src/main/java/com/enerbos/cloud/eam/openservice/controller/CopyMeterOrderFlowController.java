package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.DailyCopyMeterClient;
import com.enerbos.cloud.eam.contants.CopyMeterCommon;
import com.enerbos.cloud.eam.openservice.service.PushService;
import com.enerbos.cloud.eam.vo.CopyMeterOrderForWorkFlowVo;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
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
 * @date 2017/12/1
 * @Description
 */
@RestController
@Api(description = "抄表工单流程管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class CopyMeterOrderFlowController {

    private Logger logger = LoggerFactory.getLogger(CopyMeterOrderFlowController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Resource
    protected DailyCopyMeterClient dailyCopyMeterClient;

    @Resource
    protected WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private PushService pushService;

    /**
     * 抄表工单-发送流程
     *
     * @param id
     * @param description
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "抄表工单-发送流程", response = String.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeterOrderFlow/sendProcess", method = RequestMethod.POST)
    public EnerbosMessage commitWorkOrderFlow(@ApiParam(value = "抄表工单id", required = true) @RequestParam("id") String id,
                                              @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name = "processStatus", required = true) String processStatus,
                                              @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description,
                                              @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,
                                              @ApiParam(value = "组织ID", required = true) @RequestParam("orgId") String orgId,
                                              @ApiParam(value = "人员ID", required = true) @RequestParam("personId") String personId,
                                              Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/copyMeterOrderFlow/sendProcess, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, processStatus, description, user);
            CopyMeterOrderForWorkFlowVo copyMeterOrder = dailyCopyMeterClient.findCopyMeterFlowById(id);
            EnerbosMessage message = null;
            if (copyMeterOrder == null) {
                return EnerbosMessage.createErrorMsg("", "抄表工单不存在", "");
            }

            SiteVoForDetail site = siteClient.findById(siteId);
            if (site == null || null == site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }

            if (StringUtils.isNotBlank(copyMeterOrder.getProcessInstanceId()) && !personId.equals(copyMeterOrder.getCopyMeterPerson())) {
                return EnerbosMessage.createSuccessMsg("401", "无权操作此工单", "");
            }
            TaskForFilterVo taskForFilterVo = new TaskForFilterVo();
            taskForFilterVo.setUserId(personId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(copyMeterOrder.getProcessInstanceId());
            List<TaskVo> page = processTaskClient.findTasks(taskForFilterVo).getList();
            if (!CopyMeterCommon.COPY_METER_STATUS_CG.equals(copyMeterOrder.getStatus()) && !CopyMeterCommon.COPY_METER_STATUS_DFP.equals(copyMeterOrder.getStatus()) && !copyMeterOrder.getStatus().equals(page.get(0).getOrderStatus())) {
                return EnerbosMessage.createSuccessMsg("401", "工单状态和流程状态不一致", "");
            }
            switch (copyMeterOrder.getStatus()) {
                //流程开始
                case CopyMeterCommon.COPY_METER_STATUS_CG: {
                    message = startProcess(copyMeterOrder, personId);
                }
                break;
                //待分派分支
                case CopyMeterCommon.COPY_METER_STATUS_DFP: {
                    message = assignWorkOrderWorkFlow(copyMeterOrder, processStatus, description, personId, user.getName());
                }
                break;
                //流程开始
                case CopyMeterCommon.COPY_METER_STATUS_ZX: {
                    message = reportWorkOrderFlow(copyMeterOrder, processStatus, description, personId, user.getName());
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", copyMeterOrder.getStatus()));
            }
            return message;
        } catch (Exception e) {
            logger.error("-----/eam/open/copyMeterOrderFlow/commit ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/copyMeterOrderFlow/commit----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    public EnerbosMessage startProcess(CopyMeterOrderForWorkFlowVo copyMeterOrder, String userId) {
        if (StringUtils.isEmpty(copyMeterOrder.getSiteId())) {
            return EnerbosMessage.createErrorMsg("", "未知站点", "");
        }
        if (copyMeterOrder.getProcessInstanceId() == null || "".equals(copyMeterOrder.getProcessInstanceId())) {
            //启动流程
            //设置流程变量
            Map<String, Object> variables = new HashMap<String, Object>();
            //业务主键
            String businessKey = copyMeterOrder.getId();
            //流程key,key为维保固定前缀+站点code
            String code = "";
            SiteVoForDetail site = siteClient.findById(copyMeterOrder.getSiteId());
            if (site != null) {
                code = site.getCode();
            }
            String processKey = CopyMeterCommon.WFS_PROCESS_KEY + code;
            //String processKey = "meterRecordProcess" + code;
            ProcessVo processVo = new ProcessVo();
            processVo.setBusinessKey(businessKey);
            processVo.setProcessKey(processKey);
            processVo.setUserId(userId);
            if (StringUtils.isNotEmpty(copyMeterOrder.getCopyMeterPerson())) {
                variables.put(CopyMeterCommon.EXCUTE_USER, copyMeterOrder.getCopyMeterPerson());
                variables.put(CopyMeterCommon.BEGIN_EXCUTE, true);
                variables.put(CopyMeterCommon.BEGIN_ASSIGN, false);
            } else {
                variables.put(CopyMeterCommon.ASSIGN_USER, userId);
                variables.put(CopyMeterCommon.BEGIN_EXCUTE, false);
                variables.put(CopyMeterCommon.BEGIN_ASSIGN, true);
            }
            variables.put("userId", userId);
            variables.put("status", CopyMeterCommon.COPY_METER_STATUS_ZX);
            variables.put(CopyMeterCommon.ORDER_NUM, copyMeterOrder.getCopyMeterNum());
            variables.put(CopyMeterCommon.ORDER_DESCRIPTION, copyMeterOrder.getDescription());
            logger.debug("/eam/open/copyMeterOrder/assignWorkOrderWorkFlow, processKey: {}", processKey);

            processVo = workflowClient.startProcess(variables, processVo);

            if (null == processVo || "".equals(processVo.getProcessInstanceId())) {
                return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
            }
            //提报，修改基本字段保存
            copyMeterOrder.setProcessInstanceId(processVo.getProcessInstanceId());
            copyMeterOrder.setStatus(CopyMeterCommon.COPY_METER_STATUS_ZX);
            copyMeterOrder = dailyCopyMeterClient.saveCopyMeterOrderFlow(copyMeterOrder);
        }
        return EnerbosMessage.createSuccessMsg(copyMeterOrder.getStatus(), "流程启动成功", "");
    }

    /**
     * assignWorkOrderWorkFlow:抄表工单-分派/驳回
     *
     * @param copyMeterOrder
     * @param processStatus
     * @param description    分派工单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage assignWorkOrderWorkFlow(CopyMeterOrderForWorkFlowVo copyMeterOrder, String processStatus, String description, String userId, String loginName) {
        try {
            //创建人才有分派权限
            if (!loginName.equals(copyMeterOrder.getCreateUser())) {
                return EnerbosMessage.createErrorMsg("401", "无权限分派！", "");
            }

            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case CopyMeterCommon.PROCESS_STATUS_PASS: {
                    //同意分派，流程走下一步
                    //委托办理人
                    List<String> executors = new ArrayList<>();
                    if (copyMeterOrder.getCopyMeterPerson() != null && !"".equals(copyMeterOrder.getCopyMeterPerson())) {
                        Collections.addAll(executors, copyMeterOrder.getCopyMeterPerson().split(","));
                    } else {
                        return EnerbosMessage.createErrorMsg(null, "抄表人不能为空", "");
                    }

                    for (String executorId : executors) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(executorId);
                        Assert.notNull(personVo, "未知委托执行人！");
                    }
                    //TODO 预留物料
                    variables.put(CopyMeterCommon.EXCUTE_USER, userId);
                    variables.put(CopyMeterCommon.ACTIVITY_ASSIGN_PASS, true);
                    variables.put("status", CopyMeterCommon.COPY_METER_STATUS_ZX);
                    copyMeterOrder.setStatus(CopyMeterCommon.COPY_METER_STATUS_ZX);//工单状态更新到待接单
                    message = "抄表工单-流程启动成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", copyMeterOrder.getId(), processStatus, description));
            }
            variables.put(CopyMeterCommon.ASSIGN_USER, userId);
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(copyMeterOrder.getProcessInstanceId(), variables);
            //保存工单
            copyMeterOrder = dailyCopyMeterClient.saveCopyMeterOrderFlow(copyMeterOrder);

            //推送消息给App端
            String pushTitle = "请执行抄表工单,编号:" + copyMeterOrder.getCopyMeterNum();
            String pushContent = "编号:" + copyMeterOrder.getCopyMeterNum() + ",描述:" + copyMeterOrder.getDescription();
            String buildUpContent = "请执行抄表工单,编号:" + copyMeterOrder.getCopyMeterNum() + ",描述:" + copyMeterOrder.getDescription();
            String[] excutePerson = new String[1];
            excutePerson[0] = copyMeterOrder.getCreateUser();
            pushService.sendPush(excutePerson, pushTitle, pushContent, getExtraMap(copyMeterOrder), buildUpContent);

            return EnerbosMessage.createSuccessMsg(copyMeterOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待分派
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(copyMeterOrder.getId()) &&
                    !CopyMeterCommon.COPY_METER_STATUS_DFP.equals(copyMeterOrder.getStatus())) {
                copyMeterOrder.setStatus(CopyMeterCommon.COPY_METER_STATUS_DFP);
                dailyCopyMeterClient.saveCopyMeterOrderFlow(copyMeterOrder);
            }
            logger.error("-----/eam/open/copyMeterOrder/assignWorkOrderWorkFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/copyMeterOrder/assignWorkOrderWorkFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * reportWorkOrderFlow:抄表工单-汇报
     *
     * @param copyMeterOrder
     * @param processStatus
     * @param description    工单汇报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage reportWorkOrderFlow(CopyMeterOrderForWorkFlowVo copyMeterOrder, String processStatus, String description, String userId, String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            if (StringUtils.isNotBlank(copyMeterOrder.getCopyMeterPlanId()) && CopyMeterCommon.PROCESS_STATUS_REJECTS.equals(processStatus)) {
                return EnerbosMessage.createErrorMsg("401", "抄表计划生成工单，不允许驳回重新分派", "");
            }
            switch (processStatus) {
                case CopyMeterCommon.PROCESS_STATUS_PASS: {
                    //同意分派，流程走下一步
                    variables.put(CopyMeterCommon.ACTIVITY_EXCUTE_PASS, true);
                    variables.put("status", CopyMeterCommon.COPY_METER_STATUS_WC);
                    copyMeterOrder.setStatus(CopyMeterCommon.COPY_METER_STATUS_WC);//工单状态更新到关闭
                    message = "抄表工单-执行汇报成功";
                    variables.put(CopyMeterCommon.ACTIVITY_EXCUTE_REJECT, false);
                }
                break;
                case CopyMeterCommon.PROCESS_STATUS_REJECTS: {
                    variables.put(CopyMeterCommon.ACTIVITY_EXCUTE_REJECT, true);
                    variables.put(CopyMeterCommon.ACTIVITY_EXCUTE_PASS, false);
                    variables.put("status", CopyMeterCommon.COPY_METER_STATUS_DFP);
                    copyMeterOrder.setStatus(CopyMeterCommon.COPY_METER_STATUS_DFP);//工单状态更新到待分派
                    message = "驳回抄表工单，重新分派成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", copyMeterOrder.getId(), processStatus, description));
            }
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(copyMeterOrder.getProcessInstanceId(), variables);
            //保存工单
            copyMeterOrder = dailyCopyMeterClient.saveCopyMeterOrderFlow(copyMeterOrder);
            return EnerbosMessage.createSuccessMsg(copyMeterOrder.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待汇报
            if (StringUtils.isNotEmpty(copyMeterOrder.getId()) &&
                    !CopyMeterCommon.COPY_METER_STATUS_ZX.equals(copyMeterOrder.getStatus())) {
                copyMeterOrder.setStatus(CopyMeterCommon.COPY_METER_STATUS_ZX);
                dailyCopyMeterClient.saveCopyMeterOrderFlow(copyMeterOrder);
            }
            logger.error("-----/eam/open/copyMeterOrder/reportWorkOrderFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/copyMeterOrder/reportWorkOrderFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    private Map getExtraMap(CopyMeterOrderForWorkFlowVo copyMeterOrder) {
        Map extras = new HashMap<>();
        extras.put("getBusinessDataByCurrentUser", "true");
        extras.put("workorderid", copyMeterOrder.getId());
        extras.put("notificationType", CopyMeterCommon.WFS_PROCESS_KEY);
        return extras;
    }
}
