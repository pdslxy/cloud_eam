package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrderRfCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-08-21 16:58
 * @Description
 */
@Repository
public interface DispatchWorkOrderRfCollectorRepository extends JpaRepository<DispatchWorkOrderRfCollector, String> {
}
