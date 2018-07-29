package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/10 11:32
 * @Description 总部计划client
 */

@FeignClient(name = "enerbos-eam-microservice", fallback = HeadquartersPlanClientFallback.class)
public interface HeadquartersPlanClient {

    /**
     * 总部计划--分页、筛选、排序
     * @param filter
     * @return
     */


    @RequestMapping(value = "/eam/micro/headquartersPlan/findPageList", method = RequestMethod.POST)
    EnerbosPage<HeadquartersPlanVo> findPageList(@RequestBody HeadquartersPlanVoForFilter filter);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/delete", method = RequestMethod.POST)
    Boolean batchDelete(@RequestParam("ids") List<String> ids);

    /**
     * 批量修改状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/upStrtus", method = RequestMethod.POST)
    Boolean upStatus(@RequestBody HeadquartersPlanVoForUpStatus vo);
    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findDetail", method = RequestMethod.POST)
    HeadquartersPlanVo findDetail(
            @RequestParam("id") String id,
            @RequestParam(value = "orgId") String orgId,
            @RequestParam(value = "siteId") String siteId
    );

    /**
     * 根据ID查询详细
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findDetailById", method = RequestMethod.POST)
    HeadquartersPlanVo findDetailById(
            @RequestParam("id") String id
    );

    /**
     * 根据条件查询符合条件的数据集合
     * @param ids  id集合
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/findDetailByIds", method = RequestMethod.POST)
    List<HeadquartersPlanVo> findDetailByIds(
            @RequestParam("ids") List ids,
            @RequestParam(value = "orgId") String orgId,
            @RequestParam(value = "siteId") String siteId
            );

    /**
     * 保存
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/save", method = RequestMethod.POST)
    HeadquartersPlanVoForSave save(@RequestBody HeadquartersPlanVoForSave filter);

    /**
     * 总部计划--批量保存
     * @param filters
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/batchSave", method = RequestMethod.POST)
    List<HeadquartersPlanVoForSave> batchSave(@RequestBody List<HeadquartersPlanVoForSave> filters);

    /**
     * 批量下达
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/batchRelease", method = RequestMethod.POST)
    public Boolean batchRelease(@RequestParam("ids") List<String> ids);
    /**
     * 生成例行工作
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/createRoutineWork", method = RequestMethod.POST)
    Boolean createRoutineWork(@RequestBody HeadquartersDailyVoForCreateWork vo);

    /**
     * 生成例行工作单
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/batchLabor", method = RequestMethod.POST)
    List<DispatchWorkOrderFlowVo> batchlabor(@RequestBody HeadquartersDailyTaskVoForBatchLabor vo);

    /**
     * 根据条件查询总部计划数据
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersPlan/getHeadquartersPlanAllByFilter", method = RequestMethod.POST)
    public  List<HeadquartersPlanVo> getHeadquartersPlanAllByFilter(Map<String, Object> filter);

}

@Component
class HeadquartersPlanClientFallback implements HeadquartersPlanClient{



    @Override
    public EnerbosPage<HeadquartersPlanVo> findPageList(@RequestBody HeadquartersPlanVoForFilter filter) {
        return null;
    }


    @Override
    public HeadquartersPlanVo findDetailById(@RequestParam("ids") String id) {
        return null;
    }

    @Override
    public List<HeadquartersPlanVo> findDetailByIds(@RequestParam("ids") List ids, @RequestParam(value = "orgId") String orgId, @RequestParam(value = "siteId") String siteId) {
        return null;
    }

    @Override
    public Boolean batchDelete(@RequestParam("ids") List<String> ids) {
        return null;
    }

    @Override
    public Boolean upStatus(@RequestBody HeadquartersPlanVoForUpStatus vo) {
        return null;
    }

    @Override
    public HeadquartersPlanVo findDetail(@RequestParam("id") String id, @ApiParam(value = "组织ID", required = true) @RequestParam(value = "orgId") String orgId, @ApiParam(value = "站点ID", required = false) @RequestParam(value = "siteId") String siteId) {
        return null;
    }

    @Override
    public HeadquartersPlanVoForSave save(@RequestBody HeadquartersPlanVoForSave filter) {
        return null;
    }

    @Override
    public List<HeadquartersPlanVoForSave> batchSave(@RequestBody List<HeadquartersPlanVoForSave> filters) {
        return null;
    }

    @Override
    public Boolean batchRelease(@RequestParam("ids") List<String> ids) {
        return null;
    }

    @Override
    public Boolean createRoutineWork(@RequestBody HeadquartersDailyVoForCreateWork vo) {
        return null;
    }

    @Override
    public List<DispatchWorkOrderFlowVo> batchlabor(@RequestBody HeadquartersDailyTaskVoForBatchLabor vo) {
        return null;
    }

    @Override
    public List<HeadquartersPlanVo> getHeadquartersPlanAllByFilter(@RequestBody Map<String, Object> filter) {
        return null;
    }
}