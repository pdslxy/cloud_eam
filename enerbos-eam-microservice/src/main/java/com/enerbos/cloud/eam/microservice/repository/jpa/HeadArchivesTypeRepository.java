package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Enerbos on 2016/10/19.
 */
@Repository
public interface HeadArchivesTypeRepository extends CrudRepository<HeadArchivesType, String>, JpaSpecificationExecutor<HeadArchivesType> {
    public HeadArchivesType getById(String id);


}
