package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.ConstructionSupervise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月09日
 * @Description 施工单-监管说明接口
 */
@Mapper
public interface ConstructionSuperviseDao {

	/**
	 * findConstructionSuperviseByConstructionId: 根据施工单ID查询施工单-监管说明
	 * @param constructionId 施工单ID
	 * @return List<ConstructionActualItem> 返回施工单-监管说明集合
	 */
	List<ConstructionSupervise> findConstructionSuperviseByConstructionId(String constructionId);
}
