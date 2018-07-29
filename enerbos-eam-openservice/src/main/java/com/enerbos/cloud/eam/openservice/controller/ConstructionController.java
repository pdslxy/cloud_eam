package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.ConstructionCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
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
 * @date 2017年09月09日
 * @Description 施工单接口
 */
@RestController
@Api(description = "施工单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class ConstructionController {

    private Logger logger = LoggerFactory.getLogger(ConstructionController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private OrgClient orgClient;

    @Resource
    private ConstructionClient constructionClient;

    @Resource
    private ConstructionSuperviseClient constructionSuperviseClient;

    @Resource
    private ContractClient contractClient;

    @Resource
    private HeadArchivesClient headArchivesClient;

    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @Resource
    protected WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;

    /**
     * saveConstruction:保存施工单-工单提报
     *
     * @param constructionVo {@link ConstructionVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存施工单,只有状态为新增可以保存", response = ConstructionVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/saveConstruction", method = RequestMethod.POST)
    public EnerbosMessage saveConstruction(@ApiParam(value = "施工单-工单提报VO", required = true) @Valid @RequestBody ConstructionVo constructionVo, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/Construction/saveConstruction, host: [{}:{}], service_id: {}, ConstructionVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), constructionVo);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            ConstructionVo ewo = constructionClient.findConstructionByConstructionNum(constructionVo.getConstructionNum());
            if (StringUtils.isBlank(constructionVo.getId())) {
                if (ewo != null && !"".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "施工单编码重复", "");
                }
            } else {
                if (ewo != null && !constructionVo.getId().equals(ewo.getId())) {
                    return EnerbosMessage.createErrorMsg("", "施工单编码重复", "");
                }
            }
            if (StringUtils.isBlank(constructionVo.getCreateUser())) {
                constructionVo.setCreateUser(userId);
            } else if (!userId.equals(constructionVo.getCreateUser())) {
                return EnerbosMessage.createErrorMsg("", "施工单创建人只能是自己", "");
            }
            constructionVo.setReportDate(new Date());//提报日期
            if (StringUtils.isBlank(constructionVo.getId())) {
                constructionVo.setCreateUser(userId);
            }
            if (constructionVo.getCreateDate() == null) {
                constructionVo.setCreateDate(new Date());
            }
            ConstructionVo constructionVoSave = constructionVo;
            if (ConstructionCommon.CONSTRUCTION_STATUS_XZ.equals(constructionVo.getStatus())) {
                constructionVoSave = constructionClient.saveConstruction(constructionVo);
            }
            List<String> delete = constructionVo.getDeleteConstructionSuperviseVoList();
            if (delete != null && delete.size() > 0) {
                //删除施工单-监管说明
                constructionSuperviseClient.deleteConstructionSupervise(delete);
            }
            //添加施工单-监管说明
            List<ConstructionSuperviseVo> addConstructionSuperviseList = constructionVo.getConstructionSuperviseVoList();
            if (null != addConstructionSuperviseList && addConstructionSuperviseList.size() > 0) {
                for (ConstructionSuperviseVo addConstructionSupervise : addConstructionSuperviseList) {
                    String id = addConstructionSupervise.getId();
                    if (null == id || "".equals(id) || id.length() < 32) {
                        addConstructionSupervise.setId(null);
                    }
                    addConstructionSupervise.setConstructionId(constructionVoSave.getId());
                }
                constructionSuperviseClient.saveConstructionSuperviseList(addConstructionSuperviseList);
            }
            return EnerbosMessage.createSuccessMsg(constructionVoSave, "保存施工单保存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/saveConstruction ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/saveConstruction----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * updateConstructionStatus:修改施工单状态
     *
     * @param id     施工单ID
     * @param status 状态
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "修改施工单状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/updateConstructionStatus", method = RequestMethod.POST)
    public EnerbosMessage updateConstructionStatus(@ApiParam(value = "施工单id", required = true) @RequestParam("id") String id,
                                                   @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/construction/updateConstructionStatus, host: [{}:{}], service_id: {}, id: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, status);

            ConstructionVo ewo = constructionClient.findConstructionById(id);
            if (ewo == null || "".equals(ewo)) {
                return EnerbosMessage.createErrorMsg("", "施工单不存在", "");
            }
//            ewo.setStatusDate(new Date());//状态日期
            ewo.setStatus(status);
            constructionClient.saveConstruction(ewo);
            return EnerbosMessage.createSuccessMsg(true, "修改施工单状态成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/updateConstructionStatus ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/updateConstructionStatus----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * updateConstructionStatusList:批量修改施工单状态
     *
     * @param ids    施工单ID数组{@link List<String>}
     * @param status 施工单状态
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改施工单状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/updateConstructionStatusList", method = RequestMethod.POST)
    public EnerbosMessage updateConstructionStatusList(@ApiParam(value = "施工单id", required = true) @RequestParam("ids") List<String> ids,
                                                       @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/construction/updateConstructionStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status);

            List<ConstructionVo> list = new ArrayList<>();
            for (String id : ids) {
                ConstructionVo ewo = constructionClient.findConstructionById(id);
                if (ewo == null || "".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "施工单不存在", "");
                }
//                ewo.setStatusDate(new Date());//状态日期
                ewo.setUpdateDate(new Date());
                ewo.setStatus(status);
                list.add(ewo);
            }
            for (ConstructionVo constructionVo : list) {
                constructionClient.saveConstruction(constructionVo);
            }
            return EnerbosMessage.createSuccessMsg(true, "批量修改施工单状态成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/updateConstructionStatusList ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/updateConstructionStatusList----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageConstructionList: 分页模糊查询施工单
     *
     * @param constructionForFilterVo 查询条件 {@link ConstructionForFilterVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询施工单", response = ConstructionForListVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/findPageConstructionList", method = RequestMethod.POST)
    public EnerbosMessage findPageConstructionList(@ApiParam(value = "施工单模糊搜索查询条件VO", required = true) @RequestBody ConstructionForFilterVo constructionForFilterVo, Principal user) {
        try {
            if (Objects.isNull(constructionForFilterVo)) {
                constructionForFilterVo = new ConstructionForFilterVo();
            }
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
            Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
            //设置查询人，用于获取关注信息
            constructionForFilterVo.setPersonId(personId);
            if (constructionForFilterVo.getKeyword() != null) {
                String words = constructionForFilterVo.getKeyword();
                String[] word = StringUtils.split(words, " ");
                constructionForFilterVo.setKeywords(word);
            }
            EnerbosPage<ConstructionForListVo> constructionForListVos = constructionClient.findPageConstructionList(constructionForFilterVo);
            if (constructionForListVos != null && null != constructionForListVos.getList() && constructionForListVos.getList().size() > 0) {
                for (ConstructionForListVo constructionForListVo : constructionForListVos.getList()) {
                    if (constructionForListVo != null && constructionForListVo.getContractId() != null) {
                        ContractVo contractVo = contractClient.findContractVoById(constructionForListVo.getContractId());
                        if (contractVo != null) {
                            constructionForListVo.setContractDesc(contractVo.getDescription());
                        }
                    }
                    if (constructionForListVo != null && constructionForListVo.getSupervisionId() != null) {
                        PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(constructionForListVo.getSupervisionId());
                        if (person != null) {
                            constructionForListVo.setSupervisionName(person.getName());
                        }
                    }
                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(ConstructionCommon.CONSTRUCTION_STATUS, constructionForListVo.getStatus(), constructionForListVo.getSiteId(), constructionForListVo.getOrgId(), "EAM");
                    if (statusDomain != null) constructionForListVo.setStatusDsr(statusDomain.getDescription());

                    constructionForListVo.setCollect(constructionClient.checkCollect(constructionForListVo.getId(), Common.EAM_PROD_IDS[0], personId));
                }
            }
            return EnerbosMessage.createSuccessMsg(constructionForListVos, "分页模糊查询施工单", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/findPageConstructionList ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/findPageConstructionList----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findConstructionById:根据ID查询施工单
     *
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询施工单", response = ConstructionVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/findConstructionById", method = RequestMethod.GET)
    public EnerbosMessage findConstructionById(@ApiParam(value = "施工单id", required = true) @RequestParam("id") String id, Principal user) {
        try {
            ConstructionVo constructionVo = constructionClient.findConstructionById(id);
            if (null != constructionVo) {
                if (null != constructionVo.getSiteId()) {
                    SiteVoForDetail site = siteClient.findById(constructionVo.getSiteId());
                    if (null != site) {
                        constructionVo.setSiteName(site.getName());
                    }
                }
                if (null != constructionVo.getSupervisionId()) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(constructionVo.getSupervisionId());
                    if (null != person) {
                        constructionVo.setSupervisionName(person.getName());
                        constructionVo.setSupervisionMobile(person.getMobile());
                    }
                }
                if (null != constructionVo.getContractId()) {
                    ContractVo contractVo = contractClient.findContractVoById(constructionVo.getContractId());
                    if (null != contractVo) {
                        constructionVo.setContractNum(contractVo.getContractNum());
                        constructionVo.setContractDesc(contractVo.getDescription());
                    }
                }
                if (null != constructionVo.getReportId()) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(constructionVo.getReportId());
                    if (null != person) {
                        constructionVo.setReportName(person.getName());
                    }
                }
                if (null != constructionVo.getConfirmPersonId()) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(constructionVo.getConfirmPersonId());
                    if (null != person) {
                        constructionVo.setConfirmPersonName(person.getName());
                    }
                }
                if (null != constructionVo.getCreateUser()) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(constructionVo.getCreateUser());
                    if (null != person) {
                        constructionVo.setCreateUserName(person.getName());
                    }
                }
                //插入监管记录
                List<ConstructionSuperviseVo> superviseList = constructionSuperviseClient.findConstructionSuperviseByConstructionId(constructionVo.getId());
                constructionVo.setConstructionSuperviseVoList(superviseList);
                //插入执行记录
                String processInstanceId = constructionVo.getProcessInstanceId();
                if (null != processInstanceId && !"".equals(processInstanceId)) {
                    constructionVo.setImpleRecordVoVoList(getExecution(processInstanceId));
                }
            }
            return EnerbosMessage.createSuccessMsg(constructionVo, "根据ID查询施工单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/findConstructionById ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/findConstructionById----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * checkConstructionNumUnique:验证施工单编码的唯一性
     *
     * @param id
     * @param constructionNum 施工单编码
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "验证施工单编码的唯一性", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/checkConstructionNumUnique", method = RequestMethod.GET)
    public EnerbosMessage checkConstructionNumUnique(@ApiParam(value = "施工单id", required = false) @RequestParam(name = "id", required = false) String id,
                                                     @ApiParam(value = "施工单编码id", required = true) @RequestParam("constructionNum") String constructionNum, Principal user) {
        Boolean flag = false;
        try {
            ConstructionVo jpn = constructionClient.findConstructionByConstructionNum(constructionNum);
            if (id == null) {//新建时
                if (jpn == null) {
                    flag = true;
                }
            } else {//修改时
                if (jpn != null && jpn.getId().equals(id) || jpn == null) {
                    flag = true;
                }
            }
            return EnerbosMessage.createSuccessMsg(flag, "验证施工单编码的唯一性成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/checkConstructionNumUnique ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/checkConstructionNumUnique----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 删除选中的施工单
     *
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除施工单", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/deleteConstructionList", method = RequestMethod.GET)
    public EnerbosMessage deleteConstructionList(@ApiParam(value = "删除选中的施工单ids", required = true) @RequestParam("ids") List<String> ids, Principal user) {
        try {
            for (String id : ids) {
                ConstructionVo construction = constructionClient.findConstructionById(id);
                if (null == construction || StringUtils.isBlank(construction.getId())) {
                    return EnerbosMessage.createErrorMsg("", "要删除的施工单不存在", "");
                } else if (!ConstructionCommon.CONSTRUCTION_STATUS_XZ.equals(construction.getStatus())) {
                    return EnerbosMessage.createErrorMsg("", "编码为" + construction.getConstructionNum() + "的施工单状态不是新增", "");
                }
            }
            constructionClient.deleteConstructionList(ids);
            for (String id : ids) {
                constructionSuperviseClient.findConstructionSuperviseByConstructionId(id);
            }
            return EnerbosMessage.createSuccessMsg(true, "删除人员信息施工单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/deleteConstructionList ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/deleteConstructionList----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 施工单-收藏
     *
     * @param ids 施工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "施工单-收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/collect", method = RequestMethod.POST)
    public EnerbosMessage collectConstruction(@ApiParam(value = "施工单ID列表", required = true) @RequestParam(value = "ids", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/construction/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

            Assert.notEmpty(ids, "请选择要收藏的施工单！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<ConstructionRfCollectorVo> workOrderRfCollectorVos = Arrays.stream(ids).map(o -> new ConstructionRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
            constructionClient.collectConstruction(workOrderRfCollectorVos);

            return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/collect ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
//				logger.error("-----/eam/open/construction/deleteConstruction----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 施工单-取消收藏
     *
     * @param ids 施工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "施工单-取消收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/collect/cancel", method = RequestMethod.POST)
    public EnerbosMessage cancelCollectConstruction(@ApiParam(value = "施工单ID列表", required = true) @RequestParam(value = "ids", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/construction/collect/cancel, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

            Assert.notEmpty(ids, "请选择要取消收藏的施工单！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<ConstructionRfCollectorVo> constructionRfCollectorVos = Arrays.stream(ids).map(o -> new ConstructionRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
            constructionClient.cancelCollectConstruction(constructionRfCollectorVos);
            return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/collect/cancel ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
//				logger.error("-----/eam/open/construction/deleteConstruction----",jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 施工单-发送流程
     *
     * @param id
     * @param description
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "施工单-发送流程", response = String.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/construction/commit", method = RequestMethod.POST)
    public EnerbosMessage commitWorkOrderFlow(@ApiParam(value = "施工单id", required = true) @RequestParam("id") String id,
                                              @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name = "processStatus", required = true) String processStatus,
                                              @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/construction/commit, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, processStatus, description, user);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            ConstructionVo constructionVo = constructionClient.findConstructionById(id);
            EnerbosMessage message;
            if (constructionVo == null) {
                return EnerbosMessage.createErrorMsg("", "施工单不存在", "");
            }
            String siteId = constructionVo.getSiteId();
            SiteVoForDetail site = siteClient.findById(siteId);
            if (site == null || null == site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo = new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(constructionVo.getProcessInstanceId());
            List<TaskVo> page = processTaskClient.findTasks(taskForFilterVo).getList();
            if (StringUtils.isNotBlank(constructionVo.getProcessInstanceId()) && (null == page || page.size() < 1)) {
                return EnerbosMessage.createSuccessMsg("401", "无权操作此工单", "");
            }
            if (!ConstructionCommon.CONSTRUCTION_STATUS_XZ.equals(constructionVo.getStatus()) && !constructionVo.getStatus().equals(page.get(0).getOrderStatus())) {
                return EnerbosMessage.createSuccessMsg("401", "工单状态和流程状态不一致", "");
            }
            switch (constructionVo.getStatus()) {
                //待提报分支
                case ConstructionCommon.CONSTRUCTION_STATUS_XZ: {
                    message = commitConstruction(constructionVo, description, userId, user.getName());
                }
                break;
                //待确认分支
                case ConstructionCommon.CONSTRUCTION_STATUS_ZX: {
                    message = acceptConstruction(constructionVo, processStatus, description, userId, user.getName());
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", constructionVo.getStatus()));
            }
            return message;
        } catch (Exception e) {
            logger.error("-----/eam/open/construction/commit ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/commit----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * commitConstruction:施工单，流程启动、提报
     *
     * @param constructionVo
     * @param description    施工单提报说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage commitConstruction(ConstructionVo constructionVo, String description, String userId, String loginName) {
        try {
            if (!userId.equals(constructionVo.getCreateUser())) {
                return EnerbosMessage.createErrorMsg("", "不是提报人，不能提报！", "");
            }
            if (constructionVo.getProcessInstanceId() == null || "".equals(constructionVo.getProcessInstanceId())) {
                //启动流程
                //设置流程变量
                Map<String, Object> variables = new HashMap<String, Object>();
                //业务主键
                String businessKey = constructionVo.getId();
                //流程key,key为维保固定前缀+站点code
                String code = "";
                SiteVoForDetail site = siteClient.findById(constructionVo.getSiteId());
                if (site != null) {
                    code = site.getCode();
                }
                String processKey = ConstructionCommon.CONSTRUCTION_WFS_PROCESS_KEY + code;
                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(businessKey);
                processVo.setProcessKey(processKey);
                processVo.setUserId(userId);
                variables.put(ConstructionCommon.CONSTRUCTION_SUBMIT_USER, userId);
                variables.put(ConstructionCommon.CONSTRUCTION_NUM, constructionVo.getConstructionNum());
                variables.put(ConstructionCommon.CONSTRUCTION_DESCRIPTION, constructionVo.getDescription());
                variables.put("userId", userId);
                logger.debug("/eam/open/construction/commit, processKey: {}", processKey);
                processVo = workflowClient.startProcess(variables, processVo);

                if (null == processVo || "".equals(processVo.getProcessInstanceId())) {
                    return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
                }
                //提报，修改基本字段保存
                constructionVo.setProcessInstanceId(processVo.getProcessInstanceId());
                constructionVo.setReportDate(new Date());
                constructionVo = constructionClient.saveConstruction(constructionVo);
            }
            //指定监管人
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put(ConstructionCommon.CONSTRUCTION_CONFIRM_USER, constructionVo.getSupervisionId());
            variables.put("description", description);
            variables.put("status", ConstructionCommon.CONSTRUCTION_STATUS_ZX);
            variables.put("userId", userId);
            variables.put(ConstructionCommon.CONSTRUCTION_REPORT_PASS, true);
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(constructionVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            //提报，修改基本字段保存
            constructionVo.setStatus(ConstructionCommon.CONSTRUCTION_STATUS_ZX);
            constructionVo = constructionClient.saveConstruction(constructionVo);
            return EnerbosMessage.createSuccessMsg(constructionVo.getStatus(), "提报施工单成功", "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            if (StringUtils.isNotEmpty(constructionVo.getId()) &&
                    !ConstructionCommon.CONSTRUCTION_STATUS_XZ.equals(constructionVo.getStatus())) {
                constructionVo.setStatus(ConstructionCommon.CONSTRUCTION_STATUS_XZ);
                constructionClient.saveConstruction(constructionVo);
            }
            logger.error("-----/eam/open/construction/commit ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/commit----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * acceptConstruction:施工单-确认/驳回
     *
     * @param constructionVo
     * @param processStatus
     * @param description    确认工单说明
     * @param userId
     * @param loginName
     * @return EnerbosMessage返回执行码及数据
     */
    public EnerbosMessage acceptConstruction(ConstructionVo constructionVo, String processStatus, String description, String userId, String loginName) {
        try {
            //更新流程进度
            Map<String, Object> variables = new HashMap<String, Object>();
            String message = "";
            switch (processStatus) {
                case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
                    //同意确认，结束流程
                    variables.put(ConstructionCommon.CONSTRUCTION_CANCEL_PASS, true);
                    variables.put(ConstructionCommon.CONSTRUCTION_REJECT, false);
                    variables.put("status", ConstructionCommon.CONSTRUCTION_STATUS_WC);
                    constructionVo.setStatus(ConstructionCommon.CONSTRUCTION_STATUS_WC);//工单状态更新到待接单
                    message = "施工单-确认成功";
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", constructionVo.getId(), processStatus, description));
            }
            variables.put("userId", userId);
            variables.put(ConstructionCommon.CONSTRUCTION_CONFIRM_USER, userId);
            variables.put("description", description);
            processTaskClient.completeByProcessInstanceId(constructionVo.getProcessInstanceId(), variables);
            //保存工单
            constructionVo = constructionClient.saveConstruction(constructionVo);
            return EnerbosMessage.createSuccessMsg(constructionVo.getStatus(), message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待分派
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(constructionVo.getId()) &&
                    !ConstructionCommon.CONSTRUCTION_STATUS_ZX.equals(constructionVo.getStatus())) {
                constructionVo.setStatus(ConstructionCommon.CONSTRUCTION_STATUS_ZX);
                constructionClient.saveConstruction(constructionVo);
            }
            logger.error("-----/eam/open/construction/acceptConstruction ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/construction/acceptConstruction----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * getExecution:根据流程实例ID查询执行记录
     *
     * @param processInstanceId 流程实例ID
     * @return List<WorkFlowImpleRecordVo> 执行记录VO集合
     */
    private List<WorkFlowImpleRecordVo> getExecution(String processInstanceId) {
        List<WorkFlowImpleRecordVo> workFlowImpleRecordVoList = new ArrayList<>();
        List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
        WorkFlowImpleRecordVo workFlowImpleRecordVo = null;
        if (null != historicTaskVoList && historicTaskVoList.size() > 0) {
            for (HistoricTaskVo historicTaskVo : historicTaskVoList) {
                workFlowImpleRecordVo = new WorkFlowImpleRecordVo();
                workFlowImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
                workFlowImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
                workFlowImpleRecordVo.setName(historicTaskVo.getName());
                if (StringUtils.isNotBlank(historicTaskVo.getAssignee())) {
                    String[] ids = historicTaskVo.getAssignee().split(",");
                    String personName = "";
                    if (null != ids && !"".equals(ids)) {
                        for (String id : ids) {
                            PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(id);
                            if (person != null) {
                                personName += personAndUserClient.findByPersonId(id).getName() + ",";
                            }
                        }
                        personName = personName.substring(0, personName.length() - 1);
                    }
                    workFlowImpleRecordVo.setPersonName(personName);
                }
                workFlowImpleRecordVo.setDurationInMillis(historicTaskVo.getDurationInMillis());
                workFlowImpleRecordVo.setDescription(historicTaskVo.getDescription());
                workFlowImpleRecordVoList.add(workFlowImpleRecordVo);
            }
        }
        return workFlowImpleRecordVoList;
    }
}