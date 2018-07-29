package com.enerbos.cloud.eam.microservice.service;


import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForList;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForStoreroomList;
import com.github.pagehelper.PageInfo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年3月31日 下午4:32:36
 * @Description 物资库存服务接口
 */
public interface MaterialInventoryService {

    /**
     * 按照参数查询物资并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialInventoryVoForFilter 查询条件实体{@link com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter}
     * @return 封装的实体类集合
     */

    public abstract PageInfo<MaterialInventoryVoForList> findInventorys(
            MaterialInventoryVoForFilter materialInventoryVoForFilter);

    /**
     * 新建物资库存
     *
     * @param materialInventory 新建的实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialInventory}
     * @return 返回添加的实体
     */
    public abstract MaterialInventory saveInventory(MaterialInventory materialInventory);

    /**
     * 修改物资记录
     *
     * @param materialInventory 修改的物资实体{@link com.enerbos.cloud.eam.microservice.domain.MaterialInventory}
     * @return 修改后的物资实体
     */
    public abstract MaterialInventory updateInventory(
            MaterialInventory materialInventory);

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     */
    public abstract void deleteInventory(String ids[]);

    /**
     * 查询物资详细信息
     *
     * @param id 物资id
     * @return 返回物资实体
     */
    public abstract MaterialInventory findInventoryDetail(String id);

    /**
     * 修改物资状态
     *
     * @param materialInventory 待修改的物资实体类 {@link com.enerbos.cloud.eam.microservice.domain.MaterialInventory}
     * @return 修改后返回的实体类
     */
    public abstract MaterialInventory updateInventoryStatus(
            MaterialInventory materialInventory);

    /**
     * 查询物资在库房中的信息
     *
     * @param id       物资台账id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 返回封装实体
     */

    public abstract PageInfo<MaterialInventoryVoForList> findInventorysByItemId(
            String id, Integer pageNum, Integer pageSize, String siteId, String orgId);

    /**
     * 查询去除item集合的库存信息
     *
     * @param ids      itemid的集合
     * @param pageNum  页数
     * @param pageSize 条数
     * @return 返回结果集
     */
    public abstract PageInfo<MaterialInventoryVoForList> findInventorysNotInItems(
            String[] ids, Integer pageNum, Integer pageSize);

    /**
     * 更改当前余量
     *
     * @param itemId   物资台账id
     * @param quantity 数量
     */
    void updateInventoryCurrentBalanceByItemId(String itemId, long quantity);

    /**
     * 更改当前余量
     *
     * @param inventoryId 物资库存id
     * @param quantity    数量
     */
    void updateInventoryCurrentBalanceByIntoryId(String inventoryId, long quantity);

    /**
     * 根据库房id 查询物资库存
     *
     * @param id       库房id
     * @param pageNum  页数
     * @param pageSize 每页显示条数
     * @return 结果列表
     */
    PageInfo<MaterialInventoryVoForStoreroomList> findInventorysByStoreroomId(String id, Integer pageNum, Integer pageSize,String siteId,String orgId);

    /**
     * 物资库存重订购
     *
     * @param inventory       物资库存实体   {@link com.enerbos.cloud.eam.microservice.domain.MaterialInventory}
     * @param goodsReceiveNum 接收单编码
     */
    public void materialInventoryReorder(MaterialInventory inventory, String goodsReceiveNum);

    /**
     * 查询未盘点的物资库存
     *
     * @param ids         库存IDS
     * @param storeroomid 库房id
     * @param pageNum     页数
     * @param pageSize    条数
     * @return 查询结果
     */
    PageInfo<MaterialInventoryVoForList> findInventorysNotInCheck(String[] ids, String storeroomid, Integer pageNum, Integer pageSize, String siteId, String orgId);

    /**
     * 查询不在itemNums中的物资库存
     *
     * @param itemNums 物资台账编码  数组
     * @param pageNum  页数
     * @param pageSize 条数
     * @return 结果集
     */
    PageInfo<MaterialInventoryVoForList> findInventorysNotInItemNum(String storeroomId, String[] itemNums, Integer pageNum, Integer pageSize, String siteId, String orgId);

    /**
     * 根据　物资id 和库房ｉｄ查询实体
     *
     * @param itemid      　物资ｉｄ
     * @param storeroomId 　库房Id
     * @return
     */
    MaterialInventory findInventorysByItemIdAndStoreroomId(String itemid, String storeroomId);
}