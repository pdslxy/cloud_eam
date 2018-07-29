package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description 与标准作业计划关联的任务实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_job_standard_task")
public class MaintenanceJobStandardTask extends EnerbosBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 所属标准作业计划id
     */
    @Column(name = "job_standard_id")
    private String jobStandardId;


    /**
     * 任务序号
     */
    @Column(name = "task_sequence")
    private String taskSequence;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 标准操作持续时间
     */
    @Column(nullable = false,name = "task_duration")
    private Double taskDuration;

    /**
     * 质量标准
     */
    @Column(name = "quality_standard")
    private String qualityStandard;

    /**
     * 所属站点编码
     */
    @Column(name = "site_id")
    private String siteId;

    /**
     * 所属组织编码
     */
    @Column(name = "org_id")
    private String orgId;

	public String getJobStandardId() {
		return jobStandardId;
	}

	public void setJobStandardId(String jobStandardId) {
		this.jobStandardId = jobStandardId;
	}

	public String getTaskSequence() {
		return taskSequence;
	}

	public void setTaskSequence(String taskSequence) {
		this.taskSequence = taskSequence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTaskDuration() {
		return taskDuration;
	}

	public void setTaskDuration(Double taskDuration) {
		this.taskDuration = taskDuration;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
    public String toString() {
        return "MaintenanceJobStandardTask{" +
                "id=" + getId() +
                ", jobPlanId=" + jobStandardId +
                ", taskSequence=" + taskSequence +
                ", description='" + description + '\'' +
                ", taskDuration=" + taskDuration +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", qualityStandard='" + qualityStandard + '\'' +
                ", createUser='" + getCreateUser() + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                ", updateDate='" + getUpdateDate() + '\'' +
                '}';
    }
}
