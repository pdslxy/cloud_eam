package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.MaintenanceWorkOrderClient;
import com.enerbos.cloud.eam.client.RepairOrderClient;
import com.enerbos.cloud.eam.vo.DashboardSiteTaskCountVo;
import com.enerbos.cloud.eam.vo.OrderCountAndRingratio;
import com.enerbos.cloud.eam.vo.OrderCountBySiteVo;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import com.enerbos.cloud.wfs.client.ProcessTaskClient;
import com.enerbos.cloud.wfs.vo.TaskCountVo;
import com.enerbos.cloud.wfs.vo.TaskForFilterVo;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年09月19日
 * @Description EAM站点dashboard
 */
@RestController
@Api(description = "站点dashboard(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DashboardSiteController {

    private Logger logger = LoggerFactory.getLogger(DashboardSiteController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private OrgClient orgClient;

    @Resource
    private MaintenanceWorkOrderClient maintenanceWorkOrderClient;

    @Autowired
    private RepairOrderClient repairOrderClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    /**
     * 查询维保工单各专业情况的统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd
     * @param endDate   结束时间 格式 yyyy-MM-dd
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "查询维保工单各专业情况的统计", response = Map.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardSite/findCountWorkOrder", method = RequestMethod.GET)
    public EnerbosMessage findCountWorkOrder(@ApiParam(value = "站点id", required = true) @RequestParam("siteId") String siteId, @ApiParam(value = "站点id", required = true) @RequestParam("orgId") String orgId,
                                             @ApiParam(value = "开始日期", required = true) @RequestParam("startDate") String startDate,
                                             @ApiParam(value = "结束日期", required = true) @RequestParam("endDate") String endDate) {
        try {
            List list = maintenanceWorkOrderClient.findCountWorkOrder(siteId, startDate, endDate);

            List<FieldDomainValueVo> domainValueVos = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId("woProjectType", siteId, orgId, "EAM");


            List xAxis = new ArrayList();

            List listtotal = new ArrayList<>(); //其他
            List listwxf = new ArrayList<>();//未完成
            List listywc = new ArrayList<>();//已完成


            for (FieldDomainValueVo fieldDomainValueVo : domainValueVos) {
                //X轴内容
                xAxis.add(fieldDomainValueVo.getDescription());
                //初始化 y轴数据
                listtotal.add("0");
                listwxf.add("0");
                listywc.add("0");
            }


            for (int i = 0; i < domainValueVos.size(); i++) {

                if (list.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        Map item = (Map) list.get(j);
                        String projectType = (String) item.get("project_type");

                        if (projectType.equals(domainValueVos.get(i).getValue())) {
                            listwxf.set(i, item.get("wxf"));
                            listywc.set(i, item.get("yxf"));
                            listtotal.set(i, item.get("total"));
                        }

                    }
                }
            }

            List yAxis = new ArrayList();


            Map mapqt = new HashMap();
            mapqt.put("name", "总量");
            mapqt.put("data", listtotal);
            yAxis.add(mapqt);


            Map mapwyc = new HashMap();
            mapwyc.put("name", "未完成数量");
            mapwyc.put("data", listwxf);
            yAxis.add(mapwyc);

            Map mapywc = new HashMap();
            mapywc.put("name", "已完成数量");
            mapywc.put("data", listywc);
            yAxis.add(mapywc);


            Map resultMap = new HashMap();
            resultMap.put("xAxis", xAxis);
            resultMap.put("yAxis", yAxis);

            return EnerbosMessage.createSuccessMsg(resultMap, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/dashboardSite/findCountWorkOrder ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/dashboardSite/findCountWorkOrder----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 查询未完成维保工单各专业情况的统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd
     * @param endDate   结束时间 格式 yyyy-MM-dd
     * @return 查询结果
     */
    @ApiOperation(value = "查询维保工单各专业情况的统计", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardSite/findCountUndoneWorkOrder", method = RequestMethod.GET)
    public EnerbosMessage findCountUndoneWorkOrder(@ApiParam(value = "站点id", required = true) @RequestParam("siteId") String siteId, @ApiParam(value = "站点id", required = true) @RequestParam("orgId") String orgId,
                                                   @ApiParam(value = "开始日期", required = true) @RequestParam("startDate") String startDate,
                                                   @ApiParam(value = "结束日期", required = true) @RequestParam("endDate") String endDate) {
        try {


            List<FieldDomainValueVo> domainValueVos = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId("woProjectType", siteId, orgId, "EAM");

            List list = maintenanceWorkOrderClient.findCountUndoneWorkOrder(siteId, startDate, endDate);

            List xAxis = new ArrayList();
            List listqt = new ArrayList<>(); //其他
            List listwxf = new ArrayList<>();//未完成
            List listgys = new ArrayList<>();//供应商
            List listqbj = new ArrayList<>();//缺备件
            List listzxz = new ArrayList<>();//执行中

            for (FieldDomainValueVo fieldDomainValueVo : domainValueVos) {
                //X轴内容
                xAxis.add(fieldDomainValueVo.getDescription());
                //初始化 y轴数据
                listwxf.add("0");
                listgys.add("0");
                listqbj.add("0");
                listqt.add("0");
                listzxz.add("0");
            }


            for (int i = 0; i < domainValueVos.size(); i++) {

                if (list.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        Map item = (Map) list.get(j);
                        String projectType = (String) item.get("project_type");

                        if (projectType.equals(domainValueVos.get(i).getValue())) {
                            listwxf.set(i, item.get("wxf"));
                            listgys.set(i, item.get("gys"));
                            listqbj.set(i, item.get("qbj"));
                            listqt.set(i, item.get("qt"));
                            int zxz = (Integer) item.get("wxf") - (Integer) item.get("qt") - (Integer) item.get("gys") - (Integer) item.get("qbj");
                            listzxz.set(i, zxz);
                        }

                    }
                }
            }


            List yAxis = new ArrayList();

            Map mapqt = new HashMap();
            mapqt.put("name", "其他");
            mapqt.put("data", listqt);
            yAxis.add(mapqt);


            Map mapwyc = new HashMap();
            mapwyc.put("name", "未完成数量");
            mapwyc.put("data", listwxf);
            yAxis.add(mapwyc);

            Map mapgys = new HashMap();
            mapgys.put("name", "供应商原因");
            mapgys.put("data", listgys);
            yAxis.add(mapgys);


            Map mapzxz = new HashMap();
            mapzxz.put("name", "执行中");
            mapzxz.put("data", listzxz);
            yAxis.add(mapzxz);


            Map mapqbj = new HashMap();
            mapqbj.put("name", "缺备件原因");
            mapqbj.put("data", listqbj);
            yAxis.add(mapqt);


            Map resultMap = new HashMap();
            resultMap.put("xAxis", xAxis);
            resultMap.put("yAxis", yAxis);


            return EnerbosMessage.createSuccessMsg(resultMap, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/dashboardSite/findCountUndoneWorkOrder ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/dashboardSite/findCountUndoneWorkOrder----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 按月查询报修工单各专业的统计分析
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd
     * @param endDate   结束时间 格式 yyyy-MM-dd
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按月查询报修工单各专业的统计分析", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/findCountRepair", method = RequestMethod.GET)
    public EnerbosMessage findCountRepair(@ApiParam(value = "站点id", required = true) String siteId, @ApiParam(value = "站点id", required = true) String orgId,
                                          @ApiParam(value = "开始时间", required = true) @RequestParam("startDate") String startDate,
                                          @ApiParam(value = "结束时间", required = true) @RequestParam("endDate") String endDate) {
        try {

            List<FieldDomainValueVo> domainValueVos = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId("roProjectType", siteId, orgId, "EAM");

            List list = repairOrderClient.findCountRepair(siteId, startDate, endDate);

            List xAxis = new ArrayList();

            List listtotal = new ArrayList<>(); //其他
            List listwxf = new ArrayList<>();//未完成
            List listywc = new ArrayList<>();//已完成


            for (FieldDomainValueVo fieldDomainValueVo : domainValueVos) {
                //X轴内容
                xAxis.add(fieldDomainValueVo.getDescription());
                //初始化 y轴数据
                listtotal.add("0");
                listwxf.add("0");
                listywc.add("0");
            }


            for (int i = 0; i < domainValueVos.size(); i++) {

                if (list.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        List item = (List) list.get(j);
                        String projectType = (String) item.get(0);

                        if (projectType.equals(domainValueVos.get(i).getValue())) {
                            listwxf.set(i, item.get(1).toString());
                            listywc.set(i, item.get(2).toString());
                            listtotal.set(i, Integer.valueOf(item.get(1).toString()) + Integer.valueOf(item.get(2).toString()));
//                            listzxz.set(i,Integer.toString(Integer.valueOf(item.get(1).toString()) - Integer.valueOf(item.get(4).toString()) - Integer.valueOf(item.get(2).toString()) - Integer.valueOf(item.get(3).toString())));
                        }
                        ;

                    }
                }
            }

            List yAxis = new ArrayList();


            Map mapqt = new HashMap();
            mapqt.put("name", "总量");
            mapqt.put("data", listtotal);
            yAxis.add(mapqt);


            Map mapwyc = new HashMap();
            mapwyc.put("name", "未完成数量");
            mapwyc.put("data", listwxf);
            yAxis.add(mapwyc);

            Map mapywc = new HashMap();
            mapywc.put("name", "已完成数量");
            mapywc.put("data", listywc);
            yAxis.add(mapywc);


            Map resultMap = new HashMap();
            resultMap.put("xAxis", xAxis);
            resultMap.put("yAxis", yAxis);


            return EnerbosMessage.createSuccessMsg(resultMap, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/findCountRepair");
        }
    }

    /**
     * 按月查询未修复报修工单各专业的统计分析
     *
     * @param siteId    站点id
     * @param startDate 开始时间 格式 yyyy-MM-dd
     * @param endDate   结束时间 格式 yyyy-MM-dd
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按月查询未修复报修工单各专业的统计分析", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/repair/order/findUndoneCountRepair", method = RequestMethod.GET)
    public EnerbosMessage findUndoneCountRepair(@ApiParam(value = "站点id", required = true) String siteId, @ApiParam(value = "组织id", required = true) String orgId,
                                                @ApiParam(value = "开始时间", required = true) @RequestParam("startDate") String startDate,
                                                @ApiParam(value = "结束时间", required = true) @RequestParam("endDate") String endDate) {
        try {
            List list = repairOrderClient.findUndoneCountRepair(siteId, startDate, endDate);

            List<FieldDomainValueVo> domainValueVos = fieldDomainClient.findDomainValueByDomainNumAndSiteIdAndProId("roProjectType", siteId, orgId, "EAM");


            List xAxis = new ArrayList();
            List listqt = new ArrayList<>(); //其他
            List listwxf = new ArrayList<>();//未完成
            List listgys = new ArrayList<>();//供应商
            List listqbj = new ArrayList<>();//缺备件
            List listzxz = new ArrayList<>();//执行中

            for (FieldDomainValueVo fieldDomainValueVo : domainValueVos) {
                //X轴内容
                xAxis.add(fieldDomainValueVo.getDescription());
                //初始化 y轴数据
                listwxf.add("0");
                listgys.add("0");
                listqbj.add("0");
                listqt.add("0");
                listzxz.add("0");
            }


            for (int i = 0; i < domainValueVos.size(); i++) {

                if (list.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        List item = (List) list.get(j);
                        String projectType = (String) item.get(0);

                        if (StringUtils.isNotBlank(projectType)&&projectType.equals(domainValueVos.get(i).getValue())) {
                            listwxf.set(i, item.get(1).toString());
                            listgys.set(i, item.get(2).toString());
                            listqbj.set(i, item.get(3).toString());
                            listqt.set(i, item.get(4).toString());
                            listzxz.set(i, Integer.toString(Integer.valueOf(item.get(1).toString()) - Integer.valueOf(item.get(4).toString()) - Integer.valueOf(item.get(2).toString()) - Integer.valueOf(item.get(3).toString())));

                        }
                    }
                }
            }


            List yAxis = new ArrayList();

            Map mapqt = new HashMap();
            mapqt.put("name", "其他");
            mapqt.put("data", listqt);
            yAxis.add(mapqt);


            Map mapwyc = new HashMap();
            mapwyc.put("name", "未完成数量");
            mapwyc.put("data", listwxf);
            yAxis.add(mapwyc);

            Map mapgys = new HashMap();
            mapgys.put("name", "供应商原因");
            mapgys.put("data", listgys);
            yAxis.add(mapgys);


            Map mapzxz = new HashMap();
            mapzxz.put("name", "执行中");
            mapzxz.put("data", listzxz);
            yAxis.add(mapzxz);


            Map mapqbj = new HashMap();
            mapqbj.put("name", "缺备件原因");
            mapqbj.put("data", listqbj);
            yAxis.add(mapqbj);


            Map resultMap = new HashMap();
            resultMap.put("xAxis", xAxis);
            resultMap.put("yAxis", yAxis);


            return EnerbosMessage.createSuccessMsg(resultMap, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/repair/order/findUndoneCountRepair");
        }
    }

    /**
     * findCountByStatus：查询工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
     *
     * @param orgId  组织ID
     * @param siteId 站点id
     * @param user
     * @return 查询结果
     */
    @ApiOperation(value = "查询工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardSite/findCountByStatus", method = RequestMethod.GET)
    public EnerbosMessage findCountByStatus(@ApiParam(value = "组织id", required = true) @RequestParam("orgId") String orgId,
                                            @ApiParam(value = "站点id", required = true) @RequestParam("siteId") String siteId,
                                            @ApiParam(value = "开始日期", required = false) @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                            @ApiParam(value = "结束日期", required = false) @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Principal user) {
        try {
            OrderCountBySiteVo orderCountBySiteVo = new OrderCountBySiteVo();
            //查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
            OrderCountBySiteVo workOrder = maintenanceWorkOrderClient.findCountByStatus(orgId, siteId, startDate, endDate);
            if (workOrder != null) {
                orderCountBySiteVo.setTotal(Integer.toString(Integer.valueOf(orderCountBySiteVo.getTotal()) + Integer.valueOf(workOrder.getTotal())));
                orderCountBySiteVo.setWwc(Integer.toString(Integer.valueOf(orderCountBySiteVo.getWwc()) + Integer.valueOf(workOrder.getWwc())));
                orderCountBySiteVo.setYwc(Integer.toString(Integer.valueOf(orderCountBySiteVo.getYwc()) + Integer.valueOf(workOrder.getYwc())));
                orderCountBySiteVo.setGq(Integer.toString(Integer.valueOf(orderCountBySiteVo.getGq()) + Integer.valueOf(workOrder.getGq())));
                orderCountBySiteVo.setDfp(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDfp()) + Integer.valueOf(workOrder.getDfp())));
                orderCountBySiteVo.setDhb(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDhb()) + Integer.valueOf(workOrder.getDhb())));
                orderCountBySiteVo.setDtb(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDtb()) + Integer.valueOf(workOrder.getDtb())));
                orderCountBySiteVo.setDys(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDys()) + Integer.valueOf(workOrder.getDys())));
                orderCountBySiteVo.setDjd(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDjd()) + Integer.valueOf(workOrder.getDjd())));
                orderCountBySiteVo.setYsdqr(Integer.toString(Integer.valueOf(orderCountBySiteVo.getYsdqr()) + Integer.valueOf(workOrder.getYsdqr())));
                orderCountBySiteVo.setSqgq(Integer.toString(Integer.valueOf(orderCountBySiteVo.getSqgq()) + Integer.valueOf(workOrder.getSqgq())));
                orderCountBySiteVo.setQx(Integer.toString(Integer.valueOf(orderCountBySiteVo.getQx()) + Integer.valueOf(workOrder.getQx())));
            }
            //查询报修工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
            OrderCountBySiteVo repair = repairOrderClient.findCountByStatus(orgId, siteId, startDate, endDate);
            if (repair != null) {
                orderCountBySiteVo.setTotal(Integer.toString(Integer.valueOf(orderCountBySiteVo.getTotal()) + Integer.valueOf(repair.getTotal())));
                orderCountBySiteVo.setWwc(Integer.toString(Integer.valueOf(orderCountBySiteVo.getWwc()) + Integer.valueOf(repair.getWwc())));
                orderCountBySiteVo.setYwc(Integer.toString(Integer.valueOf(orderCountBySiteVo.getYwc()) + Integer.valueOf(repair.getYwc())));
                orderCountBySiteVo.setGq(Integer.toString(Integer.valueOf(orderCountBySiteVo.getGq()) + Integer.valueOf(repair.getGq())));
                orderCountBySiteVo.setDfp(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDfp()) + Integer.valueOf(repair.getDfp())));
                orderCountBySiteVo.setDhb(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDhb()) + Integer.valueOf(repair.getDhb())));
                orderCountBySiteVo.setDtb(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDtb()) + Integer.valueOf(repair.getDtb())));
                orderCountBySiteVo.setDys(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDys()) + Integer.valueOf(repair.getDys())));
                orderCountBySiteVo.setDjd(Integer.toString(Integer.valueOf(orderCountBySiteVo.getDjd()) + Integer.valueOf(repair.getDjd())));
                orderCountBySiteVo.setYsdqr(Integer.toString(Integer.valueOf(orderCountBySiteVo.getYsdqr()) + Integer.valueOf(repair.getYsdqr())));
                orderCountBySiteVo.setSqgq(Integer.toString(Integer.valueOf(orderCountBySiteVo.getSqgq()) + Integer.valueOf(repair.getSqgq())));
                orderCountBySiteVo.setQx(Integer.toString(Integer.valueOf(orderCountBySiteVo.getQx()) + Integer.valueOf(repair.getQx())));
            }


            List result = new ArrayList();

