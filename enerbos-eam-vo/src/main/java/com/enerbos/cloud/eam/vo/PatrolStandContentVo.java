package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2015-2017
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version EAM2.0
 * @date 2017年8月11日 下午3:03:29
 * @Description 巡检标准内容实体
 */
@ApiModel(value = "巡检标准内容实体")
public class PatrolStandContentVo {

    @ApiModelProperty(value = "唯一标识")
    private String id;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 检查标准
     */
    @ApiModelProperty(value = "检查标准")
    private String checkStand;

    /**
     * 巡检标准id
     */
    @ApiModelProperty(value = "巡检标准id")
    private String patrolStandId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "站点id")
    private String siteId;


    @ApiModelProperty(value = "组织id")
    private String orgId;
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
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckStand() {
        return checkStand;
    }

    public void setCheckStand(String checkStand) {
        this.checkStand = checkStand;
    }

    public String getPatrolStandId() {
        return patrolStandId;
    }

    public void setPatrolStandId(String patrolStandId) {
        this.patrolStandId = patrolStandId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
        return "PatrolStandContentVo{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", checkStand='" + checkStand + '\'' +
                ", patrolStandId='" + patrolStandId + '\'' +
                ", remark='" + remark + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUser='" + createUser + '\'' +
                '}';
    }

}
