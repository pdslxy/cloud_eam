package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.microservice.domain.RepairOrder;
import com.enerbos.cloud.eam.microservice.service.RepairEvaluateService;
import com.enerbos.cloud.eam.microservice.service.RepairOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-11 18:18
 * @Description EAM报修工单相关服务
 */
@RestController
public class RepairOrderController {
    private static Logger logger = LoggerFactory.getLogger(RepairOrderController.class);

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private RepairEvaluateService repairEvaluateService;

    /**
     * 根据ID查询报修工单
     *
     * @param id 报修工单ID
     * @return 流程Vo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/repair/order/{id}")
    public RepairOrderFlowVo findRepairOrderFlowVoById(@PathVariable("id") String id) {
        return repairOrderService.findRepairOrderFlowVoById(id);
    }

    /**
     * 根据ID列表查询报修工单
     *
     * @param ids 报修工单ID列表
     * @return List&lt;RepairOrderFlowVo&gt; 报修工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/findByIds")
    public List<RepairOrderFlowVo> findRepairOrderFlowVoByIds(@RequestParam("ids") String[] ids) {
        return repairOrderService.findRepairOrderFlowVoByIdList(ids);
    }

    /**
     * findOrderListByPage: 分页查询报修工单
     *
     * @param repairOrderListFilterVo 查询条件
     * @return PageInfo&lt;RepairOrderListVo&gt; 报修工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/page")
    public PageInfo<RepairOrderListVo> findOrderListByPage(@RequestBody RepairOrderListFilterVo repairOrderListFilterVo) {
        logger.info("/eam/micro/repair/order/page, params: [{}]", repairOrderListFilterVo);

        //不捕获异常，如有异常直接向外层扔出
        List<RepairOrderListVo> result = repairOrderService.findListByFilter(repairOrderListFilterVo);
/*
        for (RepairOrderListVo repairOrderListVo : result) {
            RepairEvaluate repairEvaluate= repairEvaluateService.findRepairEvalueByOrderId(repairOrderListVo.getId());
            if(repairEvaluate!=null){
                repairOrderListVo.setEvaluate(true);
            }else{
                repairOrderListVo.setEvaluate(false);
            }
        }
*/

