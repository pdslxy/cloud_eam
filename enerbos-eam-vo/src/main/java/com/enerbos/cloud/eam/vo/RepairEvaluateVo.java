package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/19.
 */
@ApiModel(value = "小程序对应的项目vo")
public class RepairEvaluateVo {

    private static final long serialVersionUID = 3894256874881718604L;

    @ApiModelProperty(value = "评价ID(新增不需要传值)")
    private String id;


    /**
     * 维修质量
     */
    @ApiModelProperty(value = "维修质量(新增不需要传值)")
    private String repairQuality;


    /**
     * 维修态度
     */
    @ApiModelProperty(value = "维修态度(新增不需要传值)")
    private String repairAttitude;


    /**
     * 评价说明
     */
    @ApiModelProperty(value = "评价说明(新增不需要传值)")
    private String description;
    
    /**
     * 人员 id
     */
    @ApiModelProperty(value = "人员 id(最长不能超过36个字节)")
    private String personId;

    /**
     * 站点 id
     */
    @ApiModelProperty(value = "站点 id(最长不能超过36个字节)")
    private String siteId;


    /**
     * 报修工单id
     */
    @ApiModelProperty(value = "报修工单id(最长不能超过36个字节)")
    private String repairOrderId;

    /**
     * 照片地址
     */
    @ApiModelProperty(value = "照片地址(新增不需要传值)")
    private String imgPath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepairQuality() {
        return repairQuality;
    }

    public void setRepairQuality(String repairQuality) {
        this.repairQuality = repairQuality;
    }

    public String getRepairAttitude() {
        return repairAttitude;
    }

    public void setRepairAttitude(String repairAttitude) {
        this.repairAttitude = repairAttitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(String repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    @Override
    public String toString() {
        return "RepairEvaluate{" +
                "id='" + id + '\'' +
                ", repairQuality='" + repairQuality + '\'' +
                ", repairAttitude='" + repairAttitude + '\'' +
                ", description='" + description + '\'' +
                ", personId='" + personId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", repairOrderId='" + repairOrderId + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
