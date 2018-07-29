package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.PatrolRecordVo;
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
 * @date 2017/8/8
 * @Description
 */
@Mapper
public interface PatrolRecordDao {

    List<PatrolRecordVo> findByOrderIdAndPointId(@Param("patrolOrderId") String patrolOrderId, @Param("patrolPointId") String patrolPointId);

    void deleteByOrderId(@Param("patrolOrderId") String patrolOrderId);
}
