package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月27日
 * @Description APP 版本信息条件过滤
 */
@ApiModel(value = "APP 版本信息条件过滤VO", description = "APP 版本信息条件过滤VO")
public class AppVersionForFilterVo {

    /**
     * ios,android
     */
    @ApiModelProperty("ios,android")
    private String platform;

    /**
     * 终端系统版本
     */
    @ApiModelProperty("终端系统版本")
    private String platformversion;

    /**
     * 下载地址
     */
    @ApiModelProperty("Eam Air App 当前版本号")
    private String currVersion;

    /**
     * 发布日期
     */
    @ApiModelProperty(value = "发布日期",hidden = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releasedate;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformversion() {
        return platformversion;
    }

    public void setPlatformversion(String platformversion) {
        this.platformversion = platformversion;
    }

    public String getCurrVersion() {
        return currVersion;
    }

    public void setCurrVersion(String currVersion) {
        this.currVersion = currVersion;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    @Override
    public String toString() {
        return "AppVersionForFilterVo{" +
                "platform='" + platform + '\'' +
                ", platformversion='" + platformversion + '\'' +
                ", currVersion='" + currVersion + '\'' +
                ", releasedate=" + releasedate +
                '}';
    }
}
