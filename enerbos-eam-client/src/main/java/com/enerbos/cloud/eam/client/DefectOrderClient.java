package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
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
 * @date 2017年9月5日
 * @Description 消缺工单Client
 */
@FeignClient(name = "enerbos-eam-microservice",url = "${eam.microservice.url:}", fallbackFactory = DefectOrderClientFallback.class)
public interface DefectOrderClient {

	/**
	 * saveDefectOrder:保存消缺工单
	 * @param defectOrderVo {@link DefectOrderVo}
	 * @return DefectOrderVo
	 */
	@RequestMapping(value = "/eam/micro/defectOrder/saveDefectOrder", method = RequestMethod.POST)
	DefectOrderVo saveDefectOrder(@RequestBody DefectOrderVo defectOrderVo);

	/**
	 * findDefectOrderById:根据ID查询消缺工单
	 * @param id
	 * @return DefectOrderVo 消缺工单VO
	 */
	@RequestMapping(value = "/eam/micro/defectOrder/findDefectOrderById", method = RequestMethod.GET)
	DefectOrderVo findDefectOrderById(@RequestParam("id") String id);

	/**
	 * findDefectOrderCommitByDefectOrderNum: 根据defectOrderNum查询消缺工单-工单提报
	 * @param defectOrderNum 消缺工单编码
	 * @return DefectOrderForCommitVo 消缺工单-工单提报
	 */
	@RequestMapping(value = "/eam/micro/defectOrder/findDefectOrderByDefectOrderNum", method = RequestMethod.GET)
	DefectOrderVo findDefectOrderByDefectOrderNum(@RequestParam("defectOrderNum") String defectOrderNum);

	/**
	 * findPageDefectOrderList: 分页查询消缺工单
	 * @param defectOrderForFilterVo 查询条件 {@link DefectOrderForFilterVo}
	 * @return EnerbosPage<DefectOrderForListVo> 预防消缺工单集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectOrder/findPageDefectOrderList")
	EnerbosPage<DefectOrderForListVo> findPageDefectOrderList(@RequestBody DefectOrderForFilterVo defectOrderForFilterVo);

	/**
	 * deleteDefectOrderList:删除选中的消缺工单
	 * @param ids
	 * @return Boolean 是否删除成功
	 */
	@RequestMapping(value = "/eam/micro/defectOrder/deleteDefectOrderList", method = RequestMethod.POST)
	Boolean deleteDefectOrderList(@RequestBody List<String> ids);

	/**
	 * 收藏消缺工单
	 * @param defectOrderRfCollectorVoList 收藏的消缺工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectOrder/collect")
	void collectDefectOrder(@RequestBody List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList);

	/**
	 * 取消收藏消缺工单
	 * @param defectOrderRfCollectorVoList 需要取消收藏的消缺工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectOrder/collect/cancel")
	void cancelCollectDefectOrder(@RequestBody List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList);
}
@Component
class  DefectOrderClientFallback implements FallbackFactory<DefectOrderClient> {
	@Override
	public DefectOrderClient create(Throwable throwable) {
		return new DefectOrderClient() {
			@Override
			public DefectOrderVo saveDefectOrder(DefectOrderVo defectOrderVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public DefectOrderVo findDefectOrderById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public DefectOrderVo findDefectOrderByDefectOrderNum(String defectOrderNum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<DefectOrderForListVo> findPageDefectOrderList(DefectOrderForFilterVo defectOrderForFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteDefectOrderList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void collectDefectOrder(List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void cancelCollectDefectOrder(List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}
