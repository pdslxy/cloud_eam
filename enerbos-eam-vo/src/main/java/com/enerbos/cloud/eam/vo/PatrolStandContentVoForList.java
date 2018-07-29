package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "巡检标准内容实体")
public class PatrolStandContentVoForList {


    @ApiModelProperty(value = "ID标识")
    private String id;


    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 检查标准
     */
    @ApiModelProperty(value = "检查标准")
    private String checkStand;


    /**
     * 巡检标准id
     */
    @ApiModelProperty(value = " 巡检标准id")
    private String patrolStandId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckStand() {
        return checkStand;
    }

    public void setCheckStand(String checkStand) {
        this.checkStand = checkStand;
    }

    public String getPatrolStandId() {
        return patrolStandId;
    }

    public void setPatrolStandId(String patrolStandId) {
        this.patrolStandId = patrolStandId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PatrolStandContentVoForList{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", checkStand='" + checkStand + '\'' +
                ", patrolStandId='" + patrolStandId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
