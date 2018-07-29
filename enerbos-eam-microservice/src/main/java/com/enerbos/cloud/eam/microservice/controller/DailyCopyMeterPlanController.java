package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlan;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanMeterRelation;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanRequency;
import com.enerbos.cloud.eam.microservice.service.DaliyCopyMeterPlanService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2015-2017
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年11月18日 11:16:54
 * @Description 抄表计划
 */
@RestController
public class DailyCopyMeterPlanController {
    private final static Logger logger = LoggerFactory
            .getLogger(DailyCopyMeterPlanController.class);

    @Autowired
    private DaliyCopyMeterPlanService daliyCopyMeterPlanService;

    /**
     * 获取抄表计划列表
     *
     * @param dailyCopyMeterPlanFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanFilterVo}
     * @return 返回查询 数据
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlans", method = RequestMethod.POST)
    public PageInfo<DailyCopyMeterPlanVoForList> findCopyMeterPlans(
            @RequestBody DailyCopyMeterPlanFilterVo dailyCopyMeterPlanFilterVo) {

        logger.info("findCopyMeterPlans - - -- > param :{}", dailyCopyMeterPlanFilterVo);
        PageInfo<DailyCopyMeterPlanVoForList> pageInfo = null;
        try {
            pageInfo = daliyCopyMeterPlanService.findCopyMeterPlans(dailyCopyMeterPlanFilterVo);
        } catch (Exception e) {
            logger.error("--------findCopyMeterPlans---------", e);
        }
        return pageInfo;
    }

    /**
     * 新增抄表计划
     *
     * @param dailyCopyMeterPlanVo 抄表计划实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVo}
     * @return 保存后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/saveCopyMeterPlan", method = RequestMethod.POST)
    public DailyCopyMeterPlanVo saveCopyMeterPlan(
            @RequestBody DailyCopyMeterPlanVo dailyCopyMeterPlanVo) {

        logger.info("saveCopyMeterPlan -----param:{}", dailyCopyMeterPlanVo);
        DaliyCopyMeterPlan daliyCopyMeterPlan = new DaliyCopyMeterPlan();


        List<DailyCopyMeterPlanRequencyVo> daliyCopyMeterPlanRequencies = dailyCopyMeterPlanVo.getDailyCopyMeterPlanRequencyVos();

        List<DaliyCopyMeterPlanRequency> daliyCopyMeterPlanRequencyList = new ArrayList<>();

        List<DaliyCopyMeterPlanMeterRelationVo> daliyCopyMeterPlanMeterRelationVos = dailyCopyMeterPlanVo.getDaliyCopyMeterPlanMeterRelationVos();
        List<DaliyCopyMeterPlanMeterRelation> daliyCopyMeterPlanMeterRelations = new ArrayList<>();


//        List<DailyCopyMeterPlanDetailVo> daliyCopyMeterPlanDetailVos = dailyCopyMeterPlanVo
//                .getCopyMeterPlanDetailVos();
//        List<DaliyCopyMeterPlanDetail> daliyCopyMeterPlanDetails = new ArrayList<DaliyCopyMeterPlanDetail>();

        try {
            ReflectionUtils.copyProperties(dailyCopyMeterPlanVo, daliyCopyMeterPlan,
                    null);
            Predicate<List> pred = list -> list != null && list.size() > 0;

            if (pred.test(daliyCopyMeterPlanRequencies)) {

                for (DailyCopyMeterPlanRequencyVo dailyCopyMeterPlanRequencyVo : daliyCopyMeterPlanRequencies) {

                    dailyCopyMeterPlanRequencyVo.setSiteId(dailyCopyMeterPlanVo.getSiteId());
                    dailyCopyMeterPlanRequencyVo.setOrgId(dailyCopyMeterPlanVo.getOrgId());
                    dailyCopyMeterPlanRequencyVo.setCreateUser(dailyCopyMeterPlanVo.getCreateUser());
                    DaliyCopyMeterPlanRequency daliyCopyMeterPlanRequency = new DaliyCopyMeterPlanRequency();
                    ReflectionUtils.copyProperties(dailyCopyMeterPlanRequencyVo,
                            daliyCopyMeterPlanRequency, null);


                    logger.info("---------{}", daliyCopyMeterPlanRequency);
                    daliyCopyMeterPlanRequencyList.add(daliyCopyMeterPlanRequency);
                }

            }


            if (pred.test(daliyCopyMeterPlanMeterRelationVos)) {

                for (DaliyCopyMeterPlanMeterRelationVo daliyCopyMeterPlanMeterRelationVo : daliyCopyMeterPlanMeterRelationVos) {

                    daliyCopyMeterPlanMeterRelationVo.setMeterId(daliyCopyMeterPlanMeterRelationVo.getId());
                    daliyCopyMeterPlanMeterRelationVo.setId(null);
                    daliyCopyMeterPlanMeterRelationVo.setSiteId(dailyCopyMeterPlanVo.getSiteId());
                    daliyCopyMeterPlanMeterRelationVo.setOrgId(dailyCopyMeterPlanVo.getOrgId());
                    daliyCopyMeterPlanMeterRelationVo.setCreateUser(dailyCopyMeterPlanVo.getCreateUser());

                    DaliyCopyMeterPlanMeterRelation daliyCopyMeterPlanMeterRelation = new DaliyCopyMeterPlanMeterRelation();
                    ReflectionUtils.copyProperties(daliyCopyMeterPlanMeterRelationVo,
                            daliyCopyMeterPlanMeterRelation, null);


                    logger.info("---------{}------", daliyCopyMeterPlanMeterRelation);
                    daliyCopyMeterPlanMeterRelations.add(daliyCopyMeterPlanMeterRelation);
                }

            }
            daliyCopyMeterPlan = daliyCopyMeterPlanService
                    .saveCopyMeterPlan(daliyCopyMeterPlan);

            if (pred.test(daliyCopyMeterPlanRequencyList)) {

                for (DaliyCopyMeterPlanRequency daliyCopyMeterPlanRequency : daliyCopyMeterPlanRequencyList) {
                    daliyCopyMeterPlanRequency.setCopyMeterPlanId(daliyCopyMeterPlan.getId());
                }

                daliyCopyMeterPlanService
                        .saveCopyMeterPlanRequency(daliyCopyMeterPlanRequencyList);

            }

            if (pred.test(daliyCopyMeterPlanMeterRelations)) {

                for (DaliyCopyMeterPlanMeterRelation daliyCopyMeterPlanMeterRelation : daliyCopyMeterPlanMeterRelations) {
                    daliyCopyMeterPlanMeterRelation.setCopymeterPlanId(daliyCopyMeterPlan.getId());
                }

                daliyCopyMeterPlanService
                        .saveCopyMeterPlanMeterRelation(daliyCopyMeterPlanMeterRelations);

            }
        } catch (Exception e) {
            logger.error("--------saveCopyMeterPlan---------", e);
        }
        dailyCopyMeterPlanVo.setId(daliyCopyMeterPlan.getId());

        return dailyCopyMeterPlanVo;
    }

    /**
     * 修改抄表单
     *
     * @param dailyCopyMeterPlanVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVo}
     * @return 修改后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/updateCopyMeterPlan", method = RequestMethod.POST)
    public DailyCopyMeterPlanVo updateCopyMeterPlan(
            @RequestBody DailyCopyMeterPlanVo dailyCopyMeterPlanVo) {

        logger.info("updateCopyMeterPlan:param:{}", dailyCopyMeterPlanVo);
        DailyCopyMeterPlanVo dailyCopyMeterPlanVo1 = new DailyCopyMeterPlanVo();
        try {
            dailyCopyMeterPlanVo1 = this.saveCopyMeterPlan(dailyCopyMeterPlanVo);
        } catch (Exception e) {
            logger.error("--------updateCopyMeterPlan---------", e);
        }

        return dailyCopyMeterPlanVo1;
    }

    /**
     * 删除抄表单
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlan", method = RequestMethod.GET)
    boolean deleteCopyMeterPlan(@RequestParam("ids") String[] ids) {
        try {
            daliyCopyMeterPlanService.deleteCopyMeterPlan(ids);
            return true;
        } catch (Exception e) {
            logger.error("--------deleteCopyMeterPlan---------", e);

        }
        return false;
    }

//
//    /**
//     * 根据抄表单id查找抄表信息
//     *
//     * @param id     抄表单id
//     * @param siteId 站点
//     * @param orgId  组织
//     * @return 数据
//     */
//    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanDetails", method = RequestMethod.GET)
//    PageInfo<DailyCopyMeterPlanDetailForList> findCopyMeterPlanDetails(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
//        logger.info("findCopyMeterPlanDetails - - -- > param :{}", id);
//        PageInfo<DailyCopyMeterPlanDetailForList> pageInfo = null;
//        try {
//            pageInfo = daliyCopyMeterPlanService
//                    .findCopyMeterPlanDetails(id, siteId, orgId);
//        } catch (Exception e) {
//            logger.error("--------findCopyMeterPlanDetails---------", e);
//        }
//        return pageInfo;
//    }
//
//

