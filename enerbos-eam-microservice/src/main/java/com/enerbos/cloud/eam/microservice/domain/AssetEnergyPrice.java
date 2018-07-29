package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/19.
 */

@Entity
@Table(name = "eam_energyprice")
public class AssetEnergyPrice {
    private static final long serialVersionUID = -1205663637397029364L;
    /**
     * id
     *
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 能源价格状态，可选项：生效/禁用
     */
    @Column(name = "status")
    private String status;

    /**
     * 日常价格
     */
    @Column(name = "price")
    private double price;

    /**
     * 波峰价格
     */
    @Column(name = "crest_price")
    private double crestPrice;

    /**
     * 波谷价格
     */
    @Column(name = "trough_price")
    private double troughPrice;

    /**
     * 填报内容的ID
     */
    @Column(name = "fill_form_id")
    private String fillFormId;

    /**
     * 价格单位，如： 元/吨   元/度
     */
    @Column(name = "price_unit")
    private String priceUnit;

    /**
     * 站点id
     */
    @Column(name = "site_id")
    private String siteId;

    /**
     * 组织id
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     *创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFillFormId() {
        return fillFormId;
    }

    public void setFillFormId(String fillFormId) {
        this.fillFormId = fillFormId;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCrestPrice() {
        return crestPrice;
    }

    public void setCrestPrice(double crestPrice) {
        this.crestPrice = crestPrice;
    }

    public double getTroughPrice() {
        return troughPrice;
    }

    public void setTroughPrice(double troughPrice) {
        this.troughPrice = troughPrice;
    }

    @Override
    public String toString() {
        return "MaterialInventoryVoForFilter [id=" + id
                + ", status=" + status + ", price="
                + price + ", crestPrice="
                + crestPrice + ", troughPrice="
                + troughPrice + ", fillFormId=" + fillFormId + ", priceUnit="
                + priceUnit + ", siteId=" + siteId + ", orgId=" + orgId
                + ", creator=" + creator + ", createDate=" + createDate
                + "]";
    }
}
