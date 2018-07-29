package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.PatrolPlanForSaveVo;
import com.enerbos.cloud.eam.vo.PatrolPlanFrequencyVoForFilter;
import com.enerbos.cloud.eam.vo.PatrolPlanVo;
import com.enerbos.cloud.eam.vo.PatrolPlanVoForFilter;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolPlanClientFallback.class)
public interface PatrolPlanClient {
    /**
     * 根据过滤条和分页信息获取巡检路线列表
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/findPage",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<PatrolPlanVo> findPatrolPlanList(@RequestBody PatrolPlanVoForFilter patrolPlanVoForFilter);

    /**
     * 删除巡检路线
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/deleteByIds",method = RequestMethod.POST)
    @ResponseBody
    public abstract String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids);

    /**
     * 新增或更新巡检路线
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolPlan/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public abstract PatrolPlanVo saveOrUpdate(@RequestBody PatrolPlanForSaveVo patrolPlanForSaveVo);

    @RequestMapping(value = "/eam/micro/patrolPlan/findPatrolPlanVoByFrequency", method = RequestMethod.POST)
    PatrolPlanVo findPatrolPlanVoById(PatrolPlanFrequencyVoForFilter patrolPlanFrequencyVoForFilter);


    @RequestMapping(value = "/eam/micro/patrolPlan/findPatrolPlanVoById")
    PatrolPlanVo findPatrolPlanVoById(@RequestParam(value = "id")String id);


}

@Component
class PatrolPlanClientFallback implements FallbackFactory<PatrolPlanClient> {

    @Override
    public PatrolPlanClient create(Throwable throwable) {
        return new PatrolPlanClient() {
            @Override
            public EnerbosPage<PatrolPlanVo> findPatrolPlanList(@RequestBody PatrolPlanVoForFilter patrolPlanVoForFilter) {
                throw new RuntimeException(throwable.getMessage());

            }

            @Override
            public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
                return null;
            }


            @Override
            public PatrolPlanVo saveOrUpdate(@RequestBody PatrolPlanForSaveVo patrolPlanForSaveVo) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public PatrolPlanVo findPatrolPlanVoById(PatrolPlanFrequencyVoForFilter patrolPlanFrequencyVoForFilter) {
                throw new RuntimeException(throwable.getMessage());

            }

            @Override
            public PatrolPlanVo findPatrolPlanVoById(@RequestParam(value = "id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}
