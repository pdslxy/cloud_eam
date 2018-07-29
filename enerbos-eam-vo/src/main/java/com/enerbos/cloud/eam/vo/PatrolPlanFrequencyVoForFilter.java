package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@ApiModel(value = "巡检计划频率", description = "巡检计划频率对应的Vo")
public class PatrolPlanFrequencyVoForFilter extends EAMBaseFilterVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检计划频率ID(新增不需要传值)")
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
    private Date nextdate;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "模糊查询关键词")
    private String words;

    @ApiModelProperty("排序参数")
    private String sorts;
    @ApiModelProperty("组织 id")
    private String orgId;
    @ApiModelProperty("站点 id")
    private String siteId;

    @ApiModelProperty(value = "序号")
    private String step;

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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String getSorts() {
        return sorts;
    }

    @Override
    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolPlanFrequencyVoForFilter{" +
                "id='" + id + '\'' +
                ", frequency='" + frequency + '\'' +
                ", unit='" + unit + '\'' +
                ", remark='" + remark + '\'' +
                ", patrolPlanId='" + patrolPlanId + '\'' +
                ", nextdate=" + nextdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", words='" + words + '\'' +
                ", sorts='" + sorts + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
