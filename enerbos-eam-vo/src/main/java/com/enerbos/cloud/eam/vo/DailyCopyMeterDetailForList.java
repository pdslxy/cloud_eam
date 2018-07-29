package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class DailyCopyMeterDetailForList implements Serializable {

    @ApiModelProperty(value = "唯一标识")
    private String id;

    /**
     * 抄表工单id
     */
    @ApiModelProperty(value = "抄表工单id")
    private String copyMeterId;

    /**
     * 抄表工单描述
     */
    @ApiModelProperty(value = "抄表工单描述")
    private String copyMeterDesc;

    /**
     * 抄表工单描述
     */
    @ApiModelProperty(value = "抄表工单编码")
    private String copyMeterNum;

    /**
     * 抄表工单状态
     */
    @ApiModelProperty(value = "抄表工单状态")
    private String status;

    /**
     * 仪表台帐id
     */
    @ApiModelProperty(value = "抄表工单状态描述")
    private String statusDesc;

    /**
     * 仪表台帐id
     */
    @ApiModelProperty(value = "仪表台帐id")
    private String meterId;

    @ApiModelProperty("设备编码")
    private String meterCode;

    @ApiModelProperty("设备名称")
    private String meterName;

    @ApiModelProperty(value = "位置名称")
    private String locationName;



    @ApiModelProperty("设备类型")
    private String meterType;

    /**
     * 价格单位，如： 元/吨   元/度
     */
    @ApiModelProperty(value = "价格单位(不能超过10个字节)")
    private String priceUnit;

    /**
     * 日常价格
     */
    @ApiModelProperty(value = "日常价格(不能超过200个字节)")
    private double price;


    @ApiModelProperty(value = "上次读数")
    private Double lastNum;


    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "上次读数时间")
    private Date lastDate;

    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "上次录入")
    private Date lastEnteringDate;

    @ApiModelProperty(value = "本次读数")
    private Double thisNum;


    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "本次读数时间")
    private Date thisDate;

    /**
     * 抄表时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "本次录入时间")
    private Date thisEnteringDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCopyMeterId() {
        return copyMeterId;
    }

    public void setCopyMeterId(String copyMeterId) {
        this.copyMeterId = copyMeterId;
    }

    public String getCopyMeterDesc() {
        return copyMeterDesc;
    }

    public void setCopyMeterDesc(String copyMeterDesc) {
        this.copyMeterDesc = copyMeterDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCopyMeterNum() {
        return copyMeterNum;
    }

    public void setCopyMeterNum(String copyMeterNum) {
        this.copyMeterNum = copyMeterNum;
    }

    public Double getLastNum() {
        return lastNum;
    }

    public void setLastNum(Double lastNum) {
        this.lastNum = lastNum;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getLastEnteringDate() {
        return lastEnteringDate;
    }

    public void setLastEnteringDate(Date lastEnteringDate) {
        this.lastEnteringDate = lastEnteringDate;
    }

    public Double getThisNum() {
        return thisNum;
    }

    public void setThisNum(Double thisNum) {
        this.thisNum = thisNum;
    }

    public Date getThisDate() {
        return thisDate;
    }

    public void setThisDate(Date thisDate) {
        this.thisDate = thisDate;
    }

    public Date getThisEnteringDate() {
        return thisEnteringDate;
    }

    public void setThisEnteringDate(Date thisEnteringDate) {
        this.thisEnteringDate = thisEnteringDate;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterDetailForList{" +
                "id='" + id + '\'' +
                ", copyMeterId='" + copyMeterId + '\'' +
                ", copyMeterDesc='" + copyMeterDesc + '\'' +
                ", copyMeterNum='" + copyMeterNum + '\'' +
                ", status='" + status + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", meterId='" + meterId + '\'' +
                ", meterCode='" + meterCode + '\'' +
                ", meterName='" + meterName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", meterType='" + meterType + '\'' +
                ", priceUnit='" + priceUnit + '\'' +
                ", price=" + price +
                ", lastNum=" + lastNum +
                ", lastDate=" + lastDate +
                ", lastEnteringDate=" + lastEnteringDate +
                ", thisNum=" + thisNum +
                ", thisDate=" + thisDate +
                ", thisEnteringDate=" + thisEnteringDate +
                '}';
    }
}

