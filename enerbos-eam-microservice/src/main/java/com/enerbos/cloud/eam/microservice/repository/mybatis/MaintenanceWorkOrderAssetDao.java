package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderAsset;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单设备表接口
 */
@Mapper
public interface MaintenanceWorkOrderAssetDao {

	/**
	 * findEamWoasset: 根据ID查询工单设备表
	 * @param id
	 * @return MaintenanceWorkOrderAsset
	 */
	MaintenanceWorkOrderAsset findWorkOrderAssetById(String id);

	/**
	 * findAssetListByWorkOrderId: 根据工单ID查询设备
	 * @param workOrderId 工单ID
	 * @return List<String> 返回设备ID集合
	 */
	List<String> findAssetListByWorkOrderId(String workOrderId);
	
	/**
	 * findWoAssetListByWorkOrderIdAndAssetIds: 根据工单ID和设备ID查询工单设备表
	 * @param params 查询条件
	 * @return List<MaintenanceWorkOrderAsset> 返回工单设备表集合
	 */
	List<MaintenanceWorkOrderAsset> findWorkOrderAssetListByWorkOrderIdAndAssetIds(@Param("params") Map<String, Object> params);

	/**
	 * findAssetListByWorkOrderIds: 根据维保工单ID集合查询关联设备id
	 * @param workOrderIds 维保工单ID List
	 * @param assetIds 设备ID集合
	 * @return List<String> 返回ID集合
	 */
	List<String> findAssetListByWorkOrderIds(@Param("workOrderIds") List<String> workOrderIds,@Param("assetIds") List<String> assetIds);

}
