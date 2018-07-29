package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.PatrolRecordTermService;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/28
 * @Description
 */
@RestController
public class PatrolRecordTermController {

    private static Logger logger = LoggerFactory.getLogger(PatrolRecordTermController.class);


    @Autowired
    private PatrolRecordTermService patrolRecordTermService;

    /**
     * 根据id查询巡检记录表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecordTerm/findById", method = RequestMethod.GET)
    public PatrolRecordTermVo findById(@RequestParam("id") String id) {
        try {
            return patrolRecordTermService.findById(id);
        } catch (Exception e) {
            logger.debug("-------/patrolRecordTerm/findById--------------", e);
        }
        return null;
    }


    /**
     * 保存巡检记录表
     *
     * @param patrolRecordTermVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecordTerm/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PatrolRecordTermVo patrolRecordTermVo) {
        try {
            patrolRecordTermService.saveOrUpdate(patrolRecordTermVo);
        } catch (Exception e) {
            logger.debug("-------/patrolRecordTerm/saveOrUpdate--------------", e);
        }
    }

    /**
     * 根据工单id查询巡检记录中巡检项内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecordTerm/findPatrolTermByOrderAndPoint", method = RequestMethod.GET)
    public PageInfo<PatrolRecordTermVo> findPatrolTermByOrderAndPoint(@RequestParam("id") String id, @RequestParam("pointid") String pointid){
        PageInfo<PatrolRecordTermVo> pageInfo = null;
        try {
            pageInfo = patrolRecordTermService.findPatrolTermByOrderAndPoint(id,pointid);
        } catch (Exception e) {
            logger.debug("-------/patrolRecordTerm/findPatrolTermByOrderAndPoint--------------", e);
        }
        return pageInfo;
    }
}
