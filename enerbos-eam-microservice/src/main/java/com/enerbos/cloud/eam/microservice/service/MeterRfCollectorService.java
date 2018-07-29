package com.enerbos.cloud.eam.microservice.service;


import com.enerbos.cloud.eam.microservice.domain.MeterRfCollector;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/15.
 */

public interface MeterRfCollectorService {
    
    void collect(String id, String eamProdId, String personId,String meterId);

    void cancelCollect(String id, String eamProdId, String personId);

    MeterRfCollector findcollect(String id, String eamProdId);
    
    List<MeterRfCollector> findByPersonId(String personId);
}
