package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/22
 * @Description
 */
public class PatrolRecordTermForOrderSaveVo {

    @ApiModelProperty(value = "巡检记录id(新增不需要传值)")
    private String id;

    @ApiModelProperty(value = "巡检项描述(新增不需要传值)")
    private String patrolTermDsr;

    @ApiModelProperty(value = "巡检点ID(新增不需要传值)")
    private String patrolPointId;

    @ApiModelProperty(value = "是否正常(新增不需要传值)")
    private String isqualified;

    @ApiModelProperty(value = "异常描述(新增不需要传值)")
    private String exceptionDsr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(String patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
    }

    public String getExceptionDsr() {
        return exceptionDsr;
    }

    public void setExceptionDsr(String exceptionDsr) {
        this.exceptionDsr = exceptionDsr;
    }

    public String getPatrolTermDsr() {
        return patrolTermDsr;
    }

    public void setPatrolTermDsr(String patrolTermDsr) {
        this.patrolTermDsr = patrolTermDsr;
    }

    @Override
    public String toString() {
        return "PatrolRecordTermForOrderSaveVo{" +
                "id='" + id + '\'' +
                ", patrolTermDsr='" + patrolTermDsr + '\'' +
                ", patrolPointId='" + patrolPointId + '\'' +
                ", isqualified='" + isqualified + '\'' +
                ", exceptionDsr='" + exceptionDsr + '\'' +
                '}';
    }
}
