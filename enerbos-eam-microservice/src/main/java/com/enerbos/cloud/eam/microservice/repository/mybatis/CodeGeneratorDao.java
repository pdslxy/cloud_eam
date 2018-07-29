package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.microservice.domain.CodeGenerator;
import com.enerbos.cloud.eam.vo.CodeGeneratorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月30日
 * @Description 生成编码
 */
@Mapper
public interface CodeGeneratorDao {

	/**
	 * findBySiteIdAndModelKey: 查询编码实体
	 * @param params 查询条件，仅支持orgId 组织ID、siteId 站点ID和modelKey 模块编码联合查询
	 * @return CodeGenerator
	 */
	CodeGenerator findBySiteIdAndModelKey(@Param("params") Map<String, Object> params);

	/**
	 * findById: 根据ID查询编码实体
	 * @param id
	 * @return CodeGenerator
	 */
	CodeGenerator findById(@Param("id") String id);
	
	/**
	 * findModelKeyListBySiteId: 根据站点ID查询模块编码
	 * @param siteId 站点ID
	 * @return List<String> 返回模块编码集合
	 */
	List<String> findModelKeyListBySiteId(@Param("siteId") String siteId);

	/**
	 * 查询组织站点内所有编码
	 * @param orgId
	 * @param siteId
	 * @return
	 */
    List<CodeGeneratorVo> findAllCodes(@Param("orgId") String orgId, @Param("siteId") String siteId);
}
