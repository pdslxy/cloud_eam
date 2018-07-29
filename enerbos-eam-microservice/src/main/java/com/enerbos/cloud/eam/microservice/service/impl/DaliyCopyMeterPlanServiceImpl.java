package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlan;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanMeterRelation;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanRequency;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterPlanMeterRelationRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterPlanRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterPlanRequencyRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DaliyCopyMeterPlanDao;
import com.enerbos.cloud.eam.microservice.service.DaliyCopyMeterPlanService;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanFilterVo;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanRequencyVo;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVoForList;
import com.enerbos.cloud.eam.vo.DaliyCopyMeterPlanMeterRelationVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DaliyCopyMeterPlanServiceImpl implements DaliyCopyMeterPlanService {

    @Autowired
    private DaliyCopyMeterPlanDao daliyCopyMeterPlanDao;

    @Autowired
    private DaliyCopyMeterPlanRepository daliyCopyMeterPlanRepository;

    @Autowired
    private DaliyCopyMeterPlanRequencyRepository daliyCopyMeterPlanRequencyRepository;

    @Autowired
    private DaliyCopyMeterPlanMeterRelationRepository daliyCopyMeterPlanMeterRelationRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<DailyCopyMeterPlanVoForList> findCopyMeterPlans(DailyCopyMeterPlanFilterVo dailyCopyMeterPlanFilterVo) throws Exception {
        PageHelper.startPage(dailyCopyMeterPlanFilterVo.getPageNum(), dailyCopyMeterPlanFilterVo.getPageSize());
        Map<String, Object> filters = null;
        if (StringUtils.isNotEmpty(dailyCopyMeterPlanFilterVo.getKeyword())) {
            dailyCopyMeterPlanFilterVo.setKeywords(dailyCopyMeterPlanFilterVo.getKeyword().split(" "));
        }
        filters = com.enerbos.cloud.util.ReflectionUtils.reflectionModelToMap(dailyCopyMeterPlanFilterVo);
        return new PageInfo<DailyCopyMeterPlanVoForList>(daliyCopyMeterPlanDao.findCopyMeterPlans(filters));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DaliyCopyMeterPlan saveCopyMeterPlan(DaliyCopyMeterPlan daliyCopyMeterPlan) {
        return daliyCopyMeterPlanRepository.save(daliyCopyMeterPlan);
    }


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteCopyMeterPlan(String[] ids) {

        for (String id : ids) {
            daliyCopyMeterPlanRepository.delete(id);
            daliyCopyMeterPlanRequencyRepository.deleteByCopyMeterPlanId(id);
            daliyCopyMeterPlanMeterRelationRepository.deleteByCopyMeterPlanId(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DaliyCopyMeterPlan findCopyMeterPlanById(String id) {
        return daliyCopyMeterPlanRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateCopyMeterPlanStatus(String[] ids, String status) {
        for (String id : ids) {
            DaliyCopyMeterPlan daliyCopyMeterPlan = findCopyMeterPlanById(id);
            daliyCopyMeterPlan.setStatus(status);
            this.saveCopyMeterPlan(daliyCopyMeterPlan);
        }
    }

    @Override
    public void saveCopyMeterPlanRequency(List<DaliyCopyMeterPlanRequency> daliyCopyMeterPlanRequencyList) {
        daliyCopyMeterPlanRequencyRepository.save(daliyCopyMeterPlanRequencyList);

    }

    @Override
    public void saveCopyMeterPlanMeterRelation(List<DaliyCopyMeterPlanMeterRelation> daliyCopyMeterPlanMeterRelations) {
        daliyCopyMeterPlanMeterRelationRepository.save(daliyCopyMeterPlanMeterRelations);

    }

    @Override
    public PageInfo<DailyCopyMeterPlanRequencyVo> findCopyMeterPlanRequencyVosById(String id) {
        Map filters = new HashMap();
        filters.put("planId", id);
        return new PageInfo<DailyCopyMeterPlanRequencyVo>(
                daliyCopyMeterPlanDao.findCopyMeterPlanRequencyVosById(filters));
    }

    @Override
    public void deleteCopyMeterPlanMeterRelationById(String[] ids) {
        for (String id : ids) {
            daliyCopyMeterPlanMeterRelationRepository.deleteByCopyMeterPlanId(id);
        }
    }

    @Override
    public void deleteCopyMeterPlanRequencyById(String[] ids) {
        for (String id : ids) {
            daliyCopyMeterPlanRequencyRepository.deleteByCopyMeterPlanId(id);
        }
    }

    @Override
    public PageInfo<DaliyCopyMeterPlanMeterRelationVo> findCopyMeterPlanMeterRelationVosById(String id) {
        Map filters = new HashMap();
        filters.put("planId", id);
        return new PageInfo<DaliyCopyMeterPlanMeterRelationVo>(
                daliyCopyMeterPlanDao.findCopyMeterPlanMeterRelationVosById(filters));
    }


}
