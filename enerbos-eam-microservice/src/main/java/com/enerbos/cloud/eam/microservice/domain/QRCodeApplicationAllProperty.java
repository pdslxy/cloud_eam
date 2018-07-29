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
 * @Description 二维码应用程序所有属性实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_qr_code_application_all_property")
public class QRCodeApplicationAllProperty implements Serializable {
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
     * 属性
     */
    @Column(name="property",nullable = true,length = 255)
    private String property;

    /**
     * 属性描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 所属应用程序
     */
    @Column(name = "application_id",nullable = false, length = 36)
    private String applicationId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QRCodeApplication{" +
                "id=" + id +
                ",description='" + description + '\'' +
                ", property='" + property + '\'' +
                ", applicationId='" + applicationId + '\'' +
                '}';
    }
}
