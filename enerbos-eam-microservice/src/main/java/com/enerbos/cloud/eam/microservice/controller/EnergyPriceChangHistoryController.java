package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.microservice.service.AssetEnergyPriceService;
import com.enerbos.cloud.eam.microservice.service.EnergyPriceChangHistoryService;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVoForFilter;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryFilterVo;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryVo;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@RestController
public class EnergyPriceChangHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(EnergyPriceChangHistoryController.class);

	@Autowired
	private EnergyPriceChangHistoryService energyPriceChangHistoryService;

	/**
	 *
	 * 根据过滤条件查询变更历史
	 * @return
	 */
	@RequestMapping(value = "/eam/micro/energyPriceChangHistory/getPageList",method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<EnergyPriceChangHistoryVo> findEnergyPriceChangHistoryPageList(@RequestBody EnergyPriceChangHistoryFilterVo filterVo) {
		PageInfo<EnergyPriceChangHistoryVo> pageInfo = energyPriceChangHistoryService.findEnergyPriceChangHistory(filterVo);
		return pageInfo;
	}
	/**
	 * 新建能源价格变更历史
	 * 
	 * @param vo
	 *            新建的实体
	 * @return 返回添加的实体
	 */
	@RequestMapping(value = "/eam/micro/energyPriceChangHistory/save",method = RequestMethod.POST)
	@ResponseBody
	public EnergyPriceChangHistoryVo saveEnergyPriceChangHistory(@RequestBody @Valid EnergyPriceChangHistoryVo vo) {
		return energyPriceChangHistoryService.save(vo);
	}


	/**
	 * 根据ID查询能源价格变更历史
	 * @param eneryPriceId  能源价格ID
	 * @return
	 */
	@RequestMapping(value = "/eam/micro/energyPriceChangHistory/findMaxCreateDateById", method = RequestMethod.GET)
	public EnergyPriceChangHistoryVo findEnergyPriceDetail(@RequestParam("eneryPriceId") String eneryPriceId) {
		return energyPriceChangHistoryService.findMaxCreateDatePriceChangHistoryById(eneryPriceId);
	}


}
