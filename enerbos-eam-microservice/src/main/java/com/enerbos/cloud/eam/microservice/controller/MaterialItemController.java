package com.enerbos.cloud.eam.microservice.controller;


import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.MaterialItem;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.service.MaterialInventoryService;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.vo.MaterialItemVo;
import com.enerbos.cloud.eam.vo.MaterialItemVoForAssertList;
import com.enerbos.cloud.eam.vo.MaterialItemVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialItemVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
 * @Description 物资管理
 */
@RestController
public class MaterialItemController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MaterialItemService itemService;

    @Autowired
    public MaterialInventoryService inventoryService;

    /**
     * 按照参数查询物资并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialItemVoForFilter 物资台账条件查询实体 {@link com.enerbos.cloud.eam.vo.MaterialItemVoForList}
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/item/findItems")
    public PageInfo<MaterialItemVoForList> findItems(
            @RequestBody MaterialItemVoForFilter materialItemVoForFilter) {

        logger.info("/eam/micro/item/findItems ： param : {}", materialItemVoForFilter);
        PageInfo<MaterialItemVoForList> pageInfoItemVo = null;

        try {
            pageInfoItemVo = itemService.findItems(materialItemVoForFilter);

            if (pageInfoItemVo != null) {
                List<MaterialItemVoForList> materialItemVoForLists = pageInfoItemVo.getList();
                for (MaterialItemVoForList materialItemVoForList : materialItemVoForLists) {
                    MaterialRfCollector materialRfCollector = itemService.findcollect(materialItemVoForList.getId(), "item", Common.EAM_PROD_IDS[0]);
                    if (materialRfCollector != null) {
                        materialItemVoForList.setCollect(true);
                    } else {
                        materialItemVoForList.setCollect(false);
                    }
                }
            }

        } catch (Exception e) {

            logger.error("--------/eam/micro/item/findItems-------", e);
        }

        return pageInfoItemVo;
    }

    /**
     * 根据设备id查询物资
     *
     * @param itemIds  物资编号
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 数据
     */
    @RequestMapping(value = "/eam/micro/item/findItemByAssertId", method = RequestMethod.GET)
    public PageInfo<MaterialItemVoForAssertList> findItemByAssertId(
            @RequestParam(value = "itemIds", required = false) String[] itemIds,
            @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
        logger.info("/eam/micro/item/findItems ： param : itemids{},pageSize{},pageNum{}", itemIds, pageSize, pageNum);
        PageInfo<MaterialItemVoForAssertList> pageInfoItemVo = null;

        try {
            pageInfoItemVo = itemService.findItemByAssertId(itemIds, pageNum, pageSize);
        } catch (Exception e) {

            logger.error("--------/eam/micro/item/findItems-------", e);
        }

        return pageInfoItemVo;
    }

    /**
     * 查询不在itemNum集合中的物资
     *
     * @param itemNums itenNums 集合
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 查询集合
     */
    @RequestMapping(value = "/eam/micro/item/findItemsNotInResevies", method = RequestMethod.GET)
    public PageInfo<MaterialItemVoForList> findItemsNotInResevies(@RequestParam("itemNums") String[] itemNums, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {

        logger.info("/eam/micro/item/findItemsNotInResevies ： param : {},{},{}", itemNums, pageSize, pageNum);
        PageInfo<MaterialItemVoForList> pageInfoItemVo = null;

        try {
            pageInfoItemVo = itemService.findItemsNotInResevies(itemNums, pageSize, pageNum, siteId, orgId);

        } catch (Exception e) {

            logger.error("--------/eam/micro/item/findItemsNotInResevies-------", e);
        }

        return pageInfoItemVo;
    }

    /**
     * 新建物资台账
     *
     * @param materialItemVo 新建的台账实体 {@link com.enerbos.cloud.eam.vo.MaterialItemVo}
     * @param userName       用户名称
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/item/saveItem", method = RequestMethod.POST)
    public MaterialItemVo saveItem(@RequestBody @Valid MaterialItemVo materialItemVo, @RequestParam(value = "userName", required = false) String userName) {

        logger.info("/eam/micro/item/saveItem param:materialItemVo:{},userName:{}", materialItemVo, userName);
        MaterialItem item = new MaterialItem();
        try {
            ReflectionUtils.copyProperties(materialItemVo, item, null);
            logger.info("the entity item:{}", item);
            item.setCreateUser(userName);
            item = itemService.saveItem(item);
            materialItemVo.setId(item.getId());
        } catch (Exception e) {
            logger.error("-----/eam/micro/item/saveItem ------", e);
        }

        return materialItemVo;
    }

    /**
     * 修改物资记录
     *
     * @param materialItemVo 修改的物资实体 {@link com.enerbos.cloud.eam.vo.MaterialItemVo}
     * @return 修改后的物资实体
     */
    @RequestMapping(value = "/eam/micro/item/updateItem", method = RequestMethod.POST)
    public MaterialItem updateItem(@RequestBody @Valid MaterialItemVo materialItemVo) {

        logger.info("/eam/micro/item/updateItem param materialItemVo:{}", materialItemVo);


        String itemid = materialItemVo.getId();
        MaterialItem item = this.findItemDetail(itemid);
        if (item != null) {
            try {
                materialItemVo.setCreateDate(item.getCreateDate());
                materialItemVo.setCreateUser(item.getCreateUser());
                materialItemVo.setUpdateDate(item.getUpdateDate());

                ReflectionUtils.copyProperties(materialItemVo, item, null);
            } catch (Exception e) {
                logger.error("----/eam/micro/item/updateItem ------", e);
            }
        }

        return itemService.updateItem(item);
    }

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     */
    @RequestMapping(value = "/eam/micro/item/deleteItem", method = RequestMethod.POST)
    public String deleteItem(
            @RequestParam(value = "ids", required = true) String[] ids) {
        logger.info("/eam/micro/item/deleteItem param ids:{}", ids);
        try {
            itemService.deleteItem(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/item/deleteItem--------------", e);
            return e.getMessage();
        }
        return HttpStatus.OK.toString();
    }

    /**
     * 查询物资详细信息
     *
     * @param id 物资id
     * @return 返回物资实体
     */
    @RequestMapping(value = "/eam/micro/item/findItemDetail", method = RequestMethod.GET)
    public MaterialItem findItemDetail(@RequestParam("id") String id) {
        logger.info("/eam/micro/item/findItemDetail param id:{}", id);
        MaterialItem item = null;
        try {
            item = itemService.findItemDetail(id);
        } catch (Exception e) {

            logger.error("-------/eam/micro/item/findItemDetail--------", e);
        }

        return item;

    }

    /**
     * 修改物资状态
     *
     * @param id     物资id
     * @param status 状态
     * @return 修改后返回的实体类
     */

    @RequestMapping(value = "/eam/micro/item/updateItemStatus", method = RequestMethod.POST)
    public boolean updateItemStatus(@RequestParam("id") String[] id,
                                    @RequestParam("status") Boolean status) {
        logger.info("updateItemStatus:param : Id:{},status:{}", id, status);
        MaterialItem item = null;
        try {
            for (String i : id) {
                item = itemService.findItemDetail(i);
                logger.info("--{}", item);
                item.setStatus(status);
                logger.info("===={}", item);
                item = itemService.saveItem(item);
            }
            return true;
        } catch (Exception e) {
            logger.error("--------/eam/micro/item/updateItemStatus-----", e);
            return false;
        }
    }

    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param ids
     * @param qrCodeNum
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/eam/micro/item/findByIdAndQrCodeNumAndSiteId", method = RequestMethod.POST)
    public List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(@RequestParam(value = "ids", required = false) String[] ids, @RequestParam(value = "qrCodeNum", required = false) String qrCodeNum, @RequestParam(value = "siteId", required = false) String siteId) {
        List<MaterialItemVo> patrolPointVolist = new ArrayList<>();
        try {
            patrolPointVolist = itemService.findByIdAndQrCodeNumAndSiteId(ids, qrCodeNum, siteId);
        } catch (Exception e) {
            logger.error("----- findByIdAndQrCodeNumAndSiteId ------", e);
        }
        return patrolPointVolist;
    }


    /**
     * 设置是否更新状态
     *
     * @param id
     * @param siteId
     * @param b
     * @return
     */
    @RequestMapping(value = "/eam/micro/item/updateIsupdatedata", method = RequestMethod.POST)
    public boolean updateIsupdatedata(String id, String siteId, boolean b) {
        try {
            itemService.updateIsupdatedata(id, siteId, b);
            return true;
        } catch (Exception e) {
            logger.error("---- updateIsupdatedata ------", e);
        }
        return false;
    }


    /**
     * 添加收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param type      类型
     * @param personId  人员id
     */
    @RequestMapping(value = "/eam/micro/material/collect", method = RequestMethod.POST)
    void collect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId) {
        itemService.collect(id, eamProdId, type, personId);
    }

    /**
     * 取消收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param type      类型
     * @param personId  人员id
     */
    @RequestMapping(value = "/eam/micro/material/cancelCollect", method = RequestMethod.POST)
    void cancelCollect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId) {
        itemService.cancelCollect(id, eamProdId, type, personId);
    }

}
