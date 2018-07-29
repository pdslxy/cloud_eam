package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月25日
 * @Description EAM站点dashboard 我的待办/经办任务数量
 */

@ApiModel(value = "我的待办/经办任务数量Vo", description = "我的待办/经办任务数量Vo")
public class DashboardSiteTaskCountVo implements Serializable {

    @ApiModelProperty(value = "工单类型")
    private String orderType;
    
    @ApiModelProperty(value = "待办任务数量")
    private String toDoTaskTotal;

    @ApiModelProperty(value = "经办任务数量")
    private String doneTaskTotal;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getToDoTaskTotal() {
        return toDoTaskTotal;
    }

    public void setToDoTaskTotal(String toDoTaskTotal) {
        this.toDoTaskTotal = toDoTaskTotal;
    }

    public String getDoneTaskTotal() {
        return doneTaskTotal;
    }

    public void setDoneTaskTotal(String doneTaskTotal) {
        this.doneTaskTotal = doneTaskTotal;
    }

    @Override
    public String toString() {
        return "DashboardSiteTaskCountVo{" +
                "orderType='" + orderType + '\'' +
                ", toDoTaskTotal='" + toDoTaskTotal + '\'' +
                ", doneTaskTotal='" + doneTaskTotal + '\'' +
                '}';
    }
}
