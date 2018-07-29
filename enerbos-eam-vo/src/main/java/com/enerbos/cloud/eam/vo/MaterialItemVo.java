package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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
 * @date 2017年3月31日 上午11:29:25
 * @Description 物资台账实体类
 */
@ApiModel(value = "物资台账")
public class MaterialItemVo extends EAMBaseFilterVo {

    /**
     * id
     */
    @ApiModelProperty(value = "item唯一标识(新增不用传值)")
    private String id;

    /**
     * 物资编码
     */
    @ApiModelProperty(value = "物资编号(最大长度不能超过255个字符)")
    private String itemNum;

    /**
     * 物资描述
     */
    @ApiModelProperty(value = "物资描述(不能为空且最大长度不能超过255个字符)")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    @NotEmpty(message = "物资描述不能为空")
    private String description;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌(最大长度不能超过255个字符)")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    private String brand;
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    @Length(max = 255, message = "最大长度不能超过255个字符")
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
     * 订购单位（个）
     */
    @ApiModelProperty(value = "订购单位（个）")
    private String orderUnit;

    @ApiModelProperty(value = "订购单位（个）", hidden = true)
    private String orderUnitName;

    /**
     * 发放单位（个）
     */
    @ApiModelProperty(value = "发放单位（个）")
    private String issueUnit;


    @ApiModelProperty(value = "发放单位（个）", hidden = true)
    private String issueUnitName;


    /**
     * 对比系数 (非必填)
     */
    @ApiModelProperty(value = "对比系数 (非必填)")
    private String conversion;
    /**
     * 发放最大数量 (非必填)
     */
    @ApiModelProperty(value = "发放最大数量 (非必填)")
    private String maxIssue;

    /**
     * 所属站点id
     */
    @ApiModelProperty(value = "所属站点id")
    private String siteId;

    @ApiModelProperty(value = "所属站点名称", hidden = true)
    private String siteName;

    /**
     * 所属组织id
     */
    @ApiModelProperty(value = "所属组织id")
    private String orgId;

    @ApiModelProperty(value = "所属组织名称", hidden = true)
    private String orgName;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(value = "二维码编码")
    private String qrCodeNum;

    @ApiModelProperty(value = "是否有数据更新")
    private String isupdatedata;

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

    public MaterialItemVo() {
    }

    public String getQrCodeNum() {
        return qrCodeNum;
    }

    public void setQrCodeNum(String qrCodeNum) {
        this.qrCodeNum = qrCodeNum;
    }

    public String getIsupdatedata() {
        return isupdatedata;
    }

    public void setIsupdatedata(String isupdatedata) {
        this.isupdatedata = isupdatedata;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    public String getMaxIssue() {
        return maxIssue;
    }

    public void setMaxIssue(String maxIssue) {
        this.maxIssue = maxIssue;
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


    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public String getIssueUnitName() {
        return issueUnitName;
    }

    public void setIssueUnitName(String issueUnitName) {
        this.issueUnitName = issueUnitName;
    }

    @Override
    public String toString() {
        return "MaterialItemVo{" +
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
                ", orderUnitName='" + orderUnitName + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", issueUnitName='" + issueUnitName + '\'' +
                ", conversion='" + conversion + '\'' +
                ", maxIssue='" + maxIssue + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
