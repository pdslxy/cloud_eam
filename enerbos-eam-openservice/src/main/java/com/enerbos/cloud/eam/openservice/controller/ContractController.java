package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.ContractClient;
import com.enerbos.cloud.eam.client.ContractCommonClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.ContractCommon;
import com.enerbos.cloud.eam.vo.ContractForFilterVo;
import com.enerbos.cloud.eam.vo.ContractForSaveVo;
import com.enerbos.cloud.eam.vo.ContractVo;
import com.enerbos.cloud.eam.vo.WorkFlowImpleRecordVo;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/4
 * @Description
 */
@RestController
@Api(description = "合同(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class ContractController {
    private static Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private ContractClient contractClient;

    @Autowired
    private ContractCommonClient contractCommonClient;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private OrgClient orgClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @ApiOperation(value = "分页查询合同信息", response = ContractVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/findPage", method = RequestMethod.POST)
    public EnerbosMessage findPage(@RequestBody ContractForFilterVo contractVoForFilter, Principal user) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if (contractVoForFilter.getCollect()) {
                contractVoForFilter.setCollectPersonId(personId);
            }
            EnerbosPage<ContractVo> pageInfo = contractClient.findContractList(contractVoForFilter);
            if (pageInfo != null & pageInfo.getList() != null) {
                List<ContractVo> contractVoList = pageInfo.getList();
                for (ContractVo contractVo : contractVoList) {
                    //从域中查询类型和状态
                    FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(ContractCommon.CONTRACT_TYPE, contractVo.getContractType(), contractVo.getSiteId(), contractVo.getOrgId(), "EAM");
                    if (typeDomain != null) contractVo.setTypeDsr(typeDomain.getDescription());
                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(ContractCommon.CONTRACT_STATUS, contractVo.getStatus(), contractVo.getSiteId(), contractVo.getOrgId(), "EAM");
                    if (statusDomain != null) contractVo.setStatusDsr(statusDomain.getDescription());
                    SiteVoForDetail site = siteClient.findById(contractVo.getSiteId());
                    if (site != null) contractVo.setSite(site.getName());
                    OrgVoForDetail org = orgClient.findById(contractVo.getOrgId());
                    if (org != null) contractVo.setOrg(org.getName());
                    //设置收藏
                    boolean isCollect = contractCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(contractVo.getId(), ContractCommon.CONTRACT, Common.EAM_PROD_IDS[0], personId);
                    contractVo.setCollect(isCollect);
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "合同列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----findPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除合同
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除合同", response = Array.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/deleteByIds", method = RequestMethod.POST)
    public EnerbosMessage deleteByIds(@ApiParam(value = "需要删除合同ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String[] ids,
                                      @ApiParam(value = "登录用户Id", required = true) @RequestParam(value = "userId", required = true) String userId) {
        try {
            String str = contractClient.deleteByIds(ids, userId);
            if (str.equals("success")) {
                return EnerbosMessage.createSuccessMsg(str, "删除合同成功", "");
            } else {
                return EnerbosMessage.createErrorMsg("", str, "");
            }
        } catch (Exception e) {
            logger.debug("-------/contract/findPage--------------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "新增或更新合同", response = ContractVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/saveOrUpdate", method = RequestMethod.POST)
    public EnerbosMessage saveOrUpdate(@RequestBody ContractForSaveVo contractForSaveVo) {
        try {
            ContractVo contractVo = contractClient.saveOrUpdate(contractForSaveVo);
            return EnerbosMessage.createSuccessMsg(contractVo, "新增或更新合同成功", "");
        } catch (Exception e) {
            logger.error("-----saveOrUpdate ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * findPointAndTermById:根据ID查询合同
     *
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询合同", response = ContractVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/findContractVoById", method = RequestMethod.GET)
    public EnerbosMessage findContractVoById(@ApiParam(value = "合同id", required = true) @RequestParam("id") String id) {
        try {
            ContractVo contractVo = contractClient.findContractVoById(id);
            if (contractVo != null) {
                //从域中查询类型和状态
                FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(ContractCommon.CONTRACT_TYPE, contractVo.getContractType(), contractVo.getSiteId(), contractVo.getOrgId(), "EAM");
                if (typeDomain != null) contractVo.setTypeDsr(typeDomain.getDescription());

                FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(ContractCommon.CONTRACT_STATUS, contractVo.getStatus(), contractVo.getSiteId(), contractVo.getOrgId(), "EAM");
                if (statusDomain != null) contractVo.setStatusDsr(statusDomain.getDescription());
                //查询人员组织和站点
                SiteVoForDetail site = siteClient.findById(contractVo.getSiteId());
                if (site != null) contractVo.setSite(site.getName());
                OrgVoForDetail org = orgClient.findById(contractVo.getOrgId());
                if (org != null) contractVo.setOrg(org.getName());

                if (StringUtils.hasLength(contractVo.getConfirmUserId())) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(contractVo.getConfirmUserId());
                    if (person != null) contractVo.setConfirmUser(person.getName());
                }
                if (StringUtils.hasLength(contractVo.getPropertyManagerId())) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(contractVo.getPropertyManagerId());
                    if (person != null) {
                        contractVo.setPropertyManager(person.getName());
                        contractVo.setPropertyManagerPhone(person.getMobile());
                    }
                }
                //插入执行记录
                String processInstanceId = contractVo.getProcessInstanceId();
                if (StringUtils.hasLength(processInstanceId)) {
                    contractVo.setEamImpleRecordVoVoList(getExecution(processInstanceId));
                }
            }
            return EnerbosMessage.createSuccessMsg(contractVo, "根据ID查询合同成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/contract/findContractVoById ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * updateContractStatusList:批量修改合同状态
     *
     * @param ids    合同ID数组{@link java.util.List<String>}
     * @param status 合同状态
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改合同状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/updateContractStatusList", method = RequestMethod.POST)
    public EnerbosMessage updateContractStatusList(@ApiParam(value = "合同id", required = true) @RequestParam("ids") List<String> ids,
                                                   @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/Contract/updateContractStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status);
            ids.stream()
                    .filter(id -> id != null && id != "")
                    .map(id -> contractClient.findContractVoById(id))
                    .filter(contractVo -> contractVo != null)
                    .forEach(contractVo -> {
                        contractVo.setStatus(status);
                        ContractForSaveVo contractForSaveVo = new ContractForSaveVo();
                        BeanUtils.copyProperties(contractVo, contractForSaveVo);
                        contractClient.saveOrUpdate(contractForSaveVo);
                    });
            return EnerbosMessage.createSuccessMsg(true, "批量修改合同状态成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/contract/updateContractStatusList ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * getExecution:根据流程实例ID查询执行记录
     *
     * @param processInstanceId 流程实例ID
     * @return List<MaintenanceWorkOrderImpleRecordVo> 执行记录VO集合
     */
    private List<WorkFlowImpleRecordVo> getExecution(String processInstanceId) {
        List<WorkFlowImpleRecordVo> workOrderImpleRecordVoList = new ArrayList<>();
        List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
        if (historicTaskVoList != null && null == historicTaskVoList.get(historicTaskVoList.size() - 1).getEndTime()) {
            historicTaskVoList.add(0, historicTaskVoList.remove(historicTaskVoList.size() - 1));
        }
        WorkFlowImpleRecordVo maintenanceWorkOrderImpleRecordVo = null;
        if (null != historicTaskVoList && historicTaskVoList.size() > 0) {
            for (HistoricTaskVo historicTaskVo : historicTaskVoList) {
                maintenanceWorkOrderImpleRecordVo = new WorkFlowImpleRecordVo();
                maintenanceWorkOrderImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
                maintenanceWorkOrderImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
                maintenanceWorkOrderImpleRecordVo.setName(historicTaskVo.getName());
                if (null != historicTaskVo.getAssignee()) {
                    String[] ids = historicTaskVo.getAssignee().split(",");
                    String personName = "";
                    if (null != ids && !"".equals(ids)) {
                        for (String id : ids) {
                            personName += personAndUserClient.findByPersonId(id).getName() + ",";
                        }
                        personName.substring(0, personName.length() - 1);
                    }
                    maintenanceWorkOrderImpleRecordVo.setPersonName(personName);
                }
                maintenanceWorkOrderImpleRecordVo.setDurationInMillis(historicTaskVo.getDurationInMillis());
                maintenanceWorkOrderImpleRecordVo.setDescription(historicTaskVo.getDescription());
                workOrderImpleRecordVoList.add(maintenanceWorkOrderImpleRecordVo);
            }
        }
        return workOrderImpleRecordVoList;
    }

    //获取错误码
    private String getErrorStatusCode(Exception e) {
        String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
        String statusCode = "";
        try {
            JSONObject jsonMessage = JSONObject.parseObject(message);
            if (jsonMessage != null) {
                statusCode = jsonMessage.get("status").toString();
            }
        } catch (Exception jsonException) {
            logger.error("-----/eam/open/workorder/findPageWorkOrderList----", jsonException);
        }
        return statusCode;
    }
}
