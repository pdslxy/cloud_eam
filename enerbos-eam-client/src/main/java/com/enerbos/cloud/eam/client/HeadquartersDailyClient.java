package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.HeadquartersDailyVo;
import com.enerbos.cloud.eam.vo.HeadquartersDailyVoForFilter;
import com.enerbos.cloud.eam.vo.HeadquartersDailyVoForSave;
import com.enerbos.cloud.eam.vo.HeadquartersDailyVoForUpStatus;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 周长松
 * @version 1.0
 * @date 2017/8/17 11:29
 * @Description
 */
@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallback = HeadquartersDailyClientFallback.class)
public interface HeadquartersDailyClient {
    /**
     * 总部事物，例行工作--分页、筛选、排序
     * @param filter
     * @return
     */


    @RequestMapping(value = "/eam/micro/headquartersDaily/findPageList", method = RequestMethod.POST)
    EnerbosPage<HeadquartersDailyVo> findPageList(@RequestBody HeadquartersDailyVoForFilter filter);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/delete", method = RequestMethod.POST)
    Boolean batchDelete(@RequestParam("ids") List<String> ids);

    /**
     * 批量修改状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/upStrtus", method = RequestMethod.POST)
    Boolean upStatus(@RequestBody HeadquartersDailyVoForUpStatus vo);
    /**
     * 查询详细页
     * @param id
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/findDetail", method = RequestMethod.POST)
    HeadquartersDailyVo findDetail(@RequestParam("id") String id);
    /**
     * 保存
     * @param filter
     * @return
     */
    @RequestMapping(value = "/eam/micro/headquartersDaily/save", method = RequestMethod.POST)
    HeadquartersDailyVoForSave save(@RequestBody HeadquartersDailyVoForSave filter);
}

@Component
class HeadquartersDailyClientFallback implements HeadquartersDailyClient{

    @Override
    public EnerbosPage<HeadquartersDailyVo> findPageList(@RequestBody HeadquartersDailyVoForFilter filter) {
        return null;
    }

    @Override
    public Boolean batchDelete(@RequestParam("ids") List<String> ids) {
        return null;
    }

    @Override
    public Boolean upStatus(@RequestBody HeadquartersDailyVoForUpStatus vo) {
        return null;
    }

    @Override
    public HeadquartersDailyVo findDetail(@RequestParam("id") String id) {
        return null;
    }

    @Override
    public HeadquartersDailyVoForSave save(@RequestBody HeadquartersDailyVoForSave filter) {
        return null;
    }
}