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
 * @date 2017-07-11 11:31
 * @Description 报修工单列表VO
 */
@ApiModel(value = "报修工单列表", description = "报修工单列表VO")
public class RepairOrderListVo implements Serializable {
    @ApiModelProperty(value = "工单id")
    private String id;
    @ApiModelProperty(value = "工单id")
    private String workOrderId;
    @ApiModelProperty(value = "工单编号")
    private String workOrderNum;
    @ApiModelProperty(value = "报修描述")
    private String description;
    @ApiModelProperty(value = "工程类型")
    private String projectType;
    @ApiModelProperty(value = "工单来源")
    private String workOrderSource;
    @ApiModelProperty(value = "是否超时 true：是")
    private Boolean executeTimeout;
    @ApiModelProperty(value = "工单状态")
    private String workOrderStatus;
    @ApiModelProperty(value = "提报日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;
    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;
    @ApiModelProperty(value = "是否评价 true：是")
    private Boolean evaluate;

    @ApiModelProperty(value = "维修质量")
    private String maintenanceQuality;
    @ApiModelProperty(value = "维修态度")
    private String maintenanceAttitude;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getWorkOrderSource() {
        return workOrderSource;
    }

    public void setWorkOrderSource(String workOrderSource) {
        this.workOrderSource = workOrderSource;
    }

    public Boolean getExecuteTimeout() {
        return executeTimeout;
    }

    public void setExecuteTimeout(Boolean executeTimeout) {
        this.executeTimeout = executeTimeout;
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

    public Boolean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Boolean evaluate) {
        this.evaluate = evaluate;
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

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "RepairOrderListVo{" +
                "id='" + id + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", projectType='" + projectType + '\'' +
                ", workOrderSource='" + workOrderSource + '\'' +
                ", executeTimeout=" + executeTimeout +
                ", workOrderStatus='" + workOrderStatus + '\'' +
                ", reportDate=" + reportDate +
                ", collect=" + collect +
                ", evaluate=" + evaluate +
                ", maintenanceQuality='" + maintenanceQuality + '\'' +
                ", maintenanceAttitude='" + maintenanceAttitude + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
