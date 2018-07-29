package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.PatrolRecordVo;
import com.enerbos.cloud.eam.vo.PatrolRouteVo;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolRecordClientFallback.class)
public interface PatrolRecordClient {

    @RequestMapping(value = "/eam/micro/patrolRecord/findById", method = RequestMethod.GET)
    public PatrolRecordVo findById(@RequestParam("id") String id);

    @RequestMapping(value = "/eam/micro/patrolRecord/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PatrolRecordVo patrolRecordVo);

    /**
     * 根据工单ID查询巡检记录表的巡检点
     *
     * @param id 工单ID
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecord/findPatrolRecordByOrderId", method = RequestMethod.GET)
    public PatrolRouteVo findPatrolRecordByOrderId(@RequestParam("id") String id);
}

@Component
class PatrolRecordClientFallback implements FallbackFactory<PatrolRecordClient> {

    @Override
    public PatrolRecordClient create(Throwable throwable) {
        return new PatrolRecordClient() {
            @Override
            public PatrolRecordVo findById(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void saveOrUpdate(@RequestBody PatrolRecordVo patrolRecordVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public PatrolRouteVo findPatrolRecordByOrderId(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}