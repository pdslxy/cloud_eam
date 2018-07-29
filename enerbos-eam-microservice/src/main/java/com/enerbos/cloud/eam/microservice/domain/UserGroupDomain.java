package com.enerbos.cloud.eam.microservice.domain;/**
 * Created by enerbos on 2017/8/26.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/26 14:47
 * @Description: 人员组和各种（域）类型的关联关系
 */
@Entity
@Table(name = "eam_usergroup_domain")
public class UserGroupDomain implements Serializable {
    private static final long serialVersionUID = -8413118286974347062L;


    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;
    /**
     * 用户组id
     */
    @Column(name="usergroup_Id",nullable = true,length = 36)
    private String userGroupId;
    /**
     * 域Value
     */
    @Column(name="domain_value",nullable = true,length = 36)
    private String domainValue;

    /**
     *域值Id
     */
    @Column(name="domainvalue_Id",nullable = true,length = 36)
    private String domainValueId;
    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String Description;
    /**
     * 组织
     */
    @Column(name="org_id",nullable = true,length = 36)
    private String orgId;
    /**
     * 站点
     */
    @Column(name="site_id",nullable = true,length = 36)
    private String siteId;
    /**
     * 创建人
     */
    @Column(name="creator",nullable = true,length = 36)
    private String creator;
    /**
     * 创建时间
     */
    @Column(name = "createtime")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;
    /**
     * 状态 活动，不活动
     */
    @Column(name="status",nullable = true)
    private Boolean status;

    /**
     *域编码
     */
    @Column(name="domainNum",nullable = true)
    private String domainNum;
    /**
     *域Id
     */
    @Column(name="domainId",nullable = true)
    private String domainId;
   /**
     * 关联类型
     */
    @Column(name="associationType",nullable = true)
    private String associationType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getDomainValue() {
        return domainValue;
    }

    public void setDomainValue(String domainValue) {
        this.domainValue = domainValue;
    }

    public String getDomainValueId() {
        return domainValueId;
    }

    public void setDomainValueId(String domainValueId) {
        this.domainValueId = domainValueId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getDomainNum() {
        return domainNum;
    }

    public void setDomainNum(String domainNum) {
        this.domainNum = domainNum;
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    @Override
    public String toString() {
        return "UserGroupDomain{" +
                "id='" + id + '\'' +
                ", userGroupId='" + userGroupId + '\'' +
                ", domainValue='" + domainValue + '\'' +
                ", domainValueId='" + domainValueId + '\'' +
                ", Description='" + Description + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", creator='" + creator + '\'' +
                ", createtime=" + createtime +
                ", status=" + status +
                '}';
    }
}
