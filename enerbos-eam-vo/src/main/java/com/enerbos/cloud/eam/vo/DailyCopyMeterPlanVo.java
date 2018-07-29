package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年11月16日 16:15:07
 * @Description 抄表计划
 */
@ApiModel(value = "抄表计划")
public class DailyCopyMeterPlanVo extends EAMBaseFilterVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7983360432330979802L;

    @ApiModelProperty(value = "唯一标识")
    private String id;
    /**
     * 抄表编码
     */
    @ApiModelProperty(value = "抄表编码")
    private String copyMeterPlanNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 抄表人
     */
    @ApiModelProperty(value = "抄表人")
    private String copyMeterPerson;

    /**
     * 仪表类型
     */
    @ApiModelProperty(value = "仪表类型")
    private String copyMeterType;

    /**
     * 状态（活动、不活动）
     */
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "创建人Id")
    private String createUser;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    @ApiModelProperty(value = "抄表人名称")
    private String copyMeterPersonName;

    @ApiModelProperty(value = "新增频率的ID")
    private List<DailyCopyMeterPlanRequencyVo> dailyCopyMeterPlanRequencyVos;

    @ApiModelProperty(value = "删除频率的id(多个用,隔开)")
    private List<String> frequencyDeleteIds;

    @ApiModelProperty(value = "新增设备的id(多个用,隔开)")
    private List<DaliyCopyMeterPlanMeterRelationVo> daliyCopyMeterPlanMeterRelationVos;

    @ApiModelProperty(value = "删除设备的id(多个用,隔开)")
    private String meterDeleteIds;

    @ApiModelProperty(value = "仪表集合")
    private List<DailyCopyMeterPlanAssetVo> assetList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCopyMeterPlanNum() {
        return copyMeterPlanNum;
    }

    public void setCopyMeterPlanNum(String copyMeterPlanNum) {
        this.copyMeterPlanNum = copyMeterPlanNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyMeterPerson() {
        return copyMeterPerson;
    }

    public void setCopyMeterPerson(String copyMeterPerson) {
        this.copyMeterPerson = copyMeterPerson;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public List<String> getFrequencyDeleteIds() {
        return frequencyDeleteIds;
    }

    public void setFrequencyDeleteIds(List<String> frequencyDeleteIds) {
        this.frequencyDeleteIds = frequencyDeleteIds;
    }

    public String getMeterDeleteIds() {
        return meterDeleteIds;
    }

    public void setMeterDeleteIds(String meterDeleteIds) {
        this.meterDeleteIds = meterDeleteIds;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public List<DailyCopyMeterPlanRequencyVo> getDailyCopyMeterPlanRequencyVos() {
        return dailyCopyMeterPlanRequencyVos;
    }

    public void setDailyCopyMeterPlanRequencyVos(List<DailyCopyMeterPlanRequencyVo> dailyCopyMeterPlanRequencyVos) {
        this.dailyCopyMeterPlanRequencyVos = dailyCopyMeterPlanRequencyVos;
    }

    public List<DaliyCopyMeterPlanMeterRelationVo> getDaliyCopyMeterPlanMeterRelationVos() {
        return daliyCopyMeterPlanMeterRelationVos;
    }

    public void setDaliyCopyMeterPlanMeterRelationVos(List<DaliyCopyMeterPlanMeterRelationVo> daliyCopyMeterPlanMeterRelationVos) {
        this.daliyCopyMeterPlanMeterRelationVos = daliyCopyMeterPlanMeterRelationVos;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCopyMeterPersonName() {
        return copyMeterPersonName;
    }

    public void setCopyMeterPersonName(String copyMeterPersonName) {
        this.copyMeterPersonName = copyMeterPersonName;
    }

    public List<DailyCopyMeterPlanAssetVo> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<DailyCopyMeterPlanAssetVo> assetList) {
        this.assetList = assetList;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterPlanVo{" +
                "id='" + id + '\'' +
                ", copyMeterPlanNum='" + copyMeterPlanNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterPerson='" + copyMeterPerson + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", status='" + status + '\'' +
                ", copyMeterDate=" + copyMeterDate +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUser='" + createUser + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", copyMeterPersonName='" + copyMeterPersonName + '\'' +
                ", dailyCopyMeterPlanRequencyVos=" + dailyCopyMeterPlanRequencyVos +
                ", frequencyDeleteIds=" + frequencyDeleteIds +
                ", daliyCopyMeterPlanMeterRelationVos=" + daliyCopyMeterPlanMeterRelationVos +
                ", meterDeleteIds='" + meterDeleteIds + '\'' +
                ", assetList=" + assetList +
                '}';
    }
}
