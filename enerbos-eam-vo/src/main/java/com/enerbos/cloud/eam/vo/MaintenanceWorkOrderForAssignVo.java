package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

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
 * @Description 维保工单-任务分派Vo
 */
@ApiModel(value = "维保工单-任务分派", description = "维保工单-任务分派VO")
public class MaintenanceWorkOrderForAssignVo implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
	 * id
	 */
	@ApiModelProperty(value = "id",required = true)
	@NotBlank(message = "id不能为空")
    private String id;

	@ApiModelProperty(value = "状态",required = true)
    private String status;
	
	@ApiModelProperty(value = "状态日期（前端不展示）")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

	@ApiModelProperty(value = "预防维护编码",required = true)
	private String maintenancePlanNum;

	@ApiModelProperty(value = "提报人",required = true)
	private String reportId;

	@ApiModelProperty(value = "提报人名")
	private String reportName;

	@ApiModelProperty(value = "待接单人员名")
	private String availableTakingPersonName;

    //==================任务分派===================
    @ApiModelProperty(value = "是否委托执行")
    private Boolean entrustExecute=false;
    
    @ApiModelProperty(value = "委托执行人,可能是多个,在数据库中以逗号进行分割")
    private String entrustExecutePersonId;
    
    @ApiModelProperty(value = "委托执行人name,可能是多个,在数据库中以逗号进行分割")
    private String entrustExecutePersonName;

    @ApiModelProperty(value = "执行人id",required = true)
    private String executorPersonId;
    
    @ApiModelProperty(value = "执行人name")
    private String executorPersonName;

	@ApiModelProperty(value = "每个实际执行人执行班组名称，多个以逗号隔开")
	private String personWorkGroup;
    
    @ApiModelProperty(value = "执行班组")
    private String executionWorkGroup;
    
    @ApiModelProperty(value = "执行班组名词")
    private String executionWorkGroupName;
    
    @ApiModelProperty(value = "分派人",required = true)
    private String assignPersonId;
    
    @ApiModelProperty(value = "分派人name")
    private String assignPersonName;

    @ApiModelProperty(value = "供应商")
    private String companyId;
    
    @ApiModelProperty(value = "供应商名称")
    private String companyName;

    @ApiModelProperty(value = "预计服务费用(元)")
    private String expectedCharge;

    @ApiModelProperty(value = "计划开始时间",required = true)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planStartDate;

    @ApiModelProperty(value = "计划完成时间",required = true)
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompletionDate;

    @ApiModelProperty(value = "作业标准ID")
    private String jobStandardId;
    
    @ApiModelProperty(value = "作业标准编码")
    private String jobStandardNum;
    
    @ApiModelProperty(value = "作业标准描述")
    private String jobStandardDesc;

    //============================================
    @ApiModelProperty(value = "所属组织")
    private String orgId;
    
    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

	@ApiModelProperty(value = "实际执行人",hidden = true)
	private String actualExecutorId;

	@ApiModelProperty(value = "执行班组")
	private String actualWorkGroup;

	@ApiModelProperty(value = "执行班组名")
	private String actualWorkGroupName;
    
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
    
    @ApiModelProperty(value = "所需物料列表，保存时只回传已修改数据")
    private List<MaintenanceWorkOrderNeedItemVo> eamNeedItemVoList;
    
    @ApiModelProperty(value = "删除所需物料ID")
    private List<String> deleteEamNeedItemVoList;
    
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

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Boolean getEntrustExecute() {
		return entrustExecute;
	}

	public void setEntrustExecute(Boolean entrustExecute) {
		this.entrustExecute = entrustExecute;
	}

	public String getEntrustExecutePersonName() {
		return entrustExecutePersonName;
	}

	public void setEntrustExecutePersonName(String entrustExecutePersonName) {
		this.entrustExecutePersonName = entrustExecutePersonName;
	}

	public String getExecutorPersonId() {
		return executorPersonId;
	}

	public void setExecutorPersonId(String executorPersonId) {
		this.executorPersonId = executorPersonId;
	}

	public String getExecutorPersonName() {
		return executorPersonName;
	}

	public void setExecutorPersonName(String executorPersonName) {
		this.executorPersonName = executorPersonName;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public List<MaintenanceWorkOrderNeedItemVo> getEamNeedItemVoList() {
		return eamNeedItemVoList;
	}

	public void setEamNeedItemVoList(List<MaintenanceWorkOrderNeedItemVo> eamNeedItemVoList) {
		this.eamNeedItemVoList = eamNeedItemVoList;
	}

	public List<String> getDeleteEamNeedItemVoList() {
		return deleteEamNeedItemVoList;
	}

	public void setDeleteEamNeedItemVoList(List<String> deleteEamNeedItemVoList) {
		this.deleteEamNeedItemVoList = deleteEamNeedItemVoList;
	}

	public List<WorkFlowImpleRecordVo> getEamImpleRecordVoVoList() {
		return eamImpleRecordVoVoList;
	}

	public void setEamImpleRecordVoVoList(List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList) {
		this.eamImpleRecordVoVoList = eamImpleRecordVoVoList;
	}

	public String getJobStandardNum() {
		return jobStandardNum;
	}

	public void setJobStandardNum(String jobStandardNum) {
		this.jobStandardNum = jobStandardNum;
	}

	public String getJobStandardDesc() {
		return jobStandardDesc;
	}

	public void setJobStandardDesc(String jobStandardDesc) {
		this.jobStandardDesc = jobStandardDesc;
	}

	public String getExecutionWorkGroup() {
		return executionWorkGroup;
	}

	public void setExecutionWorkGroup(String executionWorkGroup) {
		this.executionWorkGroup = executionWorkGroup;
	}

	public String getExecutionWorkGroupName() {
		return executionWorkGroupName;
	}

	public void setExecutionWorkGroupName(String executionWorkGroupName) {
		this.executionWorkGroupName = executionWorkGroupName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getActualExecutorId() {
		return actualExecutorId;
	}

	public void setActualExecutorId(String actualExecutorId) {
		this.actualExecutorId = actualExecutorId;
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

	public String getAvailableTakingPersonName() {
		return availableTakingPersonName;
	}

	public void setAvailableTakingPersonName(String availableTakingPersonName) {
		this.availableTakingPersonName = availableTakingPersonName;
	}

	public String getEntrustExecutePersonId() {
		return entrustExecutePersonId;
	}

	public void setEntrustExecutePersonId(String entrustExecutePersonId) {
		this.entrustExecutePersonId = entrustExecutePersonId;
	}

	public String getPersonWorkGroup() {
		return personWorkGroup;
	}

	public void setPersonWorkGroup(String personWorkGroup) {
		this.personWorkGroup = personWorkGroup;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForAssignVo{" +
				"id='" + id + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
				", siteId='" + siteId + '\'' +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", reportId='" + reportId + '\'' +
				", reportName='" + reportName + '\'' +
				", availableTakingPersonName='" + availableTakingPersonName + '\'' +
				", entrustExecute=" + entrustExecute +
				", entrustExecutePersonId='" + entrustExecutePersonId + '\'' +
				", entrustExecutePersonName='" + entrustExecutePersonName + '\'' +
				", executorPersonId='" + executorPersonId + '\'' +
				", executorPersonName='" + executorPersonName + '\'' +
				", personWorkGroup='" + personWorkGroup + '\'' +
				", executionWorkGroup='" + executionWorkGroup + '\'' +
				", executionWorkGroupName='" + executionWorkGroupName + '\'' +
				", assignPersonId='" + assignPersonId + '\'' +
				", assignPersonName='" + assignPersonName + '\'' +
				", companyId='" + companyId + '\'' +
				", companyName='" + companyName + '\'' +
				", expectedCharge='" + expectedCharge + '\'' +
				", planStartDate=" + planStartDate +
				", planCompletionDate=" + planCompletionDate +
				", jobStandardId='" + jobStandardId + '\'' +
				", jobStandardNum='" + jobStandardNum + '\'' +
				", jobStandardDesc='" + jobStandardDesc + '\'' +
				", orgId='" + orgId + '\'' +
				", processInstanceId='" + processInstanceId + '\'' +
				", actualExecutorId='" + actualExecutorId + '\'' +
				", actualWorkGroup='" + actualWorkGroup + '\'' +
				", actualWorkGroupName='" + actualWorkGroupName + '\'' +
				", updateDate=" + updateDate +
				", eamOrderstepVoList=" + eamOrderstepVoList +
				", deleteEamOrderstepVoList=" + deleteEamOrderstepVoList +
				", eamNeedItemVoList=" + eamNeedItemVoList +
				", deleteEamNeedItemVoList=" + deleteEamNeedItemVoList +
				", eamImpleRecordVoVoList=" + eamImpleRecordVoVoList +
				'}';
	}
}