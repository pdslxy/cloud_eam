package com.enerbos.cloud.eam.microservice.service;

import java.util.List;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderNeedItem;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderNeedItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单子表（所需物料）接口
 */
public interface MaintenanceWorkOrderNeedItemService {
	
	/**
	 * saveEamNeedItem: 创建工单子表（所需物料）
	 * @param maintenanceWorkOrderNeedItem 工单子表（所需物料）实体
	 * @return MaintenanceWorkOrderNeedItem 工单子表（所需物料）实体
	 */
	public MaintenanceWorkOrderNeedItem saveEamNeedItem(MaintenanceWorkOrderNeedItem maintenanceWorkOrderNeedItem);
	
	/**
	 * saveEamNeedItemList: 创建工单子表（所需物料）
	 * @param eamNeedItemList 工单子表（所需物料）实体集合
	 * @return List<MaintenanceWorkOrderNeedItem> 工单子表（所需物料）实体集合
	 */
	public List<MaintenanceWorkOrderNeedItem> saveEamNeedItemList(List<MaintenanceWorkOrderNeedItem> eamNeedItemList);

	/**
	 * findEamNeedItemById: 根据工单子表（所需物料）id查询工单子表（所需物料）
	 * @param id 工单子表（所需物料）id
	 * @return MaintenanceWorkOrderNeedItemVo 工单子表（所需物料）实体
	 */
	public MaintenanceWorkOrderNeedItemVo findEamNeedItemById(String id);

	/**
	 * deleteEamNeedItemById: 删除工单子表（所需物料）(单个)
	 * @param id 工单子表（所需物料）id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteEamNeedItemById(String id);

	/**
	 * deleteEamNeedItemById: 删除工单子表（所需物料）(多个)
	 * @param ids 工单子表（所需物料）id(数组)
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteEamNeedItemByIds(List<String> ids);

	/**
	 * findEamNeedItem: 查询工单子表（所需物料）
	 * @param eamNeedItemSelectVo 查询条件
	 * @return PageInfo<MaintenanceWorkOrderNeedItemVo> 工单子表（所需物料）集合
	 */
	public List<MaintenanceWorkOrderNeedItemVo> findEamNeedItemAllByWorkOrderId(String workOrderId);
	
	/**
	 * findEamNeedItem: 查询工单子表（所需物料）
	 * @param eamNeedItemSelectVo 查询条件
	 * @return List<MaintenanceWorkOrderNeedItemVo> 工单子表（所需物料）集合
	 */
	public List<MaintenanceWorkOrderNeedItemVo> findNeedItemListByWorkOrderIdAndItemIds(String workOrderId,List<String> itemIds);
	
	/**
	 * validationOrderNeedItemByWorkOrderId: 分页工单子表（所需物料）
	 * @param workOrderId 工单ID
	 * @return boolean 预防工单实际物料集合
	 */
	public boolean validationOrderNeedItemByWorkOrderId(String workOrderId);
}
