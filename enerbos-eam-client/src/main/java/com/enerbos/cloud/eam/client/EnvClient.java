package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.vo.MeterRfCollectorVo;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/10.
 */
@FeignClient(name = "enerbos-eam-microservice",url = "${eam.microservice.url:}", fallbackFactory = EnvClientFallback.class)
public interface EnvClient {

    /**
     * 查询环境监测的报修工单总数
     * @param orgId
     * @param siteId
     * @param orderSource
     * @return
     */
    @RequestMapping("/eam/micro/Env/getRepairOrderCount")
    int getRepairOrderCount(@RequestParam("orgId") String orgId, @RequestParam("siteId") String siteId, @RequestParam("orderSource") String orderSource);
    
    @RequestMapping(value = "/eam/micro/meter/collect", method = RequestMethod.POST)
    void collect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId,  @RequestParam("personId") String personId,@RequestParam("meterId") String meterId);

    @RequestMapping(value = "/eam/micro/meter/findByPersonId", method = RequestMethod.POST)
    List<MeterRfCollectorVo> findByPersonId(@RequestParam("personId") String personId);
}
@Component
class  EnvClientFallback implements FallbackFactory<EnvClient> {
    @Override
    public EnvClient create(Throwable throwable) {
        return new EnvClient() {
            @Override
            public int getRepairOrderCount(String orgId,String siteId, String orderSource) {
                return 0;
            }

            @Override
            public void collect(String id, String eamProdId,String personId,String meterId) {
                
            }

            @Override
            public List<MeterRfCollectorVo> findByPersonId(@RequestParam("personId") String personId) {
                return null;
            }
        };
    }
}
