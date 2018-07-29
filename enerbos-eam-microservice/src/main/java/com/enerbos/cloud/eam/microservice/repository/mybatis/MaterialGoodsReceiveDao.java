package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForList;
import org.apache.ibatis.annotations.Mapper;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月14日 下午8:34:13
 * @Description 物资接收Dao层接口
 */
@Mapper
public interface MaterialGoodsReceiveDao {

    /**
     * 查询物资接收分页列表
     *
     * @param filters 查询条件
     * @return 查询结果集
     */
    List<MaterialGoodsReceiveVoForList> findGoodsReceives(
            Map<String, Object> filters);

}
