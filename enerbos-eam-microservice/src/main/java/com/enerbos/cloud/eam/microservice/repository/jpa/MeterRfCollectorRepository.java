package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MeterRfCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/15.
 */

public interface MeterRfCollectorRepository extends JpaRepository<MeterRfCollector, String>, JpaSpecificationExecutor<MeterRfCollector> {


    void deleteByAssetIdAndProductAndPersonId(String AssetId, String product, String personId);

    MeterRfCollector findByAssetIdAndProduct(String assetId, String product);
    
    List<MeterRfCollector> findByPersonId(String personId);

}
