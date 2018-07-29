package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.DefectOrder;
import com.enerbos.cloud.eam.vo.*;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月5日
 * @Description 消缺工单service
 */
public interface DefectOrderService {

	/**
	 * findDefectOrderByID: 根据ID查询消缺工单
	 * @param id
	 * @return DefectOrder
	 */
	DefectOrder findDefectOrderByID(String id);

	/**
	 * findDefectOrderByDefectOrderNum: 根据defectOrderNum查询消缺工单
	 * @param defectOrderNum 消缺工单编码
	 * @return DefectOrderVo 消缺工单
	 */
	DefectOrderVo findDefectOrderByDefectOrderNum(String defectOrderNum);

	/**
     * findPageDefectOrderList:分页查询消缺工单
     * @param defectOrderForFilterVo 过滤查询条件vo {@link com.enerbos.cloud.eam.vo.DefectOrderForFilterVo}
     * @return List<DefectOrderForListVo>
     */
    List<DefectOrderForListVo> findPageDefectOrderList(DefectOrderForFilterVo defectOrderForFilterVo);

    /**
     * save:保存消缺工单
     * @param defectOrder 消缺工单对象 {@link com.enerbos.cloud.eam.microservice.domain.DefectOrder}
     * @return DefectOrder
     */
    DefectOrder save(DefectOrder defectOrder);

    /**
     * deleteDefectOrderById:根据ID删除消缺工单
     * @param id
     * @return
     */
    void deleteDefectOrderById(String id);
    
    /**
     * deleteDefectOrderByIds:根据ID集合删除消缺工单
     * @param ids
     * @return 
     */
    void deleteDefectOrderByIds(List<String> ids);

	/**
	 * collectDefectOrder:收藏消缺工单
	 * @param maintenanceDefectOrderRfCollectorVoList 收藏的消缺工单列表 {@link com.enerbos.cloud.eam.vo.DefectOrderRfCollectorVo}
	 */
	void collectDefectOrder(List<DefectOrderRfCollectorVo> maintenanceDefectOrderRfCollectorVoList);

	/**
	 * cancelCollectDefectOrder:取消收藏
	 * @param maintenanceDefectOrderRfCollectorVoList 需要取消收藏的消缺工单列表 {@link com.enerbos.cloud.eam.vo.DefectOrderRfCollectorVo}
	 */
	void cancelCollectDefectOrder(List<DefectOrderRfCollectorVo> maintenanceDefectOrderRfCollectorVoList);
}
