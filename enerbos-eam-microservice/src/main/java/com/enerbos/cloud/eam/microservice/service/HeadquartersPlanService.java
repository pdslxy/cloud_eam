package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlan;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 20:30
 * @Description 总部计划业务层接口
 */
public interface HeadquartersPlanService {

    /**
     * 分页过滤排序
     * @param filter
     * @return
     */
    PageInfo<HeadquartersPlanVo> findPageList(HeadquartersPlanVoForFilter filter);

    /**
     * 查询总部计划详细
     * @param id  表单ID
     * @return
     */
    HeadquartersPlanVo findDetail(String id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteById(List<String> ids);


    /**
     * 保存（添加、修改）
     * @param savevo
     */
    HeadquartersPlanVoForSave save(HeadquartersPlanVoForSave savevo);

    /**
     * 批量修改状态
     * @param headquartersPlanVoForUpStatus
     */
    void upStatus(HeadquartersPlanVoForUpStatus headquartersPlanVoForUpStatus);

    /**
     * 批量下达
     * @param ids 总部计划主键ID集合
     */
    Boolean batchRelease(List<String> ids);

    /**
     * 生成例行工作
     * @param work
     * @return
     */
    Boolean createRoutineWork(HeadquartersDailyVoForCreateWork work);


    /**
     * 批量生成工作单
     * @param batchLabor
     * @return
     */
    List<DispatchWorkOrderFlowVo> batchLabor(HeadquartersDailyTaskVoForBatchLabor batchLabor);

    /**
     *
     * @param filter 过滤条件
     * @return
     */
    public  List<HeadquartersPlanVo> getHeadquartersPlanAllByFilter(Map<String, Object> filter);

    /**
     * 查询总部计划
     * @param ids
     * @return
     */
    List<HeadquartersPlanVo> findListByIds( List<String> ids);
}
