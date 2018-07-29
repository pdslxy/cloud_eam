package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM标准作业计划VO
 */
@ApiModel(value = "标准作业计划", description = "标准作业计划的实体对应的VO")
public class MaintenanceJobStandardVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id(新增不用传值)")
	@Length(max = 36, message = "id不能超过36个字符")
    private String id;

	/**
     * 标准作业计划编码
     */
	@ApiModelProperty(value = "标准作业计划编码",required = true)
	@NotBlank(message = "标准作业计划编码不能为空")
    private String jobStandardNum;

    /**
	 * 站点
	 */
	@ApiModelProperty(value = "站点")
	private String siteId;

	/**
	 * 站点
	 */
	@ApiModelProperty(value = "站点")
	private String siteName;
    
    /**
	 * 组织
	 */
	@ApiModelProperty(value = "组织")
	private String orgId;

	/**
	 * 组织
	 */
	@ApiModelProperty(value = "组织")
	private String orgName;

    /**
     * 状态
     */
	@ApiModelProperty(value = "状态",required = true)
	@NotBlank(message = "状态不能为空")
    private String status;

    /**
     * 状态日期
     */
	@ApiModelProperty(value = "状态日期")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 描述
     */
	@ApiModelProperty(value = "描述")
    private String description;

    /**
     * 标准类型
     */
	@ApiModelProperty(value = "标准类型")
    private String standardType;

    /**
	 * 作业类型
	 */
	@ApiModelProperty(value = "作业类型")
	private String jobType;

    /**
	 * 所属分类
	 */
	@ApiModelProperty(value = "所属分类")
	private String classificationId;

	/**
	 * 所属分类编码
	 */
	@ApiModelProperty(value = "所属编码")
	private String classificationNum;

	/**
	 * 所属分类描述
	 */
	@ApiModelProperty(value = "所属分类描述")
	private String classificationName;

    /**
     * 所需工具
     */
    @ApiModelProperty(value = "所需工具")
    private String needTools;

    /**
     *  持续时间
     */
    @ApiModelProperty(value = "持续时间")
    private Double duration;

    /**
     * 预防维护计划模板
     */
    @ApiModelProperty(value = "预防维护计划模板")
    private String maintenancePlanModel;
    
    /**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人",required = true)
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

	@ApiModelProperty(value = "任务列表，保存时只回传已修改数据")
	private List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList;

	@ApiModelProperty(value = "删除任务ID")
	private List<String> deleteMaintenanceJobStandardTaskVoList;

	@ApiModelProperty(value = "物料列表，保存时只回传已修改数据")
	private List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList;

	@ApiModelProperty(value = "删除物料ID")
	private List<String> deleteMaintenanceJobStandardItemVoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobStandardNum() {
		return jobStandardNum;
	}

	public void setJobStandardNum(String jobStandardNum) {
		this.jobStandardNum = jobStandardNum;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStandardType() {
		return standardType;
	}

	public void setStandardType(String standardType) {
		this.standardType = standardType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public String getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}

	public String getNeedTools() {
		return needTools;
	}

	public void setNeedTools(String needTools) {
		this.needTools = needTools;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public String getMaintenancePlanModel() {
		return maintenancePlanModel;
	}

	public void setMaintenancePlanModel(String maintenancePlanModel) {
		this.maintenancePlanModel = maintenancePlanModel;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<MaintenanceJobStandardTaskVo> getMaintenanceJobStandardTaskVoList() {
		return maintenanceJobStandardTaskVoList;
	}

	public void setMaintenanceJobStandardTaskVoList(List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList) {
		this.maintenanceJobStandardTaskVoList = maintenanceJobStandardTaskVoList;
	}

	public List<String> getDeleteMaintenanceJobStandardTaskVoList() {
		return deleteMaintenanceJobStandardTaskVoList;
	}

	public void setDeleteMaintenanceJobStandardTaskVoList(List<String> deleteMaintenanceJobStandardTaskVoList) {
		this.deleteMaintenanceJobStandardTaskVoList = deleteMaintenanceJobStandardTaskVoList;
	}

	public List<MaintenanceJobStandardItemVo> getMaintenanceJobStandardItemVoList() {
		return maintenanceJobStandardItemVoList;
	}

	public void setMaintenanceJobStandardItemVoList(List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList) {
		this.maintenanceJobStandardItemVoList = maintenanceJobStandardItemVoList;
	}

	public List<String> getDeleteMaintenanceJobStandardItemVoList() {
		return deleteMaintenanceJobStandardItemVoList;
	}

	public void setDeleteMaintenanceJobStandardItemVoList(List<String> deleteMaintenanceJobStandardItemVoList) {
		this.deleteMaintenanceJobStandardItemVoList = deleteMaintenanceJobStandardItemVoList;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getClassificationNum() {
		return classificationNum;
	}

	public void setClassificationNum(String classificationNum) {
		this.classificationNum = classificationNum;
	}

	@Override
	public String toString() {
		return "MaintenanceJobStandardVo{" +
				"id='" + id + '\'' +
				", jobStandardNum='" + jobStandardNum + '\'' +
				", siteId='" + siteId + '\'' +
				", siteName='" + siteName + '\'' +
				", orgId='" + orgId + '\'' +
				", orgName='" + orgName + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
				", description='" + description + '\'' +
				", standardType='" + standardType + '\'' +
				", jobType='" + jobType + '\'' +
				", classificationId='" + classificationId + '\'' +
				", classificationNum='" + classificationNum + '\'' +
				", classificationName='" + classificationName + '\'' +
				", needTools='" + needTools + '\'' +
				", duration=" + duration +
				", maintenancePlanModel='" + maintenancePlanModel + '\'' +
				", createUser='" + createUser + '\'' +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", maintenanceJobStandardTaskVoList=" + maintenanceJobStandardTaskVoList +
				", deleteMaintenanceJobStandardTaskVoList=" + deleteMaintenanceJobStandardTaskVoList +
				", maintenanceJobStandardItemVoList=" + maintenanceJobStandardItemVoList +
				", deleteMaintenanceJobStandardItemVoList=" + deleteMaintenanceJobStandardItemVoList +
				'}';
	}
}