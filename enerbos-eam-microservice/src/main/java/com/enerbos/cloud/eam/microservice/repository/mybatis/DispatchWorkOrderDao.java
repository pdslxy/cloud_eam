package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-23 16:02
 * @Description
 */
@Mapper
public interface DispatchWorkOrderDao {

    /**
     * 派工工单-列表-查询派工工单列表
     *
     * @param dispatchWorkOrderListFilterVo {@link com.enerbos.cloud.eam.vo.DispatchWorkOrderListFilterVo}  筛选条件
     * @return 派工工单列表VO
     */
    List<DispatchWorkOrderListVo> findListByFilter(DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo);

    /**
     * 检查派工工单编号是否存在
     *
     * @param workOrderNum 工单编号
     * @return Integer 1：存在  null：不存在
     */
    Integer checkWorkOrderNum(String workOrderNum);

    /**
     * 根据ID查询工单
     *
     * @param id 派工工单ID
     * @return 派工工单流程Vo
     */
    DispatchWorkOrderFlowVo findOne(String id);

    /**
     * 根据ID查询工单
     *
     * @param ids 派工工单ID
     * @return 派工工单VO列表
     */
    List<DispatchWorkOrderFlowVo> findList(List<String> ids);

    /**
     * 查询总条数
     *
     * @return
     */
    OrderCountBySiteVo findDispatchWorkOrderTotal(Map<String, Object> map);

    /**
     * 查询时间段内组织下最大工单数
     *
     * @return
     */
    OrderMaxCountVo findMaxCountOrder(Map<String, String> param);
}
