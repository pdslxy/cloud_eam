package com.enerbos.cloud.eam.vo;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;
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
@ApiModel(value = "巡检点台账", description = "巡检点台账对应的Vo")
public class PatrolRecordTermVoForFilter extends EAMBaseFilterVo {
    @ApiModelProperty(value = "巡检项ID")
    private String id;

    @ApiModelProperty (value = "关联的巡检点ID")
    private String patrolPointId;

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "PatrolRecordTermVoForFilter{" +
                "id='" + id + '\'' +
                ", patrolPointId='" + patrolPointId + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
