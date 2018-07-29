package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.WarningRepairVo;

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
public interface WarningRepairService {
    /**
     * 根据点位名称和 报警类型查找 关联工单的记录
     * @param tagName
     * @param warningType
     * @return
     */
    WarningRepairVo findByTagNameAndWaringType(String tagName, String warningType);

    /**
     * 添加一条记录
     * @param warningRepairVo
     * @return
     */
    WarningRepairVo add(WarningRepairVo warningRepairVo) throws Exception;
}
