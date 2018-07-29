package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVoForFilter;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryFilterVo;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface EnergyPriceChangHistoryService {


    //分页查询历史
    PageInfo<EnergyPriceChangHistoryVo> findEnergyPriceChangHistory(EnergyPriceChangHistoryFilterVo filters);

    //根据能源价格ID查询最近的一条记录
    EnergyPriceChangHistoryVo findMaxCreateDatePriceChangHistoryById(String eneryPriceId);

    //保存

    EnergyPriceChangHistoryVo save(EnergyPriceChangHistoryVo historyVo);



}
