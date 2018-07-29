package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "eam_headquarters_daily_task")
public class HeadquartersDailyTask implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 任务编码
     */
    @Column(name = "task_num", length = 50)
    private String taskNum;

    /**
     * 任务名称
     */
    @Column(name = "task_name", length = 50)
    private String taskName;


    /**
     * 分派待确认、待分派、执行待确认、待执行、已完成
     */
    @Column(name = "status", length = 10)
    private String status;

    /**
     * 计划编码
     */
    @Column(name = "plan_num", length = 50)
    private String planNum;

    /**
     * 计划Id
     */
    @Column(name = "plan_Id", length = 50)
    private String planId;
//    /**
//     * 计划名称
//     */
//    @Column(name = "plan_name", length = 200)
//    private String planName;
    /**
     * 描述
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * 工作类型
     */
    @Column(name = "work_type", length = 36)
    private String  workType;

    /**
     * 检查项
     */
    @Column(name = "check_item", length = 36)
    private String checkItem;

    /**
     * 组织id
     */
    @Column(name = "org_id", length = 36)
    private String orgId;

    /**
     * 站点id
     */
    @Column(name = "site_id", length = 36)
    private String siteId;

    /**
     * 任务属性   标准、临时
     */
    @Column(name = "task_property", length = 50)
    private String taskProperty;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


    /**
     * 创建人
     */
    @Column(name = "create_user", length = 50)
    private String  createUser;
    /**
     * 预计完成时间
     */
    @Column(name = "estimate_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimateDate;

    /**
     * 实际完成时间
     */
    @Column(name = "actual_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualDate;

//    /**
//     * 执行负责人电话
//     */
//    @Column(name = "executor_tel", length = 16)
//    private String executorTel;
    /**
     * 总结
     */
    @Column(name = "summary", length = 500)
    private String summary;

    /**
     * 流程id
     */
    @Column(name = "process_Id", length = 50)
    private String processInstanceId;

    /**
     * 提报人
     */
    @Column(name = "reportPerson_Id", length = 36)
    private String reportPersonId;
    /**
     * 分派人
     */
    @Column(name = "dispatchPerson_Id", length = 36)
    private String dispatchPersonId;

//    /**
//     * 执行负责人
//     */
//    @Column(name = "executor", length = 500)
//    private String executor;
    /**
     * 接单人
     */
    @Column(name = "receiverPerson_Id", length = 36)
    private  String receiverPersonId;



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

//    public String getPlanName() {
//        return planName;
//    }
//
//    public void setPlanName(String planName) {
//        this.planName = planName == null ? null : planName.trim();
//    }

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

    public String getReceiverPersonId() {
        return receiverPersonId;
    }

    public void setReceiverPersonId(String receiverPersonId) {
        this.receiverPersonId = receiverPersonId;
    }

    @Override
    public String toString() {
        return "HeadquartersDailyTask{" +
                "id='" + id + '\'' +
                ", taskNum='" + taskNum + '\'' +
                ", taskName='" + taskName + '\'' +
                ", status='" + status + '\'' +
                ", planNum='" + planNum + '\'' +
                ", planId='" + planId + '\'' +
              //  ", planName='" + planName + '\'' +
                ", description='" + description + '\'' +
                ", workType='" + workType + '\'' +
                ", checkItem='" + checkItem + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", taskProperty='" + taskProperty + '\'' +
                ", createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", estimateDate=" + estimateDate +
                ", actualDate=" + actualDate +
                ", summary='" + summary + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", dispatchPersonId='" + dispatchPersonId + '\'' +
                ", receiverPersonId='" + receiverPersonId + '\'' +
                '}';
    }
}