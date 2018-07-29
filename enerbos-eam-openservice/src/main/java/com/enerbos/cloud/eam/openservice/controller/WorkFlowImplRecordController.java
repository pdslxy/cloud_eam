package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月06日
 * @Description 查询执行记录接口
 */
@RestController
@Api(description = "查询工单执行记录(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class WorkFlowImplRecordController {

	private Logger logger = LoggerFactory.getLogger(WorkFlowImplRecordController.class);

	@Autowired
	private DiscoveryClient client;

	@Autowired
	private PersonAndUserClient personAndUserClient;

	@Autowired
	private ProcessActivitiClient processActivitiClient;

	/**
	 * findRecordByProcessInstanceId:根据流程实例id查询执行记录
	 * @param processInstanceId  流程实例id
	 * @param user
	 * @return EnerbosMessage返回执行码及数据
	 */
	@ApiOperation(value = "根据流程实例id查询执行记录", response = WorkFlowImpleRecordVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
	@RequestMapping(value = "/eam/open/workFlow/findRecordByProcessInstanceId", method = RequestMethod.GET)
	public EnerbosMessage findRecordByProcessInstanceId(@ApiParam(value = "流程实例id", required = true) @RequestParam("processInstanceId") String processInstanceId, Principal user) {
		try {
			List<WorkFlowImpleRecordVo> workFlowImpleRecordVoList = new ArrayList<>();
			List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
			WorkFlowImpleRecordVo workFlowImpleRecordVo = null;
			Set<String> processName=new HashSet<>();
			if (null!=historicTaskVoList&&historicTaskVoList.size()>0) {
				for (int i = historicTaskVoList.size()-1; i >=0; i--) {
					HistoricTaskVo historicTaskVo=historicTaskVoList.get(i);
					workFlowImpleRecordVo=new WorkFlowImpleRecordVo();
					workFlowImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
					workFlowImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
					workFlowImpleRecordVo.setName(historicTaskVo.getName());
					if (processName.isEmpty()||!processName.contains(workFlowImpleRecordVo.getName())) {
						processName.add(workFlowImpleRecordVo.getName());
						workFlowImpleRecordVo.setProcessType(Common.ORDER_PROCESS_TYPE_NORMAL);
					}else if (!processName.isEmpty()&&processName.contains(workFlowImpleRecordVo.getName())){
						workFlowImpleRecordVo.setProcessType(Common.ORDER_PROCESS_TYPE_REJECT);
					}

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
					workFlowImpleRecordVoList.add(0,workFlowImpleRecordVo);
				}
			}
			return EnerbosMessage.createSuccessMsg(workFlowImpleRecordVoList, "根据流程实例id查询执行记录成功", "");
		} catch (Exception e) {
			logger.error("-----/eam/open/workFlow/findRecordByProcessInstanceId ------", e);
			String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
			String statusCode = "";
			try {
				JSONObject jsonMessage = JSONObject.parseObject(message);
				if (jsonMessage != null) {
					statusCode = jsonMessage.get("status").toString();
				}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/workFlow/findRecordByProcessInstanceId----", jsonException);
			}
			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
		}
	}
}