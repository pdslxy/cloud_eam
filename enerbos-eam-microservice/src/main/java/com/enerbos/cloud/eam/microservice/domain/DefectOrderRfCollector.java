package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月6日
 * @Description 消缺工单关联人员
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_defect_order_rf_collector")
public class DefectOrderRfCollector implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "defect_order_id", nullable = false, length = 36)
    private String defectOrderId;

    @Column(name = "person_id", nullable = false, length = 36)
    private String personId;

    @Column(name = "product", nullable = false, length = 36)
    private String product;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", nullable = true)
    private Date createDate;

    public DefectOrderRfCollector() {}

    public DefectOrderRfCollector(String defectOrderId, String personId, String product) {
        this.defectOrderId = defectOrderId;
        this.personId = personId;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDefectOrderId() {
        return defectOrderId;
    }

    public void setDefectOrderId(String defectOrderId) {
        this.defectOrderId = defectOrderId;
    }

    @Override
    public String toString() {
        return "DefectDocumentRfCollector{" +
                "id='" + id + '\'' +
                ", defectOrderId='" + defectOrderId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
