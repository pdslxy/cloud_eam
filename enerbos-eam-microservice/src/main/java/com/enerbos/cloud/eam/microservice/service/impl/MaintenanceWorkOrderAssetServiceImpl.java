package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderAsset;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceWorkOrderAssetRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceWorkOrderAssetDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderAssetService;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单设备表
 */
@Service
public class MaintenanceWorkOrderAssetServiceImpl implements MaintenanceWorkOrderAssetService {

	@Autowired
	private MaintenanceWorkOrderAssetRepository maintenanceWorkOrderAssetRepository;
	@Autowired
	private MaintenanceWorkOrderAssetDao maintenanceWorkOrderAssetDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceWorkOrderAsset> saveWorkOrderAssetList(List<MaintenanceWorkOrderAsset> eamWoassetList) {
		return maintenanceWorkOrderAssetRepository.save(eamWoassetList);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MaintenanceWorkOrderAsset findWorkOrderAssetById(String id) {
		return maintenanceWorkOrderAssetRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteWorkOrderAssetById(String id) {
		maintenanceWorkOrderAssetRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteWorkOrderAssetByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceWorkOrderAssetRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteWorkOrderAssetByWorkOrderIds(List<String> workOrderIds) {
		List<String> ids=maintenanceWorkOrderAssetDao.findAssetListByWorkOrderIds(workOrderIds,null);
		return deleteWorkOrderAssetByIds(ids);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findAssetListByWorkOrderId(String workOrderId) {
		return maintenanceWorkOrderAssetDao.findAssetListByWorkOrderId(workOrderId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceWorkOrderAsset> findWorkOrderAssetListByWorkOrderIdAndAssetIds(String workOrderId,List<String> assets) {
		Map<String, Object> params=new HashMap<>();
		params.put("workOrderId", workOrderId);
		params.put("assetId", assets);
		return maintenanceWorkOrderAssetDao.findWorkOrderAssetListByWorkOrderIdAndAssetIds(params);
	}

	@Override
	public Boolean deleteWorkOrderAssetByAssetIds(String workOrderId, List<String> assetIds) {
		List<String> workOrderIds= Collections.singletonList(workOrderId);
		List<String> ids=maintenanceWorkOrderAssetDao.findAssetListByWorkOrderIds(workOrderIds,assetIds);
		return deleteWorkOrderAssetByIds(ids);
	}
}
