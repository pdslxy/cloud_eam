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
@Table(name = "eam_daily_copymeter_detail")
public class DaliyCopyMeterDetail extends EnerbosBaseEntity implements Serializable {

    /**
     * 抄表单
     */
    @Column(name = "copymeter_id", nullable = false, length = 36)
    private String copyMeterId;

    /**
     * 仪表台帐id
     */
    @Column(name = "meter_id", nullable = true, length = 36)
    private String meterId;


    @Column(name = "last_num", nullable = true, length = 36)
    private Double lastNum;


    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_date", nullable = true, length = 20)
    private Date lastDate;

    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_entering_date", nullable = true, length = 20)
    private Date lastEnteringDate;

    @Column(name = "this_num", nullable = true, length = 36)
    private Double thisNum;


    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "this_date", nullable = true, length = 20)
    private Date thisDate;

    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "this_entering_date", nullable = true, length = 20)
    private Date thisEnteringDate;
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

    public DaliyCopyMeterDetail() {
    }

    public DaliyCopyMeterDetail(String copyMeterId, String meterId, String siteId, String orgId, String createUser) {
        this.copyMeterId = copyMeterId;
        this.meterId = meterId;
        this.siteId = siteId;
        this.orgId = orgId;
        this.setCreateUser(createUser);
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

    public String getCopyMeterId() {
        return copyMeterId;
    }

    public void setCopyMeterId(String copyMeterId) {
        this.copyMeterId = copyMeterId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }


    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }


    public Date getThisDate() {
        return thisDate;
    }

    public void setThisDate(Date thisDate) {
        this.thisDate = thisDate;
    }


    public Double getLastNum() {
        return lastNum;
    }

    public void setLastNum(Double lastNum) {
        this.lastNum = lastNum;
    }

    public Date getLastEnteringDate() {
        return lastEnteringDate;
    }

    public void setLastEnteringDate(Date lastEnteringDate) {
        this.lastEnteringDate = lastEnteringDate;
    }

    public Double getThisNum() {
        return thisNum;
    }

    public void setThisNum(Double thisNum) {
        this.thisNum = thisNum;
    }

    public Date getThisEnteringDate() {
        return thisEnteringDate;
    }

    public void setThisEnteringDate(Date thisEnteringDate) {
        this.thisEnteringDate = thisEnteringDate;
    }

    @Override
    public String toString() {
        return "DaliyCopyMeterDetail{" +
                "copyMeterId='" + copyMeterId + '\'' +
                ", meterId='" + meterId + '\'' +
                ", lastNum=" + lastNum +
                ", lastDate=" + lastDate +
                ", lastEnteringDate=" + lastEnteringDate +
                ", thisNum=" + thisNum +
                ", thisDate=" + thisDate +
                ", thisEnteringDate=" + thisEnteringDate +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
