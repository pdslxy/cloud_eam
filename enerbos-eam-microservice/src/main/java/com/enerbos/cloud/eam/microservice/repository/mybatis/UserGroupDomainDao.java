package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.SafeIP;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import com.enerbos.cloud.eam.vo.UserGroupDomainFilterVo;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/28 10:01
 * @Description: 用户组域值关联
 */
@Mapper
public interface UserGroupDomainDao {
    /**
     * 查询列表
     *
     * @param filter 查询条件
     * @return 返回查询实体
     */
    List<UserGroupDomainVo>  findUserGroupDimain(Map<String, Object> filter);

    /**
     *  根据ID查询
     * @param id 的ID
     * @return 实体
     */
    UserGroupDomainVo findOne(String id);


    UserGroupDomainVo findUserGroupDomainByDomainValueAndDomainNum( Map<String,Object> filters);

}
