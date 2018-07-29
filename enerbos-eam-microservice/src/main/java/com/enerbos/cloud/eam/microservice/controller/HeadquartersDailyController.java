package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.HeadquartersDailyService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/10 11:21
 * @Description  总部事物，例行工作--控制层
 */
@RestController
public class HeadquartersDailyController {

    private Logger logger = LoggerFactory.getLogger(HeadquartersDailyController.class);

    @Autowired
    private HeadquartersDailyService headquartersDailyService;

    /**
     * 总部事物，例行工作--分页、筛选、排序
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/findPageList", method = RequestMethod.POST)
    public PageInfo<HeadquartersDailyVo> findPageList(@RequestBody HeadquartersDailyVoForFilter filter){
        PageInfo<HeadquartersDailyVo> pageInfo = new PageInfo<>();
        try {
            pageInfo = headquartersDailyService.findPageList(filter);

        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDaily/findPageList-----", e);
        }
        return pageInfo;
    }

    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/findDetail", method = RequestMethod.POST)
    public  HeadquartersDailyVo findDetail(@RequestParam("id") String id){
        HeadquartersDailyVo dailyVo = new HeadquartersDailyVo();
        try {
            dailyVo = headquartersDailyService.findDetail(id);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDaily/findPageList-----", e);
        }
        return dailyVo;
    }

    /**
     * 保存
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/save", method = RequestMethod.POST)
    public HeadquartersDailyVoForSave save(@RequestBody HeadquartersDailyVoForSave filter){
        HeadquartersDailyVoForSave vo = new HeadquartersDailyVoForSave();
        try {
            vo = headquartersDailyService.save(filter);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDaily/save-----", e);
        }
        return vo;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/delete", method = RequestMethod.POST)
    public Boolean batchDelete(@RequestParam("ids") List<String> ids){
        try {
            headquartersDailyService.deleteById(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDaily/save-----", e);
        }
        return true;
    }


    /**
     * 批量修改状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/upStrtus", method = RequestMethod.POST)
    public Boolean upStrtus(@RequestBody HeadquartersDailyVoForUpStatus vo){
        try {
            headquartersDailyService.upStatus(vo);
        } catch (Exception e) {
            logger.error("-------/eam/micro/headquartersDaily/save-----", e);
        }
        return true;
    }

}
