package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersDailyTask;
import com.enerbos.cloud.eam.vo.HeadquartersDailyTaskVo;
import com.enerbos.cloud.eam.vo.HeadquartersPlanVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HeadquartersDailyTaskDao {
    int deleteByPrimaryKey(String id);

    int insert(HeadquartersDailyTask record);

    int insertSelective(HeadquartersDailyTask record);

    HeadquartersDailyTaskVo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HeadquartersDailyTask record);

    int updateByPrimaryKeyWithBLOBs(HeadquartersDailyTask record);

    int updateByPrimaryKey(HeadquartersDailyTask record);

    List<HeadquartersDailyTaskVo> findListByFilter(Map<String, Object> filter);


}