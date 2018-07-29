package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.RepairOrderRfCollector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 11:09
 * @Description 工单关联收藏人员
 */
@Mapper
public interface RepairOrderRfCollectorDao {

    /**
     * 根据用户编号查询关注的工单列表
     * @param personId  用户编号
     * @return 用户关注的工单列表
     */
    List<RepairOrderRfCollector> findWorkOrderRfCollectorByPersonId(String personId);

    /**
     * 检查是否已关注
     * @param workOrderId 工单编号
     * @param personId        人员编号
     * @param product         产品编号
     * @return Integer 大于0：已关注  0：未关注
     */
    Integer checkIsCollected(@Param("workOrderId") String workOrderId, @Param("personId") String personId, @Param("product") String product);
}
