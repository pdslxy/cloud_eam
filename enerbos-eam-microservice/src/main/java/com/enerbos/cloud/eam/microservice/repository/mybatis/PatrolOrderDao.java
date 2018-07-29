package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.OrderCountBySiteVo;
import com.enerbos.cloud.eam.vo.OrderMaxCountVo;
import com.enerbos.cloud.eam.vo.PatrolOrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/13
 * @Description
 */
@Mapper
public interface PatrolOrderDao {
    List<PatrolOrderVo> findPatrolOrderListByFilters(Map<String, Object> filters);

    List<PatrolOrderVo> findPatrolOrderByPatrolplanid(String id);

    /**
     * 查询巡检工单条数
     *
     * @return
     */
    OrderCountBySiteVo findPatrolOrderTotal(Map<String,Object> param);


    /**
     * 查询最大巡检工单数
     *
     * @return
     */
    OrderMaxCountVo findMaxCountOrder(Map<String, Object> param);
}
