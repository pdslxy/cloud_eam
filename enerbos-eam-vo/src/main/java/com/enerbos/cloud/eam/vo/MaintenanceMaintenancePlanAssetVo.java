package com.enerbos.cloud.eam.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

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
 * @Description 与标准作业计划关联的设备VO
 */
@ApiModel(value = "与标准作业计划关联的设备VO", description = "与标准作业计划关联的设备的实体对应的VO")
public class MaintenanceMaintenancePlanAssetVo implements Serializable{
	
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
	@ApiModelProperty(value = "")
	@Length(max = 255, message = "不能超过255个字符")
	private String assetId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	@Length(max = 255, message = "不能超过255个字符")
	private String maintenancePlanId;

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getAssetId(){
		return assetId;
	}
	
	public void setAssetId(String assetId){
		this.assetId = assetId;
	}

	 public static long getSerialVersionUID() {
		 return serialVersionUID;
	 }

	 public String getMaintenancePlanId() {
		 return maintenancePlanId;
	 }

	 public void setMaintenancePlanId(String maintenancePlanId) {
		 this.maintenancePlanId = maintenancePlanId;
	 }

	 @Override
	 public String toString() {
		 return "MaintenanceMaintenancePlanAssetVo{" +
				 "id='" + id + '\'' +
				 ", assetId='" + assetId + '\'' +
				 ", maintenancePlanId='" + maintenancePlanId + '\'' +
				 '}';
	 }
 }
