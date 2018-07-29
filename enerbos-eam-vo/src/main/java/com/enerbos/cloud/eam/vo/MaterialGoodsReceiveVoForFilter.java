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
 * @date 2017年4月1日 上午9:26:50
 * @Description 物资接收单查询 条件过滤实体
 */
@ApiModel(value = "物资接收单查询 条件过滤实体")
public class MaterialGoodsReceiveVoForFilter extends EAMBaseFilterVo implements Serializable {

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String goodsReceiveNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    /**
     * 接收类型(采购接收,库存转移)
     */
    @ApiModelProperty(value = " 接收类型")
    private String receiveType;


    @ApiModelProperty(value = "开始 时间")
    private String startDate;

    @ApiModelProperty(value = "结束 时间")
    private String endDate;

    private Boolean collect;

    /**
     * 实际接收人
     */
    @ApiModelProperty(value = "实际接收人")
    private String receivePerson;

    /**
     * 物资接收单的当前状态 DRAFT-草稿 RECEIVE-已接收
     */
    @ApiModelProperty(value = "当前状态")
    private List<String> status;

    @ApiModelProperty(value = "模糊 搜索 关键词")
    private String word;


    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getGoodsReceiveNum() {
        return goodsReceiveNum;
    }

    public void setGoodsReceiveNum(String goodsReceiveNum) {
        this.goodsReceiveNum = goodsReceiveNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }


    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MaterialGoodsReceiveVoForFilter{" +
                "goodsReceiveNum='" + goodsReceiveNum + '\'' +
                ", description='" + description + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", receiveType='" + receiveType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", receivePerson='" + receivePerson + '\'' +
                ", status=" + status +
                ", word='" + word + '\'' +
                '}';
    }
}
