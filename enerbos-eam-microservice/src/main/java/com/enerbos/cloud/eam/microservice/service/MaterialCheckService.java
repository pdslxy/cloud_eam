package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaterialCheck;
import com.enerbos.cloud.eam.microservice.domain.MaterialCheckDetail;
import com.enerbos.cloud.eam.vo.MaterialCheckDetailVoForList;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForInventoryList;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForList;
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
 * @Description 物资盘点接口
 */
public interface MaterialCheckService {

    /**
     * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialCheckVoForFilter 物资盘点列表实体
     *                                 {@link com.enerbos.cloud.eam.vo.MaterialCheckVoForFilter}
     * @return 返回列表结果集
     */
    PageInfo<MaterialCheckVoForList> findMaterialCheck(MaterialCheckVoForFilter materialCheckVoForFilter);

    /**
     * 新建物资盘点单
     *
     * @param materialCheck 物资盘点列表实体
     *                      {@link com.enerbos.cloud.eam.microservice.domain.MaterialCheck}
     * @return 返回添加的实体
     */
    MaterialCheck saveMaterialCheck(MaterialCheck materialCheck);

    /**
     * 根据ID删除物资盘点
     *
     * @param ids 物资盘点id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     */
    void deleteMaterialCheck(String[] ids);

    /**
     * 查询物资盘点详细信息
     *
     * @param id 物资盘点id
     * @return 返回物资盘点实体
     */
    MaterialCheck findMaterialCheckById(String id);

    /**
     * 根据ID删除物资盘点明细
     *
     * @param ids 物资盘点明细id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     */
    void deleteMaterialCheckDetail(String[] ids);

    /**
     * 修改物资盘点状态
     *
     * @param ids    物资盘点ids
     * @param status 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     */
    void updateMaterialCheckStatus(String[] ids, String status);

    /**
     * 根据物资盘点id查询盘点明细
     *
     * @param id       物资盘点id
     * @param pageNum  页数
     * @param pageSize 每页显示数据
     * @return 盘点明细实体
     */
    PageInfo<MaterialCheckDetailVoForList> findMaterialCheckDetail(String id, Integer pageNum, Integer pageSize);

    /**
     * 新建物资盘点明细
     *
     * @param materialCheckDetails 新建物资盘点明细实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialCheckDetail}
     * @return 物资盘点明细实体
     */
    List<MaterialCheckDetail> saveMaterialCheckDetail(List<MaterialCheckDetail> materialCheckDetails);

    /**
     * 根据物资库存id查询 盘点记录
     *
     * @param id       物资库存id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 查询结果列表
     */
    PageInfo<MaterialCheckVoForInventoryList> findMaterialCheckByInvtoryId(String id, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询物资盘点 明细
     *
     * @param id
     * @return 查询结果列表
     */
    MaterialCheckDetail findMaterialCheckDetailById(String id);
}
