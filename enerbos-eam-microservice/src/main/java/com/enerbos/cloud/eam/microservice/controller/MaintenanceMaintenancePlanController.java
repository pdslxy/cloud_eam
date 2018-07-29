package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlan;
import com.enerbos.cloud.eam.microservice.service.MaintenanceMaintenancePlanService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
public class MaintenanceMaintenancePlanController {

	private Logger logger = LoggerFactory.getLogger(MaintenanceMaintenancePlanController.class);
	
    @Autowired
    private MaintenanceMaintenancePlanService maintenancePlanService;

    /**
     * saveMaintenancePlan:保存预防维护计划
     * @param maintenanceMaintenancePlanVo {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
     * @return MaintenanceMaintenancePlanVo
     */
    @RequestMapping(value = "/eam/micro/maintenancePlan/saveMaintenancePlan", method = RequestMethod.POST)
    public MaintenanceMaintenancePlanVo saveMaintenancePlan(@RequestBody MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo){
    	MaintenanceMaintenancePlan maintenanceMaintenancePlan=new MaintenanceMaintenancePlan();
    	try {
			ReflectionUtils.copyProperties(maintenanceMaintenancePlanVo, maintenanceMaintenancePlan, null);
			maintenanceMaintenancePlan=maintenancePlanService.saveMaintenancePlan(maintenanceMaintenancePlan);
			maintenanceMaintenancePlanVo=new MaintenanceMaintenancePlanVo();
			ReflectionUtils.copyProperties(maintenanceMaintenancePlan, maintenanceMaintenancePlanVo, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/maintenancePlan/saveMaintenancePlan-----", e);
		}
    	return maintenanceMaintenancePlanVo;
    }

	/**
	 * updateMaintenancePlanStatus:变更预防维护计划状态
	 * @param statusVo {@link com.enerbos.cloud.eam.vo.StatusVo}
	 * @return StatusVo
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/updateMaintenancePlanStatus", method = RequestMethod.POST)
	public StatusVo updateMaintenancePlanStatus(@RequestBody StatusVo statusVo){
		MaintenanceMaintenancePlan maintenanceMaintenancePlan;
		try {
			List<String> ids=statusVo.getIds();
			if (ids != null&&!ids.isEmpty()) {
				for (String id:ids){
					maintenanceMaintenancePlan=new MaintenanceMaintenancePlan();
					MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo=maintenancePlanService.findMaintenancePlanById(id);
					ReflectionUtils.copyProperties(maintenanceMaintenancePlanVo, maintenanceMaintenancePlan, null);
					maintenanceMaintenancePlan.setStatus(statusVo.getStatus());
					maintenanceMaintenancePlan.setStatusDate(new Date());
					maintenancePlanService.saveMaintenancePlan(maintenanceMaintenancePlan);
				}
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/maintenancePlan/updateMaintenancePlanStatus-----", e);
		}
		return statusVo;
	}

	/**
	 * findMaintenancePlanById:根据ID查询预防维护计划
	 * @param id 
	 * @return MaintenanceMaintenancePlanVo 预防维护计划VO
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/findMaintenancePlanById", method = RequestMethod.GET)
	public MaintenanceMaintenancePlanVo findMaintenancePlanById(@RequestParam("id") String id){
		MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo=maintenancePlanService.findMaintenancePlanById(id);
    	return maintenanceMaintenancePlanVo;
	}
	
	/**
	 * findMaintenancePlanByMaintenancePlanNum:根据MaintenancePlanNum查询预防维护计划
	 * @param maintenancePlanNum 预防性维护编码
	 * @return MaintenanceMaintenancePlanVo 预防维护计划VO
	 */
	@RequestMapping(value = "/eam/micro/maintenancePlan/findMaintenancePlanByMaintenancePlanNum", method = RequestMethod.GET)
	public MaintenanceMaintenancePlanVo findMaintenancePlanByMaintenancePlanNum(@RequestParam("maintenancePlanNum") String maintenancePlanNum){
		MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo=maintenancePlanService.findMaintenancePlanByMaintenancePlanNum(maintenancePlanNum);
    	return maintenanceMaintenancePlanVo;
	}

	/**
	 * findPageMaintenancePlanList: 分页查询预防维护计划
	 * @param maintenanceMaintenancePlanSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
	 * @return PageInfo<MaintenanceMaintenancePlanVo> 预防维护计划VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/findPageMaintenancePlanList")
	public PageInfo<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanList(@RequestBody MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo) {
		List<MaintenanceMaintenancePlanForListVo> result = maintenancePlanService.findPageMaintenancePlanList(maintenanceMaintenancePlanSelectVo);
		return new PageInfo<MaintenanceMaintenancePlanForListVo>(result);
	}

	/**
	 * findAllMaintenancePlanByStatus:根据状态查询预防维护计划
	 * @param status 状态
	 * @return List<MaintenanceMaintenancePlanVo> {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanVo}
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/findAllMaintenancePlanByStatus")
	public List<MaintenanceMaintenancePlanVo> findAllMaintenancePlanByStatus(String status){
		return maintenancePlanService.findAllMaintenancePlanByStatus(status);
	}
	
    /**
     * deleteMaintenancePlanList:删除选中的预防维护计划
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/maintenancePlan/deleteMaintenancePlanList", method = RequestMethod.POST)
    public Boolean deleteMaintenancePlanList(@RequestBody List<String> ids) {
		return maintenancePlanService.deleteMaintenancePlanByIds(ids);
    }

	/**
	 * findPageMaintenancePlanByAssetId:根据设备ID分页查询预防性维护计划
	 * @param maintenanceForAssetFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return PageInfo<MaintenanceMaintenancePlanVo> 预防维护计划VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/findPageMaintenancePlanByAssetId")
	public PageInfo<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanByAssetId(@RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
		List<MaintenanceMaintenancePlanForListVo> result = maintenancePlanService.findPageMaintenancePlanByAssetId(maintenanceForAssetFilterVo);
		return new PageInfo<MaintenanceMaintenancePlanForListVo>(result);
	}

	/**
	 * 收藏预防性维护计划
	 * @param maintenanceMaintenancePlanRfCollectorVoList 收藏的预防性维护计划列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/collect")
	public void collectMaintenancePlan(@RequestBody List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList) {
		logger.info("/eam/micro/maintenancePlan/collect, params: [{}]", maintenanceMaintenancePlanRfCollectorVoList);

		maintenancePlanService.collectMaintenancePlan(maintenanceMaintenancePlanRfCollectorVoList);
	}

	/**
	 * 取消收藏预防性维护计划
	 * @param maintenanceMaintenancePlanRfCollectorVoList 需要取消收藏的预防性维护计划列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/collect/cancel")
	public void cancelCollectMaintenancePlan(@RequestBody List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList) {
		logger.info("/eam/micro/maintenancePlan/collect/cancel, params: [{}]", maintenanceMaintenancePlanRfCollectorVoList);

		maintenancePlanService.cancelCollectMaintenancePlan(maintenanceMaintenancePlanRfCollectorVoList);
	}

	/**
	 * 批量保存
	 * @param initEamSet
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/saveBatchPlans")
	Boolean saveBatchPlans(@RequestBody InitEamSet initEamSet) throws Exception {
		maintenancePlanService.saveBatchPlans(initEamSet.getMmpVos()) ;
			return  true ;
	}
}