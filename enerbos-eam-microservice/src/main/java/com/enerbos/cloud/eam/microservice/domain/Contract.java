package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年9月1日 14:46:37
 * @Description  合同管理
 */
@Entity
@Table(name = "eam_contract")
public class Contract extends EnerbosBaseEntity implements Serializable {


    private static final long serialVersionUID = 8715511144437029263L;

    /**
     * 编号
     */
    @Column(name = "contract_num", nullable = false, length = 36)
    private String contractNum;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    /**
     * 状态
     * 新增、执行中、完成
     */
    @Column(name = "status", nullable = false, length = 36)
    private String status;

    /**
     * 合同类型
     * 维保合同、施工合同、维修合同
     */
    @Column(name = "contract_type", nullable = false, length = 36)
    private String contractType;

    /**
     * 施工单位
     */
    @Column(name = "contract_company", nullable = true, length = 36)
    private String contractCompany;

    /**
     * 合同金额
     */
    @Column(name = "contract_value", nullable = true)
    private Double contractValue;

    /**
     * 施工负责人
     */
    @Column(name = "charge_person", nullable = false, length = 36)
    private String chargePerson;

    /**
     * 施工负责人联系方式
     */
    @Column(name = "charge_person_phone", nullable = true, length = 36)
    private String chargePersonPhone;

    /**
     * 合同生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effective_date", nullable = false, length = 20)
    private Date effectiveDate;

    /**
     * 合同截止日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "closing_date", nullable = false, length = 20)
    private Date closingDate;

    /**
     * 监理单位负责人
     */
    @Column(name = "supervisor_person", nullable = false, length = 20)
    private String supervisorPerson;

    /**
     * 监理单位负责人联系方式
     */
    @Column(name = "supervisor_person_phone", nullable = true, length = 20)
    private String supervisorPersonPhone;

    /**
     * 工程名称
     */
    @Column(name = "project_name", nullable = true, length = 20)
    private String projectName;

    /**
     * 工期
     */
    @Column(name = "project_limit", nullable = true, length = 20)
    private Double projectLimit;

    /**
     * 物业单位负责人
     */
    @Column(name = "property_manager_id", nullable = false, length = 36)
    private String propertyManagerId;

    /**
     * 确认时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "confirm_date", nullable = true, length = 20)
    private Date confirmDate;

    /**
     * 确认人
     */
    @Column(name = "confirm_user_id", nullable = true, length = 36)
    private String confirmUserId;

    /**
     * 所属组织
     */
    @Column(name = "org_id", nullable = false, length = 36)
    private String orgId;

    /**
     * 所属站点
     */
    @Column(name = "site_id", nullable = false, length = 36)
    private String siteId;

    /**
     * 备注
     */
    @Column(name = "remark", nullable = true, length = 255)
    private String remark;

    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id", nullable = true, length = 255)
    private String processInstanceId;


    /**
     * 建设单位负责人
     */
    @Column(name = "build_person", nullable = true, length = 255)
    private String buildPerson;

    /**
     * 建设单位负责人联系方式
     */
    @Column(name = "build_person_phone", nullable = true, length = 255)
    private String buildPersonPhone;

    /**
     * 合同评价
     */
    @Column(name = "contract_evaluate", nullable = true, length = 255)
    private String contractEvaluate;

    /**
     * 合同总结
     */
    @Column(name = "contract_summary", nullable = true, length = 255)
    private String contractSummary;


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getSupervisorPerson() {
        return supervisorPerson;
    }

    public void setSupervisorPerson(String supervisorPerson) {
        this.supervisorPerson = supervisorPerson;
    }

    public String getBuildPerson() {
        return buildPerson;
    }

    public void setBuildPerson(String buildPerson) {
        this.buildPerson = buildPerson;
    }


}
