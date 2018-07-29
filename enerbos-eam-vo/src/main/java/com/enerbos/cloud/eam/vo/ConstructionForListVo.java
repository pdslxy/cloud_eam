package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月5日
 * @Description 施工单列表Vo
 */
@ApiModel(value = "施工单列表", description = "施工单列表Vo")
public class  ConstructionForListVo implements Serializable {


    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 施工单编码
     */
    @ApiModelProperty("施工单编码")
    private String constructionNum;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 状态
     * 新增、执行中、评价中、完成
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 状态
     * 新增、执行中、评价中、完成
     */
    @ApiModelProperty("状态描述")
    private String statusDsr;

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
     * 所属站点编码
     */
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;

    /**
     * 所属组织编码
     */
    @ApiModelProperty(value = "所属组织编码")
    private String orgId;

    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "是否收藏")
    private boolean collect;

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

    public String getStatusDsr() {
        return statusDsr;
    }

    public void setStatusDsr(String statusDsr) {
        this.statusDsr = statusDsr;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    @Override
    public String toString() {
        return "ConstructionForListVo{" +
                "id='" + id + '\'' +
                ", constructionNum='" + constructionNum + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", statusDsr='" + statusDsr + '\'' +
                ", supervisionId='" + supervisionId + '\'' +
                ", supervisionName='" + supervisionName + '\'' +
                ", contractId='" + contractId + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", contractDesc='" + contractDesc + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createDate=" + createDate +
                ", collect=" + collect +
                '}';
    }
}