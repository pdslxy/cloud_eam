package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.OrderPersonVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 18:13
 * @Description EAM工单关联人员接口
 */
public interface OrderPersonService {

    /**
     * 查询工单关联的人员列表
     * @param orderPersonVo 筛选条件
     * @return 工单关联人员列表VO
     */
    List<OrderPersonVo> findListByFilter(OrderPersonVo orderPersonVo);

    /**
     * 根据ID删除
     * @param id ID
     */
    void deleteOrderPersonById(String id);

    /**
     * 根据工单编号以及字段类型删除
     * @param orderId       工单编号
     * @param fieldType    字段类型
     */
    void deleteOrderPersonByOrderIdAndFieldType(String orderId, String fieldType);

    /**
     * 根据工单编号删除
     * @param orderId       工单编号
     */
    void deleteOrderPersonByOrderId(String orderId);

    /**
     * 新增工单关联人员列表
     * @param orderPersonVoList 新增人员列表
     */
    void addOrderPerson(List<OrderPersonVo> orderPersonVoList);

    /**
     * 更新工单关联人员列表(先删除，再新增)
     * @param orderPersonVoList 所有人员列表
     * @return List<OrderPersonVo> 修改前的原始数据，方便回滚操作
     */
    List<OrderPersonVo> updateOrderPersonByOrderIdAndFieldType(List<OrderPersonVo> orderPersonVoList);

}
