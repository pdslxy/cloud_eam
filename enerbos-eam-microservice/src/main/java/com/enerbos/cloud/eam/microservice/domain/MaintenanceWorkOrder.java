package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description 维保工单实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_work_order")
public class MaintenanceWorkOrder extends EnerbosBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	//==================工单提报==============
    /**
	 * 工单编码
	 */
	@Column(name="work_order_num",nullable = false,length = 255)
	private String workOrderNum;
	
	/**
	 * 工单描述
	 */
	@Column(name="description",nullable = true,length = 255)
	private String description;
	
	/**
	 * 工作类型
	 */
	@Column(name="work_type",nullable = true,length = 255)
	private String workType;
	
	/**
	 * 工程类型
	 */
	@Column(name="project_type",nullable = true,length = 255)
	private String projectType;
	
	/**
	 * 状态
	 */
	@Column(name="status",nullable = true,length = 255)
	private String status;
	
	/**
	 * 状态日期
	 */
	@Column(name="status_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date statusDate;
	
	/**
	 * 设备id
	 */
	@Column(name="asset_id",nullable = true,length = 36)
	private String assetId;
	
	/**
	 * 设备描述
	 */
	@Column(name="asset_desc",nullable = true,length = 255)
	private String assetDesc;
	
	/**
	 * 分类id
	 */
	@Column(name="classification_id",nullable = true,length = 36)
	private String classificationId;
	
	/**
	 * 位置id
	 */
	@Column(name="location_id",nullable = true,length = 36)
	private String locationId;
	
	/**
	 * 事件级别
	 */
	@Column(name="incident_level",nullable = true,length = 255)
	private String incidentLevel;
	
	/**
	 * 站点id
	 */
	@Column(name="site_id",nullable = true,length = 36)
	private String siteId;
	
	/**
	 * 父级工单id
	 */
	@Column(name="parent_work_order_id",nullable = true,length = 36)
	private String parentWorkOrderId;
	
	/**
	 * 子工单
	 */
	@Column(name="childer_work_order_id",nullable = true,length = 36)
	private String childerWorkOrderId;
	
	/**
	 * 是否外委
	 */
	@Column(name="udisww",nullable = true,length = 1)
	private Boolean udisww=false;
	
	/**
	 * 预防性维护id
	 */
	@Column(name="maintenance_plan_num",nullable = true,length = 255)
	private String maintenancePlanNum;
	
	/**
	 * 提报人id
	 */
	@Column(name="report_id",nullable = true,length = 36)
	private String reportId;
	
	/**
	 * 提报日期
	 */
	@Column(name="report_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date reportDate;
	
	/**
	 * 提报说明
	 */
	@Column(name="report_description",nullable = true,length = 255)
	private String reportDescription;

	/**
	 * 档案编码
	 */
	@Column(name="archives_num",nullable = true,length = 255)
	private String archivesNum;

	/**
	 * 合同id
	 */
	@Column(name="contract_id",nullable = true,length = 36)
	private String contractId;

	/**
	 * 报修工单id
	 */
	@Column(name="repair_id",nullable = true,length = 36)
	private String repairId;
	
	//==================任务分派==============
	/**
	 * 是否委托执行
	 */
	@Column(name="entrust_execute",nullable = true,length = 1)
	private Boolean entrustExecute=false;
	
//	/**
//	 * 委托执行人 可能是多个 在数据库中以逗号进行分割
//	 */
//	@Column(name="entrust_execute_person",nullable = true,length = 255)
//	private String entrustExecutePerson;
	
	/**
	 * 执行班组
	 */
	@Column(name="execution_work_group",nullable = true,length = 36)
	private String executionWorkGroup;

//	/**
//	 * 执行人id
//	 */
//	@Column(name="executor_person_id",nullable = true,length = 36)
//	private String executorPersonId;
	
	/**
	 * 分派人id
	 */
	@Column(name="assign_person_id",nullable = true,length = 36)
	private String assignPersonId;
	
	/**
	 * 供应商id
	 */
	@Column(name="company_id",nullable = true,length = 36)
	private String companyId;
	
	/**
	 * 预计服务费用(元)
	 */
	@Column(name="expected_charge",nullable = true,length = 255)
	private String expectedCharge;
	
	/**
	 * 计划开始时间
	 */
	@Column(name="plan_start_date",nullable = true,length=0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date planStartDate;
	
	/**
	 * 计划完成时间
	 */
	@Column(name="plan_completion_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date planCompletionDate;
	
	/**
	 * 作业标准id
	 */
	@Column(name="job_standard_id",nullable = true,length = 36)
	private String jobStandardId;
	
	//==================执行汇报==============
//	/**
//	 * 实际执行人
//	 */
//	@Column(name="actual_executor_id",nullable = true,length = 36)
//	private String actualExecutorId;
	
	/**
	 * 实际执行班组
	 */
	@Column(name="actual_work_group",nullable = true,length = 255)
	private String actualWorkGroup;

	/**
	 * 实际执行负责人
	 */
	@Column(name="actual_executor_responsible_id",nullable = true,length = 36)
	private String actualExecutorResponsibleId;
	
	/**
	 * 实际开始时间
	 */
	@Column(name="actual_start_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date actualStartDate;
	
	/**
	 * 实际结束时间
	 */
	@Column(name="actual_end_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date actualEndDate;
	
	/**
	 * 故障总结
	 */
	@Column(name="failure_summarize",nullable = true,length = 255)
	private String failureSummarize;
	
	/**
	 * 挂起标识，是否挂起
	 */
	@Column(name="suspension",nullable = true,length = 1)
	private Boolean suspension=false;
	
	/**
	 * 挂起开始时间
	 */
	@Column(name="suspension_start_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date suspensionStartDate;
	
	/**
	 * 挂起结束时间
	 */
	@Column(name="suspension_end_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date suspensionEndDate;
	
	/**
	 * 挂起类型
	 */
	@Column(name="suspension_type",nullable = true,length = 255)
	private String suspensionType;
	
	/**
	 * 是否超时
	 */
	@Column(name="execute_whether_timeout",nullable = true,length = 1)
	private Boolean executeWhetherTimeout=false;
	
	/**
	 * 实际服务费用（外委时填此费用）
	 */
	@Column(name="actual_charge",nullable = true,length = 255)
	private String actualCharge;
	
	//==================验收确认==============
	/**
	 * 确认解决
	 */
	@Column(name="confirm",nullable = true,length = 1)
	private Boolean confirm=false;
	
	/**
	 * 验收时间
	 */
	@Column(name="acception_time",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date acceptionTime;
	
	/**
	 * 验收人id
	 */
	@Column(name="acceptor_id",nullable = true,length = 36)
	private String acceptorId;
    
    /**
	 * 验收说明
	 */
	@Column(name="acception_desc",nullable = true,length = 255)
	private String acceptionDesc;
	
	/**
	 * 工单总用时长（系统自动算出结果）--生成时间~关闭时间（分钟）
	 */
	@Column(name="work_order_total_duration",nullable = true,length = 20)
	private Long workOrderTotalDuration;
	
	/**
	 * 工单总工时（系统自动算出结果）--本次工时*设备数量（分钟）
	 */
	@Column(name="work_order_total_time",nullable = true,length = 255)
	private Double workOrderTotalTime;
	
	/**
	 * 单设备本次工时（系统自动算出结果）--实际执行步骤工时之和（分钟）
	 */
	@Column(name="single_asset_this_time",nullable = true,length = 255)
	private Double singleAssetThisTime;
	
	/**
	 * 单设备上次工时（系统自动算出结果）--该预防性维护计划上次生成工单的实际工时（分钟）
	 */
	@Column(name="single_asset_last_time",nullable = true,length = 255)
	private Double singleAssetLastTime;
	
	/**
	 * 单设备标准工时（系统自动算出结果）--作业标准步骤工时之和（分钟）
	 */
	@Column(name="single_asset_nomal_time",nullable = true,length = 255)
	private Double singleAssetNomalTime;
	
	//============================================
	/**
	 * 组织id
	 */
	@Column(name="org_id",nullable = true,length = 36)
	private String orgId;
	
	/**
	 * 流程实例ID
	 */
	@Column(name="process_instance_id",nullable = true,length = 255)
	private String processInstanceId;

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

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
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

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetDesc() {
		return assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public String getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getIncidentLevel() {
		return incidentLevel;
	}

	public void setIncidentLevel(String incidentLevel) {
		this.incidentLevel = incidentLevel;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getParentWorkOrderId() {
		return parentWorkOrderId;
	}

	public void setParentWorkOrderId(String parentWorkOrderId) {
		this.parentWorkOrderId = parentWorkOrderId;
	}

	public String getChilderWorkOrderId() {
		return childerWorkOrderId;
	}

	public void setChilderWorkOrderId(String childerWorkOrderId) {
		this.childerWorkOrderId = childerWorkOrderId;
	}

	public Boolean getUdisww() {
		return udisww;
	}

	public void setUdisww(Boolean udisww) {
		this.udisww = udisww;
	}

	public String getMaintenancePlanNum() {
		return maintenancePlanNum;
	}

	public void setMaintenancePlanNum(String maintenancePlanNum) {
		this.maintenancePlanNum = maintenancePlanNum;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getExpectedCharge() {
		return expectedCharge;
	}

	public void setExpectedCharge(String expectedCharge) {
		this.expectedCharge = expectedCharge;
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

	public String getJobStandardId() {
		return jobStandardId;
	}

	public void setJobStandardId(String jobStandardId) {
		this.jobStandardId = jobStandardId;
	}

	public String getActualWorkGroup() {
		return actualWorkGroup;
	}

	public void setActualWorkGroup(String actualWorkGroup) {
		this.actualWorkGroup = actualWorkGroup;
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

	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	public String getAcceptionDesc() {
		return acceptionDesc;
	}

	public void setAcceptionDesc(String acceptionDesc) {
		this.acceptionDesc = acceptionDesc;
	}

	public Long getWorkOrderTotalDuration() {
		return workOrderTotalDuration;
	}

	public void setWorkOrderTotalDuration(Long workOrderTotalDuration) {
		this.workOrderTotalDuration = workOrderTotalDuration;
	}

	public Double getWorkOrderTotalTime() {
		return workOrderTotalTime;
	}

	public void setWorkOrderTotalTime(Double workOrderTotalTime) {
		this.workOrderTotalTime = workOrderTotalTime;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getArchivesNum() {
		return archivesNum;
	}

	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}

	public String getExecutionWorkGroup() {
		return executionWorkGroup;
	}

	public void setExecutionWorkGroup(String executionWorkGroup) {
		this.executionWorkGroup = executionWorkGroup;
	}

	public String getActualExecutorResponsibleId() {
		return actualExecutorResponsibleId;
	}

	public void setActualExecutorResponsibleId(String actualExecutorResponsibleId) {
		this.actualExecutorResponsibleId = actualExecutorResponsibleId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrder{" +
				"workOrderNum='" + workOrderNum + '\'' +
				", description='" + description + '\'' +
				", workType='" + workType + '\'' +
				", projectType='" + projectType + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
				", assetId='" + assetId + '\'' +
				", assetDesc='" + assetDesc + '\'' +
				", classificationId='" + classificationId + '\'' +
				", locationId='" + locationId + '\'' +
				", incidentLevel='" + incidentLevel + '\'' +
				", siteId='" + siteId + '\'' +
				", parentWorkOrderId='" + parentWorkOrderId + '\'' +
				", childerWorkOrderId='" + childerWorkOrderId + '\'' +
				", udisww=" + udisww +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", reportId='" + reportId + '\'' +
				", reportDate=" + reportDate +
				", reportDescription='" + reportDescription + '\'' +
				", archivesNum='" + archivesNum + '\'' +
				", contractId='" + contractId + '\'' +
				", repairId='" + repairId + '\'' +
				", entrustExecute=" + entrustExecute +
				", executionWorkGroup='" + executionWorkGroup + '\'' +
				", assignPersonId='" + assignPersonId + '\'' +
				", companyId='" + companyId + '\'' +
				", expectedCharge='" + expectedCharge + '\'' +
				", planStartDate=" + planStartDate +
				", planCompletionDate=" + planCompletionDate +
				", jobStandardId='" + jobStandardId + '\'' +
				", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
				", actualWorkGroup='" + actualWorkGroup + '\'' +
				", actualStartDate=" + actualStartDate +
				", actualEndDate=" + actualEndDate +
				", failureSummarize='" + failureSummarize + '\'' +
				", suspension=" + suspension +
				", suspensionStartDate=" + suspensionStartDate +
				", suspensionEndDate=" + suspensionEndDate +
				", suspensionType='" + suspensionType + '\'' +
				", executeWhetherTimeout=" + executeWhetherTimeout +
				", actualCharge='" + actualCharge + '\'' +
				", confirm=" + confirm +
				", acceptionTime=" + acceptionTime +
				", acceptorId='" + acceptorId + '\'' +
				", acceptionDesc='" + acceptionDesc + '\'' +
				", workOrderTotalDuration=" + workOrderTotalDuration +
				", workOrderTotalTime=" + workOrderTotalTime +
				", singleAssetThisTime=" + singleAssetThisTime +
				", singleAssetLastTime=" + singleAssetLastTime +
				", singleAssetNomalTime=" + singleAssetNomalTime +
				", orgId='" + orgId + '\'' +
				", processInstanceId='" + processInstanceId + '\'' +
				'}';
	}
}
