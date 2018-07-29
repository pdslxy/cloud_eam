package com.enerbos.cloud.eam.microservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeter;

public interface DaliyCopyMeterRepository extends
		JpaRepository<DaliyCopyMeter, String> {

	
}
