package com.enerbos.cloud.eam.microservice.domain;

import com.enerbos.cloud.jpa.EnerbosBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description PM 的季节实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_maintenance_plan_active_time")
public class MaintenanceMaintenancePlanActiveTime implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	@Column(unique = true, nullable = false, length = 36)
	private String id;

    /**
     * 开始月份
     */
    @Column(name="start_date",nullable = true,length = 0)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
	private Date startDate;

	/**
	 * 结束日
	 */
	@Column(name="end_date",nullable = true,length = 0)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
	private Date endDate;

    @Column(name = "maintenance_plan_id",nullable = true,length = 36)
    private String maintenancePlanId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMaintenancePlanId() {
		return maintenancePlanId;
	}

	public void setMaintenancePlanId(String maintenancePlanId) {
		this.maintenancePlanId = maintenancePlanId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "MaintenanceMaintenancePlanActiveTime{" +
				"id='" + id + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", maintenancePlanId='" + maintenancePlanId + '\'' +
				'}';
	}
}
