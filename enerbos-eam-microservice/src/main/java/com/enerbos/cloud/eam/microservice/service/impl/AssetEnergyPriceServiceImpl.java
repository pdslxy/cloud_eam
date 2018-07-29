package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.enerbos.cloud.eam.microservice.domain.DispatchWorkOrder;
import com.enerbos.cloud.eam.microservice.repository.jpa.AssetEnergyPriceRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.AssetEnergyPriceDao;
import com.enerbos.cloud.eam.microservice.service.AssetEnergyPriceService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVoForFilter;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */

@Service
public class AssetEnergyPriceServiceImpl implements AssetEnergyPriceService {
    @Autowired
    private AssetEnergyPriceRepository assetEnergyPriceRepository;

    @Autowired
    private AssetEnergyPriceDao assetEnergyPriceDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<AssetEnergyPriceVo> getEnergyPriceList(AssetEnergyPriceVoForFilter assetEnergyPriceVoForFilter) {

        PageHelper.startPage(assetEnergyPriceVoForFilter.getPageNum(),
                assetEnergyPriceVoForFilter.getPageSize());

        String word = assetEnergyPriceVoForFilter.getKeyword();
        Map<String, Object> filters=new HashMap<>();
        try{
           //  filters = EamCommonUtil.reModelToMap(assetEnergyPriceVoForFilter);
               filters = ReflectionUtils.reflectionModelToMap(assetEnergyPriceVoForFilter);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<AssetEnergyPriceVo>(assetEnergyPriceDao.findEnergyPrices(filters));

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AssetEnergyPrice saveEnergyPrice(AssetEnergyPrice assetEnergyPrice){
        return assetEnergyPriceRepository.save(assetEnergyPrice);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AssetEnergyPrice updateEnergyPrice(AssetEnergyPrice assetEnergyPrice){
        return assetEnergyPriceRepository.save(assetEnergyPrice);
    }

    @Override
    public  void deleteEnergyPrice(String ids[]){
        for (String id : ids) {
            assetEnergyPriceRepository.delete(id);
        }
    }


    @Override
    public AssetEnergyPrice findEnergyPriceDetail(String id){
        return assetEnergyPriceRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean changeOrderStatus(List<String> ids, String status) {


        Map<String, String> oldStatusMap = new HashMap<>();
        List<AssetEnergyPrice> updateList = new ArrayList<>();
        AssetEnergyPrice assetEnergyPrice;
        for (int i = 0, len = ids.size(); i < len; i++) {
            assetEnergyPrice = assetEnergyPriceRepository.findOne(ids.get(i));

            if (assetEnergyPrice == null) {
                throw new EnerbosException("404", String.format("编号不存在！  [%s]", ids.get(i)));
            }
            assetEnergyPrice.setStatus(status);
            updateList.add(assetEnergyPrice);
        }
        assetEnergyPriceRepository.save(updateList);



        return true;
    }
}
