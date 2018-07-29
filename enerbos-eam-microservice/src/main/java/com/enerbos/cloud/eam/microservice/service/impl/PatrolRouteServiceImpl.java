package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolOrder;
import com.enerbos.cloud.eam.microservice.domain.PatrolPoint;
import com.enerbos.cloud.eam.microservice.domain.PatrolRoute;
import com.enerbos.cloud.eam.microservice.domain.PatrolRoutePoint;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRoutePointRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRouteRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.*;
import com.enerbos.cloud.eam.microservice.service.PatrolPointService;
import com.enerbos.cloud.eam.microservice.service.PatrolRouteService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
@Service
public class PatrolRouteServiceImpl implements PatrolRouteService {

    @Autowired
    private PatrolRouteRepository patrolRouteRepository;

    @Autowired
    private PatrolRouteDao patrolRouteDao;

    @Autowired
    private PatrolRoutePointRepository patrolRoutePointRepository;

    @Autowired
    private PatrolRoutePointDao patrolRoutePointDao;

    @Autowired
    private PatrolPointService patrolPointService;

    @Autowired
    private PatrolPlanDao patrolPlanDao;

    @Autowired
    private PatrolOrderRepository patrolOrderRepository;

    @Autowired
    private PatrolTermDao patrolTermDao;

    @Autowired
    private PatrolOrderDao patrolOrderDao;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolRouteVo> findPatrolRouteList(PatrolRouteVoForFilter patrolRouteVoForFilter) throws Exception {
        PageHelper.startPage(patrolRouteVoForFilter.getPageNum(),
                patrolRouteVoForFilter.getPageSize());

        String word = patrolRouteVoForFilter.getWords();
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(patrolRouteVoForFilter);
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<PatrolRouteVo>(patrolRouteDao.findPatrolRouteListByFilters(filters));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletePatrolRouteByIds(String[] ids) throws EnerbosException {
        for (String id : ids) {
            //删除与巡检计划的关联关系
            List<PatrolPlanVo> patrolPlanList = patrolPlanDao.findPlanByRouteId(id);
            if (patrolPlanList != null && patrolPlanList.size() > 0) {
                throw new RuntimeException("所选路线已经关联巡检计划:" + patrolPlanList.get(0).getPatrolPlanNum() + "，请取消关联后再删除");
            }
            //验证是否关联巡检工单
            Map<String, Object> map = new HashMap<>();
            map.put("patrolRouteId", id);
            List<PatrolOrderVo> patrolOrderVoList = patrolOrderDao.findPatrolOrderListByFilters(map);
            if (patrolOrderVoList != null && patrolOrderVoList.size() > 0) {
                throw new RuntimeException("所选路线已经关联巡检工单:" + patrolOrderVoList.get(0).getPatrolOrderNum());
            }
            //删除与巡检点的关联关系
            patrolRoutePointDao.deleteByRouteId(id);
            patrolRouteRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolRouteVo saveOrUpdate(PatrolRouteForSaveVo patrolRouteForSaveVo) throws EnerbosException {
        //保存或更新巡检点的信息
        PatrolRoute pr = new PatrolRoute();
        BeanUtils.copyProperties(patrolRouteForSaveVo, pr);
        if (StringUtils.isNotBlank(pr.getId())) {
            pr.setUpdatetime(new Date());
        } else {
            pr.setCreatetime(new Date());
        }
        PatrolRoute route = patrolRouteRepository.save(pr);
        //新增或删除巡检路线关联的巡检点
        List<PatrolPointVo> list = patrolRouteForSaveVo.getPointAddlist();
        if (list != null) {
            for (PatrolPointVo patrolPointVo : list) {
                PatrolRoutePoint prp = new PatrolRoutePoint();
                PatrolPoint point = patrolPointService.findPatrolPointById(patrolPointVo.getId());
                prp.setPatrolPoint(point);
                prp.setPatrolRoute(route);
                prp.setRemark(patrolRouteForSaveVo.getRemark());
                prp.setCreatetime(new Date());
                prp.setUpdatetime(prp.getCreatetime());
                prp.setStep(patrolPointVo.getStep());
                patrolRoutePointRepository.save(prp);
            }
        }
        String pointDeleteIds = patrolRouteForSaveVo.getPointDeleteIds();
        if (StringUtils.isNotBlank(pointDeleteIds)) {
            String[] idArr = pointDeleteIds.split(",");
            for (String id : idArr) {
                PatrolPoint patrolPoint = new PatrolPoint();
                patrolPoint.setId(id);
                patrolRoutePointRepository.deleteByPatrolPoint(patrolPoint);
            }
        }
        PatrolRouteVo patrolRouteVo = new PatrolRouteVo();
        BeanUtils.copyProperties(route, patrolRouteVo);
        return patrolRouteVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolRoute findPatrolRouteById(String patrolRouteId) throws EnerbosException {
        PatrolRoute patrolRoute = patrolRouteRepository.findOne(patrolRouteId);
        return patrolRoute;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PatrolPointVo> findPatrolPointByRouteId(String id, String sorts) throws EnerbosException {
        List<PatrolPointVo> patrolPointVoList = patrolRoutePointDao.findPatrolPointByPatrolrouteid(id, sorts);
        return patrolPointVoList;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolRouteVo findRouteByOrderId(String id) throws EnerbosException {
        PatrolOrder patrolOrder = patrolOrderRepository.findOne(id);

        List<PatrolPointVo> patrolPointList = new ArrayList<>();
        PatrolRouteVo patrolRouteVo = new PatrolRouteVo();
        if (patrolOrder != null) {
            String routeid = patrolOrder.getPatrolRoute().getId();
            PatrolRoute patrolRoute = patrolOrder.getPatrolRoute();
            BeanUtils.copyProperties(patrolRoute, patrolRouteVo);
            patrolPointList = patrolRoutePointDao.findPatrolPointByPatrolrouteid(routeid, null);
            for (int i = 0; i < patrolPointList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("patrolPointId", patrolPointList.get(i).getId());
                List<PatrolTermVo> list = patrolTermDao.findPatrolTermListByFilters(map);
                patrolPointList.get(i).setPatrolTermVolist(list);
            }
            patrolRouteVo.setPatrolPointVoList(patrolPointList);
        }
        return patrolRouteVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PatrolRouteVo> findByPointId(String pointId) throws EnerbosException {
        List<PatrolRouteVo> patrolRouteVoList = new ArrayList<>();
        List<PatrolRoutePointVo> list = patrolRoutePointDao.findPatrolRoutePointByPatrolpointid(pointId);
        list.stream().map(patrolRoutePointVo -> patrolRoutePointVo.getPatrolrouteid())
                .map(patrolRouteRepository::findOne)
                .forEach(patrolRoute -> {
                    PatrolRouteVo patrolRouteVo = new PatrolRouteVo();
                    BeanUtils.copyProperties(patrolRoute, patrolRouteVo);
                    patrolRouteVoList.add(patrolRouteVo);
                });
        return patrolRouteVoList;
    }


}
