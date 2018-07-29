package com.enerbos.cloud.eam.microservice.repository.jpa;

import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlanRange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/10 14:47
 * @Description 总部计划应用范围
 */
public interface HeadquartersPlanRangeRepository extends JpaRepository<HeadquartersPlanRange,String> {

    void deleteBySiteId(String siteId);

    void deleteByPlanId(String planId);
}
