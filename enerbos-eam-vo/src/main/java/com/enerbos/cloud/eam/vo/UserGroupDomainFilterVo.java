package com.enerbos.cloud.eam.vo;/**
 * Created by enerbos on 2017/8/26.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/26 14:47
 * @Description: 用户组和各种（域）类型的关联关系
 */
@ApiModel(value = "用户组和域值得数据", description = "用户组和域值查过滤条件")
public class UserGroupDomainFilterVo extends EAMBaseFilterVo {

    /**
     * id
     */
    @ApiModelProperty(value = "关联id")
    private String id;
    /**
     * 用户组id
     */
    @ApiModelProperty(value = "用户组id")
    private String userGroupId;

    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用户组名称")
    private String userGroupName;
    /**
     * 域Value
     */
    @ApiModelProperty(value = "域值value")
    private String domainValue;

    /**
     *域值ID
     */
    @ApiModelProperty(value = "域值Id")
    private String domainValueId;

    /**
     *域值说明
     */
    @ApiModelProperty(value = "域值说明")
    private String domainValueDescription;

    /**
     *域名称
     */
    @ApiModelProperty(value = "域名称")
    private String domainName;

    /**
     *域Num
     */
    @ApiModelProperty(value = "域Num")
    private  String  domainNum;
    /**
     * 描述
     */
    @ApiModelProperty(value = "用户组域关联说明，增加修改传值")
    private String description;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织Id")
    private String orgId;
    /**
     * 站点
     */
    @ApiModelProperty(value = "站点Id")
    private String siteId;

    /**
     * 状态 活动，不活动
     */
    @ApiModelProperty(value = "状态，true：活动， false:不活动")
    private  Boolean status;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "关联类型，用于同一域和域值关联不同的用户组，增加修改传值如1to1，请传ALL，同一域，域值不能有重复的关联类型")
    private String associationType;

    public String getDomainNum() {
        return domainNum;
    }

    public void setDomainNum(String domainNum) {
        this.domainNum = domainNum;
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

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
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

    public String getDomainValueDescription() {
        return domainValueDescription;
    }

    public void setDomainValueDescription(String domainValueDescription) {
        this.domainValueDescription = domainValueDescription;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    @Override
    public String toString() {
        return "UserGroupDomainFilterVo{" +
                "id='" + id + '\'' +
                ", userGroupId='" + userGroupId + '\'' +
                ", userGroupName='" + userGroupName + '\'' +
                ", domainValue='" + domainValue + '\'' +
                ", domainValueId='" + domainValueId + '\'' +
                ", domainValueDescription='" + domainValueDescription + '\'' +
                ", domainName='" + domainName + '\'' +
                ", description='" + description + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
