package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeter;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterDetail;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DaliyCopyMeterService {

    /**
     * 获取抄表信息列表
     *
     * @param dailyCopyMeterFilterVo 查询条件 @link{com.enerbos.cloud.eam.vo.DailyCopyMeterFilterVo}
     * @return 返回查询 数据
     */
    PageInfo<DailyCopyMeterVoForList> findCopyMeters(DailyCopyMeterFilterVo dailyCopyMeterFilterVo) throws Exception;

    /**
     * 新增抄表单
     *
     * @param daliyCopyMeter 抄表实体类@link{com.enerbos.cloud.eam.vo.DailyCopyMeterVo}
     * @return 保存后实体
     */
    DaliyCopyMeter saveCopyMeter(DaliyCopyMeter daliyCopyMeter);

    /**
     * 新增抄表单详细内容
     *
     * @param daliyCopyMeterDetails 抄表单详细内容列表
     * @return 增加后列表
     */
    List<DaliyCopyMeterDetail> saveCopyMeterDetail(List<DaliyCopyMeterDetail> daliyCopyMeterDetails);

    /**
     * 根据id删除
     *
     * @param ids
     */
    void deleteCopyMeter(String[] ids);

    /**
     * 根据抄表单id查找抄表信息
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return 数据
     */
    PageInfo<DailyCopyMeterDetailForList> findCopyMeterDetails(String id, String siteId, String orgId);

    /**
     * 查找抄表单
     *
     * @param id
     * @return
     */
    DaliyCopyMeter findCopyMeterById(String id);


    /**
     * 根据id删除抄表单详细列表
     *
     * @param ids 抄表单id
     * @return
     */
    void deleteCopyMeterDetail(String[] ids);

    /**
     * 根据id查询抄表详细内容
     *
     * @param meterId
     * @return
     */
    DaliyCopyMeterDetail findCopyMeterDetailById(String meterId);

    /**
     * 修改抄表单状态
     *
     * @param ids 操作的id
     * @return
     */
    void updateCopyMeterStatus(String[] ids, String status);

    /**
     * 根据抄表id查询详细列表
     *
     * @param id 抄表id
     * @return
     */
    PageInfo<DailyCopyMeterDetailForList> findCopyMeterDetailByMeterId(String id, String siteId, String orgId);


    List<DailyCopyMeterDetailForList> findcopymeterDetailByCopyMeterId(String copyMeterId) throws EnerbosException;

    List<DailyCopyMeterVo>  findCopyMeterByIds(List<String> ids);

    /**
     * 根据抄表id和仪表id查询相关详细信息
     * @param copyMeterId
     * @param meterId
     * @return
     */
    DailyCopyMeterDetailVo findCopyMeterDetailByCopyMeterIdAndMeterId(String copyMeterId, String meterId);
}
