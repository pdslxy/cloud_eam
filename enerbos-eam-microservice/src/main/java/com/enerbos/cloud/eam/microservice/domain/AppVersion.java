package com.enerbos.cloud.eam.microservice.domain;

import org.hibernate.annotations.GenericGenerator;
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
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_appversion")
public class AppVersion {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36,name = "appversionid")
    private String appversionid;

    /**
     * 版本号
     */
    @Column
    private String version;

    /**
     * 发布日期
     */
    @Column
    private Date releasedate;

    /**
     * 更新日志
     */
    @Column
    private String releasenote;

    /**
     * ios,android
     */
    @Column
    private String platform;

    /**
     * 终端系统版本
     */
    @Column
    private String platformversion;

    /**
     * 下载地址
     */
    @Column
    private String downloadurl;

    /**
     * 是否必须升级到该版本
     */
    @Column
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
        return "AppVersion{" +
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
