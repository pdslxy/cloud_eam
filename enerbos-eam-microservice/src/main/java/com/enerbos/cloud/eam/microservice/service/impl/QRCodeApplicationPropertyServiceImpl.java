package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.QRCodeApplicationProperty;
import com.enerbos.cloud.eam.microservice.repository.jpa.QRCodeApplicationPropertyRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.QRCodeApplicationPropertyDao;
import com.enerbos.cloud.eam.microservice.service.QRCodeApplicationPropertyService;
import com.enerbos.cloud.eam.vo.QRCodeApplicationPropertyVo;
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
 * @Description 二维码应用程序属性查询接口
 */
@Service
public class QRCodeApplicationPropertyServiceImpl implements QRCodeApplicationPropertyService {
    private Logger logger = LoggerFactory.getLogger(QRCodeApplicationPropertyServiceImpl.class);

    @Autowired
    private QRCodeApplicationPropertyDao QRCodeApplicationPropertyDao;

    @Autowired
    private QRCodeApplicationPropertyRepository QRCodeApplicationPropertyRepository;

    @Override
    public List<QRCodeApplicationProperty> saveApplicationProperty(List<QRCodeApplicationProperty> QRCodeApplicationPropertyList) {
        return QRCodeApplicationPropertyRepository.save(QRCodeApplicationPropertyList);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<QRCodeApplicationPropertyVo> findApplicationPropertyByQRCodeManagerId(String QRCodeManagerId) {
        return QRCodeApplicationPropertyDao.findApplicationPropertyByQRCodeManagerId(QRCodeManagerId);
    }

    @Override
    public Boolean deleteApplicationPropertyByIds(List<String> ids) {
        for (String id : ids) {
            QRCodeApplicationPropertyRepository.delete(id);
        }
        return true;
    }
}
