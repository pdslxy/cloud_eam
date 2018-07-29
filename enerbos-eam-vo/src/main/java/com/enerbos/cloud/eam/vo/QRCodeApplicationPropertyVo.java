package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

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
@ApiModel(value = "二维码应用程序生成二维码属性", description = "二维码应用程序生成二维码属性对应的VO")
public class QRCodeApplicationPropertyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private String sequence;

    /**
     * 属性ID
     */
    @ApiModelProperty(value = "属性ID")
    private String propertyId;

    /**
     * 属性
     */
    @ApiModelProperty(value = "属性")
    private String property;

    /**
     * 属性描述
     */
    @ApiModelProperty(value = "属性描述")
    private String propertyName;

    /**
     * 所属应用程序
     */
    @ApiModelProperty(value = "所属应用程序id")
    private String applicationId;

    /**
     * 二维码管理id
     */
    @ApiModelProperty(value = "二维码管理id")
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "QRCodeApplicationPropertyVo{" +
                "id='" + id + '\'' +
                ", sequence='" + sequence + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", property='" + property + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", QRCodeManagerId='" + QRCodeManagerId + '\'' +
                '}';
    }
}
