package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class MaintenanceWorkOrderForWorkFlowVo implements Serializable {

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

	@ApiModelProperty(value = "状态")
    private String status;

	@ApiModelProperty(value = "状态日期（前端不展示）")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date statusDate;

    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

	@ApiModelProperty(value = "提报人")
	private String reportId;

	@ApiModelProperty(value = "提报日期")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date reportDate;

	@ApiModelProperty(value = "工程类型")
	private String projectType;

	//==================任务分派===================
	@ApiModelProperty(value = "是否委托执行")
	private Boolean entrustExecute;

//	@ApiModelProperty(value = "委托执行人,可能是多个,在数据库中以逗号进行分割")
//	private String entrustExecutePerson;

	@ApiModelProperty(value = "执行人id")
	private List<String> executorPersonId;

	@ApiModelProperty(value = "实际执行负责人id")
	private String actualExecutorResponsibleId;

	@ApiModelProperty(value = "分派人")
	private String assignPersonId;

	@ApiModelProperty(value = "计划开始时间")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date planStartDate;

	@ApiModelProperty(value = "计划完成时间")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date planCompletionDate;

    //==============执行汇报=======================
//    @ApiModelProperty(value = "实际执行人")
//    private String actualExecutorId;

	@ApiModelProperty(value = "实际执行班组")
	private String actualWorkGroup;

	@ApiModelProperty(value = "执行班组")
	private String executionWorkGroup;

    @ApiModelProperty(value = "是否挂起")
    private Boolean suspension;

    @ApiModelProperty(value = "挂起类型")
    private String suspensionType;
    
	@ApiModelProperty(value = "预防维护编号",hidden = true)
	private String maintenancePlanNum;

    @ApiModelProperty(value = "所属组织")
    private String orgId;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

	@ApiModelProperty(value = "确认解决")
	private Boolean confirm;

	@ApiModelProperty(value = "验收时间")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date acceptionTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public Boolean getSuspension() {
		return suspension;
	}

	public void setSuspension(Boolean suspension) {
		this.suspension = suspension;
	}

	public String getSuspensionType() {
		return suspensionType;
	}

	public void setSuspensionType(String suspensionType) {
		this.suspensionType = suspensionType;
	}

	public String getMaintenancePlanNum() {
		return maintenancePlanNum;
	}

	public void setMaintenancePlanNum(String maintenancePlanNum) {
		this.maintenancePlanNum = maintenancePlanNum;
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

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public Boolean getEntrustExecute() {
		return entrustExecute;
	}

	public void setEntrustExecute(Boolean entrustExecute) {
		this.entrustExecute = entrustExecute;
	}

	public String getAssignPersonId() {
		return assignPersonId;
	}

	public void setAssignPersonId(String assignPersonId) {
		this.assignPersonId = assignPersonId;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanCompletionDate() {
		return planCompletionDate;
	}

	public void setPlanCompletionDate(Date planCompletionDate) {
		this.planCompletionDate = planCompletionDate;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getActualExecutorResponsibleId() {
		return actualExecutorResponsibleId;
	}

	public void setActualExecutorResponsibleId(String actualExecutorResponsibleId) {
		this.actualExecutorResponsibleId = actualExecutorResponsibleId;
	}

	public List<String> getExecutorPersonId() {
		return executorPersonId;
	}

	public void setExecutorPersonId(List<String> executorPersonId) {
		this.executorPersonId = executorPersonId;
	}

	public String getActualWorkGroup() {
		return actualWorkGroup;
	}

	public void setActualWorkGroup(String actualWorkGroup) {
		this.actualWorkGroup = actualWorkGroup;
	}

	public String getExecutionWorkGroup() {
		return executionWorkGroup;
	}

	public void setExecutionWorkGroup(String executionWorkGroup) {
		this.executionWorkGroup = executionWorkGroup;
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public Date getAcceptionTime() {
		return acceptionTime;
	}

	public void setAcceptionTime(Date acceptionTime) {
		this.acceptionTime = acceptionTime;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForWorkFlowVo{" +
				"id='" + id + '\'' +
				", workOrderNum='" + workOrderNum + '\'' +
				", description='" + description + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
				", siteId='" + siteId + '\'' +
				", reportId='" + reportId + '\'' +
				", reportDate=" + reportDate +
				", projectType='" + projectType + '\'' +
				", entrustExecute=" + entrustExecute +
				", executorPersonId=" + executorPersonId +
				", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
				", assignPersonId='" + assignPersonId + '\'' +
				", planStartDate=" + planStartDate +
				", planCompletionDate=" + planCompletionDate +
				", actualWorkGroup='" + actualWorkGroup + '\'' +
				", executionWorkGroup='" + executionWorkGroup + '\'' +
				", suspension=" + suspension +
				", suspensionType='" + suspensionType + '\'' +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", orgId='" + orgId + '\'' +
				", processInstanceId='" + processInstanceId + '\'' +
				", confirm=" + confirm +
				", acceptionTime=" + acceptionTime +
				'}';
	}
}