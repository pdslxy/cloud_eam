package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.PatrolPlanFrequencyService;
import com.enerbos.cloud.eam.microservice.service.PatrolPlanService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
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
 * @date 2017/7/12
 * @Description
 */
@RestController
public class PatrolPlanController {
    private static Logger logger = LoggerFactory.getLogger(PatrolPlanController.class);

    @Autowired
    private PatrolPlanService patrolPlanService;

    @Autowired
    private PatrolPlanFrequencyService patrolPlanFrequencyService;

    /**
     * 查询巡检路线列表
     *
     * @param patrolPlanVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/findPage", method = RequestMethod.POST)
    public PageInfo<PatrolPlanVo> findPage(@RequestBody PatrolPlanVoForFilter patrolPlanVoForFilter) {
        PageInfo<PatrolPlanVo> pageInfo = null;
        try {
            pageInfo = patrolPlanService.findPatrolPlanList(patrolPlanVoForFilter);
        } catch (Exception e) {
            logger.error("-------/patrolPlan/findPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 删除巡检路线
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/deleteByIds", method = RequestMethod.POST)
    public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
        try {
            patrolPlanService.deletePatrolPlanByIds(ids);
            return "success";
        } catch (Exception e) {
            logger.error("-------/patrolPlan/deleteByIds--------------", e);
            return e.getMessage();
        }
    }


    /**
     * 保存巡检路线和关联的巡检项信息
     *
     * @param patrolPlanForSaveVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/saveOrUpdate", method = RequestMethod.POST)
    public PatrolPlanVo saveOrUpdate(@RequestBody PatrolPlanForSaveVo patrolPlanForSaveVo) {
        try {
            PatrolPlanVo patrolPlanVo = patrolPlanService.saveOrUpdate(patrolPlanForSaveVo);
            return patrolPlanVo;
        } catch (Exception e) {
            logger.error("-------/patrolPlan/findPage--------------", e);
        }
        return null;
    }

    /**
     * findPatrolPlanVoById:根据ID查询巡检计划-频率
     *
     * @param patrolPlanFrequencyVoForFilter
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/findPatrolPlanVoByFrequency", method = RequestMethod.POST)
    public PatrolPlanVo findPatrolPlanVoById(@RequestBody PatrolPlanFrequencyVoForFilter patrolPlanFrequencyVoForFilter) {
        PatrolPlanVo patrolPlanVo = null;
        try {
            patrolPlanVo = patrolPlanService.findPatrolPlanById(patrolPlanFrequencyVoForFilter.getPatrolPlanId());
            if (null != patrolPlanVo) {
                PageInfo<PatrolPlanFrequencyVo> patrolPlanFrequencyList = patrolPlanFrequencyService.findPatrolPlanFrequencyList(patrolPlanFrequencyVoForFilter);
                if (patrolPlanFrequencyList != null) {
                    patrolPlanVo.setPatrolPlanFrequencyVoList(patrolPlanFrequencyList.getList());
                }
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPlan/findPlanVoById ------", e);
        }
        return patrolPlanVo;
    }

    /**
     * findPatrolPlanVoById:根据ID查询巡检计划Vo
     *
     * @param id
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/findPatrolPlanVoById")
    public PatrolPlanVo findPatrolPlanVoById(@RequestParam(value = "id") String id) {
        PatrolPlanVo patrolPlanVo = null;
        try {
            patrolPlanVo = patrolPlanService.findPatrolPlanById(id);
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPlan/findPlanVoById ------", e);
        }
        return patrolPlanVo;
    }


}
