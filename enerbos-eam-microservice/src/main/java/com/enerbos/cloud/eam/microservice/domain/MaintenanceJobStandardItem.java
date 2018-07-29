package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description 作业标准关联的物料实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_job_standard_item")
public class MaintenanceJobStandardItem extends EnerbosBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 作业标准id
     *
     */
    @Column(nullable = false,name = "job_standard_id")
    private String jobStandardId;

    /**
     * 物料描述
     */
    @Column(nullable = false,name = "item_desc")
    private String itemDesc;

    /**
     * 物料ID
     */
    @Column(name = "item_id")
    private String itemId;
    
    /**
     * 物料编码
     */
    @Column(name = "item_num")
    private String itemNum;

    /**
     * 所需物料的数量
     */
    @Column(name = "item_qty")
    private String itemQty;
    
    /**
	 * 物料单位
	 */
	@ApiModelProperty(value = "物料单位")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemUnit;

    /**
     * 库房ID
     */
    @Column(name = "store_room_id")
    private String storeRoomId;
    
    /**
     * 库房组织编码
     */
    @Column(name = "org_id")
    private String orgId;
    
    /**
     * 库房站点编码
     */
    @Column(name = "site_id")
    private String siteId;

	public String getJobStandardId() {
		return jobStandardId;
	}

	public void setJobStandardId(String jobStandardId) {
		this.jobStandardId = jobStandardId;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Override
    public String toString() {
        return "MaintenanceJobStandardItem{" +
                "id='" + getId() + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", jobStandardId='" + jobStandardId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", itemId=" + itemId +
                ", itemNum=" + itemNum +
                ", itemQty='" + itemQty + '\'' +
                ", itemUnit='" + itemUnit + '\'' +
                ", storeRoomId=" + storeRoomId +
                ", createUser='" + getCreateUser() + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                ", updateDate='" + getUpdateDate() + '\'' +
                '}';
    }
}
