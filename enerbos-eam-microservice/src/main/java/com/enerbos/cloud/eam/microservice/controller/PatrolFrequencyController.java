package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.PatrolPlanFrequencyService;
import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/8
 * @Description
 */
@RestController
public class PatrolFrequencyController {
    private static Logger logger = LoggerFactory.getLogger(PatrolFrequencyController.class);

    @Autowired
    private PatrolPlanFrequencyService patrolPlanFrequencyService;


    /**
     * 保存巡检频率
     *
     * @param patrolPlanFrequencyVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolFrequency/saveOrUpdate", method = RequestMethod.POST)
    public boolean saveOrUpdate(@RequestBody PatrolPlanFrequencyVo patrolPlanFrequencyVo) {
        try {
            patrolPlanFrequencyService.saveOrUpdate(patrolPlanFrequencyVo);
            return true;
        } catch (Exception e) {
            logger.debug("-------/patrolPlan/findPage--------------", e);
        }
        return false;
    }

    /**
     * 根据计划Id查询频率的id集合
     *
     * @param id 计划id
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolFrequency/findIdsByPlanId")
    public List<String> findIdsByPlanId(@RequestParam("id") String id) {
        try {
            List<String> list = patrolPlanFrequencyService.findIdsByPlanId(id);
            return list;
        } catch (Exception e) {
            logger.debug("-------/patrolPlan/findIdsByPlanId--------------", e);
        }
        return null;
    }



}
