package com.enerbos.cloud.eam.microservice.service;

import java.util.List;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanAsset;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
public interface MaintenanceMaintenancePlanAssetService {
	/**
	 * saveMaintenancePlanAsset: 保存与标准作业计划关联的设备
	 * @param List<MaintenanceMaintenancePlanAsset> 与标准作业计划关联的设备实体集合
	 * @return List<MaintenanceMaintenancePlanAsset> 与标准作业计划关联的设备实体集合
	 */
	public List<MaintenanceMaintenancePlanAsset> saveMaintenancePlanAsset(List<MaintenanceMaintenancePlanAsset> maintenancePlanAssetList);

	/**
	 * deleteMaintenancePlanAssetByIds: 删除与标准作业计划关联的设备(多个)
	 * @param ids 与标准作业计划关联的设备id(数组)
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteMaintenancePlanAssetByIds(List<String> ids);

	/**
	 * deleteMaintenancePlanAssetByMaintenancePlanIds: 根据预防性维护计划ID集合删除关联的有效时间
	 * @param maintenancePlanIds 预防性维护计划ID集合
	 * @return Boolean 删除是否成功
	 */
	Boolean deleteMaintenancePlanAssetByMaintenancePlanIds(List<String> maintenancePlanIds);

	/**
	 * findAssetListByMaintenancePlanId: 根据预防维护计划ID查询设备ID集合
	 * @param maintenancePlanId 预防维护计划ID
	 * @return List<String> 设备ID集合
	 */
	List<String> findMaintenancePlanAssetListByMaintenancePlanId(String maintenancePlanId);

	/**
	 * deleteMaintenancePlanAssetByAssetIds: 删除预防性维护计划关联的设备(多个)
	 * @param maintenancePlanId 预防性维护计划ID
	 * @param assetIds  设备ID集合
	 * @return Boolean 删除是否成功
	 */
	Boolean deleteMaintenancePlanAssetByAssetIds(String maintenancePlanId, List<String> assetIds);
}
