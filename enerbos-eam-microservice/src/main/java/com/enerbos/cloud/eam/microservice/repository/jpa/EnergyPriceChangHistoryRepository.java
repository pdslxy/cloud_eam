package com.enerbos.cloud.eam.microservice.repository.jpa;


import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrder;
import com.enerbos.cloud.eam.microservice.domain.EnergyPriceChangHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/12/1 13:30
 * @Description: 能源价格变更记录
 */
@Repository
public interface EnergyPriceChangHistoryRepository extends JpaRepository<EnergyPriceChangHistory, String> {
}

