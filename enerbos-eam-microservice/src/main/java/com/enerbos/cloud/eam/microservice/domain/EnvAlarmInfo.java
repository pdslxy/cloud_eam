package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 刘广路
 * @version 1.0.0
 * @date 2017/11/20 11:24
 * @Description  环境监测报警信息
 */
@Entity
@Table(name = "env_alarm_info")
public class EnvAlarmInfo implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "asset_id", nullable = true, length = 36)
    private String assetId; //设备ID

    @Column(name = "asset_name", nullable = true, length = 50)
    private String assetName;//设备名称

    @Column(name = "asset_attribute", nullable = true, length = 50)
    private String assetAttribute;//属性

    @Column(name = "location_id", nullable = true, length = 36)
    private String locationId;//位置

    @Column(name = "point_name", nullable = true, length = 50)
    private String pointName; //点的名称

    @Column(name = "point_code", nullable = true, length = 50)
    private String pointCode; //点的编码

    @Column(name = "alarm_type", nullable = true, length = 10)
    private String  alarmType; //报警类型

    @Column(name = "priority", nullable = true)
    private Long priority; //报警级别

    @Column(name = "description", nullable = true, length = 100)
    private String description; //报警描述

    @Column(name = "status", nullable = true, length = 10)
    private String status;//报警状态

    @Column(name = "create_date", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate; //报警时间

    @Column(name = "response_date", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date responseDate;//处理完成时间

    @Column(name = "continue_time", nullable = true)
    private Long continueTime;//持续时间

    /**
     * 组织id
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    /**
     * 站点id
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetAttribute() {
        return assetAttribute;
    }

    public void setAssetAttribute(String assetAttribute) {
        this.assetAttribute = assetAttribute;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public Long getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(Long continueTime) {
        this.continueTime = continueTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "EnvAlarmInfo{" +
                "id='" + id + '\'' +
                ", assetId='" + assetId + '\'' +
                ", assetName='" + assetName + '\'' +
                ", assetAttribute='" + assetAttribute + '\'' +
                ", locationId='" + locationId + '\'' +
                ", pointName='" + pointName + '\'' +
                ", pointCode='" + pointCode + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                ", responseDate=" + responseDate +
                ", continueTime=" + continueTime +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
