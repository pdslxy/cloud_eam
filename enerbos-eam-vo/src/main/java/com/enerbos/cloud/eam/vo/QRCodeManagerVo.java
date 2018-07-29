package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
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
 * @Description 二维码管理Vo
 */
@ApiModel(value = "二维码管理", description = "二维码管理对应Vo")
public class QRCodeManagerVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 二维码管理编码
     */
    @ApiModelProperty(value = "二维码管理编码")
    private String quickResponseCodeNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 状态，活动/不活动
     */
    @ApiModelProperty(value = "状态，true活动/false不活动")
    private Boolean status;

    /**
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

    /**
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点名称")
    private String siteName;

    /**
     * 所属组织编码
     */
    @ApiModelProperty(value = "所属组织编码")
    private String orgId;

    /**
     * 所属组织编码
     */
    @ApiModelProperty(value = "所属组织名称")
    private String orgName;

    /**
     * 应用程序，功能模块
     */
    @ApiModelProperty(value = "应用程序，功能模块")
    private String applicationId;

    /**
     * 应用程序的值
     */
    @ApiModelProperty(value = "应用程序值，用于区分应用程序，固定值")
    private String applicationValue;

    /**
     * 应用程序，功能模块
     */
    @ApiModelProperty(value = "应用程序名称")
    private String applicationName;

    /**
     * 是否有数据更新
     */
    @ApiModelProperty(value = "是否有数据更新")
    private Boolean dataUpdate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @Length(max = 36, message = "不能超过36个字符")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 上次生成时间
     */
    @ApiModelProperty(value = "上次生成时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastGenerateDate;

    /**
     * 二维码前缀编码
     */
    @ApiModelProperty(value = "二维码前缀编码")
    private String prefixQRCode;

    @ApiModelProperty(value = "所选应用程序属性")
    private List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList;

    @ApiModelProperty(value = "删除所选应用程序属性")
    private List<String> deleteQRCodeApplicationPropertyVoList;

    @ApiModelProperty(value = "功能对应的类方法路径",hidden = true)
    private String className;

    @ApiModelProperty(value = "查询数据方法名",hidden = true)
    private String methodName;

    @ApiModelProperty(value = "更新是否已更新字段方法名,状态更新为false",hidden = true)
    private String updateDataUpdateMethodName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuickResponseCodeNum() {
        return quickResponseCodeNum;
    }

    public void setQuickResponseCodeNum(String quickResponseCodeNum) {
        this.quickResponseCodeNum = quickResponseCodeNum;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Boolean getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(Boolean dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<QRCodeApplicationPropertyVo> getQRCodeApplicationPropertyVoList() {
        return QRCodeApplicationPropertyVoList;
    }

    public void setQRCodeApplicationPropertyVoList(List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList) {
        this.QRCodeApplicationPropertyVoList = QRCodeApplicationPropertyVoList;
    }

    public List<String> getDeleteQRCodeApplicationPropertyVoList() {
        return deleteQRCodeApplicationPropertyVoList;
    }

    public void setDeleteQRCodeApplicationPropertyVoList(List<String> deleteQRCodeApplicationPropertyVoList) {
        this.deleteQRCodeApplicationPropertyVoList = deleteQRCodeApplicationPropertyVoList;
    }

    public Date getLastGenerateDate() {
        return lastGenerateDate;
    }

    public void setLastGenerateDate(Date lastGenerateDate) {
        this.lastGenerateDate = lastGenerateDate;
    }

    public String getApplicationValue() {
        return applicationValue;
    }

    public void setApplicationValue(String applicationValue) {
        this.applicationValue = applicationValue;
    }

    public String getPrefixQRCode() {
        return prefixQRCode;
    }

    public void setPrefixQRCode(String prefixQRCode) {
        this.prefixQRCode = prefixQRCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUpdateDataUpdateMethodName() {
        return updateDataUpdateMethodName;
    }

    public void setUpdateDataUpdateMethodName(String updateDataUpdateMethodName) {
        this.updateDataUpdateMethodName = updateDataUpdateMethodName;
    }

    @Override
    public String toString() {
        return "QRCodeManagerVo{" +
                "id='" + id + '\'' +
                ", quickResponseCodeNum='" + quickResponseCodeNum + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", statusDate=" + statusDate +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", applicationValue='" + applicationValue + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", dataUpdate=" + dataUpdate +
                ", createUser='" + createUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", lastGenerateDate=" + lastGenerateDate +
                ", prefixQRCode='" + prefixQRCode + '\'' +
                ", QRCodeApplicationPropertyVoList=" + QRCodeApplicationPropertyVoList +
                ", deleteQRCodeApplicationPropertyVoList=" + deleteQRCodeApplicationPropertyVoList +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", updateDataUpdateMethodName='" + updateDataUpdateMethodName + '\'' +
                '}';
    }
}
