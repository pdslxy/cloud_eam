package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.QRCodeApplicationAllPropertyVo;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
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
 * @date 2017年08月01日
 * @Description EAM二维码应用程序所有属性Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory =QRCodeApplicationAllPropertyClientFallback.class)
public interface QRCodeApplicationAllPropertyClient {
	/**
	 * findAllApplicationAllProperty: 分页查询二维码应用程序所有属性
	 * @return List<QRCodeApplicationAllPropertyVo> 二维码应用程序所有属性VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/qrCode/findAllApplicationAllPropertyNotIn")
	List<QRCodeApplicationAllPropertyVo> findAllApplicationAllPropertyNotIn(@RequestParam("applicationId") String applicationId,
																			@RequestParam(value = "ids",required = false) List<String> ids) ;
}

@Component
class  QRCodeApplicationAllPropertyClientFallback implements FallbackFactory<QRCodeApplicationAllPropertyClient> {
	@Override
	public QRCodeApplicationAllPropertyClient create(Throwable throwable) {
		return new QRCodeApplicationAllPropertyClient() {
			@Override
			public List<QRCodeApplicationAllPropertyVo> findAllApplicationAllPropertyNotIn(String applicationId,List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}