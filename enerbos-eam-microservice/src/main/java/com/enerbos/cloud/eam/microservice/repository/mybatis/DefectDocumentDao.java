package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.DefectDocument;
import com.enerbos.cloud.eam.vo.DefectDocumentForFilterVo;
import com.enerbos.cloud.eam.vo.DefectDocumentForListVo;
import com.enerbos.cloud.eam.vo.DefectDocumentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月5日
 * @Description 缺陷单Dao
 */
@Mapper
public interface DefectDocumentDao {

	/**
	 * findDefectDocumentByID: 根据ID查询缺陷单
	 * @param id
	 * @return DefectDocument
	 */
	DefectDocument findDefectDocumentByID(String id);

	/**
	 * findDefectDocumentCommitByDefectDocumentNum: 根据defectDocumentNum查询缺陷单
	 * @param defectDocumentNum 缺陷单编码
	 * @return DefectDocumentForCommitVo 缺陷单
	 */
	DefectDocumentVo findDefectDocumentByDefectDocumentNum(String defectDocumentNum);

	/**
	 * findPageDefectDocumentList: 查询缺陷单列表
	 * @param defectDocumentForFilterVo 过滤条件 {@link DefectDocumentForFilterVo}
	 * @return List<DefectDocumentForListVo>
	 */
	List<DefectDocumentForListVo> findPageDefectDocumentList(DefectDocumentForFilterVo defectDocumentForFilterVo);
}