    /**
     * 根据id查找抄表单
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanById", method = RequestMethod.GET)
    public DaliyCopyMeterPlan findCopyMeterPlanById(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        DaliyCopyMeterPlan daliyCopyMeterPlan = null;
        try {
            daliyCopyMeterPlan = daliyCopyMeterPlanService.findCopyMeterPlanById(id);
        } catch (Exception e) {
            logger.error("--------findCopyMeterPlanDetails---------", e);
        }

        return daliyCopyMeterPlan;
    }


    /**
     * 删除抄表
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlanRequencyById", method = RequestMethod.POST)
    public boolean deleteCopyMeterPlanRequencyById(@RequestParam("ids") String[] ids) {

        try {
            daliyCopyMeterPlanService.deleteCopyMeterPlanRequencyById(ids);
            return true;
        } catch (Exception e) {
            logger.error("--------deleteCopyMeterPlanRequencyById---------", e);

        }
        return false;
    }

    /**
     * 删除抄表
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlanMeterRelationById", method = RequestMethod.POST)
    public boolean deleteCopyMeterPlanMeterRelationById(@RequestParam("ids") String[] ids) {
        try {
            daliyCopyMeterPlanService.deleteCopyMeterPlanMeterRelationById(ids);
            return true;
        } catch (Exception e) {
            logger.error("--------deleteCopyMeterPlanRequencyById---------", e);

        }
        return false;
    }

    /**
     * 根据id查询频率
     *
     * @param id 抄表计划id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanRequencyVosById")
    public PageInfo<DailyCopyMeterPlanRequencyVo> findCopyMeterPlanRequencyVosById(@RequestParam("id") String id) {
        logger.info("findCopyMeterPlanRequencyVosById - - -- > param :{}", id);
        PageInfo<DailyCopyMeterPlanRequencyVo> pageInfo = null;
        try {
            pageInfo = daliyCopyMeterPlanService
                    .findCopyMeterPlanRequencyVosById(id);

//            if (pageInfo != null) {
//                List<DailyCopyMeterPlanVoForList> dailyCopyMeterPlanVoForLists = pageInfo.getList();
//                for (DailyCopyMeterPlanVoForList dailyCopyMeterPlanVoForList : dailyCopyMeterPlanVoForLists) {
//                    DaliyRfCollector daliyRfCollector = daliyCopyMeterService.findcollect(dailyCopyMeterPlanVoForList.getId(), "daliy", Common.EAM_PROD_IDS[0]);
//                    if (daliyRfCollector != null) {
//                        dailyCopyMeterPlanVoForList.setCollect(true);
//                    } else {
//                        dailyCopyMeterPlanVoForList.setCollect(false);
//                    }
//                }
//            }
        } catch (Exception e) {
            logger.error("--------findCopyMeterPlans---------", e);
        }
        return pageInfo;
    }

    /**
     * 根据id查询仪表与计划
     *
     * @param id 抄表计划id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanMeterRelationVosById", method = RequestMethod.POST)
    public PageInfo<DaliyCopyMeterPlanMeterRelationVo> findCopyMeterPlanMeterRelationVosById(String id) {
        logger.info("findCopyMeterPlanMeterRelationVosById - - -- > param :{}", id);
        PageInfo<DaliyCopyMeterPlanMeterRelationVo> pageInfo = null;
        try {
            pageInfo = daliyCopyMeterPlanService
                    .findCopyMeterPlanMeterRelationVosById(id);

//            if (pageInfo != null) {
//                List<DailyCopyMeterPlanVoForList> dailyCopyMeterPlanVoForLists = pageInfo.getList();
//                for (DailyCopyMeterPlanVoForList dailyCopyMeterPlanVoForList : dailyCopyMeterPlanVoForLists) {
//                    DaliyRfCollector daliyRfCollector = daliyCopyMeterService.findcollect(dailyCopyMeterPlanVoForList.getId(), "daliy", Common.EAM_PROD_IDS[0]);
//                    if (daliyRfCollector != null) {
//                        dailyCopyMeterPlanVoForList.setCollect(true);
//                    } else {
//                        dailyCopyMeterPlanVoForList.setCollect(false);
//                    }
//                }
//            }
        } catch (Exception e) {
            logger.error("--------findCopyMeterPlanMeterRelationVosById---------", e);
        }
        return pageInfo;
    }

//
//
//    /**
//     * 根据抄表id查询详细列表
//     *
//     * @param id 抄表id
//     * @return
//     */
//    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanDetailByMeterId", method = RequestMethod.GET)
//    public PageInfo<DailyCopyMeterPlanDetailForList> findCopyMeterPlanDetailByMeterId(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
//        PageInfo<DailyCopyMeterPlanDetailForList> pageInfo = null;
//        try {
//            pageInfo = daliyCopyMeterPlanService
//                    .findCopyMeterPlanDetailByMeterId(id, siteId, orgId);
//        } catch (Exception e) {
//            logger.error("--------findCopyMeterPlanDetailByMeterId---------", e);
//        }
//
//        return pageInfo;
//    }
//
//    /**
//     * 根据id删除抄表单详细列表
//     *
//     * @param ids 抄表单id
//     * @return
//     */
//    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlanDetail", method = RequestMethod.GET)
//    public boolean deleteCopyMeterPlanDetail(@RequestParam("ids") String[] ids) {
//        try {
//            daliyCopyMeterPlanService.deleteCopyMeterPlanDetail(ids);
//            return true;
//        } catch (Exception e) {
//            logger.error("--------deleteCopyMeterPlan---------", e);
//
//        }
//        return false;
//    }
//

