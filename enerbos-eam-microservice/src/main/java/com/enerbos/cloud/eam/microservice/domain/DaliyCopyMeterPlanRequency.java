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
@Table(name = "eam_daily_copymeter_plan_requency")
public class DaliyCopyMeterPlanRequency extends EnerbosBaseEntity implements Serializable {

    /**
     * 周期
     */
    @Column(name = "cycle", nullable = false, length = 20)
    private String cycle;

    /**
     * 周期单位
     */
    @Column(name = "cycle_unit", nullable = false, length = 255)
    private String cycleUnit;


    /**
     * 抄表人
     */
    @Column(name = "copymeter_person", nullable = true, length = 255)
    private String copyMeterPerson;

    /**
     * 下次生成日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "next_create_date", nullable = true, length = 20)
    private Date nextCreateDate;


    /**
     * 注意事项
     */
    @Column(name = "mark", nullable = true, length = 255)
    private String mark;

    /**
     * 抄表计划
     */
    @Column(name = "copymeter_plan_id", nullable = true, length = 36)
    private String copyMeterPlanId;


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


    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getCycleUnit() {
        return cycleUnit;
    }

    public void setCycleUnit(String cycleUnit) {
        this.cycleUnit = cycleUnit;
    }

    public String getCopyMeterPerson() {
        return copyMeterPerson;
    }

    public void setCopyMeterPerson(String copyMeterPerson) {
        this.copyMeterPerson = copyMeterPerson;
    }

    public Date getNextCreateDate() {
        return nextCreateDate;
    }

    public void setNextCreateDate(Date nextCreateDate) {
        this.nextCreateDate = nextCreateDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
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
        return "DaliyCopyMeterPlanRequency{" +
                "cycle='" + cycle + '\'' +
                ", cycleUnit='" + cycleUnit + '\'' +
                ", copyMeterPerson='" + copyMeterPerson + '\'' +
                ", nextCreateDate=" + nextCreateDate +
                ", mark='" + mark + '\'' +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }


}
