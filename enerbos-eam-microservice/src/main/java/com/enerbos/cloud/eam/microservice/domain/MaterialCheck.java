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
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点实体类
 */
@Entity
@Table(name = "eam_material_check")
public class MaterialCheck extends EnerbosBaseEntity implements
        Serializable {

    private static final long serialVersionUID = -4779851640549697964L;

    /**
     * 盘点单
     */
    @Column(name = "check_num", nullable = true, length = 36)
    private String checkNum;
    /**
     * 盘点单描述
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    /**
     * 库房id
     */
    @Column(name = "storeroom_id", nullable = true, length = 36)
    private String storeroomId;

    /**
     * 盘点负责人
     */
    @Column(name = "check_person", nullable = true, length = 20)
    private String checkPerson;

    /**
     * 状态时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "status_date", nullable = true, length = 20)
    private Date statusDate;

    /**
     * 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     */
    @Column(name = "status", nullable = true, length = 10)
    private String status;

    /**
     * 是否盈亏
     */
    @Column(name = "profit", nullable = true)
    private Boolean profit;


    /**
     * 余量是否调整
     */
    @Column(name = "adjust", nullable = true)
    private Boolean adjust;


    /**
     * 所属组织id
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;

    /**
     * 所属站点id
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    public Boolean getProfit() {
        return profit;
    }

    public Boolean getAdjust() {
        return adjust;
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

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isProfit() {
        return profit;
    }

    public void setProfit(Boolean profit) {
        this.profit = profit;
    }

    public Boolean isAdjust() {
        return adjust;
    }

    public void setAdjust(Boolean adjust) {
        this.adjust = adjust;
    }

    @Override
    public String toString() {
        return "MaterialCheck{" +
                "checkNum='" + checkNum + '\'' +
                ", description='" + description + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", checkPerson='" + checkPerson + '\'' +
                ", statusDate=" + statusDate +
                ", status='" + status + '\'' +
                ", profit=" + profit +
                ", adjust=" + adjust +
                '}';
    }
}
