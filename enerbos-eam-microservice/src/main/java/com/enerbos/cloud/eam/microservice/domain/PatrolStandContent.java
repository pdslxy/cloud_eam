package com.enerbos.cloud.eam.microservice.domain;


import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version EAM2.0
 * @date 2017年8月10日 14:08:12
 * @Description 巡检标准内容
 */
@Entity
@Table(name = "patrol_stand_content")
public class PatrolStandContent extends EnerbosBaseEntity implements Serializable {

//
//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
//    @Column(unique = true, nullable = false, length = 36)
//    private String id;


    /**
     * 描述
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * 检查标准
     */
    @Column(name = "check_stand", nullable = true, length = 255)
    private String checkStand;


    /**
     * 巡检标准id
     */
    @Column(name = "patrol_stand_id", nullable = true, length = 255)
    private String patrolStandId;

    /**
     * 备注
     */
    @Column(name = "remark", nullable = true, length = 255)
    private String remark;


//    /**
//     * 创建时间
//     */
//    @Column(name = "create_date", nullable = false, length = 20)
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date createDate;
//
//    /**
//     * 最后更新时间
//     */
//    @Column(name = "update_date", nullable = false, length = 20)
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date updateDate;
//
//
//    /**
//     * 创建人
//     */
//    @Column(name = "create_user", nullable = false, length = 20)
//    private String createUser;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckStand() {
        return checkStand;
    }

    public void setCheckStand(String checkStand) {
        this.checkStand = checkStand;
    }

    public String getPatrolStandId() {
        return patrolStandId;
    }

    public void setPatrolStandId(String patrolStandId) {
        this.patrolStandId = patrolStandId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PatrolStandContent{" +
                "description='" + description + '\'' +
                ", checkStand='" + checkStand + '\'' +
                ", patrolStandId='" + patrolStandId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
