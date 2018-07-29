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
 * @date 2017/7/12
 * @Description
 */
@Entity
@Table(name = "patrol_routepoint")
public class PatrolRoutePoint implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 巡检路线id
     */
    @ManyToOne
    @JoinColumn(
            name = "patrolrouteid"
    )
    @JsonIgnore
    private PatrolRoute patrolRoute;

    /**
     * 巡检点id
     */
    @ManyToOne
    @JoinColumn(
            name = "patrolpointid"
    )
    @JsonIgnore
    private PatrolPoint patrolPoint;

    @Column(name = "createtime", nullable = false, length = 36)
    private Date createtime;

    @Column(name = "updatetime", nullable = true, length = 36)
    private Date updatetime;

    @Column(name = "remark", nullable = true, length = 255)
    private String remark;

    /**
     * 序号
     */
    @Column(name = "step", nullable = true, length = 11)
    private Integer step;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PatrolRoute getPatrolRoute() {
        return patrolRoute;
    }

    public void setPatrolRoute(PatrolRoute patrolRoute) {
        this.patrolRoute = patrolRoute;
    }

    public PatrolPoint getPatrolPoint() {
        return patrolPoint;
    }

    public void setPatrolPoint(PatrolPoint patrolPoint) {
        this.patrolPoint = patrolPoint;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolRoutePoint{" +
                "id='" + id + '\'' +
                ", patrolRoute=" + patrolRoute +
                ", patrolPoint=" + patrolPoint +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", remark='" + remark + '\'' +
                ", step=" + step +
                '}';
    }
}
