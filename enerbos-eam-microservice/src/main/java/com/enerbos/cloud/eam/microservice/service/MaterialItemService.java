package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaterialItem;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.vo.MaterialItemVo;
import com.enerbos.cloud.eam.vo.MaterialItemVoForAssertList;
import com.enerbos.cloud.eam.vo.MaterialItemVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialItemVoForList;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
 * @Description 物资接口
 */
public interface MaterialItemService {

    /**
     * 按照参数查询物资并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialItemVoForFilter 参数
     * @return 封装的实体类集合
     */

    public abstract PageInfo<MaterialItemVoForList> findItems(
            MaterialItemVoForFilter materialItemVoForFilter);

    /**
     * 更具库房查询物资分页列表
     *
     * @param inventoryId 库房id
     * @param pageNum     页数
     * @param pageSize    每页显示条数
     * @return 封装的物资实体集合
     */

    public abstract PageInfo<MaterialItemVo> findItemsByinventory(String inventoryId,
                                                                  Integer pageNum, Integer pageSize);

    /**
     * 新建物资库存
     *
     * @param itemVo 新建的实体
     * @return 返回添加的实体
     */
    public abstract MaterialItem saveItem(MaterialItem item);

    /**
     * 修改物资记录
     *
     * @param item 修改的物资实体
     * @return 修改后的物资实体
     */
    public abstract MaterialItem updateItem(MaterialItem item);

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     */
    public abstract void deleteItem(String ids[]);

    /**
     * 查询物资详细信息
     *
     * @param id 物资id
     * @return 返回物资实体
     */
    public abstract MaterialItem findItemDetail(String id);

    /**
     * 查询不在itemNum集合中的物资
     *
     * @param itemNums itenNums 集合
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 查询集合
     */
    PageInfo<MaterialItemVoForList> findItemsNotInResevies(String[] itemNums, Integer pageSize, Integer pageNum, String siteId, String orgId);

    /**
     * 根据设备id查询物资
     *
     * @param itemIds  物资编号
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 数据
     */
    PageInfo<MaterialItemVoForAssertList> findItemByAssertId(String[] itemIds, Integer pageNum, Integer pageSize);

    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param ids
     * @param qrCodeNum
     * @param siteId
     * @return
     */
    List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(String[] ids, String qrCodeNum, String siteId);


    /**
     * 设置是否更新状态
     *
     * @param id
     * @param siteId
     * @param b
     * @return
     */
    boolean updateIsupdatedata(String id, String siteId, boolean b);

    /**
     * 取消收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param type      类型
     * @param personId  人员id
     */
    void collect(String id, String eamProdId, String type, String personId);

    /**
     * 取消收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param type      类型
     * @param personId  人员id
     */
    void cancelCollect(String id, String eamProdId, String type, String personId);

    MaterialRfCollector findcollect(String id, String item, String eamProdId);
}