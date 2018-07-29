package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.vo.PatrolRecordVo;
import com.enerbos.cloud.eam.vo.PatrolRouteVo;

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
public interface PatrolRecordService {

    PatrolRecordVo findById(String id) throws EnerbosException;

    void saveOrUpdate(PatrolRecordVo patrolRecordVo)throws EnerbosException;


    /**
     * 根据工单查询巡检路线及点的记录
     * @param id 工单的ID
     * @return 巡检路线
     * @throws EnerbosException
     */
    PatrolRouteVo findPatrolRecordVoByOrderId(String id)throws EnerbosException;
}

