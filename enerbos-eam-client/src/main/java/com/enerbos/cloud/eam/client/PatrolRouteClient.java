package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.PatrolPointVoForFilter;
import com.enerbos.cloud.eam.vo.PatrolRouteForSaveVo;
import com.enerbos.cloud.eam.vo.PatrolRouteVo;
import com.enerbos.cloud.eam.vo.PatrolRouteVoForFilter;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = PatrolRouteClientFallback.class)
public interface PatrolRouteClient {
    /**
     * 根据过滤条和分页信息获取巡检路线列表
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/findPage",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<PatrolRouteVo> findPatrolRouteList(@RequestBody PatrolRouteVoForFilter patrolRouteVoForFilter);

    /**
     * 删除巡检路线
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/deleteByIds",method = RequestMethod.POST)
    @ResponseBody
    public abstract String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids);

    /**
     * 新增或更新巡检路线
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public abstract PatrolRouteVo saveOrUpdate(@RequestBody PatrolRouteForSaveVo patrolRouteForSaveVo);

    @RequestMapping(value = "/eam/micro/patrolRoute/findPatrolRouteVoById", method = RequestMethod.POST)
    PatrolRouteVo findPatrolRouteVoById(PatrolPointVoForFilter patroPointVoForFilter);

    @RequestMapping(value = "/eam/micro/patrolRoute/findRouteByPointId", method = RequestMethod.GET)
    public List<PatrolRouteVo> findRouteByPointId(@RequestParam("pointId") String pointId);

    /**
     * 根据巡检工单查询巡检路线
     * @param
     * @return
     */
    @RequestMapping(value = "/eam/micro/patrolRoute/findRouteByOrderId", method = RequestMethod.GET)
    PatrolRouteVo findRouteByOrderId(@RequestParam("id") String id);
}

@Component
class PatrolRouteClientFallback implements FallbackFactory<PatrolRouteClient> {

    @Override
    public PatrolRouteClient create(Throwable throwable) {
        return new PatrolRouteClient() {
            @Override
            public EnerbosPage<PatrolRouteVo> findPatrolRouteList(@RequestBody PatrolRouteVoForFilter patrolRouteVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids) {
                throw new RuntimeException(throwable.getMessage());

            }

            @Override
            public PatrolRouteVo saveOrUpdate(@RequestBody PatrolRouteForSaveVo patrolRouteForSaveVo) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public PatrolRouteVo findPatrolRouteVoById(PatrolPointVoForFilter patroPointVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List<PatrolRouteVo> findRouteByPointId(@RequestParam("pointId") String pointId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public PatrolRouteVo findRouteByOrderId(String id) {
                throw new RuntimeException(throwable.getMessage());
            }

        };
    }
}
