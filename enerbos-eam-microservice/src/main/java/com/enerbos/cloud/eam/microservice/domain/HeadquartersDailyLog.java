package com.enerbos.cloud.eam.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 16:53
 * @Description 例行工作单-日志实体类
 */
@Entity
@Table(name = "eam_headquarters_daily_log")
public class HeadquartersDailyLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 任务编码
     */
    @Column(name = "task_num", length = 50)
    private String taskNum;

    /**
     * 执行人
     */
    @Column(name = "workor", length = 200)
    private String workor;

    /**
     * 任务节点
     */
    @Column(name = "task_node", length = 500)
    private String taskNode;

    /**
     * 描述
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * 持续时间
     */
    @Column(name = "duration", length = 200)
    private String duration;

    /**
     * 组织id
     */
    @Column(name = "org_id", length = 36)
    private String orgId;

    /**
     * 站点id
     */
    @Column(name = "site_id", length = 36)
    private String siteId;

    /**
     * 开始时间
     */
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    /**
     * 完成时间
     */
    @Column(name = "finish_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;


    public String getTaskNode() {
        return taskNode;
    }

    public void setTaskNode(String taskNode) {
        this.taskNode = taskNode == null ? null : taskNode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum == null ? null : taskNum.trim();
    }

    public String getWorkor() {
        return workor;
    }

    public void setWorkor(String workor) {
        this.workor = workor == null ? null : workor.trim();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}