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
 * @date 2017-07-23 16:22
 * @Description 派工工单列表过滤条件VO
 */
@ApiModel(value = "派工工单列表过滤条件", description = "派工工单列表过滤条件VO")
public class DispatchWorkOrderListFilterVo extends EAMBaseFilterVo {
    @ApiModelProperty(value = "模糊查询")
    private List<String> fuzzy;
    @ApiModelProperty(value = "工单编号")
    private String workOrderNum;
    @ApiModelProperty(value = "派工描述")
    private String description;
    @ApiModelProperty(value = "工单状态(逗号分隔)")
    private List<String> workOrderStatus;
    @ApiModelProperty(value = "需求人(模糊查询)")
    private String demandPerson;
    @ApiModelProperty(value = "需求部门(模糊查询)")
    private String demandDept;
    @ApiModelProperty(value = "提报人(逗号分隔)")
    private List<String> reportPersonId;
    @ApiModelProperty(value = "实际执行人(逗号分隔)")
    private List<String> actualExecutionPersonId;
    @ApiModelProperty(value = "实际执行人字段类型(工单关联人员)", hidden = true)
    private String actualExecutionPersonFieldType;
    @ApiModelProperty(value = "验收人(逗号分隔)")
    private List<String> acceptPersonId;
    @ApiModelProperty(value = "验收人字段类型(工单关联人员)", hidden = true)
    private String acceptPersonFieldType;
    @ApiModelProperty(value = "提报日期-开始")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date reportDateBegin;
    @ApiModelProperty(value = "提报日期-截止")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date reportDateEnd;
    @ApiModelProperty(value = "验收时间-开始")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date acceptTimeBegin;
    @ApiModelProperty(value = "验收时间-截止")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date acceptTimeEnd;
    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;
    @ApiModelProperty(value = "人员编号", hidden = true)
    private String personId;

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

    public List<String> getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(List<String> workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getDemandPerson() {
        return demandPerson;
    }

    public void setDemandPerson(String demandPerson) {
        this.demandPerson = demandPerson;
    }

    public String getDemandDept() {
        return demandDept;
    }

    public void setDemandDept(String demandDept) {
        this.demandDept = demandDept;
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

    public List<String> getAcceptPersonId() {
        return acceptPersonId;
    }

    public void setAcceptPersonId(List<String> acceptPersonId) {
        this.acceptPersonId = acceptPersonId;
    }

    public String getAcceptPersonFieldType() {
        return acceptPersonFieldType;
    }

    public void setAcceptPersonFieldType(String acceptPersonFieldType) {
        this.acceptPersonFieldType = acceptPersonFieldType;
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

    @Override
    public String toString() {
        return "DispatchWorkOrderListFilterVo{" +
                "fuzzy=" + fuzzy +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", workOrderStatus=" + workOrderStatus +
                ", demandPerson='" + demandPerson + '\'' +
                ", demandDept='" + demandDept + '\'' +
                ", reportPersonId=" + reportPersonId +
                ", actualExecutionPersonId=" + actualExecutionPersonId +
                ", actualExecutionPersonFieldType='" + actualExecutionPersonFieldType + '\'' +
                ", acceptPersonId=" + acceptPersonId +
                ", acceptPersonFieldType='" + acceptPersonFieldType + '\'' +
                ", reportDateBegin=" + reportDateBegin +
                ", reportDateEnd=" + reportDateEnd +
                ", acceptTimeBegin=" + acceptTimeBegin +
                ", acceptTimeEnd=" + acceptTimeEnd +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                "} " + super.toString();
    }
}
