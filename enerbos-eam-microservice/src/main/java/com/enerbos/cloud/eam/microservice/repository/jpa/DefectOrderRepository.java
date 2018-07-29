package com.enerbos.cloud.eam.microservice.repository.jpa;


import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.microservice.domain.DefectOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 消缺工单
 */
public interface DefectOrderRepository extends JpaRepository<DefectOrder, String>, JpaSpecificationExecutor<DefectOrder> {

}
