package com.enerbos.cloud.eam.client;

import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM作业标准Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaintenanceJobStandardItemClientFallback.class)
public interface MaintenanceJobStandardItemClient {
    
    /**
	 * findJobStandardItemByJobStandardId: 查询与作业标准关联的物料
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardItemVo> 与作业标准关联的物料Vo集合
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/jobStandard/findJobStandardItemByJobStandardId")
	public List<MaintenanceJobStandardItemVo> findJobStandardItemByJobStandardId(@RequestParam("jobStandardId")  String jobStandardId);
	
	/**
	 * deleteJobItemList: 删除与作业标准关联的物料(多个)
	 * @param ids  多个与作业标准关联的物料id
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/deleteJobItemList")
	public Boolean deleteEamJobItem(@RequestBody List<String> ids);

	/**
	 * deleteJobStandardItemByJobStandardIds: 删除作业标准集合关联的物料(多个)
	 * @param jobStandardIds  作业标准ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/deleteJobStandardItemByJobStandardIds")
	public Boolean deleteJobStandardItemByJobStandardIds(@RequestBody List<String> jobStandardIds);
	
	/**
	 * saveMaintenanceJobStandardItemList: 创建与作业标准关联的物料
	 * @param maintenanceJobStandardItemVoList 所需物料Vo集合 {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo}
	 * @return MaintenanceJobStandardItemVo 与作业标准关联的物料实体vo
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/saveMaintenanceJobStandardItemList")
	public List<MaintenanceJobStandardItemVo> saveMaintenanceJobStandardItemList(@RequestBody List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList);
}

@Component
class  MaintenanceJobStandardItemClientFallback implements FallbackFactory<MaintenanceJobStandardItemClient> {
	@Override
	public MaintenanceJobStandardItemClient create(Throwable throwable) {
		return new MaintenanceJobStandardItemClient() {
			@Override
			public List<MaintenanceJobStandardItemVo> findJobStandardItemByJobStandardId(String jobStandardId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteEamJobItem(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceJobStandardItemVo> saveMaintenanceJobStandardItemList(
					List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteJobStandardItemByJobStandardIds(List<String> jobStandardIds) {
				throw new RuntimeException(throwable.getMessage());
	}
		};
	}
}