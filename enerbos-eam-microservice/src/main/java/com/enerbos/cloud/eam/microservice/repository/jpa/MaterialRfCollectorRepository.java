package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MaterialRfCollectorRepository extends JpaRepository<MaterialRfCollector, String>, JpaSpecificationExecutor<MaterialRfCollector> {


    void deleteByCollectIdAndProductAndTypeAndPersonId(String collectId, String product, String type, String personId);

    MaterialRfCollector findByCollectIdAndTypeAndProduct(String collectId, String type, String product);


}
