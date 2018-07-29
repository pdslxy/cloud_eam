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
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年9月1日 14:46:37
 * @Description 抄表管理
 */
@ApiModel(value = "抄表管理")
public class DailyCopyMeterVoForList implements Serializable {

    @ApiModelProperty(value = "唯一标识")
    private String id;
    /**
     * 抄表编码
     */
    @ApiModelProperty(value = "抄表编码")
    private String copyMeterNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "仪表种类Id")
    private String copyMeterType;

    @ApiModelProperty(value = "仪表种类")
    private String copyMeterTypeName;

    /**
     * 状态（活动、不活动）
     */
    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "抄表计划id为空值时是手动创建工单")
    private String copyMeterPlanId;

    @ApiModelProperty(value = "工单类型")
    private String copyMeterOrderType ;

    @ApiModelProperty(value = "抄表人员Id")
    private String copyMeterPersonId;

    @ApiModelProperty(value = "抄表人员名称")
    private String copyMeterPersonName;

    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "抄表时间")
    private Date copyMeterDate;

    @ApiModelProperty(value = "是否收藏")
    private Boolean collect;

    public String getCopyMeterOrderType() {
        return copyMeterOrderType;
    }

    public void setCopyMeterOrderType(String copyMeterOrderType) {
        this.copyMeterOrderType = copyMeterOrderType;
    }

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

    public String getCopyMeterTypeName() {
        return copyMeterTypeName;
    }

    public void setCopyMeterTypeName(String copyMeterTypeName) {
        this.copyMeterTypeName = copyMeterTypeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
    }

    public Date getCopyMeterDate() {
        return copyMeterDate;
    }

    public void setCopyMeterDate(Date copyMeterDate) {
        this.copyMeterDate = copyMeterDate;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getCopyMeterType() {
        return copyMeterType;
    }

    public void setCopyMeterType(String copyMeterType) {
        this.copyMeterType = copyMeterType;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCopyMeterPersonId() {
        return copyMeterPersonId;
    }

    public void setCopyMeterPersonId(String copyMeterPersonId) {
        this.copyMeterPersonId = copyMeterPersonId;
    }

    public String getCopyMeterPersonName() {
        return copyMeterPersonName;
    }

    public void setCopyMeterPersonName(String copyMeterPersonName) {
        this.copyMeterPersonName = copyMeterPersonName;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterVoForList{" +
                "id='" + id + '\'' +
                ", copyMeterNum='" + copyMeterNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", copyMeterTypeName='" + copyMeterTypeName + '\'' +
                ", status='" + status + '\'' +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", copyMeterDate=" + copyMeterDate +
                ", collect=" + collect +
                '}';
    }
}
