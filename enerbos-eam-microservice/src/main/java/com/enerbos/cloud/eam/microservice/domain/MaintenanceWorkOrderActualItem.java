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
 * @Description 维保工单-实际物料 实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_work_order_actual_item")
public class MaintenanceWorkOrderActualItem extends EnerbosBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 物料描述
	 */
	@Column(nullable = true,length = 255)
	private String itemDesc;
	
	/**
	 * 物资发放和退回的标志  1:issue 发放  2:return 退回
	 */
	@Column(nullable = true,length = 255)
	private String flag;

	 /**
	  * 物料编码
	  */
	 @Column(name="item_id",nullable = true,length = 255)
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
	 * 库房
	 */
	@Column(nullable = true,length = 36)
	private String storeRoomId;
	
	/**
	 * 工单ID
	 */
	@Column(nullable = true,length = 36)
	private String workOrderId;

	 public static long getSerialVersionUID() {
		 return serialVersionUID;
	 }

	 public String getItemDesc() {
		 return itemDesc;
	 }

	 public void setItemDesc(String itemDesc) {
		 this.itemDesc = itemDesc;
	 }

	 public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
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

	 public String getItemId() {
		 return itemId;
	 }

	 public void setItemId(String itemId) {
		 this.itemId = itemId;
	 }

	 @Override
    public String toString() {
        return "MaintenanceWorkOrderActualItem{" +
                "id='" + getId() + '\'' +
				", itemId='" + itemId + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", flag='" + flag + '\'' +
                ", itemnum='" + itemNum + '\'' +
                ", itemqty='" + itemQty + '\'' +
                ", itemUnit='" + itemUnit + '\'' +
                ", storeroomid='" + storeRoomId + '\'' +
                ", workorderid='" + workOrderId + '\'' +
                ", createUser='" + getCreateUser() + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                ", updateDate='" + getUpdateDate() + '\'' +
                '}';
    }
}