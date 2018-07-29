package com.enerbos.cloud.eam.client;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-14 14:01
 * @Description EAM报修工单Client
 */

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallbackFactory = RepairOrderClientFallback.class)
public interface RepairOrderClient {

    /**
     * 根据ID查询报修工单
     *
     * @param id 报修工单ID
     * @return RepairOrderFlowVo 流程Vo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/repair/order/{id}")
    RepairOrderFlowVo findRepairOrderFlowVoById(@PathVariable("id") String id);

    /**
     * 根据ID列表查询报修工单
     *
     * @param ids 报修工单ID列表
     * @return List&lt;RepairOrderListVo&gt; 报修工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/findByIds")
    List<RepairOrderFlowVo> findRepairOrderFlowVoByIds(@RequestParam("ids") String[] ids);

    /**
     * findOrderListByPage: 分页查询报修工单
     *
     * @param repairOrderListFilterVo {@link com.enerbos.cloud.eam.vo.RepairOrderListFilterVo} 查询条件
     * @return EnerbosPage&lt;RepairOrderListVo&gt; 报修工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/page")
    EnerbosPage<RepairOrderListVo> findOrderListByPage(@RequestBody RepairOrderListFilterVo repairOrderListFilterVo);

    /**
     * createRepairOrder: 创建&更新报修工单
     *
     * @param repairOrderFlowVo {@link com.enerbos.cloud.eam.vo.RepairOrderFlowVo} 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/createOrUpdate")
    RepairOrderFlowVo createOrUpdateRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo);

    /**
     * 保存报修工单
     *
     * @param repairOrderFlowVo {@link com.enerbos.cloud.eam.vo.RepairOrderFlowVo} 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/save")
    RepairOrderFlowVo saveRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo);

    /**
     * reportRepairOrder: 发送流程
     *
     * @param repairOrderFlowVo {@link com.enerbos.cloud.eam.vo.RepairOrderFlowVo} 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/flow/report")
    RepairOrderFlowVo reportRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo);

    /**
     * 收藏工单
     *
     * @param repairOrderRfCollectors 收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/collect")
    void collectRepairOrder(@RequestBody List<RepairOrderRfCollectorVo> repairOrderRfCollectors);

    /**
     * 取消收藏工单
     *
     * @param repairOrderRfCollectors 需要取消收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/collect/cancel")
    void cancelCollectRepairOrder(@RequestBody List<RepairOrderRfCollectorVo> repairOrderRfCollectors);

    /**
     * 报修工单-状态变更
     *
     * @param paramsMap 参数map
     *                  <p>key: ids       value: List&lt;String&gt;</p>
     *                  <p>key: status  value: String</p>
     * @return 原始工单状态
     */
    @RequestMapping(value = "/eam/micro/repair/order/flow/change", method = RequestMethod.POST)
    Map<String, String> changeRepairOrderStatus(@RequestBody Map<String, Object> paramsMap);

    /**
     * 报修工单-批量删除
     *
     * @param ids 报修工单ID列表
     */
    @RequestMapping(value = "/eam/micro/repair/order", method = RequestMethod.DELETE)
    void deleteRepairOrder(@RequestBody List<String> ids);

    /**
     * 按月查询报修工单各专业的统计分析 （柱状图）
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findCountRepair", method = RequestMethod.GET)
    List findCountRepair(@RequestParam("siteId") String siteId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    /**
     * 按月查询未修复报修工单各专业的统计分析 （柱状图）
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findUndoneCountRepair", method = RequestMethod.GET)
    List findUndoneCountRepair(@RequestParam("siteId") String siteId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    /**
     * 报修小程序首页统计已完成，为分派，处理中，已评价的数据
     *
     * @param siteId 站点id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按月查询未修复报修工单各专业的统计分析", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/micro/repair/order/findCountRepairAndEvaluate", method = RequestMethod.GET)
    CountRepairAndEvaluateVo findCountRepairAndEvaluate(@RequestParam("siteId") String siteId, @RequestParam("personId") String personId);

    /**
     * 查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     *
     * @param orgId  组织ID
     * @param siteId 站点id
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findCountByStatus", method = RequestMethod.GET)
    public OrderCountBySiteVo findCountByStatus(@RequestParam("orgId") String orgId, @RequestParam(value = "siteId", required = false) String siteId,
                                                @RequestParam(value = "startDate", required = false) Date startDate,
                                                @RequestParam(value = "endDate", required = false) Date endDate);

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param siteId 站点id
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findCountAndRingratio", method = RequestMethod.GET)
    public OrderCountAndRingratio findCountAndRingratio(@RequestParam("siteId") String siteId);


    /**
     * 查询最大报修工单数
     *
     * @param orgId     组织
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/repair/order/findMaxCountOrder", method = RequestMethod.GET)
    public OrderMaxCountVo findMaxCountOrder(@RequestParam("orgId") String orgId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);
}

@Component
class RepairOrderClientFallback implements FallbackFactory<RepairOrderClient> {
    @Override
    public RepairOrderClient create(Throwable throwable) {
        return new RepairOrderClient() {
            @Override
            public RepairOrderFlowVo findRepairOrderFlowVoById(@PathVariable("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List<RepairOrderFlowVo> findRepairOrderFlowVoByIds(@RequestParam("ids") String[] ids) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public EnerbosPage<RepairOrderListVo> findOrderListByPage(RepairOrderListFilterVo repairOrderListFilterVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public RepairOrderFlowVo createOrUpdateRepairOrder(RepairOrderFlowVo repairOrderFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public RepairOrderFlowVo saveRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public RepairOrderFlowVo reportRepairOrder(RepairOrderFlowVo repairOrderFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void collectRepairOrder(@RequestBody List<RepairOrderRfCollectorVo> repairOrderRfCollectors) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void cancelCollectRepairOrder(@RequestBody List<RepairOrderRfCollectorVo> repairOrderRfCollectors) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public Map<String, String> changeRepairOrderStatus(@RequestBody Map<String, Object> paramsMap) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void deleteRepairOrder(@RequestBody List<String> ids) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List findCountRepair(String siteId, String startDate, String endDate) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List findUndoneCountRepair(String siteId, String startDate, String endDate) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public OrderCountBySiteVo findCountByStatus(String orgId, String siteId, Date startDate, Date endDate) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public OrderCountAndRingratio findCountAndRingratio(String siteId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate) {
                return null;
            }

            @Override
            public CountRepairAndEvaluateVo findCountRepairAndEvaluate(String siteId,String personId) {
                return null;
            }
        };
    }
}

