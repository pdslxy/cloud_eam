package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.CompanyClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForFilter;
import com.enerbos.cloud.ams.vo.asset.AssetVoForList;
import com.enerbos.cloud.ams.vo.company.CompanyVoForDetail;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DefectCommon;
import com.enerbos.cloud.eam.contants.WorkOrderCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import com.enerbos.cloud.wfs.vo.TaskForFilterVo;
import com.enerbos.cloud.wfs.vo.TaskVo;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import zipkin.collector.Collector;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * @date 2017年09月06日
 * @Description 缺陷单接口
 */
@RestController
@Api(description = "缺陷单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DefectDocumentController {

    private Logger logger = LoggerFactory.getLogger(DefectDocumentController.class);

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
	private SiteClient siteClient;
    
    @Autowired
	private LocationClient locationClient;
    
    @Autowired
	private PersonAndUserClient personAndUserClient;

	@Autowired
	private UgroupClient ugroupClient;
    
    @Autowired
	private OrgClient orgClient;
    
    @Resource
	private DefectDocumentClient defectDocumentClient;


	@Resource
	private DefectOrderController defectOrderController;
    
    @Autowired
	private ProcessActivitiClient processActivitiClient;

	@Resource
	protected WorkflowClient workflowClient;

	@Autowired
	private ProcessTaskClient processTaskClient;

	@Autowired
	private UserGroupDomainClient userGroupDomainClient;

	@Autowired
	private HeadArchivesClient headArchivesClient ;

    /**
     * saveDefectDocument:保存缺陷单-工单提报
     * @param defectDocumentVo {@link DefectDocumentVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存缺陷单-工单提报", response = DefectDocumentVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/saveDefectDocumentCommit", method = RequestMethod.POST)
    public EnerbosMessage saveDefectDocumentCommit(@ApiParam(value = "缺陷单-工单提报VO", required = true) @Valid @RequestBody DefectDocumentVo defectDocumentVo, Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectDocument/saveDefectDocument, host: [{}:{}], service_id: {}, DefectDocumentVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectDocumentVo);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			DefectDocumentVo ewo = defectDocumentClient.findDefectDocumentByDefectDocumentNum(defectDocumentVo.getDefectDocumentNum());
			if (StringUtils.isBlank(defectDocumentVo.getId())) {
                if (ewo != null && !"".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "缺陷单编码重复", "");
                }
            }else {
            	if (ewo != null && !defectDocumentVo.getId().equals(ewo.getId())) {
                    return EnerbosMessage.createErrorMsg("", "缺陷单编码重复", "");
                }
            }
        	if (!DefectCommon.DEFECT_DOCUMENT_STATUS_DTB.equals(defectDocumentVo.getStatus())) {
            	return EnerbosMessage.createErrorMsg("", "缺陷单状态不是待提报，不允许保存！", "");
            }
			if (StringUtils.isBlank(defectDocumentVo.getCreateUser())) {
				defectDocumentVo.setCreateUser(userId);
			} else if (!userId.equals(defectDocumentVo.getCreateUser())) {
				return EnerbosMessage.createErrorMsg("", "缺陷单创建人只能是自己", "");
			}
//			if (StringUtils.isBlank(defectDocumentVo.getReportId()) ) {
//				defectDocumentVo.setReportId(userId);
//			} else if (!userId.equals(defectDocumentVo.getReportId())) {
//				return EnerbosMessage.createErrorMsg("", "缺陷单提报人只能是自己", "");
//			}
//        	defectDocumentVo.setStatusDate(new Date());//状态日期
			defectDocumentVo.setReportDate(new Date());//提报日期
			if (StringUtils.isBlank(defectDocumentVo.getId())) {
				defectDocumentVo.setCreateUser(userId);
			}
			if (defectDocumentVo.getCreateDate() == null) {
				defectDocumentVo.setCreateDate(new Date());
			}

			DefectDocumentVo defectDocumentVoSave=defectDocumentClient.saveDefectDocument(defectDocumentVo);
			return EnerbosMessage.createSuccessMsg(defectDocumentVoSave, "保存缺陷单-工单提报保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectDocument/saveDefectDocumentCommit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/saveDefectDocumentCommit----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * updateDefectDocumentStatus:修改缺陷单状态
     * @param id 缺陷单ID
     * @param status 状态
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "修改缺陷单状态", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/updateDefectDocumentStatus", method = RequestMethod.POST)
    public EnerbosMessage updateDefectDocumentStatus(@ApiParam(value = "缺陷单id", required = true) @RequestParam("id") String id,
    		@ApiParam(value = "status", required = true) @RequestParam("status") String status,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/defectDocument/updateDefectDocumentStatus, host: [{}:{}], service_id: {}, id: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,status);
            
    		DefectDocumentVo ewo = defectDocumentClient.findDefectDocumentById(id);
            if (ewo == null || "".equals(ewo)) {
                return EnerbosMessage.createErrorMsg("", "缺陷单不存在", "");
            }
//            ewo.setStatusDate(new Date());//状态日期
            ewo.setStatus(status);
            defectDocumentClient.saveDefectDocument(ewo);
            return EnerbosMessage.createSuccessMsg(true, "修改缺陷单状态成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectDocument/updateDefectDocumentStatus ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/updateDefectDocumentStatus----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * updateDefectDocumentStatusList:批量修改缺陷单状态
     * @param ids    缺陷单ID数组{@link List<String>}
	 * @param status 缺陷单状态
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改缺陷单状态", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/updateDefectDocumentStatusList", method = RequestMethod.POST)
    public EnerbosMessage updateDefectDocumentStatusList(@ApiParam(value = "缺陷单id", required = true) @RequestParam("ids") List<String> ids,
    		@ApiParam(value = "status", required = true) @RequestParam("status") String status,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/defectDocument/updateDefectDocumentStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids,status);
            
        	List<DefectDocumentVo> list=new ArrayList<>();
        	for (String id : ids) {
				DefectDocumentVo ewo = defectDocumentClient.findDefectDocumentById(id);
                if (ewo== null || "".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "缺陷单不存在", "");
                }
//                ewo.setStatusDate(new Date());//状态日期
                ewo.setUpdateDate(new Date());
                ewo.setStatus(status);
                list.add(ewo);
			}
        	for (DefectDocumentVo defectDocumentVo : list) {
        		defectDocumentClient.saveDefectDocument(defectDocumentVo);
			}
            return EnerbosMessage.createSuccessMsg(true, "批量修改缺陷单状态成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectDocument/updateDefectDocumentStatusList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/updateDefectDocumentStatusList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findPageDefectDocumentList: 分页模糊查询缺陷单
	 * @param defectDocumentForFilterVo 查询条件 {@link DefectDocumentForFilterVo}
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询缺陷单", response = DefectDocumentForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/findPageDefectDocumentList", method = RequestMethod.POST)
    public EnerbosMessage findPageDefectDocumentList(@ApiParam(value = "缺陷单模糊搜索查询条件VO", required = true) @RequestBody DefectDocumentForFilterVo defectDocumentForFilterVo,Principal user) {
        try {
			if (Objects.isNull(defectDocumentForFilterVo)) {
				defectDocumentForFilterVo = new DefectDocumentForFilterVo();
			}
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
			Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
			//设置查询人，用于获取关注信息
			defectDocumentForFilterVo.setPersonId(personId);
        	if (defectDocumentForFilterVo.getWords()!=null) {
        		String words=defectDocumentForFilterVo.getWords();
            	String[] word=StringUtils.split(words, " ");
            	defectDocumentForFilterVo.setWordsList(Arrays.asList(word));
			}
        	EnerbosPage<DefectDocumentForListVo> defectDocumentForListVos=defectDocumentClient.findPageDefectDocumentList(defectDocumentForFilterVo);

			defectDocumentForListVos.getList().forEach(
					vo->{
						//
						if(vo.getArchivesId()!=null){
							HeadArchivesVo archivesVo=headArchivesClient.findArchivesDetail(vo.getArchivesId());
							if(archivesVo!=null){
								vo.setMaterialNum(archivesVo.getMaterialNum());
							}

						}

					}
			);


        	return EnerbosMessage.createSuccessMsg(defectDocumentForListVos, "分页模糊查询缺陷单", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectDocument/findPageDefectDocumentList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/findPageDefectDocumentList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findPageDefectDocumentByStatusYQR: 分页模糊查询已确认的缺陷单-新建整改单选择缺陷单的查询列表
	 * @param defectDocumentForFilterVo 查询条件 {@link DefectDocumentForFilterVo}
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询已确认的缺陷单-新建整改单选择缺陷单的查询列表", response = DefectDocumentForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/findPageDefectDocumentByStatusYQR", method = RequestMethod.POST)
    public EnerbosMessage findPageDefectDocumentByStatusYQR(@ApiParam(value = "缺陷单模糊搜索查询条件VO", required = true) @RequestBody DefectDocumentForFilterVo defectDocumentForFilterVo,Principal user) {
        try {
			if (Objects.isNull(defectDocumentForFilterVo)) {
				defectDocumentForFilterVo = new DefectDocumentForFilterVo();
			}
        	if (defectDocumentForFilterVo.getWords()!=null) {
        		String words=defectDocumentForFilterVo.getWords();
            	String[] word=StringUtils.split(words, " ");
            	defectDocumentForFilterVo.setWordsList(Arrays.asList(word));
			}
        	defectDocumentForFilterVo.setStatus(Collections.singletonList(DefectCommon.DEFECT_DOCUMENT_STATUS_YQR));
        	EnerbosPage<DefectDocumentForListVo> defectDocumentForListVos=defectDocumentClient.findPageDefectDocumentList(defectDocumentForFilterVo);
			return EnerbosMessage.createSuccessMsg(defectDocumentForListVos, "分页模糊查询已确认的缺陷单-新建整改单选择缺陷单的查询列表成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectDocument/findPageDefectDocumentByStatusYQR ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/findPageDefectDocumentByStatusYQR----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findDefectDocumentById:根据ID查询缺陷单
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询缺陷单-工单提报", response = DefectDocumentVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/findDefectDocumentById", method = RequestMethod.GET)
    public EnerbosMessage findDefectDocumentById(@ApiParam(value = "缺陷单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	DefectDocumentVo defectDocumentVo=defectDocumentClient.findDefectDocumentById(id);
			if (null!=defectDocumentVo) {
        		if (null!=defectDocumentVo.getSiteId()) {
        			SiteVoForDetail site=siteClient.findById(defectDocumentVo.getSiteId());
        			if (null!=site) {
    					defectDocumentVo.setSiteName(site.getName());
    				}
				}
        		if (null!=defectDocumentVo.getRegion()) {
        			LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), defectDocumentVo.getRegion());
        			if (null!=locationVoForDetail) {
    					defectDocumentVo.setRegionName(locationVoForDetail.getName());
    				}
				}
				if (null!=defectDocumentVo.getBuildingNum()) {
					LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), defectDocumentVo.getBuildingNum());
					if (null!=locationVoForDetail) {
						defectDocumentVo.setBuildingNumName(locationVoForDetail.getName());
					}
				}
				if (null!=defectDocumentVo.getFloor()) {
					LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), defectDocumentVo.getFloor());
					if (null!=locationVoForDetail) {
						defectDocumentVo.setFloorName(locationVoForDetail.getName());
					}
				}
        		if (null!=defectDocumentVo.getArchivesId()) {
					HeadArchivesVo headArchivesVo= headArchivesClient.findArchivesDetail(defectDocumentVo.getArchivesId());
					if (null!=headArchivesVo) {
						defectDocumentVo.setArchivesNum(headArchivesVo.getMaterialNum());
						defectDocumentVo.setArchivesDesc(headArchivesVo.getMaterialName());
					}
				}
				if (null!=defectDocumentVo.getReportId()) {
					PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(defectDocumentVo.getReportId());
					if (null!=person) {
						defectDocumentVo.setReportName(person.getName());
					}
				}
				if (null!=defectDocumentVo.getConfirmPersonId()) {
					PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(defectDocumentVo.getConfirmPersonId());
					if (null!=person) {
						defectDocumentVo.setConfirmPersonName(person.getName());
					}
				}
				if (null!=defectDocumentVo.getCreateUser()) {
					PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(defectDocumentVo.getCreateUser());
					if (null!=person) {
						defectDocumentVo.setCreateUserName(person.getName());
					}
				}
				if (StringUtils.isNotBlank(defectDocumentVo.getProjectType())) {
					UserGroupDomainVo userGroupDomainVo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(defectDocumentVo.getProjectType(),
							DefectCommon.DEFECT_DOCUMENT_PROJECT_TYPE,defectDocumentVo.getOrgId(),
							defectDocumentVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
					if (userGroupDomainVo != null) {
						defectDocumentVo.setProjectTypeGroupTypeName(userGroupDomainVo.getUserGroupName());
					}
				}
                //插入执行记录
                String processInstanceId=defectDocumentVo.getProcessInstanceId();
            	if (null!=processInstanceId&&!"".equals(processInstanceId)) {
            		defectDocumentVo.setImpleRecordVoVoList(getExecution(processInstanceId));
    			}
			}
        	return EnerbosMessage.createSuccessMsg(defectDocumentVo, "根据ID查询缺陷单成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectDocument/findDefectDocumentById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/findDefectDocumentById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * checkDefectDocumentNumUnique:验证缺陷单编码的唯一性
	 * @param id
	 * @param defectDocumentNum 缺陷单编码
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
    @ApiOperation(value = "验证缺陷单编码的唯一性", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectDocument/checkDefectDocumentNumUnique", method = RequestMethod.GET)
	public EnerbosMessage checkDefectDocumentNumUnique(@ApiParam(value = "缺陷单id", required = false) @RequestParam(name="id", required = false) String id,
			@ApiParam(value = "缺陷单编码id", required = true) @RequestParam("defectDocumentNum") String defectDocumentNum,Principal user) {
	    Boolean flag = false;
	    try {
			DefectDocumentVo jpn = defectDocumentClient.findDefectDocumentByDefectDocumentNum(defectDocumentNum);
	        if (id == null) {//新建时
	        	if (jpn == null) {
	              flag = true;
	            }
	        } else {//修改时
              if (jpn != null&&jpn.getId().equals(id)||jpn == null) {
                  flag = true;
              }
	        }
	        return EnerbosMessage.createSuccessMsg(flag, "验证缺陷单编码的唯一性成功", "");
	    } catch (Exception e) {
	    	logger.error("-----/eam/open/defectDocument/checkWoNumUnique ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/checkWoNumUnique----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
	    }
	}

    /**
     * 删除选中的缺陷单
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除缺陷单", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectDocument/deleteDefectDocumentList", method = RequestMethod.GET)
    public EnerbosMessage deleteDefectDocumentList(@ApiParam(value = "删除选中的缺陷单ids", required = true) @RequestParam("ids") List<String> ids,Principal user) {
    	try {
        	for (String id : ids) {
        		DefectDocumentVo defectDocument = defectDocumentClient.findDefectDocumentById(id);
                if (null == defectDocument||StringUtils.isBlank(defectDocument.getId())) {
                	return EnerbosMessage.createErrorMsg("","要删除的缺陷单不存在", "");
                }else if(!DefectCommon.DEFECT_DOCUMENT_STATUS_DTB.equals(defectDocument.getStatus())) {
                	return EnerbosMessage.createErrorMsg("","编码为"+defectDocument.getDefectDocumentNum()+"的缺陷单状态不是待提报", "");
				}
			}
        	defectDocumentClient.deleteDefectDocumentList(ids);
            return EnerbosMessage.createSuccessMsg(defectDocumentClient.deleteDefectDocumentList(ids), "删除缺陷单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/defectDocument/deleteDefectDocumentList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/deleteDefectDocumentList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * 缺陷单-收藏
	 * @param ids 缺陷单ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "缺陷单-收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectDocument/collect", method = RequestMethod.POST)
	public EnerbosMessage collectDefectDocument(@ApiParam(value = "缺陷单ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/defectDocument/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要收藏的缺陷单！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<DefectDocumentRfCollectorVo> workOrderRfCollectorVos = Arrays.stream(ids).map(o -> new DefectDocumentRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			defectDocumentClient.collectDefectDocument(workOrderRfCollectorVos);

			return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/defectDocument/collect ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/defectDocument/deleteDefectDocument----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * 缺陷单-取消收藏
	 * @param ids 缺陷单ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "缺陷单-取消收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectDocument/collect/cancel", method = RequestMethod.POST)
	public EnerbosMessage cancelCollectDefectDocument(@ApiParam(value = "缺陷单ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/defectDocument/collect/cancel, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要取消收藏的缺陷单！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVos = Arrays.stream(ids).map(o -> new DefectDocumentRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			defectDocumentClient.cancelCollectDefectDocument(defectDocumentRfCollectorVos);
			return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/defectDocument/collect/cancel ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/defectDocument/deleteDefectDocument----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * 缺陷单-发送流程
	 * @param id
	 * @param description
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "缺陷单-发送流程", response = String.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectDocument/commit", method = RequestMethod.POST)
	public EnerbosMessage commitWorkOrderFlow(@ApiParam(value = "缺陷单id", required = true) @RequestParam("id") String id,
											  @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name="processStatus",required = true) String processStatus,
											  @ApiParam(value = "流程说明", required = true) @RequestParam("description") String description, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/defectDocument/commit, host: [{}:{}], service_id: {}, id: {},processStatus: {},description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,processStatus,description, user);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			DefectDocumentVo defectDocumentVo=defectDocumentClient.findDefectDocumentById(id);
			EnerbosMessage message;
			if (defectDocumentVo == null) {
				return EnerbosMessage.createErrorMsg("", "缺陷单不存在", "");
			}
			String siteId =defectDocumentVo.getSiteId();
			SiteVoForDetail site=siteClient.findById(siteId);
			if (site == null||null==site.getCode()) {
				return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
			}
			TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
			taskForFilterVo.setUserId(userId);
			taskForFilterVo.setSiteCode(site.getCode());
			taskForFilterVo.setPageNum(0);
			taskForFilterVo.setPageSize(10);
			taskForFilterVo.setProcessInstanceId(defectDocumentVo.getProcessInstanceId());
			List<TaskVo> page=processTaskClient.findTasks(taskForFilterVo).getList();
			if (StringUtils.isNotBlank(defectDocumentVo.getProcessInstanceId())&&(null==page||page.size()<1)){
				return EnerbosMessage.createSuccessMsg("401", "无权操作此工单", "");
			}
			if (!DefectCommon.DEFECT_DOCUMENT_STATUS_DTB.equals(defectDocumentVo.getStatus())&&!defectDocumentVo.getStatus().equals(page.get(0).getOrderStatus())) {
				return EnerbosMessage.createSuccessMsg("401", "工单状态和流程状态不一致", "");
			}
			switch (defectDocumentVo.getStatus()) {
				//待提报分支
				case DefectCommon.DEFECT_DOCUMENT_STATUS_DTB: {
					message = commitDefectDocument(defectDocumentVo,description,userId,user.getName());
				} break;
				//待确认分支
				case DefectCommon.DEFECT_DOCUMENT_STATUS_DQR: {
					message = acceptDefectDocument(defectDocumentVo,processStatus,description,userId,user.getName());
				} break;
				default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", defectDocumentVo.getStatus()));
			}
			return message;
		} catch (Exception e) {
			logger.error("-----/eam/open/defectDocument/commit ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/commit----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * commitDefectDocument:缺陷单，流程启动、提报
	 * @param defectDocumentVo
	 * @param description  缺陷单提报说明
	 * @param userId
	 * @param loginName
	 * @return EnerbosMessage返回执行码及数据
	 */
	public EnerbosMessage commitDefectDocument(DefectDocumentVo defectDocumentVo,String description,String userId,String loginName) {
		try {
			if (!userId.equals(defectDocumentVo.getCreateUser())) {
				return EnerbosMessage.createErrorMsg("", "不是提报人，不能提报！", "");
			}
			if (defectDocumentVo.getProcessInstanceId() == null||"".equals(defectDocumentVo.getProcessInstanceId())) {
				//启动流程
				//设置流程变量
				Map<String, Object> variables = new HashMap<String, Object>();
				//业务主键
				String businessKey = defectDocumentVo.getId();
				//流程key,key为维保固定前缀+站点code
				String code="";
				SiteVoForDetail site=siteClient.findById(defectDocumentVo.getSiteId());
				if (site != null) {
					code=site.getCode();
				}
				String processKey = DefectCommon.DEFECT_DOCUMENT_WFS_PROCESS_KEY + code;
				ProcessVo processVo=new ProcessVo();
				processVo.setBusinessKey(businessKey);
				processVo.setProcessKey(processKey);
				processVo.setUserId(userId);
				variables.put(DefectCommon.DEFECT_DOCUMENT_SUBMIT_USER, userId);
				variables.put(Common.ORDER_NUM,defectDocumentVo.getDefectDocumentNum());
				variables.put(Common.ORDER_DESCRIPTION,defectDocumentVo.getDescription());
				variables.put("userId", userId);
				logger.debug("/eam/open/defectDocument/commit, processKey: {}", processKey);
				processVo=workflowClient.startProcess(variables, processVo);

				if (null==processVo || "".equals(processVo.getProcessInstanceId())) {
					return EnerbosMessage.createErrorMsg("500", "流程启动失败", "");
				}
				//提报，修改基本字段保存
				defectDocumentVo.setProcessInstanceId(processVo.getProcessInstanceId());
				defectDocumentVo.setReportDate(new Date());
				defectDocumentVo = defectDocumentClient.saveDefectDocument(defectDocumentVo);
			}
			//查询分派组签收人员
			Map<String, Object> variables = new HashMap<String, Object>();
			List<String> userList = new ArrayList<>();
			UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(defectDocumentVo.getProjectType(),
					DefectCommon.DEFECT_DOCUMENT_PROJECT_TYPE,defectDocumentVo.getOrgId(),
					defectDocumentVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
			if (vo != null) {
				UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
				if (voForDetail != null) {
					voForDetail.getUsers().stream().map(person->personAndUserClient.findByPersonId(person.getPersonId())).filter(Objects::nonNull).forEach(person->userList.add(person.getPersonId()));
				}
			}
			if(null==userList||userList.size() <= 0){
				return EnerbosMessage.createSuccessMsg("", "缺陷单确认组下没有人员,请联系管理员添加!!", "");
			}
			variables.put(DefectCommon.DEFECT_DOCUMENT_CONFIRM_USER, StringUtils.join(userList, ","));
			variables.put("description", description);
			variables.put("status", DefectCommon.DEFECT_DOCUMENT_STATUS_DQR);
			variables.put("userId", userId);
			variables.put(DefectCommon.DEFECT_DOCUMENT_REPORT_PASS, true);
			Boolean processMessage = processTaskClient.completeByProcessInstanceId(defectDocumentVo.getProcessInstanceId(), variables);
			if (Objects.isNull(processMessage) || !processMessage) {
				throw new EnerbosException("500", "流程操作异常。");
			}
			//提报，修改基本字段保存
			defectDocumentVo.setStatus(DefectCommon.DEFECT_DOCUMENT_STATUS_DQR);
			defectDocumentVo=defectDocumentClient.saveDefectDocument(defectDocumentVo);
			return EnerbosMessage.createSuccessMsg(defectDocumentVo.getStatus(), "提报缺陷单成功", "");
		} catch (Exception e) {
			//流程提交失败时，将工单状态恢复成待提报
			if (StringUtils.isNotEmpty(defectDocumentVo.getId()) &&
					!DefectCommon.DEFECT_DOCUMENT_STATUS_DTB.equals(defectDocumentVo.getStatus())) {
				defectDocumentVo.setStatus(DefectCommon.DEFECT_DOCUMENT_STATUS_DTB);
				defectDocumentClient.saveDefectDocument(defectDocumentVo);
			}
			logger.error("-----/eam/open/defectDocument/commit ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/commit----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * acceptDefectDocument:缺陷单-确认/驳回
	 * @param defectDocumentVo
	 * @param processStatus
	 * @param description 确认工单说明
	 * @param userId
	 * @param loginName
	 * @return EnerbosMessage返回执行码及数据
	 */
	public EnerbosMessage acceptDefectDocument(DefectDocumentVo defectDocumentVo,String processStatus,String description,String userId,String loginName) {
		try {
			//更新流程进度
			Map<String, Object> variables = new HashMap<String, Object>();
			String message="";
			switch (processStatus){
				case Common.WORK_ORDER_PROCESS_STATUS_AGREE: {
					//同意确认，结束流程
					variables.put(DefectCommon.DEFECT_DOCUMENT_ACCEPT_PASS, true);
					variables.put(DefectCommon.DEFECT_DOCUMENT_REJECT_ACCEPT_PASS, false);
					variables.put("status", DefectCommon.DEFECT_DOCUMENT_STATUS_YQR);
					defectDocumentVo.setStatus(DefectCommon.DEFECT_DOCUMENT_STATUS_YQR);//工单状态更新到待接单
					message="缺陷单-确认成功";
				} break;
				case Common.WORK_ORDER_PROCESS_STATUS_REJECT: {
					variables.put(DefectCommon.DEFECT_DOCUMENT_REJECT_ACCEPT_PASS, true);
					variables.put(DefectCommon.DEFECT_DOCUMENT_ACCEPT_PASS, false);
					variables.put("status", DefectCommon.DEFECT_DOCUMENT_STATUS_DTB);
					defectDocumentVo.setStatus(DefectCommon.DEFECT_DOCUMENT_STATUS_DTB);//工单状态更新
					message="驳回缺陷单，重新提报成功";
				} break;
				default: throw new EnerbosException("500", String.format("未支持流程处理意见。当前流程状态：[%s][%s][%s]", defectDocumentVo.getId(),processStatus,description));
			}
			variables.put("userId", userId);
			variables.put(DefectCommon.DEFECT_DOCUMENT_CONFIRM_USER, userId);
			variables.put("description", description);
			processTaskClient.completeByProcessInstanceId(defectDocumentVo.getProcessInstanceId(), variables);
			//保存工单
			defectDocumentVo=defectDocumentClient.saveDefectDocument(defectDocumentVo);
			return EnerbosMessage.createSuccessMsg(defectDocumentVo.getStatus(), message, "");
		} catch (Exception e) {
			//流程提交失败时，将工单状态恢复成待分派
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(defectDocumentVo.getId()) &&
					!DefectCommon.DEFECT_DOCUMENT_STATUS_DQR.equals(defectDocumentVo.getStatus())) {
				defectDocumentVo.setStatus(DefectCommon.DEFECT_DOCUMENT_STATUS_DQR);
				defectDocumentClient.saveDefectDocument(defectDocumentVo);
			}
			logger.error("-----/eam/open/defectDocument/acceptDefectDocument ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectDocument/acceptDefectDocument----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
    
    /**
     * getExecution:根据流程实例ID查询执行记录
     * @param processInstanceId 流程实例ID
     * @return List<WorkFlowImpleRecordVo> 执行记录VO集合
     */
    private List<WorkFlowImpleRecordVo> getExecution(String processInstanceId) {
    	List<WorkFlowImpleRecordVo> workFlowImpleRecordVoList=new ArrayList<>();
    	List<HistoricTaskVo> historicTaskVoList=processActivitiClient.findProcessTrajectory(processInstanceId);
		WorkFlowImpleRecordVo workFlowImpleRecordVo=null;
    	if (null!=historicTaskVoList&&historicTaskVoList.size()>0) {
    		for (HistoricTaskVo historicTaskVo : historicTaskVoList) {
        		workFlowImpleRecordVo=new WorkFlowImpleRecordVo();
        		workFlowImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
        		workFlowImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
        		workFlowImpleRecordVo.setName(historicTaskVo.getName());
        		if (StringUtils.isNotBlank(historicTaskVo.getAssignee())) {
					String[] ids=historicTaskVo.getAssignee().split(",");
					String personName="";
					if (null!=ids&&!"".equals(ids)){
						for (String id:ids){
							PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(id);
							if (person != null) {
								personName+= personAndUserClient.findByPersonId(id).getName()+",";
							}
						}
						personName=personName.substring(0,personName.length()-1);
					}
        			workFlowImpleRecordVo.setPersonName(personName);
        		}
        		workFlowImpleRecordVo.setDurationInMillis(historicTaskVo.getDurationInMillis());
        		workFlowImpleRecordVo.setDescription(historicTaskVo.getDescription());
				workFlowImpleRecordVoList.add(workFlowImpleRecordVo);
        	}
		}
    	return workFlowImpleRecordVoList;
    }
}