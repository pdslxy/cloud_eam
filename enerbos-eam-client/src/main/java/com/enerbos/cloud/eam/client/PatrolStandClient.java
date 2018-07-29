package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolStandClientFallback.class)
public interface PatrolStandClient {

	/**
	 * 分页查询巡检标准
	 *
	 * @param patrolStandVoForFilter
	 *            巡检标准查询实体
	 * @return 分页列表
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/findPage", method = RequestMethod.POST)
	EnerbosPage<PatrolStandVoForList> findPatrolStandList(
			@RequestBody PatrolStandVoForFilter patrolStandVoForFilter);

	/**
	 * 根据ID查询巡检标准
	 *
	 * @param id
	 *            巡检标准id
	 * @return 返回执行码及数据
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/findPatrolStandById", method = RequestMethod.GET)
	PatrolStandVo findPatrolStandById(@RequestParam("id") String id);

	/**
	 * 保存巡检标准
	 * 
	 * @param patrolStandVo
	 *            巡检标准实体 {@link com.enerbos.cloud.eam.vo.PatrolStandVo}
	 * @return 保存的实体
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/savePatrolStand", method = RequestMethod.POST)
	PatrolStandVo savePatrolStand(@RequestBody PatrolStandVo patrolStandVo);

	/**
	 * 修改巡检标准
	 * 
	 * @param patrolStandVo
	 *            巡检标准实体 {@link com.enerbos.cloud.eam.vo.PatrolStandVo}
	 * @return 保存的实体
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/updatePatrolStand", method = RequestMethod.POST)
	PatrolStandVo updatePatrolStand(@RequestBody PatrolStandVo patrolStandVo);

	/**
	 * 删除巡检标准
	 * 
	 * @param ids
	 *            要删除的id数组
	 * @return 数据
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/deletePatrolStand", method = RequestMethod.POST)
	boolean deletePatrolStand(@RequestParam("ids") String[] ids);

	/**
	 * 根据巡检标准id查找巡检标准内容
	 * 
	 * @param id
	 *            巡检标准id
	 * @param pageSize
	 *            条数
	 * @param pageNum
	 *            页数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/findPatrolStandContent", method = RequestMethod.POST)
	EnerbosPage<PatrolStandContentVoForList> findPatrolStandContent(@RequestParam("id") String id,
			@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum")Integer pageNum);

	/**
	 * 保存巡检内容
	 * 
	 * @param patrolStandContentVo
	 *            {@link com.enerbos.cloud.eam.vo.PatrolStandContentVo}
	 * @return 保存后实体
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/savePatrolStandContent", method = RequestMethod.POST)
	PatrolStandContentVo savePatrolStandContent(@RequestBody
			PatrolStandContentVo patrolStandContentVo);

	/**
	 * 修改巡检内容
	 * 
	 * @param patrolStandContentVo
	 *            {@link com.enerbos.cloud.eam.vo.PatrolStandContentVo}
	 * @return 修改后实体
	 */

	@RequestMapping(value = "/eam/micro/patrolStand/updatePatrolStandContent", method = RequestMethod.POST)
	PatrolStandContentVo updatePatrolStandContent(@RequestBody
			PatrolStandContentVo patrolStandContentVo);

	/**
	 * 
	 * 删除巡检标准内容
	 * 
	 * @param ids
	 *            id合集
	 * @return boolean 成功 失败
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/deletePatrolStandContent", method = RequestMethod.POST)
	boolean deletePatrolStandContent(@RequestParam("ids") String[] ids);

	/**
	 * 根据id查询巡检标准内容
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/eam/micro/patrolStand/findPatrolStandContentById", method = RequestMethod.POST)
	PatrolStandContentVo findPatrolStandContentById(@RequestParam("id") String id);
}

@Component
class PatrolStandClientFallback implements FallbackFactory<PatrolStandClient> {

	@Override
	public PatrolStandClient create(Throwable cause) {
		return new PatrolStandClient() {

			@Override
			public EnerbosPage<PatrolStandVoForList> findPatrolStandList(
					PatrolStandVoForFilter patrolStandVoForFilter) {
				return null;
			}

			@Override
			public PatrolStandVo findPatrolStandById(String id) {
				return null;
			}

			@Override
			public PatrolStandVo savePatrolStand(PatrolStandVo patrolStandVo) {
				return null;
			}

			@Override
			public PatrolStandVo updatePatrolStand(PatrolStandVo patrolStandVo) {
				return null;
			}

			@Override
			public boolean deletePatrolStand(String[] ids) {
				return false;
			}

			@Override
			public EnerbosPage<PatrolStandContentVoForList> findPatrolStandContent(
					String id, Integer pageSize, Integer pageNum) {
				return null;
			}

			@Override
			public PatrolStandContentVo savePatrolStandContent(
					PatrolStandContentVo patrolStandContentVo) {

				return null;
			}

			@Override
			public PatrolStandContentVo updatePatrolStandContent(
					PatrolStandContentVo patrolStandContentVo) {

				return null;
			}

			@Override
			public boolean deletePatrolStandContent(String[] ids) {

				return false;
			}

			@Override
			public PatrolStandContentVo findPatrolStandContentById(String id) {
				return null;
			}
		};
	}
}