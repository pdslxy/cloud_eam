package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

 /**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 维保工单-所需物料 实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_work_order_need_item")
public class MaintenanceWorkOrderNeedItem extends EnerbosBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 物料描述
	 */
	@Column(nullable = true,length = 255)
	private String itemDesc;
	
	/**
	 * 物料ID
	 */
	@Column(nullable = true,length = 255)
	private String itemId;
	
	/**
	 * 物料编码
	 */
	@Column(nullable = true,length = 255)
	private String itemNum;
	
	/**
	 * 物料数量
	 */
	@Column(nullable = true,length = 255)
	private String itemQty;
	
	/**
	 * 物料单位
	 */
	@Column(nullable = true,length = 255)
	private String itemUnit;
	
	/**
	 * 库房ID
	 */
	@Column(nullable = true,length = 36)
	private String storeRoomId;
	
	/**
	 * 工单ID
	 */
	@Column(nullable = true,length = 36)
	private String workOrderId;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getStoreRoomId() {
		return storeRoomId;
	}

	public void setStoreRoomId(String storeRoomId) {
		this.storeRoomId = storeRoomId;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	 public static long getSerialVersionUID() {
		 return serialVersionUID;
	 }

	 public String getItemDesc() {
		 return itemDesc;
	 }

	 public void setItemDesc(String itemDesc) {
		 this.itemDesc = itemDesc;
	 }

	 public String getItemUnit() {
		 return itemUnit;
	 }

	 public void setItemUnit(String itemUnit) {
		 this.itemUnit = itemUnit;
	 }

	 @Override
    public String toString() {
        return "MaintenanceWorkOrderNeedItem{" +
                "id='" + getId() + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", itemQty='" + itemQty + '\'' +
				", itemUnit='" + itemUnit + '\'' +
                ", storeRoomId='" + storeRoomId + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", createUser='" + getCreateUser() + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                ", updateDate='" + getUpdateDate() + '\'' +
                '}';
    }

}
