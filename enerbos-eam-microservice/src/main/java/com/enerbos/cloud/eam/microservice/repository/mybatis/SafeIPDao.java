package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.SafeIP;
import com.enerbos.cloud.eam.vo.RepairOrderFlowVo;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/7/11.
 */
@Mapper
public interface SafeIPDao {
    /**
     * 查询ip列表
     *
     * @param filters 查询条件
     * @return 返回查询实体
     */
    List<SafeIPVoForList>  findIPs(SafeIPVoForFilter filters);

    /**
     *  根据ID查询IP
     * @param id IP的ID
     * @return IP的实体
     */
    SafeIP findOne(String id);

    /**
     *  根据IP查询
     * @param ip IP
     * @param orgId 组织
     * @param siteId 站点
     * @param prod 产品
     * @return IP实体
     */
    SafeIP findOneByIp(@Param("ip")String ip, @Param("orgId")String orgId, @Param("siteId")String siteId, @Param("prod")String prod);
}
