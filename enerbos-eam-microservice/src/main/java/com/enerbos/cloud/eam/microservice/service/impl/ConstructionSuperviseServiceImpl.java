package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.ConstructionSupervise;
import com.enerbos.cloud.eam.microservice.repository.jpa.ConstructionSuperviseRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.ConstructionSuperviseDao;
import com.enerbos.cloud.eam.microservice.service.ConstructionSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月9日
 * @Description 施工单-监管说明
 */
@Service
public class ConstructionSuperviseServiceImpl implements ConstructionSuperviseService {

	@Autowired
	private ConstructionSuperviseRepository constructionSuperviseRepository;
	@Autowired
	private ConstructionSuperviseDao constructionSuperviseDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ConstructionSupervise> saveConstructionSupervise(List<ConstructionSupervise> constructionSuperviseList) {
		return constructionSuperviseRepository.save(constructionSuperviseList);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ConstructionSupervise findConstructionSuperviseById(String id) {
		return constructionSuperviseRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteConstructionSuperviseByIds(List<String> ids) {
		for (String id : ids) {
			constructionSuperviseRepository.delete(id);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ConstructionSupervise> findConstructionSuperviseByConstructionId(String constructionId) {
		return constructionSuperviseDao.findConstructionSuperviseByConstructionId(constructionId);
	}
}
