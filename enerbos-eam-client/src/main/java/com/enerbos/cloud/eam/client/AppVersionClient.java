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

import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月27日
 * @Description APP 版本信息
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = AppVersionClientFallback.class)
public interface AppVersionClient {

	/**
	 * getNewVersion: 查询是否有新版本
	 * @param appVersionForFilterVo 过滤条件VO
	 * @return AppVersionVo 最新版本
	 */
	@RequestMapping(value = "/eam/micro/getNewVersion", method = RequestMethod.POST)
	public AppVersionVo getNewVersion(@RequestBody AppVersionForFilterVo appVersionForFilterVo);
}
@Component
class AppVersionClientFallback implements FallbackFactory<AppVersionClient> {
	@Override
	public AppVersionClient create(Throwable throwable) {
		return new AppVersionClient() {
			@Override
			public AppVersionVo getNewVersion(AppVersionForFilterVo appVersionForFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}