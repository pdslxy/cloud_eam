package com.enerbos.cloud.eam.client;

import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderNeedItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月27日
 * @Description EAM维保工单关联设备Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = WorkOrderNeedItemClientFallback.class)
public interface MaintenanceWorkOrderNeedItemClient {

	/**
     * saveNeedItem:保存工单子表（所需物料）
     * @param maintenanceWorkOrderNeedItemVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderNeedItemVo}
     * @return MaintenanceWorkOrderNeedItemVo
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/saveNeedItem", method = RequestMethod.POST)
    MaintenanceWorkOrderNeedItemVo saveNeedItem(@RequestBody MaintenanceWorkOrderNeedItemVo maintenanceWorkOrderNeedItemVo);
    
    /**
     * saveNeedItemList:保存工单子表（所需物料）
     * @param eamNeedItemVoList {@link}List<MaintenanceWorkOrderNeedItemVo>
     * @return List<MaintenanceWorkOrderNeedItemVo>
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/saveNeedItemList", method = RequestMethod.POST)
    List<MaintenanceWorkOrderNeedItemVo> saveNeedItemList(@RequestBody List<MaintenanceWorkOrderNeedItemVo> eamNeedItemVoList);
    
    /**
     * findNeedItemListByWorkOrderId:根据工单ID查询工单子表（所需物料）
     * @return List<MaintenanceWorkOrderNeedItemVo>
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/findNeedItemListByWorkOrderId", method = RequestMethod.GET)
    List<MaintenanceWorkOrderNeedItemVo> findNeedItemListByWorkOrderId(@RequestParam("workOrderId") String workOrderId);
    
	/**
	 * findNeedItemById: 根据ID查询工单子表（所需物料）
	 * @param id
	 * @return MaintenanceWorkOrderNeedItemVo 工单所需物料
	 */
	@RequestMapping(value = "/eam/micro/workOrderNeedItem/findNeedItemById",method = RequestMethod.GET)
	MaintenanceWorkOrderNeedItemVo findNeedItemById(@RequestParam("id") String id);
	
	/**
	 * findNeedItemById: 根据ID查询工单子表（所需物料）
	 * @param workOrderId
	 * @param itemIds
	 * @return MaintenanceWorkOrderNeedItemVo 工单所需物料
	 */
	@RequestMapping(value = "/eam/micro/workOrderNeedItem/findNeedItemById",method = RequestMethod.GET)
	boolean findNeedItemById(@RequestParam("workOrderId") String workOrderId,@RequestParam("itemIds") List<String> itemIds);
	
	/**
	 * validationOrderNeedItemByWorkOrderId: 分页工单子表（所需物料）
	 * @param workOrderId 工单ID
	 * @return boolean 预防工单实际物料集合
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/workOrderNeedItem/validationOrderNeedItemByWorkOrderId")
	boolean validationOrderNeedItemByWorkOrderId(@RequestParam("workOrderId") String workOrderId);
	
	/**
     * deleteNeedItem:删除工单子表（所需物料）
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/deleteNeedItem",method = RequestMethod.DELETE)
    Boolean deleteNeedItem(@RequestParam("id") String id);
    
    /**
     * deleteNeedItemList:删除工单（所需物料）
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderNeedItem/deleteNeedItemList", method = RequestMethod.POST)
    Boolean deleteNeedItemList(@RequestParam("ids") List<String> ids);
}

@Component
class  WorkOrderNeedItemClientFallback implements FallbackFactory<MaintenanceWorkOrderNeedItemClient> {
	@Override
	public MaintenanceWorkOrderNeedItemClient create(Throwable throwable) {
		return new MaintenanceWorkOrderNeedItemClient() {
			@Override
			public MaintenanceWorkOrderNeedItemVo saveNeedItem(MaintenanceWorkOrderNeedItemVo eamNeedItemVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderNeedItemVo findNeedItemById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public boolean findNeedItemById(String workOrderId, List<String> itemIds) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public boolean validationOrderNeedItemByWorkOrderId(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteNeedItem(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteNeedItemList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceWorkOrderNeedItemVo> saveNeedItemList(List<MaintenanceWorkOrderNeedItemVo> eamNeedItemVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceWorkOrderNeedItemVo> findNeedItemListByWorkOrderId(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}