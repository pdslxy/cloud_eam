package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 消缺工单
 */
@Entity
@Table(name = "eam_defect_order")
public class DefectOrder extends EnerbosBaseEntity implements Serializable {
    private static final long serialVersionUID = 3894256874881718604L;

    /**
     * 消缺工单编号
     */
    @Column(name="defect_order_num",nullable = false,length = 255)
    private String defectOrderNum;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 状态
     */
    @Column(name="status",nullable = true,length = 36)
    private String status;

    /**
     * 状态日期
     */
    @Column(name="status_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 工程类型
     */
    @Column(name="project_type",nullable = true,length = 36)
    private String projectType;

    @Column(name="reporter_assign_flag",nullable = true,length = 1)
    private Boolean reporterAssignFlag = false;//是否提报人派单

    /**
     * 事件级别
     */
    @Column(name="incident_level",nullable = true,length = 255)
    private String incidentLevel;

    /**
     * 关联的缺陷单
     */
    @Column(name="defect_document_id",nullable = true,length = 36)
    private String defectDocumentId;

    /**
     * 站点id
     */
    @Column(name="site_id",nullable = true,length = 36)
    private String siteId;

    /**
     * 提报人id
     */
    @Column(name="report_id",nullable = true,length = 36)
    private String reportId;

    /**
     * 提报日期
     */
    @Column(name="report_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 说明
     */
    @Column(name="explain_desc",nullable = true,length = 255)
    private String explainDesc;
    //==================任务分派==============
    /**
     * 是否委托执行
     */
    @Column(name="entrust_execute",nullable = true,length = 1)
    private Boolean entrustExecute=false;

    /**
     * 执行班组
     */
    @Column(name="execution_work_group",nullable = true,length = 36)
    private String executionWorkGroup;

    /**
     * 分派人id
     */
    @Column(name="assign_person_id",nullable = true,length = 36)
    private String assignPersonId;

    /**
     * 计划开始时间
     */
    @Column(name="plan_start_date",nullable = true,length=0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planStartDate;

    /**
     * 计划完成时间
     */
    @Column(name="plan_completion_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompletionDate;

    /**
     * 响应时间
     */

    @Column(name="response_time",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reponseTime;

    //==================执行汇报==============
    /**
     * 实际执行负责人
     */
    @Column(name="actual_executor_id",nullable = true,length = 255)
    private String actualExecutorResponsibleId;

    /**
     * 实际执行班组
     */
    @Column(name="actual_work_group",nullable = true,length = 255)
    private String actualWorkGroup;

    /**
     * 实际开始时间
     */
    @Column(name="actual_start_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualStartDate;

    /**
     * 实际结束时间
     */
    @Column(name="actual_end_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualEndDate;

    /**
     * 故障总结
     */
    @Column(name="failure_summarize",nullable = true,length = 255)
    private String failureSummarize;

    /**
     * 挂起标识，是否挂起
     */
    @Column(name="suspension",nullable = true,length = 1)
    private Boolean suspension=false;

    /**
     * 挂起开始时间
     */
    @Column(name="suspension_start_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date suspensionStartDate;

    /**
     * 挂起结束时间
     */
    @Column(name="suspension_end_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date suspensionEndDate;

    /**
     * 挂起类型
     */
    @Column(name="suspension_type",nullable = true,length = 255)
    private String suspensionType;

    /**
     * 是否超时
     */
    @Column(name="execute_whether_timeout",nullable = true,length = 1)
    private Boolean executeWhetherTimeout=false;

//    /**
//     * 实际服务费用（外委时填此费用）
//     */
//    @Column(name="actual_charge",nullable = true,length = 255)
//    private String actualCharge;

    /**
     * 工时耗时
     */
    @Column(name="consume_hours",nullable = true)
    private Double consumeHours;

    //==================验收确认==============
    /**
     * 确认解决
     */
    @Column(name="confirm",nullable = true,length = 1)
    private Boolean confirm=false;

    /**
     * 验收时间
     */
    @Column(name="acception_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptionDate;

    /**
     * 验收人id
     */
    @Column(name="acceptor_id",nullable = true,length = 36)
    private String acceptorId;

    /**
     * 验收说明
     */
    @Column(name="acception_desc",nullable = true,length = 255)
    private String acceptionDesc;

//    /**
//     * 工单总用时长（系统自动算出结果）--生成时间~关闭时间（分钟）
//     */
//    @Column(name="order_total_duration",nullable = true,length = 20)
//    private Long orderTotalDuration;

    /**
     * 组织id
     */
    @Column(name="org_id",nullable = true,length = 36)
    private String orgId;

    /**
     * 流程实例ID
     */
    @Column(name="process_instance_id",nullable = true,length = 255)
    private String processInstanceId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDefectOrderNum() {
        return defectOrderNum;
    }

    public void setDefectOrderNum(String defectOrderNum) {
        this.defectOrderNum = defectOrderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Boolean getReporterAssignFlag() {
        return reporterAssignFlag;
    }

    public void setReporterAssignFlag(Boolean reporterAssignFlag) {
        this.reporterAssignFlag = reporterAssignFlag;
    }

    public String getIncidentLevel() {
        return incidentLevel;
    }

    public void setIncidentLevel(String incidentLevel) {
        this.incidentLevel = incidentLevel;
    }

    public String getDefectDocumentId() {
        return defectDocumentId;
    }

    public void setDefectDocumentId(String defectDocumentId) {
        this.defectDocumentId = defectDocumentId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getExplainDesc() {
        return explainDesc;
    }

    public void setExplainDesc(String explainDesc) {
        this.explainDesc = explainDesc;
    }

    public Boolean getEntrustExecute() {
        return entrustExecute;
    }

    public void setEntrustExecute(Boolean entrustExecute) {
        this.entrustExecute = entrustExecute;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Date getPlanCompletionDate() {
        return planCompletionDate;
    }

    public void setPlanCompletionDate(Date planCompletionDate) {
        this.planCompletionDate = planCompletionDate;
    }

    public String getActualExecutorResponsibleId() {
        return actualExecutorResponsibleId;
    }

    public void setActualExecutorResponsibleId(String actualExecutorResponsibleId) {
        this.actualExecutorResponsibleId = actualExecutorResponsibleId;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getFailureSummarize() {
        return failureSummarize;
    }

    public void setFailureSummarize(String failureSummarize) {
        this.failureSummarize = failureSummarize;
    }

    public Boolean getSuspension() {
        return suspension;
    }

    public void setSuspension(Boolean suspension) {
        this.suspension = suspension;
    }

    public Date getSuspensionStartDate() {
        return suspensionStartDate;
    }

    public void setSuspensionStartDate(Date suspensionStartDate) {
        this.suspensionStartDate = suspensionStartDate;
    }

    public Date getSuspensionEndDate() {
        return suspensionEndDate;
    }

    public void setSuspensionEndDate(Date suspensionEndDate) {
        this.suspensionEndDate = suspensionEndDate;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(String suspensionType) {
        this.suspensionType = suspensionType;
    }

    public Boolean getExecuteWhetherTimeout() {
        return executeWhetherTimeout;
    }

    public void setExecuteWhetherTimeout(Boolean executeWhetherTimeout) {
        this.executeWhetherTimeout = executeWhetherTimeout;
    }

    public Double getConsumeHours() {
        return consumeHours;
    }

    public void setConsumeHours(Double consumeHours) {
        this.consumeHours = consumeHours;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public Date getAcceptionDate() {
        return acceptionDate;
    }

    public void setAcceptionDate(Date acceptionDate) {
        this.acceptionDate = acceptionDate;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getAcceptionDesc() {
        return acceptionDesc;
    }

    public void setAcceptionDesc(String acceptionDesc) {
        this.acceptionDesc = acceptionDesc;
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

    public String getExecutionWorkGroup() {
        return executionWorkGroup;
    }

    public void setExecutionWorkGroup(String executionWorkGroup) {
        this.executionWorkGroup = executionWorkGroup;
    }

    public String getActualWorkGroup() {
        return actualWorkGroup;
    }

    public void setActualWorkGroup(String actualWorkGroup) {
        this.actualWorkGroup = actualWorkGroup;
    }

    public Date getReponseTime() {
        return reponseTime;
    }

    public void setReponseTime(Date reponseTime) {
        this.reponseTime = reponseTime;
    }

    @Override
    public String toString() {
        return "DefectOrder{" +
                "defectOrderNum='" + defectOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusDate=" + statusDate +
                ", projectType='" + projectType + '\'' +
                ", reporterAssignFlag=" + reporterAssignFlag +
                ", incidentLevel='" + incidentLevel + '\'' +
                ", defectDocumentId=" + defectDocumentId +
                ", siteId='" + siteId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportDate=" + reportDate +
                ", explainDesc='" + explainDesc + '\'' +
                ", entrustExecute=" + entrustExecute +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", reponseTime=" + reponseTime +
                ", planStartDate=" + planStartDate +
                ", executionWorkGroup=" + executionWorkGroup +
                ", actualWorkGroup=" + actualWorkGroup +
                ", planCompletionDate=" + planCompletionDate +
                ", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
                ", actualStartDate=" + actualStartDate +
                ", actualEndDate=" + actualEndDate +
                ", failureSummarize='" + failureSummarize + '\'' +
                ", suspension=" + suspension +
                ", suspensionStartDate=" + suspensionStartDate +
                ", suspensionEndDate=" + suspensionEndDate +
                ", suspensionType='" + suspensionType + '\'' +
                ", executeWhetherTimeout=" + executeWhetherTimeout +
                ", consumeHours=" + consumeHours +
                ", confirm=" + confirm +
                ", acceptionDate=" + acceptionDate +
                ", acceptorId='" + acceptorId + '\'' +
                ", acceptionDesc='" + acceptionDesc + '\'' +
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}'+super.toString();
    }
}
