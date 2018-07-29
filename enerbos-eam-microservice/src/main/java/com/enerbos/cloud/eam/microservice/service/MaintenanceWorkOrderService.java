package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrder;
import com.enerbos.cloud.eam.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM维保工单service
 */
public interface MaintenanceWorkOrderService {

    /**
     * findWorkOrderByID: 根据ID查询维保工单
     * @param id
     * @return MaintenanceWorkOrder
     */
    MaintenanceWorkOrder findWorkOrderByID(String id);

    /**
     * findWorkOrderByIDs: 根据ID数组查询维保工单
     * @param ids
     * @return List<MaintenanceWorkOrderForDetailVo>
     */
    List<MaintenanceWorkOrderForDetailVo> findWorkOrderByIDs(List<String> ids);

    /**
     * findEamWorkOrderCommitByID: 根据ID查询维保工单-工单提报
     *
     * @param id
     * @return MaintenanceWorkOrderForCommitVo
     */
    MaintenanceWorkOrderForCommitVo findEamWorkOrderCommitByID(String id);

    /**
     * findEamWorkOrderAssignByID: 根据ID查询维保工单-任务分派
     *
     * @param id
     * @return MaintenanceWorkOrderForAssignVo
     */
    MaintenanceWorkOrderForAssignVo findEamWorkOrderAssignByID(String id);

    /**
     * findEamWorkOrderReportByID: 根据ID查询维保工单-执行汇报
     *
     * @param id
     * @return MaintenanceWorkOrderForReportVo
     */
    MaintenanceWorkOrderForReportVo findEamWorkOrderReportByID(String id);

    /**
     * findEamWorkOrderCheckAcceptByID: 根据ID查询维保工单-验收确认
     *
     * @param id
     * @return MaintenanceWorkOrderForCheckAcceptVo
     */
    MaintenanceWorkOrderForCheckAcceptVo findEamWorkOrderCheckAcceptByID(String id);

    /**
     * findWorkOrderCommitByWorkOrderNum: 根据woNum查询维保工单-工单提报
     *
     * @param woNum 维保工单编码
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报
     */
    MaintenanceWorkOrderForCommitVo findWorkOrderCommitByWorkOrderNum(String woNum);

    /**
     * findWorkOrderList:分页查询维保工单
     *
     * @param maintenanceWorkOrderSelectVo
     * @return List<MaintenanceWorkOrderForListVo>
     */
    List<MaintenanceWorkOrderForListVo> findWorkOrderList(MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo);

    /**
     * findPageWorkOrderByAssetId:根据设备ID分页查询维保工单
     *
     * @param maintenanceForAssetFilterVo 根据设备查询维保工单列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
     * @return List<MaintenanceWorkOrderForListVo> 维保工单Vo集合
     */
    List<MaintenanceWorkOrderForListVo> findPageWorkOrderByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);

    /**
     * save:保存维保工单
     *
     * @param maintenanceWorkOrder
     * @return MaintenanceWorkOrder
     */
    MaintenanceWorkOrder save(MaintenanceWorkOrder maintenanceWorkOrder);

    /**
     * deleteEamWorkOrderById:根据ID删除维保工单
     *
     * @param id
     * @return
     */
    void deleteEamWorkOrderById(String id);

    /**
     * deleteEamWorkOrderByIds:根据ID集合删除维保工单
     *
     * @param ids
     * @return
     */
    void deleteEamWorkOrderByIds(List<String> ids);

    /**
     * findWorkOrderSingleAssetLastTimeById:根据维保工单ID查询该预防性维护计划上次生成工单的实际工时（分钟）
     *
     * @param id
     * @return Double 该预防性维护计划上次生成工单的实际工时（分钟）
     */
    Double findWorkOrderSingleAssetLastTimeById(String id);


    /**
     * 收藏维保工单
     *
     * @param maintenanceWorkOrderRfCollectorVoList 收藏的维保工单列表
     */
    void collectWorkOrder(List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList);

    /**
     * 取消收藏
     *
     * @param maintenanceWorkOrderRfCollectorVoList 需要取消收藏的维保工单列表
     */
    void cancelCollectWorkOrder(List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList);

    /**
     * 查询维保工单各专业情况的统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List  findCountWorkOrder(String startDate, String endDate, String siteId);

    /**
     * 查询未完成维保工单各专业情况的统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    List  findCountUndoneWorkOrder(String startDate, String endDate, String siteId);


    /**
     * 查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     *
     * @param orgId  组织ID
     * @param siteId 站点id
     * @return 查询结果
     */
    OrderCountBySiteVo findCountByStatus(String orgId, String siteId, Date startDate, Date endDate);

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param map 站点id和创建日期
     * @return 查询结果
     */
    Map<String, Object> findCountAndRingratio(Map<String, String> map);


    /**
     * 查询工单最大数量
     *
     * @param orgId     组织id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<OrderMaxCountVo> findMaxCountOrder(String orgId, String startDate, String endDate);

    /**
     * 统计总部维修工单、保养工单在时间段内的总量
     *
     * @param orgId     组织Id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<DashboardOrderCountVo> findWorkOrderTotal(String orgId, Date startDate, Date endDate);
}
