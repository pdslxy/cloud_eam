package com.enerbos.cloud.eam.microservice.repository.mybatis;


import com.enerbos.cloud.eam.microservice.domain.HeadArchivesType;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVo;
import org.apache.ibatis.annotations.Mapper;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForList;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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
public interface HeadArchivesTypeDao {
    List<HeadArchivesTypeVo> findHeadArchivesTypeList(Map<String, Object> filters);

    /**
     * 查询所有类型内容
     *
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    List<HeadArchivesTypeVoForList> findArchivesTypeAll(@Param("siteId") String siteId, @Param("orgId") String orgId);

    List<HeadArchivesType> findHeadArchivesTypeListByParentId(Map map);

    HeadArchivesTypeVo findByName(@RequestParam("name")  String name, @Param("siteId") String siteId, @Param("orgId") String orgId);
}
