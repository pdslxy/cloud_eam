package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
import com.enerbos.cloud.eam.microservice.domain.MaterialRelease;
import com.enerbos.cloud.eam.microservice.domain.MaterialReleaseDetail;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.service.MaterialInventoryService;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.microservice.service.MaterialReleaseService;
import com.enerbos.cloud.eam.vo.*;
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
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2015-2017
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017/3/31 10:20
 * @Description 物资发放
 */
@RestController
public class MaterialReleaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MaterialReleaseService materialReleaseService;

    @Autowired
    public MaterialInventoryService materialInventoryService;

    @Autowired
    public MaterialItemService itemService;

    /**
     * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialReleaseVoForFilter 物资发放列表实体
     *                                   {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseVoForFilter}
     * @return 返回列表结果集
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialRelease", method = RequestMethod.POST)
    public PageInfo<MaterialReleaseVoForList> findMaterialRelease(
            @RequestBody MaterialReleaseVoForFilter materialReleaseVoForFilter) {

        logger.info("eam/micro/release/findMaterialRelease----> params:{}",
                materialReleaseVoForFilter);
        PageInfo<MaterialReleaseVoForList> pageInfo = null;
        try {
            pageInfo = materialReleaseService
                    .findMaterialRelease(materialReleaseVoForFilter);
            if (pageInfo != null) {
                List<MaterialReleaseVoForList> materialReleaseVoForLists = pageInfo.getList();
                for (MaterialReleaseVoForList materialReleaseVoForList : materialReleaseVoForLists) {
                    MaterialRfCollector materialRfCollector = itemService.findcollect(materialReleaseVoForList.getId(), "release", Common.EAM_PROD_IDS[0]);
                    if (materialRfCollector != null) {
                        materialReleaseVoForList.setCollect(true);
                    } else {
                        materialReleaseVoForList.setCollect(false);
                    }
                }
            }


        } catch (Exception e) {
            logger.error("--/eam/micro/release/findMaterialRelease--", e);
        }

        return pageInfo;

    }

    /**
     * 新建物资发放单
     *
     * @param materialReleaseVo 物资发放列表实体
     *                          {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/release/saveMaterialRelease", method = RequestMethod.POST)
    public MaterialReleaseVo saveMaterialRelease(
            @RequestBody @Valid MaterialReleaseVo materialReleaseVo) {


        logger.info("/eam/micro/release/saveMaterialRelease---param>:{}", materialReleaseVo);
        MaterialRelease invuse = new MaterialRelease();
        List<MaterialReleaseDetailVo> materialReleaseDetailVos = materialReleaseVo.getMaterialReleaseDetailVos();
        List<MaterialReleaseDetail> materialReleaseDetailVoList = new ArrayList<>();

        try {
            ReflectionUtils.copyProperties(materialReleaseVo, invuse, null);

            if (materialReleaseDetailVos.size() > 0) {
                for (MaterialReleaseDetailVo materialReleaseDetailVo : materialReleaseDetailVos) {
                    materialReleaseDetailVo.setSiteId(materialReleaseVo.getSiteId());
                    materialReleaseDetailVo.setOrgId(materialReleaseVo.getOrgId());
                    materialReleaseDetailVo.setCreateUser(materialReleaseVo.getCreateUser());
                    MaterialReleaseDetail materialReleaseDetail = new MaterialReleaseDetail();
                    ReflectionUtils.copyProperties(materialReleaseDetailVo, materialReleaseDetail, null);
                    materialReleaseDetailVoList.add(materialReleaseDetail);
                }
                invuse = materialReleaseService.saveMaterialRelease(invuse);
                for (MaterialReleaseDetail materialReleaseDetail : materialReleaseDetailVoList) {
                    materialReleaseDetail.setReleaseId(invuse.getId());
                }
                materialReleaseService.saveMaterialReleaseDetail(materialReleaseDetailVoList);
            } else {
                invuse = materialReleaseService.saveMaterialRelease(invuse);
            }
            materialReleaseVo.setId(invuse.getId());
        } catch (Exception e) {
            logger.error("-----/eam/micro/release/saveMaterialRelease ------", e);
        }
        return materialReleaseVo;
    }

    /**
     * 根据ID删除物资发放
     *
     * @param ids 物资发放id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return 删除是否成功
     */
    @RequestMapping(value = "/eam/micro/release/deleteMaterialRelease", method = RequestMethod.POST)
    public boolean deleteMaterialRelease(String ids[]) {

        try {
            materialReleaseService.deleteMaterialRelease(ids);
            return true;
        } catch (Exception e) {
            logger.error("----/eam/micro/release/deleteMaterialRelease----", e);
            return false;
        }
    }

    /**
     * 查询物资发放详细信息
     *
     * @param id 物资发放id
     * @return 返回物资发放实体
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseById", method = RequestMethod.GET)
    public MaterialRelease findMaterialReleaseById(@RequestParam("id") String id) {

        MaterialRelease materialRelease = null;
        try {
            materialRelease = materialReleaseService.findMaterialReleaseById(id);
        } catch (Exception e) {
            logger.error("-----------findMaterialReleaseById-------------", e);
        }
        return materialRelease;

    }

    /**
     * 查询物资发放明细列表
     *
     * @param materialReleaseDetailVoForFilter 查询实体
     *                                         {@link com.enerbos.cloud.eam.vo.MaterialReleaseDetailVoForFilter }
     * @return 返回物资发放列表实体数据
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseDetail", method = RequestMethod.POST)
    public PageInfo<MaterialReleaseDetailVoForList> findMaterialReleaseDetail(
            @RequestBody MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter) {

        PageInfo<MaterialReleaseDetailVoForList> pageInfo = null;

        try {
            pageInfo = materialReleaseService.findMaterialReleaseDetail(materialReleaseDetailVoForFilter);
        } catch (Exception e) {
            logger.error("-----------findMaterialReleaseDetail-------------", e);
        }
        return pageInfo;
    }

    /**
     * 新建物资发放明细单
     *
     * @param materialReleaseDetailVo 物资发放列表实体
     *                                {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseDetailVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/release/saveMaterialReleaseDetail", method = RequestMethod.POST)
    MaterialReleaseDetailVo saveMaterialReleaseDetail(
            @RequestBody MaterialReleaseDetailVo materialReleaseDetailVo) {


        MaterialReleaseDetail materialReleaseDetail = new MaterialReleaseDetail();
        try {
            ReflectionUtils.copyProperties(materialReleaseDetailVo, materialReleaseDetail, null);
            //materialReleaseDetail = materialReleaseService.saveMaterialReleaseDetail(materialReleaseDetail);
            materialReleaseDetailVo.setId(materialReleaseDetail.getId());
        } catch (Exception e) {
            logger.error("-----saveMaterialReleaseDetail ------", e);
        }
        return materialReleaseDetailVo;

    }

    /**
     * 根据id查询物资发放单明细
     *
     * @param id 物资发放明细id
     * @return 物资发放明细
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseDetailById", method = RequestMethod.POST)
    public MaterialReleaseDetailVo findMaterialReleaseDetailById(@RequestParam("id") String id) {
        MaterialReleaseDetailVo materialReleaseDetailVo = new MaterialReleaseDetailVo();

        try {
            MaterialReleaseDetail materialReleaseDetail = materialReleaseService.findMaterialReleaseDetailById(id);

            ReflectionUtils.copyProperties(materialReleaseDetail, materialReleaseDetailVo, null);

        } catch (Exception e) {
            logger.error("findMaterialReleaseDetailById", e);
        }
        return materialReleaseDetailVo;
    }


    /**
     * 更具id改变状态
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping(value = "/eam/micro/release/updateMaterialReleaseStatus", method = RequestMethod.POST)
    public String updateMaterialReleaseStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status) {

        try {
            for (String id : ids) {
                MaterialRelease materialRelease = materialReleaseService.findMaterialReleaseById(id);

                // 判断状态 首次完成
                if ("COMPLETE".equals(status) && !"COMPLETE".equals(materialRelease.getStatus())) {

                    //查询发放详细列表
                    MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter = new MaterialReleaseDetailVoForFilter();
                    materialReleaseDetailVoForFilter.setReleaseId(materialRelease.getId());
                    materialReleaseDetailVoForFilter.setPageNum(0);
                    materialReleaseDetailVoForFilter.setPageSize(100);

                    PageInfo<MaterialReleaseDetailVoForList> materialReleaseDetailVoForListPageInfo = materialReleaseService.findMaterialReleaseDetail(materialReleaseDetailVoForFilter);

                    if (materialReleaseDetailVoForListPageInfo != null) {
                        List<MaterialReleaseDetailVoForList> materialReleaseDetailVoForLists = materialReleaseDetailVoForListPageInfo.getList();

                        for (MaterialReleaseDetailVoForList materialReleaseDetailVo : materialReleaseDetailVoForLists) {
                            long quantity = materialReleaseDetailVo.getQuantity();
                            String inventoryId = materialReleaseDetailVo.getInventoryId();
                            MaterialInventory materialInventory = materialInventoryService.findInventoryDetail(inventoryId);
                            long availableBalance = materialInventory.getAvailableBalance();
                            if (quantity > availableBalance) {
                                return "发放量大于当前可用量";
                            }
                        }
                        //减掉发放的库存量
                        for (MaterialReleaseDetailVoForList materialReleaseDetail : materialReleaseDetailVoForLists) {
                            materialInventoryService.updateInventoryCurrentBalanceByIntoryId(materialReleaseDetail.getInventoryId(), materialReleaseDetail.getQuantity());
                        }
                    }
                }

                materialRelease.setStatus(status);
                materialReleaseService.saveMaterialRelease(materialRelease);
            }
        } catch (Exception e) {
            logger.error("-------updateMaterialReleaseStatus----------", e);
        }
        return HttpStatus.OK.toString();
    }

    /**
     * 修改物资发放明细单
     *
     * @param materialReleaseVo 物资发放列表实体
     *                          {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseDetailVo}
     * @return 修改实体是否成功
     */
    @RequestMapping(value = "/eam/micro/release/updateMaterialRelease", method = RequestMethod.POST)
    public MaterialReleaseVo updateMaterialRelease(@RequestBody MaterialReleaseVo materialReleaseVo) {


        logger.info("/eam/micro/release/updateMaterialRelease-----------------------> param :materialReleaseVo{}", materialReleaseVo);
        MaterialRelease invuse = new MaterialRelease();
        List<MaterialReleaseDetailVo> materialReleaseDetailVos = materialReleaseVo.getMaterialReleaseDetailVos();
        List<MaterialReleaseDetail> materialReleaseDetailVoList = new ArrayList<>();

        try {

            invuse = materialReleaseService.findMaterialReleaseById(materialReleaseVo.getId());

            ReflectionUtils.copyProperties(materialReleaseVo, invuse, null);

            invuse = materialReleaseService.saveMaterialRelease(invuse);

            // 若有新增则入库
            if (materialReleaseDetailVos != null) {

                for (MaterialReleaseDetailVo materialReleaseDetailVo : materialReleaseDetailVos) {
                    materialReleaseDetailVo.setSiteId(materialReleaseVo.getSiteId());
                    materialReleaseDetailVo.setOrgId(materialReleaseVo.getOrgId());
                    materialReleaseDetailVo.setCreateUser(materialReleaseVo.getCreateUser());
                    materialReleaseDetailVo.setReleaseId(invuse.getId());
                    MaterialReleaseDetail materialReleaseDetail = new MaterialReleaseDetail();
                    ReflectionUtils.copyProperties(materialReleaseDetailVo, materialReleaseDetail, null);
                    materialReleaseDetailVoList.add(materialReleaseDetail);
                }
                materialReleaseDetailVoList = materialReleaseService.saveMaterialReleaseDetail(materialReleaseDetailVoList);
            }

        } catch (Exception e) {
            logger.error("-------updateMaterialReleaseDetail-----", e);
        }
        return materialReleaseVo;
    }

    /**
     * 根据ID删除物资发放明细
     *
     * @param ids 物资发放明细id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return EnerbosMessage 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/release/deleteMaterialReleaseDetail", method = RequestMethod.POST)
    public boolean deleteMaterialReleaseDetail(String ids[]) {

        try {
            materialReleaseService.deleteMaterialReleaseDetail(ids);
            return true;
        } catch (Exception e) {
            logger.error("----deleteMaterialRelease----", e);
            return false;
        }
    }


    /**
     * 根据工单id查询物资发放情况
     *
     * @param id 工单id
     * @return
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseDetailById", method = RequestMethod.GET)
    public PageInfo<MaterialInventoryVoForReleaseList> findItemInReleaseByorderId(@RequestParam("id") String id, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {


        logger.info("eam/micro/release/findItemInReleaseByorderId----> params:{}",
                id);
        PageInfo<MaterialInventoryVoForReleaseList> pageInfo = null;
        try {
            pageInfo = materialReleaseService
                    .findItemInReleaseByorderId(id, pageSize, pageNum);
        } catch (Exception e) {
            logger.error("--/eam/micro/release/findItemInReleaseByorderId--", e);
        }

        return pageInfo;
    }


}
