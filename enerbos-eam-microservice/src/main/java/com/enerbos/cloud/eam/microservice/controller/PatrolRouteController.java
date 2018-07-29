package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.PatrolRoute;
import com.enerbos.cloud.eam.microservice.service.PatrolPointService;
import com.enerbos.cloud.eam.microservice.service.PatrolRouteService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
 * @date 2017/7/12
 * @Description
 */
@RestController
public class PatrolRouteController {
    private static Logger logger = LoggerFactory.getLogger(PatrolRouteController.class);

    @Autowired
    private PatrolRouteService patrolRouteService;

    @Autowired
    private PatrolPointService patrolPointService;

    /**
     * 查询巡检路线列表
     *
     * @param patrolRouteVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/findPage", method = RequestMethod.POST)
    public PageInfo<PatrolRouteVo> findPage(@RequestBody PatrolRouteVoForFilter patrolRouteVoForFilter) {
        PageInfo<PatrolRouteVo> pageInfo = null;
        try {
            pageInfo = patrolRouteService.findPatrolRouteList(patrolRouteVoForFilter);
        } catch (Exception e) {
            logger.error("-------/patrolRoute/findPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 删除巡检路线
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/deleteByIds", method = RequestMethod.POST)
    public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
        try {
            patrolRouteService.deletePatrolRouteByIds(ids);
            return "success";
        } catch (Exception e) {
            logger.error("-------/patrolRoute/deleteByIds--------------", e);
            return e.getMessage();
        }
    }


    /**
     * 保存巡检路线和关联的巡检项信息
     *
     * @param patrolRouteForSaveVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/saveOrUpdate", method = RequestMethod.POST)
    public PatrolRouteVo saveOrUpdate(@RequestBody PatrolRouteForSaveVo patrolRouteForSaveVo) {
        PatrolRouteVo patrolRouteVo = patrolRouteService.saveOrUpdate(patrolRouteForSaveVo);
        return patrolRouteVo;
    }

    /**
     * findPatrolRouteVoById:根据ID查询巡检路线-巡检点
     *
     * @param patrolPointVoForFilter
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/findPatrolRouteVoById", method = RequestMethod.POST)
    public PatrolRouteVo findPatrolRouteVoById(@RequestBody PatrolPointVoForFilter patrolPointVoForFilter) {
        PatrolRoute patrolRoute = null;
        PatrolRouteVo patrolRouteVo = new PatrolRouteVo();
        try {
            patrolRoute = patrolRouteService.findPatrolRouteById(patrolPointVoForFilter.getPatrolRouteId());
            if (null != patrolRoute) {
                BeanUtils.copyProperties(patrolRoute, patrolRouteVo);
                List<PatrolPointVo> patrolPointList = patrolRouteService.findPatrolPointByRouteId(patrolRoute.getId(), patrolPointVoForFilter.getSorts());
                patrolRouteVo.setPatrolPointVoList(patrolPointList);
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolRoute/findPatrolRouteVoById ------", e);
        }
        return patrolRouteVo;
    }

    /**
     * 查询巡检工单查询巡检路线
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/findRouteByOrderId", method = RequestMethod.GET)
    public PatrolRouteVo findRouteByOrderId(@RequestParam("id") String id) {
        PatrolRouteVo patrolRouteVo = new PatrolRouteVo();
        try {
            patrolRouteVo = patrolRouteService.findRouteByOrderId(id);
        } catch (Exception e) {
            logger.error("-------/patrolRoute/findRouteByOrderId--------------", e);
        }
        return patrolRouteVo;
    }


    /**
     * 查询巡检工单查询巡检路线
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/findRouteByPointId", method = RequestMethod.GET)
    public List<PatrolRouteVo> findRouteByPointId(@RequestParam("pointId") String pointId) {
        try {
            return patrolRouteService.findByPointId(pointId);
        } catch (Exception e) {
            logger.error("-------/patrolRoute/findRouteByPointId--------------", e);
            return null;
        }
    }


}
