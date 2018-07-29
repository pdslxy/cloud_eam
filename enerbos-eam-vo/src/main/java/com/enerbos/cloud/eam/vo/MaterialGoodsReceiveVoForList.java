package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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
 * @Description 物资接收单列表实体
 */
@ApiModel(value = "物资接收单列表实体")
public class MaterialGoodsReceiveVoForList extends EAMBaseFilterVo {

    @ApiModelProperty(value = "唯一标识")
    private String id;

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

    /**
     * 实际接收人
     */
    @ApiModelProperty(value = "实际接收人")
    private String receivePerson;

    /**
     * 实际接收日期和时间
     */
    @ApiModelProperty(value = "接收日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveDate;

    /**
     * 物资接收单的当前状态 DRAFT-草稿 RECEIVE-已接收
     */
    @ApiModelProperty(value = "当前状态")
    private String status;

    private Boolean collect;

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

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MaterialGoodsReceiveVoForList [goodsReceiveNum="
                + goodsReceiveNum + ", description=" + description
                + ", receivePerson=" + receivePerson + ", receiveDate="
                + receiveDate + ", status=" + status + "]";
    }

}
