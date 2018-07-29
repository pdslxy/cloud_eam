package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.WarningRepair;
import com.enerbos.cloud.eam.microservice.repository.jpa.WarningRepairRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.WarningRepairDao;
import com.enerbos.cloud.eam.vo.WarningRepairVo;
import com.enerbos.cloud.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enerbos.cloud.eam.microservice.service.WarningRepairService;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/10/12 下午4:42
 * @Description
 */
@Service
public class WarningRepairServiceImpl implements WarningRepairService {


    @Autowired
    private WarningRepairDao warningRepairDao ;

    @Autowired
    private WarningRepairRepository warningRepairRepository ;
    /**
     * 根据点位名称和 报警类型查找 关联工单的记录
     *
     * @param tagName
     * @param warningType
     * @return
     */
    @Override
    public WarningRepairVo findByTagNameAndWaringType(String tagName, String warningType) {
        return warningRepairDao.findByTagNameAndWaringType(tagName,warningType);
    }

    @Override
    public WarningRepairVo add(WarningRepairVo warningRepairVo) throws  Exception{
        WarningRepair warningRepair = new WarningRepair() ;
        ReflectionUtils.copyProperties(warningRepairVo,warningRepair,null);
        warningRepairRepository.save(warningRepair);
        warningRepairVo.setId(warningRepair.getId());
        return warningRepairVo;
    }
}
