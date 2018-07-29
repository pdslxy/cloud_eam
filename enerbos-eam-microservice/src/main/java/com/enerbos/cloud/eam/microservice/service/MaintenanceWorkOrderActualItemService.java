package com.enerbos.cloud.eam.microservice.service;

import java.util.List;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderActualItem;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单实际物料接口
 */
public interface MaintenanceWorkOrderActualItemService {
	/**
	 * saveEamActualitem: 创建工单实际物料
	 * @param List<MaintenanceWorkOrderActualItem> 工单实际物料实体集合
	 * @return List<MaintenanceWorkOrderActualItem> 工单实际物料实体集合
	 */
	public List<MaintenanceWorkOrderActualItem> saveEamActualItemList(List<MaintenanceWorkOrderActualItem> eamActualitemList);

	/**
	 * findEamActualitemById: 根据工单实际物料id查询工单实际物料
	 * @param id 工单实际物料id
	 * @return MaintenanceWorkOrderActualItem 工单实际物料实体
	 */
	public MaintenanceWorkOrderActualItem findEamActualItemById(String id);
	
	/**
	 * findEamActualItemByWorkOrderId: 根据工单实际物料id查询工单实际物料
	 * @param workOrderId 工单id
	 * @return List<MaintenanceWorkOrderActualItem> 工单实际物料实体
	 */
	public List<MaintenanceWorkOrderActualItem> findEamActualItemByWorkOrderId(String workOrderId);

	/**
	 * deleteEamActualitemById: 删除工单实际物料(单个)
	 * @param id 工单实际物料id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteEamActualItemById(String id);

	/**
	 * deleteEamActualitemByIds: 删除工单实际物料(多个)
	 * @param ids 工单实际物料id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteEamActualItemByIds(List<String> ids);

	/**
	 * findActualitemByWorkOrderId: 根据工单ID查询工单实际物料
	 * @param assetId 设备ID
	 * @return List<String> 返回工单物料ID集合
	 */
	List<String> findItemIdByAssetId(String assetId);
}
