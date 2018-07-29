package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/19.
 */
@Entity
@Table(name = "eam_repair_evaluate")
public class RepairEvaluate {

    private static final long serialVersionUID = 3894256874881718604L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;


    /**
     * 维修质量
     */
    @Column(name = "repair_quality", nullable = false)
    private String repairQuality;


    /**
     * 维修态度
     */
    @Column(name = "repair_attitude", nullable = false)
    private String repairAttitude;


    /**
     * 评价说明
     */
    @Column(name = "description", nullable = false)
    private String description;
    
    /**
     * 人员 id
     */
    @Column(name = "person_id", nullable = false, length = 36)
    private String personId;

    /**
     * 站点 id
     */
    @Column(name = "site_id", nullable = false, length = 36)
    private String siteId;


    /**
     * 报修工单id
     */
    @Column(name = "repair_order_id", nullable = false, length = 36)
    private String repairOrderId;

    /**
     * 照片地址
     */
    @Column(name = "img_path", nullable = true)
    private String imgPath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepairQuality() {
        return repairQuality;
    }

    public void setRepairQuality(String repairQuality) {
        this.repairQuality = repairQuality;
    }

    public String getRepairAttitude() {
        return repairAttitude;
    }

    public void setRepairAttitude(String repairAttitude) {
        this.repairAttitude = repairAttitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(String repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    @Override
    public String toString() {
        return "RepairEvaluate{" +
                "id='" + id + '\'' +
                ", repairQuality='" + repairQuality + '\'' +
                ", repairAttitude='" + repairAttitude + '\'' +
                ", description='" + description + '\'' +
                ", personId='" + personId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", repairOrderId='" + repairOrderId + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
