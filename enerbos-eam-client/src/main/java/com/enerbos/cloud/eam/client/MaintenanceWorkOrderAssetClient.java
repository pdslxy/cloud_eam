package com.enerbos.cloud.eam.client;

import java.util.List;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderAssetVo;

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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = WorkOrderAssetClientFallback.class)
public interface MaintenanceWorkOrderAssetClient {

	/**
     * saveWorkOrderAsset:添加工单和设备设施关联数据
     * @param eamWorkOrderAssetVoList 工单和设备设施关联Vo
     * @return List<MaintenanceWorkOrderAssetVo>返回添加成功后结果
     */
    @RequestMapping(value = "/eam/micro/workOrderAsset/addAssetToWorkOrder", method = RequestMethod.POST)
    List<MaintenanceWorkOrderAssetVo> saveWorkOrderAsset(@RequestBody List<MaintenanceWorkOrderAssetVo> eamWorkOrderAssetVoList);
    
    /**
	 * findAssetIdByWorkOrderId: 查询工单设备列表
	 * @param workOrderId 工单ID
	 * @return List<String> 工单关联设备的设备ID集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/findAssetIdByWorkOrderId",method = RequestMethod.GET)
	List<String> findAssetIdByWorkOrderId(@RequestParam("workOrderId")String workOrderId);
    
	/**
	 * checkWorkOrderAsset: 检查工单是否已关联设备设施
	 * @param workOrderId 工单ID
	 * @param assetIds 设备ID
	 * @return boolean
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/checkWorkOrderAsset",method = RequestMethod.POST)
	boolean checkWorkOrderAsset(@RequestParam("workOrderId") String workOrderId,@RequestParam("assetIds") List<String> assetIds);
	
	/**
	 * findWorkOrderAssetById: 查询工单设备
	 * @param id ID
	 * @return MaintenanceWorkOrderAssetVo 工单关联设备的设备
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/findWorkOrderAssetById",method = RequestMethod.GET)
	MaintenanceWorkOrderAssetVo findWorkOrderAssetById(@RequestParam("id")String id);
	
	/**
     * deleteWorkOrderAssetList:删除工单设备
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderAsset/deleteWorkOrderAssetList", method = RequestMethod.POST)
    Boolean deleteWorkOrderAssetList(@RequestParam("ids") List<String> ids);
	
    /**
     * deleteWorkOrderAsset:删除工单设备
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderAsset/deleteWorkOrderAsset", method = RequestMethod.DELETE)
    Boolean deleteWorkOrderAsset(@RequestParam("id")String id);
    
    /**
	 * deleteWorkOrderAssetByWorkOrderIds: 根据维保工单ID集合删除关联的设备
	 * @param workOrderIds 维保工单ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(value = "/eam/micro/workOrderAsset/deleteWorkOrderAssetByWorkOrderIds", method = RequestMethod.POST)
	public Boolean deleteWorkOrderAssetByWorkOrderIds(@RequestParam("workOrderIds") List<String> workOrderIds);

	/**
	 * deleteWorkOrderAssetByAssetIds: 删除维保工单关联的设备(多个)
	 * @param workOrderId 维保工单ID
	 * @param assetIds  设备ID集合
	 * @return Boolean 删除是否成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workOrderAsset/deleteWorkOrderAssetByAssetIds")
	Boolean deleteWorkOrderAssetByAssetIds(@RequestParam("workOrderId") String workOrderId,@RequestBody List<String> assetIds);
}

@Component
class  WorkOrderAssetClientFallback implements FallbackFactory<MaintenanceWorkOrderAssetClient> {
	@Override
	public MaintenanceWorkOrderAssetClient create(Throwable throwable) {
		return new MaintenanceWorkOrderAssetClient() {
			@Override
			public List<MaintenanceWorkOrderAssetVo> saveWorkOrderAsset(List<MaintenanceWorkOrderAssetVo> eamWorkOrderAssetVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<String> findAssetIdByWorkOrderId(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteWorkOrderAssetList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteWorkOrderAsset(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderAssetVo findWorkOrderAssetById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public boolean checkWorkOrderAsset(String workOrderId, List<String> assetIds) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteWorkOrderAssetByWorkOrderIds(List<String> workOrderIds) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteWorkOrderAssetByAssetIds(String workOrderId, List<String> assetIds) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}