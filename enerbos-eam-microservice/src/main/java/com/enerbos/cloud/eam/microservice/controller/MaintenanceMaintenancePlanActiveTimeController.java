package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanActiveTime;
import com.enerbos.cloud.eam.microservice.service.MaintenanceMaintenancePlanActiveTimeService;
import com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo;
import com.enerbos.cloud.util.ReflectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM预防维护计划接口
 */
@RestController
public class MaintenanceMaintenancePlanActiveTimeController {

	private final static Log logger = LogFactory.getLog(MaintenanceMaintenancePlanActiveTimeController.class);

    @Autowired
    private MaintenanceMaintenancePlanActiveTimeService maintenanceMaintenancePlanActiveTimeService;

    /**
     * findAllMaintenancePlanActiveTime: 根据预防维护计划ID查询有效时间列表
     * @param maintenancePlanId 预防维护计划ID
     * @return List<MaintenanceMaintenancePlanActiveTimeVo> 分页查询有效时间列表VO集合
     */
    @RequestMapping(value = "/eam/micro/maintenancePlan/findAllMaintenancePlanActiveTime",method = RequestMethod.GET)
    public List<MaintenanceMaintenancePlanActiveTimeVo> findAllMaintenancePlanActiveTime(@RequestParam("maintenancePlanId") String maintenancePlanId) {
        List<MaintenanceMaintenancePlanActiveTimeVo> result = maintenanceMaintenancePlanActiveTimeService.findAllMaintenancePlanActiveTime(maintenancePlanId);
        return result;
    }

