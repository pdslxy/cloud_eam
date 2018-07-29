package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "巡检点台账", description = "巡检点台账对应的Vo")
public class PatrolTermVoForFilter extends EAMBaseFilterVo {

    @ApiModelProperty(value = "巡检项ID")
    private String id;

    @ApiModelProperty(value = "巡检项描述")
    private String description;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "关联的巡检点ID")
    private String patrolPointId;

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

    @ApiModelProperty("排序参数")
    private String sorts;

    @ApiModelProperty(value = "序号")
    private String step;

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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(String patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    @Override
    public String getSorts() {
        return sorts;
    }

    @Override
    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "PatrolTermVoForFilter{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", patrolPointId='" + patrolPointId + '\'' +
                ", word='" + word + '\'' +
                ", sorts='" + sorts + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
