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
 * @date 2017/7/17
 * @Description
 */
@ApiModel(value = "巡检项", description = "巡检项对应的Vo")
public class PatrolTermVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检项ID")
    private String id;

    @ApiModelProperty(value = "巡检项描述")
    private String description;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "序号")
    private Integer step;

    @ApiModelProperty(value = "关联的巡检点ID")
    private String patrolPointId;
    @ApiModelProperty("状态：0-删除，1-新增")
    private Integer status;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(String patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolTermVo{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", step=" + step +
                ", patrolPointId='" + patrolPointId + '\'' +
                ", status=" + status +
                '}';
    }
}
