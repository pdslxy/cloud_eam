package com.enerbos.cloud.eam.microservice.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandard;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardTask;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardService;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardTaskService;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardTaskVo;
import com.enerbos.cloud.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM作业标准接口
 */
@RestController
public class MaintenanceJobStandardTaskController {
    private final static Log logger = LogFactory.getLog(MaintenanceJobStandardTaskController.class);

    @Autowired
    private MaintenanceJobStandardService maintenanceJobStandardService;

    @Autowired
    private MaintenanceJobStandardTaskService maintenanceJobStandardTaskService;

	/**
	 * findJobStandardTaskByJobStandardId: 查询与作业标准关联的任务
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardTaskVo> 与作业标准关联的任务集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/findJobStandardTaskByJobStandardId")
	public List<MaintenanceJobStandardTaskVo> findJobStandardTaskByJobStandardId(@RequestParam("jobStandardId")  String jobStandardId) {
		List<MaintenanceJobStandardTaskVo> result = maintenanceJobStandardTaskService.findJobStandardTaskByJobStandardId(jobStandardId);
		return result;
	}
	
	/**
	 * deleteJobTask: 根据id删除与作业标准相关的任务
	 * @param ids  多个与作业标准关联的任务id
	 * @param jobStandardId 作业标准id
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/jobStandard/deleteJobTask")
	public Boolean deleteJobTask(@RequestParam("ids") List<String> ids, @RequestParam("jobStandardId") String jobStandardId) {
		maintenanceJobStandardTaskService.deleteJobStandardTaskByIds(ids);
		//更新持续时间
		MaintenanceJobStandard maintenanceJobStandard = maintenanceJobStandardService.findJobStandardByID(jobStandardId);
		Double taskall = 0.0;
		List<MaintenanceJobStandardTaskVo> jobtasks = maintenanceJobStandardTaskService.findJobStandardTaskByJobStandardId(jobStandardId);
		if (jobtasks!=null&&jobtasks.size() > 0) {
			for (int i = 0; i < jobtasks.size(); i++) {
				taskall = taskall + jobtasks.get(i).getTaskDuration();
			}
		}
		maintenanceJobStandard.setDuration(taskall);
		maintenanceJobStandardService.save(maintenanceJobStandard);
		return true;
	}

	/**
	 * deleteJobStandardTaskByJobStandardIds: 删除作业标准集合关联的任务(多个)
	 * @param jobStandardIds  作业标准ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/deleteJobStandardTaskByJobStandardIds")
	public Boolean deleteJobStandardTaskByJobStandardIds(@RequestBody List<String> jobStandardIds) {
		return maintenanceJobStandardTaskService.deleteJobStandardTaskByJobStandardIds(jobStandardIds);
	}
	
	/**
	 * saveMaintenanceJobStandardTaskList: 保存与作业标准关联的任务
	 * @param maintenanceJobStandardTaskVoList 与作业标准关联的任务实体VO集合 {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardTaskVo}
	 * @return List<MaintenanceJobStandardTaskVo> 与作业标准关联的任务实体VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/saveMaintenanceJobStandardTaskList")
	public List<MaintenanceJobStandardTaskVo> saveMaintenanceJobStandardTaskList(@RequestBody List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList) {
		List<MaintenanceJobStandardTask> maintenanceJobStandardTaskList=new ArrayList<>();
		try {
			MaintenanceJobStandardTask maintenanceJobStandardTask ;
			for (MaintenanceJobStandardTaskVo maintenanceJobStandardTaskVo : maintenanceJobStandardTaskVoList) {
				maintenanceJobStandardTask = new MaintenanceJobStandardTask();
				ReflectionUtils.copyProperties(maintenanceJobStandardTaskVo, maintenanceJobStandardTask, null);
				maintenanceJobStandardTaskList.add(maintenanceJobStandardTask);
			}
			maintenanceJobStandardTaskList=maintenanceJobStandardTaskService.saveJobStandardTaskList(maintenanceJobStandardTaskList);
			MaintenanceJobStandardTaskVo maintenanceJobStandardTaskVo;
			maintenanceJobStandardTaskVoList=new ArrayList<>();
			for (MaintenanceJobStandardTask maintenanceJobStandardTask1 : maintenanceJobStandardTaskList) {
				maintenanceJobStandardTaskVo = new MaintenanceJobStandardTaskVo();
				ReflectionUtils.copyProperties(maintenanceJobStandardTask1, maintenanceJobStandardTaskVo, null);
				maintenanceJobStandardTaskVoList.add(maintenanceJobStandardTaskVo);
			}
			//更新持续时间
			String jobStandardId=maintenanceJobStandardTaskVoList.get(0).getJobStandardId();
			MaintenanceJobStandard maintenanceJobStandard = maintenanceJobStandardService.findJobStandardByID(jobStandardId);
			Double taskall = 0.0;
			List<MaintenanceJobStandardTaskVo> jobtasks = maintenanceJobStandardTaskService.findJobStandardTaskByJobStandardId(jobStandardId);
			if (jobtasks!=null&&jobtasks.size() > 0) {
				for (int i = 0; i < jobtasks.size(); i++) {
					taskall = taskall + jobtasks.get(i).getTaskDuration();
				}
			}
			maintenanceJobStandard.setDuration(taskall);
			maintenanceJobStandardService.save(maintenanceJobStandard);
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/saveMaintenanceJobStandardTaskList-----", e);
		}
		return maintenanceJobStandardTaskVoList;
	}
}