package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.ClassificationClient;
import com.enerbos.cloud.ams.vo.classification.ClassificationVoForDetail;
import com.enerbos.cloud.eam.client.MaintenanceJobStandardItemClient;
import com.enerbos.cloud.eam.client.MaintenanceJobStandardTaskClient;
import com.enerbos.cloud.eam.client.MaintenanceMaintenancePlanClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.JobStandardCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.MaintenanceJobStandardClient;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.SiteClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;
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
 * @date 2017年06月07日
 * @Description EAM作业标准接口
 */
@RestController
@Api(description = "作业标准(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaintenanceJobStandardController {
    private final static Logger logger = LoggerFactory.getLogger(MaintenanceJobStandardController.class);

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
	private SiteClient siteClient;
    
    @Autowired
	private OrgClient orgClient;

	@Autowired
	private PersonAndUserClient personClient;

    @Autowired
    private MaintenanceJobStandardClient maintenanceJobStandardClient;

	@Autowired
	private MaintenanceJobStandardItemClient maintenanceJobStandardItemClient;

	@Autowired
	private MaintenanceJobStandardTaskClient maintenanceJobStandardTaskClient;

	@Autowired
	private MaintenanceMaintenancePlanClient maintenanceMaintenancePlanClient;

	@Autowired
	private ClassificationClient classificationClient;

    /**
     * 保存作业标准
     * @param maintenanceJobStandardVo {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存作业标准", response = MaintenanceJobStandardVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/jobStandard/saveJobStandard", method = RequestMethod.POST)
    public EnerbosMessage saveJobStandard(@ApiParam(value = "作业标准VO", required = true) @Valid @RequestBody MaintenanceJobStandardVo maintenanceJobStandardVo, Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/saveJobStandard, host: [{}:{}], service_id: {}, MaintenanceJobStandardVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceJobStandardVo);
			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			MaintenanceJobStandardVo ewo = maintenanceJobStandardClient.findJobStandardByJobStandardNum(maintenanceJobStandardVo.getJobStandardNum());
        	if (null == maintenanceJobStandardVo.getId()) {
                if (ewo != null && !"".equals(ewo)) {
                    return EnerbosMessage.createSuccessMsg("", "作业标准编码重复", "");
                }
            }else {
            	if (ewo != null && !maintenanceJobStandardVo.getId().equals(ewo.getId())) {
                    return EnerbosMessage.createSuccessMsg("", "作业标准编码重复", "");
                }
            }
			if (StringUtils.isBlank(maintenanceJobStandardVo.getId())&&!Common.JOBSTANDARD_STATUS_DRAFT.equals(maintenanceJobStandardVo.getStatus())) {
				return EnerbosMessage.createErrorMsg("400", "新建状态只能为草稿", "");
			}
			if (StringUtils.isBlank(maintenanceJobStandardVo.getId())) {
				maintenanceJobStandardVo.setCreateUser(userId);
			}
			maintenanceJobStandardVo.setCreateUser(userId);
			maintenanceJobStandardVo.setCreateDate(new Date());
			MaintenanceJobStandardVo maintenanceJobStandardVoSave=maintenanceJobStandardClient.saveJobStandard(maintenanceJobStandardVo);
        	//保存物料列表
			List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList=maintenanceJobStandardVo.getMaintenanceJobStandardItemVoList();
			if (maintenanceJobStandardItemVoList != null&&maintenanceJobStandardItemVoList.size()>0) {
				for (MaintenanceJobStandardItemVo maintenanceJobStandardItemVo : maintenanceJobStandardItemVoList) {
					String itemId=maintenanceJobStandardItemVo.getId();
					if (StringUtils.isBlank(itemId)||itemId.length()<32) {
						maintenanceJobStandardItemVo.setId(null);
					}
					maintenanceJobStandardItemVo.setCreateUser(maintenanceJobStandardVoSave.getCreateUser());
					maintenanceJobStandardItemVo.setJobStandardId(maintenanceJobStandardVoSave.getId());
					maintenanceJobStandardItemVo.setCreateUser(userId);
					maintenanceJobStandardItemVo.setCreateDate(new Date());
				}
				maintenanceJobStandardItemVoList=maintenanceJobStandardItemClient.saveMaintenanceJobStandardItemList(maintenanceJobStandardItemVoList);
			}
			List<String> deleteJobStandardList=maintenanceJobStandardVo.getDeleteMaintenanceJobStandardItemVoList();
			if (deleteJobStandardList != null&&deleteJobStandardList.size()>0) {
				//删除物料
				maintenanceJobStandardItemClient.deleteEamJobItem(maintenanceJobStandardVo.getDeleteMaintenanceJobStandardItemVoList());
			}
			//保存任务列表
			List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList=maintenanceJobStandardVo.getMaintenanceJobStandardTaskVoList();
			if (maintenanceJobStandardTaskVoList != null&&maintenanceJobStandardTaskVoList.size()>0) {
				for (MaintenanceJobStandardTaskVo maintenanceJobStandardTaskVo : maintenanceJobStandardTaskVoList) {
					String taskId=maintenanceJobStandardTaskVo.getId();
					if (StringUtils.isBlank(taskId)||taskId.length()<32) {
						maintenanceJobStandardTaskVo.setId(null);
					}
					if (StringUtils.isBlank(maintenanceJobStandardTaskVo.getCreateUser())){
						maintenanceJobStandardTaskVo.setCreateUser(userId);
					}
					maintenanceJobStandardTaskVo.setJobStandardId(maintenanceJobStandardVoSave.getId());
					maintenanceJobStandardTaskVo.setCreateUser(userId);
					maintenanceJobStandardTaskVo.setCreateDate(new Date());
				}
				maintenanceJobStandardTaskVoList=maintenanceJobStandardTaskClient.saveMaintenanceJobStandardTaskList(maintenanceJobStandardTaskVoList);
			}
			List<String> deleteJobStandardTaskList=maintenanceJobStandardVo.getDeleteMaintenanceJobStandardTaskVoList();
			if (deleteJobStandardTaskList != null&&deleteJobStandardTaskList.size()>0) {
				//删除物料
				maintenanceJobStandardTaskClient.deleteJobTask(deleteJobStandardTaskList,maintenanceJobStandardVo.getId());
			}
			return EnerbosMessage.createSuccessMsg(maintenanceJobStandardVoSave, "保存作业标准成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/jobStandard/saveJobStandard ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/saveJobStandard----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * updateJobStandardStatus:修改作业标准状态
	 * @param statusVo {@link com.enerbos.cloud.eam.vo.StatusVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "修改作业标准状态", response = StatusVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/jobStandard/updateJobStandardStatus", method = RequestMethod.POST)
	public EnerbosMessage updateJobStandardStatus(@ApiParam(value = "状态VO", required = true) @Valid @RequestBody StatusVo statusVo, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/jobStandard/updateJobStandardStatus, host: [{}:{}], service_id: {}, StatusVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), statusVo);
			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			List<String> ids=statusVo.getIds();
			if (ids != null&&!ids.isEmpty()) {
				for (String id:ids){
					MaintenanceJobStandardVo ewo = maintenanceJobStandardClient.findJobStandardById(id);
					if (ewo == null||StringUtils.isBlank(ewo.getId())) {
						return EnerbosMessage.createSuccessMsg("401", "作业标准不存在", "");
					}
					if (Common.JOBSTANDARD_STATUS_DRAFT.equals(statusVo.getStatus())) {
						return EnerbosMessage.createErrorMsg("401", "状态不能修改为草稿", "");
					}
					if (JobStandardCommon.JOB_STANDARD_STATUS_ACTIVITY.equals(ewo.getStatus())) {
						MaintenanceMaintenancePlanSelectVo select=new MaintenanceMaintenancePlanSelectVo();
						select.setJobStandardId(ewo.getId());
						select.setStatus(Collections.singletonList(Common.MAINTENANCE_PLAN_STATUS_ACTIVITY));
						EnerbosPage<MaintenanceMaintenancePlanForListVo> page=maintenanceMaintenancePlanClient.findPageMaintenancePlanList(select);
						if (page!=null&&page.getTotal()>0) {
							String maintenancePlanNum="";
							for (int i = 0; i <page.getTotal()&&i <3 ; i++) {
								maintenancePlanNum+=page.getList().get(i).getMaintenancePlanNum()+",";
							}
							maintenancePlanNum=maintenancePlanNum.substring(0,maintenancePlanNum.length()-1);
							return EnerbosMessage.createErrorMsg("401", "作业标准编码为"+ewo.getJobStandardNum()+"已被"+maintenancePlanNum+"等"+page.getTotal()+"个预防维护计划使用,请先停用关联的计划!", "");
						}
					}
				}
				for (String id:ids){
					statusVo=maintenanceJobStandardClient.updateJobStandardStatus(statusVo);
				}
			}
			return EnerbosMessage.createSuccessMsg(statusVo, "修改作业标准状态", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/jobStandard/updateJobStandardStatus ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/updateJobStandardStatus----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

    /**
	 * findPageJobStandardList: 分页查询作业标准
	 * @param maintenanceJobStandardSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardSelectVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询作业标准", response = MaintenanceJobStandardForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/jobStandard/findPageJobStandardList", method = RequestMethod.POST)
    public EnerbosMessage findPageJobStandardList(@ApiParam(value = "作业标准选择条件VO", required = true) @RequestBody MaintenanceJobStandardSelectVo maintenanceJobStandardSelectVo,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/findPageJobStandardList, host: [{}:{}], service_id: {}, eamJobStandardSelectVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceJobStandardSelectVo);
			if (Objects.isNull(maintenanceJobStandardSelectVo)) {
				maintenanceJobStandardSelectVo = new MaintenanceJobStandardSelectVo();
			}
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			PersonAndUserVoForDetail personVo = personClient.findByPersonId(personId);
			Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
			//设置查询人，用于获取关注信息
			maintenanceJobStandardSelectVo.setPersonId(personId);
        	if (maintenanceJobStandardSelectVo.getWords()!=null) {
				String words=maintenanceJobStandardSelectVo.getWords();
				String[] word= StringUtils.split(words, " ");
				maintenanceJobStandardSelectVo.setWordsList(Arrays.asList(word));
			}
        	EnerbosPage<MaintenanceJobStandardForListVo> maintenanceJobStandardVos=maintenanceJobStandardClient.findPageJobStandardList(maintenanceJobStandardSelectVo);
			List<MaintenanceJobStandardForListVo> maintenanceJobStandardVoList=maintenanceJobStandardVos.getList();
			if (maintenanceJobStandardVoList != null) {
				for (MaintenanceJobStandardForListVo maintenanceJobStandardForListVo:maintenanceJobStandardVoList){
					String siteId=maintenanceJobStandardForListVo.getSiteId();
					if (null!=siteId&&!"".equals(siteId)){
						SiteVoForDetail site=siteClient.findById(siteId);
						if (null!=site){
							maintenanceJobStandardForListVo.setSiteName(site.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(maintenanceJobStandardVos, "分页查询作业标准成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/jobStandard/findPageJobStandardList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/findPageJobStandardList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findPageJobStandardByAssetId: 根据设备ID分页查询作业标准
	 * @param maintenanceForAssetFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceJobStandardSelectVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据设备ID分页查询作业标准", response = MaintenanceJobStandardForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/jobStandard/findPageJobStandardByAssetId", method = RequestMethod.POST)
    public EnerbosMessage findPageJobStandardByAssetId(@ApiParam(value = "作业标准选择条件VO", required = true) @Valid @RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/findPageJobStandardByAssetId, host: [{}:{}], service_id: {}, MaintenanceForAssetFilterVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), maintenanceForAssetFilterVo);
        	EnerbosPage<MaintenanceJobStandardForListVo> maintenanceJobStandardVos=maintenanceJobStandardClient.findPageJobStandardByAssetId(maintenanceForAssetFilterVo);
			List<MaintenanceJobStandardForListVo> maintenanceJobStandardVoList=maintenanceJobStandardVos.getList();
			for (MaintenanceJobStandardForListVo maintenanceJobStandardForListVo:maintenanceJobStandardVoList){
				String siteId=maintenanceJobStandardForListVo.getSiteId();
				if (null!=siteId&&!"".equals(siteId)){
					SiteVoForDetail site=siteClient.findById(siteId);
					if (null!=site){
						maintenanceJobStandardForListVo.setSiteName(site.getName());
					}
				}
			}
        	return EnerbosMessage.createSuccessMsg(maintenanceJobStandardVos, "根据设备ID分页查询作业标准成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/jobStandard/findPageJobStandardByAssetId ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/findPageJobStandardByAssetId----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findJobStandardById: 根据ID查询作业标准
	 * @param id 
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询作业标准", response = MaintenanceJobStandardVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/jobStandard/findJobStandardById", method = RequestMethod.GET)
    public EnerbosMessage findJobStandardById(@ApiParam(value = "作业标准ID", required = true) @RequestParam("id") String id,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/findJobStandardById, host: [{}:{}], service_id: {}, id: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id);
        	MaintenanceJobStandardVo maintenanceJobStandardVo=maintenanceJobStandardClient.findJobStandardById(id);
			String siteId=maintenanceJobStandardVo.getSiteId();
			if (null!=siteId&&!"".equals(siteId)){
				SiteVoForDetail site=siteClient.findById(siteId);
				if (null!=site){
					maintenanceJobStandardVo.setSiteName(site.getName());
				}
			}
			String orgId=maintenanceJobStandardVo.getOrgId();
			if (null!=orgId&&!"".equals(orgId)){
				OrgVoForDetail org=orgClient.findById(orgId);
				if (null!=org){
					maintenanceJobStandardVo.setOrgName(org.getName());
				}
			}
			String classificationId=maintenanceJobStandardVo.getClassificationId();
			if (null!=classificationId&&!"".equals(classificationId)){
				ClassificationVoForDetail classification=classificationClient.findById(user.getName(),classificationId);
				if (null!=classification){
					maintenanceJobStandardVo.setClassificationName(classification.getName());
					maintenanceJobStandardVo.setClassificationNum(classification.getCode());
				}
			}
        	List<MaintenanceJobStandardItemVo> maintenanceJobStandardItemVoList=maintenanceJobStandardItemClient.findJobStandardItemByJobStandardId(id);
			if (null!=maintenanceJobStandardItemVoList&&maintenanceJobStandardItemVoList.size()>0){
				maintenanceJobStandardVo.setMaintenanceJobStandardItemVoList(maintenanceJobStandardItemVoList);
			}
			List<MaintenanceJobStandardTaskVo> maintenanceJobStandardTaskVoList=maintenanceJobStandardTaskClient.findJobStandardTaskByJobStandardId(id);
			if (null!=maintenanceJobStandardTaskVoList&&maintenanceJobStandardTaskVoList.size()>0){
				maintenanceJobStandardVo.setMaintenanceJobStandardTaskVoList(maintenanceJobStandardTaskVoList);
			}
        	return EnerbosMessage.createSuccessMsg(maintenanceJobStandardVo, "根据ID查询作业标准成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/jobStandard/findJobStandardById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/findJobStandardById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * checkJobStandardNumUnique:验证作业编码的唯一性
	 * @param id   作业标准id
	 * @param jobStandardNum
	 * @param user 作业标准编码
	 * @return EnerbosMessage返回执行码及数据
	 */
    @ApiOperation(value = "验证作业编码的唯一性", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/jobStandard/checkJobStandardNumUnique", method = RequestMethod.GET)
	public EnerbosMessage checkJobStandardNumUnique(@ApiParam(value = "作业标准id", required = false) @RequestParam(name="id", required = false) String id,
													@ApiParam(value = "作业标准编码", required = true) @RequestParam("jobStandardNum") String jobStandardNum,Principal user) {
	    Boolean flag = false;
	    try {
	    	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/checkJobStandardNumUnique, host: [{}:{}], service_id: {},id: {}, jobStandardNum: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,jobStandardNum);
        	MaintenanceJobStandardVo ewo = maintenanceJobStandardClient.findJobStandardByJobStandardNum(jobStandardNum);
        	if (null == id) {
                if (ewo== null) {
					flag = true;
                }
            }else {
            	if (ewo != null && id.equals(ewo.getId())||ewo == null) {
					flag = true;
                }
            }
	        return EnerbosMessage.createSuccessMsg(flag, "验证作业编码的唯一性成功", "");
	    } catch (Exception e) {
	    	logger.error("-----/eam/open/jobStandard/checkJobStandardNumUnique ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/checkJobStandardNumUnique----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
	    }
	}
    
    /**
     * 删除选中的作业标准
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除作业标准", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/jobStandard/deleteJobStandard", method = RequestMethod.GET)
    public EnerbosMessage deleteJobStandard(@ApiParam(value = "删除选中的作业标准ids", required = true) @RequestParam("id") String id,Principal user) {
    	try {
    		ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/deleteJobStandard, host: [{}:{}], service_id: {}, id: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id);
    		MaintenanceJobStandardVo jobplan = maintenanceJobStandardClient.findJobStandardById(id);
			if(jobplan == null ){
				return EnerbosMessage.createErrorMsg("","要删除的作业标准不存在", "");
			}else if (!JobStandardCommon.JOB_STANDARD_JOB_TYPE.equals(jobplan.getStatus())) {
				return EnerbosMessage.createErrorMsg("","作业标准编码为"+jobplan.getJobStandardNum()+"的状态不是草稿，不能删除", "");
			}
			maintenanceJobStandardClient.deleteJobStandard(id);
			List<String> ids=new ArrayList<>();
			ids.add(id);
			maintenanceJobStandardTaskClient.deleteJobStandardTaskByJobStandardIds(ids);
			maintenanceJobStandardItemClient.deleteJobStandardItemByJobStandardIds(ids);
            return EnerbosMessage.createSuccessMsg(true, "删除作业标准成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/jobStandard/deleteJobStandard ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/deleteJobStandard----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 删除选中的作业标准
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除作业标准", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/jobStandard/deleteJobStandardList", method = RequestMethod.GET)
    public EnerbosMessage deleteJobStandardList(@ApiParam(value = "删除选中的作业标准ids", required = true) @RequestParam("ids") List<String> ids,Principal user) {
    	try {
    		ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/jobStandard/deleteJobStandardList, host: [{}:{}], service_id: {}, ids: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids);
        	for (String id : ids) {
        		MaintenanceJobStandardVo jobplan = maintenanceJobStandardClient.findJobStandardById(id);
				if(jobplan == null ){
					return EnerbosMessage.createErrorMsg("","要删除的作业标准不存在", "");
				}
			}
        	maintenanceJobStandardClient.deleteJobStandardList(ids);
			maintenanceJobStandardTaskClient.deleteJobStandardTaskByJobStandardIds(ids);
			maintenanceJobStandardItemClient.deleteJobStandardItemByJobStandardIds(ids);
            return EnerbosMessage.createSuccessMsg(true, "删除作业标准成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/jobStandard/deleteJobStandardList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/jobStandard/deleteJobStandardList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * 作业标准-收藏
	 * @param ids 作业标准ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "作业标准-收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/jobStandard/collect", method = RequestMethod.POST)
	public EnerbosMessage collectJobStandard(@ApiParam(value = "作业标准ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/jobStandard/collect, host: [{}:{}], service_id: {}, jobStandardIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要收藏的作业标准！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<MaintenanceJobStandardRfCollectorVo> jobStandardRfCollectorVos = Arrays.stream(ids).map(o -> new MaintenanceJobStandardRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			maintenanceJobStandardClient.collectJobStandard(jobStandardRfCollectorVos);

			return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/jobStandard/collect ------", e);
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
	 * 作业标准-取消收藏
	 * @param ids 作业标准ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "作业标准-取消收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/jobStandard/collect/cancel", method = RequestMethod.POST)
	public EnerbosMessage cancelCollectJobStandard(@ApiParam(value = "作业标准ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/jobStandard/collect/cancel, host: [{}:{}], service_id: {}, jobStandardIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要取消收藏的作业标准！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<MaintenanceJobStandardRfCollectorVo> jobStandardRfCollectorVos = Arrays.stream(ids).map(o -> new MaintenanceJobStandardRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			maintenanceJobStandardClient.cancelCollectJobStandard(jobStandardRfCollectorVos);
			return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/jobStandard/collect/cancel ------", e);
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
}