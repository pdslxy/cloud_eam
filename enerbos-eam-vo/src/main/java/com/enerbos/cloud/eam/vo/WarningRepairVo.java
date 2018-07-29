package com.enerbos.cloud.eam.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/10/11 下午4:48
 * @Description
 */
public class WarningRepairVo implements Serializable{


    private String id ;

    private Date ceateDate ;

    private Date updateDate ;

    private String createUser ;
    /**
     * 环境监测设备ID
     */
    private String assetId;

    /**
     * 工单ID
     */
    private String workOrderId;

    /**
     * 测点ID
     */
    private String meterId;

    /**
     * 报警点
     */
    private String tagName;

    /**
     * 报警类型
     */
    private String warningType;


    /**
     * 检测值
     */
    private String detecteValue;

    private String description ;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getDetecteValue() {
        return detecteValue;
    }

    public void setDetecteValue(String detecteValue) {
        this.detecteValue = detecteValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCeateDate() {
        return ceateDate;
    }

    public void setCeateDate(Date ceateDate) {
        this.ceateDate = ceateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
