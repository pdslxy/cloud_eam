package com.enerbos.cloud.eam.vo;

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
 * @date 2017年7月5日 下午2:15:10
 * @Description
 */
@ApiModel(value = "物资过滤条件")
public class MaterialItemVoForFilter extends EAMBaseFilterVo implements
        Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2896327009789189212L;

    /**
     * id
     */
    @ApiModelProperty(value = "item唯一标识")
    private String id;

    /**
     * 物资编码
     */
    @ApiModelProperty(value = "物资编号")
    private String itemNum;

    /**
     * 物资描述
     */
    @ApiModelProperty(value = "物资描述")
    private String description;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brand;
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;
    /**
     * 状态 PENDING-暂挂 ACTIVE-活动
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 是否备件
     */
    @ApiModelProperty(value = "是否备件")
    private Boolean sparePart;
    /**
     * 是否资产
     */
    @ApiModelProperty(value = "是否资产")
    private Boolean asset;
    /**
     * 是否工具
     */
    @ApiModelProperty(value = "是否工具")
    private Boolean tools;

    /**
     * 订购单位（个）
     */
    @ApiModelProperty(value = "订购单位（个）")
    private String orderUnit;

    /**
     * 发放单位（个）
     */
    @ApiModelProperty(value = "发放单位（个）")
    private String issueUnit;


    @ApiModelProperty(value = "开始时间")
    private String starttime;

    @ApiModelProperty(value = "结束时间")
    private String endtime;

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

    @ApiModelProperty(value="收藏")
    private Boolean collect ;

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSparePart() {
        return sparePart;
    }

    public void setSparePart(Boolean sparePart) {
        this.sparePart = sparePart;
    }

    public Boolean getAsset() {
        return asset;
    }

    public void setAsset(Boolean asset) {
        this.asset = asset;
    }

    public Boolean getTools() {
        return tools;
    }

    public void setTools(Boolean tools) {
        this.tools = tools;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getIssueUnit() {
        return issueUnit;
    }

    public void setIssueUnit(String issueUnit) {
        this.issueUnit = issueUnit;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "MaterialItemVoForFilter{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", sparePart=" + sparePart +
                ", asset=" + asset +
                ", tools=" + tools +
                ", orderUnit='" + orderUnit + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
