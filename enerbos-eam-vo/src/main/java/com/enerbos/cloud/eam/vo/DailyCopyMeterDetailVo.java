package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "抄表管理详细实体")
public class DailyCopyMeterDetailVo extends EAMBaseFilterVo implements Serializable {


    @ApiModelProperty(value = "唯一标识")
    private String id;

    /**
     * 抄表单
     */
    @ApiModelProperty(value = "抄表单ID")
    private String copyMeterId;

    /**
     * 仪表台帐id
     */
    @ApiModelProperty(value = "仪表台帐id")
    private String meterId;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("设备编码")
    private String code;

    @ApiModelProperty("位置名称")
    private String locationName;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("类型")
    private String typeName;
    //meter 相关字段


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;


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


    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "设备唯一编码")
    private String qrCodeNum;

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

    public String getCopyMeterId() {
        return copyMeterId;
    }

    public void setCopyMeterId(String copyMeterId) {
        this.copyMeterId = copyMeterId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }


    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }


    public Date getThisDate() {
        return thisDate;
    }

    public void setThisDate(Date thisDate) {
        this.thisDate = thisDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getQrCodeNum() {
        return qrCodeNum;
    }

    public void setQrCodeNum(String qrCodeNum) {
        this.qrCodeNum = qrCodeNum;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    public Double getLastNum() {
        return lastNum;
    }

    public void setLastNum(Double lastNum) {
        this.lastNum = lastNum;
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

    public Date getThisEnteringDate() {
        return thisEnteringDate;
    }

    public void setThisEnteringDate(Date thisEnteringDate) {
        this.thisEnteringDate = thisEnteringDate;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterDetailVo{" +
                "id='" + id + '\'' +
                ", copyMeterId='" + copyMeterId + '\'' +
                ", meterId='" + meterId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", locationName='" + locationName + '\'' +
                ", unit='" + unit + '\'' +
                ", typeName='" + typeName + '\'' +
                ", updateDate=" + updateDate +
                ", lastNum=" + lastNum +
                ", lastDate=" + lastDate +
                ", lastEnteringDate=" + lastEnteringDate +
                ", thisNum=" + thisNum +
                ", thisDate=" + thisDate +
                ", thisEnteringDate=" + thisEnteringDate +
                ", createUser='" + createUser + '\'' +
                ", qrCodeNum='" + qrCodeNum + '\'' +
                '}';
    }
}
