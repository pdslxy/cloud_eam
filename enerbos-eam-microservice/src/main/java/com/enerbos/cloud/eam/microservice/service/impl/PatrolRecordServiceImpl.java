package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolOrder;
import com.enerbos.cloud.eam.microservice.domain.PatrolRecord;
import com.enerbos.cloud.eam.microservice.domain.PatrolRecordTerm;
import com.enerbos.cloud.eam.microservice.domain.PatrolRoute;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRecordRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolPointDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRecordTermDao;
import com.enerbos.cloud.eam.microservice.service.PatrolRecordService;
import com.enerbos.cloud.eam.vo.PatrolPointVo;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
import com.enerbos.cloud.eam.vo.PatrolRecordVo;
import com.enerbos.cloud.eam.vo.PatrolRouteVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/28
 * @Description
 */
@Service
public class PatrolRecordServiceImpl implements PatrolRecordService {
    @Autowired
    private PatrolRecordRepository patrolRecordRepository;

    @Autowired
    private PatrolOrderRepository patrolOrderRepository;

    @Autowired
    private PatrolPointDao patrolPointDao;

    @Autowired
    private PatrolRecordTermDao patrolRecordTermDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PatrolRecordVo findById(String id) throws EnerbosException {
        PatrolRecord patrolRecord = patrolRecordRepository.findOne(id);
        PatrolRecordVo patrolRecordVo = new PatrolRecordVo();
        BeanUtils.copyProperties(patrolRecord, patrolRecordVo);
        return patrolRecordVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOrUpdate(PatrolRecordVo patrolRecordVo) throws EnerbosException {
        PatrolRecord patrolRecord = new PatrolRecord();
        BeanUtils.copyProperties(patrolRecordVo, patrolRecord);
        patrolRecordRepository.save(patrolRecord);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolRouteVo findPatrolRecordVoByOrderId(String id) throws EnerbosException {
        PatrolOrder patrolOrder = patrolOrderRepository.findOne(id);

        List<PatrolPointVo> patrolPointList = new ArrayList<>();
        PatrolRouteVo patrolRouteVo = new PatrolRouteVo();
        if (patrolOrder != null) {
            PatrolRoute patrolRoute = patrolOrder.getPatrolRoute();
            BeanUtils.copyProperties(patrolRoute, patrolRouteVo);

            //查询工单已经定型的点
            patrolPointList = patrolPointDao.findPatrolPointListByOrderId(id);
            for (int i = 0; i < patrolPointList.size(); i++) {
                List<PatrolRecordTermVo> list=patrolRecordTermDao.findPatrolTermByOrderAndPoint(id,patrolPointList.get(i).getId());
                patrolPointList.get(i).setPatrolRecordTermVoList(list);
            }

            patrolRouteVo.setPatrolPointVoList(patrolPointList);
        }
        return patrolRouteVo;
    }

}
