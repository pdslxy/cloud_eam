package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年8月21日
 * @Description 维保工单-收藏Vo
 */
@ApiModel(value = "维保工单-收藏Vo", description = "维保工单-收藏Vo")
public class MaintenanceWorkOrderRfCollectorVo implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String workOrderId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品ID")
    private String product;

    public MaintenanceWorkOrderRfCollectorVo() {}

    public MaintenanceWorkOrderRfCollectorVo(String workOrderId, String personId, String product) {
        this.workOrderId = workOrderId;
        this.personId = personId;
        this.product = product;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "MaintenanceWorkOrderRfCollectorVo{" +
                "workOrderId='" + workOrderId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
