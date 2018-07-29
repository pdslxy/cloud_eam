package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
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
 * @Description 缺陷单Vo
 */
@ApiModel(value = "缺陷单", description = "缺陷单Vo")
public class DefectDocumentVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 缺陷单编码
     */
    @ApiModelProperty(value = "缺陷单编码",required = true)
    @NotBlank(message = "缺陷单编码不能为空")
    private String defectDocumentNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述",required = true)
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态",required = true)
    @NotBlank(message = "状态不能为空")
    private String status;

    /**
     * 工程类型
     */
    @ApiModelProperty(value = "工程类型",required = true)
    @NotBlank(message = "工程类型不能为空")
    private String projectType;

    /**
     * 工程类型
     */
    @ApiModelProperty("工程类型")
    private String projectTypeGroupTypeName;

    /**
     * 区域-一级位置id
     */
    @ApiModelProperty(value = "区域-一级位置id",required = true)
    @NotBlank(message = "区域-一级位置id不能为空")
    private String region;

    /**
     * 区域-一级位置id
     */
    @ApiModelProperty("区域-一级位置名")
    private String regionName;

    /**
     * 楼号-二级位置id
     */
    @ApiModelProperty(value = "楼号-二级位置id",required = true)
    @NotBlank(message = "楼号-二级位置id不能为空")
    private String buildingNum;

    /**
     * 楼号-二级位置名
     */
    @ApiModelProperty("楼号-二级位置名")
    private String buildingNumName;

    /**
     * 楼层-三级位置id
     */
    @ApiModelProperty(value = "楼层-三级位置id",required = true)
    @NotBlank(message = "楼层-三级位置id不能为空")
    private String floor;

    /**
     * 区域-三级位置名
     */
    @ApiModelProperty("区域-三级位置名")
    private String floorName;

    /**
     * 方位
     */
    @ApiModelProperty("方位")
    private String position;

    /**
     * 重要程度
     */
    @ApiModelProperty("重要程度")
    private String importance;

    /**
     * 责任属性
     */
    @ApiModelProperty("责任属性")
    private String responsibility;

    /**
     * 标准
     */
    @ApiModelProperty("标准")
    private String standard;

    /**
     * 其他
     */
    @ApiModelProperty("其他")
    private String other;

    /**
     * 完成度
     */
    @ApiModelProperty("完成度")
    private String completeness;

    /**
     * 建议措施
     */
    @ApiModelProperty("建议措施")
    private String proposedMeasures;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 流程实例id
     */
    @ApiModelProperty(value = "流程实例id",hidden = true)
    private String processInstanceId;

    /**
     * 关联的档案ID
     */
    @ApiModelProperty("关联的档案ID")
    private String archivesId;

    /**
     * 关联的档案编码
     */
    @ApiModelProperty("关联的档案编码")
    private String archivesNum;

    /**
     * 关联的档案描述
     */
    @ApiModelProperty("关联的档案描述")
    private String archivesDesc;

    /**
     * 发现日期
     */
    @ApiModelProperty("发现日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date findDate;

    /**
     * 提报人id
     */
    @ApiModelProperty(value = "提报人id",required = true)
    @NotBlank(message = "提报人不能为空")
    private String reportId;

    /**
     * 提报人
     */
    @ApiModelProperty("提报人")
    private String reportName;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 确认时间
     */
    @ApiModelProperty("确认时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmDate;

    /**
     * 确认人
     */
    @ApiModelProperty("确认人")
    private String confirmPersonId;

    /**
     * 确认人名
     */
    @ApiModelProperty("确认人名")
    private String confirmPersonName;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点名称")
    private String siteName;

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
    private String createUser;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人名")
    @Length(max = 36, message = "不能超过36个字符")
    private String createUserName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getBuildingNumName() {
        return buildingNumName;
    }

    public void setBuildingNumName(String buildingNumName) {
        this.buildingNumName = buildingNumName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
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

    public String getCompleteness() {
        return completeness;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public String getProposedMeasures() {
        return proposedMeasures;
    }

    public void setProposedMeasures(String proposedMeasures) {
        this.proposedMeasures = proposedMeasures;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getArchivesNum() {
        return archivesNum;
    }

    public void setArchivesNum(String archivesNum) {
        this.archivesNum = archivesNum;
    }

    public String getArchivesDesc() {
        return archivesDesc;
    }

    public void setArchivesDesc(String archivesDesc) {
        this.archivesDesc = archivesDesc;
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

    public String getConfirmPersonName() {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName) {
        this.confirmPersonName = confirmPersonName;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public String getProjectTypeGroupTypeName() {
        return projectTypeGroupTypeName;
    }

    public void setProjectTypeGroupTypeName(String projectTypeGroupTypeName) {
        this.projectTypeGroupTypeName = projectTypeGroupTypeName;
    }

    @Override
    public String toString() {
        return "DefectDocumentVo{" +
                "id='" + id + '\'' +
                ", defectDocumentNum='" + defectDocumentNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectTypeGroupTypeName='" + projectTypeGroupTypeName + '\'' +
                ", region='" + region + '\'' +
                ", regionName='" + regionName + '\'' +
                ", buildingNum='" + buildingNum + '\'' +
                ", buildingNumName='" + buildingNumName + '\'' +
                ", floor='" + floor + '\'' +
                ", floorName='" + floorName + '\'' +
                ", position='" + position + '\'' +
                ", importance='" + importance + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", standard='" + standard + '\'' +
                ", other='" + other + '\'' +
                ", completeness='" + completeness + '\'' +
                ", proposedMeasures='" + proposedMeasures + '\'' +
                ", remark='" + remark + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", archivesId='" + archivesId + '\'' +
                ", archivesNum='" + archivesNum + '\'' +
                ", archivesDesc='" + archivesDesc + '\'' +
                ", findDate=" + findDate +
                ", reportId='" + reportId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportDate=" + reportDate +
                ", confirmDate=" + confirmDate +
                ", confirmPersonId='" + confirmPersonId + '\'' +
                ", confirmPersonName='" + confirmPersonName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                '}';
    }
}