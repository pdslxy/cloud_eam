package com.enerbos.cloud.eam.microservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderStep;

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
public interface MaintenanceWorkOrderStepRepository extends JpaRepository<MaintenanceWorkOrderStep, String> {

}