package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
 * @date 2017年4月7日 下午1:45:21
 *
 * @Description 物资接收明细
 *
 *
 */
@Entity
@Table(name = "eam_material_goods_receive_detail")
public class MaterialGoodsReceiveDetail extends EnerbosBaseEntity implements
		Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5668062100070290811L;

	/**
	 * 对应的物资接收单
	 */
	@Column(name = "goods_receive_id", nullable = true, length = 36)
	private String goodsReceiveId;

	/**
	 * 关联的物料
	 */
	@Column(name = "item_id", nullable = true, length = 36)
	private String itemId;

	/**
	 * 订购单位的名称
	 */
	@Column(name = "receive_unit", nullable = true, length = 10)
	private String receiveUnit;

	/**
	 * 接收数量
	 */
	@Column(name = "receive_quantity", nullable = true)
	private Long receiveQuantity;

	/**
	 * 单价
	 */
	@Column(name = "unit_cost", nullable = true)
	private Double unitCost;

	/**
	 * 行价格
	 */
	@Column(name = "line_cost", nullable = true)
	private Double lineCost;

	/**
	 * 平均成本,在接收或转移时计算
	 */
	@Column(name = "average_cost", nullable = true)
	private Double averageCost;

	/**
	 * 备注
	 */
	@Column(name = "mark", nullable = true, length = 255)
	private String mark;

	/**
	 * 最后修改人员
	 */
	@Column(name = "change_user", nullable = true, length = 36)
	private String changeUser;

	/**
	 * 组织id
	 */
	@Column(name = "org_id", nullable = true, length = 36)
	private String orgId;

	/**
	 * 站点id
	 */
	@Column(name = "site_id", nullable = true, length = 36)
	private String siteId;

	public String getGoodsReceiveId() {
		return goodsReceiveId;
	}

	public void setGoodsReceiveId(String goodsReceiveId) {
		this.goodsReceiveId = goodsReceiveId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getReceiveUnit() {
		return receiveUnit;
	}

	public void setReceiveUnit(String receiveUnit) {
		this.receiveUnit = receiveUnit;
	}

	public Long getreceiveQuantity() {
		return receiveQuantity;
	}

	public void setreceiveQuantity(Long receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getLineCost() {
		return lineCost;
	}

	public void setLineCost(Double lineCost) {
		this.lineCost = lineCost;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	public String getChangeUser() {
		return changeUser;
	}

	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
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

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "MaterialGoodsReceiveDetail [goodsReceiveId=" + goodsReceiveId
				+ ", itemId=" + itemId + ", receiveUnit=" + receiveUnit
				+ ", receiveQuantity=" + receiveQuantity + ", unitCost="
				+ unitCost + ", lineCost=" + lineCost + ", averageCost="
				+ averageCost + ", mark=" + mark + ", changeUser=" + changeUser
				+ ", orgId=" + orgId + ", siteId=" + siteId + "]";
	}

}
