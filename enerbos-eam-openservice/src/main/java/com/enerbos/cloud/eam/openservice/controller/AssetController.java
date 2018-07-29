package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.constants.MixType;
import com.enerbos.cloud.ams.vo.asset.AssetVoForDetail;
import com.enerbos.cloud.ams.vo.asset.AssetVoForSave;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.QRCodeManagerClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0.0
 * @date 2017年05月16日
 * @Description 设备表接口
 */

@RestController
@Api(description = "设备表管理接口")
public class AssetController {

	@Autowired
	private AssetClient assetClient;

	@Autowired
	private SiteClient siteClient;

	@Autowired
	private QRCodeManagerClient QRCodeManagerClient;

	/**
	 * saveAsset: 创建设备表
	 *
	 * @param assetVoForSave 设备表实体vo
	 * @param user           用户
	 * @return EnerbosMessage 返回执行码及数据
	 */
	@ApiOperation(value = "新建设备表", response = AssetVoForSave.class, notes = "返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/assets/add", method = RequestMethod.POST)
	public EnerbosMessage saveAsset(@ApiParam(value = "设备表实体vo", required = true) @RequestBody @Valid AssetVoForSave assetVoForSave,
									Principal user) {
		assetVoForSave.setNeedUpdate(true);
//		QRCodeManagerClient.updateQRCodeManagerBySiteIdAndApplicationValue(assetVoForSave.getSiteId(), QRCodeManagerCommon.true);
		return EnerbosMessage.createSuccessMsg(assetClient.save(user.getName(), assetVoForSave), "新建设备表成功", "");
	}

	/**
	 * updateAsset: 编辑设备表
	 *
	 * @param assetVoForSave 设备表实体vo
	 * @param user           用户
	 * @return EnerbosMessage 返回执行码及数据
	 */
	@ApiOperation(value = "编辑设备表", response = AssetVoForSave.class, notes = "返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/assets/update", method = RequestMethod.POST)
	public EnerbosMessage updateAsset(@ApiParam(value = "设备表实体vo", required = true) @RequestBody @Valid AssetVoForSave assetVoForSave,
									  Principal user) {
		assetVoForSave.setNeedUpdate(true);
		return EnerbosMessage.createSuccessMsg(assetClient.update(user.getName(), assetVoForSave), "编辑设备表成功", "");
	}




	/**
	 * deleteAssetById: 删除设备表(多个)
	 *
	 * @param ids  多个设备表id
	 * @param user 用户
	 * @return EnerbosMessage 返回执行码及数据
	 */
	@ApiOperation(value = "删除设备表(多个)", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(method = RequestMethod.GET, value = "/eam/open/assets/delete")
	public EnerbosMessage deletByIds(@ApiParam(value = "设备表ids(数组)", required = true) String[] ids,
									 Principal user) {
		return EnerbosMessage.createSuccessMsg(assetClient.deletByIds(user.getName(), ids), "删除设备表成功", "");
	}

	/**
	 * findByIdsAndQrCodeNumAndSiteId:
	 * @param id
	 * @param qrCodeNum
	 * @param siteId
	 * @return List<AssetVoForDetail>
	 */
	public List<AssetVoForDetail> findByIdsAndQrCodeNumAndSiteId(List<String> id, String qrCodeNum, String siteId,String userName) {
		if (id!=null&&!id.isEmpty()) {
			return assetClient.findDetailsByQr((String[]) id.toArray(new String[0]));
		}else {
			SiteVoForDetail site=siteClient.findById(siteId);
			String orgId="";
			if (site != null) {
				orgId=site.getOrgId();
			}
			AssetVoForDetail asset=assetClient.findByQrCodeNum(qrCodeNum,orgId,siteId, Common.EAM_PROD_IDS[0]);
			List<AssetVoForDetail> list=new ArrayList<>();
			if (asset != null) {
				list.add(asset);
			}
			return list;
		}
	}

	/**
	 * 设置是否更新状态
	 * @param ids
	 * @param siteId
	 * @return
	 */
	public boolean updateIsUpdateAsset(List<String> ids, String siteId) {
		SiteVoForDetail site=siteClient.findById(siteId);
		String orgId="";
		if (site != null) {
			orgId=site.getOrgId();
		}
		return assetClient.updateQrCodeStatus(orgId,siteId, MixType.SBSS_ID,false);
	}

	/**
	 * 设置是否更新状态
	 * @param ids
	 * @param siteId
	 * @return
	 */
	public boolean updateIsUpdateMeter(List<String> ids, String siteId) {
		SiteVoForDetail site=siteClient.findById(siteId);
		String orgId="";
		if (site != null) {
			orgId=site.getOrgId();
		}
		return assetClient.updateQrCodeStatus(orgId,siteId, MixType.YBTZ_ID,false);
	}
}