package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年07月26日
 * @Description EAM根据设备查询作业标准列表过滤条件VO
 */
@ApiModel(value = "根据设备查询作业标准列表过滤条件VO", description = "根据设备查询作业标准列表过滤条件VO")
public class MaintenanceForAssetFilterVo implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID",required = true)
	@NotBlank(message = "设备ID不能为空")
    private String assetId;

	/**
	 * 排序参数
	 */
	@ApiModelProperty(value = "排序参数")
	private String sorts ;

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "分页-页码",example = "1")
    private int pageNum;

	/**
	 * 每页显示行数
	 */
	@ApiModelProperty(value = "分页-每页显示条数",example = "10")
	private int pageSize;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public void setPageNum(int pageNum) {
		if ("".equals(pageNum)||pageNum<1) {
			this.pageNum = 1;
		} else {
			this.pageNum = pageNum;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if ("".equals(pageSize)||pageSize<1) {
			this.pageSize = 10;
		} else {
			this.pageSize = pageSize;
		}
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
}
