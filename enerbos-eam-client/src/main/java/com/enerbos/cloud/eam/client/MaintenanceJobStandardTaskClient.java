package com.enerbos.cloud.eam.client;

import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceJobStandardTaskVo;

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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaintenanceJobStandardTaskClientFallback.class)
public interface MaintenanceJobStandardTaskClient {
	
	/**
	 * findJobStandardTaskByJobStandardId: 查询与作业标准关联的任务
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardTaskVo> 与作业标准关联的任务集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/findJobStandardTaskByJobStandardId")
	public List<MaintenanceJobStandardTaskVo> findJobStandardTaskByJobStandardId(@RequestParam("jobStandardId")  String jobStandardId);
	
	/**
	 * deleteJobTask: 根据id删除与作业标准相关的任务
	 * @param ids  多个与作业标准关联的任务id
	 * @param jobStandardId 作业标准id
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/jobStandard/deleteJobTask")
	public Boolean deleteJobTask(@RequestParam("ids") List<String> ids, @RequestParam("jobStandardId") String jobStandardId) ;
	
	/**
	 * deleteJobStandardTaskByJobStandardIds: 删除作业标准集合关联的任务(多个)
	 * @param jobStandardIds  作业标准ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/deleteJobStandardTaskByJobStandardIds")
	public Boolean deleteJobStandardTaskByJobStandardIds(@RequestBody List<String> jobStandardIds);
	
	/**
	 * saveMaintenanceJobStandardTaskList: 保存与作业标准关联的任务
	 * @param List<MaintenanceJobStandardTaskVo> 与作业标准关联的任务实体VO集合
	 * @return List<MaintenanceJobStandardTaskVo> 与作业标准关联的任务实体VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/saveMaintenanceJobStandardTaskList")
	public List<MaintenanceJobStandardTaskVo> saveMaintenanceJobStandardTaskList(@RequestBody List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList);
}

@Component
class  MaintenanceJobStandardTaskClientFallback implements FallbackFactory<MaintenanceJobStandardTaskClient> {
	@Override
	public MaintenanceJobStandardTaskClient create(Throwable throwable) {
		return new MaintenanceJobStandardTaskClient() {
			@Override
			public List<MaintenanceJobStandardTaskVo> findJobStandardTaskByJobStandardId(String jobStandardId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteJobTask(List<String> ids, String jobStandardId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceJobStandardTaskVo> saveMaintenanceJobStandardTaskList(
					List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteJobStandardTaskByJobStandardIds(List<String> jobStandardIds) {
				throw new RuntimeException(throwable.getMessage());
	}
		};
	}
}