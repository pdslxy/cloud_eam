package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.MaterialCheck;
import com.enerbos.cloud.eam.microservice.domain.MaterialCheckDetail;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.service.MaterialCheckService;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
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
 * @Description 物资盘点
 */
@RestController
public class MaterialCheckController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialCheckService materialCheckService;

    @Autowired
    public MaterialItemService itemService;

    /**
     * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialCheckVoForFilter 物资盘点列表实体
     *                                 {@link com.enerbos.cloud.eam.vo.MaterialCheckVoForFilter}
     * @return 返回列表结果集
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialChecks", method = RequestMethod.POST)
    public PageInfo<MaterialCheckVoForList> findMaterialCheck(
            @RequestBody MaterialCheckVoForFilter materialCheckVoForFilter) {

        logger.info("eam/micro/check/findMaterialChecks----> params:{}",
                materialCheckVoForFilter);
        PageInfo<MaterialCheckVoForList> pageInfo = null;
        try {
            pageInfo = materialCheckService
                    .findMaterialCheck(materialCheckVoForFilter);

            if (pageInfo != null) {
                List<MaterialCheckVoForList> materialCheckVoForLists = pageInfo.getList();
                for (MaterialCheckVoForList materialCheckVoForList : materialCheckVoForLists) {
                    MaterialRfCollector materialRfCollector = itemService.findcollect(materialCheckVoForList.getId(), "check", Common.EAM_PROD_IDS[0]);
                    if (materialRfCollector != null) {
                        materialCheckVoForList.setCollect(true);
                    } else {
                        materialCheckVoForList.setCollect(false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("--/eam/micro/check/findMaterialCheck--", e);
        }

        return pageInfo;

    }

    /**
     * 新建物资盘点单
     *
     * @param materialCheckVo 物资盘点列表实体
     *                        {@link com.enerbos.cloud.eam.vo.MaterialCheckVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/check/saveMaterialCheck", method = RequestMethod.POST)
    public MaterialCheckVo saveMaterialCheck(
            @RequestBody @Valid MaterialCheckVo materialCheckVo) {

        MaterialCheck invuse = new MaterialCheck();
        List<MaterialCheckDetailVo> materialCheckDetailVos = materialCheckVo.getMaterialCheckDetailVos();
        List<MaterialCheckDetail> materialCheckDetails = new ArrayList<>();
        try {
            ReflectionUtils.copyProperties(materialCheckVo, invuse, null);
            invuse = materialCheckService.saveMaterialCheck(invuse);

            for (MaterialCheckDetailVo materialCheckDetailVo : materialCheckDetailVos) {
                materialCheckDetailVo.setSiteId(materialCheckVo.getSiteId());
                materialCheckDetailVo.setOrgId(materialCheckVo.getOrgId());
                materialCheckDetailVo.setCreateUser(materialCheckVo.getCreateUser());
                materialCheckDetailVo.setCheckId(invuse.getId());
                MaterialCheckDetail materialCheckDetail = new MaterialCheckDetail();
                ReflectionUtils.copyProperties(materialCheckDetailVo, materialCheckDetail, null);
                materialCheckDetails.add(materialCheckDetail);
            }
            materialCheckDetails = materialCheckService.saveMaterialCheckDetail(materialCheckDetails);

            materialCheckVo.setId(invuse.getId());
        } catch (Exception e) {
            logger.error("-----saveMaterialCheck ------", e);
        }
        return materialCheckVo;
    }

    /**
     * 修改物资盘点
     *
     * @param materialCheckVo 物资盘点实体{@link com.enerbos.cloud.eam.vo.MaterialCheckVo}
     * @return 修改后的物资盘点实体
     */
    @RequestMapping(value = "/eam/micro/check/updateMaterialCheck", method = RequestMethod.POST)
    public MaterialCheck updateMaterialCheck(@RequestBody MaterialCheckVo materialCheckVo) {

        String id = materialCheckVo.getId();
        MaterialCheck check = this.findMaterialCheckById(id);
        if (check != null) {
            try {
                materialCheckVo.setCreateUser(check.getCreateUser());
                materialCheckVo.setCreateDate(check.getCreateDate());
                materialCheckVo.setUpdateDate(check.getUpdateDate());
                ReflectionUtils.copyProperties(materialCheckVo, check, null);
                check = materialCheckService.saveMaterialCheck(check);


                List<MaterialCheckDetailVo> materialCheckDetailVos = materialCheckVo.getMaterialCheckDetailVos();

                if (materialCheckDetailVos != null) {
                    List<MaterialCheckDetail> materialCheckDetails = new ArrayList<>();
                    for (MaterialCheckDetailVo materialCheckDetailVo : materialCheckDetailVos) {
                        materialCheckDetailVo.setSiteId(materialCheckVo.getSiteId());
                        materialCheckDetailVo.setOrgId(materialCheckVo.getOrgId());
                        materialCheckDetailVo.setCreateUser(materialCheckVo.getCreateUser());
                        materialCheckDetailVo.setCheckId(check.getId());
                        MaterialCheckDetail materialCheckDetail = new MaterialCheckDetail();
                        ReflectionUtils.copyProperties(materialCheckDetailVo, materialCheckDetail, null);
                        materialCheckDetails.add(materialCheckDetail);
                    }
                    materialCheckDetails = materialCheckService.saveMaterialCheckDetail(materialCheckDetails);
                }

            } catch (Exception e) {
                logger.error("---updateMaterialCheck--", e);
            }

        } else {
            new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "无此id对应的记录");
            return null;

        }
        return check;
    }

    /**
     * 根据ID删除物资盘点
     *
     * @param ids 物资盘点id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return 删除是否成功
     */
    @RequestMapping(value = "/eam/micro/check/deleteMaterialCheck", method = RequestMethod.POST)
    public boolean deleteMaterialCheck(@RequestParam("ids") String ids[]) {

        try {
            materialCheckService.deleteMaterialCheck(ids);
            return true;
        } catch (Exception e) {
            logger.error("----deleteMaterialCheck----", e);
            return false;
        }
    }

    /**
     * 查询物资盘点详细信息
     *
     * @param id 物资盘点id
     * @return 返回物资盘点实体
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialCheckById", method = RequestMethod.GET)
    public MaterialCheck findMaterialCheckById(@RequestParam("id") String id) {

        MaterialCheck MaterialCheck = null;
        try {
            MaterialCheck = materialCheckService.findMaterialCheckById(id);
        } catch (Exception e) {
            logger.error("-----------findMaterialCheckById-------------", e);
        }
        return MaterialCheck;

    }

    /**
     * 修改物资盘点状态
     *
     * @param ids    物资盘点ids
     * @param status 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/check/updateMaterialCheckStatus", method = RequestMethod.POST)
    public boolean updateMaterialCheckStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status) {
        try {
            materialCheckService.updateMaterialCheckStatus(ids, status);
            return true;
        } catch (Exception e) {
            logger.error("-----updateMaterialCheckStatus-------", e);
            return false;
        }
    }


    /**
     * 根据物资盘点id查询盘点明细
     *
     * @param id       物资盘点id
     * @param pageNum  页数
     * @param pageSize 每页显示数据
     * @return 盘点明细实体
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialCheckDetail", method = RequestMethod.GET)
    public PageInfo<MaterialCheckDetailVoForList> findMaterialCheckDetail(@RequestParam("id") String id, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {

        logger.info("eam/micro/check/findMaterialCheckDetail----> params:id{},pageNum:{},pageSize:{}",
                id, pageNum, pageSize);
        PageInfo<MaterialCheckDetailVoForList> pageInfo = null;
        try {
            pageInfo = materialCheckService
                    .findMaterialCheckDetail(id, pageNum, pageSize);
        } catch (Exception e) {
            logger.error("--/eam/micro/check/findMaterialCheckDetail--", e);
        }

        return pageInfo;

    }

    /**
     * 根据ID删除物资盘点明细
     *
     * @param ids 物资盘点明细id数组
     * @return 是否成功
     */
    @RequestMapping(value = "/eam/micro/check/deleteMaterialCheckDetail", method = RequestMethod.POST)
    public boolean deleteMaterialCheckDetail(String[] ids) {
        logger.info("eam/micro/check/findMaterialCheckDetail----> params:ids{}",
                ids);
        try {
            materialCheckService.deleteMaterialCheckDetail(ids);
            return true;
        } catch (Exception e) {
            logger.error("deleteMaterialCheckDetail", e);
            return false;
        }
    }

    /**
     * 新建物资盘点明细
     *
     * @param materialCheckDetailVo 新建物资盘点明细实体 {@link com.enerbos.cloud.eam.vo.MaterialCheckDetailVo}
     * @return 物资盘点明细实体
     */
    @RequestMapping(value = "/eam/micro/check/saveMaterialCheckDetail", method = RequestMethod.POST)
    public MaterialCheckDetailVo saveMaterialCheckDetail(@RequestBody MaterialCheckDetailVo materialCheckDetailVo) {
        logger.info("eam/micro/check/saveMaterialCheckDetail----> params:materialCheckDetailVo{}",
                materialCheckDetailVo);

        MaterialCheckDetail checkDetail = new MaterialCheckDetail();
        String id = materialCheckDetailVo.getId();
        try {
            if (StringUtils.isNotEmpty(id)) {
                checkDetail = materialCheckService.findMaterialCheckDetailById(id);
//            materialCheckDetailVo.setCreateDate(checkDetail.getCreateDate());
//            materialCheckDetailVo.setCreateUser(checkDetail.getCreateUser());
//            materialCheckDetailVo.setUpdateDate(checkDetail.getUpdateDate());

//            ReflectionUtils.copyProperties(materialCheckDetailVo, checkDetail, null);
//            logger.info("////////{}",checkDetail);
                if (StringUtils.isNotEmpty(materialCheckDetailVo.getPhysicalInventory())) {
                    checkDetail.setPhysicalInventory(materialCheckDetailVo.getPhysicalInventory());
//                logger.info("---------{}",checkDetail);
//                    checkDetail = materialCheckService.saveMaterialCheckDetail(checkDetail);
                }
            } else {
                ReflectionUtils.copyProperties(materialCheckDetailVo, checkDetail, null);
//                checkDetail = materialCheckService.saveMaterialCheckDetail(checkDetail);
                materialCheckDetailVo.setId(checkDetail.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return materialCheckDetailVo;
    }

    /**
     * 根据物资库存id查询 盘点记录
     *
     * @param id       物资库存id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 查询结果列表
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialCheckByInvtoryId", method = RequestMethod.GET)
    public PageInfo<MaterialCheckVoForInventoryList> findMaterialCheckByInvtoryId(@RequestParam("id") String id, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {

        logger.info("eam/micro/check/findMaterialChecks----> params:id{},pageNum:{},pagesize:{}", id, pageNum, pageSize);
        PageInfo<MaterialCheckVoForInventoryList> pageInfo = null;
        try {
            pageInfo = materialCheckService
                    .findMaterialCheckByInvtoryId(id, pageNum, pageSize);
        } catch (Exception e) {
            logger.error("--/eam/micro/check/findMaterialCheck--", e);
        }

        return pageInfo;
    }
}
