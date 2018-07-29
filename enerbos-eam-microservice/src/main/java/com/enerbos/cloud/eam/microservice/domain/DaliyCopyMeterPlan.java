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
 * @date 2017年11月16日 11:12:17
 * @Description 抄表计划
 */
@Entity
@Table(name = "eam_daily_copymeter_plan")
public class DaliyCopyMeterPlan extends EnerbosBaseEntity implements Serializable {

    /**
     * 抄表编码
     */
    @Column(name = "copymeter_plan_num", nullable = false, length = 36)
    private String copyMeterPlanNum;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false, length = 255)
    private String description;


    /**
     * 抄表人
     */
    @Column(name = "copymeter_person", nullable = true, length = 255)
    private String copyMeterPerson;

    /**
     * 仪表类型
     */
    @Column(name = "copymeter_type", nullable = true, length = 255)
    private String copyMeterType;


    /**
     * 状态（活动、不活动）
     */
    @Column(name = "status", nullable = true,length = 25)
    private String status;

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


    public String getCopyMeterPlanNum() {
        return copyMeterPlanNum;
    }

    public void setCopyMeterPlanNum(String copyMeterPlanNum) {
        this.copyMeterPlanNum = copyMeterPlanNum;
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
        return "DaliyCopyMeterPlan{" +
                "copyMeterPlanNum='" + copyMeterPlanNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterPerson='" + copyMeterPerson + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", status='" + status + '\'' +
                ", copyMeterDate=" + copyMeterDate +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
