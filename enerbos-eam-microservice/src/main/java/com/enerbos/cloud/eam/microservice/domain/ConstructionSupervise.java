package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月9日
 * @Description 施工单-监管说明实体
 */
@Entity
@Table(name = "construction_supervise")
public class ConstructionSupervise implements Serializable {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 监管时间
     */
    @Column(name="supervise_date",nullable = true,length = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date superviseDate;

    /**
     * 监管情况说明
     */
    @Column(name="supervise_desc",nullable = true,length = 255)
    private String superviseDesc;

    /**
     * 施工单标识，与监管情况说明为一对多的关系
     */
    @Column(name="construction_id",nullable = true,length = 36)
    private String constructionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSuperviseDate() {
        return superviseDate;
    }

    public void setSuperviseDate(Date superviseDate) {
        this.superviseDate = superviseDate;
    }

    public String getSuperviseDesc() {
        return superviseDesc;
    }

    public void setSuperviseDesc(String superviseDesc) {
        this.superviseDesc = superviseDesc;
    }

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
    }

    @Override
    public String toString() {
        return "ConstructionSupervise{" +
                "id='" + id + '\'' +
                ", superviseDate=" + superviseDate +
                ", superviseDesc='" + superviseDesc + '\'' +
                ", constructionId='" + constructionId + '\'' +
                '}';
    }
}
