package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年10月31日 19:22:39
 * @Description 环境监测收藏
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_meter_rf_collector")
public class MeterRfCollector extends EnerbosBaseEntity {

    @Column(name = "asset_id", nullable = false, length = 36)
    private String assetId;

    @Column(name = "meter_id", nullable = false, length = 36)
    private String meterId;
    
    @Column(name = "person_id", nullable = false, length = 36)
    private String personId;

    @Column(name = "product", nullable = false, length = 36)
    private String product;

    public MeterRfCollector() {
    }

    public MeterRfCollector(String personId, String product,String assetId,String meterId) {
        this.personId = personId;
        this.product = product;
        this.assetId=assetId;
        this.meterId=meterId;
    }
    

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    @Override
    public String toString() {
        return "MeterRfCollector{" +
                "assetId='" + assetId + '\'' +
                ", meterId='" + meterId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
