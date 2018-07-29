package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;

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
public interface ContractRfCollectorService {

    /**
     * 收藏
     *
     * @param id 收藏数据id
     * @param product   产品
     * @param type      收藏类型
     * @param personId  人员Id
     * @throws EnerbosException
     */
    public void collect(String id, String product, String type, String personId) throws EnerbosException;

    /**取消收藏
     *
     * @param id 收藏数据id
     * @param type 收藏类型
     * @throws EnerbosException
     */
    public void cancelCollectByCollectIdAndType(String id, String product, String type, String personId) throws EnerbosException;


    /**
     * 查询收藏
     *
     * @param id
     * @param productId
     * @param type
     * @param personId
     * @return
     */
    boolean findByCollectIdAndTypeAndProductAndPerson(String id, String type, String productId, String personId);
}