    /**
     * saveMaintenancePlanActiveTimeList: 保存有效时间
     * @param maintenanceMaintenancePlanActiveTimeVoList 有效时间实体vo集合 {@link com.enerbos.cloud.eam.vo.MaintenanceMaintenancePlanActiveTimeVo}
     * @return List<MaintenanceMaintenancePlanActiveTimeVo> 有效时间实体vo
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/saveMaintenancePlanActiveTimeList")
    public List<MaintenanceMaintenancePlanActiveTimeVo> saveMaintenancePlanActiveTimeList(@RequestBody List<MaintenanceMaintenancePlanActiveTimeVo> maintenanceMaintenancePlanActiveTimeVoList) {
        List<MaintenanceMaintenancePlanActiveTime> maintenanceMaintenancePlanActiveTimeList=new ArrayList<>();
        try {
            MaintenanceMaintenancePlanActiveTime maintenanceMaintenancePlanActiveTime= new MaintenanceMaintenancePlanActiveTime();
            for (MaintenanceMaintenancePlanActiveTimeVo maintenanceMaintenancePlanActiveTimeVo : maintenanceMaintenancePlanActiveTimeVoList) {
                maintenanceMaintenancePlanActiveTime = new MaintenanceMaintenancePlanActiveTime();
                ReflectionUtils.copyProperties(maintenanceMaintenancePlanActiveTimeVo, maintenanceMaintenancePlanActiveTime, null);
                maintenanceMaintenancePlanActiveTimeList.add(maintenanceMaintenancePlanActiveTime);
            }
            maintenanceMaintenancePlanActiveTimeList=maintenanceMaintenancePlanActiveTimeService.saveMaintenancePlanActiveTimeList(maintenanceMaintenancePlanActiveTimeList);
            MaintenanceMaintenancePlanActiveTimeVo maintenanceMaintenancePlanActiveTimeVo;
            maintenanceMaintenancePlanActiveTimeVoList=new ArrayList<>();
            for (MaintenanceMaintenancePlanActiveTime maintenanceMaintenancePlanActiveTime1 : maintenanceMaintenancePlanActiveTimeList) {
                maintenanceMaintenancePlanActiveTimeVo = new MaintenanceMaintenancePlanActiveTimeVo();
                ReflectionUtils.copyProperties(maintenanceMaintenancePlanActiveTime1, maintenanceMaintenancePlanActiveTimeVo, null);
                maintenanceMaintenancePlanActiveTimeVoList.add(maintenanceMaintenancePlanActiveTimeVo);
            }
        } catch (Exception e) {
            logger.error("-------/eam/micro/maintenancePlan/saveMaintenancePlanActiveTimeList-----", e);
        }
        return maintenanceMaintenancePlanActiveTimeVoList;
    }

    /**
     * deleteMaintenancePlanActiveTimeListByIds: 删除选中的有效时间
     * @param ids  选中的有效时间id集合
     * @return Boolean 删除是否成功
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanActiveTimeListByIds")
    public Boolean deleteMaintenancePlanActiveTimeListByIds(@RequestParam("ids") List<String> ids) {
        return maintenanceMaintenancePlanActiveTimeService.deleteMaintenancePlanActiveTimeListByIds(ids);
    }

    /**
     * deleteMaintenancePlanActiveTimeByMaintenancePlanIds: 根据预防性维护计划ID集合删除关联的有效时间
     * @param maintenancePlanIds 预防性维护计划ID集合
     * @return Boolean 删除是否成功
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/maintenancePlan/deleteMaintenancePlanActiveTimeByMaintenancePlanIds")
    public Boolean deleteMaintenancePlanActiveTimeByMaintenancePlanIds(@RequestParam("maintenancePlanIds") List<String> maintenancePlanIds) {
        return maintenanceMaintenancePlanActiveTimeService.deleteMaintenancePlanActiveTimeByMaintenancePlanIds(maintenancePlanIds);
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/maintenancePlan/updatePmNextDate")
//    public Boolean updatePmNextDate(String statusId,String siteId) {
//        try {
////          AuthoriseSite site = authoriseSiteService.findAuthoriseSiteById("e0c34161f58611e58c2d507b9d28ddca");
////          EAM_ALNDOMAIN status = alnDomainService.findByValueAndDomainValue("activity","pmStatus");
//            List<MaintenanceMaintenancePlan> pmList=maintenancePlanService.findPmByStatusAndSiteNot(statusId,siteId);
//            for (MaintenanceMaintenancePlan maintenanceMaintenancePlan : pmList) {
//                if (maintenanceMaintenancePlan.getNextdate() != null) {
//                    if (maintenanceMaintenancePlan.getNextdate().getTime() < new Date().getTime()) {
//                        checkPmNextDate(maintenanceMaintenancePlan);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("-------/eam/micro/maintenancePlan/deleteMasterPmSeasons-----", e);
//            return false;
//        }
//        return true;
//    }
//
//    private void checkPmNextDate(MaintenanceMaintenancePlan pm){
//        if(pm.getNextdate().getTime()<new Date().getTime()){
//            Calendar calendar = Calendar.getInstance();
//            if (!pm.getUsetargetdate()) {//根据工单信息
//                List<MaintenanceWorkOrder> workorderList = maintenanceWorkOrderService.findByPmnumOrderByReportdateDesc(pm.getPmnum());
//                if(workorderList.size()>0){
//                    MaintenanceWorkOrder workorder = workorderList.get(0);
////                    if("关闭".equals(workorder.getStatus())){
////                        calendar.setTime(workorder.getReceptionTime());
////                    } else {
////                        return;
////                    }
//                } else {
//                    return;
//                }
//            }
//            calendar.setTime(pm.getNextdate());
////            if ("day".equals(pm.getFrequnit().getValue())) {
////                calendar.add(Calendar.DAY_OF_MONTH, pm.getFrequency());
////            }
////            if ("month".equals(pm.getFrequnit().getValue())) {
////                calendar.add(Calendar.MONTH, pm.getFrequency());
////            }
////            if ("week".equals(pm.getFrequnit().getValue())) {
////                calendar.add(Calendar.DAY_OF_MONTH, pm.getFrequency() * 7);
////            }
////            if ("year".equals(pm.getFrequnit().getValue())) {
////                calendar.add(Calendar.YEAR, pm.getFrequency());
////            }
//            pm.setLaststartdate(new Date());//上次开始时间
//            pm.setNextdate(calendar.getTime());
//            checkPmNextDate(pm);
//        } else {
//            //设置延长日期为空
//            pm.setExtdate(null);
//            maintenancePlanService.save(pm);
//        }
//    }
}