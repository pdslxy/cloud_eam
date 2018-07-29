package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * @date 2017年06月07日
 * @Description 预防性维护Vo
 */
@ApiModel(value = "预防性维护过滤条件Vo", description = "预防性维护过滤条件VO")
public class MaintenanceMaintenancePlanSelectVo extends EAMBaseFilterVo {

    /**
     * 作业标准ID
     */
    @ApiModelProperty(value = "作业标准ID",hidden = true)
    private String jobStandardId;

    /**
     * 工程类型id
     */
    @ApiModelProperty(value = "工程类型id")
    private List<String> projectType;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

    /**
     * 状态
     * DRAFT-草稿
     * ACTIVE-活动
     * INACTIVE-不活动
     */
    @ApiModelProperty(value = "状态id")
    private List<String> status;

    /**
     * 生成的工单状态,暂时默认到分派状态
     */
    @ApiModelProperty(value = "生成的工单状态,暂时默认到分派状态")
    private List<String> workOrderStatus;

    /**
     * 责任人id
     */
    @ApiModelProperty(value = "责任人id")
    private List<String> personliableId;

    /**
     * 分派人id
     */
    @ApiModelProperty(value = "分派人id")
    private List<String> assignPersonId;

    /**
     * 工单类型
     * 在此预防性维护计划中生成的工单分类或类型
     */
    @ApiModelProperty(value = "工单类型",required = true)
    @NotEmpty(message = "工单类型不能为空")
    private List<String> workOrderType;

    @ApiModelProperty(value = "合同id")
    private String contractId;

    @ApiModelProperty(value = "所属组织")
    private String orgId;

	@ApiModelProperty(value = "上次开始时间开始日期")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastStartDateStartDate;

	@ApiModelProperty(value = "上次开始时间开始日期")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastStartDateEndDate;

    @ApiModelProperty(value = "上次完成时间开始日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastEndDateStartDate;

    @ApiModelProperty(value = "上次完成时间结束日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastEndDateEndDate;

	@ApiModelProperty(value = "是否外委")
	private Boolean udisww;

    @ApiModelProperty(value = "模糊搜索关键词")
    private String words;

    @ApiModelProperty(value = "模糊搜索关键词数组", hidden = true)
    private List<String> wordsList;

	@ApiModelProperty(value = "是否收藏 true：是")
	private Boolean collect;
	@ApiModelProperty(value = "人员编号", hidden = true)
	private String personId;

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public List<String> getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(List<String> workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public List<String> getPersonliableId() {
        return personliableId;
    }

    public void setPersonliableId(List<String> personliableId) {
        this.personliableId = personliableId;
    }

    public List<String> getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(List<String> assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public List<String> getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(List<String> workOrderType) {
        this.workOrderType = workOrderType;
    }

    public Date getLastStartDateStartDate() {
        return lastStartDateStartDate;
    }

    public void setLastStartDateStartDate(Date lastStartDateStartDate) {
        this.lastStartDateStartDate = lastStartDateStartDate;
    }

    public Date getLastStartDateEndDate() {
        return lastStartDateEndDate;
    }

    public void setLastStartDateEndDate(Date lastStartDateEndDate) {
        this.lastStartDateEndDate = lastStartDateEndDate;
    }

    public Date getLastEndDateStartDate() {
        return lastEndDateStartDate;
    }

    public void setLastEndDateStartDate(Date lastEndDateStartDate) {
        this.lastEndDateStartDate = lastEndDateStartDate;
    }

    public Date getLastEndDateEndDate() {
        return lastEndDateEndDate;
    }

    public void setLastEndDateEndDate(Date lastEndDateEndDate) {
        this.lastEndDateEndDate = lastEndDateEndDate;
    }

    public Boolean getUdisww() {
        return udisww;
    }

    public void setUdisww(Boolean udisww) {
        this.udisww = udisww;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getJobStandardId() {
        return jobStandardId;
    }

    public void setJobStandardId(String jobStandardId) {
        this.jobStandardId = jobStandardId;
    }

    @Override
    public String toString() {
        return "MaintenanceMaintenancePlanSelectVo{" +
                "jobStandardId='" + jobStandardId + '\'' +
                ", projectType=" + projectType +
                ", siteId='" + siteId + '\'' +
                ", status=" + status +
                ", workOrderStatus=" + workOrderStatus +
                ", personliableId=" + personliableId +
                ", assignPersonId=" + assignPersonId +
                ", workOrderType=" + workOrderType +
                ", contractId='" + contractId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", lastStartDateStartDate=" + lastStartDateStartDate +
                ", lastStartDateEndDate=" + lastStartDateEndDate +
                ", lastEndDateStartDate=" + lastEndDateStartDate +
                ", lastEndDateEndDate=" + lastEndDateEndDate +
                ", udisww=" + udisww +
                ", words='" + words + '\'' +
                ", wordsList=" + wordsList +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                '}';
    }
}
