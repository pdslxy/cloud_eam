package com.enerbos.cloud.eam.microservice.repository.mybatis;

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
 * @author hk
 * @version 1.0
 * @date 2017-07-11 11:09
 * @Description
 */
@Mapper
public interface RepairOrderDao {

    /**
     * 报修工单-列表-查询报修工单列表
     * @param repairOrderListFilterVo {@link com.enerbos.cloud.eam.vo.RepairOrderListFilterVo}  筛选条件
     * @return 报修工单列表VO
     */
    List<RepairOrderListVo> findListByFilter(RepairOrderListFilterVo repairOrderListFilterVo);

    /**
     * 检查报修工单编号是否存在
     * @param workOrderNum 工单编号
     * @return Integer 1：存在  null：不存在
     */
    Integer checkWorkOrderNum(String workOrderNum);

    /**
     *  根据ID查询工单
     * @param id 报修工单ID
     * @return 报修工单流程Vo
     */
    RepairOrderFlowVo findOne(String id);

    /**
     *  根据ID查询工单
     * @param ids 报修工单ID
     * @return 报修工单VO列表
     */
    List<RepairOrderFlowVo> findList(List<String> ids);

    /**
     * 查询报修工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     * @param siteId 站点id
     * @return 查询结果
     */
    OrderCountBySiteVo findCountByStatus(@Param("orgId") String orgId, @Param("siteId") String siteId,
                                         @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     * @param map 站点id和日期
     * @return 查询结果
     */
    Map<String,Object> findCountAndRingratio(Map<String,String> map);

    /**
     * 报修小程序首页统计已完成，为分派，处理中，已评价的数据再
     * @param map 站点id和人员id
     * @return 查询结果
     */
    Map<String,Object> findCountRepairAndEvaluate(Map<String,String> map);
    
    

    /**
     * 查询报修工单总数
     *
     * @param param 查询条件
     * @return 查询结果
     */
    OrderMaxCountVo findMaxCountOrder(Map<String, String> param);

    /**
     * 总工单数量
     * @param orgId 组织id
     * @param siteId 站点id
     * @param orderSource 工单来源
     * @return
     */
    int getRepairOrderCount(String orgId, String siteId, String orderSource);
    
}
