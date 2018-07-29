package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface MaintenanceWorkOrderNeedItemDao {

	/**
	 * findEamNeedItemById: 根据ID查询工单子表（所需物料）
	 * @param id 
	 * @return MaintenanceWorkOrderNeedItemVo
	 */
	MaintenanceWorkOrderNeedItemVo findEamNeedItemById(String id);

	/**
	 * findNeedItemByWorkOrderId: 根据workOrderId查询工单子表（所需物料）
	 * @param workOrderId 工单ID
	 * @return List<MaintenanceWorkOrderNeedItemVo> 返回工单子表（所需物料）集合
	 */
	List<MaintenanceWorkOrderNeedItemVo> findNeedItemByWorkOrderId(@Param("workOrderId") String workOrderId);
	
	/**
	 * findNeedItemListByWorkOrderIdAndItemIds: 根据workOrderId和物品ID查询工单子表（所需物料）
	 * @param params 工单ID
	 * @return List<MaintenanceWorkOrderNeedItemVo> 返回工单子表（所需物料）集合
	 */
	List<MaintenanceWorkOrderNeedItemVo> findNeedItemListByWorkOrderIdAndItemIds(@Param("params") Map<String, Object> params);

}
