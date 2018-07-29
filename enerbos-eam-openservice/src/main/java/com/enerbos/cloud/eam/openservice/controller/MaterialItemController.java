package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.MeasureClient;
import com.enerbos.cloud.ams.vo.measure.MeasureVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.MaintenanceWorkOrderActualItemClient;
import com.enerbos.cloud.eam.client.MaterialInventoryClient;
import com.enerbos.cloud.eam.client.MaterialItemClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.vo.MaterialItemVo;
import com.enerbos.cloud.eam.vo.MaterialItemVoForAssertList;
import com.enerbos.cloud.eam.vo.MaterialItemVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialItemVoForList;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import io.swagger.annotations.*;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
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
 * @Description 物资台账台账
 */
@RestController
@Api(description = "物资台账台账(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaterialItemController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MaterialItemClient materialItemClient;

    @Autowired
    public MaterialInventoryClient materialInventoryClient;

    @Autowired
    public MeasureClient measureClient;

    @Autowired
    public SiteClient siteClient;

    @Autowired
    public OrgClient orgClient;

    @Autowired
    public MaintenanceWorkOrderActualItemClient maintenanceWorkOrderActualItemClient;


    /**
     * 分页查询物资台账管理列表
     *
     * @param materialItemVoForFilter 物资台账查询条件实体{@link com.enerbos.cloud.eam.vo.MaterialItemVoForFilter}
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资台账管理列表", response = MaterialItemVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/item/findItems", method = RequestMethod.POST)
    public EnerbosMessage findItems(@RequestBody MaterialItemVoForFilter materialItemVoForFilter,Principal user) {
        try {

            EnerbosPage<MaterialItemVoForList> pageInfo = materialItemClient
                    .findItems(materialItemVoForFilter);


            return EnerbosMessage.createSuccessMsg(pageInfo, "物资台账查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/item/findItems------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询不在itemNum集合中的物资
     *
     * @param itemNums itenNums 集合
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 查询集合
     */
    @ApiOperation(value = "查询不在itemNum集合中的物资", response = MaterialItemVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/item/findItemsNotInResevies", method = RequestMethod.GET)
    public EnerbosMessage findItemsNotInResevies(@RequestParam("itemNums") String[] itemNums, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        try {


            EnerbosPage<MaterialItemVoForList> pageInfo = materialItemClient
                    .findItemsNotInResevies(itemNums, pageSize, pageNum, siteId, orgId);
            return EnerbosMessage.createSuccessMsg(pageInfo, "查询不在itemNum集合中的物资成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/item/findItemsNotInResevies------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新建物资台账
     *
     * @param MaterialItemVo 物资台账实体 {@link com.enerbos.cloud.eam.vo.MaterialItemVo}
     * @param user           用户
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "新建物资台账", response = MaterialItemVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/item/saveItem", method = RequestMethod.POST)
    public EnerbosMessage saveItem(
            @ApiParam(value = "物资台账对象", required = true) @RequestBody @Valid MaterialItemVo MaterialItemVo,
            Principal user) {

        try {
            logger.info("/eam/open/item/saveItem------------------------->>>>>>MaterialItemVo:{}", MaterialItemVo);
            String userName = user.getName();
            return EnerbosMessage.createSuccessMsg(materialItemClient.saveItem(MaterialItemVo, userName),
                    "新建物资台账成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/item/saveItem ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资台账
     *
     * @param MaterialItemVo 物资台账实体{@link com.enerbos.cloud.eam.vo.MaterialItemVo}
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "修改物资台账", response = MaterialItemVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/item/updateItem", method = RequestMethod.POST)
    public EnerbosMessage updateItem(
            @ApiParam(value = "物资台账对象", required = true) @RequestBody @Valid MaterialItemVo MaterialItemVo) {

        try {
            return EnerbosMessage.createSuccessMsg(
                    materialItemClient.updateItem(MaterialItemVo), "更新物资台账成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/item/updateItem ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 根据ID删除物资台账台账
     *
     * @param ids 物资台账id数组
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资台账台账", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/item/deleteItem", method = RequestMethod.POST)
    public EnerbosMessage deleteItem(
            @ApiParam(value = "需要删除的物资台账台账ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = true) String[] ids) {


        try {
            String message = materialItemClient.deleteItem(ids);

            if (message.equals(HttpStatus.OK.toString())) {
                return EnerbosMessage.createSuccessMsg(true, "删除物资台账成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(false, message, "");
            }

        } catch (Exception e) {
            logger.error("-----/eam/open/item/deleteItem ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID查询物资台账详细信息
     *
     * @param id 物资台账id
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询物资台账详细信息", response = MaterialItemVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "库存编码", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/item/findItemById", method = RequestMethod.GET)
    public EnerbosMessage findItemDetail(@RequestParam("id") String id) {

        try {
            MaterialItemVo MaterialItemVo = materialItemClient.findItemDetail(id);

            logger.info("--------------{}", MaterialItemVo);
            if (MaterialItemVo != null) {
                String unit = MaterialItemVo.getIssueUnit();
                String orderUnit = MaterialItemVo.getOrderUnit();

                if (StringUtils.isNotEmpty(unit)) {
                    MeasureVo measureVo = measureClient.findById(unit);
                    if (measureVo != null) {
                        MaterialItemVo.setIssueUnitName(measureVo.getDescription());
                    }
                }

                if (StringUtils.isNotEmpty(orderUnit)) {
                    MeasureVo ordermeasureVo = measureClient.findById(orderUnit);
                    if (ordermeasureVo != null) {
                        MaterialItemVo.setOrderUnitName(ordermeasureVo.getDescription());
                    }
                }

            }
            return EnerbosMessage.createSuccessMsg(MaterialItemVo, "查询物资台账详细信息成功", "");
        } catch (Exception e) {
            logger.error("-----findItemDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资台账状态
     *
     * @param id     物资台账id
     * @param status 状态（活动不活动）
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "修改物资台账状态", response = MaterialItemVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/item/updateItemStatus", method = RequestMethod.POST)
    public EnerbosMessage updateItemStatus(
            @RequestParam("id") String[] id,
            @RequestParam("status") String status) {

        try {
            boolean b = materialItemClient.updateItemStatus(id, status);
            if (b) {
                return EnerbosMessage.createSuccessMsg(b, "修改物资台账状态成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(b, "修改物资台账状态失败", "");
            }

        } catch (Exception e) {
            logger.error("-----updateItemStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }


    /**
     * 根据设备id查询物资
     *
     * @param id       设备id
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据设备id查询物资", response = MaterialItemVoForAssertList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/item/findItemByAssertId", method = RequestMethod.GET)
    public EnerbosMessage findItemByAssertId(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {

        try {
            logger.info("findItemByAssertId:id:{},pageSize:{},pageNum:{}", id, pageSize, pageNum);

            List<String> itemList = maintenanceWorkOrderActualItemClient.findItemIdByAssetId(id);

            if (itemList.size() > 0) {
                String[] itemIds = itemList.toArray(new String[itemList.size()]);
                EnerbosPage<MaterialItemVoForAssertList> pageInfo = materialItemClient.findItemByAssertId(itemIds, pageSize, pageNum);
                return EnerbosMessage.createSuccessMsg(pageInfo, "根据设备id查询物资", "");
            } else {
                return EnerbosMessage.createSuccessMsg(null, "根据设备id在关联工单中未使用物料", "");
            }
        } catch (Exception e) {
            logger.error("-----findItemByAssertId ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }


    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param id
     * @param qrCodeNum
     * @param siteId
     * @return
     */
    public List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(List<String> id, String qrCodeNum, String siteId,String userName) {

        logger.info("param- - - > id:{},qrcodeNum:{},siteId:{}", id, qrCodeNum, siteId);
        List<MaterialItemVo> materialItemVolist = new ArrayList<>();

        if (id.size() > 0) {
            String[] ids = id.toArray(new String[id.size()]);
            materialItemVolist = materialItemClient.findByIdAndQrCodeNumAndSiteId(ids, qrCodeNum, siteId);
        } else {
            return materialItemVolist;
        }

        try {

            for (MaterialItemVo materialItemVo : materialItemVolist) {

                String unit = materialItemVo.getIssueUnit();
                String orderUnit = materialItemVo.getOrderUnit();

                if (StringUtils.isNotEmpty(unit)) {
                    MeasureVo measureVo = measureClient.findById(unit);
                    if (measureVo != null) {
                        materialItemVo.setIssueUnitName(measureVo.getDescription());
                    }
                }

                if (StringUtils.isNotEmpty(orderUnit)) {
                    MeasureVo ordermeasureVo = measureClient.findById(orderUnit);
                    if (ordermeasureVo != null) {
                        materialItemVo.setOrderUnitName(ordermeasureVo.getDescription());
                    }
                }


                SiteVoForDetail site = siteClient.findById(materialItemVo.getSiteId());
                if (site != null) {
                    materialItemVo.setSiteName(site.getName());
                }

                OrgVoForDetail orgVo = orgClient.findById(materialItemVo.getOrgId());
                if (orgVo != null) {
                    materialItemVo.setOrgName(orgVo.getName());
                }
            }
        } catch (Exception e) {
            logger.error("-----findByIdAndQrCodeNumAndSiteId ------", e);
        }
        return materialItemVolist;
    }

    /**
     * 设置是否更新状态
     *
     * @param id
     * @param siteId
     * @param b
     * @return
     */
    public boolean updateIsupdatedata(String id, String siteId, boolean b) {
        try {
            materialItemClient.updateIsupdatedata(id, siteId, b);
            return true;
        } catch (Exception e) {
            logger.error("----- updateIsupdatedata ------", e);
        }
        return false;
    }


    @ApiOperation(value = "收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/material/collect", method = RequestMethod.POST)
    public EnerbosMessage collect(@RequestParam("id") String id, @RequestParam("type") String type, Principal user) {

        logger.info("/eam/open/material/collect, id: {}, type: {}", id, type);
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            materialItemClient.collect(id, Common.EAM_PROD_IDS[0], type, personId);
            return EnerbosMessage.createSuccessMsg(true, "收藏成功", "");
        } catch (Exception e) {
            logger.error("-----material/collect ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    @ApiOperation(value = "取消收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/material/cancelCollect", method = RequestMethod.POST)
    public EnerbosMessage cancelCollect(@RequestParam("id") String id, @RequestParam("type") String type, Principal user) {
        logger.info("/eam/open/material/cancelCollect, id: {}, type: {}", id, type);
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            materialItemClient.cancelCollect(id, Common.EAM_PROD_IDS[0], type, personId);
            return EnerbosMessage.createSuccessMsg(true, "取消收藏成功", "");
        } catch (Exception e) {
            logger.error("-----material/cancelCollect ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }
}
