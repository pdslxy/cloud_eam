package com.enerbos.cloud.eam.microservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.enerbos.cloud.eam.microservice.domain.PatrolStandContent;

public interface PatrolStandContentRepository extends
		JpaRepository<PatrolStandContent, String>,
		JpaSpecificationExecutor<PatrolStandContent> {

}