//            Map<String, Object> map = new HashMap<String, Object>();
//
//            BeanInfo beanInfo = Introspector.getBeanInfo(orderCountBySiteVo.getClass());
//
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
////                property.
//                String key = property.getName();
//                if (key.compareToIgnoreCase("class") == 0) {
//                    continue;
//                }
//                Method getter = property.getReadMethod();
//                Object value = getter!=null ? getter.invoke(orderCountBySiteVo) : null;
//                map.put(key, value);
//            }
            Map map = new HashMap();
            map.put("name", "总量");
            map.put("value", orderCountBySiteVo.getTotal());
            result.add(map);

            Map map1 = new HashMap();
            map1.put("name", "未完成数量");
            map1.put("value", orderCountBySiteVo.getWwc());

            result.add(map1);

            Map map2 = new HashMap();
            map2.put("name", "已完成数量");
            map2.put("value", orderCountBySiteVo.getYwc());
            result.add(map2);

            Map map3 = new HashMap();
            map3.put("name", "挂起数量");
            map3.put("value", orderCountBySiteVo.getGq());
            result.add(map3);

            Map map4 = new HashMap();
            map4.put("name", "待分派数量");
            map4.put("value", orderCountBySiteVo.getDfp());
            result.add(map4);

            Map map5 = new HashMap();
            map5.put("name", "待汇报数量");
            map5.put("value", orderCountBySiteVo.getDhb());
            result.add(map5);

            Map map6 = new HashMap();
            map6.put("name", "待接单数量");
            map6.put("value", orderCountBySiteVo.getDjd());
            result.add(map6);

            Map map7 = new HashMap();
            map7.put("name", "待验收数量");
            map7.put("value", orderCountBySiteVo.getDys());
            result.add(map7);

            Map map8 = new HashMap();
            map8.put("name", "取消数量");
            map8.put("value", orderCountBySiteVo.getQx());
            result.add(map8);

            Map map9 = new HashMap();
            map9.put("name", "待提报数量");
            map9.put("value", orderCountBySiteVo.getDtb());
            result.add(map9);

            Map map10 = new HashMap();
            map10.put("value", orderCountBySiteVo.getSqgq());
            map10.put("name", "申请挂起数量");
            result.add(map10);


            Map map11 = new HashMap();
            map11.put("name", "验收待确认数量");
            map11.put("value", orderCountBySiteVo.getYsdqr());
            result.add(map11);


            return EnerbosMessage.createSuccessMsg(result, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/dashboardSite/findCountByStatus ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/dashboardSite/findCountByStatus----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param siteId 站点id
     * @return 查询结果
     */
    @ApiOperation(value = "今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardSite/findCountAndRingratio", method = RequestMethod.GET)
    public EnerbosMessage findCountAndRingratio(@ApiParam(value = "站点id", required = true) String siteId) {
        try {

            OrderCountAndRingratio orderCountAndRingratio = new OrderCountAndRingratio();
            List numList = new ArrayList();
            OrderCountAndRingratio workOrder = maintenanceWorkOrderClient.findCountAndRingratio(siteId);


            if (workOrder != null) {
                orderCountAndRingratio.setTotal(Integer.toString(Integer.valueOf(orderCountAndRingratio.getTotal()) + Integer.valueOf(workOrder.getTotal())));
                orderCountAndRingratio.setWwc(Integer.toString(Integer.valueOf(orderCountAndRingratio.getWwc()) + Integer.valueOf(workOrder.getWwc())));
                orderCountAndRingratio.setYwc(Integer.toString(Integer.valueOf(orderCountAndRingratio.getYwc()) + Integer.valueOf(workOrder.getYwc())));
                orderCountAndRingratio.setList(workOrder.getList());
            }
            OrderCountAndRingratio repairOrder = repairOrderClient.findCountAndRingratio(siteId);
            if (repairOrder != null) {
                orderCountAndRingratio.setTotal(Integer.toString(Integer.valueOf(orderCountAndRingratio.getTotal()) + Integer.valueOf(repairOrder.getTotal())));
                orderCountAndRingratio.setWwc(Integer.toString(Integer.valueOf(orderCountAndRingratio.getWwc()) + Integer.valueOf(repairOrder.getWwc())));
                orderCountAndRingratio.setYwc(Integer.toString(Integer.valueOf(orderCountAndRingratio.getYwc()) + Integer.valueOf(repairOrder.getYwc())));
                for (int i = 0; i < 5; i++) {
                    int in = Integer.parseInt((String) repairOrder.getList().get(i));
                    int in2 = Integer.parseInt((String) orderCountAndRingratio.getList().get(i));
                    orderCountAndRingratio.getList().set(i, in2 + in);
                }
            }

            String total = orderCountAndRingratio.getTotal();
            List list = orderCountAndRingratio.getList();
            List resultList = new ArrayList();
            for (int i = 0; i < 5; i++) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String today = formatter.format(date);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -i);
                Date date1 = calendar.getTime();
                String todayOfLast = formatter.format(date1);
                Map map = new HashMap();

                map.put("num", list.get(i));
                map.put("date", todayOfLast);
                map.put("ring", getRing(total, list, i));

                resultList.add(map);
            }
            orderCountAndRingratio.setList(resultList);

            return EnerbosMessage.createSuccessMsg(orderCountAndRingratio, "查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/dashboardSite/findCountAndRingratio ------", e);
            String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
            String statusCode = "";
            try {
                JSONObject jsonMessage = JSONObject.parseObject(message);
                if (jsonMessage != null) {
                    statusCode = jsonMessage.get("status").toString();
                }
            } catch (Exception jsonException) {
                logger.error("-----/eam/open/dashboardSite/findCountAndRingratio----", jsonException);
            }
            return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
        }
    }

    /**
     * 计算环比结果
     *
     * @param total
     * @param list
     */
    private Object getRing(String total, List list, int i) {
        int num = 0;
        if (i - 1 < 0) {
            num = Integer.parseInt(total);
        }

        int lastnum = (Integer) list.get(i);

        if (lastnum == 0) {
            return ((num - lastnum) / 1) * 100;
        } else {
            return ((num - lastnum) / lastnum) * 100;
        }

    }

    /**
     * 按工单类型分类查询我的待办/经办任务数量
     *
     * @param siteId 站点id
     * @param user
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按工单类型分类查询我的待办/经办任务数量", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardSite/findTaskTotalGroupByOrderType", method = RequestMethod.GET)
    public EnerbosMessage findTaskTotalGroupByOrderType(@ApiParam(value = "站点ID", required = true) @RequestParam("siteId") String siteId, Principal user) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info("/eam/open/dashboardSite/findTaskTotalGroupByOrderType, host: [{}:{}], service_id: {}, user: {},siteId: {}", instance.getHost(), instance.getPort(), instance.getServiceId(), user, siteId);

            String userId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            SiteVoForDetail site = siteClient.findById(siteId);
            if (site == null || null == site.getCode()) {
                return EnerbosMessage.createSuccessMsg(null, "站点为空！", "");
            }
            TaskForFilterVo taskForFilterVo = new TaskForFilterVo();
            taskForFilterVo.setUserId(userId);
            taskForFilterVo.setSiteCode(site.getCode());
            List<TaskCountVo> list = processTaskClient.findTaskCountGroupByProcessKey(taskForFilterVo);
            Map result = new HashMap();
            list.forEach(taskCount -> {
                result.put(taskCount.getProcessKey(), taskCount.getTaskCount());
            });
            List<TaskCountVo> hiTaskList = processTaskClient.findActHiTaskinstCountGroupByProcessKey(taskForFilterVo);
            List<DashboardSiteTaskCountVo> resultList = new ArrayList<>();
            hiTaskList.forEach(doneTaskCount -> {
                DashboardSiteTaskCountVo taskCountVo = new DashboardSiteTaskCountVo();
                taskCountVo.setOrderType(doneTaskCount.getProcessKey());
                taskCountVo.setDoneTaskTotal(doneTaskCount.getTaskCount());
                if (result.get(doneTaskCount.getProcessKey()) != null) {
                    taskCountVo.setToDoTaskTotal(result.remove(doneTaskCount.getProcessKey()).toString());
                } else {
                    taskCountVo.setToDoTaskTotal("0");
                }
                resultList.add(taskCountVo);

            });
            for (Object key : result.keySet()) {
                DashboardSiteTaskCountVo taskCountVo = new DashboardSiteTaskCountVo();
                taskCountVo.setOrderType(key.toString());
                taskCountVo.setDoneTaskTotal("0");
                taskCountVo.setToDoTaskTotal(result.get(key).toString());
                resultList.add(taskCountVo);
            }
            return EnerbosMessage.createSuccessMsg(resultList, "查询成功", "");
        } catch (Exception e) {
            return buildErrorMsg(e, "/eam/open/dashboardSite/findTaskTotalGroupByOrderType");
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
}