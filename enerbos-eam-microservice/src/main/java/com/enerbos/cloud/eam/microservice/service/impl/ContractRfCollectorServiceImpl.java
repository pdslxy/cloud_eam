package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.ContractRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.ContractRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.service.ContractRfCollectorService;
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
public class ContractRfCollectorServiceImpl implements ContractRfCollectorService {

    @Autowired
    private ContractRfCollectorRepository contractRfCollectorRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collect(String id, String productId, String type, String personId) throws EnerbosException {
        ContractRfCollector pc = new ContractRfCollector(id, type, personId, productId);
        pc.setCreateUser(personId);
        contractRfCollectorRepository.save(pc);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollectByCollectIdAndType(String id, String productId, String type, String personId) throws EnerbosException {
        contractRfCollectorRepository.deleteByCollectIdAndProductAndTypeAndPersonId(id, productId, type, personId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean findByCollectIdAndTypeAndProductAndPerson(String id, String type, String productId, String personId) {
        ContractRfCollector contractRfCollector = contractRfCollectorRepository.findByCollectIdAndTypeAndProductAndPersonId(id, type, productId, personId);
        Predicate<ContractRfCollector> pred = p -> p != null;
        return pred.test(contractRfCollector);
    }
}
