package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.PatrolPoint;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/7/12
 * @Description
 */
public interface PatrolPointService {
    /**
     * 根据筛选条件和分页信息获取能源价格列表
     *
     * @return
     */
    PageInfo<PatrolPointVo> findPatrolPointList(PatrolPointVoForFilter patrolPointVoForFilter) throws Exception;

    /**
     * 根据id数组删除巡检点
     *
     * @param ids
     */
    void deletePatrolPointByIds(String[] ids) throws EnerbosException;

    /**
     * 保存巡检点和关联的巡检项信息
     *
     * @param patrolPointForSaveVo
     */
    PatrolPointVo saveOrUpdate(PatrolPointForSaveVo patrolPointForSaveVo) throws EnerbosException;

    /**
     * 根据id查询巡检点
     *
     * @param id
     */
    PatrolPoint findPatrolPointById(String id) throws EnerbosException;

    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param ids
     * @param qrCodeNum
     * @param siteId
     * @return
     * @throws EnerbosException
     */
    List<PatrolPointVo> findByIdAndQrCodeNumAndSiteId(String[] ids, String qrCodeNum, String siteId) throws EnerbosException;

    /**
     * 设置是否更新状态
     *
     * @param b
     * @return
     */
    boolean updateIsupdatedata(String id, String siteId, boolean b) throws EnerbosException;

    /**
     * 根据工单id查询巡检记录中巡检项内容
     * @param orderid
     * @param pointid
     * @return
     */
    PatrolRecordVo findPatrolRecordByOrderAndPoint(String orderid, String pointid) throws EnerbosException;

    void generateQrcode(List<String> ids) throws EnerbosException;
}
