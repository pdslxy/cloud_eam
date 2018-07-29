package com.enerbos.cloud.eam.microservice.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardItem;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardItemService;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo;
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
public class MaintenanceJobStandardItemController {
    private final static Log logger = LogFactory.getLog(MaintenanceJobStandardItemController.class);

    @Autowired
    private MaintenanceJobStandardItemService maintenanceJobStandardItemService;

    /**
	 * findJobStandardItemByJobStandardId: 查询与作业标准关联的物料
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardItemVo> 与作业标准关联的物料Vo集合
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/jobStandard/findJobStandardItemByJobStandardId")
	public List<MaintenanceJobStandardItemVo> findJobStandardItemByJobStandardId(@RequestParam("jobStandardId")  String jobStandardId) {
		List<MaintenanceJobStandardItemVo> result = maintenanceJobStandardItemService.findJobStandardItemByJobStandardId(jobStandardId);
		return result;
	}
	
	/**
	 * deleteJobItemList: 删除与作业标准关联的物料(多个)
	 * @param ids  多个与作业标准关联的物料id
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/deleteJobItemList")
	public Boolean deleteEamJobItem(@RequestBody List<String> ids) {
		return maintenanceJobStandardItemService.deleteJobStandardItemByIds(ids);
	}

	/**
	 * deleteJobStandardItemByJobStandardIds: 删除作业标准集合关联的物料(多个)
	 * @param jobStandardIds  作业标准ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/deleteJobStandardItemByJobStandardIds")
	public Boolean deleteJobStandardItemByJobStandardIds(@RequestBody List<String> jobStandardIds) {
		return maintenanceJobStandardItemService.deleteJobStandardItemByJobStandardIds(jobStandardIds);
	}
	
	/**
	 * saveMaintenanceJobStandardItemList: 创建与作业标准关联的物料
	 * @param maintenanceJobStandardItemVoList 所需物料Vo集合 {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo}
	 * @return MaintenanceJobStandardItemVo 与作业标准关联的物料实体vo
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/saveMaintenanceJobStandardItemList")
	public List<MaintenanceJobStandardItemVo> saveMaintenanceJobStandardItemList(@RequestBody List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList) {
		List<MaintenanceJobStandardItem> maintenanceJobStandardItemList=new ArrayList<>();
		try {
			MaintenanceJobStandardItem maintenanceJobStandardItem ;
			for (MaintenanceJobStandardItemVo maintenanceJobStandardItemVo : maintenanceJobStandardItemVoList) {
				maintenanceJobStandardItem = new MaintenanceJobStandardItem();
				ReflectionUtils.copyProperties(maintenanceJobStandardItemVo, maintenanceJobStandardItem, null);
				maintenanceJobStandardItemList.add(maintenanceJobStandardItem);
			}
			maintenanceJobStandardItemList=maintenanceJobStandardItemService.saveJobStandardItem(maintenanceJobStandardItemList);
			MaintenanceJobStandardItemVo maintenanceJobStandardItemVo;
			maintenanceJobStandardItemVoList=new ArrayList<>();
			for (MaintenanceJobStandardItem maintenanceJobStandardItem1 : maintenanceJobStandardItemList) {
				maintenanceJobStandardItemVo = new MaintenanceJobStandardItemVo();
				ReflectionUtils.copyProperties(maintenanceJobStandardItem1, maintenanceJobStandardItemVo, null);
				maintenanceJobStandardItemVoList.add(maintenanceJobStandardItemVo);
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/saveMaintenanceJobStandardItemList-----", e);
		}
		return maintenanceJobStandardItemVoList;
	}
}