package com.enerbos.cloud.eam.vo;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
 * @date 2017年06月07日
 * @Description 预防性维护Vo
 */
@ApiModel(value = "预防性维护", description = "预防性维护的实体对应的VO")
public class MaintenanceMaintenancePlanVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id(新增不用传值)")
	@Length(max = 36, message = "id不能超过36个字符")
	private String id;

	/**
	 * 预防性维护编码
	 */
	@ApiModelProperty(value = "预防性维护编码",required = true)
	@NotBlank(message = "预防性维护编码")
	private String maintenancePlanNum;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述",required = true)
	@NotBlank(message = "描述")
	private String description;

	/**
	 * 位置id
	 */
	@ApiModelProperty(value = "位置id")
	private String locationId;

	/**
	 * 位置编码
	 */
	@ApiModelProperty(value = "位置编码")
	private String locationNum;

	/**
	 * 位置描述
	 */
	@ApiModelProperty(value = "位置描述")
	private String locationDesc;

	/**
	 * 是否外委
	 */
	@ApiModelProperty(value = "是否外委",example = "false")
	private Boolean udisww=false;

	/**
	 * 关联的标准作业
	 */
	@ApiModelProperty(value = "关联的标准作业")
	private String jobStandardId;

	/**
	 * 关联的标准作业编码
	 */
	@ApiModelProperty(value = "关联的标准编码")
	private String jobStandardNum;

	/**
	 * 关联的标准作业描述
	 */
	@ApiModelProperty(value = "关联的标准作业描述")
	private String jobStandardDesc;

	/**
	 * 工程类型
	 */
	@ApiModelProperty(value = "工程类型",required = true)
	@NotBlank(message = "工程类型不能为空")
	private String projectType;

	/**
	 * 所属站点编码
	 */
	@ApiModelProperty(value = "所属站点编码")
	private String siteId;

	/**
	 * 所属站点编码
	 */
	@ApiModelProperty(value = "所属站点名称")
	private String siteName;

	/**
	 * 所属组织编码
	 */
	@ApiModelProperty(value = "所属组织编码")
	private String orgId;

	/**
	 * 状态
	 * DRAFT-草稿
	 * ACTIVE-活动
	 * INACTIVE-不活动
	 */
	@ApiModelProperty(value = "状态",required = true)
	@NotBlank(message = "状态不能为空")
	private String status;

	/**
	 * 状态日期
	 */
	@ApiModelProperty(value = "状态日期")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date statusDate;


	@ApiModelProperty(value = "合同id")
	private String contractId;

	@ApiModelProperty(value = "合同编码")
	private String contractNum;

	@ApiModelProperty(value = "合同名称")
	private String contractName;

	@ApiModelProperty(value = "合同施工单位")
	private String contractCompany;

//	/**
//	 * 提前时间(天)
//	 */
//	@ApiModelProperty(value = "提前时间(天)")
//	private String leadTime;
//
//	/**
//	 * 提前时间是否有效
//	 */
//	@ApiModelProperty(value = "提前时间是否有效")
//	private Boolean leadTimeActive;

	/**
	 * 计数器
	 * 从“第一个开始日期”以来，在预防性维护计划中生成的工单数。当您插入新的预防性维护计划记录时，计数器设置为“0”；
	 * 每次您在预防性维护计划中生成顶级工单时，计数器读数均会增加。如果要使用作业计划序列，那么在计数器增量之后选定该作业计划。
	 */
	@ApiModelProperty(value = "计数器",hidden = true)
	private Integer maintenancePlanCounter;

	/**
	 * 频率
	 */
	@ApiModelProperty(value = "频率",required = true)
	private Integer frequency;

	/**
	 * 频率单位
	 */
	@ApiModelProperty(value = "频率单位",required = true)
	@NotBlank(message = "频率单位不能为空")
	private String frequencyUnit;

	/**
	 * 估算的下一到期日,生成下一工单的日期
	 */
	@ApiModelProperty(value = "估算的下一到期日,生成下一工单的日期")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date nextDate;

	/**
	 * 上次开始日期
	 */
	@ApiModelProperty(value = "上次开始日期")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastStartDate;

	/**
	 * 上次完成日期
	 */
	@ApiModelProperty(value = "上次完成日期")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastEndDate;

	/**
	 * 延时间
	 */
	@ApiModelProperty(value = "延时间")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date extDate;

	/**
	 * 事件级别
	 */
	@ApiModelProperty(value = "事件级别")
	private String incidentLevel;

	/**
	 * 是否使用上次的工单开始信息计算下一到期频率
	 * 使用上一目标开始日期
	 */
	@ApiModelProperty(value = "是否使用上次的工单开始信息计算下一到期频率,使用上一目标开始日期")
	private Boolean useTargetDate;

