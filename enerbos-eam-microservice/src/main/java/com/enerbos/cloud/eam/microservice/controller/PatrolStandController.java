package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.PatrolStand;
import com.enerbos.cloud.eam.microservice.domain.PatrolStandContent;
import com.enerbos.cloud.eam.microservice.service.PatrolStandService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2017-2018
 * Company 北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version EAM2.0
 * @date 2017年8月10日 14:58:19
 * @Description 巡检标准
 */
@RestController
@Api(description = "巡检标准(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class PatrolStandController {
    private static Logger logger = LoggerFactory
            .getLogger(PatrolStandController.class);

    @Autowired
    public PatrolStandService patrolStandService;

    /**
     * 分页查询巡检标准
     *
     * @param patrolStandVoForFilter 巡检标准查询实体
     * @return 分页列表
     */
    @RequestMapping(value = "/eam/micro/patrolStand/findPage", method = RequestMethod.POST)
    public PageInfo<PatrolStandVoForList> findPage(
            @RequestBody PatrolStandVoForFilter patrolStandVoForFilter) {
        logger.info("/eam/micro/patrolStand/findPage ： param : {}",
                patrolStandVoForFilter);
        PageInfo<PatrolStandVoForList> pageInfo = null;

        try {
            pageInfo = patrolStandService.findPage(patrolStandVoForFilter);
        } catch (Exception e) {

            logger.error("--------/eam/micro/patrolStand/findPage-------", e);
        }
        return pageInfo;
    }

    /**
     * 根据ID查询巡检标准
     *
     * @param id 巡检标准id
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolStand/findPatrolStandById", method = RequestMethod.GET)
    public PatrolStand findPatrolStandById(@RequestParam("id") String id) {

        logger.info("/eam/micro/patrolStand/findPatrolStandById---->id:{}", id);
        PatrolStand patrolStand = null;

        try {
            patrolStand = patrolStandService.findPatrolStandById(id);
        } catch (Exception e) {
            logger.error(
                    "------/eam/micro/patrolStand/findPatrolStandById-------",
                    e);
        }
        return patrolStand;
    }

    /**
     * 保存巡检标准
     *
     * @param patrolStandVo 巡检标准实体 {@link com.enerbos.cloud.eam.vo.PatrolStandVo}
     * @return 保存的实体
     */
    @RequestMapping(value = "/eam/micro/patrolStand/savePatrolStand", method = RequestMethod.POST)
    public PatrolStandVo savePatrolStand(
            @RequestBody PatrolStandVo patrolStandVo) {
        logger.info(
                "/eam/micro/patrolStand/savePatrolStand---->patrolStandVo:{}",
                patrolStandVo);

        PatrolStand patrolStand = new PatrolStand();

        try {
            ReflectionUtils.copyProperties(patrolStandVo, patrolStand, null);
            patrolStand = patrolStandService.savePatrolStand(patrolStand);
            patrolStandVo.setId(patrolStand.getId());
        } catch (Exception e) {
            logger.error("---/eam/micro/patrolStand/savePatrolStand-", e);
            return null;
        }

        return patrolStandVo;
    }

    /**
     * 修改巡检标准
     *
     * @param patrolStandVo 巡检标准实体 {@link com.enerbos.cloud.eam.vo.PatrolStandVo}
     * @return 保存的实体
     */
    @RequestMapping(value = "/eam/micro/patrolStand/updatePatrolStand", method = RequestMethod.POST)
    PatrolStandVo updatePatrolStand(@RequestBody PatrolStandVo patrolStandVo) {
        logger.info(
                "/eam/micro/patrolStand/updatePatrolStand---->patrolStandVo:{}",
                patrolStandVo);

        PatrolStand patrolStand = new PatrolStand();

        try {
            ReflectionUtils.copyProperties(patrolStandVo, patrolStand, null);
            patrolStand = patrolStandService.updatePatrolStand(patrolStand);
        } catch (Exception e) {
            logger.error("---/eam/micro/patrolStand/updatePatrolStand-", e);
            return null;
        }
        return patrolStandVo;
    }

    /**
     * 删除巡检标准
     *
     * @param ids 要删除的id数组
     * @return 数据
     */
    @RequestMapping(value = "/eam/micro/patrolStand/deletePatrolStand", method = RequestMethod.POST)
    boolean deletePatrolStand(@RequestParam("ids") String[] ids) {
        try {
            patrolStandService.deletePatrolStand(ids);
            return true;
        } catch (Exception e) {
            logger.error("---/eam/micro/patrolStand/deletePatrolStand---", e);
            return false;
        }
    }

    /**
     * 根据巡检标准id查找巡检标准内容
     *
     * @param id       巡检标准id
     * @param pageSize 条数
     * @param pageNum  页数
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/patrolStand/findPatrolStandContent", method = RequestMethod.POST)
    PageInfo<PatrolStandContentVoForList> findPatrolStandContent(String id,
                                                                 Integer pageSize, Integer pageNum) {
        PageInfo<PatrolStandContentVoForList> pageInfo = null;
        try {
            pageInfo = patrolStandService.findPatrolStandContent(id, pageSize,
                    pageNum);
        } catch (Exception e) {
            logger.error(
                    "---/eam/micro/patrolStand/findPatrolStandContent-----", e);
        }
        return pageInfo;

    }

    /**
     * 保存巡检内容
     *
     * @param patrolStandContentVo {@link com.enerbos.cloud.eam.vo.PatrolStandContentVo}
     * @return 保存后实体
     */
    @RequestMapping(value = "/eam/micro/patrolStand/savePatrolStandContent", method = RequestMethod.POST)
    PatrolStandContentVo savePatrolStandContent(@RequestBody
                                                        PatrolStandContentVo patrolStandContentVo) {
        logger.info(
                "/eam/micro/patrolStand/savePatrolStand---->patrolStandContentVo:{}",
                patrolStandContentVo);

        PatrolStandContent patrolStandContent = new PatrolStandContent();

        try {
            ReflectionUtils.copyProperties(patrolStandContentVo,
                    patrolStandContent, null);
            patrolStandContent = patrolStandService
                    .savePatrolStandContent(patrolStandContent);
            patrolStandContentVo.setId(patrolStandContent.getId());
        } catch (Exception e) {
            logger.error("---/eam/micro/patrolStand/savePatrolStand-", e);
            return null;
        }
        return patrolStandContentVo;

    }

    /**
     * 修改巡检内容
     *
     * @param patrolStandContentVo {@link com.enerbos.cloud.eam.vo.PatrolStandContentVo}
     * @return 修改后实体
     */
    @RequestMapping(value = "/eam/micro/patrolStand/updatePatrolStandContent", method = RequestMethod.POST)
    PatrolStandContentVo updatePatrolStandContent(@RequestBody
                                                          PatrolStandContentVo patrolStandContentVo) {
        logger.info(
                "/eam/micro/patrolStand/updatePatrolStandContent---->patrolStandContentVo:{}",
                patrolStandContentVo);

        PatrolStandContent patrolStandContent = new PatrolStandContent();

        try {
            ReflectionUtils.copyProperties(patrolStandContentVo,
                    patrolStandContent, null);
            patrolStandContent = patrolStandService
                    .updatePatrolStandContent(patrolStandContent);
        } catch (Exception e) {
            logger.error("---/eam/micro/patrolStand/updatePatrolStandContent-",
                    e);
            return null;
        }
        return patrolStandContentVo;
    }

    /**
     * 根据id查询巡检标准内容
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolStand/findPatrolStandContentById", method = RequestMethod.POST)
    PatrolStandContent findPatrolStandContentById(@RequestParam("id") String id) {
        logger.info("/eam/micro/patrolStand/findPatrolStandContentById---->id:{}", id);
        PatrolStandContent patrolStandContent = null;

        try {
            patrolStandContent = patrolStandService.findPatrolStandContentById(id);
        } catch (Exception e) {
            logger.error(
                    "------/eam/micro/patrolStand/findPatrolStandContentById-------",
                    e);
        }
        return patrolStandContent;
    }

    /**
     * 删除巡检标准内容
     *
     * @param ids id合集
     * @return boolean 成功 失败
     */
    @RequestMapping(value = "/eam/micro/patrolStand/deletePatrolStandContent", method = RequestMethod.POST)
    boolean deletePatrolStandContent(@RequestParam("ids") String[] ids) {
        try {
            patrolStandService.deletePatrolStandContent(ids);
            return true;
        } catch (Exception e) {
            logger.error(
                    "---/eam/micro/patrolStand/deletePatrolStandContent---", e);
            return false;
        }
    }

}
