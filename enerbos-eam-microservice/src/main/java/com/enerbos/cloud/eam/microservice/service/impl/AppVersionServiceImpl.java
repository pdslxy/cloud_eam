package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.CodeGenerator;
import com.enerbos.cloud.eam.microservice.repository.jpa.CodeGeneratorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.AppVersionDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.CodeGeneratorDao;
import com.enerbos.cloud.eam.microservice.service.AppVersionService;
import com.enerbos.cloud.eam.microservice.service.CodeGeneratorService;
import com.enerbos.cloud.eam.vo.AppVersionForFilterVo;
import com.enerbos.cloud.eam.vo.AppVersionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月27日
 * @Description APP 版本信息
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	private AppVersionDao appVersionDao;

	@Override
	public AppVersionVo getNewVersion(AppVersionForFilterVo appVersionForFilterVo) {
		List<AppVersionVo> curr = appVersionDao.findAppVersion(appVersionForFilterVo);
		AppVersionVo appVersionVo=new AppVersionVo();
		if(curr != null&&curr.size()>0){
			appVersionForFilterVo.setReleasedate(curr.get(0).getReleasedate());
			appVersionForFilterVo.setCurrVersion(null);
			List<AppVersionVo> appversions = appVersionDao.findAppVersion(appVersionForFilterVo);
			if (appversions != null&&!appversions.isEmpty()) {
				appVersionVo=appversions.get(0);
			}
		}
		return appVersionVo;
	}
}