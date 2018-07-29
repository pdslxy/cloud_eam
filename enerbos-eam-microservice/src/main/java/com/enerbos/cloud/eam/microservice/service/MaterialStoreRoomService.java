package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaterialStoreRoom;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForList;
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
 * @Description 库房接口
 */
public interface MaterialStoreRoomService {

    /**
     * 按照参数查询库房并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialStoreRoomVoForFilter 查询条件实体类 {@link com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter}
     * @return 封装的实体类集合
     */

    public abstract PageInfo<MaterialStoreRoomVoForList> findStoreRooms(
            MaterialStoreRoomVoForFilter materialStoreRoomVoForFilter);

    /**
     * 新建库房
     *
     * @param materialStoreRoom 新建的实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialStoreRoom}
     * @return 返回添加的实体
     */
    public abstract MaterialStoreRoom saveStoreRoom(MaterialStoreRoom materialStoreRoom);

    /**
     * 修改库房状态
     *
     * @param ids    需要修改id
     * @param status 状态
     * @return 修改后的库房实体
     */
    public abstract void changeStroeRoomStatus(String[] ids, String status);

    /**
     * 删除库房
     *
     * @param ids 库房ID数组
     */
    public abstract void deleteStoreRoom(String ids[]);

    /**
     * 查询库房详细信息
     *
     * @param id 库房id
     * @return 返回库房实体
     */
    public abstract MaterialStoreRoom findStoreRoomDetail(String id);

    /**
     * 修改实体记录
     *
     * @param materialStoreRoom 库房实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialStoreRoom}
     * @return 返回修改后的实体
     */
    public abstract MaterialStoreRoom updateStoreRoom(
            MaterialStoreRoom materialStoreRoom);

    /**
     * 根据itemNum查询组织站点下可用的库房详细信息
     *
     * @param itemNum 物资台账编码
     * @param orgId   组织id
     * @param siteId  站点id
     * @return 库房列表
     */
    PageInfo<MaterialStoreRoomVoForList> findUsableStoreRoom(String itemNum, String orgId, String siteId,Integer pageNum,Integer pageSize);


    /**
     * 查询站点下是否有默认库房
     *
     * @return 返回执行码及数据
     */
    boolean findhasdefault(String siteId, String orgId);
}