package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
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
 * @date 2017年3月31日 下午5:11:28
 * @Description 物资库存实体类
 */
@ApiModel(value = "物资库存列表实体")
public class MaterialInventoryVoForList extends EnerbosBaseFilterVo {

    @ApiModelProperty(value = "物资库存ID")
    private String id;

    @ApiModelProperty(value = "物资编码")
    private String itemNum;

    @ApiModelProperty(value = "物资编码", hidden = true)
    private String itemDescription;

    @ApiModelProperty(value = "物资名称")
    private String itemName;

    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    @ApiModelProperty(value = "库房名称", hidden = true)
    private String storeroomId;


    @ApiModelProperty(value = "物资库存当前余量")
    private String currentBalance;

    @ApiModelProperty(value = "订购单位 ")
    private String orderUnit;

    @ApiModelProperty(value = "订购单位 ", hidden = true)
    private String orderUnitName;

    @ApiModelProperty(value = "发放单位 ", hidden = true)
    private String issueUnitName;

    @ApiModelProperty(value = "发放单位 ")
    private String issueUnit;

    @ApiModelProperty(value = "是否周转")
    private boolean isTurnOver;

    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "状态", hidden = true)
    private String statusName;

    @ApiModelProperty(value = "站点", hidden = true)
    private String siteId;
    @ApiModelProperty(value = "组织", hidden = true)
    private String orgId;

    private  Boolean collect ;

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
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

    public boolean isTurnOver() {
        return isTurnOver;
    }

    public void setTurnOver(boolean isTurnOver) {
        this.isTurnOver = isTurnOver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MaterialInventoryVoForList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemName='" + itemName + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", currentBalance='" + currentBalance + '\'' +
                ", orderUnit='" + orderUnit + '\'' +
                ", orderUnitName='" + orderUnitName + '\'' +
                ", issueUnitName='" + issueUnitName + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", isTurnOver=" + isTurnOver +
                ", status='" + status + '\'' +
                '}';
    }


}
