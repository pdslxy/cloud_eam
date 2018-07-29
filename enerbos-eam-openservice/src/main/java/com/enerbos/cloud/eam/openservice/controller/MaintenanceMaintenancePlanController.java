package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForFilter;
import com.enerbos.cloud.ams.vo.asset.AssetVoForList;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.WorkOrderCommon;
import com.enerbos.cloud.eam.openservice.config.KafkaWarningRunner;
import com.enerbos.cloud.eam.openservice.config.TimedTask;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.tts.client.EamTimerTaskClient;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.constants.UASConstants;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
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
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM预防维护计划接口
 */
@RestController
@Api(description = "预防维护计划(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaintenanceMaintenancePlanController {
	private final static Logger logger = LoggerFactory.getLogger(MaintenanceMaintenancePlanController.class);

	@Autowired
    private DiscoveryClient client;
    
    @Autowired
	private SiteClient siteClient;
    
    @Autowired
	private OrgClient orgClient;
    
    @Autowired
	private PersonAndUserClient personAndUserClient;

	@Resource
	private AssetClient assetClient;
    
    @Autowired
    private MaintenanceMaintenancePlanClient maintenanceMaintenancePlanClient;
    
    @Autowired
    private MaintenanceMaintenancePlanAssetClient maintenanceMaintenancePlanAssetClient;
    
    @Autowired
    private MaintenanceMaintenancePlanActiveTimeClient maintenanceMaintenancePlanActiveTimeClient;
    
    @Autowired
	private MaintenanceJobStandardClient maintenanceJobStandardClient;

	@Autowired
	private LocationClient locationClient;

	@Autowired
	private ContractClient contractClient;

	@Autowired
	private EamTimerTaskClient eamTimerTaskClient;

	@Autowired
	private TimedTask timedTask;



    /**
     * saveMaintenancePlan:保存预防维护计划
     * @param maintenanceMaintenancePlanVo
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存预防维护计划", response = MaintenanceMaintenancePlanVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/maintenancePlan/saveMaintenancePlan", method = RequestMethod.POST)
    public EnerbosMessage saveMaintenancePlan(@ApiParam(value = "预防维护计划VO", required = true) @Valid @RequestBody MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo, Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/maintenancePlan/saveMaintenancePlan, host: [{}:{}], service_id: {}, MaintenanceMaintenancePlanVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceMaintenancePlanVo);
			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			MaintenanceMaintenancePlanVo ewo = maintenanceMaintenancePlanClient.findMaintenancePlanByMaintenancePlanNum(maintenanceMaintenancePlanVo.getMaintenancePlanNum());
			if (StringUtils.isBlank(maintenanceMaintenancePlanVo.getId())) {
				if (ewo != null && !"".equals(ewo)) {
					return EnerbosMessage.createErrorMsg("400", "预防维护计划编码重复", "");
				}
			}else {
				if (ewo != null && !maintenanceMaintenancePlanVo.getId().equals(ewo.getId())) {
					return EnerbosMessage.createErrorMsg("400", "预防维护计划编码重复", "");
				}
			}
			if (StringUtils.isBlank(maintenanceMaintenancePlanVo.getId())&&!Common.MAINTENANCE_PLAN_STATUS_DRAFT.equals(maintenanceMaintenancePlanVo.getStatus())) {
				return EnerbosMessage.createErrorMsg("400", "新建状态只能为草稿", "");
			}
			if (maintenanceMaintenancePlanVo.getJobStandardId() != null) {
        		MaintenanceJobStandardVo maintenanceJobStandardVo=maintenanceJobStandardClient.findJobStandardById(maintenanceMaintenancePlanVo.getJobStandardId());
                if (null!=maintenanceJobStandardVo){
					maintenanceMaintenancePlanVo.setJobStandardId(maintenanceJobStandardVo.getId());
				}
            }
			if (StringUtils.isBlank(maintenanceMaintenancePlanVo.getId())) {
				maintenanceMaintenancePlanVo.setCreateUser(userId);
			}
			maintenanceMaintenancePlanVo.setCreateUser(userId);
			maintenanceMaintenancePlanVo.setCreateDate(new Date());
			maintenanceMaintenancePlanVo.setWorkOrderType(WorkOrderCommon.WORK_ORDER_WORK_ORDER_TYPE);
			maintenanceMaintenancePlanVo.setWorkOrderStatus(WorkOrderCommon.WORK_ORDER_STATUS_DFP);
			MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVoSave=maintenanceMaintenancePlanClient.saveMaintenancePlan(maintenanceMaintenancePlanVo);
			//重置定时任务
			if (Common.MAINTENANCE_PLAN_STATUS_ACTIVITY.equals(maintenanceMaintenancePlanVoSave.getStatus())) {
				startEamMaintenancePlanTask(maintenanceMaintenancePlanVoSave);
			}
			List<MaintenanceMaintenancePlanActiveTimeVo> maintenanceMaintenancePlanActiveTimeVoList=maintenanceMaintenancePlanActiveTimeClient.findAllMaintenancePlanActiveTime(maintenanceMaintenancePlanVoSave.getId());
			//保存有效时间列表
			List<MaintenanceMaintenancePlanActiveTimeVo> maintenancePlanActiveTimeVoList=maintenanceMaintenancePlanVo.getMaintenancePlanActiveTimeVoList();
			if (null!=maintenancePlanActiveTimeVoList&&maintenancePlanActiveTimeVoList.size()>0){
				for (MaintenanceMaintenancePlanActiveTimeVo maintenanceMaintenancePlanActiveTimeVo:maintenancePlanActiveTimeVoList){
					String activeTimeId=maintenanceMaintenancePlanActiveTimeVo.getId();
					if (null==activeTimeId||"".equals(activeTimeId)||activeTimeId.length()<32) {
						maintenanceMaintenancePlanActiveTimeVo.setId(null);
					}
					maintenanceMaintenancePlanActiveTimeVo.setMaintenancePlanId(maintenanceMaintenancePlanVoSave.getId());
				}
				maintenancePlanActiveTimeVoList=maintenanceMaintenancePlanActiveTimeClient.saveMaintenancePlanActiveTimeList(maintenancePlanActiveTimeVoList);
			}
			List<String> deleteActiveTime=maintenanceMaintenancePlanVo.getDeleteMaintenancePlanActiveTimeVoList();
			if (deleteActiveTime!= null&&deleteActiveTime.size()>0) {
				//删除有效时间
				maintenanceMaintenancePlanActiveTimeClient.deleteMaintenancePlanActiveTimeListByIds(deleteActiveTime);
			}
			List<String> deleteAssetList=maintenanceMaintenancePlanVo.getDeleteAssetList();
			if (null!=deleteAssetList&&deleteAssetList.size()>0) {
				//删除关联设备
				maintenanceMaintenancePlanAssetClient.deleteMaintenancePlanAssetByAssetIds(maintenanceMaintenancePlanVoSave.getId(),maintenanceMaintenancePlanVo.getDeleteAssetList());
			}
			List<String> assets=maintenanceMaintenancePlanAssetClient.findAllMaintenancePlanAsset(maintenanceMaintenancePlanVoSave.getId());
			//添加关联设备到预防维护计划
			List<MaintenanceMaintenancePlanAssetVo> addAssetList=maintenanceMaintenancePlanVo.getAddAssetList();
			if (null!=addAssetList&&addAssetList.size()>0) {
				for (MaintenanceMaintenancePlanAssetVo maintenancePlanAssetVo : addAssetList) {
					if (StringUtils.isBlank(maintenancePlanAssetVo.getAssetId())) {
						continue;
					}
					if (assets.contains(maintenancePlanAssetVo.getAssetId())) {
						continue;
					}
					maintenancePlanAssetVo.setId(null);
					maintenancePlanAssetVo.setMaintenancePlanId(maintenanceMaintenancePlanVoSave.getId());
				}
				maintenanceMaintenancePlanAssetClient.saveMaintenancePlanAssetList(addAssetList);
			}
			return EnerbosMessage.createSuccessMsg(maintenanceMaintenancePlanVoSave, "保存预防维护计划成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/maintenancePlan/saveMaintenancePlan ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/saveMaintenancePlan----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * updateMaintenancePlanStatus:变更预防维护计划状态
	 * @param statusVo {@link com.enerbos.cloud.eam.vo.StatusVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "变更预防维护计划状态", response = StatusVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/maintenancePlan/updateMaintenancePlanStatus", method = RequestMethod.POST)
	public EnerbosMessage updateMaintenancePlanStatus(@ApiParam(value = "预防维护计划VO", required = true) @Valid @RequestBody StatusVo statusVo, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/maintenancePlan/saveMaintenancePlan, host: [{}:{}], service_id: {}, StatusVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), statusVo);
//			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<String> ids=statusVo.getIds();
			if (ids != null&&!ids.isEmpty()) {
				for (String id:ids){
					MaintenanceMaintenancePlanVo ewo = maintenanceMaintenancePlanClient.findMaintenancePlanById(id);
					if (ewo == null) {
						return EnerbosMessage.createSuccessMsg("400", "预防性维护计划不存在", "");
					}
					if (Common.MAINTENANCE_PLAN_STATUS_DRAFT.equals(statusVo.getStatus())) {
						return EnerbosMessage.createErrorMsg("400", "状态不能修改为草稿", "");
					}else if (Common.MAINTENANCE_PLAN_STATUS_ACTIVITY.equals(statusVo.getStatus())) {
						startEamMaintenancePlanTask(ewo);
					}else if (Common.MAINTENANCE_PLAN_STATUS_INACTIVITY.equals(statusVo.getStatus())) {
						deleteEamMaintenancePlanTask(ewo);
					}
				}
				statusVo=maintenanceMaintenancePlanClient.updateMaintenancePlanStatus(statusVo);
			}
			return EnerbosMessage.createSuccessMsg(statusVo, "变更预防维护计划状态", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/maintenancePlan/updateMaintenancePlanStatus ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/updateMaintenancePlanStatus----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

    /**
     * findPageMaintenancePlanList:分页查询预防维护计划
	 * @param maintenanceMaintenancePlanSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询预防维护计划", response = MaintenanceMaintenancePlanVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/maintenancePlan/findPageMaintenancePlanList", method = RequestMethod.POST)
    public EnerbosMessage findPageMaintenancePlanList(@ApiParam(value = "预防维护计划查询条件VO", required = true) @RequestBody MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/maintenancePlan/findMasterPmSeasonsList, host: [{}:{}], service_id: {}, MaintenanceMaintenancePlanSelectVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceMaintenancePlanSelectVo);

			if (Objects.isNull(maintenanceMaintenancePlanSelectVo)) {
				maintenanceMaintenancePlanSelectVo = new MaintenanceMaintenancePlanSelectVo();
			}
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
			Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
			//设置查询人，用于获取关注信息
			maintenanceMaintenancePlanSelectVo.setPersonId(personId);

        	if (StringUtils.isNotBlank(maintenanceMaintenancePlanSelectVo.getWords())) {
				String words=maintenanceMaintenancePlanSelectVo.getWords();
				String[] word= StringUtils.split(words, " ");
				maintenanceMaintenancePlanSelectVo.setWordsList(Arrays.asList(word));
			}
        	EnerbosPage<MaintenanceMaintenancePlanForListVo> maintenanceMaintenancePlanVos=maintenanceMaintenancePlanClient.findPageMaintenancePlanList(maintenanceMaintenancePlanSelectVo);
			List<MaintenanceMaintenancePlanForListVo> maintenanceMaintenancePlanVoList=maintenanceMaintenancePlanVos.getList();
			for (MaintenanceMaintenancePlanForListVo maintenanceMaintenancePlanVo:maintenanceMaintenancePlanVoList) {
				String siteId = maintenanceMaintenancePlanVo.getSiteId();
				if (StringUtils.isNotBlank(siteId)) {
					SiteVoForDetail site = siteClient.findById(siteId);
					if (null != site) {
						maintenanceMaintenancePlanVo.setSiteName(site.getName());
					}
				}
				String jobStandardId = maintenanceMaintenancePlanVo.getJobStandardId();
				if (StringUtils.isNotBlank(jobStandardId)) {
					MaintenanceJobStandardVo maintenanceJobStandardVo = maintenanceJobStandardClient.findJobStandardById(jobStandardId);
					if (null != maintenanceJobStandardVo) {
						maintenanceMaintenancePlanVo.setJobStandardDesc(maintenanceJobStandardVo.getDescription());
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceMaintenancePlanVos, "分页查询预防维护计划成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/maintenancePlan/findPageMaintenancePlanList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/findPageMaintenancePlanList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * findPageMaintenancePlanByContractId:根据合同id分页查询预防维护计划
	 * @param maintenanceMaintenancePlanSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据合同id分页查询预防维护计划", response = MaintenanceMaintenancePlanVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/maintenancePlan/findPageMaintenancePlanByContractId", method = RequestMethod.POST)
	public EnerbosMessage findPageMaintenancePlanByContractId(@ApiParam(value = "预防维护计划查询条件VO", required = true) @RequestBody MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/maintenancePlan/findPageMaintenancePlanByContractId, host: [{}:{}], service_id: {}, MaintenanceMaintenancePlanSelectVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceMaintenancePlanSelectVo);

			if (Objects.isNull(maintenanceMaintenancePlanSelectVo)||StringUtils.isBlank(maintenanceMaintenancePlanSelectVo.getContractId())) {
				return EnerbosMessage.createErrorMsg("", "合同id不能为空", null);
			}
			EnerbosPage<MaintenanceMaintenancePlanForListVo> maintenanceMaintenancePlanVos=maintenanceMaintenancePlanClient.findPageMaintenancePlanList(maintenanceMaintenancePlanSelectVo);
			List<MaintenanceMaintenancePlanForListVo> maintenanceMaintenancePlanVoList=maintenanceMaintenancePlanVos.getList();
			for (MaintenanceMaintenancePlanForListVo maintenanceMaintenancePlanVo:maintenanceMaintenancePlanVoList) {
				String siteId = maintenanceMaintenancePlanVo.getSiteId();
				if (StringUtils.isNotBlank(siteId)) {
					SiteVoForDetail site = siteClient.findById(siteId);
					if (null != site) {
						maintenanceMaintenancePlanVo.setSiteName(site.getName());
					}
				}
				String jobStandardId = maintenanceMaintenancePlanVo.getJobStandardId();
				if (StringUtils.isNotBlank(jobStandardId)) {
					MaintenanceJobStandardVo maintenanceJobStandardVo = maintenanceJobStandardClient.findJobStandardById(jobStandardId);
					if (null != maintenanceJobStandardVo) {
						maintenanceMaintenancePlanVo.setJobStandardDesc(maintenanceJobStandardVo.getDescription());
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceMaintenancePlanVos, "根据合同id分页查询预防维护计划成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/maintenancePlan/findPageMaintenancePlanByContractId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/findPageMaintenancePlanByContractId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
    
    /**
     * findMaintenancePlanById:根据ID查询预防维护计划
	 * @param id 
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询预防维护计划", response = MaintenanceMaintenancePlanVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/maintenancePlan/findMaintenancePlanById", method = RequestMethod.GET)
    public EnerbosMessage findMaintenancePlanById(@ApiParam(value = "预防维护计划id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/maintenancePlan/findMasterPmSeasonsList, host: [{}:{}], service_id: {}, id: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id);
        	MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo=maintenanceMaintenancePlanClient.findMaintenancePlanById(id);
			if (maintenanceMaintenancePlanVo != null) {
				String siteId = maintenanceMaintenancePlanVo.getSiteId();
				if (StringUtils.isNotBlank(siteId)) {
					SiteVoForDetail site = siteClient.findById(siteId);
					if (null != site) {
						maintenanceMaintenancePlanVo.setSiteName(site.getName());
					}
				}
				String jobStandardId = maintenanceMaintenancePlanVo.getJobStandardId();
				if (StringUtils.isNotBlank(jobStandardId)) {
					MaintenanceJobStandardVo maintenanceJobStandardVo = maintenanceJobStandardClient.findJobStandardById(jobStandardId);
					if (null != maintenanceJobStandardVo) {
						maintenanceMaintenancePlanVo.setJobStandardNum(maintenanceJobStandardVo.getJobStandardNum());
						maintenanceMaintenancePlanVo.setJobStandardDesc(maintenanceJobStandardVo.getDescription());
					}
				}
				String locationId = maintenanceMaintenancePlanVo.getLocationId();
				if (StringUtils.isNotBlank(locationId)) {
					LocationVoForDetail locationVoForDetail = locationClient.findById(user.getName(),locationId);
					if (null != locationVoForDetail) {
						maintenanceMaintenancePlanVo.setLocationNum(locationVoForDetail.getCode());
						maintenanceMaintenancePlanVo.setLocationDesc(locationVoForDetail.getName());
					}
				}
				String personliableId = maintenanceMaintenancePlanVo.getPersonliableId();
				if (StringUtils.isNotBlank(personliableId)) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personliableId);
					if (null != personVo) {
						maintenanceMaintenancePlanVo.setPersonliableName(personVo.getName());
					}
				}
				String assignPersonId = maintenanceMaintenancePlanVo.getAssignPersonId();
				if (StringUtils.isNotBlank(assignPersonId)) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(assignPersonId);
					if (null != personVo) {
						maintenanceMaintenancePlanVo.setAssignPersonName(personVo.getName());
					}
				}
				if (null!=maintenanceMaintenancePlanVo.getContractId()) {
					ContractVo contractVo=contractClient.findContractVoById(maintenanceMaintenancePlanVo.getContractId());
					if (null!=contractVo) {
						maintenanceMaintenancePlanVo.setContractNum(contractVo.getContractNum());
						maintenanceMaintenancePlanVo.setContractName(contractVo.getDescription());
						maintenanceMaintenancePlanVo.setContractCompany(contractVo.getContractCompany());
					}
				}
				String createUser = maintenanceMaintenancePlanVo.getCreateUser();
				if (StringUtils.isNotBlank(createUser)) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(createUser);
					if (null != personVo) {
						maintenanceMaintenancePlanVo.setCreateUserName(personVo.getName());
					}
				}
				//查询有效时间列表
				List<MaintenanceMaintenancePlanActiveTimeVo> maintenancePlanActiveTimeVoList=maintenanceMaintenancePlanActiveTimeClient.findAllMaintenancePlanActiveTime(maintenanceMaintenancePlanVo.getId());
				if(null!=maintenancePlanActiveTimeVoList){
					maintenanceMaintenancePlanVo.setMaintenancePlanActiveTimeVoList(maintenancePlanActiveTimeVoList);
				}
				//查询关联设备
				List<String> assets=maintenanceMaintenancePlanAssetClient.findAllMaintenancePlanAsset(maintenanceMaintenancePlanVo.getId());
				if (null!=assets&&assets.size()>0){
					AssetVoForFilter assetVoForFilter=new AssetVoForFilter();
					assetVoForFilter.setAssetIds(assets);
					assetVoForFilter.setPageNum(1);
					assetVoForFilter.setPageSize(assets.size());
					assetVoForFilter.setProductArray(new String[]{UASConstants.PRODUCT_EAM_ID});
					EnerbosPage page=assetClient.findPage(user.getName(), assetVoForFilter);
					if (page != null) {
						List<AssetVoForList> pageAsset=page.getList();
						maintenanceMaintenancePlanVo.setAssetList(pageAsset);
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceMaintenancePlanVo, "根据ID查询预防维护计划成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/maintenancePlan/findMaintenancePlanById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/findMaintenancePlanById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * checkMaintenancePlanNumUnique:验证预防性维护编码的唯一性
	 * @param id  预防性维护计划ID
	 * @param maintenancePlanNum 预防性维护计划编码
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
    @ApiOperation(value = "验证预防性维护编码的唯一性", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/maintenancePlan/checkMaintenancePlanNumUnique", method = RequestMethod.GET)
	public EnerbosMessage checkMaintenancePlanNumUnique(@ApiParam(value = "预防性维护id", required = false) @RequestParam(name="id", required = false) String id,
			@ApiParam(value = "预防性维护编码", required = true) @RequestParam("maintenancePlanNum") String maintenancePlanNum,Principal user) {
	    Boolean flag = false;
	    try {
	    	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/maintenancePlan/checkMaintenancePlanNumUnique, host: [{}:{}], service_id: {}, id: {}, maintenancePlanNum: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, maintenancePlanNum);
        	MaintenanceMaintenancePlanVo jpn = maintenanceMaintenancePlanClient.findMaintenancePlanByMaintenancePlanNum(maintenancePlanNum);
	        if (id == null) {//新建时
	        	if (jpn == null) {
	              flag = true;
	            }
	        } else {//修改时
              if (jpn != null&&id.equals(jpn.getMaintenancePlanNum())||jpn == null) {
                  flag = true;
              }
	        }
	        return EnerbosMessage.createSuccessMsg(flag, "验证预防性维护编码的唯一性成功", "");
	    } catch (Exception e) {
	    	logger.error("-----/eam/open/maintenancePlan/checkMaintenancePlanNumUnique ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/checkMaintenancePlanNumUnique----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
	    }
	}

    /**
     * deleteMaintenancePlanList:删除选中的预防维护计划
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除预防维护计划", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/maintenancePlan/deleteMaintenancePlanList", method = RequestMethod.POST)
    public EnerbosMessage deleteMaintenancePlanList(@ApiParam(value = "删除选中的预防维护计划ids", required = true) @RequestParam("ids") List<String> ids,Principal user) {
    	try {
    		ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/maintenancePlan/deleteMaintenancePlanList, host: [{}:{}], service_id: {}, ids: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids);
        	for (String id : ids) {
        		MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo = maintenanceMaintenancePlanClient.findMaintenancePlanById(id);
				if (maintenanceMaintenancePlanVo == null) {
					return EnerbosMessage.createErrorMsg("500",id+"数据不存在", "");
				}
				if (!Common.MAINTENANCE_PLAN_STATUS_DRAFT.equals(maintenanceMaintenancePlanVo.getStatus())){
					return EnerbosMessage.createErrorMsg("401",maintenanceMaintenancePlanVo.getMaintenancePlanNum()+"的状态不是草稿,不能删除", "");
				}
            }
        	maintenanceMaintenancePlanClient.deleteMaintenancePlanList(ids);
			maintenanceMaintenancePlanAssetClient.deleteMaintenancePlanAssetByMaintenancePlanIds(ids);
			maintenanceMaintenancePlanActiveTimeClient.deleteMaintenancePlanActiveTimeByMaintenancePlanIds(ids);
            return EnerbosMessage.createSuccessMsg(true, "删除预防维护计划成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/maintenancePlan/deleteMaintenancePlanList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/deleteMaintenancePlanList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * findPageMaintenancePlanByAssetId:根据设备ID分页查询预防性维护计划
	 * @param maintenanceForAssetFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanSelectVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据设备ID分页查询预防性维护计划", response = MaintenanceMaintenancePlanVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/maintenancePlan/findPageMaintenancePlanByAssetId", method = RequestMethod.POST)
	public EnerbosMessage findPageMaintenancePlanByAssetId(@ApiParam(value = "根据设备ID分页查询预防维护计划查询条件VO", required = true) @Valid @RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/maintenancePlan/findPageMaintenancePlanByAssetId, host: [{}:{}], service_id: {}, MaintenanceForAssetFilterVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceForAssetFilterVo);
			EnerbosPage<MaintenanceMaintenancePlanForListVo> maintenanceMaintenancePlanVos=maintenanceMaintenancePlanClient.findPageMaintenancePlanByAssetId(maintenanceForAssetFilterVo);
			List<MaintenanceMaintenancePlanForListVo> maintenanceMaintenancePlanVoList=maintenanceMaintenancePlanVos.getList();
			for (MaintenanceMaintenancePlanForListVo maintenanceMaintenancePlanVo:maintenanceMaintenancePlanVoList) {
				String siteId = maintenanceMaintenancePlanVo.getSiteId();
				if (StringUtils.isNotBlank(siteId)) {
					SiteVoForDetail site = siteClient.findById(siteId);
					if (null != site) {
						maintenanceMaintenancePlanVo.setSiteName(site.getName());
					}
				}
				String jobStandardId = maintenanceMaintenancePlanVo.getJobStandardId();
				if (StringUtils.isNotBlank(jobStandardId)) {
					MaintenanceJobStandardVo maintenanceJobStandardVo = maintenanceJobStandardClient.findJobStandardById(jobStandardId);
					if (null != maintenanceJobStandardVo) {
						maintenanceMaintenancePlanVo.setJobStandardDesc(maintenanceJobStandardVo.getDescription());
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceMaintenancePlanVos, "根据设备ID分页查询预防性维护计划成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/maintenancePlan/findPageMaintenancePlanByAssetId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/maintenancePlan/findPageMaintenancePlanByAssetId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * 预防性维护计划-收藏
	 * @param ids 预防性维护计划ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "预防性维护计划-收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/maintenancePlan/collect", method = RequestMethod.POST)
	public EnerbosMessage collectMaintenancePlan(@ApiParam(value = "预防性维护计划ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/maintenancePlan/collect, host: [{}:{}], service_id: {}, maintenancePlanIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要收藏的预防性维护计划！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<MaintenanceMaintenancePlanRfCollectorVo> maintenancePlanRfCollectorVos = Arrays.stream(ids).map(o -> new MaintenanceMaintenancePlanRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			maintenanceMaintenancePlanClient.collectMaintenancePlan(maintenancePlanRfCollectorVos);

			return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/maintenancePlan/collect ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * 预防性维护计划-取消收藏
	 * @param ids 预防性维护计划ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "预防性维护计划-取消收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/maintenancePlan/collect/cancel", method = RequestMethod.POST)
	public EnerbosMessage cancelCollectMaintenancePlan(@ApiParam(value = "预防性维护计划ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/maintenancePlan/collect/cancel, host: [{}:{}], service_id: {}, maintenancePlanIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要取消收藏的预防性维护计划！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<MaintenanceMaintenancePlanRfCollectorVo> maintenancePlanRfCollectorVos = Arrays.stream(ids).map(o -> new MaintenanceMaintenancePlanRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			maintenanceMaintenancePlanClient.cancelCollectMaintenancePlan(maintenancePlanRfCollectorVos);
			return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/maintenancePlan/collect/cancel ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	public void startEamMaintenancePlanTask(MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo){
//		Boolean useTargetDate=maintenanceMaintenancePlanVo.getUseTargetDate();
//		Boolean result=false;
//		if (useTargetDate) {
//			String nextDay;
//			String nextMonth;
//			String nextYear;
//			if (maintenanceMaintenancePlanVo.getExtDate()!=null){
//				nextDay=String.valueOf(maintenanceMaintenancePlanVo.getExtDate().getDay());
//				nextMonth=String.valueOf(maintenanceMaintenancePlanVo.getExtDate().getMonth());
//				nextYear=String.valueOf(maintenanceMaintenancePlanVo.getExtDate().getYear());
//			}else {
//				nextDay=String.valueOf(maintenanceMaintenancePlanVo.getNextDate().getDay());
//				nextMonth=String.valueOf(maintenanceMaintenancePlanVo.getNextDate().getMonth());
//				nextYear=String.valueOf(maintenanceMaintenancePlanVo.getNextDate().getYear());
//			}
////			String cron=Common.MAINTENANCEPLAN_TASK_TIME+" "+nextDay+"/* "+nextMonth+" ? "+nextYear;
////			result=eamTimerTaskClient.startEamMaintenancePlanTask(maintenanceMaintenancePlanVo.getId(),"",10,null,maintenanceMaintenancePlanVo.getId());
////		}else {
////			result=eamTimerTaskClient.startEamMaintenancePlanTask(maintenanceMaintenancePlanVo.getId(),"",10,null,maintenanceMaintenancePlanVo.getId());
//		}

		Boolean useTargetDate=maintenanceMaintenancePlanVo.getUseTargetDate();
		Boolean result=false;
		String cron="";
//		if (StringUtils.isBlank(maintenanceMaintenancePlanVo.getWeekFrequency())||"1,2,3,4,5,6,7".equals(maintenanceMaintenancePlanVo.getWeekFrequency())) {
//			if ("day".equals(maintenanceMaintenancePlanVo.getFrequencyUnit())) {
//				cron="* * * "+maintenanceMaintenancePlanVo.getFrequency()+" * ? *";
//			}
//			if ("month".equals(maintenanceMaintenancePlanVo.getFrequencyUnit())) {
//				cron="* * * * "+maintenanceMaintenancePlanVo.getFrequency()+" ? *";
//			}
//			if ("week".equals(maintenanceMaintenancePlanVo.getFrequencyUnit())) {
//				cron="* * * * "+maintenanceMaintenancePlanVo.getFrequency()*7+" ? *";
//			}
//			if ("year".equals(maintenanceMaintenancePlanVo.getFrequencyUnit())) {
//				cron="* * * * * ? "+maintenanceMaintenancePlanVo.getFrequency();
//			}
//			cron="* * * * * ? *";
//		}else {
			String nextDay;
			String nextMonth;
			String nextYear;
			if (maintenanceMaintenancePlanVo.getExtDate()!=null){
				nextDay=String.valueOf(maintenanceMaintenancePlanVo.getExtDate().getDay());
				nextMonth=String.valueOf(maintenanceMaintenancePlanVo.getExtDate().getMonth());
				nextYear=String.valueOf(maintenanceMaintenancePlanVo.getExtDate().getYear());
			}else {
				nextDay=String.valueOf(maintenanceMaintenancePlanVo.getNextDate().getDay());
				nextMonth=String.valueOf(maintenanceMaintenancePlanVo.getNextDate().getMonth());
				nextYear=String.valueOf(maintenanceMaintenancePlanVo.getNextDate().getYear());
			}
			//cron="0 40 16 * * ? *";
		cron=timedTask.getCron().get("maintenencePlan");
		if(StringUtils.isEmpty(cron)){
			cron="0 01 01 * * ? *";
		}
		result=eamTimerTaskClient.startEamMaintenancePlanTask(maintenanceMaintenancePlanVo.getId(),cron,1,null,maintenanceMaintenancePlanVo.getId());
		logger.info("定时任务执行,结果:{}",result);
//		}
//		result=eamTimerTaskClient.startEamMaintenancePlanTask(maintenanceMaintenancePlanVo.getId(),cron,10,null,maintenanceMaintenancePlanVo.getId());
	}

	public void deleteEamMaintenancePlanTask(MaintenanceMaintenancePlanVo maintenanceMaintenancePlanVo){
		Boolean result=eamTimerTaskClient.deleteEamTask(maintenanceMaintenancePlanVo.getId());
		logger.info("定时任务执行,结果:{}",result);
	}
}