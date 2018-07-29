package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.QRCodeManager;
import com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo;
import com.enerbos.cloud.eam.vo.QRCodeManagerVo;
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
 * @Description 二维码管理查询接口
 */
@Mapper
public interface QRCodeManagerDao {
    /**
     * findQRCodeManagerByID: 根据ID查询二维码管理
     * @param id
     * @return QRCodeManager
     */
    QRCodeManager findQRCodeManagerByID(String id);

    /**
     * findQRCodeManagerByQRCodeNum: 根据QRCodeNum查询二维码管理
     * @param QRCodeNum
     * @return QRCodeManagerVo
     */
    QRCodeManagerVo findQRCodeManagerByQRCodeNum(String QRCodeNum);

    /**
     * findQRCodeManagerBySiteIdAndApplicationId: 根据站点ID和应用程序ID查询二维码管理
     * @param siteId        站点ID
     * @param applicationId 应用程序ID
     * @return QRCodeManagerVo
     */
    QRCodeManagerVo findQRCodeManagerBySiteIdAndApplicationId(@Param("siteId") String siteId, @Param("applicationId") String applicationId);

    /**
     * findPageQRCodeManager: 查询二维码管理
     * @param QRCodeManagerForFilterVo 过滤条件 {@link com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo}
     * @return List<QRCodeManagerVo>
     */
    List<QRCodeManagerVo> findPageQRCodeManager(QRCodeManagerForFilterVo QRCodeManagerForFilterVo);

    /**
     * findPageQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值查询二维码管理
     * @param siteId        站点ID
     * @param applicationValue 应用程序值
     * @return QRCodeManager
     */
    QRCodeManager findPageQRCodeManagerBySiteIdAndApplicationValue(@Param("siteId") String siteId, @Param("applicationValue") String applicationValue);

    /**
     * findPageQRCodeManagerBySiteIdAndPrefixQRCodeAndApplicationValue: 根据站点ID和应用程序值和应用程序前缀编码查询二维码管理
     * @param siteId        站点ID
     * @param prefixQRCode 二维码前缀编码
     * @return QRCodeManagerVo
     */
    QRCodeManagerVo findPageQRCodeManagerBySiteIdAndPrefixQRCodeAndApplicationValue(@Param("siteId") String siteId, @Param("prefixQRCode") String prefixQRCode, @Param("applicationValue") String applicationValue);
}
