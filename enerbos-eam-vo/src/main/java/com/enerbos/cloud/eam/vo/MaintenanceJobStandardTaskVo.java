package com.enerbos.cloud.eam.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

 /**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 与标准作业计划关联的任务VO
 */
@ApiModel(value = "与标准作业计划关联的任务VO", description = "与标准作业计划关联的任务的实体对应的VO")
public class MaintenanceJobStandardTaskVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@ApiModelProperty(value = "(新增不用传值)")
	@Length(max = 36, message = "不能超过36个字符")
	private String id;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 255, message = "不能超过255个字符")
	private String description;
	/**
	 * 标准作业计划id
	 */
	@ApiModelProperty(value = "标准作业计划id")
	@Length(max = 255, message = "不能超过255个字符")
	private String jobStandardId;
	/**
	 * 组织
	 */
	@ApiModelProperty(value = "组织")
	@Length(max = 255, message = "不能超过255个字符")
	private String orgId;
	
	/**
	 * 站点
	 */
	@ApiModelProperty(value = "站点")
	@Length(max = 255, message = "不能超过255个字符")
	private String siteId;
	
	/**
	 * 质量标准
	 */
	@ApiModelProperty(value = "质量标准")
	@Length(max = 255, message = "不能超过255个字符")
	private String qualityStandard;
	
	/**
	 * 标准操作持续时间
	 */
	@ApiModelProperty(value = "标准操作持续时间")
	private Double taskDuration;
	/**
	 * 任务序号
	 */
	@ApiModelProperty(value = "任务序号")
	@Length(max = 255, message = "不能超过255个字符")
	private String taskSequence;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getQualityStandard() {
		return qualityStandard;
	}

	public void setQualityStandard(String qualityStandard) {
		this.qualityStandard = qualityStandard;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Double getTaskDuration() {
		return taskDuration;
	}

	public void setTaskDuration(Double taskDuration) {
		this.taskDuration = taskDuration;
	}

	public String getTaskSequence() {
		return taskSequence;
	}

	public void setTaskSequence(String taskSequence) {
		this.taskSequence = taskSequence;
	}
	
	public String getJobStandardId() {
		return jobStandardId;
	}

	public void setJobStandardId(String jobStandardId) {
		this.jobStandardId = jobStandardId;
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

	@Override
    public String toString() {
        return "MaintenanceJobStandardTaskVo{" +
                "id=" + id +
                ", jobStandardId=" + jobStandardId +
                ", taskSequence=" + taskSequence +
                ", description='" + description + '\'' +
                ", taskDuration=" + taskDuration +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", qualityStandard='" + qualityStandard + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
