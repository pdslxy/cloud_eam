package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
@ApiModel(value = "巡检记录", description = "巡检记录对应的Vo")
public class PatrolRecordTermVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检记录ID")
    private String id;

    @ApiModelProperty(value = "巡检项状态集合")
    private Map statusmap;

    @ApiModelProperty(value = "工单编号")
    private String orderNum;

    @ApiModelProperty(value = "巡检人Id")
    private String excutePersonId;

    @ApiModelProperty(value = "巡检人")
    private String excutePerson;

    @ApiModelProperty(value = "巡检日期")
    private Date updatetime;

    @ApiModelProperty(value = "巡检项描述")
    private String patrolTermDsr;

    @ApiModelProperty(value = "巡检点id")
    private String patrolPointId;

    @ApiModelProperty(value = "是否合格")
    private String isqualified;

    @ApiModelProperty(value = "异常信息描述")
    private String exceptionDsr;

    @ApiModelProperty(value = "序号")
    private String step;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map getStatusmap() {
        return statusmap;
    }

    public void setStatusmap(Map statusmap) {
        this.statusmap = statusmap;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getExcutePersonId() {
        return excutePersonId;
    }

    public void setExcutePersonId(String excutePersonId) {
        this.excutePersonId = excutePersonId;
    }

    public String getExcutePerson() {
        return excutePerson;
    }

    public void setExcutePerson(String excutePerson) {
        this.excutePerson = excutePerson;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPatrolTermDsr() {
        return patrolTermDsr;
    }

    public void setPatrolTermDsr(String patrolTermDsr) {
        this.patrolTermDsr = patrolTermDsr;
    }

    public String getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(String patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
    }

    public String getExceptionDsr() {
        return exceptionDsr;
    }

    public void setExceptionDsr(String exceptionDsr) {
        this.exceptionDsr = exceptionDsr;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolRecordTermVo{" +
                "id='" + id + '\'' +
                ", statusmap=" + statusmap +
                ", orderNum='" + orderNum + '\'' +
                ", excutePersonId='" + excutePersonId + '\'' +
                ", excutePerson='" + excutePerson + '\'' +
                ", updatetime=" + updatetime +
                ", patrolTermDsr='" + patrolTermDsr + '\'' +
                ", patrolPointId='" + patrolPointId + '\'' +
                ", isqualified='" + isqualified + '\'' +
                ", exceptionDsr='" + exceptionDsr + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
