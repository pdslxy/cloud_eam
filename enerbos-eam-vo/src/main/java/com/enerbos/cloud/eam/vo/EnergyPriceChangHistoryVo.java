package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/19.
 */

@ApiModel(value = "能源价格变更历史", description = "能源价格变更历史")
public class EnergyPriceChangHistoryVo extends EAMBaseFilterVo {


    @ApiModelProperty(value = "能源价格ID")
    private String id;

    //变更之前的价格
    @ApiModelProperty(value = "变更之前的价格")
    public double changPriceBefore;

    //变更后价格
    @ApiModelProperty(value = "变更之后的价格")
    public  double changPriceAfter;

    //能源价格ID
    @ApiModelProperty(value = "能源价格ID")
    public String  eneryPriceId;

    @ApiModelProperty(value = "创建用户")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "组织ID")
    public String orgId;

    @ApiModelProperty(value = "站点Id")
    public String siteId;

    /**
     * 价格单位，如： 元/吨   元/度
     */
    @ApiModelProperty(value = "价格单位(不能超过10个字节)")
    private String priceUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public double getChangPriceBefore() {
        return changPriceBefore;
    }

    public void setChangPriceBefore(double changPriceBefore) {
        this.changPriceBefore = changPriceBefore;
    }

    public double getChangPriceAfter() {
        return changPriceAfter;
    }

    public void setChangPriceAfter(double changPriceAfter) {
        this.changPriceAfter = changPriceAfter;
    }

    public String getEneryPriceId() {
        return eneryPriceId;
    }

    public void setEneryPriceId(String eneryPriceId) {
        this.eneryPriceId = eneryPriceId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }
}
