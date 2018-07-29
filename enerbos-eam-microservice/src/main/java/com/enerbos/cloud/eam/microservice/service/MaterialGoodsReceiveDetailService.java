package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceiveDetail;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVoForList;
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
 * @Description 物资接收单明细接口
 */
public interface MaterialGoodsReceiveDetailService {

    /**
     * 查询物资接收单明细并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param id       参数
     * @param pageNum  页数
     * @param pageSize 一页显示的行数
     * @return 封装的实体类集合
     */

    public abstract PageInfo<MaterialGoodsReceiveDetailVoForList> findGoodsReceiveDetailByGoodsReceiveId(
            String id, Integer pageNum, Integer pageSize);

    /**
     * 新建物资接收单明细
     *
     * @param materialGoodsReceiveDetail 新建的实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceiveDetail}
     * @return 返回添加的实体
     */
    public abstract MaterialGoodsReceiveDetail saveMatrectrans(
            MaterialGoodsReceiveDetail materialGoodsReceiveDetail);

    /**
     * 修改物资接收单明细
     *
     * @param materialGoodsReceiveDetail 修改的物资接收单明细 {@link com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceiveDetail}
     * @return 修改后的物资接收单明细
     */
    public abstract MaterialGoodsReceiveDetail updateGoodsReceiveDetail(
            MaterialGoodsReceiveDetail materialGoodsReceiveDetail);

    /**
     * 删除物资接收单明细
     *
     * @param ids 物资接收单明细ID
     */
    public abstract void deleteGoodsReceiveDetail(String ids[]);

    /**
     * 根据id查询物资接收明细
     *
     * @param id 物资接收明细id
     * @return 返回 物资接收实体
     */
    public abstract MaterialGoodsReceiveDetail findGoodsReceiveDetailById(
            String id);

    /**
     * 新建物资接收单明细 批量
     * @param materialGoodsReceiveDetailList 批量明细
     * @return
     */
    List<MaterialGoodsReceiveDetail> saveMaterialGoodsReceiveDetailList(List<MaterialGoodsReceiveDetail> materialGoodsReceiveDetailList);
}