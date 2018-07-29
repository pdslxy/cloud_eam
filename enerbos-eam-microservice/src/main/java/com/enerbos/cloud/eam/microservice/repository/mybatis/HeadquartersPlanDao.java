package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlan;
import com.enerbos.cloud.eam.vo.HeadquartersPlanVo;
import com.enerbos.cloud.eam.vo.HeadquartersPlanVoForFilter;
import com.enerbos.cloud.eam.vo.HeadquartersPlanVoForUpStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HeadquartersPlanDao {
    int deleteByPrimaryKey(List<String> ids);

    int insert(HeadquartersPlan record);

    int insertSelective(HeadquartersPlan record);

    HeadquartersPlanVo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HeadquartersPlan record);

    int updateByPrimaryKeyWithBLOBs(HeadquartersPlan record);

    int updateByPrimaryKey(HeadquartersPlan record);

    List<HeadquartersPlanVo> findListByFilter(Map<String, Object> filter);

    List<HeadquartersPlanVo> findListByIds(@Param("ids") List<String> ids);

    List<HeadquartersPlanVo> findAll(Map<String, Object> filter);

    void upStatusByIds(HeadquartersPlanVoForUpStatus headquartersPlanVoForUpStatus);
}