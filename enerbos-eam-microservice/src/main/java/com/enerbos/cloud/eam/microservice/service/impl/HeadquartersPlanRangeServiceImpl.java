package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadquartersPlanRangeDao;
import com.enerbos.cloud.eam.microservice.service.HeadquartersPlanRangeService;
import com.enerbos.cloud.eam.vo.HeadquartersPlanRangeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/9/19 15:26
 * @Description
 */
@Service
public class HeadquartersPlanRangeServiceImpl implements HeadquartersPlanRangeService {

    @Autowired
    private HeadquartersPlanRangeDao headquartersPlanRangeDao;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> getListByPlanId(String id) {
        return headquartersPlanRangeDao.getSiteListByPlanId(id);
    }
}
