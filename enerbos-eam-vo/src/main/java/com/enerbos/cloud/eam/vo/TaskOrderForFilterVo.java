package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * @date 2017年07月26日
 * @Description 我的任务，查询工单条件Vo
 */
@ApiModel(value = "我的任务，查询工单条件Vo", description = "我的任务，查询工单条件Vo")
public class TaskOrderForFilterVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 流程id
	 */
	@ApiModelProperty(value = "流程实例id")
	private List<String> processInstanceIdList;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private String sorts;

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "分页-页码")
	private int pageNum;

	/**
	 * 每页显示行数
	 */
	@ApiModelProperty(value = "分页-每页显示条数")
	private int pageSize;

	@ApiModelProperty(value = "模糊搜索关键词，分词",hidden=true)
	private List<String> wordsList;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<String> getProcessInstanceIdList() {
		return processInstanceIdList;
	}

	public void setProcessInstanceIdList(List<String> processInstanceIdList) {
		this.processInstanceIdList = processInstanceIdList;
	}

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<String> getWordsList() {
		return wordsList;
	}

	public void setWordsList(List<String> wordsList) {
		this.wordsList = wordsList;
	}
}