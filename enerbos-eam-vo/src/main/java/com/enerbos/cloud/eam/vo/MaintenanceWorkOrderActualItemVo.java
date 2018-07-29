package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

 /**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单实际物料VO
 */
@ApiModel(value = "工单实际物料VO", description = "工单实际物料的实体对应的VO")
public class MaintenanceWorkOrderActualItemVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@ApiModelProperty(value = "id(新增不用传值)")
	@Length(max = 36, message = "不能超过36个字符")
	private String id;
	
	/**
	 * 物料描述
	 */
	@ApiModelProperty(value = "物料描述")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemDesc;
	/**
	 * 物资发放和退回的标志  1:issue 发放  2:return 退回
	 */
	@ApiModelProperty(value = "物资发放和退回的标志  1:issue 发放  2:return 退回")
	@Length(max = 255, message = "不能超过255个字符")
	private String flag;
	 /**
	  * 物料ID
	  */
	 @ApiModelProperty(value = "物料ID")
	 @Length(max = 36, message = "不能超过36个字符")
	 private String itemId;

	/**
	 * 物料编码
	 */
	@ApiModelProperty(value = "物料编码")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemNum;
	/**
	 * 物料数量
	 */
	@ApiModelProperty(value = "物料数量")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemQty;
	
	/**
	 * 物料单位
	 */
	@ApiModelProperty(value = "物料单位")
	@Length(max = 255, message = "不能超过255个字符")
	private String itemUnit;

	 /**
	  * 物料单位描述
	  */
	 @ApiModelProperty(value = "物料单位描述")
	 @Length(max = 255, message = "不能超过255个字符")
	 private String itemUnitDesc;
	
	/**
	 * 库房
	 */
	@ApiModelProperty(value = "库房")
	@Length(max = 36, message = "不能超过36个字符")
	private String storeRoomId;
	/**
	 * 工单ID
	 */
	@ApiModelProperty(value = "工单ID")
	@Length(max = 36, message = "不能超过36个字符")
	private String workOrderId;
	
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
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
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

	 public static long getSerialVersionUID() {
		 return serialVersionUID;
	 }

	 public String getItemId() {
		 return itemId;
	 }

	 public void setItemId(String itemId) {
		 this.itemId = itemId;
	 }

	 public String getItemUnitDesc() {
		 return itemUnitDesc;
	 }

	 public void setItemUnitDesc(String itemUnitDesc) {
		 this.itemUnitDesc = itemUnitDesc;
	 }

	 @Override
	 public String toString() {
		 return "MaintenanceWorkOrderActualItemVo{" +
				 "id='" + id + '\'' +
				 ", itemDesc='" + itemDesc + '\'' +
				 ", flag='" + flag + '\'' +
				 ", itemId='" + itemId + '\'' +
				 ", itemNum='" + itemNum + '\'' +
				 ", itemQty='" + itemQty + '\'' +
				 ", itemUnit='" + itemUnit + '\'' +
				 ", itemUnitDesc='" + itemUnitDesc + '\'' +
				 ", storeRoomId='" + storeRoomId + '\'' +
				 ", workOrderId='" + workOrderId + '\'' +
				 ", createUser='" + createUser + '\'' +
				 ", createDate=" + createDate +
				 ", updateDate=" + updateDate +
				 '}';
	 }
 }
