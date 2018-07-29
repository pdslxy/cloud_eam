package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderNeedItem;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceWorkOrderNeedItemRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceWorkOrderNeedItemDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderNeedItemService;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderNeedItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单子表（所需物料）
 */
@Service
public class MaintenanceWorkOrderNeedItemServiceImpl implements MaintenanceWorkOrderNeedItemService {

	

	@Autowired
	private MaintenanceWorkOrderNeedItemRepository maintenanceWorkOrderNeedItemRepository;
	
	@Autowired
	private MaintenanceWorkOrderNeedItemDao maintenanceWorkOrderNeedItemDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public MaintenanceWorkOrderNeedItem saveEamNeedItem(MaintenanceWorkOrderNeedItem maintenanceWorkOrderNeedItem) {
		return maintenanceWorkOrderNeedItemRepository.save(maintenanceWorkOrderNeedItem);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<MaintenanceWorkOrderNeedItem> saveEamNeedItemList(List<MaintenanceWorkOrderNeedItem> eamNeedItemList) {
		return maintenanceWorkOrderNeedItemRepository.save(eamNeedItemList);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MaintenanceWorkOrderNeedItemVo findEamNeedItemById(String id) {
		return maintenanceWorkOrderNeedItemDao.findEamNeedItemById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteEamNeedItemById(String id) {
		maintenanceWorkOrderNeedItemRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteEamNeedItemByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceWorkOrderNeedItemRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean validationOrderNeedItemByWorkOrderId(String workOrderId) {
		List<MaintenanceWorkOrderNeedItemVo> list = maintenanceWorkOrderNeedItemDao.findNeedItemByWorkOrderId(workOrderId);
        for (MaintenanceWorkOrderNeedItemVo n : list) {
            if (null == n.getStoreRoomId()||null==n.getItemQty()||"".equals(n.getItemQty())) {
                return false;
            }
        }
		return false;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceWorkOrderNeedItemVo> findNeedItemListByWorkOrderIdAndItemIds(String workOrderId, List<String> itemIds) {
		Map<String, Object> params=new HashMap<>();
		params.put("workOrderId", workOrderId);
		params.put("itemIds", itemIds);
		return maintenanceWorkOrderNeedItemDao.findNeedItemListByWorkOrderIdAndItemIds(params);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceWorkOrderNeedItemVo> findEamNeedItemAllByWorkOrderId(String workOrderId) {
		return maintenanceWorkOrderNeedItemDao.findNeedItemByWorkOrderId(workOrderId);
	}
}
