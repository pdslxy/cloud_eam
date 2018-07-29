package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.QRCodeApplicationAllPropertyVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月01日
 * @Description 二维码应用程序所有属性查询接口
 */
public interface QRCodeApplicationAllPropertyService {

    /**
     * findAllApplicationAllProperty: 根据应用程序ID查询应用程序所有属性
     * @param applicationId
     * @return List<QRCodeApplicationAllPropertyVo>
     */
    List<QRCodeApplicationAllPropertyVo> findAllApplicationAllPropertyNotIn(String applicationId,List<String> ids);
}
