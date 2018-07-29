package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.ConstructionSuperviseVo;
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
 * @date 2017年09月9日
 * @Description 施工单-监管说明Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = ConstructionSuperviseFallback.class)
public interface ConstructionSuperviseClient {

	/**
     * saveConstructionSuperviseList:保存施工单-监管说明
     * @param constructionSuperviseVoList {@link com.enerbos.cloud.eam.vo.ConstructionSuperviseVo}
     * @return boolean 执行结果
     */
    @RequestMapping(value = "/eam/micro/constructionSupervise/saveConstructionSuperviseList", method = RequestMethod.POST)
    boolean saveConstructionSuperviseList(@RequestBody List<ConstructionSuperviseVo> constructionSuperviseVoList);
    
    /**
	 * findConstructionSuperviseByConstructionId: 分页施工单-监管说明
	 * @param constructionId 工单ID
	 * @return List<ConstructionSuperviseVo> 预防施工单-监管说明集合
	 */
	@RequestMapping(value = "/eam/micro/constructionSupervise/findConstructionSuperviseByConstructionId",method = RequestMethod.GET)
	List<ConstructionSuperviseVo> findConstructionSuperviseByConstructionId(@RequestParam("constructionId")String constructionId);
	
	/**
     * deleteConstructionSuperviseList:删除施工单-监管说明
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/constructionSupervise/deleteConstructionSuperviseList", method = RequestMethod.POST)
    Boolean deleteConstructionSupervise(@RequestParam("ids") List<String> ids);
}

@Component
class  ConstructionSuperviseFallback implements FallbackFactory<ConstructionSuperviseClient> {
	@Override
	public ConstructionSuperviseClient create(Throwable throwable) {
		return new ConstructionSuperviseClient() {
			@Override
			public boolean saveConstructionSuperviseList(List<ConstructionSuperviseVo> constructionSuperviseVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<ConstructionSuperviseVo> findConstructionSuperviseByConstructionId(String workOrderId) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteConstructionSupervise(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}