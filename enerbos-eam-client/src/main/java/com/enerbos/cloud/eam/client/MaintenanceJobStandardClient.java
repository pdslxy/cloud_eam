package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @date 2017年06月07日
 * @Description EAM作业标准Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaintenanceJobStandardClientFallback.class)
public interface MaintenanceJobStandardClient {
	/**
     * saveJobStandard:保存作业标准
     * @param maintenanceJobStandardVo
     * @return MaintenanceJobStandardVo
     */
    @RequestMapping(value = "/eam/micro/jobStandard/saveJobStandard", method = RequestMethod.POST)
    MaintenanceJobStandardVo saveJobStandard(@RequestBody MaintenanceJobStandardVo maintenanceJobStandardVo);

	/**
	 * updateJobStandardStatus:变更作业标准状态
	 * @param statusVo {@link com.enerbos.cloud.eam.vo.StatusVo}
	 * @return StatusVo
	 */
	@RequestMapping(value = "/eam/micro/jobStandard/updateJobStandardStatus", method = RequestMethod.POST)
	public StatusVo updateJobStandardStatus(@RequestBody StatusVo statusVo);

    /**
	 * findJobStandardById:查询作业标准
	 * @param id 
	 * @return MaintenanceJobStandardVo 作业标准VO
	 */
	@RequestMapping(value = "/eam/micro/jobStandard/findJobStandardById", method = RequestMethod.GET)
	MaintenanceJobStandardVo findJobStandardById(@RequestParam("id") String id);
	
	/**
	 * findJobStandardByJobStandardNum:查询作业标准
	 * @param jobStandardNum 作业编码
	 * @return MaintenanceJobStandardVo 作业标准VO
	 */
	@RequestMapping(value = "/eam/micro/jobStandard/findJobStandardByJobStandardNum", method = RequestMethod.GET)
	MaintenanceJobStandardVo findJobStandardByJobStandardNum(@RequestParam("jobStandardNum") String jobStandardNum);
	
	/**
	 * findPageJobStandardList: 分页查询作业标准
	 * @param maintenanceJobStandardSelectVo 查询条件
	 * @return EnerbosPage<MaintenanceJobStandardVo> 与作业标准关联的物料VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/findPageJobStandardList")
	EnerbosPage<MaintenanceJobStandardForListVo> findPageJobStandardList(@RequestBody MaintenanceJobStandardSelectVo maintenanceJobStandardSelectVo);
	
	/**
	 * findPageJobStandardByAssetId:根据设备ID分页查询作业标准
	 * @param maintenanceForAssetFilterVo 根据设备查询作业标准列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return EnerbosPage<MaintenanceJobStandardForListVo> 预防性维护计划Vo集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/findPageJobStandardByAssetId")
	EnerbosPage<MaintenanceJobStandardForListVo> findPageJobStandardByAssetId(@RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);
	
	/**
     * deleteJobStandardList:删除选中的作业标准
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/jobStandard/deleteJobStandardList", method = RequestMethod.POST)
    Boolean deleteJobStandardList(@RequestBody List<String> ids);
    
    /**
     * deleteJobStandard:删除选作业标准
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/jobStandard/deleteJobStandard", method = RequestMethod.POST)
    Boolean deleteJobStandard(@RequestParam("id")  String id);

	/**
	 * 收藏作业标准
	 * @param maintenanceJobStandardRfCollectorVoList 收藏的作业标准列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/collect")
	public void collectJobStandard(@RequestBody List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList);

	/**
	 * 取消收藏作业标准
	 * @param maintenanceJobStandardRfCollectorVoList 需要取消收藏的作业标准列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/collect/cancel")
	public void cancelCollectJobStandard(@RequestBody List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList);

	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/saveBatchStandard")
	List<MaintenanceJobStandardVo> saveBatchStandard(@RequestBody  InitEamSet initEamSet);
}

@Component
class  MaintenanceJobStandardClientFallback implements FallbackFactory<MaintenanceJobStandardClient> {
	@Override
	public MaintenanceJobStandardClient create(Throwable throwable) {
		return new MaintenanceJobStandardClient() {
			@Override
			public MaintenanceJobStandardVo saveJobStandard(MaintenanceJobStandardVo maintenanceJobStandardVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public StatusVo updateJobStandardStatus(StatusVo statusVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceJobStandardVo findJobStandardById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceJobStandardVo findJobStandardByJobStandardNum(String jobStandardNum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<MaintenanceJobStandardForListVo> findPageJobStandardList(
					MaintenanceJobStandardSelectVo maintenanceJobStandardSelectVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<MaintenanceJobStandardForListVo> findPageJobStandardByAssetId(
					MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteJobStandardList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteJobStandard(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void collectJobStandard(List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void cancelCollectJobStandard(List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<MaintenanceJobStandardVo> saveBatchStandard(@RequestBody InitEamSet initEamSet) {
				return null;
			}
		};
	}
}