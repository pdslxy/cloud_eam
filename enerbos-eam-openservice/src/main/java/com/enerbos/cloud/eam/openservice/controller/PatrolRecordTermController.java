package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.PatrolRecordTermClient;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@RestController
@Api(description = "巡检项记录(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolRecordTermController {

    private static Logger logger = LoggerFactory.getLogger(PatrolRecordTermController.class);

    @Autowired
    private PatrolRecordTermClient patrolRecordTermClient;

    /**
     * 根据工单id查询巡检记录中巡检项内容
     * @param id
     * @return
     */
    @ApiOperation(value = "根据工单id查询巡检记录中巡检项内容", response = PatrolRecordTermVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/patrolRecordTerm/findPatrolTermByOrderAndPoint", method = RequestMethod.GET)
    public EnerbosMessage findPatrolTermByOrderId(@ApiParam(value = "工单id", required = true) @RequestParam("id") String id, @ApiParam(value = "工单id", required = true) @RequestParam("pointid") String pointid) {
        try {
            EnerbosPage<PatrolRecordTermVo> pageInfo = patrolRecordTermClient.findPatrolTermByOrderAndPoint(id, pointid);
            return EnerbosMessage.createSuccessMsg(pageInfo, "根据工单id查询巡检记录中巡检项内容", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolRecordTerm/findPatrolTermByOrderAndPoint------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }

    //获取错误码
    private String getErrorStatusCode(Exception e) {
        String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
        String statusCode = "";
        try {
            JSONObject jsonMessage = JSONObject.parseObject(message);
            if (jsonMessage != null) {
                statusCode = jsonMessage.get("status").toString();
            }
        } catch (Exception jsonException) {
            logger.error("-----/eam/open/workorder/findPageWorkOrderList----", jsonException);
        }
        return statusCode;
    }
}
