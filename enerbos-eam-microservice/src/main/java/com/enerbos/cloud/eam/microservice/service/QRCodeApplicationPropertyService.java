package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.QRCodeApplicationProperty;
import com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo;

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
public interface QRCodeApplicationPropertyService {

    /**
     * saveApplicationProperty: 保存应用程序属性
     * @param QRCodeApplicationPropertyList 应用程序属性对象集合{@link com.enerbos.cloud.eam.microservice.domain.QRCodeApplicationProperty}
     * @return List<QRCodeApplicationPropertyVo>
     */
    List<QRCodeApplicationProperty> saveApplicationProperty(List<QRCodeApplicationProperty> QRCodeApplicationPropertyList);

    /**
     * findApplicationPropertyByQRCodeManagerId: 根据应用程序ID查询应用程序属性
     * @param QRCodeManagerId 二维码管理ID
     * @return List<QRCodeApplicationPropertyVo>
     */
    List<QRCodeApplicationPropertyVo> findApplicationPropertyByQRCodeManagerId(String QRCodeManagerId);

    /**
     * deleteApplicationPropertyByIds: 根据ids删除二维码应用程序属性
     * @param ids
     * @return Boolean 删除是否成功
     */
    Boolean deleteApplicationPropertyByIds(List<String> ids);
}
