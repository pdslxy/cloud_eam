package com.enerbos.cloud.eam.microservice.repository.mybatis;

import com.enerbos.cloud.eam.vo.MaterialItemVo;
import com.enerbos.cloud.eam.vo.MaterialItemVoForAssertList;
import com.enerbos.cloud.eam.vo.MaterialItemVoForList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月14日 下午8:34:13
 * @Description 物资台账Dao层接口
 */
@Mapper
public interface MaterialItemDao {

    /**
     * 查询物资台账列表
     *
     * @param filters 查询条件
     * @return 物资台账列表实体
     */
    List<MaterialItemVoForList> findItems(Map<String, Object> filters);

    /**
     * 查询物资台账列表
     *
     * @param filters 查询条件
     * @return 物资台账列表实体
     */
    List<MaterialItemVoForList> findItemsNotInResevies(Map<String, Object> filters);

    /**
     * 根据设备id查询物资
     *
     * @param filters 查询条件
     * @return 数据
     */
    List<MaterialItemVoForAssertList> findItemByAssertId(Map<String, Object> filters);


    /**
     * 根据Id查询，二维码编码和站点id查询数据，若无则返回所有
     *
     * @param id
     * @param qrCodeNum
     * @param siteId
     * @return
     */
    List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(@Param("id") String[] id, @Param("qrCodeNum") String qrCodeNum, @Param("siteId") String siteId);

    /**
     * 设置是否更新状态
     *
     * @param id
     * @param siteId
     * @param b
     * @return
     */
    void updateIsupdatedata(String id, String siteId, boolean b);
}
