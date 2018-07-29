package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description 作业标准与设备的关联实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_maintenance_plan_asset")
public class MaintenanceMaintenancePlanAsset {
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     *关联的预防性维护主键
     */
    @Column(name = "maintenance_plan_id")
    private String maintenancePlanId;

    /**
     * 关联的资产id
     */
    @Column(name = "asset_id")
    private String assetId;
    
    public MaintenanceMaintenancePlanAsset() {
    }

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaintenancePlanId() {
		return maintenancePlanId;
	}

	public void setMaintenancePlanId(String maintenancePlanId) {
		this.maintenancePlanId = maintenancePlanId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Override
    public String toString() {
        return "MaintenanceMaintenancePlanAsset{" +
                "id='" + id + '\'' +
                ", maintenancePlanId='" + maintenancePlanId + '\'' +
                ", assetId='" + assetId + '\'' +
                '}';
    }
}
