package com.enerbos.cloud.eam.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderActualItem;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderActualItemService;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderActualItemVo;
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
public class MaintenanceWorkOrderActualItemController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderActualItemController.class);

    @Resource
    protected MaintenanceWorkOrderActualItemService eamActualItemService;
    
    /**
     * saveWorkOrderActualItemList:保存工单实际物料
     * @param List<MaintenanceWorkOrderActualItemVo>
     * @return List<MaintenanceWorkOrderActualItemVo>
     */
    @RequestMapping(value = "/eam/micro/workOrderActualItem/saveWorkOrderActualItemList", method = RequestMethod.POST)
    public boolean saveWorkOrderActualItemList(@RequestParam("eamActualitemVoList") List<MaintenanceWorkOrderActualItemVo> eamActualitemVoList){
    	List<MaintenanceWorkOrderActualItem> eamActualitemList=new ArrayList<>();
    	boolean result=false;
		try {
			MaintenanceWorkOrderActualItem maintenanceWorkOrderActualItem;
			for (MaintenanceWorkOrderActualItemVo maintenanceWorkOrderActualItemVo : eamActualitemVoList) {
				maintenanceWorkOrderActualItem = new MaintenanceWorkOrderActualItem();
				ReflectionUtils.copyProperties(maintenanceWorkOrderActualItemVo, maintenanceWorkOrderActualItem, null);
				eamActualitemList.add(maintenanceWorkOrderActualItem);
			}
			eamActualitemList=eamActualItemService.saveEamActualItemList(eamActualitemList);
			result=true;
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderActualItem/saveActualItem-----", e);
		}
    	return result;
    }
    
    /**
	 * findItemIdByWorkOrderId: 分页工单实际物料
	 * @param workOrderId 维保工单ID
	 * @return List<MaintenanceWorkOrderActualItemVo> 预防工单实际物料集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderActualItem/findItemIdByWorkOrderId",method = RequestMethod.GET)
	public List<MaintenanceWorkOrderActualItemVo> findItemIdByWorkOrderId(@RequestParam("workOrderId")String workOrderId) {
		List<MaintenanceWorkOrderActualItemVo> result = new ArrayList<>();
		try {
			List<MaintenanceWorkOrderActualItem> eamActualitemList=eamActualItemService.findEamActualItemByWorkOrderId(workOrderId);
			MaintenanceWorkOrderActualItemVo maintenanceWorkOrderActualItemVo;
			for (MaintenanceWorkOrderActualItem maintenanceWorkOrderActualItem : eamActualitemList) {
				maintenanceWorkOrderActualItemVo = new MaintenanceWorkOrderActualItemVo();
				ReflectionUtils.copyProperties(maintenanceWorkOrderActualItem, maintenanceWorkOrderActualItemVo, null);
				result.add(maintenanceWorkOrderActualItemVo);
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderActualItem/findItemIdByWorkOrderId-----", e);
		}
		return result;
	}

	/**
	 * findActualitemByWorkOrderId: 根据工单ID查询工单实际物料
	 * @param assetId 设备ID
	 * @return List<String> 返回工单物料ID集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderActualItem/findItemIdByAssetId",method = RequestMethod.GET)
	public List<String> findItemIdByAssetId(@RequestParam("assetId")String assetId) {
		return eamActualItemService.findItemIdByAssetId(assetId);
	}

    /**
     * deleteActualItemList:删除工单实际物料
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderActualItem/deleteActualItemList", method = RequestMethod.POST)
    public Boolean deleteActualItem(@RequestParam("ids") List<String> ids) {
    	try {
    		eamActualItemService.deleteEamActualItemByIds(ids);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderActualItem/deleteActualItem-----", e);
			return false;
		}
		return true;
    }
}