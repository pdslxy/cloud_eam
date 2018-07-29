package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlanRange;
import com.enerbos.cloud.eam.vo.HeadquartersPlanRangeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HeadquartersPlanRangeDao {
    int deleteByPrimaryKey(String id);

    int insert(HeadquartersPlanRange record);

    int insertSelective(HeadquartersPlanRange record);

    HeadquartersPlanRange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HeadquartersPlanRange record);

    int updateByPrimaryKey(HeadquartersPlanRange record);

    List<String> getSiteListByPlanId(String id);
}