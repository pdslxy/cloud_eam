package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.QRCodeApplicationAllPropertyClient;
import com.enerbos.cloud.eam.client.QRCodeApplicationClient;
import com.enerbos.cloud.eam.client.QRCodeApplicationPropertyClient;
import com.enerbos.cloud.eam.client.QRCodeManagerClient;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.eam.openservice.service.QRCodeGenerateService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.util.ReflectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年08月01日
 * @Description EAM二维码管理接口
 */
@RestController
@Api(description = "二维码管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class QRCodeManagerController {

    private Logger logger = LoggerFactory.getLogger(QRCodeManagerController.class);

	@Autowired
	private ApplicationContext context;

    @Autowired
    private DiscoveryClient client;

    @Autowired
	private SiteClient siteClient;

    @Autowired
	private OrgClient orgClient;

	@Autowired
	private QRCodeManagerClient QRCodeManagerClient;

	@Autowired
	private QRCodeApplicationClient QRCodeApplicationClient;

	@Autowired
	private QRCodeApplicationAllPropertyClient QRCodeApplicationAllPropertyClient;

	@Autowired
	private QRCodeApplicationPropertyClient QRCodeApplicationPropertyClient;

	@Autowired
	private QRCodeGenerateService QRCodeGenerateService;

    /**
     * saveQRCodeManager:保存二维码管理
     * @param QRCodeManagerVo {@link com.enerbos.cloud.eam.vo.QRCodeManagerVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存二维码管理", response = QRCodeManagerVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/qrCode/saveQRCodeManager", method = RequestMethod.POST)
    public EnerbosMessage saveQRCodeManager(@ApiParam(value = "二维码管理VO", required = true) @RequestBody QRCodeManagerVo QRCodeManagerVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/qrCode/saveQRCodeManager, host: [{}:{}], service_id: {}, QRCodeManagerVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), QRCodeManagerVo);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			QRCodeManagerVo ewo = QRCodeManagerClient.findQRCodeManagerByQRCodeNum(QRCodeManagerVo.getQuickResponseCodeNum());
        	if (StringUtils.isBlank(QRCodeManagerVo.getId())) {
                if (ewo != null && !"".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("401", "二维码管理编码重复", "");
                }
            }else {
            	if (ewo != null && !QRCodeManagerVo.getId().equals(ewo.getId())) {
                    return EnerbosMessage.createErrorMsg("401", "二维码管理编码重复", "");
                }
				List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList=QRCodeApplicationPropertyClient.findApplicationPropertyByQRCodeManagerId(QRCodeManagerVo.getId());
				if (QRCodeApplicationPropertyVoList != null&&!QRCodeApplicationPropertyVoList.isEmpty()) {
					for (QRCodeApplicationPropertyVo QRCodeApplicationPropertyVo:QRCodeApplicationPropertyVoList){
						if (!QRCodeApplicationPropertyVo.getApplicationId().equals(QRCodeManagerVo.getApplicationId())) {
							return EnerbosMessage.createErrorMsg("401", "二维码属性和应用程序不匹配", "");
						}
					}
				}
            }
			List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList=QRCodeManagerVo.getQRCodeApplicationPropertyVoList();
			if (QRCodeApplicationPropertyVoList != null&&!QRCodeApplicationPropertyVoList.isEmpty()) {
				for (QRCodeApplicationPropertyVo QRCodeApplicationPropertyVo:QRCodeApplicationPropertyVoList){
					if (!QRCodeApplicationPropertyVo.getApplicationId().equals(QRCodeManagerVo.getApplicationId())) {
						return EnerbosMessage.createErrorMsg("401", "二维码属性和应用程序不匹配", "");
					}
				}
			}
			QRCodeManagerVo.setDataUpdate(true);
			if (null==QRCodeManagerVo.getCreateDate()){
				QRCodeManagerVo.setCreateDate(new Date());
			}
			if (StringUtils.isBlank(QRCodeManagerVo.getCreateUser())) {
				QRCodeManagerVo.setCreateUser(userId);
			}
			QRCodeManagerVo QRCodeManagerVoSave=QRCodeManagerClient.saveQRCodeManager(QRCodeManagerVo);
			if (QRCodeApplicationPropertyVoList != null&&!QRCodeApplicationPropertyVoList.isEmpty()) {
				List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoListOld=QRCodeApplicationPropertyClient.findApplicationPropertyByQRCodeManagerId(QRCodeManagerVoSave.getId());
				if (!ObjectUtils.isEmpty(QRCodeApplicationPropertyVoListOld)) {
					Map<String,String> collectedIdMap = QRCodeApplicationPropertyVoListOld.stream().collect(Collectors.toMap(QRCodeApplicationPropertyVo::getPropertyId,QRCodeApplicationPropertyVo::getId));

					QRCodeApplicationPropertyVoList.stream().filter(o -> collectedIdMap.containsKey(o.getPropertyId())).forEach(o -> o.setId(collectedIdMap.get(o.getPropertyId())));
				}
				for (QRCodeApplicationPropertyVo QRCodeApplicationPropertyVo:QRCodeApplicationPropertyVoList){
					String id=QRCodeApplicationPropertyVo.getId();
					if (StringUtils.isBlank(id)||id.length()<32) {
						QRCodeApplicationPropertyVo.setId(null);
					}
					QRCodeApplicationPropertyVo.setApplicationId(QRCodeManagerVoSave.getApplicationId());
					QRCodeApplicationPropertyVo.setQRCodeManagerId(QRCodeManagerVoSave.getId());
				}
			}
			QRCodeApplicationPropertyClient.saveApplicationPropertyList(QRCodeApplicationPropertyVoList);
			List<String> deleteQRCodeManager=QRCodeManagerVo.getDeleteQRCodeApplicationPropertyVoList();
			if (deleteQRCodeManager != null&&!deleteQRCodeManager.isEmpty()&&deleteQRCodeManager.get(0)!=null) {
				QRCodeApplicationPropertyClient.deleteApplicationPropertyByIds(QRCodeManagerVo.getDeleteQRCodeApplicationPropertyVoList());
			}
			return EnerbosMessage.createSuccessMsg(QRCodeManagerVoSave, "保存二维码管理成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/qrCode/saveQRCodeManager ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString();
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/saveQRCodeManager----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * findQRCodeManagerByID:根据ID查询二维码管理
	 * @param id
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据ID查询二维码管理", response = QRCodeManagerVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/findQRCodeManagerByID", method = RequestMethod.GET)
	public EnerbosMessage findQRCodeManagerByID(@ApiParam(value = "二维码管理id", required = true) @RequestParam("id") String id,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/findQRCodeManagerByID, host: [{}:{}], service_id: {}, id: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id);

			QRCodeManagerVo QRCodeManagerVo=QRCodeManagerClient.findQRCodeManagerByID(id);
			if (null!=QRCodeManagerVo) {
				if (null!=QRCodeManagerVo.getSiteId()) {
					SiteVoForDetail site=siteClient.findById(QRCodeManagerVo.getSiteId());
					if (null!=site) {
						QRCodeManagerVo.setSiteName(site.getName());
					}
				}
				if (null!=QRCodeManagerVo.getOrgId()) {
					OrgVoForDetail org=orgClient.findById(QRCodeManagerVo.getOrgId());
					if (null!=org) {
						QRCodeManagerVo.setOrgName(org.getName());
					}
				}
				if (StringUtils.isNotBlank(QRCodeManagerVo.getApplicationId())) {
					QRCodeApplicationVo QRCodeApplicationVo=QRCodeApplicationClient.findQRCodeApplicationByID(QRCodeManagerVo.getApplicationId());
					if (null!=QRCodeApplicationVo) {
						QRCodeManagerVo.setApplicationValue(QRCodeApplicationVo.getValue());
						QRCodeManagerVo.setApplicationName(QRCodeApplicationVo.getDescription());
					}
				}
				if (StringUtils.isNotBlank(QRCodeManagerVo.getId())) {
					List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList=QRCodeApplicationPropertyClient.findApplicationPropertyByQRCodeManagerId(QRCodeManagerVo.getId());
					QRCodeManagerVo.setQRCodeApplicationPropertyVoList(QRCodeApplicationPropertyVoList);
				}
			}
			return EnerbosMessage.createSuccessMsg(QRCodeManagerVo, "根据ID查询二维码管理成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/findWorkOrderCommitById ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/findWorkOrderCommitById----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

    /**
	 * findPageQRCodeManager: 分页模糊查询二维码管理
	 * @param QRCodeManagerForFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo}
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询二维码管理", response = QRCodeManagerVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/qrCode/findPageQRCodeManager", method = RequestMethod.POST)
    public EnerbosMessage findPageQRCodeManager(@ApiParam(value = "二维码管理模糊搜索查询条件VO", required = true) @RequestBody QRCodeManagerForFilterVo QRCodeManagerForFilterVo,Principal user) {
        try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/findPageQRCodeManager, host: [{}:{}], service_id: {}, QRCodeManagerForFilterVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), QRCodeManagerForFilterVo);

			if (QRCodeManagerForFilterVo.getWords()!=null) {
        		String words=QRCodeManagerForFilterVo.getWords();
            	String[] word=StringUtils.split(words, " ");
				QRCodeManagerForFilterVo.setWordsList(Arrays.asList(word));
			}
        	EnerbosPage<QRCodeManagerVo> QRCodeManagerForFilterVoList=QRCodeManagerClient.findPageQRCodeManager(QRCodeManagerForFilterVo);
			if (QRCodeManagerForFilterVoList != null&&null!=QRCodeManagerForFilterVoList.getList()&&QRCodeManagerForFilterVoList.getList().size()>0) {
				for(QRCodeManagerVo QRCodeManagerVo:QRCodeManagerForFilterVoList.getList()){
					if (QRCodeManagerVo != null&&QRCodeManagerVo.getSiteId()!=null) {
						SiteVoForDetail site=siteClient.findById(QRCodeManagerVo.getSiteId());
						if (site != null) {
							QRCodeManagerVo.setSiteName(site.getDescription());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(QRCodeManagerForFilterVoList, "分页模糊查询二维码管理", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/qrCode/findPageQRCodeManager ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString();
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/findPageQRCodeManager----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
	 * checkQRCodeNumUnique:验证二维码管理编码的唯一性
	 * @param id         二维码管理id
	 * @param QRCodeNum 二维码管理编码
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
    @ApiOperation(value = "验证二维码管理编码的唯一性", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/checkQRCodeNumUnique", method = RequestMethod.GET)
	public EnerbosMessage checkQRCodeNumUnique(@ApiParam(value = "二维码管理id", required = false) @RequestParam(name="id", required = false) String id,
			@ApiParam(value = "二维码管理编码", required = true) @RequestParam("QRCodeNum") String QRCodeNum,Principal user) {
	    Boolean flag = false;
	    try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/checkQRCodeNumUnique, host: [{}:{}], service_id: {}, id: {}, QRCodeNum: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,QRCodeNum);

			QRCodeManagerVo jpn = QRCodeManagerClient.findQRCodeManagerByQRCodeNum(QRCodeNum);
	        if (id == null) {//新建时
	        	if (jpn == null) {
	              flag = true;
	            }
	        } else {//修改时
              if (jpn != null&&jpn.getId().equals(id)||jpn == null) {
                  flag = true;
              }
	        }
	        return EnerbosMessage.createSuccessMsg(flag, "验证二维码管理编码的唯一性成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/checkQRCodeNumUnique ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/checkQRCodeNumUnique----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
	    }
	}

    /**
     * deleteQRCodeManagerList:删除选中的二维码管理
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除二维码管理", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/qrCode/deleteQRCodeManagerList", method = RequestMethod.GET)
    public EnerbosMessage deleteQRCodeManagerList(@ApiParam(value = "删除选中的二维码管理ids", required = true) @RequestParam("ids") List<String> ids,Principal user) {
    	try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/deleteQRCodeManagerList, host: [{}:{}], service_id: {}, ids: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids);

			for (String id : ids) {
				QRCodeManagerVo QRCodeManagerVo=QRCodeManagerClient.findQRCodeManagerByID(id);
                if (null == QRCodeManagerVo) {
                	return EnerbosMessage.createErrorMsg("500","要删除的二维码管理不存在", "");
				}
			}
            return EnerbosMessage.createSuccessMsg(QRCodeManagerClient.deleteQRCodeManagerList(ids), "删除二维码管理成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/qrCode/deleteQRCodeManagerList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString();
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/deleteQRCodeManagerList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * findQRCodeApplicationList: 查询二维码应用程序列表
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "查询二维码应用程序列表", response = QRCodeApplicationVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/findQRCodeApplicationList", method = RequestMethod.GET)
	public EnerbosMessage findQRCodeApplicationList(Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/findQRCodeApplicationList, host: [{}:{}], service_id: {}", instance.getHost(), instance.getPort(), instance.getServiceId());

			EnerbosPage<QRCodeApplicationVo> QRCodeApplicationVoList=QRCodeApplicationClient.findPageQRCodeApplication();
			return EnerbosMessage.createSuccessMsg(QRCodeApplicationVoList, "查询二维码应用程序列表成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/findQRCodeApplicationList ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/findQRCodeApplicationList----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findAllApplicationAllProperty: 查询二维码应用程序所有属性列表
	 * @param applicationId 二维码应用程序ID
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "查询二维码应用程序所有属性列表", response = QRCodeApplicationAllPropertyVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/findAllApplicationAllPropertyNotIn", method = RequestMethod.GET)
	public EnerbosMessage findAllApplicationAllPropertyNotIn(@ApiParam(value = "二维码应用程序ID", required = true) @RequestParam("applicationId") String applicationId,
															 @ApiParam(value = "应用属性ID", required = false) @RequestParam(value = "ids",required = false) List<String> ids,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/findAllApplicationAllProperty, host: [{}:{}], service_id: {}, user: {},applicationId:{},ids:{}", instance.getHost(), instance.getPort(), instance.getServiceId(),user,applicationId,ids);

			List<QRCodeApplicationAllPropertyVo> QRCodeApplicationAllPropertyVoList=QRCodeApplicationAllPropertyClient.findAllApplicationAllPropertyNotIn(applicationId,ids);
			return EnerbosMessage.createSuccessMsg(QRCodeApplicationAllPropertyVoList, "查询二维码应用程序所有属性列表成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/findAllApplicationAllProperty ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/findAllApplicationAllProperty----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * generateQRCode: 生成二维码
	 * @param siteId           站点ID
	 * @param applicationValue 二维码应用程序值
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "生成二维码", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/generateQRCode", method = RequestMethod.POST)
	public EnerbosMessage generateQRCode(@ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,
										 @ApiParam(value = "二维码应用程序值", required = false) @RequestParam(name="applicationValue",required = false) String applicationValue,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/generateQRCode, host: [{}:{}], service_id: {},siteId:{},applicationValue:{}", instance.getHost(), instance.getPort(), instance.getServiceId(),siteId,applicationValue);

			Boolean result= QRCodeManagerClient.updateQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue,false);
			return EnerbosMessage.createSuccessMsg(result, "生成二维码成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/generateQRCode ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/generateQRCode----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * generateQRCodeNum: 生成二维码编码
	 * @param applicationValue 二维码应用程序值
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "生成二维码编码", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/generateQRCodeNum", method = RequestMethod.GET)
	public EnerbosMessage generateQRCodeNum(@ApiParam(value = "二维码应用程序值", required = false) @RequestParam(name="applicationValue",required = false) String applicationValue,
			@ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/generateQRCodeNum, host: [{}:{}], service_id: {},siteId:{},applicationValue:{}", instance.getHost(), instance.getPort(), instance.getServiceId(),siteId,applicationValue);

			if (siteId == null) {
				return EnerbosMessage.createSuccessMsg(null, "没有站点ID", "");
			}
			String suffixQRCode=QRCodeManagerClient.generateQRCodeManagerSuffixQRCode(siteId,applicationValue);
			return EnerbosMessage.createSuccessMsg(suffixQRCode, "生成二维码编码成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/generateQRCodeNum ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/generateQRCodeNum----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findQrcodeImge: 查看二维码
	 * @param id
	 * @param applicationValue 二维码应用程序值
	 * @param response
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "查看二维码", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/findQrcodeImge", method = RequestMethod.GET)
	public EnerbosMessage findQrcodeImge(@ApiParam(value = "ID", required = true) @RequestParam("id") String id,
										 @ApiParam(value = "二维码应用程序值", required = true) @RequestParam(name="applicationValue",required = true) String applicationValue,
										 @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,HttpServletResponse response,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/findQrcodeImge, host: [{}:{}], service_id: {},id:{},applicationValue:{},siteId:{}", instance.getHost(), instance.getPort(), instance.getServiceId(),id,applicationValue,siteId);

			QRCodeManagerVo QRCodeManagerVo=QRCodeManagerClient.findQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue);
			if (QRCodeManagerVo == null||"".equals(QRCodeManagerVo)||null==QRCodeManagerVo.getId()||"".equals(QRCodeManagerVo.getId())) {
				return EnerbosMessage.createSuccessMsg(null, "二维码管理没有配置此站点应用程序", "");
			}
			List list=findApplicationData(Collections.singletonList(id),null,siteId,QRCodeManagerVo.getClassName(),QRCodeManagerVo.getMethodName(),null,user.getName());
			if (list == null||list.size()<1) {
				return EnerbosMessage.createSuccessMsg(null, "根据二维码编码查看二维码成功", "");
			}
			Map<String,Object> map = new HashMap();
			map.put("id",id);
			map.put("type",applicationValue);
			JSONObject jsonObject = new JSONObject(map);
			String strmap = jsonObject.toString();
			QRCodeVo qrCode=new QRCodeVo();
			qrCode.setContent(strmap);
			QRCodeGenerateService.findQrcodeImge(qrCode,response);
			return EnerbosMessage.createSuccessMsg(null, "查看二维码成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/findQrcodeImge ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/findQrcodeImge----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findQrcodeImgeByQRCodeNum: 根据二维码编码查看二维码
	 * @param QRCodeNum 应用程序数据对应的编码
	 * @param response
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据二维码编码查看二维码", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/findQrcodeImgeByQRCodeNum", method = RequestMethod.GET)
	public EnerbosMessage findQrcodeImgeByQRCodeNum(@ApiParam(value = "应用程序数据对应的编码", required = true) @RequestParam(name="QRCodeNum",required = true) String QRCodeNum,
			@ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,HttpServletResponse response,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/findQrcodeImgeByQRCodeNum, host: [{}:{}], service_id: {},QRCodeNum:{},siteId:{}", instance.getHost(), instance.getPort(), instance.getServiceId(),QRCodeNum,siteId);

			if (StringUtils.indexOf(QRCodeNum,"0")<0) {
				return EnerbosMessage.createErrorMsg(null, "编码无效", "");
			}
			//编码规则是application中的编码前缀+应用程序表里存的二维码编码字段
//			String[] num=StringUtils.split(QRCodeNum, QRCodeManagerCommon.QRCODENUMDIVISION,2);
			QRCodeManagerVo QRCodeManagerVo=QRCodeManagerClient.findPageQRCodeManagerBySiteIdAndPrefixQRCode(siteId,QRCodeNum.substring(0,2));
			List list=findApplicationData(null,QRCodeNum.substring(2),siteId,QRCodeManagerVo.getClassName(),QRCodeManagerVo.getMethodName(),QRCodeManagerVo.getUpdateDataUpdateMethodName(),user.getName());
			if (list == null||list.size()<1) {
				return EnerbosMessage.createErrorMsg(null, "编码无效", "");
			}
			String applicationValue=QRCodeManagerVo.getApplicationValue();
			Map<String,Object> map = new HashMap();
			map.put("id",ReflectionUtils.getFieldValue(list.get(0), "id"));
			map.put("type",applicationValue);
			JSONObject jsonObject = new JSONObject(map);
			String strmap = jsonObject.toString();
			QRCodeVo qrCode=new QRCodeVo();
			qrCode.setContent(strmap);
			QRCodeGenerateService.findQrcodeImge(qrCode,response);
			return EnerbosMessage.createSuccessMsg(null, "根据二维码编码查看二维码成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/findQrcodeImge ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/findQrcodeImgeByQRCodeNum----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * processQrcodeGeneratePdf: 生成二维码下载pdf
	 * @param ids
	 * @param applicationValue 二维码应用程序值
	 * @param response
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "生成二维码下载pdf", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/qrCode/processQrcodeGeneratePdf", method = RequestMethod.GET)
	public EnerbosMessage processQrcodeGeneratePdf(@ApiParam(value = "ID", required = false) @RequestParam(name="ids",required = false) List<String> ids,
												   @ApiParam(value = "二维码应用程序值", required = true) @RequestParam("applicationValue") String applicationValue,
												   @ApiParam(value = "组织ID", required = true) @RequestParam("orgId") String orgId,
												   @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,HttpServletResponse response,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/qrCode/processQrcodeGeneratePdf, host: [{}:{}], service_id: {},ids:{},applicationValue:{},siteId:{},orgId:{}", instance.getHost(), instance.getPort(), instance.getServiceId(),ids,applicationValue,siteId,orgId);

			if (ids==null||ids.isEmpty()) {
				return EnerbosMessage.createSuccessMsg(null, "请选择要下载的数据", "");
			}
			String title="";
			if (StringUtils.isNotBlank(siteId)){
				SiteVoForDetail site=siteClient.findById(siteId);
				if (null!=site){
					title+=site.getName();
				}
			}
			if (StringUtils.isNotBlank(orgId)){
				OrgVoForDetail org=orgClient.findById(orgId);
				if (null!=org){
					if (StringUtils.isNotBlank(title)) {
						title+="/";
					}
					title+=org.getName();
				}
			}
			QRCodeManagerVo QRCodeManagerVo=QRCodeManagerClient.findQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue);
			if (QRCodeManagerVo == null||"".equals(QRCodeManagerVo)||null==QRCodeManagerVo.getId()||"".equals(QRCodeManagerVo.getId())) {
				return EnerbosMessage.createSuccessMsg(null, "二维码管理没有配置此站点应用程序", "");
			}
			List list=findApplicationData(ids,null,siteId,QRCodeManagerVo.getClassName(),QRCodeManagerVo.getMethodName(),QRCodeManagerVo.getUpdateDataUpdateMethodName(),user.getName());
			QRCodeManagerClient.updateQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue,true);
			List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList=QRCodeApplicationPropertyClient.findApplicationPropertyByQRCodeManagerId(QRCodeManagerVo.getId());
			List<QRCodeVo> QRCodeVoList = new ArrayList<QRCodeVo>();
			if (list == null||list.size()<=0) {
				return EnerbosMessage.createSuccessMsg("无数据", "生成二维码下载pdf成功", "");
			}
			for (Object object: list){
				List<String> infoList = new ArrayList<String>();
				if (null!=QRCodeApplicationPropertyVoList&&QRCodeApplicationPropertyVoList.size()>0) {
					for (QRCodeApplicationPropertyVo QRCodeApplicationPropertyVo: QRCodeApplicationPropertyVoList){
						if (infoList.size()==4) {
							break;
						}
						QRCodeApplicationPropertyVo.getPropertyName();
						Object obj=ReflectionUtils.getFieldValue(object,QRCodeApplicationPropertyVo.getProperty());
						if (obj == null) {
							String info=QRCodeApplicationPropertyVo.getPropertyName()+": ";
							infoList.add(info);
						}else {
							String value=ReflectionUtils.getFieldValue(object,QRCodeApplicationPropertyVo.getProperty()).toString();
//							if (value.length()>12) {
//								value=value.substring(0,11)+"...";
//							}
							String info=QRCodeApplicationPropertyVo.getPropertyName()+":"+value+123;
							infoList.add(info);
						}
					}
				}
				Map<String,Object> map = new HashMap();
				map.put("id",ReflectionUtils.getFieldValue(object,"id").toString());
				map.put("type",applicationValue);
				JSONObject jsonObject = new JSONObject(map);
				String strmap = jsonObject.toString();
				QRCodeVo qrCode=new QRCodeVo();
				qrCode.setTitle(title);
				qrCode.setContent(strmap);
				qrCode.setInfolist(infoList);
				qrCode.setQRCodeNum(QRCodeManagerVo.getPrefixQRCode()+ReflectionUtils.getFieldValue(object, QRCodeManagerCommon.APPLICATION_QRCODENUM));
				qrCode.setModelName(QRCodeManagerVo.getApplicationName());
				QRCodeVoList.add(qrCode);
			}
			QRCodeGenerateService.processQrcodeGeneratePdf(QRCodeVoList,response);
			return EnerbosMessage.createSuccessMsg(null, "生成二维码下载pdf成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/qrCode/processQrcodeGeneratePdf ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/qrCode/processQrcodeGeneratePdf----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * findApplicationData：根据applicationValue查询相应功能的数据
	 * @param ids
	 * @param QRCodeNum 二维码后缀编码
	 * @param siteId 站点ID
	 * @param className 功能对应的类方法路径
	 * @param methodName 根据ID，二维码后缀编码，站点ID查询数据方法名
	 * @param updateDataUpdateMethodName 更新是否已更新字段方法名，此方法入参是ID,siteId,状态更新为false
	 * @return
	 */
	private List findApplicationData( List<String> ids,String QRCodeNum,String siteId,String className,String methodName,String updateDataUpdateMethodName,String userName){
		Class<?>[] parameterTypes=null;
		Class ss=null;
		try {
			ss=Class.forName(className);
		} catch (ClassNotFoundException e) {
			logger.error("-----findApplicationData----","application配置class不存在");
		}
		Object implClass=context.getBean(ss);
		Object[] parameters=null;
		if (ids != null&&ids.size()>0) {
			//根据ID查询数据
			parameterTypes= new Class[]{List.class,String.class,String.class,String.class};
			parameters=new Object[]{ids,null,null,userName};
		}else if (QRCodeNum!=null){
			//根据二维码编码QRCodeNum和站点ID查询数据
			parameterTypes= new Class[]{List.class,String.class,String.class,String.class};
			parameters=new Object[]{null,QRCodeNum,siteId,userName};
		}else {
			//查询站点所有数据
			parameterTypes= new Class[]{List.class,String.class,String.class,String.class};
			parameters=new Object[]{null,null,siteId,userName};
		}
		List list=(List)ReflectionUtils.invokeMethod(implClass,methodName,parameterTypes,parameters);
//		//下载pdf，更新数据表的是否已更新数据字段为false
//		if (null!=updateDataUpdateMethodName) {
//			parameterTypes= new Class[]{List.class,String.class};
//			parameters=new Object[]{ids,siteId};
//			ReflectionUtils.invokeMethod(implClass,updateDataUpdateMethodName,parameterTypes,parameters);
//		}
		return list;
	}
}