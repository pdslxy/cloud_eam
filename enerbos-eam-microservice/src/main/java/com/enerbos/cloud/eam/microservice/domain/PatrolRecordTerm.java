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
 * @date 2017/8/8
 * @Description
 */
@Entity
@Table(name = "patrol_recordterm")
public class PatrolRecordTerm implements Serializable {

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

    @Column(name = "patroltermdsr", nullable = true, length = 255)
    private String patrolTermDsr;

    /**
     * 是否合格
     */
    @Column(name = "isqualified", nullable = true, length = 1)
    private String isqualified;

    /**
     * 异常描述
     */
    @Column(name = "exceptiondsr", nullable = true, length = 255)
    private String exceptionDsr;

    @Column(name = "remark", nullable = true, length = 255)
    private String remark;

    @Column(name = "createtime", nullable = false, length = 255)
    private Date createTime;

    @Column(name = "updatetime", nullable = false, length = 255)
    private Date updateTime;


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

    public String getPatrolTermDsr() {
        return patrolTermDsr;
    }

    public void setPatrolTermDsr(String patrolTermDsr) {
        this.patrolTermDsr = patrolTermDsr;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
    }

    public String getExceptionDsr() {
        return exceptionDsr;
    }

    public void setExceptionDsr(String exceptionDsr) {
        this.exceptionDsr = exceptionDsr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PatrolRecordTerm{" +
                "id='" + id + '\'' +
                ", patrolOrder=" + patrolOrder +
                ", patrolPoint=" + patrolPoint +
                ", patrolTermDsr='" + patrolTermDsr + '\'' +
                ", isqualified=" + isqualified +
                ", exceptionDsr='" + exceptionDsr + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
