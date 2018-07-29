package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeter;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterDetail;
import com.enerbos.cloud.eam.microservice.service.DaliyCopyMeterService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源 Copyright: Copyright(C) 2015-2017
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年9月4日 下午4:43:13
 * @Description 日常--抄表管理
 */
@RestController
public class DailyCopyMeterController {
    private final static Logger logger = LoggerFactory
            .getLogger(DailyCopyMeterController.class);

    @Autowired
    private DaliyCopyMeterService daliyCopyMeterService;

    /**
     * 获取抄表信息列表
     *
     * @param dailyCopyMeterFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterFilterVo}
     * @return 返回查询 数据
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeters", method = RequestMethod.POST)
    public PageInfo<DailyCopyMeterVoForList> findCopyMeters(
            @RequestBody DailyCopyMeterFilterVo dailyCopyMeterFilterVo) {

        logger.info("findCopyMeters - - -- > param :{}", dailyCopyMeterFilterVo);
        PageInfo<DailyCopyMeterVoForList> pageInfo = null;
        try {
            pageInfo = daliyCopyMeterService.findCopyMeters(dailyCopyMeterFilterVo);
        } catch (Exception e) {
            logger.error("--------findCopyMeters---------", e);
        }
        return pageInfo;
    }

    /**
     * 新增抄表单
     *
     * @param dailyCopyMeterVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 保存后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeter/saveCopyMeter", method = RequestMethod.POST)
    public DailyCopyMeterVo saveCopyMeter(
            @RequestBody DailyCopyMeterVo dailyCopyMeterVo) throws Exception {
        logger.info("saveCopyMeter -----param:{}", dailyCopyMeterVo);
        Predicate<List> listNotNull = list -> list != null && list.size() > 0;
        DaliyCopyMeter daliyCopyMeter = new DaliyCopyMeter();
        List<DailyCopyMeterDetailVo> daliyCopyMeterDetailVos = dailyCopyMeterVo
                .getCopyMeterDetailVos();
        List<DaliyCopyMeterDetail> daliyCopyMeterDetails = new ArrayList<DaliyCopyMeterDetail>();
        ReflectionUtils.copyProperties(dailyCopyMeterVo, daliyCopyMeter, null);
        if (listNotNull.test(daliyCopyMeterDetailVos)) {
            for (DailyCopyMeterDetailVo dailyCopyMeterDetailVo : daliyCopyMeterDetailVos) {
                dailyCopyMeterDetailVo.setSiteId(dailyCopyMeterVo.getSiteId());
                dailyCopyMeterDetailVo.setOrgId(dailyCopyMeterVo.getOrgId());
                dailyCopyMeterDetailVo.setCreateUser(dailyCopyMeterVo.getCreateUser());
                DaliyCopyMeterDetail daliyCopyMeterDetail = new DaliyCopyMeterDetail();
                String meterId = dailyCopyMeterDetailVo.getMeterId() ;
                DaliyCopyMeterDetail daliyCopyMeterDetailSelect = daliyCopyMeterService.findCopyMeterDetailById(meterId);
                logger.info("-daliyCopyMeterDetailSelect--------{}", daliyCopyMeterDetailSelect);
                if (daliyCopyMeterDetailSelect != null) {
                    dailyCopyMeterDetailVo.setLastNum(daliyCopyMeterDetailSelect.getThisNum());
                    dailyCopyMeterDetailVo.setLastEnteringDate(daliyCopyMeterDetailSelect.getThisEnteringDate());
                    dailyCopyMeterDetailVo.setLastDate(daliyCopyMeterDetailSelect.getThisDate());
                }
                ReflectionUtils.copyProperties(dailyCopyMeterDetailVo, daliyCopyMeterDetail, null);
                logger.info("---------{}", daliyCopyMeterDetail);
                daliyCopyMeterDetails.add(daliyCopyMeterDetail);
            }

        }
        //保存工单
        daliyCopyMeter = daliyCopyMeterService.saveCopyMeter(daliyCopyMeter);
        //保存关联关系
        List<String> meterAddIds = dailyCopyMeterVo.getMeterAddIds();
        DaliyCopyMeter finalDaliyCopyMeter = daliyCopyMeter;
        if (listNotNull.test(meterAddIds)) {
            List<DaliyCopyMeterDetail> meterAddList = meterAddIds.stream().map(meterId -> new DaliyCopyMeterDetail(finalDaliyCopyMeter.getId(), meterId, finalDaliyCopyMeter.getSiteId(), finalDaliyCopyMeter.getOrgId(), finalDaliyCopyMeter.getCreateUser()))
                    .collect(Collectors.toList());
            daliyCopyMeterService.saveCopyMeterDetail(meterAddList);
        }
        List<String> meterDeleteIds = dailyCopyMeterVo.getMeterDeleteIds();
        if (listNotNull.test(meterDeleteIds)) {
            daliyCopyMeterService.deleteCopyMeterDetail((String[]) meterDeleteIds.toArray());
        }
        //保存抄表数据
        if (listNotNull.test(daliyCopyMeterDetails)) {
            for (DaliyCopyMeterDetail daliyCopyMeterDetail : daliyCopyMeterDetails) {
                daliyCopyMeterDetail.setCopyMeterId(daliyCopyMeter.getId());
            }
            daliyCopyMeterDetails = daliyCopyMeterService.saveCopyMeterDetail(daliyCopyMeterDetails);
        }
        dailyCopyMeterVo.setId(daliyCopyMeter.getId());
        return dailyCopyMeterVo;
    }

    /**
     * 修改抄表单
     *
     * @param dailyCopyMeterVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 修改后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeter/updateCopyMeter", method = RequestMethod.POST)
    public DailyCopyMeterVo updateCopyMeter(
            @RequestBody DailyCopyMeterVo dailyCopyMeterVo) {

        logger.info("updateCopyMeter:param:{}", dailyCopyMeterVo);
        DaliyCopyMeter daliyCopyMeter = new DaliyCopyMeter();
        List<DailyCopyMeterDetailVo> daliyCopyMeterDetailVos = dailyCopyMeterVo
                .getCopyMeterDetailVos();
        List<DaliyCopyMeterDetail> daliyCopyMeterDetails = new ArrayList<DaliyCopyMeterDetail>();

        try {
            ReflectionUtils.copyProperties(dailyCopyMeterVo, daliyCopyMeter,
                    null);
            if (daliyCopyMeterDetailVos.size() > 0) {

                for (DailyCopyMeterDetailVo dailyCopyMeterDetailVo : daliyCopyMeterDetailVos) {
                    dailyCopyMeterDetailVo.setSiteId(dailyCopyMeterVo.getSiteId());
                    dailyCopyMeterDetailVo.setOrgId(dailyCopyMeterVo.getOrgId());
                    dailyCopyMeterDetailVo.setCreateUser(dailyCopyMeterVo.getCreateUser());
                    dailyCopyMeterDetailVo.setCopyMeterId(daliyCopyMeter.getId());
                    DaliyCopyMeterDetail daliyCopyMeterDetail = new DaliyCopyMeterDetail();

                    String meterId = dailyCopyMeterDetailVo.getMeterId();

                    DaliyCopyMeterDetail daliyCopyMeterDetailSelect = daliyCopyMeterService.findCopyMeterDetailById(meterId);
                    if (daliyCopyMeterDetailSelect != null) {
                        dailyCopyMeterDetailVo.setLastNum(daliyCopyMeterDetailSelect.getThisNum());
                        dailyCopyMeterDetailVo.setLastDate(daliyCopyMeterDetailSelect.getThisDate());
                    }
                    ReflectionUtils.copyProperties(dailyCopyMeterDetailVo,
                            daliyCopyMeterDetail, null);
                    daliyCopyMeterDetails.add(daliyCopyMeterDetail);
                }
                if (daliyCopyMeterDetails.size() > 0) {
                    daliyCopyMeterDetails = daliyCopyMeterService
                            .saveCopyMeterDetail(daliyCopyMeterDetails);
                }
            }
            daliyCopyMeter = daliyCopyMeterService.saveCopyMeter(daliyCopyMeter);

        } catch (Exception e) {
            logger.error("--------updateCopyMeter---------", e);
        }

        return dailyCopyMeterVo;
    }

    /**
     * 删除抄表单
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeter/deleteCopyMeter", method = RequestMethod.GET)
    boolean deleteCopyMeter(@RequestParam("ids") String[] ids) {
        daliyCopyMeterService.deleteCopyMeter(ids);
        return true;
    }


    /**
     * 根据抄表单id查找抄表信息
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return 数据
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterDetails", method = RequestMethod.GET)
    PageInfo<DailyCopyMeterDetailForList> findCopyMeterDetails(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        logger.info("findCopyMeterDetails - - -- > param :{}", id);
        PageInfo<DailyCopyMeterDetailForList> pageInfo = null;
        try {
            pageInfo = daliyCopyMeterService
                    .findCopyMeterDetails(id, siteId, orgId);
        } catch (Exception e) {
            logger.error("--------findCopyMeterDetails---------", e);
        }
        return pageInfo;
    }


    /**
     * 根据id查找抄表单
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterById", method = RequestMethod.GET)
    public DaliyCopyMeter findCopyMeterById(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        DaliyCopyMeter daliyCopyMeter = null;
        try {
            daliyCopyMeter = daliyCopyMeterService.findCopyMeterById(id);
        } catch (Exception e) {
            logger.error("--------findCopyMeterDetails---------", e);
        }

        return daliyCopyMeter;
    }

    /**
     * 根据id查找抄表单
     *
     * @param ids 抄表单id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterByIds", method = RequestMethod.GET)
    public List<DailyCopyMeterVo> findCopyMeterByIds(@RequestParam("ids") List<String> ids) {
        return daliyCopyMeterService.findCopyMeterByIds(ids);
    }


    /**
     * 根据抄表id查询详细列表
     *
     * @param id 抄表id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterDetailByMeterId", method = RequestMethod.GET)
    public PageInfo<DailyCopyMeterDetailForList> findCopyMeterDetailByMeterId(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        return daliyCopyMeterService.findCopyMeterDetailByMeterId(id, siteId, orgId);
    }

    /**
     * 根据抄表id查询详细列表
     *
     * @param copyMeterId 抄表id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterDetailByCopyMeterId", method = RequestMethod.GET)
    public List<DailyCopyMeterDetailForList> findCopyMeterDetailByCopyMeterId(@RequestParam("copyMeterId") String copyMeterId) {
        List<DailyCopyMeterDetailForList> list = daliyCopyMeterService.findcopymeterDetailByCopyMeterId(copyMeterId);
        return list;
    }

    /**
     * 根据id删除抄表单详细列表
     *
     * @param ids 抄表单id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/deleteCopyMeterDetail", method = RequestMethod.GET)
    public boolean deleteCopyMeterDetail(@RequestParam("ids") String[] ids) {
        try {
            daliyCopyMeterService.deleteCopyMeterDetail(ids);
            return true;
        } catch (Exception e) {
            logger.error("--------deleteCopyMeter---------", e);

        }
        return false;
    }


    /**
     * 修改抄表单状态
     *
     * @param ids 操作的id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/updateCopyMeterStatus", method = RequestMethod.POST)
    public boolean updateCopyMeterStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status) {
        try {
            daliyCopyMeterService.updateCopyMeterStatus(ids, status);
            return true;
        } catch (Exception e) {
            logger.error("--------updateCopyMeterStatus---------", e);

        }
        return false;
    }

    /**
     * 根据id查找抄表流程Vo
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterFlowById")
    public CopyMeterOrderForWorkFlowVo findCopyMeterFlowById(@RequestParam("id") String id) {
        CopyMeterOrderForWorkFlowVo copyMeterOrderForWorkFlowVo = new CopyMeterOrderForWorkFlowVo();
        DaliyCopyMeter copyMeter = daliyCopyMeterService.findCopyMeterById(id);
        BeanUtils.copyProperties(copyMeter, copyMeterOrderForWorkFlowVo);
        return copyMeterOrderForWorkFlowVo;
    }

    @RequestMapping(value = "/eam/micro/copyMeter/saveCopyMeterOrderFlow", method = RequestMethod.POST)
    public CopyMeterOrderForWorkFlowVo saveCopyMeterOrderFlow(@RequestBody CopyMeterOrderForWorkFlowVo copyMeterOrderForWorkFlowVo) {

        DaliyCopyMeter daliyCopyMeter = daliyCopyMeterService.findCopyMeterById(copyMeterOrderForWorkFlowVo.getId());
        if (null == daliyCopyMeter || daliyCopyMeter.getId() == null) {
            throw new EnerbosException("", "工单不存在，先创建！");
        }
        daliyCopyMeter.setStatus(copyMeterOrderForWorkFlowVo.getStatus());
        if (StringUtils.isEmpty(daliyCopyMeter.getProcessInstanceId())) {
            daliyCopyMeter.setProcessInstanceId(copyMeterOrderForWorkFlowVo.getProcessInstanceId());
        }
        daliyCopyMeterService.saveCopyMeter(daliyCopyMeter);
        return copyMeterOrderForWorkFlowVo;
    }

    /**
     * 根据抄表id和仪表id查询相关详细信息
     * @param copyMeterId
     * @param meterId
     * @return
     */
    @RequestMapping(value = {"/eam/micro/copyMeter/findCopyMeterDetailByCopyMeterIdAndMeterId"}, method = RequestMethod.GET)
    public DailyCopyMeterDetailVo findCopyMeterDetailByCopyMeterIdAndMeterId(@RequestParam("copyMeterId") String copyMeterId, @RequestParam("meterId") String meterId) {

        return daliyCopyMeterService.findCopyMeterDetailByCopyMeterIdAndMeterId(copyMeterId,meterId);
    }
}
