package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/19.
 */

@ApiModel(value = "能源价格条件过滤实体")
public class AssetEnergyPriceVoForFilter extends EAMBaseFilterVo {

    /**
     * id
     *
     */
    @ApiModelProperty(value = "能源价格ID(新增不需要传值)")
    private String id;

    /**
     * 能源价格状态，可选项：生效/禁用
     */
    @ApiModelProperty(value = "能源价格状态(生效/禁用)")
    private String status;

    /**
     * 日常价格
     */
    @ApiModelProperty(value = "日常价格(不能超过200个字节)")
    private double price;

    /**
     * 波峰价格
     */
    @ApiModelProperty(value = "波峰价格(不能超过200个字节)")
    private double crestPrice;

    /**
     * 波谷价格
     */
    @ApiModelProperty(value = "波谷价格(不能超过200个字节)")
    private double troughPrice;

    /**
     * 填报内容的ID
     */
    @ApiModelProperty(value = "能源价格对应的填报内容id(不能超过36个字节)")
    private String fillFormId;

    /**
     * 价格单位，如： 元/吨   元/度
     */
    @ApiModelProperty(value = "价格单位(不能超过10个字节)")
    private String priceUnit;


    /**
     *创建人
     */
    @ApiModelProperty(value = "创建人(不能超过50个字节)")
    private String creator;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期(新增、修改不用传值)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;




    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFillFormId() {
        return fillFormId;
    }

    public void setFillFormId(String fillFormId) {
        this.fillFormId = fillFormId;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCrestPrice() {
        return crestPrice;
    }

    public void setCrestPrice(double crestPrice) {
        this.crestPrice = crestPrice;
    }

    public double getTroughPrice() {
        return troughPrice;
    }

    public void setTroughPrice(double troughPrice) {
        this.troughPrice = troughPrice;
    }


    @Override
    public String toString() {
        return "AssetEnergyPriceVoForFilter{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", crestPrice=" + crestPrice +
                ", troughPrice=" + troughPrice +
                ", fillFormId='" + fillFormId + '\'' +
                ", priceUnit='" + priceUnit + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
