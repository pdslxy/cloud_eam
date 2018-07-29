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
 * @date 2017/8/24
 * @Description 巡检点记录
 */
@Entity
@Table(name = "patrol_record")
public class PatrolRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(
            name = "patrolorderid"
    )
    @JsonIgnore
    private PatrolOrder patrolOrder;

    @ManyToOne
    @JoinColumn(
            name = "patrolpointid"
    )
    @JsonIgnore
    private PatrolPoint patrolPoint;

    @Column(name = "patrol_point_dsr", nullable = true, length = 255)
    private String patrolPointDsr;
    /**
     * 是否合格
     */
    @Column(name = "isqualified", nullable = true, length = 1)
    private String isqualified;

    /**
     * 巡检记录描述
     */
    @Column(name = "patrolrecorddsr", nullable = true, length = 255)
    private String patrolRecordDsr;

    @Column(name = "startdate", nullable = true, length = 36)
    private Date startdate;

    @Column(name = "enddate", nullable = true, length = 36)
    private Date enddate;

    @Column(name = "duration", nullable = true)
    private Integer duration;

    @Column(name = "remark", nullable = true, length = 255)
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PatrolOrder getPatrolOrder() {
        return patrolOrder;
    }

    public void setPatrolOrder(PatrolOrder patrolOrder) {
        this.patrolOrder = patrolOrder;
    }

    public PatrolPoint getPatrolPoint() {
        return patrolPoint;
    }

    public void setPatrolPoint(PatrolPoint patrolPoint) {
        this.patrolPoint = patrolPoint;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
    }

    public String getPatrolRecordDsr() {
        return patrolRecordDsr;
    }

    public void setPatrolRecordDsr(String patrolRecordDsr) {
        this.patrolRecordDsr = patrolRecordDsr;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPatrolPointDsr() {
        return patrolPointDsr;
    }

    public void setPatrolPointDsr(String patrolPointDsr) {
        this.patrolPointDsr = patrolPointDsr;
    }

    @Override
    public String toString() {
        return "PatrolRecord{" +
                "id='" + id + '\'' +
                ", patrolOrder=" + patrolOrder +
                ", patrolPoint=" + patrolPoint +
                ", patrolPointDsr='" + patrolPointDsr + '\'' +
                ", isqualified='" + isqualified + '\'' +
                ", patrolRecordDsr='" + patrolRecordDsr + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", duration=" + duration +
                ", remark='" + remark + '\'' +
                '}';
    }
}
