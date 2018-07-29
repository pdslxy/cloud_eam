package com.enerbos.cloud.eam.microservice.service;

import java.util.List;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardTask;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardTaskVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 作业标准关联的任务接口
 */
public interface MaintenanceJobStandardTaskService {
	/**
	 * saveJobStandardTask: 保存作业标准关联的任务
	 * @param maintenanceJobStandardTask 作业标准关联的任务实体
	 * @return List<MaintenanceJobStandardTask> 作业标准关联的任务实体集合
	 */
	public List<MaintenanceJobStandardTask> saveJobStandardTaskList(List<MaintenanceJobStandardTask> maintenanceJobStandardTaskList);

	/**
	 * findJobStandardTaskById: 根据id查询作业标准关联的任务
	 * @param id 
	 * @return MaintenanceJobStandardTask 作业标准关联的任务实体
	 */
	public MaintenanceJobStandardTask findJobStandardTaskById(String id);

	/**
	 * deleteJobStandardTaskById: 删除作业标准关联的任务(单个)
	 * @param id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteJobStandardTaskById(String id);

	/**
	 * deleteJobStandardTaskById: 删除作业标准关联的任务(多个)
	 * @param ids
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteJobStandardTaskByIds(List<String> ids);


	/**
	 * deleteJobStandardTaskByJobStandardIds: 根据作业标准ID集合删除关联的任务
	 * @param jobStandardIds 作业标准ID集合
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteJobStandardTaskByJobStandardIds(List<String> jobStandardIds);

	/**
	 * findJobStandardTaskByJobStandardId: 根据作业标准ID查询任务
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardTaskVo> 返回与作业标准关联的任务集合
	 */
	public List<MaintenanceJobStandardTaskVo> findJobStandardTaskByJobStandardId(String jobStandardId);
}
