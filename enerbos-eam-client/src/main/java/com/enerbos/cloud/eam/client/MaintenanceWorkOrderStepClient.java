package com.enerbos.cloud.eam.client;

import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderStepVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月28日
 * @Description EAM维保工单任务步骤Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = WorkOrderOrderStepClientFallback.class)
public interface MaintenanceWorkOrderStepClient {
	
	 /**
     * implementAllOrder:执行工单子表（任务步骤）
     * @param workOrderId维保工单ID
     * @return MaintenanceWorkOrderStepVo
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/implementAllOrder", method = RequestMethod.POST)
    boolean implementAllOrder(@RequestParam("workOrderId") String workOrderId);
    
    /**
     * saveOrderStep:保存工单子表（任务步骤）
     * @param MaintenanceWorkOrderStepVo
     * @return MaintenanceWorkOrderStepVo
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/saveOrderStep", method = RequestMethod.POST)
    MaintenanceWorkOrderStepVo saveOrderStep(@RequestBody MaintenanceWorkOrderStepVo maintenanceWorkOrderStepVo);
    
    /**
	 * findOrderStepById: 根据ID查询工单子表（任务步骤）
	 * @param id
	 * @return MaintenanceWorkOrderStepVo 工单子表（任务步骤）集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderOrderStep/findOrderStepById",method = RequestMethod.GET)
	MaintenanceWorkOrderStepVo findOrderStepById(@RequestParam("id") String id);
	
	/**
	 * findOrderStepByWorkOrderId: 根据工单ID查询所有工单子表（任务步骤）
	 * @return List<MaintenanceWorkOrderStepVo> 工单子表（任务步骤）集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderOrderStep/findOrderStepByWorkOrderId",method = RequestMethod.GET)
	List<MaintenanceWorkOrderStepVo> findOrderStepByWorkOrderId(@RequestParam("workOrderId") String workOrderId);
	
	/**
     * deleteOrderStep:删除工单子表（任务步骤）
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/deleteOrderStep",method = RequestMethod.DELETE)
    Boolean deleteOrderStep(@RequestParam("id") String id);
    
    /**
     * deleteOrderStepList:删除工单子表（任务步骤）
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderOrderStep/deleteOrderStepList",method = RequestMethod.POST)
    Boolean deleteOrderStepList(@RequestBody List<String> ids);
}

@Component
class  WorkOrderOrderStepClientFallback implements FallbackFactory<MaintenanceWorkOrderStepClient> {
	@Override
	public MaintenanceWorkOrderStepClient create(Throwable throwable) {
		return new MaintenanceWorkOrderStepClient() {
			@Override
			public boolean implementAllOrder(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderStepVo saveOrderStep(MaintenanceWorkOrderStepVo maintenanceWorkOrderStepVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderStepVo findOrderStepById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteOrderStep(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteOrderStepList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceWorkOrderStepVo> findOrderStepByWorkOrderId(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}