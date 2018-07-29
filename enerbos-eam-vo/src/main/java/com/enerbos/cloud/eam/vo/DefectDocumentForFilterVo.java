package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
 * @Description 缺陷单列表过滤条件Vo
 */
@ApiModel(value = "缺陷单列表过滤条件", description = "缺陷单列表过滤条件Vo")
public class DefectDocumentForFilterVo extends EAMBaseFilterVo implements Serializable {

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态id")
    private List<String> status;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    private List<String> projectType;

    /**
     * 区域-一级位置id
     */
    @ApiModelProperty("区域-一级位置id")
    private List<String> region;

    /**
     * 楼号-二级位置id
     */
    @ApiModelProperty("楼号-二级位置id")
    private List<String> buildingNum;

    /**
     * 楼层-三级位置id
     */
    @ApiModelProperty("楼层-三级位置id")
    private List<String> floor;

    /**
     * 重要程度
     */
    @ApiModelProperty("重要程度")
    private List<String> importance;

    /**
     * 责任属性
     */
    @ApiModelProperty("责任属性")
    private List<String> responsibility;

    /**
     * 标准
     */
    @ApiModelProperty("标准")
    private List<String> standard;

    /**
     * 关联的档案ID
     */
    @ApiModelProperty("关联的档案ID")
    private List<String> archivesId;

    /**
     * 发现开始日期
     */
    @ApiModelProperty("发现开始日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date findStartDate;

    /**
     * 发现结束日期
     */
    @ApiModelProperty("发现结束日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date findEndDate;

    /**
     * 提报人
     */
    @ApiModelProperty("提报人")
    private List<String> reportId;

    /**
     * 提报开始日期
     */
    @ApiModelProperty("提报开始日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportStartDate;

    /**
     * 提报结束日期
     */
    @ApiModelProperty("提报结束日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportEndDate;

    /**
     * 确认开始时间
     */
    @ApiModelProperty("确认开始时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date confirmStartDate;

    /**
     * 确认结束时间
     */
    @ApiModelProperty("确认结束时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date confirmEndDate;

    /**
     * 确认人
     */
    @ApiModelProperty("确认人")
    private List<String> confirmPersonId;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

    /**
     * 所属组织编码
     */
    @ApiModelProperty(value = "所属组织编码")
    private String orgId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人",required = true)
    @Length(max = 36, message = "不能超过36个字符")
    private List<String> createUser;

    /**
     * 创建开始时间
     */
    @ApiModelProperty(value = "创建开始时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createStartDate;

    /**
     * 创建结束时间
     */
    @ApiModelProperty(value = "创建结束时间")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createEndDate;

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

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public List<String> getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(List<String> buildingNum) {
        this.buildingNum = buildingNum;
    }

    public List<String> getFloor() {
        return floor;
    }

    public void setFloor(List<String> floor) {
        this.floor = floor;
    }

    public List<String> getImportance() {
        return importance;
    }

    public void setImportance(List<String> importance) {
        this.importance = importance;
    }

    public List<String> getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(List<String> responsibility) {
        this.responsibility = responsibility;
    }

    public List<String> getStandard() {
        return standard;
    }

    public void setStandard(List<String> standard) {
        this.standard = standard;
    }

    public List<String> getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(List<String> archivesId) {
        this.archivesId = archivesId;
    }

    public Date getFindStartDate() {
        return findStartDate;
    }

    public void setFindStartDate(Date findStartDate) {
        this.findStartDate = findStartDate;
    }

    public Date getFindEndDate() {
        return findEndDate;
    }

    public void setFindEndDate(Date findEndDate) {
        this.findEndDate = findEndDate;
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

    public Date getConfirmStartDate() {
        return confirmStartDate;
    }

    public void setConfirmStartDate(Date confirmStartDate) {
        this.confirmStartDate = confirmStartDate;
    }

    public Date getConfirmEndDate() {
        return confirmEndDate;
    }

    public void setConfirmEndDate(Date confirmEndDate) {
        this.confirmEndDate = confirmEndDate;
    }

    public List<String> getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(List<String> confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
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

    public List<String> getCreateUser() {
        return createUser;
    }

    public void setCreateUser(List<String> createUser) {
        this.createUser = createUser;
    }

    public Date getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(Date createStartDate) {
        this.createStartDate = createStartDate;
    }

    public Date getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(Date createEndDate) {
        this.createEndDate = createEndDate;
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

    public List<String> getReportId() {
        return reportId;
    }

    public void setReportId(List<String> reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "DefectDocumentForFilterVo{" +
                "status=" + status +
                ", projectType=" + projectType +
                ", region=" + region +
                ", buildingNum=" + buildingNum +
                ", floor=" + floor +
                ", importance=" + importance +
                ", responsibility=" + responsibility +
                ", standard=" + standard +
                ", archivesId=" + archivesId +
                ", findStartDate=" + findStartDate +
                ", findEndDate=" + findEndDate +
                ", reportId=" + reportId +
                ", reportStartDate=" + reportStartDate +
                ", reportEndDate=" + reportEndDate +
                ", confirmStartDate=" + confirmStartDate +
                ", confirmEndDate=" + confirmEndDate +
                ", confirmPersonId=" + confirmPersonId +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createUser=" + createUser +
                ", createStartDate=" + createStartDate +
                ", createEndDate=" + createEndDate +
                ", words='" + words + '\'' +
                ", wordsList=" + wordsList +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                '}';
    }
}