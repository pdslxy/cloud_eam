package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.DefectDocumentRfCollector;
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
 * @Description 消缺单-收藏
 */
@Mapper
public interface DefectDocumentRfCollectorDao {

    /**
     * 根据用户编号查询关注的消缺单列表
     * @param personId  用户编号
     * @return 用户关注的消缺单列表
     */
    List<DefectDocumentRfCollector> findDefectDocumentRfCollectorByPersonId(String personId);

    /**
     * 检查是否已关注
     * @param defectDocumentId 消缺单编号
     * @param personId        人员编号
     * @param product         产品编号
     * @return Integer 大于0：已关注  0：未关注
     */
    Integer checkIsCollected(@Param("defectDocumentId") String defectDocumentId, @Param("personId") String personId, @Param("product") String product);
}
