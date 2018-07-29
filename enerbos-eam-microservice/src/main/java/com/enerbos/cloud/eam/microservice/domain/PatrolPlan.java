package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @date 2017/8/2
 * @Description 巡检计划
 */
@Entity
@Table(name = "patrol_plan")
public class PatrolPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 编码
     */
    @Column(name = "patrolplannum", nullable = false, length = 36)
    private String patrolPlanNum;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    /**
     * 巡检状态
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status;
    /**
     * 巡检类型
     */
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @OneToOne
    @JoinColumn(
            name = "patrolrouteid"
    )
    @JsonIgnore
    private PatrolRoute patrolRoute;

    /**
     * 状态日期
     */
    @Column(name = "statusdate", nullable = false, length = 36)
    private Date statusdate;
    /**
     * 创建时间
     */
    @Column(name = "createtime", nullable = false, length = 36)
    private Date createtime;

    /**
     * 更新时间
     */
    @Column(name = "updatetime", nullable = true, length = 36)
    private Date updatetime;

    /**
     * 组织id
     */
    @Column(name = "orgid", nullable = false, length = 36)
    private String orgId;
    /**
     * 站点id
     */
    @Column(name = "siteid", nullable = false, length = 36)
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

    public String getPatrolPlanNum() {
        return patrolPlanNum;
    }

    public void setPatrolPlanNum(String patrolPlanNum) {
        this.patrolPlanNum = patrolPlanNum;
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

    public PatrolRoute getPatrolRoute() {
        return patrolRoute;
    }

    public void setPatrolRoute(PatrolRoute patrolRoute) {
        this.patrolRoute = patrolRoute;
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
        return "PatrolPlan{" +
                "id='" + id + '\'' +
                ", patrolPlanNum='" + patrolPlanNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", patrolRoute=" + patrolRoute +
                ", statusdate=" + statusdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
