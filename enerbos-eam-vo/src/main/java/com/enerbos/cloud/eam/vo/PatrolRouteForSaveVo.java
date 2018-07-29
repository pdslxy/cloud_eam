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
@ApiModel(value = "巡检路线", description = "巡检路线对应的Vo")
public class PatrolRouteForSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检路线ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "创建时间(新增不需要传值)")
    private Date createtime;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "编码")
    private String patrolRouteNum;

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

    @ApiModelProperty(value = "站点id")
    private String siteId;
    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "站点名称")
    private String site;
    @ApiModelProperty(value = "组织名称")
    private String org;

    @ApiModelProperty(value = "新增巡检点的集合")
    private List<PatrolPointVo> pointAddlist;

    @ApiModelProperty(value = "删除巡检点的id(多个用,隔开)")
    private String pointDeleteIds;

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

    public String getPatrolRouteNum() {
        return patrolRouteNum;
    }

    public void setPatrolRouteNum(String patrolRouteNum) {
        this.patrolRouteNum = patrolRouteNum;
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


    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public List<PatrolPointVo> getPointAddlist() {
        return pointAddlist;
    }

    public void setPointAddlist(List<PatrolPointVo> pointAddlist) {
        this.pointAddlist = pointAddlist;
    }

    public String getPointDeleteIds() {
        return pointDeleteIds;
    }

    public void setPointDeleteIds(String pointDeleteIds) {
        this.pointDeleteIds = pointDeleteIds;
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
        return "PatrolRouteForSaveVo{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", patrolRouteNum='" + patrolRouteNum + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", statusdate=" + statusdate +
                ", type='" + type + '\'' +
                ", updatetime=" + updatetime +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", site='" + site + '\'' +
                ", org='" + org + '\'' +
                ", pointAddlist=" + pointAddlist +
                ", pointDeleteIds='" + pointDeleteIds + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
