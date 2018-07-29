package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年07月31日
 * @Description 二维码管理模糊搜索Vo
 */
@ApiModel(value = "二维码管理模糊搜索Vo", description = "二维码管理模糊搜索的Vo")
public class QRCodeManagerForFilterVo extends EAMBaseFilterVo {

    /**
     * 状态，活动/不活动
     */
    @ApiModelProperty(value = "状态，true活动/false不活动")
    private Boolean status;

    /**
     * 应用程序，功能模块
     */
    @ApiModelProperty(value = "应用程序，功能模块")
    private String applicationId;

    /**
     * 是否有数据更新
     */
    @ApiModelProperty(value = "是否有数据更新")
    private Boolean dataUpdate;

    /**
     * 上次生成时间,过滤开始时间
     */
    @ApiModelProperty(value = "上次生成时间,过滤开始时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastGenerateStartDate;

    /**
     * 上次生成时间,过滤结束时间
     */
    @ApiModelProperty(value = "上次生成时间,过滤结束时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastGenerateEndDate;

    @ApiModelProperty(value = "模糊搜索关键词")
    private String words;

    @ApiModelProperty(value = "模糊搜索关键词，分词", hidden = true)
    private List<String> wordsList;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Boolean getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(Boolean dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public Date getLastGenerateStartDate() {
        return lastGenerateStartDate;
    }

    public void setLastGenerateStartDate(Date lastGenerateStartDate) {
        this.lastGenerateStartDate = lastGenerateStartDate;
    }

    public Date getLastGenerateEndDate() {
        return lastGenerateEndDate;
    }

    public void setLastGenerateEndDate(Date lastGenerateEndDate) {
        this.lastGenerateEndDate = lastGenerateEndDate;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public List<String> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<String> wordsList) {
        this.wordsList = wordsList;
    }

    @Override
    public String toString() {
        return "QRCodeManagerForFilterVo{" +
                "status=" + status +
                ", applicationId='" + applicationId + '\'' +
                ", dataUpdate=" + dataUpdate +
                ", lastGenerateStartDate=" + lastGenerateStartDate +
                ", lastGenerateEndDate=" + lastGenerateEndDate +
                ", words='" + words + '\'' +
                ", wordsList=" + wordsList +
                '}';
    }
}
