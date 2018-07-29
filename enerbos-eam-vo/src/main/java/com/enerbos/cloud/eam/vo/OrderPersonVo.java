package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-15 19:54
 * @Description 工单关联人员VO
 */
@ApiModel(value = "工单关联人员VO", description = "工单关联人员VO")
public class OrderPersonVo {

    @ApiModelProperty(value = "工单关联人员ID")
    private String id;

    @ApiModelProperty(value = "工单编号")
    private String orderId;

    @ApiModelProperty(value = "人员编号")
    private String personId;

    @ApiModelProperty(value = "字段类型")
    private String fieldType;

    public OrderPersonVo() {}

    public OrderPersonVo(String orderId, String fieldType) {
        this.orderId = orderId;
        this.fieldType = fieldType;
    }

    public OrderPersonVo(String orderId, String fieldType, String personId) {
        this.orderId = orderId;
        this.personId = personId;
        this.fieldType = fieldType;
    }

    public OrderPersonVo(String id, String orderId, String fieldType, String personId) {
        this.id = id;
        this.orderId = orderId;
        this.personId = personId;
        this.fieldType = fieldType;
    }

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
        return "OrderPersonVo{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", personId='" + personId + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }
}
