package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.DispatchWorkOrderClient;
import com.enerbos.cloud.eam.client.OrderPersonClient;
import com.enerbos.cloud.eam.client.UserGroupDomainClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DefectCommon;
import com.enerbos.cloud.eam.contants.DispatchWorkOrderCommon;
import com.enerbos.cloud.eam.contants.HeadquartersDailyTaskCommon;
import com.enerbos.cloud.eam.openservice.service.PushService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForList;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.ParamConstans;
import com.enerbos.cloud.util.ParamUtils;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.util.ReflectionUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-24 10:43
 * @Description EAM派工工单相关服务
 */
@Api(description = "派工工单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
@RestController
public class DispatchWorkOrderController {
    private static Logger logger = LoggerFactory.getLogger(DispatchWorkOrderController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private OrderPersonClient orderPersonClient;

    @Autowired
    private DispatchWorkOrderClient dispatchWorkOrderClient;

    @Autowired
    private WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;



    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private PushService pushService;

    /**
     * 分页查询派工工单
     * @param dispatchWorkOrderListFilterVo 查询条件
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询派工工单", response = DispatchWorkOrderListVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/page", method = RequestMethod.POST)
    public EnerbosMessage findPageWorkOrderList(@ApiParam(value = "派工工单列表过滤条件VO", required = true) DispatchWorkOrderListFilterVo dispatchWorkOrderListFilterVo, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/page, host: [{}:{}], service_id: {}, DispatchWorkOrderListFilterVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), dispatchWorkOrderListFilterVo, user);
            if (Objects.isNull(dispatchWorkOrderListFilterVo)) {
                dispatchWorkOrderListFilterVo = new DispatchWorkOrderListFilterVo();
            }
            //放入实际执行人&验收人分类编码
            dispatchWorkOrderListFilterVo.setActualExecutionPersonFieldType(DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION);
            dispatchWorkOrderListFilterVo.setAcceptPersonFieldType(DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR);

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
            Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
            //设置查询人，用于获取关注信息
            dispatchWorkOrderListFilterVo.setPersonId(personId);
            //前台未提交组织站点信息时，将不展示内容
            if (StringUtils.isEmpty(dispatchWorkOrderListFilterVo.getOrgId())) {
                dispatchWorkOrderListFilterVo.setOrgId("");
            }
            if (StringUtils.isEmpty(dispatchWorkOrderListFilterVo.getSiteId())) {
                dispatchWorkOrderListFilterVo.setSiteId("");
            }

            //当前台不勾选"只显示收藏"时，显示所有内容，而不是显示未收藏工单
            if (Objects.nonNull(dispatchWorkOrderListFilterVo.getCollect()) && !dispatchWorkOrderListFilterVo.getCollect()) {
                dispatchWorkOrderListFilterVo.setCollect(null);
            }
            EnerbosPage<DispatchWorkOrderListVo> page = dispatchWorkOrderClient.findOrderListByPage(dispatchWorkOrderListFilterVo);
            page.getList().forEach(vo -> {
                if (StringUtils.isNotEmpty(vo.getReportPersonId())) {
                    PersonAndUserVoForDetail getPerson = personAndUserClient.findByPersonId(vo.getReportPersonId());
                    if (getPerson != null) {
                        vo.setReportPerson(getPerson.getName());
                    }
                }
            });
            return EnerbosMessage.createSuccessMsg(page, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/page");
        }
    }

    /**
     * 派工工单-查询
     * @param workOrderId 工单编号
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-查询", response = DispatchWorkOrderFlowVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order", method = RequestMethod.GET)
    public EnerbosMessage findDispatchWorkOrder(@ApiParam(value = "报修工单ID", required = true) @RequestParam("id") String workOrderId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order, host: [{}:{}], service_id: {}, workOrderId: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), workOrderId, user);
            Assert.notNull(workOrderId, "工单编号不能为空！");
            DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo = dispatchWorkOrderClient.findDispatchWorkOrderFlowVoById(workOrderId);
            Assert.notNull(dispatchWorkOrderFlowVo, "未知工单！");
            List<PersonAndUserVoForList> person=this.getDispatchWorkOrderManageUserList(dispatchWorkOrderFlowVo.getOrgId(),dispatchWorkOrderFlowVo.getSiteId());
            String  personIdJoin=null;
            String  personNameJoin=null;
              if(person!=null&&person.size()>0){
                  personIdJoin =  person.stream().map(PersonAndUserVoForList::getPersonId).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();
                 personNameJoin=  person.stream().map(PersonAndUserVoForList::getName).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();
              }
            dispatchWorkOrderFlowVo.setDispatchGroupPersonId(personIdJoin);
            dispatchWorkOrderFlowVo.setDispatchGroupPersonName(personNameJoin);
            //==================== build vo ========================
            buildDispatchWorkOrderFlowVo(dispatchWorkOrderFlowVo);

            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowVo, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order");
        }
    }

    /**
     * 派工工单-保存&更新
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-保存&更新", response = DispatchWorkOrderFlowVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/save", method = RequestMethod.POST)
    public EnerbosMessage saveDispatchWorkOrder(@ApiParam(value = "派工工单-流程VO", required = true) DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, Principal user) {
        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/save, host: [{}:{}], service_id: {}, DispatchWorkOrderFlowVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), dispatchWorkOrderFlowVo, user);

            final String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            //记录当前工单操作人信息
            dispatchWorkOrderFlowVo.setOperatorPersonId(personId);
            dispatchWorkOrderFlowVo.setOperatorPerson(user.getName());

            if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())) {
                Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderStatus()), "工单状态不能为空！");
                DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo = dispatchWorkOrderClient.findDispatchWorkOrderFlowVoById(dispatchWorkOrderFlowVo.getWorkOrderId());
                if (Objects.isNull(oldDispatchWorkOrderFlowVo)) {
                    return EnerbosMessage.createErrorMsg("500", "未知工单!!", "");
                }

                if (!oldDispatchWorkOrderFlowVo.getWorkOrderNum().equals(dispatchWorkOrderFlowVo.getWorkOrderNum())) {
                    return EnerbosMessage.createErrorMsg("500", "工单编号不匹配！", "");
                }

                if (!oldDispatchWorkOrderFlowVo.getWorkOrderStatus().equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())) {
                    return EnerbosMessage.createErrorMsg("500", "工单状态不匹配！", "");
                }
                switch (dispatchWorkOrderFlowVo.getWorkOrderStatus()) {
                    //待提报分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB: break;
                    //待分派分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP: {
                        //查询用户组主管列表
                        List<String> personList = new ArrayList<>();
                        if (!getDispatchWorkOrderManageUserList(oldDispatchWorkOrderFlowVo, personList)) {
                            return EnerbosMessage.createErrorMsg("500", "派工工单分派组下没有人员,请联系管理员添加!!", "");
                        }

                        if (personList.stream().noneMatch(personId::equals)) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }

                        //更新执行人数据
                        if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getExecutionPersonId())) {
                            String[] executionPersonIdArray =  dispatchWorkOrderFlowVo.getExecutionPersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : executionPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "未知执行人！");
                                orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION);
                        }
                    } break;
                    //待汇报分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB: {
                        List<OrderPersonVo> personList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION));

                        if (personList.stream().noneMatch(vo -> personId.equals(vo.getPersonId()))) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }

                        //更新实际执行人数据
                        if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getActualExecutionPersonId())) {
                            String[] actualExecutionPersonIdArray =  dispatchWorkOrderFlowVo.getActualExecutionPersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : actualExecutionPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "实际执行人未知！");
                                orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION);
                        }
                    } break;
                    //待验收分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS: {
                        if (!oldDispatchWorkOrderFlowVo.getReportPersonId().equals(dispatchWorkOrderFlowVo.getOperatorPersonId())) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }

                        //更新验收人数据
                        if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getAcceptPersonId())) {
                            String[] acceptPersonIdArray =  dispatchWorkOrderFlowVo.getAcceptPersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : acceptPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "未知验收人！");
                                orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR);
                        }
                    } break;
                    default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrderFlowVo.getWorkOrderStatus()));
                }
            }

            dispatchWorkOrderFlowVo = dispatchWorkOrderClient.createOrUpdateDispatchWorkOrder(dispatchWorkOrderFlowVo);
            //更新Vo展示内容
            buildDispatchWorkOrderFlowVo(dispatchWorkOrderFlowVo);
            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowVo, "工单保存成功", "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            return buildErrorMsg(e, "/eam/open/dispatch/order/save");
        }
    }

    /**
     * 派工工单-提交流程
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-提交流程", response = DispatchWorkOrderFlowVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/flow/commit", method = RequestMethod.POST)
    public EnerbosMessage commitDispatchWorkOrderFlow(@ApiParam(value = "派工工单-流程VO", required = true) DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/flow/commit, host: [{}:{}], service_id: {}, DispatchWorkOrderFlowVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), dispatchWorkOrderFlowVo, user);

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            //记录当前工单操作人信息
            dispatchWorkOrderFlowVo.setOperatorPersonId(personId);
            dispatchWorkOrderFlowVo.setOperatorPerson(user.getName());

            EnerbosMessage message;
            if (StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())) {
                Assert.isTrue(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderStatus())
                        || DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB.equals(dispatchWorkOrderFlowVo.getWorkOrderStatus()), "未知工单！");

                //如果没有工单状态并且没有工单编号，允许进入提交环节
                message = createDispatchWorkOrderFlow(dispatchWorkOrderFlowVo);
            } else {
                Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderStatus()), "工单状态不能为空！");
                DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo = dispatchWorkOrderClient.findDispatchWorkOrderFlowVoById(dispatchWorkOrderFlowVo.getWorkOrderId());
                if (Objects.isNull(oldDispatchWorkOrderFlowVo)) {
                    return EnerbosMessage.createErrorMsg("500", "未知工单!!", "");
                }

                if (!oldDispatchWorkOrderFlowVo.getWorkOrderNum().equals(dispatchWorkOrderFlowVo.getWorkOrderNum())) {
                    return EnerbosMessage.createErrorMsg("500", "工单编号不匹配！", "");
                }

                if (!oldDispatchWorkOrderFlowVo.getWorkOrderStatus().equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())) {
                    return EnerbosMessage.createErrorMsg("500", "工单状态不匹配！", "");
                }
                switch (dispatchWorkOrderFlowVo.getWorkOrderStatus()) {
                    //待提报分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB: {
                        message = createDispatchWorkOrderFlow(dispatchWorkOrderFlowVo);
                    } break;
                    //待分派分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP: {
                        message = assignDispatchWorkOrderFlow(dispatchWorkOrderFlowVo, oldDispatchWorkOrderFlowVo);
                    } break;
                    //待汇报分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB: {
                        message = reportDispatchWorkOrderFlow(dispatchWorkOrderFlowVo, oldDispatchWorkOrderFlowVo);
                    } break;
                    //待验收分支
                    case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS: {
                        message = acceptDispatchWorkOrderFlow(dispatchWorkOrderFlowVo, oldDispatchWorkOrderFlowVo);
                    } break;
                    default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrderFlowVo.getWorkOrderStatus()));
                }
            }
            return message;
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/commit");
        }
    }

    /**
     * 派工工单-收藏
     * @param ids 派工工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/collect", method = RequestMethod.POST)
    public EnerbosMessage collectDispatchWorkOrder(@ApiParam(value = "派工工单ID列表", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

            Assert.notEmpty(ids, "请选择要收藏的工单！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList = Arrays.stream(ids).map(o -> new DispatchWorkOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
            dispatchWorkOrderClient.collectDispatchWorkOrder(dispatchWorkOrderRfCollectorVoList);

            return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/collect");
        }
    }

    /**
     * 派工工单-取消收藏
     * @param ids 派工工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-取消收藏", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/collect/cancel", method = RequestMethod.POST)
    public EnerbosMessage cancelCollectDispatchWorkOrder(@ApiParam(value = "派工工单ID列表", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/collect/cancel, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

            Assert.notEmpty(ids, "请选择要取消收藏的工单！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<DispatchWorkOrderRfCollectorVo> dispatchWorkOrderRfCollectorVoList = Arrays.stream(ids).map(o -> new DispatchWorkOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
            dispatchWorkOrderClient.cancelCollectDispatchWorkOrder(dispatchWorkOrderRfCollectorVoList);
            return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/collect/cancel");
        }
    }

    /**
     * 派工工单-状态变更
     * @param ids 派工工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-状态变更", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/flow/change", method = RequestMethod.POST)
    public EnerbosMessage changeDispatchWorkOrderStatus(@ApiParam(value = "派工工单ID列表", required = true) String[] ids, @ApiParam(value = "工单状态", required = true) String status, @ApiParam(value = "变更描述", required = false) String description, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/flow/change, host: [{}:{}], service_id: {}, workOrderIds: {}, Status: {}, Description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status, description, user);

            Assert.notEmpty(ids, "请选择要变更状态的工单！");
            Assert.isTrue(StringUtils.isNotEmpty(status), "请选择要变更的状态！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            boolean closeFlow = false;
            switch (status) {
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB: break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP: break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB: break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS: break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_CANCEL:
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_CLOSE: {
                    closeFlow = true;
                } break;
                default: throw new EnerbosException("500", String.format("未支持的状态：[%s]", status));
            }

            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("ids", ids);
            paramsMap.put("status", status);
            Map<String, String> oldStatusMap = dispatchWorkOrderClient.changeDispatchWorkOrderStatus(paramsMap);

            if (closeFlow) {
                DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo;
                Map<String, Object> variables = new HashMap<>();
                for (String id : ids) {
                    dispatchWorkOrderFlowVo = dispatchWorkOrderClient.findDispatchWorkOrderFlowVoById(id);
                    if (Objects.isNull(dispatchWorkOrderFlowVo)){
                        continue;
                    }

                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                    variables.put("userId", personId);
                    variables.put("status", dispatchWorkOrderFlowVo.getWorkOrderStatus());
                    variables.put("description", StringUtils.isNotEmpty(description) ? description : "管理员取消");

                    try {
                        //更新流程进度
                        Boolean processMessage = processTaskClient.completeByProcessInstanceId(dispatchWorkOrderFlowVo.getProcessInstanceId(), variables);
                        if (Objects.isNull(processMessage) || !processMessage) {
                            throw new EnerbosException("500", "流程操作异常。");
                        }
                    } catch (Exception e) {
                        Map<String, List<String>> statusIdListMap = oldStatusMap.entrySet().stream().collect(Collectors.groupingBy((Map.Entry::getValue), Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
                        statusIdListMap.entrySet().forEach(entry -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("ids", entry.getValue().toArray(new String[entry.getValue().size()]));
                            map.put("status", entry.getKey());
                            dispatchWorkOrderClient.changeDispatchWorkOrderStatus(map);
                        });

                        throw e;
                    }
                }
            }
            return EnerbosMessage.createSuccessMsg("", "变更成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/change");
        }
    }

    /**
     * 派工工单-批量删除
     * @param ids 派工工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-批量删除", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/delete", method = RequestMethod.POST)
    public EnerbosMessage deleteDispatchWorkOrder(@ApiParam(value = "派工工单ID列表", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);
            Assert.notEmpty(ids, "请选择要删除的工单！");

            DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo;
            List<String> delList = new ArrayList<>();
            List<DispatchWorkOrderFlowVo> needCancelFlowList = new ArrayList<>();
            for (String id : ids) {
                dispatchWorkOrderFlowVo = dispatchWorkOrderClient.findDispatchWorkOrderFlowVoById(id);

                if (dispatchWorkOrderFlowVo == null) {
                    continue;
                }

                if (!DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB.equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())) {
                    throw new EnerbosException("500", String.format("指定工单已经提报，不允许删除！  当前状态：[%s]", dispatchWorkOrderFlowVo.getWorkOrderStatus()));
                }

                delList.add(dispatchWorkOrderFlowVo.getWorkOrderId());
                if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getProcessInstanceId())) {
                    needCancelFlowList.add(dispatchWorkOrderFlowVo);
                }
            }

            if (!delList.isEmpty()) {
                dispatchWorkOrderClient.deleteDispatchWorkOrder(delList);

                delList.forEach(orderPersonClient::deleteOrderPersonByOrderId);

                Map<String, Object> variables = new HashMap<>();
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                variables.put("description", "工单删除");

                for (DispatchWorkOrderFlowVo workOrderFlowVo : needCancelFlowList) {
                    variables.put("status", workOrderFlowVo.getWorkOrderStatus());
                    try {
                        //更新流程进度
                        Boolean processMessage = processTaskClient.completeByProcessInstanceId(workOrderFlowVo.getProcessInstanceId(), variables);
                        if (Objects.isNull(processMessage) || !processMessage) {
                            throw new EnerbosException("500", "流程操作异常。");
                        }
                    } catch (Exception e) {
                        logger.error("工单删除-流程结束异常 ", e);
                    }
                }
            }
            return EnerbosMessage.createSuccessMsg("", "删除成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order");
        }
    }

    /**
     * 派工工单-执行记录
     * @param processInstanceId 派工工单-流程编号
     * @return EnerbosMessage返回工单执行记录
     */
    @ApiOperation(value = "派工工单-执行记录", response = DispatchWorkOrderFlowRecordVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/flow/history", method = RequestMethod.POST)
    public EnerbosMessage getDispatchWorkOrderFlowRecord(@ApiParam(value = "派工工单-流程编号", required = true) String processInstanceId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dispatch/order/flow/history, host: [{}:{}], service_id: {}, processInstanceId: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), processInstanceId, user);
            Assert.isTrue(StringUtils.isNotEmpty(processInstanceId), "流程编号不能为空！");
            List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
            List<DispatchWorkOrderFlowRecordVo> dispatchWorkOrderFlowRecordVoList = Objects.isNull(historicTaskVoList) ? Collections.EMPTY_LIST :
                    historicTaskVoList.stream()
                            .sorted(Comparator.comparing(HistoricTaskVo::getStartTime, Comparator.reverseOrder()))
                            .map(historicTaskVo ->  {
                                DispatchWorkOrderFlowRecordVo vo = new DispatchWorkOrderFlowRecordVo();
                                vo.setStartTime(historicTaskVo.getStartTime());
                                vo.setEndTime(historicTaskVo.getEndTime());
                                vo.setName(historicTaskVo.getName());

                                if (StringUtils.isNoneEmpty(historicTaskVo.getAssignee())) {
                                    String[] ids = historicTaskVo.getAssignee().split(",");
                                    List<String> personVoList = Arrays.stream(ids).map(id -> personAndUserClient.findByPersonId(id)).filter(Objects::nonNull).map(PersonAndUserVoForDetail::getName).collect(Collectors.toList());

                                    if (Objects.nonNull(personVoList) && !personVoList.isEmpty()) {
                                        vo.setPersonName(StringUtils.join(personVoList, ","));
                                    }
                                }

                                vo.setDurationInMillis(historicTaskVo.getDurationInMillis());
                                vo.setDescription(historicTaskVo.getDescription());
                                return vo;
                            })
                            .collect(Collectors.toList());
            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowRecordVoList, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/history");
        }
    }

    /**
     * 派工工单-批量分派
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "派工工单-批量分派", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dispatch/order/flow/batchDispatch", method = RequestMethod.POST)
    public EnerbosMessage batchDispatchWorkOrder(@ApiParam(value = "派工工单-流程VO", required = true) DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, Principal user) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/eam/open/dispatch/order/flow/batchDispatch, host: [{}:{}], service_id: {}, DispatchWorkOrderFlowVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), dispatchWorkOrderFlowVo, user);

        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        List<DispatchWorkOrderFlowVo> rollbackWorkOrderList = new ArrayList<>();
        Map<String, DispatchWorkOrderFlowVo> oldWorkOrderMap = new HashMap<>();
        try {
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()), "未知工单！");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getExecutionPersonId()), "执行人不能为空。");

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            PersonAndUserVoForDetail currentPersonVo = personAndUserClient.findByPersonId(personId);
            Assert.notNull(currentPersonVo, "账号信息获取失败，请刷新后重试！");

            String[] workOrderIdArray = dispatchWorkOrderFlowVo.getWorkOrderId().split(",");
            for (String id : workOrderIdArray) {
                DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo = dispatchWorkOrderClient.findDispatchWorkOrderFlowVoById(id);
                if (Objects.isNull(oldDispatchWorkOrderFlowVo)) {
                    return EnerbosMessage.createErrorMsg("500", "未知工单!!", "");
                }
                if (!DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP.equals(oldDispatchWorkOrderFlowVo.getWorkOrderStatus())) {
                    return EnerbosMessage.createErrorMsg("500", "工单已分配或未提报，请刷新后重试！工单编号：%s ", oldDispatchWorkOrderFlowVo.getWorkOrderNum());
                }
                if (!oldDispatchWorkOrderFlowVo.getReportPersonId().equals(personId)) {
                    return EnerbosMessage.createErrorMsg("401", String.format("无权限分派！ 工单编号：%s", oldDispatchWorkOrderFlowVo.getWorkOrderNum()), "");
                }
                oldWorkOrderMap.put(id, oldDispatchWorkOrderFlowVo);
            }

            String[] executionPersonIdArray =  dispatchWorkOrderFlowVo.getExecutionPersonId().split(",");

            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
            for (String id : executionPersonIdArray) {
                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                Assert.notNull(personVo, "未知执行人！");
                orderPersonVoList.add(new OrderPersonVo(null, DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
            }

            Map<String, Object> variables = new HashMap<>();
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, StringUtils.join(orderPersonVoList.parallelStream().map(OrderPersonVo::getPersonId).collect(Collectors.toList()), ","));
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, personId);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
            variables.put("userId", personId);
            variables.put("description", dispatchWorkOrderFlowVo.getProcessDescription());

            for (String id : workOrderIdArray) {
                DispatchWorkOrderFlowVo oldWorkOrderFlowVo = oldWorkOrderMap.get(id);
                DispatchWorkOrderFlowVo vo = new DispatchWorkOrderFlowVo();
                ReflectionUtils.copyProperties(oldWorkOrderFlowVo, vo, null);
                vo.setOperatorPersonId(personId);
                vo.setOperatorPerson(currentPersonVo.getName());
                vo.setDispatchPersonId(vo.getOperatorPersonId());
                vo.setDispatchPersonName(vo.getOperatorPerson());
                vo.setPlanCompleteTime(dispatchWorkOrderFlowVo.getPlanCompleteTime());

                //更新工单ID
                orderPersonVoList.forEach(orderPersonVo -> orderPersonVo.setOrderId(id));
                //更新执行人以及委托执行人数据
                List<OrderPersonVo> oldOrderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                //提交前人员相关数据为空，则回滚标注数据清空
                if (oldOrderPersonVoList.isEmpty()) {
                    rollbackDataList.add(new OrderPersonVo(id, DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, null));
                } else {
                    rollbackDataList.addAll(oldOrderPersonVoList);
                }

                vo = dispatchWorkOrderClient.commitDispatchWorkOrder(vo);
                variables.put("status", vo.getWorkOrderStatus());

                //更新流程进度
                Boolean processMessage = processTaskClient.completeByProcessInstanceId(vo.getProcessInstanceId(), variables);
                if (Objects.isNull(processMessage) || !processMessage) {
                    throw new EnerbosException("500", String.format("流程操作异常。工单编号：%s", vo.getWorkOrderNum()));
                } else {
                    rollbackWorkOrderList.add(vo);
                }
            }
            return EnerbosMessage.createSuccessMsg(null, "分派成功", "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            //回滚工单状态
            if (!rollbackWorkOrderList.isEmpty()) {
                for (DispatchWorkOrderFlowVo workOrderFlowVo : rollbackWorkOrderList) {
                    rollbackDispatchWorkOrder(workOrderFlowVo, oldWorkOrderMap.get(workOrderFlowVo.getWorkOrderId()));
                }
            }

            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/batchDispatch");
        }
    }

    /**
     * 派工工单-工单提报
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @return EnerbosMessage返回执行码及数据
     */
    private EnerbosMessage createDispatchWorkOrderFlow(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderNum()), "工单编号不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getDescription()), "派工描述不能为空。");

            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getReportPersonId()), "提报人不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getOrgId()), "所属组织不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getSiteId()), "所属站点不能为空。");

            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(dispatchWorkOrderFlowVo.getReportPersonId());
            Assert.notNull(personVo, "未知提报人！");

            SiteVoForDetail siteVo = siteClient.findById(dispatchWorkOrderFlowVo.getSiteId());
            Assert.notNull(siteVo, "未知站点！");
            Assert.isTrue(siteVo.getOrgId().equals(dispatchWorkOrderFlowVo.getOrgId()), "所属组织错误！");

            if (Objects.isNull(dispatchWorkOrderFlowVo.getReportDate())) {
                dispatchWorkOrderFlowVo.setReportDate(new Date());
            }

            Map<String, Object> variables = new HashMap<>();
            //未保存工单提报时，先进行保存
            if (StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())) {
                dispatchWorkOrderFlowVo = dispatchWorkOrderClient.createOrUpdateDispatchWorkOrder(dispatchWorkOrderFlowVo);
            }

            if (StringUtils.isEmpty(dispatchWorkOrderFlowVo.getProcessInstanceId())) {
                //流程key,key为维保固定前缀+站点code
                String processKey = String.format("%s%s", DispatchWorkOrderCommon.DISPATCH_ORDER_WFS_PROCESS_KEY, siteVo.getCode());
                logger.debug("/eam/open/dispatch/order/flow/create, processKey: {}", processKey);

                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(dispatchWorkOrderFlowVo.getWorkOrderId());
                processVo.setProcessKey(processKey);
                processVo.setUserId(dispatchWorkOrderFlowVo.getReportPersonId());

                variables.put("startUserId", dispatchWorkOrderFlowVo.getReportPersonId());
                variables.put("userId", dispatchWorkOrderFlowVo.getOperatorPersonId());
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER, dispatchWorkOrderFlowVo.getReportPersonId());
                variables.put(Common.ORDER_NUM, dispatchWorkOrderFlowVo.getWorkOrderNum());
                variables.put(Common.ORDER_DESCRIPTION, dispatchWorkOrderFlowVo.getDescription());
                variables.put("status", DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB);
                processVo = workflowClient.startProcess(variables, processVo);

                if (Objects.isNull(processVo) || StringUtils.isEmpty(processVo.getProcessInstanceId())) {
                    //dispatchWorkOrderClient.deleteDispatchWorkOrder(Collections.singletonList(dispatchWorkOrderFlowVo.getWorkOrderId()));
                    return EnerbosMessage.createErrorMsg(null, "流程启动失败", null);
                } else {
                    //更新流程ID
                    dispatchWorkOrderFlowVo.setProcessInstanceId(processVo.getProcessInstanceId());
                    dispatchWorkOrderClient.saveDispatchWorkOrder(dispatchWorkOrderFlowVo);
                }
            }

            //查询用户组主管列表
            List<String> personList = new ArrayList<>();
            if (!getDispatchWorkOrderManageUserList(dispatchWorkOrderFlowVo, personList)) {
                return EnerbosMessage.create("派工工单分派组下没有人员,请联系管理员添加!!", "500", "", false, dispatchWorkOrderFlowVo);
            }

            //工单提报
            dispatchWorkOrderFlowVo = dispatchWorkOrderClient.commitDispatchWorkOrder(dispatchWorkOrderFlowVo);

            variables.clear();
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, StringUtils.join(personList, ","));
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
            variables.put("userId", dispatchWorkOrderFlowVo.getOperatorPersonId());
            variables.put("description", dispatchWorkOrderFlowVo.getProcessDescription());
            variables.put("status", DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP);
         //   variables.put("status", dispatchWorkOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dispatchWorkOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildDispatchWorkOrderFlowVo(dispatchWorkOrderFlowVo);
            this.sendPush(dispatchWorkOrderFlowVo,DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB);
            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowVo, "工单提报成功", "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getWorkOrderId())
                    && !DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB.equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("ids", Collections.singletonList(dispatchWorkOrderFlowVo.getWorkOrderId()));
                paramsMap.put("status", DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB);
                dispatchWorkOrderClient.changeDispatchWorkOrderStatus(paramsMap);
                dispatchWorkOrderFlowVo.setWorkOrderStatus(DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB);
            }
            //将工单数据返回前台，避免提报失败导致必须要删除工单
            EnerbosMessage message = buildErrorMsg(e, "/eam/open/dispatch/order/flow/create");
            message.setData(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()) ? null : dispatchWorkOrderFlowVo);
            return message;
        }
    }

    /**
     * 派工工单-任务分派
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @param oldDispatchWorkOrderFlowVo  派工工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    private EnerbosMessage assignDispatchWorkOrderFlow(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo) {
        List<String> personList = new ArrayList<>();
        if (!getDispatchWorkOrderManageUserList(oldDispatchWorkOrderFlowVo, personList)) {
            return EnerbosMessage.createErrorMsg("500", "派工工单分派组下没有人员,请联系管理员添加!!", "");
        }

        final String personId = dispatchWorkOrderFlowVo.getOperatorPersonId();
        if (personList.stream().noneMatch(personId::equals)) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            String message;
            Map<String, Object> variables = new HashMap<>();
            switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
                case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                    Assert.isTrue(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getExecutionPersonId()), "执行人不能为空。");
                    String[] executionPersonIdArray =  dispatchWorkOrderFlowVo.getExecutionPersonId().split(",");

                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : executionPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "未知执行人！");
                        orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
                    }

                    dispatchWorkOrderFlowVo.setDispatchPersonId(dispatchWorkOrderFlowVo.getOperatorPersonId());
                    dispatchWorkOrderFlowVo.setDispatchPersonName(dispatchWorkOrderFlowVo.getOperatorPerson());
                    message = "分派成功";

                    //更新执行人以及委托执行人数据
                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                    //提交前人员相关数据为空，则回滚标注数据清空
                    if (rollbackDataList.isEmpty()) {
                        rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, null));
                    }
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, StringUtils.join(orderPersonVoList.parallelStream().map(OrderPersonVo::getPersonId).collect(Collectors.toList()), ","));
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, dispatchWorkOrderFlowVo.getOperatorPersonId());
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                } break;
                //驳回
                 case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT: {
//                        String[] executionPersonIdArray =  dispatchWorkOrderFlowVo.getExecutionPersonId().split(",");
//                        List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
//                        for (String id : executionPersonIdArray) {
//                            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
//                            Assert.notNull(personVo, "未知执行人！");
//                            orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
//                        }
//                        dispatchWorkOrderFlowVo.setDispatchPersonId(dispatchWorkOrderFlowVo.getOperatorPersonId());
//                        dispatchWorkOrderFlowVo.setDispatchPersonName(dispatchWorkOrderFlowVo.getOperatorPerson());
                        message = "分派驳回";
                        variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, dispatchWorkOrderFlowVo.getDispatchPersonId());
                        variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER, dispatchWorkOrderFlowVo.getReportPersonId());
                        variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                        variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                        variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    } break;
                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrderFlowVo.getWorkOrderStatus()));
            }
            dispatchWorkOrderFlowVo = dispatchWorkOrderClient.commitDispatchWorkOrder(dispatchWorkOrderFlowVo);
            variables.put("userId", dispatchWorkOrderFlowVo.getOperatorPersonId());
            variables.put("status", dispatchWorkOrderFlowVo.getWorkOrderStatus());
            variables.put("description", dispatchWorkOrderFlowVo.getProcessDescription());

            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dispatchWorkOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildDispatchWorkOrderFlowVo(dispatchWorkOrderFlowVo);
            this.sendPush(dispatchWorkOrderFlowVo,oldDispatchWorkOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }
            //回滚工单状态
            rollbackDispatchWorkOrder(dispatchWorkOrderFlowVo, oldDispatchWorkOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/assign");
        }
    }

    /**
     * 派工工单-执行汇报
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @param oldDispatchWorkOrderFlowVo  派工工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    private EnerbosMessage reportDispatchWorkOrderFlow(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo) {
        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            List<OrderPersonVo> personList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION));
            final String personId = dispatchWorkOrderFlowVo.getOperatorPersonId();
            if (personList.stream().noneMatch(vo -> personId.equals(vo.getPersonId()))) {
                return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
            }
            String message;
            Map<String, Object> variables = new HashMap<>();
            switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
                case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                    //确认汇报后，进入待验收环节
                    Assert.notNull(dispatchWorkOrderFlowVo.getActualExecutionPersonId(), "实际执行人不能为空！");
                    Assert.notNull(dispatchWorkOrderFlowVo.getReceiveTime(), "请填写接报时间！");
                    Assert.notNull(dispatchWorkOrderFlowVo.getCompletionTime(), "请填写完成时间！");
                    Assert.notNull(dispatchWorkOrderFlowVo.getConsumeHours(), "请填写工时耗时！");
                    String[] actualExecutionPersonIdArray =  dispatchWorkOrderFlowVo.getActualExecutionPersonId().split(",");
                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : actualExecutionPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "实际执行人未知！");
                        orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION, id));
                    }
                    //更新实际执行人数据
                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                    if (rollbackDataList.isEmpty()) {
                        rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION, null));
                    }
                    message = "汇报成功";
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, dispatchWorkOrderFlowVo.getOperatorPersonId());
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_CONFIRM_ACCEPTOR_USER, dispatchWorkOrderFlowVo.getReportPersonId());
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                } break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT: {//驳回
//                    List<PersonAndUserVoForList> person=this.getDispatchWorkOrderManageUserList(dispatchWorkOrderFlowVo.getOrgId(),dispatchWorkOrderFlowVo.getSiteId());
//                    String  personIdJoin=  person.stream().map(PersonAndUserVoForList::getPersonId).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();
//                    if (personIdJoin==null) {
//                        return EnerbosMessage.createErrorMsg("500", "派工工单分派组下没有人员,请联系管理员添加!!", "");
//                    }
//                    String[] actualExecutionPersonIdArray =  dispatchWorkOrderFlowVo.getActualExecutionPersonId().split(",");
//                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
//                    for (String id : actualExecutionPersonIdArray) {
//                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
//                        Assert.notNull(personVo, "实际执行人未知！");
//                        orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION, id));
//                    }
//                    //更新实际执行人数据
//                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
//                    if (rollbackDataList.isEmpty()) {
//                        rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION, null));
//                    }
                    message = "驳回成功";
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, dispatchWorkOrderFlowVo.getOperatorPersonId());
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, dispatchWorkOrderFlowVo.getDispatchPersonId());
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);

                } break;
                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrderFlowVo.getWorkOrderStatus()));
            }
            dispatchWorkOrderFlowVo = dispatchWorkOrderClient.commitDispatchWorkOrder(dispatchWorkOrderFlowVo);
            variables.put("userId", dispatchWorkOrderFlowVo.getOperatorPersonId());
            variables.put("description", dispatchWorkOrderFlowVo.getProcessDescription());
            variables.put("status", dispatchWorkOrderFlowVo.getWorkOrderStatus());

            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dispatchWorkOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            //更新Vo展示内容
            buildDispatchWorkOrderFlowVo(dispatchWorkOrderFlowVo);
            this.sendPush(dispatchWorkOrderFlowVo,oldDispatchWorkOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }
            //回滚工单状态
            rollbackDispatchWorkOrder(dispatchWorkOrderFlowVo, oldDispatchWorkOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/report");
        }
    }

    /**
     * 派工工单-待验收
     * @param dispatchWorkOrderFlowVo 派工工单-流程VO
     * @param oldDispatchWorkOrderFlowVo  派工工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    private EnerbosMessage acceptDispatchWorkOrderFlow(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo) {
        if (!oldDispatchWorkOrderFlowVo.getDispatchPersonId().equals(dispatchWorkOrderFlowVo.getOperatorPersonId())) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            String message;
            Map<String, Object> variables = new HashMap<>();
            switch (dispatchWorkOrderFlowVo.getProcessStatus()) {
                case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS: {
                    Assert.notNull(dispatchWorkOrderFlowVo.getAcceptTime(), "请填写验收时间！");
                    Assert.notNull(dispatchWorkOrderFlowVo.getAcceptPersonId(), "请选择验收人！");

                    String[] acceptorPersonIdArray =  dispatchWorkOrderFlowVo.getAcceptPersonId().split(",");

                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : acceptorPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "验收人未知！");
                        orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR, id));
                    }

                    //更新验收人数据
                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                    if (rollbackDataList.isEmpty()) {
                        rollbackDataList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR, null));
                    }
                    //验收通过后，流程结束
                    message = "验收成功";
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_CONFIRM_ACCEPTOR_USER, dispatchWorkOrderFlowVo.getOperatorPersonId());
                } break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT: {
                    message = "驳回成功";
                 //   List<OrderPersonVo> personList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION));
                  //  variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, StringUtils.join(personList.parallelStream().map(OrderPersonVo::getPersonId).collect(Collectors.toList()), ","));
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, oldDispatchWorkOrderFlowVo.getOrderReportPersonId());
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dispatchWorkOrderFlowVo.getWorkOrderStatus()));
            }

            dispatchWorkOrderFlowVo = dispatchWorkOrderClient.commitDispatchWorkOrder(dispatchWorkOrderFlowVo);
            variables.put("userId", dispatchWorkOrderFlowVo.getOperatorPersonId());
            variables.put("description", dispatchWorkOrderFlowVo.getProcessDescription());
            variables.put("status", dispatchWorkOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dispatchWorkOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildDispatchWorkOrderFlowVo(dispatchWorkOrderFlowVo);
            this.sendPush(dispatchWorkOrderFlowVo,oldDispatchWorkOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(dispatchWorkOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            //回滚工单状态
            rollbackDispatchWorkOrder(dispatchWorkOrderFlowVo, oldDispatchWorkOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/accept");
        }
    }

    /**
     * 回滚工单状态
     * @param dispatchWorkOrderFlowVo  新数据
     * @param oldDispatchWorkOrderFlowVo    原始数据
     */
    private void rollbackDispatchWorkOrder(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, DispatchWorkOrderFlowVo oldDispatchWorkOrderFlowVo) {
        if (Objects.nonNull(oldDispatchWorkOrderFlowVo) && !oldDispatchWorkOrderFlowVo.getWorkOrderStatus().equals(dispatchWorkOrderFlowVo.getWorkOrderStatus())) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("ids", Collections.singletonList(oldDispatchWorkOrderFlowVo.getWorkOrderId()));
            paramsMap.put("status", oldDispatchWorkOrderFlowVo.getWorkOrderStatus());
            dispatchWorkOrderClient.changeDispatchWorkOrderStatus(paramsMap);
        }
    }

    private EnerbosMessage buildErrorMsg(Exception exception, String uri) {
        logger.error(String.format("------- %s --------", uri), exception);
        String message, statusCode  = "" ;
        if (exception instanceof HystrixRuntimeException) {
            Throwable fallbackException = exception.getCause();
            if (fallbackException.getMessage().contains("{")) {
                message =  fallbackException.getMessage().substring(fallbackException.getMessage().indexOf("{")) ;
                try {
                    JSONObject jsonMessage = JSONObject.parseObject(message);
                    if(jsonMessage !=null){
                        statusCode =   jsonMessage.get("status").toString();
                        message = jsonMessage.get("message").toString();
                    }
                } catch (Exception jsonException) {
                    logger.error(String.format("------- %s --------", uri), jsonException);
                }
            } else {
                message = fallbackException.getMessage();
                if (fallbackException instanceof EnerbosException) {
                    statusCode = ((EnerbosException) exception).getErrorCode();
                }
            }
        } else {
            message = exception.getMessage();
            if (exception instanceof EnerbosException) {
                statusCode = ((EnerbosException) exception).getErrorCode();
            }
        }
        return EnerbosMessage.createErrorMsg(statusCode, message, exception.getStackTrace().toString());
    }

    private void buildDispatchWorkOrderFlowVo(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo) {
        SiteVoForDetail siteVo = siteClient.findById(dispatchWorkOrderFlowVo.getSiteId());
        dispatchWorkOrderFlowVo.setSiteName(siteVo.getName());
        dispatchWorkOrderFlowVo.setOrgName(siteVo.getOrgName());

        if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getReportPersonId()) && StringUtils.isEmpty(dispatchWorkOrderFlowVo.getReportPersonName())) {
            PersonAndUserVoForDetail reportPerson = personAndUserClient.findByPersonId(dispatchWorkOrderFlowVo.getReportPersonId());
            dispatchWorkOrderFlowVo.setReportPersonName(Objects.isNull(reportPerson) ? null : reportPerson.getName());
            //dispatchWorkOrderFlowVo.setReportPersonTel(Objects.isNull(reportPerson) ? null : reportPerson.getMobile());
        }

        if (StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getDispatchPersonId()) && StringUtils.isEmpty(dispatchWorkOrderFlowVo.getDispatchPersonName())) {
            PersonAndUserVoForDetail dispatchPerson = personAndUserClient.findByPersonId(dispatchWorkOrderFlowVo.getDispatchPersonId());
            dispatchWorkOrderFlowVo.setDispatchPersonName(Objects.isNull(dispatchPerson) ? null : dispatchPerson.getName());
        }

        List<OrderPersonVo> executionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION));
        if (!executionPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = executionPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            dispatchWorkOrderFlowVo.setExecutionPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            dispatchWorkOrderFlowVo.setExecutionPerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
        }


        List<OrderPersonVo> actualExecutionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION));
        if (!actualExecutionPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = actualExecutionPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            dispatchWorkOrderFlowVo.setActualExecutionPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            dispatchWorkOrderFlowVo.setActualExecutionPerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
        }

        List<OrderPersonVo> acceptPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_ACCEPTOR));
        if (!acceptPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = acceptPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            dispatchWorkOrderFlowVo.setAcceptPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            dispatchWorkOrderFlowVo.setAcceptPerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
        }
        if(StringUtils.isNotEmpty(dispatchWorkOrderFlowVo.getOrderReportPersonId())){
            PersonAndUserVoForDetail dispatchPerson = personAndUserClient.findByPersonId(dispatchWorkOrderFlowVo.getOrderReportPersonId());
            dispatchWorkOrderFlowVo.setOrderReportPerson(Objects.isNull(dispatchPerson) ? null : dispatchPerson.getName());
        }

    }

    /**
     * 获取工单分派环节主管用户组
     *
     * @param dispatchWorkOrderFlowVo 流程Vo
     * @param userList          主管用户Person ID列表
     * @return 操作成功或失败
     */
    private boolean getDispatchWorkOrderManageUserList(DispatchWorkOrderFlowVo dispatchWorkOrderFlowVo, List<String> userList) {
        List<PersonAndUserVoForList> userVoList = getDispatchWorkOrderManageUserList(dispatchWorkOrderFlowVo.getOrgId(), dispatchWorkOrderFlowVo.getSiteId());
        if (Objects.isNull(userVoList)) {
            return false;
        } else {
            userVoList.stream().map(PersonAndUserVoForList::getPersonId).forEach(userList::add);
        }
        return true;
    }

    private List<PersonAndUserVoForList> getDispatchWorkOrderManageUserList(String orgId, String siteId) {
        UgroupVoForDetail ugroupVoForDetail = null;
        UserGroupDomainVo vo = userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(DispatchWorkOrderCommon.DISPATCH_ORDER_DISPATCH_USER_GROUP_KEY,
                Common.MY_TASK_DOAMIN, orgId, siteId, Common.USERGROUP_ASSOCIATION_TYPE_ALL);
        if (Objects.nonNull(vo)) {
            ugroupVoForDetail = ugroupClient.findById(vo.getUserGroupId());
        }

        if (Objects.isNull(ugroupVoForDetail) || Objects.isNull(ugroupVoForDetail.getUsers())) {
            return null;
        } else {
            return ugroupVoForDetail.getUsers();
        }
    }

    //APP推送

    /**
     *
     * @param workOrderFlowVo 工单信息
     * @param workOrderStatus 工单状态（上一操作）
     */
    private void  sendPush(DispatchWorkOrderFlowVo workOrderFlowVo,String workOrderStatus){


        try {
            //通过执行记录去查询
            List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(workOrderFlowVo.getProcessInstanceId());
            String[] person=null;
            if(historicTaskVoList.size()>0){
                person=historicTaskVoList.get(0).getAssignee().split(",");
                for (int i = 0; i < person.length; i++) {
                    PersonAndUserVoForDetail personDetail=personAndUserClient.findByPersonId(person[i]);
                    if (personDetail != null) {
                        person[i]=personDetail.getLoginName();
                    }
                }
            }
            //标题
            String pushTitle=null;
            //内容
            String pushContent=null;
            //用于组合的短信内容
            String buildUpContent=null;
            String workType="派工工单";
            String status=null;
            switch (workOrderStatus){
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DTB:{
                    //推送消息给App端
                    status="分派";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                } break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DFP:{
                    if(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                        status="执行";
                        pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }else if(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                        status="提报";
                        pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }
                }break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DHB:{
                    if(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                        status="验收";
                        pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }else if(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                        status="分派";
                        pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }
                }break;
                case DispatchWorkOrderCommon.DISPATCH_ORDER_STATUS_DYS:{
                    if(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                        // status="验收完成";
                        pushTitle = ""+workType+"验收完成,编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：验收完成<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }else if(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                        status="执行";
                        pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                        pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                        pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                    }
                }break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map getExtraMap(DispatchWorkOrderFlowVo workOrderFlowVo) {
        Map extras = new HashMap<>();
        extras.put("getBusinessDataByCurrentUser", "true");
        extras.put("workorderid", workOrderFlowVo.getId());
        extras.put("notificationType", DispatchWorkOrderCommon.DISPATCH_ORDER_WFS_PROCESS_KEY
        );
        return extras;
    }

}
