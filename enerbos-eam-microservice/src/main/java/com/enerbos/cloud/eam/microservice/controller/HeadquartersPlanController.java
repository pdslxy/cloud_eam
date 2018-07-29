package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlanRange;
import com.enerbos.cloud.eam.microservice.service.HeadquartersPlanRangeService;
import com.enerbos.cloud.eam.microservice.service.HeadquartersPlanService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/10 11:21
 * @Description  总部计划--控制层
 */
@RestController
public class HeadquartersPlanController {

    private Logger logger = LoggerFactory.getLogger(HeadquartersPlanController.class);

    @Autowired
    private HeadquartersPlanService headquartersPlanService;
    @Autowired
    private HeadquartersPlanRangeService headquartersPlanRangeService;

    /**
     * 总部计划--分页、筛选、排序
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findPageList", method = RequestMethod.POST)
    public PageInfo<HeadquartersPlanVo> findPageList(@RequestBody HeadquartersPlanVoForFilter filter){
        PageInfo<HeadquartersPlanVo> pageInfo = new PageInfo<>();
        try {
            pageInfo = headquartersPlanService.findPageList(filter);

        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/findPageList-----", e);
        }
        return pageInfo;
    }

    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findDetail", method = RequestMethod.POST)
    public  HeadquartersPlanVo findDetail(@RequestParam("id") String id,
                                          @RequestParam(value = "orgId") String orgId,
                                          @RequestParam(value = "siteId",required = false) String siteId){
        HeadquartersPlanVo planVo = new HeadquartersPlanVo();
        try {
            planVo = headquartersPlanService.findDetail(id);
            if(orgId!=null&&siteId==null){//组织级别
                List<String> siteList = headquartersPlanRangeService.getListByPlanId(planVo.getId());
                planVo.setPlanSite(siteList);
            }else{//
                 List list=new ArrayList();
                 list.add(siteId);
                 planVo.setPlanSite(list);
            }
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/findPageList-----", e);
        }
        return planVo;
    }
    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findDetailById", method = RequestMethod.POST)
    public  HeadquartersPlanVo findDetailById(@RequestParam("id") String id){
        HeadquartersPlanVo planVo = new HeadquartersPlanVo();
        try {
            planVo = headquartersPlanService.findDetail(id);
                List<String> siteList = headquartersPlanRangeService.getListByPlanId(planVo.getId());
                planVo.setPlanSite(siteList);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/findPageList-----", e);
        }
        return planVo;
    }
    /**
     * 查询详细页
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findDetailByIds", method = RequestMethod.POST)
    public   List<HeadquartersPlanVo> findDetailByIds(
            @RequestParam("ids") List ids,
            @RequestParam(value = "orgId") String orgId,
            @RequestParam(value = "siteId") String siteId
    ){
        List<HeadquartersPlanVo> planVo = new ArrayList<>();
        try {
          //  planVo = headquartersPlanService.findDetail(id);
             planVo = headquartersPlanService.findListByIds(ids);

        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/findDetailByIds-----", e);
        }
        return planVo;
    }

    /**
     * 总部计划--批量保存
     * @param filters
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/batchSave", method = RequestMethod.POST)
    public List<HeadquartersPlanVoForSave> batchSave(@RequestBody List<HeadquartersPlanVoForSave> filters){
        List<HeadquartersPlanVoForSave> vos = new ArrayList<>();
        try {
            filters.forEach( vo -> {
                HeadquartersPlanVoForSave voForSave = headquartersPlanService.save(vo);
                vos.add(voForSave);
            });
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/batchSave-----", e);
        }
        return vos;
    }

    /**
     * 保存
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/save", method = RequestMethod.POST)
    public HeadquartersPlanVoForSave save(@RequestBody HeadquartersPlanVoForSave filter){
        HeadquartersPlanVoForSave vo = new HeadquartersPlanVoForSave();
        try {
            vo = headquartersPlanService.save(filter);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/save-----", e);
        }
        return vo;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/delete", method = RequestMethod.POST)
    public Boolean batchDelete(@RequestParam("ids") List<String> ids){
        try {
            headquartersPlanService.deleteById(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/save-----", e);
        }
        return true;
    }


    /**
     * 批量修改状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/upStrtus", method = RequestMethod.POST)
    public Boolean upStrtus(@RequestBody HeadquartersPlanVoForUpStatus vo){
        try {
            headquartersPlanService.upStatus(vo);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/save-----", e);
        }
        return true;
    }

    /**
     * 批量下达
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/batchRelease", method = RequestMethod.POST)
    public Boolean batchRelease(@RequestParam("ids") List<String> ids){
        Boolean res = false;
        try {
            res = headquartersPlanService.batchRelease(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/batchRelease-----", e);
        }
        return res;
    }

    /**
     * 生成例行工作
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/createRoutineWork", method = RequestMethod.POST)
    public Boolean createRoutineWork(@RequestBody HeadquartersDailyVoForCreateWork vo){
        Boolean res = false;
        try {
            res = headquartersPlanService.createRoutineWork(vo);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/createRoutineWork-----", e);
        }
        return res;
    }


    @RequestMapping(value = "/eam/micro/headquartersPlan/getHeadquartersPlanAllByFilter", method = RequestMethod.POST)
    public  List<HeadquartersPlanVo> getHeadquartersPlanAllByFilter(Map<String, Object> filter){
        return  headquartersPlanService.getHeadquartersPlanAllByFilter(filter);
    }

    /**
     * 生成派工单
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/batchLabor", method = RequestMethod.POST)
    public List<DispatchWorkOrderFlowVo> BatchLabor(@RequestBody HeadquartersDailyTaskVoForBatchLabor vo){
        List<DispatchWorkOrderFlowVo> res = new ArrayList<>();
        try {
            res = headquartersPlanService.batchLabor(vo);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersPlan/batchLabor-----", e);
        }
        return res;
    }





}
