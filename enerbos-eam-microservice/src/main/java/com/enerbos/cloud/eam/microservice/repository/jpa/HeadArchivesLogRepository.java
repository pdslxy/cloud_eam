package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface HeadArchivesLogRepository extends CrudRepository<HeadArchivesLog, String>, JpaSpecificationExecutor<HeadArchivesLog> {
    public HeadArchivesLog getById(String id);


}
