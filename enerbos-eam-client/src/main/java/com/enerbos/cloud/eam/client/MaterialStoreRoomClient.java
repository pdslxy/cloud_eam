package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVo;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForList;
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
 * @Description 库房接口
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = StoreRoomClientFullBack.class)
public interface MaterialStoreRoomClient {

    /**
     * 按照参数查询库房并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialStoreRoomVoForFilter 查询条件过滤实体
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/storeroom/findStoreRooms", method = RequestMethod.POST)
    public abstract EnerbosPage<MaterialStoreRoomVoForList> findStoreRooms(
            @RequestBody MaterialStoreRoomVoForFilter materialStoreRoomVoForFilter);

    /**
     * 新建库房
     *
     * @param materialStoreRoomVo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/saveStoreRoom", method = RequestMethod.POST)
    public abstract MaterialStoreRoomVo saveStoreRoom(
            @RequestBody MaterialStoreRoomVo materialStoreRoomVo);

    /**
     * 修改库房记录
     *
     * @param ids    要修改的库房ID数组
     * @param status z状态（暂挂,活动 ）
     * @return 修改后的库房实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/changeStroeRoomStatus", method = RequestMethod.POST)
    public abstract boolean changeStroeRoomStatus(
            @RequestParam(value = "ids", required = true) String[] ids,
            @RequestParam(value = "status", required = true) String status);

    /**
     * 删除库房
     *
     * @param ids 库房ID数组
     */
    @RequestMapping(value = "/eam/micro/storeroom/deleteStoreRoom", method = RequestMethod.POST)
    public abstract String deleteStoreRoom(
            @RequestParam(value = "ids", required = true) String ids[]);

    /**
     * 查询库房详细信息
     *
     * @param id 库房id
     * @return 返回库房实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/findStoreRoomDetail", method = RequestMethod.GET)
    public abstract MaterialStoreRoomVo findStoreRoomDetail(
            @RequestParam("id") String id);

    /**
     * 修改库房记录
     *
     * @param materialStoreRoomVo 库房实体 {@link com.enerbos.cloud.eam.vo.MaterialStoreRoomVo}
     * @return 返回修改后的库房实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/updateStoreRoom", method = RequestMethod.POST)
    public abstract MaterialStoreRoomVo updateStoreRoom(
            @RequestBody MaterialStoreRoomVo materialStoreRoomVo);

    /**
     * 根据itemNum查询组织站点下可用的库房详细信息
     *
     * @param itemNum 物资台账编码
     * @param orgId   组织id
     * @param siteId  站点id
     * @return 库房列表
     */
    @RequestMapping(value = "/eam/micro/storeroom/findUsableStoreRoom", method = RequestMethod.GET)
    EnerbosPage<MaterialStoreRoomVoForList> findUsableStoreRoom(@RequestParam("itemNum") String itemNum, @RequestParam(value = "orgId", required = false) String orgId, @RequestParam(value = "siteId", required = false) String siteId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize")Integer pageSize);


    /**
     * 查询站点下是否有默认库房
     *
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/storeroom/findhasdefault", method = RequestMethod.GET)
    boolean findhasdefault(@RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);
}

@Component
class StoreRoomClientFullBack implements
        FallbackFactory<MaterialStoreRoomClient> {

    @Override
    public MaterialStoreRoomClient create(Throwable cus) {
        return new MaterialStoreRoomClient() {

            @Override
            public MaterialStoreRoomVo saveStoreRoom(
                    MaterialStoreRoomVo materialStoreRoomVo) {

                return null;
            }

            @Override
            public EnerbosPage<MaterialStoreRoomVoForList> findStoreRooms(
                    MaterialStoreRoomVoForFilter materialStoreRoomVoForFilter) {

                return null;
            }

            @Override
            public MaterialStoreRoomVo findStoreRoomDetail(String id) {

                return null;
            }

            @Override
            public String deleteStoreRoom(String[] ids) {
                return null;

            }

            @Override
            public boolean changeStroeRoomStatus(String[] ids, String status) {
                return false;
            }

            @Override
            public MaterialStoreRoomVo updateStoreRoom(
                    MaterialStoreRoomVo materialStoreRoomVo) {
                return null;
            }

            @Override
            public EnerbosPage<MaterialStoreRoomVoForList> findUsableStoreRoom(String itemNum, String orgId, String siteId, Integer pageNum, Integer pageSize) {
                return null;
            }

            @Override
            public boolean findhasdefault(String siteId, String orgId) {
                return false;
            }

        };
    }

}