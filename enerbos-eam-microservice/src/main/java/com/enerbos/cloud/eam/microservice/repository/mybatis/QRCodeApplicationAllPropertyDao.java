package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.QRCodeApplicationAllPropertyVo;
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
 * @date 2017年07月31日
 * @Description 二维码应用程序所有属性接口
 */
@Mapper
public interface QRCodeApplicationAllPropertyDao {

    /**
     * findAllApplicationAllPropertyNotIn: 根据应用程序ID查询应用程序所有属性
     * @param applicationId
     * @param ids 查询结果去除ID集合
     * @return List<QRCodeApplicationAllPropertyVo>
     */
    List<QRCodeApplicationAllPropertyVo> findAllApplicationAllPropertyNotIn(@Param("applicationId") String applicationId,@Param("ids") List<String> ids);
}
