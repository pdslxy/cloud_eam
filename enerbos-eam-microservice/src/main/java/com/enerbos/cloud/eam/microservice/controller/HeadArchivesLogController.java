package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesLog;
import com.enerbos.cloud.eam.microservice.service.HeadArchivesLogService;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVo;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVoForFilter;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Enerbos on 2016/10/19.
 */

@RestController
public class HeadArchivesLogController {

	private static final Logger logger = LoggerFactory
			.getLogger(HeadArchivesLogController.class);

	@Autowired
	private HeadArchivesLogService headArchivesLogService;
	

	/**
	 * 根据过滤条和分页信息获取档案日志管理列表
	 * 
	 * @return
	 */
	@RequestMapping("/eam/micro/headArchivesLog/getArchivesLogList")
	@ResponseBody
	public PageInfo<HeadArchivesLogVo> getArchivesLogList(
			@RequestBody HeadArchivesLogVoForFilter headArchivesLogVoForFilter) {
		PageInfo<HeadArchivesLogVo> pageInfo = headArchivesLogService.getArchivesLogList(headArchivesLogVoForFilter);
		return pageInfo;
	}

	/**
	 * 删除档案日志
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/eam/micro/headArchivesLog/deleteArchivesLog")
	@ResponseBody
	public boolean deleteArchivesLog(
			@RequestParam(value = "ids", required = false) String[] ids) {
		try {
			headArchivesLogService.deleteArchivesLog(ids);
		} catch (Exception e) {
			logger.error("-------deleteArchivesLog--------------", e);
			return false;
		}
		return true;
	}

	/**
	 * 新建档案日志
	 * 
	 * @param headArchivesLogVo
	 *            新建的实体
	 * @return 返回添加的实体
	 */
	@RequestMapping("/eam/micro/headArchivesLog/saveArchivesLog")
	@ResponseBody
	public HeadArchivesLogVo saveArchivesLog(
			@RequestBody @Valid HeadArchivesLogVo headArchivesLogVo) {
		HeadArchivesLog headArchivesLog = new HeadArchivesLog();
		try {
			ReflectionUtils.copyProperties(headArchivesLogVo, headArchivesLog, null);
		} catch (Exception e) {
			logger.error("-----saveArchivesLog ------", e);
		}
		//headArchivesLog.setCreateTime(new Date());
		headArchivesLog = headArchivesLogService.saveArchivesLog(headArchivesLog);
		headArchivesLog.setId(headArchivesLog.getId());
		return headArchivesLogVo;
	}

	/**
	 * 修改档案日志
	 * 
	 * @param headArchivesLogVo
	 *            修改的档案日志实体
	 * @return 修改后的档案日志实体
	 */
	@RequestMapping(value = "/eam/micro/headArchivesLog/updateArchivesLog", method = RequestMethod.POST)
	public HeadArchivesLog updateArchivesLog(
			@RequestBody @Valid HeadArchivesLogVo headArchivesLogVo) {
		HeadArchivesLog headArchivesLog = new HeadArchivesLog();
		try {
			ReflectionUtils.copyProperties(headArchivesLogVo, headArchivesLog, null);
		} catch (Exception e) {
			logger.error("-----updateEnergyPrice ------", e);
		}
		return headArchivesLogService.updateArchivesLog(headArchivesLog);
	}

	/**
	 * 查询档案日志详细信息
	 * 
	 * @param id
	 *            档案日志id
	 * @return 返回档案日志实体
	 */
	@RequestMapping(value = "/eam/micro/headArchivesLog/findArchivesLogDetail", method = RequestMethod.GET)
	public HeadArchivesLog findArchivesLogDetail(@RequestParam("id") String id) {
		return headArchivesLogService.findArchivesLogDetail(id);
	}

	/**
	 *
	 * 修改档案日志状态
	 * 
	 * @param ArchivesLogId
	 *            档案日志id
	 * @param status
	 *            状态
	 * @return 修改后返回的实体类
	 */

	@RequestMapping(value = "/eam/micro/headArchivesLog/updateArchivesLogStatus", method = RequestMethod.POST)
	public HeadArchivesLog updateArchivesLogStatus(
			@RequestParam("ArchivesLogId") String ArchivesLogId,
			@RequestParam("status") String status) {
		HeadArchivesLog headArchivesLog = headArchivesLogService.findArchivesLogDetail(ArchivesLogId);
		//headArchivesLog.setStatus(status);
		return headArchivesLogService.saveArchivesLog(headArchivesLog);
	}
}
