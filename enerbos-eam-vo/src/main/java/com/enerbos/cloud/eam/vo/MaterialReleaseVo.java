package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年4月1日 上午10:49:05
 * @Description 物资发放
 */

@ApiModel(value = "物资发放")
public class MaterialReleaseVo implements Serializable {


    /**
     * id
     */
    @ApiModelProperty(value = "物资发放ID")
    private String id;

    /**
     * 物资发放编号
     */
    @ApiModelProperty(value = "物资发放编号(不能为空且不能超过255个字符)")
    @Length(max = 255, message = "物资发放编号不能超过255个字符")
    @NotEmpty(message = "物资方法编号不能为空")
    private String releaseNum;

    /**
     * 物资发放描述
     */
    @ApiModelProperty(value = "物资发放描述(不能超过255个字符)")
    @Length(max = 255, message = "物资发放描述不能超过255个字符")
    private String description;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型(不能超过36个字符)")
    @Length(max = 36, message = "类型不能超过36个字符")
    private String releaseType;// EAM_ALNDOMAIN

    /**
     * 原库房
     */
    @ApiModelProperty(value = "原库房(不能超过255个字符)")
    @Length(max = 255, message = "原库房不能超过255个字符")
    private String fromStoreroomId; // EAM_STOREROOM

    @ApiModelProperty(value = "原库房名称(不能超过255个字符)")
    @Length(max = 255, message = "原库房名称不能超过255个字符")
    private String fromStoreroomName;

    @ApiModelProperty(value = "原库房编码(不能超过255个字符)")
    @Length(max = 255, message = "原库房编码不能超过255个字符")
    private String fromStoreroomNum;

    /**
     * 目标库房
     */
    @ApiModelProperty(value = "目标库房(不能超过255个字符)")
    @Length(max = 255, message = "目标库房不能超过255个字符")
    private String targetStoreroomId; // EAM_STOREROOM


    @ApiModelProperty(value = "目标库房名称(不能超过255个字符)")
    @Length(max = 255, message = "原库房名称不能超过255个字符")
    private String targetStoreroomName;

    @ApiModelProperty(value = "目标库房编码(不能超过255个字符)")
    @Length(max = 255, message = "原库房编码不能超过255个字符")
    private String targetStoreroomNum;

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点id(不能超过36个字符)")
    @Length(max = 36, message = "站点id不能超过36个字符")
    private String siteId;


    @ApiModelProperty(value = "站点名称")
    @Length(max = 36, message = "站点名称不能超过36个字符")
    private String siteName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id(不能超过36个字符)")
    @Length(max = 36, message = "组织id不能超过36个字符")
    private String orgId;

    /**
     * 工单
     */
    @ApiModelProperty(value = " 工单(不能超过36个字符)")
    @Length(max = 36, message = " 工单不能超过36个字符")
    private String orderNum;

    /**
     * 工单的类型
     */
    @ApiModelProperty(value = "  工单的类型(不能超过255个字符)")
    @Length(max = 255, message = "  工单的类型不能超过255个字符")
    private String orderType;

    /**
     * 工单描述
     */
    @ApiModelProperty(value = "工单描述")
    private String orderDiscription;

    /**
     * 领用人
     */
    @ApiModelProperty(value = "领用人(不能超过255个字符)")
    @Length(max = 255, message = "领用人不能超过255个字符")
    private String consumingPeople;

    /**
     * 领用日期
     */
    @ApiModelProperty(value = "领用日期(不能超过255个字符)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shipmentDate;

    /**
     * 成本总计
     */
    @ApiModelProperty(value = "成本总计")
    private Double costTotal;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态(不能超过36个字符)")
    @Length(max = 36, message = "状态不能超过36个字符")
    private String status;


    @ApiModelProperty(value = "状态(不能超过36个字符)",hidden =  true)
    private String statusName;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


    private List<MaterialReleaseDetailVo> materialReleaseDetailVos;

    public List<MaterialReleaseDetailVo> getMaterialReleaseDetailVos() {
        return materialReleaseDetailVos;
    }

    public void setMaterialReleaseDetailVos(List<MaterialReleaseDetailVo> materialReleaseDetailVos) {
        this.materialReleaseDetailVos = materialReleaseDetailVos;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getFromStoreroomId() {
        return fromStoreroomId;
    }

    public void setFromStoreroomId(String fromStoreroomId) {
        this.fromStoreroomId = fromStoreroomId;
    }

    public String getFromStoreroomName() {
        return fromStoreroomName;
    }

    public void setFromStoreroomName(String fromStoreroomName) {
        this.fromStoreroomName = fromStoreroomName;
    }

    public String getFromStoreroomNum() {
        return fromStoreroomNum;
    }

    public void setFromStoreroomNum(String fromStoreroomNum) {
        this.fromStoreroomNum = fromStoreroomNum;
    }

    public String getTargetStoreroomId() {
        return targetStoreroomId;
    }

    public void setTargetStoreroomId(String targetStoreroomId) {
        this.targetStoreroomId = targetStoreroomId;
    }

    public String getTargetStoreroomName() {
        return targetStoreroomName;
    }

    public void setTargetStoreroomName(String targetStoreroomName) {
        this.targetStoreroomName = targetStoreroomName;
    }

    public String getTargetStoreroomNum() {
        return targetStoreroomNum;
    }

    public void setTargetStoreroomNum(String targetStoreroomNum) {
        this.targetStoreroomNum = targetStoreroomNum;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDiscription() {
        return orderDiscription;
    }

    public void setOrderDiscription(String orderDiscription) {
        this.orderDiscription = orderDiscription;
    }

    public String getConsumingPeople() {
        return consumingPeople;
    }

    public void setConsumingPeople(String consumingPeople) {
        this.consumingPeople = consumingPeople;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Double costTotal) {
        this.costTotal = costTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "MaterialReleaseVo{" +
                "id='" + id + '\'' +
                ", releaseNum='" + releaseNum + '\'' +
                ", description='" + description + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", fromStoreroomId='" + fromStoreroomId + '\'' +
                ", fromStoreroomName='" + fromStoreroomName + '\'' +
                ", fromStoreroomNum='" + fromStoreroomNum + '\'' +
                ", targetStoreroomId='" + targetStoreroomId + '\'' +
                ", targetStoreroomName='" + targetStoreroomName + '\'' +
                ", targetStoreroomNum='" + targetStoreroomNum + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderDiscription='" + orderDiscription + '\'' +
                ", consumingPeople='" + consumingPeople + '\'' +
                ", shipmentDate=" + shipmentDate +
                ", costTotal=" + costTotal +
                ", status='" + status + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
