package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.QRCodeManager;
import com.enerbos.cloud.eam.microservice.service.QRCodeManagerService;
import com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo;
import com.enerbos.cloud.eam.vo.QRCodeManagerVo;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

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
 * @Description EAM二维码管理接口
 */
@RestController
public class QRCodeManagerController {
    private final static Log logger = LogFactory.getLog(QRCodeManagerController.class);

    @Resource
    private QRCodeManagerService QRCodeManagerService;

    /**
     * saveQRCodeManager:保存二维码管理
     * @param QRCodeManagerVo {@link com.enerbos.cloud.eam.vo.QRCodeManagerVo}
     * @return QRCodeManagerVo
     */
    @RequestMapping(value = "/eam/micro/qrCode/saveQRCodeManager", method = RequestMethod.POST)
    public QRCodeManagerVo saveQRCodeManager(@RequestBody QRCodeManagerVo QRCodeManagerVo){
        QRCodeManager QRCodeManager=new QRCodeManager();
        try {
            ReflectionUtils.copyProperties(QRCodeManagerVo, QRCodeManager, null);
            QRCodeManager=QRCodeManagerService.saveQRCodeManager(QRCodeManager);
            QRCodeManagerVo.setId(QRCodeManager.getId());
        } catch (Exception e) {
            logger.error("-------/eam/micro/qrCode/saveQRCodeManager-----", e);
        }
        return QRCodeManagerVo;
    }

    /**
     * findQRCodeManagerByID:根据ID查询二维码管理
     * @param id
     * @return MaintenanceMaintenancePlanVo 二维码管理VO
     */
    @RequestMapping(value = "/eam/micro/qrCode/findQRCodeManagerByID", method = RequestMethod.GET)
    public QRCodeManagerVo findQRCodeManagerByID(@RequestParam("id") String id){
        QRCodeManagerVo QRCodeManagerVo=new QRCodeManagerVo();
        try {
            QRCodeManager QRCodeManager=QRCodeManagerService.findQRCodeManagerByID(id);
            ReflectionUtils.copyProperties(QRCodeManager, QRCodeManagerVo, null);
        } catch (Exception e) {
            logger.error("-------/eam/micro/qrCode/findQRCodeManagerByID-----", e);
        }
        return QRCodeManagerVo;
    }

    /**
     * findQRCodeManagerByQRCodeNum:根据QRCodeNum查询二维码管理
     * @param QRCodeNum
     * @return MaintenanceMaintenancePlanVo 二维码管理VO
     */
    @RequestMapping(value = "/eam/micro/qrCode/findQRCodeManagerByQRCodeNum", method = RequestMethod.GET)
    public QRCodeManagerVo findQRCodeManagerByQRCodeNum(@RequestParam("QRCodeNum") String QRCodeNum){
        QRCodeManagerVo QRCodeManagerVo=QRCodeManagerService.findQRCodeManagerByQRCodeNum(QRCodeNum);
        return QRCodeManagerVo;
    }

    /**
     * findPageQRCodeManager: 分页查询二维码管理
     * @param QRCodeManagerForFilterVo 查询条件 {@link com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo}
     * @return PageInfo<QRCodeManagerVo> 二维码管理VO集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/qrCode/findPageQRCodeManager")
    public PageInfo<QRCodeManagerVo> findPageQRCodeManager(@RequestBody QRCodeManagerForFilterVo QRCodeManagerForFilterVo) {
        List<QRCodeManagerVo> result = QRCodeManagerService.findPageQRCodeManager(QRCodeManagerForFilterVo);
        return new PageInfo<QRCodeManagerVo>(result);
    }

    /**
     * deleteQRCodeManagerList:删除选中的二维码管理
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/qrCode/deleteQRCodeManagerList", method = RequestMethod.POST)
    public Boolean deleteQRCodeManagerList(@RequestBody List<String> ids) {
        return QRCodeManagerService.deleteQRCodeManagerList(ids);
    }

    /**
     * findQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值查询二维码管理
     * @param siteId        站点ID
     * @param applicationValue 应用程序值
     * @return QRCodeManager 二维码管理实体
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/findQRCodeManagerBySiteIdAndApplicationValue")
    public QRCodeManagerVo findQRCodeManagerBySiteIdAndApplicationValue(@RequestParam("siteId") String siteId,@RequestParam("applicationValue") String applicationValue) {
        QRCodeManagerVo QRCodeManagerVo=QRCodeManagerService.findQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue);
        return QRCodeManagerVo;
    }

    /**
     * findPageQRCodeManagerBySiteIdAndPrefixQRCode: 根据站点ID和应用程序值查询二维码管理
     * @param siteId        站点ID
     * @param prefixQRCode 二维码前缀编码
     * @return QRCodeManagerVo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/findPageQRCodeManagerBySiteIdAndPrefixQRCode")
    public QRCodeManagerVo findPageQRCodeManagerBySiteIdAndPrefixQRCode(@RequestParam("siteId") String siteId,@RequestParam("prefixQRCode") String prefixQRCode) {
        QRCodeManagerVo QRCodeManagerVo=QRCodeManagerService.findPageQRCodeManagerBySiteIdAndPrefixQRCode(siteId,prefixQRCode);
        return QRCodeManagerVo;
    }

    /**
     * updateQRCodeManagerBySiteIdAndApplicationValue: 根据站点ID和应用程序值修改二维码管理是否有数据更新
     * @param siteId        站点ID
     * @param applicationValue
     * @param dataUpdate
     * @return Boolean 更新是否成功是否成功
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/updateQRCodeManagerBySiteIdAndApplicationValue")
    public Boolean updateQRCodeManagerBySiteIdAndApplicationValue(@RequestParam("siteId") String siteId,@RequestParam("applicationValue") String applicationValue,@RequestParam("dataUpdate") Boolean dataUpdate) {
        return QRCodeManagerService.updateQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue,dataUpdate);
    }

    /**
     * generateQRCodeManagerSuffixQRCode: 根据站点和应用程序value生成当前后缀编码
     * @param siteId        站点ID
     * @param applicationValue 应用程序value
     * @return 生成的最新编码
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/qrCode/generateQRCodeManagerSuffixQRCode")
    public String generateQRCodeManagerSuffixQRCode(@RequestParam("siteId") String siteId,@RequestParam("applicationValue") String applicationValue) {
        return QRCodeManagerService.generateQRCodeManagerSuffixQRCode(siteId,applicationValue);
    }
}
