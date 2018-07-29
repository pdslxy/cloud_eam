package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * @Description 例行工作实体类
 */
@ApiModel(value = "总部计划，生成例行工作vo")
public class HeadquartersDailyVoForCreateWork implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选中的总部计划ID集合")
    private List<String> ids;

    @ApiModelProperty(value = "频率类型  --  工作频率、检查频率")
    private String checkFrequency;

    @ApiModelProperty(value = "执行频次")
    private Long times;

    @ApiModelProperty(value = "执行频率  天、月、年")
    private String frequency;

    @ApiModelProperty(value = "工作类型  空调、强电、弱电")
    private String workType;

    @ApiModelProperty(value = "下次执行时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "完成期限")
    private Long deadline;

    @ApiModelProperty(value = "站点ID")
    private String siteId;
    @ApiModelProperty(value = "组织Id")
    private String orgId;


    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
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
}