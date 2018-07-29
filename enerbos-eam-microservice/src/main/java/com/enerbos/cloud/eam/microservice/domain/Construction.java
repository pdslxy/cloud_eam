package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

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
 * @date 2017年9月9日
 * @Description 施工单实体
 */
@Entity
@Table(name = "eam_construction")
public class Construction extends EnerbosBaseEntity implements Serializable {

    /**
     * 施工单编号
     */
    @Column(name="construction_num",nullable = false,length = 255)
    private String constructionNum;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 状态
     * 新增、执行中、评价中、完成
     */
    @Column(name="status",nullable = true,length = 36)
    private String status;

    /**
     * 监理单位负责人
     */
    @Column(name="supervision_id",nullable = true,length = 36)
    private String supervisionId;

    /**
     * 安全员
     */
    @Column(name="security_officer_id",nullable = true,length = 36)
    private String securityOfficerId;

    /**
     * 关联的合同
     */
    @Column(name="contract_id",nullable = true,length = 36)
    private String contractId;

    /**
     * 监管情况说明
     */
    @Column(name="supervise_desc",nullable = true,length = 255)
    private String superviseDesc;

    /**
     * 施工天气,晴、阴、雨、雪,可通过constructWeather获取域值
     */
    @Column(name="construct_weather",nullable = true,length = 36)
    private String constructWeather;

    /**
     * 有无动火
     */
    @Column(name="is_fire",nullable = true,length = 1)
    private Boolean isFire;

    /**
     * 有无登高
     */
    @Column(name="is_climb_up",nullable = true,length = 1)
    private Boolean isClimbUp;

    /**
     * 施工人数：手填，整数类型，需在前端做正则验证
     */
    @Column(name="construction_person_count",nullable = true,length = 255)
    private String constructionPersonCount;

    /**
     * 确认人
     */
    @Column(name="confirm_person_id",nullable = true,length = 36)
    private String confirmPersonId;

    /**
     * 确认时间
     */
    @Column(name="confirm_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmDate;

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
     * 所属站点id
     */
    @Column(name="site_id",nullable = true,length = 36)
    private String siteId;

    /**
     * 所属组织id
     */
    @Column(name="org_id",nullable = true,length = 36)
    private String orgId;

    public String getConstructionNum() {
        return constructionNum;
    }

    public void setConstructionNum(String constructionNum) {
        this.constructionNum = constructionNum;
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

    public String getSupervisionId() {
        return supervisionId;
    }

    public void setSupervisionId(String supervisionId) {
        this.supervisionId = supervisionId;
    }

    public String getSecurityOfficerId() {
        return securityOfficerId;
    }

    public void setSecurityOfficerId(String securityOfficerId) {
        this.securityOfficerId = securityOfficerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSuperviseDesc() {
        return superviseDesc;
    }

    public void setSuperviseDesc(String superviseDesc) {
        this.superviseDesc = superviseDesc;
    }

    public String getConstructWeather() {
        return constructWeather;
    }

    public void setConstructWeather(String constructWeather) {
        this.constructWeather = constructWeather;
    }

    public Boolean getFire() {
        return isFire;
    }

    public void setFire(Boolean fire) {
        isFire = fire;
    }

    public Boolean getClimbUp() {
        return isClimbUp;
    }

    public void setClimbUp(Boolean climbUp) {
        isClimbUp = climbUp;
    }

    public String getConstructionPersonCount() {
        return constructionPersonCount;
    }

    public void setConstructionPersonCount(String constructionPersonCount) {
        this.constructionPersonCount = constructionPersonCount;
    }

    public String getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(String confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
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

    @Override
    public String toString() {
        return "Construction{" +
                "constructionNum='" + constructionNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", supervisionId='" + supervisionId + '\'' +
                ", securityOfficerId='" + securityOfficerId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", superviseDesc='" + superviseDesc + '\'' +
                ", constructWeather='" + constructWeather + '\'' +
                ", isFire=" + isFire +
                ", isClimbUp=" + isClimbUp +
                ", constructionPersonCount='" + constructionPersonCount + '\'' +
                ", confirmPersonId='" + confirmPersonId + '\'' +
                ", confirmDate=" + confirmDate +
                ", remark='" + remark + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
