package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.service.AppVersionService;
import com.enerbos.cloud.eam.microservice.service.CodeGeneratorService;
import com.enerbos.cloud.eam.vo.AppVersionForFilterVo;
import com.enerbos.cloud.eam.vo.AppVersionVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月27日
 * @Description APP 版本信息
 */
@RestController
public class AppVersionController {
	
	private final static Log logger = LogFactory.getLog(AppVersionController.class);

    @Autowired
    private AppVersionService appVersionService;
    
    /**
	 * getNewVersion: 查询是否有新版本
	 * @param appVersionForFilterVo 过滤条件VO
	 * @return AppVersionVo 最新版本
	 */
	@RequestMapping(value = "/eam/micro/getNewVersion", method = RequestMethod.POST)
	public AppVersionVo getNewVersion(@RequestBody AppVersionForFilterVo appVersionForFilterVo){
    	return appVersionService.getNewVersion(appVersionForFilterVo);
	}
}
