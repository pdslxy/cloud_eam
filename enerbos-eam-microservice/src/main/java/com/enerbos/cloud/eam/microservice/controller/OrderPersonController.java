package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.OrderPersonService;
import com.enerbos.cloud.eam.vo.OrderPersonVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-17 10:18
 * @Description EAM工单关联人员相关服务
 */
@RestController
public class OrderPersonController {

    @Autowired
    private OrderPersonService orderPersonService;

    /**
     * 查询工单关联的人员列表
     * @param orderPersonVo 查询条件
     * @return List<OrderPersonVo> 工单人员列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/order/person/list")
    public List<OrderPersonVo> findOrderListByFilter(@RequestBody OrderPersonVo orderPersonVo) {
        //不捕获异常，如有异常直接向外层扔出
        List<OrderPersonVo> result = orderPersonService.findListByFilter(orderPersonVo);
        return result;
    }

    /**
     * 根据ID删除
     * @param id ID
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eam/micro/order/person/deleteById")
    public void deleteOrderPersonById(@RequestParam("id") String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "编号不能为空！");
        orderPersonService.deleteOrderPersonById(id);
    }

    /**
     * 根据工单编号以及字段类型删除
     * @param orderId       工单编号
     * @param fieldType    字段类型
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eam/micro/order/person/deleteByOrderAndFieldType")
    public void deleteOrderPersonByOrderIdAndFieldType(@RequestParam("orderId") String orderId, @RequestParam("fieldType") String fieldType) {
        Assert.isTrue(StringUtils.isNotEmpty(orderId), "工单编号不能为空！");
        Assert.isTrue(StringUtils.isNotEmpty(fieldType), "字段类型不能为空！");
        orderPersonService.deleteOrderPersonByOrderIdAndFieldType(orderId, fieldType);
    }

    /**
     * 根据工单编号以及字段类型删除
     * @param orderId       工单编号
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eam/micro/order/person/deleteByOrder")
    public void deleteOrderPersonByOrderId(@RequestParam("orderId") String orderId) {
        Assert.isTrue(StringUtils.isNotEmpty(orderId), "工单编号不能为空！");
        orderPersonService.deleteOrderPersonByOrderId(orderId);
    }

    /**
     * 新增工单关联人员列表
     * @param orderPersonVoList 新增人员列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/order/person/add")
    public void addOrderPerson(@RequestBody List<OrderPersonVo> orderPersonVoList) {
        for (OrderPersonVo orderPersonVo : orderPersonVoList) {
            Assert.isTrue(StringUtils.isNotEmpty(orderPersonVo.getOrderId()), "工单编号不能为空！");
            Assert.isTrue(StringUtils.isNotEmpty(orderPersonVo.getFieldType()), "字段类型不能为空！");
            Assert.isTrue(StringUtils.isNotEmpty(orderPersonVo.getPersonId()), "人员编号不能为空！");
        }
        orderPersonService.addOrderPerson(orderPersonVoList);
    }

    /**
     * 更新工单关联人员列表(先删除，再新增)
     * @param orderPersonVoList 所有人员列表
     * @return List<OrderPersonVo> 修改前的原始数据，方便回滚操作
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/order/person/update")
    public List<OrderPersonVo> updateOrderPersonByOrderIdAndFieldType(@RequestBody List<OrderPersonVo> orderPersonVoList) {
        for (OrderPersonVo orderPersonVo : orderPersonVoList) {
            Assert.isTrue(StringUtils.isNotEmpty(orderPersonVo.getOrderId()), "工单编号不能为空！");
            Assert.isTrue(StringUtils.isNotEmpty(orderPersonVo.getFieldType()), "字段类型不能为空！");
            //Assert.isTrue(StringUtils.isNotEmpty(orderPersonVo.getPersonId()), "人员编号不能为空！");
        }
        return orderPersonService.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
    }

}
