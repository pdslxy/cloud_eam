package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.AppVersionForFilterVo;
import com.enerbos.cloud.eam.vo.AppVersionVo;
import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface AppVersionDao {

	/**
	 * findAppVersion: 查询过滤条件查询APP版本信息
	 * @param appVersionForFilterVo  app查询条件vo
	 * @return AppVersionVo app版本信息vo
	 */
	List<AppVersionVo> findAppVersion(AppVersionForFilterVo appVersionForFilterVo);
}
