package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.service.CodeGeneratorService;
import com.enerbos.cloud.eam.microservice.service.MaterialInventoryService;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.vo.MaterialInventoryVo;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForList;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForStoreroomList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
 * @Description 库存管理
 */
@RestController
public class MaterialInventoryController {

    @Autowired
    public MaterialInventoryService inventoryService;

    @Autowired
    public CodeGeneratorService codeGeneratorService;

    @Autowired
    public MaterialItemService itemService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 按照参数查询库存并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialInventoryVoForFilter 物资库存条件查询实体 {@link com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter}
     * @return 封装的实体类集合
     * @throws Exception
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorys", method = RequestMethod.POST)
    public PageInfo<MaterialInventoryVoForList> findInventorys(
            @RequestBody MaterialInventoryVoForFilter materialInventoryVoForFilter) {


        logger.info("/eam/micro/inventory/findInventorys param ->materialInventoryVoForFilter：{}", materialInventoryVoForFilter);
        PageInfo<MaterialInventoryVoForList> pageInfoInventory = null;

        try {
            pageInfoInventory = inventoryService
                    .findInventorys(materialInventoryVoForFilter);

            if (pageInfoInventory != null) {
                List<MaterialInventoryVoForList> materialInventoryVoForLists = pageInfoInventory.getList();
                for (MaterialInventoryVoForList materialInventoryVoForList : materialInventoryVoForLists) {
                    MaterialRfCollector materialRfCollector = itemService.findcollect(materialInventoryVoForList.getId(), "inventory", Common.EAM_PROD_IDS[0]);
                    if (materialRfCollector != null) {
                        materialInventoryVoForList.setCollect(true);
                    } else {
                        materialInventoryVoForList.setCollect(false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("---/eam/inventory/findInventorys---", e);
        }

        return pageInfoInventory;
    }

    /**
     * 查询未盘点的物资库存
     *
     * @param ids         库存ids
     * @param storeroomid 库房id
     * @param pageNum     页数
     * @param pageSize    条数
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysNotInCheck", method = RequestMethod.GET)
    public PageInfo<MaterialInventoryVoForList> findInventorysNotInCheck(@RequestParam("ids") String[] ids, @RequestParam("storeroomid") String storeroomid, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {


        logger.info("/eam/micro/inventory/findInventorysNotInCheck param ->ids：{},storeroomid:{},pageSize:{},pageNum:{}", ids, storeroomid, pageNum
                , pageSize);
        PageInfo<MaterialInventoryVoForList> pageInfoInventory = null;

        try {
            pageInfoInventory = inventoryService
                    .findInventorysNotInCheck(ids, storeroomid, pageNum, pageSize, siteId, orgId);
        } catch (Exception e) {
            logger.error("---/eam/inventory/findInventorysNotInCheck---", e);
        }

        return pageInfoInventory;
    }

    /**
     * 新建 库存
     *
     * @param materialInventoryVo 新建的实体 {@link com.enerbos.cloud.eam.vo.MaterialInventoryVo}
     * @param userName            用户名称
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/inventory/saveInventory", method = RequestMethod.POST)
    public MaterialInventoryVo saveInventory(
            @RequestBody MaterialInventoryVo materialInventoryVo,
            @RequestParam(value = "userName", required = false) String userName) {

        logger.info("/eam/micro/inventory/saveInventory param ->materialInventoryVo：{},userName{}", materialInventoryVo, userName);
        MaterialInventory inventory = new MaterialInventory();
        try {

            inventory.setCreateUser(userName);
            ReflectionUtils
                    .copyProperties(materialInventoryVo, inventory, null);
            inventory = inventoryService.saveInventory(inventory);
            materialInventoryVo.setId(inventory.getId());

//            Boolean reorder = materialInventoryVo.getReorder();
//            if (reorder != null && reorder.booleanValue()) {
//                inventoryService.materialInventoryReorder(inventory, codeGeneratorService.getCode(materialInventoryVo.getSiteId(), "receive"));
//            }

        } catch (Exception e) {
            logger.error("-----/eam/micro/inventory/saveInventory------", e);
        }

        return materialInventoryVo;
    }

    /**
     * 修改库存记录
     *
     * @param materialInventoryVo 修改的库存实体 {@link com.enerbos.cloud.eam.vo.MaterialInventoryVo}
     * @return 修改后的库存实体
     */
    @RequestMapping(value = "/eam/micro/inventory/updateInventory", method = RequestMethod.POST)
    public MaterialInventory updateInventory(
            @RequestBody MaterialInventoryVo materialInventoryVo) {

        logger.info("/eam/micro/inventory/updateInventory param ->materialInventoryVo：{}", materialInventoryVo);
        MaterialInventory inventory = inventoryService
                .findInventoryDetail(materialInventoryVo.getId());

        if (inventory == null) {
            throw new EnerbosException(
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(), "无此记录");
        } else {
            try {
                ReflectionUtils.copyProperties(materialInventoryVo, inventory,
                        null);

                inventory = inventoryService.updateInventory(inventory);
//                if (inventory != null) {
//                    boolean reorder = materialInventoryVo.getReorder();
//                    if (reorder) {
//                        inventoryService.materialInventoryReorder(inventory, codeGeneratorService.getCode(materialInventoryVo.getSiteId(), "receive"));
//                    }
//                }
            } catch (Exception e) {
                logger.error(
                        "-----/eam/micro/inventory/updateInventory ------", e);
                return null;
            }
        }

        return inventory;
    }

    /**
     * 删除库存
     *
     * @param ids 库存ID数组
     */
    @RequestMapping(value = "/eam/micro/inventory/deleteInventory", method = RequestMethod.POST)
    public boolean deleteInventory(@RequestParam("ids") String ids[]) {

        logger.info("/eam/micro/inventory/deleteInventory param ->ids：{}", ids);
        try {
            inventoryService.deleteInventory(ids);
        } catch (Exception e) {
            logger.error("-----/eam/micro/inventory/deleteInventory ------", e);
            return false;
        }
        return true;
    }

    /**
     * 查询物资库存详细信息
     *
     * @param id 物资库存id
     * @return 返回物资库存实体
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventoryDetail", method = RequestMethod.POST)
    public MaterialInventory findInventoryDetail(
            @RequestParam(value = "id", required = false) String id) {

        logger.info("/eam/micro/inventory/findInventoryDetail param ->id：{}", id);
        MaterialInventory inventory = null;

        try {
            inventory = inventoryService.findInventoryDetail(id);
        } catch (Exception e) {
            logger.error("-----/eam/micro/inventory/findInventoryDetail ------", e);
        }

        return inventory;

    }

    /**
     * 修改库存状态
     *
     * @param MaterialInventoryVo 待修改的库存实体类
     * @return 修改后返回的实体类
     */
    /**
     * 修改物资库存状态
     *
     * @param id     物资库存id
     * @param status 状态 （草稿、活动、不活动）
     * @return
     */
    @RequestMapping(value = "/eam/micro/inventory/updateInventoryStatus", method = RequestMethod.POST)
    public boolean updateInventoryStatus(
            @RequestParam(value = "id", required = true) String[] id,
            @RequestParam(value = "status", required = true) String status) {

        logger.info("/eam/micro/inventory/updateInventoryStatus param ->id：{}，status:{}", id, status);


        MaterialInventory inventory = null;
        try {
            for (String i : id) {
                inventory = this.findInventoryDetail(i);
                if (inventory == null) {
                    throw new EnerbosException(
                            HttpStatus.INTERNAL_SERVER_ERROR.toString(), "无此记录");
                } else {
                    inventory.setStatus(status);
                    inventoryService.updateInventoryStatus(inventory);
                }
            }
            return true;
        } catch (EnerbosException e) {
            logger.error("-----------updateInventoryStatus------------", e);
            return false;
        }
    }

    /**
     * 查询物资台账在物资库房中的信息
     *
     * @param id       物资台账id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 返回封装实体
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysByItemId", method = RequestMethod.GET)
    public PageInfo<MaterialInventoryVoForList> findInventorysByItemId(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "siteId", required = true) String siteId, @RequestParam(value = "orgId", required = true) String orgId) {

        logger.info("/eam/micro/inventory/findInventorysByItemId -->param : id:{},pageNum:{},pageSize:{}", id, pageNum, pageSize);
        PageInfo<MaterialInventoryVoForList> pageInfoMaterialInventoryVoForList = null;

        try {
            pageInfoMaterialInventoryVoForList = inventoryService.findInventorysByItemId(
                    id, pageNum, pageSize, siteId, orgId);
        } catch (Exception e) {
            logger.error("---/eam/micro/inventory/findInventorysByItemId---", e);
        }

        return pageInfoMaterialInventoryVoForList;

    }

    /**
     * 去掉items的库存信息查询
     *
     * @param ids      去除的ids合集
     * @param pageNum  页数
     * @param pageSize 条数
     * @return 结果集
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysNotInItems", method = RequestMethod.POST)
    public PageInfo<MaterialInventoryVoForList> findInventorysNotInItems(
            @RequestParam(value = "ids", required = false) String[] ids,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        logger.info("/eam/micro/inventory/findInventorysByItemId -->param : ids:{},pageNum:{},pageSize:{}", ids, pageNum, pageSize);
        PageInfo<MaterialInventoryVoForList> MaterialInventoryVoForList = null;

        try {
            MaterialInventoryVoForList = inventoryService.findInventorysNotInItems(
                    ids, pageNum, pageSize);
        } catch (Exception e) {
            logger.error("---/eam/micro/inventory/findInventorysNotInItems---", e);
        }

        return MaterialInventoryVoForList;

    }

    /**
     * 查询不在itemNums中的物资库存
     *
     * @param itemNums 物资台账编码  数组
     * @param pageNum  页数
     * @param pageSize 条数
     * @return 结果集
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysNotInItemNum", method = RequestMethod.POST)
    public PageInfo<MaterialInventoryVoForList> findInventorysNotInItemNum(@RequestParam(value = "storeroomId", required = false) String storeroomId, @RequestParam(value = "itemNums", required = false) String[] itemNums, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("siteId") String siteId, @RequestParam("orgId") String
            orgId) {

        logger.info("/eam/micro/inventory/findInventorysNotInItemNum -->param : ids:{},pageNum:{},pageSize:{},storeroomId:{}", itemNums, pageNum, pageSize);
        PageInfo<MaterialInventoryVoForList> MaterialInventoryVoForList = null;

        try {
            MaterialInventoryVoForList = inventoryService.findInventorysNotInItemNum(storeroomId,
                    itemNums, pageNum, pageSize, siteId, orgId);
        } catch (Exception e) {
            logger.error("---/eam/micro/inventory/findInventorysNotInItemNum---", e);
        }

        return MaterialInventoryVoForList;

    }

    /**
     * 根据库房id 查询物资库存
     *
     * @param id       库房id
     * @param pageNum  页数
     * @param pageSize 每页显示条数
     * @return 结果列表
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysByStoreroomId", method = RequestMethod.GET)
    public PageInfo<MaterialInventoryVoForStoreroomList> findInventorysByStoreroomId(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {

        logger.info("/eam/micro/inventory/findInventorysByItemId -->param : id:{},pageNum:{},pageSize:{}", id, pageNum, pageSize);
        PageInfo<MaterialInventoryVoForStoreroomList> pageInfoMaterialInventoryVoForStoreroomList = null;

        try {
            pageInfoMaterialInventoryVoForStoreroomList = inventoryService.findInventorysByStoreroomId(
                    id, pageNum, pageSize, siteId, orgId);
        } catch (Exception e) {
            logger.error("---/eam/micro/inventory/findInventorysByItemId---", e);
        }

        return pageInfoMaterialInventoryVoForStoreroomList;

    }


}
