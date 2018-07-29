package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolOrder;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/7
 * @Description
 */
public interface PatrolOrderService {
    /**
     * 根据筛选条件和分页信息获取巡检工单列表
     *
     * @return
     */
    PageInfo<PatrolOrderVo> findPatrolOrderList(PatrolOrderVoForFilter patrolOrderVoForFilter) throws Exception;

    /**
     * 根据id数组删除巡检工单
     *
     * @param ids
     */
    void deletePatrolOrderByIds(String[] ids) throws EnerbosException;

    /**
     * 保存巡检工单和关联的巡检项信息
     *
     * @param patrolOrderForSaveVo
     */
    PatrolOrderVo saveOrUpdate(PatrolOrderForSaveVo patrolOrderForSaveVo) throws Exception;

    /**
     * 根据id查询巡检工单
     *
     * @param patrolOrderId
     */
    PatrolOrder findPatrolOrderById(String patrolOrderId) throws EnerbosException;

    /**
     * 根据巡检工单Id查询巡检项
     *
     * @param id
     * @return
     */
    PatrolOrder findPatrolOrderByOrderId(String id) throws EnerbosException;

    /**
     * 保存方法
     *
     * @param patrolOrder
     */
    void save(PatrolOrder patrolOrder) throws EnerbosException;

    /**
     * 查询巡检工单关联的巡检点
     *
     * @param id
     * @return
     */
    PageInfo<PatrolPointVo> findOrderPointList(String id) throws EnerbosException;

    /**
     * 查询巡检工单条数
     *
     * @param orgId     组织id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    OrderCountBySiteVo findPatrolOrderTotal(String orgId, Date startDate, Date endDate);


    /**
     * 查询最大巡检工单数
     *
     * @param orgId     组织
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate);

    /**
     * 根据巡检工单id和是否开始巡检标识，更新是否开始巡检的值
     *
     * @param id
     * @return
     * @throws EnerbosException
     */
    boolean updateIsBeginPatrolById(String id,boolean isBeginPatrol,Date beginPatrolDate) throws EnerbosException;

}

