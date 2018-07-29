package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.enerbos.cloud.common.EnerbosBaseFilterVo;

import java.io.Serializable;

/**
 * 
 * 
 * 
 * All rights Reserved, Designed By 翼虎能源
 * 
 * Copyright: Copyright(C) 2015-2017
 * 
 * Company 北京翼虎能源科技有限公司
 * 
 *
 * 
 * @author 刘秀朋
 * 
 * @version 1.0
 * 
 * @date 2017年4月1日 上午10:54:56
 * 
 * @Description 物资发放明细
 * 
 *
 */
@ApiModel(value = "物资发放明细查询条件")
public class MaterialReleaseDetailVoForFilter extends EAMBaseFilterVo {

	/**
	 * 
	 * 物资发放id
	 * 
	 */
	@ApiModelProperty(value = "物资发放id")
	private String releaseId;

	/**
	 * 发放类型
	 */
	@ApiModelProperty(value = "发放类型")
	private String releaseType;

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}

	@Override
	public String toString() {
		return "MaterialReleaseDetailVoForFilter [releaseId=" + releaseId
				+ ", releaseType=" + releaseType + "]";
	}

}
