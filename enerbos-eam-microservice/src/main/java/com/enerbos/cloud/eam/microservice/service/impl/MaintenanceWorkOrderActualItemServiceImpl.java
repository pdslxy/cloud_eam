package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderActualItem;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceWorkOrderActualItemRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceWorkOrderActualItemDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderActualItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单实际物料
 */
@Service
public class MaintenanceWorkOrderActualItemServiceImpl implements MaintenanceWorkOrderActualItemService {

	@Autowired
	private MaintenanceWorkOrderActualItemRepository maintenanceWorkOrderActualItemRepository;
	@Autowired
	private MaintenanceWorkOrderActualItemDao maintenanceWorkOrderActualItemDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceWorkOrderActualItem> saveEamActualItemList(List<MaintenanceWorkOrderActualItem> eamActualItemList) {
		return maintenanceWorkOrderActualItemRepository.save(eamActualItemList);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MaintenanceWorkOrderActualItem findEamActualItemById(String id) {
		return maintenanceWorkOrderActualItemRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteEamActualItemById(String id) {
		maintenanceWorkOrderActualItemRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteEamActualItemByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceWorkOrderActualItemRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceWorkOrderActualItem> findEamActualItemByWorkOrderId(String workOrderId) {
		return maintenanceWorkOrderActualItemDao.findActualitemByWorkOrderId(workOrderId);
	}

	@Override
	public List<String> findItemIdByAssetId(String assetId) {
		return maintenanceWorkOrderActualItemDao.findItemIdByAssetId(assetId);
	}
}
