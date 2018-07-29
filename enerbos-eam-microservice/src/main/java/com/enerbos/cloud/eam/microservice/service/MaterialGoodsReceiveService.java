package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceive;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForList;
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
 * @Description 物资接收单接口
 */
public interface MaterialGoodsReceiveService {

    /**
     * 按照参数查询物资接收单并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialGoodsReceiveVoForFilter 查询条件实体类 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForFilter}
     * @return 封装的实体类集合
     */

    public abstract PageInfo<MaterialGoodsReceiveVoForList> findGoodsReceives(
            MaterialGoodsReceiveVoForFilter materialGoodsReceiveVoForFilter);

    /**
     * 新建物资库存
     *
     * @param materialGoodsReceive 新建的实体
     * @return 返回添加的实体
     */
    public abstract MaterialGoodsReceive saveGoodsReceive(
            MaterialGoodsReceive materialGoodsReceive);

    /**
     * 修改物资接收单记录
     *
     * @param materialGoodsReceive 修改的物资接收单实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceive}
     * @return 修改后的物资接收单实体
     */
    public abstract MaterialGoodsReceive updateGoodsReceive(
            MaterialGoodsReceive materialGoodsReceive);

    /**
     * 删除物资接收单
     *
     * @param ids 物资ID数组
     */
    public abstract void deleteGoodsReceive(String ids[]);

    /**
     * 查询物资接收单详细信息
     *
     * @param id 物资接收单id
     * @return 返回物资接收单实体
     */
    public abstract MaterialGoodsReceive findGoodsreceiveById(String id);

    /**
     * 修改物资接收单状态状态
     *
     * @param materialGoodsReceive 改变状态后实体
     */
    public abstract void updateGoodsReceiveStatus(MaterialGoodsReceive materialGoodsReceive);

}