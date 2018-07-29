package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MaterialCheckDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialCheckDetailRepository extends JpaRepository<MaterialCheckDetail, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE  FROM eam_material_check_detail WHERE check_id =?1", nativeQuery = true)
    void deleteDetailBycheckId(String id);
}
