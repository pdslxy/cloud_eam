package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.Construction;
import com.enerbos.cloud.eam.microservice.service.ConstructionService;
import com.enerbos.cloud.eam.vo.ConstructionForFilterVo;
import com.enerbos.cloud.eam.vo.ConstructionForListVo;
import com.enerbos.cloud.eam.vo.ConstructionRfCollectorVo;
import com.enerbos.cloud.eam.vo.ConstructionVo;
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
 * @date 2017年9月9日
 * @Description 施工单接口
 */
@RestController
public class ConstructionController {

    private Logger logger = LoggerFactory.getLogger(ConstructionController.class);

    @Resource
    protected ConstructionService constructionService;

    /**
     * saveConstruction:保存施工单
     *
     * @param constructionVo {@link ConstructionVo}
     * @return ConstructionVo
     */
    @RequestMapping(value = "/eam/micro/construction/saveConstruction", method = RequestMethod.POST)
    public ConstructionVo saveConstruction(@RequestBody ConstructionVo constructionVo) {
        ConstructionVo constructionVoSave = new ConstructionVo();
        try {
            Construction construction = new Construction();
            if (StringUtils.isNotBlank(construction.getId())) {
                construction = constructionService.findConstructionByID(constructionVo.getId());
                if (null == construction) {
                    throw new EnerbosException("", "工单不存在，先创建！");
                }
            }
            ReflectionUtils.copyProperties(constructionVo, construction, null);
            Construction constructionSave = constructionService.save(construction);
            ReflectionUtils.copyProperties(constructionSave, constructionVoSave, null);
        } catch (Exception e) {
            logger.error("-------/eam/micro/construction/saveConstruction-----", e);
        }
        return constructionVoSave;
    }

    /**
     * findConstructionById:根据ID查询施工单
     *
     * @param id
     * @return ConstructionVo 施工单VO
     */
    @RequestMapping(value = "/eam/micro/construction/findConstructionById", method = RequestMethod.GET)
    public ConstructionVo findConstructionById(@RequestParam("id") String id) {
        ConstructionVo constructionVo = new ConstructionVo();
        try {
            Construction construction = constructionService.findConstructionByID(id);
            if (null == construction || construction.getId() == null) {
                return new ConstructionVo();
            }
            ReflectionUtils.copyProperties(construction, constructionVo, null);
        } catch (Exception e) {
            logger.error("-------/eam/micro/construction/findConstructionById-----", e);
        }
        return constructionVo;
    }

    /**
     * findConstructionCommitByConstructionNum: 根据constructionNum查询施工单-工单提报
     *
     * @param constructionNum 施工单编码
     * @return ConstructionForCommitVo 施工单-工单提报
     */
    @RequestMapping(value = "/eam/micro/construction/findConstructionByConstructionNum", method = RequestMethod.GET)
    public ConstructionVo findConstructionByConstructionNum(@RequestParam("constructionNum") String constructionNum) {
        return constructionService.findConstructionByConstructionNum(constructionNum);
    }

    /**
     * findPageConstructionList: 分页查询施工单
     *
     * @param constructionForFilterVo 查询条件 {@link ConstructionForFilterVo}
     * @return PageInfo<ConstructionForListVo> 预防施工单集合
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/findPageConstructionList")
    public PageInfo<ConstructionForListVo> findPageConstructionList(@RequestBody ConstructionForFilterVo constructionForFilterVo) {
        List<ConstructionForListVo> result = constructionService.findPageConstructionList(constructionForFilterVo);
        return new PageInfo<ConstructionForListVo>(result);
    }

    /**
     * deleteConstructionList:删除选中的施工单
     *
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/construction/deleteConstructionList", method = RequestMethod.POST)
    public Boolean deleteConstructionList(@RequestBody List<String> ids) {
        try {
            constructionService.deleteConstructionByIds(ids);
        } catch (Exception e) {
            logger.error("-------/eam/micro/construction/deleteConstructionList-----", e);
            return false;
        }
        return true;
    }

    /**
     * 收藏施工单
     *
     * @param constructionRfCollectorVoList 收藏的施工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/collect")
    public void collectConstruction(@RequestBody List<ConstructionRfCollectorVo> constructionRfCollectorVoList) {
        logger.info("/eam/micro/construction/collect, params: [{}]", constructionRfCollectorVoList);

        constructionService.collectConstruction(constructionRfCollectorVoList);
    }

    /**
     * 取消收藏施工单
     *
     * @param constructionRfCollectorVoList 需要取消收藏的施工单列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/collect/cancel")
    public void cancelCollectConstruction(@RequestBody List<ConstructionRfCollectorVo> constructionRfCollectorVoList) {
        logger.info("/eam/micro/construction/collect/cancel, params: [{}]", constructionRfCollectorVoList);

        constructionService.cancelCollectConstruction(constructionRfCollectorVoList);
    }

    /**
     * 查询施工单是否收藏
     *
     * @param
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/construction/checkCollect")
    public boolean checkCollect(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("personId") String personId) {
        return constructionService.checkIsCollect(id, productId, personId);
    }
}