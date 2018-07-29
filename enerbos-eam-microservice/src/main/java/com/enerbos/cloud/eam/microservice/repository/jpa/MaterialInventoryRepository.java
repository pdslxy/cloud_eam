package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
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
 * @Description 物资库存接口
 */
public interface MaterialInventoryRepository extends JpaRepository<MaterialInventory, String> {

    /**
     * 物资接收时更新当前余量
     * @param itemId  物资台账id
     * @param quantity 数量
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE eam_material_inventory  SET current_balance = current_balance + ?2, available_balance = available_balance + ?2 WHERE item_id = ?1", nativeQuery = true)
    void updateInventoryCurrentBalanceByItemId(String itemId, long quantity);

    /**
     * 物资发放更新当前余量
     * @param inventoryId 物资库存id
     * @param quantity 数量
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE eam_material_inventory  SET current_balance = current_balance - ?2 , available_balance = available_balance - ?2 WHERE  id = ?1", nativeQuery = true)
    void updateInventoryCurrentBalanceByIntoryId(String inventoryId, long quantity);

    /**
     * 物资发放更新当前余量
     * @param inventoryId 物资库存id
     * @param quantity 数量
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE eam_material_inventory  SET current_balance = ?2 , available_balance = ?2 WHERE  id = ?1", nativeQuery = true)
    void updateInventoryCheckByIntoryId(String inventoryId, long quantity);
}
