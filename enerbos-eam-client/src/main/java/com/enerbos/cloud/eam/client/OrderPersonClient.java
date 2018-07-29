package com.enerbos.cloud.eam.client;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-17 10:46
 * @Description EAM工单关联人员Client
 */

import com.enerbos.cloud.eam.vo.OrderPersonVo;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallbackFactory = OrderPersonClientFallback.class)
public interface OrderPersonClient {

    /**
     * 查询工单关联的人员列表
     * @param orderPersonVo {@link com.enerbos.cloud.eam.vo.OrderPersonVo} 查询条件
     * @return List<OrderPersonVo> 工单人员列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/order/person/list")
    List<OrderPersonVo> findOrderListByFilter(@RequestBody OrderPersonVo orderPersonVo);

    /**
     * 根据ID删除
     * @param id ID
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eam/micro/order/person/deleteById")
    void deleteOrderPersonById(@RequestParam("id") String id);

    /**
     * 根据工单编号以及字段类型删除
     * @param orderId       工单编号
     * @param fieldType    字段类型
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eam/micro/order/person/deleteByOrderAndFieldType")
    void deleteOrderPersonByOrderIdAndFieldType(@RequestParam("orderId") String orderId, @RequestParam("fieldType") String fieldType);

    /**
     * 根据工单编号删除
     * @param orderId       工单编号
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eam/micro/order/person/deleteByOrder")
    void deleteOrderPersonByOrderId(@RequestParam("orderId") String orderId);

    /**
     * 新增工单关联人员列表
     * @param orderPersonVoList 新增人员列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/order/person/add")
    void addOrderPerson(@RequestBody List<OrderPersonVo> orderPersonVoList);

    /**
     * 更新工单关联人员列表(先删除，再新增)
     * @param orderPersonVoList 所有人员列表
     * @return List<OrderPersonVo> 修改前的原始数据，方便回滚操作
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/order/person/update")
    List<OrderPersonVo> updateOrderPersonByOrderIdAndFieldType(@RequestBody List<OrderPersonVo> orderPersonVoList);

}

@Component
class OrderPersonClientFallback implements FallbackFactory<OrderPersonClient> {
    @Override
    public OrderPersonClient create(Throwable throwable) {
        return new OrderPersonClient() {
            @Override
            public List<OrderPersonVo> findOrderListByFilter(@RequestBody OrderPersonVo orderPersonVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void deleteOrderPersonById(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void deleteOrderPersonByOrderIdAndFieldType(@RequestParam("orderId") String orderId, @RequestParam("fieldType") String fieldType) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void deleteOrderPersonByOrderId(@RequestParam("orderId") String orderId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void addOrderPerson(@RequestBody List<OrderPersonVo> orderPersonVoList) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List<OrderPersonVo> updateOrderPersonByOrderIdAndFieldType(@RequestBody List<OrderPersonVo> orderPersonVoList) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}

