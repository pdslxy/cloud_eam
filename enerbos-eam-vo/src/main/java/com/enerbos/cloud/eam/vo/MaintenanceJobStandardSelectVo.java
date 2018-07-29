package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM作业标准VO
 */
@ApiModel(value = "作业标准列表过滤条件Vo", description = "作业标准列表过滤条件Vo")
public class MaintenanceJobStandardSelectVo extends EAMBaseFilterVo {

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态(默认为空)")
    private List<String> status;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private String orgId;

    /**
     * 站点ID
     */
    @ApiModelProperty(value = "站点ID")
    private String siteId;

    /**
     * 标准类型
     */
    @ApiModelProperty(value = "标准类型(默认为空)")
    private List<String> standardType;

    /**
     * 作业类型
     */
    @ApiModelProperty(value = "作业类型(默认为空)")
    private List<String> jobType;

	/**
	 * 所属设备分类
	 */
	@ApiModelProperty(value = "所属设备分类")
	private List<String> classificationId;

    @ApiModelProperty(value = "模糊搜索关键词")
    private String words;

    private List<String> wordsList;

	@ApiModelProperty(value = "是否收藏 true：是")
	private Boolean collect;

	@ApiModelProperty(value = "人员编号", hidden = true)
	private String personId;

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public List<String> getWordsList() {
		return wordsList;
	}

	public void setWordsList(List<String> wordsList) {
		this.wordsList = wordsList;
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

	public Boolean getCollect() {
		return collect;
	}

	public void setCollect(Boolean collect) {
		this.collect = collect;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getStandardType() {
		return standardType;
	}

	public void setStandardType(List<String> standardType) {
		this.standardType = standardType;
	}

	public List<String> getJobType() {
		return jobType;
	}

	public void setJobType(List<String> jobType) {
		this.jobType = jobType;
	}

	public List<String> getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(List<String> classificationId) {
		this.classificationId = classificationId;
	}

	@Override
	public String toString() {
		return "MaintenanceJobStandardSelectVo{" +
				"status=" + status +
				", orgId='" + orgId + '\'' +
				", siteId='" + siteId + '\'' +
				", standardType=" + standardType +
				", jobType=" + jobType +
				", classificationId=" + classificationId +
				", words='" + words + '\'' +
				", wordsList=" + wordsList +
				", collect=" + collect +
				", personId='" + personId + '\'' +
				'}';
	}
}
