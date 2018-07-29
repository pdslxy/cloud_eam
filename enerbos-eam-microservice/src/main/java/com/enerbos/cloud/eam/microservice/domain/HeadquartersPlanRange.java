package com.enerbos.cloud.eam.microservice.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 16:53
 * @Description 总部计划应用范围实体类
 */
@Entity
@Table(name = "eam_headquarters_plan_range")
public class HeadquartersPlanRange implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 总部计划主键
     */
    @Column(name = "plan_id",  length = 36)
    private String planId;

    /**
     * 站点ID
     */
    @Column(name = "site_id",  length = 36)
    private String siteId;

    /**
     * 组织ID
     */
    @Column(name = "org_id",  length = 36)
    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}