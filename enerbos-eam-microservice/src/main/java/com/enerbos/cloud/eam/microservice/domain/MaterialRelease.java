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
 * @date 2017年7月11日 下午8:18:12
 * @Description 物资发放实体
 */
@Entity
@Table(name = "eam_material_release")
public class MaterialRelease extends EnerbosBaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3281003557834658020L;

    /**
     * 物资发放编号
     */
    @Column(name = "release_num", nullable = true, length = 36)
    private String releaseNum;

    /**
     * 物资发放描述
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * 发放类型
     */
    @Column(name = "release_type", nullable = true, length = 15)
    private String releaseType;// EAM_ALNDOMAIN

    /**
     * 原库房
     */
    @Column(name = "from_storeroom_id", nullable = true, length = 36)
    private String fromStoreroomId; // EAM_STOREROOM

    /**
     * 目标库房
     */
    @Column(name = "target_storeroom_id", nullable = true, length = 36)
    private String targetStoreroomId; // EAM_STOREROOM

    /**
     * 维保/报修/巡检/巡更等等 工单的id
     */
    @Column(name = "order_num", nullable = true, length = 36)
    private String orderNum;

    /**
     * 工单类型
     */
    @Column(name = "order_type", nullable = true, length = 15)
    private String orderType;

    /**
     * 工单描述
     */
    @Column(name = "order_discription", nullable = true, length = 255)
    private String orderDiscription;

    /**
     * 负责人
     */
    @Column(name = "consuming_people", nullable = true, length = 36)
    private String consumingPeople;

    /**
     * 领用日期
     */
    @Column(name = "shipment_date", nullable = true, length = 20)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shipmentDate;

    /**
     * 成本总计
     */
    @Column(name = "cost_total", nullable = true)
    private Double costTotal;

    /**
     * 状态
     */
    @Column(name = "status", nullable = true, length = 36)
    private String status;

    /**
     * 站点
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    /**
     * 组织
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;

    public String getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getFromStoreroomId() {
        return fromStoreroomId;
    }

    public void setFromStoreroomId(String fromStoreroomId) {
        this.fromStoreroomId = fromStoreroomId;
    }

    public String getTargetStoreroomId() {
        return targetStoreroomId;
    }

    public void setTargetStoreroomId(String targetStoreroomId) {
        this.targetStoreroomId = targetStoreroomId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDiscription() {
        return orderDiscription;
    }

    public void setOrderDiscription(String orderDiscription) {
        this.orderDiscription = orderDiscription;
    }

    public String getConsumingPeople() {
        return consumingPeople;
    }

    public void setConsumingPeople(String consumingPeople) {
        this.consumingPeople = consumingPeople;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Double costTotal) {
        this.costTotal = costTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "MaterialRelease{" +
                "releaseNum='" + releaseNum + '\'' +
                ", description='" + description + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", fromStoreroomId='" + fromStoreroomId + '\'' +
                ", targetStoreroomId='" + targetStoreroomId + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderDiscription='" + orderDiscription + '\'' +
                ", consumingPeople='" + consumingPeople + '\'' +
                ", shipmentDate=" + shipmentDate +
                ", costTotal=" + costTotal +
                ", status='" + status + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
