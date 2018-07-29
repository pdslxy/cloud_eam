package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年07月27日
 * @Description 二维码管理实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_qr_code_manager")
public class QRCodeManager extends EnerbosBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 二维码管理编码
     */
    @Column(name="quick_response_code_num",nullable = false,length = 255)
    private String quickResponseCodeNum;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 状态，活动/不活动
     */
    @Column(name="status",nullable = true, length = 1)
    private Boolean status;

    /**
     * 状态日期
     */
    @Column(name="status_date",nullable = true,length = 0)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 所属站点编码
     */
    @Column(name = "site_id",nullable = true, length = 36)
    private String siteId;

    /**
     * 所属组织编码
     */
    @Column(name = "org_id", nullable = true,length = 36)
    private String orgId;

    /**
     * 应用程序，功能模块
     */
    @Column(name = "application_id", nullable = true,length = 36)
    private String applicationId;

    /**
     * 是否有数据更新
     */
    @Column(name="data_update",nullable = true, length = 1)
    private Boolean dataUpdate;

    /**
     * 上次生成时间
     */
    @Column(name="last_generate_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastGenerateDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getQuickResponseCodeNum() {
        return quickResponseCodeNum;
    }

    public void setQuickResponseCodeNum(String quickResponseCodeNum) {
        this.quickResponseCodeNum = quickResponseCodeNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
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

    public Boolean getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(Boolean dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Date getLastGenerateDate() {
        return lastGenerateDate;
    }

    public void setLastGenerateDate(Date lastGenerateDate) {
        this.lastGenerateDate = lastGenerateDate;
    }

    @Override
    public String toString() {
        return "QRCodeManager{" +
                "quickResponseCodeNum='" + quickResponseCodeNum + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", statusDate=" + statusDate +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", dataUpdate=" + dataUpdate +
                ", lastGenerateDate=" + lastGenerateDate +
                '}';
    }
}
