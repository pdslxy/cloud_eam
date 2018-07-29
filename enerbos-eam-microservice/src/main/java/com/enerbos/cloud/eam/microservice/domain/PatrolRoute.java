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
@Table(name = "patrol_route")
public class PatrolRoute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 编码
     */
    @Column(name = "patrolroutenum", nullable = false, length = 36)
    private String patrolRouteNum;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    /**
     * 活动状态
     */
    @Column(name = "status", nullable = false, length = 36)
    private String status;

    /**
     * 路线类型
     */
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    /**
     * 状态日期
     */
    @Column(name = "statusdate", nullable = false, length = 36)
    private Date statusdate;

    @Column(name = "createtime", nullable = false, length = 36)
    private Date createtime;

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

    @Column(name = "remark", nullable = true, length = 255)
    private String remark;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
        return "PatrolRoute{" +
                "id='" + id + '\'' +
                ", patrolRouteNum='" + patrolRouteNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", statusdate=" + statusdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
