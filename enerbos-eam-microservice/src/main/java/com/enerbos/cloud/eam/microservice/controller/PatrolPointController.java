package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.PatrolPoint;
import com.enerbos.cloud.eam.microservice.service.PatrolPointService;
import com.enerbos.cloud.eam.microservice.service.PatrolRecordTermService;
import com.enerbos.cloud.eam.microservice.service.PatrolTermService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
@RestController
public class PatrolPointController {
    private static Logger logger = LoggerFactory.getLogger(PatrolPointController.class);

    @Autowired
    private PatrolPointService patrolPointService;

    @Autowired
    private PatrolTermService patrolTermService;

    @Autowired
    private PatrolRecordTermService patrolRecordTermService;

    /**
     * 查询巡检点台账列表
     *
     * @param patrolPointVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPage", method = RequestMethod.POST)
    public PageInfo<PatrolPointVo> findPage(@RequestBody PatrolPointVoForFilter patrolPointVoForFilter) {
        PageInfo<PatrolPointVo> pageInfo = null;
        try {
            pageInfo = patrolPointService.findPatrolPointList(patrolPointVoForFilter);
        } catch (Exception e) {
            logger.error("-------/patrolPoint/findPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 删除巡检点
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/deleteByIds", method = RequestMethod.POST)
    public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
        try {
            patrolPointService.deletePatrolPointByIds(ids);
            return "success";
        } catch (Exception e) {
            logger.error("-------/patrolPoint/deleteByIds--------------", e);
            return e.getMessage();
        }
    }

    /**
     * 查询巡检项列表
     *
     * @param patrolTermVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolTermPage", method = RequestMethod.POST)
    public PageInfo<PatrolTermVo> findPatrolTermPage(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter) {
        PageInfo<PatrolTermVo> pageInfo = null;
        try {
            pageInfo = patrolTermService.findPatrolTermList(patrolTermVoForFilter);
        } catch (Exception e) {
            logger.error("-------/patrolPoint/findPatrolTermPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 查询巡检记录列表
     *
     * @param patrolRecordTermVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolRecordTermPage", method = RequestMethod.POST)
    public PageInfo<PatrolRecordTermVo> findPatrolRecordTermPage(@RequestBody PatrolRecordTermVoForFilter patrolRecordTermVoForFilter) {
        PageInfo<PatrolRecordTermVo> pageInfo = null;
        try {
            pageInfo = patrolRecordTermService.findPatrolRecordTerm(patrolRecordTermVoForFilter);
        } catch (Exception e) {
            logger.error("-------/patrolPoint/findPatrolRecordTermPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 保存巡检点和关联的巡检项信息
     *
     * @param patrolPointForSaveVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/saveOrUpdate", method = RequestMethod.POST)
    public PatrolPointVo saveOrUpdate(@RequestBody PatrolPointForSaveVo patrolPointForSaveVo) {
        try {
            PatrolPointVo patrolPointVo = patrolPointService.saveOrUpdate(patrolPointForSaveVo);
            return patrolPointVo;
        } catch (Exception e) {
            logger.error("-------/patrolPoint/findPage--------------", e);
        }
        return null;
    }

    /**
     * findPointAndTermById:根据ID查询巡检点台账-巡检点
     *
     * @param patrolTermVoForFilter
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolPointVoById", method = RequestMethod.POST)
    public PatrolPointVo findPatrolPointVoById(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter) {
        PatrolPoint patrolPoint = null;
        PatrolPointVo patrolPointVo = new PatrolPointVo();
        try {
            patrolPoint = patrolPointService.findPatrolPointById(patrolTermVoForFilter.getPatrolPointId());
            if (null != patrolPoint) {
                BeanUtils.copyProperties(patrolPoint, patrolPointVo);
                PageInfo<PatrolTermVo> patrolTermList = patrolTermService.findPatrolTermList(patrolTermVoForFilter);
                if (patrolTermList != null) {
                    patrolPointVo.setPatrolTermVolist(patrolTermList.getList());
                }
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/findPointVoById ------", e);
        }
        return patrolPointVo;
    }

    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param ids
     * @param qrCodeNum
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findByIdAndQrCodeNumAndSiteId", method = RequestMethod.POST)
    public List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(@RequestParam(value = "ids", required = false) String[] ids, @RequestParam(value = "qrCodeNum", required = false) String qrCodeNum, @RequestParam(value = "siteId", required = false) String siteId) {
        List<PatrolPointVo> patrolPointVolist = new ArrayList<>();
        try {
            patrolPointVolist = patrolPointService.findByIdAndQrCodeNumAndSiteId(ids, qrCodeNum, siteId);
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/findByIdAndQrCodeNumAndSiteId ------", e);
        }
        return patrolPointVolist;
    }


    /**
     * 根据工单id查询巡检记录中巡检项内容
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolRecordByOrderAndPoint", method = RequestMethod.GET)
   public  PatrolRecordVo findPatrolRecordByOrderAndPoint(@RequestParam("id") String orderid, @RequestParam("pointid") String pointid){
        PatrolRecordVo patrolRecordVo = null;
        try {
            patrolRecordVo = patrolPointService.findPatrolRecordByOrderAndPoint(orderid,pointid);
        } catch (Exception e) {
            logger.error("-------/patrolPoint/findPatrolTermByOrderAndPoint--------------", e);
        }
        return patrolRecordVo;
    }




    /**
     * 设置是否更新状态
     *
     * @param id
     * @param siteId
     * @param b
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/updateIsupdatedata", method = RequestMethod.POST)
    public boolean updateIsupdatedata(String id, String siteId, boolean b) {
        try {
            patrolPointService.updateIsupdatedata(id, siteId, b);
            return true;
        } catch (Exception e) {
            logger.error("-----/eam/open/patrolPoint/updateIsupdatedata ------", e);
        }
        return false;
    }

    @RequestMapping(value = "/eam/micro/patrolPoint/generateQrcode")
    public void generateQrcode(@RequestParam("ids")List<String> ids){
        try {
            patrolPointService.generateQrcode(ids);
        } catch (Exception e) {
            logger.error("-----/eam/micro/patrolPoint/generateQrcode ------", e);
        }
    }


}
