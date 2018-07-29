package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年07月07日
 * @Description 维保工单-验收确认Vo
 */
@ApiModel(value = "维保工单-验收确认", description = "维保工单-验收确认")
public class MaintenanceWorkOrderForCheckAcceptVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

	@ApiModelProperty(value = "提报人")
	private String reportId;

	@ApiModelProperty(value = "提报人")
	private String reportName;

	@ApiModelProperty(value = "实际执行负责人")
	private String actualExecutorResponsibleId;

	@ApiModelProperty(value = "实际执行负责人name")
	private String actualExecutorResponsibleName;

    //============== 验收确认 =======================
    @ApiModelProperty(value = "确认解决")
    private Boolean confirm;
    
    @ApiModelProperty(value = "验收时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptionTime;
    
    @ApiModelProperty(value = "验收人")
    private String acceptorId;
    
    @ApiModelProperty(value = "验收人名称")
    private String acceptorName;

    @ApiModelProperty(value = "验收说明")
    private String acceptionDesc;
    
    /**
     * 工单总用时长（系统自动算出结果）--生成时间~关闭时间（分钟）
     */
    @ApiModelProperty(value = "工单总用时长")
    private Long workOrderTotalDuration;

    /**
     * 工单总工时（系统自动算出结果）--本次工时*设备数量（分钟）
     */
    @ApiModelProperty(value = "工单总工时")
    private Double workOrderTotalTime;

    /**
     * 单设备本次工时（系统自动算出结果）--实际执行步骤工时之和（分钟）
     */
    @ApiModelProperty(value = "单设备本次工时")
    private Double singleAssetThistime;
    
    /**
     * 单设备上次工时（系统自动算出结果）--该预防性维护计划上次生成工单的实际工时（分钟）
     */
    @ApiModelProperty(value = "单设备上次工时")
    private Double singleAssetLasttime;

    /**
     * 单设备标准工时（系统自动算出结果）--作业标准步骤工时之和（分钟）
     */
    @ApiModelProperty(value = "单设备标准工时")
    private Double singleAssetNomaltime;
    	//============================================
    @ApiModelProperty(value = "所属组织")
    private String orgId;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;
    
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateDate;
	
	@ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public Date getAcceptionTime() {
		return acceptionTime;
	}

	public void setAcceptionTime(Date acceptionTime) {
		this.acceptionTime = acceptionTime;
	}

	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	public String getAcceptorName() {
		return acceptorName;
	}

	public void setAcceptorName(String acceptorName) {
		this.acceptorName = acceptorName;
	}

	public String getAcceptionDesc() {
		return acceptionDesc;
	}

	public void setAcceptionDesc(String acceptionDesc) {
		this.acceptionDesc = acceptionDesc;
	}

	public Long getWorkOrderTotalDuration() {
		return workOrderTotalDuration;
	}

	public void setWorkOrderTotalDuration(Long workOrderTotalDuration) {
		this.workOrderTotalDuration = workOrderTotalDuration;
	}

	public Double getWorkOrderTotalTime() {
		return workOrderTotalTime;
	}

	public void setWorkOrderTotalTime(Double workOrderTotalTime) {
		this.workOrderTotalTime = workOrderTotalTime;
	}

	public Double getSingleAssetThistime() {
		return singleAssetThistime;
	}

	public void setSingleAssetThistime(Double singleAssetThistime) {
		this.singleAssetThistime = singleAssetThistime;
	}

	public Double getSingleAssetLasttime() {
		return singleAssetLasttime;
	}

	public void setSingleAssetLasttime(Double singleAssetLasttime) {
		this.singleAssetLasttime = singleAssetLasttime;
	}

	public Double getSingleAssetNomaltime() {
		return singleAssetNomaltime;
	}

	public void setSingleAssetNomaltime(Double singleAssetNomaltime) {
		this.singleAssetNomaltime = singleAssetNomaltime;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<WorkFlowImpleRecordVo> getEamImpleRecordVoVoList() {
		return eamImpleRecordVoVoList;
	}

	public void setEamImpleRecordVoVoList(List<WorkFlowImpleRecordVo> eamImpleRecordVoVoList) {
		this.eamImpleRecordVoVoList = eamImpleRecordVoVoList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getActualExecutorResponsibleId() {
		return actualExecutorResponsibleId;
	}

	public void setActualExecutorResponsibleId(String actualExecutorResponsibleId) {
		this.actualExecutorResponsibleId = actualExecutorResponsibleId;
	}

	public String getActualExecutorResponsibleName() {
		return actualExecutorResponsibleName;
	}

	public void setActualExecutorResponsibleName(String actualExecutorResponsibleName) {
		this.actualExecutorResponsibleName = actualExecutorResponsibleName;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForCheckAcceptVo{" +
				"id='" + id + '\'' +
				", status='" + status + '\'' +
				", siteId='" + siteId + '\'' +
				", reportId='" + reportId + '\'' +
				", reportName='" + reportName + '\'' +
				", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
				", actualExecutorResponsibleName='" + actualExecutorResponsibleName + '\'' +
				", confirm=" + confirm +
				", acceptionTime=" + acceptionTime +
				", acceptorId='" + acceptorId + '\'' +
				", acceptorName='" + acceptorName + '\'' +
				", acceptionDesc='" + acceptionDesc + '\'' +
				", workOrderTotalDuration=" + workOrderTotalDuration +
				", workOrderTotalTime=" + workOrderTotalTime +
				", singleAssetThistime=" + singleAssetThistime +
				", singleAssetLasttime=" + singleAssetLasttime +
				", singleAssetNomaltime=" + singleAssetNomaltime +
				", orgId='" + orgId + '\'' +
				", processInstanceId='" + processInstanceId + '\'' +
				", updateDate=" + updateDate +
				", eamImpleRecordVoVoList=" + eamImpleRecordVoVoList +
				'}';
	}
}