package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceJobStandard;
import com.enerbos.cloud.eam.vo.*;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM作业标准接口
 */
public interface MaintenanceJobStandardService {
	
	/**
     * save:保存作业计划
     * @param maintenanceJobStandard
     * @return MaintenanceJobStandard
     */
    MaintenanceJobStandard save(MaintenanceJobStandard maintenanceJobStandard);
    
    /**
	 * findJobStandardByID: 根据ID查询作业标准列表
	 * @param id
	 * @return MaintenanceJobStandard
	 */
    MaintenanceJobStandard findJobStandardByID(String id);
    
    /**
     * findJobStandardByJobStandardNum：根据作业标准编码查询作业标准
     * @param jobStandardNum 作业标准编码
     * @return MaintenanceJobStandardVo
     */
    MaintenanceJobStandardVo findJobStandardByJobStandardNum(String jobStandardNum);
    
    /**
     * findJobStandardList:分页查询
     * @param MaintenanceJobStandardSelectVo 作业标准列表过滤条件Vo
     * @return List<MaintenanceJobStandardForListVo>
     */
    List<MaintenanceJobStandardForListVo> findJobStandardList(MaintenanceJobStandardSelectVo maintenanceJobStandardSelectVo);

    /**
	 * findPageJobStandardByAssetId:根据设备ID分页查询作业标准
	 * @param maintenanceForAssetFilterVo 根据设备查询作业标准列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
	 * @return List<MaintenanceJobStandardForListVo> 预防性维护计划Vo集合
	 */
	List<MaintenanceJobStandardForListVo> findPageJobStandardByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);
	
    /**
     * deleteJobStandardById:根据ID删除作业标准
     * @param id
     * @return boolean
     */
    boolean deleteJobStandardById(String id);

    /**
     * deleteJobStandardByIds：根据ids删除作业标准
     * @param ids
     * @return boolean
     */
    boolean deleteJobStandardByIds(List<String> ids);


    /**
     * 收藏作业标准
     * @param jobStandardRfCollectorVo 收藏的作业标准列表
     */
    void collectJobStandard(List<MaintenanceJobStandardRfCollectorVo> jobStandardRfCollectorVo);

    /**
     * 取消收藏
     * @param jobStandardRfCollectorVoList 需要取消收藏的作业标准列表
     */
    void cancelCollectJobStandard(List<MaintenanceJobStandardRfCollectorVo> jobStandardRfCollectorVoList);

    /**
     * 批量保存标准
     * @param mjsVos
     */
    List<MaintenanceJobStandardVo> saveBatchStandard(List<MaintenanceJobStandardVo> mjsVos) throws Exception;
}
