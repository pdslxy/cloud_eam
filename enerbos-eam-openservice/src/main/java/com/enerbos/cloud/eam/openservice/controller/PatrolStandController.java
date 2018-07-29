package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.ClassificationClient;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.classification.ClassificationVoForDetail;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.PatrolStandClient;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2017-2018
 * Company 北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version EAM2.0
 * @date 2017年8月10日 14:58:19
 * @Description 巡检标准
 */
@RestController
@Api(description = "巡检标准(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolStandController {
    private static Logger logger = LoggerFactory
            .getLogger(PatrolStandController.class);

    @Autowired
    public PatrolStandClient patrolStandClient;

    @Autowired
    public FieldDomainClient fieldDomainClient;

    @Autowired
    public SiteClient siteClient;

    @Autowired
    public ClassificationClient classificationClient;

    @Autowired
    private OrgClient orgClient;

    /**
     * 分页查询巡检标准
     *
     * @param patrolStandVoForFilter 巡检标准查询实体
     * @return 分页列表
     */
    @ApiOperation(value = "分页查询巡检标准", response = PatrolStandVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/findPage", method = RequestMethod.POST)
    public EnerbosMessage findPage(
            @RequestBody PatrolStandVoForFilter patrolStandVoForFilter) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            EnerbosPage<PatrolStandVoForList> pageInfo = patrolStandClient
                    .findPatrolStandList(patrolStandVoForFilter);

            logger.info("-result：pageInfo {}", pageInfo);
            if (pageInfo != null) {
                List<PatrolStandVoForList> patrolStandList = pageInfo.getList();

                for (PatrolStandVoForList patrolStand : patrolStandList) {
                    FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("patrolType", patrolStand.getType(), patrolStand.getSiteId(), patrolStand.getOrgId(), "EAM");
                    if (typeDomain != null) patrolStand.setTypeName(typeDomain.getDescription());
                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("patrolPointStatus", patrolStand.getStatus(), patrolStand.getSiteId(), patrolStand.getOrgId(), "EAM");
                    if (statusDomain != null) patrolStand.setStatusName(statusDomain.getDescription());
                    String siteId = patrolStand.getSiteId();
                    SiteVoForDetail siteVo = siteClient.findById(siteId);
                    if (siteVo != null) {
                        patrolStand.setSiteName(siteVo.getName());
                    }
                }
                return EnerbosMessage.createSuccessMsg(pageInfo, "巡检路线列表查询成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(null, "巡检路线列表查询失败", "");
            }

        } catch (Exception e) {
            logger.error("-----findPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e),
                    e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 根据ID查询巡检标准
     *
     * @param id 巡检标准id
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询巡检标准详细信息", response = PatrolStandVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/findPatrolStandById", method = RequestMethod.GET)
    public EnerbosMessage findPatrolStandById(@RequestParam("id") String id,
                                              Principal user) {

        try {

            PatrolStandVo patrolStandVo = patrolStandClient
                    .findPatrolStandById(id);

            if (patrolStandVo != null) {
                FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("patrolType", patrolStandVo.getType(), patrolStandVo.getSiteId(), patrolStandVo.getOrgId(), "EAM");
                if (typeDomain != null) patrolStandVo.setTypeName(typeDomain.getDescription());
                FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("patrolPointStatus", patrolStandVo.getStatus(), patrolStandVo.getSiteId(), patrolStandVo.getOrgId(), "EAM");
                if (statusDomain != null) patrolStandVo.setStatusName(statusDomain.getDescription());
                String siteId = patrolStandVo.getSiteId();
                SiteVoForDetail siteVo = siteClient.findById(siteId);
                if (siteVo != null) {
                    patrolStandVo.setSiteName(siteVo.getName());
                }
                OrgVoForDetail orgVoForDetail = orgClient.findById(patrolStandVo.getOrgId());
                if(orgVoForDetail!=null)patrolStandVo.setOrgName(orgVoForDetail.getName());

                String classificationid = patrolStandVo.getClassstructureid();

                ClassificationVoForDetail classificationVoForDetail = classificationClient
                        .findById(user.getName(), classificationid);

                if (classificationVoForDetail != null) {
                    patrolStandVo.setClassstructureNum(classificationVoForDetail.getCode());
                    patrolStandVo
                            .setClassstructureDescription(classificationVoForDetail
                                    .getDescription());
                }
            }
            return EnerbosMessage.createSuccessMsg(patrolStandVo,
                    "查询巡检标准详细信息成功", "");
        } catch (Exception e) {
            logger.error("-----findPatrolStandById ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 保存巡检标准
     *
     * @param patrolStandVo 巡检标准实体 {@link com.enerbos.cloud.eam.vo.PatrolStandVo}
     * @return 保存的实体
     */
    @ApiOperation(value = "保存巡检标准", response = PatrolStandVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/savePatrolStand", method = RequestMethod.POST)
    public EnerbosMessage savePatrolStand(
            @RequestBody PatrolStandVo patrolStandVo, Principal user) {

        patrolStandVo.setCreateDate(new Date());
        patrolStandVo.setUpdateDate(new Date());

        try {
            logger.info(
                    "/eam/open/patrolStand/savePatrolStand----- >PatrolStandVo:{}",
                    patrolStandVo);
            patrolStandVo.setSiteId(PrincipalUserUtils.getSiteIdByUser(Json
                    .pretty(user).toString()));
            patrolStandVo.setOrgId(PrincipalUserUtils.getOrgIdByUser(Json
                    .pretty(user).toString()));
            patrolStandVo.setCreateUser(user.getName());
            return EnerbosMessage.createSuccessMsg(
                    patrolStandClient.savePatrolStand(patrolStandVo),
                    "保存巡检标准成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolStand/savePatrolStand- ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 修改巡检标准
     *
     * @param patrolStandVo 巡检标准实体 {@link com.enerbos.cloud.eam.vo.PatrolStandVo}
     * @return 保存的实体
     */
    @ApiOperation(value = "修改巡检标准", response = PatrolStandVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/updatePatrolStand", method = RequestMethod.POST)
    public EnerbosMessage updatePatrolStand(
            @RequestBody PatrolStandVo patrolStandVo) {

        try {
            logger.info(
                    "/eam/open/patrolStand/updatePatrolStand----- >PatrolStandVo:{}",
                    patrolStandVo);


            return EnerbosMessage.createSuccessMsg(
                    patrolStandClient.updatePatrolStand(patrolStandVo),
                    "修改巡检标准成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolStand/updatePatrolStand------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 删除巡检标准
     *
     * @param ids 要删除的id数组
     * @return 数据
     */
    @ApiOperation(value = "删除巡检标准", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/deletePatrolStand", method = RequestMethod.POST)
    public EnerbosMessage deletePatrolStand(
            @ApiParam(value = "支持批量删除 多个用逗号 分割") @RequestParam("ids") String[] ids) {

        try {
            logger.info("/eam/open/patrolStand/deletePatrolStand----- >ids:{}",
                    ids);

            boolean isdeleteOk = patrolStandClient.deletePatrolStand(ids);
            if (isdeleteOk) {

                return EnerbosMessage.createSuccessMsg(isdeleteOk, "删除巡检标准成功",
                        "");
            } else {
                return EnerbosMessage.createSuccessMsg(isdeleteOk, "删除巡检标准失败",
                        "");
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolStand/deletePatrolStand------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 根据巡检标准id查找巡检标准内容
     *
     * @param id       巡检标准id
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 查询结果
     */
    @ApiOperation(value = "根据巡检标准id查找巡检标准内容", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/findPatrolStandContent", method = RequestMethod.POST)
    public EnerbosMessage findPatrolStandContent(@RequestParam("id") String id,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("pageSize") Integer pageNum) {

        try {
            logger.info(
                    "/eam/open/patrolStand/findPatrolStandContent----- >id:{},pageSize:{},pageNum:{}",
                    id, pageSize, pageNum);

            EnerbosPage<PatrolStandContentVoForList> pageInfo = patrolStandClient
                    .findPatrolStandContent(id, pageSize, pageNum);

            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检路线列表查询成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/patrolStand/findPatrolStandContent------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 保存巡检内容
     *
     * @param patrolStandContentVo {@link com.enerbos.cloud.eam.vo.PatrolStandContentVo}
     * @return 保存后实体
     */
    @ApiOperation(value = "保存巡检标准内容", response = PatrolStandContentVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/savePatrolStandContent", method = RequestMethod.POST)
    public EnerbosMessage savePatrolStandContent(
            @RequestBody PatrolStandContentVo patrolStandContentVo, Principal user) {


        try {
            logger.info(
                    "/eam/open/patrolStand/savePatrolStandContent----- >{}",
                    patrolStandContentVo);

            patrolStandContentVo.setSiteId(PrincipalUserUtils.getSiteIdByUser(Json
                    .pretty(user).toString()));
            patrolStandContentVo.setOrgId(PrincipalUserUtils.getOrgIdByUser(Json
                    .pretty(user).toString()));
            patrolStandContentVo.setCreateUser(user.getName());
            PatrolStandContentVo patrolStandContent = patrolStandClient
                    .savePatrolStandContent(patrolStandContentVo);

            return EnerbosMessage.createSuccessMsg(patrolStandContent,
                    "保存巡检标准内容成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/patrolStand/savePatrolStandContent------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 修改巡检内容
     *
     * @param patrolStandContentVo {@link com.enerbos.cloud.eam.vo.PatrolStandContentVo}
     * @return 修改后实体
     */
    @ApiOperation(value = "修改巡检标准内容", response = PatrolStandContentVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/updatePatrolStandContent", method = RequestMethod.POST)
    public EnerbosMessage updatePatrolStandContent(
            @RequestBody PatrolStandContentVo patrolStandContentVo) {

        try {
            logger.info(
                    "/eam/open/patrolStand/updatePatrolStandContent----- >patrolStandContentVo{}",
                    patrolStandContentVo);

            String id = patrolStandContentVo.getId();
            logger.info("id:{}",id);
            PatrolStandContentVo patrolStandContent = patrolStandClient.findPatrolStandContentById(id);

            if (StringUtils.isNotEmpty(patrolStandContentVo.getCheckStand())) {
                patrolStandContent.setCheckStand(patrolStandContentVo.getCheckStand());
            }

            if (StringUtils.isNotEmpty(patrolStandContentVo.getDescription())) {
                patrolStandContent.setDescription(patrolStandContentVo.getDescription());
            }

            if (StringUtils.isNotEmpty(patrolStandContentVo.getRemark())) {
                patrolStandContent.setRemark(patrolStandContentVo.getRemark());
            }
            logger.info("-----------the entity PatrolStandContentVo {}", patrolStandContent);
            PatrolStandContentVo patrolStand = patrolStandClient.updatePatrolStandContent(patrolStandContent);
            return EnerbosMessage.createSuccessMsg(patrolStand,
                    "修改巡检标准内容成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/patrolStand/updatePatrolStandContent------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除巡检标准内容
     *
     * @param ids id合集
     * @return boolean 成功 失败
     */
    @ApiOperation(value = "删除巡检标准内容", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolStand/deletePatrolStandContent", method = RequestMethod.POST)
    public EnerbosMessage deletePatrolStandContent(
            @RequestParam("ids") String[] ids) {

        try {
            logger.info("/eam/open/patrolStand/deletePatrolStandContent----- >ids{}",
                    ids);

            boolean isdeleteOk = patrolStandClient.deletePatrolStandContent(ids);

            if (isdeleteOk) {
                return EnerbosMessage.createSuccessMsg(isdeleteOk,
                        "删除巡检标准内容成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isdeleteOk,
                        "删除巡检标准内容失败", "");
            }

        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/patrolStand/updatePatrolStandContent------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    // 获取错误码
    private String getErrorStatusCode(Exception e) {
        String message = e.getCause().getMessage()
                .substring(e.getCause().getMessage().indexOf("{"));
        String statusCode = "";
        try {
            JSONObject jsonMessage = JSONObject.parseObject(message);
            if (jsonMessage != null) {
                statusCode = jsonMessage.get("status").toString();
            }
        } catch (Exception jsonException) {
            logger.error("-----/eam/open/patrolStand/findPage----",
                    jsonException);
        }
        return statusCode;
    }
}
