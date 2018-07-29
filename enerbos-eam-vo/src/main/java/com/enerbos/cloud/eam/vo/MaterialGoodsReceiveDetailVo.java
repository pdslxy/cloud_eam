package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年4月7日 下午1:45:21
 * @Description 物资接收明细
 */
@ApiModel(value = "物资接收明细")
public class MaterialGoodsReceiveDetailVo extends EAMBaseFilterVo {

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = " 唯一标识(新增不用传值)")
    private String id;

    /**
     * 对应的物资接收单
     */
    @ApiModelProperty(value = "物资接收单(最大长度不能超过36个字符)")
    @Length(max = 36, message = "最大长度不能超过36个字符")
    private String goodsReceiveId;

    /**
     * 关联的物料
     */
    @ApiModelProperty(value = "物料(不能为空且最大长度不能超过36个字符)")
    @Length(max = 36, message = "最大长度不能超过36个字符")
    private String itemId;

    /**
     * 订购单位的名称
     */
    @ApiModelProperty(value = "订购单位的名称(不能为空且最大长度不能超过255个字符)")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    private String receiveUnit;

    /**
     * 接收数量
     */
    @ApiModelProperty(value = "接收数量")
    private Long receiveQuantity;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private Double unitCost;

    /**
     * 行价格
     */
    @ApiModelProperty(value = "行价格")
    private Double lineCost;

    /**
     * 平均成本,在接收或转移时计算
     */
    @ApiModelProperty(value = "平均成本,在接收或转移时计算")
    private Double averageCost;

    /**
     * 备注
     */
    @ApiModelProperty(value = " 备注(不能为空且最大长度不能超过255个字符)")
    @Length(max = 255, message = "备注最大长度不能超过255个字符")
    private String mark;

    /**
     * 最后修改人员
     */
    @ApiModelProperty(value = "最后修改人员")
    @Length(max = 255, message = "最后修改人员最大长度不能超过255个字符")
    private String changeUser;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @Length(max = 255, message = "组织id最大长度不能超过255个字符")
    private String orgId;

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点id")
    @Length(max = 255, message = "组站点id最大长度不能超过255个字符")
    private String siteId;

    /**
     * 创建人
     */
    @Column(nullable = false, length = 50)
    private String createUser;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsReceiveId() {
        return goodsReceiveId;
    }

    public void setGoodsReceiveId(String goodsReceiveId) {
        this.goodsReceiveId = goodsReceiveId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getReceiveUnit() {
        return receiveUnit;
    }

    public void setReceiveUnit(String receiveUnit) {
        this.receiveUnit = receiveUnit;
    }

    public Long getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Long receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getLineCost() {
        return lineCost;
    }

    public void setLineCost(Double lineCost) {
        this.lineCost = lineCost;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser;
    }

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

    @Override
    public String toString() {
        return "MaterialGoodsReceiveDetailVo{" +
                "id='" + id + '\'' +
                ", goodsReceiveId='" + goodsReceiveId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", receiveUnit='" + receiveUnit + '\'' +
                ", receiveQuantity=" + receiveQuantity +
                ", unitCost=" + unitCost +
                ", lineCost=" + lineCost +
                ", averageCost=" + averageCost +
                ", mark='" + mark + '\'' +
                ", changeUser='" + changeUser + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

}
