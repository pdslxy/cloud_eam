package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月27日
 * @Description APP 版本信息
 */
@ApiModel(value = "APP 版本信息VO", description = "APP 版本信息VO")
public class AppVersionVo {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String appversionid;

    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private String version;

    /**
     * 发布日期
     */
    @ApiModelProperty("发布日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releasedate;

    /**
     * 更新日志
     */
    @ApiModelProperty("更新日志")
    private String releasenote;

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
    @ApiModelProperty("下载地址")
    private String downloadurl;

    /**
     * 是否必须升级到该版本
     */
    @ApiModelProperty("是否必须升级到该版本")
    private Boolean mustbe;

    public String getAppversionid() {
        return appversionid;
    }

    public void setAppversionid(String appversionid) {
        this.appversionid = appversionid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public String getReleasenote() {
        return releasenote;
    }

    public void setReleasenote(String releasenote) {
        this.releasenote = releasenote;
    }

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

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public Boolean getMustbe() {
        return mustbe;
    }

    public void setMustbe(Boolean mustbe) {
        this.mustbe = mustbe;
    }

    @Override
    public String toString() {
        return "AppVersionVo{" +
                "appversionid='" + appversionid + '\'' +
                ", version='" + version + '\'' +
                ", releasedate=" + releasedate +
                ", releasenote='" + releasenote + '\'' +
                ", platform='" + platform + '\'' +
                ", platformversion='" + platformversion + '\'' +
                ", downloadurl='" + downloadurl + '\'' +
                ", mustbe=" + mustbe +
                '}';
    }
}
