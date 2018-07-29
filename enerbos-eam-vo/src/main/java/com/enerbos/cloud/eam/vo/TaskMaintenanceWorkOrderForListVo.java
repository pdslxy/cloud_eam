package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
 * @date 2017年07月21日
 * @Description 我的待办任务，维保工单列表Vo
 */
@ApiModel(value = "我的待办任务，维保工单列表", description = "我的待办任务，维保工单列表")
public class TaskMaintenanceWorkOrderForListVo implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
	 * id
	 */
	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty(value = "工单编号")
    private String workOrderNum;

	@ApiModelProperty(value = "工单描述")
    private String description;

	@ApiModelProperty(value = "工程类型")
    private String projectType;

	@ApiModelProperty(value = "状态")
    private String status;

//	@ApiModelProperty(value = "设备id")
//    private String assetId;
//
//	@ApiModelProperty(value = "设备描述")
//    private String assetdesc;

    @ApiModelProperty(value = "工单类型")
    private String worktype;

    @ApiModelProperty(value = "位置编码")
    private String locationId;
    
    @ApiModelProperty(value = "位置描述")
    private String locationDesc;

    @ApiModelProperty(value = "所属站点编码")
    private String siteId;
    
    @ApiModelProperty(value = "是否超时")
    private Boolean udisww;

    @ApiModelProperty(value = "是否外委")
    private Boolean executeWhetherTimeout;

    @ApiModelProperty(value = "提报日期")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty(value = "所属组织")
    private String orgId;

	@ApiModelProperty(value = "任务开始日期")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date taskBeginTime;

	@ApiModelProperty(value = "流程ID")
	private String processInstanceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkOrderNum() {
		return workOrderNum;
	}

	public void setWorkOrderNum(String workOrderNum) {
		this.workOrderNum = workOrderNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorktype() {
		return worktype;
	}

	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Boolean getUdisww() {
		return udisww;
	}

	public void setUdisww(Boolean udisww) {
		this.udisww = udisww;
	}

	public Boolean getExecuteWhetherTimeout() {
		return executeWhetherTimeout;
	}

	public void setExecuteWhetherTimeout(Boolean executeWhetherTimeout) {
		this.executeWhetherTimeout = executeWhetherTimeout;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}