package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
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
 * @date 2017年4月1日 上午9:26:50
 * 
 * @Description 物资接收实体类
 * 
 *
 */
@Entity
@Table(name = "eam_material_goods_receive")
public class MaterialGoodsReceive extends EnerbosBaseEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7399953215996204383L;

	/**
	 * 编码
	 */
	@Column(name = "goods_receive_num",nullable = true,length = 36)
	private String goodsReceiveNum;

	/**
	 * 描述
	 */
	@Column(name="description",nullable = true,length = 255)
	private String description;

	/**
	 * 接收类型(采购接收,库存转移)
	 */
	@Column(name = "receive_type",nullable = true,length = 36)
	private String receiveType;

	/**
	 *  接受说明
	 */
	@Column(name = "explains",nullable = true,length = 255)
	private String explains ;
	/**
	 * 要运输到的库房标识
	 */
	@Column(name = "storeroom_id",nullable = true ,length = 36)
	private String storeroomId;

	/**
	 * 备注
	 */
	@Column(name="mark",nullable = true,length=255)
	private String mark;

	/**
	 * 实际接收日期和时间
	 */
	@Column(name = "receive_date",nullable = true,length = 30)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date receiveDate;

	/**
	 * 实际接收人
	 */
	@Column(name = "receive_person",nullable = true,length = 36)
	private String receivePerson;

	/**
	 * 物资接收单的当前状态 DRAFT-草稿 RECEIVE-已接收
	 */
	@Column(name="status",nullable = true,length =36)
	private String status;

	/**
	 * 最后修改人员
	 */
	@Column(name = "change_user",nullable = true,length = 36)
	private String changeUser;

	/**
	 * 所属组织id
	 */
	@Column(name = "org_id",nullable = true,length = 36)
	private String orgId;

	/**
	 * 所属站点id
	 */
	@Column(name = "site_id",nullable = true,length = 36)
	private String siteId;


	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getGoodsReceiveNum() {
		return goodsReceiveNum;
	}

	public void setGoodsReceiveNum(String goodsReceiveNum) {
		this.goodsReceiveNum = goodsReceiveNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getStoreroomId() {
		return storeroomId;
	}

	public void setStoreroomId(String storeroomId) {
		this.storeroomId = storeroomId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "MaterialGoodsReceive{" +
				"goodsReceiveNum='" + goodsReceiveNum + '\'' +
				", description='" + description + '\'' +
				", receiveType='" + receiveType + '\'' +
				", explains='" + explains + '\'' +
				", storeroomId='" + storeroomId + '\'' +
				", mark='" + mark + '\'' +
				", receiveDate=" + receiveDate +
				", receivePerson='" + receivePerson + '\'' +
				", status='" + status + '\'' +
				", changeUser='" + changeUser + '\'' +
				", orgId='" + orgId + '\'' +
				", siteId='" + siteId + '\'' +
				'}';
	}
}
