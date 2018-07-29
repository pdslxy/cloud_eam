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
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点明细实体类
 */
@ApiModel(value = "物资盘点明细实体类")
public class MaterialCheckDetailVoForList extends EAMBaseFilterVo {

    @ApiModelProperty(value = "物资盘点明细id")
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
    private String itemName;
    /**
     * 盘点单描述
     */
    @ApiModelProperty(value = "盘点单描述")
    private String description;

    @ApiModelProperty(value = "库存id", hidden = true)
    private String inventoryId;
    /**
     * 当前余量
     */
    @ApiModelProperty(value = "当前余量")
    private String currentAllowance;

    /**
     * 实际余量
     */
    @ApiModelProperty(value = "实际余量")
    private String physicalInventory;

    /**
     * 是否盈亏
     */
    @ApiModelProperty(value = "盘点单编号")
    private boolean profit;


    /**
     * 余量是否调整
     */
    @ApiModelProperty(value = "余量是否调整")
    private boolean adjust;


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


    public String getPhysicalInventory() {
        return physicalInventory;
    }

    public void setPhysicalInventory(String physicalInventory) {
        this.physicalInventory = physicalInventory;
    }

    public boolean isProfit() {
        return profit;
    }

    public void setProfit(boolean profit) {
        this.profit = profit;
    }

    public boolean isAdjust() {
        return adjust;
    }

    public void setAdjust(boolean adjust) {
        this.adjust = adjust;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCurrentAllowance() {
        return currentAllowance;
    }

    public void setCurrentAllowance(String currentAllowance) {
        this.currentAllowance = currentAllowance;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public String toString() {
        return "MaterialCheckDetailVoForList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", inventoryId='" + inventoryId + '\'' +
                ", currentAllowance='" + currentAllowance + '\'' +
                ", physicalInventory='" + physicalInventory + '\'' +
                ", profit=" + profit +
                ", adjust=" + adjust +
                '}';
    }
}
