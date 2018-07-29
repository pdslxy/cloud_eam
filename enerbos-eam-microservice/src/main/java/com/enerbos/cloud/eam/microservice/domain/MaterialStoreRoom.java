package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 库房
 */
@Entity
@Table(name = "eam_material_storeroom")
public class MaterialStoreRoom extends EnerbosBaseEntity implements
		Serializable {

	private static final long serialVersionUID = -4779851640549697964L;

	/**
	 * 库房编码
	 */
	@Column(name = "storeroom_num", nullable = false,length = 36)
	private String storeroomNum;

	/**
	 * 库房名称
	 */
	@Column(name = "storeroom_name",nullable = true,length = 36)
	private String storeroomName;

	/**
	 * 科目
	 */
	@Column(name="controlacc",nullable = true,length = 36)
	private String controlacc;

	/**
	 * 状态
	 */
	@Column(name="status",nullable = true,length = 36)
	private String status;
	/**
	 * 收货地址
	 */
	@Column(name = "delivery_address",nullable = true,length = 255)
	private String deliveryAddress;

	/**
	 * 是否设置为缺省库房
	 */
	@Column(name="isdefault",nullable = true)
	private Boolean isdefault;

	/**
	 * 备注
	 */
	@Column(name="mark",nullable = true,length = 255)
	private String mark;

	/**
	 * 所属站点编码
	 */
	@Column(name = "site_id",nullable = true,length = 36)
	private String siteId;

	/**
	 * 所属组织编码
	 */

	@Column(name = "org_id",nullable = true,length = 36)
	private String orgId;

	/**
	 * 负责人
	 */

	@Column(name = "person_id",nullable = true,length = 36)
	private String personId;

	public String getStoreroomNum() {
		return storeroomNum;
	}

	public void setStoreroomNum(String storeroomNum) {
		this.storeroomNum = storeroomNum;
	}

	public String getStoreroomName() {
		return storeroomName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStoreroomName(String storeroomName) {
		this.storeroomName = storeroomName;
	}

	public String getControlacc() {
		return controlacc;
	}

	public void setControlacc(String controlacc) {
		this.controlacc = controlacc;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Boolean getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Boolean isdefault) {
		this.isdefault = isdefault;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Override
	public String toString() {
		return "MaterialStoreRoom [storeroomNum=" + storeroomNum
				+ ", storeroomName=" + storeroomName + ", controlacc="
				+ controlacc + ", status=" + status + ", deliveryAddress="
				+ deliveryAddress + ", isdefault=" + isdefault + ", mark="
				+ mark + ", siteId=" + siteId + ", orgId=" + orgId
				+ ", personId=" + personId + "]";
	}

	
}
