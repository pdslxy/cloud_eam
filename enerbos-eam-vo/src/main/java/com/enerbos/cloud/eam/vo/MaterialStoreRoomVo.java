package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2015-2017
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月12日 下午4:20:31
 * @Description 库房实体
 */
@ApiModel(value = "库房实体")
public class MaterialStoreRoomVo extends EAMBaseFilterVo {


    /**
     * id
     */
    @ApiModelProperty(value = "库房ID(新增不用传值)")
    private String id;

    /**
     * 库房编码
     */
    @ApiModelProperty(value = "库房编码(新增不能为空并且长度不能超过255个字符)")
    @Length(max = 255, message = "库房编码的长度不能超过255个字符")
    private String storeroomNum;

    /**
     * 库房名称
     */
    @ApiModelProperty(value = "库房名称(新增不能为空并且长度不能超过255个字符)")
    @Length(max = 255, message = "库房名称的长度不能超过255个字符")
    private String storeroomName;

    /**
     * 科目
     */
    @ApiModelProperty(value = "科目(长度不能超过255个字符)")
    @Length(max = 255, message = "科目的长度不能超过255个字符")
    private String controlacc;

    @ApiModelProperty(value = "状态")
    @Length(max = 255, message = "状态的长度不能超过255个字符")
    private String status;
    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址(长度不能超过255个字符)")
    @Length(max = 255, message = "收货地址的长度不能超过255个字符")
    private String deliveryAddress;

    /**
     * 是否设置为缺省库房
     */
    @ApiModelProperty(value = "是否设置为缺省库房")
    private Boolean isdefault;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注(长度不能超过255个字符)")
    @Length(max = 255, message = "备注的长度不能超过255个字符")
    private String mark;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码(长度不能超过255个字符)")
    @Length(max = 255, message = "所属站点编码的长度不能超过255个字符")
    private String siteId;

    @ApiModelProperty(value = "所属站点名称(长度不能超过255个字符)")
    private String siteName;


    /**
     * 所属组织编码
     */
    @ApiModelProperty(value = "所属组织编码(长度不能超过255个字符)")
    @Length(max = 255, message = "所属组织编码的长度不能超过255个字符")
    private String orgId;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人(长度不能超过255个字符)")
    @Length(max = 255, message = "负责人的长度不能超过255个字符")
    private String personId;

    @ApiModelProperty(value = "负责人名称(长度不能超过255个字符)")
    private String personName;

    @ApiModelProperty(value = "创建人(新增不需要传值)")
    private String createUser;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "MaterialStoreRoomVo{" +
                "id='" + id + '\'' +
                ", storeroomNum='" + storeroomNum + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", controlacc='" + controlacc + '\'' +
                ", status='" + status + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", isdefault=" + isdefault +
                ", mark='" + mark + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", personId='" + personId + '\'' +
                ", personName='" + personName + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }

}
