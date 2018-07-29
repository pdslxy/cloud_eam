package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
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
 * @Description 物资接收单
 */
@ApiModel(value = "物资接收单")
public class MaterialGoodsReceiveVo extends EAMBaseFilterVo {

    @ApiModelProperty(value = "唯一标识id")
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
     * 接收类型(采购接收,库存转移)
     */
    @ApiModelProperty(value = " 接收类型")
    private String receiveType;

    /**
     * 要运输到的库房标识
     */
    @ApiModelProperty(value = "要运输到的库房标识")
    private String storeroomId;


    @ApiModelProperty(value = "库房名称")
    private String storeroomName;

    @ApiModelProperty(value = "库房名称")
    private String storeroomNum;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String mark;

    /**
     * 实际接收日期和时间
     */
    @ApiModelProperty(value = "接收日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//	@JsonDeserialize(using = DateDeser.class)
    private Date receiveDate;

    /**
     * 实际接收人
     */
    @ApiModelProperty(value = "实际接收人")
    private String receivePerson;

    /**
     * 物资接收单的当前状态 DRAFT-草稿 RECEIVE-已接收
     */
    @ApiModelProperty(value = "当前状态")
    private String status;

    @ApiModelProperty(value = "当前状态")
    private String statusName;

    /**
     * 最后修改人员
     */
    @ApiModelProperty(value = "最后修改人员")
    private String changeUser;

    /**
     * 接收说明
     */
    private String explains;
    /**
     * 所属组织id
     */
    @ApiModelProperty(value = "组织id")
    private String orgId;

    /**
     * 所属站点id
     */
    @ApiModelProperty(value = "站点id")
    private String siteId;

    @ApiModelProperty(value = "创建人(新增不需要传值)")
    private String createUser;

    private List<MaterialGoodsReceiveDetailVo> materialGoodsReceiveDetailVos;

    public List<MaterialGoodsReceiveDetailVo> getMaterialGoodsReceiveDetailVos() {
        return materialGoodsReceiveDetailVos;
    }

    public void setMaterialGoodsReceiveDetailVos(List<MaterialGoodsReceiveDetailVo> materialGoodsReceiveDetailVos) {
        this.materialGoodsReceiveDetailVos = materialGoodsReceiveDetailVos;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MaterialGoodsReceiveVo{" +
                "id='" + id + '\'' +
                ", goodsReceiveNum='" + goodsReceiveNum + '\'' +
                ", description='" + description + '\'' +
                ", receiveType='" + receiveType + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", storeroomNum='" + storeroomNum + '\'' +
                ", mark='" + mark + '\'' +
                ", receiveDate=" + receiveDate +
                ", receivePerson='" + receivePerson + '\'' +
                ", status='" + status + '\'' +
                ", changeUser='" + changeUser + '\'' +
                ", explains='" + explains + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", materialGoodsReceiveDetailVos=" + materialGoodsReceiveDetailVos +
                '}';
    }
}
