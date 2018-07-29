package com.enerbos.cloud.eam.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderNeedItem;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderNeedItemService;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderNeedItemVo;
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
public class MaintenanceWorkOrderNeedItemController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderNeedItemController.class);
    
    @Resource
    protected MaintenanceWorkOrderNeedItemService maintenanceWorkOrderNeedItemService;
    
    /**
     * saveNeedItemList:保存工单子表（所需物料）
     * @param List<MaintenanceWorkOrderNeedItemVo>
     * @return List<MaintenanceWorkOrderNeedItemVo>
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/saveNeedItemList", method = RequestMethod.POST)
    public List<MaintenanceWorkOrderNeedItemVo> saveNeedItemList(@RequestBody List<MaintenanceWorkOrderNeedItemVo> eamNeedItemVoList) throws Exception{
    	List<MaintenanceWorkOrderNeedItem> eamNeedItemList=new ArrayList<>();
		MaintenanceWorkOrderNeedItem maintenanceWorkOrderNeedItem;
		for (MaintenanceWorkOrderNeedItemVo maintenanceWorkOrderNeedItemVo : eamNeedItemVoList) {
			maintenanceWorkOrderNeedItem = new MaintenanceWorkOrderNeedItem();
			ReflectionUtils.copyProperties(maintenanceWorkOrderNeedItemVo, maintenanceWorkOrderNeedItem, null);
			eamNeedItemList.add(maintenanceWorkOrderNeedItem);
		}
		eamNeedItemList=maintenanceWorkOrderNeedItemService.saveEamNeedItemList(eamNeedItemList);
		MaintenanceWorkOrderNeedItemVo maintenanceWorkOrderNeedItemVo;
		eamNeedItemVoList=new ArrayList<>();
		for (MaintenanceWorkOrderNeedItem eamNeedItem1 : eamNeedItemList) {
			maintenanceWorkOrderNeedItemVo = new MaintenanceWorkOrderNeedItemVo();
			ReflectionUtils.copyProperties(eamNeedItem1, maintenanceWorkOrderNeedItemVo, null);
			eamNeedItemVoList.add(maintenanceWorkOrderNeedItemVo);
		}
    	return eamNeedItemVoList;
    }
    
    /**
     * findNeedItemListByWorkOrderId:根据工单ID查询工单子表（所需物料）
     * @return List<MaintenanceWorkOrderNeedItemVo>
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/findNeedItemListByWorkOrderId", method = RequestMethod.GET)
    public List<MaintenanceWorkOrderNeedItemVo> findNeedItemListByWorkOrderId(@RequestParam("workOrderId") String workOrderId) throws Exception{
    	return maintenanceWorkOrderNeedItemService.findEamNeedItemAllByWorkOrderId(workOrderId);
    }
    
//	/**
//	 * findNeedItemById: 根据ID查询工单子表（所需物料）
//	 * @param id
//	 * @return MaintenanceWorkOrderNeedItemVo 工单所需物料
//	 */
//	@RequestMapping(value = "/eam/micro/workOrderNeedItem/findNeedItemById",method = RequestMethod.GET)
//	public MaintenanceWorkOrderNeedItemVo findNeedItemById(@RequestParam("id") String id) {
//		MaintenanceWorkOrderNeedItemVo result =new MaintenanceWorkOrderNeedItemVo();
//		try {
//			result = maintenanceWorkOrderNeedItemService.findEamNeedItemById(id);
//		} catch (Exception e) {
//			logger.error("-------/eam/micro/workOrderNeedItem/findNeedItemById-----", e);
//		}
//		return result;
//	}
//	
//	/**
//	 * checkNeedItemByWorkOrderIdAndItemId: 根据workOrderId和itemIds查询是否存在工单所需物料
//	 * @param workOrderId
//	 * @param itemIds
//	 * @return MaintenanceWorkOrderNeedItemVo 工单所需物料
//	 */
//	@RequestMapping(value = "/eam/micro/workOrderNeedItem/checkNeedItemByWorkOrderIdAndItemId",method = RequestMethod.GET)
//	public boolean checkNeedItemByWorkOrderIdAndItemId(@RequestParam("workOrderId") String workOrderId,@RequestParam("itemIds") List<String> itemIds) {
//		boolean result=false;
//		try {
//			List<MaintenanceWorkOrderNeedItemVo> needItemList=maintenanceWorkOrderNeedItemService.findNeedItemListByWorkOrderIdAndItemIds(workOrderId, itemIds);
//            if (null==needItemList||needItemList.size()==0) {
//            	result=true;
//            }
//		} catch (Exception e) {
//			logger.error("-------/eam/micro/workOrderNeedItem/findNeedItemById-----", e);
//		}
//		return result;
//	}
//	
//	/**
//	 * validationOrderNeedItemByWorkOrderId: 校验工单所需物料数量是否为空以及工单ID是否为空
//	 * @param workOrderId 工单ID
//	 * @return boolean 校验结果
//	 */
//	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/workOrderNeedItem/validationOrderNeedItemByWorkOrderId")
//	public boolean validationOrderNeedItemByWorkOrderId(@RequestParam("key") String key,@RequestParam("workOrderId") String workOrderId) {
//		boolean result = false;
//    	if (StringUtils.isBlank(key)) {
//    		result = maintenanceWorkOrderNeedItemService.validationOrderNeedItemByWorkOrderId(workOrderId);
//		} else {
//			EamWorkOrderForStepAndItemVo eamWorkOrderForStepAndItemVo=redisService.getObject(key, EamWorkOrderForStepAndItemVo.class);
//			List<MaintenanceWorkOrderNeedItemVo> eamNeedItemVoList = (List<MaintenanceWorkOrderNeedItemVo>) eamWorkOrderForStepAndItemVo.getEamNeedItemVoList().values();
//			for (MaintenanceWorkOrderNeedItemVo eamNeedItemVo : eamNeedItemVoList) {
//				if (StringUtils.isBlank(eamNeedItemVo.getItemQty())){
//					return false; 
//				}
//			}
//		}
//		return result;
//	}
//
//    /**
//     * deleteNeedItem:删除工单子表（所需物料）
//     * @param id
//     * @return Boolean 是否删除成功
//     */
//    @RequestMapping(value = "/eam/micro/workOrderNeedItem/deleteNeedItem",method = RequestMethod.DELETE)
//    public String deleteNeedItem(@RequestParam("key") String key,@RequestParam("id") String id) {
//    	Map<String, MaintenanceWorkOrderNeedItemVo> map=new HashMap<>();
//    	if (StringUtils.isBlank(key)) {
//    		MaintenanceWorkOrderNeedItemVo eamNeedItemVo=maintenanceWorkOrderNeedItemService.findEamNeedItemById(id);
//    		if (null==eamNeedItemVo) {
//    			throw new EnerbosException("", "物资不存在");
//    		}
//    		key=EamCommonUtil.buildKey();
//    		List<MaintenanceWorkOrderNeedItemVo> eamNeedItemList=maintenanceWorkOrderNeedItemService.findEamNeedItemAllByWorkOrderId(eamNeedItemVo.getWorkOrderId());
//        	for (MaintenanceWorkOrderNeedItemVo eamNeedItem : eamNeedItemList) {
//        		map.put(eamNeedItem.getId(), eamNeedItem);
//    		}
//		} else {
//			EamWorkOrderForStepAndItemVo eamWorkOrderForStepAndItemVo=redisService.getObject(key, EamWorkOrderForStepAndItemVo.class);
//			map=eamWorkOrderForStepAndItemVo.getEamNeedItemVoList();
//		}
//    	EamWorkOrderForStepAndItemVo eamWorkOrderForStepAndItemVo=new EamWorkOrderForStepAndItemVo();
//		MaintenanceWorkOrderNeedItemVo eamNeedItemVos=map.get(id);
//		if (null==eamNeedItemVos) {
//			throw new EnerbosException("", "物资不存在");
//		}
//		eamNeedItemVos.setStatus(Common.WORK_ORDER_REDIS_STATUS_DELETE);
//		map.put(id, eamNeedItemVos);
//    	eamWorkOrderForStepAndItemVo.setEamNeedItemVoList(map);
//    	redisService.setObject(key, eamWorkOrderForStepAndItemVo);
//		return key;
//    }
    
    /**
     * deleteNeedItemList:删除工单（所需物料）
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/deleteNeedItemList", method = RequestMethod.POST)
    public boolean deleteNeedItemList(@RequestParam("ids") List<String> ids) {
		return maintenanceWorkOrderNeedItemService.deleteEamNeedItemByIds(ids);
    }
}