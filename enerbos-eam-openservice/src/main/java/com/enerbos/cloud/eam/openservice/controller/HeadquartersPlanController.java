package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.contants.*;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForList;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.util.ReflectionUtils;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.client.WorkflowClient;
import com.enerbos.cloud.wfs.vo.ProcessVo;
import io.swagger.annotations.*;
import io.swagger.util.Json;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

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
@Api(description = "总部计划")
public class HeadquartersPlanController {

    private Logger logger = LoggerFactory.getLogger(HeadquartersPlanController.class);

    @Autowired
    private HeadquartersPlanClient headquartersPlanClient;
    @Autowired
    private CodeGeneratorClient codeGeneratorClient;
    @Autowired
    private SiteClient siteClient;

    @Autowired
    private OrgClient orgClient;

    @Autowired
    private DispatchWorkOrderClient dispatchWorkOrderClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;
    @Autowired
    private HeadquartersDailyClient headquartersDailyClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private HeadquartersDaliyTaskClient headquartersDaliyTaskClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Resource
    private WorkflowClient workflowClient;

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;


    @Autowired
    private UgroupClient ugroupClient;









    /**
     * 总部计划--分页、筛选、排序
     * @param filter
     * @return
     */
    @ApiOperation(value = "总部计划--分页、筛选、排序", response = HeadquartersPlanVoForFilter.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/headquartersPlan/findPageList", method = RequestMethod.POST)
    public EnerbosMessage findPageList(@RequestBody HeadquartersPlanVoForFilter filter){
        try {

            List<FieldDomainValueVo> checkitemList = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId(BuildCodeCommon.HEADQUARTERS_CHECKITEM_DOMAIN, filter.getSiteId(), filter.getOrgId(), Common.EAM_PROD_VALUE);

           Map MapCheckitem=new HashMap();
            checkitemList.stream().forEach(vo->{
                MapCheckitem.put(vo.getDescription(),vo.getValue());
            });

         if(!StringUtils.isEmpty(filter.getWord())&&MapCheckitem.get(filter.getWord())!=null){
             if(filter.getCheckItem()!=null){
                 List filterCheck=new ArrayList();
                 filterCheck.add(MapCheckitem.get(filter.getWord()));
                 filterCheck.addAll(filter.getCheckItem());
                 filter.setCheckItem(filterCheck);
             }else{
                 List filterCheck=new ArrayList();
                 filterCheck.add(MapCheckitem.get(filter.getWord()));
                 filter.setCheckItem(filterCheck);
             }
             filter.setWord(null);
         }

            EnerbosPage<HeadquartersPlanVo> pageInfo = headquartersPlanClient.findPageList(filter);

            pageInfo.getList().forEach(vo -> {
                List siteNameList=new ArrayList();
                vo.getPlanSite().forEach(v->{
                    SiteVoForDetail voForDetail=siteClient.findById(v);
                    if(voForDetail!=null){
                        siteNameList.add(voForDetail.getName());
                    }
                });
                //word
                vo.setPlanSiteName(org.apache.commons.lang3.StringUtils.join(siteNameList,","));

                HeadquartersDailyVoForFilter headquartersDailyVoForFilter=new HeadquartersDailyVoForFilter();
                headquartersDailyVoForFilter.setHeadquartersPlanId(vo.getId());

                EnerbosPage<HeadquartersDailyVo> headquartersDailyVoEnerbosPage=headquartersDailyClient.findPageList(headquartersDailyVoForFilter);
                if(headquartersDailyVoEnerbosPage.getTotal()>0){
                    vo.setCreateRoutineWork(true);
                }else{
                    vo.setCreateRoutineWork(false);
                }


//                if(vo.getSiteId().equals(Common.WORK_DATA_ALL)){
//                    vo.setSiteName("全部");
//                }else{
//                    SiteVoForDetail siteVo = siteClient.findById(vo.getSiteId());
//                    vo.setSiteName(siteVo.getName());
//                }
            });
            return EnerbosMessage.createSuccessMsg(pageInfo, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/findPageList ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }


    /**
     * 保存
     * @param filter
     * @return
     */
    @ApiOperation(value = "保存总部计划", response = HeadquartersPlanVoForSave.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/save", method = RequestMethod.POST)
    public EnerbosMessage save(@ApiParam(value = "保存总部计划vo", required = true)@RequestBody HeadquartersPlanVoForSave filter,Principal user){
        try {
            String personId = PrincipalUserUtils.getSiteIdByUser(Json.pretty(user).toString());
//            //若为添加，则生成总部计划编号
//            if(!StringUtils.isNoneBlank(filter.getPlanNum())){
//                String code = codeGeneratorClient.getCodegenerator(siteId, "hq_zbjp");
//                filter.setPlanNum(code);
//            }
            if(filter.getId()==null){
                filter.setCreateDate(new Date());
                filter.setCreateUser(personId);
            }else{
                HeadquartersPlanVo planVo=headquartersPlanClient.findDetail(filter.getId(),filter.getOrgId(),filter.getSiteId());
               if(planVo==null){
                   return EnerbosMessage.createErrorMsg("500", "未知工单", null);
               }else{
                   filter.setCreateUser(planVo.getCreateUser());
                   filter.setCreateDate(planVo.getCreateDate());
               }
            }
            HeadquartersPlanVoForSave vo = headquartersPlanClient.save(filter);
            return EnerbosMessage.createSuccessMsg(vo, "保存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/save ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/delete", method = RequestMethod.POST)
    public EnerbosMessage delete( @ApiParam(value = "需要删除的计划ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = false) List<String> ids){
        try {

            for(String id:ids){
                HeadquartersPlanVo vo=headquartersPlanClient.findDetailById(id);
                if(vo==null){
                    return EnerbosMessage.createErrorMsg("500","工单ID为:"+id+"不存在", null);
                }
                if(!HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_DRAFT.equals(vo.getStatus())){
                    return EnerbosMessage.createErrorMsg("500","工单状态非草稿不能删除", null);
                }
            }
            Boolean res = headquartersPlanClient.batchDelete(ids);
            return EnerbosMessage.createSuccessMsg(res, "删除成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/delete ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 查询详细页
     * @param id
     * @return
     */
    @ApiOperation(value = "查询详细页", response = HeadquartersPlanVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/findDetail", method = RequestMethod.POST)
    public  EnerbosMessage findDetail(
            @ApiParam(value = "需要查询详细的计划ID", required = true) @RequestParam(value = "id") String id,
            @ApiParam(value = "组织ID", required = true) @RequestParam(value = "orgId") String orgId,
            @ApiParam(value = "站点ID", required = false) @RequestParam(value = "siteId",required = false) String siteId
    ){
        try {
            HeadquartersPlanVo res = headquartersPlanClient.findDetail(id,orgId,siteId);

            HeadquartersDailyVoForFilter headquartersDailyVoForFilter=new HeadquartersDailyVoForFilter();
            headquartersDailyVoForFilter.setHeadquartersPlanId(res.getId());
            EnerbosPage<HeadquartersDailyVo> headquartersDailyVoEnerbosPage=headquartersDailyClient.findPageList(headquartersDailyVoForFilter);
            if(headquartersDailyVoEnerbosPage.getTotal()>0){
                res.setCreateRoutineWork(true);
            }else{
                res.setCreateRoutineWork(false);
            }
            return EnerbosMessage.createSuccessMsg(res, "查询详细页成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/findDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 批量修改状态
     * @param vo
     * @return
     */
    @ApiOperation(value = "批量修改状态", response = HeadquartersPlanVoForUpStatus.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/upStrtus", method = RequestMethod.POST)
    public EnerbosMessage upStrtus(@RequestBody HeadquartersPlanVoForUpStatus vo,Principal user){
        try {
            Boolean res = headquartersPlanClient.upStatus(vo);
            return EnerbosMessage.createSuccessMsg(res, "修改状态成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/upStrtus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 批量下达
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量下达", response = HeadquartersPlanVoForUpStatus.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/batchRelease", method = RequestMethod.POST)
    public EnerbosMessage batchRelease(@RequestParam("ids") List<String> ids){
        try {
            Boolean res = headquartersPlanClient.batchRelease(ids);
            return EnerbosMessage.createSuccessMsg(res, "下达成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/batchRelease ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
    /**
     * 生成例行工作
     * @param vo
     * @return
     */
    @ApiOperation(value = "生成例行工作", response = HeadquartersPlanVoForUpStatus.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/createRoutineWork", method = RequestMethod.POST)
    public EnerbosMessage createRoutineWork(@RequestBody HeadquartersDailyVoForCreateWork vo,Principal user){
        try {
            List<HeadquartersPlanVo> list = new ArrayList<>();
            //判断是否有传递总部计划ID
            if(vo.getIds().size()!=0&&vo.getIds()!=null){
                 list=headquartersPlanClient.findDetailByIds(vo.getIds(),vo.getOrgId(),vo.getSiteId());
            }else{
                Map<String,Object> filter=new HashMap();
                //只查询已下达的
                filter.put("status", HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_ISSUED);
                //只查询本组织站点的
                filter.put("siteId",vo.getSiteId());//只查询本站点的
                filter.put("orgId",vo.getOrgId());//只查询本站点的
                list=headquartersPlanClient.getHeadquartersPlanAllByFilter(filter);
            }
            String personId = PrincipalUserUtils.getSiteIdByUser(Json.pretty(user).toString());
            for(HeadquartersPlanVo hevo : list){
                HeadquartersDailyVoForSave forSave=new HeadquartersDailyVoForSave();
                String code = codeGeneratorClient.getCodegenerator(vo.getOrgId(),vo.getSiteId(), BuildCodeCommon.HEADQUARTERS_DAILY_NUM);
                if(code==null){
                    return EnerbosMessage.createErrorMsg("500","编码生成失败", "");
                }
                forSave.setPlanNum(code);//编号
                forSave.setPlanName(hevo.getPlanName());//名称
                forSave.setDescription(hevo.getDescription());//描述
                forSave.setNature(hevo.getNature());//计划性质
                forSave.setStatus(HeadquartersDailyTaskCommon.DAILY_WORK_STASTS_ACTIVUTY);//状态
                forSave.setHeadquartersPlanId(hevo.getId());//总部计划ID
                forSave.setCheckItem(hevo.getCheckItem());//检查项
                forSave.setDeadline(vo.getDeadline());//完成期限
                forSave.setValidStartDate(hevo.getValidStartDate());//有效开始时间
                forSave.setValidEndDate(hevo.getValidEndDate());//有效结束时间
                forSave.setStartDate(vo.getStartDate());//下一生成日期
                forSave.setTimes(vo.getTimes()); //频率
                forSave.setFrequency(vo.getFrequency());//频率单位
                forSave.setWorkType(vo.getWorkType());//工作类型
                forSave.setCreateUser(personId);//创建人
                forSave.setCreateDate(new Date());//创建时间
                forSave.setSiteId(vo.getSiteId());//站点
                forSave.setOrgId(vo.getOrgId());//组织
                HeadquartersDailyVoForSave voForSave=headquartersDailyClient.save(forSave);
                //判断是否是当天，如果是当天立刻生成例行工作单
                 if(isToday(vo.getStartDate())){
                     EnerbosMessage message=this.createHeadquartersDaily(voForSave);
                     if(!message.isSuccess()){
                         return message;
                     }
                }
            }
           return EnerbosMessage.createSuccessMsg(true, "生成例行工作成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/createRoutineWork ------", e);
            return EnerbosMessage.createErrorMsg("500", "生成例行工作失败！", e.getStackTrace().toString());
        }
    }
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
                Assert.isTrue(!StringUtils.isEmpty(userGroupDomainVo),"例行工作单，下未分配分派组");
                UgroupVoForDetail voForDetail=  ugroupClient.findById(userGroupDomainVo.getUserGroupId());
                Assert.isTrue(!StringUtils.isEmpty(userGroupDomainVo),"例行工作单，分派组是未知的");
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
    /**
     * 批量派工
     * @param vo
     * @return
     */
    @ApiOperation(value = "批量派工单", response = HeadquartersPlanVoForUpStatus.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/batchLabor", method = RequestMethod.POST)
    public EnerbosMessage batchLabor(@RequestBody HeadquartersDailyTaskVoForBatchLabor vo,Principal user){
        try {

            String siteId = vo.getSiteId();
            //生成size条派工单编号
            Integer size = vo.getIds().size() == 0 ? vo.getTotal() : vo.getIds().size();
            List<String> codes = new ArrayList<>();
            for(int i=0; i<size; i++){
                String code = codeGeneratorClient.getCodegenerator(vo.getOrgId(),siteId, BuildCodeCommon.DISPATCH_ORDER_NUM);
                codes.add(code);
            }
            vo.setWorkOrderNumList(codes);
            //组装添加派工单数据
            List<DispatchWorkOrderFlowVo> res = headquartersPlanClient.batchlabor(vo);
            return EnerbosMessage.createSuccessMsg(res, "生成派工单成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/batchLabor ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
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

    /**
     * 导入总部计划Excel
     * @param file
     * @param siteId
     * @param orgId
     * @param proId
     * @return
     */
    @ApiOperation(value = "导入总部计划Excel", response = HeadquartersPlanVoForUpStatus.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headquartersPlan/upload", method = RequestMethod.POST)
    public EnerbosMessage upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam("siteId") String siteId,
            @RequestParam("orgId") String orgId,
            @RequestParam("proId") String proId,
            @RequestParam("siteList") String siteList,
            Principal user
    ){
        try {
            if (file.isEmpty()) {
                return EnerbosMessage.createErrorMsg("","请选择文件","");
            }
            if (!file.getOriginalFilename().trim().toLowerCase().endsWith(".xls")
                    && !file.getOriginalFilename().trim().toLowerCase()
                    .endsWith(".xlsx")) {
                return EnerbosMessage.createErrorMsg("","上传类型错误","");
            }
            String personId = PrincipalUserUtils.getSiteIdByUser(Json.pretty(user).toString());
            InputStream in;
            // 获取前台exce的输入流
            in = file.getInputStream();
            // 读取 Excel
            Workbook book = null;
            if (file.getOriginalFilename().trim().toLowerCase().endsWith(".xls")) {
                book = new HSSFWorkbook(in);
            } else {
                book = new XSSFWorkbook(in);
            }
            List<HeadquartersPlanVoForSave> voForSaves = getUpLoadVoList(book,siteId,orgId,proId,siteList,personId);
            List<HeadquartersPlanVoForSave> returnVo = headquartersPlanClient.batchSave(voForSaves);
            return EnerbosMessage.createSuccessMsg(returnVo, "导入数据成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/headquartersPlan/upload ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    //解析Excel，返回总部计划vo集合
    private List<HeadquartersPlanVoForSave> getUpLoadVoList(Workbook book,String siteId, String orgId, String proId, String siteList,String personId) throws ParseException {
        List<HeadquartersPlanVoForSave> voForSaves = new ArrayList<>();
        //获取总部计划sheet
        Sheet sheet = book.getSheetAt(0);
        //sheet页行数
        int rowCount = sheet.getPhysicalNumberOfRows();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //检查项域值
        Map checkitemMap=new HashMap();
        List<FieldDomainValueVo> checkitemList = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId(BuildCodeCommon.HEADQUARTERS_CHECKITEM_DOMAIN, siteId, orgId, proId);

        checkitemList.forEach(vo->{
            checkitemMap.put(vo.getDescription(),vo.getValue());
        });

        //计划状态域值
        Map planStatusMap=new HashMap();
        List<FieldDomainValueVo> planStatusList = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId(BuildCodeCommon.HEADQUARTERS_PLAN_STATUS_NUM, siteId, orgId, proId);
        planStatusList.forEach(vo->{
            planStatusMap.put(vo.getDescription(),vo.getValue());
        });

        //计划性质域值
        Map planNatureMap=new HashMap();
        List<FieldDomainValueVo> planNatureList = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId(BuildCodeCommon.HEADQUARTERS_PLAN_NATURE_NUM, siteId, orgId, proId);

        planNatureList.forEach(vo->{
            planNatureMap.put(vo.getDescription(),vo.getValue());
        });



        //从第三行遍历
        for (int r = 2; r < rowCount; r++) {
            HeadquartersPlanVoForSave vo = new HeadquartersPlanVoForSave();
            //计划编号
            String planCode = codeGeneratorClient.getCodegenerator(orgId,siteId, BuildCodeCommon.HEADQUARTERS_PLAN_NUM);
            //行数据
            Row row = sheet.getRow(r);
            vo.setSiteId(siteId);
            vo.setOrgId(orgId);
            vo.setPlanNum(planCode);
            vo.setPlanName(getCellValue(row.getCell(1)));
            vo.setDescription(getCellValue(row.getCell(2)));

            //            planNatureList.forEach( planNature -> {
//                if(planNature.getDescription().equals(getCellValue(row.getCell(3)))){
//                    vo.setNature(planNature.getValue());
//                }
//            });
            if(planNatureMap.get(getCellValue(row.getCell(3)))!=null){
                vo.setNature(planNatureMap.get(getCellValue(row.getCell(3))).toString());
            }else{
                throw new RuntimeException("第"+(r-1)+"条数据，计划性质不存在");
            }

//            planStatusList.forEach( planStatus -> {
//                if(planStatus.getDescription().equals(getCellValue(row.getCell(4)))){
//                    vo.setStatus(planStatus.getValue());
//                }
//            });

           if( planStatusMap.get(getCellValue(row.getCell(4)))!=null){
               vo.setStatus(planStatusMap.get(getCellValue(row.getCell(4))).toString());
           }else{
               throw new RuntimeException("第"+(r-1)+"条数据，计划状态不存在");
           }

//            checkitemList.forEach( checkitem -> {
//                if(checkitem.getDescription().equals(getCellValue(row.getCell(5)))){
//                    vo.setCheckItem(checkitem.getValue());
//                }
//            });
            if(checkitemMap.get(getCellValue(row.getCell(5)))!=null){
                vo.setCheckItem(checkitemMap.get(getCellValue(row.getCell(5))).toString());
            }else{
                throw new RuntimeException("第"+(r-1)+"条数据，检查项不存在");
            }
            vo.setValidStartDate(fmt.parse(getCellValue(row.getCell(6))));
            vo.setValidEndDate(fmt.parse(getCellValue(row.getCell(7))));
            vo.setCreateUser(personId);
            vo.setCreateDate(new Date());
            List<String> planSite = new ArrayList<>();
            String siteCode = getCellValue(row.getCell(8));

            if(StringUtils.isEmpty(siteCode)){
                throw new RuntimeException("第"+(r-1)+"条数据，应用范围为空");
            }
            String[] codes = siteCode.split(",");
            for(String code : codes){
                SiteVoForDetail siteVoForDetail=siteClient.findByCode(code);
                if(siteVoForDetail==null){
                    throw new RuntimeException("第"+(r-1)+"条数据，站点CODE不存在");
                 }
            if (orgId.equals(siteVoForDetail.getOrgId())){
                planSite.add(siteVoForDetail.getId());
            }else{
                throw new RuntimeException("第"+(r-1)+"条数据，站点不在CODE当前组织范围内");
            }
            }


//            if(!StringUtils.isEmpty(siteCode)){
//                JSONObject siteObj = JSONObject.parseObject(siteList);
//                String[] codes = siteCode.split(",");
//                for(String code : codes){
//                    if(!StringUtils.isEmpty(siteObj.get(code))){
//                        String planSiteId = String.valueOf(siteObj.get(code));
//                        planSite.add(planSiteId);
//                    }
//                }
//            }
            vo.setPlanSite(planSite);
            voForSaves.add(vo);
        }
        return voForSaves;
    }

    //检验单元格格式，返回数据
    private String getCellValue(Cell cell){
        String cellValue = "";
        int cellType = cell.getCellType();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch(cellType) {
            case Cell.CELL_TYPE_STRING: //文本
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: //数字、日期
                if(DateUtil.isCellDateFormatted(cell)) {
                    cellValue = fmt.format(cell.getDateCellValue()); //日期型
                }
                else {
                    cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN: //布尔型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: //空白
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //错误
                cellValue = "";
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = "";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }





        @ApiOperation(value = "导出EXCEL", notes = "返回数据统一包装在 EnerbosMessage.data")
        @RequestMapping(value = "/eam/open/headquartersPlan/exportTemplate", method = RequestMethod.POST)
        public void exportExcel(
                @ApiParam(value = "组织ID") @RequestParam(value = "orgId") String orgId,
                HttpServletResponse response,
                HttpServletRequest request,
                Principal user) {
            try {
                Assert.isTrue(!StringUtils.isEmpty(orgId),"组织ID不能为空");
                OrgVoForDetail orgVoForDetail=orgClient.findById(orgId);
                if(orgVoForDetail==null){
                    throw new RuntimeException("组织ID不存在");
                }
                this.exportExcelUtil( response,request,orgId);
            } catch (Exception e) {
                logger.error("---------/eam/open/patrolRoute/exportExcel---------", e);
            }
        }

        public void exportExcelUtil(HttpServletResponse response, HttpServletRequest request,String orgId){
            List<PatrolOrderVo> patrolOrderVoList = new ArrayList<>();

            //判断是否选择
            try {

                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("导入模板");
                //-----------------------------模板页设置----------------------------
                HSSFRow row1Titles = sheet.createRow(0);
                List<String> templateTitles = Arrays.asList("序号", "计划名称（计划工作内容）", "计划描述（计划工作内容调整）", "计划性质", "计划状态", "检查项","有效开始日期","有效结束日期","应用范围注：应用范围站点名称用逗号隔开,填写站点code");

                CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short) 9); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
                sheet.addMergedRegion(region1);
                HSSFCell titles=  row1Titles.createCell(0);
                titles.setCellValue("工程设备保养项目及周期");
                titles.setCellStyle(this.setCellStyleTitle(titles,wb));

                HSSFRow row1 = sheet.createRow(1);
                for (int i = 0; i < templateTitles.size(); i++) {
                    HSSFCell row=row1.createCell(i);
                    row.setCellValue(templateTitles.get(i));
                    row.setCellStyle(this.setCellStyleHeader(row,wb));
                    sheet.setColumnWidth(i,templateTitles.get(i).getBytes().length*256);
                 //   sheet.setColumnWidth(i,templateTitles.get(i).toString().length() * 512);
                    //  row1.createCell(i).setCellStyle(this.setCellStyleHeader(row1.createCell(i),wb));
                }

                //----------------------站点页面设置----------------------------
                List<String> siteTitles = Arrays.asList("序号", "站点名称）", "站点CODE");
                HSSFSheet siteSheet = wb.createSheet("站点信息");

                HSSFRow Siterow = siteSheet.createRow(0);
                for (int i = 0; i < siteTitles.size(); i++) {
                    HSSFCell row= Siterow.createCell(i);
                    row.setCellValue(siteTitles.get(i));
                    row.setCellStyle(this.setCellStyleHeader(row,wb));
                }
                List<SiteVoForList> siteVoForLists=siteClient.findByOrgId(orgId);
                for (int i = 0; i < siteVoForLists.size(); i++) {
                    HSSFRow row = siteSheet.createRow(i + 1);
                    HSSFCell cell1= row.createCell(0);
                      cell1.setCellValue(i+1);
                      cell1.setCellStyle(this.setCellStyleText(cell1,wb));
                    HSSFCell cell2= row.createCell(1);
                      cell2.setCellValue(siteVoForLists.get(i).getName());
                    cell2.setCellStyle(this.setCellStyleText(cell2,wb));
                    HSSFCell cell3= row.createCell(2);
                     cell3.setCellValue(siteVoForLists.get(i).getCode());
                    cell3.setCellStyle(this.setCellStyleText(cell3,wb));

//                    row.createCell(0).setCellStyle(this.setCellStyleText(row.createCell(0),wb));
//
//                    row.createCell(1).setCellValue(siteVoForLists.get(i).getName());
//                    row.createCell(1).setCellStyle(this.setCellStyleText(row.createCell(1),wb));
//
//                    row.createCell(2).setCellValue(siteVoForLists.get(i).getCode());
//                    row.createCell(2).setCellStyle(this.setCellStyleText(row.createCell(2),wb));
                }
                OutputStream os = response.getOutputStream();
                String filename = new String("工程管理系统-计划导入模板_2.0.xls".getBytes(QRCodeManagerCommon.CHARSET), "ISO8859-1");
                String mimeType = request.getSession().getServletContext().getMimeType(filename);
                response.setContentType(mimeType);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + java.net.URLEncoder.encode(filename, "ISO8859-1"));
                response.addHeader("Cache-Control", "no-cache");
                wb.write(os);
                os.flush();
                os.close();
            } catch (Exception e) {
                logger.error("---------/eam/open/headquartersPlan/exportTemplate---------", e);
            }

        }


    /**
     *
     * @param hssfCell 表格
     *                 背景
     *                 字体大小
     *                 数据对齐，是否居中
     * 标题样式
     */
    public   HSSFCellStyle setCellStyleTitle(HSSFCell hssfCell,HSSFWorkbook hssfWorkbook ){
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();  //新建单元格样式
        //--------背景色------
       // cellStyle.setFillForegroundColor((short) 13);// 设置背景色
      //  cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        //--------字体------------
        HSSFFont font2 = hssfWorkbook.createFont();
        font2.setFontName("微软雅黑");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 18);  //字体大小
        cellStyle.setFont(font2);//选择需要用到的字体格式

        return cellStyle;
    }


    /**
     * 修改表头样式
     * @param hssfCell
     * @param hssfWorkbook
     * @return
     */
    public   HSSFCellStyle setCellStyleHeader(HSSFCell hssfCell,HSSFWorkbook hssfWorkbook ){
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();  //新建单元格样式
        //--------背景色------
         cellStyle.setFillForegroundColor((short) 13);// 设置背景色
          cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        //--------字体------------
        HSSFFont font2 = hssfWorkbook.createFont();
        font2.setFontName("微软雅黑");
       // font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 13);  //字体大小
        cellStyle.setFont(font2);//选择需要用到的字体格式

        return cellStyle;
    }

    /**
     * 内容样式
     * @param hssfCell
     * @param hssfWorkbook
     * @return
     */
    public   HSSFCellStyle setCellStyleText(HSSFCell hssfCell,HSSFWorkbook hssfWorkbook ){
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();  //新建单元格样式
        //--------背景色------
        // cellStyle.setFillForegroundColor((short) 13);// 设置背景色
        //  cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        //--------字体------------
        HSSFFont font2 = hssfWorkbook.createFont();
        font2.setFontName("微软雅黑");
      //  font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 11);  //字体大小
        cellStyle.setFont(font2);//选择需要用到的字体格式

        return cellStyle;
    }



    /**
     * 设置正文单元样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createBodyCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName(HSSFFont.FONT_ARIAL);//设置标题字体
        cellStyle.setFont(font);
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        return cellStyle;
    }
    /**
     * 设置正文单元时间样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle createDateBodyCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName(HSSFFont.FONT_ARIAL);//设置标题字体
        cellStyle.setFont(font);
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFDataFormat format= workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));
        return cellStyle;
    }

    /**
     * 设置标题单元样式
     * @param workbook
     * @return
     */
    public  HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 8);
        font.setFontName(HSSFFont.FONT_ARIAL);//设置标题字体
        cellStyle.setFont(font);
        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);//设置列标题样式
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);// 设置背景色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        return cellStyle;
    }
    /**
     * 写入标题行
     * @param
     * @return
     */
    public static void writeTitleContent (HSSFSheet sheet,HSSFCellStyle cellStyle){
        HSSFRow row = null;
        HSSFCell cell = null;
        //标题
        row = sheet.createRow(0);
        //第一行写入标题行
        cell = row.createCell((short)0);//序号
        cell.setCellStyle(cellStyle);
        cell.setCellValue("序号");
        cell = row.createCell((short)1);//姓名
        cell.setCellStyle(cellStyle);
        cell.setCellValue("姓名");
        cell = row.createCell((short)2);//手机号
        cell.setCellStyle(cellStyle);
        cell.setCellValue("手机号");
        cell = row.createCell((short)3);//航班号
        cell.setCellStyle(cellStyle);
        cell.setCellValue("航班号");
        cell = row.createCell((short)4);//航班日期
        cell.setCellStyle(cellStyle);
        cell.setCellValue("航班日期");
        cell = row.createCell((short)5);//始发
        cell.setCellStyle(cellStyle);
        cell.setCellValue("始发");
        cell = row.createCell((short)6);//到达
        cell.setCellStyle(cellStyle);
        cell.setCellValue("到达");
        cell = row.createCell((short)7);//电子票号
        cell.setCellStyle(cellStyle);
        cell.setCellValue("电子票号");
        cell = row.createCell((short)8);//证件号
        cell.setCellStyle(cellStyle);
        cell.setCellValue("证件号");
        cell = row.createCell((short)9);//主舱
        cell.setCellStyle(cellStyle);
        cell.setCellValue("主舱");
        cell = row.createCell((short)10);//子舱
        cell.setCellStyle(cellStyle);
        cell.setCellValue("子舱");
        cell = row.createCell((short)11);//座位
        cell.setCellStyle(cellStyle);
        cell.setCellValue("座位");
        cell = row.createCell((short)12);//状态
        cell.setCellStyle(cellStyle);
        cell.setCellValue("状态");
        cell = row.createCell((short)13);//行李数
        cell.setCellStyle(cellStyle);
        cell.setCellValue("行李数");
        cell = row.createCell((short)14);//行李重
        cell.setCellStyle(cellStyle);
        cell.setCellValue("行李重");
        cell = row.createCell((short)15);//特殊
        cell.setCellStyle(cellStyle);
        cell.setCellValue("特殊");
    }
    public static void setSheetColumn(HSSFSheet sheet){
        sheet.setColumnWidth((short) 2, (short) 3200);//设置手机号列宽
        sheet.setColumnWidth((short) 4, (short) 3200);//设置航班日期列宽
        sheet.setColumnWidth((short) 7, (short) 5250);//设置电子票号列宽
        sheet.setColumnWidth((short) 8, (short) 6250);//设置证件号列宽
    }

}
