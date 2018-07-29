package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017-07-11 11:07
 * @Description
 */
public interface MaterialGoodsReceiveRepository extends JpaRepository<MaterialGoodsReceive, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE  FROM eam_material_goods_receive_detail WHERE goods_receive_id = ?1", nativeQuery = true)
    void deleteDetailByReciveId(String id);
}
