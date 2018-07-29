package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 莫鹏
 * @version 1.0
 * @date 2017年8月21日
 * @Description EAM 公共查询基础Vo类
 */
public class EAMBaseFilterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "站点ID")
    private String siteId;

    @ApiModelProperty("产品 id 数组")
    private String[] productArray ;

    @ApiModelProperty(value = "当前页(必填,起始值为0)")
    @NotNull(message = "当前页不能为空")
    private int pageNum = 0;

    @ApiModelProperty(value = "每页显示行数(必填,默认为10)")
    @NotNull(message = "每页显示行数不能为空")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序参数")
    private String sorts ;

    @ApiModelProperty(value = "排序方式（asc升序/desc降序）")
    private String order;

    @ApiModelProperty(value = "关键字")
    private String keyword ;

    @ApiModelProperty(hidden = true)
    private String[] keywords ;

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

    public String[] getProductArray() {
        return productArray;
    }

    public void setProductArray(String[] productArray) {
        this.productArray = productArray;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "EAMBaseFilterVo{" +
                "orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", productArray=" + Arrays.toString(productArray) +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sorts='" + sorts + '\'' +
                ", order='" + order + '\'' +
                ", keyword='" + keyword + '\'' +
                ", keywords=" + Arrays.toString(keywords) +
                '}';
    }
}
