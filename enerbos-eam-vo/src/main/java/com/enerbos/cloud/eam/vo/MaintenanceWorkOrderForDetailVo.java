package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
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
 * @date 2017年09月21日
 * @Description 维保工单-详情Vo
 */
@ApiModel(value = "维保工单-详情", description = "维保工单-详情VO")
public class MaintenanceWorkOrderForDetailVo implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
	 * id
	 */
	@ApiModelProperty(value = "id(新增不用传值)")
    private String id;

	@ApiModelProperty(value = "工单编号",required = true)
	@NotBlank(message = "工单编码不能为空")
    private String workOrderNum;

	@ApiModelProperty(value = "工单描述",required = true)
	@NotBlank(message = "工单描述不能为空")
    private String description;
	
	@ApiModelProperty(value = "工作类型",required = true)
	@NotBlank(message = "工单编码不能为空")
    private String workType;

	@ApiModelProperty(value = "工程类型",required = true)
	@NotBlank(message = "工程类型不能为空")
    private String projectType;

	@ApiModelProperty(value = "工程类型对应用户组名")
	private String projectGroupTypeName;

	@ApiModelProperty(value = "状态，新增接口默认是待提报，不允许修改",required = true)
	@NotBlank(message = "状态不能为空")
    private String status;

//	@ApiModelProperty(value = "状态日期（前端不展示）")
//    @Temporal(TemporalType.TIMESTAMP)
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date statusDate;
	
//	@ApiModelProperty(value = "设备ID")
//    private String assetId;
//
//	@ApiModelProperty(value = "设备描述")
//    private String assetDesc;

//	@ApiModelProperty(value = "所属分类")
//    private String classstructureId;

    @ApiModelProperty(value = "位置id")
    private String locationId;
    
    @ApiModelProperty(value = "位置编码")
    private String locationNum;
    
    @ApiModelProperty(value = "位置描述")
    private String locationDesc;

    @ApiModelProperty(value = "事件级别")
    private String incidentLevel;

    @ApiModelProperty(value = "所属站点编码")
    private String siteId;
    
    @ApiModelProperty(value = "所属站点名称")
    private String siteName;

    @ApiModelProperty(value = "父工单")
    private String parentWorkOrderId;

	@ApiModelProperty(value = "父工单编码")
	private String parentWorkOrderNum;

    @ApiModelProperty(value = "父工单描述")
    private String parentWorkOrderDesc;
    
