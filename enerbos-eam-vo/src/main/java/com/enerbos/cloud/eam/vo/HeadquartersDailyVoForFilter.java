package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
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
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 16:53
 * @Description 例行工作实体类
 */
@ApiModel(value = "例行工作--分页、过滤vo")
public class HeadquartersDailyVoForFilter extends EAMBaseFilterVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划编号")
    private String planNum;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "计划描述")
    private String description;

    @ApiModelProperty(value = "计划性质列表,逗号分隔")
    private List<String> nature;

    @ApiModelProperty(value = "状态列表，逗号分隔")
    private List<String> status;

    @ApiModelProperty(value = "检查项列表，逗号分隔")
    private List<String> checkItem;


    @ApiModelProperty(value = "有效开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartDate;

    @ApiModelProperty(value = "有效结束时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndDate;

    @ApiModelProperty(value = "频率类型  --  工作频率、检查频率")
    private String checkFrequency;

    @ApiModelProperty(value = "执行频次")
    private Long times;

    @ApiModelProperty(value = "执行频率  天、月、年")
    private String frequency;

    @ApiModelProperty(value = "工作类型  空调、强电、弱电")
    private String workType;

    @ApiModelProperty(value = "")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "")
    private Long deadline;
    @ApiModelProperty(value = "总部计划ID")
    private  String headquartersPlanId;

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNature() {
        return nature;
    }

    public void setNature(List<String> nature) {
        this.nature = nature;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(List<String> checkItem) {
        this.checkItem = checkItem;
    }

    public Date getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    public String getCheckFrequency() {
        return checkFrequency;
    }

    public void setCheckFrequency(String checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getHeadquartersPlanId() {
        return headquartersPlanId;
    }

    public void setHeadquartersPlanId(String headquartersPlanId) {
        this.headquartersPlanId = headquartersPlanId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "HeadquartersDailyVoForFilter{" +
                "planNum='" + planNum + '\'' +
                ", planName='" + planName + '\'' +
                ", description='" + description + '\'' +
                ", nature=" + nature +
                ", status=" + status +
                ", checkItem=" + checkItem +
                ", validStartDate=" + validStartDate +
                ", validEndDate=" + validEndDate +
                ", checkFrequency='" + checkFrequency + '\'' +
                ", times=" + times +
                ", frequency='" + frequency + '\'' +
                ", workType='" + workType + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", headquartersPlanId='" + headquartersPlanId + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}