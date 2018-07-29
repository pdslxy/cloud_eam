package com.enerbos.cloud.eam.microservice.domain;/**
 * Created by enerbos on 2017/12/1.
 */

import com.enerbos.cloud.jpa.EnerbosBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/12/1 13:30
 * @Description: 能源价格变更记录
 */
@Entity
@Table(name = "eam_energyprice_chang_history")
public class EnergyPriceChangHistory extends EnerbosBaseEntity {




      //变更之前的价格
      @Column(name = "ChangPriceBefore")
      private double ChangPriceBefore;
      //变更后价格
      @Column(name = "ChangPriceAfter")
      private  double ChangPriceAfter;

      //能源价格ID
      @Column(name = "enery_price_id")
      private String  EneryPriceId;

     @Column(name = "org_Id")
      private String orgId;

     @Column(name = "site_Id")
      private String siteId;

    public double getChangPriceBefore() {
        return ChangPriceBefore;
    }

    public void setChangPriceBefore(double changPriceBefore) {
        ChangPriceBefore = changPriceBefore;
    }

    public double getChangPriceAfter() {
        return ChangPriceAfter;
    }

    public void setChangPriceAfter(double changPriceAfter) {
        ChangPriceAfter = changPriceAfter;
    }

    public String getEneryPriceId() {
        return EneryPriceId;
    }

    public void setEneryPriceId(String eneryPriceId) {
        EneryPriceId = eneryPriceId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "EnergyPriceChangHistory{" +
                "ChangPriceBefore=" + ChangPriceBefore +
                ", ChangPriceAfter=" + ChangPriceAfter +
                ", EneryPriceId='" + EneryPriceId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                '}';
    }
}
