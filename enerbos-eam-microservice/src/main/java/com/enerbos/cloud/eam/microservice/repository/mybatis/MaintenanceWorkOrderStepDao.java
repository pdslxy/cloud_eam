package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface MaintenanceWorkOrderStepDao {

	/**
	 * findEamOrderStepById: 根据ID查询工单子表（任务步骤）
	 * @param id 
	 * @return MaintenanceWorkOrderStepVo
	 */
	MaintenanceWorkOrderStepVo findEamOrderStepById(@Param("id") String id);
	
	/**
	 * findAllOrderStep: 查询所有工单子表（任务步骤）
	 * @return List<MaintenanceWorkOrderStepVo> 返回工单子表（任务步骤）集合
	 */
	List<MaintenanceWorkOrderStepVo> findAllOrderStep();

	/**
	 * findEamOrderStepByWorkOrderId: 根据工单ID查询工单子表（任务步骤）
	 * @param workorderid 工单ID
	 * @return List<MaintenanceWorkOrderStep> 返回工单子表（任务步骤）集合
	 */
	List<MaintenanceWorkOrderStep> findEamOrderStepByWorkOrderId(@Param("workOrderId") String workOrderId);

}
