package com.enerbos.cloud.eam.openservice.controller;/**
 * Created by enerbos on 2017/8/17.
 */

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.vo.ProcessExecutionRecordVo;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;




/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/17 12:29
 * @Description:
 */
@RestController
@Api(description = "获取流程执行记录(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class ProcessExecutionRecordController {


    private static Logger logger = LoggerFactory.getLogger(ProcessExecutionRecordController.class);

    @Autowired
    private DiscoveryClient client;
    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private ProcessActivitiClient processActivitiClient;





    /**
     * 派工工单-执行记录
     * @param processInstanceId 派工工单-流程编号
     * @return EnerbosMessage返回工单执行记录
     */
    @ApiOperation(value = "流程执行记录-执行记录", response = ProcessExecutionRecordVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/process/execution/record/history", method = RequestMethod.GET)
    public EnerbosMessage getProcessExecutionRecord(@ApiParam(value = "流程编号", required = true) String processInstanceId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/process/execution/record/history, host: [{}:{}], service_id: {}, processInstanceId: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), processInstanceId, user);
            Assert.isTrue(StringUtils.isNotEmpty(processInstanceId), "流程编号不能为空！");
            List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
            List<ProcessExecutionRecordVo> flowRecordVoList = Objects.isNull(historicTaskVoList) ? Collections.EMPTY_LIST :
                    historicTaskVoList.stream()
                            .sorted(Comparator.comparing(HistoricTaskVo::getStartTime, Comparator.reverseOrder()))
                            .map(historicTaskVo ->  {
                                ProcessExecutionRecordVo vo = new ProcessExecutionRecordVo();
                                vo.setStartTime(historicTaskVo.getStartTime());
                                vo.setEndTime(historicTaskVo.getEndTime());
                                vo.setName(historicTaskVo.getName());
                                if (StringUtils.isNoneEmpty(historicTaskVo.getAssignee())) {
                                    String[] ids = historicTaskVo.getAssignee().split(",");
                                    List<String> personVoList = Arrays.stream(ids).map(id -> personAndUserClient.findByPersonId(id)).filter(Objects::nonNull).map(PersonAndUserVoForDetail::getName).collect(Collectors.toList());
                                    if (Objects.nonNull(personVoList) && !personVoList.isEmpty()) {
                                        vo.setPersonName(StringUtils.join(personVoList, ","));
                                    }
                                }

                                vo.setDurationInMillis(historicTaskVo.getDurationInMillis());
                                vo.setDescription(historicTaskVo.getDescription());
                                return vo;
                            })
                            .collect(Collectors.toList());
            return EnerbosMessage.createSuccessMsg(flowRecordVoList, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/process/execution/record/history");
        }
    }

    private EnerbosMessage buildErrorMsg(Exception exception, String uri) {
        logger.error(String.format("------- %s --------", uri), exception);
        String message, statusCode  = "" ;
        if (exception instanceof HystrixRuntimeException) {
            Throwable fallbackException = exception.getCause();
            if (fallbackException.getMessage().contains("{")) {
                message =  fallbackException.getMessage().substring(fallbackException.getMessage().indexOf("{")) ;
                try {
                    JSONObject jsonMessage = JSONObject.parseObject(message);
                    if(jsonMessage !=null){
                        statusCode =   jsonMessage.get("status").toString();
                        message = jsonMessage.get("message").toString();
                    }
                } catch (Exception jsonException) {
                    logger.error(String.format("------- %s --------", uri), jsonException);
                }
            } else {
                message = fallbackException.getMessage();
                if (fallbackException instanceof EnerbosException) {
                    statusCode = ((EnerbosException) exception).getErrorCode();
                }
            }
        } else {
            message = exception.getMessage();
            if (exception instanceof EnerbosException) {
                statusCode = ((EnerbosException) exception).getErrorCode();
            }
        }
        return EnerbosMessage.createErrorMsg(statusCode, message, exception.getStackTrace().toString());
    }
}
