package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.QRCodeManager;
import com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo;
import com.enerbos.cloud.eam.vo.QRCodeManagerVo;

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
public interface QRCodeManagerService {

    /**
     * saveQRCodeManager: 保存二维码管理
     * @param QRCodeManager 过滤条件 {@link com.enerbos.cloud.eam.microservice.domain.QRCodeManager}
     * @return List<QRCodeManagerVo>
     */
    QRCodeManager saveQRCodeManager(QRCodeManager QRCodeManager);

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
     * findPageQRCodeManager: 查询二维码管理
     * @param QRCodeManagerForFilterVo 过滤条件 {@link com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo}
     * @return List<QRCodeManagerVo>
     */
    List<QRCodeManagerVo> findPageQRCodeManager(QRCodeManagerForFilterVo QRCodeManagerForFilterVo);

    /**
     * deleteQRCodeManagerList: 根据ids删除二维码管理
     * @param ids
     * @return Boolean 删除是否成功
     */
    Boolean deleteQRCodeManagerList(List<String> ids);

    /**
     * findQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值查询二维码管理
     * @param siteId        站点ID
     * @param applicationValue 应用程序值
     * @return QRCodeManager 二维码管理实体
     */
    QRCodeManagerVo findQRCodeManagerBySiteIdAndApplicationValue(String siteId,String applicationValue);

    /**
     * findPageQRCodeManagerBySiteIdAndPrefixQRCode: 根据站点ID和应用程序值查询二维码管理
     * @param siteId        站点ID
     * @param prefixQRCode 二维码前缀编码
     * @return QRCodeManagerVo
     */
    QRCodeManagerVo findPageQRCodeManagerBySiteIdAndPrefixQRCode(String siteId, String prefixQRCode);

    /**
     * updateQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值修改二维码管理是否有数据更新
     * @param siteId        站点ID
     * @param applicationValue
     * @param dataUpdate
     * @return Boolean 更新是否成功是否成功
     */
    Boolean updateQRCodeManagerBySiteIdAndApplicationValue(String siteId, String applicationValue,Boolean dataUpdate);

    /**
     * generateQRCodeManagerSuffixQRCode: 根据站点和应用程序value生成当前后缀编码
     * @param siteId        站点ID
     * @param applicationValue 应用程序value
     * @return 生成的最新编码
     */
    String generateQRCodeManagerSuffixQRCode(String siteId, String applicationValue);
}
