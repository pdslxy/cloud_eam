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
 * @Description 预防性维护计划-收藏Vo
 */
@ApiModel(value = "预防性维护计划-收藏Vo", description = "预防性维护计划-收藏Vo")
public class MaintenanceMaintenancePlanRfCollectorVo implements Serializable {
    @ApiModelProperty(value = "预防性维护计划id")
    private String maintenancePlanId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品ID")
    private String product;

    public MaintenanceMaintenancePlanRfCollectorVo() {}

    public MaintenanceMaintenancePlanRfCollectorVo(String maintenancePlanId, String personId, String product) {
        this.maintenancePlanId = maintenancePlanId;
        this.personId = personId;
        this.product = product;
    }

    public String getMaintenancePlanId() {
        return maintenancePlanId;
    }

    public void setMaintenancePlanId(String maintenancePlanId) {
        this.maintenancePlanId = maintenancePlanId;
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
        return "MaintenanceMaintenancePlanRfCollectorVo{" +
                "maintenancePlanId='" + maintenancePlanId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
