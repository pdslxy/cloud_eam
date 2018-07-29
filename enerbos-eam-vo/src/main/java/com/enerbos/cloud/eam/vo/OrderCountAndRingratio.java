package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/16.
 */
@ApiModel(value = "今日工单数   当天创建的工单数量 完成/未完成 历史今日工单数 环比对应的vo", description = "今日工单数   当天创建的工单数量 完成/未完成 历史今日工单数 环比对应的vo")
public class OrderCountAndRingratio implements Serializable{
    @ApiModelProperty(value = "总量")
    private String total="0";
    
    @ApiModelProperty(value = "未完成数量")
    private String wwc="0";

    @ApiModelProperty(value = "已完成数量")
    private String ywc="0";

    @ApiModelProperty(value = "历史工单数量集合")
    private List  list;


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWwc() {
        return wwc;
    }

    public void setWwc(String wwc) {
        this.wwc = wwc;
    }

    public String getYwc() {
        return ywc;
    }

    public void setYwc(String ywc) {
        this.ywc = ywc;
    }


    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "OrderCountAndRingratio{" +
                "total='" + total + '\'' +
                ", wwc='" + wwc + '\'' +
                ", ywc='" + ywc + '\'' +
                ", list=" + list +
                '}';
    }
}
