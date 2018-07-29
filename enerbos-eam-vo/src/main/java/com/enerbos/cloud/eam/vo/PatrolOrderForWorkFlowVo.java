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
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/17
 * @Description
 */
@ApiModel(value = "巡检工单", description = "巡检工单-流程VO")
public class PatrolOrderForWorkFlowVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "工单编号")
    private String patrolOrderNum;

    @ApiModelProperty(value = "工单描述")
    private String description;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态日期（前端不展示）")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusdate;

    @ApiModelProperty(value = "计划编码")
    private String patrolPlanNum;

    @ApiModelProperty(value = "所属站点")
    private String siteId;

    @ApiModelProperty(value = "创建人员id")
    private String createPersonId;

    @ApiModelProperty(value = "提报日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty(value = "工程类型")
    private String type;

    @ApiModelProperty(value = "执行人id")
    private String excutePersonId;

    @ApiModelProperty(value = "分派人")
    private String assignPersonId;

    //==============执行汇报=======================
    @ApiModelProperty(value = "实际执行人")
    private String actualExecutorId;

    @ApiModelProperty(value = "实际执行班组")
    private String actualWorkGroup;

    @ApiModelProperty(value = "所属组织")
    private String orgId;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolOrderNum() {
        return patrolOrderNum;
    }

    public void setPatrolOrderNum(String patrolOrderNum) {
        this.patrolOrderNum = patrolOrderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExcutePersonId() {
        return excutePersonId;
    }

    public void setExcutePersonId(String excutePersonId) {
        this.excutePersonId = excutePersonId;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getActualExecutorId() {
        return actualExecutorId;
    }

    public void setActualExecutorId(String actualExecutorId) {
        this.actualExecutorId = actualExecutorId;
    }

    public String getActualWorkGroup() {
        return actualWorkGroup;
    }

    public void setActualWorkGroup(String actualWorkGroup) {
        this.actualWorkGroup = actualWorkGroup;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getPatrolPlanNum() {
        return patrolPlanNum;
    }

    public void setPatrolPlanNum(String patrolPlanNum) {
        this.patrolPlanNum = patrolPlanNum;
    }

    @Override
    public String toString() {
        return "PatrolOrderForWorkFlowVo{" +
                "id='" + id + '\'' +
                ", patrolOrderNum='" + patrolOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusdate=" + statusdate +
                ", patrolPlanNum='" + patrolPlanNum + '\'' +
                ", siteId='" + siteId + '\'' +
                ", createPersonId='" + createPersonId + '\'' +
                ", reportDate=" + reportDate +
                ", type='" + type + '\'' +
                ", excutePersonId='" + excutePersonId + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", actualExecutorId='" + actualExecutorId + '\'' +
                ", actualWorkGroup='" + actualWorkGroup + '\'' +
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
