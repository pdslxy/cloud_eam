package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/17.
 */

@Entity
@Table(name = "eam_archives")
public class HeadArchives implements Serializable {
    private static final long serialVersionUID = -1205663637397029364L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    
    
    /**
     * 资料编号
     */
    @Column(name = "material_num", length = 32)
    private String materialNum;

    /**
     * 档案名称
     */
    @Column(name="material_name", length = 50)
    private String materialName;

    /**
     * 发证部门
     */
    @Column(name = "department", length = 32)
    private String department;
    

    /**
     * 资料类型
     */
    @Column(name = "material_type", length = 32)
    private String materialType;

    /**
     * 档案编号
     */
    @Column(name = "archives_num", length = 32)
    private String archivesNum;

    /**
     * 核发日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 状态
     */
    @Column(name = "status", length = 10)
    private String status;

    /**
     * 资料版本
     */
    @Column(name = "material_version", length = 10)
    private String materialVersion;

    /**
     * 有效期起始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_Start_time")
    private Date validStartTime;

    /**
     * 有效期截止时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_End_time")
    private Date validEndTime;

    /**
     * 备注信息
     */
    @Column(name = "remark", length = 500)
    private String remark;
    

    /**
     * 总部
     */
    @Column(name = "head_Quarters",length = 10)
    private String headQuarters;

    /**
     * 项目
     */
    @Column(name = "project",length = 10)
    private String project;
    

    /**
     * 组织id
     */
    @Column(name = "site_Id", columnDefinition = "varchar(255) default '001'")
    private String siteId;

    /**
     * 站点id
     */
    @Column(name = "org_Id", columnDefinition = "varchar(255) default '001'")
    private String orgId;



    /**
     * 存放位置
     */
    @Column(name = "position", length = 100)
    private String position;

    /**
     * 提交人
     */
    @Column(name = "creator", length = 100)
    private String creator;
    

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
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getArchivesNum() {
        return archivesNum;
    }

    public void setArchivesNum(String archivesNum) {
        this.archivesNum = archivesNum;
    }

    public String getMaterialVersion() {
        return materialVersion;
    }

    public void setMaterialVersion(String materialVersion) {
        this.materialVersion = materialVersion;
    }

    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getHeadQuarters() {
        return headQuarters;
    }

    public void setHeadQuarters(String headQuarters) {
        this.headQuarters = headQuarters;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "HeadArchives{" +
                "id='" + id + '\'' +
                ", materialNum='" + materialNum + '\'' +
                ", materialName='" + materialName + '\'' +
                ", department='" + department + '\'' +
                ", materialType='" + materialType + '\'' +
                ", archivesNum='" + archivesNum + '\'' +
                ", createDate=" + createDate +
                ", status='" + status + '\'' +
                ", materialVersion='" + materialVersion + '\'' +
                ", validStartTime=" + validStartTime +
                ", validEndTime=" + validEndTime +
                ", remark='" + remark + '\'' +
                ", headQuarters='" + headQuarters + '\'' +
                ", project='" + project + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", position='" + position + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
