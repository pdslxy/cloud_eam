package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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
 * @date 2017年3月31日 上午11:29:25
 * @Description 物资台账实体类
 */
@ApiModel(value = "物资台账")
public class MaterialItemVoForAssertList extends EAMBaseFilterVo {

    /**
     * id
     */
    @ApiModelProperty(value = "item唯一标识(新增不用传值)")
    private String id;

    /**
     * 物资编码
     */
    @ApiModelProperty(value = "物资编号(最大长度不能超过255个字符)")
    private String itemNum;

    /**
     * 物资描述
     */
    @ApiModelProperty(value = "物资描述(不能为空且最大长度不能超过255个字符)")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    @NotEmpty(message = "物资描述不能为空")
    private String description;

    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    private String model;
    /**
     * 状态 PENDING-暂挂 ACTIVE-活动
     */
    @ApiModelProperty(value = "状态")
    @Length(max = 255, message = "最大长度不能超过255个字符")
    private String status;


    /**
     * 订购单位（个）
     */
    @ApiModelProperty(value = "订购单位（个）")
    private String orderUnit;


    @ApiModelProperty(value = "当前数量")
    private Long currentBalance;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String toString() {
        return "MaterialItemVoForAssertList{" +
                "id='" + id + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", description='" + description + '\'' +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", orderUnit='" + orderUnit + '\'' +
                ", currentBalance=" + currentBalance +
                '}';
    }
}
