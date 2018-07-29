package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderActualItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
@Mapper
public interface MaintenanceWorkOrderActualItemDao {

	/**
	 * findActualitemByWorkOrderId: 根据工单ID查询工单实际物料
	 * @param workOrderId 工单ID
	 * @return List<MaintenanceWorkOrderActualItem> 返回工单实际物料集合
	 */
	List<MaintenanceWorkOrderActualItem> findActualitemByWorkOrderId(String workOrderId);

	/**
	 * findActualitemByWorkOrderId: 根据工单ID查询工单实际物料
	 * @param assetId 设备ID
	 * @return List<String> 返回工单物料ID集合
	 */
	List<String> findItemIdByAssetId(String assetId);
}
