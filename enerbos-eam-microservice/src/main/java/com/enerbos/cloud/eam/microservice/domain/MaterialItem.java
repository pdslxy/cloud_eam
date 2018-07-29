package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月11日11:46:31
 * @Description 物资台账实体类
 */
@Entity
@Table(name = "eam_material_item")
public class MaterialItem extends EnerbosBaseEntity implements Serializable {

    private static final long serialVersionUID = -1205663637397029364L;

    /**
     * 物资编码
     */
    @Column(name = "item_num", nullable = false, length = 20)
    private String itemNum;

    /**
     * 物资描述
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * 品牌
     */
    @Column(name = "brand", nullable = true, length = 50)
    private String brand;

    /**
     * 型号
     */
    @Column(name = "model", nullable = true, length = 50)
    private String model;
    /**
     * 状态 PENDING-暂挂 ACTIVE-活动
     */
    @Column(name = "status", nullable = true)
    private Boolean status;

    /**
     * 是否备件
     */
    @Column(name = "spare_part", nullable = true)
    private Boolean sparePart;

    /**
     * 是否资产
     */
    @Column(name = "asset", nullable = true)
    private Boolean asset;
    /**
     * 是否工具
     */
    @Column(name = "tools", nullable = true)
    private Boolean tools;

    /**
     * 订购单位（个）
     */
    @Column(name = "order_unit", nullable = true, length = 36)
    private String orderUnit;

    /**
     * 发放单位（个）
     */
    @Column(name = "issue_unit", nullable = true, length = 36)
    private String issueUnit;

    /**
     * 对比系数 (非必填)
     */
    @Column(name = "conversion", nullable = true, length = 36)
    private String conversion;
    /**
     * 发放最大数量 (非必填)
     */
    @Column(name = "max_issue", nullable = true, length = 10)
    private String maxIssue;

    /**
     * 所属站点id
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    /**
     * 所属组织id
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;


    @Column(name = "isupdatedata", nullable = true, length = 1)
    private Boolean isUpdateData;

    @Column(name = "qrcodenum", nullable = true, length = 255)
    private String qrCodeNum;


    public MaterialItem() {
    }

    public String getItemNum() {
        return itemNum;
    }


    public Boolean isUpdateData() {
        return isUpdateData;
    }

    public void setUpdateData(Boolean updateData) {
        isUpdateData = updateData;
    }

    public String getQrCodeNum() {
        return qrCodeNum;
    }

    public void setQrCodeNum(String qrCodeNum) {
        this.qrCodeNum = qrCodeNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSparePart() {
        return sparePart;
    }

    public void setSparePart(Boolean sparePart) {
        this.sparePart = sparePart;
    }

    public Boolean getAsset() {
        return asset;
    }

    public void setAsset(Boolean asset) {
        this.asset = asset;
    }

    public Boolean getTools() {
        return tools;
    }

    public void setTools(Boolean tools) {
        this.tools = tools;
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

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    public String getMaxIssue() {
        return maxIssue;
    }

    public void setMaxIssue(String maxIssue) {
        this.maxIssue = maxIssue;
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
        return "MaterialItem{" +
                "itemNum='" + itemNum + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", sparePart=" + sparePart +
                ", asset=" + asset +
                ", tools=" + tools +
                ", orderUnit='" + orderUnit + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", conversion='" + conversion + '\'' +
                ", maxIssue='" + maxIssue + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
