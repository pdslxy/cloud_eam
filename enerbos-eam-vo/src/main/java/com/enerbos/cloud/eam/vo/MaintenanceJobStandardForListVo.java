package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM标准作业计划列表VO
 */
@ApiModel(value = "标准作业计划列表", description = "标准作业计划列表VO")
public class MaintenanceJobStandardForListVo implements Serializable {

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
	@ApiModelProperty(value = "标准作业计划编码")
    private String jobStandardNum;

    /**
	 * 站点
	 */
	@ApiModelProperty(value = "站点")
	private String siteId;

	/**
	 * 站点名称
	 */
	@ApiModelProperty(value = "站点名称")
	private String siteName;
    
    /**
	 * 组织
	 */
	@ApiModelProperty(value = "组织")
	private String orgId;

    /**
     * 状态
     */
	@ApiModelProperty(value = "状态")
    private String status;

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

	@ApiModelProperty(value = "是否收藏 true：是")
	private Boolean collect;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Boolean getCollect() {
		return collect;
	}

	public void setCollect(Boolean collect) {
		this.collect = collect;
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

	@Override
	public String toString() {
		return "MaintenanceJobStandardForListVo{" +
				"id='" + id + '\'' +
				", jobStandardNum='" + jobStandardNum + '\'' +
				", siteId='" + siteId + '\'' +
				", siteName='" + siteName + '\'' +
				", orgId='" + orgId + '\'' +
				", status='" + status + '\'' +
				", description='" + description + '\'' +
				", standardType='" + standardType + '\'' +
				", jobType='" + jobType + '\'' +
				", collect=" + collect +
				'}';
	}
}