package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.MaintenanceWorkOrderActualItemVo;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年07月11日
 * @Description EAM维保工单关联实际物料Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = WorkOrderActualItemClientFallback.class)
public interface MaintenanceWorkOrderActualItemClient {

	/**
     * saveWorkOrderActualItemList:保存工单实际物料
     * @param eamActualitemVoList {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderActualItemVo}
     * @return boolean 执行结果
     */
    @RequestMapping(value = "/eam/micro/workOrderActualItem/saveWorkOrderActualItemList", method = RequestMethod.POST)
    boolean saveWorkOrderActualItemList(@RequestParam("eamActualitemVoList") List<MaintenanceWorkOrderActualItemVo> eamActualitemVoList);
    
    /**
	 * findItemIdByWorkOrderId: 分页工单实际物料
	 * @param workOrderId 工单ID
	 * @return List<MaintenanceWorkOrderActualItemVo> 预防工单实际物料集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderActualItem/findItemIdByWorkOrderId",method = RequestMethod.GET)
	List<MaintenanceWorkOrderActualItemVo> findItemIdByWorkOrderId(@RequestParam("workOrderId")String workOrderId);

	/**
	 * findActualitemByWorkOrderId: 根据工单ID查询工单实际物料
	 * @param assetId 设备ID
	 * @return List<String> 返回工单物料ID集合
	 */
	@RequestMapping(value = "/eam/micro/workOrderActualItem/findItemIdByAssetId",method = RequestMethod.GET)
	List<String> findItemIdByAssetId(@RequestParam("assetId")String assetId);
	
	/**
     * deleteActualItemList:删除工单实际物料
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workOrderActualItem/deleteActualItemList", method = RequestMethod.POST)
    Boolean deleteActualItem(@RequestParam("ids") List<String> ids);
}

@Component
class  WorkOrderActualItemClientFallback implements FallbackFactory<MaintenanceWorkOrderActualItemClient> {
	@Override
	public MaintenanceWorkOrderActualItemClient create(Throwable throwable) {
		return new MaintenanceWorkOrderActualItemClient() {
			@Override
			public boolean saveWorkOrderActualItemList(List<MaintenanceWorkOrderActualItemVo> eamActualitemVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceWorkOrderActualItemVo> findItemIdByWorkOrderId(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteActualItem(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<String> findItemIdByAssetId(String assetId) {
				throw new RuntimeException(throwable.getMessage());
	}
		};
	}
}