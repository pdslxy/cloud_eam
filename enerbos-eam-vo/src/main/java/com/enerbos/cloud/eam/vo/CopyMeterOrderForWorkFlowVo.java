package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/17
 * @Description
 */
@ApiModel(value = "抄表工单", description = "抄表工单-流程VO")
public class CopyMeterOrderForWorkFlowVo implements Serializable {


    private static final long serialVersionUID = 4092708504449249089L;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "工单编号")
    private String copyMeterNum;

    @ApiModelProperty(value = "工单描述")
    private String description;

    /**
     * 仪表类型
     */
    @ApiModelProperty(value = "仪表类型")
    private String copyMeterType;


    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "抄表时间")
    private Date copyMeterDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "工程类型")
    private String type;

    @ApiModelProperty(value = "抄表计划Id")
    private String copyMeterPlanId;

    /**
     * 抄表人
     */
    @ApiModelProperty(value = "抄表人Id")
    private String copyMeterPerson;


    @ApiModelProperty(value = "所属组织")
    private String orgId;

    @ApiModelProperty(value = "所属站点")
    private String siteId;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCopyMeterNum() {
        return copyMeterNum;
    }

    public void setCopyMeterNum(String copyMeterNum) {
        this.copyMeterNum = copyMeterNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyMeterType() {
        return copyMeterType;
    }

    public void setCopyMeterType(String copyMeterType) {
        this.copyMeterType = copyMeterType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCopyMeterDate() {
        return copyMeterDate;
    }

    public void setCopyMeterDate(Date copyMeterDate) {
        this.copyMeterDate = copyMeterDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCopyMeterPerson() {
        return copyMeterPerson;
    }

    public void setCopyMeterPerson(String copyMeterPerson) {
        this.copyMeterPerson = copyMeterPerson;
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

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
    }

    @Override
    public String toString() {
        return "CopyMeterOrderForWorkFlowVo{" +
                "id='" + id + '\'' +
                ", copyMeterNum='" + copyMeterNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", status='" + status + '\'' +
                ", copyMeterDate=" + copyMeterDate +
                ", createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", type='" + type + '\'' +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", copyMeterPerson='" + copyMeterPerson + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
