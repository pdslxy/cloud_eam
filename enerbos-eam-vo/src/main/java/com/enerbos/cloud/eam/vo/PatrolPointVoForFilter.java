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
@ApiModel(value = "巡检点台账", description = "巡检点台账对应的Vo")
public class PatrolPointVoForFilter extends EAMBaseFilterVo {

    @ApiModelProperty(value = "巡检点ID(新增不需要传值)")
    private String id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(新增不需要传值)")
    private Date createtime;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 智能硬件
     */
    @ApiModelProperty(value = "智能硬件")
    private String intelsoftware;
    /**
     * 是否有二维码
     */
    @ApiModelProperty(value = "是否有二维码")
    private Boolean isqrcode;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String patrolnum;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 巡检状态
     */
    @ApiModelProperty(value = "巡检状态")
    private List<String> status;
    /**
     * 巡检类型
     */
    @ApiModelProperty(value = "巡检类型")
    private List<String> type;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;
    /**
     * 位置id
     */
    @ApiModelProperty(value = "位置id")
    private String lochierarchyid;

    @ApiModelProperty(value = "模糊查询关键词")
    private String words;

    @ApiModelProperty("排序参数")
    private String sorts;
    @ApiModelProperty("组织 id")
    private String orgId;
    @ApiModelProperty("站点 id")
    private String siteId;

    @ApiModelProperty("关联巡检路线id")
    private String patrolRouteId;

    @ApiModelProperty("要过滤的ID")
    private String ids;

    /**
     * iBeacon 设备的主 id
     */
    @ApiModelProperty(value = "iBeacon 设备的主 id")
    private String major;
    /**
     * iBeacon 设备的次 id
     */
    @ApiModelProperty(value = "iBeacon 设备的次 id")
    private String minor;

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

    public String getIntelsoftware() {
        return intelsoftware;
    }

    public void setIntelsoftware(String intelsoftware) {
        this.intelsoftware = intelsoftware;
    }

    public Boolean getIsqrcode() {
        return isqrcode;
    }

    public void setIsqrcode(Boolean isqrcode) {
        this.isqrcode = isqrcode;
    }

    public String getPatrolnum() {
        return patrolnum;
    }

    public void setPatrolnum(String patrolnum) {
        this.patrolnum = patrolnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getLochierarchyid() {
        return lochierarchyid;
    }

    public void setLochierarchyid(String lochierarchyid) {
        this.lochierarchyid = lochierarchyid;
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

    public String getPatrolRouteId() {
        return patrolRouteId;
    }

    public void setPatrolRouteId(String patrolRouteId) {
        this.patrolRouteId = patrolRouteId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
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

    @Override
    public String toString() {
        return "PatrolPointVoForFilter{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", intelsoftware='" + intelsoftware + '\'' +
                ", isqrcode=" + isqrcode +
                ", patrolnum='" + patrolnum + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", updatetime=" + updatetime +
                ", lochierarchyid='" + lochierarchyid + '\'' +
                ", words='" + words + '\'' +
                ", sorts='" + sorts + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", patrolRouteId='" + patrolRouteId + '\'' +
                ", ids='" + ids + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", collect=" + collect +
                ", personId='" + personId + '\'' +
                '}';
    }
}
