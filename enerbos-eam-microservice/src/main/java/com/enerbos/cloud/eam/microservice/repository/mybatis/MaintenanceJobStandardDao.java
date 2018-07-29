package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandard;
import com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardForListVo;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardSelectVo;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description 作业标准Dao
 */
@Mapper
public interface MaintenanceJobStandardDao{
	
	/**
	 * findJobStandardByID: 根据ID查询作业标准
	 * @param id 
	 * @return MaintenanceJobStandard
	 */
	MaintenanceJobStandard findJobStandardByID(String id);
	
	/**
	 * findJobStandardByJpnum: 根据jobStandardNum查询作业标准
	 * @param jobStandardNum 标准作业计划编码
	 * @return MaintenanceJobStandardVo
	 */
	MaintenanceJobStandardVo findJobStandardByJobStandardNum(String jobStandardNum);
	
	/**
	 * findJobStandard: 查询作业标准列表
	 * @param MaintenanceJobStandardSelectVo 作业标准列表选择Vo
	 * @return List<MaintenanceJobStandardForListVo>
	 */
	List<MaintenanceJobStandardForListVo> findJobStandard(MaintenanceJobStandardSelectVo MaintenanceJobStandardSelectVo);
	
	/**
	 * findPageJobStandardByAssetId:根据设备ID分页查询作业标准
	 * @param maintenanceForAssetFilterVo 根据设备查询作业标准列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return List<MaintenanceJobStandardForListVo> 预防性维护计划Vo集合
	 */
	List<MaintenanceJobStandardForListVo> findPageJobStandardByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);
}
