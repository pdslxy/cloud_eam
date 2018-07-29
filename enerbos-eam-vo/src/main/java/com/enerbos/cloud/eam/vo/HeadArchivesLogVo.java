package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Enerbos on 2016/10/17.
 */
@ApiModel(value = "档案日志管理")
public class HeadArchivesLogVo extends EAMBaseFilterVo {

    /**
     * id
     *
     */
    @ApiModelProperty(value = "档案日志ID(新增不需要传值)")
    private String id;
    
    
    /**
     * 档案id
     */
    @ApiModelProperty(value = "档案id")
    private String archivesId;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String principal;
    
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期(新增、修改不用传值)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织")
    private String siteId;

    /**
     * 站点id
     */
    @ApiModelProperty(value = "站点")
    private String orgId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "HeadArchivesLog{" +
                "id='" + id + '\'' +
                ", archivesId='" + archivesId + '\'' +
                ", principal='" + principal + '\'' +
                ", createDate='" + createDate + '\'' +
                ", description='" + description + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
