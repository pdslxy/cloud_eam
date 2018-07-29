package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanAssetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanAsset;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 与标准作业计划关联的设备接口
 */
@Mapper
public interface MaintenanceMaintenancePlanAssetDao {

	/**
	 * findAssetListByWorkOrderId: 根据预防维护计划ID查询设备
	 * @param maintenancePlanId 预防维护计划ID
	 * @return List<MaintenanceMaintenancePlanAssetVo> 返回关联设备集合
	 */
	List<MaintenanceMaintenancePlanAssetVo> findAssetListByMaintenancePlanId(String maintenancePlanId);

	/**
	 * findAssetListByMaintenancePlanIds: 根据预防性维护计划ID集合查询关联设备id
	 * @param maintenancePlanIds 预防维护计划ID List
	 * @return List<String> 返回ID集合
	 */
	List<String> findAssetListByMaintenancePlanIds(@Param("maintenancePlanIds") List<String> maintenancePlanIds,@Param("assetIds") List<String> assetIds);
}
