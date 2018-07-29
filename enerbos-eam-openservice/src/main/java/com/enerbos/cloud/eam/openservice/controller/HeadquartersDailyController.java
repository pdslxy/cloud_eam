package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DispatchWorkOrderCommon;
import com.enerbos.cloud.eam.contants.HeadquartersDailyTaskCommon;
import com.enerbos.cloud.eam.openservice.config.TimedTask;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.tts.client.EamTimerTaskClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.util.ReflectionUtils;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import io.swagger.annotations.*;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/10 11:21
 * @Description  总部计划--控制层
 */
@RestController
@Api(description = "总部事物，例行工作")
public class HeadquartersDailyController {

    private Logger logger = LoggerFactory.getLogger(HeadquartersDailyController.class);

    @Autowired
    private HeadquartersDailyClient headquartersDailyClient;
    @Autowired
    private CodeGeneratorClient codeGeneratorClient;
    @Autowired
    private SiteClient siteClient;
    @Autowired
    private HeadquartersPlanClient headquartersPlanClient;
    @Autowired
    private EamTimerTaskClient eamTimerTaskClient;

    @Autowired
    private TimedTask timedTask;


    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private HeadquartersDaliyTaskClient headquartersDaliyTaskClient;

    @Resource
    private WorkflowClient workflowClient;


    @Autowired
    private UserGroupDomainClient userGroupDomainClient;


    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private ProcessTaskClient processTaskClient;


