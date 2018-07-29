package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点明细实体类
 */
@ApiModel(value = "物资盘点明细实体类")
public class MaterialCheckDetailVo extends EAMBaseFilterVo {

    @ApiModelProperty(value = "物资盘点明细id")
    private String id;
    /**
     * 当前余量
     */
    @ApiModelProperty(value = "当前余量")
    private String currentAllowance;

    /**
     * 实际余量
     */
    @ApiModelProperty(value = "实际余量")
    private String physicalInventory;

    /**
     * 库存id
     */
    @ApiModelProperty(value = "库存id")
    private String inventoryid;


    @ApiModelProperty(value = "创建人")
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "物资盘点id")
    private String checkId;



    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentAllowance() {
        return currentAllowance;
    }

    public void setCurrentAllowance(String currentAllowance) {
        this.currentAllowance = currentAllowance;
    }

    public String getPhysicalInventory() {
        return physicalInventory;
    }

    public void setPhysicalInventory(String physicalInventory) {
        this.physicalInventory = physicalInventory;
    }

    public String getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(String inventoryid) {
        this.inventoryid = inventoryid;
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
        return "MaterialCheckDetailVo{" +
                "id='" + id + '\'' +
                ", currentAllowance='" + currentAllowance + '\'' +
                ", physicalInventory='" + physicalInventory + '\'' +
                ", inventoryid='" + inventoryid + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", checkId='" + checkId + '\'' +
                '}';
    }
}
