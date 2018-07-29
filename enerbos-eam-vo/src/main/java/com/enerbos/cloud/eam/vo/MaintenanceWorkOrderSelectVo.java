package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.eam.contants.WorkOrderCommon;
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
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 维保工单模糊搜索Vo
 */
@ApiModel(value = "维保工单模糊搜索", description = "维保工单的模糊搜索VO")
public class MaintenanceWorkOrderSelectVo extends EAMBaseFilterVo {

    @ApiModelProperty(value = "工程类型")
    private List<String> projectType;

    @ApiModelProperty(value = "状态")
    private List<String> status;

    @ApiModelProperty(value = "工单类型")
    private List<String> workType;

//    @ApiModelProperty(value = "事件级别")
//    private String incidentLevel;

    @ApiModelProperty("位置ID")
    private String locationId;

    @ApiModelProperty(value = "所属站点编码", hidden = true)
    private String siteId;

    @ApiModelProperty(value = "是否外委")
    private Boolean udisww;

//    @ApiModelProperty(value = "是否挂起")
//    private Boolean suspension;
//
//    @ApiModelProperty(value = "挂起类型")
//    private String suspensionType;

    @ApiModelProperty(value = "所属组织", hidden = true)
    private String orgId;

//    @ApiModelProperty(value = "是否委托执行")
//    private Boolean entrustExecute;

    @ApiModelProperty(value = "执行是否超时")
    private Boolean executeWhetherTimeout;

    @ApiModelProperty(value = "提报人")
    private List<String> reportId;

    @ApiModelProperty(value = "提报日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportStartDate;

    @ApiModelProperty(value = "提报日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportEndDate;

    @ApiModelProperty(value = "实际执行人")
    private List<String> actualExecutorId;

    @ApiModelProperty(value = "验收日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acceptionStartTime;

    @ApiModelProperty(value = "验收日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acceptionEndTime;

    @ApiModelProperty(value = "模糊搜索关键词")
    private String words;

    @ApiModelProperty(value = "模糊搜索关键词，分词", hidden = true)
    private List<String> wordsList;

    @ApiModelProperty(value = "合同id")
    private String contractId;

    @ApiModelProperty(value = "报修工单id")
    private String repairId;

    @ApiModelProperty(value = "是否是父工单", hidden = true)
    private Boolean whetherParentWorkOrder;

	@ApiModelProperty(value = "预防维护编号",hidden=true)
	private String maintenancePlanNum;

	@ApiModelProperty(value = "是否收藏 true：是")
	private Boolean collect;
	@ApiModelProperty(value = "人员编号", hidden = true)
	private String personId;

    @ApiModelProperty(value = "实际执行人保存类型", hidden = true)
    private String actualExecutorFieldType= WorkOrderCommon.WORK_ORDER_PERSON_ACTUAL_EXECUTION;

	public String getSiteId() {
		return siteId;
	}

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Boolean getUdisww() {
        return udisww;
    }

    public void setUdisww(Boolean udisww) {
        this.udisww = udisww;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Boolean getExecuteWhetherTimeout() {
        return executeWhetherTimeout;
    }

    public void setExecuteWhetherTimeout(Boolean executeWhetherTimeout) {
        this.executeWhetherTimeout = executeWhetherTimeout;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public List<String> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<String> wordsList) {
        this.wordsList = wordsList;
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

    public Boolean getWhetherParentWorkOrder() {
        return whetherParentWorkOrder;
    }

    public void setWhetherParentWorkOrder(Boolean whetherParentWorkOrder) {
        this.whetherParentWorkOrder = whetherParentWorkOrder;
    }

    public List<String> getProjectType() {
        return projectType;
    }

    public void setProjectType(List<String> projectType) {
        this.projectType = projectType;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getWorkType() {
        return workType;
    }

    public void setWorkType(List<String> workType) {
        this.workType = workType;
    }

    public List<String> getReportId() {
        return reportId;
    }

    public void setReportId(List<String> reportId) {
        this.reportId = reportId;
    }

    public Date getAcceptionStartTime() {
        return acceptionStartTime;
    }

    public void setAcceptionStartTime(Date acceptionStartTime) {
        this.acceptionStartTime = acceptionStartTime;
    }

    public Date getAcceptionEndTime() {
        return acceptionEndTime;
    }

    public void setAcceptionEndTime(Date acceptionEndTime) {
        this.acceptionEndTime = acceptionEndTime;
    }

    public String getMaintenancePlanNum() {
        return maintenancePlanNum;
    }

    public void setMaintenancePlanNum(String maintenancePlanNum) {
        this.maintenancePlanNum = maintenancePlanNum;
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

    public List<String> getActualExecutorId() {
        return actualExecutorId;
    }

    public void setActualExecutorId(List<String> actualExecutorId) {
        this.actualExecutorId = actualExecutorId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getActualExecutorFieldType() {
        return actualExecutorFieldType;
    }

    public void setActualExecutorFieldType(String actualExecutorFieldType) {
        this.actualExecutorFieldType = actualExecutorFieldType;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "MaintenanceWorkOrderSelectVo{" +
                "projectType=" + projectType +
                ", status=" + status +
                ", workType=" + workType +
                ", locationId='" + locationId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", udisww=" + udisww +
                ", orgId='" + orgId + '\'' +
                ", executeWhetherTimeout=" + executeWhetherTimeout +
                ", reportId=" + reportId +
                ", reportStartDate=" + reportStartDate +
                ", reportEndDate=" + reportEndDate +
                ", actualExecutorId=" + actualExecutorId +
                ", acceptionStartTime=" + acceptionStartTime +
                ", acceptionEndTime=" + acceptionEndTime +
                ", words='" + words + '\'' +
                ", wordsList=" + wordsList +
                ", contractId='" + contractId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", whetherParentWorkOrder=" + whetherParentWorkOrder +
                ", maintenancePlanNum='" + maintenancePlanNum + '\'' +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                ", actualExecutorFieldType='" + actualExecutorFieldType + '\'' +
                '}';
    }
}