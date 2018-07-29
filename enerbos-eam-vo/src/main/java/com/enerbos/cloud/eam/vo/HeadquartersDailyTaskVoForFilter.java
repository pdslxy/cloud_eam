package com.enerbos.cloud.eam.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/16 12:45
 * @Description:
 */
@ApiModel(value = "例行工作单分页查询，过滤vo")
public class HeadquartersDailyTaskVoForFilter extends EAMBaseFilterVo {

    //任务编号
    @ApiModelProperty(value = "任务编号")
    private String taskNum;

    @ApiModelProperty(value = "任务描述")
    private String description;

    @ApiModelProperty(value = "预计完成时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String estimateDate;

    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;

//    @ApiModelProperty(value = "执行单位")
//    private String executeUnit;

    @ApiModelProperty(value = "状态列表，逗号分隔")
    private List<String> status;

    @ApiModelProperty(value = "检查项列表，逗号分隔")
    private List<String> checkItem;

    @ApiModelProperty(value = "任务属性，逗号分隔")
    private List<String> taskProperty;
    @ApiModelProperty(value = "执行负责人，逗号分隔")
    private List<String> executor;
    @ApiModelProperty(value = "创建日期-开始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateBegin;
    @ApiModelProperty(value = "创建日期-结束")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateEnd;
    @ApiModelProperty(value = "计划完成时间-开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimateDateBegin;
    @ApiModelProperty(value = "计划完成时间-结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimateDateEnd;
    @ApiModelProperty(value = "完成时间-开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualDateBegin;
    @ApiModelProperty(value = "完成时间-结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actualDateEnd;


    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
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

    public List<String> getTaskProperty() {
        return taskProperty;
    }

    public void setTaskProperty(List<String> taskProperty) {
        this.taskProperty = taskProperty;
    }

    public List<String> getExecutor() {
        return executor;
    }

    public void setExecutor(List<String> executor) {
        this.executor = executor;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getEstimateDateBegin() {
        return estimateDateBegin;
    }

    public void setEstimateDateBegin(Date estimateDateBegin) {
        this.estimateDateBegin = estimateDateBegin;
    }

    public Date getEstimateDateEnd() {
        return estimateDateEnd;
    }

    public void setEstimateDateEnd(Date estimateDateEnd) {
        this.estimateDateEnd = estimateDateEnd;
    }

    public Date getActualDateBegin() {
        return actualDateBegin;
    }

    public void setActualDateBegin(Date actualDateBegin) {
        this.actualDateBegin = actualDateBegin;
    }

    public Date getActualDateEnd() {
        return actualDateEnd;
    }

    public void setActualDateEnd(Date actualDateEnd) {
        this.actualDateEnd = actualDateEnd;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "HeadquartersDailyTaskVoForFilter{" +
                "taskNum='" + taskNum + '\'' +
                ", description='" + description + '\'' +
                ", estimateDate='" + estimateDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
