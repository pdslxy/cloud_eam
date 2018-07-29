package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 16:53
 * @Description 总部计划实体类
 */
@Entity
@Table(name = "eam_headquarters_plan")
public class HeadquartersPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 计划编码
     */
    @Column(name = "plan_num", length = 50)
    private String planNum;
    /**
     * 计划名称
     */
    @Column(name = "plan_name", length = 200)
    private String planName;
    /**
     * 计划描述
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * 计划性质    --  标准、临时、自定义
     */
    @Column(name = "nature", length = 36)
    private String nature;

    /**
     * 状态   --活动、不活动、草稿
     */
    @Column(name = "status", length = 10)
    private String status;

    /**
     * 检查项
     */
    @Column(name = "check_item", length = 36)
    private String checkItem;

    /**
     * 有效开始时间
     */
    @Column(name = "valid_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartDate;

    /**
     * 有效结束时间
     */
    @Column(name = "valid_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndDate;

    /**
     * 组织id
     */
    @Column(name = "org_id", length = 36)
    private String orgId;

    /**
     * 站点id
     */
    @Column(name = "site_id", length = 36)
    private String siteId;

    /**
     * 创建人
     */
    @Column(name = "create_User", length = 36)
    private String createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public Date getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
