package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.PatrolStand;
import com.enerbos.cloud.eam.microservice.domain.PatrolStandContent;
import com.enerbos.cloud.eam.vo.PatrolStandContentVoForList;
import com.enerbos.cloud.eam.vo.PatrolStandVoForFilter;
import com.enerbos.cloud.eam.vo.PatrolStandVoForList;
import com.github.pagehelper.PageInfo;

public interface PatrolStandService {

	/**
	 * 分页查询巡检标准
	 * 
	 * @param patrolStandVoForFilter
	 *            巡检标准查询实体
	 * @return 分页列表
	 */
	PageInfo<PatrolStandVoForList> findPage(
			PatrolStandVoForFilter patrolStandVoForFilter);

	/**
	 * 根据ID查询巡检标准
	 *
	 * @param id
	 *            巡检标准id
	 * @return 返回执行码及数据
	 */
	PatrolStand findPatrolStandById(String id);

	/**
	 * 保存巡检标准
	 * 
	 * @param patrolStand
	 *            巡检标准实体
	 *            {@link com.enerbos.cloud.eam.microservice.domain.PatrolStand}
	 * @return 保存的实体
	 */
	PatrolStand savePatrolStand(PatrolStand patrolStand);

	/**
	 * 修改巡检标准
	 * 
	 * @param patrolStand
	 *            巡检标准实体
	 *            {@link com.enerbos.cloud.eam.microservice.domain.PatrolStand}
	 * @return 保存的实体
	 */
	PatrolStand updatePatrolStand(PatrolStand patrolStand);

	/**
	 * 删除巡检标准
	 * 
	 * @param ids
	 *            要删除的id数组
	 * @return 数据
	 */
	void deletePatrolStand(String[] ids);

	/**
	 * 根据巡检标准id查找巡检标准内容
	 * 
	 * @param id
	 *            巡检标准id
	 * @param pageSize
	 *            条数
	 * @param pageNum
	 *            页数
	 * @return 查询结果
	 */
	PageInfo<PatrolStandContentVoForList> findPatrolStandContent(String id,
			Integer pageSize, Integer pageNum);

	/**
	 * 保存巡检内容
	 * 
	 * @param patrolStandContent
	 *            {@link com.enerbos.cloud.eam.microservice.domain.PatrolStandContent}
	 * @return 保存后实体
	 */
	PatrolStandContent savePatrolStandContent(
			PatrolStandContent patrolStandContent);

	/**
	 * 修改巡检内容
	 * 
	 * @param patrolStandContent
	 *            {@link com.enerbos.cloud.eam.microservice.domain.PatrolStandContent}
	 * @return 修改后实体
	 */
	PatrolStandContent updatePatrolStandContent(
			PatrolStandContent patrolStandContent);

	/**
	 * 
	 * 删除巡检标准内容
	 * 
	 * @param ids
	 *            id合集
	 * @return boolean 成功 失败
	 */
	void deletePatrolStandContent(String[] ids);

	/**
	 * 根据id查询巡检标准内容
	 * @param id
	 * @return
	 */
    PatrolStandContent findPatrolStandContentById(String id);
}
