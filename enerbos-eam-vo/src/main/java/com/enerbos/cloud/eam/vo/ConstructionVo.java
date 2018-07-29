package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月9日
 * @Description 施工单实体
 */
@ApiModel(value = "施工单", description = "施工单对应的Vo")
public class ConstructionVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 施工单编码
     */
    @ApiModelProperty(value = "施工单编码",required = true)
    @NotBlank(message = "施工单编码不能为空")
    private String constructionNum;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述",required = true)
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 状态
     * 新增、执行中、评价中、完成
     */
    @ApiModelProperty(value = "状态",required = true)
    @NotBlank(message = "状态不能为空")
    private String status;

    /**
     * 监理单位负责人id
     */
    @ApiModelProperty("监理单位负责人id")
    private String supervisionId;

    /**
     * 监理单位负责人
     */
    @ApiModelProperty("监理单位负责人")
    private String supervisionName;

    /**
     * 监理单位负责人电话
     */
    @ApiModelProperty("监理单位负责人电话")
    private String supervisionMobile;

    /**
     * 安全员,手填
     */
    @ApiModelProperty("安全员,手填")
    private String securityOfficerId;

    /**
     * 关联的合同id
     */
    @ApiModelProperty("关联的合同id")
    private String contractId;

    /**
     * 关联的合同编码
     */
    @ApiModelProperty("关联的合同编码")
    private String contractNum;

    /**
     * 关联的合同描述
     */
    @ApiModelProperty("关联的合同描述")
    private String contractDesc;

    /**
     * 监管情况说明
     */
    @ApiModelProperty("监管情况说明")
    private String superviseDesc;

    /**
     * 施工天气,晴、阴、雨、雪,可通过constructWeather获取域值
     */
    @ApiModelProperty("施工天气,晴、阴、雨、雪,可通过constructWeather获取域值")
    private String constructWeather;

    /**
     * 有无动火
     */
    @ApiModelProperty("有无动火")
    private Boolean isFire;

    /**
     * 有无登高
     */
    @ApiModelProperty("有无登高")
    private Boolean isClimbUp;

    /**
     * 施工人数：手填，整数类型，需在前端做正则验证
     */
    @ApiModelProperty("施工人数：手填，整数类型，需在前端做正则验证")
    private String constructionPersonCount;

    /**
     * 确认人id
     */
    @ApiModelProperty("确认人id")
    private String confirmPersonId;

    /**
     * 确认人
     */
    @ApiModelProperty("确认人")
    private String confirmPersonName;

    /**
     * 确认时间
     */
    @ApiModelProperty("确认时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmDate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String processInstanceId;


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
     * 提报人id
     */
    @ApiModelProperty(value = "提报人id",required = true)
//    @NotBlank(message = "提报人不能为空")
    private String reportId;

    /**
     * 提报人
     */
    @ApiModelProperty("提报人")
    private String reportName;

    /**
     * 提报日期
     */
    @ApiModelProperty("提报日期")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人",required = true)
    @Length(max = 36, message = "不能超过36个字符")
    private String createUser;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人名")
    @Length(max = 36, message = "不能超过36个字符")
    private String createUserName;

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

    @ApiModelProperty(value = "执行记录")
    private List<WorkFlowImpleRecordVo> impleRecordVoVoList;

    @ApiModelProperty(value = "监管记录列表，保存时只回传已修改数据")
    private List<ConstructionSuperviseVo> constructionSuperviseVoList;

    @ApiModelProperty(value = "删除监管记录ID")
    private List<String> deleteConstructionSuperviseVoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConstructionNum() {
        return constructionNum;
    }

    public void setConstructionNum(String constructionNum) {
        this.constructionNum = constructionNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupervisionId() {
        return supervisionId;
    }

    public void setSupervisionId(String supervisionId) {
        this.supervisionId = supervisionId;
    }

    public String getSupervisionName() {
        return supervisionName;
    }

    public void setSupervisionName(String supervisionName) {
        this.supervisionName = supervisionName;
    }

    public String getSupervisionMobile() {
        return supervisionMobile;
    }

    public void setSupervisionMobile(String supervisionMobile) {
        this.supervisionMobile = supervisionMobile;
    }

    public String getSecurityOfficerId() {
        return securityOfficerId;
    }

    public void setSecurityOfficerId(String securityOfficerId) {
        this.securityOfficerId = securityOfficerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getContractDesc() {
        return contractDesc;
    }

    public void setContractDesc(String contractDesc) {
        this.contractDesc = contractDesc;
    }

    public String getSuperviseDesc() {
        return superviseDesc;
    }

    public void setSuperviseDesc(String superviseDesc) {
        this.superviseDesc = superviseDesc;
    }

    public String getConstructWeather() {
        return constructWeather;
    }

    public void setConstructWeather(String constructWeather) {
        this.constructWeather = constructWeather;
    }

    public Boolean getFire() {
        return isFire;
    }

    public void setFire(Boolean fire) {
        isFire = fire;
    }

    public Boolean getClimbUp() {
        return isClimbUp;
    }

    public void setClimbUp(Boolean climbUp) {
        isClimbUp = climbUp;
    }

    public String getConstructionPersonCount() {
        return constructionPersonCount;
    }

    public void setConstructionPersonCount(String constructionPersonCount) {
        this.constructionPersonCount = constructionPersonCount;
    }

    public String getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(String confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
    }

    public String getConfirmPersonName() {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName) {
        this.confirmPersonName = confirmPersonName;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<ConstructionSuperviseVo> getConstructionSuperviseVoList() {
        return constructionSuperviseVoList;
    }

    public void setConstructionSuperviseVoList(List<ConstructionSuperviseVo> constructionSuperviseVoList) {
        this.constructionSuperviseVoList = constructionSuperviseVoList;
    }

    public List<String> getDeleteConstructionSuperviseVoList() {
        return deleteConstructionSuperviseVoList;
    }

    public void setDeleteConstructionSuperviseVoList(List<String> deleteConstructionSuperviseVoList) {
        this.deleteConstructionSuperviseVoList = deleteConstructionSuperviseVoList;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public List<WorkFlowImpleRecordVo> getImpleRecordVoVoList() {
        return impleRecordVoVoList;
    }

    public void setImpleRecordVoVoList(List<WorkFlowImpleRecordVo> impleRecordVoVoList) {
        this.impleRecordVoVoList = impleRecordVoVoList;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "ConstructionVo{" +
                "id='" + id + '\'' +
                ", constructionNum='" + constructionNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", supervisionId='" + supervisionId + '\'' +
                ", supervisionName='" + supervisionName + '\'' +
                ", supervisionMobile='" + supervisionMobile + '\'' +
                ", securityOfficerId='" + securityOfficerId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", contractDesc='" + contractDesc + '\'' +
                ", superviseDesc='" + superviseDesc + '\'' +
                ", constructWeather='" + constructWeather + '\'' +
                ", isFire=" + isFire +
                ", isClimbUp=" + isClimbUp +
                ", constructionPersonCount='" + constructionPersonCount + '\'' +
                ", confirmPersonId='" + confirmPersonId + '\'' +
                ", confirmPersonName='" + confirmPersonName + '\'' +
                ", confirmDate=" + confirmDate +
                ", remark='" + remark + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", reportId='" + reportId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportDate=" + reportDate +
                ", createUser='" + createUser + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", impleRecordVoVoList=" + impleRecordVoVoList +
                ", constructionSuperviseVoList=" + constructionSuperviseVoList +
                ", deleteConstructionSuperviseVoList=" + deleteConstructionSuperviseVoList +
                '}';
    }
}
