package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.PatrolCommonClient;
import com.enerbos.cloud.eam.client.PatrolPointClient;
import com.enerbos.cloud.eam.client.PatrolRouteClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.PatrolCommon;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@Api(description = "巡检点台账(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolPointController {
    private static Logger logger = LoggerFactory.getLogger(PatrolPointController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private PatrolPointClient patrolPointClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private OrgClient orgClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private PatrolCommonClient patrolCommonClient;

    @Autowired
    private PatrolRouteClient patrolRouteClient;

    @ApiOperation(value = "分页查询巡检点信息", response = PatrolPointVoForFilter.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/findPage", method = RequestMethod.POST)
    public EnerbosMessage findPage(@RequestBody PatrolPointVoForFilter patrolPointVoForFilter, Principal user) {
        EnerbosMessage enerbosMessage = new EnerbosMessage();
        try {
            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            if (patrolPointVoForFilter.getCollect()) {
                patrolPointVoForFilter.setPersonId(personId);
            }
            EnerbosPage<PatrolPointVo> pageInfo = patrolPointClient.findPatrolPointList(patrolPointVoForFilter);
            if (pageInfo != null) {
                List<PatrolPointVo> list = pageInfo.getList();
                list.forEach(patrolPointVo -> {
                    String name = user.getName();
                    //添加描述
                    setDescription(patrolPointVo, name);
                    //设置收藏
                    boolean isCollect = patrolCommonClient.findCollectByCollectIdAndTypeAndProductAndPerson(patrolPointVo.getId(), PatrolCommon.POINT, Common.EAM_PROD_IDS[0], personId);
                    patrolPointVo.setCollect(isCollect);
                });
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检点列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/findPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除巡检点
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除巡检点", response = Array.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/deleteByIds", method = RequestMethod.POST)
    public EnerbosMessage deleteByIds(@ApiParam(value = "需要删除巡检点ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String[] ids) {
        try {
            String str = patrolPointClient.deleteByIds(ids);
            if (str.equals("success")) {
                return EnerbosMessage.createSuccessMsg(str, "删除巡检点成功", "");
            } else {
                return EnerbosMessage.createErrorMsg("", str, "");
            }
        } catch (Exception e) {
            logger.error("-------/patrolPoint/deleteByIds--------------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "分页查询巡检项信息", response = PatrolTermVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/findPatrolTermPage", method = RequestMethod.POST)
    public EnerbosMessage findPatrolTermPage(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter) {
        try {
            EnerbosPage<PatrolTermVo> pageInfo = patrolPointClient.findPatrolTermPage(patrolTermVoForFilter);
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检项列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----/patrolPoint/findPatrolTermPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    @ApiOperation(value = "分页查询巡检记录信息", response = PatrolRecordTermVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/findPatrolRecordTermPage", method = RequestMethod.POST)
    public EnerbosMessage findPatrolRecordTermPage(@RequestBody PatrolRecordTermVoForFilter patrolRecordTermVoForFilter) {
        try {
            EnerbosPage<PatrolRecordTermVo> pageInfo = patrolPointClient.findPatrolRecordTermPage(patrolRecordTermVoForFilter);
            if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                List<PatrolRecordTermVo> list = pageInfo.getList();
                for (PatrolRecordTermVo patrolRecordTermVo : list) {
                    String excutePersonId = patrolRecordTermVo.getExcutePersonId();
                    if (StringUtils.hasLength(excutePersonId)) {
                        String[] persionIds = excutePersonId.split(",");
                        StringBuilder createPersonNames = new StringBuilder();
                        for (String persionId : persionIds) {
                            PersonAndUserVoForDetail person = personAndUserClient.findByPersonId(persionId);
                            if (person != null) {
                                createPersonNames.append(person.getName());
                            }
                        }
                        patrolRecordTermVo.setExcutePerson(createPersonNames.toString());
                    }
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "巡检记录列表查询成功", "");
        } catch (Exception e) {
            logger.error("-----findPatrolRecordTermPage ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    @ApiOperation(value = "新增或更新巡检点", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/saveOrUpdate", method = RequestMethod.POST)
    public EnerbosMessage saveOrUpdate(@RequestBody PatrolPointForSaveVo patrolPointForSaveVo) {
        try {
            PatrolPointVo patrolPointVo = patrolPointClient.saveOrUpdate(patrolPointForSaveVo);
            return EnerbosMessage.createSuccessMsg(patrolPointVo, "保存成功", "");
        } catch (Exception e) {
            logger.error("-----saveOrUpdate ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * findPointAndTermById:根据ID查询巡检点台账-巡检点
     *
     * @param id
     * @param patrolTermVoForFilter
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询巡检点台账-巡检点", response = PatrolPointVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/findPatrolPointVoById", method = RequestMethod.GET)
    public EnerbosMessage findPatrolPointVoById(@ApiParam(value = "巡检点", required = true) @RequestParam("id") String id, PatrolTermVoForFilter patrolTermVoForFilter, Principal user) {
        try {
            patrolTermVoForFilter.setPatrolPointId(id);
            patrolTermVoForFilter.setId(null);
            PatrolPointVo patrolPointVo = patrolPointClient.findPatrolPointVoById(patrolTermVoForFilter);
            if (patrolPointVo != null) {
                String name = user.getName();
                //设置描述
                setDescription(patrolPointVo, name);
            }
            return EnerbosMessage.createSuccessMsg(patrolPointVo, "根据ID查询巡检点台账-巡检点成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/findPointAndTermById ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }


    @ApiOperation(value = "根据工单id查询巡检记录中巡检项内容", response = PatrolRecordTermVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/findPatrolRecordByOrderAndPoint", method = RequestMethod.GET)
    public EnerbosMessage findPatrolRecordByOrderAndPoint(@ApiParam(value = "工单id", required = true) @RequestParam("id") String id, @ApiParam(value = "巡检点id", required = true) @RequestParam("pointid") String pointid) {
        try {
            PatrolRecordVo patrolRecordVo = patrolPointClient.findPatrolRecordByOrderAndPoint(id, pointid);
            return EnerbosMessage.createSuccessMsg(patrolRecordVo, "根据工单id查询巡检记录中巡检项内容", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/findPatrolRecordByOrderAndPoint ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    @ApiOperation(value = "导出PDF", notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/exportPDF", method = RequestMethod.POST)
    public EnerbosMessage exportPDF(@ApiParam(value = "需要导出巡检点ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = false) String[] ids,
                                    @ApiParam(value = "组织ID") @RequestParam(value = "orgId") String orgId,
                                    @ApiParam(value = "站点ID") @RequestParam(value = "siteId") String siteId,
                                    HttpServletResponse response,
                                    Principal user) {
        List<PatrolPointVo> patrolPointVoList = new ArrayList<>();
        Document document = new Document();

        try {
            //判断是否选择
            if (ids != null && ids.length > 0) {
                PatrolTermVoForFilter patrolTermVoForFilter = new PatrolTermVoForFilter();
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    patrolTermVoForFilter.setPatrolPointId(id);
                    PatrolPointVo patrolPointVo = patrolPointClient.findPatrolPointVoById(patrolTermVoForFilter);
                    if (patrolPointVo != null) {
                        String name = user.getName();
                        //设置描述
                        setDescription(patrolPointVo, name);
                    }
                    patrolPointVoList.add(patrolPointVo);
                }
            } else { //导出全部
                PatrolPointVoForFilter patrolPointVoForFilter = new PatrolPointVoForFilter();
                patrolPointVoForFilter.setSiteId(siteId);
                patrolPointVoForFilter.setOrgId(orgId);
                patrolPointVoForFilter.setPageNum(1);
                patrolPointVoForFilter.setPageSize(1000000);
                EnerbosPage<PatrolPointVo> pageInfo = patrolPointClient.findPatrolPointList(patrolPointVoForFilter);
                if (pageInfo != null) {
                    List<PatrolPointVo> list = pageInfo.getList();
                    for (PatrolPointVo patrolPointVo : list) {
                        //查询位置描述
                        String name = user.getName();
                        //设置描述
                        setDescription(patrolPointVo, name);
                    }
                    patrolPointVoList = pageInfo.getList();
                }
            }


            //设置中文
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

            Font keyfont = new Font(bfChinese, 8, Font.BOLD);
            Font textfont = new Font(bfChinese, 8, Font.NORMAL);

            //创建一个书写器
            String filename = new String("巡检点台账.pdf".getBytes(QRCodeManagerCommon.CHARSET), "ISO8859-1");
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + java.net.URLEncoder.encode(filename, "ISO8859-1"));
            response.addHeader("Cache-Control", "no-cache");
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            document.open();
            //创建页码
            int recordPerPage = 30;
            int fullPageRequired = patrolPointVoList.size() / recordPerPage;
            int remainPage = patrolPointVoList.size() % recordPerPage >= 1 ? 1 : 0;
            int totalPage = fullPageRequired + remainPage;

            for (int j = 0; j < totalPage; j++) {
                document.newPage();

                //创建页面
                String pageNo = leftPad("页码: " + (j + 1) + " / " + totalPage, 615);
                Paragraph pageNumber = new Paragraph(pageNo, keyfont);
                document.add(pageNumber);

                //标题
                PdfPTable t = new PdfPTable(6);
                float[] widths = {0.5f, 1f, 2f, 1f, 0.5f, 1f};
                t.setWidths(widths);
                t.setWidthPercentage(100);
                t.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                List<String> titles = Arrays.asList("编号", "巡检点描述", "位置描述", "巡检类型", "状态", "站点");
                titles.forEach(title -> {
                    PdfPCell c1 = new PdfPCell(new Paragraph("编号", keyfont));
                    t.addCell(c1);
                });
                //遍历数据
                int maxRecordInPage = j + 1 == totalPage ? (remainPage == 0 ? recordPerPage : (patrolPointVoList.size() % recordPerPage)) : recordPerPage;

                for (int i = j * recordPerPage; i < ((j * recordPerPage) + maxRecordInPage); i++) {
                    PdfPCell c2 = new PdfPCell(new Paragraph(patrolPointVoList.get(i).getPatrolnum(), textfont));
                    t.addCell(c2);
                    c2 = new PdfPCell(new Paragraph(patrolPointVoList.get(i).getDescription(), textfont));
                    t.addCell(c2);
                    c2 = new PdfPCell(new Paragraph(patrolPointVoList.get(i).getLocationDsr(), textfont));
                    t.addCell(c2);
                    c2 = new PdfPCell(new Paragraph(patrolPointVoList.get(i).getTypeDescription(), textfont));
                    t.addCell(c2);
                    c2 = new PdfPCell(new Paragraph(patrolPointVoList.get(i).getStatusDescription(), textfont));
                    t.addCell(c2);
                    c2 = new PdfPCell(new Paragraph(patrolPointVoList.get(i).getSite(), textfont));
                    t.addCell(c2);
                }
                document.add(t);
            }
            document.close();
            return EnerbosMessage.createSuccessMsg("", "导出PDF成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/exportPDF ------", e);
            if (document.isOpen()) {
                document.close();
            }
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }

    }


    /**
     * updatePatrolPointStatusList:批量修改巡检点台账状态
     *
     * @param ids    巡检点台账ID数组{@link java.util.List<String>}
     * @param status 巡检点台账状态
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量修改巡检点台账状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/updatePatrolPointStatusList", method = RequestMethod.POST)
    public EnerbosMessage updatePatrolPointStatusList(@ApiParam(value = "巡检点台账id", required = true) @RequestParam("ids") List<String> ids,
                                                      @ApiParam(value = "status", required = true) @RequestParam("status") String status, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/PatrolPoint/updatePatrolPointStatusList, host: [{}:{}], service_id: {}, ids: {}, status: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids, status);

            List<PatrolPointVo> list = new ArrayList<>();
            PatrolTermVoForFilter pt = new PatrolTermVoForFilter();
            for (String id : ids) {
                pt.setPatrolPointId(id);
                PatrolPointVo pp = patrolPointClient.findPatrolPointVoById(pt);
                if (pp == null || "".equals(pp)) {
                    return EnerbosMessage.createErrorMsg("", "巡检点台账不存在", "");
                }
                //停用巡检点检查是否管理活动巡检路线
                if (PatrolCommon.PATROL_POINT_STATUS_N.equals(status)) {
                    List<PatrolRouteVo> routeList = patrolRouteClient.findRouteByPointId(id);
                    if (routeList != null && !routeList.isEmpty()) {
                        for (PatrolRouteVo patrolRouteVo : routeList) {
                            if (PatrolCommon.PATROL_ROUTE_STATUS_Y.equals(patrolRouteVo.getStatus())) {
                                return EnerbosMessage.createErrorMsg("500", "巡检点：" + pp.getPatrolnum() + "已被活动巡检路线:" + patrolRouteVo.getPatrolRouteNum() + "关联,不能变为停用状态", "");
                            }
                        }
                    }
                }
                pp.setStatus(status);
                list.add(pp);
            }
            for (PatrolPointVo pp : list) {
                PatrolPointForSaveVo pps = new PatrolPointForSaveVo();
                BeanUtils.copyProperties(pp, pps);
                patrolPointClient.saveOrUpdate(pps);
            }
            return EnerbosMessage.createSuccessMsg(true, "状态变更成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/updatePatrolPointStatusList ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }


    /**
     * generateQrcode:批量生成二维码
     *
     * @param ids  巡检点台账ID数组{@link java.util.List<String>}
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "批量生成二维码", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolPoint/generateQrcode", method = RequestMethod.POST)
    public EnerbosMessage generateQrcode(@ApiParam(value = "巡检点台账id", required = true) @RequestParam("ids") List<String> ids, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/patrolPoint/generateQrcode, host: [{}:{}], service_id: {}, ids: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), ids);
            patrolPointClient.generateQrcode(ids);
            return EnerbosMessage.createSuccessMsg(true, "批量生成巡检点二维码成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/generateQrcode ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }


    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param id
     * @param qrCodeNum
     * @param siteId
     * @return
     */
    public List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(List<String> id, String qrCodeNum, String siteId, String userName) {
        List<PatrolPointVo> patrolPointVolist = new ArrayList<>();
        try {
            String[] ids = new String[0];
            if (id != null && !id.isEmpty()) {
                Object[] arr = id.toArray();
                ids = new String[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    ids[i] = String.valueOf(arr[i]);
                }
            }

            patrolPointVolist = patrolPointClient.findByIdAndQrCodeNumAndSiteId(ids, qrCodeNum, siteId);
            for (PatrolPointVo patrolPointVo : patrolPointVolist) {
                //设置描述
                setDescription(patrolPointVo, userName);
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/findByIdAndQrCodeNumAndSiteId ------", e);
        }
        return patrolPointVolist;
    }

    /**
     * 设置是否更新状态
     *
     * @param id
     * @param siteId
     * @param b
     * @return
     */
    public boolean updateIsupdatedata(String id, String siteId, boolean b) {
        try {
            patrolPointClient.updateIsupdatedata(id, siteId, b);
            return true;
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/updateIsupdatedata ------", e);
        }
        return false;
    }

    public void setDescription(PatrolPointVo patrolPointVo, String username) {
        //查询位置描述
        LocationVoForDetail locationVoForDetail = locationClient.findById(username, patrolPointVo.getLochierarchyid());
        if (locationVoForDetail != null) {
            patrolPointVo.setLocationDsr(locationVoForDetail.getName());
            patrolPointVo.setLocationNum(locationVoForDetail.getCode());
        }
        //从域中查询类型和状态
        FieldDomainValueVo typeDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.PATROL_TYPE, patrolPointVo.getType(), patrolPointVo.getSiteid(), patrolPointVo.getOrgid(), PatrolCommon.PRODUCT_EAM);
        if (typeDomain != null) {
            patrolPointVo.setTypeDescription(typeDomain.getDescription());
        }
        FieldDomainValueVo statusDomain = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId(PatrolCommon.POINT_STATUS, patrolPointVo.getStatus(), patrolPointVo.getSiteid(), patrolPointVo.getOrgid(), PatrolCommon.PRODUCT_EAM);
        if (statusDomain != null) {
            patrolPointVo.setStatusDescription(statusDomain.getDescription());
        }
        SiteVoForDetail site = siteClient.findById(patrolPointVo.getSiteid());
        if (site != null) {
            patrolPointVo.setSite(site.getName());
        }
        OrgVoForDetail org = orgClient.findById(patrolPointVo.getOrgid());
        if (org != null) {
            patrolPointVo.setOrg(org.getName());
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
            logger.error("-----/eam/open/patrolPoint/getErrorStatusCode----", jsonException);
        }
        return statusCode;
    }

    private String leftPad(String str, int i) {
        int addSpaceNo = i - str.length();
        String space = "";
        for (int k = 0; k < addSpaceNo; k++) {
            space = " " + space;
        }
        ;
        String result = space + str;
        return result;
    }

}
