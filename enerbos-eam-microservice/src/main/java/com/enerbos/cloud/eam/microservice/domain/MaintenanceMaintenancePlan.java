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
 * @Description 预防性维护实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_maintenance_plan")
public class MaintenanceMaintenancePlan extends EnerbosBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 预防性维护编码
     */
    @Column(name="maintenance_plan_num",nullable = false,length = 255)
    private String maintenancePlanNum;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 位置id
     */
    @Column(name = "location_id",nullable = true, length = 36)
    private String locationId;

    /**
     * 是否外委
     */
    @Column(name="udisww",nullable = true, length = 1)
    private Boolean udisww;

    /**
     * 关联的作业标准
     */
    @Column(name = "job_standard_id",nullable = true, length = 36)
    private String jobStandardId;

	/**
	 * 合同id
	 */
	@Column(name="contract_id",nullable = true,length = 36)
	private String contractId;

    /**
     * 工程类型
     */
    @Column(name = "project_type",nullable = true, length = 36)
    private String projectType;

    /**
     * 所属站点编码
     */
    @Column(name = "site_id",nullable = true, length = 36)
    private String siteId;

    /**
     * 所属组织编码
     */
    @Column(name = "org_id", nullable = true,length = 36)
    private String orgId;

    /**
     * 状态
     * DRAFT-草稿
     * ACTIVE-活动
     * INACTIVE-不活动
     */
    @Column(name = "status",nullable = true, length = 36)
    private String status;

    /**
     * 状态日期
     */
    @Column(name="status_date",nullable = true,length = 0)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statusDate;

	/**
	 * 状态备注
	 */
	@Column(name = "status_remark",nullable = true, length = 255)
	private String statusRemark;

//    /**
//     * 提前时间(天)
//     */
//    @Column(name="lead_time",nullable = true,length = 255)
//    private String leadTime;
//
//    /**
//     * 提前时间是否有效
//     */
//    @Column(name="lead_time_active",nullable = true,length = 1)
//    private Boolean leadTimeActive;

    /**
     * 计数器
     * 从“第一个开始日期”以来，在预防性维护计划中生成的工单数。当您插入新的预防性维护计划记录时，计数器设置为“0”；
     * 每次您在预防性维护计划中生成顶级工单时，计数器读数均会增加。如果要使用作业计划序列，那么在计数器增量之后选定该作业计划。
     */
    @Column(name="maintenance_plan_counter",nullable = true,length = 11)
    private Integer maintenancePlanCounter;

    /**
     * 频率
     */
    @Column(name="frequency",nullable = true,length = 11)
    private Integer frequency;

    /**
     * 频率单位
     * day      天
     * month    月
     * week     周
     * yeare    年
     */
    @Column(name = "frequency_unit",nullable = true, length = 36)
    private String frequencyUnit;

    /**
     * 估算的下一到期日,生成下一工单的日期
     */
    @Column(name="next_date",nullable = true,length = 0)
    @Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date nextDate;

    /**
     * 上次开始日期
     */
    @Column(name="last_start_date",nullable = true,length = 0)
    @Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastStartDate;

    /**
     * 上次完成日期
     */
    @Column(name="last_end_date",nullable = true,length = 0)
    @Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastEndDate;

    /**
     * 延时间
     */
    @Column(name="ext_date",nullable = true,length = 0)
    @Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date extDate;

	/**
	 * 事件级别
	 */
	@Column(name="incident_level",nullable = true,length = 255)
	private String incidentLevel;

    /**
     * 是否使用上次的工单开始信息计算下一到期频率
     * 使用上一目标开始日期
     */
    @Column(name="use_target_date",nullable = true,length = 1)
    private Boolean useTargetDate;

//    /**
//     * 是否使用基于测点的值生成工单
//     */
//    @Column(name="maintenance_plan_act_meter",nullable = true,length = 1)
//    private Boolean maintenancePlanActMeter;

	/**
     * 周的周几。逗号相隔,1,2,3,4,5,6,7
     */
    @Column(name="week_frequency",nullable = true,length = 255)
    private String weekFrequency;

    /**
     * 工单类型
     * 在此预防性维护计划中生成的工单分类或类型
     */
    @Column(name = "work_order_type", nullable = true,length = 36)
    private String workOrderType;

    /**
     * 生成的工单状态,暂时默认到分派状态
     */
    @Column(name = "work_order_status", nullable = true,length = 36)
    private String workOrderStatus;

    /**
     * 责任班组
     */
    @Column(name = "personliable_work_group", nullable = true,length = 36)
    private String personliableWorkGroup;

    /**
     * 责任人id
     */
    @Column(name = "personliable_id", nullable = true,length = 36)
    private String personliableId;

	/**
	 * 分派人id
	 */
	@Column(name="assign_person_id",nullable = true,length = 36)
	private String assignPersonId;

	/**
	 *  来源ID
	 */
	@Column(name = "src_Id", nullable = true, length = 255)
	private String srcId;

	/**
	 *  来源类型
	 */
	@Column(name = "src_Type", nullable = true, length = 255)
	private String srcType;

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public Integer getMaintenancePlanCounter() {
		return maintenancePlanCounter;
	}

	public void setMaintenancePlanCounter(Integer maintenancePlanCounter) {
		this.maintenancePlanCounter = maintenancePlanCounter;
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

	public String getAssignPersonId() {
		return assignPersonId;
	}

	public void setAssignPersonId(String assignPersonId) {
		this.assignPersonId = assignPersonId;
	}

	public String getWeekFrequency() {
		return weekFrequency;
	}

	public void setWeekFrequency(String weekFrequency) {
		this.weekFrequency = weekFrequency;
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

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public Boolean getUseTargetDate() {
		return useTargetDate;
	}

	public void setUseTargetDate(Boolean useTargetDate) {
		this.useTargetDate = useTargetDate;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
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
		return "MaintenanceMaintenancePlan{" +
				"maintenancePlanNum='" + maintenancePlanNum + '\'' +
				", description='" + description + '\'' +
				", locationId='" + locationId + '\'' +
				", udisww=" + udisww +
				", jobStandardId='" + jobStandardId + '\'' +
				", contractId='" + contractId + '\'' +
				", projectType='" + projectType + '\'' +
				", siteId='" + siteId + '\'' +
				", orgId='" + orgId + '\'' +
				", status='" + status + '\'' +
				", statusDate=" + statusDate +
				", statusRemark='" + statusRemark + '\'' +
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
				", assignPersonId='" + assignPersonId + '\'' +
				", srcId='" + srcId + '\'' +
				", srcType='" + srcType + '\'' +
				'}';
	}
}
