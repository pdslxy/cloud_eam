package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.PatrolCommonClient;
import com.enerbos.cloud.eam.client.PatrolFrequencyClient;
import com.enerbos.cloud.eam.client.PatrolPlanClient;
import com.enerbos.cloud.eam.client.PatrolRouteClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.PatrolCommon;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.eam.openservice.config.TimedTask;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.tts.client.EamTimerTaskClient;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@RestController
@Api(description = "巡检计划(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolPlanController {
    private static Logger logger = LoggerFactory.getLogger(PatrolPlanController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private PatrolPlanClient patrolPlanClient;

    @Autowired
    private PatrolRouteClient patrolRouteClient;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private OrgClient orgClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private EamTimerTaskClient eamTimerTaskClient;

    @Autowired
    private PatrolFrequencyClient patrolFrequencyClient;

    @Autowired
    private PatrolCommonClient patrolCommonClient;

    @Autowired
    private TimedTask timedTask;

    @ApiOperation(value = "分页查询巡检计划信息", response = PatrolPlanVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPlan/findPage", method = RequestMethod.POST)
    public EnerbosMessage findPage(@RequestBody PatrolPlanVoForFilter patrolPlanVoForFilter, Principal user) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if (patrolPlanVoForFilter.getCollect()) {
                patrolPlanVoForFilter.setPersonId(personId);
            }
            EnerbosPage<PatrolPlanVo> pageInfo = patrolPlanClient.findPatrolPlanList(patrolPlanVoForFilter);
            List<PatrolPlanVo> patrolPlanVoList = pageInfo.getList();
            for (PatrolPlanVo patrolPlanVo : patrolPlanVoList) {
                //设置描述
                setDescription(patrolPlanVo);
                //设置收藏
                boolean isCollect = patrolCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(patrolPlanVo.getId(), PatrolCommon.PLAN, Common.EAM_PROD_IDS[0], personId);
                patrolPlanVo.setCollect(isCollect);
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检计划列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPlan/findPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除巡检计划
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除巡检计划", response = Array.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPlan/deleteByIds", method = RequestMethod.POST)
    public EnerbosMessage deleteByIds(@ApiParam(value = "需要删除巡检计划ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String[] ids) {
        try {
            String str = patrolPlanClient.deleteByIds(ids);
            if (str.equals("success")) {
                return EnerbosMessage.createSuccessMsg(str, "删除巡检计划成功", "");
            } else {
                return EnerbosMessage.createErrorMsg("", str, "");
            }
        } catch (Exception e) {
            logger.debug("-------/eam/open/patrolPlan/deleteByIds--------------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "新增或更新巡检计划", response = PatrolPlanForSaveVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPlan/saveOrUpdate", method = RequestMethod.POST)
    public EnerbosMessage saveOrUpdate(@RequestBody PatrolPlanForSaveVo patrolPlanForSaveVo) {
        try {
            List<String> oldFrequencyIds = null;
            List<String> newFrequencyIds = null;
            if (StringUtils.hasLength(patrolPlanForSaveVo.getId())) {
                oldFrequencyIds = patrolFrequencyClient.findIdsByPlanId(patrolPlanForSaveVo.getId());
            }
            PatrolPlanVo patrolPlanVo = patrolPlanClient.saveOrUpdate(patrolPlanForSaveVo);
            FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PLAN_STATUS, patrolPlanVo.getStatus(), patrolPlanVo.getSiteId(), patrolPlanVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (statusDomain != null) patrolPlanVo.setStatusDescription(statusDomain.getDescription());

            newFrequencyIds = patrolFrequencyClient.findIdsByPlanId(patrolPlanVo.getId());
            String cron = null;


            cron=timedTask.getCron().get("patrolPlan");
            if(StringUtils.isEmpty(cron)){
                cron="0 0 0/1 * * ?";
            }

            //活动状态新增定时任务
            if (patrolPlanVo.getStatus().equals("Y") && newFrequencyIds != null && !newFrequencyIds.isEmpty()) {
                eamTimerTaskClient.startEamPatrolPlanTask(patrolPlanVo.getId(), cron, 1, null, patrolPlanVo.getId(), oldFrequencyIds, newFrequencyIds);
            } else if (oldFrequencyIds != null && !oldFrequencyIds.isEmpty()) {
                eamTimerTaskClient.startEamPatrolPlanTask(patrolPlanVo.getId(), cron, 1, null, patrolPlanVo.getId(), oldFrequencyIds, null);
            }
            return EnerbosMessage.createSuccessMsg(patrolPlanVo, "保存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPlan/saveOrUpdate ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * findPointAndTermById:根据ID查询巡检点台账-巡检点
     *
     * @param id
     * @param patrolPlanFrequencyVoForFilter
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询巡检计划-频率", response = PatrolPlanVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPlan/findPatrolPlanVoById", method = RequestMethod.GET)
    public EnerbosMessage findPatrolPlanVoById(@ApiParam(value = "巡检计划", required = true) @RequestParam("id") String id, PatrolPlanFrequencyVoForFilter patrolPlanFrequencyVoForFilter) {
        try {
            patrolPlanFrequencyVoForFilter.setPatrolPlanId(id);
            patrolPlanFrequencyVoForFilter.setId(null);
            PatrolPlanVo patrolPlanVo = patrolPlanClient.findPatrolPlanVoById(patrolPlanFrequencyVoForFilter);
            setDescription(patrolPlanVo);
            return EnerbosMessage.createSuccessMsg(patrolPlanVo, "根据ID查询巡检计划-频率成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPlan/findPatrolPlanVoById ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * updatePatrolPlanStatusList:批量修改巡检计划状态
     *
     * @param ids    巡检计划ID数组{@link java.util.List<String>}
     * @param status 巡检计划状态
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改巡检计划状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPlan/updatePatrolPlanStatusList", method = RequestMethod.POST)
    public EnerbosMessage updatePatrolPlanStatusList(@ApiParam(value = "巡检计划id", required = true) @RequestParam("ids") List<String> ids,
                                                     @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/PatrolPlan/updatePatrolPlanStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status);

            List<PatrolPlanVo> list = new ArrayList<>();
            PatrolPlanFrequencyVoForFilter pf = new PatrolPlanFrequencyVoForFilter();
            for (String id : ids) {
                pf.setPatrolPlanId(id);
                PatrolPlanVo pp = patrolPlanClient.findPatrolPlanVoById(pf);
                if (pp == null || "".equals(pp)) {
                    return EnerbosMessage.createErrorMsg("", "巡检计划不存在", "");
                }
                if (status.equals(PatrolCommon.PATROL_PLAN_STATUS_Y)) {
                    PatrolPointVoForFilter patrolPointVoForFilter = new PatrolPointVoForFilter();
                    patrolPointVoForFilter.setPatrolRouteId(pp.getPatrolRouteId());
                    PatrolRouteVo prv = patrolRouteClient.findPatrolRouteVoById(patrolPointVoForFilter);
                    if (prv != null && prv.getStatus().equals(PatrolCommon.PATROL_ROUTE_STATUS_N)) {
                        return EnerbosMessage.createErrorMsg("", "巡检路线停用,无法变更为活动状态", "");
                    }
                }
                if (status.equals(PatrolCommon.PATROL_PLAN_STATUS_C) && !pp.getStatus().equals(PatrolCommon.PATROL_PLAN_STATUS_C)) {
                    return EnerbosMessage.createErrorMsg("500", "无法变更为草稿状态", "");
                }
                pp.setStatus(status);
                list.add(pp);
            }
            for (PatrolPlanVo pp : list) {
                PatrolPlanForSaveVo pps = new PatrolPlanForSaveVo();
                BeanUtils.copyProperties(pp, pps);
                //变更定时任务
                List<String> oldFrequencyIds = null;
                List<String> newFrequencyIds = null;
                if (StringUtils.hasLength(pps.getId())) {
                    oldFrequencyIds = patrolFrequencyClient.findIdsByPlanId(pps.getId());
                }
                PatrolPlanVo patrolPlanVo = patrolPlanClient.saveOrUpdate(pps);
                FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PLAN_STATUS, patrolPlanVo.getStatus(), patrolPlanVo.getSiteId(), patrolPlanVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
                if (statusDomain != null) patrolPlanVo.setStatusDescription(statusDomain.getDescription());

                newFrequencyIds = patrolFrequencyClient.findIdsByPlanId(patrolPlanVo.getId());
                String cron = "0 0 0/1 * * ?";
                //活动状态新增定时任务
                if (patrolPlanVo.getStatus().equals(PatrolCommon.PATROL_PLAN_STATUS_Y) && newFrequencyIds != null && !newFrequencyIds.isEmpty()) {
                    eamTimerTaskClient.startEamPatrolPlanTask(patrolPlanVo.getId(), cron, 1, null, patrolPlanVo.getId(), oldFrequencyIds, newFrequencyIds);
                } else if (oldFrequencyIds != null && !oldFrequencyIds.isEmpty()) {
                    eamTimerTaskClient.startEamPatrolPlanTask(patrolPlanVo.getId(), cron, 1, null, patrolPlanVo.getId(), oldFrequencyIds, null);
                }
            }
            return EnerbosMessage.createSuccessMsg(true, "状态变更成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPlan/updatePatrolPlanStatusList ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    @ApiOperation(value = "导出EXCEL", notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPlan/exportExcel", method = RequestMethod.POST)
    public void exportExcel(@ApiParam(value = "需要导出巡检计划ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = false) String[] ids,
                            @ApiParam(value = "组织ID") @RequestParam(value = "orgId") String orgId,
                            @ApiParam(value = "站点ID") @RequestParam(value = "siteId") String siteId,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Principal user) {

        List<PatrolPlanVo> patrolPlanVoList = new ArrayList<>();

        //判断是否选择
        try {
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    PatrolPlanVo patrolPlanVo = patrolPlanClient.findPatrolPlanVoById(id);
                    setDescription(patrolPlanVo);
                    patrolPlanVoList.add(patrolPlanVo);
                }
            } else { //导出全部
                PatrolPlanVoForFilter patrolPlanVoForFilter = new PatrolPlanVoForFilter();
                patrolPlanVoForFilter.setSiteId(siteId);
                patrolPlanVoForFilter.setOrgId(orgId);
                patrolPlanVoForFilter.setPageNum(1);
                patrolPlanVoForFilter.setPageSize(1000000);
                EnerbosPage<PatrolPlanVo> pageInfo = patrolPlanClient.findPatrolPlanList(patrolPlanVoForFilter);
                if (pageInfo != null) {
                    List<PatrolPlanVo> list = pageInfo.getList();
                    for (PatrolPlanVo patrolPlanVo : list) {
                        setDescription(patrolPlanVo);
                    }
                    patrolPlanVoList = pageInfo.getList();
                }
            }

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row1 = sheet.createRow(0);
            List<String> titles = Arrays.asList("编号", "路线描述", "巡检类型", "路线描述", "状态", "站点");
            for (int i = 0; i < titles.size(); i++) {
                row1.createCell(i).setCellValue(titles.get(i));
            }
            for (int i = 0; i < patrolPlanVoList.size(); i++) {
                PatrolPlanVo patrolPlanVo = patrolPlanVoList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(patrolPlanVo.getPatrolPlanNum());
                row.createCell(1).setCellValue(patrolPlanVo.getDescription());
                row.createCell(2).setCellValue(patrolPlanVo.getTypeDescription());
                row.createCell(3).setCellValue(patrolPlanVo.getPatrolRouteDsr());
                row.createCell(4).setCellValue(patrolPlanVo.getStatusDescription());
                row.createCell(5).setCellValue(patrolPlanVo.getSite());
            }
            for (int i = 0; i < 6; i++) {
                if (i != 1 && i != 3) {
                    sheet.autoSizeColumn(i);
                } else {
                    sheet.setColumnWidth(i, 10000);
                }
            }
            OutputStream os = response.getOutputStream();
            String filename = new String("巡检计划.xls".getBytes(QRCodeManagerCommon.CHARSET), "ISO8859-1");
            String mimeType = request.getSession().getServletContext().getMimeType(filename);
            response.setContentType(mimeType);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + java.net.URLEncoder.encode(filename, "ISO8859-1"));
            response.addHeader("Cache-Control", "no-cache");
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("---------/eam/open/patrolPlan/exportExcel---------", e);
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

    private void setDescription(PatrolPlanVo patrolPlanVo) {
        if (patrolPlanVo != null) {
            //从域中查询类型和状态
            FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PATROL_TYPE, patrolPlanVo.getType(), patrolPlanVo.getSiteId(), patrolPlanVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (typeDomain != null) {
                patrolPlanVo.setTypeDescription(typeDomain.getDescription());
            }
            FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PLAN_STATUS, patrolPlanVo.getStatus(), patrolPlanVo.getSiteId(), patrolPlanVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (statusDomain != null) {
                patrolPlanVo.setStatusDescription(statusDomain.getDescription());
            }
            SiteVoForDetail site = siteClient.findById(patrolPlanVo.getSiteId());
            if (site != null) {
                patrolPlanVo.setSite(site.getName());
            }
            OrgVoForDetail org = orgClient.findById(patrolPlanVo.getOrgId());
            if (org != null) {
                patrolPlanVo.setOrg(org.getName());
            }
        }
    }
}
