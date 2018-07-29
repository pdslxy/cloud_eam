package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVo;
import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVoForFilter;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
public interface PatrolPlanFrequencyService {
    /**
     * 分页查询频率
     *
     * @param patrolPlanFrequencyVoForFilter
     * @return
     */
    PageInfo<PatrolPlanFrequencyVo> findPatrolPlanFrequencyList(PatrolPlanFrequencyVoForFilter patrolPlanFrequencyVoForFilter) throws Exception;


    void saveOrUpdate(PatrolPlanFrequencyVo patrolPlanFrequencyVo) throws EnerbosException;

    List<String> findIdsByPlanId(String planId) throws EnerbosException;
}
