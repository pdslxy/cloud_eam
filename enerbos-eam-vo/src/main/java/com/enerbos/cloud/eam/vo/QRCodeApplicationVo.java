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
 * @Description 二维码应用程序Vo
 */
@ApiModel(value = "二维码应用程序", description = "二维码应用程序的实体对应的VO")
public class QRCodeApplicationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QRCodeApplicationVo{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
