package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 16:53
 * @Description 例行工单实体类
 */
@ApiModel(value = "例行工作单vo")
public class HeadquartersDailyTaskVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */

    @ApiModelProperty(value = "主键ID(新增不需要传值)")
    private String id;

    /**
     * 任务编码
     */
    @ApiModelProperty(value = "任务编码")
    private String taskNum;


    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    /**
     * 分派待确认、待分派、执行待确认、待执行、已完成
     */
    @ApiModelProperty(value = "分派待确认、待分派、执行待确认、待执行、已完成")
    private String status;

    /**
     * 计划编码
     */
    @ApiModelProperty(value = "计划编码")
        private String planNum;
    /**
     * 计划名称
     */
    @ApiModelProperty(value = " 计划名称")
    private String planName;

    /**
     * 计划名称
     */
    @ApiModelProperty(value = " 计划Id")
    private String planId;

    /**
     * 描述
     */
    @ApiModelProperty(value = " 描述")
    private String description;

    /**
     * 检查项
     */
    @ApiModelProperty(value = " 检查项")
    private String checkItem;

    /**
     * 工作类型
     */
    @ApiModelProperty(value = " 工作类型")
    private String  workType;

    /**
     * 组织id
     */
    @ApiModelProperty(value = " 组织id")
    private String orgId;

    /**
     * 站点id
     */
    @ApiModelProperty(value = " 站点id&执行单位")
    private String siteId;

    /**
     * 任务属性   标准、临时
     */
    @ApiModelProperty(value = " 任务属性   标准、临时")
    private String taskProperty;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;
    /**
     * 预计完成时间
     */
    @ApiModelProperty(value = "预计完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimateDate;


    @ApiModelProperty(value = "实际完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualDate;

    /**
     * 执行人
     */
    @ApiModelProperty(value = "执行人")
    private String executor;

    /**
     * 执行人名称
     */
    @ApiModelProperty(value = "执行人名称")
    private String executorName;


//    @ApiModelProperty(value = "执行人电话")
//    private String executorTel;
    /**
     * 总结
     */
    @ApiModelProperty(value = "总结")
    private String summary;


    /**
     * 总结
     */
    @ApiModelProperty(value = "流程Id")
    private  String processInstanceId;


    /**
     * 提报人
     */
    @ApiModelProperty(value = "提报人")
    private String reportPersonId;

    @ApiModelProperty(value = "提报人名称")
    private  String reportPersonName;
    /**
     * 分派人
     */
    @ApiModelProperty(value = "分派人")
    private String dispatchPersonId;


    @ApiModelProperty(value = "分派人名称")
    private String dispatchPersonName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "流程描述")
    private String processDescription;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程描述")
    private  String  processStatus;
    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点&执行单位")
    private String siteName;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "接单人")
    private String receiverPersonId;

    @ApiModelProperty(value = "接单人名称")
    private String receiverPersonName;


    @ApiModelProperty(value = "分派用户组人员ID")
    private String dispatchUserGroupPersonId;
    @ApiModelProperty(value = "分派用户组人员名称")
    private String dispatchUserGroupPersonName;




    public String getReceiverPersonId() {
        return receiverPersonId;
    }

    public void setReceiverPersonId(String receiverPersonId) {
        this.receiverPersonId = receiverPersonId;
    }

    public String getReceiverPersonName() {
        return receiverPersonName;
    }

    public void setReceiverPersonName(String receiverPersonName) {
        this.receiverPersonName = receiverPersonName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }
//    public String getExecutorTel() {
//        return executorTel;
//    }
//
//    public void setExecutorTel(String executorTel) {
//        this.executorTel = executorTel;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum == null ? null : taskNum.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum == null ? null : planNum.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem == null ? null : checkItem.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public String getTaskProperty() {
        return taskProperty;
    }

    public void setTaskProperty(String taskProperty) {
        this.taskProperty = taskProperty == null ? null : taskProperty.trim();
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getDispatchPersonId() {
        return dispatchPersonId;
    }

    public void setDispatchPersonId(String dispatchPersonId) {
        this.dispatchPersonId = dispatchPersonId;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }

    public String getDispatchPersonName() {
        return dispatchPersonName;
    }

    public void setDispatchPersonName(String dispatchPersonName) {
        this.dispatchPersonName = dispatchPersonName;
    }

    public String getDispatchUserGroupPersonId() {
        return dispatchUserGroupPersonId;
    }

    public void setDispatchUserGroupPersonId(String dispatchUserGroupPersonId) {
        this.dispatchUserGroupPersonId = dispatchUserGroupPersonId;
    }

    public String getDispatchUserGroupPersonName() {
        return dispatchUserGroupPersonName;
    }

    public void setDispatchUserGroupPersonName(String dispatchUserGroupPersonName) {
        this.dispatchUserGroupPersonName = dispatchUserGroupPersonName;
    }

    @Override
    public String toString() {
        return "HeadquartersDailyTaskVo{" +
                "id='" + id + '\'' +
                ", taskNum='" + taskNum + '\'' +
                ", taskName='" + taskName + '\'' +
                ", status='" + status + '\'' +
                ", planNum='" + planNum + '\'' +
                ", planName='" + planName + '\'' +
                ", description='" + description + '\'' +
                ", checkItem='" + checkItem + '\'' +
                ", workType='" + workType + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", taskProperty='" + taskProperty + '\'' +
                ", createDate=" + createDate +
                ", estimateDate=" + estimateDate +
                ", actualDate=" + actualDate +
                ", executor='" + executor + '\'' +
           //     ", executorTel='" + executorTel + '\'' +
                ", summary='" + summary + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", dispatchPersonId='" + dispatchPersonId + '\'' +
                ", processDescription='" + processDescription + '\'' +
                ", processStatus='" + processStatus + '\'' +
                '}';
    }
}