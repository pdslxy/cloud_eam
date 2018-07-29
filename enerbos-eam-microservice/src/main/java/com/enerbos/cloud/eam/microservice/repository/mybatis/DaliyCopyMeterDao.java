package com.enerbos.cloud.eam.microservice.repository.mybatis;

        import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterDetail;
        import com.enerbos.cloud.eam.vo.DailyCopyMeterDetailForList;
        import com.enerbos.cloud.eam.vo.DailyCopyMeterDetailVo;
        import com.enerbos.cloud.eam.vo.DailyCopyMeterVo;
        import com.enerbos.cloud.eam.vo.DailyCopyMeterVoForList;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;
        import java.util.Map;

@Mapper
public interface DaliyCopyMeterDao {

    /**
     * 获取抄表信息列表
     *
     * @param filters 查询条件
     * @return 返回数据
     */
    public List<DailyCopyMeterVoForList> findCopyMeters(
            Map<String, Object> filters);

    /**
     * 根据抄表单id查找抄表信息
     *
     * @param id     抄表单id
     * @param siteId 站点
     * @param orgId  组织
     * @return 数据
     */
    List<DailyCopyMeterDetailForList> findCopyMeterDetails(@Param("id") String id, @Param("siteId") String siteId, @Param("orgId") String orgId);

    /**
     * 根究materId查询最近一条数据
     *
     * @param meterId
     * @return
     */
    DaliyCopyMeterDetail findCopyMeterDetailById(@Param("meterId") String meterId);

    /**
     * 根据抄表id查询详细列表
     *
     * @param id 抄表id
     * @return
     */
    List<DailyCopyMeterDetailForList> findCopyMeterDetailByMeterId(@Param("id") String id, @Param("siteId") String siteId, @Param("orgId") String orgId);

    List<DailyCopyMeterVo> findByIds(@Param("ids") List<String> ids);

    /**
     * 根据抄表id和仪表id查询相关详细信息
     *
     * @param copyMeterId
     * @param meterId
     * @return
     */
    DailyCopyMeterDetailVo findCopyMeterDetailByCopyMeterIdAndMeterId(@Param("copyMeterId") String copyMeterId, @Param("meterId") String meterId);
}
