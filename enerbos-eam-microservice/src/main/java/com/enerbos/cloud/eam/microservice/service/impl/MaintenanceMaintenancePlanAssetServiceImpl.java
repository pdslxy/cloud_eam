package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanAssetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanAsset;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceMaintenancePlanAssetRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceMaintenancePlanAssetDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceMaintenancePlanAssetService;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 与标准作业计划关联的设备
 */
@Service
public class MaintenanceMaintenancePlanAssetServiceImpl implements MaintenanceMaintenancePlanAssetService {

	@Autowired
	private MaintenanceMaintenancePlanAssetRepository maintenanceMaintenancePlanAssetRepository;
	
	@Autowired
	private MaintenanceMaintenancePlanAssetDao maintenanceMaintenancePlanAssetDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceMaintenancePlanAsset> saveMaintenancePlanAsset(List<MaintenanceMaintenancePlanAsset> maintenancePlanAssetList) {
		return maintenanceMaintenancePlanAssetRepository.save(maintenancePlanAssetList);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteMaintenancePlanAssetByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceMaintenancePlanAssetRepository.delete(id);
		}
		return true;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteMaintenancePlanAssetByMaintenancePlanIds(List<String> maintenancePlanIds) {
		List<String> ids=maintenanceMaintenancePlanAssetDao.findAssetListByMaintenancePlanIds(maintenancePlanIds,null);
		if (ids==null||ids.size()<1||ids.isEmpty()){
			return true;
		}
		return deleteMaintenancePlanAssetByIds(ids);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findMaintenancePlanAssetListByMaintenancePlanId(String maintenancePlanId){
		List<String> assets=new ArrayList<>();
		List<MaintenanceMaintenancePlanAssetVo> maintenancePlanAssetList=maintenanceMaintenancePlanAssetDao.findAssetListByMaintenancePlanId(maintenancePlanId);
		maintenancePlanAssetList.forEach(maintenancePlanAsset->assets.add(maintenancePlanAsset.getAssetId()));
		return assets;
	}

	@Override
	public Boolean deleteMaintenancePlanAssetByAssetIds(String maintenancePlanId, List<String> assetIds) {
		List<String> maintenancePlanIds=Collections.singletonList(maintenancePlanId);
		List<String> ids=maintenanceMaintenancePlanAssetDao.findAssetListByMaintenancePlanIds(maintenancePlanIds,assetIds);
		return deleteMaintenancePlanAssetByIds(ids);
	}
}
