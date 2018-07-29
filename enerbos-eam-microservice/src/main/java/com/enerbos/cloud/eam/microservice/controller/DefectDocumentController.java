package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.DefectDocument;
import com.enerbos.cloud.eam.microservice.service.DefectDocumentService;
import com.enerbos.cloud.eam.vo.DefectDocumentForFilterVo;
import com.enerbos.cloud.eam.vo.DefectDocumentForListVo;
import com.enerbos.cloud.eam.vo.DefectDocumentRfCollectorVo;
import com.enerbos.cloud.eam.vo.DefectDocumentVo;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月5日
 * @Description 缺陷单接口
 */
@RestController
public class DefectDocumentController {

    private Logger logger = LoggerFactory.getLogger(DefectDocumentController.class);

    @Resource
    protected DefectDocumentService defectDocumentService;

    /**
     * saveDefectDocument:保存缺陷单
     * @param defectDocumentVo {@link DefectDocumentVo}
     * @return DefectDocumentVo
     */
    @RequestMapping(value = "/eam/micro/defectDocument/saveDefectDocument", method = RequestMethod.POST)
    public DefectDocumentVo saveDefectDocument(@RequestBody DefectDocumentVo defectDocumentVo){
		DefectDocumentVo defectDocumentVoSave=new DefectDocumentVo();
    	try {
			DefectDocument defectDocument=new DefectDocument();
			if (StringUtils.isNotBlank(defectDocument.getId())) {
				defectDocument=defectDocumentService.findDefectDocumentByID(defectDocumentVo.getId());
				if (null==defectDocument) {
					throw new EnerbosException("", "工单不存在，先创建！");
				}
			}
    		ReflectionUtils.copyProperties(defectDocumentVo, defectDocument, null);
			DefectDocument defectDocumentSave=defectDocumentService.save(defectDocument);
			ReflectionUtils.copyProperties(defectDocumentSave, defectDocumentVoSave, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/defectDocument/saveDefectDocument-----", e);
		}
    	return defectDocumentVoSave;
    }

	/**
	 * findDefectDocumentById:根据ID查询缺陷单
	 * @param id
	 * @return DefectDocumentVo 缺陷单VO
	 */
	@RequestMapping(value = "/eam/micro/defectDocument/findDefectDocumentById", method = RequestMethod.GET)
	public DefectDocumentVo findDefectDocumentById(@RequestParam("id") String id){
		DefectDocumentVo defectDocumentVo=new DefectDocumentVo();
		try {
			DefectDocument defectDocument=defectDocumentService.findDefectDocumentByID(id);
			if (null==defectDocument||defectDocument.getId()==null) {
				return new DefectDocumentVo();
			}
			ReflectionUtils.copyProperties(defectDocument, defectDocumentVo, null);
		} catch (Exception e) {
			logger.error("-------/eam/micro/defectDocument/findDefectDocumentById-----", e);
		}
    	return defectDocumentVo;
	}

	/**
	 * findDefectDocumentCommitByDefectDocumentNum: 根据defectDocumentNum查询缺陷单-工单提报
	 * @param defectDocumentNum 缺陷单编码
	 * @return DefectDocumentForCommitVo 缺陷单-工单提报
	 */
	@RequestMapping(value = "/eam/micro/defectDocument/findDefectDocumentByDefectDocumentNum", method = RequestMethod.GET)
	public DefectDocumentVo findDefectDocumentByDefectDocumentNum(@RequestParam("defectDocumentNum") String defectDocumentNum){
    	return defectDocumentService.findDefectDocumentByDefectDocumentNum(defectDocumentNum);
	}

    /**
	 * findPageDefectDocumentList: 分页查询缺陷单
	 * @param defectDocumentForFilterVo 查询条件 {@link DefectDocumentForFilterVo}
	 * @return PageInfo<DefectDocumentForListVo> 预防缺陷单集合
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectDocument/findPageDefectDocumentList")
	public PageInfo<DefectDocumentForListVo> findPageDefectDocumentList(@RequestBody DefectDocumentForFilterVo defectDocumentForFilterVo) {
		List<DefectDocumentForListVo> result = defectDocumentService.findPageDefectDocumentList(defectDocumentForFilterVo);
		return new PageInfo<DefectDocumentForListVo>(result);
	}

    /**
     * deleteDefectDocumentList:删除选中的缺陷单
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/defectDocument/deleteDefectDocumentList", method = RequestMethod.POST)
    public Boolean deleteDefectDocumentList(@RequestBody List<String> ids) {
    	try {
    		defectDocumentService.deleteDefectDocumentByIds(ids);
		} catch (Exception e) {
			logger.error("-------/eam/micro/defectDocument/deleteDefectDocumentList-----", e);
			return false;
		}
		return true;
    }

	/**
	 * 收藏缺陷单
	 * @param defectDocumentRfCollectorVoList 收藏的缺陷单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectDocument/collect")
	public void collectDefectDocument(@RequestBody List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList) {
		logger.info("/eam/micro/defectDocument/collect, params: [{}]", defectDocumentRfCollectorVoList);

		defectDocumentService.collectDefectDocument(defectDocumentRfCollectorVoList);
	}

	/**
	 * 取消收藏缺陷单
	 * @param defectDocumentRfCollectorVoList 需要取消收藏的缺陷单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/defectDocument/collect/cancel")
	public void cancelCollectDefectDocument(@RequestBody List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList) {
		logger.info("/eam/micro/defectDocument/collect/cancel, params: [{}]", defectDocumentRfCollectorVoList);

		defectDocumentService.cancelCollectDefectDocument(defectDocumentRfCollectorVoList);
	}
}