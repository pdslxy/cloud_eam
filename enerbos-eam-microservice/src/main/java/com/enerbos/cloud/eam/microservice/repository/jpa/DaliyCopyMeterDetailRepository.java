package com.enerbos.cloud.eam.microservice.repository.jpa;


import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DaliyCopyMeterDetailRepository extends JpaRepository<DaliyCopyMeterDetail, String> {

    /**
     * 删除详细内容
     *
     * @param copyMeterId
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from eam_daily_copymeter_detail  where  copymeter_id = ?1", nativeQuery = true)
    void deleteByCopyMeterId(String copyMeterId);

    @Query(value = "select * from eam_daily_copymeter_detail  where  copymeter_id = ?1", nativeQuery = true)
    List<DaliyCopyMeterDetail> findByCopyMeterId(String copyMeterId);
}
