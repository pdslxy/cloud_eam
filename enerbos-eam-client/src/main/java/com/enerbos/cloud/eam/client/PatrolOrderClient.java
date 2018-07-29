package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/7
 * @Description
 */
@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallbackFactory = PatrolOrderClientFallback.class)
public interface PatrolOrderClient {
    /**
     * 根据过滤条和分页信息获取巡检路线列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findPage", method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<PatrolOrderVo> findPatrolOrderList(@RequestBody PatrolOrderVoForFilter patrolOrderVoForFilter);

    /**
     * 删除巡检路线
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public abstract String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids);

    /**
     * 新增或更新巡检路线
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public abstract PatrolOrderVo saveOrUpdate(@RequestBody PatrolOrderForSaveVo patrolOrderForSaveVo);

    /**
     * findPointAndTermById:根据ID查询巡检工单
     *
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findPatrolOrderVoById", method = RequestMethod.GET)
    @ResponseBody
    PatrolOrderVo findPatrolOrderVoById(@RequestParam(value = "id", required = true) String id);

    @RequestMapping(value = "/eam/micro/patrolOrder/findPatrolOrderWorkFlowById", method = RequestMethod.GET)
    PatrolOrderForWorkFlowVo findPatrolOrderWorkFlowById(@RequestParam("id") String id);


    @RequestMapping(value = "/eam/micro/patrolOrder/savePatrolOrderFlow", method = RequestMethod.POST)
    PatrolOrderForWorkFlowVo savePatrolOrderFlow(@RequestBody PatrolOrderForWorkFlowVo patrolOrderForWorkFlowVo);

    @RequestMapping(value = "/eam/micro/patrolOrder/findOrderPointById", method = RequestMethod.GET)
    EnerbosPage<PatrolPointVo> findOrderPointById(@RequestParam("id") String id);

    @RequestMapping(value = "/eam/micro/patrolOrder/findPatrolOrderTotal", method = RequestMethod.GET)
    OrderCountBySiteVo findPatrolOrderTotal(@RequestParam("orgId") String orgId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate);

    /**
     * 查询最大巡检工单数
     *
     * @param orgId     组织
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findMaxCountOrder", method = RequestMethod.GET)
    OrderMaxCountVo findMaxCountOrder(@RequestParam("orgId")String orgId, @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate);


    @RequestMapping(value = "/eam/micro/patrolOrder/updateIsBeginPatrolById", method = RequestMethod.GET)
    public boolean updateIsBeginPatrolById(@RequestParam(value = "id", required = true) String id,
                                           @RequestParam(value = "isBeginPatrol", required = false) boolean isBeginPatrol,
                                           @RequestParam(value = "beginPatrolDate", required = false) Date beginPatrolDate);
}

@Component
class PatrolOrderClientFallback implements FallbackFactory<PatrolOrderClient> {

    @Override
    public PatrolOrderClient create(Throwable throwable) {
        return new PatrolOrderClient() {
            @Override
            public EnerbosPage<PatrolOrderVo> findPatrolOrderList(@RequestBody PatrolOrderVoForFilter patrolOrderVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public PatrolOrderVo saveOrUpdate(@RequestBody PatrolOrderForSaveVo patrolOrderForSaveVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public PatrolOrderVo findPatrolOrderVoById(@RequestParam(value = "id", required = true) String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public PatrolOrderForWorkFlowVo findPatrolOrderWorkFlowById(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public PatrolOrderForWorkFlowVo savePatrolOrderFlow(@RequestBody PatrolOrderForWorkFlowVo patrolOrderForWorkFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public EnerbosPage<PatrolPointVo> findOrderPointById(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public OrderCountBySiteVo findPatrolOrderTotal(String orgId, Date startDate, Date endDate) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public boolean updateIsBeginPatrolById(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "isBeginPatrol", required = false) boolean isBeginPatrol, @RequestParam(value = "beginPatrolDate", required = false) Date beginPatrolDate) {
                throw new RuntimeException(throwable.getMessage());
            }

        };
    }
}