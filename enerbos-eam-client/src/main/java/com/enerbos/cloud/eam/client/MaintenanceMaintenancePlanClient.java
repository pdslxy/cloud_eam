package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM预防维护计划Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaintenanceMaintenancePlanClientFallback.class)
public interface MaintenanceMaintenancePlanClient {
	/**
     * saveMaintenancePlan:保存预防维护计划
     * @param maintenanceMaintenancePlanVo {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
     * @return MaintenanceMaintenancePlanVo
     */
    @RequestMapping(value = "/eam/micro/maintenancePlan/saveMaintenancePlan", method = RequestMethod.POST)
    MaintenanceMaintenancePlanVo saveMaintenancePlan(@RequestBody MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo);

	/**
	 * updateMaintenancePlanStatus:变更预防维护计划状态
	 * @param statusVo {@link com.enerbos.cloud.eam.vo.StatusVo}
	 * @return StatusVo
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/updateMaintenancePlanStatus", method = RequestMethod.POST)
	StatusVo updateMaintenancePlanStatus(@RequestBody StatusVo statusVo);

    /**
	 * findMaintenancePlanById:根据ID查询预防维护计划
	 * @param id 
	 * @return MaintenanceMaintenancePlanVo 预防维护计划VO
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/findMaintenancePlanById", method = RequestMethod.GET)
	MaintenanceMaintenancePlanVo findMaintenancePlanById(@RequestParam("id") String id);
	
	/**
	 * findMaintenancePlanByMaintenancePlanNum:根据MaintenancePlanNum查询预防维护计划
	 * @param maintenancePlanNum 预防性维护编码
	 * @return MaintenanceMaintenancePlanVo 预防维护计划VO
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/findMaintenancePlanByMaintenancePlanNum", method = RequestMethod.GET)
	MaintenanceMaintenancePlanVo findMaintenancePlanByMaintenancePlanNum(@RequestParam("maintenancePlanNum") String maintenancePlanNum);
	
	/**
	 * findPageMaintenancePlanList: 分页查询预防维护计划
	 * @param maintenanceMaintenancePlanSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
	 * @return EnerbosPage<MaintenanceMaintenancePlanForListVo> 预防维护计划VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/findPageMaintenancePlanList")
	EnerbosPage<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanList(@RequestBody MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo);

	/**
	 * findAllMaintenancePlanByStatus:根据状态查询预防维护计划
	 * @param status 状态
	 * @return List<MaintenanceMaintenancePlanVo> {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/findAllMaintenancePlanByStatus")
	List<MaintenanceMaintenancePlanVo> findAllMaintenancePlanByStatus(String status);

	/**
     * deleteMaintenancePlanList:删除选中的预防维护计划
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/maintenancePlan/deleteMaintenancePlanList", method = RequestMethod.POST)
    Boolean deleteMaintenancePlanList(@RequestBody List<String> ids);

	/**
	 * findPageMaintenancePlanByAssetId:根据设备ID分页查询预防性维护计划
	 * @param maintenanceForAssetFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return EnerbosPage<MaintenanceMaintenancePlanVo> 预防维护计划VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/findPageMaintenancePlanByAssetId")
	EnerbosPage<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanByAssetId(@RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);

	/**
	 * 收藏预防性维护计划
	 * @param maintenanceMaintenancePlanRfCollectorVoList 收藏的预防性维护计划列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/collect")
	void collectMaintenancePlan(@RequestBody List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList);

	/**
	 * 取消收藏预防性维护计划
	 * @param maintenanceMaintenancePlanRfCollectorVoList 需要取消收藏的预防性维护计划列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/collect/cancel")
	void cancelCollectMaintenancePlan(@RequestBody List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList);

	/**
	 * 批量保存
	 * @param initEamSet
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/saveBatchPlans")
    Boolean saveBatchPlans(@RequestBody InitEamSet initEamSet);
}

@Component
class  MaintenanceMaintenancePlanClientFallback implements FallbackFactory<MaintenanceMaintenancePlanClient> {
	@Override
	public MaintenanceMaintenancePlanClient create(Throwable throwable) {
		return new MaintenanceMaintenancePlanClient() {
			@Override
			public MaintenanceMaintenancePlanVo saveMaintenancePlan(MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public StatusVo updateMaintenancePlanStatus(StatusVo statusVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceMaintenancePlanVo findMaintenancePlanById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceMaintenancePlanVo findMaintenancePlanByMaintenancePlanNum(String maintenancePlanNum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanList(
					MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteMaintenancePlanList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceMaintenancePlanVo> findAllMaintenancePlanByStatus(String status) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void collectMaintenancePlan(List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void cancelCollectMaintenancePlan(List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean saveBatchPlans(@RequestBody InitEamSet initEamSet) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}