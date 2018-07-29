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
public class PatrolStandVoForList implements Serializable {


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
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "组织Id")
    private String orgId;

    @ApiModelProperty(value = "站点名称", hidden = true)
    private String siteName;


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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "PatrolStandVoForList{" +
                "id='" + id + '\'' +
                ", patrolStandNum='" + patrolStandNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", statusDate=" + statusDate +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteName='" + siteName + '\'' +
                '}';
    }
}
