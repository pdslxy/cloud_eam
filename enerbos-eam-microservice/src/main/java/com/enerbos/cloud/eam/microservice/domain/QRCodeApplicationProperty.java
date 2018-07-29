package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年07月31日
 * @Description 二维码应用程序生成二维码属性实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_qr_code_application_property")
public class QRCodeApplicationProperty implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 序号
     */
    @Column(name="sequence",nullable = true,length = 255)
    private String sequence;

    /**
     * 属性ID
     */
    @Column(name="property_id",nullable = true,length = 255)
    private String propertyId;

    /**
     * 所属应用程序
     */
    @Column(name = "application_id",nullable = true, length = 36)
    private String applicationId;

    /**
     * 所属应用程序
     */
    @Column(name = "qr_code_manager_id",nullable = true, length = 36)
    private String QRCodeManagerId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getQRCodeManagerId() {
        return QRCodeManagerId;
    }

    public void setQRCodeManagerId(String QRCodeManagerId) {
        this.QRCodeManagerId = QRCodeManagerId;
    }

    @Override
    public String toString() {
        return "QRCodeApplicationProperty{" +
                "id='" + id + '\'' +
                ", sequence='" + sequence + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", QRCodeManagerId='" + QRCodeManagerId + '\'' +
                '}';
    }
}
