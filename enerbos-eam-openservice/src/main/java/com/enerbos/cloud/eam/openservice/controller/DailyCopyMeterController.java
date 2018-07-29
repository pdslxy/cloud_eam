package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.ClassificationClient;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForDetail;
import com.enerbos.cloud.ams.vo.asset.AssetVoForFilter;
import com.enerbos.cloud.ams.vo.asset.AssetVoForList;
import com.enerbos.cloud.ams.vo.classification.ClassificationVoForDetail;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.AssetEnergyPriceClient;
import com.enerbos.cloud.eam.client.DailyCommonClient;
import com.enerbos.cloud.eam.client.DailyCopyMeterClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.CopyMeterCommon;
import com.enerbos.cloud.eam.contants.PatrolCommon;
import com.enerbos.cloud.eam.openservice.service.PushService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年9月1日 10:40:55
 * @Description 抄表管理
 */
@RestController
@Api(description = "抄表管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DailyCopyMeterController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DailyCopyMeterClient dailyCopyMeterClient;

    @Autowired
    private AssetClient assetClient;

    @Autowired
    private AssetEnergyPriceClient assetEnergyPriceClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private ClassificationClient classificationClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    protected WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private PushService pushService;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private DailyCommonClient dailyCommonClient;

    /**
     * 获取抄表信息列表
     *
     * @param dailyCopyMeterFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterFilterVo}
     * @return 返回查询 数据
     */
    @ApiOperation(value = "获取抄表信息列表", response = DailyCopyMeterVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/findCopyMeters", method = RequestMethod.POST)
    public EnerbosMessage findCopyMeters(@RequestBody DailyCopyMeterFilterVo dailyCopyMeterFilterVo, Principal user) {

        logger.info(" findCopyMeters----Param:{}", dailyCopyMeterFilterVo);
        try {
            EnerbosPage<DailyCopyMeterVoForList> pageInfo = dailyCopyMeterClient.findCopyMeters(dailyCopyMeterFilterVo);
            //查询类型
            if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                for (DailyCopyMeterVoForList dailyCopyMeterVoForList : pageInfo.getList()) {
                    //查询状态
                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(CopyMeterCommon.COPY_METER_ORDER_STATUS, dailyCopyMeterVoForList.getStatus(), dailyCopyMeterFilterVo.getSiteId(), dailyCopyMeterFilterVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
                    if (statusDomain != null) {
                        dailyCopyMeterVoForList.setStatusDesc(statusDomain.getDescription());
                    }
                    //查询抄表人
                    if (StringUtils.isNotEmpty(dailyCopyMeterVoForList.getCopyMeterPersonId())) {
                        PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(dailyCopyMeterVoForList.getCopyMeterPersonId());
                        if (person != null) {
                            dailyCopyMeterVoForList.setCopyMeterPersonName(person.getName());
                        }
                    }
                    String[] classificationIds = dailyCopyMeterVoForList.getCopyMeterType().split(",");
                    StringBuilder typeNames = new StringBuilder();
                    for (String classificationId : classificationIds) {
                        ClassificationVoForDetail classificationVoForDetail = classificationClient.findById(user.getName(), classificationId);
                        if (classificationVoForDetail != null) {
                            typeNames.append(classificationVoForDetail.getName() + ",");
                        }
                    }
                    if (typeNames.length() > 0) {
                        dailyCopyMeterVoForList.setCopyMeterTypeName(typeNames.substring(0, typeNames.length() - 1));
                    }
                    //查询收藏
                    boolean b = dailyCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(dailyCopyMeterVoForList.getId(), CopyMeterCommon.ORDER_COLLECT_KEY, Common.EAM_PROD_IDS[0], PrincipalUserUtils.getPersonId(Json.pretty(user).toString()));
                    dailyCopyMeterVoForList.setCollect(b);
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "抄表工单查询成功", "");
        } catch (Exception e) {
            logger.error("-----findCopyMeters------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新增抄表单
     *
     * @param dailyCopyMeterVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 保存后实体
     */
    @ApiOperation(value = "新增抄表单", response = DailyCopyMeterVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/saveCopyMeter", method = RequestMethod.POST)
    public EnerbosMessage saveCopyMeter(@RequestBody DailyCopyMeterVo dailyCopyMeterVo, Principal user) {

        logger.info(" saveCopyMeter----Param:{}", dailyCopyMeterVo);

        try {
            dailyCopyMeterVo.setCreateUser(user.getName());
            if (StringUtils.isNotEmpty(dailyCopyMeterVo.getId())) {
                DailyCopyMeterVo dailyCopyMeterVo1 = dailyCopyMeterClient.findCopyMeterById(dailyCopyMeterVo.getId(), dailyCopyMeterVo.getSiteId(), dailyCopyMeterVo.getOrgId());
                if(dailyCopyMeterVo1!=null){
                    dailyCopyMeterVo.setCreateDate(dailyCopyMeterVo1.getCreateDate());
                }
            }
            DailyCopyMeterVo copyMeter = dailyCopyMeterClient.saveCopyMeter(dailyCopyMeterVo);
            if (copyMeter != null) {
               /* //指定执行人后，走到待执行状态
                if (StringUtils.isNotEmpty(copyMeter.getCopyMeterPerson()) && copyMeter.getStatus().equals(CopyMeterCommon.COPY_METER_STATUS_CG)) {
                    CopyMeterOrderForWorkFlowVo copyMeterOrderForWorkFlowVo = new CopyMeterOrderForWorkFlowVo();
                    copyMeter.setStatus(CopyMeterCommon.COPY_METER_STATUS_DFP);
                    BeanUtils.copyProperties(copyMeter, copyMeterOrderForWorkFlowVo);
                    assignWorkOrderWorkFlow(copyMeterOrderForWorkFlowVo, CopyMeterCommon.PROCESS_STATUS_PASS, "", PrincipalUserUtils.getPersonId(Json.pretty(user).toString()), user.getName());
                    copyMeter.setStatus(copyMeterOrderForWorkFlowVo.getStatus());
                }*/
                return EnerbosMessage.createSuccessMsg(copyMeter, "新增抄表单成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(copyMeter, "新增抄表单失败", "");
            }
        } catch (Exception e) {
            logger.error("-----saveCopyMeter------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改抄表单
     *
     * @param dailyCopyMeterVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 修改后实体
     */
    @ApiOperation(value = "修改抄表单", response = DailyCopyMeterVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/updateCopyMeter", method = RequestMethod.POST)
    public EnerbosMessage updateCopyMeter(@RequestBody DailyCopyMeterVo dailyCopyMeterVo) {

        logger.info(" updateCopyMeter----Param:{}", dailyCopyMeterVo);

        try {
            DailyCopyMeterVo copyMeter = dailyCopyMeterClient.updateCopyMeter(dailyCopyMeterVo);

            if (copyMeter != null) {
                return EnerbosMessage.createSuccessMsg(copyMeter, "修改抄表单成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(copyMeter, "修改抄表单失败", "");
            }
        } catch (Exception e) {
            logger.error("----- updateCopyMeter------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 删除抄表单
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @ApiOperation(value = "删除抄表单", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/deleteCopyMeter", method = RequestMethod.GET)
    public EnerbosMessage deleteCopyMeter(@RequestParam("ids") String[] ids) {

        logger.info(" deleteCopyMeter----Param:{}", ids);

        try {
            boolean isOk = dailyCopyMeterClient.deleteCopyMeter(ids);

            if (isOk) {
                return EnerbosMessage.createSuccessMsg(isOk, "删除抄表单成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isOk, "删除抄表单失败", "");
            }
        } catch (Exception e) {
            logger.error("----- deleteCopyMeter------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改抄表单状态
     *
     * @param ids 操作的id
     * @return
     */
    @ApiOperation(value = "修改抄表单状态", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/updateCopyMeterStatus", method = RequestMethod.POST)
    public EnerbosMessage updateCopyMeterStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status) {

        logger.info(" updateCopyMeterStatus----Param:{},{}", ids, status);

        try {
            boolean isOk = dailyCopyMeterClient.updateCopyMeterStatus(ids, status);

            if (isOk) {
                return EnerbosMessage.createSuccessMsg(isOk, "修改抄表单状态成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isOk, "修改抄表单状态失败", "");
            }
        } catch (Exception e) {
            logger.error("----- updateCopyMeterStatus------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据id查找抄表单
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @ApiOperation(value = "根据id查找抄表单", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/findCopyMeterById", method = RequestMethod.GET)
    public EnerbosMessage findCopyMeterById(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId, Principal user) {

        logger.info(" deleteCopyMeter----Param:{},{},{}", id, siteId, orgId);

        try {
            DailyCopyMeterVo dailyCopyMeterVo = dailyCopyMeterClient.findCopyMeterById(id, siteId, orgId);
            if (dailyCopyMeterVo != null) {
                //查询状态
                FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(CopyMeterCommon.COPY_METER_ORDER_STATUS, dailyCopyMeterVo.getStatus(), dailyCopyMeterVo.getSiteId(), dailyCopyMeterVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
                if (statusDomain != null) {
                    dailyCopyMeterVo.setStatusDesc(statusDomain.getDescription());
                }
                //查询抄表人
                if (StringUtils.isNotEmpty(dailyCopyMeterVo.getCopyMeterPerson())) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(dailyCopyMeterVo.getCopyMeterPerson());
                    if (person != null) {
                        dailyCopyMeterVo.setCopyMeterPersonName(person.getName());
                    }
                }
                List<DailyCopyMeterDetailForList> cmdList = dailyCopyMeterClient.findCopyMeterDetailByCopyMeterId(id);
                if (cmdList != null && cmdList.size() > 0) {
                    List<DailyCopyMeterDetailVo> list = new ArrayList<>();
                    for (DailyCopyMeterDetailForList dailyCopyMeterDetailForList : cmdList) {
                        DailyCopyMeterDetailVo dailyCopyMeterDetailVo = new DailyCopyMeterDetailVo();
                        //查询设备
                        AssetVoForDetail assetVoForDetail = assetClient.findById(user.getName(), dailyCopyMeterDetailForList.getMeterId());
                        if (assetVoForDetail != null) {
                            BeanUtils.copyProperties(dailyCopyMeterDetailForList, dailyCopyMeterDetailVo);
                            dailyCopyMeterDetailVo.setCode(assetVoForDetail.getCode());
                            dailyCopyMeterDetailVo.setName(assetVoForDetail.getName());
                            ClassificationVoForDetail classificationVoForDetail = classificationClient.findById(user.getName(), assetVoForDetail.getClassificationId());
                            if (classificationVoForDetail != null) {
                                dailyCopyMeterDetailVo.setTypeName(classificationVoForDetail.getName());
                            }
                            LocationVoForDetail locationVoForDetail = locationClient.findById(user.getName(), assetVoForDetail.getLocationId());
                            if (locationVoForDetail != null) {
                                dailyCopyMeterDetailVo.setLocationName(locationVoForDetail.getName());
                            }
                            AssetEnergyPriceVo energyPriceDetail = assetEnergyPriceClient.findEnergyPriceDetail(assetVoForDetail.getEnergyTypeId());
                            if (energyPriceDetail != null) {
                                dailyCopyMeterDetailVo.setUnit(energyPriceDetail.getPriceUnit());
                            }
                        }
                        list.add(dailyCopyMeterDetailVo);
                        //
                    }
                    dailyCopyMeterVo.setCopyMeterDetailVos(list);
                }
            }
            if (dailyCopyMeterVo != null) {
                return EnerbosMessage.createSuccessMsg(dailyCopyMeterVo, "根据id查找抄表单成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(null, "根据id查找抄表单失败", "");
            }
        } catch (Exception e) {
            logger.error("----- deleteCopyMeter------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据id查找抄表单详细列表
     *
     * @param meterId 抄表单id
     * @param siteId  站点
     * @param orgId   组织
     * @return
     */
    @ApiOperation(value = "根据id查找抄表单详细列表", response = DailyCopyMeterDetailForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/findCopyMeterDetails", method = RequestMethod.GET)
    public EnerbosMessage findCopyMeterDetails(@RequestParam("meterId") String meterId, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId, Principal user) {
        logger.info(" findCopyMeterDetails----Param:{},{},{}", meterId, siteId, orgId);
        try {
            EnerbosPage<DailyCopyMeterDetailForList> dailyCopyMeterDetailForListEnerbosPage = dailyCopyMeterClient.findCopyMeterDetailByMeterId(meterId, siteId, orgId);
            if (dailyCopyMeterDetailForListEnerbosPage != null) {
                List<DailyCopyMeterDetailForList> dailyCopyMeterDetailForLists = dailyCopyMeterDetailForListEnerbosPage.getList();
                for (DailyCopyMeterDetailForList dailyCopyMeterDetailForList : dailyCopyMeterDetailForLists) {
                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(CopyMeterCommon.COPY_METER_ORDER_STATUS, dailyCopyMeterDetailForList.getStatus(), siteId, orgId, PatrolCommon.PRODUCT_EAM);
                    if (statusDomain != null) {
                        dailyCopyMeterDetailForList.setStatusDesc(statusDomain.getDescription());
                    }
                    DailyCopyMeterDetailVo dailyCopyMeterDetailVo = new DailyCopyMeterDetailVo();
                    //查询设备
                    AssetVoForDetail assetVoForDetail = assetClient.findById(user.getName(), dailyCopyMeterDetailForList.getMeterId());
                    if (assetVoForDetail != null) {
                        BeanUtils.copyProperties(dailyCopyMeterDetailForList, dailyCopyMeterDetailVo);
                        dailyCopyMeterDetailForList.setMeterCode(assetVoForDetail.getCode());
                        dailyCopyMeterDetailForList.setMeterName(assetVoForDetail.getName());
                        ClassificationVoForDetail classificationVoForDetail = classificationClient.findById(user.getName(), assetVoForDetail.getClassificationId());
                        if (classificationVoForDetail != null) {
                            dailyCopyMeterDetailForList.setMeterType(classificationVoForDetail.getName());
                        }
                        LocationVoForDetail locationVoForDetail = locationClient.findById(user.getName(), assetVoForDetail.getLocationId());
                        if (locationVoForDetail != null) {
                            dailyCopyMeterDetailForList.setLocationName(locationVoForDetail.getName());
                        }
                        AssetEnergyPriceVo energyPriceDetail = assetEnergyPriceClient.findEnergyPriceDetail(assetVoForDetail.getEnergyTypeId());
                        if (energyPriceDetail != null) {
                            dailyCopyMeterDetailForList.setPriceUnit(energyPriceDetail.getPriceUnit());
                        }
                    }
                }
                return EnerbosMessage.createSuccessMsg(dailyCopyMeterDetailForListEnerbosPage, "根据id查找抄表单详细列表成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(dailyCopyMeterDetailForListEnerbosPage, "根据id查找抄表单详细列表失败", "");
            }
        } catch (Exception e) {
            logger.error("----- findCopyMeterDetails------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据id删除抄表单详细列表
     *
     * @param ids 抄表单id
     * @return
     */
    @ApiOperation(value = "根据id删除抄表单详细列表", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/deleteCopyMeterDetail", method = RequestMethod.GET)
    public EnerbosMessage deleteCopyMeterDetail(@RequestParam("ids") String[] ids) {

        logger.info(" deleteCopyMeterDetail----Param:{}", ids);

        try {
            boolean isOk = dailyCopyMeterClient.deleteCopyMeterDetail(ids);

            if (isOk) {


                return EnerbosMessage.createSuccessMsg(isOk, "根据id删除抄表单详细列表成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isOk, "根据id删除抄表单详细列表失败", "");
            }
        } catch (Exception e) {
            logger.error("----- deleteCopyMeterDetail------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据抄表id查询详细列表
     *
     * @param id 抄表id
     * @return
     */
    @ApiOperation(value = "根据id删除抄表单详细列表", response = DailyCopyMeterDetailForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/findCopyMeterByMeterId", method = RequestMethod.GET)
    public EnerbosMessage findCopyMeterByMeterId(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId, Principal user) {

        logger.info(" findCopyMeterByMeterId----Param:{}", id);

        try {
            EnerbosPage<DailyCopyMeterDetailForList> dailyCopyMeterDetailForListEnerbosPage = dailyCopyMeterClient.findCopyMeterDetailByMeterId(id, siteId, orgId);

            if (dailyCopyMeterDetailForListEnerbosPage != null) {


                List<DailyCopyMeterDetailForList> dailyCopyMeterDetailForLists = dailyCopyMeterDetailForListEnerbosPage.getList();
                for (DailyCopyMeterDetailForList dailyCopyMeterDetailForList : dailyCopyMeterDetailForLists) {
                    /*String meterid = dailyCopyMeterDetailForList.getMeterId();
                    // 调用台帐信息
                    AssetVoForDetail assetVoForDetail = assetClient.findById(user.getName(), meterid);
                    dailyCopyMeterDetailForList.setCode(assetVoForDetail.getCode());
                    dailyCopyMeterDetailForList.setDescription(assetVoForDetail.getClassification().getCode());
                    dailyCopyMeterDetailForList.setLocationCode(assetVoForDetail.getLocation().getCode());
                    dailyCopyMeterDetailForList.setLocationName(assetVoForDetail.getLocation().getName());
                    dailyCopyMeterDetailForList.setMeterLevel(assetVoForDetail.getMeterLevel());
                    dailyCopyMeterDetailForList.setConsumeEnergyType(assetVoForDetail.getConsumeEnergyType());

                    String energyTypeId = assetVoForDetail.getEnergyTypeId();
                    AssetEnergyPriceVo assetEnergyPriceVo = assetEnergyPriceClient.findEnergyPriceDetail(energyTypeId);
                    if (assetEnergyPriceVo != null) {
                        dailyCopyMeterDetailForList.setFillFormId(assetEnergyPriceVo.getFillFormId());
                        dailyCopyMeterDetailForList.setPriceUnit(assetEnergyPriceVo.getPriceUnit());
                        dailyCopyMeterDetailForList.setPrice(assetEnergyPriceVo.getPrice());
                    }*/
                }

                return EnerbosMessage.createSuccessMsg(dailyCopyMeterDetailForListEnerbosPage, "根据id查找抄表单详细列表成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(dailyCopyMeterDetailForListEnerbosPage, "根据id查找抄表单详细列表失败", "");
            }
        } catch (Exception e) {
            logger.error("----- findCopyMeterByMeterId------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    @ApiOperation(value = "查询仪表台帐列表", response = AssetVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/findMeters", method = RequestMethod.POST)
    public EnerbosMessage findMeters(@RequestBody AssetVoForFilter assetVoForFilter, Principal user) {

        logger.info(" findMeters----Param:{}", assetVoForFilter);

        EnerbosPage<AssetVoForList> assets = assetClient.findPage(user.getName(), assetVoForFilter);
        try {
            if (assets != null) {

                List<AssetVoForList> assetVoForLists = assets.getList();

//                private String fillFormId; classificationParentName 字段对应

//                private String priceUnit; spaceName
//                private double price; refType


                for (AssetVoForList assetVoForList : assetVoForLists) {

                    String energyTypeId = assetVoForList.getEnergyTypeId();

                    if (StringUtils.isNotEmpty(energyTypeId)) {
                        AssetEnergyPriceVo assetEnergyPriceVo = assetEnergyPriceClient.findEnergyPriceDetail(energyTypeId);
                        if (assetEnergyPriceVo != null) {
                            assetVoForList.setClassificationName(assetEnergyPriceVo.getFillFormId());
                            assetVoForList.setSpaceName(assetEnergyPriceVo.getPriceUnit());
                            assetVoForList.setRefType(String.valueOf(assetEnergyPriceVo.getPrice()));
                        }
                    }

                }

                return EnerbosMessage.createSuccessMsg(assets, "查询仪表台帐列表", "");
            }
        } catch (Exception e) {
            logger.error("----- deleteCopyMeterDetail------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
        return EnerbosMessage.createSuccessMsg(assets, "查询仪表台帐列表", "");
    }


    @ApiOperation(value = "收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/collect", method = RequestMethod.POST)
    public EnerbosMessage collect(@RequestParam("id") String id, @RequestParam("type") String type, Principal user) {

        logger.info("/eam/open/material/collect, id: {}, type: {}", id, type);
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            dailyCommonClient.collect(id, Common.EAM_PROD_IDS[0], type, personId);
            return EnerbosMessage.createSuccessMsg(true, "收藏成功", "");
        } catch (Exception e) {
            logger.error("-----material/collect ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    @ApiOperation(value = "取消收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/copyMeter/cancelCollect", method = RequestMethod.POST)
    public EnerbosMessage cancelCollect(@RequestParam("id") String id, @RequestParam("type") String type, Principal user) {
        logger.info("/eam/open/material/cancelCollect, id: {}, type: {}", id, type);
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            dailyCommonClient.cancelCollectByCollectIdAndType(id, Common.EAM_PROD_IDS[0], type, personId);
            return EnerbosMessage.createSuccessMsg(true, "取消收藏成功", "");
        } catch (Exception e) {
            logger.error("-----material/cancelCollect ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
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
            if (org.apache.commons.lang3.StringUtils.isEmpty(copyMeterOrder.getSiteId())) {
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
                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(businessKey);
                processVo.setProcessKey(processKey);
                processVo.setUserId(userId);
                variables.put(CopyMeterCommon.ASSIGN_USER, userId);
                variables.put("userId", userId);
                variables.put(CopyMeterCommon.ORDER_NUM, copyMeterOrder.getCopyMeterNum());
                variables.put(CopyMeterCommon.ORDER_DESCRIPTION, copyMeterOrder.getDescription());
                logger.debug("/eam/open/copyMeterOrder/assignWorkOrderWorkFlow, processKey: {}", processKey);
                processVo = workflowClient.startProcess(variables, processVo);

                if (null == processVo || "".equals(processVo.getProcessInstanceId())) {
                    return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
                }
                //提报，修改基本字段保存
                copyMeterOrder.setProcessInstanceId(processVo.getProcessInstanceId());
                copyMeterOrder = dailyCopyMeterClient.saveCopyMeterOrderFlow(copyMeterOrder);
            }
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
                    message = "抄表工单-分派成功";
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

    private Map getExtraMap(CopyMeterOrderForWorkFlowVo copyMeterOrder) {
        Map extras = new HashMap<>();
        extras.put("getBusinessDataByCurrentUser", "true");
        extras.put("workorderid", copyMeterOrder.getId());
        extras.put("notificationType", CopyMeterCommon.WFS_PROCESS_KEY);
        return extras;
    }

}


