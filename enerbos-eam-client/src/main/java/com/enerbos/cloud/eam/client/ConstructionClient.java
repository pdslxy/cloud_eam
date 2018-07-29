package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.ConstructionForFilterVo;
import com.enerbos.cloud.eam.vo.ConstructionForListVo;
import com.enerbos.cloud.eam.vo.ConstructionRfCollectorVo;
import com.enerbos.cloud.eam.vo.ConstructionVo;
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
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月9日
 * @Description 施工单Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = ConstructionClientFallback.class)
public interface ConstructionClient {

	/**
	 * saveConstruction:保存施工单
	 * @param constructionVo {@link ConstructionVo}
	 * @return ConstructionVo
	 */
	@RequestMapping(value = "/eam/micro/construction/saveConstruction", method = RequestMethod.POST)
	ConstructionVo saveConstruction(@RequestBody ConstructionVo constructionVo);

	/**
	 * findConstructionById:根据ID查询施工单
	 * @param id
	 * @return ConstructionVo 施工单VO
	 */
	@RequestMapping(value = "/eam/micro/construction/findConstructionById", method = RequestMethod.GET)
	ConstructionVo findConstructionById(@RequestParam("id") String id);

	/**
	 * findConstructionCommitByConstructionNum: 根据constructionNum查询施工单-工单提报
	 * @param constructionNum 施工单编码
	 * @return ConstructionForCommitVo 施工单-工单提报
	 */
	@RequestMapping(value = "/eam/micro/construction/findConstructionByConstructionNum", method = RequestMethod.GET)
	ConstructionVo findConstructionByConstructionNum(@RequestParam("constructionNum") String constructionNum);

	/**
	 * findPageConstructionList: 分页查询施工单
	 * @param constructionForFilterVo 查询条件 {@link ConstructionForFilterVo}
	 * @return EnerbosPage<ConstructionForListVo> 预防施工单集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/findPageConstructionList")
	EnerbosPage<ConstructionForListVo> findPageConstructionList(@RequestBody ConstructionForFilterVo constructionForFilterVo);

	/**
	 * deleteConstructionList:删除选中的施工单
	 * @param ids
	 * @return Boolean 是否删除成功
	 */
	@RequestMapping(value = "/eam/micro/construction/deleteConstructionList", method = RequestMethod.POST)
	Boolean deleteConstructionList(@RequestBody List<String> ids);

	/**
	 * 收藏施工单
	 * @param constructionRfCollectorVoList 收藏的施工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/collect")
	void collectConstruction(@RequestBody List<ConstructionRfCollectorVo> constructionRfCollectorVoList);

	/**
	 * 取消收藏施工单
	 * @param constructionRfCollectorVoList 需要取消收藏的施工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/collect/cancel")
	void cancelCollectConstruction(@RequestBody List<ConstructionRfCollectorVo> constructionRfCollectorVoList);

	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/checkCollect")
	boolean checkCollect(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("personId") String personId);
}
@Component
class  ConstructionClientFallback implements FallbackFactory<ConstructionClient> {
	@Override
	public ConstructionClient create(Throwable throwable) {
		return new ConstructionClient() {
			@Override
			public ConstructionVo saveConstruction(ConstructionVo constructionVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public ConstructionVo findConstructionById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public ConstructionVo findConstructionByConstructionNum(String constructionNum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<ConstructionForListVo> findPageConstructionList(ConstructionForFilterVo constructionForFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteConstructionList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void collectConstruction(List<ConstructionRfCollectorVo> constructionRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void cancelCollectConstruction(List<ConstructionRfCollectorVo> constructionRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public boolean checkCollect(String id, String productId, String personId) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}