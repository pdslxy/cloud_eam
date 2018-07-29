package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.PatrolStand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatrolStandRepository extends JpaRepository<PatrolStand, String>, JpaSpecificationExecutor<PatrolStand> {
}
