package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderRfCollector;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderRfCollectorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanActiveTime;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceMaintenancePlanActiveTimeRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceMaintenancePlanActiveTimeDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceMaintenancePlanActiveTimeService;
import org.springframework.util.ObjectUtils;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 预防性维护计划有效时间service
 */
@Service
public class MaintenanceMaintenancePlanActiveTimeServiceImpl implements MaintenanceMaintenancePlanActiveTimeService {

	@Autowired
	private MaintenanceMaintenancePlanActiveTimeRepository maintenanceMaintenancePlanActiveTimeRepository;
	
	@Autowired
	private MaintenanceMaintenancePlanActiveTimeDao maintenanceMaintenancePlanActiveTimeDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceMaintenancePlanActiveTime> saveMaintenancePlanActiveTimeList(List<MaintenanceMaintenancePlanActiveTime> maintenanceMaintenancePlanActiveTimeList) {
		for (MaintenanceMaintenancePlanActiveTime maintenanceMaintenancePlanActiveTime:maintenanceMaintenancePlanActiveTimeList){
			MaintenanceMaintenancePlanActiveTime maintenanceMaintenancePlanActiveTimeOld=maintenanceMaintenancePlanActiveTimeDao.findMaintenancePlanActiveTime(maintenanceMaintenancePlanActiveTime);
			if (maintenanceMaintenancePlanActiveTimeOld != null) {
				maintenanceMaintenancePlanActiveTime.setId(maintenanceMaintenancePlanActiveTimeOld.getId());
			}
		}
		return maintenanceMaintenancePlanActiveTimeRepository.save(maintenanceMaintenancePlanActiveTimeList);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteMaintenancePlanActiveTimeListByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceMaintenancePlanActiveTimeRepository.delete(id);
		}
		return true;
	}
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteMaintenancePlanActiveTimeByMaintenancePlanIds(List<String> maintenancePlanIds) {
		List<String> ids=maintenanceMaintenancePlanActiveTimeDao.findMaintenancePlanActiveTimeByMaintenancePlanIds(maintenancePlanIds);
		if (ids==null||ids.size()<1||ids.isEmpty()){
			return true;
		}
		return deleteMaintenancePlanActiveTimeListByIds(ids);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceMaintenancePlanActiveTimeVo> findAllMaintenancePlanActiveTime(String maintenancePlanId){
		return maintenanceMaintenancePlanActiveTimeDao.findAllMaintenancePlanActiveTime(maintenancePlanId);
	}
}
