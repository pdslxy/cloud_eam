package com.enerbos.cloud.eam.vo;

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
 * @Description
 */
@ApiModel(value = "总部计划，批量派工单vo")
public class HeadquartersDailyTaskVoForBatchLabor implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总部计划主键ID集合")
    private List<String> ids;

    /**
     * 任务编码
     */
    @ApiModelProperty(value = "任务编码")
    private String taskNum;

    /**
     * 分派待确认、待分派、执行待确认、待执行、已完成
     */
    @ApiModelProperty(value = "分派待确认、待分派、执行待确认、待执行、已完成")
    private String status;

    /**
     * 计划编码
     */
    @ApiModelProperty(value = "计划编码")
    private String planNum;
    /**
     * 计划名称
     */
    @ApiModelProperty(value = " 计划名称")
    private String planName;
    /**
     * 描述
     */
    @ApiModelProperty(value = "总部计划描述")
    private String description;

    /**
     * 检查项
     */
    @ApiModelProperty(value = " 检查项")
    private String checkItem;

    /**
     * 组织id
     */
    @ApiModelProperty(value = " 组织id")
    private String orgId;

    /**
     * 站点id
     */
    @ApiModelProperty(value = " 站点id")
    private String siteId;

    /**
     * 任务属性   标准、临时
     */
    @ApiModelProperty(value = " 任务属性   标准、临时")
    private String taskProperty;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 预计完成时间
     */
    @ApiModelProperty(value = "预计完成时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimateDate;

    @ApiModelProperty(value = "提报人ID")
    private String reportPersonId;
    @ApiModelProperty(value = "提报人名称")
    private String reportPersonName;
    @ApiModelProperty(value = "提报人电话")
    private String reportPersonTel;
    /**
     * 总结
     */
    @ApiModelProperty(value = "总结")
    private String summary;

    @ApiModelProperty(value = "需求部门")
    private String demandDept;
    @ApiModelProperty(value = "需求人")
    private String demandPerson;
    @ApiModelProperty(value = "联系方式")
    private String demandPersonTel;


    @ApiModelProperty(value = "总部计划总条数")
    private Integer total;

    @ApiModelProperty(value = "派工单编号集合")
    private List<String> workOrderNumList;

    public String getDemandDept() {
        return demandDept;
    }

    public void setDemandDept(String demandDept) {
        this.demandDept = demandDept;
    }

    public String getDemandPerson() {
        return demandPerson;
    }

    public void setDemandPerson(String demandPerson) {
        this.demandPerson = demandPerson;
    }

    public String getDemandPersonTel() {
        return demandPersonTel;
    }

    public void setDemandPersonTel(String demandPersonTel) {
        this.demandPersonTel = demandPersonTel;
    }

    public List<String> getWorkOrderNumList() {
        return workOrderNumList;
    }

    public void setWorkOrderNumList(List<String> workOrderNumList) {
        this.workOrderNumList = workOrderNumList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }

    public String getReportPersonTel() {
        return reportPersonTel;
    }

    public void setReportPersonTel(String reportPersonTel) {
        this.reportPersonTel = reportPersonTel;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }


    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum == null ? null : taskNum.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum == null ? null : planNum.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem == null ? null : checkItem.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public String getTaskProperty() {
        return taskProperty;
    }

    public void setTaskProperty(String taskProperty) {
        this.taskProperty = taskProperty == null ? null : taskProperty.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }



}