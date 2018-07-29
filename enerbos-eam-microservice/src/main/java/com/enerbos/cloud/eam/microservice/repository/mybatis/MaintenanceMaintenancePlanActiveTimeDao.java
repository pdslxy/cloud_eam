package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanActiveTime;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description PM的季节接口
 */
@Mapper
public interface MaintenanceMaintenancePlanActiveTimeDao {

	/**
	 * findAllMaintenancePlanActiveTime: 根据预防维护计划ID查询预防维护计划的有效时间列表
	 * @param maintenancePlanId 预防维护计划ID
	 * @return List<MaintenanceMaintenancePlanActiveTime> 返回预防维护计划的有效时间集合
	 */
	List<MaintenanceMaintenancePlanActiveTimeVo> findAllMaintenancePlanActiveTime(String maintenancePlanId);

	/**
	 * findMaintenancePlanActiveTime: 查询预防维护计划的有效时间列表
	 * @param maintenanceMaintenancePlanActiveTime 有效时间Vo
	 * @return MaintenanceMaintenancePlanActiveTime 返回预防维护计划的有效时间
	 */
	MaintenanceMaintenancePlanActiveTime findMaintenancePlanActiveTime(MaintenanceMaintenancePlanActiveTime maintenanceMaintenancePlanActiveTime);

	/**
	 * findMaintenancePlanActiveTimeByMaintenancePlanIds: 根据作业标准ID集合查询有效时间id
	 * @param maintenancePlanIds 预防维护计划ID List
	 * @return List<String> 返回有效时间ID集合
	 */
	List<String> findMaintenancePlanActiveTimeByMaintenancePlanIds(@Param("maintenancePlanIds") List<String> maintenancePlanIds);
}
