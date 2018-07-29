package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
 * @Description Dao层接口
 */
@Mapper
public interface MaterialStoreRoomDao {

    /**
     * 查询库房
     *
     * @param filters 查询条件
     * @return 库房列表实体
     */
    List<MaterialStoreRoomVoForList> findStoreRooms(Map<String, Object> filters);

    /**
     * 根据itemNum查询组织站点下可用的库房详细信息
     *
     * @param filter 查询条件集合
     * @return 库房列表
     */
    List<MaterialStoreRoomVoForList> findUsableStoreRoom(Map<String, Object> filter);


    /**
     * 查询站点下是否有默认库房
     *
     * @return 返回执行码及数据
     */
    List findhasdefault(Map<String, Object> filter);
}
