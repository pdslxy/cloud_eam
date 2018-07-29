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
 * @Description 缺陷单关联人员
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_defect_document_rf_collector")
public class DefectDocumentRfCollector implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "defect_document_id", nullable = false, length = 36)
    private String defectDocumentId;

    @Column(name = "person_id", nullable = false, length = 36)
    private String personId;

    @Column(name = "product", nullable = false, length = 36)
    private String product;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", nullable = true)
    private Date createDate;

    public DefectDocumentRfCollector() {}

    public DefectDocumentRfCollector(String defectDocumentId, String personId, String product) {
        this.defectDocumentId = defectDocumentId;
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

    public String getDefectDocumentId() {
        return defectDocumentId;
    }

    public void setDefectDocumentId(String defectDocumentId) {
        this.defectDocumentId = defectDocumentId;
    }

    @Override
    public String toString() {
        return "DefectDocumentRfCollector{" +
                "id='" + id + '\'' +
                ", defectDocumentId='" + defectDocumentId + '\'' +
                ", personId='" + personId + '\'' +
                ", product='" + product + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
