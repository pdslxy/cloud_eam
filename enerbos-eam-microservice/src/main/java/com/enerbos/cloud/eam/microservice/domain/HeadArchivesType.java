package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/8/9.
 */
@Entity
@Table(name = "eam_archives_type")
public class HeadArchivesType implements Serializable {

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
     * 类型名称
     */
    @Column(name = "type_name", length = 30)
    private String typeName;

    /**
     * 父节点id
     */
    @Column(name = "parent_id", length = 30)
    private String parentId;

    /**
     * 是否有子节点
     */
    @Column(name = "has_child", length = 30)
    private Boolean hasChild;

    /**
     * 是否为根目录
     */
    @Column(name = "is_root", length = 30)
    private Boolean isRoot;

    /**
     * 站点
     */
    @Column(name = "site_id", length = 36)
    private String siteId;
    /**
     * 组织
     */
    @Column(name = "org_id", length = 36)
    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
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
        return "HeadArchivesType{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", hasChild=" + hasChild +
                ", isRoot=" + isRoot +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
