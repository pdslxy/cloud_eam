package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrder;
import com.enerbos.cloud.eam.microservice.service.DispatchWorkOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-24 10:32
 * @Description EAM派工工单相关服务
 */
@RestController
public class DispatchWorkOrderController {
    private static Logger logger = LoggerFactory.getLogger(DispatchWorkOrderController.class);

    @Autowired
    private DispatchWorkOrderService dispatchWorkOrderService;

    /**
     * 派工工单-查询
     *
     * @param id 工单编号
     * @return DispatchWorkOrderFlowVo 流程Vo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/dispatch/order/{id}")
    public DispatchWorkOrderFlowVo findDispatchWorkOrderFlowVoById(@PathVariable("id") String id) {
        return dispatchWorkOrderService.findDispatchWorkOrderFlowVoById(id);
    }

    /**
     * 派工工单-查询ID列表
     *
     * @param ids 工单ID列表
     * @return List&lt;DispatchWorkOrderFlowVo&gt; 派工工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/findByIds")
    public List<DispatchWorkOrderFlowVo> findDispatchWorkOrderFlowVoByIds(@RequestBody String[] ids) {
        return dispatchWorkOrderService.findDispatchWorkOrderFlowVoByIdList(ids);
    }

    /**
     * 分页查询派工工单
     *
     * @param dispatchWorkOrderListFilterVo 查询条件
     * @return PageInfo<DispatchWorkOrderListVo> 派工工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/page")
    public PageInfo<DispatchWorkOrderListVo> findOrderListByPage(@RequestBody DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo) {
        //不捕获异常，如有异常直接向外层扔出
        List<DispatchWorkOrderListVo> result = dispatchWorkOrderService.findListByFilter(dispatchWorkOrderListFilterVo);
        return new PageInfo<>(result);
    }

    /**
     * 派工工单-保存&更新
     *
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/createOrUpdate")
    public DispatchWorkOrderFlowVo createOrUpdateDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        //不捕获异常，如有异常直接向外层扔出
        dispatchWorkOrderService.saveOrUpdate(dispatchWorkOrderFlowVo);
        return dispatchWorkOrderFlowVo;
    }

    /**
     * 保存派工工单
     *
     * @param dispatchWorkOrderFlowVo 派工工单数据VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/save")
    public DispatchWorkOrderFlowVo saveDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        DispatchWorkOrderFlowVo result;
        try {
            DispatchWorkOrder dispatchWorkOrder;
            if (StringUtils.isNoneEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())) {
                dispatchWorkOrder = dispatchWorkOrderService.findOne(dispatchWorkOrderFlowVo.getWorkOrderId());
                Assert.notNull(dispatchWorkOrder, "工单未知！");
            } else {
                dispatchWorkOrder = new DispatchWorkOrder();
            }

            ReflectionUtils.copyProperties(dispatchWorkOrderFlowVo, dispatchWorkOrder, Arrays.asList("createUser", "createDate"));
            dispatchWorkOrder.setId(dispatchWorkOrderFlowVo.getWorkOrderId());

            //不捕获异常，如有异常直接向外层扔出
            dispatchWorkOrder = dispatchWorkOrderService.save(dispatchWorkOrder);
            result = new DispatchWorkOrderFlowVo();
            ReflectionUtils.copyProperties(dispatchWorkOrder, result, null);
            result.setWorkOrderId(dispatchWorkOrder.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 派工工单-提交流程
     *
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return DispatchWorkOrderFlowVo 更新后的派工工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/commit")
    public DispatchWorkOrderFlowVo commitDispatchWorkOrder(@RequestBody DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getProcessStatus()), "请选择流程分支！");

        //不捕获异常，如有异常直接向外层扔出
        dispatchWorkOrderService.commitWorkOrder(dispatchWorkOrderFlowVo);
        return dispatchWorkOrderFlowVo;
    }

    /**
     * 收藏工单
     *
     * @param dispatchWorkOrderRfCollectorVoList 收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/collect")
    public void collectDispatchWorkOrder(@RequestBody List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList) {
        dispatchWorkOrderService.collectWorkOrder(dispatchWorkOrderRfCollectorVoList);
    }

    /**
     * 取消收藏工单
     *
     * @param dispatchWorkOrderRfCollectorVoList 需要取消收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/dispatch/order/collect/cancel")
    public void cancelCollectDispatchWorkOrder(@RequestBody List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList) {
        dispatchWorkOrderService.cancelCollectWorkOrder(dispatchWorkOrderRfCollectorVoList);
    }

    /**
     * 派工工单-状态变更
     *
     * @param paramsMap 参数map
     *                  <p>key: ids       value: List&lt;String&gt;</p>
     *                  <p>key: status  value: String</p>
     * @return 原始工单状态
     */
    @RequestMapping(value = "/eam/micro/dispatch/order/flow/change", method = RequestMethod.POST)
    public Map<String, String> changeDispatchWorkOrderStatus(@RequestBody Map<String, Object> paramsMap) {
        Assert.notNull(paramsMap, "参数获取失败！");

        List<String> ids = (List<String>) paramsMap.getOrDefault("ids", null);
        String status = (String) paramsMap.getOrDefault("status", null);

        Assert.notEmpty(ids, "请选择要变更状态的工单！");
        Assert.notNull(status, "状态不能为空！");

        return dispatchWorkOrderService.changeOrderStatus(ids, status);
    }

    /**
     * 派工工单-批量删除
     *
     * @param ids 派工工单ID列表
     */
    @RequestMapping(value = "/eam/micro/dispatch/order", method = RequestMethod.DELETE)
    public void deleteDispatchWorkOrder(@RequestBody List<String> ids) {
        Assert.notEmpty(ids, "请选择要删除的工单！");

        dispatchWorkOrderService.delete(ids);
    }

    /**
     * 查询工单总数
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/eam/micro/dispatch/findDispatchWorkOrderTotal", method = RequestMethod.GET)
    OrderCountBySiteVo findDispatchWorkOrderTotal(@RequestParam("orgId") String orgId, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

        return dispatchWorkOrderService.findDispatchWorkOrderTotal(param);
    }

    /**
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/eam/micro/dispatch/findMaxCountOrder", method = RequestMethod.GET)
    OrderMaxCountVo findMaxCountOrder(@RequestParam("orgId") String orgId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {

        return dispatchWorkOrderService.findMaxCountOrder(orgId,startDate,endDate);
    }

}
