package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolPlan;
import com.enerbos.cloud.eam.microservice.domain.PatrolPlanFrequency;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolPlanFrequencyRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolPlanFrequencyDao;
import com.enerbos.cloud.eam.microservice.service.PatrolPlanFrequencyService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVo;
import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVoForFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
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
public class PatrolPlanFrequencyServiceImpl implements PatrolPlanFrequencyService {
    @Autowired
    private PatrolPlanFrequencyDao patrolPlanFrequencyDao;

    @Autowired
    private PatrolPlanFrequencyRepository patrolPlanFrequencyRepository;

    /**
     * 分页查询频率
     *
     * @param patrolPlanFrequencyVoForFilter
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolPlanFrequencyVo> findPatrolPlanFrequencyList(PatrolPlanFrequencyVoForFilter patrolPlanFrequencyVoForFilter) throws Exception {
        PageHelper.startPage(patrolPlanFrequencyVoForFilter.getPageNum(),
                patrolPlanFrequencyVoForFilter.getPageSize());

        String word = patrolPlanFrequencyVoForFilter.getWords();
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(patrolPlanFrequencyVoForFilter);
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        List<PatrolPlanFrequencyVo> patrolPlanFrequencyListByFilters = patrolPlanFrequencyDao.findPatrolPlanFrequencyListByFilters(filters);
        return new PageInfo<PatrolPlanFrequencyVo>(patrolPlanFrequencyListByFilters);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveOrUpdate(PatrolPlanFrequencyVo patrolPlanFrequencyVo) throws EnerbosException {
        PatrolPlanFrequency patrolPlanFrequency = new PatrolPlanFrequency();
        PatrolPlan patrolPlan = new PatrolPlan();
        BeanUtils.copyProperties(patrolPlanFrequencyVo, patrolPlanFrequency);
        patrolPlanFrequency.setUpdatetime(new Date());
        patrolPlan.setId(patrolPlanFrequencyVo.getPatrolPlanId());
        patrolPlanFrequency.setPatrolPlan(patrolPlan);
        patrolPlanFrequencyRepository.save(patrolPlanFrequency);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> findIdsByPlanId(String planId) throws EnerbosException{
        return patrolPlanFrequencyRepository.findIdsByPlanId(planId);
    }
}
