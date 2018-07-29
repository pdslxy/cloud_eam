package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.SafeIP;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface SafeIPRepository extends CrudRepository<SafeIP, String>, JpaSpecificationExecutor<SafeIP> {


}
