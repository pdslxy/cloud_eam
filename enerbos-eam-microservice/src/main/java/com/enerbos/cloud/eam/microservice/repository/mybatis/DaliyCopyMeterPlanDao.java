package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanRequencyVo;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVoForList;
import com.enerbos.cloud.eam.vo.DaliyCopyMeterPlanMeterRelationVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DaliyCopyMeterPlanDao {

    List<DailyCopyMeterPlanVoForList> findCopyMeterPlans(Map<String, Object> filters);

    List<DailyCopyMeterPlanRequencyVo> findCopyMeterPlanRequencyVosById(Map filters);

    List<DaliyCopyMeterPlanMeterRelationVo> findCopyMeterPlanMeterRelationVosById(Map filters);
}
