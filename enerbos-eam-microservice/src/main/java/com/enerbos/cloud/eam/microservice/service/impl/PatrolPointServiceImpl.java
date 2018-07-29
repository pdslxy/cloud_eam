package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolPoint;
import com.enerbos.cloud.eam.microservice.domain.PatrolRoute;
import com.enerbos.cloud.eam.microservice.domain.PatrolTerm;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolPointRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolTermRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolPointDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRecordDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolRoutePointDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolTermDao;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PatrolPointServiceImpl implements PatrolPointService {

    @Autowired
    private PatrolPointRepository patrolPointRepository;

    @Autowired
    private PatrolPointDao patrolPointDao;

    @Autowired
    private PatrolTermRepository patrolTermRepository;

    @Autowired
    private PatrolTermDao patrolTermDao;

    @Autowired
    private PatrolRoutePointDao patrolRoutePointDao;

    @Autowired
    private PatrolRouteService patrolRouteService;

    @Autowired
    private PatrolRecordDao patrolRecordDao;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<PatrolPointVo> findPatrolPointList(PatrolPointVoForFilter patrolPointVoForFilter) throws Exception {
        PageHelper.startPage(patrolPointVoForFilter.getPageNum(),
                patrolPointVoForFilter.getPageSize());

        String word = patrolPointVoForFilter.getWords();
        String ids = patrolPointVoForFilter.getIds();

        if (patrolPointVoForFilter.getType().contains("ALL")) {
            patrolPointVoForFilter.setType(null);
        }
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(patrolPointVoForFilter);
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        if (StringUtil.isNotEmpty(ids)) {
            String[] itemids = ids.split(",");
            filters.put("itemids", itemids);
        }
        return new PageInfo<PatrolPointVo>(patrolPointDao.findPatrolPointListByFilters(filters));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletePatrolPointByIds(String[] ids) throws EnerbosException {
        //巡检点一旦被路线或计划调用均不可删除
        for (String id : ids) {
            PatrolPoint pp = patrolPointRepository.findOne(id);
            if ("Y".equals(pp.getStatus())) {
                throw new RuntimeException("所选点位已经启用");
            }
            List<PatrolRoutePointVo> patrolRoutePointList = patrolRoutePointDao.findPatrolRoutePointByPatrolpointid(id);
            if (patrolRoutePointList != null && patrolRoutePointList.size() > 0) {
                PatrolRoute patrolRoute = patrolRouteService.findPatrolRouteById(patrolRoutePointList.get(0).getPatrolrouteid());
                throw new RuntimeException("所选点位已经关联巡检路线:" + patrolRoute.getPatrolRouteNum() + "，请取消关联后再删除");
            }
            List<PatrolRecordVo> recordList = patrolRecordDao.findByOrderIdAndPointId(null, id);
            if (recordList != null && recordList.size() > 0) {
                throw new RuntimeException("所选点位已经生成巡检记录");
            }
            //删除巡检点之前一起删除关联的巡检项
            patrolTermDao.deletePatrolTermByPointId(id);
            patrolPointRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PatrolPointVo saveOrUpdate(PatrolPointForSaveVo patrolPointForSaveVo) throws EnerbosException {
        //保存或更新巡检点的信息
        PatrolPoint pp = new PatrolPoint();
        BeanUtils.copyProperties(patrolPointForSaveVo, pp);
        if (StringUtils.isNotBlank(pp.getId())) {
            pp.setUpdatetime(new Date());
        } else {
            pp.setCreatetime(new Date());
        }
        pp.setQrCodeNum(makeSerialNumber(6));
        pp.setIsqrcode(true);
        PatrolPoint point = patrolPointRepository.save(pp);
        //保存或更新巡检项的信息
        List<PatrolTermVo> list = patrolPointForSaveVo.getTermAddlist();
        PatrolTerm pt = new PatrolTerm();
        if (list != null) {
            for (PatrolTermVo patrolTermVo : list) {
                BeanUtils.copyProperties(patrolTermVo, pt);
                pt.setPatrolPoint(point);
                patrolTermRepository.save(pt);
            }
        }
        String termDeleteIds = patrolPointForSaveVo.getTermDeleteIds();
        if (StringUtils.isNotBlank(termDeleteIds)) {
            String[] idArr = termDeleteIds.split(",");
            for (String id : idArr) {
                patrolTermRepository.delete(id);
            }
        }
        PatrolPointVo patrolPointVo = new PatrolPointVo();
        BeanUtils.copyProperties(point, patrolPointVo);
        return patrolPointVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolPoint findPatrolPointById(String id) throws EnerbosException {
        PatrolPoint patrolPoint = patrolPointRepository.findOne(id);
        return patrolPoint;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(String[] ids, String qrCodeNum, String siteId) throws EnerbosException {
        List<PatrolPointVo> list = patrolPointDao.findByIdAndQrCodeNumAndSiteId(ids, qrCodeNum, siteId);
        return list;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean updateIsupdatedata(String id, String siteId, boolean b) throws EnerbosException {
        patrolPointDao.updateIsupdatedata(id, siteId, b);
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PatrolRecordVo findPatrolRecordByOrderAndPoint(String orderid, String pointid) throws EnerbosException {
        List<PatrolRecordVo> patrolRecordVoList = patrolRecordDao.findByOrderIdAndPointId(orderid, pointid);
        return (patrolRecordVoList != null && patrolRecordVoList.size() > 0) ? patrolRecordVoList.get(0) : null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void generateQrcode(List<String> ids) throws EnerbosException {
        ids.forEach(id -> {
            if (id.equalsIgnoreCase("all")) {
                List<PatrolPoint> pointList = patrolPointRepository.findAll();
                pointList.forEach(patrolPoint -> {
                    patrolPoint.setIsqrcode(true);
                    patrolPoint.setQrCodeNum(makeSerialNumber(6));
                });
            } else {
                PatrolPoint patrolPoint = patrolPointRepository.findOne(id);
                patrolPoint.setIsqrcode(true);
                patrolPoint.setQrCodeNum(makeSerialNumber(6));
            }
        });
    }

    private String makeSerialNumber(Integer numberLength) {
        StringBuffer serial = new StringBuffer();
        for (int i = 0; i < numberLength; i++) {
            serial.append((int) (10 * (Math.random())));
        }
        return serial.toString();
    }
}
