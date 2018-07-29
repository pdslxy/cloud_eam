package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.PatrolRecordTermVo;
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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolRecordTermClientFallback.class)
public interface PatrolRecordTermClient {
    @RequestMapping(value = "/eam/micro/patrolRecordTerm/findById", method = RequestMethod.GET)
    public PatrolRecordTermVo findById(@RequestParam("id") String id);

    @RequestMapping(value = "/eam/micro/patrolRecordTerm/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PatrolRecordTermVo patrolRecordTermVo);

    /**
     * 查询巡检项
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRecordTerm/findPatrolTermByOrderAndPoint", method = RequestMethod.GET)
    EnerbosPage<PatrolRecordTermVo> findPatrolTermByOrderAndPoint(@RequestParam("id") String id, @RequestParam("pointid") String pointid);

}

@Component
class PatrolRecordTermClientFallback implements FallbackFactory<PatrolRecordTermClient> {

    @Override
    public PatrolRecordTermClient create(final Throwable throwable) {
        return new PatrolRecordTermClient() {
            @Override
            public PatrolRecordTermVo findById(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void saveOrUpdate(@RequestBody PatrolRecordTermVo patrolRecordTermVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public EnerbosPage<PatrolRecordTermVo> findPatrolTermByOrderAndPoint(String id, String pointid) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}