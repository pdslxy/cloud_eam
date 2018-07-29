package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersDaily;
import org.apache.ibatis.annotations.Mapper;
import com.enerbos.cloud.eam.vo.HeadquartersDailyVo;
import com.enerbos.cloud.eam.vo.HeadquartersDailyVoForUpStatus;
import com.enerbos.cloud.eam.vo.HeadquartersPlanVo;
import com.enerbos.cloud.eam.vo.HeadquartersPlanVoForUpStatus;

import java.util.List;
import java.util.Map;

@Mapper
public interface HeadquartersDailyDao {
    int deleteByPrimaryKey(String id);

    int insert(HeadquartersDaily record);

    int insertSelective(HeadquartersDaily record);

    HeadquartersDailyVo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HeadquartersDaily record);

    int updateByPrimaryKeyWithBLOBs(HeadquartersDaily record);

    int updateByPrimaryKey(HeadquartersDaily record);

    List<HeadquartersDailyVo> findListByFilter(Map<String, Object> filter);

    List<HeadquartersDailyVo> findListByIds(String[] ids);

    List<HeadquartersDailyVo> findAll();

    void upStatusByIds(HeadquartersDailyVoForUpStatus headquartersDailyVoForUpStatus);
}