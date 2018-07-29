package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.RepairOrder;
import com.enerbos.cloud.eam.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 18:13
 * @Description EAM报修工单接口
 */
public interface RepairOrderService {

    /**
     * 根据工单ID查询
     * @param id 工单ID
     * @return RepairOrder
     */
    RepairOrder findOne(String id);

    /**
     * 根据工单ID查询
     * @param id 工单ID
     * @return 流程Vo
     */
    RepairOrderFlowVo findRepairOrderFlowVoById(String id);

    /**
     * 根据工单ID查询
     * @param ids 工单ID
     * @return 报修工单VO列表
     */
    List<RepairOrderFlowVo> findRepairOrderFlowVoByIdList(String[] ids);

    /**
     * 分页查询报修工单列表
     * @param repairOrderListFilterVo 筛选条件
     * @return 报修工单列表VO
     */
    List<RepairOrderListVo> findListByFilter(RepairOrderListFilterVo repairOrderListFilterVo);


    /**
     *  创建或更新报修工单
     * @param repairOrderFlowVo 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    RepairOrder saveOrUpdate(RepairOrderFlowVo repairOrderFlowVo);

    /**
     *  保存报修工单
     * @param repairOrder 报修工单
     * @return RepairOrder 更新后的报修工单
     */
    RepairOrder save(RepairOrder repairOrder);

    /**
     *  工单提报
     * @param repairOrderFlowVo 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    RepairOrderFlowVo reportOrder(RepairOrderFlowVo repairOrderFlowVo);

    /**
     * 批量删除工单
     * @param ids 工单ID
     */
    void delete(List<String> ids);

    /**
     * 批量变更状态
     * @param ids 工单ID
     * @param status 指定的状态
     * @return 原始工单状态
     */
    Map<String, String> changeOrderStatus(List<String> ids, String status);

    /**
     * 收藏工单
     * @param repairOrderRfCollectorVoList 收藏的工单列表
     */
    void collectWorkOrder(List<RepairOrderRfCollectorVo> repairOrderRfCollectorVoList);

    /**
     * 取消收藏
     * @param repairOrderRfCollectorVoList 需要取消收藏的工单列表
     */
    void cancelCollectWorkOrder(List<RepairOrderRfCollectorVo> repairOrderRfCollectorVoList);

    /**
     * 按月查询报修工单各专业的统计分析 （柱状图）-------首页KPI
     * @param siteId 站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate 结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    List<Object> findCountRepair(String startDate, String endDate,String siteId);

    /**
     * 按月查询未修复报修工单各专业的统计分析 （柱状图）-------首页KPI
     * @param siteId 站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate 结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    List<Object> findUndoneCountRepair(String startDate, String endDate, String siteId);


    /**
     * 查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     * @param orgId  组织ID
     * @param siteId 站点id
     * @return 查询结果
     */
    OrderCountBySiteVo findCountByStatus(String orgId, String siteId, Date startDate, Date endDate);

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     * @param map 站点id和日期
     * @return 查询结果
     */
    Map<String,Object> findCountAndRingratio(Map<String,String> map);

    /**
     * 报修小程序首页统计已完成，为分派，处理中，已评价的数据
     * @param map 站点id和人员id
     * @return 查询结果
     */
    public Map<String,Object> findCountRepairAndEvaluate(Map<String,String> map);

    /**
     * 查询报修工单总数
     *
     * @param orgId     组织ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 查询结果
     */
    OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate);

    /**
     * 查询报修工单总数
     *
     * @param orgId     组织ID
     * @param siteId 站点ID
     * @param orderSource   工单来源
     * @return 查询结果
     */
    int getRepairOrderCount(String orgId, String siteId, String orderSource);
}
