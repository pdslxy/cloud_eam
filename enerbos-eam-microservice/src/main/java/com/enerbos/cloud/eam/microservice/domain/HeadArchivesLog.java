package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/8/9.
 */
@Entity
@Table(name = "eam_archives_log")
public class HeadArchivesLog implements Serializable {
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
     * 档案id
     */
    @Column(name = "archives_id", length = 36)
    private String archivesId;

    /**
     * 负责人
     */
    @Column(name = "principal", length = 32)
    private String principal;
    /**
     * 创建日期
     */
    @Column(name = "create_date", length = 30)
    private Date createDate;
    /**
     * 描述
     */
    @Column(name = "description", length = 200)
    private String description;
    /**
     * 站点
     */
    @Column(name = "site_id", length = 32)
    private String siteId;
    /**
     * 组织
     */
    @Column(name = "org_id", length = 32)
    private String orgId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "HeadArchivesLog{" +
                "id='" + id + '\'' +
                ", archivesId='" + archivesId + '\'' +
                ", principal='" + principal + '\'' +
                ", createDate='" + createDate + '\'' +
                ", description='" + description + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
