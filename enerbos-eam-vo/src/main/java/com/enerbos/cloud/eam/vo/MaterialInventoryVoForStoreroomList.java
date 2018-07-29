package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年3月31日 下午5:11:28
 * @Description 物资库存实体类
 */
@ApiModel(value = "物资库存列表实体")
public class MaterialInventoryVoForStoreroomList extends EAMBaseFilterVo {

    @ApiModelProperty(value = "物资库存ID")
    private String id;

    @ApiModelProperty(value = "物资编码")
    private String itemNum;

    @ApiModelProperty(value = "物资描述")
    private String description;

    @ApiModelProperty(value = "物资库存当前余量")
    private String currentBalance;

    /**
     * 标准成本
     */
    @ApiModelProperty(value = "标准成本")
    private Double standardCost;

    /**
     * 平均成本
     */
    @ApiModelProperty(value = "平均成本")
    private Double averageCost;
    /**
     * 上次接收成本
     */
    @ApiModelProperty(value = "上次接收成本")
    private Double lastReceiveCost;

    @ApiModelProperty(value = "站点名称")
    private String siteName ;


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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


    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(Double standardCost) {
        this.standardCost = standardCost;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public Double getLastReceiveCost() {
        return lastReceiveCost;
    }

    public void setLastReceiveCost(Double lastReceiveCost) {
        this.lastReceiveCost = lastReceiveCost;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MaterialInventoryVoForStoreroomList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", description='" + description + '\'' +
                ", currentBalance='" + currentBalance + '\'' +
                ", standardCost=" + standardCost +
                ", averageCost=" + averageCost +
                ", lastReceiveCost=" + lastReceiveCost +
                ", siteName='" + siteName + '\'' +
                '}';
    }
}
