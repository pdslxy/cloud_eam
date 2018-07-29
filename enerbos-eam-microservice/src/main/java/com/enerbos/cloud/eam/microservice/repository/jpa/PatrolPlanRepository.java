package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.PatrolPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company   北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0.0
 * @date 2017年07月12日
 * @Description
 */

@Repository
public interface PatrolPlanRepository extends JpaRepository<PatrolPlan, String>, JpaSpecificationExecutor<PatrolPlan> {

}