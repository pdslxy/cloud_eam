package com.enerbos.cloud.eam.client;


import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanAssetVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM预防维护计划关联设备Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaintenanceMaintenancePlanAssetClientFallback.class)
public interface MaintenanceMaintenancePlanAssetClient {
	/**
	 * findAllMaintenancePlanAsset: 根据预防维护计划ID查询设备ID
	 * @param maintenancePlanId 预防维护计划ID
	 * @return List<String> 分页查询有效时间列表VO集合
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/findAllMaintenancePlanAsset",method = RequestMethod.GET)
	List<String> findAllMaintenancePlanAsset(@RequestParam("maintenancePlanId") String maintenancePlanId);
	
	/**
	 * saveMaintenancePlanAssetList: 保存有效时间
	 * @param maintenanceMaintenancePlanAssetVoList 有效时间实体vo集合 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanAssetVo}
	 * @return List<MaintenanceMaintenancePlanAssetVo> 有效时间实体vo
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/saveMaintenancePlanAssetList")
	List<MaintenanceMaintenancePlanAssetVo> saveMaintenancePlanAssetList(@RequestBody List<MaintenanceMaintenancePlanAssetVo> maintenanceMaintenancePlanAssetVoList) ;
	
	/**
	 * deleteMaintenancePlanAssetListByIds: 删除选中的有效时间
	 * @param ids  选中的有效时间id集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanAssetListByIds")
	Boolean deleteMaintenancePlanAssetListByIds(@RequestParam("ids") List<String> ids);
	
	/**
	 * deleteMaintenancePlanAssetByMaintenancePlanIds: 删除预防性维护计划关联的设备(多个)
	 * @param maintenancePlanIds  预防性维护计划ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanAssetByMaintenancePlanIds")
	Boolean deleteMaintenancePlanAssetByMaintenancePlanIds(@RequestBody List<String> maintenancePlanIds);

	/**
	 * deleteMaintenancePlanAssetByAssetIds: 删除预防性维护计划关联的设备(多个)
	 * @param maintenancePlanId 预防性维护计划ID
	 * @param assetIds  设备ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanAssetByAssetIds")
	Boolean deleteMaintenancePlanAssetByAssetIds(@RequestParam("maintenancePlanId") String maintenancePlanId,@RequestBody List<String> assetIds);
}

@Component
class  MaintenanceMaintenancePlanAssetClientFallback implements FallbackFactory<MaintenanceMaintenancePlanAssetClient> {
	@Override
	public MaintenanceMaintenancePlanAssetClient create(Throwable throwable) {
		return new MaintenanceMaintenancePlanAssetClient() {
			@Override
			public List<String> findAllMaintenancePlanAsset(String maintenancePlanId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceMaintenancePlanAssetVo> saveMaintenancePlanAssetList(
					List<MaintenanceMaintenancePlanAssetVo> maintenanceMaintenancePlanAssetVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteMaintenancePlanAssetListByIds(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteMaintenancePlanAssetByMaintenancePlanIds(List<String> maintenancePlanIds) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteMaintenancePlanAssetByAssetIds(String maintenancePlanId, List<String> assetIds) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}