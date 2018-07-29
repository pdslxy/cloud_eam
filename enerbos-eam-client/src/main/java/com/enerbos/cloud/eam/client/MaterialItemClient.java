package com.enerbos.cloud.eam.client;


import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.MaterialItemVo;
import com.enerbos.cloud.eam.vo.MaterialItemVoForAssertList;
import com.enerbos.cloud.eam.vo.MaterialItemVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialItemVoForList;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
 * @Description 物资台账client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = ItemClientFullBack.class)
public interface MaterialItemClient {

    /**
     * 按照参数查询物资并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialItemVoForFilter 查询条件实体 {@link com.enerbos.cloud.eam.vo.MaterialItemVoForFilter}
     * @return 查询的物资台账实体集合
     */
    @RequestMapping(value = "/eam/micro/item/findItems", method = RequestMethod.POST)
    public abstract EnerbosPage<MaterialItemVoForList> findItems(@RequestBody MaterialItemVoForFilter materialItemVoForFilter);

    /**
     * 新建物资库存
     *
     * @param userName 用户名称
     * @param item     新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/item/saveItem", method = RequestMethod.POST)
    public abstract MaterialItemVo saveItem(@RequestBody MaterialItemVo item, @RequestParam(value = "userName", required = false) String userName);

    /**
     * 修改物资记录
     *
     * @param item 修改的物资实体
     * @return 修改后的物资实体
     */
    @RequestMapping(value = "/eam/micro/item/updateItem", method = RequestMethod.POST)
    public abstract MaterialItemVo updateItem(@RequestBody MaterialItemVo item);

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     * @return 是否成功
     */
    @RequestMapping(value = "/eam/micro/item/deleteItem", method = RequestMethod.POST)
    public abstract String deleteItem(@RequestParam(value = "ids", required = true) String[] ids);

    /**
     * 查询物台账资详细信息
     *
     * @param id 物资id
     * @return 返回物资实体
     */
    @RequestMapping(value = "/eam/micro/item/findItemDetail", method = RequestMethod.GET)
    public abstract MaterialItemVo findItemDetail(@RequestParam("id") String id);

    /**
     * 更改物资台账状态
     *
     * @param id     物资台账id
     * @param status 物资台账状态（活动、不活动）
     * @return 返回物资实体
     */
    @RequestMapping(value = "/eam/micro/item/updateItemStatus", method = RequestMethod.POST)
    public abstract boolean updateItemStatus(
            @RequestParam("id") String[] id,
            @RequestParam("status") String status);


    /**
     * 查询不在itemNum集合中的物资
     *
     * @param itemNums itenNums 集合
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 查询集合
     */
    @RequestMapping(value = "/eam/micro/item/findItemsNotInResevies", method = RequestMethod.GET)
    EnerbosPage<MaterialItemVoForList> findItemsNotInResevies(@RequestParam("itemNums") String[] itemNums, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);

    /**
     * 根据设备id查询物资
     *
     * @param itemIds  物资编号
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 数据
     */
    @RequestMapping(value = "/eam/micro/item/findItemByAssertId", method = RequestMethod.GET)
    EnerbosPage<MaterialItemVoForAssertList> findItemByAssertId(
            @RequestParam(value = "itemIds", required = false) String[] itemIds,
            @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum);

    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/item/findByIdAndQrCodeNumAndSiteId", method = RequestMethod.POST)
    List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(@RequestParam(value = "ids", required = false) String[] ids, @RequestParam(value = "qrCodeNum", required = false) String qrCodeNum, @RequestParam(value = "siteId", required = false) String siteId);

    /**
     * 设置是否更新状态
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/item/updateIsupdatedata", method = RequestMethod.POST)
    boolean updateIsupdatedata(@RequestParam(value = "id", required = false) String id, @RequestParam("siteId") String siteId, @RequestParam("b") boolean b);

    /**
     * 添加收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param type      类型
     * @param personId  人员id
     */
    @RequestMapping(value = "/eam/micro/material/collect", method = RequestMethod.POST)
    void collect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    /**
     * 取消收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param type      类型
     * @param personId  人员id
     */
    @RequestMapping(value = "/eam/micro/material/cancelCollect", method = RequestMethod.POST)
    void cancelCollect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId);
}

@Component
class ItemClientFullBack implements FallbackFactory<MaterialItemClient> {


    @Override
    public MaterialItemClient create(Throwable cus) {

        return new MaterialItemClient() {


            @Override
            public EnerbosPage<MaterialItemVoForList> findItemsNotInResevies(String[] itemNums, Integer pageSize, Integer pageNum, String siteId, String orgId) {
                return null;
            }

            @Override
            public EnerbosPage<MaterialItemVoForAssertList> findItemByAssertId(String[] itemIds, Integer pageSize, Integer pageNum) {
                return null;
            }

            @Override
            public List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(String[] ids, String qrCodeNum, String siteId) {
                return null;
            }

            @Override
            public boolean updateIsupdatedata(String id, String siteId, boolean b) {
                return false;
            }

            @Override
            public void collect(String id, String eamProdId, String type, String personId) {

            }

            @Override
            public void cancelCollect(String id, String eamProdId, String type, String personId) {

            }

            @Override
            public MaterialItemVo updateItem(MaterialItemVo item) {
                return null;
            }

            @Override
            public MaterialItemVo saveItem(MaterialItemVo item, String userName) {

                return null;
            }

            @Override
            public EnerbosPage<MaterialItemVoForList> findItems(MaterialItemVoForFilter materialItemVoForFilter) {

                return null;
            }

            @Override
            public MaterialItemVo findItemDetail(String id) {

                return null;
            }

            @Override
            public boolean updateItemStatus(String[] id, String status) {
                return false;
            }

            @Override
            public String deleteItem(String[] ids) {
                return null;
            }
        };
    }

}