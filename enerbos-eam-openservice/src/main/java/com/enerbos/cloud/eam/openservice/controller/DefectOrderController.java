package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DefectCommon;
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
import com.enerbos.cloud.util.ReflectionUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
 * @date 2017年09月06日
 * @Description 消缺工单接口
 */
@RestController
@Api(description = "整改工单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DefectOrderController {

    private Logger logger = LoggerFactory.getLogger(DefectOrderController.class);

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
	private SiteClient siteClient;
    
    @Autowired
	private LocationClient locationClient;
    
    @Autowired
	private PersonAndUserClient personAndUserClient;
    
    @Autowired
	private OrgClient orgClient;
    
    @Resource
	private DefectOrderClient defectOrderClient;

    @Resource
	private DefectDocumentClient defectDocumentClient;
    
    @Autowired
	private ProcessActivitiClient processActivitiClient;

	@Autowired
	private UgroupClient ugroupClient;

	@Autowired
	private OrderPersonClient orderPersonClient;

	@Autowired
	private UserGroupDomainClient userGroupDomainClient;

    /**
     * saveDefectOrderCommit:保存消缺工单-工单提报
     * @param defectOrderForCommitVo {@link DefectOrderForCommitVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存消缺工单-工单提报", response = DefectOrderForCommitVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/saveDefectOrderCommit", method = RequestMethod.POST)
    public EnerbosMessage saveDefectOrderCommit(@ApiParam(value = "整改工单-工单提报VO", required = true) @Valid @RequestBody DefectOrderForCommitVo defectOrderForCommitVo, Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectOrder/saveDefectOrderCommit, host: [{}:{}], service_id: {}, DefectOrderForCommitVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectOrderForCommitVo);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			DefectOrderVo defectOrderVo=new DefectOrderVo();
			DefectOrderVo ewo = defectOrderClient.findDefectOrderByDefectOrderNum(defectOrderForCommitVo.getDefectOrderNum());
			if (StringUtils.isBlank(defectOrderForCommitVo.getId())) {
                if (ewo != null && !"".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg(null, "整改工单编码重复", null);
                }
            }else {
            	if (ewo != null && !defectOrderForCommitVo.getId().equals(ewo.getId())) {
                    return EnerbosMessage.createErrorMsg(null, "整改工单编码重复", null);
                }
            }
        	if (!DefectCommon.DEFECT_ORDER_STATUS_DTB.equals(defectOrderForCommitVo.getStatus())) {
            	return EnerbosMessage.createErrorMsg(null, "消缺工单状态不是待提报，不允许保存！", null);
            }
			if (StringUtils.isBlank(defectOrderForCommitVo.getCreateUser())) {
				defectOrderForCommitVo.setCreateUser(userId);
			} else if (!userId.equals(defectOrderForCommitVo.getCreateUser())) {
				return EnerbosMessage.createErrorMsg(null, "消缺工单创建人只能是自己",null);
			}
			if (StringUtils.isBlank(defectOrderForCommitVo.getReportId()) ) {
				defectOrderForCommitVo.setReportId(userId);
			} else if (!userId.equals(defectOrderForCommitVo.getReportId())) {
				return EnerbosMessage.createErrorMsg("", "消缺工单提报人只能是自己", null);
			}
			defectOrderForCommitVo.setReportDate(new Date());//提报日期
			if (StringUtils.isBlank(defectOrderForCommitVo.getId())) {
				defectOrderForCommitVo.setCreateUser(userId);
			}else {
				defectOrderVo=defectOrderClient.findDefectOrderById(defectOrderForCommitVo.getId());
			}
			if (defectOrderForCommitVo.getCreateDate() == null) {
				defectOrderForCommitVo.setCreateDate(new Date());
			}
			ReflectionUtils.copyProperties(defectOrderForCommitVo,defectOrderVo,null);
			//查询对应的缺陷单关联的整改单，以及各个进度




			defectOrderVo.setStatusDate(new Date());//状态日期
			DefectOrderVo defectOrder=defectOrderClient.saveDefectOrder(defectOrderVo);

			//更新进度
			Map map=this.findDefectDocumentCountByDefectOrderId(defectOrderForCommitVo.getDefectDocumentId());
			String completeness=map.get("completeness").toString();
			DefectDocumentVo documentVo=defectDocumentClient.findDefectDocumentById(defectOrderForCommitVo.getDefectDocumentId());
			documentVo.setCompleteness(completeness);
			defectDocumentClient.saveDefectDocument(documentVo);

			return EnerbosMessage.createSuccessMsg(defectOrder, "保存消缺工单-工单提报保存成功", null);
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/saveDefectOrderCommit ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/saveDefectOrderCommit----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }


   public Map findDefectDocumentCountByDefectOrderId(String defectOrderId){
    	Map map=new HashMap();
	   DefectOrderForFilterVo defectOrderForFilterVo=new DefectOrderForFilterVo();
	   defectOrderForFilterVo.setDefectDocumentId(defectOrderId);
	   EnerbosPage<DefectOrderForListVo> page=defectOrderClient.findPageDefectOrderList(defectOrderForFilterVo);//总数
	   map.put("total",page.getTotal());
	   List status=new ArrayList();
	   status.add( DefectCommon.DEFECT_ORDER_STATUS_GQ);
	   status.add( DefectCommon.DEFECT_ORDER_STATUS_GB);
	   defectOrderForFilterVo.setStatus(status);
	   EnerbosPage<DefectOrderForListVo> page1=defectOrderClient.findPageDefectOrderList(defectOrderForFilterVo);//总数
	   map.put("complete",page1.getTotal());
	   map.put("completeness",page1.getTotal()+"/"+page.getTotal());
	   return  map;
   }
    
    /**
     * saveDefectOrderAssign:保存消缺工单-任务分派
     * @param defectOrderForAssignVo {@link DefectOrderForAssignVo}
     * @param user
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存消缺工单-任务分派", response = DefectOrderForAssignVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/saveDefectOrderAssign", method = RequestMethod.POST)
    public EnerbosMessage saveDefectOrderAssign(@ApiParam(value = "整改工单-任务分派VO", required = true) @Valid  @RequestBody DefectOrderForAssignVo defectOrderForAssignVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectOrder/saveDefectOrderAssign, host: [{}:{}], service_id: {}, DefectOrderForAssignVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectOrderForAssignVo);

			String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
        	if (!DefectCommon.DEFECT_ORDER_STATUS_DFP.equals(defectOrderForAssignVo.getStatus())) {
        		return EnerbosMessage.createErrorMsg("", "消缺工单状态不是待分派，不允许修改！", "");
            }
			if (defectOrderForAssignVo.getEntrustExecute()&&null == defectOrderForAssignVo.getEntrustExecutePersonId()) {
				return EnerbosMessage.createErrorMsg("405", "消缺工单委托执行情况下委托执行人不能为空", "");
			}
            defectOrderForAssignVo.setUpdateDate(new Date());//更新最后修改时间
			String[] executorPerson=defectOrderForAssignVo.getExecutorPersonId().split(",");
			String workGroup=null;
			for (String person:executorPerson){
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(person);
				if (personVo == null) {
					return EnerbosMessage.createErrorMsg("405", "消缺工单执行人中有不存在的人员", "");
				}
				if (StringUtils.isBlank(workGroup)){
					workGroup=personVo.getWorkgroup();
				}else if (!workGroup.equals(workGroup=personVo.getWorkgroup())){
					workGroup=null;
					break;
				}
			}

			//更新执行人和实际执行人数据
			if (StringUtils.isNotEmpty(defectOrderForAssignVo.getExecutorPersonId())) {
				String[] executionPersonIdArray = defectOrderForAssignVo.getExecutorPersonId().split(",");
				List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
				List<OrderPersonVo> actualExecutorVoList = new ArrayList<>();
				for (String id : executionPersonIdArray) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
					Assert.notNull(personVo, "未知执行人！");
					orderPersonVoList.add(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION, id));
					actualExecutorVoList.add(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ACTUAL_EXECUTION, id));
				}
				orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
				actualExecutorVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(actualExecutorVoList);
			} else {
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION);
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ACTUAL_EXECUTION);
			}

			//更新委托执行人数据
			if (StringUtils.isNotEmpty(defectOrderForAssignVo.getEntrustExecutePersonId())) {
				String[] entrustExecutePersonIdArray = defectOrderForAssignVo.getEntrustExecutePersonId().split(",");
				List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
				for (String id : entrustExecutePersonIdArray) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
					Assert.notNull(personVo, "未知执行人！");
					orderPersonVoList.add(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ENTRUST_EXECUTION, id));
				}
				orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
			} else {
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ENTRUST_EXECUTION);
			}
			DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(defectOrderForAssignVo.getId());
			if (defectOrderVo == null||StringUtils.isBlank(defectOrderVo.getId())) {
				return EnerbosMessage.createErrorMsg(null, "消缺工单不存在，先新建", "");
			}
			BeanUtils.copyProperties(defectOrderForAssignVo, defectOrderVo, "processInstanceId", "reportId","projectType");
		//	ReflectionUtils.copyProperties(defectOrderForAssignVo,defectOrderVo,"processInstanceId","reportId");
			defectOrderVo.setExecutionWorkGroup(workGroup);
			defectOrderVo.setActualWorkGroup(workGroup);
			DefectOrderVo defectOrder=defectOrderClient.saveDefectOrder(defectOrderVo);
			return EnerbosMessage.createSuccessMsg(defectOrder, "保存消缺工单-任务分派保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/saveDefectOrderAssign ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/saveDefectOrderAssign----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * saveDefectOrderReport:保存消缺工单-执行汇报
     * @param defectOrderForReportVo {@link DefectOrderForReportVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存消缺工单-执行汇报", response = DefectOrderForReportVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/saveDefectOrderReport", method = RequestMethod.POST)
    public EnerbosMessage saveDefectOrderReport(@ApiParam(value = "整改工单-执行汇报VO", required = true) @RequestBody DefectOrderForReportVo defectOrderForReportVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectOrder/saveDefectOrderReport, host: [{}:{}], service_id: {}, DefectOrderForReportVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectOrderForReportVo);
            
        	String id = defectOrderForReportVo.getId();
        	if (null == id) {
                return EnerbosMessage.createErrorMsg("", "消缺工单不存在，请先新建！", "");
            }
        	if (null == defectOrderForReportVo.getStatus()||!DefectCommon.DEFECT_ORDER_STATUS_DHB.equals(defectOrderForReportVo.getStatus())) {
        		return EnerbosMessage.createErrorMsg("", "消缺工单状态不是待汇报，不允许修改！", "");
            }
//			if (null==defectOrderForReportVo.getActualStartDate()) {
//				return EnerbosMessage.createErrorMsg("405", "消缺工单实际开始时间不能为空", "");
//			}
			if (null==defectOrderForReportVo.getActualEndDate()) {
				return EnerbosMessage.createErrorMsg("405", "消缺工单工程实际结束时间不能为空", "");
			}
			if (null==defectOrderForReportVo.getConsumeHours()) {
				return EnerbosMessage.createErrorMsg("405", "消缺工单工程工时不能为空", "");
			}
			String[] executorPerson=defectOrderForReportVo.getActualExecutorId().split(",");
			String workGroup=null;
			for (String person:executorPerson){
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(person);
				if (personVo == null) {
					return EnerbosMessage.createErrorMsg("405", "消缺工单实际执行人中有不存在的人员", "");
				}
				if (StringUtils.isBlank(workGroup)){
					workGroup=personVo.getWorkgroup();
				}else if (!workGroup.equals(workGroup=personVo.getWorkgroup())){
					workGroup=null;
					break;
				}
			}
			//更新实际执行人数据
			if (StringUtils.isNotEmpty(defectOrderForReportVo.getActualExecutorId())) {
				String[] actualExecutorIdArray = defectOrderForReportVo.getActualExecutorId().split(",");
				List<OrderPersonVo> actualExecutorVoList = new ArrayList<>();
				for (String actualId : actualExecutorIdArray) {
					PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(actualId);
					Assert.notNull(personVo, "未知执行人！");
					actualExecutorVoList.add(new OrderPersonVo(defectOrderForReportVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ACTUAL_EXECUTION, actualId));
				}
				actualExecutorVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(actualExecutorVoList);
			} else {
				orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(defectOrderForReportVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ACTUAL_EXECUTION);
			}
			DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(defectOrderForReportVo.getId());
			if (defectOrderVo == null||StringUtils.isBlank(defectOrderVo.getId())) {
				return EnerbosMessage.createErrorMsg(null, "消缺工单不存在，先新建", "");
			}
			//ReflectionUtils.copyProperties(defectOrderForReportVo,defectOrderVo,null);
			BeanUtils.copyProperties(defectOrderForReportVo, defectOrderVo, "processInstanceId", "reportId","projectType");
			defectOrderVo.setActualWorkGroup(workGroup);
			defectOrderVo.setUpdateDate(new Date());//更新最后修改时间
			DefectOrderVo defectOrder=defectOrderClient.saveDefectOrder(defectOrderVo);
            return EnerbosMessage.createSuccessMsg(defectOrder, "保存消缺工单-执行汇报保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/saveDefectOrderReport ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/saveDefectOrderReport----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * saveDefectOrderAccept:保存消缺工单-验收确认
     * @param defectOrderForAcceptVo {@link DefectOrderForAcceptVo}
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "保存消缺工单-验收确认", response = DefectOrderForAcceptVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/saveDefectOrderAccept", method = RequestMethod.POST)
    public EnerbosMessage saveDefectOrderAccept(@ApiParam(value = "整改工单-验收确认VO", required = true) @RequestBody DefectOrderForAcceptVo defectOrderForAcceptVo,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectOrder/saveDefectOrderAccept, host: [{}:{}], service_id: {}, DefectOrderForAcceptVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectOrderForAcceptVo);
            
        	if (null == defectOrderForAcceptVo.getId()) {
                return EnerbosMessage.createErrorMsg("", "消缺工单不存在，请先新建！", "");
            }
        	if (!DefectCommon.DEFECT_ORDER_STATUS_DYS.equals(defectOrderForAcceptVo.getStatus())) {
        		return EnerbosMessage.createErrorMsg("", "消缺工单状态不是待验收，不允许修改！", "");
            }
        	if (defectOrderForAcceptVo.getSiteId() != null) {
            	SiteVoForDetail site=siteClient.findById(defectOrderForAcceptVo.getSiteId());
            	defectOrderForAcceptVo.setSiteId(site.getId());
            }
            if (defectOrderForAcceptVo.getOrgId() != null) {
            	OrgVoForDetail org=orgClient.findById(defectOrderForAcceptVo.getOrgId());
            	defectOrderForAcceptVo.setOrgId(org.getId());
            }
			if (null==defectOrderForAcceptVo.getConfirm()) {
				return EnerbosMessage.createErrorMsg("405", "消缺工单是否已解决不能为空", "");
			}
			if (null==defectOrderForAcceptVo.getAcceptorId()) {
				return EnerbosMessage.createErrorMsg("405", "消缺工单验收人不能为空", "");
			}
			if (null==defectOrderForAcceptVo.getAcceptionDate()) {
				return EnerbosMessage.createErrorMsg("405", "消缺工单确认验收时间不能为空", "");
			}
			DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(defectOrderForAcceptVo.getId());
			if (defectOrderVo == null||StringUtils.isBlank(defectOrderVo.getId())) {
				return EnerbosMessage.createErrorMsg(null, "消缺工单不存在，先新建", "");
			}
		//	ReflectionUtils.copyProperties(defectOrderForAcceptVo,defectOrderVo,null);
			BeanUtils.copyProperties(defectOrderForAcceptVo, defectOrderVo, "processInstanceId", "reportId","projectType");
			defectOrderVo.setUpdateDate(new Date());//更新最后修改时间
			DefectOrderVo defectOrder=defectOrderClient.saveDefectOrder(defectOrderVo);
			return EnerbosMessage.createSuccessMsg(defectOrder, "保存消缺工单-验收确认保存成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/saveDefectOrderAccept ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/saveDefectOrderAccept----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * updateDefectOrderStatus:修改消缺工单状态
     * @param id 消缺工单ID
     * @param status 状态
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "修改消缺工单状态", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/updateDefectOrderStatus", method = RequestMethod.POST)
    public EnerbosMessage updateDefectOrderStatus(@ApiParam(value = "消缺工单id", required = true) @RequestParam("id") String id,
    		@ApiParam(value = "status", required = true) @RequestParam("status") String status,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectOrder/updateDefectOrderStatus, host: [{}:{}], service_id: {}, id: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id,status);
            
    		DefectOrderVo ewo = defectOrderClient.findDefectOrderById(id);
            if (ewo == null || "".equals(ewo)) {
                return EnerbosMessage.createErrorMsg("", "消缺工单不存在", "");
            }
            ewo.setStatusDate(new Date());//状态日期
            ewo.setStatus(status);
            defectOrderClient.saveDefectOrder(ewo);
            return EnerbosMessage.createSuccessMsg(true, "修改消缺工单状态成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/updateDefectOrderStatus ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/updateDefectOrderStatus----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
     * updateDefectOrderStatusList:批量修改消缺工单状态
     * @param ids    消缺工单ID数组{@link List<String>}
	 * @param status 消缺工单状态
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改消缺工单状态", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/updateDefectOrderStatusList", method = RequestMethod.POST)
    public EnerbosMessage updateDefectOrderStatusList(@ApiParam(value = "消缺工单id", required = true) @RequestParam("ids") List<String> ids,
    		@ApiParam(value = "status", required = true) @RequestParam("status") String status,Principal user) {
        try {
        	ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/DefectOrder/updateDefectOrderStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids,status);
            
        	List<DefectOrderVo> list=new ArrayList<>();
        	for (String id : ids) {
        		DefectOrderVo ewo = defectOrderClient.findDefectOrderById(id);
                if (ewo== null || "".equals(ewo)) {
                    return EnerbosMessage.createErrorMsg("", "消缺工单不存在", "");
                }
                ewo.setStatusDate(new Date());//状态日期
                ewo.setUpdateDate(new Date());
                ewo.setStatus(status);
                list.add(ewo);
			}
        	for (DefectOrderVo defectOrderForCommitVo : list) {
        		defectOrderClient.saveDefectOrder(defectOrderForCommitVo);
			}
            return EnerbosMessage.createSuccessMsg(true, "批量修改消缺工单状态成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/updateDefectOrderStatusList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/updateDefectOrderStatusList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findPageDefectOrderList: 分页模糊查询消缺工单
	 * @param defectOrderForFilterVo 查询条件 {@link DefectOrderForFilterVo}
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页模糊查询消缺工单", response = DefectOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/findPageDefectOrderList", method = RequestMethod.POST)
    public EnerbosMessage findPageDefectOrderList(@ApiParam(value = "消缺工单模糊搜索查询条件VO", required = true) @RequestBody DefectOrderForFilterVo defectOrderForFilterVo,Principal user) {
        try {
			if (Objects.isNull(defectOrderForFilterVo)) {
				defectOrderForFilterVo = new DefectOrderForFilterVo();
			}
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
			PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
			Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
			//设置查询人，用于获取关注信息
			defectOrderForFilterVo.setPersonId(personId);
        	if (defectOrderForFilterVo.getWords()!=null) {
        		String words=defectOrderForFilterVo.getWords();
            	String[] word=StringUtils.split(words, " ");
            	defectOrderForFilterVo.setWordsList(Arrays.asList(word));
			}
        	EnerbosPage<DefectOrderForListVo> defectOrderForListVos=defectOrderClient.findPageDefectOrderList(defectOrderForFilterVo);
			if (defectOrderForListVos != null&&null!=defectOrderForListVos.getList()&&defectOrderForListVos.getList().size()>0) {
				for(DefectOrderForListVo defectOrderForListVo:defectOrderForListVos.getList()){
					if (defectOrderForListVo != null&&defectOrderForListVo.getDefectDocumentId()!=null) {
						DefectDocumentVo defectDocumentVo=defectDocumentClient.findDefectDocumentById(defectOrderForListVo.getDefectDocumentId());
						if (defectDocumentVo != null) {
							defectOrderForListVo.setDefectDocumentNum(defectDocumentVo.getDefectDocumentNum());
						}
					}
					if (defectOrderForListVo != null&&defectOrderForListVo.getReportId()!=null) {
						PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(defectOrderForListVo.getReportId());
						if (person != null) {
							defectOrderForListVo.setReportName(person.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(defectOrderForListVos, "分页模糊查询消缺工单", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/findPageDefectOrderList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/findPageDefectOrderList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * findPageDefectOrderByDefectDocumentId:根据缺陷单ID分页查询消缺工单
	 * @param defectOrderForFilterVo 查询条件 {@link DefectOrderForFilterVo}
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据缺陷单ID分页查询消缺工单", response = DefectOrderForListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectOrder/findPageDefectOrderByDefectDocumentId", method = RequestMethod.POST)
	public EnerbosMessage findPageDefectOrderByDefectDocumentId(@ApiParam(value = "根据缺陷单ID分页查询消缺工单VO", required = true) @RequestBody DefectOrderForFilterVo defectOrderForFilterVo,Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/defectOrder/findPageDefectOrderByDefectDocumentId, host: [{}:{}], service_id: {}, DefectOrderForFilterVo: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), defectOrderForFilterVo);
			EnerbosPage<DefectOrderForListVo> defectOrderForListVos=defectOrderClient.findPageDefectOrderList(defectOrderForFilterVo);
			if (defectOrderForListVos != null&&null!=defectOrderForListVos.getList()&&defectOrderForListVos.getList().size()>0) {
				for(DefectOrderForListVo defectOrderForListVo:defectOrderForListVos.getList()){
					if (defectOrderForListVo != null&&defectOrderForListVo.getDefectDocumentId()!=null) {
						DefectDocumentVo defectDocumentVo=defectDocumentClient.findDefectDocumentById(defectOrderForListVo.getDefectDocumentId());
						if (defectDocumentVo != null) {
							defectOrderForListVo.setDefectDocumentNum(defectDocumentVo.getDefectDocumentNum());
						}
					}
					if (defectOrderForListVo != null&&defectOrderForListVo.getReportId()!=null) {
						PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(defectOrderForListVo.getReportId());
						if (person != null) {
							defectOrderForListVo.setReportName(person.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(defectOrderForListVos, "根据设备ID分页查询消缺工单成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/defectOrder/findPageDefectOrderByDefectDocumentId ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/findPageDefectOrderByDefectDocumentId----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
    
    /**
	 * findDefectOrderCommitById:根据ID查询消缺工单-工单提报
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询消缺工单-工单提报", response = DefectOrderForCommitVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/findDefectOrderCommitById", method = RequestMethod.GET)
    public EnerbosMessage findDefectOrderCommitById(@ApiParam(value = "消缺工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
			DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(id);
			DefectOrderForCommitVo defectOrderForCommitVo=new DefectOrderForCommitVo();
			ReflectionUtils.copyProperties(defectOrderVo,defectOrderForCommitVo,null);
			if (null!=defectOrderForCommitVo) {
        		if (null!=defectOrderForCommitVo.getSiteId()) {
        			SiteVoForDetail site=siteClient.findById(defectOrderForCommitVo.getSiteId());
        			if (null!=site) {
    					defectOrderForCommitVo.setSiteName(site.getName());
    				}
				}
        		if (null!=defectOrderForCommitVo.getReportId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForCommitVo.getReportId());
					if (null!=personVo) {
						defectOrderForCommitVo.setReportName(personVo.getName());
						defectOrderForCommitVo.setReportMobile(personVo.getMobile());
					}
				}
				if (null!=defectOrderForCommitVo.getDefectDocumentId()) {
					DefectDocumentVo defectDocument=defectDocumentClient.findDefectDocumentById(defectOrderForCommitVo.getDefectDocumentId());
					if (null!=defectDocument) {
						defectOrderForCommitVo.setDefectDocumentNum(defectDocument.getDefectDocumentNum());
						defectOrderForCommitVo.setDefectDocumentDesc(defectDocument.getDescription());
						defectOrderForCommitVo.setDefectDocumentPosition(defectDocument.getPosition());
						if (StringUtils.isNotBlank(defectDocument.getRegion())) {
							LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), defectDocument.getRegion());
							if (null!=locationVoForDetail) {
								defectOrderForCommitVo.setDefectDocumentRegionName(locationVoForDetail.getName());
							}
						}
						if(StringUtils.isNotBlank(defectDocument.getArchivesNum())){
							defectOrderForCommitVo.setArchivesNum(defectDocument.getArchivesNum());
						}
						if (StringUtils.isNotBlank(defectDocument.getBuildingNum())) {
							LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), defectDocument.getBuildingNum());
							if (null!=locationVoForDetail) {
								defectOrderForCommitVo.setDefectDocumentBuildingNumName(locationVoForDetail.getName());
							}
						}
						if (StringUtils.isNotBlank(defectDocument.getFloor())) {
							LocationVoForDetail locationVoForDetail=locationClient.findById(user.getName(), defectDocument.getFloor());
							if (null!=locationVoForDetail) {
								defectOrderForCommitVo.setDefectDocumentFloorName(locationVoForDetail.getName());
							}
						}
					}
				}
				if (StringUtils.isNotBlank(defectOrderForCommitVo.getProjectType())) {
					UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(defectOrderForCommitVo.getProjectType(),
							DefectCommon.DEFECT_DOCUMENT_PROJECT_TYPE,defectOrderForCommitVo.getOrgId(),
							defectOrderForCommitVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
					if (vo != null) {
						UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
						if (voForDetail != null) {
							defectOrderForCommitVo.setProjectTypeGroupTypeName(voForDetail.getName());
						}
					}
				}
                //插入执行记录
                String processInstanceId=defectOrderForCommitVo.getProcessInstanceId();
            	if (null!=processInstanceId&&!"".equals(processInstanceId)) {
					defectOrderForCommitVo.setImpleRecordVoVoList(getExecution(processInstanceId));
    			}
			}
        	return EnerbosMessage.createSuccessMsg(defectOrderForCommitVo, "根据ID查询消缺工单-工单提报成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/findDefectOrderCommitById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/findDefectOrderCommitById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findDefectOrderAssignById:根据ID查询消缺工单-任务分派
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询消缺工单-任务分派", response = DefectOrderForAssignVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/findDefectOrderAssignById", method = RequestMethod.GET)
    public EnerbosMessage findDefectOrderAssignById(@ApiParam(value = "消缺工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(id);
        	DefectOrderForAssignVo defectOrderForAssignVo=new DefectOrderForAssignVo();
        	ReflectionUtils.copyProperties(defectOrderVo,defectOrderForAssignVo,null);
        	if (null!=defectOrderForAssignVo) {
        		//查询委托执行人
				List<OrderPersonVo> entrustExecutePersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ENTRUST_EXECUTION));
				if (!entrustExecutePersonList.isEmpty()) {
					List<PersonAndUserVoForDetail> tmp = entrustExecutePersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
					defectOrderForAssignVo.setEntrustExecutePersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
					defectOrderForAssignVo.setEntrustExecutePersonName(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
				}
				//查询执行人
				List<OrderPersonVo> executorPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION));
				if (!executorPersonList.isEmpty()) {
					List<PersonAndUserVoForDetail> tmp = executorPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
					defectOrderForAssignVo.setExecutorPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
					defectOrderForAssignVo.setExecutorPersonName(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
				}
        		if (null!=defectOrderForAssignVo.getAssignPersonId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAssignVo.getAssignPersonId());
        			if (null!=personVo) {
    					defectOrderForAssignVo.setAssignPersonName(personVo.getName());
    				}
				}
				if (null!=defectOrderForAssignVo.getReportId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAssignVo.getReportId());
					if (null!=personVo) {
						defectOrderForAssignVo.setReportName(personVo.getName());
					}
				}
//				if (StringUtils.isNotBlank(defectOrderForAssignVo.getExecutionDefectGroup())) {
//					Map<String, Object> filters=new HashMap<>();
//					filters.put("name", defectOrderForAssignVo.getExecutionDefectGroup());
//					filters.put("siteId", defectOrderForAssignVo.getSiteId());
//					filters.put("orgId", defectOrderForAssignVo.getOrgId());
//
//					List<UgroupVo> uGroupVoList=ugroupClient.findUgroups(user.getName(), filters, 0, 10, Common.EAM_PROD_IDS).getList();
//					if (!CollectionUtils.isEmpty(uGroupVoList)) {
//						defectOrderForAssignVo.setExecutionDefectGroupName(uGroupVoList.get(0).getDescription());
//					}
//				}
            	String processInstanceId=defectOrderForAssignVo.getProcessInstanceId();
            	if (null!=processInstanceId&&!"".equals(processInstanceId)) {
            		defectOrderForAssignVo.setImpleRecordVoVoList(getExecution(processInstanceId));
				}

				//设置待接单人
				List<OrderPersonVo> availableTakingPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION));
				if (defectOrderForAssignVo.getEntrustExecute()) {
					//委托执行人
					availableTakingPersonList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderForAssignVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ENTRUST_EXECUTION)));
				}
				List<PersonAndUserVoForDetail> tmp = availableTakingPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
				String availableTakingPersonName=StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ",");
				defectOrderForAssignVo.setAvailableTakingPersonName(availableTakingPersonName);
			}
        	return EnerbosMessage.createSuccessMsg(defectOrderForAssignVo, "根据ID查询消缺工单-任务分派成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/findDefectOrderAssignById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/findDefectOrderAssignById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findDefectOrderReportById:根据ID查询消缺工单-执行汇报
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询消缺工单-执行汇报", response = DefectOrderForReportVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/findDefectOrderReportById", method = RequestMethod.GET)
    public EnerbosMessage findDefectOrderReportById(@ApiParam(value = "消缺工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
			DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(id);
			DefectOrderForReportVo defectOrderForReportVo=new DefectOrderForReportVo();
			ReflectionUtils.copyProperties(defectOrderVo,defectOrderForReportVo,null);
			if (defectOrderForReportVo != null) {
				//查询执行人
				List<OrderPersonVo> executorPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderForReportVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ACTUAL_EXECUTION));
				if (!executorPersonList.isEmpty()) {
					List<PersonAndUserVoForDetail> tmp = executorPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
					defectOrderForReportVo.setActualExecutorId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
					defectOrderForReportVo.setActualExecutorName(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
				}
				String processInstanceId=defectOrderForReportVo.getProcessInstanceId();
				if (null!=processInstanceId&&!"".equals(processInstanceId)) {
					defectOrderForReportVo.setImpleRecordVoVoList(getExecution(processInstanceId));
				}



				//执行人和委托执行人都可以接单entrustExecute

				//设置待接单人
				List<OrderPersonVo> availableTakingPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_EXECUTION));
				if (defectOrderVo.getEntrustExecute()) {
					//委托执行人
					availableTakingPersonList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(defectOrderVo.getId(), DefectCommon.DEFECT_ORDER_PERSON_ENTRUST_EXECUTION)));
				}
				List<PersonAndUserVoForDetail> tmp = availableTakingPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
				String availableTakingPersonName=StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ",");
				String availableTakingPersonId=StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ",");
				//defectOrderForAssignVo.setAvailableTakingPersonName(availableTakingPersonName);


				defectOrderForReportVo.setAvailableTakingPersonId(availableTakingPersonId);
						//availableTakingPersonId




				if (null!=defectOrderForReportVo.getActualExecutorResponsibleId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForReportVo.getActualExecutorResponsibleId());
					if (null!=personVo) {
						defectOrderForReportVo.setActualExecutorResponsibleName(personVo.getName());
					}
				}
				if (null!=defectOrderForReportVo.getReportId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForReportVo.getReportId());
					if (null!=personVo) {
						defectOrderForReportVo.setReportName(personVo.getName());
					}
				}
				if (null!=defectOrderForReportVo.getAssignPersonId()) {
					PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForReportVo.getAssignPersonId());
					if (null!=personVo) {
						defectOrderForReportVo.setAssignPersonName(personVo.getName());
					}
				}
				if (StringUtils.isNotBlank(defectOrderForReportVo.getProjectType())) {
					UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(defectOrderForReportVo.getProjectType(),
							DefectCommon.DEFECT_DOCUMENT_PROJECT_TYPE,defectOrderForReportVo.getOrgId(),
							defectOrderForReportVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
					if (vo != null) {
						UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
						if (voForDetail != null) {
							defectOrderForReportVo.setProjectTypeGroupTypeName(voForDetail.getName());
						}
					}
				}
			}
			return EnerbosMessage.createSuccessMsg(defectOrderForReportVo, "根据ID查询消缺工单-执行汇报成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/findDefectOrderReportById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/findDefectOrderReportById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * findDefectOrderAcceptById:根据ID查询消缺工单-验收确认
	 * @param id 
	 * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询消缺工单-验收确认", response = DefectOrderForAcceptVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/findDefectOrderCheckAcceptById", method = RequestMethod.GET)
    public EnerbosMessage findDefectOrderCheckAcceptById(@ApiParam(value = "消缺工单id", required = true) @RequestParam("id") String id,Principal user) {
        try {
        	DefectOrderVo defectOrderVo=defectOrderClient.findDefectOrderById(id);
			DefectOrderForAcceptVo defectOrderForAcceptVo=new DefectOrderForAcceptVo();
			ReflectionUtils.copyProperties(defectOrderVo,defectOrderForAcceptVo,null);
        	if (null!=defectOrderForAcceptVo.getAcceptorId()) {
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAcceptVo.getAcceptorId());
    			if (null!=personVo) {
					defectOrderForAcceptVo.setAcceptorName(personVo.getName());
				}
			}
        	String processInstanceId=defectOrderForAcceptVo.getProcessInstanceId();
        	if (null!=processInstanceId&&!"".equals(processInstanceId)) {
				defectOrderForAcceptVo.setImpleRecordVoVoList(getExecution(processInstanceId));
			}
//			if (null!=defectOrderForAcceptVo.getReportId()) {
//				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAcceptVo.getReportId());
//				if (null!=personVo) {
//					defectOrderForAcceptVo.setReport(personVo.getName());
//				}
//			}
			if (null!=defectOrderForAcceptVo.getActualExecutorResponsibleId()) {
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAcceptVo.getActualExecutorResponsibleId());
				if (null!=personVo) {
					defectOrderForAcceptVo.setActualExecutorResponsibleName(personVo.getName());
				}
			}
			if (null!=defectOrderForAcceptVo.getAssignPersonId()) {
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAcceptVo.getAssignPersonId());
				if (null!=personVo) {
					defectOrderForAcceptVo.setAssignPersonName(personVo.getName());
				}
			}
			if (null!=defectOrderForAcceptVo.getActualExecutorResponsibleId()) {
				PersonAndUserVoForDetail personVo= personAndUserClient.findByPersonId(defectOrderForAcceptVo.getActualExecutorResponsibleId());
				if (null!=personVo) {
					defectOrderForAcceptVo.setActualExecutorResponsibleName(personVo.getName());
				}
			}
        	return EnerbosMessage.createSuccessMsg(defectOrderForAcceptVo, "根据ID查询消缺工单-验收确认成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/defectOrder/findDefectOrderAcceptById ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/findDefectOrderAcceptById----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }
    
    /**
	 * checkDefectOrderNumUnique:验证消缺工单编码的唯一性
	 * @param id
	 * @param defectOrderNum 消缺工单编码
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
    @ApiOperation(value = "验证消缺工单编码的唯一性", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectOrder/checkDefectOrderNumUnique", method = RequestMethod.GET)
	public EnerbosMessage checkDefectOrderNumUnique(@ApiParam(value = "消缺工单id", required = false) @RequestParam(name="id", required = false) String id,
			@ApiParam(value = "消缺工单编码", required = true) @RequestParam("defectOrderNum") String defectOrderNum,Principal user) {
	    Boolean flag = false;
	    try {
	    	DefectOrderVo jpn = defectOrderClient.findDefectOrderByDefectOrderNum(defectOrderNum);
	        if (id == null) {//新建时
	        	if (jpn == null) {
	              flag = true;
	            }
	        } else {//修改时
              if (jpn != null&&jpn.getId().equals(id)||jpn == null) {
                  flag = true;
              }
	        }
	        return EnerbosMessage.createSuccessMsg(flag, "验证消缺工单编码的唯一性成功", "");
	    } catch (Exception e) {
	    	logger.error("-----/eam/open/defectOrder/checkDefectOrderNumUnique ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/checkDefectOrderNumUnique----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
	    }
	}

    /**
     * 删除选中的消缺工单
     * @param ids
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除消缺工单", response = Boolean.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/defectOrder/deleteDefectOrderList", method = RequestMethod.GET)
    public EnerbosMessage deleteDefectOrderList(@ApiParam(value = "删除选中的消缺工单ids", required = true) @RequestParam("ids") List<String> ids,Principal user) {
    	try {
        	for (String id : ids) {
        		DefectOrderVo defectOrder = defectOrderClient.findDefectOrderById(id);
                if (null == defectOrder) {
                	return EnerbosMessage.createErrorMsg("","要删除的消缺工单不存在", "");
                }else if(!DefectCommon.DEFECT_ORDER_STATUS_DTB.equals(defectOrder.getStatus())) {
                	return EnerbosMessage.createErrorMsg("","ID为"+defectOrder.getId()+"的消缺工单状态不是待提报", "");
				}
			}
            return EnerbosMessage.createSuccessMsg(defectOrderClient.deleteDefectOrderList(ids), "删除消缺工单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/defectOrder/deleteDefectOrderList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/defectOrder/deleteDefectOrderList----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

	/**
	 * 整改工单-收藏
	 * @param ids 消缺工单ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "整改工单-收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectOrder/collect", method = RequestMethod.POST)
	public EnerbosMessage collectDefectOrder(@ApiParam(value = "消缺工单ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/defectOrder/collect, host: [{}:{}], service_id: {}, defectOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要收藏的消缺工单！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<DefectOrderRfCollectorVo> defectOrderRfCollectorVos = Arrays.stream(ids).map(o -> new DefectOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			defectOrderClient.collectDefectOrder(defectOrderRfCollectorVos);

			return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/defectOrder/collect ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/defectOrder/deleteDefectOrder----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}

	/**
	 * cancelCollectDefectOrder:整改工单-取消收藏
	 * @param ids 消缺工单ID列表
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "整改工单-取消收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/defectOrder/collect/cancel", method = RequestMethod.POST)
	public EnerbosMessage cancelCollectDefectOrder(@ApiParam(value = "消缺工单ID列表", required = true) @RequestParam(value = "ids",required = true) String[] ids, Principal user) {
		try {
			ServiceInstance instance = client.getLocalServiceInstance();
			logger.info("/eam/open/defectOrder/collect/cancel, host: [{}:{}], service_id: {}, defectOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

			Assert.notEmpty(ids, "请选择要取消收藏的消缺工单！");
			String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

			List<DefectOrderRfCollectorVo> defectOrderRfCollectorVos = Arrays.stream(ids).map(o -> new DefectOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
			defectOrderClient.cancelCollectDefectOrder(defectOrderRfCollectorVos);
			return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/defectOrder/collect/cancel ------", e);
			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
			String statusCode  = "" ;
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if(jsonMessage !=null){
					statusCode =   jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/defectOrder/deleteDefectOrder----",jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
    
    /**
     * getExecution:根据流程实例ID查询执行记录
     * @param processInstanceId 流程实例ID
     * @return List<WorkOrderImpleRecordVo> 执行记录VO集合
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