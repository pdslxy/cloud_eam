package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version EAM2.0
 * @date 2017年8月10日 14:03:12
 * @Description 巡检标准Vo
 */
@ApiModel(value = "巡检标准")
public class PatrolStandVo implements Serializable {


    @ApiModelProperty(value = "唯一标识")
    private String id;


    /**
     * 巡检编码
     */
    @ApiModelProperty(value = "巡检编码")
    @Length(max = 36, message = "最大长度不能超过36个字符")
    private String patrolStandNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    private String description;

    /**
     * 状态   草稿、活动、不活动
     */
    @ApiModelProperty(value = "状态")
    @Length(max = 36, message = "最大长度不能超过36个字符")
    private String status;

    @ApiModelProperty(value = "状态", hidden = true)
    private String statusName;

    /**
     * 类型
     */

    @ApiModelProperty(value = "类型")
    private String type;


    @ApiModelProperty(value = "类型", hidden = true)
    private String typeName;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private String classstructureid;


    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类描述", hidden = true)
    private String classstructureNum;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类描述", hidden = true)
    private String classstructureDescription;


    /**
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "组织名称", hidden = true)
    private String orgName;

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "站点名称", hidden = true)
    private String siteName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolStandNum() {
        return patrolStandNum;
    }

    public void setPatrolStandNum(String patrolStandNum) {
        this.patrolStandNum = patrolStandNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassstructureid() {
        return classstructureid;
    }

    public String getClassstructureNum() {
        return classstructureNum;
    }

    public void setClassstructureNum(String classstructureNum) {
        this.classstructureNum = classstructureNum;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClassstructureDescription() {
        return classstructureDescription;
    }

    public void setClassstructureDescription(String classstructureDescription) {
        this.classstructureDescription = classstructureDescription;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public String toString() {
        return "PatrolStandVo{" +
                "id='" + id + '\'' +
                ", patrolStandNum='" + patrolStandNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", classstructureid='" + classstructureid + '\'' +
                ", classstructureNum='" + classstructureNum + '\'' +
                ", classstructureDescription='" + classstructureDescription + '\'' +
                ", statusDate=" + statusDate +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUser='" + createUser + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
