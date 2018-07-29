package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

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
@ApiModel(value = "巡检路线", description = "巡检路线对应的Vo")
public class PatrolRouteVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检路线ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "巡检路线编码")
    private String patrolRouteNum;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "活动状态")
    private String status;


    @ApiModelProperty(value = "状态日期")
    private Date statusdate;

    @ApiModelProperty(value = "活动状态描述")
    private String statusDescription;

    @ApiModelProperty(value = "巡检类型")
    private String type;

    @ApiModelProperty(value = "巡检类型描述")
    private String typeDescription;


    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "站点")
    private String site;

    @ApiModelProperty(value = "组织")
    private String org;

    @ApiModelProperty(value = "巡检点集合")
    private List<PatrolPointVo> patrolPointVoList;

    @ApiModelProperty(value = "是否收藏")
    private boolean collect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolRouteNum() {
        return patrolRouteNum;
    }

    public void setPatrolRouteNum(String patrolRouteNum) {
        this.patrolRouteNum = patrolRouteNum;
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

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
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

    public List<PatrolPointVo> getPatrolPointVoList() {
        return patrolPointVoList;
    }

    public void setPatrolPointVoList(List<PatrolPointVo> patrolPointVoList) {
        this.patrolPointVoList = patrolPointVoList;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    @Override
    public String toString() {
        return "PatrolRouteVo{" +
                "id='" + id + '\'' +
                ", patrolRouteNum='" + patrolRouteNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusdate=" + statusdate +
                ", statusDescription='" + statusDescription + '\'' +
                ", type='" + type + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", remark='" + remark + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", site='" + site + '\'' +
                ", org='" + org + '\'' +
                ", patrolPointVoList=" + patrolPointVoList +
                ", collect=" + collect +
                '}';
    }
}
