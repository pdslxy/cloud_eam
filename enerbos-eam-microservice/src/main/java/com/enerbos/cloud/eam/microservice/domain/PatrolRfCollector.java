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
 * @author lixiaoyang
 * @version 1.0
 * @date 2017-10-17
 * @Description 巡检收藏
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "patrol_rf_collector")
public class PatrolRfCollector extends EnerbosBaseEntity {

    @Column(name = "collect_id", nullable = false, length = 36)
    private String collectId;

    @Column(name = "type", nullable = false, length = 36)
    private String type;

    @Column(name = "person_id", nullable = false, length = 36)
    private String personId;

    @Column(name = "product", nullable = false, length = 36)
    private String product;

    public PatrolRfCollector() {
    }

    public PatrolRfCollector(String collectId, String type, String personId, String product) {
        this.collectId = collectId;
        this.type = type;
        this.personId = personId;
        this.product = product;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PatrolRfCollector{" +
                "id" + this.getId() + '\'' +
                ", collectId='" + collectId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
