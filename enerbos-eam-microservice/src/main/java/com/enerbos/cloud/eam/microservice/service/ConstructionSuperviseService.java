package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.ConstructionSupervise;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月9日
 * @Description 施工单-监管说明接口
 */
public interface ConstructionSuperviseService {
	/**
	 * saveConstructionSupervise: 创建施工单-监管说明
	 * @param constructionSuperviseList 施工单-监管说明实体集合
	 * @return List<ConstructionSupervise> 施工单-监管说明实体集合
	 */
	public List<ConstructionSupervise> saveConstructionSupervise(List<ConstructionSupervise> constructionSuperviseList);

	/**
	 * findConstructionSuperviseById: 根据id查询施工单-监管说明
	 * @param id
	 * @return ConstructionSupervise 施工单-监管说明实体
	 */
	public ConstructionSupervise findConstructionSuperviseById(String id);
	
	/**
	 * findConstructionSuperviseByConstructionId: 根据施工单id查询施工单-监管说明
	 * @param constructionId 施工单id
	 * @return List<ConstructionSupervise> 施工单-监管说明实体
	 */
	public List<ConstructionSupervise> findConstructionSuperviseByConstructionId(String constructionId);

	/**
	 * deleteConstructionSuperviseByIds: 删除施工单-监管说明(多个)
	 * @param ids 施工单-监管说明id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteConstructionSuperviseByIds(List<String> ids);
}
