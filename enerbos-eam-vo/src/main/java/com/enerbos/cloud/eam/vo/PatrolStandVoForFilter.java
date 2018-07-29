package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version EAM2.0
 * @date 2017年8月10日 14:03:12
 * @Description 巡检标准Vo
 */
@ApiModel(value = "巡检标准")
public class PatrolStandVoForFilter implements Serializable {

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织 id")
    private String orgId;

    /**
     * 站点ID
     */
    @ApiModelProperty(value = "站点 id")
    private String siteId;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "分页-页码")
    private int pageNum = 1;

    /**
     * 每页显示行数
     */
    @ApiModelProperty(value = "分页-每页显示条数")
    private int pageSize = 10;
    /**
     * 排序参数
     */
    @ApiModelProperty(value = "排序参数")
    private String sorts;

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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    @Override
    public String toString() {
        return "PatrolStandVoForFilter{" +
                "orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sorts='" + sorts + '\'' +
                '}';
    }
}
