package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.HeadArchivesVo;
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
public interface HeadArchivesDao {
    List<HeadArchivesVo> findHeadArchivesList(Map<String, Object> filters);

    HeadArchivesVo findByArchivesNum(@Param("archivesNum") String archivesNum,@Param("orgId") String orgId, @Param("siteId") String siteId);
}
