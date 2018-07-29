package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/4
 * @Description
 */
@ApiModel(value = "合同", description = "合同对应的Vo")
public class ContractForFilterVo extends EAMBaseFilterVo {

    @ApiModelProperty(value = "合同ID(新增不需要传值)")
    private String id;


    @ApiModelProperty(value = "合同编码")
    private String contractNum;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "合同状态")
    private List<String> status;

    @ApiModelProperty(value = "合同类型")
    private List<String> contractType;

    @ApiModelProperty(value = "模糊查询关键词")
    private String words;

    @ApiModelProperty("排序参数")
    private String sorts;
    @ApiModelProperty("组织 id")
    private String orgId;
    @ApiModelProperty("站点 id")
    private String siteId;

    @ApiModelProperty(value = "收藏")
    private boolean collect;

    @ApiModelProperty(value = "用户Id")
    private String collectPersonId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getContractType() {
        return contractType;
    }

    public void setContractType(List<String> contractType) {
        this.contractType = contractType;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String getSorts() {
        return sorts;
    }

    @Override
    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getCollectPersonId() {
        return collectPersonId;
    }

    public void setCollectPersonId(String collectPersonId) {
        this.collectPersonId = collectPersonId;
    }

    @Override
    public String toString() {
        return "ContractForFilterVo{" +
                "id='" + id + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", contractType=" + contractType +
                ", words='" + words + '\'' +
                ", sorts='" + sorts + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", collect=" + collect +
                ", collectPersonId='" + collectPersonId + '\'' +
                '}';
    }
}
