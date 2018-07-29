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
 * @date 2017年3月31日 上午11:29:25
 * @Description 物资实体类
 */
@ApiModel(value = "物资台账列表实体")
public class MaterialItemVoForList extends EAMBaseFilterVo {

    /**
     * id
     */
    @ApiModelProperty(value = "唯一标识")
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
    @ApiModelProperty(value = "品牌 ")
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
    private Boolean status;

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
     * 所属站点id
     */
    @ApiModelProperty(value = "所属站点id")
    private String siteId;

    /**
     * 所属组织id
     */
    @ApiModelProperty(value = "所属组织id")
    private String orgId;


    private Boolean collect ;

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(String siteId) {
        this.siteId = siteId;
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
    public String toString() {
        return "MaterialItemVoForList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", status=" + status +
                ", sparePart=" + sparePart +
                ", asset=" + asset +
                ", tools=" + tools +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", collect=" + collect +
                '}';
    }
}
