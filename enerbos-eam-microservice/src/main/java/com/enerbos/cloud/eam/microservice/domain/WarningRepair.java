package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author yanzuoyu
 * @version 1.0
 * @date 2017/10/10 13:36
 * @Description 预警报警与报修工单关系记录表
 */
@Entity
@Table(name = "eam_warning_repair")
public class WarningRepair extends EnerbosBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 环境监测设备ID
     */
    @Column
    private String assetId;

    /**
     * 工单ID
     */
    @Column
    private String workOrderId;

    /**
     * 测点ID
     */
    @Column
    private String meterId;

    /**
     * 报警点
     */
    @Column
    private String tagName;

    /**
     * 报警类型
     */
    @Column
    private String warningType;

    /**
     * 描述
     */
    @Column
    private String description ;
    /**
     * 检测值
     */
    @Column
    private String detecteValue;

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
}
