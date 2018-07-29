package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年07月07日
 * @Description 维保工单-执行汇报Vo
 */
@ApiModel(value = "维保工单-执行汇报", description = "维保工单-执行汇报VO")
public class MaintenanceWorkOrderForReportVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

	@ApiModelProperty(value = "提报人")
	private String reportId;

	@ApiModelProperty(value = "提报人名")
	private String reportName;

	@ApiModelProperty(value = "工程类型")
	private String projectType;

	@ApiModelProperty(value = "工程类型对应用户组名")
	private String projectGroupTypeName;

	@ApiModelProperty(value = "分派人")
	private String assignPersonId;

	@ApiModelProperty(value = "分派人name")
	private String assignPersonName;

    //==============执行汇报=======================
	@ApiModelProperty(value = "实际执行负责人")
	private String actualExecutorResponsibleId;

	@ApiModelProperty(value = "实际执行负责人name")
	private String actualExecutorResponsibleName;

    @ApiModelProperty(value = "实际执行人")
    private String actualExecutorId;
    
    @ApiModelProperty(value = "实际执行人name")
    private String actualExecutorName;

	@ApiModelProperty(value = "每个实际执行人执行班组名称，多个以逗号隔开")
	private String personWorkGroup;
    
    @ApiModelProperty(value = "实际执行班组")
    private String actualWorkGroup;
    
    @ApiModelProperty(value = "实际执行班组名")
    private String actualWorkGroupName;

    @ApiModelProperty(value = "实际开始时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualStartDate;

    @ApiModelProperty(value = "实际结束时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualEndDate;

    @ApiModelProperty(value = "故障总结")
    private String failureSummarize;

    @ApiModelProperty(value = "是否挂起")
    private Boolean suspension;
    
    @ApiModelProperty(value = "挂起开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date suspensionStartDate;
    
    @ApiModelProperty(value = "挂起结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date suspensionEndDate;

    @ApiModelProperty(value = "挂起类型")
    private String suspensionType;
    
    @ApiModelProperty(value = "是否超时")
    private Boolean executeWhetherTimeout = false;
    
    @ApiModelProperty(value = "实际服务费用（外委时填此费用）")
    private String actualCharge;
    
    //============================================

	@ApiModelProperty(value = "预防维护编号",hidden = true)
	private String maintenancePlanNum;

	/**
	 * 工单总工时（系统自动算出结果）--本次工时*设备数量（分钟）
	 */
	@ApiModelProperty(value = "工单总工时",hidden = true)
	private Double workOrderTotalTime;

	/**
	 * 单设备本次工时（系统自动算出结果）--实际执行步骤工时之和（分钟）
	 */
	@ApiModelProperty(value = "单设备本次工时",hidden = true)
	private Double singleAssetThisTime;

	/**
	 * 单设备上次工时（系统自动算出结果）--该预防性维护计划上次生成工单的实际工时（分钟）
	 */
	@ApiModelProperty(value = "单设备上次工时",hidden = true)
	private Double singleAssetLastTime;

	/**
	 * 单设备标准工时（系统自动算出结果）--作业标准步骤工时之和（分钟）
	 */
	@ApiModelProperty(value = "单设备标准工时",hidden = true)
	private Double singleAssetNomalTime;

    @ApiModelProperty(value = "所属组织")
    private String orgId;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;
    
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateDate;
    
    @ApiModelProperty(value = "任务步骤列表，保存时只回传已修改数据")
    private List<MaintenanceWorkOrderStepVo> eamOrderstepVoList;
    
    @ApiModelProperty(value = "删除任务步骤ID")
    private List<String> deleteEamOrderstepVoList;
    
    @ApiModelProperty(value = "实际物料列表，只查询不修改")
    private List<MaintenanceWorkOrderActualItemVo> eamActualitemVoList;
    
    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getActualExecutorId() {
		return actualExecutorId;
	}

	public void setActualExecutorId(String actualExecutorId) {
		this.actualExecutorId = actualExecutorId;
	}

	public String getActualExecutorName() {
		return actualExecutorName;
	}

	public void setActualExecutorName(String actualExecutorName) {
		this.actualExecutorName = actualExecutorName;
	}

	public String getActualWorkGroup() {
		return actualWorkGroup;
	}

	public void setActualWorkGroup(String actualWorkGroup) {
		this.actualWorkGroup = actualWorkGroup;
	}

	public String getActualWorkGroupName() {
		return actualWorkGroupName;
	}

	public void setActualWorkGroupName(String actualWorkGroupName) {
		this.actualWorkGroupName = actualWorkGroupName;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getFailureSummarize() {
		return failureSummarize;
	}

	public void setFailureSummarize(String failureSummarize) {
		this.failureSummarize = failureSummarize;
	}

	public Boolean getSuspension() {
		return suspension;
	}

	public void setSuspension(Boolean suspension) {
		this.suspension = suspension;
	}

	public Date getSuspensionStartDate() {
		return suspensionStartDate;
	}

	public void setSuspensionStartDate(Date suspensionStartDate) {
		this.suspensionStartDate = suspensionStartDate;
	}

	public Date getSuspensionEndDate() {
		return suspensionEndDate;
	}

	public void setSuspensionEndDate(Date suspensionEndDate) {
		this.suspensionEndDate = suspensionEndDate;
	}

	public String getSuspensionType() {
		return suspensionType;
	}

	public void setSuspensionType(String suspensionType) {
		this.suspensionType = suspensionType;
	}

	public Boolean getExecuteWhetherTimeout() {
		return executeWhetherTimeout;
	}

	public void setExecuteWhetherTimeout(Boolean executeWhetherTimeout) {
		this.executeWhetherTimeout = executeWhetherTimeout;
	}

	public String getActualCharge() {
		return actualCharge;
	}

	public void setActualCharge(String actualCharge) {
		this.actualCharge = actualCharge;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<MaintenanceWorkOrderStepVo> getEamOrderstepVoList() {
		return eamOrderstepVoList;
	}

	public void setEamOrderstepVoList(List<MaintenanceWorkOrderStepVo> eamOrderstepVoList) {
		this.eamOrderstepVoList = eamOrderstepVoList;
	}

	public List<String> getDeleteEamOrderstepVoList() {
		return deleteEamOrderstepVoList;
	}

	public void setDeleteEamOrderstepVoList(List<String> deleteEamOrderstepVoList) {
		this.deleteEamOrderstepVoList = deleteEamOrderstepVoList;
	}

	public List<MaintenanceWorkOrderActualItemVo> getEamActualitemVoList() {
		return eamActualitemVoList;
	}

	public void setEamActualitemVoList(List<MaintenanceWorkOrderActualItemVo> eamActualitemVoList) {
		this.eamActualitemVoList = eamActualitemVoList;
	}

	public List<WorkFlowImpleRecordVo> getEamImpleRecordVoVoList() {
		return eamImpleRecordVoVoList;
	}

	public void setEamImpleRecordVoVoList(List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList) {
		this.eamImpleRecordVoVoList = eamImpleRecordVoVoList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Double getWorkOrderTotalTime() {
		return workOrderTotalTime;
	}

	public void setWorkOrderTotalTime(Double workOrderTotalTime) {
		this.workOrderTotalTime = workOrderTotalTime;
	}

	public String getMaintenancePlanNum() {
		return maintenancePlanNum;
	}

	public void setMaintenancePlanNum(String maintenancePlanNum) {
		this.maintenancePlanNum = maintenancePlanNum;
	}

	public Double getSingleAssetThisTime() {
		return singleAssetThisTime;
	}

	public void setSingleAssetThisTime(Double singleAssetThisTime) {
		this.singleAssetThisTime = singleAssetThisTime;
	}

	public Double getSingleAssetLastTime() {
		return singleAssetLastTime;
	}

	public void setSingleAssetLastTime(Double singleAssetLastTime) {
		this.singleAssetLastTime = singleAssetLastTime;
	}

	public Double getSingleAssetNomalTime() {
		return singleAssetNomalTime;
	}

	public void setSingleAssetNomalTime(Double singleAssetNomalTime) {
		this.singleAssetNomalTime = singleAssetNomalTime;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getAssignPersonId() {
		return assignPersonId;
	}

	public void setAssignPersonId(String assignPersonId) {
		this.assignPersonId = assignPersonId;
	}

	public String getAssignPersonName() {
		return assignPersonName;
	}

	public void setAssignPersonName(String assignPersonName) {
		this.assignPersonName = assignPersonName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getProjectGroupTypeName() {
		return projectGroupTypeName;
	}

	public void setProjectGroupTypeName(String projectGroupTypeName) {
		this.projectGroupTypeName = projectGroupTypeName;
	}

	public String getActualExecutorResponsibleId() {
		return actualExecutorResponsibleId;
	}

	public void setActualExecutorResponsibleId(String actualExecutorResponsibleId) {
		this.actualExecutorResponsibleId = actualExecutorResponsibleId;
	}

	public String getActualExecutorResponsibleName() {
		return actualExecutorResponsibleName;
	}

	public void setActualExecutorResponsibleName(String actualExecutorResponsibleName) {
		this.actualExecutorResponsibleName = actualExecutorResponsibleName;
	}

	public String getPersonWorkGroup() {
		return personWorkGroup;
	}

	public void setPersonWorkGroup(String personWorkGroup) {
		this.personWorkGroup = personWorkGroup;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForReportVo{" +
				"id='" + id + '\'' +
				", status='" + status + '\'' +
				", siteId='" + siteId + '\'' +
				", reportId='" + reportId + '\'' +
				", reportName='" + reportName + '\'' +
				", projectType='" + projectType + '\'' +
				", projectGroupTypeName='" + projectGroupTypeName + '\'' +
				", assignPersonId='" + assignPersonId + '\'' +
				", assignPersonName='" + assignPersonName + '\'' +
				", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
				", actualExecutorResponsibleName='" + actualExecutorResponsibleName + '\'' +
				", actualExecutorId='" + actualExecutorId + '\'' +
				", actualExecutorName='" + actualExecutorName + '\'' +
				", personWorkGroup='" + personWorkGroup + '\'' +
				", actualWorkGroup='" + actualWorkGroup + '\'' +
				", actualWorkGroupName='" + actualWorkGroupName + '\'' +
				", actualStartDate=" + actualStartDate +
				", actualEndDate=" + actualEndDate +
				", failureSummarize='" + failureSummarize + '\'' +
				", suspension=" + suspension +
				", suspensionStartDate=" + suspensionStartDate +
				", suspensionEndDate=" + suspensionEndDate +
				", suspensionType='" + suspensionType + '\'' +
				", executeWhetherTimeout=" + executeWhetherTimeout +
				", actualCharge='" + actualCharge + '\'' +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", workOrderTotalTime=" + workOrderTotalTime +
				", singleAssetThisTime=" + singleAssetThisTime +
				", singleAssetLastTime=" + singleAssetLastTime +
				", singleAssetNomalTime=" + singleAssetNomalTime +
				", orgId='" + orgId + '\'' +
				", processInstanceId='" + processInstanceId + '\'' +
				", updateDate=" + updateDate +
				", eamOrderstepVoList=" + eamOrderstepVoList +
				", deleteEamOrderstepVoList=" + deleteEamOrderstepVoList +
				", eamActualitemVoList=" + eamActualitemVoList +
				", eamImpleRecordVoVoList=" + eamImpleRecordVoVoList +
				'}';
	}
}