package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVo;
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
 * @date 2017/8/2
 * @Description
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolFrequencyClientFallback.class)
public interface PatrolFrequencyClient {


    /**
     * 新增或更新巡检路线
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolFrequency/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public abstract boolean saveOrUpdate(@RequestBody PatrolPlanFrequencyVo patrolPlanFrequencyVo);

    /**
     * 根据计划Id查询频率的id集合
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolFrequency/findIdsByPlanId")
    @ResponseBody
    public abstract List<String> findIdsByPlanId(@RequestParam("id") String id);

}

@Component
class PatrolFrequencyClientFallback implements FallbackFactory<PatrolFrequencyClient> {


    @Override
    public PatrolFrequencyClient create(Throwable throwable) {
        return new PatrolFrequencyClient(){

            @Override
            public boolean saveOrUpdate(@RequestBody PatrolPlanFrequencyVo patrolPlanFrequencyVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List<String> findIdsByPlanId(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}
