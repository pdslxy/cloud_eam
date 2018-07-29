package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

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
 * @Description 例行工作实体类
 */
@Entity
@Table(name = "eam_headquarters_daily")
public class HeadquartersDaily implements Serializable {
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
     * 总部计划id
     */
    @Column(name = "headquartersPlan_Id", length = 36)
    private String headquartersPlanId;

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
     * 频率类型  --  工作频率、检查频率
     */
    @Column(name = "check_frequency", length = 20)
    private String checkFrequency;

    /**
     * 执行频次
     */
    @Column(name = "times")
    private Long times;

    /**
     * 执行频率  天、月、年
     */
    @Column(name = "frequency")
    private String frequency;

    /**
     * 工作类型  空调、强电、弱电
     */
    @Column(name = "work_type")
    private String workType;

    /**
     *
     */
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    /**
     *
     */
    @Column(name = "deadline")
    private Long deadline;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


    /**
     * 创建人
     */
    @Column(name = "create_user", length = 50)
    private String  createUser;


    public String getHeadquartersPlanId() {
        return headquartersPlanId;
    }

    public void setHeadquartersPlanId(String headquartersPlanId) {
        this.headquartersPlanId = headquartersPlanId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum == null ? null : planNum.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem == null ? null : checkItem.trim();
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
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public String getCheckFrequency() {
        return checkFrequency;
    }

    public void setCheckFrequency(String checkFrequency) {
        this.checkFrequency = checkFrequency == null ? null : checkFrequency.trim();
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}