package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.CodeGeneratorClient;
import com.enerbos.cloud.eam.client.HeadquartersDailyClient;
import com.enerbos.cloud.eam.client.HeadquartersDaliyTaskClient;
import com.enerbos.cloud.eam.client.OrderPersonClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DispatchWorkOrderCommon;
import com.enerbos.cloud.eam.contants.HeadquartersDailyTaskCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForList;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import com.enerbos.cloud.wfs.vo.TaskForFilterVo;
import com.enerbos.cloud.wfs.vo.TaskVo;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.swagger.annotations.*;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 张鹏伟
 * @version 1.0
 * @date 2017/8/16 12:28
 * @Description  例行工作单--控制层
 */
@RestController
@Api(description = "例行工作单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class HeadquartersDaliyTaskController {

    private Logger logger = LoggerFactory.getLogger(HeadquartersDaliyTaskController.class);

    @Autowired
    private HeadquartersDaliyTaskClient headquartersDaliyTaskClient;
    @Autowired
    private CodeGeneratorClient codeGeneratorClient;

    @Autowired
    private UgroupClient ugroupClient;
    @Autowired
    private SiteClient siteClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private WorkflowClient workflowClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private OrderPersonClient orderPersonClient;



    @Autowired
    private HeadquartersDailyClient headquartersDailyClient;


    @Autowired
    private  UserGroupDomainColler userGroupDomainColler;





    /**
     * 工作单--分页、筛选、排序
     * @param filter
     * @return
     */
    @ApiOperation(value = "例行工作单--分页、筛选、排序", response = HeadquartersDailyTaskVoForFilter.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/headquartersDailyTask/findPageList", method = RequestMethod.GET)
    public EnerbosMessage findPageList( HeadquartersDailyTaskVoForFilter filter,Principal user){
        try {
            EnerbosPage<HeadquartersDailyTaskVo> pageInfo=new EnerbosPage<>();
            if(StringUtils.isEmpty(filter.getSiteId())&&StringUtils.isEmpty(filter.getOrgId())){
                return EnerbosMessage.createSuccessMsg(pageInfo, "查询成功", "");
            }
            pageInfo = headquartersDaliyTaskClient.findPageList(filter);

            pageInfo.getList().forEach(vo -> {
                if (StringUtils.isNotEmpty(vo.getSiteId())) {
                    SiteVoForDetail siteVo=siteClient.findById(vo.getSiteId());
                    vo.setSiteName(siteVo.getName());
                   vo.setOrgName(siteVo.getOrgName());
                }
                if (StringUtils.isNotEmpty(vo.getPlanId())) {
                    HeadquartersDailyVo dailyVo=headquartersDailyClient.findDetail(vo.getPlanId());
                    if(dailyVo!=null){
                        vo.setPlanName(dailyVo.getPlanName());
                        vo.setPlanNum(dailyVo.getPlanNum());
                    }
                }
            });
            return EnerbosMessage.createSuccessMsg(pageInfo, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDailyTask/findPageList ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
    /**
     * 保存
     * @param filter
     * @return
     */
    @ApiOperation(value = "保存例行工作单", response = HeadquartersDailyTaskVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDailyTask/save", method = RequestMethod.POST)
    public EnerbosMessage save(@ApiParam(value = "保存例行工作单-VO", required = true) HeadquartersDailyTaskVo filter,Principal user){
        try {
            Assert.isTrue(StringUtils.isNotEmpty(filter.getTaskNum()), "工单编号不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(filter.getTaskName()), "任务名称不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(filter.getTaskProperty()), "任务属性不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(filter.getCheckItem()), "检查项不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(filter.getWorkType()), "工作类型不能为空。");
          //  Assert.isTrue(filter.getEstimateDate()!=null, "计划完成时间不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(filter.getOrgId()), "所属组织不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(filter.getSiteId()), "所属站点不能为空。");
            SiteVoForDetail siteVo = siteClient.findById(filter.getSiteId());
            Assert.notNull(siteVo, "未知站点！");
            Assert.isTrue(siteVo.getOrgId().equals(filter.getOrgId()), "所属组织错误！");


            HeadquartersDailyTaskVo   olddailyTaskVo=new HeadquartersDailyTaskVo();
            if(filter.getId()==null){
                String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
                filter.setCreateUser(userId);
                filter.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB);
                filter.setCreateDate(new Date());
            }else{

               olddailyTaskVo=headquartersDaliyTaskClient.findDetail(filter.getId());
                filter.setReportPersonId(olddailyTaskVo.getReportPersonId());
                filter.setDispatchPersonId(olddailyTaskVo.getDispatchPersonId());
                filter.setProcessInstanceId(olddailyTaskVo.getProcessInstanceId());
               // filter.setExecutorTel(olddailyTaskVo.getExecutorTel());
            }
            switch (filter.getStatus()) {
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB: {
                    Assert.isTrue(StringUtils.isEmpty(filter.getExecutor()), "待提报状态不能指派负责人。");
                    Assert.isTrue(StringUtils.isEmpty(filter.getSummary()), "待提报状态不能填写执行总结。");
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP: {
                    Assert.isTrue(StringUtils.isNotEmpty(filter.getId()), "数据状态异常。");
                    Assert.isTrue(StringUtils.isNotEmpty(filter.getExecutor()), "待分派状态需要指派负责人。");
                    Assert.isTrue(StringUtils.isEmpty(filter.getSummary()), "待分派状态不能填写执行总结。");


                    String[] executionPersonIdArray =  filter.getExecutor().split(",");
                    List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
                    for (String id : executionPersonIdArray) {
                        PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
                        Assert.notNull(personVo, "未知执行人！");
                        orderPersonVoList.add(new OrderPersonVo(filter.getId(), HeadquartersDailyTaskCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
                    }
                    orderPersonVoList = orderPersonClient.updateOrderPersonByOrderIdAndFieldType(orderPersonVoList);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DJD: {//接单人等于执行负责人
                    Assert.isTrue(StringUtils.isNotEmpty(filter.getId()), "数据状态异常。");
                    Assert.isTrue(StringUtils.isEmpty(filter.getSummary()), "待分派状态不能填写执行总结。");

                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DHB: {
                    Assert.isTrue(StringUtils.isNotEmpty(filter.getId()), "数据状态异常。");
                    Assert.isTrue(StringUtils.isNotEmpty(filter.getSummary()), "待汇报状态需要填写执行总结。");
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DYS: {
                    Assert.isTrue(StringUtils.isNotEmpty(filter.getId()), "数据状态异常。");
                } break;
                default:
                    EnerbosMessage.createErrorMsg("500", "工单状态异常", null);
            }
            if(olddailyTaskVo.getProcessInstanceId()!=null){
                filter.setProcessInstanceId(olddailyTaskVo.getProcessInstanceId());
            }
            if(olddailyTaskVo.getCreateDate()!=null){
                filter.setCreateUser(olddailyTaskVo.getCreateUser());
                filter.setCreateDate(olddailyTaskVo.getCreateDate());
                filter.setReceiverPersonId(olddailyTaskVo.getReceiverPersonId());
            }
            HeadquartersDailyTaskVo vo = headquartersDaliyTaskClient.save(filter);
            return EnerbosMessage.createSuccessMsg(vo, "保存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDailyTask/save ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除", notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDailyTask/delete", method = RequestMethod.POST)
    public EnerbosMessage delete( @ApiParam(value = "需要删除的ID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String ids){
        try {

            String[] id=ids.split(",");
            Boolean res =false;
            for (String taskId : id) {
                HeadquartersDailyTaskVo taskVo=   headquartersDaliyTaskClient.findDetail(taskId);
                if(!HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB.equals(taskVo.getStatus())&&!HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_SUCCESS.equals(taskVo.getStatus())){
                    return EnerbosMessage.createErrorMsg("500", "非待提报或已关闭例行工作单不能删除!!", "");
                }
                 res = headquartersDaliyTaskClient.batchDelete(ids);
                // orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
            }
            if(id.length>1){
                return EnerbosMessage.createSuccessMsg(res, "批量删除成功", "");
            }else{
                return EnerbosMessage.createSuccessMsg(res, "删除成功", "");
            }

        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDailyTask/delete ------", e);
            return EnerbosMessage.createErrorMsg("500", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 查询详细页
     * @param id
     * @return
     */
    @ApiOperation(value = "例行工作单详细", response = HeadquartersDailyTaskVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDailyTask/findDetail", method = RequestMethod.GET)
    public  EnerbosMessage findDetail(@RequestParam("id") String id){
        try {
            HeadquartersDailyTaskVo res = headquartersDaliyTaskClient.findDetail(id);
            if (StringUtils.isNotEmpty(res.getPlanId())) {
                HeadquartersDailyVo dailyVo=headquartersDailyClient.findDetail(res.getPlanId());
                res.setPlanName(dailyVo.getPlanName());
                res.setPlanNum(dailyVo.getPlanNum());
            }

//            List<String> personId = new ArrayList<String>();
//            if(StringUtils.isNotEmpty(res.getExecutor())){//执行负责人
//                String[] person=res.getExecutor().split(",");
//                Arrays.stream(person).forEach(
//                        p->{
//                            PersonAndUserVoForDetail personAndUserVoForDetail =  personAndUserClient.findByPersonId(p);
//                            personId.add(personAndUserVoForDetail.getName());
//                        }
//                );
//                res.setExecutorName(org.apache.commons.lang.StringUtils.join(personId, ","));
//            }

            List<OrderPersonVo> orderPersonVos=orderPersonClient.findOrderListByFilter(new OrderPersonVo(id,HeadquartersDailyTaskCommon.DISPATCH_ORDER_PERSON_EXECUTION));
            List<PersonAndUserVoForDetail> getExecutorName=new ArrayList<>();

            orderPersonVos.stream().forEach(v->{
                PersonAndUserVoForDetail p=personAndUserClient.findByPersonId(v.getPersonId());
                getExecutorName.add(p);
            });
            String joinPersonName=null;
            String joinPersonId=null;
            if(getExecutorName.size()>0){
                 joinPersonName=getExecutorName.stream().map(PersonAndUserVoForDetail::getName).collect(Collectors.toList()).stream().reduce((a, b) -> a +"," +b).get();
                 joinPersonId=orderPersonVos.stream().map(OrderPersonVo::getPersonId).collect(Collectors.toList()).stream().reduce((a, b) -> a +"," +b).get();
            }
            res.setExecutorName(joinPersonName);
            res.setExecutor(joinPersonId);
            if(StringUtils.isNotEmpty(res.getDispatchPersonId())){//分派人
                PersonAndUserVoForDetail personAndUserVoForDetail =  personAndUserClient.findByPersonId(res.getDispatchPersonId());
                res.setDispatchPersonName(personAndUserVoForDetail.getName());
            }
            if(StringUtils.isNotEmpty(res.getReceiverPersonId())){//接单人
                PersonAndUserVoForDetail personAndUserVoForDetail =  personAndUserClient.findByPersonId(res.getReceiverPersonId());
                res.setReceiverPersonName(personAndUserVoForDetail.getName());
            }
            if(StringUtils.isNotEmpty(res.getReportPersonId())){//提报人
                PersonAndUserVoForDetail personAndUserVoForDetail =  personAndUserClient.findByPersonId(res.getReportPersonId());
                res.setReportPersonName(personAndUserVoForDetail.getName());
            }

            if(StringUtils.isNotEmpty(res.getWorkType())){
                List<PersonAndUserVoForDetail> person=userGroupDomainColler.findUserByDomainValueORDomainNums(res.getWorkType(),HeadquartersDailyTaskCommon.DAILY_TASK_WORKTYPE,res.getOrgId(),res.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
                String  personIdJoin=null;
                String  personNameJoin=null;
                if(person!=null&&person.size()!=0){
                      personIdJoin=  person.stream().map(PersonAndUserVoForDetail::getPersonId).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();
                      personNameJoin=  person.stream().map(PersonAndUserVoForDetail::getName).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();
                }
                res.setDispatchUserGroupPersonId(personIdJoin);
                res.setDispatchUserGroupPersonName(personNameJoin);
            }
            return EnerbosMessage.createSuccessMsg(res, "查询详细页成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDailyTask/findDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
    /**
     * 修改状态
     * @param
     * @return@ApiParam(value = "派工工单ID列表", required = true) String[] ids, @ApiParam(value = "工单状态", required = true) String status, Principal user
     */
    @ApiOperation(value = "修改状态", notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDailyTask/upStrtus", method = RequestMethod.POST)
    public EnerbosMessage upStrtus(
            @ApiParam(value = "ID列表", required = true) @RequestParam("ids")String[] ids,
            @ApiParam(value = "工单状态", required = true) @RequestParam("status")String status,
            Principal user){
        try {
            Boolean res = headquartersDaliyTaskClient.upStatus(ids,status);
            if(ids.length>1){
                return EnerbosMessage.createSuccessMsg(res, "批量修改状态成功", "");
            }else{
                return EnerbosMessage.createSuccessMsg(res, "修改状态成功", "");
            }

        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDailyTask/upStrtus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 流程提交
     * @param
     * @return
     */
    @ApiOperation(value = "流程提交", response = HeadquartersDailyTaskVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDailyTask/flow/commit", method = RequestMethod.POST)
    public EnerbosMessage commit(@ApiParam(value = "例行工作单id", required = true) @RequestParam("id") String id,
                                 @ApiParam(value = "流程状态，同意/驳回", required = true) @RequestParam(name="processStatus",required = true) String processStatus,
                                 @ApiParam(value = "流程说明", required = true) @RequestParam("processDescription") String description, Principal user){
        try {


            EnerbosMessage message=new EnerbosMessage();

            HeadquartersDailyTaskVo   dailyTaskVo=headquartersDaliyTaskClient.findDetail(id);


            dailyTaskVo.setProcessDescription(description);
            dailyTaskVo.setProcessStatus(processStatus);
            if(dailyTaskVo==null){
                return EnerbosMessage.createErrorMsg("500", "数据不存在!!", "");
            }
//            if(message!=null){
//                return  message;
//            }
                switch (dailyTaskVo.getStatus()) {
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB: {

                    dailyTaskVo.setProcessInstanceId(dailyTaskVo.getProcessInstanceId());
                    message=createDailyTaskFlow(dailyTaskVo,user);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP: {
                    message = dailyTaskFlowAssign(dailyTaskVo,user);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DJD: {//接单人等于执行负责人
                    message=dailyTaskFlowOrders(dailyTaskVo,user);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DHB: {
                    message=dailyTaskFlowReporting(dailyTaskVo,user);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DYS: {
                    message=dailyTaskFlowCheck(dailyTaskVo,user);
                } break;
                    default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dailyTaskVo.getStatus()));

            }
            return message;
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dispatch/order/flow/report");
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



    /**
     * 例行工作单-工单提报
     * @param dailyTaskVo 例行工作单-VO
     * @return EnerbosMessage返回执行码及数据
     */
    private EnerbosMessage createDailyTaskFlow(HeadquartersDailyTaskVo dailyTaskVo,Principal user) {
        try {
            Map<String, Object> variables = new HashMap<>();
            SiteVoForDetail siteVo = siteClient.findById(dailyTaskVo.getSiteId());
            Assert.notNull(siteVo, "未知站点！");
            Assert.isTrue(siteVo.getOrgId().equals(dailyTaskVo.getOrgId()), "所属组织错误！");
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if (StringUtils.isEmpty(dailyTaskVo.getProcessInstanceId())) {
                //流程key,key为维保固定前缀+站点code
                String processKey = String.format("%s%s",HeadquartersDailyTaskCommon.DAILY_TASK_WFS_PROCESS_KEY,siteVo.getCode());
                ProcessVo processVo = new ProcessVo();
                processVo.setBusinessKey(dailyTaskVo.getId());
                processVo.setProcessKey(processKey);
                processVo.setUserId(personId);
                variables.put("startUserId", personId);
                variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_SUBMIT_USER, personId);
                variables.put(Common.ORDER_NUM, dailyTaskVo.getTaskNum());
                variables.put("userId", personId);
                variables.put("status", dailyTaskVo.getStatus());
                variables.put(Common.ORDER_DESCRIPTION, dailyTaskVo.getTaskName());
                variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB);
                //variables.put("taskNum", dailyTaskVo.getTaskNum());
              //  variables.put("description", "流程启动");
                processVo = workflowClient.startProcess(variables, processVo);

                if (Objects.isNull(processVo) || StringUtils.isEmpty(processVo.getProcessInstanceId())) {
                    //dispatchWorkOrderClient.deleteDispatchWorkOrder(Collections.singletonList(dispatchWorkOrderFlowVo.getWorkOrderId()));
                    return EnerbosMessage.createErrorMsg(null, "流程启动失败", null);
                } else {
                    //更新流程ID
                    dailyTaskVo.setProcessInstanceId(processVo.getProcessInstanceId());
                    dailyTaskVo.setReportPersonId(personId);
                    headquartersDaliyTaskClient.save(dailyTaskVo);
                }
            }


            List<PersonAndUserVoForDetail> person=userGroupDomainColler.findUserByDomainValueORDomainNums(dailyTaskVo.getWorkType(),"workType",dailyTaskVo.getOrgId(),dailyTaskVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);

            String  personIdJoin=  person.stream().map(PersonAndUserVoForDetail::getPersonId).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();
           // //工单提报
         //   dispatchWorkOrderFlowVo = dispatchWorkOrderClient.commitDispatchWorkOrder(dispatchWorkOrderFlowVo);
            variables.clear();
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER, dailyTaskVo.getReportPersonId());
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, personIdJoin);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
            variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
            variables.put("description", dailyTaskVo.getProcessDescription());
            variables.put("status", dailyTaskVo.getStatus());
            variables.put("userId", personId);
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dailyTaskVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }else{
                dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP);
            }
            headquartersDaliyTaskClient.save(dailyTaskVo);
            return EnerbosMessage.createSuccessMsg(dailyTaskVo, "例行工单提报成功", "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            EnerbosMessage message = buildErrorMsg(e, "流程提交失败");
          //  message.setData(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()) ? null : dispatchWorkOrderFlowVo);
            return message;
        }
    }
    /**
     * 例行工单-分派
     * @param dailyTaskVo
     * @return
     */
    private EnerbosMessage dailyTaskFlowAssign(HeadquartersDailyTaskVo dailyTaskVo,Principal user) {
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            dailyTaskVo.setDispatchPersonId(personId);
//            //查询当前用户是否在一个用户组，如果不在，返回无权限
//            if(!dailyTaskVo.getExecutor().equals(personId)){
//                return EnerbosMessage.createErrorMsg("401", "无操作权限!!", "");
//            }
            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail sitevo= siteClient.findById(dailyTaskVo.getSiteId());
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(sitevo.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(dailyTaskVo.getProcessInstanceId());
            List<TaskVo> page=processTaskClient.findTasks(taskForFilterVo).getList();
            if (StringUtils.isNotBlank(dailyTaskVo.getProcessInstanceId())&&(null==page||page.size()<1)){
                return EnerbosMessage.createSuccessMsg("401", "无权操作此工单", "");
            }

            Map<String, Object> variables = new HashMap<String, Object>();
            String message;
            dailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
            dailyTaskVo.setProcessStatus(dailyTaskVo.getProcessStatus());
            dailyTaskVo.setProcessDescription(dailyTaskVo.getProcessDescription());




//            String[] executionPersonIdArray =  dailyTaskVo.getExecutor().split(",");
//            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
//            for (String id : executionPersonIdArray) {
//                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(id);
//                Assert.notNull(personVo, "未知执行人！");
//                // orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
//            }
            switch (dailyTaskVo.getProcessStatus()) {
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS: {//确认
                    List<OrderPersonVo> orderPersonVos=orderPersonClient.findOrderListByFilter(new OrderPersonVo(dailyTaskVo.getId(),HeadquartersDailyTaskCommon.DISPATCH_ORDER_PERSON_EXECUTION));
                    String  joinPersonId=null;
                    if(orderPersonVos.size()>0){
                        joinPersonId=orderPersonVos.stream().map(OrderPersonVo::getPersonId).collect(Collectors.toList()).stream().reduce((a, b) -> a +"," +b).get();

                    }
                    Assert.isTrue(StringUtils.isNotEmpty(joinPersonId), "执行负责人不能为空。");
                    message = "分派成功";
                    dailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.clear();
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ASSIGN_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ORDERS_USER, joinPersonId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DJD);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DJD);
                    dailyTaskVo.setDispatchPersonId(personId);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT: {//驳回

                   if(Common.SYSTEM_USER.equals(dailyTaskVo.getCreateUser())){
                       throw new EnerbosException("500", "系统生成工单待分派节点不能驳回!");
                   }

                    message = "分派驳回成功";
                  //  oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ASSIGN_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_SUBMIT_USER, dailyTaskVo.getReportPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL: {//取消
                    message = "分派取消成功";
                    //oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ASSIGN_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE);
                } break;

                default: throw new EnerbosException("500", String.format("未支持流程。当前流程状态：[%s]", dailyTaskVo.getStatus()));
            }
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dailyTaskVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            headquartersDaliyTaskClient.save(dailyTaskVo);
            return EnerbosMessage.createSuccessMsg(dailyTaskVo, message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            EnerbosMessage message = buildErrorMsg(e, "流程提交失败");
            //  message.setData(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()) ? null : dispatchWorkOrderFlowVo);
            return message;
        }
    }
    /**
     * 例行工单-接单
     * @param dailyTaskVo
     * @return
     */
    private EnerbosMessage dailyTaskFlowOrders(HeadquartersDailyTaskVo dailyTaskVo,Principal user) {
        try {
            String message;
            Map<String, Object> variables = new HashMap<>();

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            SiteVoForDetail site=siteClient.findById(dailyTaskVo.getSiteId());
            if (site == null||null==site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(personId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(10);
            taskForFilterVo.setProcessInstanceId(dailyTaskVo.getProcessInstanceId());
            List<TaskVo> page=processTaskClient.findTasks(taskForFilterVo).getList();
            if (org.apache.commons.lang.StringUtils.isNotBlank(dailyTaskVo.getProcessInstanceId())&&(null==page||page.size()<1)){
                return EnerbosMessage.createErrorMsg("401", "无权操作此工单", "");
            }
//           if(!dailyTaskVo.getExecutor().contains(personId)){
//               return EnerbosMessage.createErrorMsg("401", "无操作权限!!", "");
//           }
//            String[] executionPersonIdArray =  dailyTaskVo.getExecutor().split(",");
//            List<OrderPersonVo> orderPersonVoList = new ArrayList<>();
          //  PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);

//            for (String id : executionPersonIdArray) {
//                PersonVo personVo = personClient.findOne(id);
//                Assert.notNull(personVo, "未知执行人！");
//                 orderPersonVoList.add(new OrderPersonVo(dispatchWorkOrderFlowVo.getWorkOrderId(), DispatchWorkOrderCommon.DISPATCH_ORDER_PERSON_EXECUTION, id));
//            }
            List<PersonAndUserVoForDetail> person=userGroupDomainColler.findUserByDomainValueORDomainNums(dailyTaskVo.getWorkType(),HeadquartersDailyTaskCommon.DAILY_TASK_WORKTYPE,dailyTaskVo.getOrgId(),dailyTaskVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);

            String  personIdJoin=  person.stream().map(PersonAndUserVoForDetail::getPersonId).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();

            dailyTaskVo.setProcessStatus(dailyTaskVo.getProcessStatus());
            dailyTaskVo.setProcessDescription(dailyTaskVo.getProcessDescription());

            switch (dailyTaskVo.getProcessStatus()) {
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS: {//确认
                    message = "接单成功";
                    dailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ORDERS_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_REPORT_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DHB);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DHB);
                    dailyTaskVo.setReceiverPersonId(personId);//接单人
                   // dailyTaskVo.setExecutorTel(personVo.getMobile());

                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT: {//驳回
                    message = "接单驳回成功";
                    //  oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ORDERS_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ASSIGN_USER, dailyTaskVo.getDispatchPersonId());

                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL: {//取消
                    message = "接单取消成功";
                    //oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ORDERS_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE);
                } break;

                default: throw new EnerbosException("500", String.format("未支持流程。"));
            }

            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dailyTaskVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }else{

            }
            headquartersDaliyTaskClient.save(dailyTaskVo);
            return EnerbosMessage.createSuccessMsg(dailyTaskVo, message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            EnerbosMessage message = buildErrorMsg(e, "流程提交失败");
            //  message.setData(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()) ? null : dispatchWorkOrderFlowVo);
            return message;
        }
    }
    /**
     * 例行工单-执行汇报
     * @param dailyTaskVo
     * @return
     */
    private EnerbosMessage dailyTaskFlowReporting(HeadquartersDailyTaskVo dailyTaskVo,Principal user) {
        try {
            String message;
            Map<String, Object> variables = new HashMap<>();

//            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
//            if(!dailyTaskVo.getExecutor().contains(personId)){
//                return EnerbosMessage.createErrorMsg("401", "无操作权限!!", "");
//            }
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site=siteClient.findById(dailyTaskVo.getSiteId());
            if (site == null||null==site.getCode()) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(personId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setPageNum(0);
            taskForFilterVo.setPageSize(30);
            taskForFilterVo.setProcessInstanceId(dailyTaskVo.getProcessInstanceId());
            List<TaskVo> page=processTaskClient.findTasks(taskForFilterVo).getList();
            if (org.apache.commons.lang.StringUtils.isNotBlank(dailyTaskVo.getProcessInstanceId())&&(null==page||page.size()<1)){
                return EnerbosMessage.createErrorMsg("401", "无权操作此工单", "");
            }
            dailyTaskVo.setSummary(dailyTaskVo.getSummary());
            dailyTaskVo.setProcessStatus(dailyTaskVo.getProcessStatus());
            dailyTaskVo.setProcessDescription(dailyTaskVo.getProcessDescription());
            List<PersonAndUserVoForDetail> person=userGroupDomainColler.findUserByDomainValueORDomainNums(dailyTaskVo.getWorkType(),HeadquartersDailyTaskCommon.DAILY_TASK_WORKTYPE,dailyTaskVo.getOrgId(),dailyTaskVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);

            String  personIdJoin=  person.stream().map(PersonAndUserVoForDetail::getPersonId).collect(Collectors.toList()).stream().reduce((a,b) -> a +"," +b).get();

            switch (dailyTaskVo.getProcessStatus()) {
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS: {//确认
                    message = "执行汇报成功";
                    dailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_REPORT_USER, dailyTaskVo.getReceiverPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_CHECK_USER, dailyTaskVo.getDispatchPersonId());

                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DYS);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DYS);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT: {//驳回

                    message = "执行汇报驳回成功";
                    //  oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_REPORT_USER, dailyTaskVo.getDispatchPersonId());//dailyTaskVo.getReceiverPersonId()
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_ASSIGN_USER, dailyTaskVo.getDispatchPersonId());

                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP);
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL: {//取消
                    message = "执行汇报取消成功";
                    //oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_REPORT_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_CANCEL, Boolean.TRUE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE);
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程。"));
            }
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dailyTaskVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            headquartersDaliyTaskClient.save(dailyTaskVo);
            return EnerbosMessage.createSuccessMsg(dailyTaskVo, message, "");
        } catch (Exception e) {
            //流程提交失败时，将工单状态恢复成待提报
            EnerbosMessage message = buildErrorMsg(e, "流程提交失败");
            //  message.setData(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()) ? null : dispatchWorkOrderFlowVo);
            return message;
        }
    }
    /**
     * 例行工单-验收
     * @param dailyTaskVo
     * @return
     */
    private EnerbosMessage dailyTaskFlowCheck(HeadquartersDailyTaskVo dailyTaskVo ,Principal user) {
        try {
            String message;
            Map<String, Object> variables = new HashMap<>();
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if(!dailyTaskVo.getDispatchPersonId().equals(personId)){
                return EnerbosMessage.createErrorMsg("401", "无权限操作!!", "");
            }
            dailyTaskVo.setProcessStatus(dailyTaskVo.getProcessStatus());
            dailyTaskVo.setProcessDescription(dailyTaskVo.getProcessDescription());
            switch (dailyTaskVo.getProcessStatus()) {
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS: {//确认
                    message = "验收成功";
                    dailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_CHECK_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.TRUE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.FALSE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_SUCCESS);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_SUCCESS);
                    dailyTaskVo.setActualDate(new Date());
                } break;
                case HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT: {//驳回

                    message = "验收驳回成功";
                    //  oldHeadquartersDailyTaskVo.setExecutor(dailyTaskVo.getExecutor());
                    dailyTaskVo.setDispatchPersonId(personId);
                    //variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_CHECK_USER, dailyTaskVo.getDispatchPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_REPORT_USER, dailyTaskVo.getReceiverPersonId());
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_PASS, Boolean.FALSE);
                    variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_PROCESS_STATUS_REJECT, Boolean.TRUE);
                    variables.put("status", HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DHB);
                    variables.put("description", dailyTaskVo.getProcessDescription());
                    variables.put("userId", personId);
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DHB);
                } break;
                default: throw new EnerbosException("500", String.format("未支持流程。"));
            }
            //更新流程进度
            Boolean processMessage = processTaskClient.completeByProcessInstanceId(dailyTaskVo.getProcessInstanceId(), variables);
            if (Objects.isNull(processMessage) || !processMessage) {
                throw new EnerbosException("500", "流程操作异常。");
            }
            headquartersDaliyTaskClient.save(dailyTaskVo);
            return EnerbosMessage.createSuccessMsg(dailyTaskVo, message, "");
        } catch (Exception e) {
            EnerbosMessage message = buildErrorMsg(e, "流程提交失败");
            //  message.setData(StringUtils.isEmpty(dispatchWorkOrderFlowVo.getWorkOrderId()) ? null : dispatchWorkOrderFlowVo);
            return message;
        }
    }

    private EnerbosMessage CheckData(HeadquartersDailyTaskVo dailyTaskVo,HeadquartersDailyTaskVo oldDailyTaskVo){

        if(StringUtils.isEmpty(dailyTaskVo.getId())){
            return EnerbosMessage.createErrorMsg("500", "例行任务单不存在，请先保存例行任务单数据!!", "");
        }
        return null;
    }


}
