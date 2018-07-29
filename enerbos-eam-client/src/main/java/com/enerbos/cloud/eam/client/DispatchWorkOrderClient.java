package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-24 10:38
 * @Description EAM派工工单Client
 */
@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallbackFactory = DispatchWorkOrderClientFallback.class)
public interface DispatchWorkOrderClient {

    /**
     * 派工工单-查询
     *
     * @param id 工单编号
     * @return DispatchWorkOrderFlowVo 流程Vo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/dispatch/order/{id}")
    DispatchWorkOrderFlowVo findDispatchWorkOrderFlowVoById(@PathVariable("id") String id);

    /**
     * 派工工单-查询ID列表
     *
     * @param ids 工单ID列表
     * @return List&lt;DispatchWorkOrderFlowVo&gt; 派工工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/findByIds")
    List<DispatchWorkOrderFlowVo> findDispatchWorkOrderFlowVoByIds(@RequestBody String[] ids);

    /**
     * 分页查询派工工单
     *
     * @param dispatchWorkOrderListFilterVo 查询条件
     * @return EnerbosPage&lt;DispatchWorkOrderFlowVo&gt; 派工工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/page")
    EnerbosPage<DispatchWorkOrderListVo> findOrderListByPage(@RequestBody DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo);

    /**
     * 派工工单-保存&更新
     *
     * @param dispatchWorkOrderFlowVo {@link com.enerbos.cloud.eam.vo.DispatchWorkOrderFlowVo} 派工工单-流程VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/createOrUpdate")
    DispatchWorkOrderFlowVo createOrUpdateDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo);

    /**
     * 派工工单-保存
     *
     * @param dispatchWorkOrderFlowVo {@link com.enerbos.cloud.eam.vo.DispatchWorkOrderFlowVo} 派工工单-流程VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/save")
    DispatchWorkOrderFlowVo saveDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo);

    /**
     * 派工工单-提交流程
     *
     * @param dispatchWorkOrderFlowVo {@link com.enerbos.cloud.eam.vo.DispatchWorkOrderFlowVo} 派工工单-流程VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/commit")
    DispatchWorkOrderFlowVo commitDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo);

    /**
     * 收藏工单
     *
     * @param dispatchWorkOrderRfCollectorVoList 收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/collect")
    void collectDispatchWorkOrder(@RequestBody List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList);

    /**
     * 取消收藏工单
     *
     * @param dispatchWorkOrderRfCollectorVoList 需要取消收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/collect/cancel")
    void cancelCollectDispatchWorkOrder(@RequestBody List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList);

    /**
     * 派工工单-状态变更
     *
     * @param paramsMap 参数map
     *                  <p>key: ids       value: List&lt;String&gt;</p>
     *                  <p>key: status  value: String</p>
     * @return 原始工单状态
     */
    @RequestMapping(value = "/eam/micro/dispatch/order/flow/change", method = RequestMethod.POST)
    Map<String, String> changeDispatchWorkOrderStatus(@RequestBody Map<String, Object> paramsMap);

    /**
     * 派工工单-批量删除
     *
     * @param ids 派工工单ID列表
     */
    @RequestMapping(value = "/eam/micro/dispatch/order", method = RequestMethod.DELETE)
    void deleteDispatchWorkOrder(@RequestBody List<String> ids);


    /**
     * 查询总部工单数
     *
     * @param orgId     组织id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/dispatch/findDispatchWorkOrderTotal", method = RequestMethod.GET)
    OrderCountBySiteVo findDispatchWorkOrderTotal(@RequestParam("orgId") String orgId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate);

    /**
     * 查询最大派工工单数
     *
     * @param orgId     组织
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/dispatch/findMaxCountOrder", method = RequestMethod.GET)
    OrderMaxCountVo findMaxCountOrder(@RequestParam("orgId")String orgId, @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate);
}

@Component
class DispatchWorkOrderClientFallback implements FallbackFactory<DispatchWorkOrderClient> {
    @Override
    public DispatchWorkOrderClient create(Throwable throwable) {
        return new DispatchWorkOrderClient() {
            @Override
            public DispatchWorkOrderFlowVo findDispatchWorkOrderFlowVoById(@PathVariable("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List<DispatchWorkOrderFlowVo> findDispatchWorkOrderFlowVoByIds(@RequestBody String[] ids) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public EnerbosPage<DispatchWorkOrderListVo> findOrderListByPage(@RequestBody DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public DispatchWorkOrderFlowVo createOrUpdateDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public DispatchWorkOrderFlowVo saveDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public DispatchWorkOrderFlowVo commitDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void collectDispatchWorkOrder(@RequestBody List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void cancelCollectDispatchWorkOrder(@RequestBody List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public Map<String, String> changeDispatchWorkOrderStatus(@RequestBody Map<String, Object> paramsMap) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void deleteDispatchWorkOrder(@RequestBody List<String> ids) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public OrderCountBySiteVo findDispatchWorkOrderTotal(String orgId, Date startDate, Date endDate) {
                return null;
            }

            @Override
            public OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate) {
                return null;
            }
        };
    }
}
