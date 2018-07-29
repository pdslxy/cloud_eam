package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
        * @author 刘秀朋
        * @version 1.0
        * @date 2017年9月1日 14:46:37
        * @Description 抄表管理
        */
@Entity
@Table(name = "eam_daily_copymeter")
public class DaliyCopyMeter extends EnerbosBaseEntity implements Serializable {

    /**
     * 抄表编码
     */
    @Column(name = "copymeter_num", nullable = false, length = 36)
    private String copyMeterNum;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false, length = 255)
    private String description;


    /**
     * 抄表人
     */
    @Column(name = "copymeter_person", nullable = true, length = 26)
    private String copyMeterPerson;

    /**
     * 仪表类型
     */
    @Column(name = "copymeter_type", nullable = true, length = 255)
    private String copyMeterType;

    /**
     * 工单类型(自动生成\手动创建)
     */
    @Column(name = "copy_meter_order_type", nullable = true, length = 25)
    private String copyMeterOrderType;


    /**
     * 状态（活动、不活动）
     */
    @Column(name = "status", nullable = true, length = 20)
    private String status;

    @Column(name = "copy_meter_plan_id", nullable = true, length = 36)
    private String copyMeterPlanId ;


    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "copymeter_date", nullable = true, length = 20)
    private Date copyMeterDate;


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

    /**
     * 流程
     */
    @Column(name = "process_instance_id", nullable = true, length = 36)
    private String processInstanceId;


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

    public String getCopyMeterNum() {
        return copyMeterNum;
    }

    public void setCopyMeterNum(String copyMeterNum) {
        this.copyMeterNum = copyMeterNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyMeterPerson() {
        return copyMeterPerson;
    }

    public void setCopyMeterPerson(String copyMeterPerson) {
        this.copyMeterPerson = copyMeterPerson;
    }

    public String getCopyMeterType() {
        return copyMeterType;
    }

    public void setCopyMeterType(String copyMeterType) {
        this.copyMeterType = copyMeterType;
    }

    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCopyMeterDate() {
        return copyMeterDate;
    }

    public void setCopyMeterDate(Date copyMeterDate) {
        this.copyMeterDate = copyMeterDate;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCopyMeterOrderType() {
        return copyMeterOrderType;
    }

    public void setCopyMeterOrderType(String copyMeterOrderType) {
        this.copyMeterOrderType = copyMeterOrderType;
    }

    @Override
    public String toString() {
        return "DaliyCopyMeter{" +
                "copyMeterNum='" + copyMeterNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterPerson='" + copyMeterPerson + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", copyMeterOrderType='" + copyMeterOrderType + '\'' +
                ", status='" + status + '\'' +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", copyMeterDate=" + copyMeterDate +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
