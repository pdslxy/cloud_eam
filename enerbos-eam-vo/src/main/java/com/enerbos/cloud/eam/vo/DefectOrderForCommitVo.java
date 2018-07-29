package com.enerbos.cloud.eam.vo;

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
 * @Description 消缺工单-工单提报
 */
@ApiModel(value = "消缺工单-工单提报", description = "消缺工单-工单提报Vo")
public class DefectOrderForCommitVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 消缺工单编号
     */
    @ApiModelProperty(value = "消缺工单编码",required = true)
    @NotBlank(message = "消缺工单编码不能为空")
    private String defectOrderNum;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @NotBlank(message = "状态不能为空")
    private String status;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    @NotBlank(message = "工程类型不能为空")
    private String projectType;

    /**
     * 工程类型用户组名
     */
    @ApiModelProperty("工程类型用户组名")
    private String projectTypeGroupTypeName;

    /**
     * 是否提报人派单
     */
    @ApiModelProperty("是否提报人派单")
    private Boolean reporterAssignFlag = false;

    /**
     * 事件级别
     */
    @ApiModelProperty("事件级别")
    private String incidentLevel;

    /**
     * 关联的缺陷单
     */
    @ApiModelProperty("关联的缺陷单")
    private String defectDocumentId;

    /**
     * 关联的缺陷单编码
     */
    @ApiModelProperty("关联的缺陷单编码")
    private String defectDocumentNum;

    /**
     * 关联的缺陷单描述
     */
    @ApiModelProperty("关联的缺陷单描述")
    private String defectDocumentDesc;

    /**
     * 关联的缺陷单区域名
     */
    @ApiModelProperty("关联的缺陷单区域名")
    private String defectDocumentRegionName;

    /**
     * 关联的缺陷单楼号名
     */
    @ApiModelProperty("关联的缺陷单楼号名")
    private String defectDocumentBuildingNumName;

    /**
     * 关联的缺陷单楼层名
     */
    @ApiModelProperty("关联的缺陷单楼层名")
    private String defectDocumentFloorName;

    /**
     * 关联的缺陷单方位名
     */
    @ApiModelProperty("关联的缺陷单方位名")
    private String defectDocumentPosition;


    @ApiModelProperty("关联的档案编码")
    private String  archivesNum;

    /**
     * 站点id
     */
    @ApiModelProperty("站点id")
    private String siteId;

    /**
     * 站点
     */
    @ApiModelProperty("站点")
    private String siteName;

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
     * 提报人电话
     */
    @ApiModelProperty("提报人电话")
    private String reportMobile;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String explainDesc;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private String orgId;

    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID",hidden = true)
    private String processInstanceId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @Length(max = 36, message = "不能超过36个字符")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> impleRecordVoVoList;


    public String getArchivesNum() {
        return archivesNum;
    }

    public void setArchivesNum(String archivesNum) {
        this.archivesNum = archivesNum;
    }

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

    public Boolean getReporterAssignFlag() {
        return reporterAssignFlag;
    }

    public void setReporterAssignFlag(Boolean reporterAssignFlag) {
        this.reporterAssignFlag = reporterAssignFlag;
    }

    public String getIncidentLevel() {
        return incidentLevel;
    }

    public void setIncidentLevel(String incidentLevel) {
        this.incidentLevel = incidentLevel;
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

    public String getDefectDocumentDesc() {
        return defectDocumentDesc;
    }

    public void setDefectDocumentDesc(String defectDocumentDesc) {
        this.defectDocumentDesc = defectDocumentDesc;
    }

    public String getDefectDocumentRegionName() {
        return defectDocumentRegionName;
    }

    public void setDefectDocumentRegionName(String defectDocumentRegionName) {
        this.defectDocumentRegionName = defectDocumentRegionName;
    }

    public String getDefectDocumentBuildingNumName() {
        return defectDocumentBuildingNumName;
    }

    public void setDefectDocumentBuildingNumName(String defectDocumentBuildingNumName) {
        this.defectDocumentBuildingNumName = defectDocumentBuildingNumName;
    }

    public String getDefectDocumentFloorName() {
        return defectDocumentFloorName;
    }

    public void setDefectDocumentFloorName(String defectDocumentFloorName) {
        this.defectDocumentFloorName = defectDocumentFloorName;
    }

    public String getDefectDocumentPosition() {
        return defectDocumentPosition;
    }

    public void setDefectDocumentPosition(String defectDocumentPosition) {
        this.defectDocumentPosition = defectDocumentPosition;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public String getReportMobile() {
        return reportMobile;
    }

    public void setReportMobile(String reportMobile) {
        this.reportMobile = reportMobile;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getExplainDesc() {
        return explainDesc;
    }

    public void setExplainDesc(String explainDesc) {
        this.explainDesc = explainDesc;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<WorkFlowImpleRecordVo> getImpleRecordVoVoList() {
        return impleRecordVoVoList;
    }

    public void setImpleRecordVoVoList(List<WorkFlowImpleRecordVo> impleRecordVoVoList) {
        this.impleRecordVoVoList = impleRecordVoVoList;
    }

    public String getProjectTypeGroupTypeName() {
        return projectTypeGroupTypeName;
    }

    public void setProjectTypeGroupTypeName(String projectTypeGroupTypeName) {
        this.projectTypeGroupTypeName = projectTypeGroupTypeName;
    }

    @Override
    public String toString() {
        return "DefectOrderForCommitVo{" +
                "id='" + id + '\'' +
                ", defectOrderNum='" + defectOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectTypeGroupTypeName='" + projectTypeGroupTypeName + '\'' +
                ", reporterAssignFlag=" + reporterAssignFlag +
                ", incidentLevel='" + incidentLevel + '\'' +
                ", defectDocumentId='" + defectDocumentId + '\'' +
                ", defectDocumentNum='" + defectDocumentNum + '\'' +
                ", defectDocumentDesc='" + defectDocumentDesc + '\'' +
                ", defectDocumentRegionName='" + defectDocumentRegionName + '\'' +
                ", defectDocumentBuildingNumName='" + defectDocumentBuildingNumName + '\'' +
                ", defectDocumentFloorName='" + defectDocumentFloorName + '\'' +
                ", defectDocumentPosition='" + defectDocumentPosition + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportMobile='" + reportMobile + '\'' +
                ", reportDate=" + reportDate +
                ", explainDesc='" + explainDesc + '\'' +
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                '}';
    }
}
