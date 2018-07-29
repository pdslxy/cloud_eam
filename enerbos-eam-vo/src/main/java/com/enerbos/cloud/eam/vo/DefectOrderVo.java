package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@ApiModel(value = "消缺工单", description = "消缺工单Vo")
public class DefectOrderVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 消缺工单编号
     */
    @ApiModelProperty("消缺工单编码")
    private String defectOrderNum;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期",hidden = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    private String projectType;

    /**
     * 是否提报人派单
     */
    @ApiModelProperty("是否提报人派单")
    private Boolean reporterAssignFlag = false;

    /**
     * 事件级别
     */
    @ApiModelProperty("事件级别")
    private String incidentLevel;

    /**
     * 关联的缺陷单
     */
    @ApiModelProperty("关联的缺陷单")
    private String defectDocumentId;

    /**
     * 站点id
     */
    @ApiModelProperty("站点id")
    private String siteId;

    /**
     * 提报人id
     */
    @ApiModelProperty("提报人id")
    private String reportId;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String explainDesc;
    //==================任务分派==============
    /**
     * 是否委托执行
     */
    @ApiModelProperty("是否委托执行")
    private Boolean entrustExecute=false;

    /**
     * 执行班组
     */
    @ApiModelProperty("执行班组")
    private String executionWorkGroup;

    /**
     * 分派人id
     */
    @ApiModelProperty("分派人id")
    private String assignPersonId;

    /**
     * 响应时间
     */
    @ApiModelProperty("响应时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reponseTime;

    /**
     * 计划开始时间
     */
    @ApiModelProperty("计划开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planStartDate;

    /**
     * 计划完成时间
     */
    @ApiModelProperty("计划完成时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompletionDate;

    //==================执行汇报==============
    /**
     * 实际执行负责人
     */
    @ApiModelProperty("实际执行负责人")
    private String actualExecutorResponsibleId;

    /**
     * 实际执行班组
     */
    @ApiModelProperty("实际执行班组")
    private String actualWorkGroup;

    /**
     * 实际开始时间
     */
    @ApiModelProperty("实际开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualStartDate;

    /**
     * 实际结束时间
     */
    @ApiModelProperty("实际结束时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualEndDate;

    /**
     * 故障总结
     */
    @ApiModelProperty("故障总结")
    private String failureSummarize;

    /**
     * 挂起标识，是否挂起
     */
    @ApiModelProperty("挂起标识，是否挂起")
    private Boolean suspension=false;

    /**
     * 挂起开始时间
     */
    @ApiModelProperty("挂起开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date suspensionStartDate;

    /**
     * 挂起结束时间
     */
    @ApiModelProperty("挂起结束时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date suspensionEndDate;

    /**
     * 挂起类型
     */
    @ApiModelProperty("挂起类型")
    private String suspensionType;

    /**
     * 是否超时
     */
    @ApiModelProperty("是否超时")
    private Boolean executeWhetherTimeout=false;

    /**
     * 工时耗时
     */
    @ApiModelProperty("工时耗时")
    private Double consumeHours;

    //==================验收确认==============
    /**
     * 确认解决
     */
    @ApiModelProperty("确认解决")
    private Boolean confirm=false;

    /**
     * 验收时间
     */
    @ApiModelProperty("验收时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptionDate;

    /**
     * 验收人id
     */
    @ApiModelProperty("验收人id")
    private String acceptorId;

    /**
     * 验收说明
     */
    @ApiModelProperty("验收说明")
    private String acceptionDesc;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private String orgId;

    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID",hidden = true)
    private String processInstanceId;

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

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> impleRecordVoVoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getExecutionWorkGroup() {
        return executionWorkGroup;
    }

    public void setExecutionWorkGroup(String executionWorkGroup) {
        this.executionWorkGroup = executionWorkGroup;
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

    public String getActualWorkGroup() {
        return actualWorkGroup;
    }

    public void setActualWorkGroup(String actualWorkGroup) {
        this.actualWorkGroup = actualWorkGroup;
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

    public List<WorkFlowImpleRecordVo> getImpleRecordVoVoList() {
        return impleRecordVoVoList;
    }

    public void setImpleRecordVoVoList(List<WorkFlowImpleRecordVo> impleRecordVoVoList) {
        this.impleRecordVoVoList = impleRecordVoVoList;
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

    public Date getReponseTime() {
        return reponseTime;
    }

    public void setReponseTime(Date reponseTime) {
        this.reponseTime = reponseTime;
    }

    @Override
    public String toString() {
        return "DefectOrderVo{" +
                "id='" + id + '\'' +
                ", defectOrderNum='" + defectOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusDate=" + statusDate +
                ", projectType='" + projectType + '\'' +
                ", reporterAssignFlag=" + reporterAssignFlag +
                ", incidentLevel='" + incidentLevel + '\'' +
                ", defectDocumentId='" + defectDocumentId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportDate=" + reportDate +
                ", explainDesc='" + explainDesc + '\'' +
                ", entrustExecute=" + entrustExecute +
                ", executionWorkGroup='" + executionWorkGroup + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", reponseTime=" + reponseTime +
                ", planStartDate=" + planStartDate +
                ", planCompletionDate=" + planCompletionDate +
                ", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
                ", actualWorkGroup='" + actualWorkGroup + '\'' +
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
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                '}';
    }
}
