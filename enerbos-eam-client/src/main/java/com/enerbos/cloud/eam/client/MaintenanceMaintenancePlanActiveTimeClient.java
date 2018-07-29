package com.enerbos.cloud.eam.client;

import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM预防维护计划有效时间Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaintenanceMaintenancePlanActiveTimeClientFallback.class)
public interface MaintenanceMaintenancePlanActiveTimeClient {
	/**
     * findAllMaintenancePlanActiveTime: 根据预防维护计划ID查询有效时间列表
     * @param maintenancePlanId 预防维护计划ID
     * @return List<MaintenanceMaintenancePlanActiveTimeVo> 分页查询有效时间列表VO集合
     */
    @RequestMapping(value = "/eam/micro/maintenancePlan/findAllMaintenancePlanActiveTime",method = RequestMethod.GET)
    public List<MaintenanceMaintenancePlanActiveTimeVo> findAllMaintenancePlanActiveTime(@RequestParam("maintenancePlanId") String maintenancePlanId);
    
    /**
     * saveMaintenancePlanActiveTimeList: 保存有效时间
     * @param maintenanceMaintenancePlanActiveTimeVoList 有效时间实体vo集合 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo}
     * @return List<MaintenanceMaintenancePlanActiveTimeVo> 有效时间实体vo
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/saveMaintenancePlanActiveTimeList")
    public List<MaintenanceMaintenancePlanActiveTimeVo> saveMaintenancePlanActiveTimeList(@RequestBody List<MaintenanceMaintenancePlanActiveTimeVo> maintenanceMaintenancePlanActiveTimeVoList) ;
    
    /**
     * deleteMaintenancePlanActiveTimeListByIds: 删除选中的有效时间
     * @param ids  选中的有效时间id集合
     * @return Boolean 删除是否成功
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanActiveTimeListByIds")
    public Boolean deleteMaintenancePlanActiveTimeListByIds(@RequestParam("ids") List<String> ids);

	/**
	 * deleteMaintenancePlanActiveTimeByMaintenancePlanIds: 根据预防性维护计划ID集合删除关联的有效时间
	 * @param maintenancePlanIds 预防性维护计划ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanActiveTimeByMaintenancePlanIds")
	public Boolean deleteMaintenancePlanActiveTimeByMaintenancePlanIds(@RequestParam("maintenancePlanIds") List<String> maintenancePlanIds);
}

@Component
class MaintenanceMaintenancePlanActiveTimeClientFallback implements FallbackFactory<MaintenanceMaintenancePlanActiveTimeClient> {
	@Override
	public MaintenanceMaintenancePlanActiveTimeClient create(Throwable throwable) {
		return new MaintenanceMaintenancePlanActiveTimeClient() {
			@Override
			public List<MaintenanceMaintenancePlanActiveTimeVo> findAllMaintenancePlanActiveTime(String maintenancePlanId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceMaintenancePlanActiveTimeVo> saveMaintenancePlanActiveTimeList(
					List<MaintenanceMaintenancePlanActiveTimeVo> maintenanceMaintenancePlanActiveTimeVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteMaintenancePlanActiveTimeListByIds(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteMaintenancePlanActiveTimeByMaintenancePlanIds(List<String> maintenancePlanIds) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}