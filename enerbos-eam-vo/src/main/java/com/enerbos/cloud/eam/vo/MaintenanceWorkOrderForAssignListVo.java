package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年07月04日
 * @Description 批量分派维保工单Vo
 */
@ApiModel(value = "批量分派维保工单", description = "批量分派维保工单VO")
public class MaintenanceWorkOrderForAssignListVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
	 * ids
	 */
	@ApiModelProperty(value = "ids")
    private List<String> ids;

	@ApiModelProperty(value = "流程状态，同意/驳回")
	@NotBlank(message = "流程状态不能为空，同意agree/驳回reject")
	private String processStatus;

    //==================任务分派===================
	@ApiModelProperty(value = "执行人id")
	private List<String> executorPersonId;

    @ApiModelProperty(value = "计划开始时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planStartDate;

    @ApiModelProperty(value = "计划完成时间")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planCompletionDate;

	@ApiModelProperty(value = "流程说明")
	private String description;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public List<String> getExecutorPersonId() {
		return executorPersonId;
	}

	public void setExecutorPersonId(List<String> executorPersonId) {
		this.executorPersonId = executorPersonId;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanCompletionDate() {
		return planCompletionDate;
	}

	public void setPlanCompletionDate(Date planCompletionDate) {
		this.planCompletionDate = planCompletionDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Override
	public String toString() {
		return "MaintenanceWorkOrderForAssignListVo{" +
				"ids=" + ids +
				", processStatus='" + processStatus + '\'' +
				", executorPersonId=" + executorPersonId +
				", planStartDate=" + planStartDate +
				", planCompletionDate=" + planCompletionDate +
				", description='" + description + '\'' +
				'}';
	}
}
