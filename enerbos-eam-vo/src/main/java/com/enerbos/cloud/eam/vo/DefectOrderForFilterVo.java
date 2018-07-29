package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 消缺工单
 */
@ApiModel(value = "消缺工单列表过滤条件", description = "消缺工单列表过滤条件Vo")
public class DefectOrderForFilterVo extends EAMBaseFilterVo implements Serializable {

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private List<String> status;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    private List<String> projectType;

    /**
     * 是否提报人派单
     */
    @ApiModelProperty("是否提报人派单")
    private Boolean reporterAssignFlag;

    /**
     * 事件级别
     */
    @ApiModelProperty("事件级别")
    private List<String> incidentLevel;

    /**
     * 关联的缺陷单
     */
    @ApiModelProperty("关联的缺陷单")
    private String defectDocumentId;

    /**
     * 站点id
     */
    @ApiModelProperty("站点id")
    private String siteId;

    /**
     * 提报人id
     */
    @ApiModelProperty("提报人id")
    private List<String> reportId;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报开始日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportStartDate;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报结束日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportEndDate;

    /**
     * 验收时间
     */
    @ApiModelProperty("验收开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptionStartDate;

    /**
     * 验收时间
     */
    @ApiModelProperty("验收结束时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptionEndDate;

    /**
     * 验收人id
     */
    @ApiModelProperty("验收人id")
    private List<String> acceptorId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private String orgId;

    @ApiModelProperty(value = "模糊搜索关键词")
    private String words;

    @ApiModelProperty(value = "模糊搜索关键词，分词", hidden = true)
    private List<String> wordsList;

    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;

    @ApiModelProperty(value = "人员编号", hidden = true)
    private String personId;

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getProjectType() {
        return projectType;
    }

    public void setProjectType(List<String> projectType) {
        this.projectType = projectType;
    }

    public Boolean getReporterAssignFlag() {
        return reporterAssignFlag;
    }

    public void setReporterAssignFlag(Boolean reporterAssignFlag) {
        this.reporterAssignFlag = reporterAssignFlag;
    }

    public List<String> getIncidentLevel() {
        return incidentLevel;
    }

    public void setIncidentLevel(List<String> incidentLevel) {
        this.incidentLevel = incidentLevel;
    }

    public String getDefectDocumentId() {
        return defectDocumentId;
    }

    public void setDefectDocumentId(String defectDocumentId) {
        this.defectDocumentId = defectDocumentId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<String> getReportId() {
        return reportId;
    }

    public void setReportId(List<String> reportId) {
        this.reportId = reportId;
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

    public Date getAcceptionStartDate() {
        return acceptionStartDate;
    }

    public void setAcceptionStartDate(Date acceptionStartDate) {
        this.acceptionStartDate = acceptionStartDate;
    }

    public Date getAcceptionEndDate() {
        return acceptionEndDate;
    }

    public void setAcceptionEndDate(Date acceptionEndDate) {
        this.acceptionEndDate = acceptionEndDate;
    }

    public List<String> getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(List<String> acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getOrgId() {
        return orgId;
    }

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

    @Override
    public String toString() {
        return "DefectOrderForFilterVo{" +
                "status=" + status +
                ", projectType=" + projectType +
                ", reporterAssignFlag=" + reporterAssignFlag +
                ", incidentLevel=" + incidentLevel +
                ", defectDocumentId='" + defectDocumentId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", reportId=" + reportId +
                ", reportStartDate=" + reportStartDate +
                ", reportEndDate=" + reportEndDate +
                ", acceptionStartDate=" + acceptionStartDate +
                ", acceptionEndDate=" + acceptionEndDate +
                ", acceptorId=" + acceptorId +
                ", orgId='" + orgId + '\'' +
                ", words='" + words + '\'' +
                ", wordsList=" + wordsList +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                '}';
    }
}
