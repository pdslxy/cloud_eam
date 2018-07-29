package com.enerbos.cloud.eam.vo;

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
 * @date 2017/8/7
 * @Description
 */
@ApiModel(value = "巡检工单", description = "巡检工单对应的Vo")
public class PatrolOrderForSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检点ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "创建时间(新增不需要传值)")
    private Date createtime;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "编码")
    private String patrolOrderNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "巡检状态")
    private String status;

    @ApiModelProperty(value = "状态日期")
    private Date statusdate;


    @ApiModelProperty(value = "巡检类型")
    private String type;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "巡检计划编码")
    private String patrolPlanId;

    @ApiModelProperty(value = "巡检路线编码")
    private String patrolRouteId;

    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "创建人员id")
    private String createPersonId;

    @ApiModelProperty(value = "分派人员id")
    private String assignPersonId;

    @ApiModelProperty(value = "执行人员id")
    private String excutePersonId;

    @ApiModelProperty(value = "巡检记录集合")
    private List<PatrolRecordTermForOrderSaveVo> recordTermList;

    @ApiModelProperty(value = "开始日期")
    private Date startdate;

    @ApiModelProperty(value = "结束日期")
    private Date enddate;

    @ApiModelProperty(value = "持续时间(分钟)")
    private Integer duration;

    @ApiModelProperty(value = "巡检记录ID")
    private String patrolRecordId;

    @ApiModelProperty(value = "序号")
    private String step;

    @ApiModelProperty(value = "备注说明")
    private String excuteRemark;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

    public PatrolOrderForSaveVo() {
    }

    public PatrolOrderForSaveVo(String id, String description, String patrolOrderNum, String remark, String status, Date statusdate, String type, String patrolPlanId, String siteId, String orgId, String createPersonId, String assignPersonId, String excutePersonId) {
        this.id = id;
        this.description = description;
        this.patrolOrderNum = patrolOrderNum;
        this.remark = remark;
        this.status = status;
        this.statusdate = statusdate;
        this.type = type;
        this.patrolPlanId = patrolPlanId;
        this.siteId = siteId;
        this.orgId = orgId;
        this.createPersonId = createPersonId;
        this.assignPersonId = assignPersonId;
        this.excutePersonId = excutePersonId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatrolOrderNum() {
        return patrolOrderNum;
    }

    public void setPatrolOrderNum(String patrolOrderNum) {
        this.patrolOrderNum = patrolOrderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPatrolPlanId() {
        return patrolPlanId;
    }

    public void setPatrolPlanId(String patrolPlanId) {
        this.patrolPlanId = patrolPlanId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getExcutePersonId() {
        return excutePersonId;
    }

    public void setExcutePersonId(String excutePersonId) {
        this.excutePersonId = excutePersonId;
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

    public List<PatrolRecordTermForOrderSaveVo> getRecordTermList() {
        return recordTermList;
    }

    public void setRecordTermList(List<PatrolRecordTermForOrderSaveVo> recordTermList) {
        this.recordTermList = recordTermList;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getPatrolRouteId() {
        return patrolRouteId;
    }

    public void setPatrolRouteId(String patrolRouteId) {
        this.patrolRouteId = patrolRouteId;
    }

    public String getPatrolRecordId() {
        return patrolRecordId;
    }

    public void setPatrolRecordId(String patrolRecordId) {
        this.patrolRecordId = patrolRecordId;
    }

    public String getExcuteRemark() {
        return excuteRemark;
    }

    public void setExcuteRemark(String excuteRemark) {
        this.excuteRemark = excuteRemark;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "PatrolOrderForSaveVo{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", patrolOrderNum='" + patrolOrderNum + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", statusdate=" + statusdate +
                ", type='" + type + '\'' +
                ", updatetime=" + updatetime +
                ", patrolPlanId='" + patrolPlanId + '\'' +
                ", patrolRouteId='" + patrolRouteId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createPersonId='" + createPersonId + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", excutePersonId='" + excutePersonId + '\'' +
                ", recordTermList=" + recordTermList +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", duration=" + duration +
                ", patrolRecordId='" + patrolRecordId + '\'' +
                ", step='" + step + '\'' +
                ", excuteRemark='" + excuteRemark + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
