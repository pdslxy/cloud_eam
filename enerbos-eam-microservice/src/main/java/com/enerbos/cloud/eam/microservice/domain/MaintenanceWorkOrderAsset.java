package com.enerbos.cloud.eam.microservice.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 维保工单设备关系表实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_work_order_asset")
public class MaintenanceWorkOrderAsset implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(unique = true, nullable = false, length = 36, name = "id")
	private String id;
	/**
	 * 
	 */
	@Column(nullable = true,length = 36)
	private String assetId;
	/**
	 * 
	 */
	@Column(nullable = true,length = 36)
	private String workOrderId;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	@Override
    public String toString() {
        return "MaintenanceWorkOrderAsset{" +
                "id='" + id + '\'' +
                ", assetId='" + assetId + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                '}';
    }
}