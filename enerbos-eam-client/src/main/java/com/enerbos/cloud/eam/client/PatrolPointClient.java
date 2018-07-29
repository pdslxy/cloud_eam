package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolPointClientFallback.class)
public interface PatrolPointClient {
    /**
     * 根据过滤条和分页信息获取巡检点列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPage", method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<PatrolPointVo> findPatrolPointList(@RequestBody PatrolPointVoForFilter patrolPointVoForFilter);

    /**
     * 删除巡检点
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public abstract String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids);

    /**
     * 获取巡检项列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolTermPage", method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<PatrolTermVo> findPatrolTermPage(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter);

    /**
     * 获取巡检记录列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolRecordTermPage", method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<PatrolRecordTermVo> findPatrolRecordTermPage(@RequestBody PatrolRecordTermVoForFilter patrolRecordTermVoForFilter);

    /**
     * 新增或更新巡检点
     *
     * @param patrolPointForSaveVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public abstract PatrolPointVo saveOrUpdate(@RequestBody PatrolPointForSaveVo patrolPointForSaveVo);

    /**
     * 根据ID查询巡检点台账-巡检点
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolPointVoById", method = RequestMethod.POST)
    @ResponseBody
    public abstract PatrolPointVo findPatrolPointVoById(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter);

    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findByIdAndQrCodeNumAndSiteId", method = RequestMethod.POST)
    @ResponseBody
    public abstract List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(@RequestParam(value = "ids", required = false) String[] ids, @RequestParam(value = "qrCodeNum", required = false) String qrCodeNum, @RequestParam(value = "siteId", required = false) String siteId);

    /**
     * 设置是否更新状态
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/updateIsupdatedata", method = RequestMethod.POST)
    @ResponseBody
    public abstract boolean updateIsupdatedata(@RequestParam(value = "id", required = false) String id, @RequestParam("siteId") String siteId, @RequestParam("b") boolean b);


    /**
     * 设置是否更新状态
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPoint/findPatrolRecordByOrderAndPoint", method = RequestMethod.GET)
    PatrolRecordVo findPatrolRecordByOrderAndPoint(@RequestParam("id") String id, @RequestParam("pointid") String pointid);

    @RequestMapping(value = "/eam/micro/patrolPoint/generateQrcode")
    void generateQrcode(@RequestParam("ids")List<String> ids);
}

@Component
class PatrolPointClientFallback implements FallbackFactory<PatrolPointClient> {

    @Override
    public PatrolPointClient create(Throwable throwable) {
        return new PatrolPointClient() {
            @Override
            public EnerbosPage<PatrolPointVo> findPatrolPointList(@RequestBody PatrolPointVoForFilter patrolPointVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public EnerbosPage<PatrolTermVo> findPatrolTermPage(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public EnerbosPage<PatrolRecordTermVo> findPatrolRecordTermPage(@RequestBody PatrolRecordTermVoForFilter patrolRecordTermVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public PatrolPointVo saveOrUpdate(@RequestBody PatrolPointForSaveVo patrolPointForSaveVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public PatrolPointVo findPatrolPointVoById(@RequestBody PatrolTermVoForFilter patrolTermVoForFilter) {
                return null;
            }

            @Override
            public List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(@RequestParam(value = "ids", required = false) String[] ids, @RequestParam(value = "qrCodeNum", required = false) String qrCodeNum, @RequestParam(value = "siteId", required = false) String siteId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public boolean updateIsupdatedata(@RequestParam(value = "id", required = false) String id, @RequestParam("siteId") String siteId, @RequestParam("b") boolean b) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public PatrolRecordVo findPatrolRecordByOrderAndPoint(@RequestParam("id") String id, @RequestParam("pointid") String pointid) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void generateQrcode(List<String> ids) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}