    /**
     * 修改抄表单状态
     *
     * @param ids 操作的id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/updateCopyMeterPlanStatus", method = RequestMethod.POST)
    public boolean updateCopyMeterPlanStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status) {
        try {
            daliyCopyMeterPlanService.updateCopyMeterPlanStatus(ids, status);
            return true;
        } catch (Exception e) {
            logger.error("--------updateCopyMeterPlanStatus---------", e);

        }
        return false;
    }
//
//
//    /**
//     * 添加收藏
//     *
//     * @param id        收藏id
//     * @param eamProdId 产品id
//     * @param type      类型
//     * @param personId  人员id
//     */
//    @RequestMapping(value = "/eam/micro/copyMeterPlan/collect", method = RequestMethod.POST)
//    void collect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId) {
//        daliyCopyMeterPlanService.collect(id, eamProdId, type, personId);
//    }
//
//    /**
//     * 取消收藏
//     *
//     * @param id        收藏id
//     * @param eamProdId 产品id
//     * @param type      类型
//     * @param personId  人员id
//     */
//    @RequestMapping(value = "/eam/micro/copyMeterPlan/cancelCollect", method = RequestMethod.POST)
//    void cancelCollect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId) {
//        daliyCopyMeterPlanService.cancelCollect(id, eamProdId, type, personId);
//    }
}
