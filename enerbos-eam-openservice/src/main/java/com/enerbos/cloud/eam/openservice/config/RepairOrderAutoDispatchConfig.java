package com.enerbos.cloud.eam.openservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-12-04 17:26
 * @Description 报修工单自动分派流程配置
 */
@Component
@ConfigurationProperties(locations = "classpath:repair-order-auto-dispatch.yml",prefix="repairOrderAutoDispatch")
public class RepairOrderAutoDispatchConfig {

    private Map<String, List<String>> siteMap = new HashMap<>();

    public Map<String, List<String>> getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(Map<String, List<String>> siteMap) {
        this.siteMap = siteMap;
    }
}
