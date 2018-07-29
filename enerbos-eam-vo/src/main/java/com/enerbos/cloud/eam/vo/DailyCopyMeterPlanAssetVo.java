package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/11/30
 * @Description
 */
@ApiModel(value = "抄表计划设备Vo")
public class DailyCopyMeterPlanAssetVo implements Serializable {

    private static final long serialVersionUID = 8237136577340462523L;

    @ApiModelProperty("id 主键")
    private String id;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("设备编码")
    private String code;

    @ApiModelProperty("位置名称")
    private String locationName;

    @ApiModelProperty("位置编码")
    private String locationCode;

    @ApiModelProperty("分类信息名称")
    private String classificationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterPlanAssetVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationCode='" + locationCode + '\'' +
                ", classificationName='" + classificationName + '\'' +
                '}';
    }
}
