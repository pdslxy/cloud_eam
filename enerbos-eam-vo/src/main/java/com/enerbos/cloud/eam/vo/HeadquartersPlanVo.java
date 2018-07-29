package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@ApiModel(value = "总部计划")
public class HeadquartersPlanVo implements Serializable {
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

    @ApiModelProperty(value = "应用范围")
    private List<String>  planSite;

    @ApiModelProperty(value = "应用范围站点名称")
    private String planSiteName;
    @ApiModelProperty(value = "是否生成例行工作计划")
    private boolean IsCreateRoutineWork;

    public List<String> getPlanSite() {
        return planSite;
    }

    public void setPlanSite(List<String> planSite) {
        this.planSite = planSite;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

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
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPlanSiteName() {
        return planSiteName;
    }

    public void setPlanSiteName(String planSiteName) {
        this.planSiteName = planSiteName;
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

    public boolean isCreateRoutineWork() {
        return IsCreateRoutineWork;
    }

    public void setCreateRoutineWork(boolean createRoutineWork) {
        IsCreateRoutineWork = createRoutineWork;
    }
}
