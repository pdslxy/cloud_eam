package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.PatrolStandContentVoForList;
import com.enerbos.cloud.eam.vo.PatrolStandVoForList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PatrolStandDao {
    /**
     * 分页查询巡检标准
     *
     * @param filter 巡检标准查询条件
     * @return 分页列表
     */
    List<PatrolStandVoForList> findPage(Map<String, Object> filter);

    /**
	 * 根据巡检标准id查找巡检标准内容
	 * 
	 * @param id
	 *            巡检标准id
	 * @return 查询结果
	 */
	List<PatrolStandContentVoForList> findPatrolStandContent(String id);
}
