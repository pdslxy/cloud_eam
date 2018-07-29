package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月01日
 * @Description 二维码应用程序属性接口
 */
@Mapper
public interface QRCodeApplicationPropertyDao {

    /**
     * findAllApplicationProperty: 根据应用程序ID查询应用程序属性
     * @param QRCodeManagerId 二维码管理ID
     * @return List<QRCodeApplicationPropertyVo>
     */
    List<QRCodeApplicationPropertyVo> findApplicationPropertyByQRCodeManagerId(@Param("QRCodeManagerId") String QRCodeManagerId);
}
