package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.DaliyRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.service.DailyRfCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/10/17
 * @Description
 */
@Service
public class DailyRfCollectorServiceImpl implements DailyRfCollectorService {

    @Autowired
    private DaliyCopyMeterRfCollectorRepository daliyCopyMeterRfCollectorRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collect(String id, String productId, String type, String personId) throws EnerbosException {
        DaliyRfCollector daliyRfCollector = daliyCopyMeterRfCollectorRepository.findByCollectIdAndTypeAndProductAndPersonId(id, type, productId, personId);
        if(daliyRfCollector==null){
            DaliyRfCollector pc = new DaliyRfCollector(id, type, personId, productId);
            pc.setCreateUser(personId);
            daliyCopyMeterRfCollectorRepository.save(pc);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollectByCollectIdAndType(String id, String productId, String type, String personId) throws EnerbosException {
        daliyCopyMeterRfCollectorRepository.deleteByCollectIdAndProductAndTypeAndPersonId(id, productId, type, personId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean findByCollectIdAndTypeAndProductAndPerson(String id, String type, String productId, String personId) {
        DaliyRfCollector daliyRfCollector = daliyCopyMeterRfCollectorRepository.findByCollectIdAndTypeAndProductAndPersonId(id, type, productId, personId);
        Predicate<DaliyRfCollector> pred = p -> p != null;
        return pred.test(daliyRfCollector);
    }
}
