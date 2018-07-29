package com.enerbos.cloud.eam.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderAsset;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderAssetService;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderService;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderAssetVo;
import com.enerbos.cloud.util.ReflectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM维保工单接口
 */
@RestController
public class MaintenanceWorkOrderAssetController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderAssetController.class);

    @Resource
    protected MaintenanceWorkOrderService maintenanceWorkOrderService;
    
    @Resource
    protected MaintenanceWorkOrderAssetService maintenanceWorkOrderAssetService;
    
    /**
     * saveWorkOrderAsset:添加工单和设备设施关联数据
     * @param eamWorkOrderAssetVoList 工单和设备设施关联Vo
     * @return List<MaintenanceWorkOrderAssetVo>返回添加成功后结果
     */
    @RequestMapping(value = "/eam/micro/workOrderAsset/addAssetToWorkOrder", method = RequestMethod.POST)
    public List<MaintenanceWorkOrderAssetVo> saveWorkOrderAsset(@RequestBody List<MaintenanceWorkOrderAssetVo> eamWorkOrderAssetVoList){
    	List<MaintenanceWorkOrderAsset> eamWoassetList=new ArrayList<>();
		try {
			MaintenanceWorkOrderAsset maintenanceWorkOrderAsset ;
			for (MaintenanceWorkOrderAssetVo maintenanceWorkOrderAssetVo : eamWorkOrderAssetVoList) {
				maintenanceWorkOrderAsset = new MaintenanceWorkOrderAsset();
				ReflectionUtils.copyProperties(maintenanceWorkOrderAssetVo, maintenanceWorkOrderAsset, null);
				eamWoassetList.add(maintenanceWorkOrderAsset);
			}
			eamWoassetList=maintenanceWorkOrderAssetService.saveWorkOrderAssetList(eamWoassetList);
			MaintenanceWorkOrderAssetVo maintenanceWorkOrderAssetVo;
			eamWorkOrderAssetVoList=new ArrayList<>();
			for (MaintenanceWorkOrderAsset eamWoasset1 : eamWoassetList) {
				maintenanceWorkOrderAssetVo = new MaintenanceWorkOrderAssetVo();
				ReflectionUtils.copyProperties(eamWoasset1, maintenanceWorkOrderAssetVo, null);
				eamWorkOrderAssetVoList.add(maintenanceWorkOrderAssetVo);
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderAsset/addAssetToWorkorder-----", e);
		}
    	return eamWorkOrderAssetVoList;
    }
    
    /**
	 * findAssetIdByWorkOrderId: 查询工单设备列表
	 * @param workOrderId 工单ID
	 * @return List<String> 工单关联设备的设备ID集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/findAssetIdByWorkOrderId",method = RequestMethod.GET)
	public List<String> findAssetIdByWorkOrderId(@RequestParam("workOrderId")String workOrderId) {
		List<String> result = new ArrayList<>();
		try {
			result=maintenanceWorkOrderAssetService.findAssetListByWorkOrderId(workOrderId);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderAsset/findAssetIdByWorkOrderId-----", e);
		}
		return result;
	}
	
	/**
	 * findWorkOrderAssetById: 查询工单设备
	 * @param id
	 * @return MaintenanceWorkOrderAssetVo 工单关联设备的设备
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/findWorkOrderAssetById",method = RequestMethod.GET)
	public MaintenanceWorkOrderAssetVo findWorkOrderAssetById(@RequestParam("id")String id) {
		MaintenanceWorkOrderAssetVo result = new MaintenanceWorkOrderAssetVo();
		try {
			MaintenanceWorkOrderAsset maintenanceWorkOrderAsset =maintenanceWorkOrderAssetService.findWorkOrderAssetById(id);
			ReflectionUtils.copyProperties(maintenanceWorkOrderAsset, result, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderAsset/findWorkOrderAssetById-----", e);
		}
		return result;
	}
	
	/**
	 * checkWorkOrderAsset: 检查工单是否已关联设备设施
	 * @param workOrderId 工单ID
	 * @param assetIds 设备ID
	 * @return boolean
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/checkWorkOrderAsset",method = RequestMethod.POST)
	public boolean checkWorkOrderAsset(@RequestParam("workOrderId") String workOrderId,@RequestParam("assetIds") List<String> assetIds) {
		boolean result=false;
		try {
			List<MaintenanceWorkOrderAsset> woassetList=maintenanceWorkOrderAssetService.findWorkOrderAssetListByWorkOrderIdAndAssetIds(workOrderId, assetIds);
            if (null==woassetList||woassetList.size()==0) {
            	result=true;
            }
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderAsset/checkWorkOrderAsset-----", e);
			return false;
		}
		return result;
	}

    /**
     * deleteWorkOrderAssetList:删除工单设备
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderAsset/deleteWorkOrderAssetList", method = RequestMethod.POST)
    public Boolean deleteWorkOrderAssetList(@RequestParam("ids") List<String> ids) {
    	try {
    		maintenanceWorkOrderAssetService.deleteWorkOrderAssetByIds(ids);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderAsset/deleteWorkOrderAssetList-----", e);
			return false;
		}
		return true;
    }

	/**
	 * deleteWorkOrderAssetByWorkOrderIds: 根据维保工单ID集合删除关联的设备
	 * @param workOrderIds 维保工单ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/deleteWorkOrderAssetByWorkOrderIds", method = RequestMethod.POST)
	public Boolean deleteWorkOrderAssetByWorkOrderIds(@RequestParam("workOrderIds") List<String> workOrderIds) {
		return maintenanceWorkOrderAssetService.deleteWorkOrderAssetByWorkOrderIds(workOrderIds);
	}
    
    /**
     * deleteWorkOrderAsset:删除工单设备
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderAsset/deleteWorkOrderAsset", method = RequestMethod.DELETE)
    public Boolean deleteWorkOrderAsset(@RequestParam("id")String id) {
    	try {
    		maintenanceWorkOrderAssetService.deleteWorkOrderAssetById(id);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderAsset/deleteWorkOrderAsset-----", e);
			return false;
		}
		return true;
    }

	/**
	 * deleteWorkOrderAssetByAssetIds: 删除维保工单关联的设备(多个)
	 * @param workOrderId 维保工单ID
	 * @param assetIds  设备ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workOrderAsset/deleteWorkOrderAssetByAssetIds")
	public Boolean deleteWorkOrderAssetByAssetIds(@RequestParam("workOrderId") String workOrderId,@RequestBody List<String> assetIds){
		return maintenanceWorkOrderAssetService.deleteWorkOrderAssetByAssetIds(workOrderId,assetIds);
	}
}