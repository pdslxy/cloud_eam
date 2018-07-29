package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/8
 * @Description
 */
@Mapper
public interface PatrolRecordTermDao {
    /**
     * 巡检点对应的执行过的巡检工单
     *
     * @param patrolPointId
     * @return
     */
    List<Map<String,Object>> findExcutePatrolOrderByPoint(@Param("patrolPointId")String patrolPointId,@Param("orderStatus")String orderStatus);

    List<Map<String,Object>> findTermStatusListByOrder(@Param("orderId") String orderId, @Param("pointId") String pointId);

    List<PatrolRecordTermVo> findPatrolTermByOrderAndPoint(@Param("id") String id, @Param("pointid") String pointid);

    void deleteByOrderId(@Param("patrolOrderId") String patrolOrderId);

}
