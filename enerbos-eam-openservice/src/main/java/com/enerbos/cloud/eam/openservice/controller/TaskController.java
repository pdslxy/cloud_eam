package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.vo.TaskCountVo;
import com.enerbos.cloud.wfs.vo.TaskForFilterVo;
import com.enerbos.cloud.wfs.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年07月21日
 * @Description EAM 我的任务接口
 */
@RestController
@Api(description = "我的任务(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    /**
     * findPageTaskToDoList: 分页查询全部我的待办任务
     * @param orderStatus  工单状态
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询全部我的待办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageTaskToDoList", method = RequestMethod.GET)
    public EnerbosMessage findPageTaskToDoList(@ApiParam(value = "工单类型", required = false) @RequestParam(value = "orderType",required = false) List<String> orderType,
                                               @ApiParam(value = "工单状态", required = false) @RequestParam(name="orderStatus", required = false) List<String> orderStatus,
                                               @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,
                                               @ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                               @ApiParam(value = "排序参数 asc升序/desc降序", required = true) @RequestParam(name="sorts", required = false) String sorts,
                                               @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                               @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageTaskToDoList, host: [{}:{}], service_id: {}, orderType: {},orderStatus:{},siteId: {},word: {},  sorts: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), orderType,orderStatus,siteId,word,sorts,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setStatus(orderStatus);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            taskForFilterVo.setProcessKey(orderType);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findTasks(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询全部我的待办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageTaskToDoList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageTaskToDoList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageTaskToDoList ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findTaskToDoListTotalGroupByOrderType: 按工单类型分类查询我的待办任务数量
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按工单类型分类查询我的待办任务数量", response = Map.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findTaskToDoListTotalGroupByOrderType", method = RequestMethod.GET)
    public EnerbosMessage findTaskToDoListTotalGroupByOrderType(@ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findTaskToDoListTotalGroupByOrderType, host: [{}:{}], service_id: {}, user: {},siteId: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), user,siteId);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            List<TaskCountVo> list=processTaskClient.findTaskCountGroupByProcessKey(taskForFilterVo);
            Map result=new HashMap();
            list.forEach(taskCount->{
                result.put(taskCount.getProcessKey(),taskCount.getTaskCount());
            });
            return EnerbosMessage.createSuccessMsg(result, "按工单类型分类查询我的待办任务数量成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findTaskToDoListTotalGroupByOrderType ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findTaskToDoListTotalGroupByOrderType----",jsonException);
            }
            logger.error("-----/eam/open/task/findTaskToDoListTotalGroupByOrderType ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageWorkOrderTaskToDoList: 分页查询维保工单我的待办任务
     * @param orderStatus  工单状态
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询维保工单我的待办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageWorkOrderTaskToDoList", method = RequestMethod.GET)
    public EnerbosMessage findPageWorkOrderTaskToDoList(@ApiParam(value = "工单状态", required = false) @RequestParam(name="orderStatus", required = false) List<String> orderStatus,
                                                        @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                        @ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                        @ApiParam(value = "排序参数 asc升序/desc降序", required = true) @RequestParam(name="sorts", required = false) String sorts,
                                                        @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                        @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageWorkOrderTaskToDoList, host: [{}:{}], service_id: {}, orderStatus: {}, siteId: {}, word: {}, sorts: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), orderStatus,siteId,word,sorts,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add(Common.WORK_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setStatus(orderStatus);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findTasks(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询维保工单我的待办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageWorkOrderTaskToDoList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageWorkOrderTaskToDoList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageWorkOrderTaskToDoList ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageRepairOrderTaskToDoList: 分页查询报修工单我的待办任务
     * @param orderStatus  工单状态
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询报修工单我的待办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageRepairOrderTaskToDoList", method = RequestMethod.GET)
    public EnerbosMessage findPageRepairOrderTaskToDoList(@ApiParam(value = "工单状态", required = false) @RequestParam(name="orderStatus", required = false) List<String> orderStatus,
                                                          @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                          @ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                          @ApiParam(value = "排序参数 asc升序/desc降序", required = true) @RequestParam(name="sorts", required = false) String sorts,
                                                          @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                          @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageRepairOrderTaskToDoList, host: [{}:{}], service_id: {}, orderStatus: {},siteId: {}, word: {}, sorts: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), orderStatus,siteId,word,sorts,pageNum,pageSize);

            List<String> processKeyList=new ArrayList<>();
            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add(Common.REPAIR_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setStatus(orderStatus);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findTasks(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询报修工单我的待办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageRepairOrderTaskToDoList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageRepairOrderTaskToDoList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageRepairOrderTaskToDoList ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageDispatchOrderTaskToDoList: 分页查询派工工单我的待办任务
     * @param orderStatus  工单状态
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询派工工单我的待办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageDispatchOrderTaskToDoList", method = RequestMethod.GET)
    public EnerbosMessage findPageDispatchOrderTaskToDoList(@ApiParam(value = "工单状态", required = false) @RequestParam(name="orderStatus", required = false) List<String> orderStatus,
                                                            @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                            @ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                            @ApiParam(value = "排序参数 asc升序/desc降序", required = true) @RequestParam(name="sorts", required = false) String sorts,
                                                            @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                            @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageDispatchOrderTaskToDoList, host: [{}:{}], service_id: {}, orderStatus: {},siteId: {}, word: {}, sorts: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), orderStatus,siteId,word,sorts,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add(Common.DISPATCH_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setStatus(orderStatus);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findTasks(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询派工工单我的待办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskToDoList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageDispatchOrderTaskToDoList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskToDoList ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageTaskDoneList: 分页查询我的经办任务
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param complete 流程是否是未结束,true未结束/false全部
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询我的经办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageTaskDoneList", method = RequestMethod.GET)
    public EnerbosMessage findPageTaskDoneList(@ApiParam(value = "工单类型", required = false) @RequestParam(value = "orderType",required = false) List<String> orderType,
                                               @ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                               @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                               @ApiParam(value = "排序参数 asc升序/desc降序", required = false) @RequestParam(name="sorts", required = false) String sorts,
                                               @ApiParam(value = "流程是否是未结束,true未结束/false全部", required = false,defaultValue="true") @RequestParam(name="complete", required = false,defaultValue="true") Boolean complete,
                                               @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                               @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageTaskDoneList, host: [{}:{}], service_id: {}, orderType:{},siteId: {},word: {}, sorts: {}, complete: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(),orderType, siteId,word,sorts,complete,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            taskForFilterVo.setProcessKey(orderType);
            taskForFilterVo.setComplete(complete);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findActHiTaskinst(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询我的经办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageTaskDoneList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageTaskDoneList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageTaskDoneList----",e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findTaskDoneCountGroupByOrderType: 按工单类型分组查询我的经办任务数量
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按工单类型分组查询我的经办任务数量", response = Map.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findTaskDoneCountGroupByOrderType", method = RequestMethod.GET)
    public EnerbosMessage findTaskDoneCountGroupByOrderType(@ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findTaskDoneCountGroupByOrderType, host: [{}:{}], service_id: {}, user:{},siteId: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), user,siteId);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createErrorMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            List<TaskCountVo> list=processTaskClient.findActHiTaskinstCountGroupByProcessKey(taskForFilterVo);
            Map result=new HashMap();
            list.forEach(taskCount->{
                result.put(taskCount.getProcessKey(),taskCount.getTaskCount());
            });
            return EnerbosMessage.createSuccessMsg(result, "按工单类型分组查询我的经办任务数量成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findTaskDoneCountGroupByOrderType ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findTaskDoneCountGroupByOrderType----",jsonException);
            }
            logger.error("-----/eam/open/task/findTaskDoneCountGroupByOrderType----",e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageWorkOrderTaskDoneList: 分页查询维保工单我的经办任务
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param complete 流程是否是未结束,true未结束/false全部
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询维保工单我的经办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageWorkOrderTaskDoneList", method = RequestMethod.GET)
    public EnerbosMessage findPageWorkOrderTaskDoneList(@ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                        @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                        @ApiParam(value = "排序参数 asc升序/desc降序", required = false) @RequestParam(name="sorts", required = false) String sorts,
                                                        @ApiParam(value = "流程是否是未结束,true未结束/false全部", required = false,defaultValue="true") @RequestParam(name="complete", required = false,defaultValue="true") Boolean complete,
                                                        @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                        @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageWorkOrderTaskDoneList, host: [{}:{}], service_id: {}, siteId: {},word: {}, sorts: {}, complete: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), siteId,word,sorts,complete,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add( Common.WORK_ORDER_WFS_PROCESS_KEY );
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            taskForFilterVo.setComplete(complete);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findActHiTaskinst(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询维保工单我的经办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageWorkOrderTaskDoneList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageWorkOrderTaskDoneList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageWorkOrderTaskDoneList----",e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageRepairOrderTaskDoneList: 分页查询报修工单我的经办任务
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param complete 流程是否是未结束,true未结束/false全部
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询报修工单我的经办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageRepairOrderTaskDoneList", method = RequestMethod.GET)
    public EnerbosMessage findPageRepairOrderTaskDoneList(@ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                          @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                          @ApiParam(value = "排序参数 asc升序/desc降序", required = false) @RequestParam(name="sorts", required = false) String sorts,
                                                          @ApiParam(value = "流程是否是未结束,true未结束/false全部", required = false,defaultValue="true") @RequestParam(name="complete", required = false,defaultValue="true") Boolean complete,
                                                          @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                          @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageRepairOrderTaskDoneList, host: [{}:{}], service_id: {}, siteId: {},word: {}, sorts: {}, complete: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), siteId,word,sorts,complete,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add(Common.REPAIR_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            taskForFilterVo.setComplete(complete);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findActHiTaskinst(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询报修工单我的经办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageRepairOrderTaskDoneList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageRepairOrderTaskDoneList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageRepairOrderTaskDoneList----",e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findPageDispatchOrderTaskDoneList: 分页查询派工工单我的经办任务
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param complete 流程是否是未结束,true未结束/false全部
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询派工工单我的经办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPageDispatchOrderTaskDoneList", method = RequestMethod.GET)
    public EnerbosMessage findPageDispatchOrderTaskDoneList(@ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                            @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                            @ApiParam(value = "排序参数 asc升序/desc降序", required = false) @RequestParam(name="sorts", required = false) String sorts,
                                                            @ApiParam(value = "流程是否是未结束,true未结束/false全部", required = false,defaultValue="true") @RequestParam(name="complete", required = false,defaultValue="true") Boolean complete,
                                                            @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                            @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageDispatchOrderTaskDoneList, host: [{}:{}], service_id: {}, siteId: {},word: {}, sorts: {}, complete: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), siteId,word,sorts,complete,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add( Common.DISPATCH_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            taskForFilterVo.setComplete(complete);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findActHiTaskinst(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询派工工单我的经办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskDoneList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageDispatchOrderTaskDoneList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskDoneList----",e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }


    /**
     * findPageDispatchOrderTaskDoneList: 分页查询派工工单我的经办任务
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param complete 流程是否是未结束,true未结束/false全部
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询巡检工单我的经办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPagePatrolOrderTaskDoneList", method = RequestMethod.GET)
    public EnerbosMessage findPagePatrolOrderTaskDoneList(@ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                          @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                          @ApiParam(value = "排序参数 asc升序/desc降序", required = false) @RequestParam(name="sorts", required = false) String sorts,
                                                          @ApiParam(value = "流程是否是未结束,true未结束/false全部", required = false,defaultValue="true") @RequestParam(name="complete", required = false,defaultValue="true") Boolean complete,
                                                          @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                          @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageDispatchOrderTaskDoneList, host: [{}:{}], service_id: {}, siteId: {},word: {}, sorts: {}, complete: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), siteId,word,sorts,complete,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add(Common.PATROL_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            taskForFilterVo.setComplete(complete);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findActHiTaskinst(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询巡检工单我的经办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskDoneList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageDispatchOrderTaskDoneList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskDoneList----",e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }


    /**
     * findPageDispatchOrderTaskToDoList: 分页查询派工工单我的待办任务
     * @param orderStatus  工单状态
     * @param word 模糊搜索关键词
     * @param sorts 排序参数 asc升序/desc降序
     * @param pageNum 当前页
     * @param pageSize 每页显示行数
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "分页查询巡检工单我的待办任务", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findPagePatrolOrderTaskToDoList", method = RequestMethod.GET)
    public EnerbosMessage findPagePatrolOrderTaskToDoList(@ApiParam(value = "工单状态", required = false) @RequestParam(name="orderStatus", required = false) List<String> orderStatus,
                                                          @ApiParam(value = "站点ID", required = true) @RequestParam(name="siteId", required = true) String siteId,
                                                          @ApiParam(value = "模糊搜索关键词", required = false) @RequestParam(name="word", required = false) String word,
                                                          @ApiParam(value = "排序参数 asc升序/desc降序", required = true) @RequestParam(name="sorts", required = false) String sorts,
                                                          @ApiParam(value = "当前页", required = false) @RequestParam(name="pageNum", required = false,defaultValue = "1") int pageNum,
                                                          @ApiParam(value = "每页显示行数", required = false) @RequestParam(name="pageSize", required = false,defaultValue = "10") int pageSize,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findPageDispatchOrderTaskToDoList, host: [{}:{}], service_id: {}, orderStatus: {},siteId: {}, word: {}, sorts: {}, pageNum: {}, pageSize: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), orderStatus,siteId,word,sorts,pageNum,pageSize);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            List<String> processKeyList=new ArrayList<>();
            SiteVoForDetail site=siteClient.findById(siteId);
            if (site == null||StringUtils.isBlank(site.getCode())) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            processKeyList.add(Common.PATROL_ORDER_WFS_PROCESS_KEY);
            TaskForFilterVo taskForFilterVo=new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setProcessKey(processKeyList);
            taskForFilterVo.setSiteCode(site.getCode());
            taskForFilterVo.setStatus(orderStatus);
            taskForFilterVo.setSorts(sorts);
            taskForFilterVo.setPageNum(pageNum);
            taskForFilterVo.setPageSize(pageSize);
            if (word!=null) {
                String[] words= StringUtils.split(word, " ");
                taskForFilterVo.setWordsList(Arrays.asList(words));
            }
            EnerbosPage<TaskVo> page=processTaskClient.findTasks(taskForFilterVo);
            return EnerbosMessage.createSuccessMsg(page, "分页查询巡检工单我的待办任务成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskToDoList ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findPageDispatchOrderTaskToDoList----",jsonException);
            }
            logger.error("-----/eam/open/task/findPageDispatchOrderTaskToDoList ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * findDomainValueValue: 根据域value查询域值，将所有域值当做域value查询所有域值
     * @param domainNum 域value
     * @param orgId 组织ID
     * @param siteId 站点ID
     * @param prodId 产品ID
     * @param user 用户
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据域value查询域值，将所有域值当做域value查询所有域值", response = TaskVo.class, notes="返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/task/findDomainValueValue", method = RequestMethod.GET)
    public EnerbosMessage findDomainValueValue(@ApiParam(value = "域值编码", required = true) @RequestParam("domainNum") String domainNum,
                                               @ApiParam(value = "组织ID", required = true) @RequestParam("orgId") String orgId,
                                               @ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId,
                                               @ApiParam(value = "产品ID", required = true) @RequestParam("prodId") String prodId,Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/task/findDomainValueValue, host: [{}:{}], service_id: {}, domainNum: {},orgId:{},siteId: {},prodId:{}", instance.getHost(), instance.getPort(), instance.getServiceId(), domainNum,orgId,siteId,prodId);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());

            List<FieldDomainValueVo> list=fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId(domainNum,siteId,orgId,prodId);
            Map<String,Object> result=new HashMap<>();
            Collection values;
            list.forEach(domainValue->{
                List<FieldDomainValueVo> domainValues=fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId(domainValue.getValue(),siteId,orgId,prodId);
                domainValues.forEach(value->result.put(value.getValue(),value));
            });
            return EnerbosMessage.createSuccessMsg(result.values(), "根据域value查询域值，将所有域值当做域value查询所有域值成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/task/findDomainValueValue ------", e);
            String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
            String statusCode  = "" ;
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if(jsonMessage !=null){
                    statusCode =   jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/task/findDomainValueValue----",jsonException);
            }
            logger.error("-----/eam/open/task/findDomainValueValue ------", e);
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

}
