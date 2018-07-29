package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/6
 * @Description
 */
@ApiModel(value = "合同", description = "合同-流程VO")
public class ContractForWorkFlowVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同ID(新增不需要传值)")
    private String id;

    @ApiModelProperty(value = "编号")
    private String contractNum;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "合同类型")
    private String contractType;

    @ApiModelProperty(value = "施工单位")
    private String contractCompany;

    @ApiModelProperty(value = "合同金额")
    private Double contractValue;

    @ApiModelProperty(value = "施工负责人")
    private String chargePerson;

    @ApiModelProperty(value = "施工负责人联系方式")
    private String chargePersonPhone;

    @ApiModelProperty(value = "合同生效日期")
    private Date effectiveDate;

    @ApiModelProperty(value = "合同截止日期")
    private Date closingDate;

    @ApiModelProperty(value = "监理单位负责人")
    private String supervisorPerson;

    @ApiModelProperty(value = "监理单位负责人联系方式")
    private String supervisorPersonPhone;

    @ApiModelProperty(value = "工程名称")
    private String projectName;

    @ApiModelProperty(value = "工期")
    private Double projectLimit;

    @ApiModelProperty(value = "物业单位负责人")
    private String propertyManagerId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "确认时间")
    private Date confirmDate;

    @ApiModelProperty(value = "确认人")
    private String confirmUserId;

    @ApiModelProperty(value = "所属组织")
    private String orgId;

    @ApiModelProperty(value = "所属站点")
    private String siteId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "流程实例id")
    private String processInstanceId;

    @ApiModelProperty(value = "建设单位负责人")
    private String buildPerson;

    @ApiModelProperty(value = "建设单位负责人联系方式")
    private String buildPersonPhone;

    @ApiModelProperty(value = "合同评价")
    private String contractEvaluate;

    @ApiModelProperty(value = "合同总结")
    private String contractSummary;

    @ApiModelProperty(value = "创建人")
    private String createUser;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(String contractCompany) {
        this.contractCompany = contractCompany;
    }

    public Double getContractValue() {
        return contractValue;
    }

    public void setContractValue(Double contractValue) {
        this.contractValue = contractValue;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getChargePersonPhone() {
        return chargePersonPhone;
    }

    public void setChargePersonPhone(String chargePersonPhone) {
        this.chargePersonPhone = chargePersonPhone;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getSupervisorPerson() {
        return supervisorPerson;
    }

    public void setSupervisorPerson(String supervisorPerson) {
        this.supervisorPerson = supervisorPerson;
    }

    public String getSupervisorPersonPhone() {
        return supervisorPersonPhone;
    }

    public void setSupervisorPersonPhone(String supervisorPersonPhone) {
        this.supervisorPersonPhone = supervisorPersonPhone;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProjectLimit() {
        return projectLimit;
    }

    public void setProjectLimit(Double projectLimit) {
        this.projectLimit = projectLimit;
    }

    public String getPropertyManagerId() {
        return propertyManagerId;
    }

    public void setPropertyManagerId(String propertyManagerId) {
        this.propertyManagerId = propertyManagerId;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
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

    public String getBuildPerson() {
        return buildPerson;
    }

    public void setBuildPerson(String buildPerson) {
        this.buildPerson = buildPerson;
    }

    public String getBuildPersonPhone() {
        return buildPersonPhone;
    }

    public void setBuildPersonPhone(String buildPersonPhone) {
        this.buildPersonPhone = buildPersonPhone;
    }

    public String getContractEvaluate() {
        return contractEvaluate;
    }

    public void setContractEvaluate(String contractEvaluate) {
        this.contractEvaluate = contractEvaluate;
    }

    public String getContractSummary() {
        return contractSummary;
    }

    public void setContractSummary(String contractSummary) {
        this.contractSummary = contractSummary;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "ContractForWorkFlowVo{" +
                "id='" + id + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", contractType='" + contractType + '\'' +
                ", contractCompany='" + contractCompany + '\'' +
                ", contractValue=" + contractValue +
                ", chargePerson='" + chargePerson + '\'' +
                ", chargePersonPhone='" + chargePersonPhone + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", closingDate=" + closingDate +
                ", supervisorPerson='" + supervisorPerson + '\'' +
                ", supervisorPersonPhone='" + supervisorPersonPhone + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectLimit=" + projectLimit +
                ", propertyManagerId='" + propertyManagerId + '\'' +
                ", confirmDate=" + confirmDate +
                ", confirmUserId='" + confirmUserId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", remark='" + remark + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", buildPerson='" + buildPerson + '\'' +
                ", buildPersonPhone='" + buildPersonPhone + '\'' +
                ", contractEvaluate='" + contractEvaluate + '\'' +
                ", contractSummary='" + contractSummary + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
