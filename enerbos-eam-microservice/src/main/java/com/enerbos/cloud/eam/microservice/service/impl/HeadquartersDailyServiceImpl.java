package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersDaily;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlanRange;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersDailyRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersDailyTaskRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersPlanRangeRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadquartersDailyDao;
import com.enerbos.cloud.eam.microservice.service.HeadquartersDailyService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 20:31
 * @Description 总部事物，例行工作业务层实现类
 */
@Service
public class HeadquartersDailyServiceImpl implements HeadquartersDailyService {

    @Autowired
    private HeadquartersDailyDao headquartersDailyDao;

    @Autowired
    private HeadquartersDailyRepository headquartersDailyRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<HeadquartersDailyVo> findPageList(HeadquartersDailyVoForFilter filter) {
        PageHelper.startPage(filter.getPageNum(),filter.getPageSize());
        String word = filter.getWord();
        Map<String, Object> filters=new HashMap<>();
        try{
            filters = ReflectionUtils.reflectionModelToMap(filter);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<>(headquartersDailyDao.findListByFilter(filters));
    }

    @Override
    public HeadquartersDailyVo findDetail(String id) {
        return headquartersDailyDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteById(List<String> ids){
        for (String id : ids) {
            headquartersDailyRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HeadquartersDailyVoForSave save(HeadquartersDailyVoForSave savevo) {
        HeadquartersDaily po = new HeadquartersDaily();
        BeanUtils.copyProperties(savevo,po);
        HeadquartersDaily daily = headquartersDailyRepository.save(po);
        //返回数据
        HeadquartersDailyVoForSave newSave = new HeadquartersDailyVoForSave();
        BeanUtils.copyProperties(daily,newSave);

        return newSave;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void upStatus(HeadquartersDailyVoForUpStatus headquartersDailyVoForUpStatus) {
        headquartersDailyDao.upStatusByIds(headquartersDailyVoForUpStatus);
    }

}
