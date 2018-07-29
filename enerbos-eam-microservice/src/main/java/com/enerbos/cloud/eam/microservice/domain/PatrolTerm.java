package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

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
@Table(name = "patrol_term")
public class PatrolTerm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 巡检项
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * 巡检项
     */
    @Column(name = "remark", nullable = true, length = 255)
    private String remark;

    /**
     * 序号
     */
    @Column(name = "step", nullable = true, length = 11)
    private Integer step;

    @ManyToOne
    @JoinColumn(
            name = "patrolpointid"
    )
    @JsonIgnore
    private PatrolPoint patrolPoint;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PatrolPoint getPatrolPoint() {
        return patrolPoint;
    }

    public void setPatrolPoint(PatrolPoint patrolPoint) {
        this.patrolPoint = patrolPoint;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolTerm{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", step=" + step +
                ", patrolPoint=" + patrolPoint +
                '}';
    }
}
