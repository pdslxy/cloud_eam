package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanRfCollector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月21日
 * @Description 预防性维护计划-收藏
 */
@Mapper
public interface MaintenanceMaintenancePlanRfCollectorDao {

    /**
     * 根据用户编号查询关注的预防性维护计划列表
     * @param personId  用户编号
     * @return 用户关注的预防性维护计划列表
     */
    List<MaintenanceMaintenancePlanRfCollector> findMaintenancePlanRfCollectorByPersonId(String personId);

    /**
     * 检查是否已关注
     * @param maintenancePlanId 预防性维护计划编号
     * @param personId        人员编号
     * @param product         产品编号
     * @return Integer 大于0：已关注  0：未关注
     */
    Integer checkIsCollected(@Param("maintenancePlanId") String maintenancePlanId, @Param("personId") String personId, @Param("product") String product);
}
