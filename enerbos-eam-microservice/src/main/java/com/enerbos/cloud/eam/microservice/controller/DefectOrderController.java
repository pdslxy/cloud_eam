package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.DefectOrder;
import com.enerbos.cloud.eam.microservice.service.DefectOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月5日
 * @Description 消缺工单接口
 */
@RestController
public class DefectOrderController {

    private Logger logger = LoggerFactory.getLogger(DefectOrderController.class);

    @Resource
    protected DefectOrderService defectOrderService;

    /**
     * saveDefectOrder:保存消缺工单
     * @param defectOrderVo {@link DefectOrderVo}
     * @return DefectOrderVo
     */
    @RequestMapping(value = "/eam/micro/defectOrder/saveDefectOrder", method = RequestMethod.POST)
    public DefectOrderVo saveDefectOrder(@RequestBody DefectOrderVo defectOrderVo){
		DefectOrderVo defectOrderVoSave=new DefectOrderVo();
    	try {
			DefectOrder defectOrder=new DefectOrder();
			if (StringUtils.isNotBlank(defectOrderVo.getId())) {
				defectOrder=defectOrderService.findDefectOrderByID(defectOrderVo.getId());
				if (null==defectOrder) {
					throw new EnerbosException("", "工单不存在，先创建！");
				}
			}
    		ReflectionUtils.copyProperties(defectOrderVo, defectOrder, null);
//			if(defectOrderVo.getResponseTime()!=null){
//				defectOrder.setReponseTime(defectOrderVo.getResponseTime());
//			}
			DefectOrder defectOrderSave=defectOrderService.save(defectOrder);
			ReflectionUtils.copyProperties(defectOrderSave, defectOrderVoSave, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/defectOrder/saveDefectOrder-----", e);
		}
    	return defectOrderVoSave;
    }

	/**
	 * findDefectOrderById:根据ID查询消缺工单
	 * @param id
	 * @return DefectOrderVo 消缺工单VO
	 */
	@RequestMapping(value = "/eam/micro/defectOrder/findDefectOrderById", method = RequestMethod.GET)
	public DefectOrderVo findDefectOrderById(@RequestParam("id") String id){
		DefectOrderVo defectOrderVo=new DefectOrderVo();
		try {
			DefectOrder defectOrder=defectOrderService.findDefectOrderByID(id);
			if (null==defectOrder||defectOrder.getId()==null) {
				return new DefectOrderVo();
			}
			ReflectionUtils.copyProperties(defectOrder, defectOrderVo, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/defectOrder/findDefectOrderById-----", e);
		}
    	return defectOrderVo;
	}

	/**
	 * findDefectOrderCommitByDefectOrderNum: 根据defectOrderNum查询消缺工单-工单提报
	 * @param defectOrderNum 消缺工单编码
	 * @return DefectOrderForCommitVo 消缺工单-工单提报
	 */
	@RequestMapping(value = "/eam/micro/defectOrder/findDefectOrderByDefectOrderNum", method = RequestMethod.GET)
	public DefectOrderVo findDefectOrderByDefectOrderNum(@RequestParam("defectOrderNum") String defectOrderNum){
    	return defectOrderService.findDefectOrderByDefectOrderNum(defectOrderNum);
	}

    /**
	 * findPageDefectOrderList: 分页查询消缺工单
	 * @param defectOrderForFilterVo 查询条件 {@link DefectOrderForFilterVo}
	 * @return PageInfo<DefectOrderForListVo> 预防消缺工单集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectOrder/findPageDefectOrderList")
	public PageInfo<DefectOrderForListVo> findPageDefectOrderList(@RequestBody DefectOrderForFilterVo defectOrderForFilterVo) {
		List<DefectOrderForListVo> result = defectOrderService.findPageDefectOrderList(defectOrderForFilterVo);
		return new PageInfo<DefectOrderForListVo>(result);
	}

    /**
     * deleteDefectOrderList:删除选中的消缺工单
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/defectOrder/deleteDefectOrderList", method = RequestMethod.POST)
    public Boolean deleteDefectOrderList(@RequestBody List<String> ids) {
    	try {
    		defectOrderService.deleteDefectOrderByIds(ids);
		} catch (Exception e) {
			logger.error("-------/eam/micro/defectOrder/deleteDefectOrderList-----", e);
			return false;
		}
		return true;
    }

	/**
	 * 收藏消缺工单
	 * @param defectOrderRfCollectorVoList 收藏的消缺工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectOrder/collect")
	public void collectDefectOrder(@RequestBody List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList) {
		logger.info("/eam/micro/defectOrder/collect, params: [{}]", defectOrderRfCollectorVoList);

		defectOrderService.collectDefectOrder(defectOrderRfCollectorVoList);
	}

	/**
	 * 取消收藏消缺工单
	 * @param defectOrderRfCollectorVoList 需要取消收藏的消缺工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectOrder/collect/cancel")
	public void cancelCollectDefectOrder(@RequestBody List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList) {
		logger.info("/eam/micro/defectOrder/collect/cancel, params: [{}]", defectOrderRfCollectorVoList);

		defectOrderService.cancelCollectDefectOrder(defectOrderRfCollectorVoList);
	}
}