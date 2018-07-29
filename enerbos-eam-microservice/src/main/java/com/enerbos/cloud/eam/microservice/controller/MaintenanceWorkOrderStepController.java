package com.enerbos.cloud.eam.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderStep;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderService;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderStepService;
import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderStepVo;
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
 * @date 2017年06月28日
 * @Description EAM维保工单关联任务步骤接口
 */
@RestController
public class MaintenanceWorkOrderStepController {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderStepController.class);

    @Resource
    protected MaintenanceWorkOrderService maintenanceWorkOrderService;
    
    @Resource
    protected MaintenanceWorkOrderStepService eamOrderStepService;
    
    /**
     * implementAllOrder:执行工单子表（任务步骤）
     * @param workOrderId 维保工单ID
     * @return MaintenanceWorkOrderStepVo
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/implementAllOrder", method = RequestMethod.POST)
    public boolean implementAllOrder(@RequestParam("workOrderId") String workOrderId){
    	try {
			eamOrderStepService.implementAllOrder(workOrderId);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderOrderStep/implementAllOrder-----", e);
			return false;
		}
    	return true;
    }
    
    /**
     * saveOrderstep:保存工单子表（任务步骤）
     * @param maintenanceWorkOrderStepVo
     * @return MaintenanceWorkOrderStepVo
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/saveOrderStep", method = RequestMethod.POST)
    public MaintenanceWorkOrderStepVo saveOrderStep(@RequestBody MaintenanceWorkOrderStepVo maintenanceWorkOrderStepVo){
    	MaintenanceWorkOrderStep maintenanceWorkOrderStep=new MaintenanceWorkOrderStep();
    	try {
			ReflectionUtils.copyProperties(maintenanceWorkOrderStepVo, maintenanceWorkOrderStep, null);
			maintenanceWorkOrderStep=eamOrderStepService.saveEamOrderstep(maintenanceWorkOrderStep);
			maintenanceWorkOrderStepVo.setId(maintenanceWorkOrderStep.getId());
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderOrderStep/saveOrderStep-----", e);
		}
    	return maintenanceWorkOrderStepVo;
    }
    
    /**
	 * findOrderStepById: 根据ID查询工单子表（任务步骤）
	 * @param id
	 * @return MaintenanceWorkOrderStepVo 工单子表（任务步骤）集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderOrderStep/findOrderStepById",method = RequestMethod.GET)
	public MaintenanceWorkOrderStepVo findOrderStepById(@RequestParam("id") String id) {
		MaintenanceWorkOrderStepVo maintenanceWorkOrderStepVo = new MaintenanceWorkOrderStepVo();
		try {
			maintenanceWorkOrderStepVo=eamOrderStepService.findEamOrderstepById(id);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderOrderStep/findOrderStepById-----", e);
		}
		return maintenanceWorkOrderStepVo;
	}
	
	/**
	 * findOrderStepByWorkOrderId: 根据工单ID查询所有工单子表（任务步骤）
	 * @return List<MaintenanceWorkOrderStepVo> 工单子表（任务步骤）集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderOrderStep/findOrderStepByWorkOrderId",method = RequestMethod.GET)
	public List<MaintenanceWorkOrderStepVo> findOrderStepByWorkOrderId(@RequestParam("workOrderId") String workOrderId) {
		List<MaintenanceWorkOrderStepVo> eamOrderstepVoList=new ArrayList<>();
		try {
			List<MaintenanceWorkOrderStep> eamOrderstepList=eamOrderStepService.findOrderStepByWorkOrderId(workOrderId);
			MaintenanceWorkOrderStepVo maintenanceWorkOrderStepVo=null;
			for (MaintenanceWorkOrderStep maintenanceWorkOrderStep : eamOrderstepList) {
				maintenanceWorkOrderStepVo=new MaintenanceWorkOrderStepVo();
				ReflectionUtils.copyProperties(maintenanceWorkOrderStep, maintenanceWorkOrderStepVo, null);
				eamOrderstepVoList.add(maintenanceWorkOrderStepVo);
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderOrderStep/findOrderStepByWorkOrderId-----", e);
		}
		return eamOrderstepVoList;
	}
    
//    /**
//	 * findOrderStepByPage: 分页工单子表（任务步骤）
//	 * @param EamOrderstepSelectVo 查询条件
//	 * @return PageInfo<MaintenanceWorkOrderStepVo> 工单子表（任务步骤）集合
//	 */
//	@RequestMapping(value = "/eam/micro/workOrderOrderStep/findOrderStepByPage",method = RequestMethod.POST)
//	public PageInfo<MaintenanceWorkOrderStepVo> findOrderStepByPage(@RequestBody EamOrderstepSelectVo eamOrderstepSelectVo) {
//		List<MaintenanceWorkOrderStepVo> result = null;
//		try {
//			result = eamOrderStepService.findEamOrderstep(eamOrderstepSelectVo);
//		} catch (Exception e) {
//			logger.error("-------/eam/micro/workOrderOrderStep/findOrderStepByPage-----", e);
//		}
//		return new PageInfo<MaintenanceWorkOrderStepVo>(result);
//	}
    
    /**
     * deleteOrderStep:删除工单子表（任务步骤）
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/deleteOrderStep",method = RequestMethod.DELETE)
    public Boolean deleteOrderStep(@RequestParam("id") String id) {
    	try {
    		eamOrderStepService.deleteEamOrderstepById(id);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderOrderStep/deleteOrderStep-----", e);
			return false;
		}
		return true;
    }
    
    /**
     * deleteOrderStepList:删除工单子表（任务步骤）
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/deleteOrderStepList",method = RequestMethod.POST)
    public Boolean deleteOrderStepList(@RequestBody List<String> ids) {
    	try {
    		eamOrderStepService.deleteEamOrderstepByIds(ids);
		} catch (Exception e) {
			logger.error("-------/eam/micro/workOrderOrderStep/deleteOrderStepList-----", e);
			return false;
		}
		return true;
    }
}