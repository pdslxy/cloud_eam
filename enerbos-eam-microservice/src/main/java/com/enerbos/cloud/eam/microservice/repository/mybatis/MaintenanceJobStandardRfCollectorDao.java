package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardRfCollector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月21日
 * @Description 作业标准-收藏
 */
@Mapper
public interface MaintenanceJobStandardRfCollectorDao {

    /**
     * 根据用户编号查询关注的作业标准列表
     * @param personId  用户编号
     * @return 用户关注的作业标准列表
     */
    List<MaintenanceJobStandardRfCollector> findJobStandardRfCollectorByPersonId(String personId);

    /**
     * 检查是否已关注
     * @param jobStandardId 作业标准编号
     * @param personId        人员编号
     * @param product         产品编号
     * @return Integer 大于0：已关注  0：未关注
     */
    Integer checkIsCollected(@Param("jobStandardId") String jobStandardId, @Param("personId") String personId, @Param("product") String product);
}
