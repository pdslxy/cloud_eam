package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@ApiModel(value = "巡检工单", description = "巡检工单对应的Vo")
public class PatrolOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检工单ID(新增不需要传值)")
    private String id;

    @ApiModelProperty(value = "巡检工单编码")
    private String patrolOrderNum;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态描述")
    private String statusDescription;


    @ApiModelProperty(value = "巡检类型")
    private String type;

    @ApiModelProperty(value = "巡检类型描述")
    private String typeDescription;

    @ApiModelProperty(value = "状态日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusdate;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @ApiModelProperty(value = "创建日期")
    private String createDate;

    @ApiModelProperty(value = "提报日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty(value = "开始巡检日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginPatrolDate;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatetime;

    @ApiModelProperty(value = "巡检计划编码")
    private String patrolPlanId;

    @ApiModelProperty(value = "巡检计划编码")

    private String patrolPlanNum;
    @ApiModelProperty(value = "巡检计划描述")
    private String patrolPlanDsr;

    @ApiModelProperty(value = "巡检路线编码")
    private String patrolRouteId;

    @ApiModelProperty(value = "巡检路线编码")
    private String patrolRouteNum;

    @ApiModelProperty(value = "巡检路线描述")
    private String patrolRouteDsr;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "站点")
    private String site;

    @ApiModelProperty(value = "组织")
    private String org;

    @ApiModelProperty(value = "创建人员id")
    private String createPersonId;

    @ApiModelProperty(value = "创建人员")
    private String createPerson;

    @ApiModelProperty(value = "提报人员")
    private String reportPersonId;

    @ApiModelProperty(value = "分派人员id")
    private String assignPersonId;
    @ApiModelProperty(value = "分派人员")
    private String assignPerson;

    @ApiModelProperty(value = "执行人员id")
    private String excutePersonId;

    @ApiModelProperty(value = "执行人员")
    private String excutePerson;


    @ApiModelProperty(value = "执行班组")
    private String excuteWorkGroup;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户组")
    private String groupTypeName;

    @ApiModelProperty(value = "用户组")
    private Boolean beginPatrol;

    @ApiModelProperty(value = "是否收藏")
    private boolean collect;

    @ApiModelProperty(value = "备注说明")
    private String excuteRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateDate() {
        if (this.createtime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(this.createtime);
        }
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getAssignPerson() {
        return assignPerson;
    }

    public void setAssignPerson(String assignPerson) {
        this.assignPerson = assignPerson;
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

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public List<WorkFlowImpleRecordVo> getEamImpleRecordVoVoList() {
        return eamImpleRecordVoVoList;
    }

    public void setEamImpleRecordVoVoList(List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList) {
        this.eamImpleRecordVoVoList = eamImpleRecordVoVoList;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getPatrolOrderNum() {
        return patrolOrderNum;
    }

    public void setPatrolOrderNum(String patrolOrderNum) {
        this.patrolOrderNum = patrolOrderNum;
    }

    public String getPatrolPlanNum() {
        return patrolPlanNum;
    }

    public void setPatrolPlanNum(String patrolPlanNum) {
        this.patrolPlanNum = patrolPlanNum;
    }

    public String getPatrolPlanDsr() {
        return patrolPlanDsr;
    }

    public void setPatrolPlanDsr(String patrolPlanDsr) {
        this.patrolPlanDsr = patrolPlanDsr;
    }

    public String getExcuteWorkGroup() {
        return excuteWorkGroup;
    }

    public void setExcuteWorkGroup(String excuteWorkGroup) {
        this.excuteWorkGroup = excuteWorkGroup;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupTypeName() {
        return groupTypeName;
    }

    public void setGroupTypeName(String groupTypeName) {
        this.groupTypeName = groupTypeName;
    }

    public String getPatrolRouteId() {
        return patrolRouteId;
    }

    public void setPatrolRouteId(String patrolRouteId) {
        this.patrolRouteId = patrolRouteId;
    }

    public String getPatrolRouteNum() {
        return patrolRouteNum;
    }

    public void setPatrolRouteNum(String patrolRouteNum) {
        this.patrolRouteNum = patrolRouteNum;
    }

    public String getPatrolRouteDsr() {
        return patrolRouteDsr;
    }

    public void setPatrolRouteDsr(String patrolRouteDsr) {
        this.patrolRouteDsr = patrolRouteDsr;
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getBeginPatrolDate() {
        return beginPatrolDate;
    }

    public void setBeginPatrolDate(Date beginPatrolDate) {
        this.beginPatrolDate = beginPatrolDate;
    }

    public Boolean getBeginPatrol() {
        return beginPatrol;
    }

    public void setBeginPatrol(Boolean beginPatrol) {
        this.beginPatrol = beginPatrol;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getExcuteRemark() {
        return excuteRemark;
    }

    public void setExcuteRemark(String excuteRemark) {
        this.excuteRemark = excuteRemark;
    }

    @Override
    public String toString() {
        return "PatrolOrderVo{" +
                "id='" + id + '\'' +
                ", patrolOrderNum='" + patrolOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusDescription='" + statusDescription + '\'' +
                ", type='" + type + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                ", statusdate=" + statusdate +
                ", createtime=" + createtime +
                ", createDate='" + createDate + '\'' +
                ", reportDate=" + reportDate +
                ", beginPatrolDate=" + beginPatrolDate +
                ", updatetime=" + updatetime +
                ", patrolPlanId='" + patrolPlanId + '\'' +
                ", patrolPlanNum='" + patrolPlanNum + '\'' +
                ", patrolPlanDsr='" + patrolPlanDsr + '\'' +
                ", patrolRouteId='" + patrolRouteId + '\'' +
                ", patrolRouteNum='" + patrolRouteNum + '\'' +
                ", patrolRouteDsr='" + patrolRouteDsr + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", site='" + site + '\'' +
                ", org='" + org + '\'' +
                ", createPersonId='" + createPersonId + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", assignPerson='" + assignPerson + '\'' +
                ", excutePersonId='" + excutePersonId + '\'' +
                ", excutePerson='" + excutePerson + '\'' +
                ", excuteWorkGroup='" + excuteWorkGroup + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", eamImpleRecordVoVoList=" + eamImpleRecordVoVoList +
                ", remark='" + remark + '\'' +
                ", groupTypeName='" + groupTypeName + '\'' +
                ", beginPatrol=" + beginPatrol +
                ", collect=" + collect +
                ", excuteRemark='" + excuteRemark + '\'' +
                '}';
    }
}
