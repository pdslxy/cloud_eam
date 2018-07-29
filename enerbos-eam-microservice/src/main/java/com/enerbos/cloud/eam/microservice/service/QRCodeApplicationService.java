package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.QRCodeApplicationVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月01日
 * @Description 二维码应用程序查询接口
 */
public interface QRCodeApplicationService {

    /**
     * findQRCodeApplicationByID: 根据ID查询二维码应用程序
     * @param id
     * @return QRCodeApplicationVo
     */
    QRCodeApplicationVo findQRCodeApplicationByID(String id);

    /**
     * findAllQRCodeApplication: 查询所有二维码应用程序
     * @return List<QRCodeApplicationVo>
     */
    List<QRCodeApplicationVo> findAllQRCodeApplication();
}
