package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/10.
 */

public class EnvForFilterVo implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "站点ID")
    private String siteId;

    @ApiModelProperty(value = "来源")
    private String orderSource;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    @Override
    public String toString() {
        return "EnvForFilterVo{" +
                "orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orderSource='" + orderSource + '\'' +
                '}';
    }
}
