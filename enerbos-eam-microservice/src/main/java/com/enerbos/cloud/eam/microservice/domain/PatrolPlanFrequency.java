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
@Table(name = "patrol_planfrequency")
public class PatrolPlanFrequency implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;
    /**
     * 巡检项
     */
    @Column(name = "frequency", nullable = false, length = 255)
    private String frequency;
    /**
     * 巡检项
     */
    @Column(name = "unit", nullable = false, length = 255)
    private String unit;

    /**
     * 巡检项
     */
    @Column(name = "remark", nullable = true, length = 255)
    private String remark;

    @ManyToOne
    @JoinColumn(
            name = "patrolplanid"
    )
    @JsonIgnore
    private PatrolPlan patrolPlan;

    /**
     * 状态日期
     */
    @Column(name = "nextdate", nullable = false, length = 36)
    private Date nextdate;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PatrolPlan getPatrolPlan() {
        return patrolPlan;
    }

    public void setPatrolPlan(PatrolPlan patrolPlan) {
        this.patrolPlan = patrolPlan;
    }

    public Date getNextdate() {
        return nextdate;
    }

    public void setNextdate(Date nextdate) {
        this.nextdate = nextdate;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolPlanFrequency{" +
                "id='" + id + '\'' +
                ", frequency='" + frequency + '\'' +
                ", unit='" + unit + '\'' +
                ", remark='" + remark + '\'' +
                ", patrolPlan=" + patrolPlan +
                ", nextdate=" + nextdate +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", step=" + step +
                '}';
    }
}
