package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolRoute;
import com.enerbos.cloud.eam.vo.*;
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
public interface PatrolRouteService {
    /**
     * 根据筛选条件和分页信息获取巡检路线列表
     *
     * @return
     */
    PageInfo<PatrolRouteVo> findPatrolRouteList(PatrolRouteVoForFilter patrolRouteVoForFilter) throws Exception;

    /**
     * 根据id数组删除巡检路线
     *
     * @param ids
     */
    void deletePatrolRouteByIds(String[] ids) throws EnerbosException;

    /**
     * 保存巡检路线和关联的巡检项信息
     *
     * @param patrolRouteVo
     */
    PatrolRouteVo saveOrUpdate(PatrolRouteForSaveVo patrolRouteVo) throws EnerbosException;

    /**
     * 根据id查询巡检路线
     *
     * @param patrolRouteId
     */
    PatrolRoute findPatrolRouteById(String patrolRouteId) throws EnerbosException;

    /**
     * 根据巡检路线Id查询巡检项
     *
     * @param id
     * @param sorts 排序
     * @return
     */
    List<PatrolPointVo> findPatrolPointByRouteId(String id,String sorts) throws EnerbosException;

    /**
     * 根据巡检工单id查询巡检路线
     * @param id
     * @return
     */
    PatrolRouteVo findRouteByOrderId(String id) throws EnerbosException;

    List<PatrolRouteVo> findByPointId(String pointId) throws EnerbosException;
}
