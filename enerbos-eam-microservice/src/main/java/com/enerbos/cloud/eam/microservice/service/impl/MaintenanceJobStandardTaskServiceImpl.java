package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardTask;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceJobStandardTaskRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceJobStandardTaskDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardTaskService;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardTaskVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 与标准作业计划关联的任务
 */
@Service
public class MaintenanceJobStandardTaskServiceImpl implements MaintenanceJobStandardTaskService {

	@Autowired
	private MaintenanceJobStandardTaskRepository maintenanceJobStandardTaskRepository;
	
	@Autowired
	private MaintenanceJobStandardTaskDao maintenanceJobStandardTaskDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceJobStandardTask> saveJobStandardTaskList(List<MaintenanceJobStandardTask> maintenanceJobStandardTaskList) {
		return maintenanceJobStandardTaskRepository.save(maintenanceJobStandardTaskList);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MaintenanceJobStandardTask findJobStandardTaskById(String id) {
		return maintenanceJobStandardTaskRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteJobStandardTaskById(String id) {
		maintenanceJobStandardTaskRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteJobStandardTaskByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceJobStandardTaskRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteJobStandardTaskByJobStandardIds(List<String> jobStandardIds) {
		List<String> ids=maintenanceJobStandardTaskDao.findJobStandardTaskByJobStandardIds(jobStandardIds);
		if (ids==null||ids.size()<1||ids.isEmpty()){
			return true;
		}
		return deleteJobStandardTaskByIds(ids);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceJobStandardTaskVo> findJobStandardTaskByJobStandardId(String jobStandardId) {
		return maintenanceJobStandardTaskDao.findJobStandardTaskByJobStandardId(jobStandardId);
	}
}
