package com.enerbos.cloud.eam.client;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/10/17
 * @Description
 */
@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallbackFactory = DailyCommonClientFallback.class)
public interface DailyCommonClient {
    @RequestMapping("/eam/micro/daily/collect")
    public abstract void collect(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    @RequestMapping("/eam/micro/daily/cancelCollect")
    public abstract void cancelCollectByCollectIdAndType(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    @RequestMapping("/eam/micro/daily/findCollectByCollectIdAndTypeAndProductAndPerson")
    boolean findCollectByCollectIdAndTypeAndProductAndPerson(@RequestParam("id") String id, @RequestParam("type") String type, @RequestParam("productId") String productId, @RequestParam("personId") String personId);
}

@Component
class DailyCommonClientFallback implements FallbackFactory<DailyCommonClient> {

    @Override
    public DailyCommonClient create(Throwable throwable) {
        return new DailyCommonClient() {
            @Override
            public void collect(@RequestParam("id") String id, @RequestParam("productId") String product, @RequestParam("type") String type, @RequestParam("personId") String personId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public void cancelCollectByCollectIdAndType(String id, String productId, String type, String personId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public boolean findCollectByCollectIdAndTypeAndProductAndPerson(String id, String type, String productId, String personId) {
                throw new RuntimeException(throwable.getMessage());
            }
        };
    }
}