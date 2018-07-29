package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.RepairOrderCommon;
import com.enerbos.cloud.eam.contants.WorkOrderCommon;
import com.enerbos.cloud.eam.openservice.config.RepairOrderAutoDispatchConfig;
import com.enerbos.cloud.eam.openservice.service.PushService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForList;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
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

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-14 14:11
 * @Description EAM报修工单相关服务
 */
@Api(description = "报修工单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
@RestController
public class RepairOrderController {
    private static Logger logger = LoggerFactory.getLogger(RepairOrderController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private RepairOrderClient repairOrderClient;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private OrderPersonClient orderPersonClient;

    @Autowired
    private WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;

    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @Autowired
    private CodeGeneratorClient codeGeneratorClient;

    @Autowired
    private MaintenanceWorkOrderClient maintenanceWorkOrderClient;

    @Autowired
    private PushService pushService;

    @Autowired
    private RepairOrderAutoDispatchConfig repairOrderAutoDispatchConfig;

    /**
     * 分页查询报修工单
     *
     * @param repairOrderListFilterVo 查询条件
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询报修工单", response = RepairOrderListVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/page", method = RequestMethod.GET)
    public EnerbosMessage findPageWorkOrderList(@ApiParam(value = "报修工单-列表过滤条件VO", required = false) RepairOrderListFilterVo repairOrderListFilterVo, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/page, host: [{}:{}], service_id: {}, RepairOrderListFilterVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), repairOrderListFilterVo, user);
            if (Objects.isNull(repairOrderListFilterVo)) {
                repairOrderListFilterVo = new RepairOrderListFilterVo();
            }
            //放入实际执行人分类编码
            repairOrderListFilterVo.setActualExecutionPersonFieldType(RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION);

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
            Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
            //设置查询人，用于获取关注信息
            repairOrderListFilterVo.setPersonId(personId);
            //前台未提交组织站点信息时，将不展示内容
            if (StringUtils.isEmpty(repairOrderListFilterVo.getOrgId())) {
                repairOrderListFilterVo.setOrgId("");
            }
            if (StringUtils.isEmpty(repairOrderListFilterVo.getSiteId())) {
                repairOrderListFilterVo.setSiteId("");
            }

            EnerbosPage<RepairOrderListVo> page = repairOrderClient.findOrderListByPage(repairOrderListFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/page");
        }
    }

    /**
     * 查询报修工单
     *
     * @param workOrderId 工单编号
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "查询报修工单", response = RepairOrderFlowVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order", method = RequestMethod.GET)
    public EnerbosMessage findWorkOrder(@ApiParam(value = "报修工单-工单编号", required = false) @RequestParam("id") String workOrderId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order, host: [{}:{}], service_id: {}, workOrderId: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), workOrderId, user);
            Assert.notNull(workOrderId, "工单编号不能为空！");
            RepairOrderFlowVo repairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(workOrderId);
            Assert.notNull(repairOrderFlowVo, "未知工单！");

            //==================== build vo ========================
            buildRepairOrderFlowVo(repairOrderFlowVo);
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order");
        }
    }

    /**
     * 报修工单-保存&更新
     *
     * @param repairOrderFlowVo 报修工单-流程VO
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "报修工单-保存&更新", response = RepairOrderFlowVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/save", method = RequestMethod.POST)
    public EnerbosMessage saveRepairOrder(@ApiParam(value = "报修工单-流程VO", required = true) RepairOrderFlowVo repairOrderFlowVo, Principal user) {
        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        RepairOrderFlowVo oldRepairOrderFlowVo = null;
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/save, host: [{}:{}], service_id: {}, RepairOrderFlowVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), repairOrderFlowVo, user);

            final String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            //记录当前工单操作人信息
            repairOrderFlowVo.setOperatorPersonId(personId);
            repairOrderFlowVo.setOperatorPerson(user.getName());

            if (StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderId())) {
                Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderStatus()), "工单状态不能为空！");
                oldRepairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(repairOrderFlowVo.getWorkOrderId());
                if (Objects.isNull(oldRepairOrderFlowVo)) {
                    return EnerbosMessage.createErrorMsg("500", "未知工单!!", "");
                }

                if (!oldRepairOrderFlowVo.getWorkOrderNum().equals(repairOrderFlowVo.getWorkOrderNum())) {
                    return EnerbosMessage.createErrorMsg("500", "工单编号不匹配！", "");
                }

                if (!oldRepairOrderFlowVo.getWorkOrderStatus().equals(repairOrderFlowVo.getWorkOrderStatus())) {
                    return EnerbosMessage.createErrorMsg("500", "工单状态不匹配！", "");
                }
                switch (repairOrderFlowVo.getWorkOrderStatus()) {
                    //待提报分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB:
                        break;
                    //待分派分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP: {
                        //检查权限
                        List<String> personList;
                        if (StringUtils.isNotEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
                            personList = Collections.singletonList(oldRepairOrderFlowVo.getDispatchPersonId());
                        } else {
                            if (oldRepairOrderFlowVo.getReportAssignFlag()) {
                                personList = Collections.singletonList(oldRepairOrderFlowVo.getReportPersonId());
                            } else {
                                personList = new ArrayList<>();
                                oldRepairOrderFlowVo.setOperatorPersonId(repairOrderFlowVo.getOperatorPersonId());
                                oldRepairOrderFlowVo.setOperatorPerson(repairOrderFlowVo.getOperatorPerson());
                                if (!getRepairOrderManageUserList(oldRepairOrderFlowVo, personList)) {
                                    return EnerbosMessage.createErrorMsg("500", "报修工单分派组下没有人员，请联系管理员添加!!", "");
                                }
                            }
                        }
                        if (personList.stream().noneMatch(personId::equals)) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }

                        //更新执行人数据
                        if (StringUtils.isNotEmpty(repairOrderFlowVo.getExecutionPersonId())) {
                            String[] executionPersonIdArray = repairOrderFlowVo.getExecutionPersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : executionPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "未知执行人！");
                                orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION);
                        }

                        //更新委托执行人数据
                        if (StringUtils.isNotEmpty(repairOrderFlowVo.getEntrustExecutePersonId())) {
                            String[] entrustExecutionPersonIdArray = repairOrderFlowVo.getEntrustExecutePersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : entrustExecutionPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "未知委托执行人！");
                                orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION);
                        }
                    }
                    break;
                    //待接单分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DJD: {
                        //待接单环节没有保存内容
                        return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, "工单保存成功", "");
                    }
                    //待汇报分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB: {
                        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getReceivePersonId())) {
                            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
                        }
                        if (!oldRepairOrderFlowVo.getReceivePersonId().equals(personId)) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }
                        //更新实际执行人数据
                        if (StringUtils.isNotEmpty(repairOrderFlowVo.getActualExecutionPersonId())) {
                            String[] actualExecutionPersonIdArray = repairOrderFlowVo.getActualExecutionPersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : actualExecutionPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "实际执行人未知！");
                                orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION);
                        }
                    }
                    break;
                    //申请挂起分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ: {
                        //申请挂起环节没有保存内容
                        return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, "工单保存成功", "");
                    }
                    //待验收分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS: {
                        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
                            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
                        }
                        if (!oldRepairOrderFlowVo.getDispatchPersonId().equals(repairOrderFlowVo.getOperatorPersonId())) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }

                        //更新验收人数据
                        if (StringUtils.isNotEmpty(repairOrderFlowVo.getAcceptPersonId())) {
                            String[] acceptPersonIdArray = repairOrderFlowVo.getAcceptPersonId().split(",");
                            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                            for (String id : acceptPersonIdArray) {
                                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                                Assert.notNull(personVo, "未知验收人！");
                                orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR, id));
                            }

                            orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                            if (orderPersonVoList.isEmpty()) {
                                rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR, null));
                            } else {
                                rollbackDataList.addAll(orderPersonVoList);
                            }
                        } else {
                            rollbackDataList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR)));
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR);
                        }
                    }
                    break;
                    //验收待确认分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR: {
                        if (!oldRepairOrderFlowVo.getReportPersonId().equals(personId)) {
                            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
                        }
                    } break;
                    default:
                        throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
                }
            }

            repairOrderFlowVo = repairOrderClient.createOrUpdateRepairOrder(repairOrderFlowVo);
            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, "工单保存成功", "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            //回滚工单数据
            if (Objects.nonNull(oldRepairOrderFlowVo)) {
                repairOrderClient.createOrUpdateRepairOrder(oldRepairOrderFlowVo);
            }
            return buildErrorMsg(e, "/eam/open/repair/order/save");
        }
    }

    /**
     * 报修工单-发送流程
     *
     * @param repairOrderFlowVo 报修工单-流程VO
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "报修工单-发送流程", response = RepairOrderFlowVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/flow/commit", method = RequestMethod.POST)
    public EnerbosMessage commitRepairOrderFlow(@ApiParam(value = "报修工单-流程VO", required = true) RepairOrderFlowVo repairOrderFlowVo, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/flow/commit, host: [{}:{}], service_id: {}, RepairOrderFlowVo: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), repairOrderFlowVo, user);

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            //记录当前工单操作人信息
            repairOrderFlowVo.setOperatorPersonId(personId);
            repairOrderFlowVo.setOperatorPerson(user.getName());

            EnerbosMessage message;
            if (StringUtils.isEmpty(repairOrderFlowVo.getWorkOrderId())) {
                Assert.isTrue(StringUtils.isEmpty(repairOrderFlowVo.getWorkOrderStatus())
                        || RepairOrderCommon.REPAIR_ORDER_STATUS_DTB.equals(repairOrderFlowVo.getWorkOrderStatus()), "未知工单！");

                //如果没有工单状态并且没有工单编号，允许进入提交环节
                message = createRepairOrderFlow(repairOrderFlowVo);
            } else {
                Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderStatus()), "工单状态不能为空！");
                RepairOrderFlowVo oldRepairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(repairOrderFlowVo.getWorkOrderId());
                if (Objects.isNull(oldRepairOrderFlowVo)) {
                    return EnerbosMessage.createErrorMsg("500", "未知工单!!", "");
                }

                if (!oldRepairOrderFlowVo.getWorkOrderNum().equals(repairOrderFlowVo.getWorkOrderNum())) {
                    return EnerbosMessage.createErrorMsg("500", "工单编号不匹配！", "");
                }

                if (!oldRepairOrderFlowVo.getWorkOrderStatus().equals(repairOrderFlowVo.getWorkOrderStatus())) {
                    return EnerbosMessage.createErrorMsg("500", "工单状态不匹配！", "");
                }

                switch (repairOrderFlowVo.getWorkOrderStatus()) {
                    //待提报分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB: {
                        message = createRepairOrderFlow(repairOrderFlowVo);
                    }
                    break;
                    //待分派分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP: {
                        message = assignRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    //待接单分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DJD: {
                        message = receiveRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    //待汇报分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB: {
                        message = reportRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    //申请挂起分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ: {
                        message = applyForWaitRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    //挂起分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT: {
                        message = processWaitRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    //待验收分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS: {
                        message = acceptRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    //验收待确认分支
                    case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR: {
                        message = closeRepairOrderFlow(repairOrderFlowVo, oldRepairOrderFlowVo);
                    }
                    break;
                    default:
                        throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
                }
            }
            return message;
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/flow/commit");
        }
    }

    /**
     * 报修工单-收藏
     *
     * @param ids 报修工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "报修工单-收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/collect", method = RequestMethod.POST)
    public EnerbosMessage collectRepairOrder(@ApiParam(value = "报修工单ID列表", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/collect, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

            Assert.notEmpty(ids, "请选择要收藏的工单！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<RepairOrderRfCollectorVo> repairOrderRfCollectorVos = Arrays.stream(ids).map(o -> new RepairOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
            repairOrderClient.collectRepairOrder(repairOrderRfCollectorVos);

            return EnerbosMessage.createSuccessMsg("", "收藏成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/collect");
        }
    }

    /**
     * 报修工单-取消收藏
     *
     * @param ids 报修工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "报修工单-取消收藏", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/collect/cancel", method = RequestMethod.POST)
    public EnerbosMessage cancelCollectRepairOrder(@ApiParam(value = "报修工单ID列表", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/collect/cancel, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);

            Assert.notEmpty(ids, "请选择要取消收藏的工单！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<RepairOrderRfCollectorVo> repairOrderRfCollectorVos = Arrays.stream(ids).map(o -> new RepairOrderRfCollectorVo(o, personId, Common.EAM_PROD_IDS[0])).collect(Collectors.toList());
            repairOrderClient.cancelCollectRepairOrder(repairOrderRfCollectorVos);
            return EnerbosMessage.createSuccessMsg("", "取消收藏成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/collect/cancel");
        }
    }

    /**
     * 报修工单-状态变更
     *
     * @param ids 报修工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "报修工单-状态变更", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/flow/change", method = RequestMethod.POST)
    public EnerbosMessage changeRepairOrderStatus(@ApiParam(value = "报修工单ID列表", required = true) String[] ids, @ApiParam(value = "工单状态", required = true) String status, @ApiParam(value = "变更描述", required = false) String description, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/flow/change, host: [{}:{}], service_id: {}, workOrderIds: {}, Status: {}, Description: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status, description, user);

            Assert.notEmpty(ids, "请选择要变更状态的工单！");
            Assert.isTrue(StringUtils.isNotEmpty(status), "请选择要变更的状态！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            boolean closeFlow = false;
            switch (status) {
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DJD:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR:
                    break;
                case RepairOrderCommon.REPAIR_ORDER_STATUS_CANCEL:
                case RepairOrderCommon.REPAIR_ORDER_STATUS_CLOSE: {
                    closeFlow = true;
                } break;
                default:
                    throw new EnerbosException("500", String.format("未支持的状态：[%s]", status));
            }

            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("ids", ids);
            paramsMap.put("status", status);
            Map<String, String> oldStatusMap = repairOrderClient.changeRepairOrderStatus(paramsMap);

            if (closeFlow) {
                RepairOrderFlowVo repairOrderFlowVo;
                Map<String, Object> variables = new HashMap<>();
                for (String id : ids) {
                    repairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(id);
                    if (Objects.isNull(repairOrderFlowVo)) { continue; }

                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_PROCESS_STATUS_GYS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                    variables.put("userId", personId);
                    variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
                    variables.put("description", StringUtils.isNotEmpty(description) ? description : "管理员取消");

                    try {
                        //更新流程进度
                        Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
                        if (Objects.isNull(processMessage) || !processMessage) {
                            throw new EnerbosException("500", "流程操作异常。");
                        }
                    } catch (Exception e) {
                        Map<String, List<String>> statusIdListMap = oldStatusMap.entrySet().stream().collect(Collectors.groupingBy((Map.Entry::getValue), Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
                        statusIdListMap.entrySet().forEach(entry -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("ids", entry.getValue().toArray(new String[entry.getValue().size()]));
                            map.put("status", entry.getKey());
                            repairOrderClient.changeRepairOrderStatus(map);
                        });

                        throw e;
                    }
                }
            }
            return EnerbosMessage.createSuccessMsg("", "变更成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/flow/change");
        }
    }

    /**
     * 报修工单-批量删除
     *
     * @param ids 报修工单ID列表
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "报修工单-批量删除", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/delete", method = RequestMethod.POST)
    public EnerbosMessage deleteRepairOrder(@ApiParam(value = "报修工单ID列表", required = true) String[] ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order, host: [{}:{}], service_id: {}, workOrderIds: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, user);
            Assert.notEmpty(ids, "请选择要删除的工单！");

            RepairOrderFlowVo repairOrderFlowVo;
            List<String> delList = new ArrayList<>();
            List<RepairOrderFlowVo> needCancelFlowList = new ArrayList<>();
            for (String id : ids) {
                repairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(id);

                if (repairOrderFlowVo == null) { continue; }

                if (!RepairOrderCommon.REPAIR_ORDER_STATUS_DTB.equals(repairOrderFlowVo.getWorkOrderStatus())) {
                    throw new EnerbosException("500", String.format("指定工单已经提报，不允许删除！  工单编号：[%s]", repairOrderFlowVo.getWorkOrderNum()));
                }

                if (StringUtils.isNotEmpty(repairOrderFlowVo.getProcessInstanceId())) {
                    throw new EnerbosException("500", String.format("指定工单已经提报，不允许删除！！  工单编号：[%s]", repairOrderFlowVo.getWorkOrderNum()));
                }

                delList.add(repairOrderFlowVo.getWorkOrderId());
                if (StringUtils.isNotEmpty(repairOrderFlowVo.getProcessInstanceId())) {
                    needCancelFlowList.add(repairOrderFlowVo);
                }
            }

            if (!delList.isEmpty()) {
                repairOrderClient.deleteRepairOrder(delList);

                delList.forEach(orderPersonClient::deleteOrderPersonByOrderId);

                Map<String, Object> variables = new HashMap<>(10);
                variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_PROCESS_STATUS_GYS_PASS, Boolean.FALSE);
                variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                variables.put("description", "工单删除");

                for (RepairOrderFlowVo orderFlowVo : needCancelFlowList) {
                    variables.put("status", orderFlowVo.getWorkOrderStatus());
                    try {
                        //更新流程进度
                        Boolean processMessage = processTaskClient.completeByProcessInstanceId(orderFlowVo.getProcessInstanceId(), variables);
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
            return buildErrorMsg(e, "/eam/open/repair/order");
        }
    }

    /**
     * 报修工单-执行记录 (已废弃)
     *
     * @param processInstanceId 报修工单-流程编号
     * @return EnerbosMessage返回工单执行记录
     */
    @Deprecated
    @ApiOperation(value = "报修工单-执行记录", response = RepairOrderFlowRecordVo.class, notes = "返回数据统一包装在 EnerbosMessage.data", hidden = true)
    @RequestMapping(value = "/eam/open/repair/order/flow/history", method = RequestMethod.POST)
    public EnerbosMessage getRepairOrderFlowRecord(@ApiParam(value = "报修工单-流程编号", required = true) String processInstanceId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/flow/history, host: [{}:{}], service_id: {}, processInstanceId: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), processInstanceId, user);
            Assert.isTrue(StringUtils.isNotEmpty(processInstanceId), "流程编号不能为空！");
            List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
            List<RepairOrderFlowRecordVo> repairOrderFlowRecordVoList = Objects.isNull(historicTaskVoList) ? Collections.EMPTY_LIST :
                    historicTaskVoList.stream()
                            .sorted(Comparator.comparing(HistoricTaskVo::getStartTime, Comparator.reverseOrder()))
                            .map(historicTaskVo -> {
                                RepairOrderFlowRecordVo vo = new RepairOrderFlowRecordVo();
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
            return EnerbosMessage.createSuccessMsg(repairOrderFlowRecordVoList, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/flow/history");
        }
    }

    /**
     * 报修工单-获取工程类型对应的用户列表
     *
     * @param projectType  报修工单-项目类型
     * @param orgId            报修工单-组织ID
     * @param siteId            报修工单-站点ID
     * @return EnerbosMessage返回用户列表
     */
    @ApiOperation(value = "报修工单-获取工程类型对应的用户列表", response = PersonAndUserVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/projectType/userList", method = RequestMethod.POST)
    public EnerbosMessage getRepairOrderProjectTypeUserList(@ApiParam(value = "报修工单-项目类型", required = true) String projectType,
                                                            @ApiParam(value = "报修工单-组织ID", required = true) String orgId,
                                                            @ApiParam(value = "报修工单-站点ID", required = true) String siteId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/repair/order/projectType/userList, host: [{}:{}], service_id: {}, projectType: {}, Principal: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), projectType, user);
            Assert.isTrue(StringUtils.isNotEmpty(projectType), "项目类型不能为空！");

            List<PersonAndUserVoForList> userVoList = getRepairOrderManageUserList(projectType, orgId, siteId, Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            return Objects.isNull(userVoList) ? EnerbosMessage.createErrorMsg("500", "未查询到项目类型对应的用户组。", "") : userVoList.isEmpty() ? EnerbosMessage.createErrorMsg("500", "报修工单工程类型对应分派组下没有人员，请联系管理员添加。", "") : EnerbosMessage.createSuccessMsg(userVoList, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/projectType/userList");
        }
    }

    /**
     * 报修工单-工单提报
     *
     * @param repairOrderFlowVo 报修工单-提报VO
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage createRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo) {
        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderNum()), "工单编号不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getDescription()), "报修描述不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getProjectType()), "工程类型不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderSource()), "工单来源不能为空。");

            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getReportPersonId()), "提报人不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getOrgId()), "所属组织不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getSiteId()), "所属站点不能为空。");

            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(repairOrderFlowVo.getReportPersonId());
            Assert.notNull(personVo, "未知提报人！");

            SiteVoForDetail siteVo = siteClient.findById(repairOrderFlowVo.getSiteId());
            Assert.notNull(siteVo, "未知站点！");
            Assert.isTrue(siteVo.getOrgId().equals(repairOrderFlowVo.getOrgId()), "所属组织错误！");

            repairOrderFlowVo.setReportPersonName(personVo.getName());
            if (Objects.isNull(repairOrderFlowVo.getReportDate())) {
                repairOrderFlowVo.setReportDate(new Date());
            }

            Map<String, Object> variables = new HashMap<>();
            //未保存工单提报时，先进行保存
            if (StringUtils.isEmpty(repairOrderFlowVo.getWorkOrderId())) {
                repairOrderFlowVo = repairOrderClient.createOrUpdateRepairOrder(repairOrderFlowVo);
            }


            if (StringUtils.isEmpty(repairOrderFlowVo.getProcessInstanceId())) {
                //流程key,key为维保固定前缀+站点code
                String processKey = String.format("%s%s", RepairOrderCommon.REPAIR_ORDER_WFS_PROCESS_KEY, siteVo.getCode());
                logger.debug("/eam/open/repair/order/flow/create, processKey: {}", processKey);

                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(repairOrderFlowVo.getWorkOrderId());
                processVo.setProcessKey(processKey);
                processVo.setUserId(repairOrderFlowVo.getReportPersonId());

                variables.put("startUserId", repairOrderFlowVo.getReportPersonId());
                variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
                variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER, repairOrderFlowVo.getReportPersonId());
                variables.put(Common.ORDER_NUM, repairOrderFlowVo.getWorkOrderNum());
                variables.put(Common.ORDER_DESCRIPTION, repairOrderFlowVo.getDescription());
                variables.put("status", RepairOrderCommon.REPAIR_ORDER_STATUS_DTB);
                processVo = workflowClient.startProcess(variables, processVo);


                if (null == processVo || "".equals(processVo.getProcessInstanceId())) {
                    //repairOrderClient.deleteRepairOrder(Collections.singletonList(repairOrderFlowVo.getWorkOrderId()));
                    return EnerbosMessage.createErrorMsg(null, "流程启动失败", null);
                } else {
                    //更新流程ID
                    repairOrderFlowVo.setProcessInstanceId(processVo.getProcessInstanceId());
                    repairOrderClient.saveRepairOrder(repairOrderFlowVo);
                }
            }

            boolean ignoreDispatchFlow = false;
            if (repairOrderAutoDispatchConfig.getSiteMap().containsKey(repairOrderFlowVo.getSiteId())) {
                ignoreDispatchFlow = repairOrderAutoDispatchConfig.getSiteMap().get(repairOrderFlowVo.getSiteId()).stream().anyMatch(repairOrderFlowVo.getProjectType()::equals);
            }

            //查询用户组主管列表
            List<String> personList;
            if (!ignoreDispatchFlow && repairOrderFlowVo.getReportAssignFlag()) {
                personList = Collections.singletonList(repairOrderFlowVo.getReportPersonId());
            } else {
                personList = new ArrayList<>();
                if (!getRepairOrderManageUserList(repairOrderFlowVo, personList)) {
                    return EnerbosMessage.create("报修工单分派组下没有人员,请联系管理员添加!!", "500", "", false, repairOrderFlowVo);
                }
            }


            //设置是否需要跳过分派流程
            repairOrderFlowVo.setIgnoreDispatchFlow(ignoreDispatchFlow);
            List<String> executionPersonList = new ArrayList<>();

            if (ignoreDispatchFlow) {
                //自动分派时，去掉提报人派单选项
                repairOrderFlowVo.setReportAssignFlag(false);

                //获取分派执行组成员
                if (!getRepairOrderUserListByType(repairOrderFlowVo, executionPersonList, RepairOrderCommon.USERGROUP_ASSOCIATION_TYPE_EXECUTE)) {
                    return EnerbosMessage.create("报修工单执行组下没有人员,请联系管理员添加!!", "500", "", false, repairOrderFlowVo);
                }
                List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                for (String id : executionPersonList) {
                    orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, id));
                }
                //更新执行人以及委托执行人数据
                rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                //提交前人员相关数据为空，则回滚标注数据清空
                if (rollbackDataList.isEmpty()) {
                    rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, null));
                }

                //从值班分派组获取第一个用户作为此次分派人
                repairOrderFlowVo.setDispatchPersonId(personList.get(0));
                repairOrderFlowVo.setDispatchTime(new Date());
            }

            //工单提报
            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);

            variables.clear();
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, StringUtils.join(personList, ","));
            variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
            variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", RepairOrderCommon.REPAIR_ORDER_STATUS_DFP);
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            if (ignoreDispatchFlow) {
                variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, repairOrderFlowVo.getDispatchPersonId());
                variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_RECEIVE_USER, StringUtils.join(executionPersonList, ","));
                variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                variables.put("userId", repairOrderFlowVo.getDispatchPersonId());
                variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
                variables.put("description", repairOrderFlowVo.getProcessDescription());

                //更新流程进度
                processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
                if (Objects.isNull(processMessage) || !processMessage) {
                    throw new EnerbosException("500", "流程操作异常。");
                }
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo, repairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, String.format("工单提报成功%s", ignoreDispatchFlow ? "，并自动分派至相关人员处理。" : ""), "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            //流程提交失败时，将工单状态恢复成待提报
            if (StringUtils.isNotEmpty(repairOrderFlowVo.getWorkOrderId()) &&
                    !RepairOrderCommon.REPAIR_ORDER_STATUS_DTB.equals(repairOrderFlowVo.getWorkOrderStatus())) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("ids", Collections.singletonList(repairOrderFlowVo.getWorkOrderId()));
                paramsMap.put("status", RepairOrderCommon.REPAIR_ORDER_STATUS_DTB);
                repairOrderClient.changeRepairOrderStatus(paramsMap);
                repairOrderFlowVo.setWorkOrderStatus(RepairOrderCommon.REPAIR_ORDER_STATUS_DTB);
            }
            //将工单数据返回前台，避免提报失败导致必须要删除工单
            EnerbosMessage message = buildErrorMsg(e, "/eam/open/repair/order/flow/create");
            message.setData(StringUtils.isEmpty(repairOrderFlowVo.getWorkOrderId()) ? null : repairOrderFlowVo);
            return message;
        }
    }

    /**
     * 报修工单-任务分派
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage assignRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            //查询用户组主管列表
            List<String> personList;
            if (StringUtils.isNotEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
                personList = Collections.singletonList(oldRepairOrderFlowVo.getDispatchPersonId());
            } else {
                if (oldRepairOrderFlowVo.getReportAssignFlag()) {
                    personList = Collections.singletonList(oldRepairOrderFlowVo.getReportPersonId());
                } else {
                    personList = new ArrayList<>();
                    oldRepairOrderFlowVo.setOperatorPersonId(repairOrderFlowVo.getOperatorPersonId());
                    oldRepairOrderFlowVo.setOperatorPerson(repairOrderFlowVo.getOperatorPerson());
                    if (!getRepairOrderManageUserList(oldRepairOrderFlowVo, personList)) {
                        return EnerbosMessage.createErrorMsg("500", "报修工单分派组下没有人员,请联系管理员添加!!", "");
                    }
                }
            }

            final String personId = repairOrderFlowVo.getOperatorPersonId();
            if (personList.stream().noneMatch(personId::equals)) {
                return EnerbosMessage.createErrorMsg("401", "无权限分派！", "");
            }

            String message;
            Map<String, Object> variables = new HashMap<>();
            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                    Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getExecutionPersonId()), "执行人不能为空。");
                    String[] executionPersonIdArray = repairOrderFlowVo.getExecutionPersonId().split(",");

                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : executionPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "未知执行人！");
                        orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, id));
                    }

                    if (repairOrderFlowVo.getEntrustExecute()) {
                        Assert.isTrue(StringUtils.isNotEmpty(repairOrderFlowVo.getEntrustExecutePersonId()), "委托执行人不能为空。");

                        String[] entrustExecutionPersonIdArray = repairOrderFlowVo.getEntrustExecutePersonId().split(",");

                        for (String id : entrustExecutionPersonIdArray) {
                            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                            Assert.notNull(personVo, "未知委托执行人！");
                            orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION, id));
                        }
                    } else {
                        //提报时未选择委托人，则移除历史数据
                        List<OrderPersonVo> entrustExecutionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION));

                        if (Objects.nonNull(entrustExecutionPersonList) && !entrustExecutionPersonList.isEmpty()) {
                            rollbackDataList.addAll(entrustExecutionPersonList);
                            orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION);
                        }
                    }

                    repairOrderFlowVo.setDispatchPersonId(repairOrderFlowVo.getOperatorPersonId());
                    //repairOrderFlowVo.setDispatchPersonName(user.getName());
                    repairOrderFlowVo.setDispatchTime(new Date());
                    message = "任务分派成功";

                    //更新执行人以及委托执行人数据
                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                    //提交前人员相关数据为空，则回滚标注数据清空
                    Map<String, BigDecimal> map = rollbackDataList.isEmpty() ? new HashMap<>() : rollbackDataList.stream().collect(Collectors.toMap(OrderPersonVo::getFieldType, o -> BigDecimal.ONE, BigDecimal::add));
                    if (!map.containsKey(RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION)) {
                        rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, null));
                    }
                    if (!map.containsKey(RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION)) {
                        rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION, null));
                    }

                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_RECEIVE_USER, StringUtils.join(orderPersonVoList.stream().map(OrderPersonVo::getPersonId).toArray(), ","));
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    if (Common.SYSTEM_USER.equals(oldRepairOrderFlowVo.getReportPersonId())
                            && RepairOrderCommon.SYSTEM_AUTO.equals(oldRepairOrderFlowVo.getWorkOrderSource())) {
                        return EnerbosMessage.createErrorMsg("500", "系统报警生成工单无法驳回，请选择取消操作！", "");
                    }
                    message = "驳回成功";

                    //移除执行人数据
                    repairOrderFlowVo.setExecutionPersonId(null);
                    repairOrderFlowVo.setExecutionPerson(null);
                    repairOrderFlowVo.setExecutionWorkGroup(null);
                    List<OrderPersonVo> executionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION));

                    if (Objects.nonNull(executionPersonList) && !executionPersonList.isEmpty()) {
                        rollbackDataList.addAll(executionPersonList);
                        orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION);
                    } else {
                        rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION, null));
                    }

                    //移除委托执行人数据
                    repairOrderFlowVo.setEntrustExecute(Boolean.FALSE);
                    repairOrderFlowVo.setEntrustExecutePersonId(null);
                    repairOrderFlowVo.setEntrustExecutePerson(null);
                    List<OrderPersonVo> entrustExecutionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION));

                    if (Objects.nonNull(entrustExecutionPersonList) && !entrustExecutionPersonList.isEmpty()) {
                        rollbackDataList.addAll(entrustExecutionPersonList);
                        orderPersonClient.deleteOrderPersonByOrderIdAndFieldType(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION);
                    } else {
                        rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION, null));
                    }

                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL: {
                    message = "工单取消成功";

                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
            variables.put("description", repairOrderFlowVo.getProcessDescription());

            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/assign");
        }
    }

    /**
     * 报修工单-确认接单&驳回
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage receiveRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
        }
        List<OrderPersonVo> personList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION));
        personList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION)));

        final String personId = repairOrderFlowVo.getOperatorPersonId();
        if (personList.stream().noneMatch(vo -> personId.equals(vo.getPersonId()))) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        Map<String, Object> variables = new HashMap<>();
        try {
            String message;
            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                    //确认接单后，进入待汇报环节
                    message = "接单成功";

                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, repairOrderFlowVo.getOperatorPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    //清理当前环节数据
                    message = "驳回成功";

                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, repairOrderFlowVo.getDispatchPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_RECEIVE_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());

            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/receive");
        }
    }

    /**
     * 报修工单-执行汇报
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage reportRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getReceivePersonId()) || StringUtils.isEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
        }
        if (!oldRepairOrderFlowVo.getReceivePersonId().equals(repairOrderFlowVo.getOperatorPersonId())) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        Map<String, Object> variables = new HashMap<>(16);
        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            String message;
            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {

                    //确认汇报后，进入待验收环节
                    Assert.notNull(repairOrderFlowVo.getActualExecutionPersonId(), "实际执行人不能为空！");
                    Assert.notNull(repairOrderFlowVo.getCompletionTime(), "请填写完成时间！");
                    Assert.notNull(repairOrderFlowVo.getConsumeHours(), "请填写工时耗时！");

                    repairOrderFlowVo.setSuspension(Boolean.FALSE);
                    repairOrderFlowVo.setSuspensionType(null);

                    String[] actualExecutionPersonIdArray = repairOrderFlowVo.getActualExecutionPersonId().split(",");

                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : actualExecutionPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "实际执行人未知！");
                        orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION, id));
                    }

                    //更新实际执行人数据
                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                    if (rollbackDataList.isEmpty()) {
                        rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION, null));
                    }

                    message = "汇报成功";
                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ACCEPTOR_USER, oldRepairOrderFlowVo.getDispatchPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    //进入申请挂起环节
                    Assert.notNull(repairOrderFlowVo.getSuspensionType(), "请选择挂起类型！");
                    repairOrderFlowVo.setSuspension(Boolean.TRUE);

                    message = "申请挂起成功";
                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUSPEND_APPLY_USER, oldRepairOrderFlowVo.getDispatchPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }
            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/report");
        }
    }

    /**
     * 报修工单-申请挂起
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage applyForWaitRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getReceivePersonId()) || StringUtils.isEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
        }
        if (!oldRepairOrderFlowVo.getDispatchPersonId().equals(repairOrderFlowVo.getOperatorPersonId())) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        Map<String, Object> variables = new HashMap<>(16);
        try {
            String message;

            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                    message = "挂起成功";
                    //供应商维修挂起，进入待验收环节
                    if (Common.GYSWX.equals(oldRepairOrderFlowVo.getSuspensionType())) {
                        variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ACCEPTOR_USER, oldRepairOrderFlowVo.getDispatchPersonId());
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_PROCESS_STATUS_GYS_PASS, Boolean.TRUE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    } else {
                        variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUSPEND_USER, oldRepairOrderFlowVo.getReceivePersonId());
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_PROCESS_STATUS_GYS_PASS, Boolean.FALSE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    }
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    message = "驳回成功";

                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, oldRepairOrderFlowVo.getReceivePersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_PROCESS_STATUS_GYS_PASS, Boolean.FALSE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUSPEND_APPLY_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/applyForWait");
        }
    }

    /**
     * 报修工单-挂起
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage processWaitRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        //#EAMI-35 挂起环节操作权限改为接单人处理
        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getReceivePersonId()) || StringUtils.isEmpty(oldRepairOrderFlowVo.getDispatchPersonId())) {
            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
        }
        if (!oldRepairOrderFlowVo.getReceivePersonId().equals(repairOrderFlowVo.getOperatorPersonId())) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        try {
            String message;
            Map<String, Object> variables = new HashMap<>(16);
            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                    message = "操作成功";

                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, oldRepairOrderFlowVo.getDispatchPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    message = "驳回成功";

                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, oldRepairOrderFlowVo.getDispatchPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUSPEND_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/processWait");
        }
    }

    /**
     * 报修工单-申请验收
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage acceptRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        if (StringUtils.isEmpty(oldRepairOrderFlowVo.getDispatchPersonId()) || StringUtils.isEmpty(oldRepairOrderFlowVo.getReportPersonId()) || StringUtils.isEmpty(oldRepairOrderFlowVo.getReceivePersonId())) {
            return EnerbosMessage.createErrorMsg("500", "数据错误，请确认工单状态！", "");
        }
        if (!oldRepairOrderFlowVo.getDispatchPersonId().equals(repairOrderFlowVo.getOperatorPersonId())) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }

        List<OrderPersonVo> rollbackDataList = new ArrayList<>();
        try {
            //是否生成维保工单
            boolean createMaintenanceWorkOrder = false;
            String message;
            Map<String, Object> variables = new HashMap<>(16);
            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                    Assert.notNull(repairOrderFlowVo.getAcceptTime(), "请填写验收时间！");
                    Assert.notNull(repairOrderFlowVo.getAcceptPersonId(), "请选择验收人！");
                    Assert.notNull(repairOrderFlowVo.getConfirm(), "请确认工单是否解决！");

                    createMaintenanceWorkOrder = Objects.isNull(repairOrderFlowVo.getCreateMaintenanceWorkOrder()) ? false : repairOrderFlowVo.getCreateMaintenanceWorkOrder();
                    if (!createMaintenanceWorkOrder && !repairOrderFlowVo.getConfirm()) {
                        return EnerbosMessage.createErrorMsg("500", "工单尚未解决，请确认解决后再进行验收！", "");
                    }

                    String[] acceptorPersonIdArray = repairOrderFlowVo.getAcceptPersonId().split(",");

                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : acceptorPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "验收人未知！");
                        orderPersonVoList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR, id));
                    }

                    //更新验收人数据
                    rollbackDataList.addAll(orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList));
                    if (rollbackDataList.isEmpty()) {
                        rollbackDataList.add(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR, null));
                    }
                    //验收通过后，进入验收待确认环节
                    message = "验收成功";

                    //进入验收待确认环节
                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_CONFIRM_ACCEPTOR_USER, oldRepairOrderFlowVo.getReportPersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);

                    boolean isClose = RepairOrderCommon.SYSTEM_AUTO.equals(oldRepairOrderFlowVo.getWorkOrderSource())
                            || (oldRepairOrderFlowVo.getSuspension() && Common.GYSWX.equals(oldRepairOrderFlowVo.getSuspensionType()))
                            || (createMaintenanceWorkOrder && !repairOrderFlowVo.getConfirm());
                    if (isClose) {
                        //1.如果工单来源为系统自动或供应商维修挂起，则不需要提报人最终确认，流程提前结束
                        //2.如果工单未解决并且选择生成维保工单，则进入生成维保工单流程，本工单关闭
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                        variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                    }
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    //清理数据
                    repairOrderFlowVo.setAcceptTime(null);
                    repairOrderFlowVo.setConfirm(null);
                    repairOrderFlowVo.setAcceptDescription(null);

                    message = "驳回成功";
                    variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER, oldRepairOrderFlowVo.getReceivePersonId());
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_ACCEPTOR_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            if (createMaintenanceWorkOrder && StringUtils.isEmpty(oldRepairOrderFlowVo.getMaintenanceWorkOrderId())) {
                final RepairOrderFlowVo finalRepairOrderFlowVo = repairOrderFlowVo;
                RepairOrderFlowVo result = CompletableFuture.completedFuture(codeGeneratorClient.getCodegenerator(finalRepairOrderFlowVo.getOrgId(),finalRepairOrderFlowVo.getSiteId(), Common.WORK_ORDER_MODEL_KEY))
                        .thenApplyAsync((code) -> {
                            MaintenanceWorkOrderForCommitVo commitVo = new MaintenanceWorkOrderForCommitVo();
                            Date now = new Date();
                            commitVo.setSiteId(finalRepairOrderFlowVo.getSiteId());
                            commitVo.setOrgId(finalRepairOrderFlowVo.getOrgId());
                            commitVo.setWorkOrderNum(code);
                            commitVo.setDescription(finalRepairOrderFlowVo.getDescription());
                            commitVo.setStatus(WorkOrderCommon.WORK_ORDER_STATUS_DTB);
                            commitVo.setStatusDate(now);
                            commitVo.setReportDate(now);
                            commitVo.setCreateDate(now);
                            commitVo.setCreateUser(finalRepairOrderFlowVo.getOperatorPersonId());
                            commitVo.setRepairId(finalRepairOrderFlowVo.getId());
                            commitVo.setRepairNum(finalRepairOrderFlowVo.getWorkOrderNum());
                            commitVo.setProjectType(finalRepairOrderFlowVo.getProjectType());
                            commitVo.setWorkType(Common.WORK_ORDER_WORK_TYPE_REPAIR);
                            commitVo.setIncidentLevel(finalRepairOrderFlowVo.getIncidentLevel());
                            commitVo.setReportId(finalRepairOrderFlowVo.getOperatorPersonId());
                            commitVo.setReportName(finalRepairOrderFlowVo.getReportPersonName());

                            return maintenanceWorkOrderClient.saveWorkOrderCommit(commitVo);
                        })
                        .thenApplyAsync(commitVo -> {
                            finalRepairOrderFlowVo.setMaintenanceWorkOrderId(commitVo.getId());
                            finalRepairOrderFlowVo.setMaintenanceWorkOrderNum(commitVo.getWorkOrderNum());
                            return repairOrderClient.saveRepairOrder(finalRepairOrderFlowVo);
                        })
                        .exceptionally(throwable -> {
                            logger.error("生成维保工单失败，失败原因：{}", throwable.getMessage());
                            return null;
                        }).join();

                if (result != null) {
                    repairOrderFlowVo.setMaintenanceWorkOrderId(result.getMaintenanceWorkOrderId());
                    repairOrderFlowVo.setMaintenanceWorkOrderNum(result.getMaintenanceWorkOrderNum());
                }
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚操作人员数据
            if (!rollbackDataList.isEmpty()) {
                orderPersonClient.updateOrderPersonByOrderIdAndFieldType(rollbackDataList);
            }

            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/accept");
        }
    }

    /**
     * 报修工单-验收确认
     *
     * @param repairOrderFlowVo    报修工单-流程VO
     * @param oldRepairOrderFlowVo 报修工单-原始数据
     * @return EnerbosMessage返回执行码及数据
     */
    EnerbosMessage closeRepairOrderFlow(RepairOrderFlowVo repairOrderFlowVo, RepairOrderFlowVo oldRepairOrderFlowVo) {
        if (!oldRepairOrderFlowVo.getReportPersonId().equals(repairOrderFlowVo.getOperatorPersonId())) {
            return EnerbosMessage.createErrorMsg("401", "无权限操作！", "");
        }
        try {
            String message;
            Map<String, Object> variables = new HashMap<>(16);
            switch (repairOrderFlowVo.getProcessStatus()) {
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS: {
                    //确认验收后，流程结束
                    message = "确认验收成功，流程结束。";
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                case RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT: {
                    message = "驳回成功";
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                }
                break;
                default:
                    throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", repairOrderFlowVo.getWorkOrderStatus()));
            }

            repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);
            variables.put(RepairOrderCommon.REPAIR_ORDER_ACTIVITY_ASSIGNEE_CONFIRM_ACCEPTOR_USER, repairOrderFlowVo.getOperatorPersonId());
            variables.put("userId", repairOrderFlowVo.getOperatorPersonId());
            variables.put("description", repairOrderFlowVo.getProcessDescription());
            variables.put("status", repairOrderFlowVo.getWorkOrderStatus());
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(repairOrderFlowVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }

            //更新Vo展示内容
            buildRepairOrderFlowVo(repairOrderFlowVo);
            this.sendPush(repairOrderFlowVo,oldRepairOrderFlowVo.getWorkOrderStatus());
            return EnerbosMessage.createSuccessMsg(repairOrderFlowVo, message, "");
        } catch (Exception e) {
            //回滚工单状态
            rollbackRepairOrder(oldRepairOrderFlowVo, repairOrderFlowVo);

            return buildErrorMsg(e, "/eam/open/repair/order/flow/close");
        }
    }

    /**
     * 回滚工单数据
     *
     * @param oldRepairOrderFlowVo 原始数据
     * @param repairOrderFlowVo    新数据
     */
    private void rollbackRepairOrder(RepairOrderFlowVo oldRepairOrderFlowVo, RepairOrderFlowVo repairOrderFlowVo) {
        if (Objects.nonNull(oldRepairOrderFlowVo) && !oldRepairOrderFlowVo.getWorkOrderStatus().equals(repairOrderFlowVo.getWorkOrderStatus())) {
            //回滚工单数据
            repairOrderClient.saveRepairOrder(oldRepairOrderFlowVo);
/*
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("ids", Collections.singletonList(oldRepairOrderFlowVo.getWorkOrderId()));
            paramsMap.put("status", oldRepairOrderFlowVo.getWorkOrderStatus());
            repairOrderClient.changeRepairOrderStatus(paramsMap);
            repairOrderFlowVo.setWorkOrderStatus(oldRepairOrderFlowVo.getWorkOrderStatus());
*/
        }
    }

    /**
     * 获取工单工程类型下主管用户组
     *
     * @param repairOrderFlowVo 流程Vo
     * @param userList          主管用户Person ID列表
     * @return 操作成功或失败
     */
    private boolean getRepairOrderManageUserList(RepairOrderFlowVo repairOrderFlowVo, List<String> userList) {
        return getRepairOrderUserListByType(repairOrderFlowVo, userList, Common.USERGROUP_ASSOCIATION_TYPE_ALL);
    }

    /**
     * 根据指定类型获取工单工程类型下用户组
     *
     * @param repairOrderFlowVo 流程Vo
     * @param userList           维修工用户Person ID列表
     * @param associationType   关联类型
     * @return 操作成功或失败
     */
    private boolean getRepairOrderUserListByType(RepairOrderFlowVo repairOrderFlowVo, List<String> userList, String associationType) {
        List<PersonAndUserVoForList> userVoList = getRepairOrderManageUserList(repairOrderFlowVo.getProjectType(), repairOrderFlowVo.getOrgId(), repairOrderFlowVo.getSiteId(), associationType);
        if (Objects.isNull(userVoList)) {
            return false;
        } else {
            userVoList.stream().map(PersonAndUserVoForList::getPersonId).forEach(userList::add);
        }
        return true;
    }

    private List<PersonAndUserVoForList> getRepairOrderManageUserList(String projectType, String orgId, String siteId, String associationType) {
        UgroupVoForDetail ugroupVoForDetail;
        UserGroupDomainVo vo = userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(projectType,
                RepairOrderCommon.REPAIR_ORDER_PROJECT_TYPE, orgId, siteId, StringUtils.isEmpty(associationType) ? Common.USERGROUP_ASSOCIATION_TYPE_ALL : associationType);
        if (Objects.nonNull(vo)) {
            ugroupVoForDetail = ugroupClient.findById(vo.getUserGroupId());
        } else {
            String userGroup = String.format("%s_%s", RepairOrderCommon.REPAIR_ORDER_PROJECT_TYPE_PREFIX, projectType);
            ugroupVoForDetail = ugroupClient.findByCodeAndOrgIdAndSiteIdAndProductId(userGroup, orgId, siteId, Common.EAM_PROD_IDS[0]);
        }

        if (Objects.isNull(ugroupVoForDetail) || Objects.isNull(ugroupVoForDetail.getUsers())) {
            return null;
        } else {
            return ugroupVoForDetail.getUsers();
        }
    }


    private EnerbosMessage buildErrorMsg(Exception exception, String uri) {
        logger.error(String.format("------- %s --------", uri), exception);
        String message, statusCode = "";
        if (exception instanceof HystrixRuntimeException) {
            Throwable fallbackException = exception.getCause();
            if (fallbackException.getMessage().contains("{")) {
                message = fallbackException.getMessage().substring(fallbackException.getMessage().indexOf("{"));
                try {
                    JSONObject jsonMessage = JSONObject.parseObject(message);
                    if (jsonMessage != null) {
                        statusCode = jsonMessage.get("status").toString();
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

    private void buildRepairOrderFlowVo(RepairOrderFlowVo repairOrderFlowVo) {
        SiteVoForDetail siteVo = siteClient.findById(repairOrderFlowVo.getSiteId());
        repairOrderFlowVo.setSiteName(siteVo.getName());
        repairOrderFlowVo.setOrgName(siteVo.getOrgName());

        if (StringUtils.isNotEmpty(repairOrderFlowVo.getReportPersonId()) && StringUtils.isEmpty(repairOrderFlowVo.getReportPersonName())) {
            PersonAndUserVoForDetail reportPerson = personAndUserClient.findByPersonId(repairOrderFlowVo.getReportPersonId());
            repairOrderFlowVo.setReportPersonName(Objects.isNull(reportPerson) ? null : reportPerson.getName());
            //repairOrderFlowVo.setReportPersonTel(Objects.isNull(reportPerson) ? null : reportPerson.getMobile());
        }

        if (StringUtils.isNotEmpty(repairOrderFlowVo.getDispatchPersonId()) && StringUtils.isEmpty(repairOrderFlowVo.getDispatchPersonName())) {
            PersonAndUserVoForDetail dispatchPerson = personAndUserClient.findByPersonId(repairOrderFlowVo.getDispatchPersonId());
            repairOrderFlowVo.setDispatchPersonName(Objects.isNull(dispatchPerson) ? null : dispatchPerson.getName());
        }

        if (StringUtils.isNotEmpty(repairOrderFlowVo.getReceivePersonId()) && StringUtils.isEmpty(repairOrderFlowVo.getReceivePerson())) {
            PersonAndUserVoForDetail receivePerson = personAndUserClient.findByPersonId(repairOrderFlowVo.getReceivePersonId());
            repairOrderFlowVo.setReceivePerson(Objects.isNull(receivePerson) ? null : receivePerson.getName());
        }

        List<OrderPersonVo> executionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION));
        if (!executionPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = executionPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            repairOrderFlowVo.setExecutionPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            repairOrderFlowVo.setExecutionPerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
            List<String> workGroupList = tmp.stream().map(PersonAndUserVoForDetail::getWorkgroup).distinct().collect(Collectors.toList());
            if (workGroupList.size() == 1) {
                repairOrderFlowVo.setExecutionWorkGroup(workGroupList.get(0));
            }
        }
        List<OrderPersonVo> entrustExecutionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION));
        if (!entrustExecutionPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = entrustExecutionPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            repairOrderFlowVo.setEntrustExecutePersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            repairOrderFlowVo.setEntrustExecutePerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
        }

        List<OrderPersonVo> actualExecutionPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACTUAL_EXECUTION));
        if (!actualExecutionPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = actualExecutionPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            repairOrderFlowVo.setActualExecutionPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            repairOrderFlowVo.setActualExecutionPerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
            List<String> workGroupList = tmp.stream().map(PersonAndUserVoForDetail::getWorkgroup).distinct().collect(Collectors.toList());
            if (workGroupList.size() == 1) {
                repairOrderFlowVo.setActualWorkGroup(workGroupList.get(0));
            }
        }

        List<OrderPersonVo> acceptPersonList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ACCEPTOR));
        if (!acceptPersonList.isEmpty()) {
            List<PersonAndUserVoForDetail> tmp = acceptPersonList.stream().map(orderPersonVo -> personAndUserClient.findByPersonId(orderPersonVo.getPersonId())).filter(Objects::nonNull).collect(Collectors.toList());
            repairOrderFlowVo.setAcceptPersonId(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getPersonId).toArray(), ","));
            repairOrderFlowVo.setAcceptPerson(StringUtils.join(tmp.stream().map(PersonAndUserVoForDetail::getName).toArray(), ","));
        }

        //加载拥有操作权限的人员列表
        switch (repairOrderFlowVo.getWorkOrderStatus()) {
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB: {
                if (StringUtils.isNotEmpty(repairOrderFlowVo.getReportPersonId())) {
                    repairOrderFlowVo.setAuthPersonList(Collections.singletonList(repairOrderFlowVo.getReportPersonId()));
                }
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR: {
                repairOrderFlowVo.setAuthPersonList(Collections.singletonList(repairOrderFlowVo.getReportPersonId()));
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP: {
                //如果是驳回工单，只有原分派人有权限进行分派操作
                if (StringUtils.isNotEmpty(repairOrderFlowVo.getDispatchPersonId())) {
                    repairOrderFlowVo.setAuthPersonList(Collections.singletonList(repairOrderFlowVo.getDispatchPersonId()));
                } else {
                    if (repairOrderFlowVo.getReportAssignFlag() != null && repairOrderFlowVo.getReportAssignFlag()) {
                        repairOrderFlowVo.setAuthPersonList(Collections.singletonList(repairOrderFlowVo.getReportPersonId()));
                    } else {
                        List<String> personList = new ArrayList<>();
                        boolean flag = getRepairOrderManageUserList(repairOrderFlowVo, personList);
                        repairOrderFlowVo.setAuthPersonList(flag ? personList : null);
                    }
                }
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DJD: {
                List<OrderPersonVo> personList = orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_EXECUTION));
                personList.addAll(orderPersonClient.findOrderListByFilter(new OrderPersonVo(repairOrderFlowVo.getWorkOrderId(), RepairOrderCommon.REPAIR_ORDER_PERSON_ENTRUST_EXECUTION)));
                repairOrderFlowVo.setAuthPersonList(personList.stream().map(OrderPersonVo::getPersonId).collect(Collectors.toList()));
            } break;
            //#EAMI-35 挂起环节操作权限改为接单人处理
            case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT:
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB: {
                repairOrderFlowVo.setAuthPersonList(repairOrderFlowVo.getReceivePersonId() == null ? Collections.emptyList() : Collections.singletonList(repairOrderFlowVo.getReceivePersonId()));
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ:
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS: {
                //申请挂起、待验收环节只有分派人有权限处理
                repairOrderFlowVo.setAuthPersonList(repairOrderFlowVo.getDispatchPersonId() == null ? Collections.emptyList() : Collections.singletonList(repairOrderFlowVo.getDispatchPersonId()));
            } break;
            default: break;
        }
    }

    //APP推送
    private void  sendPush(RepairOrderFlowVo workOrderFlowVo,String oederStatus){
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
        String workType="报修工单";
        String status=null;
        switch (oederStatus){
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DTB:{
                status="分派";
                //推送消息给App端
                pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
            } break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DFP:{
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    status="接单";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                    status="提报";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DJD:{
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    status="执行";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                    status="分派";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DHB:{
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    status="验收";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {//进入申请挂起
                    status="确认挂起";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_DYS:{
                status="确认验收";
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒："+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                    status="执行";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_YS_DQR:{
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    status="验收完成";
                    pushTitle = "请验收"+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                    status="验收";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT_SQ:{
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    status="验收";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                    status="执行";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
            case RepairOrderCommon.REPAIR_ORDER_STATUS_WAIT:{
                if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_PASS.equals(workOrderFlowVo.getProcessStatus())){
                    status="执行";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }else if(RepairOrderCommon.REPAIR_ORDER_PROCESS_STATUS_REJECT.equals(workOrderFlowVo.getProcessStatus())) {
                    status="分派";
                    pushTitle = "请"+status+""+workType+",编号:" + workOrderFlowVo.getWorkOrderNum();
                    pushContent = "编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    buildUpContent = "工作提醒：请"+status+"<"+workType+">编号:" + workOrderFlowVo.getWorkOrderNum() + ",描述:" + workOrderFlowVo.getDescription();
                    pushService.sendPush(person, pushTitle, pushContent, getExtraMap(workOrderFlowVo), buildUpContent);
                }
            }break;
        }
    }

    private Map getExtraMap(RepairOrderFlowVo workOrderFlowVo) {
        Map extras = new HashMap<>();
        extras.put("getBusinessDataByCurrentUser", "true");
        extras.put("workorderid", workOrderFlowVo.getId());
        extras.put("notificationType", RepairOrderCommon.REPAIR_ORDER_WFS_PROCESS_KEY
        );
        return extras;
    }
}
