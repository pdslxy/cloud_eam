package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点实体类
 */
@ApiModel(value = "物资盘点实体类")
public class MaterialCheckVoForFilter extends EAMBaseFilterVo implements
        Serializable {

    private static final long serialVersionUID = -4779851640549697964L;


    /**
     * 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     */
    @ApiModelProperty(value = "状态")
    private List<String> status;
    /**
     * 盘点负责人
     */
    @ApiModelProperty(value = "盘点负责人")
    private String checkPerson;


    /**
     * 盘点开始时间
     */
    @ApiModelProperty(value = "盘点开始时间")
    private String startUpdateDate;

    /**
     * 盘点结束时间
     */
    @ApiModelProperty(value = "盘点结束时间")
    private String endUpdateDate;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建开始时间")
    private String startDate;

    @ApiModelProperty(value = "创建结束时间")
    private String endDate;

    private Boolean collect;
    /**
     * 是否盈亏
     */
    @ApiModelProperty(value = "盘点单编号")
    private Boolean profit;


    /**
     * 余量是否调整
     */
    @ApiModelProperty(value = "余量是否调整")
    private Boolean adjust;


    @ApiModelProperty(value = "搜索关键词")
    private String word;


    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getStartUpdateDate() {
        return startUpdateDate;
    }

    public void setStartUpdateDate(String startUpdateDate) {
        this.startUpdateDate = startUpdateDate;
    }

    public String getEndUpdateDate() {
        return endUpdateDate;
    }

    public void setEndUpdateDate(String endUpdateDate) {
        this.endUpdateDate = endUpdateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Boolean getProfit() {
        return profit;
    }

    public void setProfit(Boolean profit) {
        this.profit = profit;
    }

    public Boolean getAdjust() {
        return adjust;
    }

    public void setAdjust(Boolean adjust) {
        this.adjust = adjust;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MaterialCheckVoForFilter{" +
                "status='" + status + '\'' +
                ", checkPerson='" + checkPerson + '\'' +
                ", startUpdateDate='" + startUpdateDate + '\'' +
                ", endUpdateDate='" + endUpdateDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", collect=" + collect +
                ", profit=" + profit +
                ", adjust=" + adjust +
                ", word='" + word + '\'' +
                '}';
    }
}
