package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 20:30
 * @Description 总部事物，例行工作业务层接口
 */
public interface HeadquartersDailyService {

    /**
     * 分页过滤排序
     * @param filter
     * @return
     */
    PageInfo<HeadquartersDailyVo> findPageList(HeadquartersDailyVoForFilter filter);
    /**
     * 查询总部计划详细
     * @param id
     * @return
     */
    HeadquartersDailyVo findDetail(String id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteById(List<String> ids);


    /**
     * 保存（添加、修改）
     * @param savevo
     */
    HeadquartersDailyVoForSave save(HeadquartersDailyVoForSave savevo);

    /**
     * 批量修改状态
     * @param headquartersDailyVoForUpStatus
     */
    void upStatus(HeadquartersDailyVoForUpStatus headquartersDailyVoForUpStatus);

}
