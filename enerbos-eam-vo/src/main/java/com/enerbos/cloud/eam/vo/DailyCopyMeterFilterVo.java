package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class DailyCopyMeterFilterVo extends EAMBaseFilterVo {


    /**
     * 仪表类型
     */
    @ApiModelProperty(value = "仪表类型")
    private String copyMeterType;

    @ApiModelProperty(value = "抄表计划Id")
    private String copyMeterPlanId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建开始时间")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建结束时间")
    private Date endDate;


    @ApiModelProperty(value = "是否收藏")
    private Boolean collect;
    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "抄表开始时间")
    private Date startCopyMeterDate;


    @ApiModelProperty(value = "工单类型")
    private List<String> copyMeterOrderType;
    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "抄表结束时间")
    private Date endCopyMeterDate;

    @ApiModelProperty(value = "状态")
    private List<String> status;

    /**
     * 抄表编码
     */
    @ApiModelProperty(value = "抄表编码")
    private String copyMeterNum;

    public List<String> getCopyMeterOrderType() {
        return copyMeterOrderType;
    }

    public void setCopyMeterOrderType(List<String> copyMeterOrderType) {
        this.copyMeterOrderType = copyMeterOrderType;
    }

    public String getCopyMeterType() {
        return copyMeterType;
    }

    public void setCopyMeterType(String copyMeterType) {
        this.copyMeterType = copyMeterType;
    }

    public Date getStartCopyMeterDate() {
        return startCopyMeterDate;
    }

    public void setStartCopyMeterDate(Date startCopyMeterDate) {
        this.startCopyMeterDate = startCopyMeterDate;
    }

    public Date getEndCopyMeterDate() {
        return endCopyMeterDate;
    }

    public void setEndCopyMeterDate(Date endCopyMeterDate) {
        this.endCopyMeterDate = endCopyMeterDate;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getCopyMeterNum() {
        return copyMeterNum;
    }

    public void setCopyMeterNum(String copyMeterNum) {
        this.copyMeterNum = copyMeterNum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterFilterVo{" +
                "copyMeterType='" + copyMeterType + '\'' +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", collect=" + collect +
                ", startCopyMeterDate=" + startCopyMeterDate +
                ", copyMeterOrderType=" + copyMeterOrderType +
                ", endCopyMeterDate=" + endCopyMeterDate +
                ", status=" + status +
                ", copyMeterNum='" + copyMeterNum + '\'' +
                '}';
    }
}
