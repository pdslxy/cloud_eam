package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.ContractCommonClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@RestController
@Api(description = "合同管理通用(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class ContractCommonController {
    private static Logger logger = LoggerFactory.getLogger(ContractCommonController.class);

    @Autowired
    private ContractCommonClient contractCommonClient;

    @Autowired
    private DiscoveryClient client;

    @ApiOperation(value = "收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/collect")
    public EnerbosMessage collect(@RequestParam("id") String id, @RequestParam("type") String type, Principal user) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/eam/open/repair/order/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, user);
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            contractCommonClient.collect(id, Common.EAM_PROD_IDS[0], type, personId);
            return EnerbosMessage.createSuccessMsg(true, "收藏成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/contract/collect");
        }
    }


    @ApiOperation(value = "取消收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/contract/cancelCollect")
    public EnerbosMessage cancelCollect(@RequestParam("id") String id, @RequestParam("type") String type, Principal user) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/eam/open/repair/order/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), id, user);
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            contractCommonClient.cancelCollectByCollectIdAndType(id, Common.EAM_PROD_IDS[0], type, personId);
            return EnerbosMessage.createSuccessMsg(true, "取消收藏成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/contract/cancelCollect");
        }
    }


    private EnerbosMessage buildErrorMsg(Exception exception, String uri) {
        logger.error(String.format("------- %s --------", uri), exception);
        String message, statusCode = "";
        if (exception instanceof HystrixRuntimeException) {
            Throwable fallbackException = exception.getCause();
            if (fallbackException.getMessage().contains("{")) {
                message = fallbackException.getMessage().substring(fallbackException.getMessage().indexOf("{"));
                try {
                    JSONObject jsonMessage = JSONObject.parseObject(message);
                    if (jsonMessage != null) {
                        statusCode = jsonMessage.get("status").toString();
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
