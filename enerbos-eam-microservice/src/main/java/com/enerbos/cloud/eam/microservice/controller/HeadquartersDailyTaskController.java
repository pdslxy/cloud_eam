package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.HeadquartersDailyTaskService;
import com.enerbos.cloud.eam.microservice.service.HeadquartersPlanService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 张鹏伟
 * @version 1.0
 * @date 2017/8/10 11:21
 * @Description  例行工作单位--控制层
 */
@RestController
public class HeadquartersDailyTaskController {

    private Logger logger = LoggerFactory.getLogger(HeadquartersDailyTaskController.class);

    @Autowired
    private HeadquartersDailyTaskService headquartersDailyTaskService;

    /**
     * 例行工作单位--分页、筛选、排序
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/findPageList", method = RequestMethod.POST)
    public PageInfo<HeadquartersDailyTaskVo> findPageList(@RequestBody HeadquartersDailyTaskVoForFilter filter){
        PageInfo<HeadquartersDailyTaskVo> pageInfo = new PageInfo<>();
        try {
            pageInfo = headquartersDailyTaskService.findPageList(filter);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDailyTask/findPageList-----", e);
        }
        return pageInfo;
    }
    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/findDetail", method = RequestMethod.POST)
    public  HeadquartersDailyTaskVo findDetail(@RequestParam("id") String id){
        HeadquartersDailyTaskVo planVo = new HeadquartersDailyTaskVo();
        try {
            planVo = headquartersDailyTaskService.findDetail(id);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDailyTask/findPageList-----", e);
        }
        return planVo;
    }

    /**
     * 根据例行工作计划Id,组织，站点查询该工单生成的例行工作单
     * @param planId 计划Id
     * @param orgId  组织Id
     * @param siteId 站点Id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/findDailyTaskByplanId", method = RequestMethod.POST)
    public  List<HeadquartersDailyTaskVo> findDailyTaskByplanId(
            @RequestParam("planId") String planId,
            @RequestParam("orgId") String orgId,
            @RequestParam("siteId") String siteId
    ){
        List<HeadquartersDailyTaskVo>taskVos=new ArrayList<>();
        try {
            taskVos = headquartersDailyTaskService.findDailyTaskByplanId(planId,orgId,siteId);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDailyTask/findPageList-----", e);
        }
        return taskVos;
    }



    /**
     * 保存
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/save", method = RequestMethod.POST)
    public HeadquartersDailyTaskVo save(@RequestBody HeadquartersDailyTaskVo filter){
        HeadquartersDailyTaskVo vo = new HeadquartersDailyTaskVo();
        logger.info("------------例行工单保存------------");

        logger.info(filter.toString());
        try {
            vo = headquartersDailyTaskService.save(filter);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDailyTask/save-----", e);
        }
        return vo;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/delete", method = RequestMethod.POST)
    public Boolean batchDelete(@RequestParam("ids") String ids){
        try {
            headquartersDailyTaskService.deleteById(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDailyTask/save-----", e);
        }
        return true;
    }
    /**
     * 修改状态
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/upStrtus", method = RequestMethod.POST)
    public Boolean upStrtus(@RequestParam("ids")  String[] ids,@RequestParam("status") String status){
        try {
            headquartersDailyTaskService.upStatus(ids,status);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDailyTask/save-----", e);
        }
        return true;
    }

}
