package com.enerbos.cloud.eam.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

 /**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 作业标准关联的物料VO
 */
@ApiModel(value = "作业标准关联的物料VO", description = "作业标准的物料VO")
public class MaintenanceJobStandardItemVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id(新增不用传值)")
	@Length(max = 36, message = "不能超过36个字符")
	private String id;
	
	/**
	 * 作业标准id
	 */
	@ApiModelProperty(value = "作业标准id")
	@Length(max = 255, message = "不能超过255个字符")
	private String jobStandardId;
	
	/**
	 * 物料描述
	 */
	@ApiModelProperty(value = "物料描述")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemDesc;
	
	/**
	 * 物料ID
	 */
	@ApiModelProperty(value = "物料ID")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemId;
	
	/**
	 * 物料编码
	 */
	@ApiModelProperty(value = "物料编码")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemNum;
	
	/**
	 * 所需物料的数量
	 */
	@ApiModelProperty(value = "所需物料的数量")
	@Length(max = 255, message = "不能超过255个字符")
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
	@ApiModelProperty(value = "库房ID")
	@Length(max = 255, message = "不能超过255个字符")
	private String storeRoomId;
	
	/**
	 * 库房组织编码
	 */
	@ApiModelProperty(value = "库房组织编码")
	@Length(max = 255, message = "不能超过255个字符")
	private String orgId;
	/**
	 * 库房站点编码
	 */
	@ApiModelProperty(value = "库房站点编码")
	@Length(max = 255, message = "不能超过255个字符")
	private String siteId;
	
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@Length(max = 36, message = "不能超过36个字符")
	private String createUser;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
    public String toString() {
        return "MaintenanceJobStandardItemVo{" +
                "id='" + id + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", jobStandardId='" + jobStandardId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", itemId=" + itemId +
                ", itemNum=" + itemNum +
                ", itemQty='" + itemQty + '\'' +
                ", itemUnit='" + itemUnit + '\'' +
                ", storeRoomId=" + storeRoomId +
                ", createUser='" + createUser + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
