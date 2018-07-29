package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/13
 * @Description
 */
@Mapper
public interface PatrolPlanFrequencyDao {
    List<PatrolPlanFrequencyVo> findPatrolPlanFrequencyListByFilters(Map<String, Object> filters);

    void deletePatrolPlanFrequencyByPointId(String patrolpointid);
}
