package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrder;
import com.enerbos.cloud.eam.microservice.domain.QRCodeManager;
import com.enerbos.cloud.eam.microservice.repository.jpa.QRCodeManagerRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.QRCodeManagerDao;
import com.enerbos.cloud.eam.microservice.service.QRCodeManagerService;
import com.enerbos.cloud.eam.vo.QRCodeManagerForFilterVo;
import com.enerbos.cloud.eam.vo.QRCodeManagerVo;
import com.enerbos.cloud.util.ParamConstans;
import com.enerbos.cloud.util.ParamUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
@Service
public class QRCodeManagerServiceImpl implements QRCodeManagerService{
    private Logger logger = LoggerFactory.getLogger(QRCodeManagerServiceImpl.class);

    @Autowired
    private QRCodeManagerDao QRCodeManagerDao;

    @Autowired
    private QRCodeManagerRepository QRCodeManagerRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public QRCodeManager saveQRCodeManager(QRCodeManager QRCodeManager) {
        QRCodeManagerVo QRCodeManagerVo=QRCodeManagerDao.findQRCodeManagerBySiteIdAndApplicationId(QRCodeManager.getSiteId(),QRCodeManager.getApplicationId());
        if (QRCodeManagerVo != null&&!QRCodeManagerVo.getId().equals(QRCodeManager.getId())&&QRCodeManager.getStatus()) {
            throw new EnerbosException("","同一个站点一个应用程序只能有一个二维码管理是活动状态");
        }
        return QRCodeManagerRepository.save(QRCodeManager);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public QRCodeManager findQRCodeManagerByID(String id) {
        return QRCodeManagerDao.findQRCodeManagerByID(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public QRCodeManagerVo findQRCodeManagerByQRCodeNum(String QRCodeNum) {
        return QRCodeManagerDao.findQRCodeManagerByQRCodeNum(QRCodeNum);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<QRCodeManagerVo> findPageQRCodeManager(QRCodeManagerForFilterVo QRCodeManagerForFilterVo) {
        PageHelper.startPage(QRCodeManagerForFilterVo.getPageNum(), QRCodeManagerForFilterVo.getPageSize());
        if (StringUtils.isNotBlank(QRCodeManagerForFilterVo.getSorts())) {
            String sorts = QRCodeManagerForFilterVo.getSorts();
            ParamUtils.convertSortParam(new QRCodeManagerVo(), sorts).get(ParamConstans.SUCCESS).toString();
        }
        return QRCodeManagerDao.findPageQRCodeManager(QRCodeManagerForFilterVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Boolean deleteQRCodeManagerList(List<String> ids) {
        for (String id : ids) {
            QRCodeManagerRepository.delete(id);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public QRCodeManagerVo findQRCodeManagerBySiteIdAndApplicationValue(String siteId, String applicationValue) {
        return QRCodeManagerDao.findPageQRCodeManagerBySiteIdAndPrefixQRCodeAndApplicationValue(siteId,null,applicationValue);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Boolean updateQRCodeManagerBySiteIdAndApplicationValue(String siteId, String applicationValue,Boolean dataUpdate) {
        QRCodeManager QRCodeManager=QRCodeManagerDao.findPageQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue);
        QRCodeManager.setDataUpdate(dataUpdate);
        if (!dataUpdate){
            QRCodeManager.setLastGenerateDate(new Date());
        }
        QRCodeManager=QRCodeManagerRepository.save(QRCodeManager);
        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String generateQRCodeManagerSuffixQRCode(String siteId, String applicationValue) {
        QRCodeManager QRCodeManager=QRCodeManagerDao.findPageQRCodeManagerBySiteIdAndApplicationValue(siteId,applicationValue);
//        String currentSuffixQRCode=QRCodeManager.getCurrentSuffixQRCode();
//        if (currentSuffixQRCode == null||currentSuffixQRCode.length()==0) {
//            currentSuffixQRCode="1";
//        }
//        BigInteger num=new BigInteger(currentSuffixQRCode);
//        num.add(BigInteger.ONE);
//        QRCodeManager.setCurrentSuffixQRCode(num.toString());
        QRCodeManager=QRCodeManagerRepository.save(QRCodeManager);
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public QRCodeManagerVo findPageQRCodeManagerBySiteIdAndPrefixQRCode(String siteId, String prefixQRCode) {
        return QRCodeManagerDao.findPageQRCodeManagerBySiteIdAndPrefixQRCodeAndApplicationValue(siteId,prefixQRCode,null);
    }
}
