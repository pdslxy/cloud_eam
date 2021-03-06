package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-08-02 15:04
 * @Description 派工工单-执行记录Vo
 */
@ApiModel(value = "流程-执行记录", description = "流程-执行记录Vo")
public class ProcessExecutionRecordVo implements Serializable {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "任务节点名称")
    private String name;

    @ApiModelProperty(value = "负责人")
    private String personName;

    @ApiModelProperty(value = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "持续时间")
    private Long durationInMillis;

    @ApiModelProperty(value = "流程说明（发送流程时填写）")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DispatchWorkOrderFlowRecordVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", personName='" + personName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", durationInMillis=" + durationInMillis +
                ", description='" + description + '\'' +
                '}';
    }
}
