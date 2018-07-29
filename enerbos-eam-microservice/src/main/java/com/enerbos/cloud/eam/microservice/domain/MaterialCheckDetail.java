package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点明细实体类
 */
@Entity
@Table(name = "eam_material_check_detail")
public class MaterialCheckDetail extends EnerbosBaseEntity implements
        Serializable {

    /**
     * 当前余量
     */
    @Column(name = "current_allowance", nullable = true, length = 10)
    private String currentAllowance;

    /**
     * 实际余量
     */
    @Column(name = "physical_inventory", nullable = true, length = 10)
    private String physicalInventory;

    /**
     * 库存id
     */
    @Column(name = "inventory_id", nullable = true, length = 36)
    private String inventoryid;

    @Column(name = "check_id", nullable = true, length = 36)
    private String checkId;

    /**
     * 所属组织id
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;

    /**
     * 所属站点id
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;


    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCurrentAllowance() {
        return currentAllowance;
    }

    public void setCurrentAllowance(String currentAllowance) {
        this.currentAllowance = currentAllowance;
    }

    public String getPhysicalInventory() {
        return physicalInventory;
    }

    public void setPhysicalInventory(String physicalInventory) {
        this.physicalInventory = physicalInventory;
    }

    public String getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(String inventoryid) {
        this.inventoryid = inventoryid;
    }

    @Override
    public String toString() {
        return "MaterialCheckDetail{" +
                "currentAllowance='" + currentAllowance + '\'' +
                ", physicalInventory='" + physicalInventory + '\'' +
                ", inventoryid='" + inventoryid + '\'' +
                ", checkId='" + checkId + '\'' +
                '}';
    }
}
