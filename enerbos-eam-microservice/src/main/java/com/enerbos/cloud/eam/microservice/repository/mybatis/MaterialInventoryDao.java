package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
import com.enerbos.cloud.eam.vo.MaterialInventoryVo;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForList;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForStoreroomList;
import com.enerbos.cloud.eam.vo.MaterialItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface MaterialInventoryDao {
    /**
     * 查询物资发放明细
     *
     * @param filters 查询条件
     * @return 物资库存列表实体
     */
    List<MaterialInventoryVoForList> findInventorys(Map<String, Object> filters);

    /**
     * 查询物资发放明细
     *
     * @param filters 查询条件
     * @param itemid  物资台账id
     * @return 物资库存列表实体
     */
    List<MaterialItemVo> findItemsByinventory(Map<String, Object> filters, String itemid);


    /**
     * 根据库房id 查询物资库存
     *
     * @param id     库房id
     * @param siteId
     * @param orgId
     * @return 结果列表
     */
    List<MaterialInventoryVoForStoreroomList> findInventorysByStoreroomId(@Param("id") String id, @Param("siteId") String siteId, @Param("orgId") String orgId);

    /**
     * 查询未盘点的物资库存
     *
     * @param filters 查询条件
     * @return 查询结果集
     */
    List<MaterialInventoryVoForList> findInventorysNotInCheck(Map<String, Object> filters);

    /**
     * 根据物资id查询是否已经入库到库存中
     *
     * @param id
     * @return
     */
    List<MaterialInventoryVoForList> isItemInUse(@Param("id") String id);

    /**
     * 根据库房查询
     *
     * @param id
     * @return
     */
    List<MaterialInventoryVo> findStoreRoomInInventroy(String id);

    /**
     * 根据　物资id 和库房ｉｄ查询实体
     *
     * @param itemid      　物资ｉｄ
     * @param storeroomId 　库房Id
     * @return
     */
    MaterialInventory findInventorysByItemIdAndStoreroomId(@Param("itemid") String itemid, @Param("storeroomId") String storeroomId);
}
