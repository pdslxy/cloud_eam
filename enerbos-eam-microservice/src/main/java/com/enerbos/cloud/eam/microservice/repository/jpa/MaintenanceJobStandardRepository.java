package com.enerbos.cloud.eam.microservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandard;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description 作业标准repository
 */
public interface MaintenanceJobStandardRepository extends JpaRepository<MaintenanceJobStandard, String>, JpaSpecificationExecutor<MaintenanceJobStandard> {

}
