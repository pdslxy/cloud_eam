package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.AppVersionForFilterVo;
import com.enerbos.cloud.eam.vo.AppVersionVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月27日
 * @Description APP 版本信息
 */
public interface AppVersionService {
	/**
	 * getNewVersion: 查询是否有新版本
	 * @param appVersionForFilterVo 过滤条件VO
	 * @return AppVersionVo 最新版本
	 */
	AppVersionVo getNewVersion(AppVersionForFilterVo appVersionForFilterVo);
}