package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 消缺工单列表
 */
@ApiModel(value = "消缺工单列表", description = "消缺工单列表Vo")
public class DefectOrderForListVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 消缺工单编号
     */
    @ApiModelProperty("消缺工单编码")
    private String defectOrderNum;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    private String projectType;

    /**
     * 关联的缺陷单
     */
    @ApiModelProperty("关联的缺陷单id")
    private String defectDocumentId;

    /**
     * 关联的缺陷单
     */
    @ApiModelProperty("关联的缺陷单编码")
    private String defectDocumentNum;

    /**
     * 站点id
     */
    @ApiModelProperty("站点id")
    private String siteId;

    /**
     * 提报人id
     */
    @ApiModelProperty("提报人id")
    private String reportId;

    /**
     * 提报人名
     */
    @ApiModelProperty("提报人名")
    private String reportName;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;

    @ApiModelProperty(value = "流程ID")
    private String processInstanceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefectOrderNum() {
        return defectOrderNum;
    }

    public void setDefectOrderNum(String defectOrderNum) {
        this.defectOrderNum = defectOrderNum;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDefectDocumentId() {
        return defectDocumentId;
    }

    public void setDefectDocumentId(String defectDocumentId) {
        this.defectDocumentId = defectDocumentId;
    }

    public String getDefectDocumentNum() {
        return defectDocumentNum;
    }

    public void setDefectDocumentNum(String defectDocumentNum) {
        this.defectDocumentNum = defectDocumentNum;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "DefectOrderForListVo{" +
                "id='" + id + '\'' +
                ", defectOrderNum='" + defectOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", defectDocumentId='" + defectDocumentId + '\'' +
                ", defectDocumentNum='" + defectDocumentNum + '\'' +
                ", siteId='" + siteId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportDate=" + reportDate +
                ", orgId='" + orgId + '\'' +
                ", collect=" + collect +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
