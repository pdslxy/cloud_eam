package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.PatrolOrderCommon;
import com.enerbos.cloud.eam.microservice.domain.PatrolOrder;
import com.enerbos.cloud.eam.microservice.service.PatrolOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
public class PatrolOrderController {
    private static Logger logger = LoggerFactory.getLogger(PatrolOrderController.class);

    @Autowired
    private PatrolOrderService patrolOrderService;


    /**
     * 查询巡检工单列表
     *
     * @param patrolOrderVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findPage", method = RequestMethod.POST)
    public PageInfo<PatrolOrderVo> findPage(@RequestBody PatrolOrderVoForFilter patrolOrderVoForFilter) {
        PageInfo<PatrolOrderVo> pageInfo = null;
        try {
            pageInfo = patrolOrderService.findPatrolOrderList(patrolOrderVoForFilter);
        } catch (Exception e) {
            logger.error("-------/patrolOrder/findPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 删除巡检工单
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/deleteByIds", method = RequestMethod.POST)
    public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
        try {
            patrolOrderService.deletePatrolOrderByIds(ids);
            return "success";
        } catch (Exception e) {
            logger.error("-------/patrolOrder/deleteByIds--------------", e);
            return e.getMessage();
        }
    }


    /**
     * 保存巡检工单和关联的巡检项信息
     *
     * @param patrolOrderForSaveVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/saveOrUpdate", method = RequestMethod.POST)
    public PatrolOrderVo saveOrUpdate(@RequestBody PatrolOrderForSaveVo patrolOrderForSaveVo) {
        try {
            PatrolOrderVo patrolOrderVo = patrolOrderService.saveOrUpdate(patrolOrderForSaveVo);
            return patrolOrderVo;
        } catch (Exception e) {
            logger.error("-------/patrolOrder/saveOrUpdate--------------", e);
        }
        return null;
    }

    /**
     * findPatrolOrderVoById:根据ID查询巡检工单-巡检点
     *
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findPatrolOrderVoById", method = RequestMethod.GET)
    public PatrolOrderVo findPatrolOrderVoById(@RequestParam(value = "id", required = true) String id) {
        PatrolOrder patrolOrder = null;
        PatrolOrderVo patrolOrderVo = new PatrolOrderVo();
        try {
            patrolOrder = patrolOrderService.findPatrolOrderById(id);
            if (null != patrolOrder) {
                BeanUtils.copyProperties(patrolOrder, patrolOrderVo);
                patrolOrderVo.setBeginPatrol(patrolOrder.getIsBeginPatrol());
                if (patrolOrder.getPatrolPlan() != null) {
                    patrolOrderVo.setPatrolPlanId(patrolOrder.getPatrolPlan().getId());
                    patrolOrderVo.setPatrolPlanNum(patrolOrder.getPatrolPlan().getPatrolPlanNum());
                    patrolOrderVo.setPatrolPlanDsr(patrolOrder.getPatrolPlan().getDescription());
                }
                if (patrolOrder.getPatrolRoute() != null) {
                    patrolOrderVo.setPatrolRouteId(patrolOrder.getPatrolRoute().getId());
                    patrolOrderVo.setPatrolRouteNum(patrolOrder.getPatrolRoute().getPatrolRouteNum());
                    patrolOrderVo.setPatrolRouteDsr(patrolOrder.getPatrolRoute().getDescription());
                }
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolOrder/findPatrolOrderVoById ------", e);
        }
        return patrolOrderVo;
    }

    /**
     * findWorkOrderCommitById:根据ID查询巡检工单-工单提报
     *
     * @param id
     * @return PatrolOrderForCommitVo 巡检工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findPatrolOrderWorkFlowById", method = RequestMethod.GET)
    PatrolOrderForWorkFlowVo findPatrolOrderWorkFlowById(@RequestParam("id") String id) {
        PatrolOrderForWorkFlowVo patrolOrderForWorkFlowVo = new PatrolOrderForWorkFlowVo();
        try {
            PatrolOrder patrolOrder = patrolOrderService.findPatrolOrderById(id);
            if (null == patrolOrder || patrolOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            ReflectionUtils.copyProperties(patrolOrder, patrolOrderForWorkFlowVo, null);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveWorkOrderCheckAccept-----", e);
        }
        return patrolOrderForWorkFlowVo;
    }

    /**
     * findWorkOrderCommitById:根据ID查询巡检工单-工单提报
     *
     * @param patrolOrderForWorkFlowVo {@link com.enerbos.cloud.eam.vo.PatrolOrderForWorkFlowVo}
     * @return PatrolOrderForCommitVo 巡检工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/savePatrolOrderFlow", method = RequestMethod.POST)
    PatrolOrderForWorkFlowVo savePatrolOrderFlow(@RequestBody PatrolOrderForWorkFlowVo patrolOrderForWorkFlowVo) {
        try {
            PatrolOrder patrolOrder = patrolOrderService.findPatrolOrderById(patrolOrderForWorkFlowVo.getId());
            if (null == patrolOrder || patrolOrder.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            if (patrolOrderForWorkFlowVo.getStatus().equals(PatrolOrderCommon.STATUS_DJD)) {
                patrolOrder.setExcutePersonId(patrolOrderForWorkFlowVo.getExcutePersonId());
            } else {
                if (patrolOrderForWorkFlowVo.getActualExecutorId() != null && PatrolOrderCommon.STATUS_DHB.equals(patrolOrderForWorkFlowVo.getStatus())) {
                    patrolOrder.setExcutePersonId(patrolOrderForWorkFlowVo.getActualExecutorId());
                }
            }
            //待分派驳回到待提报分派人置为空
            if (PatrolOrderCommon.STATUS_DFP.equals(patrolOrder.getStatus()) && PatrolOrderCommon.STATUS_DTB.equals(patrolOrderForWorkFlowVo.getStatus())) {
                patrolOrder.setAssignPersonId(null);
            }
            patrolOrder.setStatus(patrolOrderForWorkFlowVo.getStatus());
            patrolOrder.setStatusdate(new Date());
            patrolOrder.setUpdatetime(new Date());
            if (StringUtils.isEmpty(patrolOrder.getProcessInstanceId())) {
                patrolOrder.setProcessInstanceId(patrolOrderForWorkFlowVo.getProcessInstanceId());
            }
            patrolOrderService.save(patrolOrder);
        } catch (Exception e) {
            logger.error("-------/eam/micro/patrolOrder/savePatrolOrderFlow-----", e);
        }
        return patrolOrderForWorkFlowVo;
    }

    /**
     * 查询巡检工单条数
     *
     * @param orgId     组织id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findPatrolOrderTotal", method = RequestMethod.GET)
    public OrderCountBySiteVo findPatrolOrderTotal(@RequestParam("orgId") String orgId, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        return patrolOrderService.findPatrolOrderTotal(orgId, startDate, endDate);
    }

    /**
     * 查询最大巡检工单数
     *
     * @param orgId     组织
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findMaxCountOrder", method = RequestMethod.GET)
    OrderMaxCountVo findMaxCountOrder(@RequestParam("orgId") String orgId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return patrolOrderService.findMaxCountOrder(orgId, startDate, endDate);
    }

    /**
     * 查询巡检工单列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/findOrderPointById", method = RequestMethod.GET)
    public PageInfo<PatrolPointVo> findOrderPointById(@RequestParam("id") String id) {
        PageInfo<PatrolPointVo> pageInfo = null;
        try {
            pageInfo = patrolOrderService.findOrderPointList(id);
        } catch (Exception e) {
            logger.error("-------/patrolOrder/findOrderPointById--------------", e);
        }
        return pageInfo;
    }

    /**
     * 查询巡检工单列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolOrder/updateIsBeginPatrolById", method = RequestMethod.GET)
    public boolean updateIsBeginPatrolById(@RequestParam(value = "id", required = true) String id,
                                           @RequestParam(value = "isBeginPatrol", required = false) boolean isBeginPatrol,
                                           @RequestParam(value = "beginPatrolDate", required = false) Date beginPatrolDate) {
        try {
            return patrolOrderService.updateIsBeginPatrolById(id, isBeginPatrol, beginPatrolDate);
        } catch (Exception e) {
            logger.error("-------/patrolOrder/updateIsBeginPatrolById--------------", e);
            return false;
        }
    }
}
