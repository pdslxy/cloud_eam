package com.enerbos.cloud.eam.microservice.service;

import java.util.List;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanActiveTime;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 预防性维护计划有效时间service
 */
public interface MaintenanceMaintenancePlanActiveTimeService {
	/**
	 * saveMaintenancePlanActiveTimeList: 保存预防性维护计划有效时间
	 * @param maintenanceMaintenancePlanActiveTimeList 预防性维护计划有效时间实体集合{@link com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanActiveTime}
	 * @return List<MaintenanceMaintenancePlanActiveTime> 预防性维护计划有效时间实体集合
	 */
	List<MaintenanceMaintenancePlanActiveTime> saveMaintenancePlanActiveTimeList(List<MaintenanceMaintenancePlanActiveTime> maintenanceMaintenancePlanActiveTimeList);

	/**
	 * deleteMaintenancePlanActiveTimeListByIds: 删除预防性维护计划有效时间
	 * @param ids
	 * @return Boolean 删除是否成功
	 */
	Boolean deleteMaintenancePlanActiveTimeListByIds(List<String> ids);

	/**
	 * deleteMaintenancePlanActiveTimeByMaintenancePlanIds: 根据预防性维护计划ID集合删除关联的有效时间
	 * @param maintenancePlanIds 预防性维护计划ID集合
	 * @return Boolean 删除是否成功
	 */
	Boolean deleteMaintenancePlanActiveTimeByMaintenancePlanIds(List<String> maintenancePlanIds);

	/**
	 * findAllMaintenancePlanActiveTime: 根据预防维护计划ID查询有效时间集合
	 * @param maintenancePlanId 预防性维护计划ID
	 * @return List<MaintenanceMaintenancePlanActiveTime> 预防性维护计划有效时间Vo集合
	 */
	List<MaintenanceMaintenancePlanActiveTimeVo> findAllMaintenancePlanActiveTime(String maintenancePlanId);
}
