package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.PatrolCommonClient;
import com.enerbos.cloud.eam.client.PatrolOrderClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.PatrolCommon;
import com.enerbos.cloud.eam.contants.PatrolOrderCommon;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import com.enerbos.cloud.wfs.vo.HistoricTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;

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
@RestController
@Api(description = "巡检工单(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolOrderController {
    private static Logger logger = LoggerFactory.getLogger(PatrolOrderController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private PatrolOrderClient patrolOrderClient;
    @Autowired
    private SiteClient siteClient;
    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @Autowired
    private OrgClient orgClient;

    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private UserGroupDomainColler userGroupDomainColler;

    @Autowired
    private PatrolCommonClient patrolCommonClient;

    @ApiOperation(value = "分页查询巡检工单信息", response = PatrolOrderVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/findPage", method = RequestMethod.POST)
    public EnerbosMessage findPage(@RequestBody PatrolOrderVoForFilter patrolOrderVoForFilter, Principal user) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if (patrolOrderVoForFilter.getCollect()) {
                patrolOrderVoForFilter.setCollectPersonId(personId);
            }
            EnerbosPage<PatrolOrderVo> pageInfo = patrolOrderClient.findPatrolOrderList(patrolOrderVoForFilter);
            if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                List<PatrolOrderVo> list = pageInfo.getList();
                for (PatrolOrderVo patrolOrderVo : list) {
                    //添加描述
                    setDescription(patrolOrderVo);
                    //设置收藏
                    boolean isCollect = patrolCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(patrolOrderVo.getId(), PatrolCommon.ORDER, Common.EAM_PROD_IDS[0], personId);
                    patrolOrderVo.setCollect(isCollect);
                    //添加人员名称
                    setPersonName(patrolOrderVo);
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检工单列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolOrder/findPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除巡检工单
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除巡检工单", response = Array.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/deleteByIds", method = RequestMethod.POST)
    public EnerbosMessage deleteByIds(@ApiParam(value = "需要删除巡检工单ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String[] ids) {
        try {
            String str = patrolOrderClient.deleteByIds(ids);
            if (str.equals("success")) {
                return EnerbosMessage.createSuccessMsg(str, "删除巡检工单成功", "");
            } else {
                return EnerbosMessage.createErrorMsg("", str, "");
            }
        } catch (Exception e) {
            logger.debug("-------/eam/open/patrolOrder/deleteByIds--------------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "新增或更新巡检工单", response = PatrolOrderForSaveVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/saveOrUpdate", method = RequestMethod.POST)
    public EnerbosMessage saveOrUpdate(@RequestBody PatrolOrderForSaveVo patrolOrderForSaveVo) {
        try {
            PatrolOrderVo patrolOrderVo = patrolOrderClient.saveOrUpdate(patrolOrderForSaveVo);
            return EnerbosMessage.createSuccessMsg(patrolOrderVo, "保存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolOrder/saveOrUpdate ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * findPointAndTermById:根据ID查询巡检工单
     *
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询巡检工单", response = PatrolOrderVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/findPatrolOrderById", method = RequestMethod.GET)
    public EnerbosMessage findPatrolOrderVoById(@ApiParam(value = "巡检工单", required = true) @RequestParam("id") String id) {
        try {
            PatrolOrderVo patrolOrderVo = patrolOrderClient.findPatrolOrderVoById(id);
            if (patrolOrderVo != null) {
                //设置描述
                setDescription(patrolOrderVo);
                //查询用户组
                if (StringUtils.hasLength(patrolOrderVo.getType())) {
                    UserGroupDomainVo userGroupDomainVo = userGroupDomainColler.findUserGroupDomainByDomainValues(patrolOrderVo.getType(),
                            PatrolOrderCommon.PATROL_TYPE, patrolOrderVo.getOrgId(),
                            patrolOrderVo.getSiteId(), Common.USERGROUP_ASSOCIATION_TYPE_ALL);
                    if (userGroupDomainVo != null) {
                        patrolOrderVo.setGroupTypeName(userGroupDomainVo.getUserGroupName());
                        //提报状态下查询工单分派组人员
                        if (PatrolOrderCommon.STATUS_DTB.equals(patrolOrderVo.getStatus())) {
                            List<PersonAndUserVoForDetail> list = userGroupDomainColler.findUserByDomainValueORDomainNums(patrolOrderVo.getType(),
                                    PatrolOrderCommon.PATROL_TYPE, patrolOrderVo.getOrgId(), patrolOrderVo.getSiteId(), Common.USERGROUP_ASSOCIATION_TYPE_ALL);
                            if (list != null && list.size() > 0) {
                                StringBuilder assignPersonName = new StringBuilder();
                                for (PersonAndUserVoForDetail personAndUserVoForDetail : list) {
                                    assignPersonName.append(personAndUserVoForDetail.getName() + ",");
                                }
                                patrolOrderVo.setAssignPerson(assignPersonName.toString().substring(0, assignPersonName.toString().length() - 1));
                            }
                        }
                    }
                }
                //设置人员名称
                setPersonName(patrolOrderVo);
                //插入执行记录
                String processInstanceId = patrolOrderVo.getProcessInstanceId();
                if (StringUtils.hasLength(processInstanceId)) {
                    patrolOrderVo.setEamImpleRecordVoVoList(getExecution(processInstanceId));
                }
            }
            return EnerbosMessage.createSuccessMsg(patrolOrderVo, "根据ID查询巡检工单-巡检点成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolOrder/findPatrolOrderById ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    @ApiOperation(value = "查询巡检工单关联的巡检点", response = PatrolPointVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/findOrderPointById", method = RequestMethod.GET)
    public EnerbosMessage findOrderPointById(@ApiParam(value = "巡检工单id", required = true) @RequestParam("id") String id) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            EnerbosPage<PatrolPointVo> pageInfo = patrolOrderClient.findOrderPointById(id);
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检点列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----findOrderPointById ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "导出EXCEL", notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/exportExcel", method = RequestMethod.POST)
    public void exportExcel(@ApiParam(value = "需要导出巡检工单ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = false) String[] ids,
                            @ApiParam(value = "组织ID") @RequestParam(value = "orgId") String orgId,
                            @ApiParam(value = "站点ID") @RequestParam(value = "siteId") String siteId,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Principal user) {

        List<PatrolOrderVo> patrolOrderVoList = new ArrayList<>();

        //判断是否选择
        try {
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    PatrolOrderVo patrolOrderVo = patrolOrderClient.findPatrolOrderVoById(id);
                    setDescription(patrolOrderVo);
                    patrolOrderVoList.add(patrolOrderVo);
                }
            } else { //导出全部
                PatrolOrderVoForFilter patrolOrderVoForFilter = new PatrolOrderVoForFilter();
                patrolOrderVoForFilter.setSiteId(siteId);
                patrolOrderVoForFilter.setOrgId(orgId);
                patrolOrderVoForFilter.setPageNum(1);
                patrolOrderVoForFilter.setPageSize(1000000);
                EnerbosPage<PatrolOrderVo> pageInfo = patrolOrderClient.findPatrolOrderList(patrolOrderVoForFilter);
                if (pageInfo != null) {
                    List<PatrolOrderVo> list = pageInfo.getList();
                    for (PatrolOrderVo patrolOrderVo : list) {
                        setDescription(patrolOrderVo);
                    }
                    patrolOrderVoList = pageInfo.getList();
                }
            }

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row1 = sheet.createRow(0);
            List<String> titles = Arrays.asList("编号", "工单描述", "工单类型", "生成时间", "状态", "站点");
            for (int i = 0; i < titles.size(); i++) {
                row1.createCell(i).setCellValue(titles.get(i));
            }
            for (int i = 0; i < patrolOrderVoList.size(); i++) {
                PatrolOrderVo patrolOrderVo = patrolOrderVoList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(patrolOrderVo.getPatrolOrderNum());
                row.createCell(1).setCellValue(patrolOrderVo.getDescription());
                row.createCell(2).setCellValue(patrolOrderVo.getTypeDescription());
                row.createCell(3).setCellValue(patrolOrderVo.getCreatetime());
                row.createCell(4).setCellValue(patrolOrderVo.getStatusDescription());
                row.createCell(5).setCellValue(patrolOrderVo.getSite());
            }
            for (int i = 0; i < 6; i++) {
                if (i != 1) {
                    sheet.autoSizeColumn(i);
                } else {
                    sheet.setColumnWidth(i, 10000);
                }
            }
            OutputStream os = response.getOutputStream();
            String filename = new String("巡检工单.xls".getBytes(QRCodeManagerCommon.CHARSET), "ISO8859-1");
            String mimeType = request.getSession().getServletContext().getMimeType(filename);
            response.setContentType(mimeType);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + java.net.URLEncoder.encode(filename, "ISO8859-1"));
            response.addHeader("Cache-Control", "no-cache");
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("---------/eam/open/patrolRoute/exportExcel---------", e);
        }
    }


    /**
     * updatePatrolOrderStatusList:批量修改巡检工单状态
     *
     * @param ids    巡检工单ID数组{@link java.util.List<String>}
     * @param status 巡检工单状态
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改巡检工单状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolOrder/updatePatrolOrderStatusList", method = RequestMethod.POST)
    public EnerbosMessage updatePatrolOrderStatusList(@ApiParam(value = "巡检工单id", required = true) @RequestParam("ids") List<String> ids,
                                                      @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/PatrolOrder/updatePatrolOrderStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status);
            ids.stream()
                    .filter(id -> id != null && id != "")
                    .map(id -> patrolOrderClient.findPatrolOrderVoById(id))
                    .filter(patrolOrderVo -> patrolOrderVo != null)
                    .forEach(patrolOrderVo -> {
                        patrolOrderVo.setStatus(status);
                        patrolOrderVo.setStatusdate(new Date());
                        PatrolOrderForSaveVo patrolOrderForSaveVo = new PatrolOrderForSaveVo();
                        BeanUtils.copyProperties(patrolOrderVo, patrolOrderForSaveVo);
                        patrolOrderClient.saveOrUpdate(patrolOrderForSaveVo);
                    });
            return EnerbosMessage.createSuccessMsg(true, "状态变更成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolOrder/updatePatrolOrderStatusList ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    //获取错误码
    private String getErrorStatusCode(Exception e) {
        String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
        String statusCode = "";
        try {
            JSONObject jsonMessage = JSONObject.parseObject(message);
            if (jsonMessage != null) {
                statusCode = jsonMessage.get("status").toString();
            }
        } catch (Exception jsonException) {
            logger.error("-----getErrorStatusCode----", jsonException);
        }
        return statusCode;
    }

    /**
     * getExecution:根据流程实例ID查询执行记录
     *
     * @param processInstanceId 流程实例ID
     * @return List<MaintenanceWorkOrderImpleRecordVo> 执行记录VO集合
     */
   /* private List<WorkFlowImpleRecordVo> getExecution(String processInstanceId) {
        List<WorkFlowImpleRecordVo> workOrderImpleRecordVoList = new ArrayList<>();
        List<HistoricTaskVo> historicTaskVoList = processActivitiClient.findProcessTrajectory(processInstanceId);
        if (historicTaskVoList != null && null == historicTaskVoList.get(historicTaskVoList.size() - 1).getEndTime()) {
            historicTaskVoList.add(0, historicTaskVoList.remove(historicTaskVoList.size() - 1));
        }
        WorkFlowImpleRecordVo maintenanceWorkOrderImpleRecordVo = null;
        if (null != historicTaskVoList && historicTaskVoList.size() > 0) {
            for (HistoricTaskVo historicTaskVo : historicTaskVoList) {
                maintenanceWorkOrderImpleRecordVo = new WorkFlowImpleRecordVo();
                maintenanceWorkOrderImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
                maintenanceWorkOrderImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
                maintenanceWorkOrderImpleRecordVo.setName(historicTaskVo.getName());
                if (null != historicTaskVo.getAssignee()) {
                    String[] ids = historicTaskVo.getAssignee().split(",");
                    String personName = "";
                    if (null != ids && !"".equals(ids)) {
                        for (String id : ids) {
                            personName += personAndUserClient.findByPersonId(id).getName() + ",";
                        }
                        personName.substring(0, personName.length() - 1);
                    }
                    maintenanceWorkOrderImpleRecordVo.setPersonName(personName);
                }
                maintenanceWorkOrderImpleRecordVo.setDurationInMillis(historicTaskVo.getDurationInMillis());
                maintenanceWorkOrderImpleRecordVo.setDescription(historicTaskVo.getDescription());
                workOrderImpleRecordVoList.add(maintenanceWorkOrderImpleRecordVo);
            }
        }
        return workOrderImpleRecordVoList;
    }*/

    private List<WorkFlowImpleRecordVo> getExecution(String processInstanceId) {
        List<WorkFlowImpleRecordVo> workOrderImpleRecordVoList=new ArrayList<>();
        List<HistoricTaskVo> historicTaskVoList=processActivitiClient.findProcessTrajectory(processInstanceId);
        WorkFlowImpleRecordVo maintenanceWorkOrderImpleRecordVo=null;
        Set<String> processName=new HashSet<>();
        if (null!=historicTaskVoList&&historicTaskVoList.size()>0) {
            for (int i = historicTaskVoList.size()-1; i >=0; i--) {
                HistoricTaskVo historicTaskVo=historicTaskVoList.get(i);
                maintenanceWorkOrderImpleRecordVo=new WorkFlowImpleRecordVo();
                maintenanceWorkOrderImpleRecordVo.setStartTime(historicTaskVo.getStartTime());
                maintenanceWorkOrderImpleRecordVo.setEndTime(historicTaskVo.getEndTime());
                maintenanceWorkOrderImpleRecordVo.setName(historicTaskVo.getName());
                if (processName.isEmpty()||!processName.contains(maintenanceWorkOrderImpleRecordVo.getName())) {
                    processName.add(maintenanceWorkOrderImpleRecordVo.getName());
                    maintenanceWorkOrderImpleRecordVo.setProcessType(Common.ORDER_PROCESS_TYPE_NORMAL);
                }else if (!processName.isEmpty()&&processName.contains(maintenanceWorkOrderImpleRecordVo.getName())){
                    maintenanceWorkOrderImpleRecordVo.setProcessType(Common.ORDER_PROCESS_TYPE_REJECT);
                }

                if (org.apache.commons.lang.StringUtils.isNotBlank(historicTaskVo.getAssignee())) {
                    String[] ids=historicTaskVo.getAssignee().split(",");
                    String personName="";
                    if (null!=ids&&!"".equals(ids)){
                        for (String id:ids){
                            PersonAndUserVoForDetail person=personAndUserClient.findByPersonId(id);
                            if (person != null) {
                                personName+= personAndUserClient.findByPersonId(id).getName()+",";
                            }
                        }
                        personName=personName.substring(0,personName.length()-1);
                    }
                    maintenanceWorkOrderImpleRecordVo.setPersonName(personName);
                }
                maintenanceWorkOrderImpleRecordVo.setDurationInMillis(historicTaskVo.getDurationInMillis());
                maintenanceWorkOrderImpleRecordVo.setDescription(historicTaskVo.getDescription());
                workOrderImpleRecordVoList.add(0,maintenanceWorkOrderImpleRecordVo);
            }
        }
        return workOrderImpleRecordVoList;
    }
    private void setDescription(PatrolOrderVo patrolOrderVo) {
        if (patrolOrderVo != null) {
            //从域中查询类型和状态
            FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PATROL_TYPE, patrolOrderVo.getType(), patrolOrderVo.getSiteId(), patrolOrderVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (typeDomain != null) {
                patrolOrderVo.setTypeDescription(typeDomain.getDescription());
            }
            FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.ORDER_STATUS, patrolOrderVo.getStatus(), patrolOrderVo.getSiteId(), patrolOrderVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (statusDomain != null) {
                patrolOrderVo.setStatusDescription(statusDomain.getDescription());
            }
            SiteVoForDetail site = siteClient.findById(patrolOrderVo.getSiteId());
            if (site != null) {
                patrolOrderVo.setSite(site.getName());
            }
            OrgVoForDetail org = orgClient.findById(patrolOrderVo.getOrgId());
            if (org != null) {
                patrolOrderVo.setOrg(org.getName());
            }
        }
    }

    private void setPersonName(PatrolOrderVo patrolOrderVo) {
        if (patrolOrderVo != null) {
            if (StringUtils.hasLength(patrolOrderVo.getCreatePersonId())) {
                PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(patrolOrderVo.getCreatePersonId());
                if (person != null) {
                    patrolOrderVo.setCreatePerson(person.getName());
                }
            }
            if (StringUtils.hasLength(patrolOrderVo.getAssignPersonId())) {
                PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(patrolOrderVo.getCreatePersonId());
                if (person != null) {
                    patrolOrderVo.setAssignPerson(person.getName());
                }
            }
            if (StringUtils.hasLength(patrolOrderVo.getExcutePersonId())) {
                String[] persionIds = patrolOrderVo.getExcutePersonId().split(",");
                StringBuilder excutePersonNames = new StringBuilder();
                String workGroup = "";
                for (String persionId : persionIds) {
                    PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(persionId);
                    if (person != null) {
                        excutePersonNames.append(person.getName() + ", ");
                        workGroup = person.getWorkgroup();
                    }
                }
                patrolOrderVo.setExcutePerson(excutePersonNames.toString().substring(0, excutePersonNames.toString().length() - 2));
                patrolOrderVo.setExcuteWorkGroup(workGroup);
            }
        }
    }
}
