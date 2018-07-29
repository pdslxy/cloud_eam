package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.OrderPersonVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 11:09
 * @Description
 */
@Mapper
public interface OrderPersonDao {

    /**
     * 根据条件查询工单关联人员
     * @param orderPersonVo {@link com.enerbos.cloud.eam.vo.OrderPersonVo} 筛选条件
     * @return List<OrderPersonVo>
     */
    List<OrderPersonVo> findListByFilter(OrderPersonVo orderPersonVo);

}