    /**
     * 总部事物，例行工作--分页、筛选、排序
     * @param filter
     * @return
     */
    @ApiOperation(value = "例行工作--分页、过滤vo", response = HeadquartersDailyVoForFilter.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/headquartersDaily/findPageList", method = RequestMethod.POST)
    public EnerbosMessage findPageList(@RequestBody HeadquartersDailyVoForFilter filter){
        try {
            if(StringUtils.isEmpty(filter.getOrgId())){
                return   EnerbosMessage.createErrorMsg("500","组织ID不能为空",null);
            }
            if(StringUtils.isEmpty(filter.getSiteId())){
                return   EnerbosMessage.createErrorMsg("500","站点ID不能为空",null);
            }
            EnerbosPage<HeadquartersDailyVo> pageInfo = headquartersDailyClient.findPageList(filter);

            pageInfo.getList().forEach(vo -> {
                if (StringUtils.isNotEmpty(vo.getHeadquartersPlanId())) {
                    HeadquartersPlanVo headquartersPlanVo=headquartersPlanClient.findDetailById(vo.getHeadquartersPlanId());
                      if(headquartersPlanVo!=null){
                          vo.setHeadquartersPlanNum(headquartersPlanVo.getPlanNum());
                          vo.setPlanDescription(headquartersPlanVo.getDescription());
                      }
                }

                if(StringUtils.isNotEmpty(vo.getSiteId())){
                    if(vo.getSiteId().equals(Common.WORK_DATA_ALL)){
                        vo.setSiteName("全部");
                    }else{
                        SiteVoForDetail siteVo = siteClient.findById(vo.getSiteId());
                        vo.setSiteName(siteVo.getName());
                    }
                }

            });
            return EnerbosMessage.createSuccessMsg(pageInfo, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDaily/findPageList ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }


    /**
     * 保存
     * @param filter
     * @return
     */
    @ApiOperation(value = "保存总部事物，例行工作", response = HeadquartersDailyVoForSave.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDaily/save", method = RequestMethod.POST)
    public EnerbosMessage save(@ApiParam(value = "保存总部事物，例行工作vo", required = true)@RequestBody HeadquartersDailyVoForSave filter,Principal user){
        HeadquartersDailyVoForSave vo=new HeadquartersDailyVoForSave();
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if(filter.getId()!=null&&!"".equals(filter.getId()) ){
                HeadquartersDailyVo vo1=headquartersDailyClient.findDetail(filter.getId());
                if(vo1==null){
                 return EnerbosMessage.createErrorMsg("500", "未知工单",null);
                }
                filter.setCreateDate(vo1.getCreateDate());
                filter.setCreateUser(vo1.getCreateUser());
             }else{
                filter.setCreateDate(new Date());
                filter.setCreateUser(personId);
            }
            vo = headquartersDailyClient.save(filter);
               if(vo!=null){
                   startEamMaintenancePlanTask(vo);
               }
            HeadquartersDailyVoForSave voForSave=headquartersDailyClient.save(vo);
            //判断是否是当天，如果是当天立刻生成例行工作单
            if(isToday(vo.getStartDate())){
                EnerbosMessage message=this.createHeadquartersDaily(voForSave);
                if(!message.isSuccess()){
                    return message;
                }
            }
            return EnerbosMessage.createSuccessMsg(vo, "例行工作保存成功", "");
        } catch (Exception e) {
            //如保存完毕，但是TTS调用出错，将删除保存数据
            if(StringUtils.isNotEmpty(vo.getId())){
                List<String> ids=new ArrayList<>();
                ids.add(vo.getId());
                headquartersDailyClient.batchDelete(ids);
            }
            logger.error("-----/eam/open/headquartersDaily/save ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
    //生成例行工作单并提交流程到待分派
    private EnerbosMessage createHeadquartersDaily(HeadquartersDailyVoForSave headquartersDailyVo){
        try {
            String siteId = headquartersDailyVo.getSiteId();
            String result="";
            SiteVoForDetail site=new SiteVoForDetail();
            if (org.apache.commons.lang.StringUtils.isNotBlank(siteId)) {
                site=siteClient.findById(headquartersDailyVo.getSiteId());
                if (site == null) {
                    logger.error("站点编码无效！siteId:{}",siteId);
                    return EnerbosMessage.createErrorMsg("500", "站点编码无效,例行工作单生成失败！", null);
                }
                result=codeGeneratorClient.getCodegenerator(site.getOrgId(),siteId, Common.DAILY_TASK_WFS_PROCESS_KEY);
                if (null==result||"".equals(result)) {
                    logger.error("编码生成规则内容读取失败！siteId:{},modelKey:{}",siteId,Common.DAILY_TASK_WFS_PROCESS_KEY);
                    return EnerbosMessage.createErrorMsg("500", "编码生成规则内容读取失败,例行工作单生成失败！", null);
                }
            }else {
                logger.error("站点为空！siteId:{}",siteId);
                return EnerbosMessage.createErrorMsg("500", "站点为空,例行工作单生成失败！", null);
            }
            HeadquartersDailyTaskVo vo=new HeadquartersDailyTaskVo();
            vo.setTaskName(headquartersDailyVo.getPlanName());
            vo.setTaskNum(result);
            vo.setPlanNum(headquartersDailyVo.getPlanNum());
            vo.setPlanId(headquartersDailyVo.getId());
            vo.setCheckItem(headquartersDailyVo.getCheckItem());
            vo.setWorkType(headquartersDailyVo.getWorkType());
            vo.setTaskProperty(headquartersDailyVo.getNature());
            vo.setSiteId(headquartersDailyVo.getSiteId());
            vo.setOrgId(headquartersDailyVo.getOrgId());
            vo.setCreateUser(Common.SYSTEM_USER);
            vo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DTB);
            Calendar estimateCalendar = Calendar.getInstance();
            estimateCalendar.add(Calendar.DAY_OF_MONTH, headquartersDailyVo.getDeadline().intValue());
            vo.setEstimateDate(estimateCalendar.getTime());
            HeadquartersDailyTaskVo dailyTaskVo =headquartersDaliyTaskClient.save(vo);
            if (dailyTaskVo == null|| org.apache.commons.lang.StringUtils.isBlank(dailyTaskVo.getId())) {
                logger.error("保存工单保存失败！, headquartersDailyTaskVo: {}",dailyTaskVo);
                return EnerbosMessage.createErrorMsg("500", "保存工单保存失败,例行工作单生成失败！", null);
            }
            //启动流程
            Map<String, Object> variables = new HashMap<>();
            String processKey = String.format("%s%s",HeadquartersDailyTaskCommon.DAILY_TASK_WFS_PROCESS_KEY,site.getCode());
            ProcessVo processVo = new ProcessVo();
            processVo.setBusinessKey(dailyTaskVo.getId());
            processVo.setProcessKey(processKey);
            processVo.setUserId(Common.SYSTEM_USER);
            variables.put("startUserId", Common.SYSTEM_USER);
            variables.put(HeadquartersDailyTaskCommon.DAILY_TASK_ACTIVITY_ASSIGNEE_SUBMIT_USER, Common.SYSTEM_USER);
            variables.put(Common.ORDER_NUM, dailyTaskVo.getTaskNum());
            variables.put("userId", Common.SYSTEM_USER);
            variables.put("description", "流程启动");
            processVo = workflowClient.startProcess(variables, processVo);
            //提报
            if(processVo==null){
                logger.info("--------例行工作生成例行工作单流程启动失败--------当前时间:{},dailyTaskVo:{},processVo:{}，variables：{},当前时间：{}",dailyTaskVo,processVo,variables,new Date());
                return EnerbosMessage.createErrorMsg("500", "因工单流程启动失败,例行工作单生成失败！", null);
            }else{//更新流程Id和状态
                //更新流程ID
                dailyTaskVo.setProcessInstanceId(processVo.getProcessInstanceId());
                dailyTaskVo.setReportPersonId(Common.SYSTEM_USER);
                headquartersDaliyTaskClient.save(dailyTaskVo);
            }
            if(processVo!=null){
                UserGroupDomainVo userGroupDomainVo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(dailyTaskVo.getWorkType(),Common.WORK_TYPE_DOMAIN,dailyTaskVo.getOrgId(),dailyTaskVo.getSiteId(),Common.USERGROUP_ASSOCIATION_TYPE_ALL);
                Assert.isTrue(!org.springframework.util.StringUtils.isEmpty(userGroupDomainVo),"例行工作单，下未分配分派组");
                UgroupVoForDetail voForDetail=  ugroupClient.findById(userGroupDomainVo.getUserGroupId());
                Assert.isTrue(!org.springframework.util.StringUtils.isEmpty(userGroupDomainVo),"例行工作单，分派组是未知的");
                List<PersonAndUserVoForDetail> detailList=new ArrayList<>();
                voForDetail.getUsers().stream().forEach(
                        u->{
                            PersonAndUserVoForDetail person= personAndUserClient.findByPersonId(u.getPersonId());
                            if(person!=null){//清空无关数据
                                person.setOrgs(null);
                                person.setSites(null);
                                person.setProducts(null);
                                person.setLevels(null);
                                person.setRoles(null);
                                detailList.add(person);
                            }
                        }
                );
                if(detailList==null||detailList.size()==0){
                    return EnerbosMessage.createErrorMsg("500", "例行工作单提报失败，该工单分派组没有关联分派人！", null);
                }
                String  personIdJoin=  detailList.stream().map(PersonAndUserVoForDetail::getPersonId).collect(Collectors.toList()).stream().reduce((a, b) -> a +"," +b).get();
                // //工单提报
                variables.clear();
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER, dailyTaskVo.getReportPersonId());
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER, personIdJoin);
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_PASS, Boolean.TRUE);
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_REJECT, Boolean.FALSE);
                variables.put(DispatchWorkOrderCommon.DISPATCH_ORDER_PROCESS_STATUS_CANCEL, Boolean.FALSE);
                variables.put("description", dailyTaskVo.getProcessDescription());
                variables.put("status", dailyTaskVo.getStatus());
                variables.put("userId", Common.SYSTEM_USER);
                //更新流程进度
                Boolean processMessage = processTaskClient.completeByProcessInstanceId(dailyTaskVo.getProcessInstanceId(), variables);
                if (Objects.isNull(processMessage) || !processMessage) {
                    logger.info("--------例行工作生成例行工作单流程提报失败--------当前时间:{},dailyTaskVo:{},processVo:{}，variables：{}",new Date(),dailyTaskVo,processVo,variables);
                    return EnerbosMessage.createErrorMsg("500", "例行工作生成例行工作单流程提报失败,例行工作单生成失败！", null);
                }else{
                    dailyTaskVo.setStatus(HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_DFP);
                }
                headquartersDaliyTaskClient.save(dailyTaskVo);
                HeadquartersDailyVoForSave headquartersDailyVoForSave=new HeadquartersDailyVoForSave();
                try {
                    ReflectionUtils.copyProperties(headquartersDailyVo, headquartersDailyVoForSave, null);
                    headquartersDailyClient.save(headquartersDailyVoForSave);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("例行工作生成例行工作单--保存失败");
                }
            }
            logger.info("--------例行工作生成例行工作单--------当前时间:{}",new Date());
            return EnerbosMessage.createSuccessMsg("null", "例行工作单生成功", null);
        }catch (Exception e){
            logger.info("--------例行工作生成例行工作单异常--------当前时间:{}",new Date());
            return EnerbosMessage.createErrorMsg("500", e.getMessage(), null);
        }


    }

    public  boolean isToday(Date date){
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){//格式化为相同格式
            return true;
        }else {
            return false;
        }


    }
    public void startEamMaintenancePlanTask(HeadquartersDailyVoForSave vo){
        Boolean result=false;
        //默认为每天1点1分
       String  cron=null;
        cron=timedTask.getCron().get("planDaily");
        if(StringUtils.isEmpty(cron)){
            cron="0 01 01 * * ? *";
        }
//    按照单位执行有BUG，即如果设定的日期因某种原因未执行，该次执行谁来？？
//        switch (vo.getFrequency()){
//            case Common.FREQUENCY_DAY :{
//
//            }break;
//            //周按照7天为一周，而非周一周二
//            case Common.FREQUENCY_WEEK :{
//
//            }break;
//            //周按照7天为一周，而非周一周二
//            case Common.FREQUENCY_MONTH :{
//
//            }break;
//            case Common.FREQUENCY_YEAR:{
//
//            }break;
//            default:
//
//        }

        result=eamTimerTaskClient.startEamRoutineWorksheet(vo.getId(),cron,1,"",vo.getId());
        logger.info("定时任务调用:"+result);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDaily/delete", method = RequestMethod.POST)
    public EnerbosMessage delete( @ApiParam(value = "需要删除的事物，例行工作ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = false) List<String> ids){
        try {
            Boolean res = headquartersDailyClient.batchDelete(ids);
            if(ids.size()>1){
                return EnerbosMessage.createSuccessMsg(res, "批量删除成功", "");
            }else{
                return EnerbosMessage.createSuccessMsg(res, "删除成功", "");
            }

        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDaily/delete ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 查询详细页
     * @param id
     * @return
     */
    @ApiOperation(value = "查询详细页", response = HeadquartersDailyVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDaily/findDetail", method = RequestMethod.POST)
    public  EnerbosMessage findDetail(@ApiParam(value = "需要查询详细的例行工作ID", required = true) @RequestParam(value = "id") String id){
        try {
            HeadquartersDailyVo res = headquartersDailyClient.findDetail(id);

            if (StringUtils.isNotEmpty(res.getHeadquartersPlanId())) {
                HeadquartersPlanVo headquartersPlanVo=headquartersPlanClient.findDetailById(res.getHeadquartersPlanId());
                res.setHeadquartersPlanNum(headquartersPlanVo.getPlanNum());
                res.setPlanDescription(headquartersPlanVo.getDescription());
            }
            return EnerbosMessage.createSuccessMsg(res, "查询详细页成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDaily/findDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 批量修改状态
     * @param vo
     * @return
     */
    @ApiOperation(value = "批量修改状态", response = HeadquartersDailyVoForUpStatus.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersDaily/upStrtus", method = RequestMethod.POST)
    public EnerbosMessage upStrtus(@RequestBody HeadquartersDailyVoForUpStatus vo,Principal user){
        try {
            Boolean res = headquartersDailyClient.upStatus(vo);
            if(vo.getIds().length>1){
                return EnerbosMessage.createSuccessMsg(res, "批量修改状态成功", "");
            }else{
                return EnerbosMessage.createSuccessMsg(res, "修改状态成功", "");
            }

        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersDaily/upStrtus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

}
