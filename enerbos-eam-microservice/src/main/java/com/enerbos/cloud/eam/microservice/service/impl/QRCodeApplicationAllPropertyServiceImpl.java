package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.repository.mybatis.QRCodeApplicationAllPropertyDao;
import com.enerbos.cloud.eam.microservice.service.QRCodeApplicationAllPropertyService;
import com.enerbos.cloud.eam.vo.QRCodeApplicationAllPropertyVo;
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
 * @Description 二维码应用程序所有属性查询接口
 */
@Service
public class QRCodeApplicationAllPropertyServiceImpl implements QRCodeApplicationAllPropertyService{
    private Logger logger = LoggerFactory.getLogger(QRCodeApplicationAllPropertyServiceImpl.class);

    @Autowired
    private QRCodeApplicationAllPropertyDao QRCodeApplicationAllPropertyDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<QRCodeApplicationAllPropertyVo> findAllApplicationAllPropertyNotIn(String applicationId,List<String> ids) {
        return QRCodeApplicationAllPropertyDao.findAllApplicationAllPropertyNotIn(applicationId,ids);
    }
}
