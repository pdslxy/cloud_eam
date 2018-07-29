package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
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
 * @date 2017年08月18日
 * @Description
 */
@ApiModel(value = "批量变更状态VO，状态是域值")
public class StatusVo implements Serializable {

    /**
     * ids
     */
    @ApiModelProperty(value = "ids",required = true)
    @NotEmpty
    private List<String> ids;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态，value",required = true)
    @NotBlank(message = "状态不能为空")
    private String status;

    /**
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statusDate;

    /**
     * 能源价格状态，可选项：生效/禁用
     */
    @ApiModelProperty(value = "状态备注，修改状态是填入")
    private String statusRemark;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }

    @Override
    public String toString() {
        return "StatusVo{" +
                "ids='" + ids + '\'' +
                ", status='" + status + '\'' +
                ", statusDate=" + statusDate +
                ", statusRemark='" + statusRemark + '\'' +
                '}';
    }
}
