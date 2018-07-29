package com.enerbos.cloud.eam.microservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlan;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description 预防性维护 repository
 */
public interface MaintenanceMaintenancePlanRepository extends JpaRepository<MaintenanceMaintenancePlan, String>, JpaSpecificationExecutor<MaintenanceMaintenancePlan> {

}
