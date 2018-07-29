package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.DefectOrder;
import com.enerbos.cloud.eam.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月5日
 * @Description 消缺工单Dao
 */
@Mapper
public interface DefectOrderDao {

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
	 * findPageDefectOrderList: 查询消缺工单列表
	 * @param defectOrderForFilterVo 过滤条件 {@link DefectOrderForFilterVo}
	 * @return List<DefectOrderForListVo>
	 */
	List<DefectOrderForListVo> findPageDefectOrderList(DefectOrderForFilterVo defectOrderForFilterVo);
}
