package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
 * @date 2017年7月12日 下午1:45:53
 * @Description 库房管理查询条件过滤实体
 */
@ApiModel(value = "库房管理查询条件过滤实体")
public class MaterialStoreRoomVoForFilter extends EAMBaseFilterVo implements
        Serializable {

    private static final long serialVersionUID = -4779851640549697964L;

    /**
     * 库房编码
     */
    @ApiModelProperty(value = "库房编码")
    private String storeroomNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    /**
     * 科目
     */
    @ApiModelProperty(value = "科目")
    private String controlacc;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String deliveryAddress;

    /**
     * 是否设置为缺省库房
     */

    @ApiModelProperty(value = "是否为缺省库房")
    private Boolean isdefault;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人ID")
    private String personId;

    @ApiModelProperty(value = "状态")
    private String status;


    @ApiModelProperty(value = "模糊搜索关键词")
    private String word;
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "收藏")
    private Boolean collect;


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

    public String getStoreroomNum() {
        return storeroomNum;
    }

    public void setStoreroomNum(String storeroomNum) {
        this.storeroomNum = storeroomNum;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public String getControlacc() {
        return controlacc;
    }

    public void setControlacc(String controlacc) {
        this.controlacc = controlacc;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "MaterialStoreRoomVoForFilter{" +
                "storeroomNum='" + storeroomNum + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", controlacc='" + controlacc + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", isdefault=" + isdefault +
                ", personId='" + personId + '\'' +
                ", status='" + status + '\'' +
                ", word='" + word + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", collect=" + collect +
                '}';
    }
}
