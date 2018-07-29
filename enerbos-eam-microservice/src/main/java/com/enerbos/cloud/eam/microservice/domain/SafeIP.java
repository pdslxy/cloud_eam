package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/17.
 */

@Entity
@Table(name = "eam_ip")
public class SafeIP {
    private static final long serialVersionUID = -1205663637397029364L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;


    /**
     * ip4name
     */
    @Column(name = "ip", nullable = false, length = 200)
    private String ip;


    /**
     * ip状态，可选项：生效/禁用
     */
    @Column(name = "status", length = 10)
    private String status;


    /**
     * 创建人
     */
    @Column(name = "creator", length = 36)
    private String creator;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


    /**
     * 组织ID
     */
    @Column(name = "org_id", nullable = false, length = 36)
    private String orgId;

    /**
     * 站点ID
     */
    @Column(name = "site_id", nullable = false, length = 36)
    private String siteId;


    //产品
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;


    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;


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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SafeIP{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", status='" + status + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate=" + createDate +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", productId='" + productId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
