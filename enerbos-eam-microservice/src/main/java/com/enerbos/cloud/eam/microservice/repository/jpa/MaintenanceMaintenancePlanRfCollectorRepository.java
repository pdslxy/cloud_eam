package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlan;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanRfCollector;
import com.enerbos.cloud.eam.microservice.domain.RepairOrderRfCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月21日
 * @Description
 */
@Repository
public interface MaintenanceMaintenancePlanRfCollectorRepository extends JpaRepository<MaintenanceMaintenancePlanRfCollector, String> {
}
