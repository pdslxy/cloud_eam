package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.RepairEvaluate;
import com.enerbos.cloud.eam.microservice.repository.jpa.RepairEvaluateRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.RepairEvaluateDao;
import com.enerbos.cloud.eam.microservice.service.RepairEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/19.
 */
@Service
public class RepairEvaluateServiceImpl implements RepairEvaluateService {
    
    @Autowired
    private RepairEvaluateRepository repairEvaluateRepository;
    
    @Autowired
    private RepairEvaluateDao repairEvaluateDao;
    
    //@Autowired
    //private PersonSiteDao personSiteDao;

    @Override
    public RepairEvaluate saveRepairEvaluate(RepairEvaluate repairEvaluate) {
        return repairEvaluateRepository.save(repairEvaluate);
    }

    @Override
    public RepairEvaluate findRepairEvalueByOrderId(String repairOrderId){
        return repairEvaluateDao.findRepairEvalueByOrderId(repairOrderId);
    }
    @Override
    public Map<String,Object> findCountRepairAndEvaluate(Map<String,String> map){
        return repairEvaluateDao.findCountRepairAndEvaluate(map);
    }
}
