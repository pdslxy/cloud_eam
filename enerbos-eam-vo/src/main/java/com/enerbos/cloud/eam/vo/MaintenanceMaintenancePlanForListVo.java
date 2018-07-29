package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 预防性维护列表Vo
 */
@ApiModel(value = "预防性维护列表", description = "预防性维护列表VO")
public class MaintenanceMaintenancePlanForListVo implements Serializable {
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
	@ApiModelProperty(value = "预防性维护编码")
    private String maintenancePlanNum;

    /**
     * 描述
     */
	@ApiModelProperty(value = "描述")
    private String description;

    /**
     * 关联的标准作业
     */
	@ApiModelProperty(value = "关联的标准作业")
    private String jobStandardId;

	/**
	 * 关联的标准作业描述
	 */
	@ApiModelProperty(value = "关联的标准作业描述")
	private String jobStandardDesc;

    /**
     * 工程类型
     */
	@ApiModelProperty(value = "工程类型")
    private String projectType;

    /**
     * 所属站点编码
     */
	@ApiModelProperty(value = "所属站点编码")
    private String siteId;

	/**
	 * 所属站点名称
	 */
	@ApiModelProperty(value = "所属站点名称")
	private String siteName;

    /**
     * 状态
     * DRAFT-草稿
     * ACTIVE-活动
     * INACTIVE-不活动
     */
	@ApiModelProperty(value = "状态")
    private String status;

	@ApiModelProperty(value = "是否收藏 true：是")
	private Boolean collect;

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

	public String getJobStandardId() {
		return jobStandardId;
	}

	public void setJobStandardId(String jobStandardId) {
		this.jobStandardId = jobStandardId;
	}

	public String getJobStandardDesc() {
		return jobStandardDesc;
	}

	public void setJobStandardDesc(String jobStandardDesc) {
		this.jobStandardDesc = jobStandardDesc;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
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

	public String getStatusId() {
		return status;
	}

	public void setStatusId(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getCollect() {
		return collect;
	}

	public void setCollect(Boolean collect) {
		this.collect = collect;
	}

	@Override
	public String toString() {
		return "MaintenanceMaintenancePlanForListVo{" +
				"id='" + id + '\'' +
				", maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", description='" + description + '\'' +
				", jobStandardId='" + jobStandardId + '\'' +
				", jobStandardDesc='" + jobStandardDesc + '\'' +
				", projectType='" + projectType + '\'' +
				", siteId='" + siteId + '\'' +
				", siteName='" + siteName + '\'' +
				", status='" + status + '\'' +
				", collect=" + collect +
				'}';
	}
}
