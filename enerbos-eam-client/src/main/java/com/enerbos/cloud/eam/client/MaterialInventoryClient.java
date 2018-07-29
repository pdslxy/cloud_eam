package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.MaterialInventoryVo;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForList;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForStoreroomList;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = InventoryClientFallback.class)
public interface MaterialInventoryClient {

    /**
     * 按照参数查询物资并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialInventoryVoForFilter 查询条件实体 {@link com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter}
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorys", method = RequestMethod.POST)
    public abstract EnerbosPage<MaterialInventoryVoForList> findInventorys(
            @RequestBody MaterialInventoryVoForFilter materialInventoryVoForFilter);

    /**
     * 新建物资库存
     *
     * @param materialInventoryVo 查询条件实体 {@link com.enerbos.cloud.eam.vo.MaterialInventoryVo}
     * @param userName            用户名称
     * @return 物资库存实体
     */
    @RequestMapping(value = "/eam/micro/inventory/saveInventory", method = RequestMethod.POST)
    public abstract MaterialInventoryVo saveInventory(
            @RequestBody MaterialInventoryVo materialInventoryVo,
            @RequestParam(value = "userName", required = false) String userName);

    /**
     * 修改物资记录
     *
     * @param materialInventoryVo 修改的物资实体 {@link com.enerbos.cloud.eam.vo.MaterialInventoryVo}
     * @return 修改后的物资实体
     */
    @RequestMapping(value = "/eam/micro/inventory/updateInventory", method = RequestMethod.POST)
    public abstract MaterialInventoryVo updateInventory(
            @RequestBody MaterialInventoryVo materialInventoryVo);

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     * @return 删除是否成功
     */
    @RequestMapping(value = "/eam/micro/inventory/deleteInventory", method = RequestMethod.POST)
    public abstract boolean deleteInventory(
            @RequestParam(value = "ids", required = true) String ids[]);

    /**
     * 查询物资详细信息
     *
     * @param id 物资库存id
     * @return 返回物资实体
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventoryDetail", method = RequestMethod.POST)
    public abstract MaterialInventoryVo findInventoryDetail(
            @RequestParam(value = "id", required = true) String id);

    /**
     * 修改物资状态
     *
     * @param id     物资库存id
     * @param status 状态 (活动、不活动、草稿)
     * @return 物资库存实体
     */
    @RequestMapping(value = "/eam/micro/inventory/updateInventoryStatus", method = RequestMethod.POST)
    public abstract boolean updateInventoryStatus(
            @RequestParam(value = "id", required = true) String[] id,
            @RequestParam(value = "status", required = true) String status);

    /**
     * 查询物资在库房中的信息
     *
     * @param id       物资id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 返回封装实体
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysByItemId", method = RequestMethod.GET)
    public abstract EnerbosPage<MaterialInventoryVoForList> findInventorysByItemId(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "siteId", required = true) String siteId, @RequestParam(value = "orgId", required = true) String orgId);

    /**
     * 去掉items的库存信息查询
     *
     * @param ids      去掉的ids合集
     * @param pageNum  页数
     * @param pageSize 条数
     * @return 结果集
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysNotInItems", method = RequestMethod.POST)
    public abstract EnerbosPage<MaterialInventoryVoForList> findInventorysNotInItems(
            @RequestParam(value = "ids", required = false) String[] ids,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "siteId", required = true) String siteId, @RequestParam(value = "orgId", required = true) String orgId);

    /**
     * 根据库房id 查询物资库存
     *
     * @param id       库房id
     * @param pageNum  页数
     * @param pageSize 每页显示条数
     * @return 返回执行码及数据
     */

    @RequestMapping(value = "/eam/micro/inventory/findInventorysByStoreroomId", method = RequestMethod.GET)
    public abstract EnerbosPage<MaterialInventoryVoForStoreroomList> findInventorysByStoreroomId(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);

    /**
     * 查询未盘点的物资库存
     *
     * @param ids         盘点id
     * @param storeroomid 库房id
     * @param pageNum     页数
     * @param pageSize    条数
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysNotInCheck", method = RequestMethod.GET)
    EnerbosPage<MaterialInventoryVoForList> findInventorysNotInCheck(@RequestParam("ids") String[] ids, @RequestParam("storeroomid") String storeroomid, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);

    /**
     * 查询不在itemNums中的物资库存
     *
     * @param itemNums 物资台账编码  数组
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 结果集
     */
    @RequestMapping(value = "/eam/micro/inventory/findInventorysNotInItemNum", method = RequestMethod.POST)
    public abstract EnerbosPage<MaterialInventoryVoForList> findInventorysNotInItemNum(@RequestParam(value = "storeroomId", required = false) String storeroomId, @RequestParam(value = "itemNums", required = false) String[] itemNums, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("siteId") String siteId, @RequestParam("orgId") String
            orgId);
}

@Component
class InventoryClientFallback implements
        FallbackFactory<MaterialInventoryClient> {


    @Override
    public MaterialInventoryClient create(Throwable cus) {

        return new MaterialInventoryClient() {

            @Override
            public boolean updateInventoryStatus(String id[],
                                                 String status) {
                return false;
            }

            @Override
            public EnerbosPage<MaterialInventoryVoForList> findInventorysByItemId(String id, Integer pageNum, Integer pageSize, String siteId, String orgId) {
                return null;
            }

            @Override
            public MaterialInventoryVo updateInventory(
                    MaterialInventoryVo materialInventoryVo) {

                return null;
            }

            @Override
            public MaterialInventoryVo saveInventory(
                    MaterialInventoryVo materialInventoryVo, String userName) {

                return null;
            }

            @Override
            public EnerbosPage<MaterialInventoryVoForList> findInventorys(
                    MaterialInventoryVoForFilter materialInventoryVoForFilter) {

                return null;
            }

            @Override
            public MaterialInventoryVo findInventoryDetail(String id) {

                return null;
            }

            @Override
            public boolean deleteInventory(String[] ids) {
                return false;

            }


            @Override
            public EnerbosPage<MaterialInventoryVoForList> findInventorysNotInItems(String[] ids, Integer pageNum, Integer pageSize, String siteId, String orgId) {
                return null;
            }

            @Override
            public EnerbosPage<MaterialInventoryVoForStoreroomList> findInventorysByStoreroomId(String id, Integer pageNum, Integer pageSize, String siteId, String orgId) {
                return null;
            }


            @Override
            public EnerbosPage<MaterialInventoryVoForList> findInventorysNotInCheck(String[] ids, String storeroomid, Integer pageSize, Integer pageNum, String siteId, String orgId) {
                return null;
            }


            @Override
            public EnerbosPage<MaterialInventoryVoForList> findInventorysNotInItemNum(String releaseId, String[] itemNums, Integer pageNum, Integer pageSize, String siteId, String orgId) {
                return null;
            }


        };
    }

}