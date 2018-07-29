package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceive;
import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceiveDetail;
import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.service.MaterialGoodsReceiveDetailService;
import com.enerbos.cloud.eam.microservice.service.MaterialGoodsReceiveService;
import com.enerbos.cloud.eam.microservice.service.MaterialInventoryService;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
 * @Description 物资接收单
 */
@RestController
public class MaterialGoodsReceiveController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public MaterialGoodsReceiveService materialGoodsReceiveService;

    @Autowired
    public MaterialGoodsReceiveDetailService materialGoodsReceiveDetailService;

    @Autowired
    public MaterialInventoryService materialInventoryService;

    @Autowired
    public MaterialItemService itemService;

    /**
     * 按照参数查询物资接收单并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialGoodsReceiveVoForFilter 条件查询实体 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForFilter}
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/findGoodsReceives", method = RequestMethod.POST)
    public PageInfo<MaterialGoodsReceiveVoForList> findGoodsReceives(
            @RequestBody MaterialGoodsReceiveVoForFilter materialGoodsReceiveVoForFilter) {

        logger.info(
                "/eam/micro/goodsreceive/findGoodsReceives param entity materialGoodsReceiveVoForFilter :{}",
                materialGoodsReceiveVoForFilter);
        PageInfo<MaterialGoodsReceiveVoForList> MaterialGoodsReceiveVoForListInfo = null;

        try {
            MaterialGoodsReceiveVoForListInfo = materialGoodsReceiveService
                    .findGoodsReceives(materialGoodsReceiveVoForFilter);

            if (MaterialGoodsReceiveVoForListInfo != null) {
                List<MaterialGoodsReceiveVoForList> materialGoodsReceiveVoForLists = MaterialGoodsReceiveVoForListInfo.getList();
                for (MaterialGoodsReceiveVoForList materialGoodsReceiveVoForList : materialGoodsReceiveVoForLists) {
                    MaterialRfCollector materialRfCollector = itemService.findcollect(materialGoodsReceiveVoForList.getId(), "receive", Common.EAM_PROD_IDS[0]);
                    if (materialRfCollector != null) {
                        materialGoodsReceiveVoForList.setCollect(true);
                    } else {
                        materialGoodsReceiveVoForList.setCollect(false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("/eam/micro/goodsreceive/findGoodsReceives-----", e);
            return null;
        }

        return MaterialGoodsReceiveVoForListInfo;
    }

    /**
     * 新建物资接收单
     *
     * @param materialGoodsReceiveVo 物资接收单实体{@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/saveGoodsReceive", method = RequestMethod.POST)
    public MaterialGoodsReceiveVo saveGoodsReceive(
            @RequestBody MaterialGoodsReceiveVo materialGoodsReceiveVo) {

        logger.info(
                "/eam/micro/goodsreceive/saveGoodsReceive param entity materialGoodsReceiveVo :{}",
                materialGoodsReceiveVo);

        MaterialGoodsReceive ro = new MaterialGoodsReceive();
        List<MaterialGoodsReceiveDetail> materialGoodsReceiveDetailList = new ArrayList<MaterialGoodsReceiveDetail>();
        List<MaterialGoodsReceiveDetailVo> materialGoodsReceiveVoMaterialGoodsReceiveDetailVos = materialGoodsReceiveVo.getMaterialGoodsReceiveDetailVos();

        try {

            ReflectionUtils.copyProperties(materialGoodsReceiveVo, ro, null);
            ro = materialGoodsReceiveService.saveGoodsReceive(ro);

            for (MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo : materialGoodsReceiveVoMaterialGoodsReceiveDetailVos) {

                materialGoodsReceiveDetailVo.setSiteId(materialGoodsReceiveVo.getSiteId());
                materialGoodsReceiveDetailVo.setOrgId(materialGoodsReceiveVo.getOrgId());
                materialGoodsReceiveDetailVo.setCreateUser(materialGoodsReceiveVo.getCreateUser());
                materialGoodsReceiveDetailVo.setGoodsReceiveId(ro.getId());
                MaterialGoodsReceiveDetail materialGoodsReceiveDetail = new MaterialGoodsReceiveDetail();
                ReflectionUtils.copyProperties(materialGoodsReceiveDetailVo, materialGoodsReceiveDetail, null);
                materialGoodsReceiveDetailList.add(materialGoodsReceiveDetail);
            }
            materialGoodsReceiveDetailList = materialGoodsReceiveDetailService.saveMaterialGoodsReceiveDetailList(materialGoodsReceiveDetailList);

            for (int i = 0; i < materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.size(); i++) {
//                if ("YJS".equals(materialGoodsReceiveVo.getStatus())) {
//                    long quantity = materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.get(i).getReceiveQuantity();
//
//                    if (quantity > 0) {
//
//                        String itemid = materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.get(i).getItemId();
//
//                        // 查询库存里面　库房ｉｄ和物资ｉｄ是否已经入库　　如果已经入库则只增加库存即可　如果没有入库则 添加入库动作
//                        MaterialInventory materialInventory = materialInventoryService.findInventorysByItemIdAndStoreroomId(itemid, materialGoodsReceiveVo.getStoreroomId());
//
//                        if (materialInventory != null) {
//                            //已经入库只是新增库存
//                            materialInventoryService.updateInventoryCurrentBalanceByItemId(itemid, quantity);
//
//                        } else {
//                            //没有入库添加入库动作
//                            MaterialInventory materialInventoryNew = new MaterialInventory();
//                            materialInventoryNew.setAbcType("A");
//                            materialInventoryNew.setAvailableBalance(materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.get(i).getReceiveQuantity());
//                            materialInventoryNew.setCurrentBalance(materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.get(i).getReceiveQuantity());
//                            materialInventoryNew.setItemId(itemid);
//                            materialInventoryNew.setStoreroomId(materialGoodsReceiveVo.getStoreroomId());
//                            materialInventoryNew.setStandardCost(0.0);
//                            materialInventoryNew.setCreateUser(materialGoodsReceiveVo.getCreateUser());
//                            materialInventoryNew.setSiteId(materialGoodsReceiveVo.getSiteId());
//                            materialInventoryNew.setOrgId(materialGoodsReceiveVo.getSiteId());
//                            materialInventoryService.saveInventory(materialInventoryNew);
//                        }
//                    }
//                }
                materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.get(i).setId(materialGoodsReceiveDetailList.get(i).getId());
            }

            materialGoodsReceiveVo.setId(ro.getId());


        } catch (Exception e) {
            logger.error(
                    "-----/eam/micro/goodsreceive/saveGoodsReceive ------", e);
        }

        return materialGoodsReceiveVo;

    }

    /**
     * 修改物资接收单记录
     *
     * @param materialGoodsReceiveVo 要修改物资接收单实体 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVo}
     * @return 修改后的物资接收单实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/updateGoodsReceive", method = RequestMethod.POST)
    public MaterialGoodsReceive updateGoodsReceive(
            @RequestBody MaterialGoodsReceiveVo materialGoodsReceiveVo) {

        logger.info("/eam/micro/goodsreceive/updateGoodsReceive - - -->param:{}", materialGoodsReceiveVo);
        String id = materialGoodsReceiveVo.getId();
        List<MaterialGoodsReceiveDetailVo> materialGoodsReceiveVoMaterialGoodsReceiveDetailVos = materialGoodsReceiveVo.getMaterialGoodsReceiveDetailVos();
        List<MaterialGoodsReceiveDetail> materialGoodsReceiveDetailList = new ArrayList<MaterialGoodsReceiveDetail>();
        MaterialGoodsReceive materialGoodsReceive = materialGoodsReceiveService
                .findGoodsreceiveById(id);
//        String lastStatus = materialGoodsReceive.getStatus();
        try {
            if (materialGoodsReceive != null) {
                materialGoodsReceiveVo.setCreateUser(materialGoodsReceive
                        .getCreateUser());
                ReflectionUtils.copyProperties(materialGoodsReceiveVo,
                        materialGoodsReceive, null);

                if (materialGoodsReceiveVoMaterialGoodsReceiveDetailVos.size() > 0) {
                    for (MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo : materialGoodsReceiveVoMaterialGoodsReceiveDetailVos) {

                        materialGoodsReceiveDetailVo.setSiteId(materialGoodsReceiveVo.getSiteId());
                        materialGoodsReceiveDetailVo.setOrgId(materialGoodsReceiveVo.getOrgId());
                        materialGoodsReceiveDetailVo.setCreateUser(materialGoodsReceiveVo.getCreateUser());
                        materialGoodsReceiveDetailVo.setGoodsReceiveId(id);
                        MaterialGoodsReceiveDetail materialGoodsReceiveDetail = new MaterialGoodsReceiveDetail();
                        ReflectionUtils.copyProperties(materialGoodsReceiveDetailVo, materialGoodsReceiveDetail, null);
                        materialGoodsReceiveDetailList.add(materialGoodsReceiveDetail);
                    }
                    materialGoodsReceiveDetailList = materialGoodsReceiveDetailService.saveMaterialGoodsReceiveDetailList(materialGoodsReceiveDetailList);
                }

//                if ("YJS".equals(materialGoodsReceiveVo.getStatus()) && !"YJS".equals(lastStatus)) {
//
//                    //完成接收　要操作在库存中增加
//                    PageInfo<MaterialGoodsReceiveDetailVoForList> pageInfo = materialGoodsReceiveDetailService.findGoodsReceiveDetailByGoodsReceiveId(id, 0, 999);
//
//                    if (pageInfo != null) {
//                        List<MaterialGoodsReceiveDetailVoForList> materialGoodsReceiveDetailVoForLists = pageInfo.getList();
//
//                        //循环接受的物资
//                        for (MaterialGoodsReceiveDetailVoForList materialGoodsReceiveDetailVo : materialGoodsReceiveDetailVoForLists) {
//                            long quantity = materialGoodsReceiveDetailVo.getReceiveQuantity();
//                            //接受的物资大于0时　处理库存增加
//                            if (quantity > 0) {
//                                String itemid = materialGoodsReceiveDetailVo.getItemId();
//
//                                // 查询库存里面　库房ｉｄ和物资ｉｄ是否已经入库　　如果已经入库则只增加库存即可　如果没有入库则 添加入库动作
//                                MaterialInventory materialInventory = materialInventoryService.findInventorysByItemIdAndStoreroomId(itemid, materialGoodsReceiveVo.getStoreroomId());
//
//                                if (materialInventory != null) {
//                                    //已经入库只是新增库存
//                                    materialInventoryService.updateInventoryCurrentBalanceByItemId(itemid, quantity);
//
//                                } else {
//                                    //没有入库添加入库动作
//                                    MaterialInventory materialInventoryNew = new MaterialInventory();
//                                    materialInventoryNew.setAbcType("A");
//                                    materialInventoryNew.setAvailableBalance(materialGoodsReceiveDetailVo.getReceiveQuantity());
//                                    materialInventoryNew.setCurrentBalance(materialGoodsReceiveDetailVo.getReceiveQuantity());
//                                    materialInventoryNew.setItemId(itemid);
//                                    materialInventoryNew.setStoreroomId(materialGoodsReceiveVo.getStoreroomId());
//                                    materialInventoryNew.setStandardCost(0.0);
//                                    materialInventoryNew.setCreateUser(materialGoodsReceiveVo.getCreateUser());
//                                    materialInventoryNew.setSiteId(materialGoodsReceiveVo.getSiteId());
//                                    materialInventoryNew.setOrgId(materialGoodsReceiveVo.getSiteId());
//                                    materialInventoryService.saveInventory(materialInventoryNew);
//                                }
//                            }
//                        }
//
//                    }
//
//                }

            } else {
                new EnerbosException(
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        "没有对应id的记录");
            }

        } catch (Exception e) {
            logger.error(
                    "-----/eam/micro/goodsreceive/updateGoodsReceive ------", e);
        }

        MaterialGoodsReceive materialGoodsReceive1 = materialGoodsReceiveService
                .updateGoodsReceive(materialGoodsReceive);

        return materialGoodsReceive1;
    }

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/deleteGoodsReceive", method = RequestMethod.POST)
    public boolean deleteGoodsReceive(
            @RequestParam(value = "ids", required = true) String ids[]) {


        logger.info(
                "/eam/micro/goodsreceive/deleteGoodsReceive param  ids :{}",
                ids);
        try {
            materialGoodsReceiveService.deleteGoodsReceive(ids);
            return true;
        } catch (Exception e) {

            logger.error("--/eam/micro/goodsreceive/deleteGoodsReceive-", e);
            return false;
        }

    }

    /**
     * 查询物资接收单详细信息
     *
     * @param id 物资接收单id
     * @return 返回物资接收单实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/findGoodsreceiveById", method = RequestMethod.GET)
    public MaterialGoodsReceive findGoodsreceiveById(
            @RequestParam(value = "id", required = true) String id) {
        logger.info(
                "/eam/micro/goodsreceive/deleteGoodsReceive param  id :{}",
                id);
        MaterialGoodsReceive materialGoodsReceive = null;

        try {
            materialGoodsReceive = materialGoodsReceiveService
                    .findGoodsreceiveById(id);
        } catch (Exception e) {

            logger.error(
                    "---/eam/micro/goodsreceive/findGoodsreceiveById-----", e);
        }

        return materialGoodsReceive;

    }


    /**
     * 修改物资接收单状态
     *
     * @param ids    要修改的物资接收单id数组
     * @param status 状态（暂挂、活动）
     * @return 是否成功
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/updateGoodsReceiveStatus", method = RequestMethod.POST)
    public boolean updateGoodsReceiveStatus(@RequestParam("ids") String[] ids,
                                            @RequestParam("status") String status) {

        logger.info(
                "/eam/micro/goodsreceive/updateGoodsReceiveStatus param is :ids:{},status:{}",
                ids, status);

        try {

            for (String id : ids) {
                MaterialGoodsReceive materialGoodsReceive = findGoodsreceiveById(id);


                //首次完成接收单 要触发接收动作
                if ("YJS".equals(status) && !"YJS".equals(materialGoodsReceive.getStatus())) {

                    materialGoodsReceive.setStatus(status);
                    materialGoodsReceiveService.updateGoodsReceiveStatus(materialGoodsReceive);
                    // 完成接收　要操作在库存中增加
                    PageInfo<MaterialGoodsReceiveDetailVoForList> pageInfo = materialGoodsReceiveDetailService.findGoodsReceiveDetailByGoodsReceiveId(id, 0, 999);

                    if (pageInfo != null) {
                        List<MaterialGoodsReceiveDetailVoForList> materialGoodsReceiveDetailVoForLists = pageInfo.getList();

                        //循环接受的物资
                        for (MaterialGoodsReceiveDetailVoForList materialGoodsReceiveDetailVo : materialGoodsReceiveDetailVoForLists) {
                            long quantity = materialGoodsReceiveDetailVo.getReceiveQuantity();
                            //接受的物资大于0时　处理库存增加
                            if (quantity > 0) {
                                String itemid = materialGoodsReceiveDetailVo.getItemId();

                                // 查询库存里面　库房ｉｄ和物资ｉｄ是否已经入库　　如果已经入库则只增加库存即可　如果没有入库则 添加入库动作
                                MaterialInventory materialInventory = materialInventoryService.findInventorysByItemIdAndStoreroomId(itemid, materialGoodsReceive.getStoreroomId());

                                if (materialInventory != null) {
                                    //已经入库只是新增库存
                                    materialInventoryService.updateInventoryCurrentBalanceByItemId(itemid, quantity);

                                } else {
                                    //没有入库添加入库动作
                                    MaterialInventory materialInventoryNew = new MaterialInventory();
                                    materialInventoryNew.setAbcType("A");
                                    materialInventoryNew.setAvailableBalance(materialGoodsReceiveDetailVo.getReceiveQuantity());
                                    materialInventoryNew.setCurrentBalance(materialGoodsReceiveDetailVo.getReceiveQuantity());
                                    materialInventoryNew.setItemId(itemid);
                                    materialInventoryNew.setStoreroomId(materialGoodsReceive.getStoreroomId());
                                    materialInventoryNew.setStandardCost(0.0);
                                    materialInventoryNew.setStatus("draft");
                                    materialInventoryNew.setCreateUser(materialGoodsReceive.getCreateUser());
                                    materialInventoryNew.setSiteId(materialGoodsReceive.getSiteId());
                                    materialInventoryNew.setOrgId(materialGoodsReceive.getOrgId());
                                    materialInventoryService.saveInventory(materialInventoryNew);
                                }
                            }
                        }
                    }
                }

                if (!"YJS".equals(materialGoodsReceive.getStatus())) {
                    materialGoodsReceive.setStatus(status);
                    materialGoodsReceiveService.updateGoodsReceiveStatus(materialGoodsReceive);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("--/eam/micro/goodsreceive/updateGoodsReceiveStatus-",
                    e);
            return false;
        }
    }

    /**
     * 查询物资接收单明细并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param id       物资接收单id
     * @param pageNum  页数
     * @param pageSize 一页显示的行数
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId", method = RequestMethod.GET)
    public PageInfo<MaterialGoodsReceiveDetailVoForList> findGoodsReceiveDetailByGoodsReceiveId(
            @RequestParam("id") String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {

        logger.info(
                "/eam/micro/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId param id:{},pageNum:{},pageSize:{}",
                id, pageNum, pageSize);

        PageInfo<MaterialGoodsReceiveDetailVoForList> materialGoodsReceiveDetailVoForList = null;

        try {
            materialGoodsReceiveDetailVoForList = materialGoodsReceiveDetailService
                    .findGoodsReceiveDetailByGoodsReceiveId(id, pageNum,
                            pageSize);

        } catch (Exception e) {
            logger.error(
                    "/eam/micro/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId-->",
                    e);
        }

        return materialGoodsReceiveDetailVoForList;
    }

    /**
     * 新建物资接收单明细
     *
     * @param materialGoodsReceiveDetailVo 物资接收明细 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo}
     * @return 返回添加的物资接收明细实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/saveGoodsReceiveDetail", method = RequestMethod.POST)
    public MaterialGoodsReceiveDetailVo saveGoodsReceiveDetail(
            @RequestBody MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo) {

        logger.info("/eam/micro/goodsreceive/saveGoodsReceiveDetail param :{}",
                materialGoodsReceiveDetailVo);

        MaterialGoodsReceiveDetail matrectrans = new MaterialGoodsReceiveDetail();
        try {

            ReflectionUtils.copyProperties(materialGoodsReceiveDetailVo,
                    matrectrans, null);
            matrectrans = materialGoodsReceiveDetailService
                    .saveMatrectrans(matrectrans);

            materialGoodsReceiveDetailVo.setId(matrectrans.getId());
        } catch (Exception e) {
            logger.error("--/eam/micro/goodsreceive/saveGoodsReceiveDetail---",
                    e);
        }

        return materialGoodsReceiveDetailVo;
    }

    /**
     * 修改物资接收单明细
     *
     * @param materialGoodsReceiveDetailVo 修改的物资接收单明细  {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo}
     * @return 修改后的物资接收单明细
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/updateGoodsReceiveDetail", method = RequestMethod.POST)
    public MaterialGoodsReceiveDetail updateGoodsReceiveDetail(
            @RequestBody MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo) {

        logger.info(
                "/eam/micro/goodsreceive/updateGoodsReceiveDetail param:{}",
                materialGoodsReceiveDetailVo);

        String id = materialGoodsReceiveDetailVo.getId();
        MaterialGoodsReceiveDetail materialGoodsReceiveDetail = materialGoodsReceiveDetailService
                .findGoodsReceiveDetailById(id);
        try {

            //更新物资接收表内容
            if (materialGoodsReceiveDetail != null) {
                if (materialGoodsReceiveDetailVo.getReceiveQuantity() != null) {
                    materialGoodsReceiveDetail.setreceiveQuantity(materialGoodsReceiveDetailVo.getReceiveQuantity());
                }
                if (materialGoodsReceiveDetailVo.getLineCost() != null) {
                    materialGoodsReceiveDetail.setLineCost(materialGoodsReceiveDetailVo.getLineCost());
                }
                if (materialGoodsReceiveDetailVo.getUnitCost() != null) {
                    materialGoodsReceiveDetail.setUnitCost(materialGoodsReceiveDetailVo.getUnitCost());
                }

                logger.info("materialGoodsReceiveDetail {}", materialGoodsReceiveDetail);
                materialGoodsReceiveDetail = materialGoodsReceiveDetailService.updateGoodsReceiveDetail(materialGoodsReceiveDetail);
            }

            //更新库存数量
            long quantity = materialGoodsReceiveDetailVo.getReceiveQuantity();

            if (quantity > 0) {
                materialInventoryService.updateInventoryCurrentBalanceByItemId(materialGoodsReceiveDetailVo.getItemId(), quantity);
            }

        } catch (Exception e) {
            logger.error(
                    "--/eam/micro/goodsreceive/updateGoodsReceiveDetail--", e);
        }

        return materialGoodsReceiveDetail;

    }

    /**
     * 删除物资接收单明细
     *
     * @param ids 物资接收单明细ID
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/deleteGoodsReceiveDetail", method = RequestMethod.POST)
    public boolean deleteGoodsReceiveDetail(@RequestParam("ids") String ids[]) {

        logger.info(
                "/eam/micro/goodsreceive/updateGoodsReceiveDetail param:ids{}",
                ids);
        try {
            materialGoodsReceiveDetailService.deleteGoodsReceiveDetail(ids);
            return true;
        } catch (Exception e) {
            logger.error("/eam/micro/goodsreceive/deleteGoodsReceiveDetail-->",
                    e);
            return false;
        }
    }

}
