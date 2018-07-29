package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/17
 * @Description
 */
@ApiModel(value = "频率", description = "频率对应的Vo")
public class PatrolPlanFrequencyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "频率ID")
    private String id;

    @ApiModelProperty(value = "频率")
    private String frequency;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "巡检路线id")
    private String patrolPlanId;

    @ApiModelProperty(value = "下次生成日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date nextdate;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatetime;

    @ApiModelProperty("组织 id")
    private String orgId;
    @ApiModelProperty("站点 id")
    private String siteId;

    @ApiModelProperty(value = "序号")
    private Integer step;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPatrolPlanId() {
        return patrolPlanId;
    }

    public void setPatrolPlanId(String patrolPlanId) {
        this.patrolPlanId = patrolPlanId;
    }

    public Date getNextdate() {
        return nextdate;
    }

    public void setNextdate(Date nextdate) {
        this.nextdate = nextdate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolPlanFrequencyVo{" +
                "id='" + id + '\'' +
                ", frequency='" + frequency + '\'' +
                ", unit='" + unit + '\'' +
                ", remark='" + remark + '\'' +
                ", patrolPlanId='" + patrolPlanId + '\'' +
                ", nextdate=" + nextdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", step=" + step +
                '}';
    }
}
