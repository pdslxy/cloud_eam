package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年4月1日 上午10:54:56
 * @Description 物资发放明细
 */
@ApiModel(value = "物资发放明细列表实体")
public class MaterialReleaseDetailVoForList {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不传值)")
    private String id;

    /**
     * 物资编码
     */
    @ApiModelProperty(value = "物资编码")
    private String itemNum;

    /**
     * 物资描述
     */
    @ApiModelProperty(value = "物资描述")
    private String itemDescription;

    /**
     * 行成本
     */
    @ApiModelProperty(value = "行成本")
    private Double lineCost;


    @ApiModelProperty(value = "行成本", hidden = true)
    private String inventoryId;

    /**
     * 目标库房
     */
    @ApiModelProperty(value = "目标库房")
    private String storeroomName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Long quantity;

    /**
     * 物资发放描述
     */
    @ApiModelProperty(value = "物资发放描述")
    private String releaseDescription;


    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getLineCost() {
        return lineCost;
    }

    public void setLineCost(Double lineCost) {
        this.lineCost = lineCost;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getReleaseDescription() {
        return releaseDescription;
    }

    public void setReleaseDescription(String releaseDescription) {
        this.releaseDescription = releaseDescription;
    }

    @Override
    public String toString() {
        return "MaterialReleaseDetailVoForList [id=" + id + ", itemNum="
                + itemNum + ", itemDescription=" + itemDescription
                + ", lineCost=" + lineCost + ", storeroomName=" + storeroomName
                + ", quantity=" + quantity + ", releaseDescription="
                + releaseDescription + "]";
    }

}
