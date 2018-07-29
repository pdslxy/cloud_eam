package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DailyCopyMeterPlanFilterVo extends  EAMBaseFilterVo{

    @ApiModelProperty(value = "是否收藏")
    private Boolean collect;

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

    @ApiModelProperty(value = "巡检状态")
    private List<String> status;

    @ApiModelProperty(value = "巡检类型")
    private List<String> type;

    @ApiModelProperty(value = "用户Id")
    private String personId;

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "DailyCopyMeterPlanFilterVo{" +
                "collect=" + collect +
                ", word='" + word + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", personId='" + personId + '\'' +
                '}';
    }
}