        return new PageInfo<>(result);
    }

    /**
     * createRepairOrder: 创建&更新报修工单
     *
     * @param repairOrderFlowVo 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/createOrUpdate")
    public RepairOrderFlowVo createOrUpdateRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo) {
        logger.info("/eam/micro/repair/order/save, params: [{}]", repairOrderFlowVo);

        //不捕获异常，如有异常直接向外层扔出
        repairOrderService.saveOrUpdate(repairOrderFlowVo);
        return repairOrderFlowVo;
    }

    /**
     * 保存报修工单
     *
     * @param repairOrderFlowVo 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/save")
    public RepairOrderFlowVo saveRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo) {
        RepairOrderFlowVo result;
        try {
            RepairOrder repairOrder;
            if (StringUtils.isNoneEmpty(repairOrderFlowVo.getWorkOrderId())) {
                repairOrder = repairOrderService.findOne(repairOrderFlowVo.getWorkOrderId());
                Assert.notNull(repairOrder, "工单未知！");
            } else {
                repairOrder = new RepairOrder();
            }
            ReflectionUtils.copyProperties(repairOrderFlowVo, repairOrder, Arrays.asList("createUser", "createDate"));
            repairOrder.setId(repairOrderFlowVo.getWorkOrderId());

            //不捕获异常，如有异常直接向外层扔出
            repairOrder = repairOrderService.save(repairOrder);
            result = new RepairOrderFlowVo();
            ReflectionUtils.copyProperties(repairOrder, result, null);
            result.setWorkOrderId(repairOrder.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * reportRepairOrder: 发送流程
     *
     * @param repairOrderFlowVo 报修工单数据VO
     * @return RepairOrderFlowVo 更新后的报修工单
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/flow/report")
    public RepairOrderFlowVo reportRepairOrder(@RequestBody RepairOrderFlowVo repairOrderFlowVo) {
        logger.info("/eam/micro/repair/order/flow/report, params: [{}]", repairOrderFlowVo);
        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getProcessStatus()), "请选择流程分支！");

        //不捕获异常，如有异常直接向外层扔出
        repairOrderFlowVo = repairOrderService.reportOrder(repairOrderFlowVo);
        return repairOrderFlowVo;
    }

    /**
     * 收藏工单
     *
     * @param repairOrderRfCollectorVoList 收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/collect")
    public void collectRepairOrder(@RequestBody List<RepairOrderRfCollectorVo> repairOrderRfCollectorVoList) {
        logger.info("/eam/micro/repair/order/collect, params: [{}]", repairOrderRfCollectorVoList);

        repairOrderService.collectWorkOrder(repairOrderRfCollectorVoList);
    }

    /**
     * 取消收藏工单
     *
     * @param repairOrderRfCollectorVoList 需要取消收藏的工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/repair/order/collect/cancel")
    public void cancelCollectRepairOrder(@RequestBody List<RepairOrderRfCollectorVo> repairOrderRfCollectorVoList) {
        logger.info("/eam/micro/repair/order/collect/cancel, params: [{}]", repairOrderRfCollectorVoList);

        repairOrderService.cancelCollectWorkOrder(repairOrderRfCollectorVoList);
    }

    /**
     * 报修工单-状态变更
     *
     * @param paramsMap 参数map
     *                  <p>key: ids       value: List&lt;String&gt;</p>
     *                  <p>key: status  value: String</p>
     * @return 原始工单状态
     */
    @RequestMapping(value = "/eam/micro/repair/order/flow/change", method = RequestMethod.POST)
    public Map<String, String> changeRepairOrderStatus(@RequestBody Map<String, Object> paramsMap) {
        Assert.notNull(paramsMap, "参数获取失败！");

        List<String> ids = (List<String>) paramsMap.getOrDefault("ids", null);
        String status = (String) paramsMap.getOrDefault("status", null);

        Assert.notEmpty(ids, "请选择要变更状态的工单！");
        Assert.notNull(status, "状态不能为空！");

        return repairOrderService.changeOrderStatus(ids, status);
    }

    /**
     * 报修工单-批量删除
     *
     * @param ids 报修工单ID列表
     */
    @RequestMapping(value = "/eam/micro/repair/order", method = RequestMethod.DELETE)
    public void deleteRepairOrder(@RequestBody List<String> ids) {
        Assert.notEmpty(ids, "请选择要删除的工单！");

        repairOrderService.delete(ids);
    }

    /**
     * 按月查询报修工单各专业的统计分析 （柱状图）
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findCountRepair", method = RequestMethod.GET)
    public List findCountRepair(@RequestParam("siteId") String siteId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
//        OrderCountVo orderCountVo=new OrderCountVo();
        List<Object> list = new ArrayList<>();
        list = repairOrderService.findCountRepair(startDate, endDate, siteId);

//            if(list.size()>0){
//                List listme = new ArrayList<>();
//                List listyxf = new ArrayList<>();
//                List listwxf = new ArrayList<>();
//                for(int i=0;i<list.size();i++){
//                    Object[] itemArray = (Object[]) list.get(i);
//                    listme.add(itemArray[0].toString());
//                    listyxf.add(itemArray[1].toString());
//                    listwxf.add(itemArray[2].toString());
//                }
//                orderCountVo.setListme(listme);
//                orderCountVo.setListwxf(listwxf);
//                orderCountVo.setListyxf(listyxf);
//            }
        return list;
    }

    /**
     * 按月查询未修复报修工单各专业的统计分析 （柱状图）
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param endDate   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findUndoneCountRepair", method = RequestMethod.GET)
    public List findUndoneCountRepair(@RequestParam("siteId") String siteId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
//        OrderCountVo orderCountVo = new OrderCountVo();
        List<Object> list = new ArrayList<>();
        list = repairOrderService.findUndoneCountRepair(startDate, endDate, siteId);
//        if (list.size() > 0) {
//            List listme = new ArrayList<>();
//            List listqt = new ArrayList<>();
//            List listwxf = new ArrayList<>();
//            List listgys = new ArrayList<>();
//            List listqbj = new ArrayList<>();
//            List listzxz = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                Object[] itemArray = (Object[]) list.get(i);
//                listme.add(itemArray[0].toString());
//                listwxf.add(itemArray[1].toString());
//                listgys.add(itemArray[2].toString());
//                listqbj.add(itemArray[3].toString());
//                listqt.add(itemArray[4].toString());
//                listzxz.add(Integer.toString(Integer.valueOf(itemArray[0].toString()) - Integer.valueOf(itemArray[4].toString()) - Integer.valueOf(itemArray[2].toString()) - Integer.valueOf(itemArray[3].toString())));
//            }
//            orderCountVo.setListme(listme);
//            orderCountVo.setListqt(listqt);
//            orderCountVo.setListwxf(listwxf);
//            orderCountVo.setListgys(listgys);
//            orderCountVo.setListqbj(listqbj);
//            orderCountVo.setListzxz(listzxz);
//        }
        return list;
    }


    /**
     * 报修小程序首页统计已完成，为分派，处理中，已评价的数据
     *
     * @param siteId 站点id
     * @return 查询结果
     */
    @ApiOperation(value = "按月查询未修复报修工单各专业的统计分析", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/micro/repair/order/findCountRepairAndEvaluate", method = RequestMethod.GET)
    public CountRepairAndEvaluateVo findCountRepairAndEvaluate(@RequestParam("siteId") String siteId, @RequestParam("personId") String personId) {
        CountRepairAndEvaluateVo countRepairAndEvaluateVo = new CountRepairAndEvaluateVo();

        Map<String, String> map = new HashMap<>();
        map.put("siteId", siteId);
        map.put("personId", personId);
        Map<String, Object> repairOrder = repairOrderService.findCountRepairAndEvaluate(map);
        Map<String, Object> repairEvaluate = repairEvaluateService.findCountRepairAndEvaluate(map);
        countRepairAndEvaluateVo.setYwc(repairOrder.get("ywc").toString());
        countRepairAndEvaluateVo.setClz(repairOrder.get("clz").toString());
        countRepairAndEvaluateVo.setWfp(repairOrder.get("wfp").toString());
        countRepairAndEvaluateVo.setYpj(repairEvaluate.get("ypj").toString());

        return countRepairAndEvaluateVo;
    }

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
                                                @RequestParam(value = "endDate", required = false) Date endDate) {
        OrderCountBySiteVo repair = repairOrderService.findCountByStatus(orgId, siteId, startDate, endDate);
        return repair;
    }

    /**
     * 今日工单总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param siteId 站点id
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findCountAndRingratio", method = RequestMethod.GET)
    public OrderCountAndRingratio findCountAndRingratio(@RequestParam("siteId") String siteId) {
        OrderCountAndRingratio orderCountAndRingratio = new OrderCountAndRingratio();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String today = formatter.format(currentTime);
        Map<String, String> map = new HashMap<>();
        map.put("siteId", siteId);
        map.put("createDate", today);
        Map<String, Object> repairOrder = repairOrderService.findCountAndRingratio(map);
        orderCountAndRingratio.setTotal(repairOrder.get("total").toString());
        orderCountAndRingratio.setWwc(repairOrder.get("wwc").toString());
        orderCountAndRingratio.setYwc(repairOrder.get("ywc").toString());
        List numList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(System.currentTimeMillis());
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -i);
            date = calendar.getTime();
            String todayOfLast = formatter.format(date);
            Map<String, String> map1 = new HashMap<>();
            map1.put("siteId", siteId);
            map1.put("createDate", todayOfLast);
            Map<String, Object> repairOrderFor = repairOrderService.findCountAndRingratio(map1);
            numList.add(repairOrderFor.get("total").toString());
        }

//        List ringList=new ArrayList<>();
//        for(int j=0;j<numList.size();j++){
//            if(!orderCountAndRingratio.getTotal().equals("0")) {
//                ringList.add(Integer.toString(Integer.valueOf(numList.get(j).toString()) / Integer.valueOf(orderCountAndRingratio.getTotal())));
//            }
//            else{
//                ringList.add("0");
//            }
//        }

        orderCountAndRingratio.setList(numList);
        return orderCountAndRingratio;
    }

    /**
     * 查询报修工单总数
     *
     * @param orgId 组织ID
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/repair/order/findMaxCountOrder", method = RequestMethod.GET)
    public OrderMaxCountVo findMaxCountOrder(@RequestParam("orgId") String orgId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return repairOrderService.findMaxCountOrder(orgId, startDate, endDate);
    }

}
