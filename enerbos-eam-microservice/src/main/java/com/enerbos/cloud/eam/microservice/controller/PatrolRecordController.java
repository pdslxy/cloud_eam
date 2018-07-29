package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.PatrolRecordService;
import com.enerbos.cloud.eam.vo.PatrolRecordVo;
import com.enerbos.cloud.eam.vo.PatrolRouteVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/28
 * @Description
 */
@RestController
public class PatrolRecordController {

    private static Logger logger = LoggerFactory.getLogger(PatrolRecordController.class);


    @Autowired
    private PatrolRecordService patrolRecordService;

    /**
     * 根据id查询巡检记录表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecord/findById", method = RequestMethod.GET)
    public PatrolRecordVo findById(@RequestParam("id") String id) {
        try {
            return patrolRecordService.findById(id);
        } catch (Exception e) {
            logger.debug("-------/patrolRecord/findById--------------", e);
        }
        return null;
    }


    /**
     * 保存巡检记录表
     *
     * @param patrolRecordVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecord/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PatrolRecordVo patrolRecordVo) {
        try {
            patrolRecordService.saveOrUpdate(patrolRecordVo);
        } catch (Exception e) {
            logger.debug("-------/patrolRecord/saveOrUpdate--------------", e);
        }
    }

    /**
     * 根据工单ID查询巡检记录表的巡检点
     *
     * @param id 工单ID
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecord/findPatrolRecordByOrderId", method = RequestMethod.GET)
    public PatrolRouteVo findPatrolRecordByOrderId(@RequestParam("id") String id) {
        try {
            return patrolRecordService.findPatrolRecordVoByOrderId(id);
        } catch (Exception e) {
            logger.error("-------/patrolRecord/findPatrolRecordByOrderId--------------", e);
        }
        return null;
    }
}
