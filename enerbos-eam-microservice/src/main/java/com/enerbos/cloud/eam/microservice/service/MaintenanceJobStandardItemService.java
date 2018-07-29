package com.enerbos.cloud.eam.microservice.service;

import java.util.List;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardItem;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 作业标准关联的物料接口
 */
public interface MaintenanceJobStandardItemService {
	/**
	 * saveJobStandardItem: 创建作业标准关联的物料
	 * @param maintenanceJobStandardItems 所需物料实体集合
	 * @return MaintenanceJobStandardItem 作业标准关联的物料实体
	 */
	public List<MaintenanceJobStandardItem> saveJobStandardItem(List<MaintenanceJobStandardItem> maintenanceJobStandardItems);

	/**
	 * findJobStandardItemById: 根据id查询作业标准关联的物料
	 * @param id
	 * @return MaintenanceJobStandardItem 作业标准关联的物料实体
	 */
	public MaintenanceJobStandardItem findJobStandardItemById(String id);

	/**
	 * deleteJobStandardItemById: 根据ID删除作业标准关联的物料
	 * @param id
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteJobStandardItemById(String id);

	/**
	 * deleteJobStandardItemByIds: 根据ID删除作业标准关联的物料
	 * @param ids
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteJobStandardItemByIds(List<String> ids);

	/**
	 * deleteJobStandardItemByJobStandardIds: 根据作业标准ID集合删除关联的物料
	 * @param jobStandardIds 作业标准ID集合
	 * @return Boolean 删除是否成功
	 */
	public Boolean deleteJobStandardItemByJobStandardIds(List<String> jobStandardIds);

	/**
	 * findJobStandardItemByJobStandardId: 查询作业标准关联的物料
	 * @return PageInfo<MaintenanceJobStandardItemVo> 作业标准关联的物料VO集合
	 */
	public List<MaintenanceJobStandardItemVo> findJobStandardItemByJobStandardId(String jobStandardId);
}
