package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version EAM2.0
 * @date 2017年8月10日 14:03:12
 * @Description 巡检标准
 */
@Entity
@Table(name = "patrol_stand")
public class PatrolStand extends EnerbosBaseEntity implements Serializable {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;


    /**
     * 巡检编码
     */
    @Column(unique = true, name = "patrol_stand_num", nullable = false, length = 36)
    private String patrolStandNum;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    /**
     * 状态   草稿、活动、不活动
     */
    @Column(name = "status", nullable = true, length = 36)
    private String status;


    /**
     * 类型
     */

    @Column(name = "type", nullable = true, length = 36)
    private String type;

    /**
     * 分类id
     */
    @Column(name = "classstructure_id", nullable = true, length = 36)
    private String classstructureid;


    /**
     * 状态日期
     */
    @Column(name = "status_date", nullable = true, length = 20)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 组织id
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;
    /**
     * 站点id
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    /**
     * 备注
     */
    @Column(name = "remark", nullable = true, length = 255)
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolStandNum() {
        return patrolStandNum;
    }

    public void setPatrolStandNum(String patrolStandNum) {
        this.patrolStandNum = patrolStandNum;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassstructureid() {
        return classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PatrolStand{" +
                "id='" + id + '\'' +
                ", patrolStandNum='" + patrolStandNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", classstructureid='" + classstructureid + '\'' +
                ", statusDate=" + statusDate +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

}
