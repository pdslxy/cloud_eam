package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanRequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DaliyCopyMeterPlanRequencyRepository extends
        JpaRepository<DaliyCopyMeterPlanRequency, String> {

    /**
     * 删除详细内容
     *
     * @param copyMeterPlanId
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from eam_daily_copymeter_plan_requency  where  copymeter_plan_id = ?1", nativeQuery = true)
    void deleteByCopyMeterPlanId(String copyMeterPlanId);
}
