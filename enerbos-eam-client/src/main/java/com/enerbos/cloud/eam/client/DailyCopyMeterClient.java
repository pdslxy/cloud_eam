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

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年9月1日 10:43:01
 * @Description 抄表管理client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = CopyMeterClientFallback.class)
public interface DailyCopyMeterClient {


    /**
     * 获取抄表信息列表
     *
     * @param dailyCopyMeterFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterFilterVo}
     * @return 返回查询 数据
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeters", method = RequestMethod.POST)
    EnerbosPage<DailyCopyMeterVoForList> findCopyMeters(@RequestBody DailyCopyMeterFilterVo dailyCopyMeterFilterVo);

    /**
     * 新增抄表单
     *
     * @param dailyCopyMeterVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 保存后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeter/saveCopyMeter", method = RequestMethod.POST)
    DailyCopyMeterVo saveCopyMeter(@RequestBody DailyCopyMeterVo dailyCopyMeterVo);

    /**
     * 修改抄表单
     *
     * @param dailyCopyMeterVo 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 修改后实体
     */
    @RequestMapping(value = "/eam/micro/copyMeter/updateCopyMeter", method = RequestMethod.POST)
    DailyCopyMeterVo updateCopyMeter(@RequestBody DailyCopyMeterVo dailyCopyMeterVo);


    /**
     * 删除抄表单
     *
     * @param ids 要删除的记录id
     * @return 删除结果
     */
    @RequestMapping(value = "/eam/micro/copyMeter/deleteCopyMeter", method = RequestMethod.GET)
    boolean deleteCopyMeter(@RequestParam("ids") String[] ids);

    /**
     * 根据抄表单id查找抄表信息
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return 数据
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterDetails", method = RequestMethod.GET)
    EnerbosPage<DailyCopyMeterDetailForList> findCopyMeterDetails(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);

    /**
     * 根据id查找抄表单
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterById", method = RequestMethod.GET)
    DailyCopyMeterVo findCopyMeterById(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);

    /**
     * 根据id查找抄表单
     *
     * @param ids 抄表单id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterByIds", method = RequestMethod.GET)
    List<DailyCopyMeterVo> findCopyMeterByIds(@RequestParam("ids") List<String> ids);

    /**
     * 根据id删除抄表单详细列表
     *
     * @param ids 抄表单id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/deleteCopyMeterDetail", method = RequestMethod.GET)
    boolean deleteCopyMeterDetail(@RequestParam("ids") String[] ids);


    /**
     * 修改抄表单状态
     *
     * @param ids 操作的id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/updateCopyMeterStatus", method = RequestMethod.POST)
    boolean updateCopyMeterStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status);


    /**
     * 查询仪表记录
     *
     * @param id 操作的id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterDetailByMeterId", method = RequestMethod.GET)
    EnerbosPage<DailyCopyMeterDetailForList> findCopyMeterDetailByMeterId(@RequestParam("id") String id, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId);

    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterDetailByCopyMeterId", method = RequestMethod.GET)
    List<DailyCopyMeterDetailForList> findCopyMeterDetailByCopyMeterId(@RequestParam("copyMeterId") String copyMeterId);

    /**
     * 收藏
     *
     * @param id
     * @param eamProdId
     * @param type
     * @param personId
     */
    @RequestMapping(value = "/eam/micro/copyMeter/collect", method = RequestMethod.POST)
    void collect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    /**
     * 取消收藏
     *
     * @param id
     * @param eamProdId
     * @param type
     * @param personId
     */
    @RequestMapping(value = "/eam/micro/copyMeter/cancelCollect", method = RequestMethod.POST)
    void cancelCollect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    /**
     * 根据id查找抄表流程Vo
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/copyMeter/findCopyMeterFlowById")
    CopyMeterOrderForWorkFlowVo findCopyMeterFlowById(@RequestParam("id") String id);

    @RequestMapping(value = "/eam/micro/copyMeter/saveCopyMeterOrderFlow", method = RequestMethod.POST)
    CopyMeterOrderForWorkFlowVo saveCopyMeterOrderFlow(@RequestBody CopyMeterOrderForWorkFlowVo copyMeterOrderForWorkFlowVo);


    @RequestMapping(value = {"/eam/micro/copyMeter/findCopyMeterDetailByCopyMeterIdAndMeterId"},method = RequestMethod.GET)
    DailyCopyMeterDetailVo findCopyMeterDetailByCopyMeterIdAndMeterId(@RequestParam("copyMeterId") String var1, @RequestParam("meterId") String var2);
}


@Component
class CopyMeterClientFallback implements FallbackFactory<DailyCopyMeterClient> {

    @Override
    public DailyCopyMeterClient create(Throwable cause) {
        return new DailyCopyMeterClient() {
            @Override
            public EnerbosPage<DailyCopyMeterVoForList> findCopyMeters(DailyCopyMeterFilterVo dailyCopyMeterFilterVo) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterVo saveCopyMeter(DailyCopyMeterVo dailyCopyMeterVo) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterVo updateCopyMeter(DailyCopyMeterVo dailyCopyMeterVo) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean deleteCopyMeter(String[] ids) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public EnerbosPage<DailyCopyMeterDetailForList> findCopyMeterDetails(String id, String siteId, String orgId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterVo findCopyMeterById(String id, String siteId, String orgId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public List<DailyCopyMeterVo> findCopyMeterByIds(List<String> ids) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean deleteCopyMeterDetail(String[] ids) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public boolean updateCopyMeterStatus(String[] ids, String status) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public EnerbosPage<DailyCopyMeterDetailForList> findCopyMeterDetailByMeterId(String id, String siteId, String orgId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public List<DailyCopyMeterDetailForList> findCopyMeterDetailByCopyMeterId(String copyMeterId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public void collect(String id, String eamProdId, String type, String personId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public void cancelCollect(String id, String eamProdId, String type, String personId) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public CopyMeterOrderForWorkFlowVo findCopyMeterFlowById(String id) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public CopyMeterOrderForWorkFlowVo saveCopyMeterOrderFlow(CopyMeterOrderForWorkFlowVo copyMeterOrder) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public DailyCopyMeterDetailVo findCopyMeterDetailByCopyMeterIdAndMeterId(String var1, String var2) {
                return null;
            }
        };
    }
}