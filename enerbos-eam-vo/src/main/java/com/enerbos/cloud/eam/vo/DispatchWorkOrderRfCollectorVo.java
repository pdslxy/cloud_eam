package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-08-21 16:52
 * @Description 派工工单-收藏Vo
 */
@ApiModel(value = "派工工单-收藏Vo", description = "派工工单-收藏Vo")
public class DispatchWorkOrderRfCollectorVo {
    @ApiModelProperty(value = "工单id")
    private String workOrderId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品ID")
    private String product;

    @ApiModelProperty(value = "收藏日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    public DispatchWorkOrderRfCollectorVo() {}

    public DispatchWorkOrderRfCollectorVo(String workOrderId, String personId, String product) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "DispatchWorkOrderRfCollectorVo{" +
                "workOrderId='" + workOrderId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
