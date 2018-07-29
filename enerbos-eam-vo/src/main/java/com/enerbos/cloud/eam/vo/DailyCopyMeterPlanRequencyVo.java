package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * @date 2017年11月16日 16:26:58
 * @Description 频率
 */
@ApiModel(value = "抄表频率")
public class DailyCopyMeterPlanRequencyVo extends EAMBaseFilterVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7983360432330979802L;

    @ApiModelProperty(value = "唯一标识")
    private String id;
    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    private String cycle;

    /**
     * 周期单位
     */
    @ApiModelProperty(value = "周期单位")
    private String cycleUnit;



    /**
     * 下次生成日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "next_create_date", nullable = true, length = 20)
    private Date nextCreateDate;

    /**
     * 注意事项
     */
    @ApiModelProperty(value = "注意事项")
    private String mark;

    /**
     * 抄表计划
     */
    @ApiModelProperty(value = "抄表时间")
    private String copyMeterPlanId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "创建人")
    private String createUser;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getCycleUnit() {
        return cycleUnit;
    }

    public void setCycleUnit(String cycleUnit) {
        this.cycleUnit = cycleUnit;
    }



    public Date getNextCreateDate() {
        return nextCreateDate;
    }

    public void setNextCreateDate(Date nextCreateDate) {
        this.nextCreateDate = nextCreateDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCopyMeterPlanId() {
        return copyMeterPlanId;
    }

    public void setCopyMeterPlanId(String copyMeterPlanId) {
        this.copyMeterPlanId = copyMeterPlanId;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterPlanRequencyVo{" +
                "id='" + id + '\'' +
                ", cycle='" + cycle + '\'' +
                ", cycleUnit='" + cycleUnit + '\'' +
                ", nextCreateDate=" + nextCreateDate +
                ", mark=" + mark +
                ", copyMeterPlanId='" + copyMeterPlanId + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
