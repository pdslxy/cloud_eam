package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo;
import com.enerbos.cloud.eam.vo.QRCodeManagerVo;
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
 * @Description EAM二维码管理Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = QRCodeManagerClientFallback.class)
public interface QRCodeManagerClient {
	/**
	 * saveQRCodeManager:保存二维码管理
	 * @param QRCodeManagerVo {@link com.enerbos.cloud.eam.vo.QRCodeManagerVo}
	 * @return QRCodeManagerVo
	 */
	@RequestMapping(value = "/eam/micro/qrCode/saveQRCodeManager", method = RequestMethod.POST)
	QRCodeManagerVo saveQRCodeManager(@RequestBody QRCodeManagerVo QRCodeManagerVo);

	/**
	 * findQRCodeManagerByID:根据ID查询二维码管理
	 * @param id
	 * @return MaintenanceMaintenancePlanVo 二维码管理VO
	 */
	@RequestMapping(value = "/eam/micro/qrCode/findQRCodeManagerByID", method = RequestMethod.GET)
	QRCodeManagerVo findQRCodeManagerByID(@RequestParam("id") String id);

	/**
	 * findQRCodeManagerByQRCodeNum:根据QRCodeNum查询二维码管理
	 * @param QRCodeNum
	 * @return MaintenanceMaintenancePlanVo 二维码管理VO
	 */
	@RequestMapping(value = "/eam/micro/qrCode/findQRCodeManagerByQRCodeNum", method = RequestMethod.GET)
	QRCodeManagerVo findQRCodeManagerByQRCodeNum(@RequestParam("QRCodeNum") String QRCodeNum);

	/**
	 * findPageQRCodeManager: 分页查询二维码管理
	 * @param QRCodeManagerForFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo}
	 * @return EnerbosPage<QRCodeManagerVo> 二维码管理VO集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/qrCode/findPageQRCodeManager")
	EnerbosPage<QRCodeManagerVo> findPageQRCodeManager(@RequestBody QRCodeManagerForFilterVo QRCodeManagerForFilterVo);

	/**
	 * deleteQRCodeManagerList:删除选中的二维码管理
	 * @param ids
	 * @return Boolean 是否删除成功
	 */
	@RequestMapping(value = "/eam/micro/qrCode/deleteQRCodeManagerList", method = RequestMethod.POST)
	Boolean deleteQRCodeManagerList(@RequestBody List<String> ids);

	/**
	 * findQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值查询二维码管理
	 * @param siteId        站点ID
	 * @param applicationValue 应用程序值
	 * @return QRCodeManager 二维码管理实体
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/findQRCodeManagerBySiteIdAndApplicationValue")
	QRCodeManagerVo findQRCodeManagerBySiteIdAndApplicationValue(@RequestParam("siteId") String siteId,@RequestParam("applicationValue") String applicationValue);

	/**
	 * findPageQRCodeManagerBySiteIdAndPrefixQRCode: 根据站点ID和应用程序值查询二维码管理
	 * @param siteId        站点ID
	 * @param prefixQRCode 二维码前缀编码
	 * @return QRCodeManagerVo
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/findPageQRCodeManagerBySiteIdAndPrefixQRCode")
	QRCodeManagerVo findPageQRCodeManagerBySiteIdAndPrefixQRCode(@RequestParam("siteId") String siteId,@RequestParam("prefixQRCode") String prefixQRCode);

	/**
	 * updateQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值修改二维码管理是否有数据更新
	 * @param siteId        站点ID
	 * @param applicationValue
	 * @param dataUpdate
	 * @return Boolean 更新是否成功是否成功
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/updateQRCodeManagerBySiteIdAndApplicationValue")
	Boolean updateQRCodeManagerBySiteIdAndApplicationValue(@RequestParam("siteId") String siteId,@RequestParam("applicationValue") String applicationValue,@RequestParam("dataUpdate") Boolean dataUpdate);

	/**
	 * generateQRCodeManagerSuffixQRCode: 根据站点和应用程序value生成当前后缀编码
	 * @param siteId        站点ID
	 * @param applicationValue 应用程序value
	 * @return 生成的最新编码
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/generateQRCodeManagerSuffixQRCode")
	String generateQRCodeManagerSuffixQRCode(@RequestParam("siteId") String siteId,@RequestParam("applicationValue") String applicationValue);
}

@Component
class  QRCodeManagerClientFallback implements FallbackFactory<QRCodeManagerClient> {
	@Override
	public QRCodeManagerClient create(Throwable throwable) {
		return new QRCodeManagerClient() {
			@Override
			public QRCodeManagerVo saveQRCodeManager(QRCodeManagerVo QRCodeManagerVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public QRCodeManagerVo findQRCodeManagerByID(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public QRCodeManagerVo findQRCodeManagerByQRCodeNum(String QRCodeNum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<QRCodeManagerVo> findPageQRCodeManager(QRCodeManagerForFilterVo QRCodeManagerForFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteQRCodeManagerList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public QRCodeManagerVo findQRCodeManagerBySiteIdAndApplicationValue(String siteId, String applicationValue) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public QRCodeManagerVo findPageQRCodeManagerBySiteIdAndPrefixQRCode(String siteId, String prefixQRCode) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean updateQRCodeManagerBySiteIdAndApplicationValue(String siteId, String applicationValue, Boolean dataUpdate) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public String generateQRCodeManagerSuffixQRCode(String siteId, String applicationValue) {
				throw new RuntimeException(throwable.getMessage());
	}
		};
	}
}