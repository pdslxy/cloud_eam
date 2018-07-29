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
 * @date 2017年8月19日 10:16:47
 * @Description 发放单中物料发放情况列表
 */
@ApiModel(value = "发放单中物料发放情况列表")
public class MaterialInventoryVoForReleaseList extends EAMBaseFilterVo {

    @ApiModelProperty(value = "发放单ID")
    private String id;

    @ApiModelProperty(value = "物资编码")
    private String itemNum;

    @ApiModelProperty(value = "订购单位 ")
    private String orderUnit;

    @ApiModelProperty(value = "订购单位 ", hidden = true)
    private String orderUnitName;

    @ApiModelProperty(value = "物资名称")
    private String itemName;

    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    @ApiModelProperty(value = "库房名称", hidden = true)
    private String storeroomId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Long quantity;


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

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "MaterialInventoryVoForReleaseList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", orderUnit='" + orderUnit + '\'' +
                ", orderUnitName='" + orderUnitName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
