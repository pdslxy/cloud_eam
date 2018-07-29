package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandard;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardRfCollector;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandardTask;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceMaintenancePlan;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceJobStandardRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceJobStandardRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceJobStandardTaskRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceJobStandardDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceJobStandardRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceJobStandardService;
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
 * @Description EAM作业标准接口
 */
@Service
public class MaintenanceJobStandardServiceImpl implements MaintenanceJobStandardService {

    @Autowired
    private MaintenanceJobStandardDao maintenanceJobStandardDao;
    
    @Autowired
    private MaintenanceJobStandardRepository maintenanceJobStandardRepository;

    @Autowired
    private MaintenanceJobStandardRfCollectorRepository maintenanceJobStandardRfCollectorRepository;

    @Autowired
    private MaintenanceJobStandardRfCollectorDao maintenanceJobStandardRfCollectorDao;

    @Autowired
    private MaintenanceJobStandardTaskRepository maintenanceJobStandardTaskRepository ;

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaintenanceJobStandard save(MaintenanceJobStandard jobplan) {
        return maintenanceJobStandardRepository.save(jobplan);
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceJobStandard findJobStandardByID(String id) {
        return maintenanceJobStandardDao.findJobStandardByID(id);
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceJobStandardVo findJobStandardByJobStandardNum(String jobStandardNum) {
        return maintenanceJobStandardDao.findJobStandardByJobStandardNum(jobStandardNum);
    }

    
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaintenanceJobStandardForListVo> findJobStandardList(MaintenanceJobStandardSelectVo maintenanceJobStandardSelectVo){
		PageHelper.startPage(maintenanceJobStandardSelectVo.getPageNum(), maintenanceJobStandardSelectVo.getPageSize());
        if (StringUtils.isNotBlank(maintenanceJobStandardSelectVo.getSorts())) {
            String sorts = maintenanceJobStandardSelectVo.getSorts();
            maintenanceJobStandardSelectVo.setSorts(ParamUtils.convertSortParam(new MaintenanceJobStandard(), sorts).get(ParamConstans.SUCCESS).toString());
        }
        return maintenanceJobStandardDao.findJobStandard(maintenanceJobStandardSelectVo);
    }
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MaintenanceJobStandardForListVo> findPageJobStandardByAssetId(
			MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
		PageHelper.startPage(maintenanceForAssetFilterVo.getPageNum(), maintenanceForAssetFilterVo.getPageSize());
        if (StringUtils.isNotBlank(maintenanceForAssetFilterVo.getSorts())) {
            String sorts = maintenanceForAssetFilterVo.getSorts();
            maintenanceForAssetFilterVo.setSorts(ParamUtils.convertSortParam(new MaintenanceMaintenancePlan(), sorts).get(ParamConstans.SUCCESS).toString());
        }
		return maintenanceJobStandardDao.findPageJobStandardByAssetId(maintenanceForAssetFilterVo);
	}

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean deleteJobStandardById(String id) {
        maintenanceJobStandardRepository.delete(id);
        return true;
    }
    
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean deleteJobStandardByIds(List<String> ids) {
        for(String id : ids){
        	maintenanceJobStandardRepository.delete(id);
        }
        return true;
    }

    @Override
    public void collectJobStandard(List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList) {
        if (maintenanceJobStandardRfCollectorVoList == null || maintenanceJobStandardRfCollectorVoList.isEmpty()) {
            return;
        }
        //过滤重复数据
        Map<String, List<MaintenanceJobStandardRfCollectorVo>> map = maintenanceJobStandardRfCollectorVoList.stream()
                .filter(vo -> Objects.nonNull(vo.getJobStandardId()))
                .filter(vo -> Objects.nonNull(vo.getPersonId()))
                .collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getJobStandardId(), o.getPersonId()), Collectors.toList()));

        List<MaintenanceJobStandardRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new MaintenanceJobStandardRfCollector(vo.getJobStandardId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
        list.stream().filter(o -> 0 == maintenanceJobStandardRfCollectorDao.checkIsCollected(o.getJobStandardId(), o.getPersonId(), o.getProduct())).forEach(maintenanceJobStandardRfCollectorRepository::save);
    }

    @Override
    public void cancelCollectJobStandard(List<MaintenanceJobStandardRfCollectorVo> maintenanceJobStandardRfCollectorVoList) {
        if (maintenanceJobStandardRfCollectorVoList == null || maintenanceJobStandardRfCollectorVoList.isEmpty()) {
            return;
        }
        Map<String, List<MaintenanceJobStandardRfCollectorVo>> map = maintenanceJobStandardRfCollectorVoList.stream().collect(Collectors.groupingBy(MaintenanceJobStandardRfCollectorVo::getPersonId, Collectors.toList()));
        for (Map.Entry<String, List<MaintenanceJobStandardRfCollectorVo>> entry : map.entrySet()) {
            List<MaintenanceJobStandardRfCollector> list = maintenanceJobStandardRfCollectorDao.findJobStandardRfCollectorByPersonId(entry.getKey());

            if (!ObjectUtils.isEmpty(list)) {
                Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(MaintenanceJobStandardRfCollectorVo::getJobStandardId, MaintenanceJobStandardRfCollectorVo::getPersonId));

                list.stream().filter(o -> collectedIdMap.containsKey(o.getJobStandardId())).forEach(o -> maintenanceJobStandardRfCollectorRepository.delete(o.getId()));
            }
        }
    }

    /**
     * 批量保存标准
     *
     * @param mjsVos
     */
    @Override
    @Transactional
    public List<MaintenanceJobStandardVo> saveBatchStandard(List<MaintenanceJobStandardVo> mjsVos) throws  Exception {
        List<MaintenanceJobStandard>  mjss  = new ArrayList<MaintenanceJobStandard>();
        ReflectionUtils.copyProperties(mjsVos ,mjss,new MaintenanceJobStandard() );
        mjss = maintenanceJobStandardRepository.save(mjss) ;
        Map<String,String> jobStandardNumAndIdMap =new HashMap<String,String>( ) ;
        for (MaintenanceJobStandard maintenanceJobStandard:mjss
             ) {
            jobStandardNumAndIdMap.put(maintenanceJobStandard.getJobStandardNum() , maintenanceJobStandard.getId());
        }
        List<MaintenanceJobStandardTask>  mjsts = new ArrayList<MaintenanceJobStandardTask>();
        for (MaintenanceJobStandardVo maintenanceJobStandardVo: mjsVos
             ) {
            if(maintenanceJobStandardVo.getMaintenanceJobStandardTaskVoList()!=null){
                 for (MaintenanceJobStandardTaskVo mjskVo:maintenanceJobStandardVo.getMaintenanceJobStandardTaskVoList()){
                     MaintenanceJobStandardTask mjsk = new MaintenanceJobStandardTask() ;
                     ReflectionUtils.copyProperties(mjskVo,mjsk,null);
                     mjsk.setJobStandardId(jobStandardNumAndIdMap.get(maintenanceJobStandardVo.getJobStandardNum()));
                     mjsts.add(mjsk);
                 }
            }
        }
        maintenanceJobStandardTaskRepository.save(mjsts);
        ReflectionUtils.copyProperties(mjss,mjsVos ,new MaintenanceJobStandardVo() );
        return mjsVos ;
    }
}