package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 16:44
 * @Description 报修工单列表过滤条件VO
 */
@ApiModel(value = "报修工单列表过滤条件", description = "报修工单列表过滤条件VO")
public class RepairOrderListFilterVo extends EAMBaseFilterVo {
    @ApiModelProperty(value = "模糊查询")
    private List<String> fuzzy;
    @ApiModelProperty(value = "工单编号")
    private String workOrderNum;
    @ApiModelProperty(value = "报修描述")
    private String description;
    @ApiModelProperty(value = "工程类型(逗号分隔)")
    private List<String> projectType;
    @ApiModelProperty(value = "工单来源(逗号分隔)")
    private List<String> workOrderSource;
    @ApiModelProperty(value = "事件性质(逗号分隔)")
    private List<String> incidentNature;
    @ApiModelProperty(value = "事件级别(逗号分隔)")
    private List<String> incidentLevel;
    @ApiModelProperty(value = "是否超时 true：是")
    private Boolean executeTimeout;
    @ApiModelProperty(value = "工单状态(逗号分隔)")
    private List<String> workOrderStatus;
    @ApiModelProperty(value = "报修人(模糊查询)")
    private String repairPerson;
    @ApiModelProperty(value = "报修部门(模糊查询)")
    private String repairDept;
    @ApiModelProperty(value = "提报人(逗号分隔)")
    private List<String> reportPersonId;
    @ApiModelProperty(value = "实际执行人(逗号分隔)")
    private List<String> actualExecutionPersonId;
    @ApiModelProperty(value = "实际执行人字段类型(工单关联人员)", hidden = true)
    private String actualExecutionPersonFieldType;
    @ApiModelProperty(value = "提报日期-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportDateBegin;
    @ApiModelProperty(value = "提报日期-截止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportDateEnd;
    @ApiModelProperty(value = "验收时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date acceptTimeBegin;
    @ApiModelProperty(value = "验收时间-截止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date acceptTimeEnd;
    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;
    @ApiModelProperty(value = "人员编号", hidden = true)
    private String personId;
    @ApiModelProperty(value = "工号", hidden = true)
    private String jobNumber;
    @ApiModelProperty(value = "工单创建人ID")
    private String createUser;
    @ApiModelProperty(value = "关联设备ID")
    private String refAssetId;

    public List<String> getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(List<String> fuzzy) {
        this.fuzzy = fuzzy;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProjectType() {
        return projectType;
    }

    public void setProjectType(List<String> projectType) {
        this.projectType = projectType;
    }

    public List<String> getWorkOrderSource() {
        return workOrderSource;
    }

    public void setWorkOrderSource(List<String> workOrderSource) {
        this.workOrderSource = workOrderSource;
    }

    public List<String> getIncidentNature() {
        return incidentNature;
    }

    public void setIncidentNature(List<String> incidentNature) {
        this.incidentNature = incidentNature;
    }

    public List<String> getIncidentLevel() {
        return incidentLevel;
    }

    public void setIncidentLevel(List<String> incidentLevel) {
        this.incidentLevel = incidentLevel;
    }

    public Boolean getExecuteTimeout() {
        return executeTimeout;
    }

    public void setExecuteTimeout(Boolean executeTimeout) {
        this.executeTimeout = executeTimeout;
    }

    public List<String> getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(List<String> workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(String repairPerson) {
        this.repairPerson = repairPerson;
    }

    public String getRepairDept() {
        return repairDept;
    }

    public void setRepairDept(String repairDept) {
        this.repairDept = repairDept;
    }

    public List<String> getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(List<String> reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public List<String> getActualExecutionPersonId() {
        return actualExecutionPersonId;
    }

    public void setActualExecutionPersonId(List<String> actualExecutionPersonId) {
        this.actualExecutionPersonId = actualExecutionPersonId;
    }

    public String getActualExecutionPersonFieldType() {
        return actualExecutionPersonFieldType;
    }

    public void setActualExecutionPersonFieldType(String actualExecutionPersonFieldType) {
        this.actualExecutionPersonFieldType = actualExecutionPersonFieldType;
    }

    public Date getReportDateBegin() {
        return reportDateBegin;
    }

    public void setReportDateBegin(Date reportDateBegin) {
        this.reportDateBegin = reportDateBegin;
    }

    public Date getReportDateEnd() {
        return reportDateEnd;
    }

    public void setReportDateEnd(Date reportDateEnd) {
        this.reportDateEnd = reportDateEnd;
    }

    public Date getAcceptTimeBegin() {
        return acceptTimeBegin;
    }

    public void setAcceptTimeBegin(Date acceptTimeBegin) {
        this.acceptTimeBegin = acceptTimeBegin;
    }

    public Date getAcceptTimeEnd() {
        return acceptTimeEnd;
    }

    public void setAcceptTimeEnd(Date acceptTimeEnd) {
        this.acceptTimeEnd = acceptTimeEnd;
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

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getRefAssetId() {
        return refAssetId;
    }

    public void setRefAssetId(String refAssetId) {
        this.refAssetId = refAssetId;
    }

    @Override
    public String toString() {
        return "RepairOrderListFilterVo{" +
                "fuzzy=" + fuzzy +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", projectType=" + projectType +
                ", workOrderSource=" + workOrderSource +
                ", incidentNature=" + incidentNature +
                ", incidentLevel=" + incidentLevel +
                ", executeTimeout=" + executeTimeout +
                ", workOrderStatus=" + workOrderStatus +
                ", repairPerson='" + repairPerson + '\'' +
                ", repairDept='" + repairDept + '\'' +
                ", reportPersonId=" + reportPersonId +
                ", actualExecutionPersonId=" + actualExecutionPersonId +
                ", actualExecutionPersonFieldType='" + actualExecutionPersonFieldType + '\'' +
                ", reportDateBegin=" + reportDateBegin +
                ", reportDateEnd=" + reportDateEnd +
                ", acceptTimeBegin=" + acceptTimeBegin +
                ", acceptTimeEnd=" + acceptTimeEnd +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", createUser='" + createUser + '\'' +
                ", refAssetId='" + refAssetId + '\'' +
                "} " + super.toString();
    }
}
