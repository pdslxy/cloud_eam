package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlan;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanAsset;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlanRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceMaintenancePlanAssetRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceMaintenancePlanRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceMaintenancePlanRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceMaintenancePlanDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceMaintenancePlanRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceMaintenancePlanService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ParamConstans;
import com.enerbos.cloud.util.ParamUtils;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM预防性维护service
 */
@Service
public class MaintenanceMaintenancePlanServiceImpl implements MaintenanceMaintenancePlanService {

    @Autowired
    private MaintenanceMaintenancePlanDao maintenancePlanDao;
    
    @Autowired
    private MaintenanceMaintenancePlanRepository maintenancePlanRepository;

    @Autowired
    private MaintenanceMaintenancePlanRfCollectorRepository maintenanceMaintenancePlanRfCollectorRepository;

    @Autowired
    private MaintenanceMaintenancePlanRfCollectorDao maintenanceMaintenancePlanRfCollectorDao;

    @Autowired
    private MaintenanceMaintenancePlanAssetRepository maintenanceMaintenancePlanAssetRepository ;
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaintenanceMaintenancePlan saveMaintenancePlan(MaintenanceMaintenancePlan maintenanceMaintenancePlan) {
        return maintenancePlanRepository.save(maintenanceMaintenancePlan);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceMaintenancePlanVo findMaintenancePlanById(String id) {
        return maintenancePlanDao.findMaintenancePlanById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceMaintenancePlanVo findMaintenancePlanByMaintenancePlanNum(String maintenancePlanNum) {
        return maintenancePlanDao.findMaintenancePlanByMaintenancePlanNum(maintenancePlanNum);
    }

    @Override
    public List<MaintenanceMaintenancePlanVo> findAllMaintenancePlanByStatus(String status) {
        return maintenancePlanDao.findAllMaintenancePlanByStatus(status);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanList(MaintenanceMaintenancePlanSelectVo maintenanceMaintenancePlanSelectVo) {
        PageHelper.startPage(maintenanceMaintenancePlanSelectVo.getPageNum(), maintenanceMaintenancePlanSelectVo.getPageSize());
        if (StringUtils.isNotBlank(maintenanceMaintenancePlanSelectVo.getSorts())) {
            String sorts = maintenanceMaintenancePlanSelectVo.getSorts();
            maintenanceMaintenancePlanSelectVo.setSorts(ParamUtils.convertSortParam(new MaintenanceMaintenancePlan(), sorts).get(ParamConstans.SUCCESS).toString());
        }
        return maintenancePlanDao.findPageMaintenancePlanList(maintenanceMaintenancePlanSelectVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Boolean deleteMaintenancePlanByIds(List<String> ids) {
        for (String id : ids) {
            maintenancePlanRepository.delete(id);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaintenanceMaintenancePlanForListVo> findPageMaintenancePlanByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo){
    	PageHelper.startPage(maintenanceForAssetFilterVo.getPageNum(), maintenanceForAssetFilterVo.getPageSize());
        if (StringUtils.isNotBlank(maintenanceForAssetFilterVo.getSorts())) {
            String sorts = maintenanceForAssetFilterVo.getSorts();
            maintenanceForAssetFilterVo.setSorts(ParamUtils.convertSortParam(new MaintenanceMaintenancePlan(), sorts).get(ParamConstans.SUCCESS).toString());
        }
        return maintenancePlanDao.findPageMaintenancePlanByAssetId(maintenanceForAssetFilterVo);
    }

    @Override
    public void collectMaintenancePlan(List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList) {
        if (maintenanceMaintenancePlanRfCollectorVoList == null || maintenanceMaintenancePlanRfCollectorVoList.isEmpty()) {
            return;
        }
        //过滤重复数据
        Map<String, List<MaintenanceMaintenancePlanRfCollectorVo>> map = maintenanceMaintenancePlanRfCollectorVoList.stream()
                .filter(vo -> Objects.nonNull(vo.getMaintenancePlanId()))
                .filter(vo -> Objects.nonNull(vo.getPersonId()))
                .collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getMaintenancePlanId(), o.getPersonId()), Collectors.toList()));

        List<MaintenanceMaintenancePlanRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new MaintenanceMaintenancePlanRfCollector(vo.getMaintenancePlanId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
        list.stream().filter(o -> 0 == maintenanceMaintenancePlanRfCollectorDao.checkIsCollected(o.getMaintenancePlanId(), o.getPersonId(), o.getProduct())).forEach(maintenanceMaintenancePlanRfCollectorRepository::save);
    }

    @Override
    public void cancelCollectMaintenancePlan(List<MaintenanceMaintenancePlanRfCollectorVo> maintenanceMaintenancePlanRfCollectorVoList) {
        if (maintenanceMaintenancePlanRfCollectorVoList == null || maintenanceMaintenancePlanRfCollectorVoList.isEmpty()) {
            return;
        }
        Map<String, List<MaintenanceMaintenancePlanRfCollectorVo>> map = maintenanceMaintenancePlanRfCollectorVoList.stream().collect(Collectors.groupingBy(MaintenanceMaintenancePlanRfCollectorVo::getPersonId, Collectors.toList()));
        for (Map.Entry<String, List<MaintenanceMaintenancePlanRfCollectorVo>> entry : map.entrySet()) {
            List<MaintenanceMaintenancePlanRfCollector> list = maintenanceMaintenancePlanRfCollectorDao.findMaintenancePlanRfCollectorByPersonId(entry.getKey());

            if (!ObjectUtils.isEmpty(list)) {
                Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(MaintenanceMaintenancePlanRfCollectorVo::getMaintenancePlanId, MaintenanceMaintenancePlanRfCollectorVo::getPersonId));

                list.stream().filter(o -> collectedIdMap.containsKey(o.getMaintenancePlanId())).forEach(o -> maintenanceMaintenancePlanRfCollectorRepository.delete(o.getId()));
            }
        }
    }

    @Override
    @Transactional
    public void saveBatchPlans(List<MaintenanceMaintenancePlanVo> mmpVos) throws  Exception{
        List<MaintenanceMaintenancePlan> mmps = new ArrayList<MaintenanceMaintenancePlan>() ;
        ReflectionUtils.copyProperties(mmpVos,mmps,new MaintenanceMaintenancePlan());
        mmps =  maintenancePlanRepository.save(mmps) ;
        Map<String,String> codeIdMap = new HashMap<String,String>() ;
        for (MaintenanceMaintenancePlan mmp: mmps
             ) {
            codeIdMap.put(mmp.getMaintenancePlanNum(),mmp.getId()) ;
        }
        List<MaintenanceMaintenancePlanAsset> maintenanceMaintenancePlanAssets  = new ArrayList<MaintenanceMaintenancePlanAsset>() ;
        for (MaintenanceMaintenancePlanVo mmpVo: mmpVos
             ) {
            List<String> assetIds = mmpVo.getAssetList() ;
            for (String id: assetIds
                 ) {
                MaintenanceMaintenancePlanAsset maintenanceMaintenancePlanAsset  = new MaintenanceMaintenancePlanAsset() ;
                maintenanceMaintenancePlanAsset.setAssetId(id);
                maintenanceMaintenancePlanAsset.setMaintenancePlanId(codeIdMap.get(mmpVo.getMaintenancePlanNum()));
                maintenanceMaintenancePlanAssets.add(maintenanceMaintenancePlanAsset);
            }
        }
        maintenanceMaintenancePlanAssetRepository.save(maintenanceMaintenancePlanAssets);
    }
}
