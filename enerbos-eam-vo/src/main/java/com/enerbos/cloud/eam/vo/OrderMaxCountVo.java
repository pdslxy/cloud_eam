package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年9月21日 10:23:46
 */

@ApiModel(value = "本月各工单最大数量统计Vo", description = "本月各工单最大数量统计Vo")
public class OrderMaxCountVo {

    @ApiModelProperty(value = "各工单最大量")
    private String maxTotal;


    @ApiModelProperty(value = "站点")
    private String siteId;

    @ApiModelProperty(value = "站点名称")
    private String siteName;

    @ApiModelProperty(value = "查询内名称")
    private String orderName;


    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(String maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public String toString() {
        return "OrderMaxCountVo{" +
                "maxTotal='" + maxTotal + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", orderName='" + orderName + '\'' +
                '}';
    }
}
