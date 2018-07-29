package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@ApiModel(value = "巡检点与巡检路线对应关系", description = "巡检点与巡检路线对应关系的Vo")
public class PatrolRoutePointVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对应关系ID")
    private String id;

    @ApiModelProperty(value = "巡检路线ID")
    private String patrolrouteid;

    @ApiModelProperty(value = "巡检点ID")
    private String patrolpointid;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "序号")
    private String step;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatrolrouteid() {
        return patrolrouteid;
    }

    public void setPatrolrouteid(String patrolrouteid) {
        this.patrolrouteid = patrolrouteid;
    }

    public String getPatrolpointid() {
        return patrolpointid;
    }

    public void setPatrolpointid(String patrolpointid) {
        this.patrolpointid = patrolpointid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolRoutePointVo{" +
                "id='" + id + '\'' +
                ", patrolrouteid='" + patrolrouteid + '\'' +
                ", patrolpointid='" + patrolpointid + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", remark='" + remark + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
