package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolTermDao;
import com.enerbos.cloud.eam.microservice.service.PatrolTermService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.PatrolTermVo;
import com.enerbos.cloud.eam.vo.PatrolTermVoForFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class PatrolTermServiceImpl implements PatrolTermService {
    @Autowired
    private PatrolTermDao patrolTermDao;

    /**
     * 分页查询巡检项
     *
     * @param patrolTermVoForFilter
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolTermVo> findPatrolTermList(PatrolTermVoForFilter patrolTermVoForFilter) throws Exception {
        PageHelper.startPage(patrolTermVoForFilter.getPageNum(),
                patrolTermVoForFilter.getPageSize());

        String word = patrolTermVoForFilter.getWord();
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(patrolTermVoForFilter);
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        List<PatrolTermVo> patrolTermListByFilters = patrolTermDao.findPatrolTermListByFilters(filters);
        return new PageInfo<PatrolTermVo>(patrolTermListByFilters);
    }
}
