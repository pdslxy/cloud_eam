package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.repository.mybatis.QRCodeApplicationDao;
import com.enerbos.cloud.eam.microservice.service.QRCodeApplicationService;
import com.enerbos.cloud.eam.vo.QRCodeApplicationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class QRCodeApplicationServiceImpl implements QRCodeApplicationService{
    private Logger logger = LoggerFactory.getLogger(QRCodeApplicationServiceImpl.class);

    @Autowired
    private QRCodeApplicationDao QRCodeApplicationDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public QRCodeApplicationVo findQRCodeApplicationByID(String id) {
        return QRCodeApplicationDao.findQRCodeApplicationByID(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<QRCodeApplicationVo> findAllQRCodeApplication() {
        return QRCodeApplicationDao.findAllQRCodeApplication();
    }
}
