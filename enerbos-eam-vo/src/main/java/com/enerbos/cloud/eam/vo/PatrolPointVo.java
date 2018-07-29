package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2017/7/12
 * @Description
 */
@ApiModel(value = "巡检点台账", description = "巡检点台账对应的Vo")
public class PatrolPointVo implements Serializable {
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

    @ApiModelProperty(value = "巡检状态描述")
    private String statusDescription;

    @ApiModelProperty(value = "巡检类型")
    private String type;

    @ApiModelProperty(value = "巡检类型描述")
    private String typeDescription;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "位置id")
    private String lochierarchyid;

    @ApiModelProperty(value = "位置描述")
    private String locationDsr;

    @ApiModelProperty(value = "位置描述")
    private String locationNum;

    @ApiModelProperty(value = "站点id")
    private String siteid;
    @ApiModelProperty(value = "组织id")
    private String orgid;

    @ApiModelProperty(value = "站点名称")
    private String site;
    @ApiModelProperty(value = "组织名称")
    private String org;
    @ApiModelProperty(value = "巡检项的集合")
    private List<PatrolTermVo> patrolTermVolist;
    @ApiModelProperty(value = "二维码编码")
    private String qrCodeNum;
    @ApiModelProperty(value = "是否有数据更新")
    private String isupdatedata;

    @ApiModelProperty(value = "是否合格")
    private String isqualified;


    @ApiModelProperty(value = "序号")
    private Integer step;

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

    @ApiModelProperty(value = "是否收藏")
    private boolean collect;


    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startdate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date enddate;

    @ApiModelProperty(value = "巡检记录项的集合")
    private List<PatrolRecordTermVo> patrolRecordTermVoList;


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

    public boolean isIsqrcode() {
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

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public List<PatrolTermVo> getPatrolTermVolist() {
        return patrolTermVolist;
    }

    public void setPatrolTermVolist(List<PatrolTermVo> patrolTermVolist) {
        this.patrolTermVolist = patrolTermVolist;
    }

    public String getQrCodeNum() {
        return qrCodeNum;
    }

    public void setQrCodeNum(String qrCodeNum) {
        this.qrCodeNum = qrCodeNum;
    }

    public String getIsupdatedata() {
        return isupdatedata;
    }

    public void setIsupdatedata(String isupdatedata) {
        this.isupdatedata = isupdatedata;
    }

    public String getLocationDsr() {
        return locationDsr;
    }

    public void setLocationDsr(String locationDsr) {
        this.locationDsr = locationDsr;
    }

    public String getLocationNum() {
        return locationNum;
    }

    public void setLocationNum(String locationNum) {
        this.locationNum = locationNum;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public List<PatrolRecordTermVo> getPatrolRecordTermVoList() {
        return patrolRecordTermVoList;
    }

    public void setPatrolRecordTermVoList(List<PatrolRecordTermVo> patrolRecordTermVoList) {
        this.patrolRecordTermVoList = patrolRecordTermVoList;
    }

    @Override
    public String toString() {
        return "PatrolPointVo{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", description='" + description + '\'' +
                ", intelsoftware='" + intelsoftware + '\'' +
                ", isqrcode=" + isqrcode +
                ", patrolnum='" + patrolnum + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", statusDescription='" + statusDescription + '\'' +
                ", type='" + type + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                ", updatetime=" + updatetime +
                ", lochierarchyid='" + lochierarchyid + '\'' +
                ", locationDsr='" + locationDsr + '\'' +
                ", locationNum='" + locationNum + '\'' +
                ", siteid='" + siteid + '\'' +
                ", orgid='" + orgid + '\'' +
                ", site='" + site + '\'' +
                ", org='" + org + '\'' +
                ", patrolTermVolist=" + patrolTermVolist +
                ", qrCodeNum='" + qrCodeNum + '\'' +
                ", isupdatedata='" + isupdatedata + '\'' +
                ", isqualified='" + isqualified + '\'' +
                ", step=" + step +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", collect=" + collect +
                '}';
    }
}
