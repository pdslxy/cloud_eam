package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.WarningRepairVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/10/12 下午4:27
 * @Description
 */
@Mapper
public interface WarningRepairDao {
    public WarningRepairVo findByTagNameAndWaringType(@Param("tagName") String tagName ,@Param("warningType") String warningType);
}
