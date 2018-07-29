package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.ContractClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.ContractCommon;
import com.enerbos.cloud.eam.vo.ContractForWorkFlowVo;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/6
 * @Description
 */
@RestController
@Api(description = "合同流程管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class ContractFlowController {

    private Logger logger = LoggerFactory.getLogger(ContractFlowController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Resource
    protected ContractClient contractClient;

    @Resource
    protected WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private ProcessFormClient processFormClient;

    @Autowired
    private UserGroupDomainColler userGroupDomainColler;

    /**
     * 合同-发送流程
     *
     * @param id
     * @param description
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "合同-发送流程", response = String.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contractFlow/commit", method = RequestMethod.POST)
    public EnerbosMessage commitContractFlow(@ApiParam(value = "合同id", required = true) @RequestParam("id") String id,
                                             @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name = "processStatus", required = true) String processStatus,
                                             @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description,
                                             @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,
                                             @ApiParam(value = "人员ID", required = true) @RequestParam("personId") String personId,
                                             Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/contractFlow/commit, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, processStatus, description, user);


            ContractForWorkFlowVo contract = contractClient.findContractWorkFlowById(id);
            EnerbosMessage message;
            if (contract == null) {
                return EnerbosMessage.createErrorMsg("", "合同不存在", "");
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
            taskForFilterVo.setProcessInstanceId(contract.getProcessInstanceId());
            List<TaskVo> page = processTaskClient.findTasks(taskForFilterVo).getList();
            if (StringUtils.isNotBlank(contract.getProcessInstanceId()) && (null == page || page.size() < 1)) {
                return EnerbosMessage.createSuccessMsg("401", "无权操作此合同", "");
            }
            if (!ContractCommon.STATUS_XZ.equals(contract.getStatus()) && !contract.getStatus().equals(page.get(0).getOrderStatus())) {
                return EnerbosMessage.createSuccessMsg("401", "合同状态和流程状态不一致", "");
            }
            switch (contract.getStatus()) {
                //新增分支
                case ContractCommon.STATUS_XZ: {
                    message = commitContract(contract, description, personId, user.getName());
                }
                break;
                //执行分支
                case ContractCommon.STATUS_ZX: {
                    message = excuteContractFlow(contract, processStatus, description, personId, user.getName());
                }
                break;
                //执行分支
                case ContractCommon.STATUS_PJ: {
                    message = confirmContractFlow(contract, processStatus, description, personId, user.getName());
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", contract.getStatus()));
            }
            return message;
        } catch (Exception e) {
            logger.error("-----/eam/open/contractFlow/commit ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/contractFlow/commit----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * commitContractWorkFlow:合同，流程启动、提报
     *
     * @param contract
     * @param description 合同说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage commitContract(ContractForWorkFlowVo contract, String description, String userId, String loginName) {
        try {
            if (!userId.equals(contract.getPropertyManagerId())) {
                return EnerbosMessage.createErrorMsg("", "不是物业单位负责人，不能发送流程！", "");
            }
            if (StringUtils.isEmpty(contract.getSiteId())) {
                return EnerbosMessage.createErrorMsg("", "未知站点", "");
            }
            if (contract.getProcessInstanceId() == null || "".equals(contract.getProcessInstanceId())) {
                //启动流程
                //设置流程变量
                Map<String, Object> variables = new HashMap<String, Object>();
                //业务主键
                String businessKey = contract.getId();
                //流程key,key为维保固定前缀+站点code
                String code = "";
                SiteVoForDetail site = siteClient.findById(contract.getSiteId());
                if (site != null) {
                    code = site.getCode();
                }
                String processKey = ContractCommon.WFS_PROCESS_KEY + code;
                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(businessKey);
                processVo.setProcessKey(processKey);
                processVo.setUserId(userId);
                variables.put(ContractCommon.SUBMIT_USER, userId);
                variables.put("userId", userId);
                variables.put(Common.ORDER_NUM, contract.getContractNum());
                variables.put(Common.ORDER_DESCRIPTION, contract.getDescription());
                logger.debug("/eam/open/contract/commit, processKey: {}", processKey);
                processVo = workflowClient.startProcess(variables, processVo);

                if (null == processVo || "".equals(processVo.getProcessInstanceId())) {
                    return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
                }
                //提报，修改基本字段保存
                contract.setProcessInstanceId(processVo.getProcessInstanceId());
                contract = contractClient.saveContractFlow(contract);
            }
            //查询新增组签收人员
            Map<String, Object> variables = new HashMap<String, Object>();
            /*List<String> userList = new ArrayList<>();
            List<PersonAndUserVoForDetail> personList = userGroupDomainColler.findUserByDomainValueORDomainNums(contract.getContractType(),
                    ContractCommon.CONTRACT_TYPE, contract.getOrgId(),
                    contract.getSiteId(), Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            personList.forEach(person -> userList.add(person.getPersonId()));
            if (null == userList || userList.size() <= 0) {
                return EnerbosMessage.createSuccessMsg("", "合同新增组下没有人员,请联系管理员添加!!", "");
            }*/
            variables.put(ContractCommon.EXCUTE_USER, contract.getPropertyManagerId());
            variables.put("description", description);
            variables.put("status", ContractCommon.STATUS_ZX);
            variables.put(ContractCommon.ACTIVITY_COMMIT_PASS, true);
            variables.put("userId", userId);
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(contract.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            //提报，修改基本字段保存
            contract.setStatus(ContractCommon.STATUS_ZX);
            contract = contractClient.saveContractFlow(contract);
            return EnerbosMessage.createSuccessMsg(contract.getStatus(), "合同提报成功", "");
        } catch (Exception e) {
            //流程提交失败时，将合同状态恢复成新增
            if (StringUtils.isNotEmpty(contract.getId()) &&
                    !ContractCommon.STATUS_XZ.equals(contract.getStatus())) {
                contract.setStatus(ContractCommon.STATUS_XZ);
                contractClient.saveContractFlow(contract);
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
     * reportContractFlow:合同-执行
     *
     * @param contract
     * @param processStatus
     * @param description   合同执行说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage excuteContractFlow(ContractForWorkFlowVo contract, String processStatus, String description, String userId, String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case ContractCommon.PROCESS_STATUS_AGREE: {
                    //同意执行，流程走下一步
                    /*List<String> userList = new ArrayList<>();
                    List<PersonAndUserVoForDetail> personList = userGroupDomainColler.findUserByDomainValueORDomainNums(contract.getContractType(),
                            ContractCommon.CONTRACT_TYPE, contract.getOrgId(),
                            contract.getSiteId(), Common.USERGROUP_ASSOCIATION_TYPE_ALL);
                    personList.forEach(person -> userList.add(person.getPersonId()));
                    if (null == userList || userList.size() <= 0) {
                        return EnerbosMessage.createSuccessMsg("", "合同执行组下没有人员,请联系管理员添加!!", "");
                    }*/
                    variables.put(ContractCommon.CONFIRM_USER, contract.getPropertyManagerId());
                    variables.put(ContractCommon.ACTIVITY_EXCUTE_PASS, true);
                    variables.put("status", ContractCommon.STATUS_PJ);
                    contract.setStatus(ContractCommon.STATUS_PJ);//合同状态更新到评价中
                    message = "合同-执行成功";
//                    variables.put(ContractCommon.ACTIVITY_CANCEL_PASS, false);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", contract.getId(), processStatus, description));
            }
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(contract.getProcessInstanceId(), variables);
            //保存合同
            contract = contractClient.saveContractFlow(contract);
            return EnerbosMessage.createSuccessMsg(contract.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将合同状态恢复成执行中
            if (StringUtils.isNotEmpty(contract.getId()) &&
                    !ContractCommon.STATUS_ZX.equals(contract.getStatus())) {
                contract.setStatus(ContractCommon.STATUS_ZX);
                contractClient.saveContractFlow(contract);
            }
            logger.error("-----/eam/open/workorder/reportContractFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/reportContractFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * reportContractFlow:合同-执行
     *
     * @param contract
     * @param processStatus
     * @param description   合同执行说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage confirmContractFlow(ContractForWorkFlowVo contract, String processStatus, String description, String userId, String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case ContractCommon.PROCESS_STATUS_AGREE: {
                    //确认评价，流程走下一步
                    variables.put(ContractCommon.ACTIVITY_CANCEL_PASS, true);
                    variables.put("status", ContractCommon.STATUS_WC);
                    contract.setStatus(ContractCommon.STATUS_WC);//合同状态更新到完成
                    message = "合同-确认成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", contract.getId(), processStatus, description));
            }
            variables.put("description", description);
            variables.put("userId", userId);
            processTaskClient.completeByProcessInstanceId(contract.getProcessInstanceId(), variables);
            //保存合同
            contract = contractClient.saveContractFlow(contract);
            return EnerbosMessage.createSuccessMsg(contract.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将合同状态恢复成执行中
            if (StringUtils.isNotEmpty(contract.getId()) &&
                    !ContractCommon.STATUS_PJ.equals(contract.getStatus())) {
                contract.setStatus(ContractCommon.STATUS_PJ);
                contractClient.saveContractFlow(contract);
            }
            logger.error("-----/eam/open/workorder/reportContractFlow ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/workorder/reportContractFlow----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
}
