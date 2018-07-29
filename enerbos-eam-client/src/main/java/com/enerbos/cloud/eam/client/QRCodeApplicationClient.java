package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.QRCodeApplicationVo;
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
 * @Description EAM二维码应用程序Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = QRCodeApplicationClientFallback.class)
public interface QRCodeApplicationClient {
	/**
	 * findQRCodeApplicationByID:根据ID查询二维码应用程序
	 * @param id
	 * @return QRCodeApplicationVo 二维码应用程序VO
	 */
	@RequestMapping(value = "/eam/micro/qrCode/findQRCodeApplicationByID", method = RequestMethod.GET)
	QRCodeApplicationVo findQRCodeApplicationByID(@RequestParam("id") String id);

	/**
	 * findPageQRCodeApplication: 分页查询二维码应用程序
	 * @return EnerbosPage<QRCodeApplicationVo> 二维码应用程序VO集合
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/findPageQRCodeApplication")
	EnerbosPage<QRCodeApplicationVo> findPageQRCodeApplication();
}

@Component
class  QRCodeApplicationClientFallback implements FallbackFactory<QRCodeApplicationClient> {
	@Override
	public QRCodeApplicationClient create(Throwable throwable) {
		return new QRCodeApplicationClient() {
			@Override
			public QRCodeApplicationVo findQRCodeApplicationByID(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<QRCodeApplicationVo> findPageQRCodeApplication() {
		throw new RuntimeException(throwable.getMessage());
	}
		};
    }
}