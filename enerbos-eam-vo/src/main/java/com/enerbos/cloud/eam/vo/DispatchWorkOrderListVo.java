package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-23 16:28
 * @Description 派工工单列表VO
 */
@ApiModel(value = "派工工单列表", description = "派工工单列表VO")
public class DispatchWorkOrderListVo implements Serializable {
    @ApiModelProperty(value = "工单id")
    private String id;
    @ApiModelProperty(value = "工单id")
    private String workOrderId;
    @ApiModelProperty(value = "工单编号")
    private String workOrderNum;
    @ApiModelProperty(value = "派工描述")
    private String description;
    @ApiModelProperty(value = "需求部门")
    private String demandDept;
    @ApiModelProperty(value = "需求人")
    private String demandPerson;
    @ApiModelProperty(value = "提报人id")
    private String reportPersonId;
    @ApiModelProperty(value = "提报人")
    private String reportPerson;
    @ApiModelProperty(value = "工单状态")
    private String workOrderStatus;
    @ApiModelProperty(value = "提报日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;
    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;
    @ApiModelProperty(value = "流程实例Id")
    private String processInstanceId;
    @ApiModelProperty("需求人联系方式")
    private String demandPersonTel;

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

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getDemandPersonTel() {
        return demandPersonTel;
    }

    public void setDemandPersonTel(String demandPersonTel) {
        this.demandPersonTel = demandPersonTel;
    }

    @Override
    public String toString() {
        return "DispatchWorkOrderListVo{" +
                "id='" + id + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", demandDept='" + demandDept + '\'' +
                ", demandPerson='" + demandPerson + '\'' +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", reportPerson='" + reportPerson + '\'' +
                ", workOrderStatus='" + workOrderStatus + '\'' +
                ", reportDate=" + reportDate +
                ", collect=" + collect +
                ", processInstanceId=" + processInstanceId +
                ", demandPersonTel='" + demandPersonTel + '\'' +
                '}';
    }
}
