package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrder;
import com.enerbos.cloud.eam.vo.*;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-23 17:10
 * @Description EAM派工工单接口
 */
public interface DispatchWorkOrderService {

    /**
     * 派工工单-查询
     *
     * @param id 工单编号
     * @return DispatchWorkOrder
     */
    DispatchWorkOrder findOne(String id);

    /**
     * /**
     * 派工工单-查询
     *
     * @param id 工单编号
     * @return DispatchWorkOrderFlowVo 流程Vo
     */
    DispatchWorkOrderFlowVo findDispatchWorkOrderFlowVoById(String id);

    /**
     * 派工工单-查询ID列表
     *
     * @param ids 工单ID列表
     * @return List&lt;DispatchWorkOrderFlowVo&gt; 派工工单集合
     */
    List<DispatchWorkOrderFlowVo> findDispatchWorkOrderFlowVoByIdList(String[] ids);

    /**
     * 分页查询派工工单列表
     *
     * @param dispatchWorkOrderListFilterVo 筛选条件
     * @return 派工工单列表VO
     */
    List<DispatchWorkOrderListVo> findListByFilter(DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo);

    /**
     * 派工工单-保存&更新
     *
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return DispatchWorkOrder 更新后的派工工单
     */
    DispatchWorkOrder saveOrUpdate(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo);

    /**
     * 保存派工工单
     *
     * @param dispatchWorkOrder 派工工单
     * @return DispatchWorkOrder 更新后的派工工单
     */
    DispatchWorkOrder save(DispatchWorkOrder dispatchWorkOrder);

    /**
     * 派工工单-提交流程
     *
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    DispatchWorkOrderFlowVo commitWorkOrder(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo);

    /**
     * 批量变更状态
     *
     * @param ids    工单ID
     * @param status 指定的状态
     */
    Map<String, String> changeOrderStatus(List<String> ids, String status);

    /**
     * 批量删除工单
     *
     * @param ids 工单ID
     */
    void delete(List<String> ids);

    /**
     * 收藏工单
     *
     * @param dispatchWorkOrderRfCollectorVoList 收藏的工单列表
     */
    void collectWorkOrder(List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList);

    /**
     * 取消收藏
     *
     * @param dispatchWorkOrderRfCollectorVoList 需要取消收藏的工单列表
     */
    void cancelCollectWorkOrder(List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList);

    /**
     * 获取派工单总数
     *
     * @return
     */
    OrderCountBySiteVo findDispatchWorkOrderTotal(Map<String,Object> map);

    /**
     * 查询时间段内组织下最大工单数
     * @param orgId 组织
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate);
}
