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
 * @Description 二维码应用程序实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_qr_code_application")
public class QRCodeApplication implements Serializable {
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
     * 值
     */
    @Column(name="value",nullable = true,length = 255)
    private String value;

    /**
     * 描述
     */
    @Column(name="description",nullable = true,length = 255)
    private String description;

    /**
     * 功能对应的类方法路径
     */
    @Column(name = "class_name", nullable = true,length = 255)
    private String className;

    /**
     * 查询数据方法名
     */
    @Column(name = "method_name", nullable = true,length = 36)
    private String methodName;

    /**
     * 更新是否已更新字段方法名,状态更新为false
     */
    @Column(name = "update_data_update_method_name", nullable = true,length = 36)
    private String updateDataUpdateMethodName;

    /**
     * 二维码前缀编码
     */
    @Column(name = "prefix_qr_code", nullable = true,length = 36)
    private String prefixQRCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUpdateDataUpdateMethodName() {
        return updateDataUpdateMethodName;
    }

    public void setUpdateDataUpdateMethodName(String updateDataUpdateMethodName) {
        this.updateDataUpdateMethodName = updateDataUpdateMethodName;
    }

    public String getPrefixQRCode() {
        return prefixQRCode;
    }

    public void setPrefixQRCode(String prefixQRCode) {
        this.prefixQRCode = prefixQRCode;
    }

    @Override
    public String toString() {
        return "QRCodeApplication{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", updateDataUpdateMethodName='" + updateDataUpdateMethodName + '\'' +
                ", prefixQRCode='" + prefixQRCode + '\'' +
                '}';
    }
}
