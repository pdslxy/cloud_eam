package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
 * @Description 缺陷单
 */
@Entity
@Table(name = "eam_defect_document")
public class DefectDocument extends EnerbosBaseEntity implements Serializable {
    private static final long serialVersionUID = -8903627477418936812L;

    /**
     * 缺陷单编码
     */
    @Column(name="defect_document_num",nullable = false,length = 255)
    private String defectDocumentNum;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 状态
     */
    @Column(name="status",nullable = true,length = 36)
    private String status;

    /**
     * 工程类型
     */
    @Column(name="project_type",nullable = true,length = 36)
    private String projectType;

    /**
     * 区域-一级位置id
     */
    @Column(name="region",nullable = true,length = 255)
    private String region;

    /**
     * 楼号-二级位置id
     */
    @Column(name="building_num",nullable = true,length = 255)
    private String buildingNum;

    /**
     * 楼层-三级位置id
     */
    @Column(name="floor",nullable = true,length = 255)
    private String floor;

    /**
     * 方位
     */
    @Column(name="position",nullable = true,length = 255)
    private String position;

    /**
     * 重要程度
     */
    @Column(name="importance",nullable = true,length = 255)
    private String importance;

    /**
     * 责任属性
     */
    @Column(name="responsibility",nullable = true,length = 255)
    private String responsibility;

    /**
     * 标准
     */
    @Column(name="standard",nullable = true,length = 255)
    private String standard;

    /**
     * 其他
     */
    @Column(name="other",nullable = true,length = 255)
    private String other;

    /**
     * 完成度
     */
    @Column(name="completeness",nullable = true,length = 255)
    private String completeness;

    /**
     * 建议措施
     */
    @Column(name="proposed_measures",nullable = true,length = 255)
    private String proposedMeasures;

    /**
     * 备注
     */
    @Column(name="remark",nullable = true,length = 255)
    private String remark;

    /**
     * 流程实例id
     */
    @Column(name="process_instance_id",nullable = true,length = 36)
    private String processInstanceId;

    /**
     * 关联的档案ID
     */
    @Column(name="archives_id",nullable = true,length = 36)
    private String archivesId;

    /**
     * 发现日期
     */
    @Column(name="find_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date findDate;

    /**
     * 提报人
     */
    @Column(name="report_id",nullable = true,length = 36)
    private String reportId;

    /**
     * 提报日期
     */
    @Column(name="report_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 确认时间
     */
    @Column(name="confirm_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmDate;

    /**
     * 确认人
     */
    @Column(name="confirm_person_id",nullable = true,length = 36)
    private String confirmPersonId;

    /**
     * 所属站点id
     */
    @Column(name="site_id",nullable = true,length = 36)
    private String siteId;

    /**
     * 所属组织id
     */
    @Column(name="org_id",nullable = true,length = 36)
    private String orgId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDefectDocumentNum() {
        return defectDocumentNum;
    }

    public void setDefectDocumentNum(String defectDocumentNum) {
        this.defectDocumentNum = defectDocumentNum;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getProposedMeasures() {
        return proposedMeasures;
    }

    public void setProposedMeasures(String proposedMeasures) {
        this.proposedMeasures = proposedMeasures;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getFindDate() {
        return findDate;
    }

    public void setFindDate(Date findDate) {
        this.findDate = findDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(String confirmPersonId) {
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

    public String getCompleteness() {
        return completeness;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "DefectDocument{" +
                "defectDocumentNum='" + defectDocumentNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", region='" + region + '\'' +
                ", buildingNum='" + buildingNum + '\'' +
                ", floor='" + floor + '\'' +
                ", position='" + position + '\'' +
                ", importance='" + importance + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", standard='" + standard + '\'' +
                ", other='" + other + '\'' +
                ", completeness='" + completeness + '\'' +
                ", reportId='" + reportId + '\'' +
                ", archivesId='" + archivesId + '\'' +
                ", remark='" + remark + '\'' +
                ", proposedMeasures='" + proposedMeasures + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", findDate=" + findDate +
                ", reportDate=" + reportDate +
                ", confirmDate=" + confirmDate +
                ", confirmPersonId='" + confirmPersonId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}'+super.toString();
    }
}
