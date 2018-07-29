package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolPlan;
import com.enerbos.cloud.eam.vo.PatrolPlanForSaveVo;
import com.enerbos.cloud.eam.vo.PatrolPlanVo;
import com.enerbos.cloud.eam.vo.PatrolPlanVoForFilter;
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
public interface PatrolPlanService {
    /**
     * 根据筛选条件和分页信息获取巡检路线列表
     *
     * @return
     */
    PageInfo<PatrolPlanVo> findPatrolPlanList(PatrolPlanVoForFilter patrolPlanVoForFilter) throws Exception;

    /**
     * 根据id数组删除巡检路线
     *
     * @param ids
     */
    void deletePatrolPlanByIds(String[] ids) throws EnerbosException;

    /**
     * 保存巡检路线和关联的巡检项信息
     *
     * @param patrolPlanForSaveVo
     */
    PatrolPlanVo saveOrUpdate(PatrolPlanForSaveVo patrolPlanForSaveVo) throws Exception;

    /**
     * 根据id查询巡检路线
     *
     * @param patrolPlanId
     */
    PatrolPlanVo findPatrolPlanById(String patrolPlanId) throws EnerbosException;

    /**
     * 根据巡检路线Id查询巡检项
     *
     * @param id
     * @return
     */
    PatrolPlan findPatrolPlanByPlanId(String id) throws EnerbosException;
}
