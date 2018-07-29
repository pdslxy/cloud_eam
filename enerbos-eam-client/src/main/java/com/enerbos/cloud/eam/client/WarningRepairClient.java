package com.enerbos.cloud.eam.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.enerbos.cloud.eam.vo.WarningRepairVo;

import feign.hystrix.FallbackFactory;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/10/12 下午4:51
 * @Description
 */

@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = WarningRepairClientFallback.class)
public interface WarningRepairClient {
    /**
     * 根据点位名称和 报警类型查找 关联工单的记录
     * @param tagName
     * @param warningType
     * @return
     */
    @RequestMapping(value = "/eam/micro/warningRepairs/findByTagNameAndWaringType" ,method = RequestMethod.GET)
    public WarningRepairVo findByTagNameAndWaringType(@RequestParam("tagName") String tagName , @RequestParam("warningType") String warningType);


    /**
     * 添加一个报警工单关联记录
     * @param warningRepairVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/warningRepairs/add" ,method = RequestMethod.POST)
    public WarningRepairVo add(@RequestBody WarningRepairVo warningRepairVo);


}
@Component
class WarningRepairClientFallback implements FallbackFactory<WarningRepairClient> {

    @Override
    public WarningRepairClient create(Throwable cause) {
        return new WarningRepairClient() {
            /**
             * 根据点位名称和 报警类型查找 关联工单的记录
             *
             * @param tagName
             * @param warningType
             * @return
             */
            @Override
            public WarningRepairVo findByTagNameAndWaringType(@RequestParam("tagName") String tagName, @RequestParam("warningType") String warningType) {
                return null;
            }

            /**
             * 添加一个报警工单关联记录
             *
             * @param warningRepairVo
             * @return
             */
            @Override
            public WarningRepairVo add(@RequestBody WarningRepairVo warningRepairVo) {
                return null;
            }
        };
    }
}