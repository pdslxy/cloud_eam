package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年4月1日 上午10:49:05
 * @Description 物资发放查询条件实体
 */

@ApiModel(value = "物资发放查询条件实体")
public class MaterialReleaseVoForFilter extends EAMBaseFilterVo implements Serializable {

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private List<String> releaseType;// EAM_ALNDOMAIN

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private List<String> status;// EAM_ALNDOMAIN

    /**
     * 原库房
     */
    @ApiModelProperty(value = "原库房")
    private String fromStoreroomId; // EAM_STOREROOM

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String consumingPeople;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建开始-时间")
    private String startDate;

    @ApiModelProperty(value = "创建结束时间")
    private String endDate;

    private Boolean collect;

    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

    public List<String> getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(List<String> releaseType) {
        this.releaseType = releaseType;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getFromStoreroomId() {
        return fromStoreroomId;
    }

    public void setFromStoreroomId(String fromStoreroomId) {
        this.fromStoreroomId = fromStoreroomId;
    }

    public String getConsumingPeople() {
        return consumingPeople;
    }

    public void setConsumingPeople(String consumingPeople) {
        this.consumingPeople = consumingPeople;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

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

    @Override
    public String toString() {
        return "MaterialReleaseVoForFilter{" +
                "releaseType=" + releaseType +
                ", status=" + status +
                ", fromStoreroomId='" + fromStoreroomId + '\'' +
                ", consumingPeople='" + consumingPeople + '\'' +
                ", createUser='" + createUser + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", collect=" + collect +
                ", word='" + word + '\'' +
                '}';
    }
}
