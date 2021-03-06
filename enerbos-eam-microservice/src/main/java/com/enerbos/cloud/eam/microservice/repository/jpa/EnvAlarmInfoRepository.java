package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.EnvAlarmInfo;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 刘广路
 * @version 1.0.0
 * @date 2017/11/20 11:24
 * @Description  环境监测报警信息
 */
public interface EnvAlarmInfoRepository extends JpaRepository<EnvAlarmInfo, String> {

}