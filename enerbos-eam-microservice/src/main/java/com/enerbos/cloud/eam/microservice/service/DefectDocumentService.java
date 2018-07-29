package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.DefectDocument;
import com.enerbos.cloud.eam.vo.DefectDocumentForFilterVo;
import com.enerbos.cloud.eam.vo.DefectDocumentForListVo;
import com.enerbos.cloud.eam.vo.DefectDocumentRfCollectorVo;
import com.enerbos.cloud.eam.vo.DefectDocumentVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月5日
 * @Description 缺陷单service
 */
public interface DefectDocumentService {

	/**
	 * findDefectDocumentByID: 根据ID查询缺陷单
	 * @param id
	 * @return DefectDocument
	 */
	DefectDocument findDefectDocumentByID(String id);

	/**
	 * findDefectDocumentByDefectDocumentNum: 根据defectDocumentNum查询缺陷单
	 * @param defectDocumentNum 缺陷单编码
	 * @return DefectDocumentVo 缺陷单
	 */
	DefectDocumentVo findDefectDocumentByDefectDocumentNum(String defectDocumentNum);

	/**
     * findPageDefectDocumentList:分页查询缺陷单
     * @param defectDocumentForFilterVo 过滤查询条件vo {@link com.enerbos.cloud.eam.vo.DefectDocumentForFilterVo}
     * @return List<DefectDocumentForListVo>
     */
    List<DefectDocumentForListVo> findPageDefectDocumentList(DefectDocumentForFilterVo defectDocumentForFilterVo);

    /**
     * save:保存缺陷单
     * @param defectDocument 缺陷单对象 {@link com.enerbos.cloud.eam.microservice.domain.DefectDocument}
     * @return DefectDocument
     */
    DefectDocument save(DefectDocument defectDocument);

    /**
     * deleteDefectDocumentById:根据ID删除缺陷单
     * @param id
     * @return
     */
    void deleteDefectDocumentById(String id);
    
    /**
     * deleteDefectDocumentByIds:根据ID集合删除缺陷单
     * @param ids
     * @return 
     */
    void deleteDefectDocumentByIds(List<String> ids);

	/**
	 * collectDefectDocument:收藏缺陷单
	 * @param maintenanceDefectDocumentRfCollectorVoList 收藏的缺陷单列表 {@link com.enerbos.cloud.eam.vo.DefectDocumentRfCollectorVo}
	 */
	void collectDefectDocument(List<DefectDocumentRfCollectorVo> maintenanceDefectDocumentRfCollectorVoList);

	/**
	 * cancelCollectDefectDocument:取消收藏
	 * @param maintenanceDefectDocumentRfCollectorVoList 需要取消收藏的缺陷单列表 {@link com.enerbos.cloud.eam.vo.DefectDocumentRfCollectorVo}
	 */
	void cancelCollectDefectDocument(List<DefectDocumentRfCollectorVo> maintenanceDefectDocumentRfCollectorVoList);
}
