package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.MeasureClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.measure.MeasureVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.MaterialInventoryClient;
import com.enerbos.cloud.eam.client.MaterialItemClient;
import com.enerbos.cloud.eam.client.MaterialStoreRoomClient;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.tts.client.EamTimerTaskClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
 * @Description 物资库存
 */
@RestController
@Api(description = "物资库存(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaterialInventoryController {

    @Autowired
    public MaterialInventoryClient materialInventoryClient;
    @Autowired
    private DiscoveryClient client;

    @Autowired
    private MaterialItemClient materialItemClient;


    @Autowired
    private MaterialStoreRoomClient materialStoreRoomClient;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    public MeasureClient measureClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private EamTimerTaskClient eamTimerTaskClient ;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 分页查询物资库存列表
     *
     * @param materialInventoryVoForFilter 查询条件实体{@link com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资库存列表", response = MaterialInventoryVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "inventoryVoForFilter", value = "所有查询条件")})
    @RequestMapping(value = "/eam/open/invtentory/findInventorys", method = RequestMethod.POST)
    public EnerbosMessage findInventorys(
            @RequestBody MaterialInventoryVoForFilter materialInventoryVoForFilter) {
        try {
            EnerbosPage<MaterialInventoryVoForList> pageInfo = materialInventoryClient
                    .findInventorys(materialInventoryVoForFilter);

            if (pageInfo != null) {
                List<MaterialInventoryVoForList> inventoryList = pageInfo.getList();

                for (MaterialInventoryVoForList inventory : inventoryList) {
                    String orderUnit = inventory.getOrderUnit();

                    if(StringUtils.isNotEmpty(orderUnit)){
                        MeasureVo measureVo = measureClient.findById(orderUnit);

                        if (measureVo != null) {
                            inventory.setOrderUnitName(measureVo.getDescription());
                        }
                    }
//                    MeasureVo measureVo = measureClient.findById(orderUnit);
//
//                    if (measureVo != null) {
//                        inventory.setOrderUnitName(measureVo.getDescription());
//                    }

                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("inventoryStatus", inventory.getStatus(), inventory.getSiteId(), inventory.getOrgId(), "EAM");

                    if (statusDomain != null) {
                        inventory.setStatusName(statusDomain.getDescription());
                    }
                }
            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "物资库存查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/invtentory/findInventorys------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 查询未盘点的物资库存
     *
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资库存列表", response = MaterialInventoryVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/invtentory/findInventorysNotInCheck", method = RequestMethod.GET)
    public EnerbosMessage findInventorysNotInCheck(@RequestParam("ids") String[] ids, @RequestParam("storeroomid") String storeroomid, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        try {
            EnerbosPage<MaterialInventoryVoForList> pageInfo = materialInventoryClient
                    .findInventorysNotInCheck(ids, storeroomid, pageNum, pageSize, siteId, orgId);

            if (pageInfo != null) {
                List<MaterialInventoryVoForList> inventoryList = pageInfo.getList();

                for (MaterialInventoryVoForList inventory : inventoryList) {
                    String orderUnit = inventory.getOrderUnit();

                    if(StringUtils.isNotEmpty(orderUnit)){
                        MeasureVo measureVo = measureClient.findById(orderUnit);

                        if (measureVo != null) {
                            inventory.setOrderUnitName(measureVo.getDescription());
                        }
                    }
//                    MeasureVo measureVo = measureClient.findById(orderUnit);
//
//                    if (measureVo != null) {
//                        inventory.setOrderUnitName(measureVo.getDescription());
//                    }
                }
            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "物资库存查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/invtentory/findInventorysNotInCheck------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新建物资库存
     *
     * @param materialInventoryVo 物资库存实体{@link com.enerbos.cloud.eam.vo.MaterialInventoryVo}
     * @param user                用户名称
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "新建物资库存", response = MaterialInventoryVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/saveInventory", method = RequestMethod.POST)
    public EnerbosMessage saveInventory(
            @ApiParam(value = "物资库存对象", required = true) @RequestBody @Valid MaterialInventoryVo materialInventoryVo,
            Principal user) {
        try {

            String userName = user.getName();

       MaterialInventoryVo materialInventoryResult =    materialInventoryClient.saveInventory(
                    materialInventoryVo, userName) ;
            if(materialInventoryVo.getReorder()){
                String cron = "0 0 1 * * ?";
                eamTimerTaskClient.startEamInventoryTask(materialInventoryResult.getId(),cron,1,null,materialInventoryResult.getId()) ;
            }

            return EnerbosMessage
                    .createSuccessMsg(materialInventoryResult, "新建物资库存成功", "");

        } catch (Exception e) {
            logger.error("-----/eam/open/inventory/saveInventory ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资库存
     *
     * @param materialInventoryVo 物资库存实体{@link com.enerbos.cloud.eam.vo.MaterialInventoryVo}
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "修改物资库存", response = MaterialInventoryVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/updateInventory", method = RequestMethod.POST)
    public EnerbosMessage updateInventory(
            @ApiParam(value = "物资库存对象", required = true) @RequestBody @Valid MaterialInventoryVo materialInventoryVo) {
        try {

            logger.info("*****************************{}",materialInventoryVo);
            MaterialInventoryVo materialInventoryResult =    materialInventoryClient
                    .updateInventory(materialInventoryVo) ;
            if(materialInventoryVo.getReorder()){
                String cron = "0 0 1 * * ?";
                eamTimerTaskClient.startEamInventoryTask(materialInventoryResult.getId(),cron,1,null,materialInventoryResult.getId()) ;
            }
            return EnerbosMessage.createSuccessMsg(materialInventoryResult, "更新物资库存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/inventory/updateInventory ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID删除物资库存
     *
     * @param ids 物资库存id数组
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资库存", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/deleteInventory", method = RequestMethod.POST)
    public EnerbosMessage deleteInventory(
            @ApiParam(value = "需要删除的物资库存ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String ids[]) {

        logger.info("/eam/open/inventory/deleteInventory----->param:ids:{}", ids);
        try {
            boolean isSuccess = materialInventoryClient.deleteInventory(ids);
            if (isSuccess) {

                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资库存成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资库存失败", "");
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/inventory/deleteInventory ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据ID查询物资库存详细信息
     *
     * @param id 物资库存id
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询物资库存详细信息", response = MaterialInventoryVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "物资库存编码", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/inventory/findInventoryDetailById", method = RequestMethod.POST)
    public EnerbosMessage findInventoryDetail(
            @RequestParam(value = "id", required = true) String id) {

        try {
            MaterialInventoryVo materialInventoryVo = materialInventoryClient
                    .findInventoryDetail(id);

            if (materialInventoryVo != null) {
                String itemId = materialInventoryVo.getItemId();
                MaterialItemVo itemVo = materialItemClient.findItemDetail(itemId);
                if (itemVo != null) {
                    materialInventoryVo.setItemNum(itemVo.getItemNum());
                    materialInventoryVo.setItemName(itemVo.getDescription());
                }
                String siteId = materialInventoryVo.getSiteId();
                SiteVoForDetail siteVo = siteClient.findById(siteId);
                if (siteVo != null) {
                    materialInventoryVo.setSiteName(siteVo.getName());
                }
                String storeroomId = materialInventoryVo.getStoreroomId();
                MaterialStoreRoomVo materialStoreRoomVo = materialStoreRoomClient.findStoreRoomDetail(storeroomId);

                if (materialStoreRoomVo != null) {
                    materialInventoryVo.setStoreroomName(materialStoreRoomVo.getStoreroomName());
                    materialInventoryVo.setStoreroomNum(materialStoreRoomVo.getStoreroomNum());
                }

                String orderunit = materialInventoryVo.getOrderUnit();
                if(StringUtils.isNotEmpty(orderunit)){
                    MeasureVo measureVo = measureClient.findById(orderunit);

                    if (measureVo != null) {
                        materialInventoryVo.setOrderUnitName(measureVo.getDescription());
                    }
                }
//                MeasureVo measureVo = measureClient.findById(orderunit);
//                if (measureVo != null) {
//                    materialInventoryVo.setOrderUnitName(measureVo.getDescription());
//                }


                String issueunit = materialInventoryVo.getIssueUnit();
                if(StringUtils.isNotEmpty(issueunit)){
                    MeasureVo measureVo = measureClient.findById(issueunit);

                    if (measureVo != null) {
                        materialInventoryVo.setIssueUnitName(measureVo.getDescription());
                    }
                }
//                MeasureVo measureVoissue = measureClient.findById(issueunit);
//                if (measureVo != null) {
//                    materialInventoryVo.setIssueUnitName(measureVoissue.getDescription());
//                }

                String status = materialInventoryVo.getStatus();

                FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("inventoryStatus", status, materialInventoryVo.getSiteId(), materialInventoryVo.getOrgId(), "EAM");

                if (statusDomain != null) {
                    materialInventoryVo.setStatusName(statusDomain.getDescription());
                }

                return EnerbosMessage.createSuccessMsg(materialInventoryVo,
                        "根据ID查询物资库存详细信息成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(materialInventoryVo,
                        "id不存在", "");
            }


        } catch (Exception e) {
            logger.error("-----/eam/open/inventory/findInventoryDetail------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 更新物资库存状态
     *
     * @param id     物资库存id
     * @param status 状态{活动、不活动、草稿}
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "更新物资库存状态", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/updateInventoryStatus", method = RequestMethod.POST)
    public EnerbosMessage updateInventoryStatus(
            @RequestParam(value = "id", required = true) String[] id,
            @RequestParam(value = "status", required = true) String status) {
        try {

            logger.info("updateInventoryStatus == > param :{},{}", id, status);
            boolean result = materialInventoryClient.updateInventoryStatus(id, status);
            if (result) {
                return EnerbosMessage.createSuccessMsg(result, "更新物资库存状态成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(result, "更新物资库存状态失败", "");
            }
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/inventory/updateInventoryStatus------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据物资台账id 查询物资库存
     *
     * @param id       物资台账id
     * @param pageNum  页数
     * @param pageSize 每页显示条数
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据itemId查询物资库存", response = MaterialInventoryVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/findInventorysByItemId", method = RequestMethod.GET)
    public EnerbosMessage findInventorysByItemId(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "siteId", required = true) String siteId, @RequestParam(value = "orgId", required = true) String orgId) {

        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/inventory/findInventorysByItemId, host: [{}:{}], service_id: {}, param：Id=: {}",
                    instance.getHost(), instance.getPort(),
                    instance.getServiceId(), id);


            EnerbosPage<MaterialInventoryVoForList> pageInfo = materialInventoryClient.findInventorysByItemId(id, pageNum, pageSize, siteId, orgId);

            if (pageInfo != null) {
                List<MaterialInventoryVoForList> inventoryList = pageInfo.getList();

                for (MaterialInventoryVoForList inventory : inventoryList) {
                    String orderUnit = inventory.getOrderUnit();

                    if(StringUtils.isNotEmpty(orderUnit)){
                        MeasureVo measureVo = measureClient.findById(orderUnit);

                        if (measureVo != null) {
                            inventory.setOrderUnitName(measureVo.getDescription());
                        }
                    }


                    FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("inventoryStatus", inventory.getStatus(), inventory.getSiteId(), inventory.getOrgId(), "EAM");

                    if (statusDomain != null) {
                        inventory.setStatusName(statusDomain.getDescription());
                    }

                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo,
                    "根据itemId查询物资库存成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/inventory/findInventorysByItemId------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询不在Items中的物资库存
     *
     * @param ids      物资台账id  数组
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "查询不在Items中的物资库存", response = MaterialInventoryVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/findInventorysNotInItems", method = RequestMethod.POST)
    public EnerbosMessage findInventorysNotInItems(
            @RequestParam(value = "ids", required = false) String[] ids,
            @RequestParam(value = "pageNum", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", required = true) Integer pageSize, @RequestParam(value = "siteId", required = true) String siteId, @RequestParam(value = "orgId", required = true) String orgId) {

        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/inventory/findInventorysNotInItems, host: [{}:{}], service_id: {}, param：Ids=: {},pageNum:{},pageSize:{}",
                    instance.getHost(), instance.getPort(),
                    instance.getServiceId(), ids, pageNum, pageSize);

            EnerbosPage<MaterialInventoryVoForList> pageInfo = materialInventoryClient
                    .findInventorysNotInItems(ids, pageNum, pageSize, siteId, orgId);

            if (pageInfo != null) {

                List<MaterialInventoryVoForList> inventoryList = pageInfo.getList();

                for (MaterialInventoryVoForList inventory : inventoryList) {
                    String orderUnit = inventory.getOrderUnit();

                    if(StringUtils.isNotEmpty(orderUnit)){
                        MeasureVo measureVo = measureClient.findById(orderUnit);

                        if (measureVo != null) {
                            inventory.setOrderUnitName(measureVo.getDescription());
                        }
                    }
//                    MeasureVo measureVo = measureClient.findById(orderUnit);
//
//                    if (measureVo != null) {
//                        inventory.setOrderUnitName(measureVo.getDescription());
//                    }
                }
            }


            return EnerbosMessage.createSuccessMsg(pageInfo,
                    "查询不在items中物资库存成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/inventory/findInventorysNotInItems------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 查询不在itemNums中的物资库存
     *
     * @param storeroomId 库房ｉｄ
     * @param itemNums    物资台账编码  数组
     * @param pageNum     页数
     * @param pageSize    每页显示数
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "查询不在itemNums中的物资库存", response = MaterialInventoryVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/findInventorysNotInItemNum", method = RequestMethod.POST)
    public EnerbosMessage findInventorysNotInItemNum(@RequestParam(value = "storeroomId", required = false) String storeroomId,
                                                     @RequestParam(value = "itemNums", required = false) String[] itemNums,
                                                     @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = true) Integer pageSize, @RequestParam(value = "siteId", required = true) String siteId, @RequestParam(value = "orgId", required = true) String orgId) {

        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/inventory/findInventorysNotInItemNum, host: [{}:{}], service_id: {}, param：itemNums=: {},pageNum:{},pageSize:{},storeroomId:{}",
                    instance.getHost(), instance.getPort(),
                    instance.getServiceId(), itemNums, pageNum, pageSize, storeroomId);

            if (!StringUtils.isNotEmpty(storeroomId)) {
                return EnerbosMessage.createSuccessMsg(new EnerbosPage<MaterialInventoryVoForList>(),
                        "查询不在itemNum中物资库存成功", "");
            }

            EnerbosPage<MaterialInventoryVoForList> pageInfo = materialInventoryClient
                    .findInventorysNotInItemNum(storeroomId, itemNums, pageNum, pageSize, siteId, orgId);


            if (pageInfo != null) {

                List<MaterialInventoryVoForList> inventoryList = pageInfo.getList();

                for (MaterialInventoryVoForList inventory : inventoryList) {
                    String orderUnit = inventory.getOrderUnit();

                    if(StringUtils.isNotEmpty(orderUnit)){
                        MeasureVo measureVo = measureClient.findById(orderUnit);

                        if (measureVo != null) {
                            inventory.setOrderUnitName(measureVo.getDescription());
                        }
                    }
//                    MeasureVo measureVo = measureClient.findById(orderUnit);
//
//                    if (measureVo != null) {
//                        inventory.setOrderUnitName(measureVo.getDescription());
//                    }
                }
            }

            return EnerbosMessage.createSuccessMsg(pageInfo,
                    "查询不在itemNum中物资库存成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/inventory/findInventorysNotInItemNum------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据库房id 查询物资库存
     *
     * @param id       库房id
     * @param pageNum  页数
     * @param pageSize 每页显示条数
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据itemId查询物资库存", response = MaterialInventoryVoForStoreroomList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/inventory/findInventorysByStoreroomId", method = RequestMethod.GET)
    public EnerbosMessage findInventorysByStoreroomId(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {

        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/inventory/findInventorysByStoreroomId, host: [{}:{}], service_id: {}, param：Id=: {}",
                    instance.getHost(), instance.getPort(),
                    instance.getServiceId(), id);
            if (!StringUtils.isNotEmpty(id)) {
                return EnerbosMessage.createSuccessMsg(new EnerbosPage<MaterialInventoryVoForStoreroomList>(),
                        "根据StoreroomId查询物资库存成功", "");
            }
            EnerbosPage<MaterialInventoryVoForStoreroomList> pageinfo = materialInventoryClient
                    .findInventorysByStoreroomId(id, pageNum, pageSize, siteId, orgId);

            List<MaterialInventoryVoForStoreroomList> list = pageinfo.getList();
            for (MaterialInventoryVoForStoreroomList storeroomList : list) {
                String siteid = storeroomList.getSiteId();
                SiteVoForDetail siteVo = siteClient.findById(siteid);

                if (siteVo != null) {
                    storeroomList.setSiteName(siteVo.getName());
                }
            }
            return EnerbosMessage.createSuccessMsg(pageinfo,
                    "根据StoreroomId查询物资库存成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/inventory/findInventorysByStoreroomId------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }
}
