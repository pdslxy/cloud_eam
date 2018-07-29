package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.service.ContractRfCollectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/10/17
 * @Description 合同管理通用功能
 */
@RestController
public class ContractCommonController {
    private static Logger logger = LoggerFactory.getLogger(ContractCommonController.class);

    @Autowired
    ContractRfCollectorService contractRfCollectorService;

    /**
     * 收藏
     *
     * @param id
     * @param productId
     * @param type
     * @param personId
     */
    @RequestMapping("/eam/micro/contract/collect")
    public void collect(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("type") String type, @RequestParam("personId") String personId) {
        logger.info("/eam/micro/contract/collect, id:", id);

        contractRfCollectorService.collect(id, productId, type, personId);
    }

    /**
     * 取消收藏
     *
     * @param id
     * @param type
     */
    @RequestMapping("/eam/micro/contract/cancelCollect")
    public void cancelCollectByCollectIdAndType(@RequestParam("id") String id, @RequestParam("productId") String productId, @RequestParam("type") String type, @RequestParam("personId") String personId) {
        logger.info("/eam/micro/contract/cancelCollect, id:", id);

        contractRfCollectorService.cancelCollectByCollectIdAndType(id, productId, type, personId);
    }

    /**
     * 查询收藏
     *
     * @param id
     * @param type
     * @param productId
     * @param personId
     * @return
     */
    @RequestMapping("/eam/micro/contract/findCollectByCollectIdAndTypeAndProductAndPerson")
    public boolean findCollectByCollectIdAndTypeAndProductAndPerson(@RequestParam("id") String id, @RequestParam("type") String type, @RequestParam("productId") String productId, @RequestParam("personId") String personId) {
        logger.info("/eam/micro/contract/findCollectByCollectIdAndTypeAndProductAndPerson, id:", id);
        return contractRfCollectorService.findByCollectIdAndTypeAndProductAndPerson(id, type, productId, personId);
    }

}
