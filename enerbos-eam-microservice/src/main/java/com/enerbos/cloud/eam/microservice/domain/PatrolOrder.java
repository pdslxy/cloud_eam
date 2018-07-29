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
 * @Description 巡检工单
 */
@Entity
@Table(name = "patrol_order")
public class PatrolOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 编码
     */
    @Column(name = "patrolordernum", nullable = false, length = 36)
    private String patrolOrderNum;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    /**
     * 巡检状态
     */
    @Column(name = "status", nullable = true, length = 10)
    private String status;
    /**
     * 巡检类型
     */
    @Column(name = "type", nullable = false, length = 36)
    private String type;

    /**
     * 创建人员id
     */
    @Column(name = "createpersonid", nullable = true, length = 36)
    private String createPersonId;

    /**
     * 分派人员id
     */
    @Column(name = "assignpersonid", nullable = true, length = 36)
    private String assignPersonId;

    /**
     * 执行人员id
     */
    @Column(name = "excutepersonid", nullable = true, length = 255)
    private String excutePersonId;

    /**
     * 巡检计划
     */
    @ManyToOne
    @JoinColumn(
            name = "patrolplanid"
    )
    @JsonIgnore
    private PatrolPlan patrolPlan;


    /**
     * 巡检路线
     */
    @ManyToOne
    @JoinColumn(
            name = "patrolrouteid"
    )
    @JsonIgnore
    private PatrolRoute patrolRoute;

    /**
     * 状态日期
     */
    @Column(name = "statusdate", nullable = false, length = 36)
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusdate;
    /**
     * 创建时间
     */
    @Column(name = "createtime", nullable = false, length = 36)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    /**
     * 更新时间
     */
    @Column(name = "updatetime", nullable = true, length = 36)
    @Temporal(TemporalType.TIMESTAMP)
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

    /**
     * 备注说明
     */
    @Column(name = "excute_remark", nullable = true, length = 255)
    private String excuteRemark;

    /**
     * 流程实例ID
     */
    @Column(name = "process_instance_id", nullable = true, length = 255)
    private String processInstanceId;

    /**
     * 提报日期
     */
    @Column(name = "report_date", nullable = true, length = 255)
    private Date reportDate;

    /**
     * 提报人id
     */
    @Column(name = "report_person_id", nullable = true, length = 36)
    private String reportPersonId;

    @Column(name = "is_begin_patrol", nullable = true, length = 1)
    private Boolean isBeginPatrol;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_patrol_date", nullable = true, length = 255)
    private Date beginPatrolDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getAssignPersonId() {
        return assignPersonId;
    }

    public void setAssignPersonId(String assignPersonId) {
        this.assignPersonId = assignPersonId;
    }

    public String getExcutePersonId() {
        return excutePersonId;
    }

    public void setExcutePersonId(String excutePersonId) {
        this.excutePersonId = excutePersonId;
    }

    public PatrolPlan getPatrolPlan() {
        return patrolPlan;
    }

    public void setPatrolPlan(PatrolPlan patrolPlan) {
        this.patrolPlan = patrolPlan;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPatrolOrderNum() {
        return patrolOrderNum;
    }

    public void setPatrolOrderNum(String patrolOrderNum) {
        this.patrolOrderNum = patrolOrderNum;
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

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Boolean getIsBeginPatrol() {
        return isBeginPatrol;
    }

    public void setIsBeginPatrol(Boolean isbeginPatrol) {
        this.isBeginPatrol = isbeginPatrol;
    }

    public Date getBeginPatrolDate() {
        return beginPatrolDate;
    }

    public void setBeginPatrolDate(Date beginPatrolDate) {
        this.beginPatrolDate = beginPatrolDate;
    }

    public PatrolRoute getPatrolRoute() {
        return patrolRoute;
    }

    public void setPatrolRoute(PatrolRoute patrolRoute) {
        this.patrolRoute = patrolRoute;
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getExcuteRemark() {
        return excuteRemark;
    }

    public void setExcuteRemark(String excuteRemark) {
        this.excuteRemark = excuteRemark;
    }

    @Override
    public String toString() {
        return "PatrolOrder{" +
                "id='" + id + '\'' +
                ", patrolOrderNum='" + patrolOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", createPersonId='" + createPersonId + '\'' +
                ", assignPersonId='" + assignPersonId + '\'' +
                ", excutePersonId='" + excutePersonId + '\'' +
                ", patrolPlan=" + patrolPlan +
                ", patrolRoute=" + patrolRoute +
                ", statusdate=" + statusdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", remark='" + remark + '\'' +
                ", excuteRemark='" + excuteRemark + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", reportDate=" + reportDate +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", isBeginPatrol=" + isBeginPatrol +
                ", beginPatrolDate=" + beginPatrolDate +
                '}';
    }
}
