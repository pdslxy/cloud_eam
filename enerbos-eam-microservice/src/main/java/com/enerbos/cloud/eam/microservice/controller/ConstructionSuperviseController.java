package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.ConstructionSupervise;
import com.enerbos.cloud.eam.microservice.service.ConstructionService;
import com.enerbos.cloud.eam.microservice.service.ConstructionSuperviseService;
import com.enerbos.cloud.eam.vo.ConstructionSuperviseVo;
import com.enerbos.cloud.eam.vo.ConstructionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0.0
 * @date 2017年9月9日
 * @Description 施工单监管记录接口
 */
@RestController
public class ConstructionSuperviseController {

    private Logger logger = LoggerFactory.getLogger(ConstructionSuperviseController.class);

    @Resource
    protected ConstructionService constructionService;

    @Autowired
    private ConstructionSuperviseService constructionSuperviseService;

    /**
     * saveConstruction:保存监管记录
     *
     * @param constructionSuperviseVoList {@link ConstructionVo}
     * @return
     */
    @RequestMapping(value = "/eam/micro/constructionSupervise/saveConstructionSuperviseList", method = RequestMethod.POST)
    public boolean saveConstructionSuperviseList(@RequestBody List<ConstructionSuperviseVo> constructionSuperviseVoList) {
        try {
            List<ConstructionSupervise> constructionSupervises = new ArrayList<ConstructionSupervise>();
            for (ConstructionSuperviseVo constructionSuperviseVo : constructionSuperviseVoList) {
                ConstructionSupervise constructionSupervise = new ConstructionSupervise();
                BeanUtils.copyProperties(constructionSuperviseVo, constructionSupervise);
                constructionSupervises.add(constructionSupervise);
            }
            constructionSuperviseService.saveConstructionSupervise(constructionSupervises);
            return true;
        } catch (Exception e) {
            logger.error("/eam/micro/constructionSupervise/saveConstructionSuperviseList", e);
            return false;
        }
    }

    /**
     * findConstructionById:根据ID查询施工单
     *
     * @param constructionId
     * @return ConstructionVo 施工单VO
     */
    @RequestMapping(value = "/eam/micro/constructionSupervise/findConstructionSuperviseByConstructionId", method = RequestMethod.GET)
    public List<ConstructionSuperviseVo> findConstructionSuperviseByConstructionId(@RequestParam("constructionId") String constructionId) {
        List<ConstructionSuperviseVo> constructionSuperviseVos = new ArrayList<>();
        try {
            List<ConstructionSupervise> list = constructionSuperviseService.findConstructionSuperviseByConstructionId(constructionId);
            if (list != null && list.size() > 0) {
                for (ConstructionSupervise constructionSupervise : list) {
                    ConstructionSuperviseVo constructionSuperviseVo = new ConstructionSuperviseVo();
                    BeanUtils.copyProperties(constructionSupervise, constructionSuperviseVo);
                    constructionSuperviseVos.add(constructionSuperviseVo);
                }
            }
        } catch (Exception e) {
            logger.error("/eam/micro/constructionSupervise/findConstructionSuperviseByConstructionId", e);
        }
        return constructionSuperviseVos;
    }

    /**
     * findConstructionCommitByConstructionNum: 根据constructionNum查询施工单-工单提报
     *
     * @param ids 施工单编码
     * @return ConstructionForCommitVo 施工单-工单提报
     */
    @RequestMapping(value = "/eam/micro/constructionSupervise/deleteConstructionSuperviseList", method = RequestMethod.POST)
    public Boolean deleteConstructionSupervise(@RequestParam("ids") List<String> ids) {
        try {
            constructionSuperviseService.deleteConstructionSuperviseByIds(ids);
            return true;
        } catch (Exception e) {
            logger.error("/eam/micro/constructionSupervise/deleteConstructionSuperviseList", e);
            return false;
        }
    }

}