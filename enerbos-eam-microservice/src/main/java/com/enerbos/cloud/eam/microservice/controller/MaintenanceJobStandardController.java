package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandard;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardService;
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
 * @Description EAM作业标准接口
 */
@RestController
public class MaintenanceJobStandardController {
    private Logger logger = LoggerFactory.getLogger(MaintenanceJobStandardController.class);

    @Autowired
    private MaintenanceJobStandardService maintenanceJobStandardService;

    /**
     * saveJobStandard:保存作业标准
     * @param maintenanceJobStandardVo {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardVo}
     * @return MaintenanceJobStandardVo
     */
    @RequestMapping(value = "/eam/micro/jobStandard/saveJobStandard", method = RequestMethod.POST)
    public MaintenanceJobStandardVo saveJobStandard(@RequestBody MaintenanceJobStandardVo maintenanceJobStandardVo){
    	MaintenanceJobStandard maintenanceJobStandard=new MaintenanceJobStandard();
    	try {
			ReflectionUtils.copyProperties(maintenanceJobStandardVo, maintenanceJobStandard, null);
			maintenanceJobStandard=maintenanceJobStandardService.save(maintenanceJobStandard);
			ReflectionUtils.copyProperties(maintenanceJobStandard, maintenanceJobStandardVo, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/saveJobStandard-----", e);
		}
    	return maintenanceJobStandardVo;
    }

	/**
	 * updateJobStandardStatus:变更作业标准状态
	 * @param statusVo {@link com.enerbos.cloud.eam.vo.StatusVo}
	 * @return StatusVo
	 */
	@RequestMapping(value = "/eam/micro/jobStandard/updateJobStandardStatus", method = RequestMethod.POST)
	public StatusVo updateJobStandardStatus(@RequestBody StatusVo statusVo){
		try {
			List<String> ids=statusVo.getIds();
			if (ids != null&&!ids.isEmpty()) {
				for (String id:ids){
					MaintenanceJobStandard maintenanceJobStandard=maintenanceJobStandardService.findJobStandardByID(id);
					maintenanceJobStandard.setStatus(statusVo.getStatus());
					maintenanceJobStandard.setStatusDate(new Date());
					maintenanceJobStandard=maintenanceJobStandardService.save(maintenanceJobStandard);
					ReflectionUtils.copyProperties(maintenanceJobStandard, statusVo, null);
				}
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/updateJobStandardStatus-----", e);
		}
		return statusVo;
	}
    
    /**
	 * findJobStandardById:查询作业标准
	 * @param id 
	 * @return MaintenanceJobStandardVo 作业标准VO
	 */
	@RequestMapping(value = "/eam/micro/jobStandard/findJobStandardById", method = RequestMethod.GET)
	public MaintenanceJobStandardVo findJobStandardById(@RequestParam("id") String id){
		MaintenanceJobStandardVo maintenanceJobStandardVo=new MaintenanceJobStandardVo();
    	try {
    		MaintenanceJobStandard maintenanceJobStandard=maintenanceJobStandardService.findJobStandardByID(id);
			if (maintenanceJobStandard != null) {
				ReflectionUtils.copyProperties(maintenanceJobStandard, maintenanceJobStandardVo, null);
			}
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/findJobStandardById-----", e);
		}
    	return maintenanceJobStandardVo;
	}
	
	/**
	 * findJobStandardByJobStandardNum:查询作业标准
	 * @param jobStandardNum 作业编码
	 * @return MaintenanceJobStandardVo 作业标准VO
	 */
	@RequestMapping(value = "/eam/micro/jobStandard/findJobStandardByJobStandardNum", method = RequestMethod.GET)
	public MaintenanceJobStandardVo findJobStandardByJobStandardNum(@RequestParam("jobStandardNum") String jobStandardNum){
		MaintenanceJobStandardVo maintenanceJobStandardVo=maintenanceJobStandardService.findJobStandardByJobStandardNum(jobStandardNum);
    	return maintenanceJobStandardVo;
	}
	
	/**
	 * findPageJobStandardList: 分页查询作业标准
	 * @param maintenanceJobStandardSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardSelectVo}
	 * @return PageInfo<MaintenanceJobStandardVo> 与作业标准关联的物料VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/findPageJobStandardList")
	public PageInfo<MaintenanceJobStandardForListVo> findPageJobStandardList(@RequestBody MaintenanceJobStandardSelectVo maintenanceJobStandardSelectVo) {
		List<MaintenanceJobStandardForListVo> result = null;
		try {
			result= maintenanceJobStandardService.findJobStandardList(maintenanceJobStandardSelectVo);
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/findPageJobStandardList-----", e);
		}
		return new PageInfo<MaintenanceJobStandardForListVo>(result);
	}
	
	/**
	 * findPageJobStandardByAssetId:根据设备ID分页查询作业标准
	 * @param maintenanceForAssetFilterVo 根据设备查询作业标准列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return PageInfo<MaintenanceJobStandardForListVo> 预防性维护计划Vo集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/findPageJobStandardByAssetId")
	public PageInfo<MaintenanceJobStandardForListVo> findPageJobStandardByAssetId(@RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
		List<MaintenanceJobStandardForListVo> result = null;
		try {
			result= maintenanceJobStandardService.findPageJobStandardByAssetId(maintenanceForAssetFilterVo);
		} catch (Exception e) {
			logger.error("-------/eam/micro/jobStandard/findPageJobStandardByAssetId-----", e);
		}
		return new PageInfo<MaintenanceJobStandardForListVo>(result);
	}

    /**
     * deleteJobStandardList:删除选中的作业标准
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/jobStandard/deleteJobStandardList", method = RequestMethod.POST)
    public Boolean deleteJobStandardList(@RequestBody List<String> ids) {
		maintenanceJobStandardService.deleteJobStandardByIds(ids);
		return true;
    }
    
    /**
     * deleteJobStandard:删除选作业标准
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/jobStandard/deleteJobStandard", method = RequestMethod.POST)
    public Boolean deleteJobStandard(@RequestParam("id")  String id) {
		maintenanceJobStandardService.deleteJobStandardById(id);
		return true;
    }

	/**
	 * 收藏作业标准
	 * @param maintenanceJobStandardRfCollectorVoList 收藏的作业标准列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/collect")
	public void collectJobStandard(@RequestBody List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList) {
		logger.info("/eam/micro/jobStandard/collect, params: [{}]", maintenanceJobStandardRfCollectorVoList);

		maintenanceJobStandardService.collectJobStandard(maintenanceJobStandardRfCollectorVoList);
	}

	/**
	 * 取消收藏作业标准
	 * @param maintenanceJobStandardRfCollectorVoList 需要取消收藏的作业标准列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/collect/cancel")
	public void cancelCollectJobStandard(@RequestBody List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList) {
		logger.info("/eam/micro/jobStandard/collect/cancel, params: [{}]", maintenanceJobStandardRfCollectorVoList);

		maintenanceJobStandardService.cancelCollectJobStandard(maintenanceJobStandardRfCollectorVoList);
	}

	/**
	 * 批量保存标准
	 * @param initEamSet
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/jobStandard/saveBatchStandard")
	List<MaintenanceJobStandardVo> saveBatchStandard(@RequestBody InitEamSet initEamSet) throws Exception {

		return maintenanceJobStandardService.saveBatchStandard(initEamSet.getMjsVos()) ;
	}
}