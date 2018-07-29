package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.MaterialInventoryVoForReleaseList;
import com.enerbos.cloud.eam.vo.MaterialReleaseDetailVoForList;
import com.enerbos.cloud.eam.vo.MaterialReleaseVoForList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
public interface MaterialReleaseDao {

	/**
	 * 查询物资发放明细
	 * 
	 * @param filters
	 *            查询条件
	 * @return 物资发放明细列表实体
	 */
	List<MaterialReleaseDetailVoForList> findMaterialReleaseDetail(
			Map<String, Object> filters);

	/**
	 * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
	 * 把分好的数据封装到集合中
	 * 
	 * @param filters
	 *            查询条件
	 * @return 封装的结果集
	 */
	List<MaterialReleaseVoForList> findMaterialRelease(
			Map<String, Object> filters);


	/**
	 * 根据工单id查询物资发放情况
	 *
	 * @param id 工单id
	 * @return
	 */
    List<MaterialInventoryVoForReleaseList> findItemInReleaseByorderId(@Param("id") String id);
}
