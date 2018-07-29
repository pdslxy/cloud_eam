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
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点实体类
 */
@ApiModel(value = "库存物资盘点实体")
public class MaterialCheckVoForInventoryList extends EAMBaseFilterVo {

    private String id;
    /**
     * 盘点单编号
     */
    @ApiModelProperty(value = "盘点单编号")
    private String checkNum;

    /**
     * 当前余量
     */
    @ApiModelProperty(value = "量")
    private long currentAllowance;


    /**
     * 当前余量
     */
    @ApiModelProperty(value = "物资库存当前余量")
    private long currentBalance;
    /**
     * 可用数量
     */
    @ApiModelProperty(value = "可用数量")
    private long availableBalance;


    /**
     * 盘点负责人
     */
    @ApiModelProperty(value = "盘点负责人")
    private String checkPerson;


    /**
     * 盘点开始时间
     */
    @ApiModelProperty(value = "盘点开始时间")
    private String updateDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public long getCurrentAllowance() {
        return currentAllowance;
    }

    public void setCurrentAllowance(long currentAllowance) {
        this.currentAllowance = currentAllowance;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "MaterialCheckVoForInventoryList{" +
                "id='" + id + '\'' +
                ", checkNum='" + checkNum + '\'' +
                ", currentAllowance=" + currentAllowance +
                ", currentBalance=" + currentBalance +
                ", availableBalance=" + availableBalance +
                ", checkPerson='" + checkPerson + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
