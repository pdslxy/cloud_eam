package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.CompanyClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.*;
import com.enerbos.cloud.eam.vo.DashboardOrderCountVo;
import com.enerbos.cloud.eam.vo.OrderCountBySiteVo;
import com.enerbos.cloud.eam.vo.OrderMaxCountVo;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.wfs.client.ProcessActivitiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
 * @Description EAM组织dashboard
 */
@RestController
@Api(description = "组织dashboard(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class DashboardOrgController {

    private Logger logger = LoggerFactory.getLogger(DashboardOrgController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SiteClient siteClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    private PersonAndUserClient personAndUserClient;

    @Autowired
    private OrgClient orgClient;

    @Resource
    private MaintenanceWorkOrderClient maintenanceWorkOrderClient;

    @Resource
    private MaintenanceWorkOrderNeedItemClient maintenanceWorkOrderNeedItemClient;

    @Resource
    private AssetClient assetClient;

    @Resource
    private MaintenanceWorkOrderStepClient maintenanceWorkOrderStepClient;

    @Resource
    private MaintenanceWorkOrderActualItemClient maintenanceWorkOrderActualItemClient;

    @Resource
    private MaintenanceWorkOrderAssetClient maintenanceWorkOrderAssetClient;

    @Resource
    private MaintenanceJobStandardClient maintenanceJobStandardClient;

    @Autowired
    private ProcessActivitiClient processActivitiClient;

    @Resource
    private MaintenanceJobStandardTaskClient maintenanceJobStandardTaskClient;

    @Autowired
    private ContractClient contractClient;

    @Autowired
    private RepairOrderClient repairOrderClient;

    @Autowired
    private UgroupClient ugroupClient;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private OrderPersonClient orderPersonClient;

    @Autowired
    private UserGroupDomainColler userGroupDomainColler;

    @Autowired
    private DispatchWorkOrderClient dispatchWorkOrderClient;

    @Autowired
    private PatrolOrderClient patrolOrderClient;

    /**
     * findCountOrderTotal:按工单类型查询总部所有工单数
     *
     * @param orgId     组织id
     * @param startDate 开始时间 格式 yyyy-MM-dd
     * @param endDate   结束时间 格式 yyyy-MM-dd
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "按工单类型查询总部所有工单数", response = DashboardOrderCountVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardOrg/findCountOrderTotal", method = RequestMethod.GET)
    public EnerbosMessage findCountOrderTotal(@ApiParam(value = "组织id", required = true) @RequestParam("orgId") String orgId,
                                              @ApiParam(value = "开始日期", required = false) @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                              @ApiParam(value = "结束日期", required = false) @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List result = new ArrayList<>();
            DashboardOrderCountVo dashboardOrderCountVo = new DashboardOrderCountVo();


            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            if (startDate == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                String dateString = format.format(calendar.getTime()) +" 00:00:00" ;

                startDate = format.parse(dateString) ;
            }
            if (endDate == null) {
                 Calendar calendar = Calendar.getInstance();
                String dateString = format.format(calendar.getTime()) +" 23:59:59" ;
                endDate = format.parse(dateString);
            }

            //查询维保工单总数
            List<DashboardOrderCountVo> dashboardOrderCountVos = maintenanceWorkOrderClient.findWorkOrderTotal(orgId, startDate, endDate);

            for (DashboardOrderCountVo dashboardOrderCount : dashboardOrderCountVos) {
                String orderType = dashboardOrderCount.getOrderType();
                Map map = new HashMap() ;
                if (orderType.equals("PM")) {
                    map.put("name","保养工单");
                    map.put("value",dashboardOrderCount.getOrderTotal()) ;
                    //dashboardOrderCount.setOrderType("保养工单");
                } else {
                    map.put("name","维修工单");
                    map.put("value",dashboardOrderCount.getOrderTotal()) ;
                    //dashboardOrderCount.setOrderType("维修工单");
                }
                result.add(map) ;
            }

//            if (dashboardOrderCountVos.size() > 0) {
//                result.addAll(dashboardOrderCountVos);
//            }


            //查询报修工单总数
            OrderCountBySiteVo repairOrder = repairOrderClient.findCountByStatus(orgId, null, startDate, endDate);
            if (repairOrder != null) {
                Map map = new HashMap() ;
                map.put("name","报修工单");
                map.put("value",repairOrder.getTotal()) ;
                result.add(map);
            }

            //查询派工工单总数
            OrderCountBySiteVo dispatchOrder = dispatchWorkOrderClient.findDispatchWorkOrderTotal(orgId, startDate, endDate);
            if (dispatchOrder != null) {
//                dashboardOrderCountVo = new DashboardOrderCountVo();
//                dashboardOrderCountVo.setOrderType("派工工单");
//                dashboardOrderCountVo.setOrderTotal(dispatchOrder.getTotal());
//                result.add(dashboardOrderCountVo);
                Map map = new HashMap() ;
                map.put("name","派工工单");
                map.put("value",dispatchOrder.getTotal()) ;
                result.add(map);
            }
            //查询巡检工单总数
            OrderCountBySiteVo partolOrder = patrolOrderClient.findPatrolOrderTotal(orgId, startDate, endDate);
            if (dispatchOrder != null) {
//                dashboardOrderCountVo = new DashboardOrderCountVo();
//                dashboardOrderCountVo.setOrderType("巡检工单");
//                dashboardOrderCountVo.setOrderTotal(partolOrder.getTotal());
//                result.add(dashboardOrderCountVo);
                Map map = new HashMap() ;
                map.put("name","巡检工单");
                map.put("value",partolOrder.getTotal()) ;
                result.add(map);
            }

            return EnerbosMessage.createSuccessMsg(result, "查询成功", "");
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
     * findCountOrderTotal:按工单类型查询总部所有工单数
     *
     * @param orgId 组织id
     * @return EnerbosMessage返回执行码及数据
     */
    @ApiOperation(value = "统计总部各工单最大值", response = OrderMaxCountVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/dashboardOrg/findMaxCountOrder", method = RequestMethod.GET)
    public EnerbosMessage findMaxCountOrder(@ApiParam(value = "组织id", required = true) @RequestParam("orgId") String orgId) {
        try {
            List<OrderMaxCountVo> result = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = format.format(new Date());

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            String startDate = format.format(calendar.getTime());

            //维保
            List<OrderMaxCountVo> list = maintenanceWorkOrderClient.findMaxCountOrder(orgId, startDate, endDate);
            //报修
            OrderMaxCountVo repairorderMaxCountVo = repairOrderClient.findMaxCountOrder(orgId, startDate, endDate);
            //派工
            OrderMaxCountVo dispatchorderMaxCountVo = dispatchWorkOrderClient.findMaxCountOrder(orgId, startDate, endDate);
            //巡检
            OrderMaxCountVo patrolorderMaxCountVo = patrolOrderClient.findMaxCountOrder(orgId, startDate, endDate);

            if (list.size() > 0) {
                result.addAll(list);
            }
            if (repairorderMaxCountVo != null) {
                result.add(repairorderMaxCountVo);
            }
            if (dispatchorderMaxCountVo != null) {
                result.add(dispatchorderMaxCountVo);
            }
            if (patrolorderMaxCountVo != null) {
                result.add(patrolorderMaxCountVo);
            }

            for (OrderMaxCountVo orderMaxCountVo : result) {

                SiteVoForDetail siteVoForDetail = siteClient.findById(orderMaxCountVo.getSiteId());
                if (siteVoForDetail != null) {
                    orderMaxCountVo.setSiteName(siteVoForDetail.getName());
                }
            }


            return EnerbosMessage.createSuccessMsg(result, "查询成功", "");
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


//	/**
//	 * 查询未完成维保工单各专业情况的统计
//	 * @param siteId 站点id
//	 * @param startDate 开始时间 格式 yyyy-MM-dd
//	 * @param endDate 结束时间 格式 yyyy-MM-dd
//	 * @return 查询结果
//	 */
//	@ApiOperation(value = "查询维保工单各专业情况的统计", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
//	@RequestMapping(value = "/eam/open/dashboardSite/findCountUndoneWorkOrder", method = RequestMethod.GET)
//	public EnerbosMessage findCountUndoneWorkOrder(@ApiParam(value = "站点id", required = true) @RequestParam("siteId") String siteId,
//												   @ApiParam(value = "开始日期", required = true) @RequestParam("startDate")  String startDate,
//												   @ApiParam(value = "结束日期", required = true) @RequestParam("endDate")  String endDate){
//		try {
//			OrderCountVo OrderCountVo=maintenanceWorkOrderClient.findCountUndoneWorkOrder(siteId,startDate,endDate);
//			return EnerbosMessage.createSuccessMsg(OrderCountVo, "查询成功", "");
//		} catch (Exception e) {
//			logger.error("-----/eam/open/dashboardSite/findCountUndoneWorkOrder ------", e);
//			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
//			String statusCode  = "" ;
//			try {
//				JSONObject jsonMessage = JSONObject.parseObject(message);
//				if(jsonMessage !=null){
//					statusCode =   jsonMessage.get("status").toString();
//				}
//			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/dashboardSite/findCountUndoneWorkOrder----",jsonException);
//			}
//			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
//		}
//	}
//
//	/**
//	 * 按月查询报修工单各专业的统计分析
//	 * @param siteId 站点id
//	 * @param startDate 开始时间 格式 yyyy-MM-dd
//	 * @param endDate 结束时间 格式 yyyy-MM-dd
//	 * @return EnerbosMessage返回执行码及数据
//	 */
//	@ApiOperation(value = "按月查询报修工单各专业的统计分析", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
//	@RequestMapping(value = "/eam/open/repair/order/findCountRepair", method = RequestMethod.GET)
//	public EnerbosMessage findCountRepair(@ApiParam(value = "站点id", required = true) String siteId,
//										  @ApiParam(value = "开始时间", required = true) @RequestParam("startDate") String startDate,
//										  @ApiParam(value = "结束时间", required = true) @RequestParam("endDate") String endDate) {
//		try {
//			OrderCountVo OrderCountVo=repairOrderClient.findCountRepair(siteId,startDate,endDate);
//			return EnerbosMessage.createSuccessMsg(OrderCountVo, "查询成功", "");
//		} catch (Exception e) {
//			return buildErrorMsg(e, "/eam/open/repair/order/findCountRepair");
//		}
//	}
//
//	/**
//	 * 按月查询未修复报修工单各专业的统计分析
//	 * @param siteId 站点id
//	 * @param startDate 开始时间 格式 yyyy-MM-dd
//	 * @param endDate 结束时间 格式 yyyy-MM-dd
//	 * @return EnerbosMessage返回执行码及数据
//	 */
//	@ApiOperation(value = "按月查询未修复报修工单各专业的统计分析", response = EnerbosMessage.class, notes = "返回数据统一包装在 EnerbosMessage.data")
//	@RequestMapping(value = "/eam/open/repair/order/findUndoneCountRepair", method = RequestMethod.GET)
//	public EnerbosMessage findUndoneCountRepair(@ApiParam(value = "站点id", required = true) String siteId,
//												@ApiParam(value = "开始时间", required = true) @RequestParam("startDate") String startDate,
//												@ApiParam(value = "结束时间", required = true) @RequestParam("endDate") String endDate) {
//		try {
//			OrderCountVo orderCountVo=repairOrderClient.findUndoneCountRepair(startDate,endDate,siteId);
//			return EnerbosMessage.createSuccessMsg(orderCountVo, "查询成功", "");
//		} catch (Exception e) {
//			return buildErrorMsg(e, "/eam/open/repair/order/findUndoneCountRepair");
//		}
//	}
//
//	/**
//	 * findCountByStatus：查询工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
//	 * @param siteId 站点id
//	 * @return 查询结果
//	 */
//	@ApiOperation(value = "查询工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
//	@RequestMapping(value = "/eam/open/dashboardSite/findCountByStatus", method = RequestMethod.GET)
//	public EnerbosMessage findCountByStatus(@ApiParam(value = "站点id", required = true) String siteId){
//		try {
//			OrderCountBySiteVo orderCountBySiteVo=maintenanceWorkOrderClient.findCountByStatus(siteId);
//			return EnerbosMessage.createSuccessMsg(orderCountBySiteVo, "查询成功", "");
//		} catch (Exception e) {
//			logger.error("-----/eam/open/dashboardSite/findCountByStatus ------", e);
//			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
//			String statusCode  = "" ;
//			try {
//				JSONObject jsonMessage = JSONObject.parseObject(message);
//				if(jsonMessage !=null){
//					statusCode =   jsonMessage.get("status").toString();
//				}
//			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/dashboardSite/findCountByStatus----",jsonException);
//			}
//			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
//		}
//	}
//
//	/**
//	 * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
//	 * @param siteId 站点id
//	 * @return 查询结果
//	 */
//	@ApiOperation(value = "今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比", response = EnerbosMessage.class, notes="返回数据统一包装在 EnerbosMessage.data")
//	@RequestMapping(value = "/eam/open/dashboardSite/findCountAndRingratio", method = RequestMethod.GET)
//	public EnerbosMessage findCountAndRingratio(@ApiParam(value = "站点id", required = true) String siteId){
//		try {
//			OrderCountAndRingratio orderCountAndRingratio=maintenanceWorkOrderClient.findCountAndRingratio(siteId);
//			return EnerbosMessage.createSuccessMsg(orderCountAndRingratio, "查询成功", "");
//		} catch (Exception e) {
//			logger.error("-----/eam/open/dashboardSite/findCountAndRingratio ------", e);
//			String message =  e.getCause().getMessage().substring( e.getCause().getMessage().indexOf("{")) ;
//			String statusCode  = "" ;
//			try {
//				JSONObject jsonMessage = JSONObject.parseObject(message);
//				if(jsonMessage !=null){
//					statusCode =   jsonMessage.get("status").toString();
//				}
//			} catch (Exception jsonException) {
//				logger.error("-----/eam/open/dashboardSite/findCountAndRingratio----",jsonException);
//			}
//			return EnerbosMessage.createErrorMsg(statusCode, message, e.getStackTrace().toString());
//		}
//	}
//
//	private EnerbosMessage buildErrorMsg(Exception exception, String uri) {
//		logger.error(String.format("------- %s --------", uri), exception);
//		String message, statusCode = "";
//		if (exception instanceof HystrixRuntimeException) {
//			Throwable fallbackException = exception.getCause();
//			if (fallbackException.getMessage().contains("{")) {
//				message = fallbackException.getMessage().substring(fallbackException.getMessage().indexOf("{"));
//				try {
//					JSONObject jsonMessage = JSONObject.parseObject(message);
//					if (jsonMessage != null) {
//						statusCode = jsonMessage.get("status").toString();
//						message = jsonMessage.get("message").toString();
//					}
//				} catch (Exception jsonException) {
//					logger.error(String.format("------- %s --------", uri), jsonException);
//				}
//			} else {
//				message = fallbackException.getMessage();
//				if (fallbackException instanceof EnerbosException) {
//					statusCode = ((EnerbosException) exception).getErrorCode();
//				}
//			}
//		} else {
//			message = exception.getMessage();
//			if (exception instanceof EnerbosException) {
//				statusCode = ((EnerbosException) exception).getErrorCode();
//			}
//		}
//		return EnerbosMessage.createErrorMsg(statusCode, message, exception.getStackTrace().toString());
//	}
}