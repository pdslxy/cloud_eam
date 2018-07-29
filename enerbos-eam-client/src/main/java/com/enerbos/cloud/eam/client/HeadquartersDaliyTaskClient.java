package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
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
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/16 13:53
 * @Description:
 */

@FeignClient(name = "enerbos-eam-microservice",url = "${eam.microservice.url:}", fallback = HeadquartersDaliyTaskClientFallback.class)
public interface HeadquartersDaliyTaskClient {

    /**
     * 总部计划--分页、筛选、排序
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/findPageList", method = RequestMethod.POST)
    EnerbosPage<HeadquartersDailyTaskVo> findPageList(@RequestBody HeadquartersDailyTaskVoForFilter filter);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/delete", method = RequestMethod.POST)
    Boolean batchDelete(@RequestParam("ids") String ids);

    /**
     * 修改状态
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/upStrtus", method = RequestMethod.POST)
    Boolean upStatus(@RequestParam("ids")  String[] ids,@RequestParam("status") String Status);
    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/findDetail", method = RequestMethod.POST)
    HeadquartersDailyTaskVo findDetail(@RequestParam("id") String id);
    /**
     * 保存
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/save", method = RequestMethod.POST)
    HeadquartersDailyTaskVo save(@RequestBody HeadquartersDailyTaskVo filter);


    /**
     * 根据例行工作计划Id,组织，站点查询该工单生成的例行工作单
     * @param planId 计划Id
     * @param orgId  组织Id
     * @param siteId 站点Id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDailyTask/findDailyTaskByplanId", method = RequestMethod.POST)
    public  List<HeadquartersDailyTaskVo> findDailyTaskByplanId(
            @RequestParam("planId") String planId,
            @RequestParam("orgId") String orgId,
            @RequestParam("siteId") String siteId
    );

}

@Component
class HeadquartersDaliyTaskClientFallback implements HeadquartersDaliyTaskClient{

    @Override
    public EnerbosPage<HeadquartersDailyTaskVo> findPageList(@RequestBody HeadquartersDailyTaskVoForFilter filter) {
        return null;
    }

    @Override
    public Boolean batchDelete(@RequestParam("ids") String ids) {
        return null;
    }

    @Override
    public Boolean upStatus(@RequestParam("ids")  String[] ids, @RequestParam("status") String Status) {
        return null;
    }

    @Override
    public HeadquartersDailyTaskVo findDetail(@RequestParam("id") String id) {
        return null;
    }

    @Override
    public HeadquartersDailyTaskVo save(@RequestBody HeadquartersDailyTaskVo filter) {
        return null;
    }

    @Override
    public List<HeadquartersDailyTaskVo> findDailyTaskByplanId(@RequestParam("planId") String planId, @RequestParam("orgId") String orgId, @RequestParam("siteId") String siteId) {
        return null;
    }
}
