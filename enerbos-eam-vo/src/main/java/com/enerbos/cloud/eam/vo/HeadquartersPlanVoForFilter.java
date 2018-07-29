package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 20:55
 * @Description
 */

@ApiModel(value = "总部计划分页查询，过滤vo")
public class HeadquartersPlanVoForFilter extends EAMBaseFilterVo {

    @ApiModelProperty(value = "计划编号")
    private String planNum;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "计划描述")
    private String description;

    @ApiModelProperty(value = "计划性质列表，逗号分隔")
    private List<String> nature;

    @ApiModelProperty(value = "状态列表，逗号分隔")
    private List<String> status;

    @ApiModelProperty(value = "检查项列表，逗号分隔")
    private List<String> checkItem;

    //有效开始日期
    @ApiModelProperty(value = "有效开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartDate;
    //有效结束日期
    @ApiModelProperty(value = "有效结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndDate;

//    @ApiModelProperty(value = "组织id")

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;




    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNature() {
        return nature;
    }

    public void setNature(List<String> nature) {
        this.nature = nature;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(List<String> checkItem) {
        this.checkItem = checkItem;
    }

    public Date getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    @Override
    public String toString() {
        return "HeadquartersPlanVoForFilter{" +
                "planNum='" + planNum + '\'' +
                ", planName='" + planName + '\'' +
                ", description='" + description + '\'' +
                ", nature='" + nature + '\'' +
                ", status='" + status + '\'' +
                ", checkItem='" + checkItem + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
