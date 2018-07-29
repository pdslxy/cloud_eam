package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.microservice.domain.EnergyPriceChangHistory;
import com.enerbos.cloud.eam.microservice.repository.jpa.AssetEnergyPriceRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.EnergyPriceChangHistoryRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.AssetEnergyPriceDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.EnergyPriceChangHistoryDao;
import com.enerbos.cloud.eam.microservice.service.AssetEnergyPriceService;
import com.enerbos.cloud.eam.microservice.service.EnergyPriceChangHistoryService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */

@Service
public class EnergyPriceChangHistoryServiceImpl implements EnergyPriceChangHistoryService {

    @Autowired
    private EnergyPriceChangHistoryRepository historyRepository;

    @Autowired
    private EnergyPriceChangHistoryDao energyPriceChangHistoryDao;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageInfo<EnergyPriceChangHistoryVo>  findEnergyPriceChangHistory(EnergyPriceChangHistoryFilterVo filterVo) {
        Map<String, Object> filters = new HashMap<>();
        try{
            filters = ReflectionUtils.reflectionModelToMap(filterVo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new PageInfo<>(energyPriceChangHistoryDao.findEnergyPriceChangHistory(filters));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public EnergyPriceChangHistoryVo findMaxCreateDatePriceChangHistoryById(String eneryPriceId) {
        return energyPriceChangHistoryDao.findMaxCreateDatePriceChangHistoryById(eneryPriceId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EnergyPriceChangHistoryVo save(EnergyPriceChangHistoryVo historyVo) {
        EnergyPriceChangHistory history=new EnergyPriceChangHistory();
        BeanUtils.copyProperties(historyVo,history);
        EnergyPriceChangHistory history1=historyRepository.save(history);
        BeanUtils.copyProperties(history1,historyVo);
        return historyVo;
    }
}
