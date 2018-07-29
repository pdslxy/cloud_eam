package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.HeadquartersPlanRangeVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/9/19 15:25
 * @Description
 */
public interface HeadquartersPlanRangeService {
    /**
     * 根据总部计划ID查询应用范围数据
     * @param id
     * @return
     */
    List<String> getListByPlanId(String id);
}
