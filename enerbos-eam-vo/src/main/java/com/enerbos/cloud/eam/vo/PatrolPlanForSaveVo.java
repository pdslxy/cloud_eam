package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/1
 * @Description
 */
@ApiModel(value = "巡检计划", description = "巡检计划对应的Vo")
public class PatrolPlanForSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检点ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "创建时间(新增不需要传值)")
    private Date createtime;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "编码")
    private String patrolPlanNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "巡检状态")
    private String status;

    @ApiModelProperty(value = "状态日期")
    private Date statusdate;

    @ApiModelProperty(value = "巡检类型")
    private String type;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "巡检路线编码")
    private String patrolRouteId;

    @ApiModelProperty(value = "站点id")
    private String siteId;
    @ApiModelProperty(value = "组织id")
    private String orgId;


    @ApiModelProperty(value = "新增巡检项的集合")
    private List<PatrolPlanFrequencyVo> frequencyAddlist;

    @ApiModelProperty(value = "删除巡检项的id(多个用,隔开)")
    private List<String> frequencyDeleteIds;

    @ApiModelProperty(value = "序号")
    private Integer step;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPatrolRouteId() {
        return patrolRouteId;
    }

    public void setPatrolRouteId(String patrolRouteId) {
        this.patrolRouteId = patrolRouteId;
    }

    public List<PatrolPlanFrequencyVo> getFrequencyAddlist() {
        return frequencyAddlist;
    }

    public void setFrequencyAddlist(List<PatrolPlanFrequencyVo> frequencyAddlist) {
        this.frequencyAddlist = frequencyAddlist;
    }

    public List<String> getFrequencyDeleteIds() {
        return frequencyDeleteIds;
    }

    public void setFrequencyDeleteIds(List<String> frequencyDeleteIds) {
        this.frequencyDeleteIds = frequencyDeleteIds;
    }

    public String getPatrolPlanNum() {
        return patrolPlanNum;
    }

    public void setPatrolPlanNum(String patrolPlanNum) {
        this.patrolPlanNum = patrolPlanNum;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolPlanForSaveVo{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", patrolPlanNum='" + patrolPlanNum + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", statusdate=" + statusdate +
                ", type='" + type + '\'' +
                ", updatetime=" + updatetime +
                ", patrolRouteId='" + patrolRouteId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", frequencyAddlist=" + frequencyAddlist +
                ", frequencyDeleteIds=" + frequencyDeleteIds +
                ", step=" + step +
                '}';
    }
}
