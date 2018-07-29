package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.PatrolOrderCommon;
import com.enerbos.cloud.eam.microservice.domain.PatrolOrder;
import com.enerbos.cloud.eam.microservice.domain.PatrolRecordTerm;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRecordTermRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRecordTermDao;
import com.enerbos.cloud.eam.microservice.service.PatrolRecordTermService;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVoForFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
@Service
public class PatrolRecordTermServiceImpl implements PatrolRecordTermService {
    @Autowired
    private PatrolRecordTermRepository patrolRecordTermRepository;

    @Autowired
    private PatrolRecordTermDao patrolRecordTermDao;

    @Autowired
    private PatrolOrderRepository patrolOrderRepository;

    @Override
    public PageInfo<PatrolRecordTermVo> findPatrolRecordTerm(PatrolRecordTermVoForFilter patrolRecordTermVoForFilter) throws EnerbosException {
        PageHelper.startPage(patrolRecordTermVoForFilter.getPageNum(),
                patrolRecordTermVoForFilter.getPageSize());
        Map<String, Object> map = new HashMap<>();
        List<PatrolRecordTermVo> patrolRecordTermList = new ArrayList<>();
        //巡检点对应的执行过的巡检工单
        List<Map<String, Object>> patrolOrderList = patrolRecordTermDao.findExcutePatrolOrderByPoint(patrolRecordTermVoForFilter.getPatrolPointId(), PatrolOrderCommon.STATUS_GB);
        //根据巡检工单查询巡检项状态
        for (Map<String, Object> datamap : patrolOrderList) {
            PatrolRecordTermVo patrolRecordTermVo = new PatrolRecordTermVo();
            String orderId = (String) datamap.get("id");
            List<Map<String, Object>> statusMapList = patrolRecordTermDao.findTermStatusListByOrder(orderId, patrolRecordTermVoForFilter.getPatrolPointId());

            Map statusMap = new HashMap<>();
            if (statusMapList != null && statusMapList.size() > 0) {
                for (Map<String, Object> statumap : statusMapList) {
                    statusMap.put(statumap.get("patroltermdsr"), statumap.get("isqualified"));
                }
            }
            patrolRecordTermVo.setId((String) datamap.get("id"));
            patrolRecordTermVo.setStatusmap(statusMap);
            patrolRecordTermVo.setUpdatetime((Date) statusMapList.get(0).get("updatetime"));
            patrolRecordTermVo.setOrderNum((String) datamap.get("patrolordernum"));
            //查询巡检人
            PatrolOrder order = patrolOrderRepository.findOne(orderId);
            patrolRecordTermVo.setExcutePersonId(order.getExcutePersonId());
            patrolRecordTermList.add(patrolRecordTermVo);
        }

        return new PageInfo<PatrolRecordTermVo>(patrolRecordTermList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PatrolRecordTermVo findById(String id) throws EnerbosException {
        PatrolRecordTerm patrolRecordTerm = patrolRecordTermRepository.findOne(id);
        PatrolRecordTermVo patrolRecordTermVo = new PatrolRecordTermVo();
        BeanUtils.copyProperties(patrolRecordTerm, patrolRecordTermVo);
        return patrolRecordTermVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOrUpdate(PatrolRecordTermVo patrolRecordTermVo)throws EnerbosException {
        PatrolRecordTerm patrolRecordTerm = new PatrolRecordTerm();
        BeanUtils.copyProperties(patrolRecordTermVo, patrolRecordTerm);
        patrolRecordTermRepository.save(patrolRecordTerm);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolRecordTermVo> findPatrolTermByOrderAndPoint(String id, String pointid) {
            return new PageInfo<PatrolRecordTermVo>(patrolRecordTermDao.findPatrolTermByOrderAndPoint(id, pointid));
    }
}
