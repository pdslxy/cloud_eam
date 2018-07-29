package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "例行工作vo")
public class HeadquartersDailyVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键ID(新增不需要传值)")
    private String id;

    @ApiModelProperty(value = "计划编号")
    private String planNum;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "计划描述")
    private String description;

    @ApiModelProperty(value = "计划性质--标准、临时、自定义")
    private String nature;

    @ApiModelProperty(value = "状态   --活动、不活动、草稿")
    private String status;

    @ApiModelProperty(value = "检查项")
    private String checkItem;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "站点名称")
    private String siteName;

    @ApiModelProperty(value = "有效开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartDate;

    @ApiModelProperty(value = "有效结束时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndDate;

    @ApiModelProperty(value = "频率类型  --  工作频率、检查频率")
    private String checkFrequency;

    @ApiModelProperty(value = "执行频次")
    private Long times;

    @ApiModelProperty(value = "执行频率  天、月、年")
    private String frequency;

    @ApiModelProperty(value = "工作类型  空调、强电、弱电")
    private String workType;

    @ApiModelProperty(value = "下一生成日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "")
    private Long deadline;

    @ApiModelProperty(value = "总部计划Id")
    private String  headquartersPlanId;
    @ApiModelProperty(value = "总部计划编号")
    private String  headquartersPlanNum;
    @ApiModelProperty(value = "总部计划描述")
    private String  planDescription;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String  createUser;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getHeadquartersPlanId() {
        return headquartersPlanId;
    }

    public void setHeadquartersPlanId(String headquartersPlanId) {
        this.headquartersPlanId = headquartersPlanId;
    }

    public String getHeadquartersPlanNum() {
        return headquartersPlanNum;
    }

    public void setHeadquartersPlanNum(String headquartersPlanNum) {
        this.headquartersPlanNum = headquartersPlanNum;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
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
}