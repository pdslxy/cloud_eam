package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.RepairOrder;
import com.enerbos.cloud.eam.vo.OrderCountBySiteVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 11:05
 * @Description
 */
@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, String> {
    /**
     * 查询报修工单各情况的统计
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate 结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @param siteId 站点id
     * @return
     */
    @Query(value="SELECT project_type," +
            "SUM(CASE WHEN work_order_status IN ('DYS', 'GB','YSDQR') THEN 1 ELSE 0 END) AS yxf," +
            "SUM(CASE WHEN work_order_status IN ('GQ','DHB','DFP','DJD','SQGQ') THEN 1 ELSE 0 END) AS wxf " +
            "FROM eam_repair_order  " +
            "WHERE report_date> :stime AND report_date< :etime AND site_id = :sid " +
            "GROUP BY project_type"
            ,nativeQuery=true)
    List<Object> findCountRepair(@Param("stime")String startDate, @Param("etime")String endDate, @Param("sid")String siteId);

    /**
     * 查询未修复报修工单各情况的统计（柱状图）
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate 结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @param siteId 站点id
     * @return
     */
    @Query(value="SELECT project_type," +
            "SUM(CASE WHEN work_order_status IN ('GQ','DHB','DFP','DJD','SQGQ') THEN 1 ELSE 0 END) AS wxf," +
            "SUM(CASE WHEN work_order_status IN ('GQ','SQGQ') AND `suspension_type`='GYSWX' THEN 1 ELSE 0 END) AS gys," +
            "SUM(CASE WHEN work_order_status IN ('GQ','SQGQ') AND `suspension_type`='QSBJ' THEN 1 ELSE 0 END) AS qbj, " +
            "SUM(CASE WHEN work_order_status IN ('GQ','SQGQ') AND `suspension_type`='OTHER' THEN 1 ELSE 0 END) AS qt " +
            "FROM eam_repair_order  " +
            "WHERE report_date> :stime AND report_date< :etime AND site_id = :sid " +
            "GROUP BY project_type"
            ,nativeQuery=true)
    List<Object> findUndoneCountRepair(@Param("stime")String startDate, @Param("etime")String endDate,@Param("sid")String siteId);
    
    
}
