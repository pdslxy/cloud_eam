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

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年11月18日 10:30:26
 * @Description 抄表计划client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = CopyMeterPlanClientFallback.class)
public interface DailyCopyMeterPlanClient {


    /**
     * 获取抄表信息列表
     *
     * @param dailyCopyMeterPlanFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanFilterVo}
     * @return 返回查询 数据
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlans", method = RequestMethod.POST)
    EnerbosPage<DailyCopyMeterPlanVoForList> findCopyMeterPlans(@RequestBody DailyCopyMeterPlanFilterVo dailyCopyMeterPlanFilterVo);

    /**
     * 新增抄表计划
     *
     * @param dailyCopyMeterPlanVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVo}
     * @return 保存后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/saveCopyMeterPlan", method = RequestMethod.POST)
    DailyCopyMeterPlanVo saveCopyMeterPlan(@RequestBody DailyCopyMeterPlanVo dailyCopyMeterPlanVo);



    /**
     * 修改抄表计划
     *
     * @param dailyCopyMeterPlanVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterPlanVo}
     * @return 修改后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/updateCopyMeterPlan", method = RequestMethod.POST)
    DailyCopyMeterPlanVo updateCopyMeterPlan(@RequestBody DailyCopyMeterPlanVo dailyCopyMeterPlanVo);


    /**
     * 删除抄表计划
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlan", method = RequestMethod.GET)
    boolean deleteCopyMeterPlan(@RequestParam("ids") String[] ids);

    /**
     * 根据id查找抄表计划
     *
     * @param id     抄表计划id
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanById", method = RequestMethod.GET)
    DailyCopyMeterPlanVo findCopyMeterPlanById(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);


    /**
     * 修改抄表计划状态
     *
     * @param ids 操作的id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/updateCopyMeterPlanStatus", method = RequestMethod.POST)
    boolean updateCopyMeterPlanStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status);

    /**
     * 删除抄表
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlanRequencyById", method = RequestMethod.POST)
    boolean deleteCopyMeterPlanRequencyById(@RequestParam("ids")String[] ids);

    /**
     * 删除抄表
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/deleteCopyMeterPlanMeterRelationById", method = RequestMethod.POST)
    boolean deleteCopyMeterPlanMeterRelationById(String[] ids);

    /**
     * 根据id查询频率
     * @param id 抄表计划id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanRequencyVosById")
    EnerbosPage<DailyCopyMeterPlanRequencyVo> findCopyMeterPlanRequencyVosById(@RequestParam("id") String id);

    /**
     * 根据id查询仪表与计划
     * @param id 抄表计划id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeterPlan/findCopyMeterPlanMeterRelationVosById", method = RequestMethod.POST)
    EnerbosPage<DaliyCopyMeterPlanMeterRelationVo> findCopyMeterPlanMeterRelationVosById(@RequestParam("id") String id);
}


@Component
class CopyMeterPlanClientFallback implements FallbackFactory<DailyCopyMeterPlanClient> {

    @Override
    public DailyCopyMeterPlanClient create(Throwable cause) {
        return  new DailyCopyMeterPlanClient() {
            @Override
            public EnerbosPage<DailyCopyMeterPlanVoForList> findCopyMeterPlans(DailyCopyMeterPlanFilterVo dailyCopyMeterPlanFilterVo) {
               throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterPlanVo saveCopyMeterPlan(DailyCopyMeterPlanVo dailyCopyMeterPlanVo) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterPlanVo updateCopyMeterPlan(DailyCopyMeterPlanVo dailyCopyMeterPlanVo) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean deleteCopyMeterPlan(String[] ids) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterPlanVo findCopyMeterPlanById(String id, String siteId, String orgId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean updateCopyMeterPlanStatus(String[] ids, String status) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean deleteCopyMeterPlanRequencyById(String[] ids) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean deleteCopyMeterPlanMeterRelationById(String[] ids) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public EnerbosPage<DailyCopyMeterPlanRequencyVo> findCopyMeterPlanRequencyVosById(@RequestParam("id") String id) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public EnerbosPage<DaliyCopyMeterPlanMeterRelationVo> findCopyMeterPlanMeterRelationVosById(@RequestParam("id") String id) {
                throw new RuntimeException(cause.getMessage());
            }
        } ;
    }
}