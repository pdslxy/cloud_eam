package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.vo.QRCodeApplicationAllPropertyVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月01日
 * @Description EAM二维码应用程序所有属性接口
 */
@RestController
public class QRCodeApplicationAllPropertyController {
    private final static Log logger = LogFactory.getLog(QRCodeApplicationAllPropertyController.class);

    @Autowired
    private com.enerbos.cloud.eam.microservice.service.QRCodeApplicationAllPropertyService QRCodeApplicationAllPropertyService;

    /**
     * findAllApplicationAllProperty: 分页查询二维码应用程序所有属性
     * @return List<QRCodeApplicationAllPropertyVo> 二维码应用程序所有属性VO集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/qrCode/findAllApplicationAllPropertyNotIn")
    public List<QRCodeApplicationAllPropertyVo> findAllApplicationAllPropertyNotIn(@RequestParam("applicationId") String applicationId,@RequestParam(value = "ids",required = false) List<String> ids) {
        List<QRCodeApplicationAllPropertyVo> result = QRCodeApplicationAllPropertyService.findAllApplicationAllPropertyNotIn(applicationId,ids);
        return result;
    }
}
