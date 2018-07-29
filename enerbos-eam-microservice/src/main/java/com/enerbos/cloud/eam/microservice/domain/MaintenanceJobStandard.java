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
 * @Description 作业标准实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_job_standard")
public class MaintenanceJobStandard extends EnerbosBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 标准作业计划编码
     */
    @Column(name = "job_standard_num",nullable = false, length = 36)
    private String jobStandardNum;

    /**
	 * 站点
	 */
	@Column(name = "site_id",nullable = true, length = 36)
	private String siteId;
    
    /**
	 * 组织
	 */
	@Column(name = "org_id",nullable = true, length = 36)
	private String orgId;

    /**
     * 状态
     * DRAFT-草稿
     * ACTIVE-活动的
     * INACTIVE-不活动的
     */
	@Column(name = "status",nullable = true, length = 36)
    private String status;

    /**
     * 状态日期
     */
	@Column(name="status_date",nullable = true,length = 0)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

	/**
	 * 状态备注
	 */
	@Column(name = "status_remark",nullable = true, length = 255)
	private String statusRemark;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 标准类型
     * DAOP 日常运行
     * TROU 故障维修
     * COWO 外委工作
     */
    @Column(name = "standard_type",nullable = true,length = 36)
    private String standardType;

    /**
     * 作业类型
     * operating 操作
     * warranty 保修
     * patrol 巡检
     * minorrepairs 小修
     * troubleshoot 故障维修
     */
    @Column(name = "job_type",nullable = true, length = 36)
    private String jobType;

     /*
     * * 所属分类
     */
    @Column(name = "classification_id",nullable = true, length = 36)
    private String classificationId;

    /**
     * 所需工具
     */
    @Column(name="need_tools",nullable = true,length = 255)
    private String needTools;

    /**
     *  持续时间
     */
    @Column(name="duration",nullable = true,length = 255)
    private Double duration;

    /**
     * 预防维护计划模板
     */
    @Column(name="maintenance_plan_model",nullable = true,length = 1000)
    private String maintenancePlanModel;

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	@Override
    public String toString() {
        return "MaintenanceJobStandard{" +
                "id=" + getId() +
                ", jobStandardNum='" + jobStandardNum + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", status=" + status +
                ", statusDate=" + statusDate +
				", statusRemark=" + statusRemark +
                ", description='" + description + '\'' +
                ", standardType=" + standardType +
                ", jobType=" + jobType +
                ", classificationId='" + classificationId + '\'' +
                ", needTools='" + needTools + '\'' +
                ", duration=" + duration +
                ", maintenancePlanModel=" + maintenancePlanModel +
                ", createUser='" + getCreateUser() + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                ", updateDate='" + getUpdateDate() + '\'' +
                '}';
    }
}
