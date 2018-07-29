package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-10 20:39
 * @Description 报修工单实体
 */
@Entity
@Table(name = "eam_repair_order")
public class RepairOrder extends EnerbosBaseEntity implements Serializable {

    public RepairOrder() {}

    /**
     *  状态
     */
    @Column(name = "work_order_status", nullable = true, length = 255)
    private String workOrderStatus;

    /**
     *  状态更新时间
     */
    @Column(name="work_order_status_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date workOrderStatusDate;

    //=================== 工单提报 ==================
    /**
     *  工单编号
     */
    @Column(name="work_order_num", nullable = true, length = 255)
    private String workOrderNum;

    /**
     *  报修描述
     */
    @Column(name = "description", nullable = true, length = 2000)
    private String description;

    /**
     *  工单来源
     */
    @Column(name = "work_order_source", nullable = true, length = 255)
    private String workOrderSource;

    /**
     *  工程类型
     */
    @Column(name = "project_type", nullable = true, length = 255)
    private String projectType;

    /**
     *  事件性质
     */
    @Column(name = "incident_nature", nullable = true, length = 255)
    private String incidentNature;

    /**
     *  事件级别
     */
    @Column(name = "incident_level", nullable = true, length = 255)
    private String incidentLevel;

    /**
     *  报修部门
     */
    @Column(name = "repair_dept", nullable = true, length = 255)
    private String repairDept;

    /**
     *  报修人
     */
    @Column(name = "repair_person", nullable = true, length = 255)
    private String repairPerson;

    /**
     *  报修人电话
     */
    @Column(name = "repair_person_tel", nullable = true, length = 255)
    private String repairPersonTel;

    /**
     * 是否提报人派单
     */
    @Column(name = "report_assign_flag", nullable = true)
    private Boolean reportAssignFlag = false;

    /**
     *  提报人
     */
    @Column(name = "report_person_id", nullable = true, length = 36)
    private String reportPersonId;

    /**
     *  提报人电话
     */
    @Column(name = "report_person_tel", nullable = true, length = 50)
    private String reportPersonTel;

    /**
     *  提报时间
     */
    @Column(name="report_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 提报说明(备注)
     */
    @Column(name = "report_description", nullable = true, length = 500)
    private String reportDescription;

    //=================== 任务分派 ==================
    /**
     * 分派人
     */
    @Column(name = "dispatch_person_id", nullable = true, length = 36)
    private String dispatchPersonId;

    /**
     * 响应时间
     */
    @Column(name = "dispatch_time", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dispatchTime;

    /**
     * 是否委托执行
     */
    @Column(name = "entrust_execute", nullable = true)
    private Boolean entrustExecute = false;
    //=================== 接单 ==================
    /**
     *  接单人
     */
    @Column(name = "receive_person_id", nullable = true, length = 36)
    private String receivePersonId;
    //=================== 执行汇报 ==================
    /**
     * 完成时间
     */
    @Column(name="completion_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completionTime;

    /**
     *  工时耗时(单位：分钟)
     */
    @Column(name = "consume_hours", nullable = true)
    private Double consumeHours;

    /**
     *  是否挂起
     */
    @Column(name="suspension", nullable = true)
    private Boolean suspension = false;

    /**
     *  挂起类型
     */
    @Column(name = "suspension_type", nullable = true, length = 255)
    private String suspensionType;

    /**
     *  情况说明
     */
    @Column(name = "suspension_cause", nullable = true, length = 255)
    private String suspensionCause;

    /**
     *  执行是否超时
     */
    @Column(name = "execute_timeout", nullable = true)
    private Boolean executeTimeout = false;

    /**
     *  工单汇报人
     */
    @Column(name = "order_report_person_id", nullable = true, length = 36)
    private String orderReportPersonId;

    //=================== 验收确认 ==================
    /**
     *  验收时间
     */
    @Column(name="accept_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptTime;

    /**
     *  确认解决
     */
    @Column(name="confirm", nullable = true)
    private Boolean confirm;

    /**
     *  验收说明
     */
    @Column(name = "accept_description", nullable = true, length = 500)
    private String acceptDescription;

    /**
     *  关联维保工单ID
     */
    @Column(name = "maintenance_work_order_id", unique = true, nullable = true, length = 36)
    private String maintenanceWorkOrderId;

    /**
     *  关联维保工单编码
     */
    @Column(name = "maintenance_work_order_num", nullable = true, length = 255)
    private String maintenanceWorkOrderNum;
    //=================== 验收待确认 ==================
    /**
     *  维修质量
     */
    @Column(name = "maintenance_quality", nullable = true, length = 255)
    private String maintenanceQuality;

    /**
     *  维修态度
     */
    @Column(name = "maintenance_attitude", nullable = true, length = 255)
    private String maintenanceAttitude;

    //=================== 其它 ==================
    /**
     *  所属组织
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;

    /**
     *  所属站点
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    /**
     *  工号
     */
    @Column(name = "job_number", nullable = true, length = 255)
    private String jobNumber;


    /**
     *  流程ID
     */
    @Column(name = "process_instance_id", nullable = true, length = 255)
    private String processInstanceId;

    /**
     *  关联设备ID
     */
    @Column(name = "ref_asset_id", nullable = true, length = 32)
    private String refAssetId;

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkOrderSource() {
        return workOrderSource;
    }

    public void setWorkOrderSource(String workOrderSource) {
        this.workOrderSource = workOrderSource;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getIncidentNature() {
        return incidentNature;
    }

    public void setIncidentNature(String incidentNature) {
        this.incidentNature = incidentNature;
    }

    public String getIncidentLevel() {
        return incidentLevel;
    }

    public void setIncidentLevel(String incidentLevel) {
        this.incidentLevel = incidentLevel;
    }

    public String getRepairDept() {
        return repairDept;
    }

    public void setRepairDept(String repairDept) {
        this.repairDept = repairDept;
    }

    public String getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(String repairPerson) {
        this.repairPerson = repairPerson;
    }

    public String getRepairPersonTel() {
        return repairPersonTel;
    }

    public void setRepairPersonTel(String repairPersonTel) {
        this.repairPersonTel = repairPersonTel;
    }

    public Date getWorkOrderStatusDate() {
        return workOrderStatusDate;
    }

    public void setWorkOrderStatusDate(Date workOrderStatusDate) {
        this.workOrderStatusDate = workOrderStatusDate;
    }

    public Boolean getReportAssignFlag() {
        return reportAssignFlag;
    }

    public void setReportAssignFlag(Boolean reportAssignFlag) {
        this.reportAssignFlag = reportAssignFlag;
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getReportPersonTel() {
        return reportPersonTel;
    }

    public void setReportPersonTel(String reportPersonTel) {
        this.reportPersonTel = reportPersonTel;
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

    public String getDispatchPersonId() {
        return dispatchPersonId;
    }

    public void setDispatchPersonId(String dispatchPersonId) {
        this.dispatchPersonId = dispatchPersonId;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Boolean getEntrustExecute() {
        return entrustExecute;
    }

    public void setEntrustExecute(Boolean entrustExecute) {
        this.entrustExecute = entrustExecute;
    }

    public String getReceivePersonId() {
        return receivePersonId;
    }

    public void setReceivePersonId(String receivePersonId) {
        this.receivePersonId = receivePersonId;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public Double getConsumeHours() {
        return consumeHours;
    }

    public void setConsumeHours(Double consumeHours) {
        this.consumeHours = consumeHours;
    }

    public Boolean getSuspension() {
        return suspension;
    }

    public void setSuspension(Boolean suspension) {
        this.suspension = suspension;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(String suspensionType) {
        this.suspensionType = suspensionType;
    }

    public String getSuspensionCause() {
        return suspensionCause;
    }

    public void setSuspensionCause(String suspensionCause) {
        this.suspensionCause = suspensionCause;
    }

    public Boolean getExecuteTimeout() {
        return executeTimeout;
    }

    public void setExecuteTimeout(Boolean executeTimeout) {
        this.executeTimeout = executeTimeout;
    }

    public String getOrderReportPersonId() {
        return orderReportPersonId;
    }

    public void setOrderReportPersonId(String orderReportPersonId) {
        this.orderReportPersonId = orderReportPersonId;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public String getAcceptDescription() {
        return acceptDescription;
    }

    public void setAcceptDescription(String acceptDescription) {
        this.acceptDescription = acceptDescription;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMaintenanceQuality() {
        return maintenanceQuality;
    }

    public void setMaintenanceQuality(String maintenanceQuality) {
        this.maintenanceQuality = maintenanceQuality;
    }

    public String getMaintenanceAttitude() {
        return maintenanceAttitude;
    }

    public void setMaintenanceAttitude(String maintenanceAttitude) {
        this.maintenanceAttitude = maintenanceAttitude;
    }

    public String getMaintenanceWorkOrderId() {
        return maintenanceWorkOrderId;
    }

    public void setMaintenanceWorkOrderId(String maintenanceWorkOrderId) {
        this.maintenanceWorkOrderId = maintenanceWorkOrderId;
    }

    public String getMaintenanceWorkOrderNum() {
        return maintenanceWorkOrderNum;
    }

    public void setMaintenanceWorkOrderNum(String maintenanceWorkOrderNum) {
        this.maintenanceWorkOrderNum = maintenanceWorkOrderNum;
    }

    public String getRefAssetId() {
        return refAssetId;
    }

    public void setRefAssetId(String refAssetId) {
        this.refAssetId = refAssetId;
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "workOrderStatus='" + workOrderStatus + '\'' +
                ", workOrderStatusDate=" + workOrderStatusDate +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", workOrderSource='" + workOrderSource + '\'' +
                ", projectType='" + projectType + '\'' +
                ", incidentNature='" + incidentNature + '\'' +
                ", incidentLevel='" + incidentLevel + '\'' +
                ", repairDept='" + repairDept + '\'' +
                ", repairPerson='" + repairPerson + '\'' +
                ", repairPersonTel='" + repairPersonTel + '\'' +
                ", reportAssignFlag=" + reportAssignFlag +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", reportPersonTel='" + reportPersonTel + '\'' +
                ", reportDate=" + reportDate +
                ", reportDescription='" + reportDescription + '\'' +
                ", dispatchPersonId='" + dispatchPersonId + '\'' +
                ", dispatchTime=" + dispatchTime +
                ", entrustExecute=" + entrustExecute +
                ", receivePersonId='" + receivePersonId + '\'' +
                ", completionTime=" + completionTime +
                ", consumeHours=" + consumeHours +
                ", suspension=" + suspension +
                ", suspensionType='" + suspensionType + '\'' +
                ", suspensionCause='" + suspensionCause + '\'' +
                ", executeTimeout=" + executeTimeout +
                ", orderReportPersonId='" + orderReportPersonId + '\'' +
                ", acceptTime=" + acceptTime +
                ", confirm=" + confirm +
                ", acceptDescription='" + acceptDescription + '\'' +
                ", maintenanceWorkOrderId='" + maintenanceWorkOrderId + '\'' +
                ", maintenanceWorkOrderNum='" + maintenanceWorkOrderNum + '\'' +
                ", maintenanceQuality='" + maintenanceQuality + '\'' +
                ", maintenanceAttitude='" + maintenanceAttitude + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", refAssetId='" + refAssetId + '\'' +
                "} " + super.toString();
    }
}
