package com.enerbos.cloud.eam.microservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderStep;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceWorkOrderStepRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceWorkOrderStepDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderStepService;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderStepVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单子表（任务步骤）
 */
@Service
public class MaintenanceWorkOrderStepServiceImpl implements MaintenanceWorkOrderStepService {

	@Autowired
	private MaintenanceWorkOrderStepRepository maintenanceWorkOrderStepRepository;
	@Autowired
	private MaintenanceWorkOrderStepDao maintenanceWorkOrderStepDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public MaintenanceWorkOrderStep saveEamOrderstep(MaintenanceWorkOrderStep maintenanceWorkOrderStep) {
		return maintenanceWorkOrderStepRepository.save(maintenanceWorkOrderStep);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MaintenanceWorkOrderStepVo findEamOrderstepById(String id) {
		return maintenanceWorkOrderStepDao.findEamOrderStepById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteEamOrderstepById(String id) {
		maintenanceWorkOrderStepRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteEamOrderstepByIds(List<String> ids) {
		for (String id : ids) {
			maintenanceWorkOrderStepRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void implementAllOrder(String workOrderId) {
		List<MaintenanceWorkOrderStep> ordersteps = maintenanceWorkOrderStepDao.findEamOrderStepByWorkOrderId(workOrderId);
        if(ordersteps.size()>0){
            for(int i=0;i<ordersteps.size();i++){
                if(ordersteps.get(i).getExecuteSituation()==null||!ordersteps.get(i).getExecuteSituation()){
                    ordersteps.get(i).setExecuteSituation(true);
                    maintenanceWorkOrderStepRepository.save(ordersteps.get(i));
                }
            }
        }
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceWorkOrderStep> findOrderStepByWorkOrderId(String workOrderId) {
		return maintenanceWorkOrderStepDao.findEamOrderStepByWorkOrderId(workOrderId);
	}
}
