package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.DefectDocumentForFilterVo;
import com.enerbos.cloud.eam.vo.DefectDocumentForListVo;
import com.enerbos.cloud.eam.vo.DefectDocumentRfCollectorVo;
import com.enerbos.cloud.eam.vo.DefectDocumentVo;
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
 * @Description 缺陷单Client
 */
@FeignClient(name = "enerbos-eam-microservice",url = "${eam.microservice.url:}", fallbackFactory = DefectDocumentClientFallback.class)
public interface DefectDocumentClient {

	/**
	 * saveDefectDocument:保存缺陷单
	 * @param defectDocumentVo {@link DefectDocumentVo}
	 * @return DefectDocumentVo
	 */
	@RequestMapping(value = "/eam/micro/defectDocument/saveDefectDocument", method = RequestMethod.POST)
	DefectDocumentVo saveDefectDocument(@RequestBody DefectDocumentVo defectDocumentVo);

	/**
	 * findDefectDocumentById:根据ID查询缺陷单
	 * @param id
	 * @return DefectDocumentVo 缺陷单VO
	 */
	@RequestMapping(value = "/eam/micro/defectDocument/findDefectDocumentById", method = RequestMethod.GET)
	DefectDocumentVo findDefectDocumentById(@RequestParam("id") String id);

	/**
	 * findDefectDocumentCommitByDefectDocumentNum: 根据defectDocumentNum查询缺陷单-工单提报
	 * @param defectDocumentNum 缺陷单编码
	 * @return DefectDocumentForCommitVo 缺陷单-工单提报
	 */
	@RequestMapping(value = "/eam/micro/defectDocument/findDefectDocumentByDefectDocumentNum", method = RequestMethod.GET)
	DefectDocumentVo findDefectDocumentByDefectDocumentNum(@RequestParam("defectDocumentNum") String defectDocumentNum);

	/**
	 * findPageDefectDocumentList: 分页查询缺陷单
	 * @param defectDocumentForFilterVo 查询条件 {@link DefectDocumentForFilterVo}
	 * @return EnerbosPage<DefectDocumentForListVo> 预防缺陷单集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectDocument/findPageDefectDocumentList")
	EnerbosPage<DefectDocumentForListVo> findPageDefectDocumentList(@RequestBody DefectDocumentForFilterVo defectDocumentForFilterVo);

	/**
	 * deleteDefectDocumentList:删除选中的缺陷单
	 * @param ids
	 * @return Boolean 是否删除成功
	 */
	@RequestMapping(value = "/eam/micro/defectDocument/deleteDefectDocumentList", method = RequestMethod.POST)
	Boolean deleteDefectDocumentList(@RequestBody List<String> ids);

	/**
	 * 收藏缺陷单
	 * @param defectDocumentRfCollectorVoList 收藏的缺陷单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectDocument/collect")
	void collectDefectDocument(@RequestBody List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList);

	/**
	 * 取消收藏缺陷单
	 * @param defectDocumentRfCollectorVoList 需要取消收藏的缺陷单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectDocument/collect/cancel")
	void cancelCollectDefectDocument(@RequestBody List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList);
}
@Component
class  DefectDocumentClientFallback implements FallbackFactory<DefectDocumentClient> {
	@Override
	public DefectDocumentClient create(Throwable throwable) {
		return new DefectDocumentClient() {
			@Override
			public DefectDocumentVo saveDefectDocument(DefectDocumentVo defectDocumentVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public DefectDocumentVo findDefectDocumentById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public DefectDocumentVo findDefectDocumentByDefectDocumentNum(String defectDocumentNum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<DefectDocumentForListVo> findPageDefectDocumentList(DefectDocumentForFilterVo defectDocumentForFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteDefectDocumentList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void collectDefectDocument(List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void cancelCollectDefectDocument(List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}