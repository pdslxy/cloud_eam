package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.RepairEvaluateVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/19.
 */
@FeignClient(name = "enerbos-eam-microservice",url = "${eam.microservice.url:}", fallbackFactory = RepairEvaluateClientFallback.class)
public interface RepairEvaluateClient {
    /**
     * createRepairOrder: 添加评价
     * @param repairEvaluateVo 评价VO
     * @return repairEvaluateVo 评价vo
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/saveRepairEvaluate")
    public RepairEvaluateVo saveRepairEvaluate(@RequestBody RepairEvaluateVo repairEvaluateVo);

    /***
     * 根据报修工单查询评价.
     * @param repairOrderId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/findRepairEvalueByOrderId")
    public RepairEvaluateVo findRepairEvalueByOrderId(@RequestParam("repairOrderId") String repairOrderId);
    
    
}

@Component
class RepairEvaluateClientFallback implements FallbackFactory<RepairEvaluateClient> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RepairEvaluateClient create(Throwable throwable) {


        return new RepairEvaluateClient() {


            @Override
            public RepairEvaluateVo saveRepairEvaluate(RepairEvaluateVo repairEvaluateVo) {
                return null;
            }

            @Override
            public RepairEvaluateVo findRepairEvalueByOrderId(String repairOrderId) {
                return null;
            }
        };
    }
}
