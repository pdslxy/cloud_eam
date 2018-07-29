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
 * @Description 消缺工单-确认验收
 */
@ApiModel(value = "消缺工单-确认验收", description = "消缺工单-确认验收Vo")
public class DefectOrderForAcceptVo implements Serializable {

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
    private String status;

    /**
     * 站点id
     */
    @ApiModelProperty("工程类型")
    private String siteId;
    //==================执行汇报==============
    /**
     * 实际执行负责人id
     */
    @ApiModelProperty("实际执行负责人id")
    private String actualExecutorResponsibleId;

    /**
     * 实际执行负责人
     */
    @ApiModelProperty("实际执行负责人")
    private String actualExecutorResponsibleName;




    //==================验收确认==============
    /**
     * 确认解决
     */
    @ApiModelProperty("确认解决")
    private Boolean confirm=false;

    /**
     * 验收时间
     */
    @ApiModelProperty("验收时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptionDate;

    /**
     * 验收人id
     */
    @ApiModelProperty("验收人id")
    private String acceptorId;

    /**
     * 验收人
     */
    @ApiModelProperty("验收人")
    private String acceptorName;

    /**
     * 验收说明
     */
    @ApiModelProperty("验收说明")
    private String acceptionDesc;

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
     * 分派人id
     */
    @ApiModelProperty("分派人Id")
    private String assignPersonId;

    /**
     * 分派人
     */
    @ApiModelProperty("分派人名称")
    private String assignPersonName;

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> impleRecordVoVoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getActualExecutorResponsibleId() {
        return actualExecutorResponsibleId;
    }

    public void setActualExecutorResponsibleId(String actualExecutorResponsibleId) {
        this.actualExecutorResponsibleId = actualExecutorResponsibleId;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public Date getAcceptionDate() {
        return acceptionDate;
    }

    public void setAcceptionDate(Date acceptionDate) {
        this.acceptionDate = acceptionDate;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getAcceptionDesc() {
        return acceptionDesc;
    }

    public void setAcceptionDesc(String acceptionDesc) {
        this.acceptionDesc = acceptionDesc;
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

    public List<WorkFlowImpleRecordVo> getImpleRecordVoVoList() {
        return impleRecordVoVoList;
    }

    public void setImpleRecordVoVoList(List<WorkFlowImpleRecordVo> impleRecordVoVoList) {
        this.impleRecordVoVoList = impleRecordVoVoList;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getActualExecutorResponsibleName() {
        return actualExecutorResponsibleName;
    }

    public void setActualExecutorResponsibleName(String actualExecutorResponsibleName) {
        this.actualExecutorResponsibleName = actualExecutorResponsibleName;
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

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getAssignPersonName() {
        return assignPersonName;
    }

    public void setAssignPersonName(String assignPersonName) {
        this.assignPersonName = assignPersonName;
    }

    @Override
    public String toString() {
        return "DefectOrderForAcceptVo{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", siteId='" + siteId + '\'' +
                ", actualExecutorResponsibleId='" + actualExecutorResponsibleId + '\'' +
                ", actualExecutorResponsibleName='" + actualExecutorResponsibleName + '\'' +
                ", confirm=" + confirm +
                ", acceptionDate=" + acceptionDate +
                ", acceptorId='" + acceptorId + '\'' +
                ", acceptorName='" + acceptorName + '\'' +
                ", acceptionDesc='" + acceptionDesc + '\'' +
                ", orgId='" + orgId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                '}';
    }
}
