package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年4月7日 下午1:45:21
 * @Description 物资接收明细
 */
@ApiModel(value = "物资接收明细")
public class MaterialGoodsReceiveDetailVoForList implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5668062100070290811L;

    private String id;
    /**
     * 物资编码
     */
    @ApiModelProperty(value = "物资编码")
    private String itemNum;


    @ApiModelProperty(value = "物资ID", hidden = true)
    private String itemId;


    @ApiModelProperty(value = "物资描述")
    private String description;

    /**
     * 接收数量
     */
    @ApiModelProperty(value = "接收数量")
    private Long receiveQuantity;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private Double unitCost;

    /**
     * 行价格
     */
    @ApiModelProperty(value = "行价格")
    private Double lineCost;

    @ApiModelProperty(value = "备注")
    private Date mark;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Long receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getLineCost() {
        return lineCost;
    }

    public void setLineCost(Double lineCost) {
        this.lineCost = lineCost;
    }

    public Date getMark() {
        return mark;
    }

    public void setMark(Date mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "MaterialGoodsReceiveDetailVoForList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", description='" + description + '\'' +
                ", receiveQuantity=" + receiveQuantity +
                ", unitCost=" + unitCost +
                ", lineCost=" + lineCost +
                ", mark=" + mark +
                '}';
    }
}
