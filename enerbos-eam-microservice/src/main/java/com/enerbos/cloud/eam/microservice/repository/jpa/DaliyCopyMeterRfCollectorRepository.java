package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.DaliyRfCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DaliyCopyMeterRfCollectorRepository extends JpaRepository<DaliyRfCollector, String>, JpaSpecificationExecutor<DaliyRfCollector> {


    void deleteByCollectIdAndProductAndTypeAndPersonId(String collectId, String product, String type, String personId);

    DaliyRfCollector findByCollectIdAndTypeAndProductAndPersonId(String collectId, String type, String product, String personId);
}
