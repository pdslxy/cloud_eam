package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlan;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanMeterRelation;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterPlanRequency;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanFilterVo;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanRequencyVo;
import com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVoForList;
import com.enerbos.cloud.eam.vo.DaliyCopyMeterPlanMeterRelationVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DaliyCopyMeterPlanService {

    /**
     * 获取抄表计划列表
     *
     * @param dailyCopyMeterPlanFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanFilterVo}
     * @return 返回查询 数据
     */
    PageInfo<DailyCopyMeterPlanVoForList> findCopyMeterPlans(DailyCopyMeterPlanFilterVo dailyCopyMeterPlanFilterVo) throws Exception;

    /**
     * 新增抄表单
     *
     * @param daliyCopyMeterPlan 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVo}
     * @return 保存后实体
     */
    DaliyCopyMeterPlan saveCopyMeterPlan(DaliyCopyMeterPlan daliyCopyMeterPlan);

    /**
     * 根据id删除
     *
     * @param ids
     */
    void deleteCopyMeterPlan(String[] ids);


    /**
     * 查找抄表单
     *
     * @param id
     * @return
     */
    DaliyCopyMeterPlan findCopyMeterPlanById(String id);




    /**
     * 修改抄表单状态
     *
     * @param ids 操作的id
     * @return
     */
    void updateCopyMeterPlanStatus(String[] ids, String status);

    /**
     * 保存计划频率
     * @param daliyCopyMeterPlanRequencyList
     */
    void saveCopyMeterPlanRequency(List<DaliyCopyMeterPlanRequency> daliyCopyMeterPlanRequencyList);

    /**
     * 保存计划与仪表关系
     * @param daliyCopyMeterPlanMeterRelations
     */
    void saveCopyMeterPlanMeterRelation(List<DaliyCopyMeterPlanMeterRelation> daliyCopyMeterPlanMeterRelations);

    PageInfo<DailyCopyMeterPlanRequencyVo> findCopyMeterPlanRequencyVosById(String id);

    void deleteCopyMeterPlanMeterRelationById(String[] ids);

    void deleteCopyMeterPlanRequencyById(String[] ids);

    PageInfo<DaliyCopyMeterPlanMeterRelationVo> findCopyMeterPlanMeterRelationVosById(String id);
}
