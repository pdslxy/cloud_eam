package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 施工单列表过滤条件Vo
 */
@ApiModel(value = "施工单列表过滤条件", description = "施工单列表过滤条件Vo")
public class ConstructionForFilterVo extends EAMBaseFilterVo implements Serializable {

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态id")
    private List<String> status;

    /**
     * 监理单位负责人
     */
    @ApiModelProperty("监理单位负责人")
    private List<String> supervisionId;

    /**
     * 关联的合同id
     */
    @ApiModelProperty("关联的合同id")
    private String contractId;

    /**
     * 施工天气,晴、阴、雨、雪,可通过constructWeather获取域值
     */
    @ApiModelProperty("施工天气,晴、阴、雨、雪,可通过constructWeather获取域值")
    private List<String> constructWeather;

    /**
     * 有无动火
     */
    @ApiModelProperty("有无动火")
    private Boolean isFire;

    /**
     * 有无登高
     */
    @ApiModelProperty("有无登高")
    private Boolean isClimbUp;

    /**
     * 提报人
     */
    @ApiModelProperty("提报人")
    private List<String> reportId;

    /**
     * 提报开始日期
     */
    @ApiModelProperty("提报开始日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportStartDate;

    /**
     * 提报结束日期
     */
    @ApiModelProperty("提报结束日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportEndDate;

    /**
     * 确认开始时间
     */
    @ApiModelProperty("确认开始时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date confirmStartDate;

    /**
     * 确认结束时间
     */
    @ApiModelProperty("确认结束时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date confirmEndDate;

    /**
     * 确认人
     */
    @ApiModelProperty("确认人")
    private List<String> confirmPersonId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人",required = true)
    @Length(max = 36, message = "不能超过36个字符")
    private List<String> createUser;

    /**
     * 创建开始时间
     */
    @ApiModelProperty(value = "创建开始时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createStartDate;

    /**
     * 创建结束时间
     */
    @ApiModelProperty(value = "创建结束时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createEndDate;

    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;

    @ApiModelProperty(value = "人员编号", hidden = true)
    private String personId;

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getSupervisionId() {
        return supervisionId;
    }

    public void setSupervisionId(List<String> supervisionId) {
        this.supervisionId = supervisionId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public List<String> getConstructWeather() {
        return constructWeather;
    }

    public void setConstructWeather(List<String> constructWeather) {
        this.constructWeather = constructWeather;
    }

    public Boolean getFire() {
        return isFire;
    }

    public void setFire(Boolean fire) {
        isFire = fire;
    }

    public Boolean getClimbUp() {
        return isClimbUp;
    }

    public void setClimbUp(Boolean climbUp) {
        isClimbUp = climbUp;
    }

    public List<String> getReportId() {
        return reportId;
    }

    public void setReportId(List<String> reportId) {
        this.reportId = reportId;
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public Date getConfirmStartDate() {
        return confirmStartDate;
    }

    public void setConfirmStartDate(Date confirmStartDate) {
        this.confirmStartDate = confirmStartDate;
    }

    public Date getConfirmEndDate() {
        return confirmEndDate;
    }

    public void setConfirmEndDate(Date confirmEndDate) {
        this.confirmEndDate = confirmEndDate;
    }

    public List<String> getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(List<String> confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
    }

    public List<String> getCreateUser() {
        return createUser;
    }

    public void setCreateUser(List<String> createUser) {
        this.createUser = createUser;
    }

    public Date getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(Date createStartDate) {
        this.createStartDate = createStartDate;
    }

    public Date getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(Date createEndDate) {
        this.createEndDate = createEndDate;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "ConstructionForFilterVo{" +
                "status=" + status +
                ", supervisionId=" + supervisionId +
                ", contractId='" + contractId + '\'' +
                ", constructWeather=" + constructWeather +
                ", isFire=" + isFire +
                ", isClimbUp=" + isClimbUp +
                ", reportId=" + reportId +
                ", reportStartDate=" + reportStartDate +
                ", reportEndDate=" + reportEndDate +
                ", confirmStartDate=" + confirmStartDate +
                ", confirmEndDate=" + confirmEndDate +
                ", confirmPersonId=" + confirmPersonId +
                ", createUser=" + createUser +
                ", createStartDate=" + createStartDate +
                ", createEndDate=" + createEndDate +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                '}'+super.toString();
    }
}