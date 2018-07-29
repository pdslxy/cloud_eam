package com.enerbos.cloud.eam.openservice.config;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.MeterClient;
import com.enerbos.cloud.ams.client.WarningRuleClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForDetail;
import com.enerbos.cloud.ams.vo.meter.MeterVoForSave;
import com.enerbos.cloud.ams.vo.warningRule.WarningRuleVoForDetail;
import com.enerbos.cloud.eam.client.CodeGeneratorClient;
import com.enerbos.cloud.eam.client.RepairOrderClient;
import com.enerbos.cloud.eam.client.WarningRepairClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.RepairOrderCommon;
import com.enerbos.cloud.eam.vo.RepairOrderFlowVo;
import com.enerbos.cloud.eam.vo.WarningRepairVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 17/11/16 上午11:00
 * @Description 定时任务cron表达式配置
 */
@Component
@ConfigurationProperties(locations = "classpath:timed-task.yml",prefix="timedTask")
public class TimedTask {
    private Map<String ,String> cron  = new HashMap<>();

    public Map<String, String> getCron() {
        return cron;
    }

    public void setCron(Map<String, String> cron) {
        this.cron = cron;
    }
}
