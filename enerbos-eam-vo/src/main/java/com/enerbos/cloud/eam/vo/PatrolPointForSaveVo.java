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
@ApiModel(value = "巡检点台账", description = "巡检点台账对应的Vo")
public class PatrolPointForSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检点ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "创建时间(新增不需要传值)")
    private Date createtime;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "智能硬件")
    private String intelsoftware;

    @ApiModelProperty(value = "是否有二维码")
    private boolean isqrcode;

    @ApiModelProperty(value = "编码")
    private String patrolnum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "巡检状态")
    private String status;


    @ApiModelProperty(value = "巡检类型")
    private String type;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "位置id")
    private String lochierarchyid;

    @ApiModelProperty(value = "站点id")
    private String siteid;
    @ApiModelProperty(value = "组织id")
    private String orgid;

    @ApiModelProperty(value = "站点名称")
    private String site;
    @ApiModelProperty(value = "组织名称")
    private String org;

    @ApiModelProperty(value = "新增巡检项的集合")
    private List<PatrolTermVo> termAddlist;

    @ApiModelProperty(value = "删除巡检项的id(多个用,隔开)")
    private String termDeleteIds;

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

    @ApiModelProperty(value = "二维码编码")
    private String qrCodeNum;


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

    public boolean getIsqrcode() {
        return isqrcode;
    }

    public void setIsqrcode(boolean isqrcode) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLochierarchyid() {
        return lochierarchyid;
    }

    public void setLochierarchyid(String lochierarchyid) {
        this.lochierarchyid = lochierarchyid;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
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

    public List<PatrolTermVo> getTermAddlist() {
        return termAddlist;
    }

    public void setTermAddlist(List<PatrolTermVo> termAddlist) {
        this.termAddlist = termAddlist;
    }

    public String getTermDeleteIds() {
        return termDeleteIds;
    }

    public void setTermDeleteIds(String termDeleteIds) {
        this.termDeleteIds = termDeleteIds;
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

    public String getQrCodeNum() {
        return qrCodeNum;
    }

    public void setQrCodeNum(String qrCodeNum) {
        this.qrCodeNum = qrCodeNum;
    }

    @Override
    public String toString() {
        return "PatrolPointForSaveVo{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", intelsoftware='" + intelsoftware + '\'' +
                ", isqrcode=" + isqrcode +
                ", patrolnum='" + patrolnum + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", updatetime=" + updatetime +
                ", lochierarchyid='" + lochierarchyid + '\'' +
                ", siteid='" + siteid + '\'' +
                ", orgid='" + orgid + '\'' +
                ", site='" + site + '\'' +
                ", org='" + org + '\'' +
                ", termAddlist=" + termAddlist +
                ", termDeleteIds='" + termDeleteIds + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", qrCodeNum='" + qrCodeNum + '\'' +
                '}';
    }
}
