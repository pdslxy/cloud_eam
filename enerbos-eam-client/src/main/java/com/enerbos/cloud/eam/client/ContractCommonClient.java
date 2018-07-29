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
 * @date 2017/11/1
 * @Description
 */
@FeignClient(name = "enerbos-eam-microservice", url = "${eam.microservice.url:}", fallbackFactory = ContractCommonClientFallback.class)
public interface ContractCommonClient {
    @RequestMapping("/eam/micro/contract/collect")
    public abstract void collect(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    @RequestMapping("/eam/micro/contract/cancelCollect")
    public abstract void cancelCollectByCollectIdAndType(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("type") String type, @RequestParam("personId") String personId);

    @RequestMapping("/eam/micro/contract/findCollectByCollectIdAndTypeAndProductAndPerson")
    boolean findCollectByCollectIdAndTypeAndProductAndPerson(@RequestParam("id")String id, @RequestParam("type") String type,@RequestParam("productId") String productId, @RequestParam("personId") String personId);
}

@Component
class ContractCommonClientFallback implements FallbackFactory<ContractCommonClient> {

    @Override
    public ContractCommonClient create(Throwable throwable) {
        return new ContractCommonClient() {
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
