package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年11月16日 13:58:18
 * @Description 仪表与抄表计划关系
 */
@Entity
@Table(name = "eam_daily_copymeter_plan_meter_relation")
public class DaliyCopyMeterPlanMeterRelation extends EnerbosBaseEntity implements Serializable {

    /**
     * 仪表台帐id
     */
    @Column(name = "meter_id", nullable = false, length = 255)
    private String meterId;

    /**
     * 抄表计划id
     */
    @Column(name = "copymeter_plan_id", nullable = false, length = 255)
    private String copymeterPlanId;


    /**
     * 组织id
     */
    @Column(name = "site_id", nullable = true, length = 36)
    private String siteId;

    /**
     * 站点id
     */
    @Column(name = "org_id", nullable = true, length = 36)
    private String orgId;

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getCopymeterPlanId() {
        return copymeterPlanId;
    }

    public void setCopymeterPlanId(String copymeterPlanId) {
        this.copymeterPlanId = copymeterPlanId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "DaliyCopyMeterPlanMeterRf{" +
                "meterId='" + meterId + '\'' +
                ", copymeterPlanId='" + copymeterPlanId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
