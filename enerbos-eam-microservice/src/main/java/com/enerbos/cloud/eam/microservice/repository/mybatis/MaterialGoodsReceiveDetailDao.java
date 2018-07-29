package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVoForList;
import org.apache.ibatis.annotations.Mapper;

/**
 * All rights Reserved, Designed By 翼虎能源
 *
 * Copyright: Copyright(C) 2015-2017
 *
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 *
 * @version 1.0
 *
 * @date 2017年7月14日 下午8:34:13
 *
 * @Description Dao层接口
 *
 *
 */
@Mapper
public interface MaterialGoodsReceiveDetailDao {


	/**
	 * 查询物资发放明细
	 *
     * @param id 物资接收id
	 * @return 物资接收明细列表实体
	 */
	List<MaterialGoodsReceiveDetailVoForList> findGoodsReceiveDetailByGoodsReceiveId(String id);

	
}
