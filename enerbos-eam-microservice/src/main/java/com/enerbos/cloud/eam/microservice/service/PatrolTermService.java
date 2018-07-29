package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.PatrolTermVo;
import com.enerbos.cloud.eam.vo.PatrolTermVoForFilter;
import com.github.pagehelper.PageInfo;

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
public interface PatrolTermService {
    /**
     * 分页查询巡检项
     *
     * @param patrolTermVoForFilter
     * @return
     */
    PageInfo<PatrolTermVo> findPatrolTermList(PatrolTermVoForFilter patrolTermVoForFilter) throws Exception;
}
