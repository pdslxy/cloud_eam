package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolOrder;
import com.enerbos.cloud.eam.microservice.domain.PatrolPlan;
import com.enerbos.cloud.eam.microservice.domain.PatrolPlanFrequency;
import com.enerbos.cloud.eam.microservice.domain.PatrolRoute;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolPlanFrequencyRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolPlanRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolOrderDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolPlanDao;
import com.enerbos.cloud.eam.microservice.service.PatrolPlanService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PatrolPlanServiceImpl implements PatrolPlanService {

    @Autowired
    private PatrolPlanRepository patrolPlanRepository;

    @Autowired
    private PatrolPlanDao patrolPlanDao;

    @Autowired
    private PatrolPlanFrequencyRepository patrolPlanFrequencyRepository;

    @Autowired
    private PatrolOrderDao patrolOrderDao;
    @Autowired
    private PatrolOrderRepository patrolOrderRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolPlanVo> findPatrolPlanList(PatrolPlanVoForFilter PatrolPlanVoForFilter) throws Exception {
        PageHelper.startPage(PatrolPlanVoForFilter.getPageNum(),
                PatrolPlanVoForFilter.getPageSize());

        String word = PatrolPlanVoForFilter.getWords();
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(PatrolPlanVoForFilter);
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<PatrolPlanVo>(patrolPlanDao.findPatrolPlanListByFilters(filters));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletePatrolPlanByIds(String[] ids) throws EnerbosException {
        for (String id : ids) {
            //查询是否有工单关联巡检计划
            List<PatrolOrderVo> patrolOrderList = patrolOrderDao.findPatrolOrderByPatrolplanid(id);
            if (patrolOrderList != null && patrolOrderList.size() > 0) {
                PatrolOrder patrolOrder = patrolOrderRepository.findOne(patrolOrderList.get(0).getId());
                throw new RuntimeException("所选计划已经关联巡检工单:" + patrolOrder.getPatrolOrderNum() + "，请取消关联后再删除");
            }
            //先删除与频率的关联关系
            PatrolPlan patrolPlan = new PatrolPlan();
            patrolPlan.setId(id);
            patrolPlanFrequencyRepository.deleteByPatrolPlan(patrolPlan);
            patrolPlanRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolPlanVo saveOrUpdate(PatrolPlanForSaveVo patrolPlanForSaveVo) throws Exception {
//保存或更新巡检点的信息
        PatrolPlan pp = new PatrolPlan();
        BeanUtils.copyProperties(patrolPlanForSaveVo, pp);
        if (StringUtils.isNotBlank(pp.getId())) {
            pp.setUpdatetime(new Date());
        } else {
            pp.setCreatetime(new Date());
        }
        PatrolRoute patrolRoute = new PatrolRoute();
        patrolRoute.setId(patrolPlanForSaveVo.getPatrolRouteId());
        pp.setPatrolRoute(patrolRoute);
        PatrolPlan plan = patrolPlanRepository.save(pp);
        //保存或更新巡检项的信息
        List<PatrolPlanFrequencyVo> list = patrolPlanForSaveVo.getFrequencyAddlist();
        PatrolPlanFrequency ppf = new PatrolPlanFrequency();
        if (list != null) {
            for (PatrolPlanFrequencyVo patrolFrequencyVo : list) {
                BeanUtils.copyProperties(patrolFrequencyVo, ppf);
                ppf.setPatrolPlan(plan);
                ppf.setCreatetime(new Date());
                ppf.setUpdatetime(new Date());
                patrolPlanFrequencyRepository.save(ppf);
            }
        }
        List<String> frequencyDeleteIds = patrolPlanForSaveVo.getFrequencyDeleteIds();
        if (frequencyDeleteIds != null && frequencyDeleteIds.size() > 0) {
            String s = frequencyDeleteIds.get(0);
            String[] ids = s.split(",");
            for (String id : ids) {
                patrolPlanFrequencyRepository.delete(id);
            }
        }
        PatrolPlanVo patrolPlanVo = new PatrolPlanVo();
        BeanUtils.copyProperties(pp, patrolPlanVo);
        return patrolPlanVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolPlanVo findPatrolPlanById(String patrolPlanId) throws EnerbosException {
//        PatrolPlan PatrolPlan = patrolPlanRepository.findOne(PatrolPlanId);
        PatrolPlanVo patrolPlanVo = patrolPlanDao.findPatrolPlanVoById(patrolPlanId);
        return patrolPlanVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolPlan findPatrolPlanByPlanId(String id) throws EnerbosException {
        PatrolPlan patrolPlan = patrolPlanRepository.findOne(id);
        return patrolPlan;
    }

}
