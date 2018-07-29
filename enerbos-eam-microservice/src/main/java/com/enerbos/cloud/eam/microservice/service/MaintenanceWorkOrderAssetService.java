package com.enerbos.cloud.eam.microservice.service;

import java.util.List;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderAsset;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
public interface MaintenanceWorkOrderAssetService {

	/**
	 * saveWorkOrderAssetList: 保存工单设备表
	 * @param eamWoassetList 工单设备表实体
	 * @return MaintenanceWorkOrderAsset 工单设备表实体
	 */
	public List<MaintenanceWorkOrderAsset> saveWorkOrderAssetList(List<MaintenanceWorkOrderAsset> eamWoassetList);

	/**
	 * findWorkOrderAssetById: 根据工单设备表id查询工单设备表
	 * @param id 工单设备表id
	 * @return MaintenanceWorkOrderAsset 工单设备表实体
	 */
	public MaintenanceWorkOrderAsset findWorkOrderAssetById(String id);
	
	/**
	 * findAssetListByWorkOrderId: 根据工单ID查询设备
	 * @param workOrderId 工单ID
	 * @return List<String> 返回设备ID集合
	 */
	public List<String> findAssetListByWorkOrderId(String workOrderId);

	/**
	 * deleteWorkOrderAssetById: 删除工单设备表(单个)
	 * @param id 工单设备表id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteWorkOrderAssetById(String id);

	/**
	 * deleteWorkOrderAssetById: 删除工单设备表(多个)
	 * @param ids 工单设备表id(数组)
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteWorkOrderAssetByIds(List<String> ids);

	/**
	 * deleteWorkOrderAssetByWorkOrderIds: 根据维保工单ID集合删除关联的设备
	 * @param workOrderIds 维保工单ID集合
	 * @return Boolean 删除是否成功
	 */
	Boolean deleteWorkOrderAssetByWorkOrderIds(List<String> workOrderIds);

	/**
	 * findWorkOrderAssetListByWorkOrderIdAndAssetIds: 根据工单ID和设备ID查询工单设备表
	 * @param workOrderId 工单ID
	 * @param assets 设备ID集合
	 * @return PageInfo<MaintenanceWorkOrderAsset> 返回工单设备表集合
	 */
	public List<MaintenanceWorkOrderAsset> findWorkOrderAssetListByWorkOrderIdAndAssetIds(String workOrderId,List<String> assets);

	/**
	 * deleteWorkOrderAssetByAssetIds: 删除维保工单关联的设备(多个)
	 * @param workOrderId 维保工单ID
	 * @param assetIds  设备ID集合
	 * @return Boolean 删除是否成功
	 */
	Boolean deleteWorkOrderAssetByAssetIds(String workOrderId, List<String> assetIds);
}
