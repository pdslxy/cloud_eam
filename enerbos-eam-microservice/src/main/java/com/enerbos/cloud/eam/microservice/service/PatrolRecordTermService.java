package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVoForFilter;
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
public interface PatrolRecordTermService {
    /**
     * 查询巡检点记录
     *
     * @param patrolRecordTermVoForFilter
     * @return
     */
    PageInfo<PatrolRecordTermVo> findPatrolRecordTerm(PatrolRecordTermVoForFilter patrolRecordTermVoForFilter) throws EnerbosException;

    PatrolRecordTermVo findById(String id) throws EnerbosException;

    void saveOrUpdate(PatrolRecordTermVo patrolRecordTermVo)throws EnerbosException;

    /**
     * 根据工单id查询巡检记录中巡检项内容
     * @param id
     * @return
     */
    PageInfo<PatrolRecordTermVo> findPatrolTermByOrderAndPoint(String id, String pointid);
}
