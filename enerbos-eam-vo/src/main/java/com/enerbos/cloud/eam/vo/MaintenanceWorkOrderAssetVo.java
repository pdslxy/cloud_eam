package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

 /**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 工单设备表VO
 */
@ApiModel(value = "工单设备表VO", description = "工单设备表的实体对应的VO")
public class MaintenanceWorkOrderAssetVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@ApiModelProperty(value = "(新增不用传值)")
	@Length(max = 36, message = "不能超过36个字符")
	private String id;
	
	/**
	 * 
	 */
	@ApiModelProperty(value = "设备ID")
	@Length(max = 36, message = "不能超过36个字符")
	private String assetId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "维保工单ID")
	@Length(max = 36, message = "不能超过36个字符")
	private String workOrderId;

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	 @Override
	 public String toString() {
		 return "MaintenanceWorkOrderAssetVo{" +
				 "id='" + id + '\'' +
				 ", assetId='" + assetId + '\'' +
				 ", workOrderId='" + workOrderId + '\'' +
				 '}';
	 }
 }
