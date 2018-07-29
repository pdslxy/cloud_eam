package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-15 18:47
 * @Description 工单关联人员
 */
@Entity
@Table(name = "eam_order_person")
public class OrderPerson implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 工单编号
     */
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;

    /**
     * 人员编号
     */
    @Column(name = "person_id", nullable = false, length = 36)
    private String personId;

    /**
     * 字段类型
     */
    @Column(name = "field_type", nullable = false, length = 255)
    private String fieldType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return "OrderPerson{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", personId='" + personId + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }
}
