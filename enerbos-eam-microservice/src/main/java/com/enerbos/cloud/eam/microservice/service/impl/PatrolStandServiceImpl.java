package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.PatrolStand;
import com.enerbos.cloud.eam.microservice.domain.PatrolStandContent;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolStandContentRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolStandRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolStandDao;
import com.enerbos.cloud.eam.microservice.service.PatrolStandService;
import com.enerbos.cloud.eam.vo.PatrolStandContentVoForList;
import com.enerbos.cloud.eam.vo.PatrolStandVoForFilter;
import com.enerbos.cloud.eam.vo.PatrolStandVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class PatrolStandServiceImpl implements PatrolStandService {

    @Autowired
    public PatrolStandDao patrolStandDao;

    @Autowired
    public PatrolStandRepository patrolStandRepository;

    @Autowired
    public PatrolStandContentRepository patrolStandContentRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolStandVoForList> findPage(
            PatrolStandVoForFilter patrolStandVoForFilter) {
        PageHelper.startPage(patrolStandVoForFilter.getPageNum(),
                patrolStandVoForFilter.getPageSize());

        Map<String, Object> filter = null;
        try {
            filter = ReflectionUtils
                    .reflectionModelToMap(patrolStandVoForFilter);

        } catch (Exception e) {
            logger.error("PatrolStandServiceImpl.findPage", e);
        }
        return new PageInfo<PatrolStandVoForList>(
                patrolStandDao.findPage(filter));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolStand findPatrolStandById(String id) {
        return patrolStandRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolStand savePatrolStand(PatrolStand patrolStand) {
        return patrolStandRepository.save(patrolStand);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolStand updatePatrolStand(PatrolStand patrolStand) {
        return patrolStandRepository.save(patrolStand);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletePatrolStand(String[] ids) {

        for (String id : ids) {
            patrolStandRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolStandContentVoForList> findPatrolStandContent(
            String id, Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<PatrolStandContentVoForList>(
                patrolStandDao.findPatrolStandContent(id));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolStandContent savePatrolStandContent(
            PatrolStandContent patrolStandContent) {
        return patrolStandContentRepository.save(patrolStandContent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolStandContent updatePatrolStandContent(
            PatrolStandContent patrolStandContent) {
        return patrolStandContentRepository.save(patrolStandContent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletePatrolStandContent(String[] ids) {

        for (String id : ids) {
            patrolStandContentRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolStandContent findPatrolStandContentById(String id) {
        return patrolStandContentRepository.findOne(id);
    }
}
