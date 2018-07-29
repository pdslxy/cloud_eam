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
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点实体类
 */
@ApiModel(value = "物资盘点实体类")
public class MaterialCheckVoForList extends EAMBaseFilterVo {

    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 盘点单编号
     */
    @ApiModelProperty(value = "盘点单编号")
    private String checkNum;
    /**
     * 盘点单描述
     */
    @ApiModelProperty(value = "盘点单描述")
    private String description;
    /**
     * 库房名称
     */
    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    /**
     * 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     */
    @ApiModelProperty(value = "状态")
    private String status;
    /**
     * 盘点负责人
     */
    @ApiModelProperty(value = "盘点负责人")
    private String checkPerson;


    /**
     * 盘点时间
     */
    @ApiModelProperty(value = "盘点时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


    /**
     * 是否盈亏
     */
    @ApiModelProperty(value = "是否盈亏")
    private boolean profit;


    /**
     * 余量是否调整
     */
    @ApiModelProperty(value = "余量是否调整")
    private boolean adjust;


    private Boolean collect;

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isProfit() {
        return profit;
    }

    public void setProfit(boolean profit) {
        this.profit = profit;
    }

    public boolean isAdjust() {
        return adjust;
    }

    public void setAdjust(boolean adjust) {
        this.adjust = adjust;
    }

    @Override
    public String toString() {
        return "MaterialCheckVoForList{" +
                "id='" + id + '\'' +
                ", checkNum='" + checkNum + '\'' +
                ", description='" + description + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", status='" + status + '\'' +
                ", checkPerson='" + checkPerson + '\'' +
                ", updateDate=" + updateDate +
                ", profit=" + profit +
                ", adjust=" + adjust +
                ", collect=" + collect +
                '}';
    }
}
