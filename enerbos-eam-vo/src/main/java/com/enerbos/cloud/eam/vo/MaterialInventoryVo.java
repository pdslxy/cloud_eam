package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

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
 * @date 2017年3月31日 下午5:11:28
 * @Description 物资库存实体类
 */
@ApiModel(value = "物资库存")
public class MaterialInventoryVo extends EAMBaseFilterVo {

    // 物资发放类型
    public static final String COSTTYPE_STANDARD = "STANDARD";

    /**
     * id
     */
    @ApiModelProperty(value = "物资库存ID(新增不需要传值)")
    private String id;

    /**
     * 当前余量
     */
    @ApiModelProperty(value = "物资库存当前余量")
    private long currentBalance;
    /**
     * 预留数量
     */
    @ApiModelProperty(value = "预留数量")
    private long reservationNumber;
    /**
     * 可用数量
     */
    @ApiModelProperty(value = "可用数量")
    private long availableBalance;
    /**
     * 标准成本
     */
    @ApiModelProperty(value = "标准成本")
    private Double standardCost;

    @ApiModelProperty(value = "是否周转")
    private boolean isTurnOver;
    /**
     * 平均成本
     */
    @ApiModelProperty(value = "平均成本")
    private Double averageCost;
    /**
     * 上次接收成本
     */
    @ApiModelProperty(value = "上次接收成本")
    private Double lastReceiveCost;

    /**
     * ABC 类型
     */
    @ApiModelProperty(value = "ABC 类型(ABC 类型不能超过255个字符)")
    @Length(max = 255, message = "ABC 类型不能超过255个字符")
    private String abcType;

    /**
     * 重订购
     */
    @ApiModelProperty(value = "重订购")
    private Boolean reorder;

    /**
     * 重订购点
     */
    @ApiModelProperty(value = "重订购点")
    private String reorderPoint;//
    /**
     * 提前时间（天）
     */
    @ApiModelProperty(value = "提前时间（天）")
    private String deliveryTime;//
    /**
     * 安全库存
     */
    @ApiModelProperty(value = "安全库存(安全库存不能超过255个字符)")
    @Length(max = 255, message = "安全库存不能超过255个字符")
    private String safeStock;
    /**
     * 经济订购量
     */
    @ApiModelProperty(value = "经济订购量")
    private String economicOrderQuantity;

    /**
     * 订购单位
     */
    @ApiModelProperty(value = "订购单位(订购单位不能超过36个字符)")
    @Length(max = 36, message = "订购单位不能超过36个字符")
    private String orderUnit;

    /**
     * 发放单位
     */
    @ApiModelProperty(value = "发放单位(发放单位不能超过36个字符)")
    @Length(max = 36, message = "发放单位不能超过36个字符")
    private String issueUnit;
    /**
     * 订购单位
     */
    @ApiModelProperty(value = "订购单位(订购单位不能超过36个字符)", hidden = true)
    @Length(max = 36, message = "订购单位不能超过36个字符")
    private String orderUnitName;

    /**
     * 发放单位
     */
    @ApiModelProperty(value = "发放单位(发放单位不能超过36个字符)", hidden = true)
    @Length(max = 36, message = "发放单位不能超过36个字符")
    private String issueUnitName;

    /**
     * 发放成本类型
     */
    @ApiModelProperty(value = "发放成本类型(发放成本类型不能超过36个字符)")
    @Length(max = 36, message = "发放成本类型不能超过36个字符")
    private String costTypeId;


    /**
     * 状态 PENDING-暂挂 ACTIVE-活动
     */
    @ApiModelProperty(value = "状态(状态不能超过36个字符)")
    @Length(max = 36, message = "状态不能超过36个字符")
    private String status;

    @ApiModelProperty(value = "状态(状态不能超过36个字符)",hidden = true)
    private String statusName;

    /**
     * 上次发放日期
     */
    @ApiModelProperty(value = "上次发放日期(新增、修改不用传值)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastIssueDate;

    /**
     * 物资
     */
    @ApiModelProperty(value = "物资(物资不能超过36个字符)")
    @Length(max = 36, message = "物资不能超过36个字符")
    private String itemId;

    @ApiModelProperty(value = "物资编码")
    private String itemNum;

    @ApiModelProperty(value = "物资名称")
    private String itemName;

    /**
     * 库房
     */
    @ApiModelProperty(value = "库房(库房不能超过36个字符)")
    @Length(max = 36, message = "库房不能超过36个字符")
    private String storeroomId;

    /**
     * 库房名称
     */
    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    @ApiModelProperty(value = "库房编码")
    private String storeroomNum;

    @ApiModelProperty(value = "站点站点名称")
    private String siteName;

    @ApiModelProperty(value = "创建人")
    private String createUser;


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public MaterialInventoryVo() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(Double standardCost) {
        this.standardCost = standardCost;
    }

    public boolean isTurnOver() {
        return isTurnOver;
    }

    public void setTurnOver(boolean turnOver) {
        isTurnOver = turnOver;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public Double getLastReceiveCost() {
        return lastReceiveCost;
    }

    public String getStoreroomNum() {
        return storeroomNum;
    }

    public void setStoreroomNum(String storeroomNum) {
        this.storeroomNum = storeroomNum;
    }

    public void setLastReceiveCost(Double lastReceiveCost) {
        this.lastReceiveCost = lastReceiveCost;
    }

    public String getAbcType() {
        return abcType;
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType;
    }

    public Boolean getReorder() {
        return reorder;
    }

    public void setReorder(Boolean reorder) {
        this.reorder = reorder;
    }

    public String getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(String reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSafeStock() {
        return safeStock;
    }

    public void setSafeStock(String safeStock) {
        this.safeStock = safeStock;
    }

    public String getEconomicOrderQuantity() {
        return economicOrderQuantity;
    }

    public void setEconomicOrderQuantity(String economicOrderQuantity) {
        this.economicOrderQuantity = economicOrderQuantity;
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

    public String getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(String costTypeId) {
        this.costTypeId = costTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastIssueDate() {
        return lastIssueDate;
    }

    public void setLastIssueDate(Date lastIssueDate) {
        this.lastIssueDate = lastIssueDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "MaterialInventoryVo{" +
                "id='" + id + '\'' +
                ", currentBalance=" + currentBalance +
                ", reservationNumber=" + reservationNumber +
                ", availableBalance=" + availableBalance +
                ", standardCost=" + standardCost +
                ", isTurnOver=" + isTurnOver +
                ", averageCost=" + averageCost +
                ", lastReceiveCost=" + lastReceiveCost +
                ", abcType='" + abcType + '\'' +
                ", reorder=" + reorder +
                ", reorderPoint='" + reorderPoint + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", safeStock='" + safeStock + '\'' +
                ", economicOrderQuantity='" + economicOrderQuantity + '\'' +
                ", orderUnit='" + orderUnit + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", orderUnitName='" + orderUnitName + '\'' +
                ", issueUnitName='" + issueUnitName + '\'' +
                ", costTypeId='" + costTypeId + '\'' +
                ", status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                ", lastIssueDate=" + lastIssueDate +
                ", itemId='" + itemId + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", itemName='" + itemName + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", storeroomNum='" + storeroomNum + '\'' +
                ", siteName='" + siteName + '\'' +
                '}';
    }
}
