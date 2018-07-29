package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.MaterialCheckDetailVoForList;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForInventoryList;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForList;
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
 * @Description 物资盘点Dao层接口
 */
@Mapper
public interface MaterialCheckDao {

    /**
     * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param filter 查询条件合集
     * @return 返回列表结果集
     */
    List<MaterialCheckVoForList> findMaterialCheck(Map<String, Object> filter);

    /**
     * 查找物资盘点明细列表
     *
     * @param id 物资盘点id
     * @return 列表结果集
     */
    List<MaterialCheckDetailVoForList> findMaterialCheckDetail(String id);
    /**
     * 根据物资库存id查询 盘点记录
     *
     * @param id       物资库存id
     * @return 查询结果列表
     */
    List<MaterialCheckVoForInventoryList> findMaterialCheckByInvtoryId(String id);
}
