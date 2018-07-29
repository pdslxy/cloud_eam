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
 * @date 2017年9月5日
 * @Description 缺陷单-收藏Vo
 */
@ApiModel(value = "缺陷单-收藏Vo", description = "缺陷单-收藏Vo")
public class DefectDocumentRfCollectorVo implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String defectDocumentId;

    @ApiModelProperty(value = "人员id")
    private String personId;

    @ApiModelProperty(value = "产品ID")
    private String product;

    public DefectDocumentRfCollectorVo() {}

    public DefectDocumentRfCollectorVo(String defectDocumentId, String personId, String product) {
        this.defectDocumentId = defectDocumentId;
        this.personId = personId;
        this.product = product;
    }

    public String getDefectDocumentId() {
        return defectDocumentId;
    }

    public void setDefectDocumentId(String defectDocumentId) {
        this.defectDocumentId = defectDocumentId;
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
        return "DefectDocumentRfCollectorVo{" +
                "defectDocumentId='" + defectDocumentId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
