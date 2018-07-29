package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 16:53
 * @Description 总部计划实体类
 */
@ApiModel(value = "总部计划--更改状态vo")
public class HeadquartersPlanVoForUpStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键ID集合")
    private List<String> ids;

    @ApiModelProperty(value = "状态   --活动、不活动、草稿")
    private String status;

    @ApiModelProperty(value = "站点id")
    private String siteId;

    public HeadquartersPlanVoForUpStatus() {}
    public HeadquartersPlanVoForUpStatus(List<String> ids, String status, String siteId) {
        this.ids = ids;
        this.status = status;
        this.siteId = siteId;
    }
    public HeadquartersPlanVoForUpStatus(List<String> ids, String status) {
        this.ids = ids;
        this.status = status;

    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
