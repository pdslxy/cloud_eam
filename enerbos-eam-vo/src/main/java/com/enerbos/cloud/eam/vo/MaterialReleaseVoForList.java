package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

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
 * @date 2017年4月1日 上午10:49:05
 * @Description 物资发放列表 实体
 */

@ApiModel(value = "物资发放列表实体")
public class MaterialReleaseVoForList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3802610151488991561L;

    /**
     * id
     */
    @ApiModelProperty(value = "物资发放ID")
    private String id;

    /**
     * 物资发放编号
     */
    @ApiModelProperty(value = "物资发放编号")
    private String releaseNum;

    /**
     * 物资发放描述
     */
    @ApiModelProperty(value = "物资发放描述")
    private String description;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String releaseType;// EAM_ALNDOMAIN

    /**
     * 库房
     */
    @ApiModelProperty(value = "库房(不能超过255个字符)")
    @Length(max = 255, message = "库房不能超过255个字符")
    private String storeroomName; // EAM_STOREROOM

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点id")
    private String siteId;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private String orgId;

    /**
     * 工单
     */
    @ApiModelProperty(value = " 工单")
    private String orderNum;

    /**
     * 工单的类型
     */
    @ApiModelProperty(value = "  工单的类型")
    private String orderType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态(不能超过36个字符)")
    @Length(max = 36, message = "状态不能超过36个字符")
    private String status;

    private Boolean collect;

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

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }


    @Override
    public String toString() {
        return "MaterialReleaseVoForList{" +
                "id='" + id + '\'' +
                ", releaseNum='" + releaseNum + '\'' +
                ", description='" + description + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderType='" + orderType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
