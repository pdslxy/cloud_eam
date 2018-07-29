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
 * @date 2017年7月11日 下午2:09:54
 * @Description 物资库存实体类
 */
@Entity
@Table(name = "eam_material_inventory")
public class MaterialInventory extends EnerbosBaseEntity implements
        Serializable {

    private static final long serialVersionUID = 3015300515767807931L;

    /**
     * 当前余量
     */
    @Column(name = "current_balance", nullable = true)
    private long currentBalance;
    /**
     * 预留数量
     */
    @Column(name = "reservation_number", nullable = true)
    private long reservationNumber;
    /**
     * 可用数量
     */
    @Column(name = "available_balance", nullable = true)
    private long availableBalance;
    /**
     * 标准成本
     */
    @Column(name = "standard_cost", nullable = true)
    private Double standardCost;
    /**
     * 平均成本
     */
    @Column(name = "average_cost", nullable = true)
    private Double averageCost;
    /**
     * 上次接收成本
     */
    @Column(name = "last_receive_cost", nullable = true)
    private Double lastReceiveCost;

    /**
     * ABC 类型
     */
    @Column(name = "abc_type", nullable = true, length = 10)
    private String abcType;

    /**
     * 重订购
     */
    @Column(name = "reorder", nullable = true)
    private Boolean reorder;

    /**
     * 重订购点
     */
    @Column(name = "reorder_point", nullable = true, length = 10)
    private String reorderPoint;//
    /**
     * 提前时间（天）
     */
    @Column(name = "delivery_time", nullable = true, length = 5)
    private String deliveryTime;//
    /**
     * 安全库存
     */
    @Column(name = "safe_stock", nullable = true, length = 10)
    private String safeStock;
    /**
     * 经济订购量
     */
    @Column(name = "economic_order_quantity", nullable = true, length = 10)
    private String economicOrderQuantity;

    /**
     * 订购单位
     */
    @Column(name = "order_unit", nullable = true, length = 36)
    private String orderUnit;

    /**
     * 发放单位
     */
    @Column(name = "issue_unit", nullable = true, length = 36)
    private String issueUnit;

    /**
     * 发放成本类型
     */
    @Column(name = "cost_type_id", nullable = true, length = 10)
    private String costTypeId;

    /**
     * 状态 PENDING-暂挂 ACTIVE-活动
     */
    @Column(name = "status", nullable = true, length = 36)
    private String status;

    /**
     * 上次发放日期
     */
    @Column(name = "last_issue_date", nullable = true, length = 20)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastIssueDate;

    /**
     * 物资
     */
    @Column(name = "item_id", nullable = true, length = 36)
    private String itemId;

    /**
     * 库房
     */
    @Column(name = "storeroom_id", nullable = true, length = 36)
    private String storeroomId;

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

    /**
     * 是否周转
     */
    @Column(name = "is_turn_over", nullable = true)
    private boolean isTurnOver;

    public MaterialInventory() {
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(Double standardCost) {
        this.standardCost = standardCost;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public Double getLastReceiveCost() {
        return lastReceiveCost;
    }

    public void setLastReceiveCost(Double lastReceiveCost) {
        this.lastReceiveCost = lastReceiveCost;
    }

    public String getAbcType() {
        return abcType;
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType;
    }

    public Boolean getReorder() {
        return reorder;
    }

    public void setReorder(Boolean reorder) {
        this.reorder = reorder;
    }

    public String getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(String reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSafeStock() {
        return safeStock;
    }

    public void setSafeStock(String safeStock) {
        this.safeStock = safeStock;
    }

    public String getEconomicOrderQuantity() {
        return economicOrderQuantity;
    }

    public void setEconomicOrderQuantity(String economicOrderQuantity) {
        this.economicOrderQuantity = economicOrderQuantity;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getIssueUnit() {
        return issueUnit;
    }

    public void setIssueUnit(String issueUnit) {
        this.issueUnit = issueUnit;
    }

    public String getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(String costTypeId) {
        this.costTypeId = costTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastIssueDate() {
        return lastIssueDate;
    }

    public void setLastIssueDate(Date lastIssueDate) {
        this.lastIssueDate = lastIssueDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
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

    public boolean isTurnOver() {
        return isTurnOver;
    }

    public void setTurnOver(boolean turnOver) {
        isTurnOver = turnOver;
    }

    @Override
    public String toString() {
        return "MaterialInventory{" +
                "currentBalance=" + currentBalance +
                ", reservationNumber=" + reservationNumber +
                ", availableBalance=" + availableBalance +
                ", standardCost=" + standardCost +
                ", averageCost=" + averageCost +
                ", lastReceiveCost=" + lastReceiveCost +
                ", abcType='" + abcType + '\'' +
                ", reorder=" + reorder +
                ", reorderPoint='" + reorderPoint + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", safeStock='" + safeStock + '\'' +
                ", economicOrderQuantity='" + economicOrderQuantity + '\'' +
                ", orderUnit='" + orderUnit + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", costTypeId='" + costTypeId + '\'' +
                ", status='" + status + '\'' +
                ", lastIssueDate=" + lastIssueDate +
                ", itemId='" + itemId + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", isTurnOver=" + isTurnOver +
                '}';
    }
}
