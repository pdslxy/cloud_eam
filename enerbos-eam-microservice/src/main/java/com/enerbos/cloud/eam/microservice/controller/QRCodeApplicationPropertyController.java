package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.QRCodeApplicationProperty;
import com.enerbos.cloud.eam.microservice.service.QRCodeApplicationPropertyService;
import com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo;
import com.enerbos.cloud.util.ReflectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月01日
 * @Description EAM二维码应用程序属性接口
 */
@RestController
public class QRCodeApplicationPropertyController {
    private final static Log logger = LogFactory.getLog(QRCodeApplicationPropertyController.class);

    @Resource
    private QRCodeApplicationPropertyService QRCodeApplicationPropertyService;

    /**
     * saveApplicationPropertyList:保存二维码应用程序属性
     * @param QRCodeApplicationPropertyVoList 二维码应用程序属性Vo集合{@link com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo}
     * @return Boolean 是否保存成功
     */
    @RequestMapping(value = "/eam/micro/qrCode/saveApplicationPropertyList", method = RequestMethod.POST)
    public Boolean saveApplicationPropertyList(@RequestBody List<QRCodeApplicationPropertyVo> QRCodeApplicationPropertyVoList){
        List<QRCodeApplicationProperty> QRCodeApplicationPropertyList=new ArrayList<>();
        boolean result=false;
        try {
            QRCodeApplicationProperty QRCodeApplicationProperty;
            for (QRCodeApplicationPropertyVo QRCodeApplicationPropertyVo : QRCodeApplicationPropertyVoList) {
                QRCodeApplicationProperty = new QRCodeApplicationProperty();
                ReflectionUtils.copyProperties(QRCodeApplicationPropertyVo, QRCodeApplicationProperty, null);
                QRCodeApplicationPropertyList.add(QRCodeApplicationProperty);
            }
            QRCodeApplicationPropertyList=QRCodeApplicationPropertyService.saveApplicationProperty(QRCodeApplicationPropertyList);
            result=true;
        } catch (Exception e) {
            logger.error("-------/eam/micro/qrCode/saveApplicationPropertyList-----", e);
        }
        return result;
    }

    /**
     * findApplicationPropertyByQRCodeManagerId: 根据应用程序ID查询应用程序属性
     * @return List<QRCodeApplicationPropertyVo> 二维码应用程序属性VO集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/qrCode/findApplicationPropertyByQRCodeManagerId")
    public List<QRCodeApplicationPropertyVo> findApplicationPropertyByQRCodeManagerId(@RequestParam("QRCodeManagerId") String QRCodeManagerId) {
        List<QRCodeApplicationPropertyVo> result = QRCodeApplicationPropertyService.findApplicationPropertyByQRCodeManagerId(QRCodeManagerId);
        return result;
    }

    /**
     * deleteApplicationPropertyByIds:删除选中的应用程序属性
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/qrCode/deleteApplicationPropertyByIds", method = RequestMethod.POST)
    public Boolean deleteApplicationPropertyByIds(@RequestBody List<String> ids) {
        return QRCodeApplicationPropertyService.deleteApplicationPropertyByIds(ids);
    }
}
