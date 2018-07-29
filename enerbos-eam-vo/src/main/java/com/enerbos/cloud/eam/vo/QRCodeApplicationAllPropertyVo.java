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
 * @Description 二维码应用程序所有属性实体
 */
@ApiModel(value = "二维码应用程序所有属性", description = "二维码应用程序所有属性对应的VO")
public class QRCodeApplicationAllPropertyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 属性
     */
    @ApiModelProperty(value = "属性,必须与字段对应")
    private String property;

    /**
     * 属性描述
     */
    @ApiModelProperty(value = "属性描述")
    private String description;

    /**
     * 所属应用程序
     */
    @ApiModelProperty(value = "所属应用程序")
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
        return "QRCodeApplicationAllPropertyVo{" +
                "id='" + id + '\'' +
                ", property='" + property + '\'' +
                ", description='" + description + '\'' +
                ", applicationId='" + applicationId + '\'' +
                '}';
    }
}
