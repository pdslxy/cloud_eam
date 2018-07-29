package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description 维保工单repository
 */
public interface MaintenanceWorkOrderRepository extends JpaRepository<MaintenanceWorkOrder, String>, JpaSpecificationExecutor<MaintenanceWorkOrder> {
    /**
     * 查询维保工单各情况的统计
     * @param startDate
     * @param endDate
     * @param siteId
     * @return
     */
    @Query(value="SELECT project_type,COUNT(*) total," +
            "SUM(CASE WHEN `status` IN ('DYS', 'GB','YSDQR','QX') THEN 1 ELSE 0 END) AS yxf," +
            "SUM(CASE WHEN `status` IN ('GQ','DHB','DFP','DJD','SQGQ','DTB') THEN 1 ELSE 0 END) AS wxf " +
            "FROM eam_work_order  " +
            "WHERE report_date> :stime AND report_date< :etime AND site_id = :sid " +
            "GROUP BY project_type"
            ,nativeQuery=true)
    List<Object>  findCountWorkOrder(@Param("stime")String startDate, @Param("etime")String endDate, @Param("sid")String siteId);

    /**
     * 查询未完成维保工单各情况的统计
     * @param startDate
     * @param endDate
     * @param siteId
     * @return
     */
    @Query(value="SELECT project_type," +
            "SUM(CASE WHEN `status` IN ('GQ','DHB','DFP','DJD','SQGQ') THEN 1 ELSE 0 END) AS wxf," +
            "SUM(CASE WHEN `status` IN ('GQ','SQGQ') AND `suspension_type`='GYSWX' THEN 1 ELSE 0 END) AS gys," +
            "SUM(CASE WHEN `status` IN ('GQ','SQGQ') AND `suspension_type`='QSBJ' THEN 1 ELSE 0 END) AS qbj, " +
            "SUM(CASE WHEN `status` IN ('GQ','SQGQ') AND `suspension_type`='OTHER' THEN 1 ELSE 0 END) AS qt " +
            "FROM eam_work_order  " +
            "WHERE report_date> :stime AND report_date< :etime AND site_id = :sid " +
            "GROUP BY project_type"
            ,nativeQuery=true)
    List<Object> findCountUndoneWorkOrder(@Param("stime")String startDate, @Param("etime")String endDate,@Param("sid")String siteId);

}
