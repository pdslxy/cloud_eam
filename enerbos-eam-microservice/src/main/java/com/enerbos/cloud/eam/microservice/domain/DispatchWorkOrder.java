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
 * @date 2017-07-23 15:22
 * @Description 派工工单实体
 */
@Entity
@Table(name = "eam_dispatch_order")
public class DispatchWorkOrder extends EnerbosBaseEntity implements Serializable {

    public DispatchWorkOrder() {}

    /**
     *  工单状态
     */
    @Column(name = "work_order_status", nullable = true, length = 255)
    private String workOrderStatus;

    /**
     *  工单状态更新时间
     */
    @Column(name="work_order_status_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date workOrderStatusDate;

    //=================== 工单提报 ==================
    /**
     * 派工工单编号
     */
    @Column(name="work_order_num", nullable = true, length = 255)
    private String workOrderNum;

    /**
     *  派工描述
     */
    @Column(name = "description", nullable = true, length = 2000)
    private String description;

    /**
     *  需求部门
     */
    @Column(name = "demand_dept", nullable = true, length = 255)
    private String demandDept;

    /**
     *  需求人
     */
    @Column(name = "demand_person", nullable = true, length = 255)
    private String demandPerson;

    /**
     *  需求人电话
     */
    @Column(name = "demand_person_tel", nullable = true, length = 255)
    private String demandPersonTel;

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
    @Column(name = "report_remarks", nullable = true, length = 255)
    private String reportRemarks;

    //=================== 任务分派 ==================
    /**
     * 分派人
     */
    @Column(name = "dispatch_person_id", nullable = true, length = 36)
    private String dispatchPersonId;

    /**
     * 计划完成时间
     */
    @Column(name="plan_complete_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompleteTime;
    //=================== 执行汇报 ==================

    /**
     * 接报时间（点击确认接单的时间）
     */
    @Column(name="receive_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;

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
     *  情况说明
     */
    @Column(name = "report_description", nullable = true, length = 255)
    private String reportDescription;

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
     *  验收说明
     */
    @Column(name = "accept_description", nullable = true, length = 255)
    private String acceptDescription;
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
     *  来源ID
     */
    @Column(name = "src_Id", nullable = true, length = 255)
    private String srcId;

    /**
     *  来源类型
     */
    @Column(name = "src_Type", nullable = true, length = 255)
    private String srcType;

    /**
     *  流程ID
     */
    @Column(name = "process_instance_id", nullable = true, length = 255)
    private String processInstanceId;


    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public Date getWorkOrderStatusDate() {
        return workOrderStatusDate;
    }

    public void setWorkOrderStatusDate(Date workOrderStatusDate) {
        this.workOrderStatusDate = workOrderStatusDate;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDemandDept() {
        return demandDept;
    }

    public void setDemandDept(String demandDept) {
        this.demandDept = demandDept;
    }

    public String getDemandPerson() {
        return demandPerson;
    }

    public void setDemandPerson(String demandPerson) {
        this.demandPerson = demandPerson;
    }

    public String getDemandPersonTel() {
        return demandPersonTel;
    }

    public void setDemandPersonTel(String demandPersonTel) {
        this.demandPersonTel = demandPersonTel;
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

    public String getReportRemarks() {
        return reportRemarks;
    }

    public void setReportRemarks(String reportRemarks) {
        this.reportRemarks = reportRemarks;
    }

    public String getDispatchPersonId() {
        return dispatchPersonId;
    }

    public void setDispatchPersonId(String dispatchPersonId) {
        this.dispatchPersonId = dispatchPersonId;
    }

    public Date getPlanCompleteTime() {
        return planCompleteTime;
    }

    public void setPlanCompleteTime(Date planCompleteTime) {
        this.planCompleteTime = planCompleteTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
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
        return "DispatchWorkOrder{" +
                "workOrderStatus='" + workOrderStatus + '\'' +
                ", workOrderStatusDate=" + workOrderStatusDate +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", demandDept='" + demandDept + '\'' +
                ", demandPerson='" + demandPerson + '\'' +
                ", demandPersonTel='" + demandPersonTel + '\'' +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", reportPersonTel='" + reportPersonTel + '\'' +
                ", reportDate=" + reportDate +
                ", reportRemarks='" + reportRemarks + '\'' +
                ", dispatchPersonId='" + dispatchPersonId + '\'' +
                ", planCompleteTime=" + planCompleteTime +
                ", receiveTime=" + receiveTime +
                ", completionTime=" + completionTime +
                ", consumeHours=" + consumeHours +
                ", reportDescription='" + reportDescription + '\'' +
                ", orderReportPersonId='" + orderReportPersonId + '\'' +
                ", acceptTime=" + acceptTime +
                ", acceptDescription='" + acceptDescription + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                "} " + super.toString();
    }
}
