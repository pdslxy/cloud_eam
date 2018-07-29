package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/17
 * @Description
 */
@ApiModel(value = "巡检点记录", description = "巡检点记录对应的Vo")
public class PatrolRecordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检点记录ID")
    private String id;


    @ApiModelProperty(value = "巡检点id")
    private String patrolPointId;

    @ApiModelProperty(value = "巡检点描述")
    private String patrolPointDsr;

    @ApiModelProperty(value = "巡检工单id")
    private String patrolOrderId;

    @ApiModelProperty(value = "是否合格")
    private String isqualified;

    @ApiModelProperty(value = "巡检记录描述")
    private String patrolRecordDsr;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startdate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date enddate;

    @ApiModelProperty(value = "持续时间，（分钟）")
    private Integer duration;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty()
    private List<PatrolRecordTermVo> patrolRecordTermVoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(String patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    public String getPatrolPointDsr() {
        return patrolPointDsr;
    }

    public void setPatrolPointDsr(String patrolPointDsr) {
        this.patrolPointDsr = patrolPointDsr;
    }

    public String getPatrolOrderId() {
        return patrolOrderId;
    }

    public void setPatrolOrderId(String patrolOrderId) {
        this.patrolOrderId = patrolOrderId;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
    }

    public String getPatrolRecordDsr() {
        return patrolRecordDsr;
    }

    public void setPatrolRecordDsr(String patrolRecordDsr) {
        this.patrolRecordDsr = patrolRecordDsr;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PatrolRecordTermVo> getPatrolRecordTermVoList() {
        return patrolRecordTermVoList;
    }

    public void setPatrolRecordTermVoList(List<PatrolRecordTermVo> patrolRecordTermVoList) {
        this.patrolRecordTermVoList = patrolRecordTermVoList;
    }

    @Override
    public String toString() {
        return "PatrolRecordVo{" +
                "id='" + id + '\'' +
                ", patrolPointId='" + patrolPointId + '\'' +
                ", patrolPointDsr='" + patrolPointDsr + '\'' +
                ", patrolOrderId='" + patrolOrderId + '\'' +
                ", isqualified='" + isqualified + '\'' +
                ", patrolRecordDsr='" + patrolRecordDsr + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", duration=" + duration +
                ", remark='" + remark + '\'' +
                ", patrolRecordTermVoList=" + patrolRecordTermVoList +
                '}';
    }
}