//  @ApiModelProperty(value = "子工单")
//  private String childerWoId;

    @ApiModelProperty(value = "是否外委")
    private Boolean udisww;

    @ApiModelProperty(value = "预防维护编号")
    private String maintenancePlanNum;

    @ApiModelProperty(value = "提报人")
    private String reportId;
    
    @ApiModelProperty(value = "提报人名称")
    private String reportName;
    
    @ApiModelProperty(value = "提报人电话")
    private String reportMobile ;

    @ApiModelProperty(value = "提报日期")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty(value = "提报说明")
    private String reportDescription;

	@ApiModelProperty(value = "档案编码")
	private String archivesNum;

	@ApiModelProperty(value = "合同id")
	private String contractId;

	@ApiModelProperty(value = "合同编码")
	private String contractNum;

	@ApiModelProperty(value = "合同名称")
	private String contractName;

	@ApiModelProperty(value = "合同施工单位")
	private String contractCompany;

	@ApiModelProperty(value = "报修工单id")
	private String repairId;

	@ApiModelProperty(value = "报修工单编码")
	private String repairNum;

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

	//==============执行汇报=======================
	@ApiModelProperty(value = "实际执行负责人")
	private String actualExecutorResponsibleId;

	@ApiModelProperty(value = "实际执行负责人name")
	private String actualExecutorResponsibleName;

	@ApiModelProperty(value = "实际执行人")
	private String actualExecutorId;

	@ApiModelProperty(value = "实际执行人name")
	private String actualExecutorName;

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

	//============== 验收确认 =======================
	@ApiModelProperty(value = "确认解决")
	private Boolean confirm;

	@ApiModelProperty(value = "验收时间")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date acceptionTime;

	@ApiModelProperty(value = "验收人")
	private String acceptorId;

	@ApiModelProperty(value = "验收人名称")
	private String acceptorName;

	@ApiModelProperty(value = "验收说明")
	private String acceptionDesc;

	/**
	 * 工单总用时长（系统自动算出结果）--生成时间~关闭时间（分钟）
	 */
	@ApiModelProperty(value = "工单总用时长")
	private Long workOrderTotalDuration;

	/**
	 * 工单总工时（系统自动算出结果）--本次工时*设备数量（分钟）
	 */
	@ApiModelProperty(value = "工单总工时")
	private Double workOrderTotalTime;

	/**
	 * 单设备本次工时（系统自动算出结果）--实际执行步骤工时之和（分钟）
	 */
	@ApiModelProperty(value = "单设备本次工时")
	private Double singleAssetThistime;

	/**
	 * 单设备上次工时（系统自动算出结果）--该预防性维护计划上次生成工单的实际工时（分钟）
	 */
	@ApiModelProperty(value = "单设备上次工时")
	private Double singleAssetLasttime;

	/**
	 * 单设备标准工时（系统自动算出结果）--作业标准步骤工时之和（分钟）
	 */
	@ApiModelProperty(value = "单设备标准工时")
	private Double singleAssetNomaltime;

	//============================================
    @ApiModelProperty(value = "//所属组织")
    private String orgId;
    
    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;
    
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
    
    @ApiModelProperty(value = "所选设备列表，只能添加和删除")
    private List assetList;
    
    @ApiModelProperty(value = "添加设备设施ID")
    private List<MaintenanceWorkOrderAssetVo> addAssetList;
    
    @ApiModelProperty(value = "删除设备设施ID")
    private List<String> deleteAssetList;
    
    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList;

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

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getParentWorkOrderId() {
		return parentWorkOrderId;
	}

	public void setParentWorkOrderId(String parentWorkOrderId) {
		this.parentWorkOrderId = parentWorkOrderId;
	}

	public String getParentWorkOrderDesc() {
		return parentWorkOrderDesc;
	}

	public void setParentWorkOrderDesc(String parentWorkOrderDesc) {
		this.parentWorkOrderDesc = parentWorkOrderDesc;
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

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	public List getAssetList() {
		return assetList;
	}

	public void setAssetList(List assetList) {
		this.assetList = assetList;
	}

	public List<MaintenanceWorkOrderAssetVo> getAddAssetList() {
		return addAssetList;
	}

	public void setAddAssetList(List<MaintenanceWorkOrderAssetVo> addAssetList) {
		this.addAssetList = addAssetList;
	}

	public List<String> getDeleteAssetList() {
		return deleteAssetList;
	}

	public void setDeleteAssetList(List<String> deleteAssetList) {
		this.deleteAssetList = deleteAssetList;
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

	public String getArchivesNum() {
		return archivesNum;
	}

	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}

	public String getReportMobile() {
		return reportMobile;
	}

	public void setReportMobile(String reportMobile) {
		this.reportMobile = reportMobile;
	}

	public String getLocationNum() {
		return locationNum;
	}

	public void setLocationNum(String locationNum) {
		this.locationNum = locationNum;
	}

	public String getParentWorkOrderNum() {
		return parentWorkOrderNum;
	}

	public void setParentWorkOrderNum(String parentWorkOrderNum) {
		this.parentWorkOrderNum = parentWorkOrderNum;
	}

	public String getProjectGroupTypeName() {
		return projectGroupTypeName;
	}

	public void setProjectGroupTypeName(String projectGroupTypeName) {
		this.projectGroupTypeName = projectGroupTypeName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractCompany() {
		return contractCompany;
	}

	public void setContractCompany(String contractCompany) {
		this.contractCompany = contractCompany;
	}

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	public String getRepairNum() {
		return repairNum;
	}

	public void setRepairNum(String repairNum) {
		this.repairNum = repairNum;
	}

	public Boolean getEntrustExecute() {
		return entrustExecute;
	}

	public void setEntrustExecute(Boolean entrustExecute) {
		this.entrustExecute = entrustExecute;
	}

	public String getEntrustExecutePersonId() {
		return entrustExecutePersonId;
	}

	public void setEntrustExecutePersonId(String entrustExecutePersonId) {
		this.entrustExecutePersonId = entrustExecutePersonId;
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

	public String getAcceptorName() {
		return acceptorName;
	}

	public void setAcceptorName(String acceptorName) {
		this.acceptorName = acceptorName;
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

	public Double getSingleAssetThistime() {
		return singleAssetThistime;
	}

	public void setSingleAssetThistime(Double singleAssetThistime) {
		this.singleAssetThistime = singleAssetThistime;
	}

	public Double getSingleAssetLasttime() {
		return singleAssetLasttime;
	}

	public void setSingleAssetLasttime(Double singleAssetLasttime) {
		this.singleAssetLasttime = singleAssetLasttime;
	}

	public Double getSingleAssetNomaltime() {
		return singleAssetNomaltime;
	}

	public void setSingleAssetNomaltime(Double singleAssetNomaltime) {
		this.singleAssetNomaltime = singleAssetNomaltime;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForDetailVo{" +
				"id='" + id + '\'' +
				", workOrderNum='" + workOrderNum + '\'' +
				", description='" + description + '\'' +
				", workType='" + workType + '\'' +
				", projectType='" + projectType + '\'' +
				", projectGroupTypeName='" + projectGroupTypeName + '\'' +
				", status='" + status + '\'' +
//				", statusDate=" + statusDate +
				", locationId='" + locationId + '\'' +
				", locationNum='" + locationNum + '\'' +
				", locationDesc='" + locationDesc + '\'' +
				", incidentLevel='" + incidentLevel + '\'' +
				", siteId='" + siteId + '\'' +
				", siteName='" + siteName + '\'' +
				", parentWorkOrderId='" + parentWorkOrderId + '\'' +
				", parentWorkOrderNum='" + parentWorkOrderNum + '\'' +
				", parentWorkOrderDesc='" + parentWorkOrderDesc + '\'' +
				", udisww=" + udisww +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", reportId='" + reportId + '\'' +
				", reportName='" + reportName + '\'' +
				", reportMobile='" + reportMobile + '\'' +
				", reportDate=" + reportDate +
				", reportDescription='" + reportDescription + '\'' +
				", archivesNum='" + archivesNum + '\'' +
				", contractId='" + contractId + '\'' +
				", contractNum='" + contractNum + '\'' +
				", contractName='" + contractName + '\'' +
				", contractCompany='" + contractCompany + '\'' +
				", repairId='" + repairId + '\'' +
				", repairNum='" + repairNum + '\'' +
				", entrustExecute=" + entrustExecute +
				", entrustExecutePersonId='" + entrustExecutePersonId + '\'' +
				", entrustExecutePersonName='" + entrustExecutePersonName + '\'' +
				", executorPersonId='" + executorPersonId + '\'' +
				", executorPersonName='" + executorPersonName + '\'' +
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
				", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
				", actualExecutorResponsibleName='" + actualExecutorResponsibleName + '\'' +
				", actualExecutorId='" + actualExecutorId + '\'' +
				", actualExecutorName='" + actualExecutorName + '\'' +
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
				", confirm=" + confirm +
				", acceptionTime=" + acceptionTime +
				", acceptorId='" + acceptorId + '\'' +
				", acceptorName='" + acceptorName + '\'' +
				", acceptionDesc='" + acceptionDesc + '\'' +
				", workOrderTotalDuration=" + workOrderTotalDuration +
				", workOrderTotalTime=" + workOrderTotalTime +
				", singleAssetThistime=" + singleAssetThistime +
				", singleAssetLasttime=" + singleAssetLasttime +
				", singleAssetNomaltime=" + singleAssetNomaltime +
				", orgId='" + orgId + '\'' +
				", processInstanceId='" + processInstanceId + '\'' +
				", createUser='" + createUser + '\'' +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", assetList=" + assetList +
				", addAssetList=" + addAssetList +
				", deleteAssetList=" + deleteAssetList +
				", eamImpleRecordVoVoList=" + eamImpleRecordVoVoList +
				'}';
	}
}