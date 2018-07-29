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
@ApiModel(value = "巡检计划", description = "巡检计划对应的Vo")
public class PatrolPlanVoForFilter extends EAMBaseFilterVo {

    @ApiModelProperty(value = "巡检计划ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "巡检计划编码")
    private String patrolplannum;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "巡检路线Id")
    private String patrolRouteId;

    @ApiModelProperty(value = "巡检状态")
    private List<String> status;

    @ApiModelProperty(value = "巡检类型")
    private List<String> type;

    @ApiModelProperty(value = "状态日期")
    private Date statusdate;

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

    @ApiModelProperty(value = "收藏")
    private boolean collect;

    @ApiModelProperty(value = "用户Id")
    private String personId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolplannum() {
        return patrolplannum;
    }

    public void setPatrolplannum(String patrolplannum) {
        this.patrolplannum = patrolplannum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
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

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }


    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPatrolRouteId() {
        return patrolRouteId;
    }

    public void setPatrolRouteId(String patrolRouteId) {
        this.patrolRouteId = patrolRouteId;
    }

    @Override
    public String toString() {
        return "PatrolPlanVoForFilter{" +
                "id='" + id + '\'' +
                ", patrolplannum='" + patrolplannum + '\'' +
                ", description='" + description + '\'' +
                ", patrolRouteId='" + patrolRouteId + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", statusdate=" + statusdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", words='" + words + '\'' +
                ", sorts='" + sorts + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                '}';
    }
}
