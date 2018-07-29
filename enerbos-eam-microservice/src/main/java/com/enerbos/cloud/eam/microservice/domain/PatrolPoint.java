package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
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
@Entity
@Table(name = "patrol_point")
public class PatrolPoint implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 创建时间
     */
    @Column
    private Date createtime;
    /**
     * 描述
     */
    @Column
    private String description;
    /**
     * 智能硬件
     */
    @Column
    private String intelsoftware;
    /**
     * 是否有二维码
     */
    @Column
    private boolean isqrcode;
    /**
     * 编码
     */
    @Column
    private String patrolnum;
    /**
     * 备注
     */
    @Column
    private String remark;
    /**
     * 巡检状态
     */
    @Column
    private String status;
    /**
     * 巡检类型
     */
    @Column
    private String type;
    /**
     * 更新时间
     */
    @Column
    private Date updatetime;
    /**
     * 位置id
     */
    @Column(length = 36)
    private String lochierarchyid;
    /**
     * 组织id
     */
    @Column(length = 36)
    private String orgid;
    /**
     * 站点id
     */
    @Column(length = 36)
    private String siteid;

    @Column(name = "isupdatedata", nullable = true, length = 1)
    private boolean isUpdateData;

    @Column(name = "qrcodenum", nullable = true, length = 255)
    private String qrCodeNum;

    /**
     * iBeacon 设备的主 id
     */
    @Column(name = "major", nullable = true, length = 128)
    private String major;
    /**
     * iBeacon 设备的次 id
     */
    @Column(name = "minor", nullable = true, length = 128)
    private String minor;


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

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public boolean isUpdateData() {
        return isUpdateData;
    }

    public void setUpdateData(boolean updateData) {
        isUpdateData = updateData;
    }

    public String getQrCodeNum() {
        return qrCodeNum;
    }

    public void setQrCodeNum(String qrCodeNum) {
        this.qrCodeNum = qrCodeNum;
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

    @Override
    public String toString() {
        return "PatrolPoint{" +
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
                ", orgid='" + orgid + '\'' +
                ", siteid='" + siteid + '\'' +
                ", isUpdateData=" + isUpdateData +
                ", qrCodeNum='" + qrCodeNum + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                '}';
    }
}
