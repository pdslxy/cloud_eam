package com.enerbos.cloud.eam.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.Length;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;

import java.util.Date;

/**
 * 
 * 
 * 
 * All rights Reserved, Designed By 翼虎能源
 * 
 * Copyright: Copyright(C) 2015-2017
 * 
 * Company 北京翼虎能源科技有限公司
 * 
 *
 * 
 * @author 刘秀朋
 * 
 * @version 1.0
 * 
 * @date 2017年4月1日 上午10:54:56
 * 
 * @Description 物资发放明细
 * 
 *
 */
@ApiModel(value = "物资发放明细")
public class MaterialReleaseDetailVo extends EAMBaseFilterVo {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id(新增不传值)")
	private String id;

	/**
	 * 
	 * 物资发放id
	 * 
	 */
	@ApiModelProperty(value = "物资发放id(长度不能超过36个字符)")
	@Length(max = 36, message = "物资发放id不能超过36个字符")
	private String releaseId;

	/**
	 * 库存信息
	 */
	@ApiModelProperty(value = "库存信息(不能超过36个字符)")
	@Length(max = 36, message = "库存信息不能超过36个字符")
	private String inventoryId;

	
	/**
	 * 行成本
	 */
	@ApiModelProperty(value = "行成本")
	private Double lineCost;

	/**
	 * 周转资产编码
	 */
	@ApiModelProperty(value = "周转资产编码(不能超过255个字符)")
	@Length(max = 255, message = "周转资产编码不能超过255个字符")
	private String turnOverAssetNum;

	/**
	 * 数量
	 */
	@ApiModelProperty(value = "数量")
	private Long quantity;

	/**
	 * 新资产编码
	 */
	@ApiModelProperty(value = "新资产编码(不能超过255个字符)")
	@Length(max = 255, message = "新资产编码不能超过255个字符")
	private String newAssetNum;

	/**
	 * 判断是否为预留物料
	 */
	@ApiModelProperty(value = "判断是否为预留物料")
	private Boolean isReserved;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;
	@ApiModelProperty(value = "更新时间")
	private Date updateDate;
	/**
	 * 输入人
	 */
	@ApiModelProperty(value = "输入人(不能超过255个字符)")
	@Length(max = 255, message = "输入人不能超过255个字符")
	private String createUser;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注(不能超过255个字符)")
	@Length(max = 255, message = "备注不能超过255个字符")
	private String mark;

	/**
	 * 位置
	 */
	@ApiModelProperty(value = "位置(不能超过36个字符)")
	@Length(max = 36, message = "位置不能超过36个字符")
	private String locationsId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Double getLineCost() {
		return lineCost;
	}

	public void setLineCost(Double lineCost) {
		this.lineCost = lineCost;
	}

	public String getTurnOverAssetNum() {
		return turnOverAssetNum;
	}

	public void setTurnOverAssetNum(String turnOverAssetNum) {
		this.turnOverAssetNum = turnOverAssetNum;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getNewAssetNum() {
		return newAssetNum;
	}

	public void setNewAssetNum(String newAssetNum) {
		this.newAssetNum = newAssetNum;
	}

	public Boolean getIsReserved() {
		return isReserved;
	}

	public void setIsReserved(Boolean isReserved) {
		this.isReserved = isReserved;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getLocationsId() {
		return locationsId;
	}

	public void setLocationsId(String locationsId) {
		this.locationsId = locationsId;
	}

	@Override
	public String toString() {
		return "MaterialReleaseDetailVo [id=" + id + ", releaseId=" + releaseId
				+ ", inventoryId=" + inventoryId + ", lineCost=" + lineCost
				+ ", turnOverAssetNum=" + turnOverAssetNum + ", quantity="
				+ quantity + ", newAssetNum=" + newAssetNum + ", isReserved="
				+ isReserved + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", createUser=" + createUser + ", mark=" + mark
				+ ", locationsId=" + locationsId + "]";
	}

}
