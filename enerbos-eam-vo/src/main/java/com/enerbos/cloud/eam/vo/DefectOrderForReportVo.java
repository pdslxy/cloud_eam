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
 * @Description 消缺工单-执行汇报
 */
@ApiModel(value = "消缺工单-执行汇报", description = "消缺工单-执行汇报Vo")
public class DefectOrderForReportVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;


    /**
     * 待接单人员Id
     */
    @ApiModelProperty("待接单人员Id")
    private String availableTakingPersonId;

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
    @ApiModelProperty("工程类型")
    private String siteId;

    /**
     * 提报人id
     */
    @ApiModelProperty("工程类型")
    private String reportId;

    /**
     * 提报人
     */
    @ApiModelProperty("工程类型")
    private String reportName;

    /**
     * 分派人id
     */
    @ApiModelProperty("工程类型")
    private String assignPersonId;

    /**
     * 分派人
     */
    @ApiModelProperty("工程类型")
    private String assignPersonName;
    //==================执行汇报==============
    /**
     * 实际执行负责人id
     */
    @ApiModelProperty("实际执行负责人id")
    private String actualExecutorResponsibleId;

    /**
     * 实际执行负责人
     */
    @ApiModelProperty("实际执行负责人")
    private String actualExecutorResponsibleName;

    /**
     * 实际执行人id
     */
    @ApiModelProperty("实际执行人id")
    private String actualExecutorId;

    /**
     * 实际执行人
     */
    @ApiModelProperty("实际执行人")
    private String actualExecutorName;

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
    @ApiModelProperty(value = "工时耗时",required = true)
    private Double consumeHours;



    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private String orgId;

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

    /**
     * 流程实例ID
     */


    @ApiModelProperty(value = "流程实例ID",hidden = true)
    private String processInstanceId;

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> impleRecordVoVoList;

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

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
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

    public String getActualExecutorId() {
        return actualExecutorId;
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

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setActualExecutorId(String actualExecutorId) {
        this.actualExecutorId = actualExecutorId;
    }

    public String getActualExecutorName() {
        return actualExecutorName;
    }

    public void setActualExecutorName(String actualExecutorName) {
        this.actualExecutorName = actualExecutorName;
    }

    public String getActualExecutorResponsibleName() {
        return actualExecutorResponsibleName;
    }

    public void setActualExecutorResponsibleName(String actualExecutorResponsibleName) {
        this.actualExecutorResponsibleName = actualExecutorResponsibleName;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getAssignPersonName() {
        return assignPersonName;
    }

    public void setAssignPersonName(String assignPersonName) {
        this.assignPersonName = assignPersonName;
    }

    public String getAvailableTakingPersonId() {
        return availableTakingPersonId;
    }

    public void setAvailableTakingPersonId(String availableTakingPersonId) {
        this.availableTakingPersonId = availableTakingPersonId;
    }



    @Override
    public String toString() {
        return "DefectOrderForReportVo{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectTypeGroupTypeName='" + projectTypeGroupTypeName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", assignPersonName='" + assignPersonName + '\'' +
                ", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
                ", actualExecutorResponsibleName='" + actualExecutorResponsibleName + '\'' +
                ", actualExecutorId='" + actualExecutorId + '\'' +
                ", actualExecutorName='" + actualExecutorName + '\'' +
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
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                '}';
    }
}
