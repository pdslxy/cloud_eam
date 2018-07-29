package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enerbos.cloud.eam.vo.MaintenanceJobStandardTaskVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 与标准作业计划关联的任务接口
 */
@Mapper
public interface MaintenanceJobStandardTaskDao {

	/**
	 * findJobStandardTaskByJobStandardId: 根据作业标准ID查询任务
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardTaskVo> 返回与作业标准关联的任务集合
	 */
	List<MaintenanceJobStandardTaskVo> findJobStandardTaskByJobStandardId(@Param("jobStandardId") String jobStandardId);

	/**
	 * findJobStandardTaskByJobStandardIds: 根据作业标准ID查询任务id
	 * @param jobStandardIds 作业标准ID List
	 * @return List<String> 返回作业标准关联的任务id
	 */
	List<String> findJobStandardTaskByJobStandardIds(@Param("jobStandardIds") List<String> jobStandardIds);
}
