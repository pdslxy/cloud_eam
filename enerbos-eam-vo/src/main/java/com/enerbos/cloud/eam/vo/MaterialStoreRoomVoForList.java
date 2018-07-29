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
 * @date 2017年7月12日 下午2:07:47
 * @Description 库房列表实体
 */
@ApiModel(value = "库房列表实体")
public class MaterialStoreRoomVoForList implements Serializable {

    private static final long serialVersionUID = -4779851640549697964L;

    /**
     * id
     */
    @ApiModelProperty(value = "库房ID")
    private String id;

    /**
     * 库房编码
     */
    @ApiModelProperty(value = "库房编码")
    private String storeroomNum;

    /**
     * 库房名称
     */
    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    /**
     * 科目
     */
    @ApiModelProperty(value = "科目")
    private String controlacc;

    @ApiModelProperty(value = "状态")
    private String status;
    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String deliveryAddress;

    /**
     * 是否设置为缺省库房
     */
    @ApiModelProperty(value = "是否设置为缺省库房")
    private Boolean isdefault;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人ID")
    private String personId;

    @ApiModelProperty(value = "负责人名称")
    private String personName;
    @ApiModelProperty(value = "收藏")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "MaterialStoreRoomVoForList [id=" + id + ", storeroomNum="
                + storeroomNum + ", storeroomName=" + storeroomName
                + ", controlacc=" + controlacc + ", status=" + status
                + ", deliveryAddress=" + deliveryAddress + ", isdefault="
                + isdefault + ", personId=" + personId + ", personName="
                + personName + "]";
    }


}
