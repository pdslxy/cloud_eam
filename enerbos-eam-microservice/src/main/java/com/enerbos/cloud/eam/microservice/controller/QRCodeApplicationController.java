package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.QRCodeApplicationService;
import com.enerbos.cloud.eam.vo.QRCodeApplicationVo;
import com.enerbos.cloud.eam.vo.QRCodeManagerVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月01日
 * @Description EAM二维码应用程序接口
 */
@RestController
public class QRCodeApplicationController {
    private final static Log logger = LogFactory.getLog(QRCodeApplicationController.class);

    @Resource
    private QRCodeApplicationService QRCodeApplicationService;

    /**
     * findQRCodeApplicationByID:根据ID查询二维码应用程序
     * @param id
     * @return QRCodeApplicationVo 二维码应用程序VO
     */
    @RequestMapping(value = "/eam/micro/qrCode/findQRCodeApplicationByID", method = RequestMethod.GET)
    public QRCodeApplicationVo findQRCodeApplicationByID(@RequestParam("id") String id){
        QRCodeApplicationVo QRCodeApplicationVo=QRCodeApplicationService.findQRCodeApplicationByID(id);
        return QRCodeApplicationVo;
    }

    /**
     * findPageQRCodeApplication: 分页查询二维码应用程序
     * @return PageInfo<QRCodeApplicationVo> 二维码应用程序VO集合
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/findPageQRCodeApplication")
    public PageInfo<QRCodeApplicationVo> findPageQRCodeApplication() {
        List<QRCodeApplicationVo> result = QRCodeApplicationService.findAllQRCodeApplication();
        return new PageInfo<QRCodeApplicationVo>(result);
    }
}
