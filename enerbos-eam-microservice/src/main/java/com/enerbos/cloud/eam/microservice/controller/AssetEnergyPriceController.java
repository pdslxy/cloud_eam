package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVoForFilter;
import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.microservice.service.AssetEnergyPriceService;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */

@RestController
public class AssetEnergyPriceController {

	private static final Logger logger = LoggerFactory
			.getLogger(AssetEnergyPriceController.class);

	@Autowired
	private AssetEnergyPriceService assetEnergyPriceService;

	/**
	 * 根据过滤条和分页信息获取能源价格列表
	 * 
	 * @return
	 */
	@RequestMapping("/eam/micro/energyPrice/getEnergyPriceList")
	@ResponseBody
	public PageInfo<AssetEnergyPriceVo> getEnergyPriceList(
			@RequestBody AssetEnergyPriceVoForFilter assetEnergyPriceVoForFilter) {
		PageInfo<AssetEnergyPriceVo> pageInfo = assetEnergyPriceService
				.getEnergyPriceList(assetEnergyPriceVoForFilter);
		return pageInfo;
	}

	/**
	 * 删除计划
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/eam/micro/energyPrice/deleteEnergyPrice")
	@ResponseBody
	public boolean deleteEnergyPrice(
			@RequestParam(value = "ids", required = false) String[] ids) {
		try {
			assetEnergyPriceService.deleteEnergyPrice(ids);
		} catch (Exception e) {
			logger.error("-------deleteEnergyPriceById--------------", e);
			return false;
		}
		return true;
	}

	/**
	 * 新建能源价格
	 * 
	 * @param assetEnergyPriceVo
	 *            新建的实体
	 * @return 返回添加的实体
	 */
	@RequestMapping("/eam/micro/energyPrice/saveEnergyPrice")
	@ResponseBody
	public AssetEnergyPriceVo saveEnergyPrice(
			@RequestBody @Valid AssetEnergyPriceVo assetEnergyPriceVo) {
		AssetEnergyPrice assetEnergyPrice = new AssetEnergyPrice();
		try {
			ReflectionUtils.copyProperties(assetEnergyPriceVo, assetEnergyPrice, null);
		} catch (Exception e) {
			logger.error("-----saveEnergyPrice ------", e);
		}
		assetEnergyPrice.setCreateDate(new Date());
		assetEnergyPrice = assetEnergyPriceService.saveEnergyPrice(assetEnergyPrice);
		assetEnergyPriceVo.setId(assetEnergyPrice.getId());
		return assetEnergyPriceVo;
	}

	/**
	 * 修改能源价格
	 * 
	 * @param assetEnergyPriceVo
	 *            修改的能源价格实体
	 * @return 修改后的能源价格实体
	 */
	@RequestMapping(value = "/eam/micro/energyPrice/updateEnergyPrice", method = RequestMethod.POST)
	public AssetEnergyPrice updateEnergyPrice(
			@RequestBody @Valid AssetEnergyPriceVo assetEnergyPriceVo) {
		AssetEnergyPrice assetEnergyPrice = new AssetEnergyPrice();
		try {
			ReflectionUtils.copyProperties(assetEnergyPriceVo, assetEnergyPrice, null);
		} catch (Exception e) {
			logger.error("-----updateEnergyPrice ------", e);
		}
		return assetEnergyPriceService.updateEnergyPrice(assetEnergyPrice);
	}

	/**
	 * 查询能源价格详细信息
	 * 
	 * @param id
	 *            能源价格id
	 * @return 返回能源价格实体
	 */
	@RequestMapping(value = "/eam/micro/energyPrice/findEnergyPriceDetail", method = RequestMethod.GET)
	public AssetEnergyPrice findEnergyPriceDetail(@RequestParam("id") String id) {
		return assetEnergyPriceService.findEnergyPriceDetail(id);
	}

	/**
	 *
	 * 修改能源价格状态
	 * 
	 *
	 * @return 修改后返回的实体类
	 */

	@RequestMapping(value = "/eam/micro/energyPrice/updateEnergyPriceStatus", method = RequestMethod.POST)
	public boolean updateEnergyPriceStatus(
			@RequestBody Map<String, Object> paramsMap) {

		List<String> ids = (List<String>) paramsMap.getOrDefault("ids", null);
		String status = (String) paramsMap.getOrDefault("status", null);

		Assert.notEmpty(ids, "请选择要变更状态的工单！");
		Assert.notNull(status, "状态不能为空！");

		return assetEnergyPriceService.changeOrderStatus(ids,status);
	}
}
