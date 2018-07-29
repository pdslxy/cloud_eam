package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaterialRelease;
import com.enerbos.cloud.eam.microservice.domain.MaterialReleaseDetail;
import com.enerbos.cloud.eam.vo.*;
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
 * @date 2017年4月6日 上午11:33:29
 * @Description 物资发放服务接口
 */
public interface MaterialReleaseService {

    /**
     * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialReleaseVoForFilter 物资发放列表实体
     *                                   {@link com.enerbos.cloud.eam.vo.MaterialReleaseVoForFilter}
     * @return 返回列表结果集
     */
    PageInfo<MaterialReleaseVoForList> findMaterialRelease(
            MaterialReleaseVoForFilter materialReleaseVoForFilter);

    /**
     * 新建物资发放
     *
     * @param materialRelease 新建的实体 {@link com.enerbos.cloud.eam.microservice.domain.MaterialRelease}
     * @return 返回添加的实体
     */
    MaterialRelease saveMaterialRelease(MaterialRelease materialRelease);

    /**
     * 删除物资
     *
     * @param ids 物资发放ID数组
     */

    void deleteMaterialRelease(String[] ids);

    /**
     * 查询物资发放详细信息
     *
     * @param id 物资id
     * @return 返回物资发放实体
     */
    MaterialRelease findMaterialReleaseById(String id);

    /**
     * 查询物资发放明细列表
     *
     * @param materialReleaseDetailVoForFilter 查询实体
     *                                         {@link com.enerbos.cloud.eam.vo.MaterialReleaseDetailVoForFilter }
     * @return 返回物资发放列表实体数据
     */
    PageInfo<MaterialReleaseDetailVoForList> findMaterialReleaseDetail(
            MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter);

    /**
     * 新建物资发放明细单
     *
     * @param materialReleaseDetailVoList 物资发放列表实体
     *                              {@link com.enerbos.cloud.eam.microservice.domain.MaterialReleaseDetail}
     * @return 新建后物资发放单明细实体
     */
    List<MaterialReleaseDetail> saveMaterialReleaseDetail(List<MaterialReleaseDetail> materialReleaseDetailVoList);

    /**
     * 根据ID删除物资发放明细
     *
     * @param ids 物资发放明细id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     */
    void deleteMaterialReleaseDetail(String[] ids);

    /**
     * 根据id查询物资方法明细
     *
     * @param id 物资方法明细id
     * @return 物资发放明细实体
     */
    MaterialReleaseDetail findMaterialReleaseDetailById(String id);

    /**
     * 根据工单id查询物资发放情况
     *
     * @param id 工单id
     * @return
     */
    PageInfo<MaterialInventoryVoForReleaseList> findItemInReleaseByorderId(String id,Integer pageSize,Integer pageNum);
}
