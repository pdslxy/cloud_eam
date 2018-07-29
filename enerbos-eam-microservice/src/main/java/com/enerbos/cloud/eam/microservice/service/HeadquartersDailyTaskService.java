package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 张鹏伟
 * @version 1.0
 * @date 2017/8/16 14:01
 * @Description 例行工作单
 */
public interface HeadquartersDailyTaskService {

    /**
     * 分页过滤排序
     * @param filter
     * @return
     */
    PageInfo<HeadquartersDailyTaskVo> findPageList(HeadquartersDailyTaskVoForFilter filter);

    /**
     * 查询总部计划详细
     * @param id
     * @return
     */
    HeadquartersDailyTaskVo findDetail(String id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteById(String ids);


    /**
     * 保存（添加、修改）
     * @param savevo
     */
    HeadquartersDailyTaskVo save(HeadquartersDailyTaskVo savevo);

    /**
     * 根据例行工作计划Id,组织，站点查询该工单生成的例行工作单
     * @param planId 计划Id
     * @param orgId  组织Id
     * @param siteId 站点Id
     * @return
     */
    List<HeadquartersDailyTaskVo> findDailyTaskByplanId(  String planId, String orgId,  String siteId);

    /**
     *
     * @param id    例行工作单id
     * @param status 例行工作状态
     */
    void upStatus(String[] id,String status);

}
