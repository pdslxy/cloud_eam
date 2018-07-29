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
 * @date 2017年9月9日
 * @Description 施工单-收藏Vo
 */
@ApiModel(value = "施工单-收藏Vo", description = "施工单-收藏Vo")
public class ConstructionRfCollectorVo implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String constructionId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品ID")
    private String product;

    public ConstructionRfCollectorVo() {}

    public ConstructionRfCollectorVo(String constructionId, String personId, String product) {
        this.constructionId = constructionId;
        this.personId = personId;
        this.product = product;
    }

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
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
        return "ConstructionRfCollectorVo{" +
                "constructionId='" + constructionId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
