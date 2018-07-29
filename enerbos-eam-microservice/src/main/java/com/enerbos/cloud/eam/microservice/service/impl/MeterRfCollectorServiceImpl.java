package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MeterRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.MeterRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.service.MeterRfCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/15.
 */
@Service
public class MeterRfCollectorServiceImpl implements MeterRfCollectorService {

    @Autowired
    private MeterRfCollectorRepository meterRfCollectorRepository;
    
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collect(String id, String eamProdId, String personId,String meterId) {

        MeterRfCollector pc = new MeterRfCollector();
        pc.setAssetId(id);
        pc.setPersonId(personId);
        pc.setProduct(eamProdId);
        pc.setCreateUser(personId);
        pc.setMeterId(meterId);
        meterRfCollectorRepository.save(pc);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollect(String id, String eamProdId, String personId) {
        meterRfCollectorRepository.deleteByAssetIdAndProductAndPersonId(id, eamProdId, personId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MeterRfCollector findcollect(String id, String eamProdId) {
        return meterRfCollectorRepository.findByAssetIdAndProduct(id, eamProdId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<MeterRfCollector> findByPersonId(String personId){
        return  meterRfCollectorRepository.findByPersonId(personId);
    }
}

