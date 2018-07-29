package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.Construction;
import com.enerbos.cloud.eam.vo.ConstructionForFilterVo;
import com.enerbos.cloud.eam.vo.ConstructionForListVo;
import com.enerbos.cloud.eam.vo.ConstructionVo;
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
 * @Description 施工单Dao
 */
@Mapper
public interface ConstructionDao {

	/**
	 * findConstructionByID: 根据ID查询施工单
	 * @param id
	 * @return Construction
	 */
	Construction findConstructionByID(String id);

	/**
	 * findConstructionCommitByConstructionNum: 根据constructionNum查询施工单
	 * @param 根据constructionNum查询施工单 施工单编码
	 * @return ConstructionForCommitVo 施工单
	 */
	ConstructionVo findConstructionByConstructionNum(String 根据constructionNum查询施工单);

	/**
	 * findPageConstructionList: 查询施工单列表
	 * @param defectDocumentForFilterVo 过滤条件 {@link ConstructionForFilterVo}
	 * @return List<ConstructionForListVo>
	 */
	List<ConstructionForListVo> findPageConstructionList(ConstructionForFilterVo defectDocumentForFilterVo);
}
