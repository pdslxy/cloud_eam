package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.ClassificationClient;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForDetail;
import com.enerbos.cloud.ams.vo.classification.ClassificationVoForDetail;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.DailyCommonClient;
import com.enerbos.cloud.eam.client.DailyCopyMeterPlanClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.CopyMeterCommon;
import com.enerbos.cloud.eam.contants.PatrolCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年11月18日 10:49:34
 * @Description 抄表计划
 */
@RestController
@Api(description = "抄表计划(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DailyCopyMeterPlanController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DailyCopyMeterPlanClient dailyCopyMeterPlanClient;

    @Autowired
    private AssetClient assetClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    private ClassificationClient classificationClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private DailyCommonClient dailyCommonClient;

    /**
     * 获取抄表信息列表
     *
     * @param dailyCopyMeterPlanFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanFilterVo}
     * @return 返回查询 数据
     */
    @ApiOperation(value = "获取抄表信息列表", response = DailyCopyMeterPlanVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeterPlan/findCopyMeterPlans", method = RequestMethod.POST)
    public EnerbosMessage findCopyMeterPlans(@RequestBody DailyCopyMeterPlanFilterVo dailyCopyMeterPlanFilterVo, Principal user) {
        logger.info(" findCopyMeterPlans----Param:{}", dailyCopyMeterPlanFilterVo);
        try {
            if(dailyCopyMeterPlanFilterVo.getCollect()!=null&&dailyCopyMeterPlanFilterVo.getCollect()){
                String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
                dailyCopyMeterPlanFilterVo.setPersonId(personId);
            }
            EnerbosPage<DailyCopyMeterPlanVoForList> pageInfo = dailyCopyMeterPlanClient.findCopyMeterPlans(dailyCopyMeterPlanFilterVo);
            if (pageInfo != null) {
                List<DailyCopyMeterPlanVoForList> dailyCopyMeterPlanVoForLists = pageInfo.getList();
                for (DailyCopyMeterPlanVoForList dailyCopyMeterPlanVoForList : dailyCopyMeterPlanVoForLists) {
                    //查询类型
                    String[] classificationIds = dailyCopyMeterPlanVoForList.getCopyMeterType().split(",");
                    StringBuilder typeNames = new StringBuilder();
                    for (String classificationId : classificationIds) {
                        ClassificationVoForDetail classificationVoForDetail = classificationClient.findById(user.getName(), classificationId);
                        if (classificationVoForDetail != null) {
                            typeNames.append(classificationVoForDetail.getName() + ",");
                        }
                    }
                    if (typeNames.length() > 0) {
                        dailyCopyMeterPlanVoForList.setCopyMeterTypeName(typeNames.substring(0, typeNames.length() - 1));
                    }
                    //查询状态
                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(CopyMeterCommon.COPY_METER_PLAN_STATUS, dailyCopyMeterPlanVoForList.getStatus(), dailyCopyMeterPlanFilterVo.getSiteId(), dailyCopyMeterPlanFilterVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
                    if (statusDomain != null) {
                        dailyCopyMeterPlanVoForList.setStatusDsr(statusDomain.getDescription());
                    }
                    //查询收藏
                    boolean b = dailyCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(dailyCopyMeterPlanVoForList.getId(), CopyMeterCommon.PLAN_COLLECT_KEY, Common.EAM_PROD_IDS[0], PrincipalUserUtils.getPersonId(Json.pretty(user).toString()));
                    dailyCopyMeterPlanVoForList.setCollect(b);
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "抄表计划查询成功", "");
        } catch (Exception e) {
            logger.error("-----findCopyMeterPlans------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新增抄表计划
     *
     * @param dailyCopyMeterPlanVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVo}
     * @return 保存后实体
     */
    @ApiOperation(value = "新增抄表计划", response = DailyCopyMeterPlanVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeterPlan/saveCopyMeterPlan", method = RequestMethod.POST)
    public EnerbosMessage saveCopyMeterPlan(@RequestBody DailyCopyMeterPlanVo dailyCopyMeterPlanVo, Principal user) {

        logger.info(" saveCopyMeterPlan----Param:{}", dailyCopyMeterPlanVo);

        try {
            DailyCopyMeterPlanVo CopyMeterPlan = dailyCopyMeterPlanClient.saveCopyMeterPlan(dailyCopyMeterPlanVo);

            if (CopyMeterPlan != null) {
                return EnerbosMessage.createSuccessMsg(CopyMeterPlan, "新增抄表计划成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(CopyMeterPlan, "新增抄表计划失败", "");
            }
        } catch (Exception e) {
            logger.error("-----saveCopyMeterPlan------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 删除抄表计划
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @ApiOperation(value = "删除抄表计划", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeterPlan/deleteCopyMeterPlan", method = RequestMethod.GET)
    public EnerbosMessage deleteCopyMeterPlan(@RequestParam("ids") String[] ids) {

        logger.info(" deleteCopyMeterPlan----Param:{}", ids);

        try {
            boolean isOk = dailyCopyMeterPlanClient.deleteCopyMeterPlan(ids);

            if (isOk) {
                return EnerbosMessage.createSuccessMsg(isOk, "删除抄表计划成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isOk, "删除抄表计划失败", "");
            }
        } catch (Exception e) {
            logger.error("----- deleteCopyMeterPlan------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改抄表计划状态
     *
     * @param ids 操作的id
     * @return
     */
    @ApiOperation(value = "修改抄表计划状态", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeterPlan/updateCopyMeterPlanStatus", method = RequestMethod.POST)
    public EnerbosMessage updateCopyMeterPlanStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status) {

        logger.info(" updateCopyMeterPlanStatus----Param:{},{}", ids, status);

        try {

            String statusToUpdate = status;

            boolean isOk = dailyCopyMeterPlanClient.updateCopyMeterPlanStatus(ids, status);


            if (isOk) {
                return EnerbosMessage.createSuccessMsg(isOk, "修改抄表计划状态成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isOk, "修改抄表计划状态失败", "");
            }
        } catch (Exception e) {
            logger.error("----- updateCopyMeterPlanStatus------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据id查找抄表计划
     *
     * @param id     抄表计划id
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @ApiOperation(value = "根据id查找抄表计划", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeterPlan/findCopyMeterPlanById", method = RequestMethod.GET)
    public EnerbosMessage findCopyMeterPlanById(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId, Principal user) {

        logger.info(" deleteCopyMeterPlan----Param:{},{},{}", id, siteId, orgId);

        try {
            DailyCopyMeterPlanVo dailyCopyMeterPlanVo = dailyCopyMeterPlanClient.findCopyMeterPlanById(id, siteId, orgId);

            if (dailyCopyMeterPlanVo != null) {
                //查找人员
                if (StringUtils.hasLength(dailyCopyMeterPlanVo.getCreateUser())) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(dailyCopyMeterPlanVo.getCreateUser());
                    if (person != null) {
                        dailyCopyMeterPlanVo.setCreateUserName(person.getName());
                    }
                }
                if (StringUtils.hasLength(dailyCopyMeterPlanVo.getCopyMeterPerson())) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(dailyCopyMeterPlanVo.getCopyMeterPerson());
                    if (person != null) {
                        dailyCopyMeterPlanVo.setCopyMeterPersonName(person.getName());
                    }
                }
                //查找频率
                dailyCopyMeterPlanVo.setDailyCopyMeterPlanRequencyVos(dailyCopyMeterPlanClient.findCopyMeterPlanRequencyVosById(id).getList());

                //查找设备
                List<DaliyCopyMeterPlanMeterRelationVo> meterRelationlist = dailyCopyMeterPlanClient.findCopyMeterPlanMeterRelationVosById(id).getList();
                List<DailyCopyMeterPlanAssetVo> assetLists = new ArrayList<>();
                for (DaliyCopyMeterPlanMeterRelationVo daliyCopyMeterPlanMeterRelationVo : meterRelationlist) {
                    DailyCopyMeterPlanAssetVo dailyCopyMeterPlanAssetVo = new DailyCopyMeterPlanAssetVo();
                    AssetVoForDetail assetVoForDetail = assetClient.findById(user.getName(), daliyCopyMeterPlanMeterRelationVo.getMeterId());
                    if (assetVoForDetail != null) {
                        LocationVoForDetail locationVoForDetail = locationClient.findById(user.getName(), assetVoForDetail.getLocationId());
                        BeanUtils.copyProperties(assetVoForDetail, dailyCopyMeterPlanAssetVo);
                        if (locationVoForDetail != null) {
                            dailyCopyMeterPlanAssetVo.setLocationName(locationVoForDetail.getName());
                            dailyCopyMeterPlanAssetVo.setLocationCode(locationVoForDetail.getCode());
                        }
                        ClassificationVoForDetail classificationVoForDetail = classificationClient.findById(user.getName(), assetVoForDetail.getClassificationId());
                        if (classificationVoForDetail != null) {
                            dailyCopyMeterPlanAssetVo.setClassificationName(classificationVoForDetail.getName());
                        }
                        assetLists.add(dailyCopyMeterPlanAssetVo);
                    }
                }
                dailyCopyMeterPlanVo.setAssetList(assetLists);
                return EnerbosMessage.createSuccessMsg(dailyCopyMeterPlanVo, "根据id查找抄表计划成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(null, "根据id查找抄表计划失败", "");
            }
        } catch (Exception e) {
            logger.error("----- findCopyMeterPlanById------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

}


