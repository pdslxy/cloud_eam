package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月19日
 * @Description EAM组织dashboard 按工单类型统计组织下工单总数Vo
 */

@ApiModel(value = "按工单类型统计组织下工单总数Vo", description = "按工单类型统计组织下工单总数Vo")
public class DashboardOrderCountVo implements Serializable {

    @ApiModelProperty(value = "工单类型")
    private String orderType;
    
    @ApiModelProperty(value = "工单数量")
    private String orderTotal;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "DashboardOrderCountVo{" +
                "orderType='" + orderType + '\'' +
                ", orderTotal='" + orderTotal + '\'' +
                '}';
    }
}
