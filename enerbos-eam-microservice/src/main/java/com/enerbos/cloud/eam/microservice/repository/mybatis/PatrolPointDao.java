package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.PatrolPointVo;
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
 * @date 2017/7/13
 * @Description
 */
@Mapper
public interface PatrolPointDao {
    List<PatrolPointVo> findPatrolPointListByFilters(Map<String, Object> filters);

    List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(@Param("ids") String[] ids, @Param("qrcodenum") String qrCodeNum, @Param("siteid") String siteId);

    void updateIsupdatedata(String id, String siteId, boolean b);

    /**
     * 根据工单ID，查询点位
     * @param orderId 工单ID
     * @return
     */
    List<PatrolPointVo> findPatrolPointListByOrderId(String orderId);
}
