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
 * @date 2017年07月07日
 * @Description 维保工单-工单提报Vo
 */
@ApiModel(value = "维保工单-工单提报", description = "维保工单-工单提报VO")
public class MaintenanceWorkOrderForCommitVo implements Serializable {

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

	@ApiModelProperty(value = "状态日期（前端不展示）")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;
	
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

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
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

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForCommitVo{" +
				"id='" + id + '\'' +
				", workOrderNum='" + workOrderNum + '\'' +
				", description='" + description + '\'' +
				", workType='" + workType + '\'' +
				", projectType='" + projectType + '\'' +
				", projectGroupTypeName='" + projectGroupTypeName + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
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