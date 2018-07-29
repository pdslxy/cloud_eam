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
 * @date 2017年06月13日
 * @Description 维保工单-任务步骤VO
 */
@ApiModel(value = "维保工单-任务步骤VO", description = "维保工单-任务步骤VO")
public class MaintenanceWorkOrderStepVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@ApiModelProperty(value = "id(新增不用传值)")
	@Length(max = 36, message = "不能超过36个字符")
	private String id;
	
	/**
	 * 是否异常
	 */
	@ApiModelProperty(value = "是否异常,true/false")
	private Boolean abnormal=false;
	
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 255, message = "不能超过255个字符")
	private String description;
	
	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "估计执行时间持续时间(分钟)")
	private Double duration;
	
	/**
	 * 执行情况
	 */
	@ApiModelProperty(value = "执行情况,true/false")
	private Boolean executeSituation=false;
	
	/**
	 * 是否处理
	 */
	@ApiModelProperty(value = "是否处理")
	private Boolean handle=false;
	
	/**
	 * 质量标准
	 */
	@ApiModelProperty(value = "质量标准")
	@Length(max = 255, message = "不能超过255个字符")
	private String qualityStandard;
	
	/**
	 * 任务步骤（记录的数字）
	 */
	@ApiModelProperty(value = "任务步骤（记录的数字）")
	@Length(max = 255, message = "不能超过255个字符")
	private String step;
	
	/**
	 * 关联维保工单
	 */
	@ApiModelProperty(value = "关联维保工单")
	@Length(max = 36, message = "不能超过36个字符")
	private String workOrderId;
	
	/**
	 * 实际执行时间
	 */
	@ApiModelProperty(value = "实际执行时间，(任务分派时此字段不显示，执行汇报时只能修改此字段)")
	private String actualExecuteTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getAbnormal() {
		return abnormal;
	}
	public void setAbnormal(Boolean abnormal) {
		this.abnormal = abnormal;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	 public Double getDuration() {
		 return duration;
	 }

	 public void setDuration(Double duration) {
		 this.duration = duration;
	 }

	 public Boolean getExecuteSituation() {
		return executeSituation;
	}
	public void setExecuteSituation(Boolean executeSituation) {
		this.executeSituation = executeSituation;
	}
	public Boolean getHandle() {
		return handle;
	}
	public void setHandle(Boolean handle) {
		this.handle = handle;
	}
	public String getQualityStandard() {
		return qualityStandard;
	}
	public void setQualityStandard(String qualityStandard) {
		this.qualityStandard = qualityStandard;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getActualExecuteTime() {
		return actualExecuteTime;
	}
	public void setActualExecuteTime(String actualExecuteTime) {
		this.actualExecuteTime = actualExecuteTime;
	}

	 @Override
	 public String toString() {
		 return "MaintenanceWorkOrderStepVo{" +
				 "id='" + id + '\'' +
				 ", abnormal=" + abnormal +
				 ", description='" + description + '\'' +
				 ", duration=" + duration +
				 ", executeSituation=" + executeSituation +
				 ", handle=" + handle +
				 ", qualityStandard='" + qualityStandard + '\'' +
				 ", step='" + step + '\'' +
				 ", workOrderId='" + workOrderId + '\'' +
				 ", actualExecuteTime='" + actualExecuteTime + '\'' +
				 '}';
	 }
 }
