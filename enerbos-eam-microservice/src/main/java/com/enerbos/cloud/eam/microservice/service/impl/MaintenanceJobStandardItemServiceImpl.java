package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardItem;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceJobStandardItemRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceJobStandardItemDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardItemService;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 作业标准关联的物料
 */
@Service
public class MaintenanceJobStandardItemServiceImpl implements MaintenanceJobStandardItemService {

	@Autowired
	private MaintenanceJobStandardItemRepository maintenanceJobStandardItemRepository;
	
	@Autowired
	private MaintenanceJobStandardItemDao maintenanceJobStandardItemDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceJobStandardItem> saveJobStandardItem(List<MaintenanceJobStandardItem> maintenanceJobStandardItems) {
		return maintenanceJobStandardItemRepository.save(maintenanceJobStandardItems);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MaintenanceJobStandardItem findJobStandardItemById(String id) {
		return maintenanceJobStandardItemRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteJobStandardItemById(String id) {
		maintenanceJobStandardItemRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteJobStandardItemByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceJobStandardItemRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteJobStandardItemByJobStandardIds(List<String> jobStandardIds) {
		List<String> ids=maintenanceJobStandardItemDao.findJobStandardItemByJobStandardIds(jobStandardIds);
		if (ids==null||ids.size()<1||ids.isEmpty()){
			return true;
		}
		return deleteJobStandardItemByIds(ids);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceJobStandardItemVo> findJobStandardItemByJobStandardId(String jobStandardId){
		return maintenanceJobStandardItemDao.findJobStandardItemByJobStandardId(jobStandardId);
	}
}
