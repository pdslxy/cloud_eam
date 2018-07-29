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

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 缺陷单列表Vo
 */
@ApiModel(value = "缺陷单列表", description = "缺陷单列表Vo")
public class DefectDocumentForListVo implements Serializable {

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
    @NotBlank(message = "缺陷单编码")
    private String defectDocumentNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述",required = true)
    @NotBlank(message = "描述")
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
     * 完成度
     */
    @ApiModelProperty("完成度")
    private String completeness;

    /**
     * 发现日期
     */
    @ApiModelProperty("发现日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date findDate;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

    @ApiModelProperty(value = "是否收藏 true：是")
    private Boolean collect;

    @ApiModelProperty(value = "档案ID")
    private String archivesId;
    @ApiModelProperty(value = "资料编码")
    private String materialNum;
    @ApiModelProperty(value = "流程ID")
    private String  processInstanceId;

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

    public String getCompleteness() {
        return completeness;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public Date getFindDate() {
        return findDate;
    }

    public void setFindDate(Date findDate) {
        this.findDate = findDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "DefectDocumentForListVo{" +
                "id='" + id + '\'' +
                ", defectDocumentNum='" + defectDocumentNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", projectType='" + projectType + '\'' +
                ", importance='" + importance + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", completeness='" + completeness + '\'' +
                ", findDate=" + findDate +
                ", siteId='" + siteId + '\'' +
                ", collect=" + collect +
                '}';
    }
}