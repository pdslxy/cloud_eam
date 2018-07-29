package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-24 17:01
 * @Description 派工工单-流程VO
 */
@ApiModel(value = "派工工单-流程VO", description = "派工工单-流程VO")
public class DispatchWorkOrderFlowVo implements Serializable {
    //============== 报修工单状态相关 =================
    @ApiModelProperty(value = "工单状态")
    private String workOrderStatus;
    @ApiModelProperty(value = "工单状态日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date workOrderStatusDate;
    @ApiModelProperty(value = "所属组织编码")
    private String orgId;
    @ApiModelProperty(value = "所属组织")
    private String orgName;
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;
    @ApiModelProperty(value = "所属站点")
    private String siteName;
    //============== 报修工单提报相关 =================
    @ApiModelProperty(value = "工单id")
    private String id;
    @ApiModelProperty(value = "工单id")
    private String workOrderId;
    @ApiModelProperty(value = "工单编号")
    private String workOrderNum;
    @ApiModelProperty(value = "派工描述")
    private String description;
    //============= 报修工单报修人员相关 ================
    @ApiModelProperty(value = "需求部门")
    private String demandDept;
    @ApiModelProperty(value = "需求人")
    private String demandPerson;
    @ApiModelProperty(value = "联系方式")
    private String demandPersonTel;
    //============= 报修工单提报人员相关 ================
    @ApiModelProperty(value = "提报人ID")
    private String reportPersonId;
    @ApiModelProperty(value = "提报人名称")
    private String reportPersonName;
    @ApiModelProperty(value = "提报人电话")
    private String reportPersonTel;
    @ApiModelProperty(value = "提报日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;
    @ApiModelProperty(value = "提报说明(备注)")
    private String reportRemarks;
    //============= 报修工单任务分派相关 ================
    @ApiModelProperty(value = "执行人ID")
    private String executionPersonId;
    @ApiModelProperty(value = "执行人名称")
    private String executionPerson;
    @ApiModelProperty(value = "分派人")
    private String dispatchPersonId;
    @ApiModelProperty(value = "分派人名称")
    private String dispatchPersonName;
    @ApiModelProperty(value = "计划完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompleteTime;
    //============= 报修工单执行汇报相关 ================
    @ApiModelProperty(value = "接报时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;
    @ApiModelProperty(value = "完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completionTime;
    @ApiModelProperty(value = "工时耗时(单位：分钟)")
    private Double consumeHours;
    @ApiModelProperty(value = "情况说明")
    private String reportDescription;
    @ApiModelProperty(value = "实际执行人ID")
    private String actualExecutionPersonId;
    @ApiModelProperty(value = "实际执行人")
    private String actualExecutionPerson;

    @ApiModelProperty(value = "工单汇报人ID")
    private String orderReportPersonId;
    @ApiModelProperty(value = "工单汇报人")
    private String orderReportPerson;
    //============= 报修工单验收确认相关 ================
    @ApiModelProperty(value = "验收人ID")
    private String acceptPersonId;
    @ApiModelProperty(value = "验收人")
    private String acceptPerson;
    @ApiModelProperty(value = "验收时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptTime;
    @ApiModelProperty(value = "验收说明")
    private String acceptDescription;

    //============= 报修工单流程数据相关 ================
    @ApiModelProperty(value = "流程状态")
    private String processStatus;
    @ApiModelProperty(value = "流程说明")
    private String processDescription;
    @ApiModelProperty(value = "流程ID", hidden = true)
    private String processInstanceId;
    //============= 当前流程操作人相关信息 ===============
    @ApiModelProperty(value = "操作人ID", hidden = true)
    private String operatorPersonId;
    @ApiModelProperty(value = "操作人名称", hidden = true)
    private String operatorPerson;


    @ApiModelProperty(value = "分派组人员ID", hidden = true)
    private String dispatchGroupPersonId;
    @ApiModelProperty(value = "分派组人员名称", hidden = true)
    private String dispatchGroupPersonName;

    /**
     *  来源ID
     */
    @ApiModelProperty(value = "来源ID", hidden = true)
    private String srcId;

    /**
     *  来源类型
     */
    @ApiModelProperty(value = "来源类型", hidden = true)
    private String srcType;


    //============= get / set ================
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
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

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
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

    public String getExecutionPersonId() {
        return executionPersonId;
    }

    public void setExecutionPersonId(String executionPersonId) {
        this.executionPersonId = executionPersonId;
    }

    public String getExecutionPerson() {
        return executionPerson;
    }

    public void setExecutionPerson(String executionPerson) {
        this.executionPerson = executionPerson;
    }

    public String getDispatchPersonId() {
        return dispatchPersonId;
    }

    public void setDispatchPersonId(String dispatchPersonId) {
        this.dispatchPersonId = dispatchPersonId;
    }

    public String getDispatchPersonName() {
        return dispatchPersonName;
    }

    public void setDispatchPersonName(String dispatchPersonName) {
        this.dispatchPersonName = dispatchPersonName;
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

    public String getActualExecutionPersonId() {
        return actualExecutionPersonId;
    }

    public void setActualExecutionPersonId(String actualExecutionPersonId) {
        this.actualExecutionPersonId = actualExecutionPersonId;
    }

    public String getActualExecutionPerson() {
        return actualExecutionPerson;
    }

    public void setActualExecutionPerson(String actualExecutionPerson) {
        this.actualExecutionPerson = actualExecutionPerson;
    }

    public String getAcceptPersonId() {
        return acceptPersonId;
    }

    public void setAcceptPersonId(String acceptPersonId) {
        this.acceptPersonId = acceptPersonId;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
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

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getOperatorPersonId() {
        return operatorPersonId;
    }

    public void setOperatorPersonId(String operatorPersonId) {
        this.operatorPersonId = operatorPersonId;
    }

    public String getOperatorPerson() {
        return operatorPerson;
    }

    public void setOperatorPerson(String operatorPerson) {
        this.operatorPerson = operatorPerson;
    }

    public String getDispatchGroupPersonId() {
        return dispatchGroupPersonId;
    }

    public void setDispatchGroupPersonId(String dispatchGroupPersonId) {
        this.dispatchGroupPersonId = dispatchGroupPersonId;
    }

    public String getDispatchGroupPersonName() {
        return dispatchGroupPersonName;
    }

    public void setDispatchGroupPersonName(String dispatchGroupPersonName) {
        this.dispatchGroupPersonName = dispatchGroupPersonName;
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

    public String getOrderReportPersonId() {
        return orderReportPersonId;
    }

    public void setOrderReportPersonId(String orderReportPersonId) {
        this.orderReportPersonId = orderReportPersonId;
    }

    public String getOrderReportPerson() {
        return orderReportPerson;
    }

    public void setOrderReportPerson(String orderReportPerson) {
        this.orderReportPerson = orderReportPerson;
    }

    @Override
    public String toString() {
        return "DispatchWorkOrderFlowVo{" +
                "workOrderStatus='" + workOrderStatus + '\'' +
                ", workOrderStatusDate=" + workOrderStatusDate +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", id='" + id + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", demandDept='" + demandDept + '\'' +
                ", demandPerson='" + demandPerson + '\'' +
                ", demandPersonTel='" + demandPersonTel + '\'' +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", reportPersonName='" + reportPersonName + '\'' +
                ", reportPersonTel='" + reportPersonTel + '\'' +
                ", reportDate=" + reportDate +
                ", reportRemarks='" + reportRemarks + '\'' +
                ", executionPersonId='" + executionPersonId + '\'' +
                ", executionPerson='" + executionPerson + '\'' +
                ", dispatchPersonId='" + dispatchPersonId + '\'' +
                ", dispatchPersonName='" + dispatchPersonName + '\'' +
                ", planCompleteTime=" + planCompleteTime +
                ", receiveTime=" + receiveTime +
                ", completionTime=" + completionTime +
                ", consumeHours=" + consumeHours +
                ", reportDescription='" + reportDescription + '\'' +
                ", actualExecutionPersonId='" + actualExecutionPersonId + '\'' +
                ", actualExecutionPerson='" + actualExecutionPerson + '\'' +
                ", acceptPersonId='" + acceptPersonId + '\'' +
                ", acceptPerson='" + acceptPerson + '\'' +
                ", acceptTime=" + acceptTime +
                ", acceptDescription='" + acceptDescription + '\'' +
                ", processStatus='" + processStatus + '\'' +
                ", processDescription='" + processDescription + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", operatorPersonId='" + operatorPersonId + '\'' +
                ", operatorPerson='" + operatorPerson + '\'' +
                '}';
    }
}
