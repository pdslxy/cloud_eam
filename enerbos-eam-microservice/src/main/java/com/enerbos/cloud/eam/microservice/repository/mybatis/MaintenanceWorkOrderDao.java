package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrder;
import com.enerbos.cloud.eam.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 * @date 2017年06月02日
 * @Description 维保工单Dao
 */
@Mapper
public interface MaintenanceWorkOrderDao {

    /**
     * findWorkOrderById: 根据ID查询维保工单
     * @param id
     * @return MaintenanceWorkOrder
     */
    MaintenanceWorkOrder findWorkOrderById(String id);

    /**
     * findWorkOrderByIds: 根据Ids查询维保工单
     * @param ids id数组
     * @return List<MaintenanceWorkOrderForDetailVo>
     */
    List<MaintenanceWorkOrderForDetailVo> findWorkOrderByIds(@Param("ids") List<String> ids);

    /**
     * findWorkOrderCommitByID: 根据ID查询维保工单-工单提报
     *
     * @param id
     * @return MaintenanceWorkOrderForCommitVo
     */
    MaintenanceWorkOrderForCommitVo findWorkOrderCommitByID(String id);

    /**
     * findWorkOrderAssignByID: 根据ID查询维保工单-任务分派
     *
     * @param id
     * @return MaintenanceWorkOrderForAssignVo
     */
    MaintenanceWorkOrderForAssignVo findWorkOrderAssignByID(String id);

    /**
     * findWorkOrderReportByID: 根据ID查询维保工单-执行汇报
     *
     * @param id
     * @return MaintenanceWorkOrderForReportVo
     */
    MaintenanceWorkOrderForReportVo findWorkOrderReportByID(String id);

    /**
     * findWorkOrderCheckAcceptByID: 根据ID查询维保工单-验收确认
     *
     * @param id
     * @return MaintenanceWorkOrderForCheckAcceptVo
     */
    MaintenanceWorkOrderForCheckAcceptVo findWorkOrderCheckAcceptByID(String id);

    /**
     * findWorkOrderCommitByWorkOrderNum: 根据woNum查询维保工单-工单提报
     *
     * @param workOrderNum 维保工单编码
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报
     */
    MaintenanceWorkOrderForCommitVo findWorkOrderCommitByWorkOrderNum(String workOrderNum);

    /**
     * findEamWorkorder: 查询维保工单列表
     *
     * @param maintenanceWorkOrderSelectVo 过滤条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
     * @return List<MaintenanceWorkOrderForListVo>
     */
    List<MaintenanceWorkOrderForListVo> findEamWorkOrder(MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo);

    /**
     * findPageWorkOrderByAssetId:根据设备ID分页查询维保工单
     *
     * @param maintenanceForAssetFilterVo 根据设备查询维保工单列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
     * @return List<TaskMaintenanceWorkOrderForListVo> 维保工单Vo集合
     */
    List<MaintenanceWorkOrderForListVo> findPageWorkOrderByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);

    /**
     * findWorkOrderSingleAssetLastTimeById:根据维保工单ID查询该预防性维护计划上次生成工单的实际工时（分钟）
     *
     * @param id
     * @return Double 该预防性维护计划上次生成工单的实际工时（分钟）
     */
    Double findWorkOrderSingleAssetLastTimeById(@Param("id") String id);

    /**
     * 查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     *
     * @param orgId  组织id
     * @param siteId 站点id
     * @return 查询结果
     */
    OrderCountBySiteVo findCountByStatus(@Param("orgId") String orgId, @Param("siteId") String siteId,
                                         @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param map 站点id
     * @return 查询结果
     */
    Map<String, Object> findCountAndRingratio(Map<String, String> map);

    /**
     * 查询工单最大数量
     *
     * @param param 查询参数
     * @return
     */
    List<OrderMaxCountVo> findMaxCountOrder(Map<String, String> param);

    /**
     * 统计总部维修工单、保养工单在时间段内的总量
     *
     * @param param 查询参数
     * @return
     */
    List<DashboardOrderCountVo> findWorkOrderTotal(Map<String, Object> param);

    /**
     * 查询维保工单各情况的统计
     * @param map
     * @return
     */
    List findCountWorkOrder(Map map);
    /**
     * 查询未完成维保工单各情况的统计
     * @param map
     * @return
     */
    List findCountUndoneWorkOrder(Map map);
}
