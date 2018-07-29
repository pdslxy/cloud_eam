package com.enerbos.cloud.eam.microservice.service;

import java.util.List;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderStep;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderStepVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单子表（任务步骤）接口
 */
public interface MaintenanceWorkOrderStepService {

	/**
	 * saveEamOrderstep: 编辑工单子表（任务步骤）
	 * @param maintenanceWorkOrderStep 工单子表（任务步骤）实体
	 * @return MaintenanceWorkOrderStep 工单子表（任务步骤）实体
	 */
	public MaintenanceWorkOrderStep saveEamOrderstep(MaintenanceWorkOrderStep maintenanceWorkOrderStep);

	/**
	 * findEamOrderstepById: 根据工单子表（任务步骤）id查询工单子表（任务步骤）
	 * @param id 工单子表（任务步骤）id
	 * @return MaintenanceWorkOrderStepVo 工单子表（任务步骤）实体
	 */
	public MaintenanceWorkOrderStepVo findEamOrderstepById(String id);
	
	/**
	 * findAllOrderStep: 查询所有工单子表（任务步骤）
	 * @param workOrderId 工单id
	 * @return List<MaintenanceWorkOrderStep> 工单子表（任务步骤）集合
	 */
	public List<MaintenanceWorkOrderStep> findOrderStepByWorkOrderId(String workOrderId);

	/**
	 * deleteEamOrderstepById: 删除工单子表（任务步骤）(单个)
	 * @param id 工单子表（任务步骤）id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteEamOrderstepById(String id);

	/**
	 * deleteEamOrderstepById: 删除工单子表（任务步骤）(多个)
	 * @param ids 工单子表（任务步骤）id(数组)
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteEamOrderstepByIds(List<String> ids);

	/**
	 * implementAllOrder: 执行工单子表（任务步骤）
	 * @param workorderid 工单ID
	 * @return
	 */
	void implementAllOrder(String workorderid);
}
