package com.enerbos.cloud.eam.openservice.controller;

import java.security.Principal;

import com.enerbos.cloud.uas.vo.product.ProductVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.CodeGeneratorClient;
import com.enerbos.cloud.util.PrincipalUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;

import javax.validation.Valid;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年6月30日
 * @Description 生成编码
 */
@RestController
@Api(description = "生成编码(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class CodeGeneratorController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
    private CodeGeneratorClient codeGeneratorClient;
    
    /**
	 * getCodegenerator:获取编码
	 * @param siteId 站点ID
	 * @param modelKey 模块ID
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "获取编码", response = String.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/getCodegenerator", method = RequestMethod.GET)
    public EnerbosMessage getCodegenerator(@ApiParam(value = "模块ID", required = true) @RequestParam("modelKey") String modelKey,
                                           @ApiParam(value = "组织ID", required = true) @RequestParam("orgId") String orgId,
                                           @ApiParam("站点ID") @RequestParam(value = "siteId",required = false) String siteId,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
        	logger.info("/eam/open/getCodegenerator, host: [{}:{}], service_id: {}, modelKey: {},orgId: {},siteId: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), modelKey,orgId,siteId);
        	String result=codeGeneratorClient.getCodegenerator(orgId,siteId, modelKey);
        	if (null==result||"".equals(result)) {
        		return EnerbosMessage.createErrorMsg("", "编码生成规则内容读取失败！", "");
			}
        	return EnerbosMessage.createSuccessMsg(result, "获取编码成功", "");
        } catch (Exception e) {
        	logger.error("-----/eam/open/getCodegenerator ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ; 
            String statusCode  = "" ;
            try {
            	JSONObject jsonMessage = JSONObject.parseObject(message);
            	if(jsonMessage !=null){
            		statusCode =   jsonMessage.get("status").toString(); 
       		 	}
			} catch (Exception jsonException) {
				logger.error("-----/eam/open/getCodegenerator----",jsonException);
			}
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

}
