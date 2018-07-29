package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.PatrolPointVo;
import com.enerbos.cloud.eam.vo.PatrolRoutePointVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
public interface PatrolRoutePointDao {
    List<PatrolPointVo> findPatrolPointByPatrolrouteid(@Param("patrolrouteid") String patrolrouteid, @Param("sorts") String sorts);

    List<PatrolRoutePointVo> findPatrolRoutePointByPatrolpointid(String patrolpointid);

    void deleteByRouteId(String patrolrouteid);
}
