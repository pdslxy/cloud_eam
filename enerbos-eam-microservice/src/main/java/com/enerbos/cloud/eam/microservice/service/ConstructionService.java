package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.Construction;
import com.enerbos.cloud.eam.vo.ConstructionForFilterVo;
import com.enerbos.cloud.eam.vo.ConstructionForListVo;
import com.enerbos.cloud.eam.vo.ConstructionRfCollectorVo;
import com.enerbos.cloud.eam.vo.ConstructionVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月9日
 * @Description 施工单service
 */
public interface ConstructionService {

    /**
     * findConstructionByID: 根据ID查询施工单
     *
     * @param id
     * @return Construction
     */
    Construction findConstructionByID(String id);

    /**
     * findConstructionByConstructionNum: 根据constructionNum查询施工单
     *
     * @param constructionNum 施工单编码
     * @return ConstructionVo 施工单
     */
    ConstructionVo findConstructionByConstructionNum(String constructionNum);

    /**
     * findPageConstructionList:分页查询施工单
     *
     * @param constructionForFilterVo 过滤查询条件vo {@link ConstructionForFilterVo}
     * @return List<ConstructionForListVo>
     */
    List<ConstructionForListVo> findPageConstructionList(ConstructionForFilterVo constructionForFilterVo);

    /**
     * save:保存施工单
     *
     * @param construction 施工单对象 {@link Construction}
     * @return Construction
     */
    Construction save(Construction construction);

    /**
     * deleteConstructionByIds:根据ID集合删除施工单
     *
     * @param ids
     * @return
     */
    void deleteConstructionByIds(List<String> ids) throws Exception;

    /**
     * collectConstruction:收藏施工单
     *
     * @param constructionRfCollectorVoList 收藏的施工单列表 {@link ConstructionRfCollectorVo}
     */
    void collectConstruction(List<ConstructionRfCollectorVo> constructionRfCollectorVoList);

    /**
     * cancelCollectConstruction:取消收藏
     *
     * @param constructionRfCollectorVoList 需要取消收藏的施工单列表 {@link ConstructionRfCollectorVo}
     */
    void cancelCollectConstruction(List<ConstructionRfCollectorVo> constructionRfCollectorVoList);

    boolean checkIsCollect(String id, String productId, String personId);
}
