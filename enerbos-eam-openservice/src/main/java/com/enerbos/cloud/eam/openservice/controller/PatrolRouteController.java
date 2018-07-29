package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.PatrolCommonClient;
import com.enerbos.cloud.eam.client.PatrolPlanClient;
import com.enerbos.cloud.eam.client.PatrolRouteClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.PatrolCommon;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.eam.vo.*;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
@Api(description = "巡检路线(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolRouteController {
    private static Logger logger = LoggerFactory.getLogger(PatrolRouteController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private PatrolRouteClient patrolRouteClient;
    @Autowired
    private SiteClient siteClient;

    @Autowired
    private OrgClient orgClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private PatrolCommonClient patrolCommonClient;

    @Autowired
    private PatrolPlanClient patrolPlanClient;

    @ApiOperation(value = "分页查询巡检路线信息", response = PatrolRouteVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRoute/findPage", method = RequestMethod.POST)
    public EnerbosMessage findPage(@RequestBody PatrolRouteVoForFilter patrolRouteVoForFilter, Principal user) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if (patrolRouteVoForFilter.getCollect()) {
                patrolRouteVoForFilter.setPersonId(personId);
            }
            EnerbosPage<PatrolRouteVo> pageInfo = patrolRouteClient.findPatrolRouteList(patrolRouteVoForFilter);
            if (pageInfo != null & pageInfo.getList() != null) {
                List<PatrolRouteVo> patrolRouteVoList = pageInfo.getList();
                for (PatrolRouteVo patrolRouteVo : patrolRouteVoList) {
                    //设置描述
                    setDescription(patrolRouteVo);
                    //设置收藏
                    boolean isCollect = patrolCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(patrolRouteVo.getId(), PatrolCommon.ROUTE, Common.EAM_PROD_IDS[0], personId);
                    patrolRouteVo.setCollect(isCollect);
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检路线列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolRoute/findPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除巡检路线
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除巡检路线", response = Array.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRoute/deleteByIds", method = RequestMethod.POST)
    public EnerbosMessage findPage(@ApiParam(value = "需要删除巡检路线ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String[] ids) {
        try {
            String str = patrolRouteClient.deleteByIds(ids);
            if (str.equals("success")) {
                return EnerbosMessage.createSuccessMsg(str, "删除巡检路线成功", "");
            } else {
                return EnerbosMessage.createErrorMsg("", str, "");
            }
        } catch (Exception e) {
            logger.error("-------/eam/open/patrolRoute/deleteByIds--------------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "新增或更新巡检路线", response = PatrolRouteVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRoute/saveOrUpdate", method = RequestMethod.POST)
    public EnerbosMessage saveOrUpdate(@RequestBody PatrolRouteForSaveVo patrolRouteForSaveVo) {
        try {
            PatrolRouteVo patrolRouteVo = patrolRouteClient.saveOrUpdate(patrolRouteForSaveVo);
            FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.ROUTE_STATUS, patrolRouteVo.getStatus(), patrolRouteVo.getSiteId(), patrolRouteVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (statusDomain != null) patrolRouteVo.setStatusDescription(statusDomain.getDescription());
            return EnerbosMessage.createSuccessMsg(patrolRouteVo, "保存成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolRoute/saveOrUpdate ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * findPointAndTermById:根据ID查询巡检路线-巡检点
     *
     * @param id
     * @param patroPointVoForFilter
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询巡检路线-巡检点", response = PatrolRouteVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRoute/findPatrolRouteVoById", method = RequestMethod.GET)
    public EnerbosMessage findPatrolRouteVoById(@ApiParam(value = "巡检路线", required = true) @RequestParam("id") String id, PatrolPointVoForFilter patroPointVoForFilter) {
        try {
            patroPointVoForFilter.setPatrolRouteId(id);
            patroPointVoForFilter.setId(null);
            PatrolRouteVo patrolRouteVo = patrolRouteClient.findPatrolRouteVoById(patroPointVoForFilter);
            setDescription(patrolRouteVo);
            return EnerbosMessage.createSuccessMsg(patrolRouteVo, "根据ID查询巡检路线-巡检点成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolRoute/findPatrolRouteVoById ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * updatePatrolRouteStatusList:批量修改巡检路线状态
     *
     * @param ids    巡检路线ID数组{@link java.util.List<String>}
     * @param status 巡检路线状态
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改巡检路线状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRoute/updatePatrolRouteStatusList", method = RequestMethod.POST)
    public EnerbosMessage updatePatrolRouteStatusList(@ApiParam(value = "巡检路线id", required = true) @RequestParam("ids") List<String> ids,
                                                      @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/PatrolRoute/updatePatrolRouteStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status);

            List<PatrolRouteVo> list = new ArrayList<>();
            PatrolPointVoForFilter pp = new PatrolPointVoForFilter();
            for (String id : ids) {
                pp.setPatrolRouteId(id);
                PatrolRouteVo pr = patrolRouteClient.findPatrolRouteVoById(pp);
                if (pr == null || "".equals(pr)) {
                    return EnerbosMessage.createErrorMsg("", "巡检路线不存在", "");
                }
                if (PatrolCommon.PATROL_ROUTE_STATUS_N.equals(status)) {
                    PatrolPlanVoForFilter ppf = new PatrolPlanVoForFilter();
                    ppf.setPatrolRouteId(id);
                    EnerbosPage<PatrolPlanVo> planPage = patrolPlanClient.findPatrolPlanList(ppf);
                    if (planPage != null && planPage.getList() != null && !planPage.getList().isEmpty()) {
                        PatrolPlanVo patrolPlanVo = planPage.getList().get(0);
                        if (PatrolCommon.PATROL_PLAN_STATUS_Y.equals(patrolPlanVo.getStatus())) {
                            return EnerbosMessage.createErrorMsg("500", "巡检路线：" + pr.getPatrolRouteNum() + "已经关联活动巡检计划：" + patrolPlanVo.getPatrolPlanNum() + "，不能停用", "");
                        }
                    }
                }
                pr.setStatus(status);
                pr.setStatusdate(new Date());
                pr.setUpdatetime(new Date());
                list.add(pr);
            }
            for (PatrolRouteVo pr : list) {
                PatrolRouteForSaveVo prs = new PatrolRouteForSaveVo();
                BeanUtils.copyProperties(pr, prs);
                patrolRouteClient.saveOrUpdate(prs);
            }
            return EnerbosMessage.createSuccessMsg(true, "状态变更成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolRoute/updatePatrolRouteStatusList ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    @ApiOperation(value = "导出EXCEL", notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRoute/exportExcel", method = RequestMethod.POST)
    public void exportExcel(@ApiParam(value = "需要导出巡检点ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = false) String[] ids,
                            @ApiParam(value = "组织ID") @RequestParam(value = "orgId") String orgId,
                            @ApiParam(value = "站点ID") @RequestParam(value = "siteId") String siteId,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Principal user) {


        List<PatrolRouteVo> patrolRouteVoList = new ArrayList<>();

        //判断是否选择
        try {
            if (ids != null && ids.length > 0) {
                PatrolPointVoForFilter patrolPointVoForFilter = new PatrolPointVoForFilter();
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    patrolPointVoForFilter.setPatrolRouteId(id);
                    PatrolRouteVo patrolRouteVo = patrolRouteClient.findPatrolRouteVoById(patrolPointVoForFilter);
                    //设置描述
                    setDescription(patrolRouteVo);
                    patrolRouteVoList.add(patrolRouteVo);
                }
            } else { //导出全部
                PatrolRouteVoForFilter patrolRouteVoForFilter = new PatrolRouteVoForFilter();
                patrolRouteVoForFilter.setSiteId(siteId);
                patrolRouteVoForFilter.setOrgId(orgId);
                patrolRouteVoForFilter.setPageNum(1);
                patrolRouteVoForFilter.setPageSize(1000000);
                EnerbosPage<PatrolRouteVo> pageInfo = patrolRouteClient.findPatrolRouteList(patrolRouteVoForFilter);
                if (pageInfo != null) {
                    List<PatrolRouteVo> list = pageInfo.getList();
                    for (PatrolRouteVo patrolRouteVo : list) {
                        //设置描述
                        setDescription(patrolRouteVo);
                    }
                    patrolRouteVoList = pageInfo.getList();
                }
            }

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row1 = sheet.createRow(0);
            List<String> list = Arrays.asList("编号", "路线描述", "巡检类型", "备注", "状态", "站点");
            for (int i = 0; i < list.size(); i++) {
                row1.createCell(i).setCellValue(list.get(i));
            }
            for (int i = 0; i < patrolRouteVoList.size(); i++) {
                PatrolRouteVo patrolRouteVo = patrolRouteVoList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(patrolRouteVo.getPatrolRouteNum());
                row.createCell(1).setCellValue(patrolRouteVo.getDescription());
                row.createCell(2).setCellValue(patrolRouteVo.getTypeDescription());
                row.createCell(3).setCellValue(patrolRouteVo.getRemark());
                row.createCell(4).setCellValue(patrolRouteVo.getStatusDescription());
                row.createCell(5).setCellValue(patrolRouteVo.getSite());
            }
            for (int i = 0; i < 6; i++) {
                if (i != 1 && i != 3) {
                    sheet.autoSizeColumn(i);
                } else {
                    sheet.setColumnWidth(i, 10000);
                }
            }
            OutputStream os = response.getOutputStream();
            String filename = new String("巡检路线.xls".getBytes(QRCodeManagerCommon.CHARSET), "ISO8859-1");
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
            logger.error("-----/eam/open/patrolRoute/getErrorStatusCode----", jsonException);
        }
        return statusCode;
    }

    private void setDescription(PatrolRouteVo patrolRouteVo) {
        if (patrolRouteVo != null) {
            //从域中查询类型和状态
            FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PATROL_TYPE, patrolRouteVo.getType(), patrolRouteVo.getSiteId(), patrolRouteVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (typeDomain != null) {
                patrolRouteVo.setTypeDescription(typeDomain.getDescription());
            }
            FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.ROUTE_STATUS, patrolRouteVo.getStatus(), patrolRouteVo.getSiteId(), patrolRouteVo.getOrgId(), PatrolCommon.PRODUCT_EAM);
            if (statusDomain != null) {
                patrolRouteVo.setStatusDescription(statusDomain.getDescription());
            }
            SiteVoForDetail site = siteClient.findById(patrolRouteVo.getSiteId());
            if (site != null) {
                patrolRouteVo.setSite(site.getName());
            }
            OrgVoForDetail org = orgClient.findById(patrolRouteVo.getOrgId());
            if (org != null) {
                patrolRouteVo.setOrg(org.getName());
            }
        }
    }
}
