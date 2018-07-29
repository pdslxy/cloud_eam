package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年10月31日 19:22:39
 * @Description 环境监测收藏
 */
@ApiModel(value = "收藏对应的vo", description = "环境监测点位收藏对应的vo")
public class MeterRfCollectorVo extends EnerbosBaseEntity {

    @ApiModelProperty(value = "设备ID")
    private String assetId;

    @ApiModelProperty(value = "点位ID")
    private String meterId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品id")
    private String product;

    public MeterRfCollectorVo() {
    }

    public MeterRfCollectorVo(String personId, String product, String assetId, String meterId) {
        this.personId = personId;
        this.product = product;
        this.assetId=assetId;
        this.meterId=meterId;
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

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    @Override
    public String toString() {
        return "MeterRfCollectorVo{" +
                "assetId='" + assetId + '\'' +
                ", meterId='" + meterId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
