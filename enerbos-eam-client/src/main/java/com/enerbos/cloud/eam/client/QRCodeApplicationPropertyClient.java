package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo;
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
 * @date 2017年08月01日
 * @Description EAM二维码应用程序所有属性Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory =QRCodeApplicationPropertyClientFallback.class)
public interface QRCodeApplicationPropertyClient {

	/**
	 * saveApplicationPropertyList:保存二维码应用程序属性
	 * @param QRCodeApplicationPropertyVoList 二维码应用程序属性Vo集合{@link com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo}
	 * @return Boolean 是否保存成功
	 */
	@RequestMapping(value = "/eam/micro/qrCode/saveApplicationPropertyList", method = RequestMethod.POST)
	Boolean saveApplicationPropertyList(@RequestBody List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList);

	/**
	 * findApplicationPropertyByQRCodeManagerId: 根据应用程序ID查询应用程序属性
	 * @return List<QRCodeApplicationPropertyVo> 二维码应用程序属性VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/qrCode/findApplicationPropertyByQRCodeManagerId")
	List<QRCodeApplicationPropertyVo> findApplicationPropertyByQRCodeManagerId(@RequestParam("QRCodeManagerId") String QRCodeManagerId);

	/**
	 * deleteApplicationPropertyByIds:删除选中的应用程序属性
	 * @param ids
	 * @return Boolean 是否删除成功
	 */
	@RequestMapping(value = "/eam/micro/qrCode/deleteApplicationPropertyByIds", method = RequestMethod.POST)
	Boolean deleteApplicationPropertyByIds(@RequestBody List<String> ids);
}

@Component
class  QRCodeApplicationPropertyClientFallback implements FallbackFactory<QRCodeApplicationPropertyClient> {
	@Override
	public QRCodeApplicationPropertyClient create(Throwable throwable) {
		return new QRCodeApplicationPropertyClient() {
			@Override
			public Boolean saveApplicationPropertyList(List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteApplicationPropertyByIds(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public List<QRCodeApplicationPropertyVo> findApplicationPropertyByQRCodeManagerId(String QRCodeManagerId) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
	}
}