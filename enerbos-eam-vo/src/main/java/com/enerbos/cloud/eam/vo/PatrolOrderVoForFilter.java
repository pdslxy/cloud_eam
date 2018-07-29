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
 * @date 2017/8/7
 * @Description
 */
@ApiModel(value = "巡检工单", description = "巡检工单对应的Vo")
public class PatrolOrderVoForFilter extends EAMBaseFilterVo {

    @ApiModelProperty(value = "巡检工单ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "巡检工单编码")
    private String patrolOrderNum;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "巡检状态")
    private List<String> status;
    /**
     * 巡检类型
     */
    @ApiModelProperty(value = "巡检类型")
    private List<String> type;

    @ApiModelProperty(value = "状态日期")
    private Date statusdate;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "创建时间(本周，本月，全部)")
    private String createDate; 

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

    @ApiModelProperty("巡检计划id")
    private String patrolPlanId;

    @ApiModelProperty("提报开始时间")
    private String startDate;

    @ApiModelProperty("提报结束时间")
    private String endDate;

    @ApiModelProperty(value = "收藏")
    private boolean collect;

    @ApiModelProperty(value = "收藏人Id")
    private String collectPersonId;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolOrderNum() {
        return patrolOrderNum;
    }

    public void setPatrolOrderNum(String patrolOrderNum) {
        this.patrolOrderNum = patrolOrderNum;
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

    public String getPatrolPlanId() {
        return patrolPlanId;
    }

    public void setPatrolPlanId(String patrolPlanId) {
        this.patrolPlanId = patrolPlanId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }


    public String getCollectPersonId() {
        return collectPersonId;
    }

    public void setCollectPersonId(String collectPersonId) {
        this.collectPersonId = collectPersonId;
    }

    @Override
    public String toString() {
        return "PatrolOrderVoForFilter{" +
                "id='" + id + '\'' +
                ", patrolOrderNum='" + patrolOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", statusdate=" + statusdate +
                ", createtime=" + createtime +
                ", createDate='" + createDate + '\'' +
                ", updatetime=" + updatetime +
                ", words='" + words + '\'' +
                ", sorts='" + sorts + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", patrolPlanId='" + patrolPlanId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", collect=" + collect +
                ", collectPersonId='" + collectPersonId + '\'' +
                '}';
    }
}
