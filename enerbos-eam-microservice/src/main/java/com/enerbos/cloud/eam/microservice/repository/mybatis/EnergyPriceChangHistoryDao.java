package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author 能源价格历史查询记录
 * @version 1.0
 * @date 2017/7/11.
 */
@Mapper
public interface EnergyPriceChangHistoryDao {

    //分页查询历史
    List<EnergyPriceChangHistoryVo> findEnergyPriceChangHistory(Map<String, Object> filters);

    //根据能源价格ID查询最近的一条记录
    EnergyPriceChangHistoryVo findMaxCreateDatePriceChangHistoryById(String Id);

}
