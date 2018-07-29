package com.enerbos.cloud.eam.microservice.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.enerbos.cloud.eam.vo.MaintenanceJobStandardItemVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description 作业标准关联的物料接口
 */
@Mapper
public interface MaintenanceJobStandardItemDao {

	/**
	 * findJobStandardItemByJobStandardId: 根据作业标准ID查询所需物料
	 * @param jobStandardId 作业标准ID
	 * @return List<MaintenanceJobStandardItemVo> 返回作业标准关联的物料Vo集合
	 */
	List<MaintenanceJobStandardItemVo> findJobStandardItemByJobStandardId(@Param("jobStandardId") String jobStandardId);

	/**
	 * findJobStandardItemByJobStandardIds: 根据作业标准ID查询所需物料id
	 * @param jobStandardIds 作业标准ID List
	 * @return List<String> 返回作业标准关联的物料id
	 */
	List<String> findJobStandardItemByJobStandardIds(@Param("jobStandardIds") List<String> jobStandardIds);
}
