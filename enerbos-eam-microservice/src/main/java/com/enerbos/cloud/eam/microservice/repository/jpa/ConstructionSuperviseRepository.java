package com.enerbos.cloud.eam.microservice.repository.jpa;


import com.enerbos.cloud.eam.microservice.domain.Construction;
import com.enerbos.cloud.eam.microservice.domain.ConstructionSupervise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 施工单-监管说明
 */
public interface ConstructionSuperviseRepository extends JpaRepository<ConstructionSupervise, String> {

}
