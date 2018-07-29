package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.PatrolOrderCommon;
import com.enerbos.cloud.eam.microservice.domain.*;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolPlanRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRecordRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRecordTermRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolOrderDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRecordDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRecordTermDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRoutePointDao;
import com.enerbos.cloud.eam.microservice.service.PatrolOrderService;
import com.enerbos.cloud.eam.microservice.service.PatrolTermService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/7
 * @Description
 */
@Service
public class PatrolOrderServiceImpl implements PatrolOrderService {

    private static Logger logger = LoggerFactory.getLogger(PatrolOrderServiceImpl.class);

    @Autowired
    private PatrolOrderRepository patrolOrderRepository;

    @Autowired
    private PatrolOrderDao patrolOrderDao;

    @Autowired
    private PatrolPlanRepository patrolPlanRepository;

    @Autowired
    private PatrolRecordDao patrolRecordDao;

    @Autowired
    private PatrolRoutePointDao patrolRoutePointDao;

    @Autowired
    private PatrolTermService patrolTermService;

    @Autowired
    private PatrolRecordTermRepository patrolRecordTermRepository;

    @Autowired
    private PatrolRecordTermDao patrolRecordTermDao;

    @Autowired
    private PatrolRecordRepository patrolRecordRepository;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolOrderVo> findPatrolOrderList(PatrolOrderVoForFilter PatrolOrderVoForFilter) throws Exception {
        PageHelper.startPage(PatrolOrderVoForFilter.getPageNum(),
                PatrolOrderVoForFilter.getPageSize());

        String word = PatrolOrderVoForFilter.getWords();
        String createDate = PatrolOrderVoForFilter.getCreateDate();
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(PatrolOrderVoForFilter);
        if (StringUtil.isNotEmpty(createDate)) {
            String startDate = "";
            String endDate = "";
            if (createDate.equals("week")) {
                LocalDateTime localDateTime = LocalDateTime.now();
                int week = localDateTime.getDayOfWeek().getValue();
                LocalDateTime monday = localDateTime.plusDays(-week);
                monday = monday.plusHours(-monday.getHour());
                monday = monday.plusMinutes(-monday.getMinute());
                monday = monday.plusSeconds(-monday.getSecond());
                startDate = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime sunday = monday.plusDays(7);
                sunday = sunday.plusHours(23);
                sunday = sunday.plusMinutes(59);
                sunday = sunday.plusSeconds(59);
                endDate = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                logger.info("---week query---------{} {} ", startDate, endDate);
            } else if (createDate.equals("month")) {

                LocalDateTime localDateTime = LocalDateTime.now();
                LocalDateTime first = localDateTime.plusDays(-localDateTime.getDayOfMonth() + 1);
                first = first.plusHours(-first.getHour());
                first = first.plusMinutes(-first.getMinute());
                first = first.plusSeconds(-first.getSecond());
                startDate = first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                LocalDateTime last = first.plusMonths(1).plusDays(-1);
                last = last.plusHours(23);
                last = last.plusMinutes(59);
                last = last.plusSeconds(59);
                endDate = last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                logger.info("---month query---------{} {} ", startDate, endDate);
            }
            filters.put("startDate", startDate);
            filters.put("endDate", endDate);
        }
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        List<PatrolOrderVo> patrolOrderList = patrolOrderDao.findPatrolOrderListByFilters(filters);

        return new PageInfo<PatrolOrderVo>(patrolOrderList);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletePatrolOrderByIds(String[] ids) throws EnerbosException {
        for (String id : ids) {
            //如果不是已经提报过的工单，不删除
            PatrolOrder patrolOrder = patrolOrderRepository.findOne(id);
            if (patrolOrder.getStatus().equals("DTB")) {
                //删除巡检记录
                patrolRecordDao.deleteByOrderId(id);
                //删除巡检项记录
                patrolRecordTermDao.deleteByOrderId(id);
                patrolOrderRepository.delete(id);
            } else {
                throw new RuntimeException("所选工单已经提报过，不能删除");
            }
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolOrderVo saveOrUpdate(PatrolOrderForSaveVo patrolOrderForSaveVo) throws Exception {
        PatrolOrder patrolOrder = new PatrolOrder();
        String status = patrolOrderForSaveVo.getStatus();
        if (status.equals(PatrolOrderCommon.STATUS_DTB)) { //待提报
            //保存或更新巡检点的信息
            PatrolOrder po = new PatrolOrder();
            BeanUtils.copyProperties(patrolOrderForSaveVo, po);
            //关联巡检计划
            if (StringUtil.isNotEmpty(patrolOrderForSaveVo.getPatrolPlanId())) {
                PatrolPlan patrolPlan = new PatrolPlan();
                patrolPlan.setId(patrolOrderForSaveVo.getPatrolPlanId());
                po.setPatrolPlan(patrolPlan);

                PatrolPlan pp = patrolPlanRepository.findOne(patrolOrderForSaveVo.getPatrolPlanId());
                PatrolRoute patrolRoute = pp.getPatrolRoute();
                po.setPatrolRoute(patrolRoute);
            }
            //关联巡检路线
            if (StringUtil.isNotEmpty(patrolOrderForSaveVo.getPatrolRouteId())) {
                PatrolRoute patrolRoute = new PatrolRoute();
                patrolRoute.setId(patrolOrderForSaveVo.getPatrolRouteId());
                po.setPatrolRoute(patrolRoute);
            }
            po.setUpdatetime(new Date());
            patrolOrder = patrolOrderRepository.save(po);
            //生成巡检记录(用于执行汇报查询巡检点)
            //查询关联巡检点
            List<PatrolPointVo> pointVoList = patrolRoutePointDao.findPatrolPointByPatrolrouteid(patrolOrder.getPatrolRoute().getId(), null);
            //先删除
            patrolRecordDao.deleteByOrderId(patrolOrder.getId());
            for (PatrolPointVo patrolPointVo : pointVoList) {
                PatrolRecord patrolRecord = new PatrolRecord();
                PatrolPoint patrolPoint = new PatrolPoint();
                patrolRecord.setPatrolOrder(patrolOrder);
                patrolPoint.setId(patrolPointVo.getId());
                patrolRecord.setPatrolPoint(patrolPoint);
                patrolRecord.setPatrolPointDsr(patrolPointVo.getDescription());
                patrolRecordRepository.save(patrolRecord);
                //生成巡检项
                PatrolTermVoForFilter patrolTermVoForFilter = new PatrolTermVoForFilter();
                patrolTermVoForFilter.setPatrolPointId(patrolPointVo.getId());
                PageInfo<PatrolTermVo> page = patrolTermService.findPatrolTermList(patrolTermVoForFilter);
                List<PatrolTermVo> termlist = page.getList();
                for (PatrolTermVo patrolTermVo : termlist) {
                    PatrolRecordTerm prt = new PatrolRecordTerm();
                    prt.setPatrolTermDsr(patrolTermVo.getDescription());
                    prt.setPatrolPoint(patrolPoint);
                    prt.setPatrolOrder(patrolOrder);
                    prt.setUpdateTime(new Date());
                    prt.setCreateTime(prt.getUpdateTime());
                    patrolRecordTermRepository.save(prt);
                }
            }
        } else if (status.equals(PatrolOrderCommon.STATUS_DFP)) {//待分派
            patrolOrder = patrolOrderRepository.findOne(patrolOrderForSaveVo.getId());
            patrolOrder.setStatus(patrolOrderForSaveVo.getStatus());
            patrolOrder.setAssignPersonId(patrolOrderForSaveVo.getAssignPersonId());
            patrolOrder.setExcutePersonId(patrolOrderForSaveVo.getExcutePersonId());
            patrolOrder.setStatusdate(new Date());
            patrolOrder = patrolOrderRepository.save(patrolOrder);
        } else {//执行汇报
            List<PatrolRecordTermForOrderSaveVo> list = patrolOrderForSaveVo.getRecordTermList();
            if (list != null && list.size() > 0) {
                //更新开始巡检标识与备注
                PatrolOrder po = patrolOrderRepository.findOne(patrolOrderForSaveVo.getId());
                po.setExcuteRemark(patrolOrderForSaveVo.getExcuteRemark());
                if (patrolOrderForSaveVo.getStartdate() != null) {
                    if (po.getBeginPatrolDate() == null) {
                        po.setIsBeginPatrol(true);
                        po.setBeginPatrolDate(patrolOrderForSaveVo.getStartdate());
                        patrolOrderRepository.save(po);
                    }
                }
                //保存巡检点记录
                patrolOrder.setId(patrolOrderForSaveVo.getId());
                PatrolPoint patrolPoint = new PatrolPoint();
                patrolPoint.setId(list.get(0).getPatrolPointId());
                PatrolRecord patrolRecord = patrolRecordRepository.findOne(patrolOrderForSaveVo.getPatrolRecordId());

                try {
                    long nm = 1000 * 60;
                    // 获得两个时间的毫秒时间差异
                    long diff = patrolOrderForSaveVo.getEnddate().getTime() - patrolOrderForSaveVo.getStartdate().getTime();
                    // 计算差多少分钟
                    long min = diff / nm;
                    patrolRecord.setDuration(Integer.parseInt(min + ""));
                } catch (Exception e) {
                    logger.error("----parse time-----", e);
                }
                patrolRecord.setStartdate(patrolOrderForSaveVo.getStartdate());
                patrolRecord.setEnddate(patrolOrderForSaveVo.getEnddate());
                patrolRecord.setRemark(patrolOrderForSaveVo.getRemark());
                for(PatrolRecordTermForOrderSaveVo patrolRecordTermForOrderSaveVo:list){
                    if ("0".equals(patrolRecordTermForOrderSaveVo.getIsqualified())) {
                        patrolRecord.setIsqualified("0");
                        break;
                    }
                    if("1".equals(patrolRecordTermForOrderSaveVo.getIsqualified())){
                        patrolRecord.setIsqualified("1");
                    }
                }
                //保存巡检项记录

                for (PatrolRecordTermForOrderSaveVo patrolRecordTermForOrderSaveVo : list) {
                    PatrolRecordTerm prt = new PatrolRecordTerm();
                    BeanUtils.copyProperties(patrolRecordTermForOrderSaveVo, prt);
                    prt.setPatrolPoint(patrolPoint);
                    prt.setPatrolOrder(patrolOrder);
                    prt.setUpdateTime(new Date());
                    prt.setCreateTime(prt.getUpdateTime());
                    //默认为正常
//                    if (StringUtils.isEmpty(prt.getIsqualified())) {
//                        prt.setIsqualified("1");
//                    }
                    patrolRecordTermRepository.save(prt);
                }
            }
        }
        PatrolOrderVo patrolOrderVo = new PatrolOrderVo();
        PatrolOrder po = patrolOrderRepository.findOne(patrolOrder.getId());
        BeanUtils.copyProperties(po, patrolOrderVo);
        return patrolOrderVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolOrder findPatrolOrderById(String PatrolOrderId) throws EnerbosException {
        PatrolOrder PatrolOrder = patrolOrderRepository.findOne(PatrolOrderId);
        return PatrolOrder;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolOrder findPatrolOrderByOrderId(String id) throws EnerbosException {
        PatrolOrder patrolOrder = patrolOrderRepository.findOne(id);
        return patrolOrder;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(PatrolOrder patrolOrder) throws EnerbosException {
        patrolOrderRepository.save(patrolOrder);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolPointVo> findOrderPointList(String id) throws EnerbosException {
        List<PatrolPointVo> patrolPointList = new ArrayList<>();
        List<PatrolRecordVo> list = patrolRecordDao.findByOrderIdAndPointId(id, null);
        list.forEach(patrolRecordVo -> {
            PatrolPointVo patrolPointVo = new PatrolPointVo();
            patrolPointVo.setId(patrolRecordVo.getPatrolPointId());
            patrolPointVo.setDescription(patrolRecordVo.getPatrolPointDsr());
            patrolPointVo.setIsqualified(patrolRecordVo.getIsqualified());
            patrolPointList.add(patrolPointVo);
        });
        return new PageInfo<PatrolPointVo>(patrolPointList);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderCountBySiteVo findPatrolOrderTotal(String orgId, Date startDate, Date endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return patrolOrderDao.findPatrolOrderTotal(param);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderMaxCountVo findMaxCountOrder(String orgId, String startDate, String endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return patrolOrderDao.findMaxCountOrder(param);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean updateIsBeginPatrolById(String id, boolean isBeginPatrol, Date beginPatrolDate) throws EnerbosException {
        PatrolOrder order = patrolOrderRepository.findOne(id);
        order.setIsBeginPatrol(isBeginPatrol);
        order.setBeginPatrolDate(beginPatrolDate);
        patrolOrderRepository.save(order);
        return true;
    }


}