//	/**
//	 * 是否使用基于测点的值生成工单
//	 */
//	@ApiModelProperty(value = "是否使用基于测点的值生成工单")
//	private Boolean maintenancePlanActMeter;

	/**
	 * 周的周几。逗号相隔
	 */
	@ApiModelProperty(value = "周几。逗号相隔，如1,2,3,4,5,6,7")
	private String weekFrequency;

	/**
	 * 工单类型
	 * 在此预防性维护计划中生成的工单分类或类型，默认保养工单，前端不显示
	 */
	@ApiModelProperty(value = "工单类型",hidden = true)
	private String workOrderType;

	/**
	 * 生成的工单状态,默认待分派状态,前端不显示
	 */
	@ApiModelProperty(value = "生成的工单状态,暂时默认到分派状态",hidden = true)
	private String workOrderStatus;

	/**
	 * 责任班组
	 */
	@ApiModelProperty(value = "责任班组")
	private String personliableWorkGroup;

	/**
	 * 责任人id
	 */
	@ApiModelProperty(value = "责任人id")
	private String personliableId;

	/**
	 * 责任人名称
	 */
	@ApiModelProperty(value = "责任人名称")
	private String personliableName;

	/**
	 * 分派人id
	 */
	@ApiModelProperty(value = "分派人id")
	private String assignPersonId;

	/**
	 * 分派人名称
	 */
	@ApiModelProperty(value = "分派人名称")
	private String assignPersonName;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人",required = true)
	@Length(max = 36, message = "不能超过36个字符")
	private String createUser;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人名")
	@Length(max = 36, message = "不能超过36个字符")
	private String createUserName;

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

	/**
	 *  来源ID
	 */
	@ApiModelProperty(value = "来源ID")
	private String srcId;

	/**
	 *  来源类型
	 */
	@ApiModelProperty(value = "来源类型")
	private String srcType;

	@ApiModelProperty(value = "有效时间列表，保存时只回传已修改数据")
	private List<MaintenanceMaintenancePlanActiveTimeVo> maintenancePlanActiveTimeVoList;

	@ApiModelProperty(value = "删除有效时间ID")
	private List<String> deleteMaintenancePlanActiveTimeVoList;

	@ApiModelProperty(value = "所选设备列表，只能添加和删除")
	private List assetList;

	@ApiModelProperty(value = "添加设备设施ID")
	private List<MaintenanceMaintenancePlanAssetVo> addAssetList;

	@ApiModelProperty(value = "删除设备设施ID")
	private List<String> deleteAssetList;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaintenancePlanNum() {
		return maintenancePlanNum;
	}

	public void setMaintenancePlanNum(String maintenancePlanNum) {
		this.maintenancePlanNum = maintenancePlanNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public Boolean getUdisww() {
		return udisww;
	}

	public void setUdisww(Boolean udisww) {
		this.udisww = udisww;
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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public Date getLastStartDate() {
		return lastStartDate;
	}

	public void setLastStartDate(Date lastStartDate) {
		this.lastStartDate = lastStartDate;
	}

	public Date getLastEndDate() {
		return lastEndDate;
	}

	public void setLastEndDate(Date lastEndDate) {
		this.lastEndDate = lastEndDate;
	}

	public Date getExtDate() {
		return extDate;
	}

	public void setExtDate(Date extDate) {
		this.extDate = extDate;
	}

	public String getIncidentLevel() {
		return incidentLevel;
	}

	public void setIncidentLevel(String incidentLevel) {
		this.incidentLevel = incidentLevel;
	}

	public String getWeekFrequency() {
		return weekFrequency;
	}

	public void setWeekFrequency(String weekFrequency) {
		this.weekFrequency = weekFrequency;
	}

	public String getPersonliableWorkGroup() {
		return personliableWorkGroup;
	}

	public void setPersonliableWorkGroup(String personliableWorkGroup) {
		this.personliableWorkGroup = personliableWorkGroup;
	}

	public String getPersonliableId() {
		return personliableId;
	}

	public void setPersonliableId(String personliableId) {
		this.personliableId = personliableId;
	}

	public String getPersonliableName() {
		return personliableName;
	}

	public void setPersonliableName(String personliableName) {
		this.personliableName = personliableName;
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

	public List<MaintenanceMaintenancePlanActiveTimeVo> getMaintenancePlanActiveTimeVoList() {
		return maintenancePlanActiveTimeVoList;
	}

	public void setMaintenancePlanActiveTimeVoList(List<MaintenanceMaintenancePlanActiveTimeVo> maintenancePlanActiveTimeVoList) {
		this.maintenancePlanActiveTimeVoList = maintenancePlanActiveTimeVoList;
	}

	public List<String> getDeleteMaintenancePlanActiveTimeVoList() {
		return deleteMaintenancePlanActiveTimeVoList;
	}

	public void setDeleteMaintenancePlanActiveTimeVoList(List<String> deleteMaintenancePlanActiveTimeVoList) {
		this.deleteMaintenancePlanActiveTimeVoList = deleteMaintenancePlanActiveTimeVoList;
	}

	public List getAssetList() {
		return assetList;
	}

	public void setAssetList(List assetList) {
		this.assetList = assetList;
	}

	public List<MaintenanceMaintenancePlanAssetVo> getAddAssetList() {
		return addAssetList;
	}

	public void setAddAssetList(List<MaintenanceMaintenancePlanAssetVo> addAssetList) {
		this.addAssetList = addAssetList;
	}

	public List<String> getDeleteAssetList() {
		return deleteAssetList;
	}

	public void setDeleteAssetList(List<String> deleteAssetList) {
		this.deleteAssetList = deleteAssetList;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getLocationNum() {
		return locationNum;
	}

	public void setLocationNum(String locationNum) {
		this.locationNum = locationNum;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getFrequencyUnit() {
		return frequencyUnit;
	}

	public void setFrequencyUnit(String frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
	}

	public String getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public Integer getMaintenancePlanCounter() {
		return maintenancePlanCounter;
	}

	public void setMaintenancePlanCounter(Integer maintenancePlanCounter) {
		this.maintenancePlanCounter = maintenancePlanCounter;
	}

	public Boolean getUseTargetDate() {
		return useTargetDate;
	}

	public void setUseTargetDate(Boolean useTargetDate) {
		this.useTargetDate = useTargetDate;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getSrcType() {
		return srcType;
	}

	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}

	@Override
	public String toString() {
		return "MaintenanceMaintenancePlanVo{" +
				"id='" + id + '\'' +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", description='" + description + '\'' +
				", locationId='" + locationId + '\'' +
				", locationNum='" + locationNum + '\'' +
				", locationDesc='" + locationDesc + '\'' +
				", udisww=" + udisww +
				", jobStandardId='" + jobStandardId + '\'' +
				", jobStandardNum='" + jobStandardNum + '\'' +
				", jobStandardDesc='" + jobStandardDesc + '\'' +
				", projectType='" + projectType + '\'' +
				", siteId='" + siteId + '\'' +
				", siteName='" + siteName + '\'' +
				", orgId='" + orgId + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
				", contractId='" + contractId + '\'' +
				", contractNum='" + contractNum + '\'' +
				", contractName='" + contractName + '\'' +
				", contractCompany='" + contractCompany + '\'' +
				", maintenancePlanCounter=" + maintenancePlanCounter +
				", frequency=" + frequency +
				", frequencyUnit='" + frequencyUnit + '\'' +
				", nextDate=" + nextDate +
				", lastStartDate=" + lastStartDate +
				", lastEndDate=" + lastEndDate +
				", extDate=" + extDate +
				", incidentLevel='" + incidentLevel + '\'' +
				", useTargetDate=" + useTargetDate +
				", weekFrequency='" + weekFrequency + '\'' +
				", workOrderType='" + workOrderType + '\'' +
				", workOrderStatus='" + workOrderStatus + '\'' +
				", personliableWorkGroup='" + personliableWorkGroup + '\'' +
				", personliableId='" + personliableId + '\'' +
				", personliableName='" + personliableName + '\'' +
				", assignPersonId='" + assignPersonId + '\'' +
				", assignPersonName='" + assignPersonName + '\'' +
				", createUser='" + createUser + '\'' +
				", createUserName='" + createUserName + '\'' +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", srcId='" + srcId + '\'' +
				", srcType='" + srcType + '\'' +
				", maintenancePlanActiveTimeVoList=" + maintenancePlanActiveTimeVoList +
				", deleteMaintenancePlanActiveTimeVoList=" + deleteMaintenancePlanActiveTimeVoList +
				", assetList=" + assetList +
				", addAssetList=" + addAssetList +
				", deleteAssetList=" + deleteAssetList +
				'}';
	}
}
