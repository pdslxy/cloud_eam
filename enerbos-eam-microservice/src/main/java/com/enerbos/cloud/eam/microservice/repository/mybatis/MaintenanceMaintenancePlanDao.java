package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;

import com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanForListVo;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月02日
 * @Description 预防性维护Dao
 */
@Mapper
public interface MaintenanceMaintenancePlanDao{

	/**
	 * findMaintenancePlanById：根据ID查询预防维护计划
	 * @param id
	 * @return MaintenanceMaintenancePlanVo {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
	 */
	MaintenanceMaintenancePlanVo findMaintenancePlanById(String id);

	/**
	 * findMaintenancePlanByMaintenancePlanNum:根据maintenancePlanNum编码查询预防维护计划
	 * @param maintenancePlanNum 预防维护计划编码
	 * @return MaintenanceMaintenancePlanVo {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
	 */
	MaintenanceMaintenancePlanVo findMaintenancePlanByMaintenancePlanNum(String maintenancePlanNum);

	/**
	 * findAllMaintenancePlanByStatus:根据状态查询预防维护计划
	 * @param status 状态
	 * @return List<MaintenanceMaintenancePlanVo> {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
	 */
	List<MaintenanceMaintenancePlanVo> findAllMaintenancePlanByStatus(String status);

	/**
	 * findPageMaintenancePlanList:分页查询预防性维护计划
	 * @param maintenanceMaintenancePlanSelectVo 预防性维护计划过滤条件Vo {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
	 * @return List<MaintenanceMaintenancePlanForListVo> 预防性维护计划Vo集合
	 */
	List<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanList(MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo);

	/**
	 * findPageMaintenancePlanByAssetId:根据设备ID分页查询预防性维护计划
	 * @param maintenanceForAssetFilterVo 根据设备查询预防性维护计划列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return List<MaintenanceMaintenancePlanForListVo> 预防性维护计划Vo集合
	 */
	List<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);
}
