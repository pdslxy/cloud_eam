package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * @Description 消缺工单-任务分派
 */
@ApiModel(value = "消缺工单-任务分派", description = "消缺工单-任务分派Vo")
public class DefectOrderForAssignVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 消缺工单编号
     */
    @ApiModelProperty(value = "消缺工单编码",required = true)
    @NotBlank(message = "消缺工单编码不能为空")
    private String defectOrderNum;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    private String projectType;

    /**
     * 工程类型用户组名
     */
    @ApiModelProperty("工程类型用户组名")
    private String projectTypeGroupTypeName;

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
     * 提报人
     */
    @ApiModelProperty("提报人")
    private String reportName;
    //==================任务分派==============
    /**
     * 执行班组
     */
    @ApiModelProperty("执行班组")
    private String executionWorkGroup;

    /**
     * 执行人id
     */
    @ApiModelProperty("执行人id")
    private String executorPersonId;

    /**
     * 执行人
     */
    @ApiModelProperty("执行人")
    private String executorPersonName;

    /**
     * 待接单人员名
     */
    @ApiModelProperty("待接单人员名")
    private String availableTakingPersonName;

    /**
     * 是否委托执行
     */
    @ApiModelProperty("是否委托执行")
    private Boolean entrustExecute=false;

    /**
     * 委托执行人id
     */
    @ApiModelProperty("委托执行人id")
    private String entrustExecutePersonId;

    /**
     * 委托执行人
     */
    @ApiModelProperty("委托执行人")
    private String entrustExecutePersonName;

    /**
     * 分派人id
     */
    @ApiModelProperty("分派人id")
    private String assignPersonId;

    /**
     * 分派人
     */
    @ApiModelProperty("分派人")
    private String assignPersonName;

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


    /**
     * 响应时间
     */
    @ApiModelProperty("响应时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reponseTime;

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
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> impleRecordVoVoList;

    @ApiModelProperty(value = "提报员分派")
    private  boolean reporterAssignFlag;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecutionWorkGroup() {
        return executionWorkGroup;
    }

    public void setExecutionWorkGroup(String executionWorkGroup) {
        this.executionWorkGroup = executionWorkGroup;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<WorkFlowImpleRecordVo> getImpleRecordVoVoList() {
        return impleRecordVoVoList;
    }

    public void setImpleRecordVoVoList(List<WorkFlowImpleRecordVo> impleRecordVoVoList) {
        this.impleRecordVoVoList = impleRecordVoVoList;
    }

    public String getExecutorPersonId() {
        return executorPersonId;
    }

    public void setExecutorPersonId(String executorPersonId) {
        this.executorPersonId = executorPersonId;
    }

    public String getEntrustExecutePersonId() {
        return entrustExecutePersonId;
    }

    public void setEntrustExecutePersonId(String entrustExecutePersonId) {
        this.entrustExecutePersonId = entrustExecutePersonId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getExecutorPersonName() {
        return executorPersonName;
    }

    public void setExecutorPersonName(String executorPersonName) {
        this.executorPersonName = executorPersonName;
    }

    public String getEntrustExecutePersonName() {
        return entrustExecutePersonName;
    }

    public void setEntrustExecutePersonName(String entrustExecutePersonName) {
        this.entrustExecutePersonName = entrustExecutePersonName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeGroupTypeName() {
        return projectTypeGroupTypeName;
    }

    public void setProjectTypeGroupTypeName(String projectTypeGroupTypeName) {
        this.projectTypeGroupTypeName = projectTypeGroupTypeName;
    }

    public String getAssignPersonName() {
        return assignPersonName;
    }

    public void setAssignPersonName(String assignPersonName) {
        this.assignPersonName = assignPersonName;
    }

    public String getAvailableTakingPersonName() {
        return availableTakingPersonName;
    }

    public void setAvailableTakingPersonName(String availableTakingPersonName) {
        this.availableTakingPersonName = availableTakingPersonName;
    }

    public Date getReponseTime() {
        return reponseTime;
    }

    public void setReponseTime(Date reponseTime) {
        this.reponseTime = reponseTime;
    }

    public boolean isReporterAssignFlag() {
        return reporterAssignFlag;
    }

    public void setReporterAssignFlag(boolean reporterAssignFlag) {
        this.reporterAssignFlag = reporterAssignFlag;
    }

    @Override
    public String toString() {
        return "DefectOrderForAssignVo{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectTypeGroupTypeName='" + projectTypeGroupTypeName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", executionWorkGroup='" + executionWorkGroup + '\'' +
                ", executorPersonId='" + executorPersonId + '\'' +
                ", executorPersonName='" + executorPersonName + '\'' +
                ", availableTakingPersonName='" + availableTakingPersonName + '\'' +
                ", entrustExecute=" + entrustExecute +
                ", entrustExecutePersonId='" + entrustExecutePersonId + '\'' +
                ", entrustExecutePersonName='" + entrustExecutePersonName + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", assignPersonName='" + assignPersonName + '\'' +
                ", planStartDate=" + planStartDate +
                ", planCompletionDate=" + planCompletionDate +
                ", reponseTime=" + reponseTime +
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", updateDate=" + updateDate +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                '}';
    }
}
