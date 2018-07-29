package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanAsset;
import com.enerbos.cloud.eam.microservice.service.MaintenanceMaintenancePlanAssetService;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanAssetVo;
import com.enerbos.cloud.util.ReflectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @Description EAM预防维护计划接口
 */
@RestController
public class MaintenanceMaintenancePlanAssetController {

	private final static Log logger = LogFactory.getLog(MaintenanceMaintenancePlanAssetController.class);

    @Autowired
    private MaintenanceMaintenancePlanAssetService maintenanceMaintenancePlanAssetService;

	/**
	 * findAllMaintenancePlanAsset: 根据预防维护计划ID查询设备ID
	 * @param maintenancePlanId 预防维护计划ID
	 * @return List<String> 分页查询有效时间列表VO集合
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/findAllMaintenancePlanAsset",method = RequestMethod.GET)
	public List<String> findAllMaintenancePlanAsset(@RequestParam("maintenancePlanId") String maintenancePlanId) {
		List<String> result = maintenanceMaintenancePlanAssetService.findMaintenancePlanAssetListByMaintenancePlanId(maintenancePlanId);
		return result;
	}

	/**
	 * saveMaintenancePlanAssetList: 保存有效时间
	 * @param maintenanceMaintenancePlanAssetVoList 有效时间实体vo集合 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanAssetVo}
	 * @return List<MaintenanceMaintenancePlanAssetVo> 有效时间实体vo
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/saveMaintenancePlanAssetList")
	public List<MaintenanceMaintenancePlanAssetVo> saveMaintenancePlanAssetList(@RequestBody List<MaintenanceMaintenancePlanAssetVo> maintenanceMaintenancePlanAssetVoList) {
		List<MaintenanceMaintenancePlanAsset> maintenanceMaintenancePlanAssetList=new ArrayList<>();
		try {
			MaintenanceMaintenancePlanAsset maintenanceMaintenancePlanAsset ;
			for (MaintenanceMaintenancePlanAssetVo maintenanceMaintenancePlanAssetVo : maintenanceMaintenancePlanAssetVoList) {
				maintenanceMaintenancePlanAsset = new MaintenanceMaintenancePlanAsset();
				ReflectionUtils.copyProperties(maintenanceMaintenancePlanAssetVo, maintenanceMaintenancePlanAsset, null);
				maintenanceMaintenancePlanAssetList.add(maintenanceMaintenancePlanAsset);
			}
			maintenanceMaintenancePlanAssetList=maintenanceMaintenancePlanAssetService.saveMaintenancePlanAsset(maintenanceMaintenancePlanAssetList);
			MaintenanceMaintenancePlanAssetVo maintenanceMaintenancePlanAssetVo;
			maintenanceMaintenancePlanAssetVoList=new ArrayList<>();
			for (MaintenanceMaintenancePlanAsset maintenanceMaintenancePlanAsset1 : maintenanceMaintenancePlanAssetList) {
				maintenanceMaintenancePlanAssetVo = new MaintenanceMaintenancePlanAssetVo();
				ReflectionUtils.copyProperties(maintenanceMaintenancePlanAsset1, maintenanceMaintenancePlanAssetVo, null);
				maintenanceMaintenancePlanAssetVoList.add(maintenanceMaintenancePlanAssetVo);
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/maintenancePlan/saveMaintenancePlanAssetList-----", e);
		}
		return maintenanceMaintenancePlanAssetVoList;
	}

	/**
	 * deleteMaintenancePlanAssetListByIds: 删除选中的有效时间
	 * @param ids  选中的有效时间id集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanAssetListByIds")
	public Boolean deleteMaintenancePlanAssetListByIds(@RequestParam("ids") List<String> ids) {
		return maintenanceMaintenancePlanAssetService.deleteMaintenancePlanAssetByIds(ids);
	}

	/**
	 * deleteMaintenancePlanAssetByMaintenancePlanIds: 删除预防性维护计划关联的设备(多个)
	 * @param maintenancePlanIds  预防性维护计划ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanAssetByMaintenancePlanIds")
	public Boolean deleteMaintenancePlanAssetByMaintenancePlanIds(@RequestBody List<String> maintenancePlanIds) {
		return maintenanceMaintenancePlanAssetService.deleteMaintenancePlanAssetByMaintenancePlanIds(maintenancePlanIds);
	}

	/**
	 * deleteMaintenancePlanAssetByAssetIds: 删除预防性维护计划关联的设备(多个)
	 * @param maintenancePlanId 预防性维护计划ID
	 * @param assetIds  设备ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanAssetByAssetIds")
	public Boolean deleteMaintenancePlanAssetByAssetIds(@RequestParam("maintenancePlanId") String maintenancePlanId,@RequestBody List<String> assetIds){
		return maintenanceMaintenancePlanAssetService.deleteMaintenancePlanAssetByAssetIds(maintenancePlanId,assetIds);
	}
}