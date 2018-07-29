package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.PatrolRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.service.PatrolRfCollectorService;
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
public class PatrolRfCollectorServiceImpl implements PatrolRfCollectorService {

    @Autowired
    private PatrolRfCollectorRepository patrolRfCollectorRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collect(String id, String productId, String type, String personId) throws EnerbosException {
        PatrolRfCollector patrolRfCollector = patrolRfCollectorRepository.findByCollectIdAndTypeAndProductAndPersonId(id, type, productId, personId);
        if(patrolRfCollector==null){
            PatrolRfCollector pc = new PatrolRfCollector(id, type, personId, productId);
            pc.setCreateUser(personId);
            patrolRfCollectorRepository.save(pc);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollectByCollectIdAndType(String id, String productId, String type, String personId) throws EnerbosException {
        patrolRfCollectorRepository.deleteByCollectIdAndProductAndTypeAndPersonId(id, productId, type, personId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean findByCollectIdAndTypeAndProductAndPerson(String id, String type, String productId, String personId) {
        PatrolRfCollector patrolRfCollector = patrolRfCollectorRepository.findByCollectIdAndTypeAndProductAndPersonId(id, type, productId, personId);
        Predicate<PatrolRfCollector> pred = p -> p != null;
        return pred.test(patrolRfCollector);
    }
}
