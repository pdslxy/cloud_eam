package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
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
 * @date 2017年11月17日 10:51:56
 * @Description 抄表计划列表
 */
@ApiModel(value = "抄表计划列表")
public class DailyCopyMeterPlanVoForList extends EAMBaseFilterVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7983360432330979802L;

    @ApiModelProperty(value = "唯一标识")
    private String id;
    /**
     * 抄表计划编码
     */
    @ApiModelProperty(value = "抄表计划编码")
    private String copyMeterPlanNum;

    /**
     * 抄表计划描述
     */
    @ApiModelProperty(value = "抄表计划描述")
    private String description;


    /**
     * 仪表类型
     */
    @ApiModelProperty(value = "仪表类型")
    private String copyMeterType;

    @ApiModelProperty(value = "仪表类型名称")
    private String copyMeterTypeName;
    /**
     * 状态（活动、不活动、草稿）
     */
    @ApiModelProperty(value = "状态")
    private String status;


    @ApiModelProperty(value = "状态描述")
    private String statusDsr;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "是否收藏")
    private Boolean collect;

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getCopyMeterTypeName() {
        return copyMeterTypeName;
    }

    public void setCopyMeterTypeName(String copyMeterTypeName) {
        this.copyMeterTypeName = copyMeterTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCopyMeterPlanNum() {
        return copyMeterPlanNum;
    }

    public void setCopyMeterPlanNum(String copyMeterPlanNum) {
        this.copyMeterPlanNum = copyMeterPlanNum;
    }

    public String getStatusDsr() {
        return statusDsr;
    }

    public void setStatusDsr(String statusDsr) {
        this.statusDsr = statusDsr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyMeterType() {
        return copyMeterType;
    }

    public void setCopyMeterType(String copyMeterType) {
        this.copyMeterType = copyMeterType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterPlanVoForList{" +
                "id='" + id + '\'' +
                ", copyMeterPlanNum='" + copyMeterPlanNum + '\'' +
                ", description='" + description + '\'' +
                ", copyMeterType='" + copyMeterType + '\'' +
                ", copyMeterTypeName='" + copyMeterTypeName + '\'' +
                ", status='" + status + '\'' +
                ", statusDsr='" + statusDsr + '\'' +
                ", createDate=" + createDate +
                ", collect=" + collect +
                '}';
    }
}
