package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.*;

import java.io.Serializable;

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
 * @date 2017年7月11日 下午8:20:49
 * 
 * @Description 物资发放明细实体
 * 
 *
 */
@Entity
@Table(name = "eam_material_release_detail")
public class MaterialReleaseDetail extends EnerbosBaseEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6285560808082946532L;

	/**
	 * 库存信息
	 */
	@Column(name = "inventory_id", nullable = true, length = 36)
	private String inventoryId;

	/**
	 * 物资发放id
	 */
	@Column(name = "release_id", nullable = true, length = 36)
	private String releaseId;
	/**
	 * 行成本
	 */
	@Column(name = "line_cost", nullable = true)
	private Double lineCost;

	/**
	 * 周转资产编码
	 */
	@Column(name = "turnover_asset_num", nullable = true, length = 36)
	private String turnOverAssetNum;

	/**
	 * 数量
	 */
	@Column(name = "quantity", nullable = true, length = 20)
	private Long quantity;

	/**
	 * 新资产编码
	 */
	@Column(name = "new_asset_num", nullable = true, length = 36)
	private String newAssetNum;

	/**
	 * 判断是否为预留物料
	 */
	@Column(name = "is_reserved", nullable = true, length = 1)
	private Boolean isReserved;

	/**
	 * 备注
	 */
	@Column(name = "mark", nullable = true, length = 255)
	private String mark;

	/**
	 * 位置
	 */
	@Column(name = "locations_id", nullable = true, length = 36)
	private String locationsId;

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

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public Boolean getReserved() {
		return isReserved;
	}

	public void setReserved(Boolean reserved) {
		isReserved = reserved;
	}

	@Override
	public String toString() {
		return "MaterialReleaseDetail{" +
				"inventoryId='" + inventoryId + '\'' +
				", releaseId='" + releaseId + '\'' +
				", lineCost=" + lineCost +
				", turnOverAssetNum='" + turnOverAssetNum + '\'' +
				", quantity=" + quantity +
				", newAssetNum='" + newAssetNum + '\'' +
				", isReserved=" + isReserved +
				", mark='" + mark + '\'' +
				", locationsId='" + locationsId + '\'' +
				'}';
	}

}
