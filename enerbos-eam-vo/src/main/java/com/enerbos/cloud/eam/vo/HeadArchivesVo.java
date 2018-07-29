package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Enerbos on 2016/10/17.
 */
@ApiModel(value = "档案管理")
public class HeadArchivesVo extends EAMBaseFilterVo {

    /**
     * id
     */
    @ApiModelProperty(value = "档案ID(新增不需要传值)")
    private String id;


    /**
     * 资料编号
     */
    @ApiModelProperty(value = "资料编号")
    private String materialNum;

    /**
     * 档案名称
     */
    @ApiModelProperty(value = "档案名称(不能超过50个字节)")
    private String materialName;

    /**
     * 发证部门
     */
    @ApiModelProperty(value = "发证部门(不能超过30个字节)")
    private String department;


    /**
     * 资料类型
     */
    @ApiModelProperty(value = "资料类型(不能超过30个字节)")
    private String materialType;

    /**
     * 资料类型名称
     */
    @ApiModelProperty(value = "资料类型(不能超过30个字节)")
    private String typeName;

    /**
     * 档案编号
     */
    @ApiModelProperty(value = "档案编号(不能超过30个字节)")
    private String archivesNum;

    /**
     * 核发日期
     */
    @ApiModelProperty(value = "核发日期(新增、修改不用传值)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 资料版本
     */
    @ApiModelProperty(value = "资料版本")
    private String materialVersion;

    /**
     * 有效期起始时间
     */
    @ApiModelProperty(value = "有效期起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartTime;

    /**
     * 有效期截止时间
     */
    @ApiModelProperty(value = "有效期截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndTime;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息(不能超过500个字节)")
    private String remark;


    /**
     * 总部
     */
    @ApiModelProperty(value = "总部")
    private String headQuarters;

    /**
     * 项目
     */
    @ApiModelProperty(value = "项目")
    private String project;


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

    /**
     * 存放位置
     */
    @ApiModelProperty(value = "存放位置")
    private String position;

    @ApiModelProperty(value = "位置编码")
    private String positioncode;


    @ApiModelProperty(value = "位置描述")
    private String positionDescription;

    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    private String creator;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getArchivesNum() {
        return archivesNum;
    }

    public void setArchivesNum(String archivesNum) {
        this.archivesNum = archivesNum;
    }


    public String getPositioncode() {
        return positioncode;
    }

    public void setPositioncode(String positioncode) {
        this.positioncode = positioncode;
    }

    public String getPositionDescription() {
        return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }

    public String getMaterialVersion() {
        return materialVersion;
    }

    public void setMaterialVersion(String materialVersion) {
        this.materialVersion = materialVersion;
    }

    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getHeadQuarters() {
        return headQuarters;
    }

    public void setHeadQuarters(String headQuarters) {
        this.headQuarters = headQuarters;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "HeadArchivesVo{" +
                "id='" + id + '\'' +
                ", materialNum='" + materialNum + '\'' +
                ", materialName='" + materialName + '\'' +
                ", department='" + department + '\'' +
                ", materialType='" + materialType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", archivesNum='" + archivesNum + '\'' +
                ", createDate=" + createDate +
                ", status='" + status + '\'' +
                ", materialVersion='" + materialVersion + '\'' +
                ", validStartTime=" + validStartTime +
                ", validEndTime=" + validEndTime +
                ", remark='" + remark + '\'' +
                ", headQuarters='" + headQuarters + '\'' +
                ", project='" + project + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", position='" + position + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
