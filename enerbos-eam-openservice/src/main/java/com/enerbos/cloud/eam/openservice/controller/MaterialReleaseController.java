package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.MeasureClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.measure.MeasureVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017/3/31 10:20
 * @Description 物资发放
 */
@RestController
@Api(description = "物资发放(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaterialReleaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MaterialReleaseClient materialReleaseClient;

    @Autowired
    public DiscoveryClient client;

    @Autowired
    public MaterialInventoryClient materialInventoryClient;

    @Autowired
    public MaterialStoreRoomClient materialStoreRoomClient;

    @Autowired
    public SiteClient siteClient;

    @Autowired
    public MeasureClient measureClient;

    @Autowired
    public FieldDomainClient fieldDomainClient;


    @Autowired
    public MaintenanceWorkOrderActualItemClient maintenanceWorkOrderActualItemClient;

    @Autowired
    public RepairOrderClient repairOrderClient;

    /**
     * 分页查询物资发放列表
     *
     * @param materialReleaseVoForFilter 物资发放列表实体
     *                                   {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseVoForFilter}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资发放列表", response = MaterialReleaseVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/release/findMaterialRelease", method = RequestMethod.POST)
    public EnerbosMessage findMaterialRelease(@RequestBody
                                                      MaterialReleaseVoForFilter materialReleaseVoForFilter) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/goodsreceive/findMaterialRelease ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(),
                materialReleaseVoForFilter);
        try {
            EnerbosPage<MaterialReleaseVoForList> pageInfo = materialReleaseClient
                    .findMaterialRelease(materialReleaseVoForFilter);

            List<MaterialReleaseVoForList> releaselist = pageInfo.getList();

            for (MaterialReleaseVoForList release : releaselist) {

                String orderType = release.getOrderType();

                if (StringUtils.isNotEmpty(orderType)) {
                    FieldDomainValueVo ordertypeVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("woType", orderType, release.getSiteId(), release.getOrgId(), "EAM");
                    if (ordertypeVo != null) {
                        release.setOrderType(ordertypeVo.getDescription());
                    } else {
                        release.setOrderType("");
                    }

                }


                String releaseType = release.getReleaseType();

                if (StringUtils.isNotEmpty(releaseType)) {
                    FieldDomainValueVo releaseTypeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("useType", releaseType, release.getSiteId(), release.getOrgId(), "EAM");
                    if (releaseTypeDomain != null) {
                        release.setReleaseType(releaseTypeDomain.getDescription());
                    } else {
                        release.setReleaseType("");
                    }
                }


                String status = release.getStatus();
                FieldDomainValueVo fieldDomainValueVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("invsueStatus", status, release.getSiteId(), release.getOrgId(), "EAM");
                if (fieldDomainValueVo != null) {
                    release.setStatus(fieldDomainValueVo.getDescription());
                } else {
                    release.setStatus("");
                }

            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "物资发放查询成功", "");
        } catch (Exception e) {
            logger.error("-----findMaterialRelease ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新建物资发放单
     *
     * @param materialReleaseVo 物资发放列表实体
     *                          {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "新建物资发放", response = MaterialReleaseVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/saveMaterialRelease", method = RequestMethod.POST)
    public EnerbosMessage saveMaterialRelease(
            @ApiParam(value = "物资发放", required = true) @RequestBody @Valid MaterialReleaseVo materialReleaseVo,
            Principal user) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/saveMaterialRelease ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(), materialReleaseVo);
        try {

            materialReleaseVo.setCreateUser(user.getName());

            MaterialReleaseVo materialRelease = materialReleaseClient
                    .saveMaterialRelease(materialReleaseVo);
            logger.info("/eam/open/release/saveMaterialRelease:{}", materialRelease);
            if (materialRelease != null) {

                return EnerbosMessage.createSuccessMsg(materialRelease, "新建物资发放成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(materialRelease, "新建物资发放失败", "");
            }

        } catch (Exception e) {
            logger.error("-----saveMaterialRelease ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID删除物资发放
     *
     * @param ids 物资发放id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资发放", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/deleteMaterialRelease", method = RequestMethod.POST)
    public EnerbosMessage deleteMaterialRelease(
            @ApiParam(value = "需要删除的库存ID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String ids[]) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/saveMaterialRelease ： Host：{},Port:{}  param ->ids:{}",
                instance.getHost(), instance.getPort(), ids);
        try {
            boolean isSuccess = materialReleaseClient
                    .deleteMaterialRelease(ids);
            return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资发放成功", "");
        } catch (Exception e) {
            logger.error("-----deleteMaterialRelease ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id 物资发放ID
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询物资发放的详细信息", response = MaterialReleaseVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/findMaterialReleaseById", method = RequestMethod.GET)
    public EnerbosMessage findMaterialReleaseById(@RequestParam("id") String id) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/saveMaterialRelease ： Host：{},Port:{}  param ->id:{}",
                instance.getHost(), instance.getPort(), id);

        try {
            MaterialReleaseVo materialReleaseVo = materialReleaseClient
                    .findMaterialReleaseById(id);

            if (materialReleaseVo != null) {

                String status = materialReleaseVo.getStatus();
                FieldDomainValueVo fieldDomainValueVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("invsueStatus", status, materialReleaseVo.getSiteId(), materialReleaseVo.getOrgId(), "EAM");
                if (fieldDomainValueVo != null) {
                    materialReleaseVo.setStatusName(fieldDomainValueVo.getDescription());
                } else {
                    materialReleaseVo.setStatusName("");
                }


                String fromstoreroomid = materialReleaseVo.getFromStoreroomId();

                MaterialStoreRoomVo fromstoreroom = materialStoreRoomClient.findStoreRoomDetail(fromstoreroomid);
                if (fromstoreroom != null) {
                    materialReleaseVo.setFromStoreroomName(fromstoreroom.getStoreroomName());
                    materialReleaseVo.setFromStoreroomNum(fromstoreroom.getStoreroomNum());
                }

                SiteVoForDetail site = siteClient.findById(materialReleaseVo.getSiteId());
                if (site != null) {
                    materialReleaseVo.setSiteName(site.getName());
                }
                return EnerbosMessage.createSuccessMsg(materialReleaseVo,
                        "根据ID查询物资发放详细信息成功", "");

            } else {
                return EnerbosMessage.createSuccessMsg(null,
                        "ID 不存在", "");
            }

        } catch (Exception e) {
            logger.error("-----findMaterialReleaseById ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询物资发放明细列表
     *
     * @param materialReleaseDetailVoForFilter 查询实体
     *                                         {@link com.enerbos.cloud.eam.vo.MaterialReleaseDetailVoForFilter }
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "查询物资发放明细列表", response = MaterialReleaseDetailVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/release/findMaterialReleaseDetail", method = RequestMethod.POST)
    public EnerbosMessage findMaterialReleaseDetail(
            @RequestBody MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/findMaterialReleaseDetail ： Host：{},Port:{}  param ->materialReleaseDetailVoForFilter:{}",
                instance.getHost(), instance.getPort(),
                materialReleaseDetailVoForFilter);

        try {
            EnerbosPage<MaterialReleaseDetailVoForList> pageInfo = materialReleaseClient
                    .findMaterialReleaseDetail(materialReleaseDetailVoForFilter);
            return EnerbosMessage.createSuccessMsg(pageInfo, "物资发放明细查询成功", "");
        } catch (Exception e) {
            logger.error("-----findMaterialReleaseDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 新建物资发放明细单
     *
     * @param materialReleaseDetailVo 物资发放列表实体
     *                                {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseDetailVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "新建物资发放明细", response = MaterialReleaseDetailVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/saveMaterialReleaseDetail", method = RequestMethod.POST)
    public EnerbosMessage saveMaterialReleaseDetail(
            @ApiParam(value = "物资发放", required = true) @RequestBody @Valid MaterialReleaseDetailVo materialReleaseDetailVo,
            Principal user) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/saveMaterialReleaseDetail ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(), materialReleaseDetailVo);
        try {

            materialReleaseDetailVo.setCreateUser(user.getName());

            MaterialReleaseDetailVo result = materialReleaseClient
                    .saveMaterialReleaseDetail(materialReleaseDetailVo);
            if (result != null) {
                return EnerbosMessage
                        .createSuccessMsg(result, "新建物资发放明细成功", "");
            } else {
                return EnerbosMessage
                        .createSuccessMsg(result, "新建物资发放明细失败", "");
            }
        } catch (Exception e) {
            logger.error("-----saveMaterialRelease ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资发放明细单
     *
     * @param materialReleaseVo 物资发放列表实体
     *                          {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseDetailVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "修改物资发放", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/updateMaterialRelease", method = RequestMethod.POST)
    public EnerbosMessage updateMaterialRelease(
            @ApiParam(value = "修改物资发放", required = true) @RequestBody @Valid MaterialReleaseVo materialReleaseVo,
            Principal user) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/saveMaterialReleaseDetail ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(), materialReleaseVo);
        try {

            MaterialReleaseVo materialRelease = materialReleaseClient.updateMaterialRelease(materialReleaseVo);
            if (materialRelease != null) {
                return EnerbosMessage.createSuccessMsg(materialRelease, "修改物资发放成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(materialRelease, "修改物资发放失败", "");
            }

        } catch (Exception e) {
            logger.error("-----updateMaterialReleaseDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID删除物资发放明细
     *
     * @param ids 物资发放明细id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资发放明细", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/deleteMaterialReleaseDetail", method = RequestMethod.POST)
    public EnerbosMessage deleteMaterialReleaseDetail(
            @ApiParam(value = "需要删除的库存ID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String ids[]) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/deleteMaterialReleaseDetail ： Host：{},Port:{}  param ->ids:{}",
                instance.getHost(), instance.getPort(), ids);
        try {
            boolean isSuccess = materialReleaseClient
                    .deleteMaterialReleaseDetail(ids);
            if (isSuccess) {

                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资发放成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资发放失败", "");
            }
        } catch (Exception e) {
            logger.error("-----deleteMaterialReleaseDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据ID修改状态
     *
     * @param ids 物资发放明细id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资发放明细", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/updateMaterialReleaseStatus", method = RequestMethod.POST)
    public EnerbosMessage updateMaterialReleaseStatus(
            @ApiParam(value = "需要删除的库存ID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String ids[], @RequestParam("status") String status) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/deleteMaterialReleaseDetail ： Host：{},Port:{}  param ->ids:{}",
                instance.getHost(), instance.getPort(), ids);
        try {
            String message = materialReleaseClient
                    .updateMaterialReleaseStatus(ids, status);
            if (HttpStatus.OK.toString().equals(message)) {
                return EnerbosMessage.createSuccessMsg(true, "修改物资发放状态成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(false, message, "");
            }
        } catch (Exception e) {
            logger.error("-----deleteMaterialReleaseDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据工单id查询物资发放情况
     *
     * @param id 工单id
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据工单id查询物资发放情况", response = MaterialInventoryVoForReleaseList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/release/findItemInReleaseByorderId", method = RequestMethod.GET)
    public EnerbosMessage findItemInReleaseByorderId(@RequestParam("id") String id, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/release/findItemInReleaseByorderId ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(),
                id);
        try {
            RepairOrderFlowVo repairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(id);


            EnerbosPage<MaterialInventoryVoForReleaseList> pageInfo = materialReleaseClient
                    .findItemInReleaseByorderId(repairOrderFlowVo.getWorkOrderNum(), pageSize, pageNum);

            if (pageInfo != null) {

                List<MaterialInventoryVoForReleaseList> releaselist = pageInfo.getList();

                for (MaterialInventoryVoForReleaseList release : releaselist) {

                    String ordertype = release.getOrderUnit();

                    if (StringUtils.isNotEmpty(ordertype)) {
                        MeasureVo measureVo = measureClient.findById(ordertype);
                        if (measureVo != null) {
                            release.setOrderUnitName(measureVo.getDescription());
                        }
                    }
                    String storeroomid = release.getStoreroomId();

                    MaterialStoreRoomVo storeroom = materialStoreRoomClient.findStoreRoomDetail(storeroomid);
                    if (storeroom != null) {
                        release.setStoreroomName(storeroom.getStoreroomName());
                    }
                }
            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "物资发放查询成功", "");
        } catch (Exception e) {
            logger.error("-----findItemInReleaseByorderId ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

}
