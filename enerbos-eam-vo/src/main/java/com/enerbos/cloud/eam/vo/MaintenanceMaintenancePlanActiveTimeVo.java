package com.enerbos.cloud.eam.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 预防性维护计划有效时间VO
 */
@ApiModel(value = "预防性维护计划有效时间VO", description = "预防性维护计划有效时间实体对应的VO")
public class MaintenanceMaintenancePlanActiveTimeVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@ApiModelProperty(value = "(新增不用传值)")
	@Length(max = 36, message = "不能超过36个字符")
	private String id;

	 /**
	  *开始月份
	  */
	 @ApiModelProperty(value = "开始月日")
	 @Temporal(TemporalType.DATE)
	 @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
	 private Date startDate;

	 /**
	  *开始日
	  */
	 @ApiModelProperty(value = "结束月日")
	 @Temporal(TemporalType.DATE)
	 @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
	 private Date endDate;


	/**
	 * 预防性维护计划ID
	 */
	@ApiModelProperty(value = "预防性维护计划ID")
	@Length(max = 255, message = "不能超过255个字符")
	private String maintenancePlanId;

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "MaintenanceMaintenancePlanActiveTimeVo{" +
				"id='" + id + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", maintenancePlanId='" + maintenancePlanId + '\'' +
				'}';
	}
}
