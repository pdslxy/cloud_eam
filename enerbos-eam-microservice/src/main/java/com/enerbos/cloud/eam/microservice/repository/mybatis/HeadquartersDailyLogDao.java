package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersDailyLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HeadquartersDailyLogDao {
    int deleteByPrimaryKey(String id);

    int insert(HeadquartersDailyLog record);

    int insertSelective(HeadquartersDailyLog record);

    HeadquartersDailyLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HeadquartersDailyLog record);

    int updateByPrimaryKeyWithBLOBs(HeadquartersDailyLog record);

    int updateByPrimaryKey(HeadquartersDailyLog record);
}