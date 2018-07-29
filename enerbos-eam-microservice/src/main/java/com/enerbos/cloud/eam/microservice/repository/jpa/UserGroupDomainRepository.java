package com.enerbos.cloud.eam.microservice.repository.jpa;/**
 * Created by enerbos on 2017/8/28.
 */

import com.enerbos.cloud.eam.microservice.domain.UserGroupDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/28 10:01
 * @Description: 用户组域值关联repository
 */
public interface UserGroupDomainRepository extends JpaRepository<UserGroupDomain, String>, JpaSpecificationExecutor<UserGroupDomain> {
}
