package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.RepairEvaluate;
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
 * @date 2017/9/19.
 */
@Mapper
public interface RepairEvaluateDao {

    /**
     * 查询项目列表
     * @param repairOrderId 查询条件
     * @return 返回查询实体
     */
    RepairEvaluate findRepairEvalueByOrderId(@Param("repairOrderId") String repairOrderId);

    /**
     * 报修小程序首页统计已完成，为分派，处理中，已评价的数据
     * @param map 站点id和人员id
     * @return 查询结果
     */
    Map<String,Object> findCountRepairAndEvaluate(Map<String,String> map);
}
