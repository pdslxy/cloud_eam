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
 * @date 2017年9月1日 14:46:37
 * @Description 抄表管理
 */
@ApiModel(value = "抄表管理")
public class DailyCopyMeterVo extends EAMBaseFilterVo implements Serializable {

    private static final long serialVersionUID = -7983360432330979802L;

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

    /**
     * 抄表人
     */
    @ApiModelProperty(value = "抄表人Id")
    private String copyMeterPerson;

    /**
     * 抄表人
     */
    @ApiModelProperty(value = "抄表人")
    private String copyMeterPersonName;

    @ApiModelProperty(value = "工单类型")
    private String copyMeterOrderType;

    @ApiModelProperty(value = "计划Id")
    private String copyMeterPlanId;


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
     * 状态（活动、不活动）
     */
    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

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

    @ApiModelProperty(value = "创建人")
    private String createUser;


    @ApiModelProperty("新增的仪表Id")
    private List<String> meterAddIds;

    @ApiModelProperty("删除的仪表Id")
    private List<String> meterDeleteIds;

    @ApiModelProperty("抄表记录")
    private List<DailyCopyMeterDetailVo> copyMeterDetailVos;


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCopyMeterOrderType() {
        return copyMeterOrderType;
    }

    public void setCopyMeterOrderType(String copyMeterOrderType) {
        this.copyMeterOrderType = copyMeterOrderType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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


    public List<String> getMeterAddIds() {
        return meterAddIds;
    }

    public void setMeterAddIds(List<String> meterAddIds) {
        this.meterAddIds = meterAddIds;
    }

    public List<String> getMeterDeleteIds() {
        return meterDeleteIds;
    }

    public void setMeterDeleteIds(List<String> meterDeleteIds) {
        this.meterDeleteIds = meterDeleteIds;
    }

    public List<DailyCopyMeterDetailVo> getCopyMeterDetailVos() {
        return copyMeterDetailVos;
    }

    public void setCopyMeterDetailVos(List<DailyCopyMeterDetailVo> copyMeterDetailVos) {
        this.copyMeterDetailVos = copyMeterDetailVos;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCopyMeterPersonName() {
        return copyMeterPersonName;
    }

    public void setCopyMeterPersonName(String copyMeterPersonName) {
        this.copyMeterPersonName = copyMeterPersonName;
    }


    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterVo{" +
                "id='" + id + '\'' +
                ", copyMeterNum='" + copyMeterNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterPerson='" + copyMeterPerson + '\'' +
                ", copyMeterPersonName='" + copyMeterPersonName + '\'' +
                ", copyMeterOrderType='" + copyMeterOrderType + '\'' +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", status='" + status + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", copyMeterDate=" + copyMeterDate +
                ", createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", meterAddIds=" + meterAddIds +
                ", meterDeleteIds=" + meterDeleteIds +
                ", copyMeterDetailVos=" + copyMeterDetailVos +
                '}';
    }
}
