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
 * @date 2017年7月17日11:00:39
 * @Description 物资盘点实体类
 */
@ApiModel(value = "物资盘点实体类")
public class MaterialCheckVo extends EAMBaseFilterVo {

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "id标识")
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
     * 库房id
     */
    @ApiModelProperty(value = "库房id")
    private String storeroomId;

    @ApiModelProperty(value = "库房编码", hidden = true)
    private String storeroomNum;
    @ApiModelProperty(value = "库房名称", hidden = true)
    private String storeroomName;

    /**
     * 盘点负责人
     */
    @ApiModelProperty(value = "盘点负责人")
    private String checkPerson;

    /**
     * 状态时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "状态时间")
    private Date statusDate;

    /**
     * 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     */

    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 是否盈亏
     */
    @ApiModelProperty(value = "盘点单编号")
    private boolean profit;


    /**
     * 余量是否调整
     */
    @ApiModelProperty(value = "余量是否调整")
    private boolean adjust;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    private List<MaterialCheckDetailVo> materialCheckDetailVos;


    public List<MaterialCheckDetailVo> getMaterialCheckDetailVos() {
        return materialCheckDetailVos;
    }

    public void setMaterialCheckDetailVos(List<MaterialCheckDetailVo> materialCheckDetailVos) {
        this.materialCheckDetailVos = materialCheckDetailVos;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(String storeroomId) {
        this.storeroomId = storeroomId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "MaterialCheckVo{" +
                "id='" + id + '\'' +
                ", checkNum='" + checkNum + '\'' +
                ", description='" + description + '\'' +
                ", storeroomId='" + storeroomId + '\'' +
                ", storeroomNum='" + storeroomNum + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", checkPerson='" + checkPerson + '\'' +
                ", statusDate=" + statusDate +
                ", status='" + status + '\'' +
                ", profit=" + profit +
                ", adjust=" + adjust +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
