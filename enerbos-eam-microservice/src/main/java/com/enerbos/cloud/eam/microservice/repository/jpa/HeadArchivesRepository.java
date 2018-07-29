package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.HeadArchives;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface HeadArchivesRepository extends CrudRepository<HeadArchives, String>, JpaSpecificationExecutor<HeadArchives> {
    public HeadArchives getById(String id);


}
