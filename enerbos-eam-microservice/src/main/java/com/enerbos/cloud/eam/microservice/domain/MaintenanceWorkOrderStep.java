package com.enerbos.cloud.eam.microservice.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月13日
 * @Description 维保工单-任务步骤 实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_work_order_step")
public class MaintenanceWorkOrderStep implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(unique = true, nullable = false, length = 36, name = "id")
	private String id;
	
	/**
	 * 是否异常
	 */
	@Column(nullable = true)
	private Boolean abnormal;
	
	/**
	 * 描述
	 */
	@Column(nullable = true,length = 255)
	private String description;
	
	/**
	 * 持续时间
	 */
	@Column(nullable = true,length = 255)
	private Double duration;
	
	/**
	 * 执行情况
	 */
	@Column(nullable = true)
	private Boolean executeSituation;
	
	/**
	 * 是否处理
	 */
	@Column(nullable = true)
	private Boolean handle;
	
	/**
	 * 质量标准
	 */
	@Column(nullable = true,length = 255)
	private String qualityStandard;
	
	/**
	 * 任务步骤（记录的数字）
	 */
	@Column(nullable = true,length = 255)
	private String step;
	
	/**
	 * 关联维保工单
	 */
	@Column(nullable = true,length = 36)
	private String workOrderId;
	
	/**
	 * 实际执行时间
	 */
	@Column(nullable = true)
	private String actualExecuteTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Boolean abnormal) {
		this.abnormal = abnormal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Boolean getExecuteSituation() {
		return executeSituation;
	}

	public void setExecuteSituation(Boolean executeSituation) {
		this.executeSituation = executeSituation;
	}

	public Boolean getHandle() {
		return handle;
	}

	public void setHandle(Boolean handle) {
		this.handle = handle;
	}

	public String getQualityStandard() {
		return qualityStandard;
	}

	public void setQualityStandard(String qualityStandard) {
		this.qualityStandard = qualityStandard;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getActualExecuteTime() {
		return actualExecuteTime;
	}

	public void setActualExecuteTime(String actualExecuteTime) {
		this.actualExecuteTime = actualExecuteTime;
	}

	@Override
    public String toString() {
        return "MaintenanceWorkOrderStep{" +
                "id='" + id + '\'' +
                ", abnormal='" + abnormal + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", executeSituation='" + executeSituation + '\'' +
                ", handle='" + handle + '\'' +
                ", qualityStandard='" + qualityStandard + '\'' +
                ", step='" + step + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", actualExecuteTime='" + actualExecuteTime + '\'' +
                '}';
    }
}