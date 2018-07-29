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
 * @Description 作业标准-收藏Vo
 */
@ApiModel(value = "作业标准-收藏Vo", description = "作业标准-收藏Vo")
public class MaintenanceJobStandardRfCollectorVo implements Serializable {
    @ApiModelProperty(value = "作业标准id")
    private String jobStandardId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品ID")
    private String product;

    public MaintenanceJobStandardRfCollectorVo() {}

    public MaintenanceJobStandardRfCollectorVo(String jobStandardId, String personId, String product) {
        this.jobStandardId = jobStandardId;
        this.personId = personId;
        this.product = product;
    }

    public String getJobStandardId() {
        return jobStandardId;
    }

    public void setJobStandardId(String jobStandardId) {
        this.jobStandardId = jobStandardId;
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
                "jobStandardId='" + jobStandardId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
